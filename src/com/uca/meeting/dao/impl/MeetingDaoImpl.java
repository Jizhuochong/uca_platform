package com.uca.meeting.dao.impl;

import org.springframework.stereotype.Repository;

import cn.com.capinfo.core.dao.impl.GenericDaoImpl;

import com.uca.meeting.dao.MeetingDao;
import com.uca.meeting.pojos.UcaMeetingRoom;

@Repository
public class MeetingDaoImpl extends GenericDaoImpl<UcaMeetingRoom> implements MeetingDao {

}
