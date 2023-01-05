/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.openbac.api.device;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Objects;

/**
 *
 * @author tbreckle
 */
public class BACnetDeviceAddressIP implements BACnetDeviceAddress {
        
        private InetSocketAddress inetSocketAddress;

        public BACnetDeviceAddressIP() {
                
                inetSocketAddress = null;
                
        }
        
        public BACnetDeviceAddressIP(String ip, int port) {
                
                if (port < 0 || port > 65535) {
                        throw new IllegalArgumentException("invalid port: " + port);
                }
                
                try {
                        inetSocketAddress = new InetSocketAddress(InetAddress.getByName(ip), port);
                } catch (UnknownHostException ex) {
                        throw new IllegalArgumentException("cannot resolve address: " + ip + ":" + port);
                }
                
        }

        @Override
        public String getAddressAsString() {
                return inetSocketAddress.getHostString() + ":" + inetSocketAddress.getPort();
        }

        @Override
        public String toString() {
                return "BACnetDeviceAddressIP{" + "inetSocketAddress=" + inetSocketAddress + '}';
        }

        @Override
        public int hashCode() {
                int hash = 7;
                hash = 59 * hash + Objects.hashCode(this.inetSocketAddress);
                return hash;
        }

        @Override
        public boolean equals(Object obj) {
                if (obj == null) {
                        return false;
                }
                if (getClass() != obj.getClass()) {
                        return false;
                }
                final BACnetDeviceAddressIP other = (BACnetDeviceAddressIP) obj;
                if (!Objects.equals(this.inetSocketAddress, other.inetSocketAddress)) {
                        return false;
                }
                return true;
        }

}
