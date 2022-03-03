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

public final class Schlick {
	private Schlick() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static double fresnelDielectric(final double cosTheta, final double r0) {
		return r0 + (1.0D - r0) * fresnelWeight(cosTheta);
	}
	
	public static double fresnelWeight(final double cosTheta) {
		final double m = Math.saturate(1.0D - cosTheta);
		
		return (m * m) * (m * m) * m;
	}
}