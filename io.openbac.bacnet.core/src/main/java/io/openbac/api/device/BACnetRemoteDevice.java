/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.openbac.api.device;

import java.net.SocketAddress;
import java.util.Objects;

/**
 *
 * @author tbreckle
 */
public class BACnetRemoteDevice {
    
        private BACnetDeviceType type;
        private BACnetDeviceAddress address;
        private SocketAddress deviceAddress;
        private int maxSegmentsAccepted;
        private boolean segmentationSupported;

        public BACnetRemoteDevice(BACnetDeviceType type, BACnetDeviceAddress address, int maxSegmentsAccepted, boolean segmentationSupported) {
                this.type = type;
                this.address = address;
                this.maxSegmentsAccepted = maxSegmentsAccepted;
                this.segmentationSupported = segmentationSupported;
        }
        
        public BACnetRemoteDevice() {
                
                type = BACnetDeviceType.IP;
                address = null;
                
        }

        public BACnetDeviceType getType() {
                return type;
        }

        public void setType(BACnetDeviceType type) {
                this.type = type;
        }

        public BACnetDeviceAddress getAddress() {
                return address;
        }
        
        public String getAddressAsString() {
                return address.getAddressAsString();
        }

        public void setAddress(BACnetDeviceAddress address) {
                this.address = address;
        }

        public int getMaxSegmentsAccepted() {
                return maxSegmentsAccepted;
        }

        public void setMaxSegmentsAccepted(int maxSegmentsAccepted) {
                this.maxSegmentsAccepted = maxSegmentsAccepted;
        }

        public boolean isSegmentationSupported() {
                return segmentationSupported;
        }

        public void setSegmentationSupported(boolean segmentationSupported) {
                this.segmentationSupported = segmentationSupported;
        }

        @Override
        public int hashCode() {
                int hash = 7;
                hash = 97 * hash + Objects.hashCode(this.type);
                hash = 97 * hash + Objects.hashCode(this.address);
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
                final BACnetRemoteDevice other = (BACnetRemoteDevice) obj;
                if (this.type != other.type) {
                        return false;
                }
                if (!Objects.equals(this.address, other.address)) {
                        return false;
                }
                return true;
        }

        @Override
        public String toString() {
                return "BACnetDevice{" + "type=" + type + ", address=" + address + ", maxSegmentsAccepted=" + maxSegmentsAccepted + ", segmentationSupported=" + segmentationSupported + '}';
        }
        
}
