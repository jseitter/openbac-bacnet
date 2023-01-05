/**
 * 
 */
package io.openbac.bacnet.type;

import java.io.InputStream;
import java.util.HashMap;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import io.netty.buffer.ByteBuf;
import io.openbac.bacnet.exceptions.BACnetParseException;
import io.openbac.bacnet.testutil.BACnetTest;
import io.openbac.bacnet.testutil.TestDataLoader;
import io.openbac.bacnet.type.primitive.BACnetBitString;
import io.openbac.bacnet.type.primitive.BACnetPrimitive;

/**
 * @author J. Seitter
 *
 */
public class BACnetBitStringTest extends BACnetTest {

	

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// get the testdata
		InputStream testdata = BACnetBitStringTest.class.getClassLoader()
				.getResourceAsStream("io/openbac/bacnet/type/BACnetBitString.td");
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

			BACnetBitString bitstring = (BACnetBitString) BACnetPrimitive.createPrimitive(BACnetBitString.class, buf);

			System.out.println(bitstring.toDebugString());
			System.out.println("value: " + bitstring.toString());

			if (props.get("class").equals("true"))
				Assert.assertTrue(bitstring.tagClass);
			if (props.get("class").equals("false"))
				Assert.assertFalse(bitstring.tagClass);

			
			Assert.assertEquals(Integer.valueOf(bitstring.tagNumber), Integer.decode(props.get("tag")));
		}
	}
	

	@Test
	public void testEncode() {
		
	}
}
