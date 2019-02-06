package com.aldogrand.kfc.msg.events.error;

import com.aldogrand.kfc.annotation.ContentType;
import com.aldogrand.kfc.msg.EventContentType;
import com.aldogrand.kfc.msg.events.KFCErrorEvent;

@ContentType(EventContentType.FETCH_EVENT_ERROR)
public class FetchError extends KFCErrorEvent {
	
}
