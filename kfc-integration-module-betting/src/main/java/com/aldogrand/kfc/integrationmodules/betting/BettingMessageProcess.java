package com.aldogrand.kfc.integrationmodules.betting;

import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.xml.source.DomSourceFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;

import com.aldogrand.kfc.interfaces.KFCProducer;

public class BettingMessageProcess {

	@Autowired
	private KFCProducer kafkaProducer;

	public BettingMessageProcess() {}

	public Source process(DOMSource request) {
		Message kafkaMessage = new GenericMessage<DOMSource>(request);
		//kafkaProducer.send(kafkaMessage);
		return new DomSourceFactory().createSource(
				"<echoResponse xmlns=\"http://www.springframework.org/spring-ws/samples/echo\">" +
				request.getNode().getTextContent() + "</echoResponse>");
	}
}
