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
import org.macroing.java.lang.Ints;
import org.macroing.java.util.Arrays;

public abstract class Shape {
	protected Shape() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public abstract BoundingVolume getBoundingVolume();
	
	public abstract OrthonormalBasis33D computeOrthonormalBasis(final Ray3D ray, final double t);
	
	public abstract Point2D computeTextureCoordinates(final Ray3D ray, final double t);
	
	public abstract boolean contains(final Point3D p);
	
	public abstract double intersection(final Ray3D ray, final double tMinimum, final double tMaximum);
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add unit tests!
	public static Shape cone() {
		return cone(360.0D);
	}
	
//	TODO: Add unit tests!
	public static Shape cone(final double phiMax) {
		return cone(phiMax, 1.0D);
	}
	
//	TODO: Add unit tests!
	public static Shape cone(final double phiMax, final double radius) {
		return cone(phiMax, radius, 1.0D);
	}
	
//	TODO: Add unit tests!
	public static Shape cone(final double phiMax, final double radius, final double zMax) {
		return new Cone(Doubles.toRadians(phiMax), radius, zMax);
	}
	
//	TODO: Add unit tests!
	public static Shape cylinder() {
		return cylinder(360.0D);
	}
	
//	TODO: Add unit tests!
	public static Shape cylinder(final double phiMax) {
		return cylinder(phiMax, 1.0D);
	}
	
//	TODO: Add unit tests!
	public static Shape cylinder(final double phiMax, final double radius) {
		return cylinder(phiMax, radius, 1.0D);
	}
	
//	TODO: Add unit tests!
	public static Shape cylinder(final double phiMax, final double radius, final double zMax) {
		return cylinder(phiMax, radius, zMax, -1.0D);
	}
	
//	TODO: Add unit tests!
	public static Shape cylinder(final double phiMax, final double radius, final double zMax, final double zMin) {
		return new Cylinder(Doubles.toRadians(phiMax), radius, zMax, zMin);
	}
	
//	TODO: Add unit tests!
	public static Shape disk() {
		return disk(360.0D);
	}
	
//	TODO: Add unit tests!
	public static Shape disk(final double phiMax) {
		return disk(phiMax, 0.0D);
	}
	
//	TODO: Add unit tests!
	public static Shape disk(final double phiMax, final double radiusInner) {
		return disk(phiMax, radiusInner, 1.0D);
	}
	
//	TODO: Add unit tests!
	public static Shape disk(final double phiMax, final double radiusInner, final double radiusOuter) {
		return disk(phiMax, radiusInner, radiusOuter, 0.0D);
	}
	
//	TODO: Add unit tests!
	public static Shape disk(final double phiMax, final double radiusInner, final double radiusOuter, final double zMax) {
		return new Disk(Doubles.toRadians(phiMax), radiusInner, radiusOuter, zMax);
	}
	
//	TODO: Add unit tests!
	public static Shape hyperboloid() {
		return hyperboloid(new Point3D(0.0001D, 0.0001D, 0.0D));
	}
	
//	TODO: Add unit tests!
	public static Shape hyperboloid(final Point3D a) {
		return hyperboloid(a, new Point3D(1.0D, 1.0D, 1.0D));
	}
	
//	TODO: Add unit tests!
	public static Shape hyperboloid(final Point3D a, final Point3D b) {
		return hyperboloid(a, b, 360.0D);
	}
	
//	TODO: Add unit tests!
	public static Shape hyperboloid(final Point3D a, final Point3D b, final double phiMax) {
		return Hyperboloid.create(a, b, Doubles.toRadians(phiMax));
	}
	
//	TODO: Add unit tests!
	public static Shape paraboloid() {
		return paraboloid(360.0D);
	}
	
//	TODO: Add unit tests!
	public static Shape paraboloid(final double phiMax) {
		return paraboloid(phiMax, 1.0D);
	}
	
//	TODO: Add unit tests!
	public static Shape paraboloid(final double phiMax, final double radius) {
		return paraboloid(phiMax, radius, 1.0D);
	}
	
//	TODO: Add unit tests!
	public static Shape paraboloid(final double phiMax, final double radius, final double zMax) {
		return paraboloid(phiMax, radius, zMax, 0.0D);
	}
	
//	TODO: Add unit tests!
	public static Shape paraboloid(final double phiMax, final double radius, final double zMax, final double zMin) {
		return new Paraboloid(Doubles.toRadians(phiMax), radius, zMax, zMin);
	}
	
//	TODO: Add unit tests!
	public static Shape plane() {
		return plane(new Point3D(0.0D, 0.0D, 0.0D), new Point3D(0.0D, 0.0D, 1.0D), new Point3D(1.0D, 0.0D, 0.0D));
	}
	
//	TODO: Add unit tests!
	public static Shape plane(final Point3D a, final Point3D b, final Point3D c) {
		return new Plane(a, b, c);
	}
	
//	TODO: Add unit tests!
	public static Shape polygon(final Point3D... points) {
		return Polygon.create(points);
	}
	
//	TODO: Add unit tests!
	public static Shape rectangle() {
		return rectangle(new Point3D(-2.0D, +2.0D, 0.0D), new Point3D(+2.0D, +2.0D, 0.0D), new Point3D(+2.0D, -2.0D, 0.0D), new Point3D(-2.0D, -2.0D, 0.0D));
	}
	
//	TODO: Add unit tests!
	public static Shape rectangle(final Point3D a, final Point3D b, final Point3D c, final Point3D d) {
		return Rectangle.create(a, b, c, d);
	}
	
//	TODO: Add unit tests!
	public static Shape rectangularCuboid() {
		return rectangularCuboid(new Point3D(-0.5D, -0.5D, -0.5D), new Point3D(0.5D, 0.5D, 0.5D));
	}
	
//	TODO: Add unit tests!
	public static Shape rectangularCuboid(final Point3D a, final Point3D b) {
		return RectangularCuboid.create(a, b);
	}
	
//	TODO: Add unit tests!
	public static Shape sphere() {
		return sphere(new Point3D());
	}
	
//	TODO: Add unit tests!
	public static Shape sphere(final Point3D center) {
		return sphere(center, 1.0D);
	}
	
//	TODO: Add unit tests!
	public static Shape sphere(final Point3D center, final double radius) {
		return new Sphere(center, radius);
	}
	
//	TODO: Add unit tests!
	public static Shape torus() {
		return torus(0.25D, 1.0D);
	}
	
//	TODO: Add unit tests!
	public static Shape torus(final double radiusInner, final double radiusOuter) {
		return new Torus(radiusInner, radiusOuter);
	}
	
//	TODO: Add unit tests!
	public static Shape triangle() {
		return triangle(new Point3D(0.0D, 1.0D, 0.0D), new Point3D(1.0D, -1.0D, 0.0D), new Point3D(-1.0D, -1.0D, 0.0D));
	}
	
//	TODO: Add unit tests!
	public static Shape triangle(final Point3D a, final Point3D b, final Point3D c) {
		return new Triangle(a, b, c);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add unit tests!
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
		public BoundingVolume getBoundingVolume() {
			return BoundingVolume.axisAlignedBoundingBox(new Point3D(-this.radius, -this.radius, 0.0D), new Point3D(this.radius, this.radius, this.zMax));
		}
		
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
		public boolean contains(final Point3D p) {
			return p.z >= 0.0D && p.z <= this.zMax && p.sphericalPhi() <= this.phiMax;
		}
		
		@Override
		public double intersection(final Ray3D ray, final double tMinimum, final double tMaximum) {
			final Point3D o = ray.getOrigin();
			
			final Vector3D d = ray.getDirection();
			
			final double k = (this.radius / this.zMax) * (this.radius / this.zMax);
			final double a = d.x * d.x + d.y * d.y - k * d.z * d.z;
			final double b = 2.0D * (d.x * o.x + d.y * o.y - k * d.z * (o.z - this.zMax));
			final double c = o.x * o.x + o.y * o.y - k * (o.z - this.zMax) * (o.z - this.zMax);
			
			final double[] ts = Doubles.solveQuadraticSystem(a, b, c);
			
			for(int i = 0; i < ts.length; i++) {
				final double t = ts[i];
				
				if(Doubles.isNaN(t)) {
					return Doubles.NaN;
				}
				
				if(t > tMinimum && t < tMaximum) {
					final Point3D p = Point3D.add(o, d, t);
					
					if(p.z >= 0.0D && p.z <= this.zMax && p.sphericalPhi() <= this.phiMax) {
						return t;
					}
				}
			}
			
			return Doubles.NaN;
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add unit tests!
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
		public BoundingVolume getBoundingVolume() {
			return BoundingVolume.axisAlignedBoundingBox(new Point3D(-this.radius, -this.radius, this.zMin), new Point3D(this.radius, this.radius, this.zMax));
		}
		
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
		public boolean contains(final Point3D p) {
			return p.z >= this.zMin && p.z <= this.zMax && p.sphericalPhi() <= this.phiMax;
		}
		
		@Override
		public double intersection(final Ray3D ray, final double tMinimum, final double tMaximum) {
			final Point3D o = ray.getOrigin();
			
			final Vector3D d = ray.getDirection();
			
			final double a = d.x * d.x + d.y * d.y;
			final double b = 2.0D * (d.x * o.x + d.y * o.y);
			final double c = o.x * o.x + o.y * o.y - this.radius * this.radius;
			
			final double[] ts = Doubles.solveQuadraticSystem(a, b, c);
			
			for(int i = 0; i < ts.length; i++) {
				final double t = ts[i];
				
				if(Doubles.isNaN(t)) {
					return Doubles.NaN;
				}
				
				if(t > tMinimum && t < tMaximum) {
					final Point3D p = Point3D.add(o, d, t);
					
					if(p.z >= this.zMin && p.z <= this.zMax && p.sphericalPhi() <= this.phiMax) {
						return t;
					}
				}
			}
			
			return Doubles.NaN;
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add unit tests!
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
		public BoundingVolume getBoundingVolume() {
			return BoundingVolume.axisAlignedBoundingBox(new Point3D(-this.radiusOuter, -this.radiusOuter, this.zMax), new Point3D(this.radiusOuter, this.radiusOuter, this.zMax));
		}
		
		@Override
		public OrthonormalBasis33D computeOrthonormalBasis(final Ray3D ray, final double t) {
			final Point3D p = Point3D.add(ray.getOrigin(), ray.getDirection(), t);
			
			final double length = Doubles.sqrt(p.x * p.x + p.y * p.y);
			
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
			
			final double length = Doubles.sqrt(p.x * p.x + p.y * p.y);
			
			final double u = p.sphericalPhi() / this.phiMax;
			final double v = (this.radiusOuter - length) / (this.radiusOuter - this.radiusInner);
			
			return new Point2D(u, v);
		}
		
		@Override
		public boolean contains(final Point3D p) {
			return p.x * p.x + p.y * p.y <= this.radiusOuter * this.radiusOuter && p.x * p.x + p.y * p.y >= this.radiusInner * this.radiusInner && p.sphericalPhi() <= this.phiMax;
		}
		
		@Override
		public double intersection(final Ray3D ray, final double tMinimum, final double tMaximum) {
			final Point3D o = ray.getOrigin();
			
			final Vector3D d = ray.getDirection();
			
			if(Doubles.isZero(d.z)) {
				return Doubles.NaN;
			}
			
			final double t = (this.zMax - o.z) / d.z;
			
			if(t <= tMinimum || t >= tMaximum) {
				return Doubles.NaN;
			}
			
			final Point3D p = Point3D.add(o, d, t);
			
			final double lengthSquared = p.x * p.x + p.y * p.y;
			
			if(lengthSquared > this.radiusOuter * this.radiusOuter || lengthSquared < this.radiusInner * this.radiusInner || p.sphericalPhi() > this.phiMax) {
				return Doubles.NaN;
			}
			
			return t;
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add unit tests!
	private static final class Hyperboloid extends Shape {
		private final Point3D a;
		private final Point3D b;
		private final double aH;
		private final double cH;
		private final double phiMax;
		private final double rMax;
		private final double zMax;
		private final double zMin;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		private Hyperboloid(final Point3D a, final Point3D b, final double aH, final double cH, final double phiMax, final double rMax, final double zMax, final double zMin) {
			this.a = Objects.requireNonNull(a, "a == null");
			this.b = Objects.requireNonNull(b, "b == null");
			this.aH = aH;
			this.cH = cH;
			this.phiMax = phiMax;
			this.rMax = rMax;
			this.zMax = zMax;
			this.zMin = zMin;
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		@Override
		public BoundingVolume getBoundingVolume() {
			return BoundingVolume.axisAlignedBoundingBox(new Point3D(-this.rMax, -this.rMax, this.zMin), new Point3D(this.rMax, this.rMax, this.zMax));
		}
		
		@Override
		public OrthonormalBasis33D computeOrthonormalBasis(final Ray3D ray, final double t) {
			final Point3D p = Point3D.add(ray.getOrigin(), ray.getDirection(), t);
			
			final double phi = doComputePhi(p);
			final double phiCos = Doubles.cos(phi);
			final double phiSin = Doubles.sin(phi);
			
			final double uX = -this.phiMax * p.y;
			final double uY = +this.phiMax * p.x;
			final double uZ = +0.0D;
			
			final double vX = (this.b.x - this.a.x) * phiCos - (this.b.y - this.a.y) * phiSin;
			final double vY = (this.b.x - this.a.x) * phiSin + (this.b.y - this.a.y) * phiCos;
			final double vZ = this.b.z - this.a.z;
			
			final Vector3D u = Vector3D.normalize(new Vector3D(uX, uY, uZ));
			final Vector3D v = Vector3D.normalize(new Vector3D(vX, vY, vZ));
			final Vector3D w = Vector3D.crossProduct(u, v);
			
			return new OrthonormalBasis33D(w, v, u);
		}
		
		@Override
		public Point2D computeTextureCoordinates(final Ray3D ray, final double t) {
			final Point3D p = Point3D.add(ray.getOrigin(), ray.getDirection(), t);
			
			final double v = (p.z - this.a.z) / (this.b.z - this.a.z);
			final double u = doComputePhi(p, v) / this.phiMax;
			
			return new Point2D(u, v);
		}
		
		@Override
		public boolean contains(final Point3D p) {
			return p.z >= this.zMin && p.z <= this.zMax && doComputePhi(p) <= this.phiMax;
		}
		
		@Override
		public double intersection(final Ray3D ray, final double tMinimum, final double tMaximum) {
			final Point3D o = ray.getOrigin();
			
			final Vector3D d = ray.getDirection();
			
			final double a = this.aH * d.x * d.x + this.aH * d.y * d.y - this.cH * d.z * d.z;
			final double b = 2.0D * (this.aH * d.x * o.x + this.aH * d.y * o.y - this.cH * d.z * o.z);
			final double c = this.aH * o.x * o.x + this.aH * o.y * o.y - this.cH * o.z * o.z - 1.0D;
			
			final double[] ts = Doubles.solveQuadraticSystem(a, b, c);
			
			for(int i = 0; i < ts.length; i++) {
				final double t = ts[i];
				
				if(Doubles.isNaN(t)) {
					return Doubles.NaN;
				}
				
				if(t > tMinimum && t < tMaximum) {
					final Point3D p = Point3D.add(o, d, t);
					
					if(p.z >= this.zMin && p.z <= this.zMax && doComputePhi(p) <= this.phiMax) {
						return t;
					}
				}
			}
			
			return Doubles.NaN;
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public static Hyperboloid create(final Point3D initialA, final Point3D initialB, final double phiMax) {
			Objects.requireNonNull(initialA, "initialA == null");
			Objects.requireNonNull(initialA, "initialA == null");
			
			Point3D a = Doubles.isZero(initialA.z) ? initialA : Doubles.isZero(initialB.z) ? initialB : initialA;
			Point3D b = Doubles.isZero(initialA.z) ? initialB : Doubles.isZero(initialB.z) ? initialA : initialB;
			Point3D c = a;
			
			double aH = Math.POSITIVE_INFINITY;
			double cH = Math.POSITIVE_INFINITY;
			
			for(int i = 0; i < 10 && (Doubles.isInfinite(aH) || Doubles.isNaN(aH)); i++) {
				c = Point3D.add(c, Vector3D.multiply(Vector3D.direction(a, b), 2.0D));
				
				final double d = c.x * c.x + c.y * c.y;
				final double e = b.x * b.x + b.y * b.y;
				
				aH = (1.0D / d - (c.z * c.z) / (d * b.z * b.z)) / (1.0D - (e * c.z * c.z) / (d * b.z * b.z));
				cH = (aH * e - 1.0D) / (b.z * b.z);
			}
			
			if(Doubles.isInfinite(aH) || Doubles.isNaN(aH)) {
				throw new IllegalArgumentException();
			}
			
			final double rMax = Doubles.max(Doubles.sqrt(initialA.x * initialA.x + initialA.y * initialA.y), Doubles.sqrt(initialB.x * initialB.x + initialB.y * initialB.y));
			final double zMax = Doubles.max(initialA.z, initialB.z);
			final double zMin = Doubles.min(initialA.z, initialB.z);
			
			return new Hyperboloid(a, b, aH, cH, phiMax, rMax, zMax, zMin);
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		private double doComputePhi(final Point3D p) {
			return doComputePhi(p, (p.z - this.a.z) / (this.b.z - this.a.z));
		}
		
		private double doComputePhi(final Point3D p, final double v) {
			final Point3D a = Point3D.lerp(this.a, this.b, v);
			final Point3D b = new Point3D(p.x * a.x + p.y * a.y, p.y * a.x - p.x * a.y, 0.0D);
			
			return b.sphericalPhi();
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add unit tests!
	private static final class Paraboloid extends Shape {
		private final double phiMax;
		private final double radius;
		private final double zMax;
		private final double zMin;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public Paraboloid(final double phiMax, final double radius, final double zMax, final double zMin) {
			this.phiMax = phiMax;
			this.radius = radius;
			this.zMax = zMax;
			this.zMin = zMin;
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		@Override
		public BoundingVolume getBoundingVolume() {
			return BoundingVolume.axisAlignedBoundingBox(new Point3D(-this.radius, -this.radius, this.zMin), new Point3D(this.radius, this.radius, this.zMax));
		}
		
		@Override
		public OrthonormalBasis33D computeOrthonormalBasis(final Ray3D ray, final double t) {
			final Point3D p = Point3D.add(ray.getOrigin(), ray.getDirection(), t);
			
			final double uX = -this.phiMax * p.y;
			final double uY = +this.phiMax * p.x;
			final double uZ = +0.0D;
			
			final double vX = (this.zMax - this.zMin) * (p.x / (2.0D * p.z));
			final double vY = (this.zMax - this.zMin) * (p.y / (2.0D * p.z));
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
		public boolean contains(final Point3D p) {
			return p.z >= this.zMin && p.z <= this.zMax && p.sphericalPhi() <= this.phiMax;
		}
		
		@Override
		public double intersection(final Ray3D ray, final double tMinimum, final double tMaximum) {
			final Point3D o = ray.getOrigin();
			
			final Vector3D d = ray.getDirection();
			
			final double k = this.zMax / (this.radius * this.radius);
			
			final double a = k * (d.x * d.x + d.y * d.y);
			final double b = 2.0D * k * (d.x * o.x + d.y * o.y) - d.z;
			final double c = k * (o.x * o.x + o.y * o.y) - o.z;
			
			final double[] ts = Doubles.solveQuadraticSystem(a, b, c);
			
			for(int i = 0; i < ts.length; i++) {
				final double t = ts[i];
				
				if(Doubles.isNaN(t)) {
					return Doubles.NaN;
				}
				
				if(t > tMinimum && t < tMaximum) {
					final Point3D p = Point3D.add(o, d, t);
					
					if(p.z >= this.zMin && p.z <= this.zMax && p.sphericalPhi() <= this.phiMax) {
						return t;
					}
				}
			}
			
			return Doubles.NaN;
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add unit tests!
	private static final class Plane extends Shape {
		private final Point3D a;
		private final Point3D b;
		private final Point3D c;
		private final Vector3D n;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public Plane(final Point3D a, final Point3D b, final Point3D c) {
			this.a = Objects.requireNonNull(a, "a == null");
			this.b = Objects.requireNonNull(b, "b == null");
			this.c = Objects.requireNonNull(c, "c == null");
			this.n = Vector3D.normalNormalized(a, b, c);
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		@Override
		public BoundingVolume getBoundingVolume() {
			return BoundingVolume.infiniteBoundingVolume();
		}
		
		@Override
		public OrthonormalBasis33D computeOrthonormalBasis(final Ray3D ray, final double t) {
			return new OrthonormalBasis33D(this.n);
		}
		
		@Override
		public Point2D computeTextureCoordinates(final Ray3D ray, final double t) {
			final Point3D a = this.a;
			final Point3D b = this.b;
			final Point3D c = this.c;
			final Point3D p = Point3D.add(ray.getOrigin(), ray.getDirection(), t);
			
			final Vector3D n = this.n;
			final Vector3D nAbs = Vector3D.abs(n);
			
			final boolean isX = nAbs.x > nAbs.y && nAbs.x > nAbs.z;
			final boolean isY = nAbs.y > nAbs.z;
			
			final Vector2D vA = isX ? Vector2D.directionYZ(a) : isY ? Vector2D.directionZX(a) : Vector2D.directionXY(a);
			final Vector2D vB = isX ? Vector2D.directionYZ(b) : isY ? Vector2D.directionZX(b) : Vector2D.directionXY(b);
			final Vector2D vC = isX ? Vector2D.directionYZ(c) : isY ? Vector2D.directionZX(c) : Vector2D.directionXY(c);
			final Vector2D vAB = Vector2D.subtract(vB, vA);
			final Vector2D vAC = Vector2D.subtract(vC, vA);
			
			final double determinant = Vector2D.crossProduct(vAC, vAB);
			final double determinantReciprocal = 1.0D / determinant;
			
			final double hU = isX ? p.y : isY ? p.z : p.x;
			final double hV = isX ? p.z : isY ? p.x : p.y;
			
			final double u = hU * (-vAC.y * determinantReciprocal) + hV * (+vAC.x * determinantReciprocal) + Vector2D.crossProduct(vAC, vA) * determinantReciprocal;
			final double v = hU * (+vAB.y * determinantReciprocal) + hV * (-vAB.x * determinantReciprocal) + Vector2D.crossProduct(vAB, vA) * determinantReciprocal;
			
			return new Point2D(u, v);
		}
		
		@Override
		public boolean contains(final Point3D p) {
			return Point3D.coplanar(this.a, this.b, this.c, p);
		}
		
		@Override
		public double intersection(final Ray3D ray, final double tMinimum, final double tMaximum) {
			final Point3D a = this.a;
			final Point3D o = ray.getOrigin();
			
			final Vector3D d = ray.getDirection();
			final Vector3D n = this.n;
			
			final double nDotD = Vector3D.dotProduct(n, d);
			
			if(Doubles.isZero(nDotD)) {
				return Doubles.NaN;
			}
			
			final double t = Vector3D.dotProduct(Vector3D.direction(o, a), n) / nDotD;
			
			if(t > tMinimum && t < tMaximum) {
				return t;
			}
			
			return Doubles.NaN;
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add unit tests!
	private static final class Polygon extends Shape {
		private final Point2D[] point2Ds;
		private final Point3D[] point3Ds;
		private final Vector3D n;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		private Polygon(final Point2D[] point2Ds, final Point3D[] point3Ds, final Vector3D n) {
			this.point2Ds = Arrays.requireNonNull(point2Ds, "point2Ds");
			this.point3Ds = Arrays.requireNonNull(point3Ds, "point3Ds");
			this.n = Objects.requireNonNull(n, "n == null");
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		@Override
		public BoundingVolume getBoundingVolume() {
			return BoundingVolume.axisAlignedBoundingBox(this.point3Ds);
		}
		
		@Override
		public OrthonormalBasis33D computeOrthonormalBasis(final Ray3D ray, final double t) {
			return new OrthonormalBasis33D(this.n);
		}
		
		@Override
		public Point2D computeTextureCoordinates(final Ray3D ray, final double t) {
			final Point3D a = this.point3Ds[0];
			final Point3D b = this.point3Ds[1];
			final Point3D c = this.point3Ds[this.point3Ds.length - 1];
			final Point3D p = Point3D.add(ray.getOrigin(), ray.getDirection(), t);
			
			final Vector3D n = this.n;
			final Vector3D nAbs = Vector3D.abs(n);
			
			final boolean isX = nAbs.x > nAbs.y && nAbs.x > nAbs.z;
			final boolean isY = nAbs.y > nAbs.z;
			
			final Vector2D vA = isX ? Vector2D.directionYZ(a) : isY ? Vector2D.directionZX(a) : Vector2D.directionXY(a);
			final Vector2D vB = isX ? Vector2D.directionYZ(c) : isY ? Vector2D.directionZX(c) : Vector2D.directionXY(c);
			final Vector2D vC = isX ? Vector2D.directionYZ(b) : isY ? Vector2D.directionZX(b) : Vector2D.directionXY(b);
			final Vector2D vAB = Vector2D.subtract(vB, vA);
			final Vector2D vAC = Vector2D.subtract(vC, vA);
			
			final double determinant = Vector2D.crossProduct(vAB, vAC);
			final double determinantReciprocal = 1.0D / determinant;
			
			final double hU = isX ? p.y : isY ? p.z : p.x;
			final double hV = isX ? p.z : isY ? p.x : p.y;
			
			final double u = hU * (-vAB.y * determinantReciprocal) + hV * (+vAB.x * determinantReciprocal) + Vector2D.crossProduct(vA, vAB) * determinantReciprocal;
			final double v = hU * (+vAC.y * determinantReciprocal) + hV * (-vAC.x * determinantReciprocal) + Vector2D.crossProduct(vAC, vA) * determinantReciprocal;
			
			return new Point2D(u, v);
		}
		
		@Override
		public boolean contains(final Point3D p) {
			final Point3D a = this.point3Ds[0];
			final Point3D b = this.point3Ds[1];
			
			final Vector3D w = this.n;
			final Vector3D u = Vector3D.directionNormalized(a, b);
			final Vector3D v = Vector3D.crossProduct(w, u);
			
			final Point2D q = Point2D.project(a, p, u, v);
			
			/*
			 * Check whether q is contained inside the polygon in 2D:
			 */
			
			boolean isInside = false;
			
			for(int i = 0, j = this.point2Ds.length - 1; i < this.point2Ds.length; j = i++) {
				final Point2D pI = this.point2Ds[i];
				final Point2D pJ = this.point2Ds[j];
				
				if((pI.y > p.y) != (pJ.y > p.y) && p.x < (pJ.x - pI.x) * (p.y - pI.y) / (pJ.y - pI.y) + pI.x) {
					isInside = !isInside;
				}
			}
			
			if(isInside) {
				return true;
			}
			
			/*
			 * Check whether q is contained on the line segments of the polygon in 2D:
			 */
			
			for(int i = 0, j = 1; i < this.point2Ds.length; i++, j = (j + 1) % this.point2Ds.length) {
				final Point2D pI = this.point2Ds[i];
				final Point2D pJ = this.point2Ds[j];
				
				if(!Doubles.isZero((q.x - pI.x) * (pJ.y - pI.y) - (q.y - pI.y) * (pJ.x - pI.x))) {
					continue;
				} else if(Doubles.abs(pJ.x - pI.x) >= Doubles.abs(pJ.y - pI.y) && pJ.x - pI.x > 0.0D ? pI.x <= q.x && q.x <= pJ.x : pJ.x <= q.x && q.x <= pI.x) {
					return true;
				} else if(Doubles.abs(pJ.x - pI.x) <  Doubles.abs(pJ.y - pI.y) && pJ.y - pI.y > 0.0D ? pI.y <= q.y && q.y <= pJ.y : pJ.y <= q.y && q.y <= pI.y) {
					return true;
				}
			}
			
			return false;
		}
		
		@Override
		public double intersection(final Ray3D ray, final double tMinimum, final double tMaximum) {
			final Point3D a = this.point3Ds[0];
			final Point3D o = ray.getOrigin();
			
			final Vector3D d = ray.getDirection();
			final Vector3D n = this.n;
			
			final double nDotD = Vector3D.dotProduct(n, d);
			
			if(Doubles.isZero(nDotD)) {
				return Doubles.NaN;
			}
			
			final double t = Vector3D.dotProduct(Vector3D.direction(o, a), n) / nDotD;
			
			if(t > tMinimum && t < tMaximum && contains(Point3D.add(o, d, t))) {
				return t;
			}
			
			return Doubles.NaN;
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public static Polygon create(final Point3D... points) {
			final Point3D[] point3Ds = doRequireValidPoints(points);
			
			final Vector3D n = Vector3D.normalNormalized(point3Ds[0], point3Ds[1], point3Ds[2]);
			
			final Point2D[] point2Ds = doCreateProjectedPoints(point3Ds, n);
			
			return new Polygon(point2Ds, point3Ds, n);
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		private static Point2D[] doCreateProjectedPoints(final Point3D[] points, final Vector3D n) {
			final Point3D a = points[0];
			final Point3D b = points[1];
			
			final Vector3D w = n;
			final Vector3D u = Vector3D.directionNormalized(a, b);
			final Vector3D v = Vector3D.crossProduct(w, u);
			
			final Point2D[] point2Ds = new Point2D[points.length];
			
			for(int i = 0; i < points.length; i++) {
				point2Ds[i] = Point2D.project(a, points[i], u, v);
			}
			
			return point2Ds;
		}
		
		private static Point3D[] doRequireValidPoints(final Point3D[] points) {
			Arrays.requireNonNull(points, "points");
			
			Ints.requireRange(points.length, 3, Integer.MAX_VALUE, "points.length");
			
			if(Point3D.coplanar(points)) {
				return points.clone();
			}
			
			throw new IllegalArgumentException("The provided Point3D instances are not coplanar.");
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add unit tests!
	private static final class Rectangle extends Shape {
		private final Point3D a;
		private final Point3D b;
		private final Point3D c;
		private final Point3D d;
		private final Vector3D n;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public Rectangle(final Point3D a, final Point3D b, final Point3D c, final Point3D d, final Vector3D n) {
			this.a = Objects.requireNonNull(a, "a == null");
			this.b = Objects.requireNonNull(b, "b == null");
			this.c = Objects.requireNonNull(c, "c == null");
			this.d = Objects.requireNonNull(d, "d == null");
			this.n = Objects.requireNonNull(n, "n == null");
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		@Override
		public BoundingVolume getBoundingVolume() {
			return BoundingVolume.axisAlignedBoundingBox(this.a, this.b, this.c, this.d);
		}
		
		@Override
		public OrthonormalBasis33D computeOrthonormalBasis(final Ray3D ray, final double t) {
			return new OrthonormalBasis33D(this.n);
		}
		
		@Override
		public Point2D computeTextureCoordinates(final Ray3D ray, final double t) {
			final Point3D a = this.a;
			final Point3D b = this.b;
			final Point3D d = this.d;
			final Point3D p = Point3D.add(ray.getOrigin(), ray.getDirection(), t);
			
			final Vector3D n = this.n;
			final Vector3D nAbs = Vector3D.abs(n);
			
			final boolean isX = nAbs.x > nAbs.y && nAbs.x > nAbs.z;
			final boolean isY = nAbs.y > nAbs.z;
			
			final Vector2D vA = isX ? Vector2D.directionYZ(a) : isY ? Vector2D.directionZX(a) : Vector2D.directionXY(a);
			final Vector2D vB = isX ? Vector2D.directionYZ(d) : isY ? Vector2D.directionZX(d) : Vector2D.directionXY(d);
			final Vector2D vC = isX ? Vector2D.directionYZ(b) : isY ? Vector2D.directionZX(b) : Vector2D.directionXY(b);
			final Vector2D vAB = Vector2D.subtract(vB, vA);
			final Vector2D vAC = Vector2D.subtract(vC, vA);
			
			final double determinant = Vector2D.crossProduct(vAB, vAC);
			final double determinantReciprocal = 1.0D / determinant;
			
			final double hU = isX ? p.y : isY ? p.z : p.x;
			final double hV = isX ? p.z : isY ? p.x : p.y;
			
			final double u = hU * (-vAB.y * determinantReciprocal) + hV * (+vAB.x * determinantReciprocal) + Vector2D.crossProduct(vA, vAB) * determinantReciprocal;
			final double v = hU * (+vAC.y * determinantReciprocal) + hV * (-vAC.x * determinantReciprocal) + Vector2D.crossProduct(vAC, vA) * determinantReciprocal;
			
			return new Point2D(u, v);
		}
		
		@Override
		public boolean contains(final Point3D p) {
			final Point3D a = this.a;
			final Point3D b = this.b;
			final Point3D c = this.c;
			
			if(!Point3D.coplanar(a, b, c, p)) {
				return false;
			}
			
			return doContains(p);
		}
		
		@Override
		public double intersection(final Ray3D ray, final double tMinimum, final double tMaximum) {
			final Point3D a = this.a;
			final Point3D o = ray.getOrigin();
			
			final Vector3D d = ray.getDirection();
			final Vector3D n = this.n;
			
			final double nDotD = Vector3D.dotProduct(n, d);
			
			if(Doubles.isZero(nDotD)) {
				return Doubles.NaN;
			}
			
			final double t = Vector3D.dotProduct(Vector3D.direction(o, a), n) / nDotD;
			
			if(t > tMinimum && t < tMaximum && doContains(Point3D.add(o, d, t))) {
				return t;
			}
			
			return Doubles.NaN;
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public static Rectangle create(final Point3D a, final Point3D b, final Point3D c, final Point3D d) {
			Objects.requireNonNull(a, "a == null");
			Objects.requireNonNull(b, "b == null");
			Objects.requireNonNull(c, "c == null");
			Objects.requireNonNull(d, "d == null");
			
			doRequireValidPoints(a, b, c, d);
			
			final Vector3D n = Vector3D.normalNormalized(a, b, c);
			
			return new Rectangle(a, b, c, d, n);
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		private boolean doContains(final Point3D p) {
			final Point3D a = this.a;
			final Point3D b = this.b;
			final Point3D c = this.c;
			
			final Vector3D directionAB = Vector3D.direction(a, b);
			final Vector3D directionBC = Vector3D.direction(b, c);
			final Vector3D directionAP = Vector3D.direction(a, p);
			
			final double dotProductAPAB = Vector3D.dotProduct(directionAP, Vector3D.normalize(directionAB));
			final double dotProductAPBC = Vector3D.dotProduct(directionAP, Vector3D.normalize(directionBC));
			
			return dotProductAPAB >= 0.0D && dotProductAPAB <= directionAB.length() && dotProductAPBC >= 0.0D && dotProductAPBC <= directionBC.length();
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		private static void doRequireValidPoints(final Point3D a, final Point3D b, final Point3D c, final Point3D d) {
			if(!Point3D.coplanar(a, b, c, d)) {
				throw new IllegalArgumentException("The provided Point3D instances are not coplanar.");
			}
			
			final double distanceAB = Point3D.distance(a, b);
			final double distanceBC = Point3D.distance(b, c);
			final double distanceCD = Point3D.distance(c, d);
			final double distanceDA = Point3D.distance(d, a);
			
			final double deltaABCD = Doubles.abs(distanceAB - distanceCD);
			final double deltaBCDA = Doubles.abs(distanceBC - distanceDA);
			
			final boolean isValidABCD = deltaABCD <= 0.00001D;
			final boolean isValidBCDA = deltaBCDA <= 0.00001D;
			final boolean isValid = isValidABCD && isValidBCDA;
			
			if(!isValid) {
				throw new IllegalArgumentException();
			}
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add unit tests!
	private static final class RectangularCuboid extends Shape {
		private final Point3D maximum;
		private final Point3D minimum;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		private RectangularCuboid(final Point3D maximum, final Point3D minimum) {
			this.maximum = Objects.requireNonNull(maximum, "maximum == null");
			this.minimum = Objects.requireNonNull(minimum, "minimum == null");
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		@Override
		public BoundingVolume getBoundingVolume() {
			return BoundingVolume.axisAlignedBoundingBox(this.maximum, this.minimum);
		}
		
		@Override
		public OrthonormalBasis33D computeOrthonormalBasis(final Ray3D ray, final double t) {
			final Point3D p = Point3D.add(ray.getOrigin(), ray.getDirection(), t);
			final Point3D q = Point3D.midpoint(this.maximum, this.minimum);
			
			final Vector3D v = Vector3D.multiply(Vector3D.direction(this.minimum, this.maximum), 0.5D);
			
			if(p.x + v.x - 0.0001D < q.x) {
				return new OrthonormalBasis33D(Vector3D.x(-1.0D));
			}
			
			if(p.x - v.x + 0.0001D > q.x) {
				return new OrthonormalBasis33D(Vector3D.x(+1.0D));
			}
			
			if(p.y + v.y - 0.0001D < q.y) {
				return new OrthonormalBasis33D(Vector3D.y(-1.0D));
			}
			
			if(p.y - v.y + 0.0001D > q.y) {
				return new OrthonormalBasis33D(Vector3D.y(+1.0D));
			}
			
			if(p.z + v.z - 0.0001D < q.z) {
				return new OrthonormalBasis33D(Vector3D.z(-1.0D));
			}
			
			if(p.z - v.z + 0.0001D > q.z) {
				return new OrthonormalBasis33D(Vector3D.z(+1.0D));
			}
			
			return new OrthonormalBasis33D();
		}
		
		@Override
		public Point2D computeTextureCoordinates(final Ray3D ray, final double t) {
			final Point3D p = Point3D.add(ray.getOrigin(), ray.getDirection(), t);
			final Point3D q = Point3D.midpoint(this.maximum, this.minimum);
			
			final Vector3D v = Vector3D.multiply(Vector3D.direction(this.minimum, this.maximum), 0.5D);
			
			if(p.x + v.x - 0.0001D < q.x || p.x - v.x + 0.0001D > q.x) {
				return new Point2D(Doubles.normalize(p.z, this.minimum.z, this.maximum.z), Doubles.normalize(p.y, this.minimum.y, this.maximum.y));
			}
			
			if(p.y + v.y - 0.0001D < q.y || p.y - v.y + 0.0001D > q.y) {
				return new Point2D(Doubles.normalize(p.x, this.minimum.x, this.maximum.x), Doubles.normalize(p.z, this.minimum.z, this.maximum.z));
			}
			
			if(p.z + v.z - 0.0001D < q.z || p.z - v.z + 0.0001D > q.z) {
				return new Point2D(Doubles.normalize(p.x, this.minimum.x, this.maximum.x), Doubles.normalize(p.y, this.minimum.y, this.maximum.y));
			}
			
			return new Point2D();
		}
		
		@Override
		public boolean contains(final Point3D p) {
			return p.x >= this.minimum.x && p.x <= this.maximum.x && p.y >= this.minimum.y && p.y <= this.maximum.y && p.z >= this.minimum.z && p.z <= this.maximum.z;
		}
		
		@Override
		public double intersection(final Ray3D ray, final double tMinimum, final double tMaximum) {
			final Vector3D v0 = Vector3D.reciprocal(ray.getDirection());
			final Vector3D v1 = Vector3D.hadamardProduct(Vector3D.direction(ray.getOrigin(), this.minimum), v0);
			final Vector3D v2 = Vector3D.hadamardProduct(Vector3D.direction(ray.getOrigin(), this.maximum), v0);
			
			final double t0 = Doubles.max(Doubles.min(v1.x, v2.x), Doubles.min(v1.y, v2.y), Doubles.min(v1.z, v2.z));
			final double t1 = Doubles.min(Doubles.max(v1.x, v2.x), Doubles.max(v1.y, v2.y), Doubles.max(v1.z, v2.z));
			
			if(t0 > t1) {
				return Doubles.NaN;
			}
			
			if(t0 > tMinimum && t0 < tMaximum) {
				return t0;
			}
			
			if(t1 > tMinimum && t1 < tMaximum) {
				return t1;
			}
			
			return Doubles.NaN;
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public static RectangularCuboid create(final Point3D a, final Point3D b) {
			Objects.requireNonNull(a, "a == null");
			Objects.requireNonNull(b, "b == null");
			
			final Point3D maximum = Point3D.max(a, b);
			final Point3D minimum = Point3D.min(a, b);
			
			return new RectangularCuboid(maximum, minimum);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add unit tests!
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
		public BoundingVolume getBoundingVolume() {
			return BoundingVolume.boundingSphere(this.center, this.radius);
		}
		
		@Override
		public OrthonormalBasis33D computeOrthonormalBasis(final Ray3D ray, final double t) {
			final Point3D p = Point3D.add(ray.getOrigin(), ray.getDirection(), t);
			
			final Vector3D w = Vector3D.directionNormalized(this.center, p);
			final Vector3D v = Vector3D.normalize(Vector3D.crossProduct(Vector3D.normalize(new Vector3D(-(Doubles.PI * 2.0D) * w.y, Doubles.PI * 2.0D * w.x, 0.0D)), w));
			
			return new OrthonormalBasis33D(w, v);
		}
		
		@Override
		public Point2D computeTextureCoordinates(final Ray3D ray, final double t) {
			final Point3D p = Point3D.add(ray.getOrigin(), ray.getDirection(), t);
			
			final Vector3D n = Vector3D.directionNormalized(this.center, p);
			
			final double u = Doubles.addLessThan(Doubles.atan2(n.y, n.x), 0.0D, 2.0D * Doubles.PI) / (2.0D * Doubles.PI);
			final double v = Doubles.acos(Doubles.max(Doubles.min(n.z, 1.0D), -1.0D)) / Doubles.PI;
			
			return new Point2D(u, v);
		}
		
		@Override
		public boolean contains(final Point3D p) {
			return Point3D.distanceSquared(this.center, p) <= this.radius * this.radius;
		}
		
		@Override
		public double intersection(final Ray3D ray, final double tMinimum, final double tMaximum) {
			final Point3D o = ray.getOrigin();
			
			final Vector3D d = ray.getDirection();
			final Vector3D e = Vector3D.direction(this.center, o);
			
			final double a = d.lengthSquared();
			final double b = 2.0D * Vector3D.dotProduct(e, d);
			final double c = e.lengthSquared() - this.radius * this.radius;
			
			final double[] ts = Doubles.solveQuadraticSystem(a, b, c);
			
			final double t0 = ts[0];
			final double t1 = ts[1];
			
			if(!Doubles.isNaN(t0) && t0 > tMinimum && t0 < tMaximum) {
				return t0;
			}
			
			if(!Doubles.isNaN(t1) && t1 > tMinimum && t1 < tMaximum) {
				return t1;
			}
			
			return Doubles.NaN;
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add unit tests!
	private static final class Torus extends Shape {
		private final double radiusInner;
		private final double radiusOuter;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public Torus(final double radiusInner, final double radiusOuter) {
			this.radiusInner = radiusInner;
			this.radiusOuter = radiusOuter;
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		@Override
		public BoundingVolume getBoundingVolume() {
			return BoundingVolume.boundingSphere(new Point3D(), this.radiusInner + this.radiusOuter);
		}
		
		@Override
		public OrthonormalBasis33D computeOrthonormalBasis(final Ray3D ray, final double t) {
			final Point3D p = Point3D.add(ray.getOrigin(), ray.getDirection(), t);
			
			final Vector3D d = new Vector3D(p);
			
			final double derivative = d.lengthSquared() - this.radiusInner * this.radiusInner - this.radiusOuter * this.radiusOuter;
			
			final double x = p.x * derivative;
			final double y = p.y * derivative;
			final double z = p.z * derivative + 2.0D * (this.radiusOuter * this.radiusOuter) * p.z;
			
			final Vector3D w = Vector3D.normalize(new Vector3D(x, y, z));
			
			return new OrthonormalBasis33D(w);
		}
		
		@Override
		public Point2D computeTextureCoordinates(final Ray3D ray, final double t) {
			final Point3D p = Point3D.add(ray.getOrigin(), ray.getDirection(), t);
			
			final double phi = Doubles.asin(Doubles.saturate(p.z / this.radiusInner, -1.0D, 1.0D));
			final double theta = Doubles.addLessThan(Doubles.atan2(p.y, p.x), 0.0D, Doubles.PI * 2.0D);
			
			final double u = theta / (Doubles.PI * 2.0D);
			final double v = (phi + Doubles.PI / 2.0D) / Doubles.PI;
			
			return new Point2D(u, v);
		}
		
//		TODO: Implement!
		@Override
		public boolean contains(final Point3D p) {
			return false;
		}
		
		@Override
		public double intersection(final Ray3D ray, final double tMinimum, final double tMaximum) {
			final Point3D o = ray.getOrigin();
			
			final Vector3D vD = ray.getDirection();
			final Vector3D vO = new Vector3D(o);
			
			final double f0 = vD.lengthSquared();
			final double f1 = Vector3D.dotProduct(vO, vD) * 2.0D;
			final double f2 = this.radiusInner * this.radiusInner;
			final double f3 = this.radiusOuter * this.radiusOuter;
			final double f4 = vO.lengthSquared() - f2 - f3;
			final double f5 = vD.z;
			final double f6 = vO.z;
			
			final double a = f0 * f0;
			final double b = f0 * 2.0D * f1;
			final double c = f1 * f1 + 2.0D * f0 * f4 + 4.0D * f3 * f5 * f5;
			final double d = f1 * 2.0D * f4 + 8.0D * f3 * f6 * f5;
			final double e = f4 * f4 + 4.0D * f3 * f6 * f6 - 4.0D * f3 * f2;
			
			final double[] ts = Math.solveQuartic(a, b, c, d, e);
			
			if(ts.length == 0) {
				return Doubles.NaN;
			}
			
			if(ts[0] >= tMaximum || ts[ts.length - 1] <= tMinimum) {
				return Doubles.NaN;
			}
			
			for(int i = 0; i < ts.length; i++) {
				if(ts[i] > tMinimum) {
					return ts[i];
				}
			}
			
			return Doubles.NaN;
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add unit tests!
	private static final class Triangle extends Shape {
		private final Point3D a;
		private final Point3D b;
		private final Point3D c;
		private final Vector3D n;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public Triangle(final Point3D a, final Point3D b, final Point3D c) {
			this.a = Objects.requireNonNull(a, "a == null");
			this.b = Objects.requireNonNull(b, "b == null");
			this.c = Objects.requireNonNull(c, "c == null");
			this.n = Vector3D.normalNormalized(a, b, c);
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		@Override
		public BoundingVolume getBoundingVolume() {
			return BoundingVolume.axisAlignedBoundingBox(this.a, this.b, this.c);
		}
		
		@Override
		public OrthonormalBasis33D computeOrthonormalBasis(final Ray3D ray, final double t) {
			final Point2D pTA = new Point2D(0.5D, 0.0D);
			final Point2D pTB = new Point2D(1.0D, 1.0D);
			final Point2D pTC = new Point2D(0.0D, 1.0D);
			
			final Vector2D vTCA = Vector2D.direction(pTC, pTA);
			final Vector2D vTCB = Vector2D.direction(pTC, pTB);
			
			final Point3D pPA = this.a;
			final Point3D pPB = this.b;
			final Point3D pPC = this.c;
			
			final Vector3D vPCA = Vector3D.direction(pPC, pPA);
			final Vector3D vPCB = Vector3D.direction(pPC, pPB);
			
			final Vector3D w = this.n;
			
			final double determinant = Vector2D.crossProduct(vTCA, vTCB);
			
			if(Doubles.isZero(determinant)) {
				return new OrthonormalBasis33D(w);
			}
			
			final double determinantReciprocal = 1.0D / determinant;
			
			final double x = (-vTCB.x * vPCA.x + vTCA.x * vPCB.x) * determinantReciprocal;
			final double y = (-vTCB.x * vPCA.y + vTCA.x * vPCB.y) * determinantReciprocal;
			final double z = (-vTCB.x * vPCA.z + vTCA.x * vPCB.z) * determinantReciprocal;
			
			final Vector3D v = new Vector3D(x, y, z);
			
			return new OrthonormalBasis33D(w, v);
		}
		
		@Override
		public Point2D computeTextureCoordinates(final Ray3D ray, final double t) {
			final Point3D pBC = doCreateBarycentricCoordinates(ray);
			
			final Point2D pTA = new Point2D(0.5D, 0.0D);
			final Point2D pTB = new Point2D(1.0D, 1.0D);
			final Point2D pTC = new Point2D(0.0D, 1.0D);
			
			final double u = pTA.x * pBC.x + pTB.x * pBC.y + pTC.x * pBC.z;
			final double v = pTA.y * pBC.x + pTB.y * pBC.y + pTC.y * pBC.z;
			
			return new Point2D(u, v);
		}
		
		@Override
		public boolean contains(final Point3D p) {
			final Point3D a = this.a;
			final Point3D b = this.b;
			final Point3D c = this.c;
			
			if(Point3D.coplanar(a, b, c, p)) {
				final Vector3D n = this.n;
				
				final Vector3D edgeAB = Vector3D.direction(a, b);
				final Vector3D edgeBC = Vector3D.direction(b, c);
				final Vector3D edgeCA = Vector3D.direction(c, a);
				
				final Vector3D edgeAP = Vector3D.direction(a, p);
				final Vector3D edgeBP = Vector3D.direction(b, p);
				final Vector3D edgeCP = Vector3D.direction(c, p);
				
				final boolean isInsideA = Vector3D.tripleProduct(n, edgeAB, edgeAP) > 0.0D;
				final boolean isInsideB = Vector3D.tripleProduct(n, edgeBC, edgeBP) > 0.0D;
				final boolean isInsideC = Vector3D.tripleProduct(n, edgeCA, edgeCP) > 0.0D;
				final boolean isInside = isInsideA && isInsideB && isInsideC;
				
				return isInside;
			}
			
			return false;
		}
		
		@Override
		public double intersection(final Ray3D ray, final double tMinimum, final double tMaximum) {
			final Point3D a = this.a;
			final Point3D b = this.b;
			final Point3D c = this.c;
			
			final Vector3D v0 = Vector3D.direction(a, b);
			final Vector3D v1 = Vector3D.direction(c, a);
			final Vector3D v2 = ray.getDirection();
			final Vector3D v3 = Vector3D.crossProduct(v0, v1);
			
			final double determinant = Vector3D.dotProduct(v2, v3);
			final double determinantReciprocal = 1.0D / determinant;
			
			final Point3D o = ray.getOrigin();
			
			final Vector3D v4 = Vector3D.direction(o, a);
			
			final double t = Vector3D.dotProduct(v3, v4) * determinantReciprocal;
			
			if(t <= tMinimum || t >= tMaximum) {
				return Doubles.NaN;
			}
			
			final Vector3D v5 = Vector3D.crossProduct(v4, v2);
			
			final double uDot = Vector3D.dotProduct(v5, v1);
			final double u = uDot * determinantReciprocal;
			
			if(u < 0.0D) {
				return Doubles.NaN;
			}
			
			final double vDot = Vector3D.dotProduct(v5, v0);
			final double v = vDot * determinantReciprocal;
			
			if(v < 0.0D) {
				return Doubles.NaN;
			}
			
			if((uDot + vDot) * determinant > determinant * determinant) {
				return Doubles.NaN;
			}
			
			return t;
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		private Point3D doCreateBarycentricCoordinates(final Ray3D ray) {
			final Point3D a = this.a;
			final Point3D b = this.b;
			final Point3D c = this.c;
			
			final Vector3D v0 = Vector3D.direction(a, b);
			final Vector3D v1 = Vector3D.direction(c, a);
			final Vector3D v2 = ray.getDirection();
			final Vector3D v3 = Vector3D.crossProduct(v0, v1);
			
			final double determinant = Vector3D.dotProduct(v2, v3);
			final double determinantReciprocal = 1.0D / determinant;
			
			final Point3D o = ray.getOrigin();
			
			final Vector3D v4 = Vector3D.direction(o, a);
			final Vector3D v5 = Vector3D.crossProduct(v4, v2);
			
			final double u = Vector3D.dotProduct(v5, v1) * determinantReciprocal;
			final double v = Vector3D.dotProduct(v5, v0) * determinantReciprocal;
			final double w = 1.0D - u - v;
			
			return new Point3D(w, u, v);
		}
	}
}