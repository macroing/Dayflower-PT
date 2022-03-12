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

public final class Intersection {
	private final Primitive primitive;
	private final Ray3D ray;
	private final double t;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Intersection(final Primitive primitive, final Ray3D ray, final double t) {
		this.primitive = Objects.requireNonNull(primitive, "primitive == null");
		this.ray = Objects.requireNonNull(ray, "ray == null");
		this.t = t;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public OrthonormalBasis33D getOrthonormalBasis() {
		return this.primitive.getShape().computeOrthonormalBasis(this.ray, this.t);
	}
	
	public Point2D getTextureCoordinates() {
		return this.primitive.getShape().computeTextureCoordinates(this.ray, this.t);
	}
	
	public Point3D getSurfaceIntersectionPoint() {
		return Point3D.add(this.ray.getOrigin(), this.ray.getDirection(), this.t);
	}
	
	public Primitive getPrimitive() {
		return this.primitive;
	}
	
	public Ray3D getRay() {
		return this.ray;
	}
	
	public Vector3D getSurfaceNormal() {
		return this.primitive.getShape().computeSurfaceNormal(this.ray, this.t);
	}
	
	public Vector3D getSurfaceNormalCorrectlyOriented() {
		return Vector3D.faceForwardLHSNegated(getSurfaceNormal(), getRay().getDirection());
	}
	
	public double getT() {
		return this.t;
	}
}