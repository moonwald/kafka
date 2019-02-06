package com.aldogrand.sbpc.dataaccess.jpa;
        
import java.util.List;

import com.aldogrand.sbpc.dataaccess.SourceEventParticipantDao;
import com.aldogrand.sbpc.dataaccess.model.SourceEventParticipantDto;
import com.aldogrand.sbpc.model.PositionType;
import com.aldogrand.kfc.utils.database.GenericJpaDao;

/**
 * Implementation of {@link SourceEventParticipantDao}
 * for database interaction
 * 
 * @author aldogrand
 *
 */
public class SourceEventParticipantDaoJpaImpl extends GenericJpaDao<SourceEventParticipantDto> implements SourceEventParticipantDao {

	public SourceEventParticipantDaoJpaImpl() {
		super(SourceEventParticipantDto.class);
	}

	@Override
	public SourceEventParticipantDto getParticipant(long id) {
		return getEntity(id);
	}
	
	@Override
	public List<SourceEventParticipantDto> getTeamBySourceEvent(long eventId) {
		return executeQuery("from SourceEventParticipant sep where sep.source_event_id = ? and sep.participant_type in ('HOME', 'AWAY') ",
                0, null, eventId);			
	}

	@Override
	public SourceEventParticipantDto getParticipantByEventAndType(long eventId,
			PositionType posType) {
		return null;
	}

	@Override
	public SourceEventParticipantDto saveParticipant(SourceEventParticipantDto participant) {
		return saveEntity(participant);
	}


}
