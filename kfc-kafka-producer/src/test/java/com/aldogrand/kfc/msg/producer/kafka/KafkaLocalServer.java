package com.aldogrand.kfc.msg.producer.kafka;
//
// Copyright (c) 2011-2014 AldoGrand Consultancy Ltd., 
//



import com.netflix.curator.framework.CuratorFramework;
import com.netflix.curator.framework.CuratorFrameworkFactory;
import com.netflix.curator.framework.imps.CuratorFrameworkState;
import com.netflix.curator.retry.ExponentialBackoffRetry;
import com.netflix.curator.test.TestingServer;
import kafka.server.KafkaConfig;
import kafka.server.KafkaServerStartable;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;

import java.io.File;
import java.util.Properties;

public class KafkaLocalServer {

    private TestingServer server;
    private CuratorFramework zkServer;
    private KafkaServerStartable kafkaServer;
    private File logDir;
    private int port = 20001;
    private int zkPort = 20002;

    private static final Log LOG = LogFactory.getLog(KafkaLocalServer.class);

    public String getHost() {
        return "localhost";
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getZkPort() {
        return zkPort;
    }

    public void setZkPort(int zkPort) {
        this.zkPort = zkPort;
    }

    public Object getBrokerList() throws Exception {
        return "0:"+port;
    }

    public void startup() throws Exception {
        server = new TestingServer(zkPort);
        String zkConnectionString = server.getConnectString();
        ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 3);
        zkServer = CuratorFrameworkFactory.newClient(zkConnectionString, retryPolicy);
        zkServer.start();

        logDir = new File(System.getProperty("java.io.tmpdir"), "kafka/logs/kafka-test-" + port);
        Properties config = new Properties();
        config.setProperty("zookeeper.connect", zkConnectionString);
        config.setProperty("broker.id", "0");
        config.setProperty("port", String.valueOf(port));
        config.setProperty("log.dirs", logDir.getAbsolutePath());
        config.setProperty("auto.create.topics.enable", "true");
        config.setProperty("num.partitions", "1");
        kafkaServer = new KafkaServerStartable(new KafkaConfig(config));
        kafkaServer.startup();
        LOG.info("Kafka local server started with properties: " + config);
    }

    @After
    public void shutdown() throws Exception {
        kafkaServer.shutdown();
        if (zkServer.getState().equals(CuratorFrameworkState.STARTED)) {
            zkServer.close();
        }
        server.close();
        FileUtils.deleteDirectory(logDir);
    }

}
