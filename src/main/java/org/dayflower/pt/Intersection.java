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

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

import org.macroing.geo4j.common.Point2D;
import org.macroing.geo4j.common.Point3D;
import org.macroing.geo4j.common.Vector3D;
import org.macroing.geo4j.onb.OrthonormalBasis33D;
import org.macroing.geo4j.ray.Ray3D;

/**
 * An {@code Intersection} represents an intersection.
 * <p>
 * This class is indirectly mutable and not thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
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
	
	/**
	 * Constructs a new {@code Intersection} instance.
	 * <p>
	 * If either {@code primitive} or {@code rayOS} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param primitive the {@link Primitive} instance
	 * @param rayOS a {@link Ray3D} instance with the ray in object space
	 * @param tOS a {@code double} with the parametric distance (t) to the intersection in object space
	 * @throws NullPointerException thrown if, and only if, either {@code primitive} or {@code rayOS} are {@code null}
	 */
	public Intersection(final Primitive primitive, final Ray3D rayOS, final double tOS) {
		this.primitive = Objects.requireNonNull(primitive, "primitive == null");
		this.rayOS = Objects.requireNonNull(rayOS, "rayOS == null");
		this.rayWS = getPrimitive().getTransform().getObjectToWorld().transform(getRayOS());
		this.tOS = tOS;
		this.tWS = getPrimitive().getTransform().getObjectToWorld().transformT(getRayOS(), getRayWS(), getTOS());
		this.orthonormalBasisOS = new LazySupplier<>(() -> getPrimitive().getShape().computeOrthonormalBasis(getRayOS(), getTOS()));
		this.orthonormalBasisWS = new LazySupplier<>(() -> getOrthonormalBasisOS().transformTranspose(getPrimitive().getTransform().getWorldToObject()));
		this.textureCoordinates = new LazySupplier<>(() -> getPrimitive().getShape().computeTextureCoordinates(getRayOS(), getTOS()));
		this.surfaceIntersectionPointOS = new LazySupplier<>(() -> Point3D.add(getRayOS().getOrigin(), getRayOS().getDirection(), getTOS()));
		this.surfaceIntersectionPointWS = new LazySupplier<>(() -> getPrimitive().getTransform().getObjectToWorld().transformAndDivide(getSurfaceIntersectionPointOS()));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns an {@link OrthonormalBasis33D} instance with the orthonormal basis in object space.
	 * 
	 * @return an {@code OrthonormalBasis33D} instance with the orthonormal basis in object space
	 */
	public OrthonormalBasis33D getOrthonormalBasisOS() {
		return this.orthonormalBasisOS.get();
	}
	
	/**
	 * Returns an {@link OrthonormalBasis33D} instance with the orthonormal basis in world space.
	 * 
	 * @return an {@code OrthonormalBasis33D} instance with the orthonormal basis in world space
	 */
	public OrthonormalBasis33D getOrthonormalBasisWS() {
		return this.orthonormalBasisWS.get();
	}
	
	/**
	 * Returns a {@link Point2D} instance with the texture coordinates.
	 * 
	 * @return a {@code Point2D} instance with the texture coordinates
	 */
	public Point2D getTextureCoordinates() {
		return this.textureCoordinates.get();
	}
	
	/**
	 * Returns a {@link Point3D} instance with the surface intersection point in object space.
	 * 
	 * @return a {@code Point3D} instance with the surface intersection point in object space
	 */
	public Point3D getSurfaceIntersectionPointOS() {
		return this.surfaceIntersectionPointOS.get();
	}
	
	/**
	 * Returns a {@link Point3D} instance with the surface intersection point in world space.
	 * 
	 * @return a {@code Point3D} instance with the surface intersection point in world space
	 */
	public Point3D getSurfaceIntersectionPointWS() {
		return this.surfaceIntersectionPointWS.get();
	}
	
	/**
	 * Returns the {@link Primitive} instance.
	 * 
	 * @return the {@code Primitive} instance
	 */
	public Primitive getPrimitive() {
		return this.primitive;
	}
	
	/**
	 * Returns a {@link Ray3D} instance with the ray in object space.
	 * 
	 * @return a {@code Ray3D} instance with the ray in object space
	 */
	public Ray3D getRayOS() {
		return this.rayOS;
	}
	
	/**
	 * Returns a {@link Ray3D} instance with the ray in world space.
	 * 
	 * @return a {@code Ray3D} instance with the ray in world space
	 */
	public Ray3D getRayWS() {
		return this.rayWS;
	}
	
	/**
	 * Returns a {@link Vector3D} instance with the surface normal in object space.
	 * 
	 * @return a {@code Vector3D} instance with the surface normal in object space
	 */
	public Vector3D getSurfaceNormalOS() {
		return getOrthonormalBasisOS().w;
	}
	
	/**
	 * Returns a {@link Vector3D} instance with the correctly oriented surface normal in object space.
	 * 
	 * @return a {@code Vector3D} instance with the correctly oriented surface normal in object space
	 */
	public Vector3D getSurfaceNormalOSCorrectlyOriented() {
		return Vector3D.dotProduct(getSurfaceNormalOS(), getRayOS().getDirection()) > 0.0D ? Vector3D.negate(getSurfaceNormalOS()) : getSurfaceNormalOS();
	}
	
	/**
	 * Returns a {@link Vector3D} instance with the surface normal in world space.
	 * 
	 * @return a {@code Vector3D} instance with the surface normal in world space
	 */
	public Vector3D getSurfaceNormalWS() {
		return getOrthonormalBasisWS().w;
	}
	
	/**
	 * Returns a {@link Vector3D} instance with the correctly oriented surface normal in world space.
	 * 
	 * @return a {@code Vector3D} instance with the correctly oriented surface normal in world space
	 */
	public Vector3D getSurfaceNormalWSCorrectlyOriented() {
		return Vector3D.dotProduct(getSurfaceNormalWS(), getRayOS().getDirection()) > 0.0D ? Vector3D.negate(getSurfaceNormalWS()) : getSurfaceNormalWS();
	}
	
	/**
	 * Returns a {@code double} with the parametric distance (t) to the intersection in object space.
	 * 
	 * @return a {@code double} with the parametric distance (t) to the intersection in object space
	 */
	public double getTOS() {
		return this.tOS;
	}
	
	/**
	 * Returns a {@code double} with the parametric distance (t) to the intersection in world space.
	 * 
	 * @return a {@code double} with the parametric distance (t) to the intersection in world space
	 */
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
			return this.value.updateAndGet(value -> value != null ? value : Objects.requireNonNull(this.valueSupplier.get(), "valueSupplier.get() == null"));
		}
	}
}