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
import io.openbac.bacnet.type.primitive.BACnetDouble;
import io.openbac.bacnet.type.primitive.BACnetPrimitive;

public class BACnetDoubleTest extends BACnetTest {



	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// get the testdata
		InputStream testdata = BACnetBooleanTest.class.getClassLoader()
				.getResourceAsStream("io/openbac/bacnet/type/BACnetDouble.td");
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

			BACnetDouble obj = (BACnetDouble) BACnetPrimitive
					.createPrimitive(BACnetDouble.class, buf);
			
			System.out.println(obj.toDebugString());
			System.out.println(obj.getValue());
			Assert.assertEquals(Double.valueOf(props.get("value")), Double.valueOf(obj.getValue()));
			System.out.println("ok");

		}
	}

	@Test
	public void testEncode() {

	}

}
