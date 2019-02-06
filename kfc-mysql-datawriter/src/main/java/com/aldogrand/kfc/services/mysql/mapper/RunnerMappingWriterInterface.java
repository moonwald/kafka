/**
 * 
 */
package com.aldogrand.kfc.services.mysql.mapper;

import com.aldogrand.sbpc.model.Runner;
import com.aldogrand.sbpc.model.SourceRunner;

/**
 * <p>
 * <b>Title</b> RunnerMappingWriterInterface.java
 * </p>
 * 
 * <pre>
 * This service has two functions and only deals with the Market mapping.
 * {@link #createRunner(SourceRunner, Runner)}
 * 		1. Create new market for the source market received and do the mapping
 * {@link #updateRunner(SourceRunner, Runner)}
 * 		2. Map the source market with the target market.
 * </pre>
 * <p>
 * <b>Description</b> kfc-mysql-datawriter.
 * </p>
 * <p>
 * <b>Company</b> AldoGrand Consultancy Ltd
 * </p>
 * <p>
 * <b>Copyright</b> Copyright (c) 2014
 * </p>
 * 
 * @author aldogrand
 * @version 1.0
 */
public interface RunnerMappingWriterInterface
{

	/**
	 * @param sourceRunner
	 *            to be mapped with runner
	 * @param runner
	 *            existing runner to be mapped
	 * @return Runner after mapping
	 */
	public Runner updateRunner(SourceRunner sourceRunner, Runner runner);

	/**
	 * @param sourceRunner
	 *            to be mapped
	 * @param runner
	 *            to be created and mapped
	 * @return Runner after mapping
	 */
	public Runner createRunner(SourceRunner sourceRunner, Runner runner);

}