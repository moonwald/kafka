package com.aldogrand.kfc.producer;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Scanner;

import kafka.serializer.Decoder;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.messaging.Message;

import com.aldogrand.kfc.consumer.JSONToKFCEventTransformer;
import com.aldogrand.kfc.exception.ProducerException;
import com.aldogrand.kfc.interfaces.KFCProducer;
import com.aldogrand.kfc.msg.events.KFCEvent;

/**
 *
 * <p>
 * <b>Title</b>: ApplicationKFCProducer.java
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
 *
 * @author dlehane
 *
 */
public class ApplicationKFCProducer {

	/**
	 * Application to simply accept a text file with multiple json messages delimited by a carriage return. Send these messages to
	 * the producer in turn.
	 * 
	 * @param args
	 *            [0] - path and file name to file you want to parse e.g C:\temp\json_messages.txt (windows)
	 */
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("/kfc-file-to-kafka.xml");

		Decoder decoder = (Decoder) ctx.getBean(Decoder.class);
		JSONToKFCEventTransformer transformer = (JSONToKFCEventTransformer) ctx.getBean(JSONToKFCEventTransformer.class);
		KFCProducer producer = (KFCProducer) ctx.getBean(KFCProducer.class);

		try {
			transformer.setApplicationContext(ctx);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		String pathToFile = (String) args[0];

		try {
			Scanner scanner = new Scanner(new File(pathToFile));
			StringBuffer line = new StringBuffer();

			while (scanner.hasNextLine()) {
				line.replace(0, line.length(), scanner.nextLine());

				Message message = (Message) decoder.fromBytes(line.toString().getBytes(Charset.forName("UTF-8")));

				KFCEvent event = transformer.transform(message);

				producer.send(event);
			}

		} catch (ProducerException | IOException e) {
			e.printStackTrace();
		}
	}

}
