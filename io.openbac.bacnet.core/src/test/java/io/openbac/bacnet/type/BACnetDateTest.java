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
import io.openbac.bacnet.type.primitive.BACnetDate;
import io.openbac.bacnet.type.primitive.BACnetPrimitive;

/**
 * @author J. Seitter
 *
 */
public class BACnetDateTest extends BACnetTest {

	

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		init("io/openbac/bacnet/type/BACnetDate.td");
	}

	@Test
	public void testDecode() throws BACnetParseException {

		int size = loader.resultBuffers.size();
		System.out.println("will run "+size+" tests");
		for (int i = 0; i < size; i++) {
			System.out.println("------------ running case # "+i+" -------------");
			ByteBuf buf = loader.resultBuffers.get(i);
			HashMap<String, String> props = loader.resultProps.get(i);

			BACnetDate obj = BACnetPrimitive.createPrimitive(BACnetDate.class, buf);

			System.out.println(obj.toDebugString());
			System.out.println("value: " + obj.toString());

			if (props.get("class").equals("true"))
				Assert.assertTrue(obj.tagClass);
			if (props.get("class").equals("false"))
				Assert.assertFalse(obj.tagClass);

			
			Assert.assertEquals(Integer.valueOf(obj.tagNumber), Integer.decode(props.get("tag")));
			Assert.assertEquals(Integer.valueOf(obj.getYear()), Integer.decode(props.get("year")));
			Assert.assertEquals(Integer.valueOf(obj.getMonth().getValue()), Integer.decode(props.get("month")));
			Assert.assertEquals(Integer.valueOf(obj.getDay()), Integer.decode(props.get("day")));
			Assert.assertEquals(Integer.valueOf(obj.getDayOfWeek().getValue()), Integer.decode(props.get("day_of_week")));
		}
	}
	

	@Test
	public void testEncode() {
	
	}
}
