package com.aldogrand.kfc.utils.general;

/**
 * <p>
 * <b>Title</b> OddsType
 * </p>
 * <p>
 * <b>Description</b> The different odds types supported by Matchbook.<br/>
 * Each of these odds types can be converted to an other by the
 * {@link OddsConverter}.
 * </p>
 * <p>
 * <b>Company</b> AldoGrand Consultancy Ltd
 * </p>
 * <p>
 * <b>Copyright</b> Copyright (c) 2012
 * </p>
 * 
 * @author Aldo Grand
 * @version 1.0
 */
public enum OddsType {
	US("US"), DECIMAL("DECIMAL"), PROBABILITY("%"), HONG_KONG("HK"), MALAY("MALAY"), INDONESIAN(
			"INDO"), FRACTIONAL("FRAC"), UNKNOWN("UNKNOWN");

	private String text;

	OddsType(String text) {
		this.text = text;
	}

	public String getText() {
		return this.text;
	}

	public static OddsType fromString(String text) {
		if (text != null) {
			for (OddsType b : OddsType.values()) {
				if (text.equalsIgnoreCase(b.text)) {
					return b;
				}
			}
		}
		return null;
	}
}
