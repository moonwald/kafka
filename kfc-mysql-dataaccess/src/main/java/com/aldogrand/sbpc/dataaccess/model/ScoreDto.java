package com.aldogrand.sbpc.dataaccess.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.aldogrand.sbpc.model.ScoreType;

/**
* <p>
* <b>Title</b> ScoreDto
* </p>
* <p>
* <b>Description</b> The score of an event.
* </p>
* <p>
* <b>Company</b> AldoGrand Consultancy Ltd
* </p>
* <p>
* <b>Copyright</b> Copyright (c) 2014
* </p>
* 
* @author Aldo Grand
* @version 1.0
*/
@Entity(name = "Score")
@Table(name = "scores")
public class ScoreDto extends AbstractFetchableDto {

	private static final long serialVersionUID = 7032002284997454887L;

	private SourceEventDto sourceEvent;
	private ScoreType type;
	private Integer home;
	private Integer away;

	/**
	 * Default constructor.
	 */
	public ScoreDto() {
		super();
	}

	public ScoreDto(SourceEventDto sourceEvent, ScoreType type, Integer home, Integer away) {
		this();
		this.sourceEvent = sourceEvent;
		this.type = type;
		this.home = home;
		this.away = away;
	}

	/**
	 * @return the sourceEvent
	 */
	@OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "source_event", nullable = false)
	public SourceEventDto getSourceEvent() {
		return sourceEvent;
	}


	/**
	 * @param sourceEvent the sourceEvent to set
	 */
	public void setSourceEvent(SourceEventDto sourceEvent) {
		this.sourceEvent = sourceEvent;
	}


	/**
	 * @return the type
	 */
	@Column(name = "type", length = 25, nullable = false)
	@Enumerated(EnumType.STRING)
	public ScoreType getType() {
		return type;
	}


	/**
	 * @param type the type to set
	 */
	public void setType(ScoreType type) {
		this.type = type;
	}


	/**
	 * @return the home
	 */
	@Column(name = "home", nullable = false)
	public Integer getHome() {
		return home;
	}


	/**
	 * @param home the home to set
	 */
	public void setHome(Integer home) {
		this.home = home;
	}


	/**
	 * @return the away
	 */
	@Column(name = "away", nullable = false)
	public Integer getAway() {
		return away;
	}


	/**
	 * @param away the away to set
	 */
	public void setAway(Integer away) {
		this.away = away;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return
			new StringBuilder("ScoreDto").append("{")
				.append("id").append(":").append(" ").append(this.id)
				.append(",").append(" ")
				.append("sourceEvent").append(":").append(" ").append(this.sourceEvent)
				.append(",").append(" ")
				.append("type").append(":").append(" ").append(this.type)
				.append(",").append(" ")
				.append("home").append(":").append(" ").append(this.home)
				.append(",").append(" ")
				.append("away").append(":").append(" ").append(this.away)
				.append(",").append(" ")
				.append("creationTime").append(":").append(" ").append(this.creationTime)
				.append(",").append(" ")
				.append("lastFetchTime").append(":").append(" ").append(this.lastFetchTime)
				.append(",").append(" ")
				.append("lastChangeTime").append(":").append(" ").append(this.lastChangeTime)
				.append(",").append(" ")
				.append("version").append(":").append(" ").append(this.version)
			.append("}")
		.toString();
	}

}
