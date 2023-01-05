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
import io.openbac.bacnet.type.primitive.BACnetPrimitive;
import io.openbac.bacnet.type.primitive.BACnetTime;

/**
 * @author J. Seitter
 *
 */
public class BACnetTimeTest extends BACnetTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// get the testdata
		InputStream testdata = BACnetTimeTest.class.getClassLoader()
				.getResourceAsStream("io/openbac/bacnet/type/BACnetTime.td");
		loader = new TestDataLoader(testdata);
	}

	@Test
	public void testDecode() throws BACnetParseException {

		int size = loader.resultBuffers.size();
		System.out.println("will run " + size + " tests");
		for (int i = 0; i < size; i++) {
			System.out.println("------------ running case # " + i + " -------------");
			ByteBuf buf = loader.resultBuffers.get(i);
			HashMap<String, String> props = loader.resultProps.get(i);

			BACnetTime time = BACnetPrimitive.createPrimitive(BACnetTime.class, buf);

			System.out.println(time.toDebugString());
			System.out.println("value: " + time.toString());

			if (props.get("class").equals("true"))
				Assert.assertTrue(time.tagClass);
			if (props.get("class").equals("false"))
				Assert.assertFalse(time.tagClass);

			Assert.assertEquals(Integer.valueOf(time.tagNumber), Integer.decode(props.get("tag")));
			Assert.assertEquals(Integer.valueOf(time.getHour()), Integer.decode(props.get("hour")));
			Assert.assertEquals(Integer.valueOf(time.getMinute()), Integer.decode(props.get("minute")));
			Assert.assertEquals(Integer.valueOf(time.getSecond()), Integer.decode(props.get("second")));
			Assert.assertEquals(Integer.valueOf(time.getHundredth()), Integer.decode(props.get("hundredth")));
		}
	}

	@Test
	public void testEncode() {

	}
}
