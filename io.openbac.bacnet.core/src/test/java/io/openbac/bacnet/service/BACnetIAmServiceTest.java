/**
 * 
 */
package io.openbac.bacnet.service;

import java.io.InputStream;
import java.util.HashMap;

import org.junit.Assert;
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
		init("io/openbac/bacnet/service/BACnetIAmService.td");
	}

	@Test
	public void testDecode() throws BACnetParseException {

		int size = loader.resultBuffers.size();
		System.out.println("will run " + size + " tests");
		for (int i = 0; i < size; i++) {
			System.out.println("------------ running case # " + i + " -------------");
			ByteBuf buf = loader.resultBuffers.get(i);
			HashMap<String, String> props = loader.resultProps.get(i);

			Assert.assertEquals(Long.valueOf(props.get("pdutype")).longValue(), (int) (buf.readByte() >> 4) & 0xFF); // PDU
																														// Type
			Assert.assertEquals(Long.valueOf(props.get("choice")).longValue(), (int) buf.readByte()); // service choice

			BACnetIAmService srv = BACnetUnconfirmedService.create(BACnetIAmService.class, buf);

			System.out.println(srv.toString());

			Assert.assertEquals(Long.decode(props.get("objinstance")).longValue(),
					srv.getObjectIdentifier().getInstance());
			Assert.assertEquals(Long.decode(props.get("objtype")).longValue(),
					srv.getObjectIdentifier().getObjectTypeInt());
			Assert.assertEquals(Long.decode(props.get("maxapdu")).longValue(),
					srv.getMaxAPDULengthAccepted().intValue());
			Assert.assertEquals(Long.decode(props.get("segmentation")).longValue(),
					srv.getSegmentationSupported().intValue());
			Assert.assertEquals(Long.decode(props.get("vendor")).longValue(), srv.getVendorID().intValue());
		}
	}

	@Test
	public void testEncode() {

	}
}
