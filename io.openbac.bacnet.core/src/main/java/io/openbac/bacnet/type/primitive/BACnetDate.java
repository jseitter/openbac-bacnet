/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.openbac.bacnet.type.primitive;

import io.netty.buffer.ByteBuf;
import io.openbac.bacnet.type.enumerated.BACnetDayOfWeek;
import io.openbac.bacnet.type.enumerated.BACnetMonth;

/**
 *
 * @author Tobias Breckle
 */
public class BACnetDate extends BACnetPrimitive {

        private int year;
        private BACnetMonth month;
        private int day;
        private BACnetDayOfWeek dayOfWeek;
        
        public BACnetDate(final int year, final BACnetMonth month, final int day, final BACnetDayOfWeek dayOfWeek) {

                this.year = year;
                this.month = month;
                this.day = day;
                this.dayOfWeek = dayOfWeek;

        }

        public BACnetDate(final ByteBuf data) {
        		buf=data;
        		decodeTag();
        		
                if (tagLVT != 4) {
                        throw new IllegalArgumentException("Invalid length: " + tagLVT);
                }

                year = buf.readByte() + 1900;
                month = BACnetMonth.getMonth(buf.readByte());
                day = buf.readByte();
                dayOfWeek = BACnetDayOfWeek.getDayOfWeek(buf.readByte());
                
        }

        public int getYear() {
                return year;
        }

        public void setYear(final int year) {
                this.year = year;
        }

        public boolean isYearUnspecified() {
                return (year == 255);
        }

        public BACnetMonth getMonth() {
                return month;
        }

        public void setMonth(final BACnetMonth month) {
                this.month = month;
        }

        public boolean isMonthUnspecified() {
                return (month == BACnetMonth.UNSPECIFIED);
        }

        public int getDay() {
                return day;
        }

        public void setDay(final int day) {
                this.day = day;
        }

        public boolean isDayrUnspecified() {
                return (day == 255);
        }

        public BACnetDayOfWeek getDayOfWeek() {
                return dayOfWeek;
        }

        public void setDayOfWeek(final BACnetDayOfWeek dayOfWeek) {
                this.dayOfWeek = dayOfWeek;
        }
        
        public boolean isDayOfWeekUnspecified() {
                return (dayOfWeek == BACnetDayOfWeek.UNSPECIFIED);
        }

        @Override
        public String toString() {
                return "BACnetDate{" + "year=" + year + ", month=" + month + ", day=" + day + ", dayOfWeek=" + dayOfWeek + '}';
        }

        @Override
        public Class<?> getType() {
                return BACnetDate.class;
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
