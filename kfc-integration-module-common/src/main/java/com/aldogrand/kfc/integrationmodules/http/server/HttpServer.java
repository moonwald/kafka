package com.aldogrand.kfc.integrationmodules.http.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.aldogrand.kfc.integrationmodules.http.server.utils.NamedThreadFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpContentDecompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

/**
 * <p>
 * <b>Title</b> HttpServer
 * </p>
 * <p>
 * <b>Description</b> A Netty based simple HTTP Server. The actual HTTP request processing
 * is handled by the {@link AbstractHttpRequestHandler} implementation.
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
public class HttpServer
{
    private static final int ACCEPTOR_THREADS_DEFAULT = 1;
    private static final int WORKER_THREADS_DEFAULT = 2;
    
    private final Logger logger = LogManager.getLogger(getClass());
    
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private Channel serverChannel;
    
    private String host;
    private int port;
    private AbstractHttpRequestHandler httpRequestHandler;
    private int acceptorThreads = ACCEPTOR_THREADS_DEFAULT;
    private int workerThreads = WORKER_THREADS_DEFAULT;
    
    /**
     * @return the host
     */
    public String getHost()
    {
        return host;
    }

    /**
     * @param host the host to set
     */
    public void setHost(String host)
    {
        this.host = host;
    }

    /**
     * @return the port
     */
    public int getPort()
    {
        return port;
    }

    /**
     * @param port the port to set
     */
    public void setPort(int port)
    {
        this.port = port;
    }

    /**
     * @return the httpRequestHandler
     */
    public AbstractHttpRequestHandler getHttpRequestHandler()
    {
        return httpRequestHandler;
    }

    /**
     * @param httpRequestHandler the httpRequestHandler to set
     */
    public void setHttpRequestHandler(AbstractHttpRequestHandler httpRequestHandler)
    {
        this.httpRequestHandler = httpRequestHandler;
    }

    /**
     * @return the acceptorThreads
     */
    public int getAcceptorThreads()
    {
        return acceptorThreads;
    }

    /**
     * @param acceptorThreads the acceptorThreads to set
     */
    public void setAcceptorThreads(int acceptorThreads)
    {
        this.acceptorThreads = acceptorThreads;
    }

    /**
     * @return the workerThreads
     */
    public int getWorkerThreads()
    {
        return workerThreads;
    }

    /**
     * @param workerThreads the workerThreads to set
     */
    public void setWorkerThreads(int workerThreads)
    {
        this.workerThreads = workerThreads;
    }

    /**
     * Start the HTTP server.
     * @throws InterruptedException
     */
    public void start() throws InterruptedException
    {
        logger.info("Starting Netty HTTP Server listening on port {}.", port);
        
        bossGroup = new NioEventLoopGroup(acceptorThreads,new NamedThreadFactory(Thread.NORM_PRIORITY)) ;
        workerGroup = new NioEventLoopGroup(workerThreads, new NamedThreadFactory(Thread.MAX_PRIORITY));
        
        

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
            .childHandler(new ChannelInitializer<SocketChannel>() 
            {
                @Override
                public void initChannel(SocketChannel channel) throws Exception 
                {
                    ChannelPipeline pipeline = channel.pipeline();
                    pipeline.addLast("decoder", new HttpRequestDecoder(4096, 8192, 8192));
                    pipeline.addLast("encoder", new HttpResponseEncoder());
                    pipeline.addLast("decompressor", new HttpContentDecompressor());
                    pipeline.addLast("compressor", new HttpContentCompressor());
                    pipeline.addLast("aggregator", new HttpObjectAggregator(1024 * 1024));
                    pipeline.addLast("handler", httpRequestHandler);
                }
            })
            .option(ChannelOption.SO_BACKLOG, 1024)
            .option(ChannelOption.TCP_NODELAY, true);

        // Bind and start to accept incoming connections.
        ChannelFuture bindFuture = (host != null ? bootstrap.bind(host, port) : bootstrap.bind(port)).sync();
        serverChannel = bindFuture.channel();
    }
    
    /**
     * Stop the HTTP server.
     * @throws InterruptedException
     */
    public void stop() throws InterruptedException
    {
        logger.info("Stopping Netty HTTP Server listening on port {}.", port);
        
        if (serverChannel != null)
        {
            serverChannel.close().sync();
        }
        if (workerGroup != null)
        {
            workerGroup.shutdownGracefully().sync();
        }
        if (bossGroup != null)
        {
            bossGroup.shutdownGracefully().sync();
        }
    }
}
