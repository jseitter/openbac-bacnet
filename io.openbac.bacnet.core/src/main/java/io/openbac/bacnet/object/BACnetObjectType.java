package io.openbac.bacnet.object;

/**
 *
 * @author Tobias Breckle
 */
public class BACnetObjectType {
        
        public enum ObjectType {
                ACCESS_DOOR             (30), 
                ACCUMULATOR             (23), 
                ANALOG_INPUT            (0), 
                ANALOG_OUTPUT           (1), 
                ANALOG_VALUE            (2), 
                AVERAGING               (18), 
                BINARY_INPUT            (3), 
                BINARY_OUTPUT           (4), 
                BINARY_VALUE            (5), 
                CALENDAR                (6), 
                COMMAND                 (7), 
                DEVICE                  (8), 
                EVENT_ENROLLMENT        (9), 
                EVENT_LOG               (25), 
                FILE                    (10), 
                GROUP                   (11), 
                LIFE_SAFETY_POINT       (21), 
                LIFE_SAFETY_ZONE        (22), 
                LOAD_CONTROL            (28), 
                LOOP                    (12), 
                MULTI_STATE_INPUT       (13), 
                MULTI_STATE_OUTPUT      (14), 
                MULTI_STATE_VALUE       (19), 
                NOTIFICATION_CLASS      (15), 
                PROGRAM                 (16), 
                PULSE_CONVERTER         (24), 
                SCHEDULE                (17), 
                TRUCTURED_VIEW          (29), 
                TREND_LOG               (20), 
                TREND_LOG_MULTIPLE      (27);
        
                

                private final int objectId;

                ObjectType(final int objectId) {
                        this.objectId = objectId;
                }

                public int getObjectType() {
                        return objectId;
                }

                public static ObjectType getObjectType(final int objectId) {

                        for (ObjectType objectType : values()) {
                                if (objectType.objectId == objectId) {
                                        return objectType;
                                }
                        }
                        throw new IllegalArgumentException("objectId has invalid value");

                }

        }        
}
