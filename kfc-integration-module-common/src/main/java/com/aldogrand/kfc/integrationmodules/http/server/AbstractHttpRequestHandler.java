package com.aldogrand.kfc.integrationmodules.http.server;

import io.netty.channel.ChannelHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

/**
 * <p>
 * <b>Title</b> AbstractHttpRequestHandler
 * </p>
 * <p>
 * <b>Description</b> 
 * </p>
 * <p>
 * <b>Company</b> AldoGrand Consultancy Ltd
 * </p>
 * <p>
 * <b>Copyright</b> Copyright (c) 2014
 * </p>
 * @author Aldo Grand
 * @version 1.0
 */
@ChannelHandler.Sharable
public abstract class AbstractHttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest>
{
    protected Logger logger = LogManager.getLogger(getClass());

    /* (non-Javadoc)
     * @see io.netty.channel.SimpleChannelInboundHandler#channelRead0(io.netty.channel.ChannelHandlerContext, java.lang.Object)
     */
    @Override
    protected final void channelRead0(ChannelHandlerContext context, FullHttpRequest request) throws Exception
    {
        handleHttpRequest(context, request);
    }

    /**
     * Handle the HTTP request.
     * @param context
     * @param request
     * @throws Exception
     */
    private void handleHttpRequest(ChannelHandlerContext context, FullHttpRequest request) throws Exception
    {
        HttpMethod method = request.getMethod();

        try
        {
            handleHttpRequest(context, method, request);
        }
        catch (Exception e)
        {
            logger.error("Error handling the HTTP request.", e);

            closeWithResponseStatus(context, HttpResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Handle the HTTP request.
     * @param context
     * @param method
     * @param request
     * @throws Exception
     */
    private void handleHttpRequest(ChannelHandlerContext context, HttpMethod method, FullHttpRequest request) throws Exception
    {
        if (HttpMethod.GET.equals(method))
        {
            handleGetRequest(context, request);
        }
        else if (HttpMethod.POST.equals(method))
        {
            handlePostRequest(context, request);
        }
        else if (HttpMethod.PUT.equals(method))
        {
            handlePutRequest(context, request);
        }
        else if (HttpMethod.DELETE.equals(method))
        {
            handleDeleteRequest(context, request);
        }
        else
        {
            closeWithResponseStatus(context, HttpResponseStatus.NOT_IMPLEMENTED);
        }        
    }

    /**
     * Handle the HTTP GET request.
     * @param context
     * @param request
     * @throws Exception
     */
    protected void handleGetRequest(ChannelHandlerContext context, FullHttpRequest request) throws Exception
    {
        closeWithResponseStatus(context, HttpResponseStatus.NOT_IMPLEMENTED);
    }
    
    /**
     * Handle the HTTP POST request.
     * @param context
     * @param request
     * @throws Exception
     */
    protected void handlePostRequest(ChannelHandlerContext context, FullHttpRequest request) throws Exception
    {
        closeWithResponseStatus(context, HttpResponseStatus.NOT_IMPLEMENTED);
    }

    /**
     * Handle the HTTP PUT request.
     * @param context
     * @param request
     * @throws Exception
     */
    protected void handlePutRequest(ChannelHandlerContext context, FullHttpRequest request) throws Exception
    {
        closeWithResponseStatus(context, HttpResponseStatus.NOT_IMPLEMENTED);
    }

    /**
     * Handle the HTTP DELETE request.
     * @param context
     * @param request
     * @throws Exception
     */
    protected void handleDeleteRequest(ChannelHandlerContext context, FullHttpRequest request) throws Exception
    {
        closeWithResponseStatus(context, HttpResponseStatus.NOT_IMPLEMENTED);
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

    /* (non-Javadoc)
     * @see io.netty.channel.ChannelInboundHandlerAdapter#exceptionCaught(io.netty.channel.ChannelHandlerContext, java.lang.Throwable)
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception 
    {
        super.exceptionCaught(ctx, cause);
        
        logger.debug("Exception caught in Netty pipeline", cause);
    }
}
