package com.aldogrand.kfc.utils.general;

import com.aldogrand.sbpc.model.MetaTag;
import com.aldogrand.sbpc.model.MetaTagType;

public class XDCUtility {

	/**
	 * Create a {@link MetaTag} for the given details
	 * 
	 * @param type MetaTag type
	 * @param name Name of the meta tag
	 * @return MetaTag model
	 */
	public static MetaTag createMetaTag(MetaTagType type, String name) {
		MetaTag tag = new MetaTag();
		tag.setType(type); 
		tag.setName(name);
		return tag;
	}
}
