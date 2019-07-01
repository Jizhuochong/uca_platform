package com.uca.archives.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.capinfo.core.utils.DateUtil;
import cn.com.capinfo.core.utils.Page;
import cn.com.capinfo.core.utils.ParameterUtil;
import cn.com.capinfo.core.utils.SecurityIdentifyUtils;
import cn.com.capinfo.security.utils.SecurityUtils;

import com.alibaba.fastjson.JSONObject;
import com.uca.archives.dao.ArchivesCompletionDetailedFormDao;
import com.uca.archives.dao.ArchivesCompletionRegisterFormDao;
import com.uca.archives.pojos.ArchivesCompletionDetailedForm;
import com.uca.archives.pojos.ArchivesCompletionRegisterForm;
import com.uca.archives.pojos.DocExportTpl;
import com.uca.archives.service.ArchivesCompletionRegisterFormService;
import com.uca.ucasecurity.dao.UmUserDao;
import com.uca.ucasecurity.pojos.UmUser;
import com.uca.utils.WordOperator;

@Service
public class ArchivesCompletionRegisterFormServiceImpl implements ArchivesCompletionRegisterFormService {
    private SecurityIdentifyUtils secUtils = new SecurityUtils();


    protected static final String TPL_QUERY_SQL = "select t.* from doc_export_tpl t";
	private static final Object TPL_FIELD_NAME = "TPL_FILENAME";
    @Autowired
    private ArchivesCompletionRegisterFormDao archivesCompletionRegisterFormDao;
    @Autowired
    private ArchivesCompletionDetailedFormDao archivesCompletionDetailedFormDao;
    @Autowired
    private UmUserDao umUserDao;

    @Override
    public void find(JSONObject object, Page<ArchivesCompletionRegisterForm> page, String type, String sd, String ed,
            boolean isCurrentUser) {
        Date start = null;
        Date end = null;
        if (ParameterUtil.isNotBlank(sd) && ParameterUtil.isDate(sd)) {
            start = DateUtil.parseTime(sd + " 00:00:00");
        }
        if (ParameterUtil.isNotBlank(ed) && ParameterUtil.isDate(ed)) {
            end = DateUtil.parseTime(ed + " 23:59:59");
        }

        Criteria crit = archivesCompletionRegisterFormDao.getSession().createCriteria(
                ArchivesCompletionRegisterForm.class);
        if (isCurrentUser) {
            crit.add(Restrictions.eq("createUserId", secUtils.getUser().getUserId()));
        }
        if (ParameterUtil.isNotNull(type) && ParameterUtil.isNumber(type)) {
            crit.add(Restrictions.eq("type", Integer.parseInt(type)));
        }
        if (null != start && null != end) {
            crit.add(Restrictions.between("createTime", start, end));
        } else {
            if (null != start) {
                crit.add(Restrictions.ge("createTime", start));
            } else if (null != end) {
                crit.add(Restrictions.le("createTime", end));
            }
        }

        int total = crit.list().size();

        crit.addOrder(Order.desc("createTime"));
        crit.setFirstResult(page.getPageNo());
        crit.setMaxResults(page.getPageSize());
        List<ArchivesCompletionRegisterForm> lists = crit.list();

        if (lists != null && lists.size() > 0) {
            for (ArchivesCompletionRegisterForm obj : lists) {
                UmUser user = umUserDao.queryById(obj.getCreateUserId());
                if (null != user) {
                    obj.setCreateUserName(user.getUserName());
                }
            }
            object.put("total", total);
            object.put("rows", lists);
        } else {
            object.put("total", 0);
            object.put("rows", new ArrayList<ArchivesCompletionRegisterForm>());
        }
    }

    @Override
    public void saveOrUpdate(JSONObject object, ArchivesCompletionRegisterForm po) {
        if (po.getId() == null) {
            this.save(object, po);
        } else {
            this.update(object, po);
        }
    }

    private void save(JSONObject object, ArchivesCompletionRegisterForm po) {
        po.setCreateUserId(secUtils.getUser().getUserId());
        po.setCreateTime(new Date());
        archivesCompletionRegisterFormDao.saveOrUpdate(po);
        if (po.getId() > 0) {
            object.put("success", true);
            object.put("msg", "添加成功！");
        } else {
            object.put("success", false);
            object.put("msg", "添加失败！");
        }
    }

