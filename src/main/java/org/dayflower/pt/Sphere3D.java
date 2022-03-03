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

public final class Sphere3D implements Shape3D {
	private final Point3D center;
	private final double radius;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Sphere3D(final Point3D center, final double radius) {
		this.center = Objects.requireNonNull(center, "center == null");
		this.radius = radius;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public Point2D computeTextureCoordinates(final Ray3D ray, final double t) {
		final Vector3D surfaceNormal = computeSurfaceNormal(ray, t);
		
		final double u = Math.getOrAdd(Math.atan2(surfaceNormal.y, surfaceNormal.x), 0.0D, 2.0D * Math.PI) / (2.0D * Math.PI);
		final double v = Math.acos(Math.max(Math.min(surfaceNormal.z, 1.0D), -1.0D)) / Math.PI;
		
		return new Point2D(u, v);
	}
	
	@Override
	public Vector3D computeSurfaceNormal(final Ray3D ray, final double t) {
		return Vector3D.directionNormalized(this.center, Point3D.add(ray.getOrigin(), ray.getDirection(), t));
	}
	
	@Override
	public double intersection(final Ray3D ray, final double tMinimum, final double tMaximum) {
		final Point3D origin = ray.getOrigin();
		
		final Vector3D direction = ray.getDirection();
		final Vector3D centerToOrigin = Vector3D.direction(this.center, origin);
		
		final double a = direction.lengthSquared();
		final double b = 2.0D * Vector3D.dotProduct(centerToOrigin, direction);
		final double c = centerToOrigin.lengthSquared() - this.radius * this.radius;
		
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
}