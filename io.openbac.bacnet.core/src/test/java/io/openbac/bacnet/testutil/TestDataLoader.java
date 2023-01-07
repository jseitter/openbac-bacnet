package io.openbac.bacnet.testutil;

import static io.netty.buffer.Unpooled.*;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import io.netty.buffer.ByteBuf;

/**
 * Loads test data from special formatted file:
 * 
 * # starts comment lines ? starts assert lines (key value pairs) else are hex
 * encoded space seperated data bytes
 * 
 * @author joerg
 *
 */
public class TestDataLoader {

	public List<ByteBuf> resultBuffers = new ArrayList<>();
	public List<HashMap<String, String>> resultProps = new ArrayList<>();

	public TestDataLoader(InputStream testdata) throws FileNotFoundException, UnsupportedEncodingException {
		LineNumberReader lnr = new LineNumberReader(new InputStreamReader(testdata, "UTF-8"));
		lnr.lines().forEachOrdered(line -> {
			// check if comment
			if (!line.startsWith("#")) {
				if (line.startsWith("?")) {
					// remove the #
					line = line.substring(1, line.length());
					// lines starting with # contain the assertion keys
					line = line.strip();
					// split into KeyValues on ,
					String[] keyValueRaw = line.split(",");
					HashMap<String, String> p = new HashMap<>();
					for (String s : keyValueRaw) {
						s = s.strip();
						// split into key and value
						String[] kv = s.split("=");
						p.put(kv[0], kv[1]);

					}
					resultProps.add(p);

				} else {
					// decode line into bytes
					String stripped1 = line.replaceAll("0x", "");
					String stripped2 = stripped1.replace(" ", "");
					byte[] lineResult;
					ByteBuf buf = buffer();
					try {
						lineResult = Hex.decodeHex(stripped2);
						buf.writeBytes(lineResult);
						resultBuffers.add(buf);
					} catch (DecoderException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		});
	}

}
