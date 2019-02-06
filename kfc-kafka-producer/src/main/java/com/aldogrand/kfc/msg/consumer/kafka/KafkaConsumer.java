package com.aldogrand.kfc.msg.consumer.kafka;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;
import kafka.serializer.StringDecoder;
import kafka.utils.VerifiableProperties;

import org.apache.commons.lang3.StringUtils;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.codehaus.jackson.JsonNode;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;

import com.aldogrand.kfc.consumer.MessageDecoder;
import com.aldogrand.kfc.msg.consumer.kafka.utils.NamedThreadFactory;

/**
 * <p>
 * <b>Title</b> KafkaConsumer
 * </p>
 * <p>
 * <b>Description</b> A Kafka Consumer that consumes messages
 * from a topic using a configurable number of threads.
 * </p>
 * <p>
 * <b>Company</b> AldoGrand Consultancy Ltd
 * </p>
 * <p>
 * <b>Copyright</b> Copyright (c) 2014
 * </p>
 * @author Aldo Grand
 * @version 1.0
 */
public class KafkaConsumer
{
    private static final String CLIENT_ID_PROP_NAME = "client.id";
    private static final String GROUP_ID_PROP_NAME = "group.id";
    private static final String ZOOKEEPER_CONNECT_PROP_NAME = "zookeeper.connect";
    private static final String SOCKET_TIMEOUT_PROP_NAME = "socket.timeout.ms";
    private static final Long SOCKET_TIMEOUT_PROP_DEFAULT = 30000l;
    private static final String AUTO_COMMIT_ENABLE_PROP_NAME = "auto.commit.enable";
    private static final Boolean AUTO_COMMIT_ENABLE_PROP_DEFAULT = Boolean.TRUE;
    private static final String AUTO_COMMIT_INTERVAL_PROP_NAME = "auto.commit.interval.ms";
    private static final Long AUTO_COMMIT_INTERVAL_PROP_DEFAULT = 60000l;
    private static final String AUTO_OFFSET_RESET_PROP_NAME = "auto.offset.reset";
    private static final String AUTO_OFFSET_RESET_PROP_VALUE = "smallest";
    private static final String ZOOKEEPER_CONNECT_TIMEOUT_PROP_NAME = "zookeeper.connection.timeout.ms";
    private static final Long ZOOKEEPER_CONNECT_TIMEOUT_PROP_DEFAULT = 6000l;
    private static final String ZOOKEEPER_SESSION_TIMEOUT_PROP_NAME = "zookeeper.session.timeout.ms";
    private static final Long ZOOKEEPER_SESSION_TIMEOUT_PROP_DEFAULT = 6000l;
    private static final String ZOOKEEPER_SYNC_TIME_PROP_NAME = "zookeeper.sync.time.ms";
    private static final Long ZOOKEEPER_SYNC_TIME_PROP_DEFAULT = 2000l;
    private static final String CONSUMER_THREAD_NAME_PREFIX = "kafka-consumer-";
    private static final int CONSUMER_THREADS_DEFAULT = 1;
    private static final String CURATOR_CLIENT_THREAD_NAME_PREFIX = "kafka-consumer-zookeeper-";

    protected final Logger logger = LogManager.getLogger(getClass());
    
    private final List<KafkaStreamProcessor> streamProcessors = new ArrayList<KafkaStreamProcessor>();
    private ConsumerConnector connector;
    private ExecutorService executor;
    private boolean initialized = false;
    
    private String clientId;
    private String consumerGroupId;
    private String zooKeeperList;
    private Long socketTimeout = SOCKET_TIMEOUT_PROP_DEFAULT;
    private Long zooKeeperConnectTimeout = ZOOKEEPER_CONNECT_TIMEOUT_PROP_DEFAULT;
    private Long zooKeeperSessionTimeout = ZOOKEEPER_SESSION_TIMEOUT_PROP_DEFAULT;
    private Long zooKeeperSyncTime = ZOOKEEPER_SYNC_TIME_PROP_DEFAULT;
    private String zooKeeperConfigRoot;
    private Boolean autoOffsetCommit = AUTO_COMMIT_ENABLE_PROP_DEFAULT;
    private Long autoOffsetCommitInterval = AUTO_COMMIT_INTERVAL_PROP_DEFAULT;
    private List<String> topicNames;
    private String topicName;
    private int consumerThreads = CONSUMER_THREADS_DEFAULT;
    private Class<? extends MessageDecoder> serializerClass;
    private MessageHandler messageHandler;
    
    /**
     * A unique id for the client application
     * @return the clientId
     */
    public String getClientId()
    {
        return clientId;
    }

