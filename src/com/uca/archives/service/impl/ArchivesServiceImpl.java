package com.uca.archives.service.impl;

import java.io.File;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;

import cn.com.capinfo.core.pojos.BaseUser;
import cn.com.capinfo.core.utils.DateUtil;
import cn.com.capinfo.core.utils.Page;
import cn.com.capinfo.core.utils.ParameterUtil;
import cn.com.capinfo.core.utils.SecurityIdentifyUtils;
import cn.com.capinfo.core.utils.SendEmail;
import cn.com.capinfo.core.utils.SystemProperties;
import cn.com.capinfo.security.utils.SecurityUtils;

import com.alibaba.fastjson.JSONObject;
import com.uca.archives.dao.ArchivesDao;
import com.uca.archives.dao.OrderNumDao;
import com.uca.archives.pojos.UcaArchives;
import com.uca.archives.pojos.UcaOrderNum;
import com.uca.archives.service.ArchivesService;
import com.uca.archives.vo.UcaArchivesVo;
import com.uca.constants.UcaConstants;
import com.uca.ucasecurity.dao.UmUserDao;
import com.uca.ucasecurity.dao.UmUserExpandDao;
import com.uca.ucasecurity.pojos.UmUser;
import com.uca.ucasecurity.pojos.UmUserExpand;
import com.uca.utils.BuildFileNameUtil;

@Service
public class ArchivesServiceImpl implements ArchivesService {

    @Autowired
    private ArchivesDao archivesDao;
    @Autowired
    private OrderNumDao orderNumDao;

    @Autowired
    private UmUserExpandDao umUserExpandDao;
    @Autowired
    private UmUserDao umUserDao;

    private SecurityIdentifyUtils secUtils = new SecurityUtils();

    /**
     * 保存和更新档案
     * 
     * @param object
     * @param po
     */
    @Override
    public void saveOrUpdateArchives(JSONObject object, UcaArchives po) {
        // TODO Auto-generated method stub
        if (po.getArchivesId() == null) {
            this.saveArchives(object, po);
        } else {
            this.updateArchives(object, po);
        }
    }

    private void saveArchives(JSONObject object, UcaArchives po) {
        Date time = new Date();
        po.setUploadTime(time);
        BaseUser user = secUtils.getUser();
        po.setUploadUserId(user.getUserId());
        po.setUpdateTime(time);
        // 初始化状态
        po.setQueryStatus(UcaConstants.APPLY_QUERY_RECORD_STATUS_TWO);
        po.setCopyStatus(UcaConstants.ARCHIVES_COPY_STATUS_ZERO);
        po.setIntoFileStatus(UcaConstants.ARCHIVES_INTO_FILE_STATUS_ZERO);
        po.setRenameStatus(UcaConstants.RENAME_STATUS_N);
        UmUserExpand userExpand = (UmUserExpand) user.getProperty("userExpand");
        if (userExpand != null)
            po.setUploadOrgName(userExpand.getDevOrg());
        this.archivesDao.save(po);
        if (po.getCheckStatus() == UcaConstants.ARCHIVES_CHECK_STATUS_INIT) {
            object.put("msg", "档案保存成功！");
        } else if (po.getCheckStatus() == UcaConstants.ARCHIVES_CHECK_STATUS_SENDING) {
            object.put("msg", "档案提交审核成功！");
        }
        object.put("success", true);
    }

