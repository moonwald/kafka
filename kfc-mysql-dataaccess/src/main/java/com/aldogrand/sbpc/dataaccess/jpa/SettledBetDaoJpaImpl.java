package com.aldogrand.sbpc.dataaccess.jpa;

import com.aldogrand.sbpc.dataaccess.SettledBetDao;
import com.aldogrand.sbpc.dataaccess.model.SettledBetDto;
import com.aldogrand.kfc.utils.database.GenericJpaDao;

/**
 * <p>
 * <b>Title</b> SettledBetDaoJpaImpl
 * </p>
 * <p>
 * <b>Description</b> JPA implementation of {@link SettledBetDao}.
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

public class SettledBetDaoJpaImpl extends GenericJpaDao<SettledBetDto> implements SettledBetDao {

	public SettledBetDaoJpaImpl(){
		super(SettledBetDto.class);
	}
	
	public SettledBetDaoJpaImpl(Class<SettledBetDto> type) {
		super(type);
	}
	
	@Override
	public SettledBetDto getSettledBet(Long betId) {
		return executeQuery("from SettledBet sb where sb.bet.id = ?1 ", betId);
	}

	@Override
	public SettledBetDto saveSettledBet(SettledBetDto settledBetDto) {
		return saveEntity(settledBetDto);
	}

}
