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
import io.openbac.bacnet.type.primitive.BACnetBoolean;
import io.openbac.bacnet.type.primitive.BACnetCharacterString;
import io.openbac.bacnet.type.primitive.BACnetOctetString;
import io.openbac.bacnet.type.primitive.BACnetPrimitive;
import io.openbac.util.HexUtils;

/**
 * @author J. Seitter
 *
 */
public class BACnetOctetStringTest extends BACnetTest {

	

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		init("io/openbac/bacnet/type/BACnetOctetString.td");
	}

	@Test
	public void testDecode() throws BACnetParseException {

		int size = loader.resultBuffers.size();
		System.out.println("will run "+size+" tests");
		for (int i = 0; i < size; i++) {
			System.out.println("------------ running case # "+i+" -------------");
			ByteBuf buf = loader.resultBuffers.get(i);
			HashMap<String, String> props = loader.resultProps.get(i);

			BACnetOctetString obj = BACnetPrimitive.createPrimitive(BACnetOctetString.class, buf);

			System.out.println(obj.toDebugString());
			System.out.println(obj.toString());
			System.out.println("value: " + HexUtils.convert(obj.getValue()));

			if (props.get("class").equals("true"))
				Assert.assertTrue(obj.tagClass);
			if (props.get("class").equals("false"))
				Assert.assertFalse(obj.tagClass);

			Assert.assertEquals(Integer.valueOf(obj.tagNumber), Integer.decode(props.get("tag")));
			//TODO: proper assertion for content
			//Assert.assertEquals(props.get("value"), obj.getValue());
		}
	}
	

	@Test
	public void testEncode() {
	
	}
}
