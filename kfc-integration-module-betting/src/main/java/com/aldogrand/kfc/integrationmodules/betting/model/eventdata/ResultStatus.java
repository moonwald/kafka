package com.aldogrand.kfc.integrationmodules.betting.model.eventdata;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "ResultStatus")
@XmlEnum
public enum ResultStatus {
	None, 
	Winner, 
	Pushed,
	Loser,
	Placed,
	Partial;
	
    public String value() {
        return name();
    }

    public static ResultStatus fromValue(String v) {
        return valueOf(v);
    }
}
