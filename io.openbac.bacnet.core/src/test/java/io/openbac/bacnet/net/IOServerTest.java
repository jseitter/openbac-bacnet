package io.openbac.bacnet.net;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class IOServerTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void test() throws IOException, InterruptedException {
		
		BACnetIOServer srv = new BACnetIOServer("192.168.60.26", BACnetIOServer.BACNET_DEFAULT_PORT0);
		//srv.run();
		
	}

}
 