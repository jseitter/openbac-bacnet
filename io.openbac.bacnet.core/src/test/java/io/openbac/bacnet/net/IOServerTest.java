package io.openbac.bacnet.net;

import java.io.IOException;

public class IOServerTest {

	public static void main(String[] args) throws IOException, InterruptedException {

		BACnetIOServer srv = new BACnetIOServer("192.168.60.26", BACnetIOServer.BACNET_DEFAULT_PORT0);
		srv.run();

	}
}
