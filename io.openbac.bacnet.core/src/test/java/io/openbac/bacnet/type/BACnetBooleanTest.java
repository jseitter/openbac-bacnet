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
import io.openbac.bacnet.type.primitive.BACnetPrimitive;

/**
 * @author J. Seitter
 *
 */
public class BACnetBooleanTest extends BACnetTest {

	

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// get the testdata
		InputStream testdata = BACnetBooleanTest.class.getClassLoader()
				.getResourceAsStream("io/openbac/bacnet/type/BACnetBoolean.td");
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

			BACnetBoolean obj = BACnetPrimitive.createPrimitive(BACnetBoolean.class, buf);

			System.out.println(obj.toDebugString());
			System.out.println("value: " + obj.getValue());

			if (props.get("class").equals("true"))
				Assert.assertTrue(obj.tagClass);
			if (props.get("class").equals("false"))
				Assert.assertFalse(obj.tagClass);

			if (props.get("value").equals("true"))
				Assert.assertTrue(obj.getValue());
			if (props.get("value").equals("false"))
				Assert.assertFalse(obj.getValue());
			
			Assert.assertEquals(Integer.valueOf(obj.tagNumber), Integer.decode(props.get("tag")));
		}
	}
	

	@Test
	public void testEncode() {
	
	}
}
