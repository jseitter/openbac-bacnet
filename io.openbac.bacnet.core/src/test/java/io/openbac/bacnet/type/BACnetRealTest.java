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
import io.openbac.bacnet.type.primitive.BACnetReal;

public class BACnetRealTest extends BACnetTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// get the testdata
		InputStream testdata = BACnetBooleanTest.class.getClassLoader()
				.getResourceAsStream("io/openbac/bacnet/type/BACnetReal.td");
		loader = new TestDataLoader(testdata);
	}

	@Test
	public void decodeTest() throws BACnetParseException {
		int size = loader.resultBuffers.size();
		System.out.println("will run " + size + " tests");
		for (int i = 0; i < size; i++) {
			System.out.println("------------ running case # " + i + " -------------");
			ByteBuf buf = loader.resultBuffers.get(i);
			HashMap<String, String> props = loader.resultProps.get(i);

			BACnetReal obj = BACnetPrimitive.createPrimitive(BACnetReal.class, buf);

			System.out.println(obj.toDebugString());
			System.out.println(obj.getValue());
			Assert.assertEquals(Float.valueOf(props.get("value")), Float.valueOf(obj.getValue()));
			System.out.println("ok");

		}
	}

	@Test
	public void testEncode() {

	}

}
