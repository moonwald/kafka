/**
 * 
 */
package com.aldogrand.kfc.msg.producer.kafka;

import static junit.framework.TestCase.assertTrue;
import kafka.producer.Partitioner;

import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * <p>
 * <b>Title</b> DefaultPartitionerTest.java
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
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/producer-test.xml"})
public class DefaultPartitionerTest
{
	private Partitioner defaultPartitioner = new DefaultPartitioner(null);
	private String key;
	int total_partition = 13;
	
	@Test
	public void testNumberKey()
	{
		key = "123455677890887657689711418";
		int partitionNum = this.defaultPartitioner.partition(key, total_partition);
		assertTrue(partitionNum<total_partition);
		assertTrue(0<partitionNum);
	}
	@Test
	public void testDoubleKey()
	{
		key = "123455677.890887657689";
		int partitionNum = this.defaultPartitioner.partition(key, total_partition);
		assertTrue(partitionNum<total_partition);
		assertTrue(0<partitionNum);
	}

	@Test
	public void testStringKey()
	{
		key = "ashgdqwpehfcmrmcf,wgnmwehprx,okyefygxnbtxjqyhrxkujyherxmrukzxo";
		int partitionNum = this.defaultPartitioner.partition(key, total_partition);
		assertTrue(partitionNum<total_partition);
	}
	@Test
	public void testWierdStringKey()
	{
		key = "0.+-*/?/~#>>@@::<<,]}[{=+-_0)98(8*7&^%$£!¬`|'\'  ldpvl][}:}{?}{:Q{S/w][D";
		int partitionNum = this.defaultPartitioner.partition(key, total_partition);
		assertTrue(partitionNum<total_partition);
	}
	@Test
	public void testHighNumberKey()
	{
		key = "1234556778908876576897114182121664548448466565656565985964598.23total_partition2468435";
		int partitionNum = this.defaultPartitioner.partition(key, total_partition);
		assertTrue(partitionNum<total_partition);
	}
}
