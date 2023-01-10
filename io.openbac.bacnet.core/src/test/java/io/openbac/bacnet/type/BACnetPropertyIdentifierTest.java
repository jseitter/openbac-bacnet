	package io.openbac.bacnet.type;

import java.util.HashMap;

import org.junit.BeforeClass;
import org.junit.Test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.openbac.bacnet.testutil.BACnetTest;
import io.openbac.bacnet.type.enumerated.BACnetPropertyIdentifier;
import io.openbac.util.HexUtils;

public class BACnetPropertyIdentifierTest extends BACnetTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		init("io/openbac/bacnet/type/BACnetPropertyIdentifier.td");
	}

	@Test
	public void testDecode() {
		int size = loader.resultBuffers.size();
		System.out.println("will run "+size+" tests");
		for (int i = 0; i < size; i++) {
			System.out.println("------------ running case # "+i+" -------------");
			ByteBuf buf = loader.resultBuffers.get(i);
			HashMap<String, String> props = loader.resultProps.get(i);
			//TODO fix this
			//BACnetPropertyIdentifier bool = new(BACnetPropertyIdentifier(BACnetPrimitive.createPrimitive(BACnetEnumerated.class, buf));
		}
	}
	
	@Test
	public void testEncode() {
		
		BACnetPropertyIdentifier id = BACnetPropertyIdentifier.presentValue;
		ByteBuf buffer = Unpooled.buffer();
		id.encode(buffer, 3);
		System.out.println(HexUtils.convert(buffer.array()));

		buffer = Unpooled.buffer();
		id.encode(buffer, 9);
		System.out.println(HexUtils.convert(buffer.array()));
		
		buffer = Unpooled.buffer();
		id.encode(buffer, 18);
		System.out.println(HexUtils.convert(buffer.array()));
		
	}

}
