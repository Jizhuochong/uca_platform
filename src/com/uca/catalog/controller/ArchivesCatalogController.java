package com.uca.catalog.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.com.capinfo.core.controller.BaseController;
import cn.com.capinfo.core.utils.Page;
import cn.com.capinfo.core.utils.SecurityIdentifyUtils;
import cn.com.capinfo.security.utils.SecurityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.uca.archives.pojos.UcaArchives;
import com.uca.archives.service.ArchivesService;
import com.uca.catalog.pojos.UcaArchivesCatalog;
import com.uca.catalog.service.ArchivesCatalogService;
import com.uca.utils.BuildFileNameUtil;
import com.uca.utils.PoiExcelHelper;

@Controller
@RequestMapping("/catalog/*")
public class ArchivesCatalogController extends BaseController<UcaArchivesCatalog> {
    private static SerializerFeature[] serializerFeatureNull = new SerializerFeature[] {
            SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteMapNullValue,
            SerializerFeature.WriteNullNumberAsZero, SerializerFeature.WriteNullStringAsEmpty };

    private SecurityIdentifyUtils secUtils = new SecurityUtils();

    @Autowired
    private ArchivesCatalogService archivesCatalogService;

    @Autowired
    private ArchivesService archivesService;

    @RequestMapping(value = "/toList", method = { RequestMethod.GET, RequestMethod.POST })
    public String toList() throws Exception {
        return "uca/catalog/catalog_list";
    }

    @RequestMapping(value = "/list", method = { RequestMethod.POST })
    @ResponseBody
    public String list(@RequestParam(required = false) String archivesNum,
            @RequestParam(required = false) String projectName, @RequestParam(required = false) String devOrg,
            @RequestParam(required = false) String projectAddress, @RequestParam(required = false) String planPerNum,
            @RequestParam(required = false) String sd, @RequestParam(required = false) String ed) throws Exception {
        Page<UcaArchivesCatalog> page = this.getPageRows();
        JSONObject object = new JSONObject();

        archivesCatalogService.find(object, page, archivesNum, projectName, devOrg, projectAddress, planPerNum, sd, ed);
        return JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss", serializerFeatureNull);
    }

    @RequestMapping(value = "/save", method = { RequestMethod.POST })
    @ResponseBody
    public JSONObject save(@RequestParam(required = true) String archivesNum,
            @RequestParam(required = true) String projectName, @RequestParam(required = true) String devOrg,
            @RequestParam(required = true) String projectAddress, @RequestParam(required = true) String planPerNum,
            @RequestParam(required = false) String designUnit, @RequestParam(required = false) String constructionUnit)
            throws Exception {
        JSONObject object = new JSONObject();
        UcaArchivesCatalog po = new UcaArchivesCatalog();
        po.setArchivesNum(archivesNum);
        po.setProjectName(projectName);
        po.setDevOrg(devOrg);
        po.setProjectAddress(projectAddress);
        po.setPlanPerNum(planPerNum);
        po.setDesignUnit(designUnit);
        po.setConstructionUnit(constructionUnit);
        archivesCatalogService.saveOrUpdate(object, po);
        return object;
    }

    @RequestMapping(value = "/edit", method = { RequestMethod.POST })
    @ResponseBody
    public JSONObject edit(@RequestParam(required = true) String catalogId,
            @RequestParam(required = true) String archivesNum, @RequestParam(required = true) String projectName,
            @RequestParam(required = true) String devOrg, @RequestParam(required = true) String projectAddress,
            @RequestParam(required = true) String planPerNum, @RequestParam(required = false) String designUnit,
            @RequestParam(required = false) String constructionUnit) throws Exception {
        JSONObject object = new JSONObject();
        UcaArchivesCatalog po = new UcaArchivesCatalog();
        po.setCatalogId(Integer.valueOf(catalogId));
        po.setArchivesNum(archivesNum);
        po.setProjectName(projectName);
        po.setDevOrg(devOrg);
        po.setProjectAddress(projectAddress);
        po.setPlanPerNum(planPerNum);
        po.setDesignUnit(designUnit);
        po.setConstructionUnit(constructionUnit);
        archivesCatalogService.saveOrUpdate(object, po);
        return object;
    }

