package com.aldogrand.sbpc.dataaccess.jpa;

import com.aldogrand.sbpc.dataaccess.ReportJobInfoDao;
import com.aldogrand.sbpc.dataaccess.model.ReportJobInfoDto;
import com.aldogrand.kfc.utils.database.GenericJpaDao;

/**
 * <p>
 * <b>Title</b> ReportJobInfoDaoJpaImpl
 * </p>
 * <p>
 * <b>Description</b> JPA implementation of {@link ReportJobInfoDao}.
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

public class ReportJobInfoDaoJpaImpl extends GenericJpaDao<ReportJobInfoDto>
		implements ReportJobInfoDao {
	
	public ReportJobInfoDaoJpaImpl(){
		super(ReportJobInfoDto.class);
	}

	public ReportJobInfoDaoJpaImpl(Class<ReportJobInfoDto> type) {
		super(type);
	}

	@Override
	public ReportJobInfoDto getReportJobInfo(String jobName, long accountId,
			long connectorId) {
		
		return executeQuery("from ReportJobInfo rji where rji.jobName = ?1 and rji.accountId = ?2 and rji.connectorId = ?3", 
				jobName, accountId, connectorId);
	}
	
	@Override
    public ReportJobInfoDto saveReportJobInfo(ReportJobInfoDto reportJobInfoDto)
    {
        return saveEntity(reportJobInfoDto);
    }

}
