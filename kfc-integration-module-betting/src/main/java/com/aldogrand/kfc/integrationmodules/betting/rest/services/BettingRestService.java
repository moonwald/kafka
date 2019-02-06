package com.aldogrand.kfc.integrationmodules.betting.rest.services;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.aldogrand.kfc.integrationmodules.betting.services.BettingKafkaSenderService;

/**
 * REST service endpoint for Betgenius Integration
 * 
 * @author aldogrand
 *
 */
@Path(value="/betgenius")
public class BettingRestService {

	private final Logger logger = Logger.getLogger(getClass());
		
	@Autowired
	public BettingKafkaSenderService kafkaSenderService;
		
	@POST
    @Path("/process-message")
	@Produces({MediaType.TEXT_XML})
    public Response receiveEventManagement(String eventMgntXML){
		try {
			this.kafkaSenderService.sendContent(eventMgntXML, "BETGENIUS", "PROCESS_MESSAGE");
		} catch (Throwable e) {
			logger.error("Unexpected exception processing message {}\"", e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new Error(e.getMessage())).build();
		}
		return Response.ok().entity(eventMgntXML).build();
    }
		
	
	@GET
	@Path("/status")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getStatus() {
		return Response.ok().entity("Betgenius Up").build();
	}

	@POST
	@Path("/heartbeat")
	@Produces({MediaType.TEXT_XML})
	public Response hearbeat() {
		try {
			// TODO
		} catch(Throwable e) {			
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new Error(e.getMessage())).build();
		}
		return Response.ok().build();
	}

	public BettingKafkaSenderService getKafkaSenderService() {
		return kafkaSenderService;
	}


	public void setKafkaSenderService(BettingKafkaSenderService kafkaSenderService) {
		this.kafkaSenderService = kafkaSenderService;
	}
	
}
