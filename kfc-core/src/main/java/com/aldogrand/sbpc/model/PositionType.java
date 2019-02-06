package com.aldogrand.sbpc.model;

import org.apache.commons.lang3.StringUtils;

import com.aldogrand.sbpc.connectors.model.Team;

/**
 * Represents the {@link Team} position
 * 
 * @author aldogrand
 *
 */
public enum PositionType {
	HOME("HOME"), AWAY("AWAY"), UNDEFINED("UNDEFINED");
	
	private String type;

	PositionType(String name) {
		this.type = name;
	}
	
	public static PositionType fromString(String positionType) {
		if (StringUtils.isNotBlank(positionType)) {
			for (PositionType status : PositionType.values()) {
				if (positionType.equalsIgnoreCase(status.type)) {
					return status;
				}
			}
		}
		return null;
	}
	
}