    /**
     * @param clientId the clientId to set
     */
    public void setClientId(String clientId)
    {
        this.clientId = clientId;
    }

    /**
     * Each message from a topic is guaranteed to go to just one consumer in a 
     * consumer group. This enables concurrent message consumption and processing.
     * @return the consumerGroupId
     */
    public String getConsumerGroupId()
    {
        return consumerGroupId;
    }

    /**
     * @param consumerGroupId the consumerGroupId to set
     */
    public void setConsumerGroupId(String consumerGroupId)
    {
        this.consumerGroupId = consumerGroupId;
    }

    /**
     * ZooKeeper hosts in the format<br/>
     * <host1:port>,<host2:port>
     * @return the zooKeeperList
     */
    public String getZooKeeperList()
    {
        return zooKeeperList;
    }

    /**
     * @param zooKeeperList the zooKeeperList to set
     */
    public void setZooKeeperList(String zooKeeperList)
    {
        this.zooKeeperList = zooKeeperList;
    }

    /**
     * @return the socketTimeout
     */
    public Long getSocketTimeout()
    {
        return socketTimeout;
    }

    /**
     * @param socketTimeout the socketTimeout to set
     */
    public void setSocketTimeout(Long socketTimeout)
    {
        this.socketTimeout = socketTimeout;
    }

    /**
     * @return the zooKeeperConnectTimeout
     */
    public Long getZooKeeperConnectTimeout()
    {
        return zooKeeperConnectTimeout;
    }

    /**
     * @param zooKeeperConnectTimeout the zooKeeperConnectTimeout to set
     */
    public void setZooKeeperConnectTimeout(Long zooKeeperConnectTimeout)
    {
        this.zooKeeperConnectTimeout = zooKeeperConnectTimeout;
    }

    /**
     * @return the zooKeeperSessionTimeout
     */
    public Long getZooKeeperSessionTimeout()
    {
        return zooKeeperSessionTimeout;
    }

    /**
     * @param zooKeeperSessionTimeout the zooKeeperSessionTimeout to set
     */
    public void setZooKeeperSessionTimeout(Long zooKeeperSessionTimeout)
    {
        this.zooKeeperSessionTimeout = zooKeeperSessionTimeout;
    }

    /**
     * @return the zooKeeperSyncTime
     */
    public Long getZooKeeperSyncTime()
    {
        return zooKeeperSyncTime;
    }

    /**
     * @param zooKeeperSyncTime the zooKeeperSyncTime to set
     */
    public void setZooKeeperSyncTime(Long zooKeeperSyncTime)
    {
        this.zooKeeperSyncTime = zooKeeperSyncTime;
    }

    /**
     * @return the zooKeeperConfigRoot
     */
    public String getZooKeeperConfigRoot()
    {
        return zooKeeperConfigRoot;
    }

    /**
     * @param zooKeeperConfigRoot the zooKeeperConfigRoot to set
     */
    public void setZooKeeperConfigRoot(String zooKeeperConfigRoot)
    {
        this.zooKeeperConfigRoot = zooKeeperConfigRoot;
    }

    /**
     * @return the autoOffsetCommit
     */
    public Boolean getAutoOffsetCommit()
    {
        return autoOffsetCommit;
    }

    /**
     * @param autoOffsetCommit the autoOffsetCommit to set
     */
    public void setAutoOffsetCommit(Boolean autoOffsetCommit)
    {
        this.autoOffsetCommit = autoOffsetCommit;
    }

    /**
     * @return the autoOffsetCommitInterval
     */
    public Long getAutoOffsetCommitInterval()
    {
        return autoOffsetCommitInterval;
    }

    /**
     * @param autoOffsetCommitInterval the autoOffsetCommitInterval to set
     */
    public void setAutoOffsetCommitInterval(Long autoOffsetCommitInterval)
    {
        this.autoOffsetCommitInterval = autoOffsetCommitInterval;
    }

    /**
     * @return the topicName
     */
    public List<String> getTopicNames()
    {
        return topicNames;
    }

    /**
     * @param topicName the topicName to set
     */
    public void setTopicNames(List<String> topicNames)
    {
        this.topicNames = topicNames;
    }

    /**
     * @return the consumerThreads
     */
    public int getConsumerThreads()
    {
        return consumerThreads;
    }

    /**
     * @param consumerThreads the consumerThreads to set
     */
    public void setConsumerThreads(int consumerThreads)
    {
        this.consumerThreads = consumerThreads;
    }

    /**
     * @return the serializerClass
     */
    public Class<? extends MessageDecoder> getSerializerClass()
    {
        return serializerClass;
    }

