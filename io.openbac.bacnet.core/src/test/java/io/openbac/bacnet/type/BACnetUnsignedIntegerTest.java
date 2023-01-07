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
import io.openbac.bacnet.type.primitive.BACnetUnsignedInteger;

public class BACnetUnsignedIntegerTest extends BACnetTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		init("io/openbac/bacnet/type/BACnetUnsignedInteger.td");
	}

	@Test
	public void decodeTest() throws BACnetParseException {
		int size = loader.resultBuffers.size();
		System.out.println("will run " + size + " tests");
		for (int i = 0; i < size; i++) {
			System.out.println("------------ running case # " + i + " -------------");
			ByteBuf buf = loader.resultBuffers.get(i);
			HashMap<String, String> props = loader.resultProps.get(i);

			BACnetUnsignedInteger uint = BACnetPrimitive.createPrimitive(BACnetUnsignedInteger.class, buf);

			System.out.println(uint.toDebugString());
			System.out.println(uint.getValue());
			Assert.assertEquals(Long.decode(props.get("value")), Long.valueOf(uint.getValue()));
			System.out.println("ok");

		}
	}

	@Test
	public void testEncode() {

	}

}
