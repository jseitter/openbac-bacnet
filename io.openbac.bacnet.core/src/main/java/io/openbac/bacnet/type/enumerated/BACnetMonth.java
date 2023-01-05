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
public enum BACnetMonth {

        JANUARY                 (1),
        FEBRUARY                (2),
        MARCH                   (3),
        APRIL                   (4),
        MAY                     (5),
        JUNE                    (6),
        JULY                    (7),
        AUGUST                  (8),
        SEPTEMBER               (9),
        OCTOBER                 (10),
        NOVEMBER                (11),
        DECEMBER                (12),
        ODD_MONTHS              (13),
        EVEN_MONTHS             (14),
        UNSPECIFIED             (255);

        private final byte month;

        BACnetMonth(final int month) {
                this.month = (byte) month;
        }
        

        public byte getValue() {
                return month;
        }

        public static BACnetMonth getMonth(final byte month) {

                for (BACnetMonth m : values()) {
                        if (m.month == month) {
                                return m;
                        }
                }
                throw new IllegalArgumentException("month has invalid value "+month);

        }


}
