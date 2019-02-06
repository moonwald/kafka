package com.aldogrand.kfc.producer;

import com.aldogrand.kfc.msg.EventContentType;
import com.aldogrand.kfc.msg.events.KFCEvent;

import java.util.Map;

/**
 * Created by aldogrand on 06/11/14.
 */
public class KFCEventContentTypeMapper {

    private Map <EventContentType, Class> contentTypeToKFCEventMap;
    private Map <Class, EventContentType> kFCEventToContentTypeMap;

    public Class<? extends KFCEvent> getKFCEventMapping(EventContentType key) {
        return contentTypeToKFCEventMap.get(key);
    }

    public EventContentType getContentTypeMapping(Class<? extends KFCEvent> key) {
        return kFCEventToContentTypeMap.get(key);
    }

    public void setContentTypeToKFCEventMap(Map<EventContentType, Class> contentTypeToKFCEventMap) {
        this.contentTypeToKFCEventMap = contentTypeToKFCEventMap;
    }

    public void setKFCEventToContentTypeMap(Map<Class, EventContentType> kFCEventToContentTypeMap) {
        this.kFCEventToContentTypeMap = kFCEventToContentTypeMap;
    }
}
