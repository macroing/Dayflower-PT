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

public abstract class Shape {
	protected Shape() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public abstract OrthonormalBasis33D computeOrthonormalBasis(final Ray3D ray, final double t);
	
	public abstract Point2D computeTextureCoordinates(final Ray3D ray, final double t);
	
	public abstract Vector3D computeSurfaceNormal(final Ray3D ray, final double t);
	
	public abstract double intersection(final Ray3D ray, final double tMinimum, final double tMaximum);
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static Shape cone() {
		return cone(360.0D);
	}
	
	public static Shape cone(final double phiMax) {
		return cone(phiMax, 1.0D);
	}
	
	public static Shape cone(final double phiMax, final double radius) {
		return cone(phiMax, radius, 1.0D);
	}
	
	public static Shape cone(final double phiMax, final double radius, final double zMax) {
		return new Cone(Math.toRadians(phiMax), radius, zMax);
	}
	
	public static Shape sphere() {
		return sphere(new Point3D());
	}
	
	public static Shape sphere(final Point3D center) {
		return sphere(center, 1.0D);
	}
	
	public static Shape sphere(final Point3D center, final double radius) {
		Objects.requireNonNull(center, "center == null");
		
		return new Sphere(center, radius);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final class Cone extends Shape {
		private final double phiMax;
		private final double radius;
		private final double zMax;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public Cone(final double phiMax, final double radius, final double zMax) {
			this.phiMax = phiMax;
			this.radius = radius;
			this.zMax = zMax;
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		@Override
		public OrthonormalBasis33D computeOrthonormalBasis(final Ray3D ray, final double t) {
			final Point3D surfaceIntersectionPoint = Point3D.add(ray.getOrigin(), ray.getDirection(), t);
			
			final double value = 1.0D - (surfaceIntersectionPoint.z / this.zMax);
			
			final Vector3D u = Vector3D.normalize(new Vector3D(-this.phiMax * surfaceIntersectionPoint.y, this.phiMax * surfaceIntersectionPoint.x, 0.0D));
			final Vector3D v = Vector3D.normalize(new Vector3D(-surfaceIntersectionPoint.x / value, -surfaceIntersectionPoint.y / value, this.zMax));
			final Vector3D w = Vector3D.crossProduct(u, v);
			
			return new OrthonormalBasis33D(w, v, u);
		}
		
		@Override
		public Point2D computeTextureCoordinates(final Ray3D ray, final double t) {
			final Point3D surfaceIntersectionPoint = Point3D.add(ray.getOrigin(), ray.getDirection(), t);
			
			final double u = surfaceIntersectionPoint.sphericalPhi() / this.phiMax;
			final double v = surfaceIntersectionPoint.z / this.zMax;
			
			return new Point2D(u, v);
		}
		
		@Override
		public Vector3D computeSurfaceNormal(final Ray3D ray, final double t) {
			return computeOrthonormalBasis(ray, t).w;
		}
		
		@Override
		public double intersection(final Ray3D ray, final double tMinimum, final double tMaximum) {
			final Point3D origin = ray.getOrigin();
			
			final Vector3D direction = ray.getDirection();
			
			final double k = (this.radius / this.zMax) * (this.radius / this.zMax);
			final double a = direction.x * direction.x + direction.y * direction.y - k * direction.z * direction.z;
			final double b = 2.0D * (direction.x * origin.x + direction.y * origin.y - k * direction.z * (origin.z - this.zMax));
			final double c = origin.x * origin.x + origin.y * origin.y - k * (origin.z - this.zMax) * (origin.z - this.zMax);
			
			final double[] ts = Math.solveQuadraticSystem(a, b, c);
			
			for(int i = 0; i < ts.length; i++) {
				final double t = ts[i];
				
				if(Math.isNaN(t)) {
					return Math.NaN;
				}
				
				if(t > tMinimum && t < tMaximum) {
					final Point3D surfaceIntersectionPoint = Point3D.add(origin, direction, t);
					
					if(surfaceIntersectionPoint.z >= 0.0D && surfaceIntersectionPoint.z <= this.zMax && surfaceIntersectionPoint.sphericalPhi() <= this.phiMax) {
						return t;
					}
				}
			}
			
			return Math.NaN;
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final class Sphere extends Shape {
		private final Point3D center;
		private final double radius;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public Sphere(final Point3D center, final double radius) {
			this.center = Objects.requireNonNull(center, "center == null");
			this.radius = radius;
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		@Override
		public OrthonormalBasis33D computeOrthonormalBasis(final Ray3D ray, final double t) {
			final Vector3D w = computeSurfaceNormal(ray, t);
			final Vector3D v = Vector3D.normalize(Vector3D.crossProduct(Vector3D.normalize(new Vector3D(-(Math.PI * 2.0D) * w.y, Math.PI * 2.0D * w.x, 0.0D)), w));
			
			return new OrthonormalBasis33D(w, v);
		}
		
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
}