    /**
     * @param serializerClass the serializerClass to set
     */
    public void setSerializerClass(Class<? extends MessageDecoder> serializerClass)
    {
        this.serializerClass = serializerClass;
    }

    /**
     * @return the messageHandler
     */
    public MessageHandler getMessageHandler()
    {
        return messageHandler;
    }

    /**
     * @param messageHandler the messageHandler to set
     */
    public void setMessageHandler(MessageHandler messageHandler)
    {
        this.messageHandler = messageHandler;
    }

    /**
     * Initialise the Kafka consumer.
     * @throws Exception
     */
    public void init() throws Exception
    {
        init(null);
    }
    
    /**
     * Initialise the Kafka consumer and start consuming messages from the specified
     * partition offsets.
     * @param partitionOffsets A map where the key is the partition number and the value is the offset for that partition.
     * @throws Exception 
     */
    public synchronized void init(Map<Integer, Long> partitionOffsets) throws Exception
    {
        if (initialized)
        {
            return;
        }
        
        logger.debug("Initialising the Kafka Consumer ...");

        if ((consumerGroupId == null) || (consumerGroupId.isEmpty()))
        {
            throw new IllegalStateException("Unable to initialize Kafka Consumer. ConsumerGroupId is not set.");
        }
        if ((zooKeeperList == null) || (zooKeeperList.isEmpty()))
        {
            throw new IllegalStateException("Unable to initialize Kafka Consumer. ZooKeeperList is not set.");
        }
        if (StringUtils.isEmpty(topicName))
        {
        	throw new IllegalStateException("Unable to initialize Kafka Consumer. TopicNames is not set.");
        }
//        if (CollectionUtils.isEmpty(topicNames))
//        {
//            throw new IllegalStateException("Unable to initialize Kafka Consumer. TopicNames is not set.");
//        }
        if (serializerClass == null)
        {
            throw new IllegalStateException("Unable to initialize Kafka Consumer. SerializerClass is not set.");
        }
        if (messageHandler == null)
        {
            throw new IllegalStateException("Unable to initialize Kafka Consumer. MessageHandler is not set.");
        }

        // Set the consumer offsets
        if ((partitionOffsets != null) && (!partitionOffsets.isEmpty()))
        {
            setConsumerOffsets(partitionOffsets);
        }
        
        String name = (clientId != null ? CONSUMER_THREAD_NAME_PREFIX + clientId : CONSUMER_THREAD_NAME_PREFIX);
        executor = Executors.newCachedThreadPool(new NamedThreadFactory(Thread.NORM_PRIORITY-2));
        
        connector = createConsumerConnector();
        Map<String, Integer> topicNamesThreadCountMap = Collections.singletonMap(topicName, consumerThreads);
//		for (String topicName : topicNames)
//		{
//				topicNamesThreadCountMap.put(topicName, consumerThreads);
//		}
        Map<String, List<KafkaStream<String, Message<JsonNode>>>> kafkaStreamMap = connector.createMessageStreams(
        		topicNamesThreadCountMap, new StringDecoder(null), serializerClass.getConstructor(VerifiableProperties.class).newInstance(new Object[] {null}));
        
        for (String topicName : kafkaStreamMap.keySet())
        {
            List<KafkaStream<String, Message<JsonNode>>> kafkaStreams = kafkaStreamMap.get(topicName);
            for (KafkaStream<String, Message<JsonNode>> kafkaStream : kafkaStreams)
            {
                KafkaStreamProcessor processor = new KafkaStreamProcessor(topicName, kafkaStream);
                streamProcessors.add(processor);
                executor.submit(processor);
            }
        }
        
        initialized = true;
    }
    
