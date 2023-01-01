/**
 * Copyright 2022 - 2023 J&#246;rgen Lundgren
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
import java.util.Optional;

import org.macroing.geo4j.bv.BoundingVolume3D;
import org.macroing.geo4j.matrix.Matrix44D;
import org.macroing.geo4j.ray.Ray3D;
import org.macroing.java.lang.Doubles;

public final class Primitive {
	private final BoundingVolume3D boundingVolume;
	private final Material material;
	private final Shape shape;
	private final Transform transform;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add unit tests!
	public Primitive(final Material material, final Shape shape) {
		this(material, shape, new Transform());
	}
	
//	TODO: Add unit tests!
	public Primitive(final Material material, final Shape shape, final Transform transform) {
		this.material = Objects.requireNonNull(material, "material == null");
		this.shape = Objects.requireNonNull(shape, "shape == null");
		this.transform = Objects.requireNonNull(transform, "transform == null");
		this.boundingVolume = this.shape.getBoundingVolume().transform(this.transform.getObjectToWorld());
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add unit tests!
	public Material getMaterial() {
		return this.material;
	}
	
//	TODO: Add unit tests!
	public Optional<Intersection> intersection(final Ray3D rayWS, final double tMinimum, final double tMaximum) {
		if(this.boundingVolume.contains(rayWS.getOrigin()) || this.boundingVolume.intersects(rayWS, tMinimum, tMaximum)) {
			final Matrix44D worldToObject = this.transform.getWorldToObject();
			
			final Ray3D rayOS = worldToObject.transform(rayWS);
			
			final double tOS = this.shape.intersection(rayOS, tMinimum, worldToObject.transformT(rayWS, rayOS, tMaximum));
			
			if(!Doubles.isNaN(tOS)) {
				return Optional.of(new Intersection(this, rayOS, tOS));
			}
		}
		
		return Optional.empty();
	}
	
//	TODO: Add unit tests!
	public Shape getShape() {
		return this.shape;
	}
	
//	TODO: Add unit tests!
	@Override
	public String toString() {
		return String.format("new Primitive(%s, %s, %s)", this.material, this.shape, this.transform);
	}
	
//	TODO: Add unit tests!
	public Transform getTransform() {
		return this.transform;
	}
	
//	TODO: Add unit tests!
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof Primitive)) {
			return false;
		} else {
			return equals(Primitive.class.cast(object));
		}
	}
	
//	TODO: Add unit tests!
	public boolean equals(final Primitive primitive) {
		if(primitive == this) {
			return true;
		} else if(primitive == null) {
			return false;
		} else if(!Objects.equals(this.boundingVolume, primitive.boundingVolume)) {
			return false;
		} else if(!Objects.equals(this.material, primitive.material)) {
			return false;
		} else if(!Objects.equals(this.shape, primitive.shape)) {
			return false;
		} else if(!Objects.equals(this.transform, primitive.transform)) {
			return false;
		} else {
			return true;
		}
	}
	
//	TODO: Add unit tests!
	@Override
	public int hashCode() {
		return Objects.hash(this.boundingVolume, this.material, this.shape, this.transform);
	}
}