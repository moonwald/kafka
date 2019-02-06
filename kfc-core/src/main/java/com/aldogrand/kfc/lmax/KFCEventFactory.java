package com.aldogrand.kfc.lmax;

import com.lmax.disruptor.EventFactory;

/**
 * 
 * <p>
 * <b>Title</b> KFCEventFactory.java
 * </p>
 * <p>
 * <b>Description</b> KFCEventFactory <br/>
 * </p>
 * <p>
 * <b>Company</b> AldoGrand Consultancy Ltd
 * </p>
 * <p>
 * <b>Copyright</b> Copyright (c) 2015
 * </p>
 * 
 * @author aldogrand
 * @version 1.0
 */
public class KFCEventFactory implements EventFactory<KFCLMAXEvent>{

	@Override
	public KFCLMAXEvent newInstance() {
		return new KFCLMAXEvent();
	}

}
