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

public final class Vector2D {
	public final double x;
	public final double y;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Vector2D() {
		this(0.0D, 0.0D);
	}
	
	public Vector2D(final double x, final double y) {
		this.x = x;
		this.y = y;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static Vector2D directionXY(final Point3D p) {
		return new Vector2D(p.x, p.y);
	}
	
	public static Vector2D directionYZ(final Point3D p) {
		return new Vector2D(p.y, p.z);
	}
	
	public static Vector2D directionZX(final Point3D p) {
		return new Vector2D(p.z, p.x);
	}
	
	public static Vector2D subtract(final Vector2D vLHS, final Vector2D vRHS) {
		return new Vector2D(vLHS.x - vRHS.x, vLHS.y - vRHS.y);
	}
	
	public static double crossProduct(final Vector2D vLHS, final Vector2D vRHS) {
		return vLHS.x * vRHS.y - vLHS.y * vRHS.x;
	}
}