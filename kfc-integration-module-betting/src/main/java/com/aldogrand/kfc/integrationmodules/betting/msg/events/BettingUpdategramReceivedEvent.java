package com.aldogrand.kfc.integrationmodules.betting.msg.events;

import com.aldogrand.kfc.annotation.ContentType;
import com.aldogrand.kfc.integrationmodules.betting.model.eventdata.Updategram;
import com.aldogrand.kfc.msg.events.KFCEvent;

@ContentType(BettingEventContentType.BETGENIUS_UPDATEGRAM_RECEIVED)
public class BettingUpdategramReceivedEvent extends KFCEvent{

	private Updategram updategram;

	public Updategram getUpdategram() {
		return updategram;
	}

	public void setUpdategram(Updategram updategram) {
		this.updategram = updategram;
	}
}
