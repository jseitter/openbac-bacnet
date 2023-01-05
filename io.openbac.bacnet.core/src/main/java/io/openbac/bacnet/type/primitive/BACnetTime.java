/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.openbac.bacnet.type.primitive;

import io.netty.buffer.ByteBuf;

/**
 *
 * @author Tobias Breckle
 */
public class BACnetTime extends BACnetPrimitive {

        private int hour;
        private int minute;
        private int second;
        private int hundredth;
        
        public BACnetTime(final int hour, final int minute, final int second, final int hundredth) {

                this.hour = hour;
                this.minute = minute;
                this.second = second;
                this.hundredth = hundredth;

        }

        public BACnetTime(final ByteBuf data) {
        		buf=data;
        		decodeTag();
        	
                if (tagLVT != 4) {
                        throw new IllegalArgumentException("Invalid length: " + tagLVT);
                }

                
                hour = buf.readByte();
                minute = buf.readByte();
                second = buf.readByte();
                hundredth = buf.readByte();
                
        }

        public int getHour() {
                return hour;
        }

        public void setHour(final int hour) {
                this.hour = hour;
        }
        
        public boolean isHourUnspecified() {
                return (hour == 255);
        }

        public int getMinute() {
                return minute;
        }

        public void setMinute(final int minute) {
                this.minute = minute;
        }

        public boolean isMinuteUnspecified() {
                return (minute == 255);
        }

        public int getSecond() {
                return second;
        }

        public void setSecond(final int second) {
                this.second = second;
        }

        public boolean isSecondUnspecified() {
                return (second == 255);
        }

        public int getHundredth() {
                return hundredth;
        }

        public void setHundredth(final int hundredth) {
                this.hundredth = hundredth;
        }

        public boolean isHundredthUnspecified() {
                return (hundredth == 255);
        }

        @Override
        public String toString() {
                return "BACnetTime{" + "hour=" + hour + ", minute=" + minute + ", second=" + second + ", hundredth=" + hundredth + '}';
        }

        @Override
        public Class<?> getType() {
                return BACnetTime.class;
        }

		@Override
		public void encodeApplication(ByteBuf data) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void encode(ByteBuf buf, int contextId) {
			// TODO Auto-generated method stub
			
		}

}
