package io.openbac.bacnet.type.enumerated;

import io.openbac.bacnet.type.primitive.BACnetBitString;

/**
 * 
 * @author joerg
 *
 */
public class BACnetServicesSupported {

	private BACnetBitString servicesSupported;

	public BACnetServicesSupported(BACnetServicesSupported.Service[] services) {
		
		boolean[] bits = new boolean[64];
		for(Service s : services) {
			bits[s.service] = true;
		}
		servicesSupported = new BACnetBitString(bits);
	}
	
	public enum Service {
		acknowledgeAlarm(0), 
		confirmedCOVNotification(1), 
		confirmedEventNotification(2), 
		getAlarmSummary(3),
		getEnrollmentSummary(4),
		subscribeCOV(5),
		atomicReadFile(6), 
		atomicWriteFile(7), 
		addListElement(8), 
		removeListElement(9), 
		createObject(10), 
		deleteObject(11), 
		readProperty(12), 
		readPropertyConditional(13), 
		readPropertyMultiple(14), 
		writeProperty(15), 
		writePropertyMultiple(16), 
		deviceCommunicationControl(17), 
		confirmedPrivateTransfer(18), 
		confirmedTextMessage(19), 
		reinitializeDevice(20), 
		vtOpen(21), 
		vtClose(22), 
		vtData(23),
		authenticate(24),
		requestKey(25),
		i_am(26),
		i_have(27),
		unconfirmedCOVNotification(28),
		unconfirmedEventNotification(29),
		unconfirmedPrivateTransfer(30), 
		unconfirmedTextMessage(31),
		timeSynchronization(32),
		who_has(33),
		who_is(34),
		readRange(35),
		utcTimeSynchronization(36),
		lifeSafetyOperation(37),
		subscribeCOVProperty(38),
		getEventInformation(39);

		private final int service;

		Service(int service) {
			this.service = service;
		}

		public int getService() {
			return service;
		}

	}

	public BACnetBitString getServicesSupported() {
		return servicesSupported;
	}

	public void setServicesSupported(BACnetBitString servicesSupported) {
		this.servicesSupported = servicesSupported;
	}
}