    /**
     * Set the Kafka consumer offsets directly in ZooKeeper.<br/>
     * This is a bit of a hack but it is significantly easier than using the low level Kafka consumer.
     * @param offsets
     * @throws Exception
     */
    private void setConsumerOffsets(Map<Integer, Long> offsets) throws Exception
    {
        logger.debug("Setting consumer offsets.");
        
        String threadName = (clientId != null ? CURATOR_CLIENT_THREAD_NAME_PREFIX + clientId : CURATOR_CLIENT_THREAD_NAME_PREFIX);
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(500, 5);
        
        CuratorFrameworkFactory.Builder curatorBuilder = CuratorFrameworkFactory.builder()
                .connectString(zooKeeperList)
                .connectionTimeoutMs(zooKeeperConnectTimeout.intValue())
                .sessionTimeoutMs(zooKeeperSessionTimeout.intValue())
                .threadFactory(new NamedThreadFactory(Thread.NORM_PRIORITY-2));
               // .retryPolicy(retryPolicy);
        if ((zooKeeperConfigRoot != null) && (zooKeeperConfigRoot.isEmpty()))
        {
            curatorBuilder = curatorBuilder.namespace(zooKeeperConfigRoot);
        }
        
        CuratorFramework curatorClient = curatorBuilder.build();
        curatorClient.start();
        
        try
        {
            for (Integer partition : offsets.keySet())
            {
                String path = createPartitionPath(partition);
                Long offset = offsets.get(partition);

                logger.debug("Setting consumer offset for partition {} to {}.", partition, offset);
                
                Stat stat = curatorClient.checkExists().forPath(path);
                if (stat == null)
                {
                    curatorClient.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT)
                        .forPath(path, Long.valueOf(offset).toString().getBytes());
                }
                else
                {
                    stat = curatorClient.setData().withVersion(stat.getVersion())
                            .forPath(path, Long.valueOf(offset).toString().getBytes());
                }
            }
        }
        catch (Exception e)
        {
            logger.error("Error setting consumer offsets. Unable to start consumer.", e);
            
            throw e;
        }
        finally
        {
            curatorClient.close();
        }
    }
    
    /**
     * Create the path to the partition offset for this consumer.
     * @param partitionNumber
     * @return
     */
    private String createPartitionPath(Integer partitionNumber)
    {
        StringBuilder path = new StringBuilder();
        path.append("/consumers/").append(consumerGroupId).append("/offsets/")
            .append(topicNames).append("/").append(partitionNumber);
        return path.toString();
    }
    
    /**
     * Create a new {@link ConsumerConnector}.
     * @return
     */
    private ConsumerConnector createConsumerConnector()
    {
        ConsumerConfig config = createConsumerConfig();
        ConsumerConnector connector = Consumer.createJavaConsumerConnector(config);
        return connector;
    }
    
    /**
     * Create the {@link ConsumerConfig} used to create a {@link ConsumerConnector}.
     * @return
     */
    protected ConsumerConfig createConsumerConfig()
    {
        Properties props = new Properties();
        props.setProperty(CLIENT_ID_PROP_NAME, clientId != null ? clientId : ""); // A unique id for the client application
        props.setProperty(GROUP_ID_PROP_NAME, consumerGroupId); // A grouping of consumers - allows more concurrency in event processing
        props.setProperty(ZOOKEEPER_CONNECT_PROP_NAME, zooKeeperList); // ZooKeeper hosts <host1:port>,<host2:port>
        props.setProperty(SOCKET_TIMEOUT_PROP_NAME, socketTimeout.toString()); // Timeout for network operations
        props.setProperty(ZOOKEEPER_CONNECT_TIMEOUT_PROP_NAME, zooKeeperConnectTimeout.toString()); // max time to wait while trying to connect to ZooKeeper
        props.setProperty(ZOOKEEPER_SESSION_TIMEOUT_PROP_NAME, zooKeeperSessionTimeout.toString()); // if consumer fails to heartbeat ZooKeeper in this time it is considered dead
        props.setProperty(ZOOKEEPER_SYNC_TIME_PROP_NAME, zooKeeperSyncTime.toString()); // Time a follower can be behind leader
        props.setProperty(AUTO_COMMIT_ENABLE_PROP_NAME, autoOffsetCommit.toString().toLowerCase()); // Automatically or manually commit consumer offset.
        props.setProperty(AUTO_COMMIT_INTERVAL_PROP_NAME, autoOffsetCommitInterval.toString()); // The interval used for automatically committing offsets.
        props.setProperty(AUTO_OFFSET_RESET_PROP_NAME, AUTO_OFFSET_RESET_PROP_VALUE); // smallest | largest - Where to restart consuming if there is no stored offset for the consumer group
        
        ConsumerConfig config = new ConsumerConfig(props);
        return config;
    }

    /**
     * Destroy the Kafka consumer releasing all resources.
     */
    public synchronized void destroy()
    {
        if (!initialized)
        {
            return;
        }
        
        logger.debug("Destroying Kafka Consumer ...");
        
        for (KafkaStreamProcessor processor : streamProcessors)
        {
            processor.setActive(false);
        }
        streamProcessors.clear();
        
        connector.commitOffsets();
        connector.shutdown();
        this.connector = null;
        
        executor.shutdown();
        this.executor = null;
        
        initialized = false;
    }
    
    /**
     * Process the message data consumed from the named topic and partition.
     * @param topicName
     * @param partitionKey
     * @param partitionNumber
     * @param sequenceNumber
     * @param message
     * @throws InterruptedException if processing of the message is interrupted.
     */
    private void onMessage(String topicName, String partitionKey, int partitionNumber, 
            long sequenceNumber, Message<JsonNode> message) throws InterruptedException
    {
        logger.trace("Processing message {} from topic {} and parition {}.", 
                sequenceNumber, topicName, partitionNumber);

        try
        {
//            message.addHeader(KafkaMessageHeaders.KAFKA_TOPIC_NAME.toString(), topicName);
//            message.addHeader(KafkaMessageHeaders.KAFKA_PARTITION_KEY.toString(), partitionKey);
//            message.addHeader(KafkaMessageHeaders.KAFKA_PARTITION_NUMBER.toString(), Integer.toString(partitionNumber));
//            message.addHeader(KafkaMessageHeaders.KAFKA_SEQUENCE_NUMBER.toString(), Long.toString(sequenceNumber));
            
            messageHandler.handleMessage(message);
        }
        catch (Throwable t)
        {
            if (t instanceof InterruptedException)
            {
                logger.error("Interrupted processing message {} from topic {}. Message has not completed processing.", message, topicName, t);
                throw (InterruptedException) t;
            }
            
            logger.error("Error processing message {} from topic {}.", message, topicName, t);
        }
    }

    /**
     * <p>
     * <b>Title</b> KafkaStreamProcessor
     * </p>
     * <p>
     * <b>Description</b> A {@link Runnable} that performs the processing of messages
     * from {@link KafkaStream}s. 
     * </p>
     * <p>
     * This {@link Runnable} can be stopped by setting {@link #setActive(boolean)} to
     * false. It can be re-used by resubmitting it to a new {@link Thread} to resume
     * processing of the {@link KafkaStream} from where it left off.
     * </p>
     * <p>
     * <b>Company</b> AldoGrand Consultancy Ltd
     * </p>
     * <p>
     * <b>Copyright</b> Copyright (c) 2014
     * </p>
     * @author Aldo Grand
     * @version 1.0
     */
    private class KafkaStreamProcessor implements Runnable
    {
        private final ConsumerIterator<String, Message<JsonNode>> iterator;
        private Thread executingThread;
        
        private final String topicName;
        private volatile boolean active;
        
        KafkaStreamProcessor(String topicName, KafkaStream<String, Message<JsonNode>> kafkaStream)
        {
            this.topicName = topicName;
            this.iterator = kafkaStream.iterator();
            active = true;
        }
        
        /**
         * 
         * @return the active
         */
        @SuppressWarnings("unused")
        public boolean isActive()
        {
            return active;
        }

        /**
         * @param active the active to set
         */
        public synchronized void setActive(boolean active)
        {
            this.active = active;
            if ((!active) && (executingThread != null))
            {
                executingThread.interrupt();
            }
        }

        /* (non-Javadoc)
         * @see java.lang.Runnable#run()
         */
        @Override
        public void run()
        {
            executingThread = Thread.currentThread();
            
            while (active)
            {
                try
                {
                    if (iterator.hasNext())
                    {
                        try
                        {
                            MessageAndMetadata<String, Message<JsonNode>> messageAndMetadata = iterator.next();
                            int partition = messageAndMetadata.partition();
                            String partitionKey = messageAndMetadata.key();
                            long offset = messageAndMetadata.offset();
                            try
                            {
                            	Message<JsonNode> message = messageAndMetadata.message();
                            	
                            	
                                if (message != null)
                                {
                                    onMessage(topicName, partitionKey, partition, offset, message);
                                }
                            }
                            catch (Throwable t) // Errors reading the message
                            {
                                if (t instanceof InterruptedException)
                                {
                                    throw t;
                                }
                                
                                logger.error("Error consuming message {} from topic {}, partition {}. Unable to handle message.", 
                                        offset, topicName, partition, t);
                            }
                        }
                        catch (Throwable t) // protect against errors from the iterator.next
                        {
                            if (t instanceof InterruptedException)
                            {
                                throw t;
                            }
                            
                            logger.error("Error consuming message. Unable to handle message.", t);
                        }
                    }
                }
                catch (InterruptedException e)
                {
                    // Interrupted so thread can finish
                }
            }
            
            executingThread = null;
        }

        /* (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString()
        {
            StringBuilder builder = new StringBuilder();
            builder.append("KafkaStreamProcessor [topicName=");
            builder.append(topicName);
            builder.append(", active=");
            builder.append(active);
            builder.append("]");
            return builder.toString();
        }
    }

	/**
	 * @return the topicNames2
	 */
	public String getTopicName()
	{
		return topicName;
	}

	/**
	 * @param topicNames2 the topicNames2 to set
	 */
	public void setTopicName(String topicNames)
	{
		this.topicName = topicNames;
	}
}
