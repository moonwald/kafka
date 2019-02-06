package com.aldogrand.kfc.integrationmodules.betting.model.eventdata;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "MatchPhase")
@XmlEnum
public enum  MatchPhase {
    PreMatch,
    FirstHalf,
    HalfTime,
    SecondHalf,
    BeforeExtraTime,
    ExtraTimeFirstHalf,
    ExtraTimeHalfTime,
    ExtraTimeSecondHalf,
    BeforePenaltyShootout,
    PenaltyShootout,
    PostMatch,
    MatchAbandoned;

    public String value() {
        return name();
    }

    public static MatchPhase fromValue(String v) {
        return valueOf(v);
    }
}
