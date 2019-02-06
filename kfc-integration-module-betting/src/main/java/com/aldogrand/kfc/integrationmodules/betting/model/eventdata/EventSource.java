package com.aldogrand.kfc.integrationmodules.betting.model.eventdata;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Source of an {@link Event}
 * 
 * @author aldogrand
 *
 */
@XmlType(name = "EventSource")
@XmlEnum
public enum EventSource {
	Automatic, Manual;

	public String value() {
		return name();
	}

	public static EventSource fromValue(String v) {
		return valueOf(v);
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
