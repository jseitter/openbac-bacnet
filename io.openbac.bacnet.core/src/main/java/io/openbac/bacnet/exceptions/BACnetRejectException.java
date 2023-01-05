package io.openbac.bacnet.exceptions;

import io.openbac.bacnet.type.enumerated.BACnetRejectReason;

public class BACnetRejectException extends BACnetBaseException {

        // TODO exceptionReason
        public BACnetRejectException(final BACnetRejectReason exceptionReason) {
                super(exceptionReason.toString());
        }

        public BACnetRejectException(final String msg, final BACnetRejectReason exceptionReason) {
                super(msg);
        }

        public BACnetRejectException(final String msg, final Throwable t, final BACnetRejectReason exceptionReason) {
                super(msg, t);
        }

        public BACnetRejectException(final String msg) {
                super(msg);
        }

        public BACnetRejectException(final String msg, final Throwable t) {
                super(msg, t);
        }

}
