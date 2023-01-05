package io.openbac.bacnet.type;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ BACnetBitStringTest.class, BACnetBooleanTest.class, BACnetCharacterStringTest.class,
		BACnetDateTest.class, BACnetDoubleTest.class, BACnetObjectIdentifierTest.class, BACnetOctetStringTest.class,
		BACnetPropertyIdentifierTest.class, BACnetRealTest.class, BACnetSignedIntegerTest.class, BACnetTimeTest.class,
		BACnetUnsignedIntegerTest.class })

public class AllTests {

}
