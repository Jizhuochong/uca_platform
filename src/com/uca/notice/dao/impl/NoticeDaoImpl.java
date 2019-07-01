package com.uca.notice.dao.impl;

import org.springframework.stereotype.Repository;

import cn.com.capinfo.core.dao.impl.GenericDaoImpl;

import com.uca.notice.dao.NoticeDao;
import com.uca.notice.pojos.UcaNotice;

@Repository
public class NoticeDaoImpl extends GenericDaoImpl<UcaNotice> implements NoticeDao{

}
