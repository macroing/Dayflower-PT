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
import java.util.Optional;

public final class Primitive {
	private final BoundingVolume boundingVolume;
	private final Material material;
	private final Shape shape;
	private final Transform transform;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Primitive(final Material material, final Shape shape) {
		this(material, shape, new Transform());
	}
	
	public Primitive(final Material material, final Shape shape, final Transform transform) {
		this.material = Objects.requireNonNull(material, "material == null");
		this.shape = Objects.requireNonNull(shape, "shape == null");
		this.transform = Objects.requireNonNull(transform, "transform == null");
		this.boundingVolume = this.shape.getBoundingVolume().transform(this.transform.getObjectToWorld());
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Material getMaterial() {
		return this.material;
	}
	
	public Optional<Intersection> intersection(final Ray3D rayWS, final double tMinimum, final double tMaximum) {
		if(this.boundingVolume.contains(rayWS.getOrigin()) || this.boundingVolume.intersects(rayWS, tMinimum, tMaximum)) {
			final Matrix44D worldToObject = this.transform.getWorldToObject();
			
			final Ray3D rayOS = Ray3D.transform(worldToObject, rayWS);
			
			final double tOS = this.shape.intersection(rayOS, tMinimum, Ray3D.transformT(worldToObject, rayWS, rayOS, tMaximum));
			
			if(!Math.isNaN(tOS)) {
				return Optional.of(new Intersection(this, rayOS, tOS));
			}
		}
		
		return Optional.empty();
	}
	
	public Shape getShape() {
		return this.shape;
	}
	
	public Transform getTransform() {
		return this.transform;
	}
}