/**
 * 
 */
package com.aldogrand.kfc.msg.events.mapped;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;

import com.aldogrand.sbpc.model.Runner;
import com.aldogrand.kfc.annotation.ContentType;
import com.aldogrand.kfc.msg.EventContentType;
import com.aldogrand.kfc.msg.events.KFCEvent;

/**
 * <p>
 * <b>Title</b> RunnerMappedEvent.java
 * </p>
 * com.aldogrand.kfc.msg.events.writer.mapped
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
@ContentType(EventContentType.RUNNER_MAPPED)
public class RunnerMappedEvent extends KFCEvent {

	/**
	 * runner of type Runner
	 */
	@JsonProperty("body")
	private Runner runner;

	/**
	 * @return the runner
	 */
	public Runner getRunner() {
		return runner;
	}

	/**
	 * @param runner the runner to set
	 */
	public void setRunner(Runner runner) {
		this.runner = runner;
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
