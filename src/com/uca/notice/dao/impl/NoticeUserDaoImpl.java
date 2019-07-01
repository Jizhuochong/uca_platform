package com.uca.notice.dao.impl;

import org.springframework.stereotype.Repository;

import cn.com.capinfo.core.dao.impl.GenericDaoImpl;

import com.uca.notice.dao.NoticeUserDao;
import com.uca.notice.pojos.UcaNoticeUser;

@Repository
public class NoticeUserDaoImpl extends GenericDaoImpl<UcaNoticeUser> implements NoticeUserDao {
}