package io.openbac.bacnet.type;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.netty.buffer.ByteBuf;

/**
 *
 * @author tbreckle
 * @param <E>
 */
public class BACnetSequenceOf<E extends BACnetEncodable> extends BACnetEncodable {

	private final List<E> elements;

	public BACnetSequenceOf() {
		elements = new ArrayList<>();
	}

	public BACnetSequenceOf(List<E> elements) {
		this.elements = elements;
	}

	public BACnetSequenceOf(final ByteBuf data) {
		this.elements = null;

	}

	public BACnetSequenceOf(ByteBuf data, Integer contextId)  {

		elements = new ArrayList<>();

	}

	public void add(E element) {
		elements.add(element);
	}

	public void remove(E element) {
		if (elements.contains(element)) {
			elements.remove(element);
		}
	}

	public void removeAll() {
		elements.clear();
	}

	public int size() {
		return elements.size();
	}

	public E get(int index) {
		return elements.get(index);
	}

	@Override
	public String toString() {
		return "BACnetSequenceOf{" + "elements=" + elements + '}';
	}

	public List<E> getElements() {
		return elements;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 59 * hash + Objects.hashCode(this.elements);
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
		final BACnetSequenceOf<?> other = (BACnetSequenceOf<?>) obj;
		if (!Objects.equals(this.elements, other.elements)) {
			return false;
		}
		return true;
	}

	@Override
	public void encode(ByteBuf buf, int contextId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Class<?> getType() {
		// TODO Auto-generated method stub
		return null;
	}

}