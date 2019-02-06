package com.aldogrand.kfc.error;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.messaging.Message;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.aldogrand.sbpc.model.EventStatus;
import com.aldogrand.sbpc.model.SourceEvent;
import com.aldogrand.kfc.error.model.MessagingError;
import com.aldogrand.kfc.error.repository.ErrorRepository;
import com.aldogrand.kfc.interfaces.KFCEventFactory;
import com.aldogrand.kfc.msg.KFCEventFactoryImpl;
import com.aldogrand.kfc.msg.events.KFCErrorEvent;
import com.aldogrand.kfc.msg.events.KFCEvent;
import com.aldogrand.kfc.msg.events.error.CreationError;
import com.aldogrand.kfc.msg.events.raw.SourceEventCreatedEvent;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/kfc-error-manager-test.xml"})
public class ErrorRepositoryTests {

	private static String TEST_PRODUCED_BY = "JUNIT_TEST";
	
	@Autowired
	private ErrorRepository repository;
	
	private static KFCEventFactory kfcMessageFactory = new KFCEventFactoryImpl();;
	
	@AfterClass
	public static void afterClass() {		
//		List<MessagingError> errors = repository.findAll();
//				
//		for (MessagingError error : errors) {
//			KFCErrorEvent KFCErrorEvent = error.getPayload();
//			if (KFCErrorEvent.getProducedBy().equalsIgnoreCase(TEST_PRODUCED_BY)) {
//				repository.delete(error);
//			}
//		}		
	}
	
	@Ignore
	@Test
	public void insertkfcErrors() {				
		repository.save(createSampleOfErrors());
		//readsErrorsProducedByJUnit();
	}
	
	@Ignore
	@Test
	public void readsFirstPage() {
		Page<MessagingError> errorsPaged = repository.findAll(new PageRequest(0, 10));				
		assertTrue(errorsPaged.isFirst());		
	}
		
	public void readsErrorsProducedByJUnit() {		
		List<MessagingError> errors = repository.findAll();
		boolean found = false;
		
		for (MessagingError error : errors) {
			KFCErrorEvent KFCErrorEvent = error.getPayload();
			if (KFCErrorEvent.getProducedBy().equalsIgnoreCase(TEST_PRODUCED_BY)) {
				found = true;
				break;
			}
		}
		assertTrue(found);
	}

	
	private List<MessagingError> createSampleOfErrors() {		
		List<String> nameVariants = new ArrayList<String>();
		SourceEvent sourceEvent = new SourceEvent();
		sourceEvent.setConnectorId(1L);
		sourceEvent.setCreator(false);
		sourceEvent.setId(1L);
		sourceEvent.setSourceId("1000");
		sourceEvent.setSourceName("Event Source Name");
		nameVariants.add(sourceEvent.getSourceName());
		sourceEvent.setStartTime(new Date());
		sourceEvent.setStatus(EventStatus.OPEN);
		sourceEvent.setMarkets(Collections.EMPTY_LIST);
		sourceEvent.setMetaTags(Collections.EMPTY_SET);
		sourceEvent.setNameVariants(nameVariants);
				
		SourceEventCreatedEvent sourceEventCreated = new SourceEventCreatedEvent();
		sourceEventCreated.setIntegrationModuleId("1");
		sourceEventCreated.setSourceEvent(sourceEvent);
		
		Message<KFCEvent> msgEvent = kfcMessageFactory.createMessage(sourceEventCreated);
		
		CreationError creationError = new CreationError();
		creationError.setError("Exception");
		creationError.setProducedBy(TEST_PRODUCED_BY);
		creationError.setTimestamp(Calendar.getInstance().getTimeInMillis());
		creationError.setOriginalEvent(msgEvent);
		
		Message<KFCErrorEvent> msgError = (Message<KFCErrorEvent>) kfcMessageFactory.createMessage(creationError);
		
		List<MessagingError> errors = new ArrayList<MessagingError>();		
		MessagingError error1 = new MessagingError();		
		error1.setHeader(msgError.getHeaders());
		error1.setPayload(msgError.getPayload());
		
		errors.add(error1);
		
		return errors;
	}
}