    private void update(JSONObject object, ArchivesCompletionRegisterForm po) {
        ArchivesCompletionRegisterForm uac = archivesCompletionRegisterFormDao.queryById(po.getId());
        if (null == uac) {
            object.put("success", false);
            object.put("msg", "竣工档案登记表不存在！");
            return;
        }

        uac.setType(po.getType());
        uac.setArchiveWord(po.getArchiveWord());
        uac.setBuildWord(po.getBuildWord());
        uac.setBuildUnit(po.getBuildUnit());
        uac.setMailingAddress(po.getMailingAddress());
        uac.setZipCode(po.getZipCode());
        uac.setRegisterPerson(po.getRegisterPerson());
        uac.setRegisterDate(po.getRegisterDate());
        uac.setPhone(po.getPhone());
        uac.setProjectName(po.getProjectName());
        uac.setProjectLocation(po.getProjectLocation());
        uac.setDesignUnit(po.getDesignUnit());
        uac.setConstructionUnit(po.getConstructionUnit());
        uac.setMeasureUnit(po.getMeasureUnit());
        uac.setProjectType(po.getProjectType());
        uac.setStartDate(po.getStartDate());
        uac.setCompletionDate(po.getCompletionDate());
        uac.setTotalInvestment(po.getTotalInvestment());
        uac.setRemark(po.getRemark());
        uac.setArchivesManagers(po.getArchivesManagers());

        archivesCompletionRegisterFormDao.saveOrUpdate(uac);

        object.put("success", true);
        object.put("msg", "修改成功！");
    }

    @Override
    public void getById(JSONObject object, Integer id) {
        ArchivesCompletionRegisterForm po = archivesCompletionRegisterFormDao.queryById(id);
        if (null == po) {
            object.put("success", false);
            object.put("msg", "选择的数据记录不存在！");
            return;
        }
        object.put("success", true);
        object.put("objPo", po);
    }

    @Override
    public void delete(JSONObject object, int id) {
        ArchivesCompletionRegisterForm po = archivesCompletionRegisterFormDao.queryById(id);
        if (null == po) {
            object.put("success", false);
            object.put("msg", "选择的数据记录不存在！");
            return;
        }

        archivesCompletionRegisterFormDao.delete(po);

        object.put("success", true);
        object.put("msg", "删除成功！");
    }

    @Override
    public ArchivesCompletionRegisterForm getById(Integer id) {
        ArchivesCompletionRegisterForm po = archivesCompletionRegisterFormDao.queryById(id);
        if (null != po) {
            return po;
        }
        return null;
    }
    
    private File getTplPathByDocTypeVal(int docTypeVal) {
		File tpl = null;
		String tplPath = Thread.currentThread().getContextClassLoader().getResource("templates").getPath() + getTplFilenameByDocTypeVal(docTypeVal);
		tpl = FileUtils.getFile(tplPath);
		return tpl;
	}
    
	private String getTplFilenameByDocTypeVal(final int docTypeVal) {
//		 where t.doc_type_val=?
        Criteria crit = archivesCompletionRegisterFormDao.getSession().createCriteria(DocExportTpl.class);
        crit.add(Restrictions.eq("doctypeval", docTypeVal));
        DocExportTpl tpl = (DocExportTpl) crit.uniqueResult();
        return tpl.getTplfilename();
	}
    
    public List<ArchivesCompletionDetailedForm> findByRegisterId(int registerId) {
        Criteria crit = archivesCompletionDetailedFormDao.getSession().createCriteria(
                ArchivesCompletionDetailedForm.class);
        crit.add(Restrictions.eq("registerId", registerId));
        crit.addOrder(Order.asc("createTime"));
        List<ArchivesCompletionDetailedForm> lists = crit.list();

        if (lists != null && lists.size() > 0) {
            return lists;
        }
        return null;
    }
	
