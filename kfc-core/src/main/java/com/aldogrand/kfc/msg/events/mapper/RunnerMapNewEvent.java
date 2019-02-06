/**
 * 
 */
package com.aldogrand.kfc.msg.events.mapper;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.aldogrand.sbpc.model.Runner;
import com.aldogrand.sbpc.model.SourceRunner;
import com.aldogrand.kfc.annotation.ContentType;
import com.aldogrand.kfc.msg.EventContentType;
import com.aldogrand.kfc.msg.events.KFCEvent;

/**
 * <p>
 * <b>Title</b> RunnerMapNewEvent.java
 * </p>
 * <p>
 * <b>Description</b> kfc-core.
 * </p>
 * <p>
 * <b>Company</b> AldoGrand Consultancy Ltd
 * </p>
 * <p>
 * <b>Copyright</b> Copyright (c) 2014
 * </p>
 * @author aldogrand
 * @version 1.0
 */
@ContentType(EventContentType.RUNNER_MAP_NEW)
public class RunnerMapNewEvent extends KFCEvent {

	/**
	 * runner of type Runner
	 */
	private Runner runner;

	/**
	 * sourceRunner of type SourceRunner
	 */
	private SourceRunner sourceRunner;

	/**
	 * @return the runner
	 */
	public Runner getRunner() {
		return runner;
	}

	/**
	 * @param runner
	 *            the runner to set
	 */
	public void setRunner(Runner runner) {
		this.runner = runner;
	}

	/**
	 * @return the sourceRunner
	 */
	public SourceRunner getSourceRunner() {
		return sourceRunner;
	}

	/**
	 * @param sourceRunner
	 *            the sourceRunner to set
	 */
	public void setSourceRunner(SourceRunner sourceRunner) {
		this.sourceRunner = sourceRunner;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, false);
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, false);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