    private void updateArchives(JSONObject object, UcaArchives po) {
        UcaArchives ua = this.archivesDao.queryById(po.getArchivesId());
        if (ua != null) {
            // 删除原有的文件
            if (ua.getFileUrl() != null && !ua.getFileUrl().equals(po.getFileUrl())) {
                File file = new File(BuildFileNameUtil.UPLOAD_HOME + File.separator + BuildFileNameUtil.UPLOAD_URL
                        + File.separator + ua.getFileUrl());
                if (file.exists())
                    file.delete();
            }
            ua.setFileUrl(po.getFileUrl());
            ua.setSourceFileName(po.getSourceFileName());
            ua.setHandingPersonId(po.getHandingPersonId());// 设置经办人
            // if (po.getType() == UcaConstants.ARCHIVES_TYPE_PROJECT)
            ua.setProjectName(po.getProjectName());
            BaseUser user = secUtils.getUser();
            Date updateTime = new Date();
            // 保存或送审
            if (ua.getCheckStatus() == UcaConstants.ARCHIVES_CHECK_STATUS_INIT) {
                ua.setUpdateUserId(user.getUserId());
                ua.setUpdateTime(updateTime);
                if (po.getCheckStatus() == UcaConstants.ARCHIVES_CHECK_STATUS_INIT) {
                    object.put("msg", "档案保存成功！");
                } else if (po.getCheckStatus() == UcaConstants.ARCHIVES_CHECK_STATUS_SENDING) {
                    ua.setCheckStatus(UcaConstants.ARCHIVES_CHECK_STATUS_SENDING);
                    object.put("msg", "档案提交审核成功！");
                }
                object.put("success", true);
                this.archivesDao.saveOrUpdate(ua);
            }
            // 重新上传
            else if (ua.getCheckStatus() == UcaConstants.ARCHIVES_CHECK_STATUS_FAILED) {
                ua.setCheckStatus(UcaConstants.ARCHIVES_CHECK_STATUS_AGAIN);
                this.archivesDao.saveOrUpdate(ua);

                po.setArchivesId(null);
                po.setUploadTime(new Date());
                po.setUploadUserId(ua.getUploadUserId());
                UmUserExpand userExpand = (UmUserExpand) user.getProperty("userExpand");
                po.setUploadOrgName(userExpand.getDevOrg());
                po.setCheckStatus(UcaConstants.ARCHIVES_CHECK_STATUS_SENDING);
                po.setHandingPersonId(ua.getHandingPersonId());
                po.setCopyStatus(UcaConstants.ARCHIVES_COPY_STATUS_ZERO);
                po.setQueryStatus(UcaConstants.APPLY_QUERY_RECORD_STATUS_TWO);
                po.setSourceId(ua.getArchivesId());
                po.setUpdateUserId(null);
                po.setUpdateTime(null);
                po.setInstruction(null);
                po.setCheckTime(null);
                this.archivesDao.save(po);
                object.put("success", true);
                object.put("msg", "档案重新上传成功！");
            }
            // 重复提交审核
            else {
                object.put("success", false);
                object.put("msg", "请勿重复提交！");
            }
        } else {
            object.put("success", false);
            object.put("msg", "选择的记录不存在！");
        }
    }

    /**
     * 档案分页查询
     * 
     * @param object
     * @param page
     * @param type
     *            档案类型（1：工程档案，2：声像档案）
     */
    @Override
    public void findArchives(JSONObject object, Page<UcaArchivesVo> page, Integer type) {
        // TODO Auto-generated method stub
        // StringBuilder sql = new
        // StringBuilder("select ua.archives_id,ua.archives_num,ua.project_name,ua.upload_time,ua.check_status,u.user_name,ua.instruction,ua.file_url,ua.source_file_name,ua.file_url_other,ua.source_file_name_other from uca_archives as ua left join um_user as u on ua.handing_person_id = u.user_id where ua.upload_user_id =:uploadUserId and ua.type=:type and ua.check_status in(0,1,2,3) order by ua.check_status asc,ua.upload_time desc");
        StringBuilder sql = new StringBuilder(
                "select ua.archives_id,ua.archives_num,ua.project_name,ua.upload_time,ua.check_status,u.user_name,ua.instruction,ua.file_url,ua.source_file_name,ua.file_url_other,ua.source_file_name_other from uca_archives as ua left join um_user as u on ua.handing_person_id = u.user_id where ua.upload_user_id =:uploadUserId and ua.type=:type and ua.check_status in(0,1,2,3) order by ua.upload_time desc");
        String pagingSql = " limit " + page.getPageNo() + "," + page.getPageSize();
        BaseUser user = secUtils.getUser();
        SQLQuery query = this.archivesDao.getSession().createSQLQuery(sql.append(pagingSql).toString());
        query.setParameter("uploadUserId", user.getUserId());
        query.setParameter("type", type);

        List<Object[]> list = query.list();
        List<UcaArchivesVo> listResult = new ArrayList<UcaArchivesVo>(list.size());
        UcaArchivesVo vo = null;
        if (list != null && list.size() > 0) {
            for (Object[] obj : list) {
                vo = new UcaArchivesVo();
                vo.setArchivesId(Integer.valueOf(obj[0].toString()));
                vo.setArchivesNum(obj[1] != null ? obj[1].toString() : "");
                vo.setProjectName(obj[2] != null ? obj[2].toString() : "");
                vo.setUploadTime(obj[3] != null ? DateUtil.DateToStr((Date) obj[3]) : "");
                vo.setCheckStatus(Integer.valueOf(obj[4].toString()));
                vo.setUserName(obj[5] != null ? obj[5].toString() : "");
                vo.setInstruction(obj[6] != null ? obj[6].toString().replace("null", "") : "");
                vo.setFileUrl(obj[7] != null ? obj[7].toString() : "");
                vo.setSourceFileName(obj[8] != null ? obj[8].toString() : "");
                vo.setFileUrlOther(obj[9] != null ? obj[9].toString() : "");
                vo.setSourceFileNameOther(obj[10] != null ? obj[10].toString() : "");
                listResult.add(vo);
            }
        }

        page.setListResult(listResult);

        pagingSql = "select count(1) from uca_archives as ua left join um_user as u on ua.handing_person_id = u.user_id where ua.upload_user_id =:uploadUserId and ua.type=:type and ua.check_status in(0,1,2,3)";
        query = this.archivesDao.getSession().createSQLQuery(pagingSql);
        query.setParameter("uploadUserId", user.getUserId());
        query.setParameter("type", type);

        List countList = query.list();
        object.put("rows", page.getListResult());
        object.put("total", (BigInteger) countList.get(0));
    }

