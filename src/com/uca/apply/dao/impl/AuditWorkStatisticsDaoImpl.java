package com.uca.apply.dao.impl;

import org.springframework.stereotype.Repository;

import cn.com.capinfo.core.dao.impl.GenericDaoImpl;

import com.uca.apply.dao.AuditWorkStatisticsDao;
import com.uca.apply.pojos.UcaAuditWorkStatistics;

@Repository
public class AuditWorkStatisticsDaoImpl extends GenericDaoImpl<UcaAuditWorkStatistics> implements AuditWorkStatisticsDao{

}
