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

public final class Shape {
	private final IntersectionFunction intersectionFunction;
	private final SurfaceNormalFunction surfaceNormalFunction;
	private final TextureCoordinatesFunction textureCoordinatesFunction;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private Shape(final IntersectionFunction intersectionFunction, final SurfaceNormalFunction surfaceNormalFunction, final TextureCoordinatesFunction textureCoordinatesFunction) {
		this.intersectionFunction = Objects.requireNonNull(intersectionFunction, "intersectionFunction == null");
		this.surfaceNormalFunction = Objects.requireNonNull(surfaceNormalFunction, "surfaceNormalFunction == null");
		this.textureCoordinatesFunction = Objects.requireNonNull(textureCoordinatesFunction, "textureCoordinatesFunction == null");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Point2D computeTextureCoordinates(final Ray3D ray, final double t) {
		return this.textureCoordinatesFunction.computeTextureCoordinates(ray, t);
	}
	
	public Vector3D computeSurfaceNormal(final Ray3D ray, final double t) {
		return this.surfaceNormalFunction.computeSurfaceNormal(ray, t);
	}
	
	public double intersection(final Ray3D ray, final double tMinimum, final double tMaximum) {
		return this.intersectionFunction.intersection(ray, tMinimum, tMaximum);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static Shape sphere(final Point3D center, final double radius) {
		Objects.requireNonNull(center, "center == null");
		
		final IntersectionFunction intersectionFunction = (ray, tMinimum, tMaximum) -> doIntersectionSphere(ray, tMinimum, tMaximum, center, radius);
		final SurfaceNormalFunction surfaceNormalFunction = (ray, t) -> doComputeSurfaceNormalSphere(ray, t, center);
		final TextureCoordinatesFunction textureCoordinatesFunction = (ray, t) -> doComputeTextureCoordinatesSphere(ray, t, center);
		
		return new Shape(intersectionFunction, surfaceNormalFunction, textureCoordinatesFunction);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static Point2D doComputeTextureCoordinatesSphere(final Ray3D ray, final double t, final Point3D center) {
		final Vector3D surfaceNormal = doComputeSurfaceNormalSphere(ray, t, center);
		
		final double u = Math.getOrAdd(Math.atan2(surfaceNormal.y, surfaceNormal.x), 0.0D, 2.0D * Math.PI) / (2.0D * Math.PI);
		final double v = Math.acos(Math.max(Math.min(surfaceNormal.z, 1.0D), -1.0D)) / Math.PI;
		
		return new Point2D(u, v);
	}
	
	private static Vector3D doComputeSurfaceNormalSphere(final Ray3D ray, final double t, final Point3D center) {
		return Vector3D.directionNormalized(center, Point3D.add(ray.getOrigin(), ray.getDirection(), t));
	}
	
	private static double doIntersectionSphere(final Ray3D ray, final double tMinimum, final double tMaximum, final Point3D center, final double radius) {
		final Point3D origin = ray.getOrigin();
		
		final Vector3D direction = ray.getDirection();
		final Vector3D centerToOrigin = Vector3D.direction(center, origin);
		
		final double a = direction.lengthSquared();
		final double b = 2.0D * Vector3D.dotProduct(centerToOrigin, direction);
		final double c = centerToOrigin.lengthSquared() - radius * radius;
		
		final double[] ts = Math.solveQuadraticSystem(a, b, c);
		
		final double t0 = ts[0];
		final double t1 = ts[1];
		
		if(!Math.isNaN(t0) && t0 > tMinimum && t0 < tMaximum) {
			return t0;
		}
		
		if(!Math.isNaN(t1) && t1 > tMinimum && t1 < tMaximum) {
			return t1;
		}
		
		return Math.NaN;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static interface IntersectionFunction {
		double intersection(final Ray3D ray, final double tMinimum, final double tMaximum);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static interface SurfaceNormalFunction {
		Vector3D computeSurfaceNormal(final Ray3D ray, final double t);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static interface TextureCoordinatesFunction {
		Point2D computeTextureCoordinates(final Ray3D ray, final double t);
	}
}