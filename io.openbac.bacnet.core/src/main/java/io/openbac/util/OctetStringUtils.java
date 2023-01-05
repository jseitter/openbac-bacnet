package io.openbac.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Pattern;

import io.openbac.bacnet.type.primitive.BACnetOctetString;

/**
 * utils to process bacnet octet strings
 *
 * @author TBreckle
 */
public final class OctetStringUtils {

        static private final String IPV4_REGEX = "(([0-1]?[0-9]{1,2}\\.)|(2[0-4][0-9]\\.)|(25[0-5]\\.)){3}(([0-1]?[0-9]{1,2})|(2[0-4][0-9])|(25[0-5]))";
        static private Pattern IPV4_PATTERN = Pattern.compile(IPV4_REGEX);

        public static String getIpString(final BACnetOctetString octetString) throws IllegalArgumentException {

                final byte[] bytes = octetString.getValue();
                if (bytes.length < 4) {
                        throw new IllegalArgumentException("Invalid number of bytes, need to be >=4.");
                }

                final StringBuilder sb = new StringBuilder();
                sb.append(bytes[0] & 0xff);
                for (int i = 1; i < 4; i++) {
                        sb.append(".").append(bytes[i] & 0xff);
                }
                return sb.toString();

        }

        public static int getPort(final BACnetOctetString octetString) throws IllegalArgumentException {

                final byte[] bytes = octetString.getValue();
                if (bytes.length != 6) {
                        throw new IllegalArgumentException("no port available, too less characters");
                }
                return ((bytes[4] & 0xff) << 8) | (bytes[5] & 0xff);

        }

        public static String getIpAndPortString(final BACnetOctetString octetString) throws IllegalArgumentException {

                final byte[] bytes = octetString.getValue();
                if (bytes.length != 6) {
                        throw new IllegalArgumentException("Invalid number of bytes, need to be 6.");
                }

                final StringBuilder sb = new StringBuilder();
                sb.append(bytes[0] & 0xff);
                for (int i = 1; i < bytes.length; i++) {
                        sb.append(".").append(bytes[i] & 0xff);
                }
                sb.append(":").append(((bytes[4] & 0xff) << 8) | (bytes[5] & 0xff));
                return sb.toString();

        }

        public static InetAddress getInetAddress(final BACnetOctetString octetString) throws IllegalArgumentException, UnknownHostException {

                return InetAddress.getByAddress(octetString.getValue());

        }
        
        public static boolean validateIpV4Address(String ipV4Address) {
                return IPV4_PATTERN.matcher(ipV4Address).matches();
        }
        
        public static boolean validateBacnetLinkIpV4(final BACnetOctetString octetString) {
                
                final String ip = getIpString(octetString);
                final int port = getPort(octetString);
                final boolean ipValid = validateIpV4Address(ip);
                boolean portValid = false;
                if (port >= 0 && port < 65536) {
                        portValid = true;
                }
                
                return portValid && ipValid;
                
        }

}
