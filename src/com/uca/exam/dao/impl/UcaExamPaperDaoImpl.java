package com.uca.exam.dao.impl;

import org.springframework.stereotype.Repository;

import cn.com.capinfo.core.dao.impl.GenericDaoImpl;

import com.uca.exam.dao.UcaExamPaperDao;
import com.uca.exam.pojos.UcaExaminationPaper;

@Repository
public class UcaExamPaperDaoImpl extends GenericDaoImpl<UcaExaminationPaper> implements UcaExamPaperDao{

}
