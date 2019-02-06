package com.aldogrand.kfc.integrationmodules.betting.http.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.aldogrand.kfc.integrationmodules.Message;
import com.aldogrand.kfc.integrationmodules.betting.heartbeat.BettingHeartbeatService;
import com.aldogrand.kfc.integrationmodules.betting.msg.interfaces.BettingKeyGenerator;
import com.aldogrand.kfc.integrationmodules.betting.services.BettingKafkaSenderService;
import com.aldogrand.kfc.integrationmodules.http.server.AbstractHttpRequestHandler;

/**
 * 
 * <p>
 * <b>Title</b> BetgeniusHttpRequestHandler
 * </p>
 * <p>
 * <b>Description</b>
 * </p>
 * <p>
 * <b>Company</b> AldoGrand Consultancy Ltd
 * </p>
 * <p>
 * <b>Copyright</b> Copyright (c) 2015
 * </p>
 * @author Aldo Grand
 *
 */
public class BettingHttpRequestHandler extends AbstractHttpRequestHandler {

	// End points
	private static final String STATUS_URI = "/betgenius/status";
	private static final String PROCESS_MESSAGE_URI = "/betgenius/process-message";
	private static final String HEARTBEAT_URI = "/betgenius/heartbeat";
	
	// Content type
	private static final String BODY_TYPE_HEADER_NAME = "Content-Type";
    private static final String CONTENT_TYPE_LONG = "text/xml; charset=UTF-8";
    private static final String CONTENT_TYPE_SHORT = "text/xml";
    
    // Constants
    private static final String RESPONSE_STATUS = "Betgenius interfaces Up";    
    private static final String PROCESS_MESSAGE_KEY = "PROCESS_MESSAGE";
    private static final String DEFAULT_TOPIC_NAME = "BETGENIUS_UPDATEGRAM_IN";
        
    private ObjectMapper objectMapper;    
    private String moduleTopicName;    
    private String heartbeatTopicName;
    
    @Autowired
	public BettingKafkaSenderService kafkaSenderService;
    
    @Autowired
    private BettingHeartbeatService heartbeatService;
    
    @Autowired
    private BettingKeyGenerator betgeniusKeyGenerator;
    
    protected Logger logger = LogManager.getLogger(getClass());
      
    /**
     * Handle the HTTP GET request.
     * @param context
     * @param request
     * @throws Exception
     */
    protected void handleGetRequest(ChannelHandlerContext context, FullHttpRequest request) throws Exception {
		String uri = request.getUri();
		
		if (uri.equals(STATUS_URI)) {
			this.handleStatusRequest(context, request);
		} else {
			logger.debug("Operation end point " + uri + " not found.");

			closeWithResponseStatus(context, HttpResponseStatus.NOT_FOUND);
		}
    }
    
