package com.aldogrand.sbpc.dataaccess;

import java.util.List;

import com.aldogrand.sbpc.dataaccess.model.SourceEventParticipantDto;
import com.aldogrand.sbpc.model.PositionType;

/**
 * Interface for Data Access Layer of 
 * {@link SourceEventParticipantDto}
 * 
 * @author aldogrand
 *
 */
public interface SourceEventParticipantDao {

	/**
     * Retrieve the {@link SourceEventParticipantDto} with the given id.
     * 
     * @param id
     * @return
     */
	SourceEventParticipantDto getParticipant(long id);
	
	
	/**
	 * Retrieve the {@link SourceEventParticipantDto} for the given source event
	 * where it is a team i.e. has a Participant Type
	 * 
	 * @param sourceEventId
	 * @return
	 */
	List<SourceEventParticipantDto> getTeamBySourceEvent(long sourceEventId);
	
	/**
	 * Retrieve the {@link SourceEventParticipantDto} for the given event and type
	 * @param eventId
	 * @param posType
	 * @return
	 */
	SourceEventParticipantDto getParticipantByEventAndType(long eventId, PositionType posType);
    
    /**
     * Save the {@link SourceEventParticipantDto} returning the updated version.
     * 
     * @param participant
     * @return
     */
	SourceEventParticipantDto saveParticipant(SourceEventParticipantDto participant);
}
