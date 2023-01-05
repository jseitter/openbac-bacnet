package io.openbac.bacnet.object;

import java.util.ArrayList;
import java.util.List;

public interface BACnetObject {

	final List<BACnetProperty> propertyList = new ArrayList<>();
	
}
