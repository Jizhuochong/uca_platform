package com.uca.archives.dao.impl;

import org.springframework.stereotype.Repository;

import cn.com.capinfo.core.dao.impl.GenericDaoImpl;

import com.uca.archives.dao.OrderNumDao;
import com.uca.archives.pojos.UcaOrderNum;

@Repository
public class OrderNumDaoImpl extends GenericDaoImpl<UcaOrderNum> implements OrderNumDao{

}