    /**
     * Handle Status Request 
     * @param context
     * @param request
     */
    private void handleStatusRequest(ChannelHandlerContext context, FullHttpRequest request) {    	
		try {			
			FullHttpResponse response = createHttpResponse(HttpResponseStatus.OK, RESPONSE_STATUS);
            if (HttpHeaders.isKeepAlive(request)) {
                 response.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
                 context.writeAndFlush(response); // leave connection open
                 
			} else {
				response.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.CLOSE);
				context.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE); // close the connection
			}
		} catch (IOException e) {
			closeWithResponseStatus(context, HttpResponseStatus.INTERNAL_SERVER_ERROR);
		}		
	}

	/**
     * Handle the HTTP POST request.
     * @param context
     * @param request
     * @throws Exception
     */
    protected void handlePostRequest(ChannelHandlerContext context, FullHttpRequest request) throws Exception {		
    	String uri = request.getUri();
		if (uri.equals(PROCESS_MESSAGE_URI)) {					
			this.handleProcessRequest(context, request);			
		} else if (uri.equals(HEARTBEAT_URI)) {
			this.handleHeartbeatRequest(context, request);
		} else {
			logger.debug("Operation end point " + uri + " not found.");

			closeWithResponseStatus(context, HttpResponseStatus.NOT_FOUND);
		}
    }
    
    /**
     * Handle process message request from Betgenius. 
     * @param context
     * @param request
     */
	private void handleProcessRequest(ChannelHandlerContext context, FullHttpRequest request) {
		        
		try {
			Object object = deserializeRequestContent(context, request);
	        if (object == null) // response is already sent in deserializeRequestContent 
	        { 
	            return;
	        }

	        if (!(object instanceof String)) 
	        {
	            logger.debug("Invalid body content. All command requests should contain a valid XML representation, not {}.", object);
	            closeWithBody(context, HttpResponseStatus.BAD_REQUEST, Message.Key.REQUEST_BODY_INVALID_CONTENT);
	            return;
	        }
	        
	        String content = (String) object;
	        String key = betgeniusKeyGenerator.generateKey(content, kafkaSenderService.getBetgeniusContentType());
			kafkaSenderService.sendContent(content, 
						moduleTopicName != null? moduleTopicName : DEFAULT_TOPIC_NAME, 
						key != null? key : PROCESS_MESSAGE_KEY);
			
            FullHttpResponse response = createHttpResponse(HttpResponseStatus.OK, request.content());
            if (HttpHeaders.isKeepAlive(request)) {
                response.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
                context.writeAndFlush(response); // leave connection open
                
			} else {
				response.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.CLOSE);
				context.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE); // close the connection
			}
		} catch (IOException e) {
			closeWithResponseStatus(context, HttpResponseStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
    
	/**
	 * Handle Betgenius heartbeat
	 * @param context
	 * @param request
	 */
    private void handleHeartbeatRequest(ChannelHandlerContext context, FullHttpRequest request) {
    	try {
    		heartbeatService.receiveBeat();
    		
            FullHttpResponse response = createHttpResponse(HttpResponseStatus.OK, request.content());
            if (HttpHeaders.isKeepAlive(request)) {
                response.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
                context.writeAndFlush(response); // leave connection open
                
			} else {
				response.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.CLOSE);
				context.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE); // close the connection
			}
		} catch (IOException e) {
			closeWithResponseStatus(context, HttpResponseStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
    

	/**
     * Create a {@link FullHttpResponse} with the given status containing the
     * content serialized as JSON.
     * @param status
     * @param content
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    private FullHttpResponse createHttpResponse(HttpResponseStatus status, Object content) 
    		throws JsonGenerationException, JsonMappingException, IOException {    
    	
    	ByteBuf responseBuf = Unpooled.buffer();
        ByteBufOutputStream bbos = null;
        bbos = new ByteBufOutputStream(responseBuf);            
        objectMapper.writeValue(bbos, content);
        bbos.flush();
        
        try {
	    	if (content != null) {
	            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status, responseBuf);
	            response.headers().set(HttpHeaders.Names.CONTENT_TYPE, CONTENT_TYPE_LONG);
	            response.headers().set(HttpHeaders.Names.CONTENT_LENGTH, responseBuf.readableBytes());
	            response.headers().set(BODY_TYPE_HEADER_NAME, content.getClass().getName());
	            return response;
				
	    	} else {
	    		FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status);
	    		response.headers().set(HttpHeaders.Names.CONTENT_TYPE, CONTENT_TYPE_LONG);
	            response.headers().set(HttpHeaders.Names.CONTENT_LENGTH, responseBuf.readableBytes());
	            return response;
	    	}
        } finally {
			if (bbos != null) {
				try {
					bbos.close();
				} catch (IOException e) {
					// NoOp
				}
			}
        }
    }    

    /**
     * Send an error response and close the connection.
     * @param context
     * @param reason
     */
    protected void closeWithResponseStatus(ChannelHandlerContext context, HttpResponseStatus reason)
    {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, reason, Unpooled.EMPTY_BUFFER, false);
        response.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.CLOSE);
        context.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }	

    /**
     * Deserialize the request body content as String encoded in UTF_8
     * 
     * @param context ChannelHandlerContext
     * @param request FullHttpRequest
     * @return Body content de-serialized
     * @throws IOException
     */
    private Object deserializeRequestContent(ChannelHandlerContext context, FullHttpRequest request) throws IOException 
    {
        String typeName = request.headers().get(BODY_TYPE_HEADER_NAME);
        if (typeName == null) 
        {
            logger.debug("No body type header. All requests should contain the header \"Content-Type\".");
            closeWithBody(context, HttpResponseStatus.BAD_REQUEST, Message.Key.REQUEST_CONTENT_TYPE_INVALID);
            return null;
        }
        
        if (!typeName.equalsIgnoreCase(CONTENT_TYPE_SHORT)) 
        {
            logger.error("Cannot read content. Content-Type isn't right.", typeName);
            closeWithBody(context, HttpResponseStatus.BAD_REQUEST, null);
            return null;            
        }

        ByteBuf requestBuf = request.content();
        if (requestBuf.readableBytes() == 0) // no content
        {
            logger.debug("Invalid request. Request contains no content. Requests should contain a valid XML request object.");            
            closeWithBody(context, HttpResponseStatus.NO_CONTENT, Message.Key.REQUEST_BODY_NO_CONTENT);
            return null;
        }         
        return requestBuf.toString(CharsetUtil.UTF_8);       
    }    

    /**
     * Create a response with the body.
     * @param context HTTP context
     * @param responseStatus HTTP status
     * @param payload Body
     * @throws IOException
     */
    protected void closeWithBody(ChannelHandlerContext context, HttpResponseStatus responseStatus, Object payload) throws IOException 
    {
        FullHttpResponse responseBody = createHttpResponse(responseStatus, payload);
        responseBody.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.CLOSE);
        context.writeAndFlush(responseBody).addListener(ChannelFutureListener.CLOSE);
    }
	
	public ObjectMapper getObjectMapper() {
		return objectMapper;
	}

	public void setObjectMapper(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	public String getModuleTopicName() {
		return moduleTopicName;
	}

	public void setModuleTopicName(String moduleTopicName) {
		this.moduleTopicName = moduleTopicName;
	}

	public String getHeartbeatTopicName() {
		return heartbeatTopicName;
	}

	public void setHeartbeatTopicName(String heartbeatTopicName) {
		this.heartbeatTopicName = heartbeatTopicName;
	}

	public BettingKafkaSenderService getKafkaSenderService() {
		return kafkaSenderService;
	}

	public void setKafkaSenderService(BettingKafkaSenderService kafkaSenderService) {
		this.kafkaSenderService = kafkaSenderService;
	}

	public BettingHeartbeatService getHeartbeatService() {
		return heartbeatService;
	}

	public void setHeartbeatService(BettingHeartbeatService heartbeatService) {
		this.heartbeatService = heartbeatService;
	}

  public BettingKeyGenerator getBetgeniusKeyGenerator() {
    return betgeniusKeyGenerator;
  }

  public void setBetgeniusKeyGenerator(BettingKeyGenerator betgeniusKeyGenerator) {
    this.betgeniusKeyGenerator = betgeniusKeyGenerator;
  }

}