    public File getGeneratePhoneRecordExportFile(int docTypeVal, Integer id) {
    	ArchivesCompletionRegisterForm fr = getById(id);
		File tpl = null, gen = null;
		
		// 获取模板文件
		tpl = getTplPathByDocTypeVal(docTypeVal);
		gen = FileUtils.getFile(FileUtils.getTempDirectory() + "/" + UUID.randomUUID().toString() + ".docx");
		
		// 获取导出内容数据
		List<ArchivesCompletionDetailedForm> findByRegisterId = findByRegisterId(id);
		Map<String, String> map = new HashMap<String, String>();
    	Map<String, String> map2 = new HashMap<String, String>();
		try {
			/*archiveWord
管径（断面）	长度（米）
diameterSection	diameterLength
总投资（元）	totalInvestment
*/			
			if(fr != null){
				map.put("TYPE", fr.getType()==3?"市政工程":"");
				map.put("ARCHIVEWORD", fr.getArchiveWord()==null?"":fr.getArchiveWord());
				map.put("BUILDWORD", fr.getBuildWord()==null?"":fr.getBuildWord());
				map.put("BUILDUNIT", fr.getBuildUnit()==null?"":fr.getBuildUnit());
				map.put("ZIPCODE", fr.getZipCode()==null?"":fr.getZipCode());
				map.put("REGISTERPERSON", fr.getRegisterPerson()==null?"":fr.getRegisterPerson());
				map.put("MAILINGADDRESS", fr.getMailingAddress()==null?"":fr.getMailingAddress());
				map.put("REGISTERDATE", fr.getRegisterDate()==null?"":fr.getRegisterDate());
				map.put("PHONE", fr.getPhone()==null?"":fr.getPhone());
				map.put("PROJECTNAME", fr.getProjectName()==null?"":fr.getProjectName());
				map.put("PROJECTLOCATION", fr.getProjectLocation()==null?"":fr.getProjectLocation());
				map.put("DESIGNUNIT", fr.getDesignUnit()==null?"":fr.getDesignUnit());
				map.put("CONSTRUCTIONUNIT", fr.getConstructionUnit()==null?"":fr.getConstructionUnit());
				map.put("PROJECTTYPE", fr.getProjectType()==null?"":fr.getProjectType());
				map.put("MEASUREUNIT", fr.getMeasureUnit()==null?"":fr.getMeasureUnit());
				map.put("TOTALINVESTMENT", fr.getTotalInvestment()<=0.00?0.00+"":fr.getTotalInvestment()+"");
				map.put("STARTDATE", fr.getStartDate()==null?"":fr.getStartDate());
				map.put("COMPLETIONDATE", fr.getCompletionDate()==null?"":fr.getCompletionDate());
				map.put("REMARK", fr.getRemark()==null?"":fr.getRemark());
				map.put("ARCHIVESMANAGERS", fr.getArchivesManagers()==null?"":fr.getArchivesManagers());
				if(findByRegisterId!=null && findByRegisterId.size()>0){
					int i=0;
					for (ArchivesCompletionDetailedForm acdf : findByRegisterId) {
						if(findByRegisterId.size()>1){
							map2.put("DIAMETERSELECT"+i,acdf.getDiameterSection()==null?"":acdf.getDiameterSection());
							map2.put("DIAMETERLENGTH"+i, acdf.getDiameterLength()<=0.00?0.00+"":acdf.getDiameterLength()+"");
						}else{
							map.put("DIAMETERSELECT",acdf.getDiameterSection()==null?"":acdf.getDiameterSection());
							map.put("DIAMETERLENGTH", acdf.getDiameterLength()<=0.00?0.00+"":acdf.getDiameterLength()+"");
							
						}
					}
    				map.put("sz", i+"");
				}else{
					map.put("DIAMETERSELECT","");
					map.put("DIAMETERLENGTH","");
				}
			}
			WordOperator oper = new WordOperator(tpl.getAbsolutePath(),gen.getAbsolutePath(),map,map2);
			
			// 替换模板中的标记内容
			oper.processText();
			
			// 模板标记位置插入图片
//			oper.processPhonePicture("barCode", BarCodeUtil.createBarCode(fr), Document.PICTURE_TYPE_BMP, 0d, 0d);
			oper.outFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gen;
	}
   
	@Override
	public File getGenerategyRecordExportFile(int type, int id) {
		ArchivesCompletionRegisterForm fr = getById(id);
    	File tpl = null, gen = null;
    	
    	// 获取模板文件
    	tpl = getTplPathByDocTypeVal(type);
    	gen = FileUtils.getFile(FileUtils.getTempDirectory() + "/" + UUID.randomUUID().toString() + ".docx");
    	
    	// 获取导出内容数据
    	List<ArchivesCompletionDetailedForm> findByRegisterId = findByRegisterId(id);
    	Map<String, String> map = new HashMap<String, String>();
    	Map<String, String> map2 = new HashMap<String, String>();
    	try {
    		/*archiveWord
管径（断面）	长度（米）
diameterSection	diameterLength
总投资（元）	totalInvestment
    		 */			
    		if(fr != null){
    			map.put("TYPE", fr.getType()==2?"市政工程":"");
    			map.put("ARCHIVEWORD", fr.getArchiveWord()==null?"":fr.getArchiveWord());
    			map.put("BUILDWORD", fr.getBuildWord()==null?"":fr.getBuildWord());
    			map.put("BUILDUNIT", fr.getBuildUnit()==null?"":fr.getBuildUnit());
    			map.put("ZIPCODE", fr.getZipCode()==null?"":fr.getZipCode());
    			map.put("REGISTERPERSON", fr.getRegisterPerson()==null?"":fr.getRegisterPerson());
    			map.put("MAILINGADDRESS", fr.getMailingAddress()==null?"":fr.getMailingAddress());
    			map.put("REGISTERDATE", fr.getRegisterDate()==null?"":fr.getRegisterDate());
    			map.put("PHONE", fr.getPhone()==null?"":fr.getPhone());
    			map.put("PROJECTNAME", fr.getProjectName()==null?"":fr.getProjectName());
    			map.put("PROJECTLOCATION", fr.getProjectLocation()==null?"":fr.getProjectLocation());
    			map.put("DESIGNUNIT", fr.getDesignUnit()==null?"":fr.getDesignUnit());
    			map.put("CONSTRUCTIONUNIT", fr.getConstructionUnit()==null?"":fr.getConstructionUnit());
    			map.put("PROJECTTYPE", fr.getProjectType()==null?"":fr.getProjectType());
    			map.put("MEASUREUNIT", fr.getMeasureUnit()==null?"":fr.getMeasureUnit());
    			map.put("STARTDATE", fr.getStartDate()==null?"":fr.getStartDate());
    			map.put("COMPLETIONDATE", fr.getCompletionDate()==null?"":fr.getCompletionDate());
    			map.put("REMARK", fr.getRemark()==null?"":fr.getRemark());
    			map.put("ARCHIVESMANAGERS", fr.getArchivesManagers()==null?"":fr.getArchivesManagers());
    			if(findByRegisterId!=null && findByRegisterId.size()>0){
    				int i=0;
    				for (ArchivesCompletionDetailedForm acdf : findByRegisterId) {
    					if(findByRegisterId.size()>1){
    						map2.put("TOTALINVESTMENT"+i, acdf.getTotalInvestment()<=0.00?0.00+"":acdf.getTotalInvestment()+"");
    						map2.put("PROJECTCONTENT"+i,acdf.getProjectContent()==null?"":acdf.getProjectContent());
    						map2.put("PROJECTQUANTITY"+i, acdf.getProjectQuantity()<=0.00?0.00+"":acdf.getProjectQuantity()+"");
//    						map.put("TOTALINVESTMENT", acdf.getTotalInvestment()<=0.00?0.00+"":acdf.getTotalInvestment()+"");
//    						map.put("PROJECTCONTENT",acdf.getProjectContent()==null?"":acdf.getProjectContent());
//    						map.put("PROJECTQUANTITY", acdf.getProjectQuantity()<=0.00?0.00+"":acdf.getProjectQuantity()+"");
    					}else{
    						map.put("TOTALINVESTMENT", acdf.getTotalInvestment()<=0.00?0.00+"":acdf.getTotalInvestment()+"");
    						map.put("PROJECTCONTENT",acdf.getProjectContent()==null?"":acdf.getProjectContent());
    						map.put("PROJECTQUANTITY", acdf.getProjectQuantity()<=0.00?0.00+"":acdf.getProjectQuantity()+"");
    					}
    					i++;
    				}
    				map.put("sz", i+"");
    			}else{
    				map.put("TOTALINVESTMENT", "");
					map.put("PROJECTCONTENT","");
					map.put("PROJECTQUANTITY","");
    			}
    		}
    		WordOperator oper = new WordOperator(tpl.getAbsolutePath(),gen.getAbsolutePath(),map,map2);
    		
    		// 替换模板中的标记内容
    		oper.processText();
    		oper.outFile();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return gen;
	}

	@Override
	public File getGenerateJZRecordExportFile(int type, int id) {
		ArchivesCompletionRegisterForm fr = getById(id);
    	File tpl = null, gen = null;
    	
    	// 获取模板文件
    	tpl = getTplPathByDocTypeVal(type);
    	gen = FileUtils.getFile(FileUtils.getTempDirectory() + "/" + UUID.randomUUID().toString() + ".docx");
    	
    	// 获取导出内容数据
    	List<ArchivesCompletionDetailedForm> findByRegisterId = findByRegisterId(id);
    	Map<String, String> map = new HashMap<String, String>();
    	Map<String, String> map2 = new HashMap<String, String>();
    	try {
    		/*archiveWord
管径（断面）	长度（米）
diameterSection	diameterLength
总投资（元）	totalInvestment
    		 */			
    		if(fr != null){
    			map.put("TYPE", fr.getType()==1?"建筑工程":"");
    			map.put("ARCHIVEWORD", fr.getArchiveWord()==null?"":fr.getArchiveWord());
    			map.put("BUILDWORD", fr.getBuildWord()==null?"":fr.getBuildWord());
    			map.put("BUILDUNIT", fr.getBuildUnit()==null?"":fr.getBuildUnit());
    			map.put("ZIPCODE", fr.getZipCode()==null?"":fr.getZipCode());
    			map.put("REGISTERPERSON", fr.getRegisterPerson()==null?"":fr.getRegisterPerson());
    			map.put("MAILINGADDRESS", fr.getMailingAddress()==null?"":fr.getMailingAddress());
    			map.put("REGISTERDATE", fr.getRegisterDate()==null?"":fr.getRegisterDate());
    			map.put("PHONE", fr.getPhone()==null?"":fr.getPhone());
    			map.put("PROJECTNAME", fr.getProjectName()==null?"":fr.getProjectName());
    			map.put("PROJECTLOCATION", fr.getProjectLocation()==null?"":fr.getProjectLocation());
    			map.put("DESIGNUNIT", fr.getDesignUnit()==null?"":fr.getDesignUnit());
    			map.put("CONSTRUCTIONUNIT", fr.getConstructionUnit()==null?"":fr.getConstructionUnit());
    			map.put("PROJECTTYPE", fr.getProjectType()==null?"":fr.getProjectType());
    			map.put("MEASUREUNIT", fr.getMeasureUnit()==null?"":fr.getMeasureUnit());
    			map.put("STARTDATE", fr.getStartDate()==null?"":fr.getStartDate());
    			map.put("COMPLETIONDATE", fr.getCompletionDate()==null?"":fr.getCompletionDate());
    			map.put("REMARK", fr.getRemark()==null?"":fr.getRemark());
    			map.put("ARCHIVESMANAGERS", fr.getArchivesManagers()==null?"":fr.getArchivesManagers());
    			if(findByRegisterId!=null && findByRegisterId.size()>0){
    				int i=0;
    				for (ArchivesCompletionDetailedForm acdf : findByRegisterId) {
    					if(findByRegisterId.size()>1){
    						map2.put("TOTALINVESTMENT"+i, acdf.getTotalInvestment()<=0.00?0.00+"":acdf.getTotalInvestment()+"");
    						map2.put("BUILDINGS"+i,acdf.getBuildings()==null?"":acdf.getBuildings());
    						map2.put("PROJECTPURPOSE"+i, acdf.getProjectPurpose()==null?"":acdf.getProjectPurpose());
    						map2.put("CATEGORYSTRUCTURE"+i, acdf.getCategoryStructure()==null?"":acdf.getCategoryStructure());
    						map2.put("FLOORS"+i, acdf.getFloors()==null?"":acdf.getFloors());
    						map2.put("COVERSAREA"+i, acdf.getCoversArea()<=0.00?0.00+"":acdf.getCoversArea()+"");
    						map2.put("CONSTRUCTIONAREA"+i, acdf.getConstructionArea()<=0.00?0.00+"":acdf.getConstructionArea()+"");
    					}else{
    						map.put("TOTALINVESTMENT", acdf.getTotalInvestment()<=0.00?0.00+"":acdf.getTotalInvestment()+"");
    						map.put("BUILDINGS",acdf.getBuildings()==null?"":acdf.getBuildings());
    						map.put("PROJECTPURPOSE", acdf.getProjectPurpose()==null?"":acdf.getProjectPurpose());
    						map.put("CATEGORYSTRUCTURE", acdf.getCategoryStructure()==null?"":acdf.getCategoryStructure());
    						map.put("FLOORS", acdf.getFloors()==null?"":acdf.getFloors());
    						map.put("COVERSAREA", acdf.getCoversArea()<=0.00?0.00+"":acdf.getCoversArea()+"");
    						map.put("CONSTRUCTIONAREA", acdf.getConstructionArea()<=0.00?0.00+"":acdf.getConstructionArea()+"");
    						
    					}
    					i++;
    				}
    				map.put("sz", i+"");
    			}else{
    				map.put("TOTALINVESTMENT","");
					map.put("BUILDINGS","");
					map.put("PROJECTPURPOSE","");
					map.put("CATEGORYSTRUCTURE", "");
					map.put("FLOORS", "");
					map.put("COVERSAREA", "");
					map.put("CONSTRUCTIONAREA", "");
					
    			}
    		}
    		WordOperator oper = new WordOperator(tpl.getAbsolutePath(),gen.getAbsolutePath(),map,map2);
    		
    		// 替换模板中的标记内容
    		oper.processText();
    		oper.outFile();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return gen;
	}


}
