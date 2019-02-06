package com.aldogrand.sbpc.dataaccess.model;

import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * <p>
 * <b>Title</b> ReportJobInfoDto
 * </p>
 * <p>
 * <b>Description</b> Info about report jobs.
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
@Entity(name = "ReportJobInfo")
@Table(name = "report_job_info")
@Cacheable(false)
public class ReportJobInfoDto extends AbstractDto {

	private static final long serialVersionUID = -3250236823891568267L;
	
	private String jobName;
	private Long accountId;
	private String accountName;
	private Long connectorId;
	private String connectorName;
	private Date lastUpdate = new Date();
	private String jobStatus;
	
	@Column(name = "job_name", length = 100, nullable = false)
	public String getJobName() {
		return jobName;
	}
	
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	
	@Column(name = "account_id", nullable = false)
	public Long getAccountId() {
		return accountId;
	}
	
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	
	@Column(name = "account_name", length = 45, nullable = false)
	public String getAccountName() {
		return accountName;
	}
	
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
	@Column(name = "connector_id", nullable = false)
	public Long getConnectorId() {
		return connectorId;
	}
	
	public void setConnectorId(Long connectorId) {
		this.connectorId = connectorId;
	}
	
	@Column(name = "connector_name", length = 45, nullable = false)
	public String getConnectorName() {
		return connectorName;
	}
	
	public void setConnectorName(String connectorName) {
		this.connectorName = connectorName;
	}
	
	@Column(name = "last_update", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
	public Date getLastUpdate() {
		return lastUpdate;
	}
	
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
	@Column(name = "job_status", length = 10, nullable = false)
	public String getJobStatus() {
		return jobStatus;
	}
	
	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}


}
