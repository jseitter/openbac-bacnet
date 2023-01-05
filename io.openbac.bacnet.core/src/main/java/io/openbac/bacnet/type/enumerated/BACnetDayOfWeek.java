/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.openbac.bacnet.type.enumerated;

/**
 *
 * @author tobi
 */
public enum BACnetDayOfWeek {

        MONDAY                  (1),
        TUESDAY                 (2),
        WEDNESDAY               (3),
        THURSDAY                (4),
        FRIDAY                  (5),
        SATURDAY                (6),
        SUNDAY                  (7),
        UNSPECIFIED             (255);

        private final byte dayOfWeek;

        BACnetDayOfWeek(final int month) {
                this.dayOfWeek = (byte) month;
        }

        public byte getValue() {
                return dayOfWeek;
        }

        public static BACnetDayOfWeek getDayOfWeek(final byte dayOfWeek) {

                for (BACnetDayOfWeek dow : values()) {
                        if (dow.dayOfWeek == dayOfWeek) {
                                return dow;
                        }
                }
                throw new IllegalArgumentException("dayOfWeek has invalid value");

        }


}
