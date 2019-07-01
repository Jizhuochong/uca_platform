package com.uca.archives.dao.impl;

import org.springframework.stereotype.Repository;

import cn.com.capinfo.core.dao.impl.GenericDaoImpl;

import com.uca.archives.dao.ArchivesDao;
import com.uca.archives.pojos.UcaArchives;

@Repository
public class ArchivesDaoImpl extends GenericDaoImpl<UcaArchives> implements ArchivesDao{

}
