package com.aldogrand.sbpc.dataaccess;

import com.aldogrand.sbpc.dataaccess.model.ReportJobInfoDto;

/**
 * <p>
 * <b>Title</b> ReportJobInfoDao
 * </p>
 * <p>
 * <b>Description</b> A Data Access Object (DAO) that loads and saves {@link ReportJobInfoDto}s.
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

public interface ReportJobInfoDao {

	ReportJobInfoDto getReportJobInfo(String jobName, long accountId, long connectorId);
	
	ReportJobInfoDto saveReportJobInfo(ReportJobInfoDto reportJobInfoDto);
}
