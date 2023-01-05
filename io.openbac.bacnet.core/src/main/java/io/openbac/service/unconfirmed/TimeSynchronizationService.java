package io.openbac.service.unconfirmed;

import io.netty.buffer.ByteBuf;
import io.openbac.bacnet.exceptions.BACnetParseException;
import io.openbac.bacnet.type.primitive.BACnetDate;
import io.openbac.bacnet.type.primitive.BACnetTime;

/**
 * Service to send data and time information to other BACnet Systems
 * 
 * @author Joerg Seitter
 *
 */
public class TimeSynchronizationService {
	
	BACnetDate date;
	BACnetTime time;
	
	public TimeSynchronizationService(BACnetDate date, BACnetTime time) {
		this.date=date;
		this.time=time;
	}
		
	
	public TimeSynchronizationService(final ByteBuf buf) throws BACnetParseException {
		date= BACnetDate.createPrimitive(BACnetDate.class, buf);
	}
	
	

}


