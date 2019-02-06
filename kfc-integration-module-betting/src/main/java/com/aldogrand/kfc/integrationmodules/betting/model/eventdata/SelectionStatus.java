package com.aldogrand.kfc.integrationmodules.betting.model.eventdata;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "SelectionStatus")
@XmlEnum
public enum SelectionStatus {
	Unpriced, 
	Trading, 
	Suspended;
	
    public String value() {
        return name();
    }

    public static SelectionStatus fromValue(String v) {
        return valueOf(v);
    }
}
