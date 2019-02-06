package com.aldogrand.kfc.msg.producer.kafka;

import kafka.producer.Partitioner;
import kafka.utils.VerifiableProperties;

import org.apache.commons.lang3.math.NumberUtils;
/**
 * 
 * <p>
 * <b>Title</b> DefaultPartitioner.java
 * </p>
 * com.aldogrand.kfc.msg.producer.kafka
 * <p>
 * <b>Description</b> kfc-kafka-producer.
 * </p>
 * <p>
 * <b>Company</b> AldoGrand Consultancy Ltd
 * </p>
 * <p>
 * <b>Copyright</b> Copyright (c) 2015
 * </p>
 * @author aldogrand
 * @version 1.0
 */
public class DefaultPartitioner implements Partitioner 
{
    public DefaultPartitioner(VerifiableProperties props) {

    }

    /**
     * This method is invoked by the kafka and based on the key passed the partition will be decided
     * Key can be a number or any object.
     * if it is number do the mod operation.
     * if not a number then do following.,
     * 1. Take the hashcode.
     * 2. check if it is negative if so multiply by -1 and generate the partition number
     */
	public int partition(Object key, int a_numPartitions)
	{
		int partition = 0;
		String stringKey = (String) key;
		if (NumberUtils.isNumber(stringKey))
		{
			partition = NumberUtils.createDouble(stringKey).intValue() % a_numPartitions;
		} else
		{
			if (stringKey.hashCode() < 0)
				partition = (stringKey.hashCode() * -1) % a_numPartitions;
			else
			{
				partition = (stringKey.hashCode()) % a_numPartitions;
			}
		}
		return partition;
	}
}
