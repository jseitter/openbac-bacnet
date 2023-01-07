/**
 * 
 */
package io.openbac.bacnet.service;

import java.io.InputStream;
import java.util.HashMap;

import org.junit.BeforeClass;
import org.junit.Test;

import io.netty.buffer.ByteBuf;
import io.openbac.bacnet.exceptions.BACnetParseException;
import io.openbac.bacnet.testutil.BACnetTest;
import io.openbac.bacnet.testutil.TestDataLoader;
import io.openbac.service.unconfirmed.BACnetIAmService;
import io.openbac.service.unconfirmed.BACnetUnconfirmedService;

/**
 * @author J. Seitter
 *
 */
public class BACnetIAmServiceTest extends BACnetTest {

	

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// get the testdata
		InputStream testdata = BACnetIAmServiceTest.class.getClassLoader()
				.getResourceAsStream("io/openbac/bacnet/service/BACnetIAmService.td");
		loader = new TestDataLoader(testdata);
	}

	@Test
	public void testDecode() throws BACnetParseException {

		int size = loader.resultBuffers.size();
		System.out.println("will run "+size+" tests");
		for (int i = 0; i < size; i++) {
			System.out.println("------------ running case # "+i+" -------------");
			ByteBuf buf = loader.resultBuffers.get(i);
			HashMap<String, String> props = loader.resultProps.get(i);

			buf.readByte(); // skip PDU Type
			buf.readByte(); // skip service choice
 		//	BACnetIAmService srv = BACnetUnconfirmedService.create(BACnetIAmService.class, buf);
			
		//	System.out.println(srv.toString());
			
		}
	}
	

	@Test
	public void testEncode() {
	
	}
}