    @RequestMapping(value = "/getObjById", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public JSONObject getObjById(@RequestParam(required = true) int id) throws Exception {
        JSONObject object = new JSONObject();
        archivesCatalogService.getById(object, id);
        return object;
    }

    @RequestMapping(value = "/delete", method = { RequestMethod.POST })
    @ResponseBody
    public JSONObject delete(@RequestParam(required = true) int catalogId) throws Exception {
        JSONObject object = new JSONObject();
        archivesCatalogService.delete(object, catalogId);
        return object;
    }

    /**
     * 批量导入
     * 
     * @param file
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/importExcel", method = { RequestMethod.GET, RequestMethod.POST })
    public void importExcel(HttpServletResponse response,
            @RequestParam(value = "fileItem", required = true) MultipartFile file) throws Exception {
        JSONObject object = new JSONObject();
        File fileImport = null;
        String fileUrl = "";
        String fileName = "";
        if (!file.isEmpty()) {
            fileName = file.getOriginalFilename();// 原始文件名称
            try {
                fileUrl = BuildFileNameUtil.getNewFileUrl(fileName);
                fileImport = new File(fileUrl);
                FileCopyUtils.copy(file.getBytes(), fileImport);
            } catch (IOException e) {
                throw e;
            }
        }

        List<ArrayList<String>> excelData = new ArrayList<ArrayList<String>>();

        if (file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1).equals("xls")
                || file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1).equals("xlsx")) {// 转换csv到excelData对象sh
            PoiExcelHelper peh = new PoiExcelHelper();
            excelData = peh.read(fileImport.getAbsolutePath(), 0);
        } else {
            object.put("success", false);
            object.put("msg", "模板必须为Excel文件");
            response.setContentType("text/plain;charset=utf-8");
            response.getWriter().write(object.toString());
            return;
        }

        ArrayList<String> strsTitle = excelData.get(0);
        if (!"规划许可证号".equals(strsTitle.get(0)) || !"工程名称".equals(strsTitle.get(1))
                || !"建设工程地址".equals(strsTitle.get(2)) || !"建设单位".equals(strsTitle.get(3))
                || !"设计单位".equals(strsTitle.get(4)) || !"施工单位".equals(strsTitle.get(5))) {
            object.put("success", false);
            object.put("msg", "模板格式错误！");
            response.setContentType("text/plain;charset=utf-8");
            response.getWriter().write(object.toString());
            return;
        }

        excelData.remove(0);// 去掉标题

        try {
            List<UcaArchivesCatalog> list = new ArrayList<UcaArchivesCatalog>();
            for (ArrayList<String> strs : excelData) {
                String archivesNum = strs.get(0);
                String projectName = strs.get(1);
                String devOrg = strs.get(2);
                String projectAddress = strs.get(3);
                String planPerNum = strs.get(4);
                String constructionUnit = strs.get(5);

               /* UcaArchives ucaArchives = archivesService.getArchivesByArchivesNum(archivesNum);
                if (null == ucaArchives) {
                    object.put("success", false);
                    object.put("msg", "档案目录所对应的档案记录不存在(通过工程档案电子编号(档号)参数查询)！");
                    response.setContentType("text/plain;charset=utf-8");
                    response.getWriter().write(object.toString());
                    return;
                }*/

                UcaArchivesCatalog catalog = new UcaArchivesCatalog();
                catalog.setProjectName(projectName);
                catalog.setDevOrg(devOrg);
                catalog.setProjectAddress(projectAddress);
                catalog.setPlanPerNum(planPerNum);
                catalog.setArchivesNum(archivesNum);
                catalog.setConstructionUnit(constructionUnit);
                catalog.setCreateUserId(secUtils.getUser().getUserId());
                catalog.setUpdateUserId(secUtils.getUser().getUserId());
                catalog.setCreateTime(new Date());
                catalog.setUpdateTime(new Date());

                list.add(catalog);
            }
            archivesCatalogService.saveCatalogs(list);
            object.put("success", true);
            object.put("msg", "批量导入数据成功！");
        } catch (Exception e) {
            throw e;
        }
        response.setContentType("text/plain;charset=utf-8");
        response.getWriter().write(object.toString());
    }

}
