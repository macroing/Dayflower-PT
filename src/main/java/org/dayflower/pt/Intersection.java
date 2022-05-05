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
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

public final class Intersection {
	private final Primitive primitive;
	private final Ray3D rayOS;
	private final Ray3D rayWS;
	private final Supplier<OrthonormalBasis33D> orthonormalBasisOS;
	private final Supplier<OrthonormalBasis33D> orthonormalBasisWS;
	private final Supplier<Point2D> textureCoordinates;
	private final Supplier<Point3D> surfaceIntersectionPointOS;
	private final Supplier<Point3D> surfaceIntersectionPointWS;
	private final double tOS;
	private final double tWS;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add unit tests!
	public Intersection(final Primitive primitive, final Ray3D rayOS, final double tOS) {
		this.primitive = Objects.requireNonNull(primitive, "primitive == null");
		this.rayOS = Objects.requireNonNull(rayOS, "rayOS == null");
		this.rayWS = Ray3D.transform(getPrimitive().getTransform().getObjectToWorld(), getRayOS());
		this.tOS = tOS;
		this.tWS = Ray3D.transformT(getPrimitive().getTransform().getObjectToWorld(), getRayOS(), getRayWS(), getTOS());
		this.orthonormalBasisOS = new LazySupplier<>(() -> getPrimitive().getShape().computeOrthonormalBasis(getRayOS(), getTOS()));
		this.orthonormalBasisWS = new LazySupplier<>(() -> OrthonormalBasis33D.transformTranspose(getPrimitive().getTransform().getWorldToObject(), getOrthonormalBasisOS()));
		this.textureCoordinates = new LazySupplier<>(() -> getPrimitive().getShape().computeTextureCoordinates(getRayOS(), getTOS()));
		this.surfaceIntersectionPointOS = new LazySupplier<>(() -> Point3D.add(getRayOS().getOrigin(), getRayOS().getDirection(), getTOS()));
		this.surfaceIntersectionPointWS = new LazySupplier<>(() -> Point3D.transformAndDivide(getPrimitive().getTransform().getObjectToWorld(), getSurfaceIntersectionPointOS()));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add unit tests!
	public OrthonormalBasis33D getOrthonormalBasisOS() {
		return this.orthonormalBasisOS.get();
	}
	
//	TODO: Add unit tests!
	public OrthonormalBasis33D getOrthonormalBasisWS() {
		return this.orthonormalBasisWS.get();
	}
	
//	TODO: Add unit tests!
	public Point2D getTextureCoordinates() {
		return this.textureCoordinates.get();
	}
	
//	TODO: Add unit tests!
	public Point3D getSurfaceIntersectionPointOS() {
		return this.surfaceIntersectionPointOS.get();
	}
	
//	TODO: Add unit tests!
	public Point3D getSurfaceIntersectionPointWS() {
		return this.surfaceIntersectionPointWS.get();
	}
	
//	TODO: Add unit tests!
	public Primitive getPrimitive() {
		return this.primitive;
	}
	
//	TODO: Add unit tests!
	public Ray3D getRayOS() {
		return this.rayOS;
	}
	
//	TODO: Add unit tests!
	public Ray3D getRayWS() {
		return this.rayWS;
	}
	
//	TODO: Add unit tests!
	public Vector3D getSurfaceNormalOS() {
		return getOrthonormalBasisOS().w;
	}
	
//	TODO: Add unit tests!
	public Vector3D getSurfaceNormalOSCorrectlyOriented() {
		return Vector3D.faceForwardLHSNegated(getSurfaceNormalOS(), getRayOS().getDirection());
	}
	
//	TODO: Add unit tests!
	public Vector3D getSurfaceNormalWS() {
		return getOrthonormalBasisWS().w;
	}
	
//	TODO: Add unit tests!
	public Vector3D getSurfaceNormalWSCorrectlyOriented() {
		return Vector3D.faceForwardLHSNegated(getSurfaceNormalWS(), getRayWS().getDirection());
	}
	
//	TODO: Add unit tests!
	public double getTOS() {
		return this.tOS;
	}
	
//	TODO: Add unit tests!
	public double getTWS() {
		return this.tWS;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final class LazySupplier<T> implements Supplier<T> {
		private final AtomicReference<T> value;
		private final Supplier<T> valueSupplier;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public LazySupplier(final Supplier<T> valueSupplier) {
			this.value = new AtomicReference<>();
			this.valueSupplier = Objects.requireNonNull(valueSupplier, "valueSupplier == null");
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		@Override
		public T get() {
			return this.value.updateAndGet(value -> value != null ? value : this.valueSupplier.get());
		}
	}
}