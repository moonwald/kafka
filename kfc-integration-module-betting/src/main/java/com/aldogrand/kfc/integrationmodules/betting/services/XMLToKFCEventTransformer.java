package com.aldogrand.kfc.integrationmodules.betting.services;

import static org.springframework.messaging.MessageHeaders.ERROR_CHANNEL;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.support.GenericMessage;

import com.aldogrand.kfc.integrationmodules.IntegrationModuleServiceInfo;
import com.aldogrand.kfc.integrationmodules.betting.model.eventdata.Updategram;
import com.aldogrand.kfc.integrationmodules.betting.msg.events.BettingUpdategramReceivedEvent;
import com.aldogrand.kfc.msg.events.fetcher.OffersReceivedEvent;

/**
 * Transformer from Betgenius XML document to kfc event 
 * <p>
 * <b>Title</b> XMLToKFCEventTransformer
 * </p>
 * <p>
 * <b>Description</b>
 * </p>
 * <p>
 * <b>Company</b> AldoGrand Consultancy Ltd
 * </p>
 * <p>
 * <b>Copyright</b> Copyright (c) 2015
 * </p>
 * @author Aldo Grand
 *
 */
public class XMLToKFCEventTransformer {

	private final Logger logger = LogManager.getLogger(getClass());
	
	private JAXBContext jaxbContext;
	
	@Autowired
	private IntegrationModuleServiceInfo serviceInfo;
	
	/**
	 * Transform Message<String> to KFCEvent 
	 * 
	 * @param message Payload is a XML from Betgenius
	 * 
	 * @return BetgeniusUpdategramReceivedEvent <KFCEvent>
	 */
	public BettingUpdategramReceivedEvent transform(Message<String> message) {
		BettingUpdategramReceivedEvent updategramReceivedEvent = new BettingUpdategramReceivedEvent();
		
		if (serviceInfo == null) {
  		  updategramReceivedEvent.setIntegrationModuleId("10");
  		  updategramReceivedEvent.setIntegrationModuleName("Betgenius");
		} else {
		  updategramReceivedEvent.setIntegrationModuleId(serviceInfo.getIntegrationModuleId());
          updategramReceivedEvent.setIntegrationModuleName(serviceInfo.getIntegrationModuleName());
		}
		
		try {
			String contentTypeName = (String) message.getHeaders().get(MessageHeaders.CONTENT_TYPE);
			if (contentTypeName == null) {
				throw new IllegalArgumentException(
						"no content type given in message " + message.getHeaders());
			} else if (contentTypeName.equalsIgnoreCase("BETGENIUS_UPDATEGRAM_RAW") && message.getPayload() != null) {
		        
				Unmarshaller unmarshaller = getUnmarshaller(Updategram.class);				
				InputStream is = new ByteArrayInputStream(message.getPayload().getBytes(StandardCharsets.UTF_8));
				
				Updategram updateGram = (Updategram) unmarshaller.unmarshal(is);
				updategramReceivedEvent.setUpdategram(updateGram);
			} else {
				updategramReceivedEvent = null;
			}
			
			return updategramReceivedEvent;
			
		} catch (JAXBException e) {
			logger.error("Error transforming process-message XML to Updategram", e);
			return null;
		} 
	}
	
	/**
	 * Transform XML content type to BetgeniusUpdategramReceivedEvent
	 * @param content Raw XML from Betgenius
	 * @return BetgeniusUpdategramReceivedEvent
	 */
	public BettingUpdategramReceivedEvent transform(String content, String contentType) {	  
	  Map <String, Object> header = new HashMap <String, Object>();     
      header.put("contentType", contentType);
      Message<String> message = new GenericMessage<String>(content, header);
      
	  BettingUpdategramReceivedEvent updategramReceivedEvent = transform(message);
	  
	  return updategramReceivedEvent;	  
	}
	
	private Unmarshaller getUnmarshaller(Class<?>... clazz)
			throws JAXBException {
		if (jaxbContext == null) {
			synchronized (this) {
				if (jaxbContext == null) {
					jaxbContext = JAXBContext.newInstance(clazz);
				}
			}
		}
		// Create a new unmarshaller as they are not thread safe.
		return jaxbContext.createUnmarshaller();
	}

  public IntegrationModuleServiceInfo getServiceInfo() {
    return serviceInfo;
  }

  public void setServiceInfo(IntegrationModuleServiceInfo serviceInfo) {
    this.serviceInfo = serviceInfo;
  }
}
