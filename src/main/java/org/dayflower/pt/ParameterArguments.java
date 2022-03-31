/**
 * Copyright 2022 J&#246;rgen Lundgren
 * 
 * This file is part of org.dayflower.pt.
 * 
 * org.dayflower.pt is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * org.dayflower.pt is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with org.dayflower.pt. If not, see <http://www.gnu.org/licenses/>.
 */
package org.dayflower.pt;

import java.util.Objects;

public final class ParameterArguments {
	private ParameterArguments() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static <T> T[] requireNonNullArray(final T[] objects, final String name) {
		Objects.requireNonNull(name, "name == null");
		Objects.requireNonNull(objects, String.format("%s == null", name));
		
		for(int i = 0; i < objects.length; i++) {
			Objects.requireNonNull(objects[i], String.format("%s[%s] == null", name, Integer.toString(i)));
		}
		
		return objects;
	}
	
	public static int requireRange(final int value, final int edgeA, final int edgeB, final String name) {
		Objects.requireNonNull(name, "name == null");
		
		final int minimum = Math.min(edgeA, edgeB);
		final int maximum = Math.max(edgeA, edgeB);
		
		if(value < minimum) {
			throw new IllegalArgumentException(String.format("%s < %d: %s == %d", name, Integer.valueOf(minimum), name, Integer.valueOf(value)));
		} else if(value > maximum) {
			throw new IllegalArgumentException(String.format("%s > %d: %s == %d", name, Integer.valueOf(maximum), name, Integer.valueOf(value)));
		} else {
			return value;
		}
	}
}