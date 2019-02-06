package com.aldogrand.kfc.msg.events.error;

import com.aldogrand.kfc.annotation.ContentType;
import com.aldogrand.kfc.msg.EventContentType;
import com.aldogrand.kfc.msg.events.KFCErrorEvent;

@ContentType(EventContentType.MAP_EVENT_ERROR)
public class MapError extends KFCErrorEvent {

}
