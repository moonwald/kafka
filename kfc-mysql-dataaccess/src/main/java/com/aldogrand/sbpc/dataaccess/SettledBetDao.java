package com.aldogrand.sbpc.dataaccess;

import com.aldogrand.sbpc.dataaccess.model.SettledBetDto;

/**
 * <p>
 * <b>Title</b> SettledBetDao
 * </p>
 * <p>
 * <b>Description</b> A Data Access Object (DAO) that loads and saves {@link SettledBetDto}s.
 * </p>
 * <p>
 * <b>Company</b> AldoGrand Consultancy Ltd
 * </p>
 * <p>
 * <b>Copyright</b> Copyright (c) 2014
 * </p>
 * @author Dani Garcia
 * @version 1.0
 */

public interface SettledBetDao {

	SettledBetDto getSettledBet(Long betId);

	SettledBetDto saveSettledBet(SettledBetDto settledBetDto);

}
