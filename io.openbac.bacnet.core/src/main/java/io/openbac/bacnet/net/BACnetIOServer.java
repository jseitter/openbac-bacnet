package io.openbac.bacnet.net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.internal.logging.InternalLoggerFactory;
import io.netty.util.internal.logging.Slf4JLoggerFactory;
import io.openbac.bacnet.net.apdu.BACnetAPDUHandler;
import io.openbac.bacnet.net.npdu.BACnetBVLLInboundHandler;
import io.openbac.bacnet.net.npdu.BACnetBVLLOutboundHandler;
import io.openbac.bacnet.net.npdu.BACnetNPDU;
import io.openbac.bacnet.net.npdu.BACnetNPDUDecoder;
import io.openbac.bacnet.net.npdu.BACnetNPDUEncoder;

/**
 * IO Server to handle network communication
 *
 * @author joerg
 *
 */
public class BACnetIOServer {

	private static final Logger LOG = LoggerFactory.getLogger(BACnetIOServer.class);

	public final static int BACNET_DEFAULT_PORT0 = 0xBAC0;
	public final static int BACNET_DEFAULT_PORT1 = 0xBAC1;

	private final int port;
	private final String address;
	private final InetAddress inetAddress;

	/**
	 *
	 * @param address
	 * @param port
	 * @throws java.io.IOException
	 * @throws InterruptedException
	 */
	public BACnetIOServer(String address, int port) throws IOException {

		this.port = port;
		this.address = address;

		try {
			// resolve given ip adderss to InetAdress
			inetAddress = InetAddress.getByName(address);
		} catch (UnknownHostException ex) {
			LOG.error("cannot resolv address: " + address);
			throw new RuntimeException("cannot resolv address: " + address, ex);
		}
		InternalLoggerFactory.setDefaultFactory(Slf4JLoggerFactory.INSTANCE);
	}

	public void run() throws InterruptedException {
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap(); // (2)

			b.group(workerGroup).channel(NioDatagramChannel.class) // (3)
					.handler(new ChannelInitializer<DatagramChannel>() { // (4)
						@Override
						public void initChannel(DatagramChannel ch) throws Exception {
							// handler pipeline reflects the different protocol layers
							ch.pipeline().addLast(new LoggingHandler("BACnet", LogLevel.DEBUG));
							ch.pipeline().addLast(new BACnetBVLLInboundHandler(), new BACnetBVLLOutboundHandler());
							//ch.pipeline().addLast(new BACnetNPDUHandler());
							ch.pipeline().addLast(new BACnetNPDUDecoder(), new BACnetNPDUEncoder());
							ch.pipeline().addLast(new BACnetAPDUHandler());
						}
					}).option(ChannelOption.SO_BROADCAST, true).option(ChannelOption.SO_REUSEADDR, true); // (6)

			// Bind and start to accept incoming connections.
			ChannelFuture f = b.bind(port).sync(); // (7)

			// Wait until the server socket is closed.
			// In this example, this does not happen, but you can do that to gracefully
			// shut down your server.
			f.channel().closeFuture().sync();
		} finally {
			workerGroup.shutdownGracefully();
		}

	}

	/**
	 * send given datagram to target address
	 *
	 * @param datagram
	 * @param targetAddress
	 */
	public void sendDatagram(String datagram, InetSocketAddress targetAddress) {
		/*
		 * IoSession sess = acceptor.newSession(targetAddress, new
		 * InetSocketAddress(inetAddress, port)); //IoBuffer buff =
		 * IoBuffer.wrap(datagram.getBuffer()); WriteFuture wf = sess.write(datagram);
		 * try { wf.await(1000); } catch (InterruptedException ex) {
		 * java.util.logging.Logger.getLogger(BACnetIOServer.class.getName()).log(Level.
		 * SEVERE, null, ex); } if (wf.isWritten()) {
		 * LOG.debug("sendDatagram: written"); } else {
		 * LOG.debug("sendDatagram: not written"); } sess.close(false);
		 */
	}

	public void sendNPDU(BACnetNPDU npdu, InetSocketAddress targetAddress) {
		/*
		 * IoSession sess = acceptor.newSession(targetAddress, new
		 * InetSocketAddress(inetAddress, port)); //IoBuffer buff =
		 * IoBuffer.wrap(datagram.getBuffer()); WriteFuture wf = sess.write(npdu); try {
		 * wf.await(1000); } catch (InterruptedException ex) {
		 * java.util.logging.Logger.getLogger(BACnetIOServer.class.getName()).log(Level.
		 * SEVERE, null, ex); } if (wf.isWritten()) { LOG.debug("sendNPDU: written"); }
		 * else { LOG.debug("sendNPDU: not written"); } sess.close(false);
		 */
	}

}
