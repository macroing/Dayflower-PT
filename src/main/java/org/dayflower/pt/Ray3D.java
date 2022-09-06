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

import java.lang.reflect.Field;//TODO: Add unit tests!
import java.util.Objects;

import org.macroing.java.lang.Doubles;

public final class Ray3D {
	private final Point3D origin;
	private final Vector3D direction;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Ray3D(final Point3D origin, final Vector3D direction) {
		this.origin = Objects.requireNonNull(origin, "origin == null");
		this.direction = Vector3D.normalize(Objects.requireNonNull(direction, "direction == null"));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Point3D getOrigin() {
		return this.origin;
	}
	
	@Override
	public String toString() {
		return String.format("new Ray3D(%s, %s)", this.origin, this.direction);
	}
	
	public Vector3D getDirection() {
		return this.direction;
	}
	
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof Ray3D)) {
			return false;
		} else {
			return equals(Ray3D.class.cast(object));
		}
	}
	
	public boolean equals(final Ray3D r) {
		if(r == this) {
			return true;
		} else if(r == null) {
			return false;
		} else if(!Objects.equals(this.origin, r.origin)) {
			return false;
		} else if(!Objects.equals(this.direction, r.direction)) {
			return false;
		} else {
			return true;
		}
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.origin, this.direction);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add unit tests!
	public static Ray3D transform(final Matrix44D mLHS, final Ray3D rRHS) {
		return new Ray3D(Point3D.transformAndDivide(mLHS, rRHS.origin), Vector3D.transform(mLHS, rRHS.direction));
	}
	
	public static double transformT(final Matrix44D m, final Ray3D rOldSpace, final Ray3D rNewSpace, final double t) {
		return !Doubles.isNaN(t) && !Doubles.isZero(t) && t < Math.MAX_VALUE ? Doubles.abs(Point3D.distance(rNewSpace.origin, Point3D.transformAndDivide(m, Point3D.add(rOldSpace.origin, rOldSpace.direction, t)))) : t;
	}
}