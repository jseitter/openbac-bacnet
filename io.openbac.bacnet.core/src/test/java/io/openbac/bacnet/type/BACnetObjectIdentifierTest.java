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
import io.openbac.bacnet.type.primitive.BACnetObjectIdentifier;
import io.openbac.bacnet.type.primitive.BACnetPrimitive;

/**
 * @author J. Seitter
 *
 */
public class BACnetObjectIdentifierTest extends BACnetTest {

	

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// get the testdata
		InputStream testdata = BACnetObjectIdentifierTest.class.getClassLoader()
				.getResourceAsStream("io/openbac/bacnet/type/BACnetObjectIdentifier.td");
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

			BACnetObjectIdentifier obj = BACnetPrimitive.createPrimitive(BACnetObjectIdentifier.class, buf);

			System.out.println(obj.toDebugString());
			System.out.println("type: " + obj.getObjectType().name());
			System.out.println("instance: " + obj.getInstance());

			
			if (props.get("class").equals("true"))
				Assert.assertTrue(obj.tagClass);
			if (props.get("class").equals("false"))
				Assert.assertFalse(obj.tagClass);
			
			Assert.assertEquals(Integer.valueOf(obj.tagNumber), Integer.decode(props.get("tag")));
			Assert.assertEquals(Integer.valueOf(obj.getInstance()), Integer.decode(props.get("instance")));
			Assert.assertEquals(Integer.valueOf(obj.getObjectTypeInt()), Integer.decode(props.get("type")));
		}
	}
	

	@Test
	public void testEncode() {
	
	}
}
