package com.uca.exam.dao.impl;

import org.springframework.stereotype.Repository;

import cn.com.capinfo.core.dao.impl.GenericDaoImpl;

import com.uca.exam.dao.UcaQuestionDao;
import com.uca.exam.pojos.UcaQuestion;

@Repository
public class UcaQuestionDaoImpl extends GenericDaoImpl<UcaQuestion> implements UcaQuestionDao{

}
