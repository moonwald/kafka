package com.aldogrand.kfc.msg.consumer.kafka.leshan.eclipse;

/*******************************************************************************
 * Copyright (c) 2015 Bosch Software Innovations GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v1.0 which accompany this distribution.
 *
 * The Eclipse Public License is available at
 *    http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 *    http://www.eclipse.org/org/documents/edl-v10.html.
 *
 * Contributors:
 *     Alexander Ellwein, Daniel Maier (Bosch Software Innovations GmbH)
 *                                - initial API and implementation
 *******************************************************************************/


import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;


public final class NamedThreadFactory implements ThreadFactory {
	private int priority;
	   private boolean daemon;
	   private final String namePrefix;
	   private static final AtomicInteger poolNumber = new AtomicInteger(1);
	   private final AtomicInteger threadNumber = new AtomicInteger(1);

	   public NamedThreadFactory(int priority) {
	      this(priority, true);
	   }

	   public NamedThreadFactory(int priority, boolean daemon) {
	      this.priority = priority;
	      this.daemon = daemon;
	      namePrefix = "jobpool-" +poolNumber.getAndIncrement() + "-thread-";
	   }

	   @Override
	   public Thread newThread(Runnable r) {
	      Thread t = new Thread(r, namePrefix + threadNumber.getAndIncrement());
	      t.setDaemon(daemon);
	      t.setPriority(priority);
	      return t;
	   }
	
}
