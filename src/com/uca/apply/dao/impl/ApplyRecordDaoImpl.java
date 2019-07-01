package com.uca.apply.dao.impl;

import org.springframework.stereotype.Repository;

import cn.com.capinfo.core.dao.impl.GenericDaoImpl;

import com.uca.apply.dao.ApplyRecordDao;
import com.uca.apply.pojos.UcaApplyQueryRecord;

@Repository
public class ApplyRecordDaoImpl extends GenericDaoImpl<UcaApplyQueryRecord> implements ApplyRecordDao{

}
