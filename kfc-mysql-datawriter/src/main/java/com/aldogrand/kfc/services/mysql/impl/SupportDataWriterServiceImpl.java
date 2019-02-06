package com.aldogrand.kfc.services.mysql.impl;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.aldogrand.sbpc.dataaccess.MetaTagDao;
import com.aldogrand.sbpc.dataaccess.model.MetaTagDto;
import com.aldogrand.sbpc.model.MetaTagType;
import com.aldogrand.kfc.services.mysql.SupportDataWriterService;

/**
 * 
 * <p>
 * <b>Title</b> SupportDataWriterServiceImpl
 * </p>
 * <p>
 * <b>Description</b> Service to support datawriter services. 
 * For example to open a new transaction and get a new database snapshot.
 * </p>
 * <p>
 * <b>Company</b> AldoGrand Consultancy Ltd
 * </p>
 * <p>
 * <b>Copyright</b> Copyright (c) 2012
 * </p>
 * @author Aldo Grand
 *
 */
public class SupportDataWriterServiceImpl implements SupportDataWriterService {

	private MetaTagDao			metaTagDao;
	
	@Transactional(readOnly=true, propagation = Propagation.REQUIRES_NEW)
	public MetaTagDto getMetaTag(String name, MetaTagType type) {
		MetaTagDto tagDto = metaTagDao.getMetaTag(name, type);
		return tagDto;
	}

	public MetaTagDao getMetaTagDao() {
		return metaTagDao;
	}

	public void setMetaTagDao(MetaTagDao metaTagDao) {
		this.metaTagDao = metaTagDao;
	}

}