    /**
     * 根据ID获取档案
     * 
     * @param object
     * @param id
     */
    @Override
    public void getArchivesById(JSONObject object, Integer id, Integer type) {
        // TODO Auto-generated method stub
        UcaArchivesVo vo = new UcaArchivesVo();
        vo.setType(type);
        if (id != null) {
            UcaArchives po = this.archivesDao.queryById(id);
            vo.setArchivesId(po.getArchivesId());
            vo.setCheckStatus(po.getCheckStatus());
            vo.setFileUrl(po.getFileUrl());
            vo.setSourceFileName(po.getSourceFileName());
            vo.setHandingPersonId(po.getHandingPersonId());
            vo.setProjectName(po.getProjectName());
            if (po.getHandingPersonId() != null) {
                UmUserExpand expand = this.umUserExpandDao.queryById(po.getHandingPersonId());
                if (expand != null)
                    vo.setOrgId(expand.getOrgId());
            }
        }
        object.put("success", true);
        object.put("objPo", vo);
    }

    /**
     * 根据ArchivesId获取档案
     * 
     * @param archivesId
     */
    @Override
    public UcaArchives getArchivesById(int archivesId) {
        return archivesDao.queryById(archivesId);
    }

    /**
     * 档案复制进度查询
     * 
     * @param orderNum
     *            调卷单编号
     * @param page
     * @throws ParseException
     */
    @Override
    public void findArchivesSchedule(String orderNum, Page<UcaArchives> page) throws ParseException {
        // TODO Auto-generated method stub
        List<Criterion> cnList = new ArrayList<Criterion>(0);
        if (StringUtils.isNotEmpty(orderNum))
            cnList.add(Restrictions.like("orderNum", "%" + orderNum + "%"));
        // 查询最近3个月的记录
        String endTime = DateUtil.getStrDate_24();
        cnList.add(Restrictions.between("uploadTime", DateUtil.otherDate(endTime, "yyyy-MM-dd HH:mm:ss", -90),
                DateUtil.parseTime(endTime)));

        ProjectionList proList = Projections.projectionList();// 设置投影集合
        proList.add(Projections.property("t.orderNum").as("orderNum"));
        proList.add(Projections.property("t.uploadTime").as("uploadTime"));
        proList.add(Projections.property("t.copyStatus").as("copyStatus"));

        page.addOrderBy("uploadTime");
        page.addOrderDir("desc");

        this.archivesDao.findList(cnList, proList, page);
    }

    /**
     * 退档通知查询
     * 
     * @param page
     * @throws ParseException
     */
    @Override
    public void findArchivesScheduleBack(Page<UcaArchives> page) throws ParseException {
        // TODO Auto-generated method stub
        List<Criterion> cnList = new ArrayList<Criterion>(0);
        /*
         * if(StringUtils.isNotEmpty(orderNum))
         * cnList.add(Restrictions.like("orderNum", "%"+orderNum+"%"));
         */
        StringBuilder sql = new StringBuilder(
                "select ua.order_num,ue.dev_org,ua.update_time from uca_archives as ua inner join um_user_expand as ue on ua.upload_user_id = ue.user_id where ua.copy_status = 1 and check_time between :beginTime and :endTime order by ua.check_time desc");
        String pagingSql = " limit " + page.getPageNo() + "," + page.getPageSize();
        SQLQuery query = this.archivesDao.getSession().createSQLQuery(sql.append(pagingSql).toString());

        // 查询最近3个月的记录
        String endTime = DateUtil.getStrDate_24();
        query.setParameter("beginTime", DateUtil.otherDate(endTime, "yyyy-MM-dd HH:mm:ss", -90));
        query.setParameter("endTime", endTime);

        List<Object[]> list = query.list();
        List<UcaArchives> listResult = new ArrayList<UcaArchives>(list.size());
        UcaArchives po = null;
        if (list != null && list.size() > 0) {
            for (Object[] obj : list) {
                po = new UcaArchives();
                po.setOrderNum(obj[0] != null ? obj[0].toString() : "");
                po.setDevOrg(obj[1] != null ? obj[1].toString() : "");
                po.setUpdateTime(obj[2] != null ? (Date) obj[2] : null);
                listResult.add(po);
            }
        }

        page.setListResult(listResult);

        pagingSql = "select count(1) from uca_archives as ua inner join um_user_expand as ue on ua.upload_user_id = ue.user_id where ua.copy_status = 1 and check_time between :beginTime and :endTime";
        query = this.archivesDao.getSession().createSQLQuery(pagingSql);
        query.setParameter("beginTime", DateUtil.otherDate(endTime, "yyyy-MM-dd HH:mm:ss", -90));
        query.setParameter("endTime", endTime);

        List countList = query.list();
        page.setTotalRowCount(((BigInteger) countList.get(0)).intValue());
    }

