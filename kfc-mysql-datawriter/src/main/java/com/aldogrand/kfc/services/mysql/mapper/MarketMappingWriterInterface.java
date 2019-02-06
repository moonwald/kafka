/**
 * 
 */
package com.aldogrand.kfc.services.mysql.mapper;

import com.aldogrand.sbpc.model.Market;
import com.aldogrand.sbpc.model.SourceMarket;

/**
 * <p>
 * <b>Title</b> MarketMappingWriterInterface.java
 * </p>
 * <pre>
 * This service has two functions and only deals with the Market mapping.
 * {@link #createMarket(SourceMarket, Market)}
 * 		1. Create new market for the source market received and do the mapping
 * {@link #updateMarket(SourceMarket, Market)}
 * 		2. Map the source market with the target market.
 * </pre>
 * 
 * <b>Description</b> kfc-mysql-datawriter. </p>
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
public interface MarketMappingWriterInterface
{

	/**
	 * Map the source market with the target market.
	 * 
	 * @param sourceMarket
	 *            to be mapped with market
	 * @param market
	 *            existing to be mapped
	 * @return Market after mapping
	 */
	public Market updateMarket(SourceMarket sourceMarket, Market market);

	/**
	 * Create new market for the source market received and do the mapping
	 * 
	 * @param sourceMarket
	 *            to be mapped with market
	 * @param market
	 *            to be created and mapped
	 * @return Market after mapping
	 */
	public Market createMarket(SourceMarket sourceMarket, Market market);
}