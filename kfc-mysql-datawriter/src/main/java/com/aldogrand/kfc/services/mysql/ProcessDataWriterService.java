package com.aldogrand.kfc.services.mysql;

import com.aldogrand.kfc.msg.events.processor.EventProcessedEvent;
import com.aldogrand.kfc.msg.events.processor.MarketProcessedEvent;
import com.aldogrand.kfc.msg.events.processor.RunnerProcessedEvent;
import com.aldogrand.kfc.services.mysql.impl.ProcessDataWriterServiceImpl;


/**
 * 
 * <p>
 * <b>Title</b> ProcessDataWriterService
 * </p>
 * <p>
 * <b>Description</b>
 * </p>
 * <p>
 * <b>Company</b> AldoGrand Consultancy Ltd
 * </p>
 * <p>
 * <b>Copyright</b> Copyright (c) 2012
 * </p>
 * @author fjaen
 *
 */
public interface ProcessDataWriterService {
	
	/**
	 * This method receive the EventProcessedEvent with the changed event 
	 * data information to be updated in the database
	 * for more please look at the following method 
	 * {@link ProcessDataWriterServiceImpl#doUpdateCompleteEvent(com.aldogrand.sbpc.model.Event)}
	 * @param processedEvent
	 */
	void update(EventProcessedEvent processedEvent);

	/**
	 * This method receive the EventProcessedEvent with the changed event 
	 * data information to be updated in the database
	 * for more please look at the following method 
	 * {@link ProcessDataWriterServiceImpl#doUpdateCompleteMarket(com.aldogrand.sbpc.model.Market)}
	 * @param processedEvent
	 */
    void update(MarketProcessedEvent processedMarket);

    /**
	 * This method receive the EventProcessedEvent with the changed event 
	 * data information to be updated in the database
	 * for more please look at the following method 
	 * {@link ProcessDataWriterServiceImpl#doUpdateCompleteRunner(com.aldogrand.sbpc.model.Runner)}
	 * @param processedEvent
	 */
    void update(RunnerProcessedEvent processedRunner);
}