    @Override
    public void findArchivesAuditing(JSONObject object, Page<UcaArchivesVo> page, Integer type, Integer checkStatus) {
        // TODO Auto-generated method stub
        /**
         * select ua.archives_id,ua.order_num,ua.update_time,ua.check_status,u.
         * user_name from uca_archives as ua inner join um_user as u on
         * ua.upload_user_id = u.user_id where ua.handing_person_id=1 and
         * ua.type=1 and ua.check_status=1 order by check_time desc;
         */
        StringBuilder sql = new StringBuilder(
                "select ua.archives_id,ua.order_num,ua.update_time,"
                        + "ua.check_status,ua.file_url,ua.source_file_name,u.user_name,ua.project_name "
                        + "from uca_archives as ua inner join um_user as u on ua.upload_user_id = u.user_id "
                        + "where ua.handing_person_id=:handingPersonId and ua.type=:type and ua.check_status=:checkStatus order by update_time desc");
        String pagingSql = " limit " + page.getPageNo() + "," + page.getPageSize();
        SQLQuery query = archivesDao.getSession().createSQLQuery(sql.append(pagingSql).toString());
        query.setParameter("handingPersonId", secUtils.getUser().getUserId());
        query.setParameter("type", type);
       query.setParameter("checkStatus", checkStatus);

        List<Object[]> list = query.list();
        List<UcaArchivesVo> listResult = new ArrayList<UcaArchivesVo>(list.size());
        UcaArchivesVo vo = null;
        if (list != null && list.size() > 0) {
            for (Object[] obj : list) {
                vo = new UcaArchivesVo();
                vo.setArchivesId(Integer.valueOf(obj[0].toString()));
                vo.setOrderNum(obj[1] != null ? obj[1].toString() : "");
                vo.setUpdateTime(obj[2] != null ? DateUtil.DateToStr((Date) obj[2]) : "");
                vo.setCheckStatus(obj[3] != null ? Integer.valueOf(obj[3].toString()) : -1);
                vo.setFileUrl(obj[4] != null ? obj[4].toString() : "");
                vo.setSourceFileName(obj[5] != null ? obj[5].toString() : "");
                vo.setUserName(obj[6] != null ? obj[6].toString() : "");
                vo.setProjectName(obj[7] != null ? obj[7].toString() : "");
                listResult.add(vo);
            }
        }

        page.setListResult(listResult);

        /**
         * select count(1) from uca_archives as ua inner join um_user as u on
         * ua.upload_user_id = u.user_id where ua.handing_person_id=1 and
         * ua.type=1 and ua.check_status=1
         */
        pagingSql = "select count(1) from uca_archives as ua inner join um_user as u on ua.upload_user_id = u.user_id "
                + "where ua.handing_person_id=:handingPersonId and ua.type=:type and ua.check_status=:checkStatus";
        query = archivesDao.getSession().createSQLQuery(pagingSql);
        query.setParameter("handingPersonId", secUtils.getUser().getUserId());
        query.setParameter("type", type);
        query.setParameter("checkStatus", checkStatus);

        List countList = query.list();
        object.put("rows", page.getListResult());
        object.put("total", (BigInteger) countList.get(0));
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public void pass(JSONObject object, int archivesId) {
        // TODO Auto-generated method stub
        UcaArchives ua = getArchivesById(archivesId);
        if (null == ua) {
            object.put("success", false);
            object.put("msg", "选择的数据记录不存在！");
            return;
        }
        if (null == ua.getHandingPersonId() || ua.getHandingPersonId().intValue() != secUtils.getUser().getUserId()) {
            object.put("success", false);
            object.put("msg", "当前用户与选择的数据记录经办人不匹配！");
            return;
        }

        Calendar calendar = Calendar.getInstance();
        int idYear = calendar.get(Calendar.YEAR);
        UcaOrderNum ucaOrderNum = orderNumDao.queryById(idYear);

        int current = ucaOrderNum.getCurrent();
        if (current > ucaOrderNum.getEnd()) {
            object.put("success", false);
            object.put("msg", "当年序号范围：0001-9999，已经超出序号范围，请联系管理员！");
            return;
        }

        String currentTxt = String.valueOf(current);
        if (currentTxt.length() < 4) {
            if (currentTxt.length() == 1) {
                currentTxt = "000" + currentTxt;
            }
            if (currentTxt.length() == 2) {
                currentTxt = "00" + currentTxt;
            }
            if (currentTxt.length() == 3) {
                currentTxt = "0" + currentTxt;
            }
        }

        ua.setOrderNum("[" + idYear + "]" + currentTxt);
        ua.setCheckStatus(UcaConstants.ARCHIVES_CHECK_STATUS_PASS);
        Date time = new Date();
        ua.setCheckTime(time);
        ua.setUpdateTime(time);
        archivesDao.saveOrUpdate(ua);

        ucaOrderNum.setCurrent(current + 1);
        orderNumDao.saveOrUpdate(ucaOrderNum);

        object.put("success", true);
        object.put("msg", "审核通过！");
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public void unpass(JSONObject object, int archivesId, String unPassTxt, String fileUrl, String sourceFileName) {
        // TODO Auto-generated method stub
        UcaArchives ua = getArchivesById(archivesId);
        if (null == ua) {
            object.put("success", false);
            object.put("msg", "选择的数据记录不存在！");
            return;
        }
        if (null == ua.getHandingPersonId() || ua.getHandingPersonId().intValue() != secUtils.getUser().getUserId()) {
            object.put("success", false);
            object.put("msg", "当前用户与选择的数据记录经办人不匹配！");
            return;
        }

        ua.setCheckStatus(UcaConstants.ARCHIVES_CHECK_STATUS_FAILED);
        ua.setInstruction(unPassTxt);
        Date time = new Date();
        ua.setCheckTime(time);
        ua.setUpdateTime(time);

        ua.setFileUrlOther(fileUrl);
        ua.setSourceFileNameOther(sourceFileName);

        archivesDao.saveOrUpdate(ua);

        object.put("success", true);
        object.put("msg", "审核不通过！");
    }

    @Override
    public void findArchivesSchedule(JSONObject object, Page<UcaArchivesVo> page, Integer type, Integer checkStatus) {
        // TODO Auto-generated method stub
        StringBuilder sql = new StringBuilder("select ua.archives_id,ua.order_num,ua.check_time,ua.check_status,"
                + "ua.instruction,ua.copy_status,ua.query_status,u.user_name,ua.project_name "
                + "from uca_archives as ua inner join um_user as u on ua.upload_user_id = u.user_id "
                + "where ua.handing_person_id=:handingPersonId and ua.type=:type and ua.check_status=:checkStatus "
                + "order by update_time desc");
        String pagingSql = " limit " + page.getPageNo() + "," + page.getPageSize();
        SQLQuery query = archivesDao.getSession().createSQLQuery(sql.append(pagingSql).toString());
        query.setParameter("handingPersonId", secUtils.getUser().getUserId());
        query.setParameter("type", type);
        query.setParameter("checkStatus", checkStatus);

        List<Object[]> list = query.list();
        List<UcaArchivesVo> listResult = new ArrayList<UcaArchivesVo>(list.size());
        UcaArchivesVo vo = null;
        if (list != null && list.size() > 0) {
            for (Object[] obj : list) {
                vo = new UcaArchivesVo();
                vo.setArchivesId(Integer.valueOf(obj[0].toString()));
                vo.setOrderNum(obj[1] != null ? obj[1].toString() : "");
                vo.setCheckTime(obj[2] != null ? obj[2].toString() : "");
                vo.setCheckStatus(obj[3] != null ? Integer.valueOf(obj[3].toString()) : -1);
                vo.setInstruction(obj[4] != null ? obj[4].toString() : "");
                vo.setCopyStatus(obj[5] != null ? Integer.valueOf(obj[5].toString()) : -1);
                vo.setQueryStatus(obj[6] != null ? Integer.valueOf(obj[6].toString()) : -1);
                vo.setUserName(obj[7] != null ? obj[7].toString() : "");
                vo.setProjectName(obj[8] != null ? obj[8].toString() : "");
                listResult.add(vo);
            }
        }

        page.setListResult(listResult);

        pagingSql = "select count(1) from uca_archives as ua inner join um_user as u on ua.upload_user_id = u.user_id "
                + "where ua.handing_person_id=:handingPersonId and ua.type=:type and ua.check_status=:checkStatus";
        query = archivesDao.getSession().createSQLQuery(pagingSql);
        query.setParameter("handingPersonId", secUtils.getUser().getUserId());
        query.setParameter("type", type);
        query.setParameter("checkStatus", checkStatus);

        List countList = query.list();
        object.put("rows", page.getListResult());
        object.put("total", (BigInteger) countList.get(0));
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public void copyFinish(JSONObject object, int archivesId) {
        // TODO Auto-generated method stub
        UcaArchives ua = getArchivesById(archivesId);
        if (null == ua) {
            object.put("success", false);
            object.put("msg", "选择的数据记录不存在！");
            return;
        }
        if (null == ua.getHandingPersonId() || ua.getHandingPersonId().intValue() != secUtils.getUser().getUserId()) {
            object.put("success", false);
            object.put("msg", "当前用户与选择的数据记录经办人不匹配！");
            return;
        }

        ua.setCopyStatus(UcaConstants.ARCHIVES_COPY_STATUS_ONE);
        ua.setUpdateTime(new Date());
        archivesDao.saveOrUpdate(ua);

        object.put("success", true);
        object.put("msg", "档案复制办理完成！");
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public void editOrderNum(JSONObject object, int archivesId, String orderNum) {
        // TODO Auto-generated method stub
        UcaArchives ua = getArchivesById(archivesId);
        if (null == ua) {
            object.put("success", false);
            object.put("msg", "选择的数据记录不存在！");
            return;
        }
        if (this.getOrderNumCount(orderNum) > 0) {
            object.put("success", false);
            object.put("msg", "调卷单编号已经存在，不允许重复！");
            return;
        }
        if (null == ua.getHandingPersonId() || ua.getHandingPersonId().intValue() != secUtils.getUser().getUserId()) {
            object.put("success", false);
            object.put("msg", "当前用户与选择的数据记录经办人不匹配！");
            return;
        }

        ua.setOrderNum(orderNum);
        ua.setUpdateTime(new Date());
        archivesDao.saveOrUpdate(ua);

        object.put("success", true);
        object.put("msg", "修改调卷单编号成功！");
    }

    @Override
    public int getOrderNumCount(String orderNum) {
        String sql = "SELECT count(*) FROM uca_archives u where u.order_num=:orderNum";
        SQLQuery query = archivesDao.getSession().createSQLQuery(sql);
        query.setParameter("orderNum", orderNum);
        Object count = query.uniqueResult();
        if (ParameterUtil.isNumber(String.valueOf(count))) {
            if (Integer.parseInt(String.valueOf(count)) > 0) {
                return Integer.parseInt(String.valueOf(count));
            }
        }
        return 0;
    }

    @Override
    public UcaArchives getArchivesByArchivesNum(String archivesNum) {
        // TODO Auto-generated method stub
        if (StringUtils.isNotEmpty(archivesNum)) {
            Criteria crit = archivesDao.getSession().createCriteria(UcaArchives.class);
            crit.add(Restrictions.eq("archivesNum", archivesNum));
            List<UcaArchives> lists = crit.list();
            if (lists != null && lists.size() > 0) {
                return lists.get(0);
            }
        }
        return null;
    }

    @Override
    public UcaArchives getArchivesByArchivesId(String id) {
        // TODO Auto-generated method stub
        if (StringUtils.isNotEmpty(id) && NumberUtils.isNumber(id)) {
            Criteria crit = archivesDao.getSession().createCriteria(UcaArchives.class);
            crit.add(Restrictions.eq("archivesId", Integer.parseInt(id)));
            List<UcaArchives> lists = crit.list();
            if (lists != null && lists.size() > 0) {
                return lists.get(0);
            }
        }
        return null;
    }

    @Override
    public void auditing(JSONObject object, int archivesId, String result) {
        // TODO Auto-generated method stub
        UcaArchives ua = getArchivesById(archivesId);
        if (null == ua) {
            object.put("success", false);
            object.put("msg", "选择的数据记录不存在！");
            return;
        }

        if ("true".equals(result)) {
            Calendar calendar = Calendar.getInstance();
            int idYear = calendar.get(Calendar.YEAR);
            UcaOrderNum ucaOrderNum = orderNumDao.queryById(idYear);

            int current = ucaOrderNum.getCurrent();
            if (current > ucaOrderNum.getEnd()) {
                object.put("success", false);
                object.put("msg", "当年序号范围：0001-9999，已经超出序号范围，请联系管理员！");
                return;
            }

            String currentTxt = String.valueOf(current);
            if (currentTxt.length() < 4) {
                if (currentTxt.length() == 1) {
                    currentTxt = "000" + currentTxt;
                }
                if (currentTxt.length() == 2) {
                    currentTxt = "00" + currentTxt;
                }
                if (currentTxt.length() == 3) {
                    currentTxt = "0" + currentTxt;
                }
            }

            ua.setOrderNum("[" + idYear + "]" + currentTxt);
            ua.setCheckStatus(UcaConstants.ARCHIVES_CHECK_STATUS_PASS);
            Date time = new Date();
            ua.setCheckTime(time);
            ua.setUpdateTime(time);
            archivesDao.saveOrUpdate(ua);

            ucaOrderNum.setCurrent(current + 1);
            orderNumDao.saveOrUpdate(ucaOrderNum);

            object.put("success", true);
            object.put("msg", "审核通过！");
        } else if ("false".equals(result)) {
            ua.setCheckStatus(UcaConstants.ARCHIVES_CHECK_STATUS_FAILED);
            Date time = new Date();
            ua.setCheckTime(time);
            ua.setUpdateTime(time);
            archivesDao.saveOrUpdate(ua);

            object.put("success", true);
            object.put("msg", "审核不通过！");
        }
    }

    @Override
    public void findArchivesPass(JSONObject object, Page<UcaArchivesVo> page, Integer type, Integer checkStatus) {
        // TODO Auto-generated method stub
        StringBuilder sql = new StringBuilder(
                "select ua.archives_id,ua.order_num,ua.check_time,ua.check_status,ua.copy_status,ua.query_status,"
                        + "ua.project_name,u.user_name,ua.handing_person_id,ua.archives_num,ua.rename_status "
                        + "from uca_archives as ua inner join um_user as u on ua.upload_user_id = u.user_id "
        				+ "where ua.type=:type and ua.check_status in ('1','3') order by check_status asc, rename_status asc,check_time desc");
        //+ "where ua.type=:type and ua.check_status =:checkStatus order by rename_status asc,check_time desc");
        String pagingSql = " limit " + page.getPageNo() + "," + page.getPageSize();
        SQLQuery query = archivesDao.getSession().createSQLQuery(sql.append(pagingSql).toString());
        query.setParameter("type", type);
       // query.setParameter("checkStatus", checkStatus);

        List<Object[]> list = query.list();
        List<UcaArchivesVo> listResult = new ArrayList<UcaArchivesVo>(list.size());
        UcaArchivesVo vo = null;
        if (list != null && list.size() > 0) {
            for (Object[] obj : list) {
                vo = new UcaArchivesVo();
                vo.setArchivesId(Integer.valueOf(obj[0].toString()));
                vo.setOrderNum(obj[1] != null ? obj[1].toString() : "");
                vo.setCheckTime(obj[2] != null ? obj[2].toString() : "");
                vo.setCheckStatus(obj[3] != null ? Integer.valueOf(obj[3].toString()) : -1);
                vo.setCopyStatus(obj[4] != null ? Integer.valueOf(obj[4].toString()) : -1);
                vo.setQueryStatus(obj[5] != null ? Integer.valueOf(obj[5].toString()) : -1);
                vo.setProjectName(obj[6] != null ? obj[6].toString() : "");
                vo.setUserName(obj[7] != null ? obj[7].toString() : "");
                vo.setHandingPersonId(obj[8] != null ? Integer.valueOf(obj[8].toString()) : -1);
                vo.setArchivesNum(obj[9] != null ? obj[9].toString() : "");
                vo.setRenameStatus(obj[10] != null ? Integer.valueOf(obj[10].toString()) : 1);
                if (vo.getHandingPersonId() != -1) {
                    UmUser handingPerson = umUserDao.queryById(vo.getHandingPersonId());
                    if (null != handingPerson) {
                        vo.setHandingPersonName(handingPerson.getUserName());
                    }
                }
                listResult.add(vo);
            }
        }

        page.setListResult(listResult);

        pagingSql = "select count(1) from uca_archives as ua inner join um_user as u on ua.upload_user_id = u.user_id "
                + "where ua.type=:type and ua.check_status in (1,3)";
        query = archivesDao.getSession().createSQLQuery(pagingSql);
        query.setParameter("type", type);
        //query.setParameter("checkStatus", checkStatus);

        List countList = query.list();
        object.put("rows", page.getListResult());
        object.put("total", (BigInteger) countList.get(0));
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public void renameArchives(JSONObject object, int archivesId, String archivesNum) throws Exception {
        // TODO Auto-generated method stub
        String UPLOAD_HOME = SystemProperties.getProperty("upload.home");
        String UPLOAD_URL = SystemProperties.getProperty("upload.url");
        String receiveEmail = SystemProperties.getProperty("receive.email");
        String subjectEmail = SystemProperties.getProperty("subject.email");
        String messageEmail = SystemProperties.getProperty("message.email");

        UcaArchives ua = getArchivesById(archivesId);
        if (null == ua) {
            object.put("success", false);
            object.put("msg", "选择的数据记录不存在！");
            return;
        }

        String context_real_root_folder_path_old = UPLOAD_HOME + UPLOAD_URL + File.separator + ua.getFileUrl();
        File oldFile = new File(context_real_root_folder_path_old);
        if (!oldFile.exists()) {
            object.put("success", false);
            object.put("msg", "工程档案XML文件不存在！");
            return;
        }

        String fileName = oldFile.getName();
        String context_real_root_folder_path_new = context_real_root_folder_path_old.substring(0,
                context_real_root_folder_path_old.indexOf(fileName))
                + archivesNum
                + fileName.substring(fileName.lastIndexOf("."));

        FileCopyUtils.copy(oldFile, new File(context_real_root_folder_path_new));

        Map attachMap = new HashMap();
        attachMap.put(archivesNum + fileName.substring(fileName.lastIndexOf(".")), context_real_root_folder_path_new);

        String fileUrl = context_real_root_folder_path_new.replace(UPLOAD_HOME + UPLOAD_URL, "");
        if (ParameterUtil.isNotNull(fileUrl)) {
            ua.setFileUrl(fileUrl);
        }

        String sourceFileName = ua.getSourceFileName();
        if (ParameterUtil.isNotNull(sourceFileName)) {
            ua.setSourceFileName(archivesNum + sourceFileName.substring(sourceFileName.lastIndexOf(".")));
        }

        ua.setArchivesNum(archivesNum);

        ua.setRenameStatus(UcaConstants.RENAME_STATUS_Y);
        ua.setUpdateUserId(secUtils.getUser().getUserId());
        ua.setUpdateTime(new Date());
        archivesDao.saveOrUpdate(ua);

        // 发送邮件
        SendEmail.sendSSLMessage(receiveEmail, subjectEmail, messageEmail, attachMap);

        object.put("success", true);
        object.put("msg", "调整工程档案档号成功，工程档案XML文件名称按照“档号”重命名成功，并发送邮件到指定邮箱！");
    }

    @Override
    public List<UcaArchives> findArchivesSoundBookingWorkload(String status, String sd, String ed, int archivesType) {
        Date start = null;
        Date end = null;
        if (ParameterUtil.isNotBlank(sd) && ParameterUtil.isDate(sd)) {
            start = DateUtil.parseTime(sd + " 00:00:00");
        }
        if (ParameterUtil.isNotBlank(ed) && ParameterUtil.isDate(ed)) {
            end = DateUtil.parseTime(ed + " 23:59:59");
        }

        Criteria crit = archivesDao.getSession().createCriteria(UcaArchives.class);
        crit.add(Restrictions.eq("type", archivesType));
        if (ParameterUtil.isNotNull(status) && ParameterUtil.isNumber(status)) {
            crit.add(Restrictions.eq("queryStatus", Integer.parseInt(status)));
        }
        if (null != start && null != end) {
            crit.add(Restrictions.between("updateTime", start, end));
        } else {
            if (null != start) {
                crit.add(Restrictions.ge("updateTime", start));
            } else if (null != end) {
                crit.add(Restrictions.le("updateTime", end));
            }
        }
        List<UcaArchives> lists = crit.list();
        if (lists != null && lists.size() > 0) {
            return lists;
        }
        return null;
    }

    @Override
    public List<UcaArchives> findArchivesSoundAuditingWorkload(String status, String sd, String ed, int archivesType) {
        Date start = null;
        Date end = null;
        if (ParameterUtil.isNotBlank(sd) && ParameterUtil.isDate(sd)) {
            start = DateUtil.parseTime(sd + " 00:00:00");
        }
        if (ParameterUtil.isNotBlank(ed) && ParameterUtil.isDate(ed)) {
            end = DateUtil.parseTime(ed + " 23:59:59");
        }

        Criteria crit = archivesDao.getSession().createCriteria(UcaArchives.class);
        crit.add(Restrictions.eq("type", archivesType));
        if (ParameterUtil.isNotNull(status) && ParameterUtil.isNumber(status)) {
            crit.add(Restrictions.eq("checkStatus", Integer.parseInt(status)));
        }
        if (null != start && null != end) {
            crit.add(Restrictions.between("checkTime", start, end));
        } else {
            if (null != start) {
                crit.add(Restrictions.ge("checkTime", start));
            } else if (null != end) {
                crit.add(Restrictions.le("checkTime", end));
            }
        }
        List<UcaArchives> lists = crit.list();
        if (lists != null && lists.size() > 0) {
            return lists;
        }
        return null;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public void checkresultAuditing(String id, String pass, String desc, JSONObject object) {
        UcaArchives ucaArchives = this.getArchivesByArchivesId(id);
        if (null == ucaArchives) {
            object.put("code", "0");
            object.put("msg", "档案不存在，审核失败！");
            return;
        }

        if ("0".equals(pass)) {
            ucaArchives.setCheckStatus(UcaConstants.ARCHIVES_CHECK_STATUS_FAILED);
            ucaArchives.setInstruction(desc);
            Date time = new Date();
            ucaArchives.setCheckTime(time);
            ucaArchives.setUpdateTime(time);
            archivesDao.saveOrUpdate(ucaArchives);

            object.put("code", "1");
            object.put("msg", "审核不通过！");
        } else if ("1".equals(pass)) {
            Calendar calendar = Calendar.getInstance();
            int idYear = calendar.get(Calendar.YEAR);
            UcaOrderNum ucaOrderNum = orderNumDao.queryById(idYear);

            int current = ucaOrderNum.getCurrent();
            if (current > ucaOrderNum.getEnd()) {
                return;
            }

            String currentTxt = String.valueOf(current);
            if (currentTxt.length() < 4) {
                if (currentTxt.length() == 1) {
                    currentTxt = "000" + currentTxt;
                }
                if (currentTxt.length() == 2) {
                    currentTxt = "00" + currentTxt;
                }
                if (currentTxt.length() == 3) {
                    currentTxt = "0" + currentTxt;
                }
            }

            ucaArchives.setOrderNum("[" + idYear + "]" + currentTxt);
            ucaArchives.setCheckStatus(UcaConstants.ARCHIVES_CHECK_STATUS_PASS);
            Date time = new Date();
            ucaArchives.setCheckTime(time);
            ucaArchives.setUpdateTime(time);
            ucaArchives.setInstruction(desc);
            archivesDao.saveOrUpdate(ucaArchives);

            ucaOrderNum.setCurrent(current + 1);
            orderNumDao.saveOrUpdate(ucaOrderNum);

            object.put("code", "1");
            object.put("msg", "审核通过！");
        }
    }

}
