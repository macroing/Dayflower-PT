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
	
	public static Shape cylinder() {
		return cylinder(360.0D);
	}
	
	public static Shape cylinder(final double phiMax) {
		return cylinder(phiMax, 1.0D);
	}
	
	public static Shape cylinder(final double phiMax, final double radius) {
		return cylinder(phiMax, radius, 1.0D);
	}
	
	public static Shape cylinder(final double phiMax, final double radius, final double zMax) {
		return cylinder(phiMax, radius, zMax, -1.0D);
	}
	
	public static Shape cylinder(final double phiMax, final double radius, final double zMax, final double zMin) {
		return new Cylinder(Math.toRadians(phiMax), radius, zMax, zMin);
	}
	
	public static Shape disk() {
		return disk(360.0D);
	}
	
	public static Shape disk(final double phiMax) {
		return disk(phiMax, 0.0D);
	}
	
	public static Shape disk(final double phiMax, final double radiusInner) {
		return disk(phiMax, radiusInner, 1.0D);
	}
	
	public static Shape disk(final double phiMax, final double radiusInner, final double radiusOuter) {
		return disk(phiMax, radiusInner, radiusOuter, 0.0D);
	}
	
	public static Shape disk(final double phiMax, final double radiusInner, final double radiusOuter, final double zMax) {
		return new Disk(Math.toRadians(phiMax), radiusInner, radiusOuter, zMax);
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
			final Point3D p = Point3D.add(ray.getOrigin(), ray.getDirection(), t);
			
			final double value = 1.0D - (p.z / this.zMax);
			
			final double uX = -this.phiMax * p.y;
			final double uY = +this.phiMax * p.x;
			final double uZ = +0.0D;
			
			final double vX = -p.x / value;
			final double vY = -p.y / value;
			final double vZ = +this.zMax;
			
			final Vector3D u = Vector3D.normalize(new Vector3D(uX, uY, uZ));
			final Vector3D v = Vector3D.normalize(new Vector3D(vX, vY, vZ));
			final Vector3D w = Vector3D.crossProduct(u, v);
			
			return new OrthonormalBasis33D(w, v, u);
		}
		
		@Override
		public Point2D computeTextureCoordinates(final Ray3D ray, final double t) {
			final Point3D p = Point3D.add(ray.getOrigin(), ray.getDirection(), t);
			
			final double u = p.sphericalPhi() / this.phiMax;
			final double v = p.z / this.zMax;
			
			return new Point2D(u, v);
		}
		
		@Override
		public double intersection(final Ray3D ray, final double tMinimum, final double tMaximum) {
			final Point3D o = ray.getOrigin();
			
			final Vector3D d = ray.getDirection();
			
			final double k = (this.radius / this.zMax) * (this.radius / this.zMax);
			final double a = d.x * d.x + d.y * d.y - k * d.z * d.z;
			final double b = 2.0D * (d.x * o.x + d.y * o.y - k * d.z * (o.z - this.zMax));
			final double c = o.x * o.x + o.y * o.y - k * (o.z - this.zMax) * (o.z - this.zMax);
			
			final double[] ts = Math.solveQuadraticSystem(a, b, c);
			
			for(int i = 0; i < ts.length; i++) {
				final double t = ts[i];
				
				if(Math.isNaN(t)) {
					return Math.NaN;
				}
				
				if(t > tMinimum && t < tMaximum) {
					final Point3D p = Point3D.add(o, d, t);
					
					if(p.z >= 0.0D && p.z <= this.zMax && p.sphericalPhi() <= this.phiMax) {
						return t;
					}
				}
			}
			
			return Math.NaN;
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final class Cylinder extends Shape {
		private final double phiMax;
		private final double radius;
		private final double zMax;
		private final double zMin;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public Cylinder(final double phiMax, final double radius, final double zMax, final double zMin) {
			this.phiMax = phiMax;
			this.radius = radius;
			this.zMax = zMax;
			this.zMin = zMin;
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		@Override
		public OrthonormalBasis33D computeOrthonormalBasis(final Ray3D ray, final double t) {
			final Point3D p = Point3D.add(ray.getOrigin(), ray.getDirection(), t);
			
			final double uX = -this.phiMax * p.y;
			final double uY = +this.phiMax * p.x;
			final double uZ = +0.0D;
			
			final double vX = 0.0D;
			final double vY = 0.0D;
			final double vZ = this.zMax - this.zMin;
			
			final Vector3D u = Vector3D.normalize(new Vector3D(uX, uY, uZ));
			final Vector3D v = Vector3D.normalize(new Vector3D(vX, vY, vZ));
			final Vector3D w = Vector3D.crossProduct(u, v);
			
			return new OrthonormalBasis33D(w, v, u);
		}
		
		@Override
		public Point2D computeTextureCoordinates(final Ray3D ray, final double t) {
			final Point3D p = Point3D.add(ray.getOrigin(), ray.getDirection(), t);
			
			final double u = p.sphericalPhi() / this.phiMax;
			final double v = (p.z - this.zMin) / (this.zMax - this.zMin);
			
			return new Point2D(u, v);
		}
		
		@Override
		public double intersection(final Ray3D ray, final double tMinimum, final double tMaximum) {
			final Point3D o = ray.getOrigin();
			
			final Vector3D d = ray.getDirection();
			
			final double a = d.x * d.x + d.y * d.y;
			final double b = 2.0D * (d.x * o.x + d.y * o.y);
			final double c = o.x * o.x + o.y * o.y - this.radius * this.radius;
			
			final double[] ts = Math.solveQuadraticSystem(a, b, c);
			
			for(int i = 0; i < ts.length; i++) {
				final double t = ts[i];
				
				if(Math.isNaN(t)) {
					return Math.NaN;
				}
				
				if(t > tMinimum && t < tMaximum) {
					final Point3D p = Point3D.add(o, d, t);
					
					if(p.z >= this.zMin && p.z <= this.zMax && p.sphericalPhi() <= this.phiMax) {
						return t;
					}
				}
			}
			
			return Math.NaN;
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final class Disk extends Shape {
		private final double phiMax;
		private final double radiusInner;
		private final double radiusOuter;
		private final double zMax;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public Disk(final double phiMax, final double radiusInner, final double radiusOuter, final double zMax) {
			this.phiMax = phiMax;
			this.radiusInner = radiusInner;
			this.radiusOuter = radiusOuter;
			this.zMax = zMax;
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		@Override
		public OrthonormalBasis33D computeOrthonormalBasis(final Ray3D ray, final double t) {
			final Point3D p = Point3D.add(ray.getOrigin(), ray.getDirection(), t);
			
			final double length = Math.sqrt(p.x * p.x + p.y * p.y);
			
			final double uX = -this.phiMax * p.y;
			final double uY = +this.phiMax * p.x;
			final double uZ = +0.0D;
			
			final double vX = p.x * (this.radiusInner - this.radiusOuter) / length;
			final double vY = p.y * (this.radiusInner - this.radiusOuter) / length;
			final double vZ = 0.0D;
			
			final Vector3D u = Vector3D.normalize(new Vector3D(uX, uY, uZ));
			final Vector3D v = Vector3D.normalize(new Vector3D(vX, vY, vZ));
			final Vector3D w = Vector3D.crossProduct(u, v);
			
			return new OrthonormalBasis33D(w, v, u);
		}
		
		@Override
		public Point2D computeTextureCoordinates(final Ray3D ray, final double t) {
			final Point3D p = Point3D.add(ray.getOrigin(), ray.getDirection(), t);
			
			final double length = Math.sqrt(p.x * p.x + p.y * p.y);
			
			final double u = p.sphericalPhi() / this.phiMax;
			final double v = (this.radiusOuter - length) / (this.radiusOuter - this.radiusInner);
			
			return new Point2D(u, v);
		}
		
		@Override
		public double intersection(final Ray3D ray, final double tMinimum, final double tMaximum) {
			final Point3D o = ray.getOrigin();
			
			final Vector3D d = ray.getDirection();
			
			if(Math.isZero(d.z)) {
				return Math.NaN;
			}
			
			final double t = (this.zMax - o.z) / d.z;
			
			if(t <= tMinimum || t >= tMaximum) {
				return Math.NaN;
			}
			
			final Point3D p = Point3D.add(o, d, t);
			
			final double lengthSquared = p.x * p.x + p.y * p.y;
			
			if(lengthSquared > this.radiusOuter * this.radiusOuter || lengthSquared < this.radiusInner * this.radiusInner || p.sphericalPhi() > this.phiMax) {
				return Math.NaN;
			}
			
			return t;
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
			final Point3D p = Point3D.add(ray.getOrigin(), ray.getDirection(), t);
			
			final Vector3D w = Vector3D.directionNormalized(this.center, p);
			final Vector3D v = Vector3D.normalize(Vector3D.crossProduct(Vector3D.normalize(new Vector3D(-(Math.PI * 2.0D) * w.y, Math.PI * 2.0D * w.x, 0.0D)), w));
			
			return new OrthonormalBasis33D(w, v);
		}
		
		@Override
		public Point2D computeTextureCoordinates(final Ray3D ray, final double t) {
			final Point3D p = Point3D.add(ray.getOrigin(), ray.getDirection(), t);
			
			final Vector3D n = Vector3D.directionNormalized(this.center, p);
			
			final double u = Math.getOrAdd(Math.atan2(n.y, n.x), 0.0D, 2.0D * Math.PI) / (2.0D * Math.PI);
			final double v = Math.acos(Math.max(Math.min(n.z, 1.0D), -1.0D)) / Math.PI;
			
			return new Point2D(u, v);
		}
		
		@Override
		public double intersection(final Ray3D ray, final double tMinimum, final double tMaximum) {
			final Point3D o = ray.getOrigin();
			
			final Vector3D d = ray.getDirection();
			final Vector3D e = Vector3D.direction(this.center, o);
			
			final double a = d.lengthSquared();
			final double b = 2.0D * Vector3D.dotProduct(e, d);
			final double c = e.lengthSquared() - this.radius * this.radius;
			
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