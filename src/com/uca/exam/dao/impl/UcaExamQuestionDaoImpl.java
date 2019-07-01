package com.uca.exam.dao.impl;

import org.springframework.stereotype.Repository;

import cn.com.capinfo.core.dao.impl.GenericDaoImpl;

import com.uca.exam.dao.UcaExamQuestionDao;
import com.uca.exam.pojos.UcaExaminationQuestion;

@Repository
public class UcaExamQuestionDaoImpl extends GenericDaoImpl<UcaExaminationQuestion> implements UcaExamQuestionDao{

}
