package com.aldogrand.kfc.services.mysql;

import com.aldogrand.sbpc.dataaccess.model.MetaTagDto;
import com.aldogrand.sbpc.model.MetaTagType;

public interface SupportDataWriterService {

	public MetaTagDto getMetaTag(String name, MetaTagType type);
}
