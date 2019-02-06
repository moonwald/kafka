package com.aldogrand.sbpc.dataaccess;

import java.util.Date;

import com.aldogrand.sbpc.dataaccess.model.SourceEventIncidentDto;
import com.aldogrand.sbpc.model.EventPhase;
import com.aldogrand.sbpc.model.IncidentType;

/**
 * Interface for Data Access Layer of 
 * {@link SourceEventIncidentDto}
 * 
 * @author aldogrand
 *
 */
public interface SourceEventIncidentDao {


	/**
	 * Retrieve the {@link SourceEventIncidentDto} for the given
	 * primary key
	 * 
	 * @param id
	 * @return
	 */
	SourceEventIncidentDto getIncident(long id);
	
	/**
	 * Retrieve the {@link SourceEventIncidentDto} for the given event, time and type
	 * 
	 * @param sequencePhase
	 * @param sequenceNumber
	 * @param type
	 * @param participantId
	 * @param elapsedTime
	 * @return
	 */
	SourceEventIncidentDto getIncident(EventPhase eventPhase, long sequenceNumber, IncidentType type, long participantId, Date elapsedTime);
	
    /**
     * Save the {@link SourceEventIncidentDto} returning the updated version
     * 
     * @param incident
     * @return
     */
	SourceEventIncidentDto saveEventIncident(SourceEventIncidentDto incident);

	/**
	 * 
	 * @param eventId
	 * @param sequenceNumber
	 * @param elapsedTime
	 */
	boolean validateIncidentSequence(long eventId, long sequenceNumber,
			Date elapsedTime);
	
	
	/**
	 * Retrieve the most recent {@link SourceEventIncidentDto} received
	 * 
	 * @param eventId
	 * @return
	 */
	SourceEventIncidentDto getlatestEventIncidentReceivedForEvent(long eventId);
	
	/**
	 * 
	 * @param eventId
	 * @param sequencePhase
	 * @return
	 */
	long getlatestMatchIncidentSequenceReceivedForEvent(long eventId, long sequencePhase);
}
