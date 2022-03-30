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

public final class Point3D {
	public final double x;
	public final double y;
	public final double z;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Point3D() {
		this(0.0D, 0.0D, 0.0D);
	}
	
	public Point3D(final double x, final double y, final double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public double sphericalPhi() {
		return Math.getOrAdd(Math.atan2(this.y, this.x), 0.0D, Math.PI * 2.0D);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static Point3D add(final Point3D p, final Vector3D v) {
		return new Point3D(p.x + v.x, p.y + v.y, p.z + v.z);
	}
	
	public static Point3D add(final Point3D p, final Vector3D v, final double s) {
		return new Point3D(p.x + v.x * s, p.y + v.y * s, p.z + v.z * s);
	}
	
	public static Point3D lerp(final Point3D a, final Point3D b, final double t) {
		return new Point3D(Math.lerp(a.x, b.x, t), Math.lerp(a.y, b.y, t), Math.lerp(a.z, b.z, t));
	}
	
	public static Point3D transform(final Matrix44D mLHS, final Point3D pRHS) {
		final double x = mLHS.element11 * pRHS.x + mLHS.element12 * pRHS.y + mLHS.element13 * pRHS.z + mLHS.element14;
		final double y = mLHS.element21 * pRHS.x + mLHS.element22 * pRHS.y + mLHS.element23 * pRHS.z + mLHS.element24;
		final double z = mLHS.element31 * pRHS.x + mLHS.element32 * pRHS.y + mLHS.element33 * pRHS.z + mLHS.element34;
		
		return new Point3D(x, y, z);
	}
	
	public static Point3D transformAndDivide(final Matrix44D mLHS, final Point3D pRHS) {
		final double x = mLHS.element11 * pRHS.x + mLHS.element12 * pRHS.y + mLHS.element13 * pRHS.z + mLHS.element14;
		final double y = mLHS.element21 * pRHS.x + mLHS.element22 * pRHS.y + mLHS.element23 * pRHS.z + mLHS.element24;
		final double z = mLHS.element31 * pRHS.x + mLHS.element32 * pRHS.y + mLHS.element33 * pRHS.z + mLHS.element34;
		final double w = mLHS.element41 * pRHS.x + mLHS.element42 * pRHS.y + mLHS.element43 * pRHS.z + mLHS.element44;
		
		return Math.equal(w, 1.0D) || Math.isZero(w) ? new Point3D(x, y, z) : new Point3D(x / w, y / w, z / w);
	}
	
	public static double distance(final Point3D eye, final Point3D lookAt) {
		return Vector3D.direction(eye, lookAt).length();
	}
	
	public static double distanceSquared(final Point3D eye, final Point3D lookAt) {
		return Vector3D.direction(eye, lookAt).lengthSquared();
	}
}