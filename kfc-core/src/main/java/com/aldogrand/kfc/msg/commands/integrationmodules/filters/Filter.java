package com.aldogrand.kfc.msg.commands.integrationmodules.filters;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * <p>
 * <b>Title</b> Filter.java
 * </p>
 * <p>
 * <b>Description</b> Filter <br/>
 * </p>
 * <p>
 * <b>Company</b> AldoGrand Consultancy Ltd
 * </p>
 * <p>
 * <b>Copyright</b> Copyright (c) 2015
 * </p>
 * 
 * @author aldogrand
 * @version 1.0
 */

public abstract class Filter {

	public Filter()	{}
	
	public String asCommaSeparated(String[] values){
		return StringUtils.join(values, ",");
	}
	
	public String asCommaSeparated(List<Object> values){
		return StringUtils.join((Iterator) values, ",");
	}
}
