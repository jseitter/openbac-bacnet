package io.openbac.bacnet.testutil;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import io.openbac.bacnet.type.BACnetBooleanTest;

public class BACnetTest {

	protected static TestDataLoader loader;

	public static void init(String testfile) throws FileNotFoundException, UnsupportedEncodingException {
		// get the testdata
	
	InputStream testdata = BACnetBooleanTest.class.getClassLoader()
			.getResourceAsStream(testfile);
	loader = new TestDataLoader(testdata);
	}
}
