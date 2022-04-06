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

public abstract class BoundingVolume {
	protected BoundingVolume() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public abstract BoundingVolume transform(final Matrix44D m);
	
	public abstract boolean contains(final Point3D p);
	
	public final boolean intersects(final Ray3D r, final double tMin, final double tMax) {
		return !Math.isNaN(intersection(r, tMin, tMax));
	}
	
	public abstract double intersection(final Ray3D r, final double tMin, final double tMax);
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static BoundingVolume axisAlignedBoundingBox(final Point3D... points) {
		return AxisAlignedBoundingBox.create(points);
	}
	
	public static BoundingVolume boundingSphere(final Point3D center, final double radius) {
		return new BoundingSphere(center, radius);
	}
	
	public static BoundingVolume infiniteBoundingVolume() {
		return new InfiniteBoundingVolume();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final class AxisAlignedBoundingBox extends BoundingVolume {
		private final Point3D maximum;
		private final Point3D minimum;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		private AxisAlignedBoundingBox(final Point3D maximum, final Point3D minimum) {
			this.maximum = Objects.requireNonNull(maximum, "maximum == null");
			this.minimum = Objects.requireNonNull(minimum, "minimum == null");
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		@Override
		public AxisAlignedBoundingBox transform(final Matrix44D m) {
			final Point3D[] points = new Point3D[] {
				Point3D.transformAndDivide(m, new Point3D(this.minimum.x, this.minimum.y, this.minimum.z)),
				Point3D.transformAndDivide(m, new Point3D(this.maximum.x, this.minimum.y, this.minimum.z)),
				Point3D.transformAndDivide(m, new Point3D(this.minimum.x, this.maximum.y, this.minimum.z)),
				Point3D.transformAndDivide(m, new Point3D(this.minimum.x, this.minimum.y, this.maximum.z)),
				Point3D.transformAndDivide(m, new Point3D(this.minimum.x, this.maximum.y, this.maximum.z)),
				Point3D.transformAndDivide(m, new Point3D(this.maximum.x, this.maximum.y, this.minimum.z)),
				Point3D.transformAndDivide(m, new Point3D(this.maximum.x, this.minimum.y, this.maximum.z)),
				Point3D.transformAndDivide(m, new Point3D(this.maximum.x, this.maximum.y, this.maximum.z))
			};
			
			Point3D maximum = Point3D.MIN;
			Point3D minimum = Point3D.MAX;
			
			for(final Point3D point : points) {
				maximum = Point3D.max(maximum, point);
				minimum = Point3D.min(minimum, point);
			}
			
			return new AxisAlignedBoundingBox(maximum, minimum);
		}
		
		@Override
		public boolean contains(final Point3D p) {
			return p.x >= this.minimum.x && p.x <= this.maximum.x && p.y >= this.minimum.y && p.y <= this.maximum.y && p.z >= this.minimum.z && p.z <= this.maximum.z;
		}
		
		@Override
		public double intersection(final Ray3D r, final double tMin, final double tMax) {
			final Point3D o = r.getOrigin();
			
			final Vector3D v0 = r.getDirection();
			final Vector3D v1 = Vector3D.reciprocal(v0);
			final Vector3D v2 = Vector3D.hadamardProduct(Vector3D.direction(o, this.maximum), v1);
			final Vector3D v3 = Vector3D.hadamardProduct(Vector3D.direction(o, this.minimum), v1);
			
			final double t0 = Math.max(Math.min(v2.x, v3.x), Math.min(v2.y, v3.y), Math.min(v2.z, v3.z));
			final double t1 = Math.min(Math.max(v2.x, v3.x), Math.max(v2.y, v3.y), Math.max(v2.z, v3.z));
			
			return t0 > t1 ? Math.NaN : t0 > tMin && t0 < tMax ? t0 : t1 > tMin && t1 < tMax ? t1 : Math.NaN;
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public static AxisAlignedBoundingBox create(final Point3D... points) {
			Utilities.requireNonNullArray(points, "points");
			Utilities.requireRange(points.length, 1, Integer.MAX_VALUE, "points.length");
			
			Point3D maximum = Point3D.MIN;
			Point3D minimum = Point3D.MAX;
			
			for(final Point3D p : points) {
				maximum = Point3D.max(maximum, p);
				minimum = Point3D.min(minimum, p);
			}
			
			return new AxisAlignedBoundingBox(maximum, minimum);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final class BoundingSphere extends BoundingVolume {
		private final Point3D center;
		private final double radius;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public BoundingSphere(final Point3D center, final double radius) {
			this.center = Objects.requireNonNull(center, "center == null");
			this.radius = radius;
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		@Override
		public BoundingSphere transform(final Matrix44D m) {
			final Point3D c = Point3D.transformAndDivide(m, this.center);
			final Point3D x = Point3D.transformAndDivide(m, new Point3D(this.center.x + this.radius, this.center.y,               this.center.z));
			final Point3D y = Point3D.transformAndDivide(m, new Point3D(this.center.x,               this.center.y + this.radius, this.center.z));
			final Point3D z = Point3D.transformAndDivide(m, new Point3D(this.center.x,               this.center.y,               this.center.z + this.radius));
			
			final double r = Math.sqrt(Math.max(Point3D.distanceSquared(c, x), Point3D.distanceSquared(c, y), Point3D.distanceSquared(c, z)));
			
			return new BoundingSphere(c, r);
		}
		
		@Override
		public boolean contains(final Point3D p) {
			return Point3D.distanceSquared(this.center, p) <= this.radius * this.radius;
		}
		
		@Override
		public double intersection(final Ray3D r, final double tMin, final double tMax) {
			final Point3D o = r.getOrigin();
			
			final Vector3D d = r.getDirection();
			final Vector3D e = Vector3D.direction(this.center, o);
			
			final double a = d.lengthSquared();
			final double b = 2.0D * Vector3D.dotProduct(e, d);
			final double c = e.lengthSquared() - this.radius * this.radius;
			
			final double[] ts = Math.solveQuadraticSystem(a, b, c);
			
			final double t0 = ts[0];
			final double t1 = ts[1];
			
			if(!Math.isNaN(t0) && t0 > tMin && t0 < tMax) {
				return t0;
			}
			
			if(!Math.isNaN(t1) && t1 > tMin && t1 < tMax) {
				return t1;
			}
			
			return Math.NaN;
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final class InfiniteBoundingVolume extends BoundingVolume {
		public InfiniteBoundingVolume() {
			
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		@Override
		public InfiniteBoundingVolume transform(final Matrix44D m) {
			Objects.requireNonNull(m, "m == null");
			
			return this;
		}
		
		@Override
		public boolean contains(final Point3D p) {
			Objects.requireNonNull(p, "p == null");
			
			return true;
		}
		
		@Override
		public double intersection(final Ray3D r, final double tMin, final double tMax) {
			Objects.requireNonNull(r, "r == null");
			
			return tMin;
		}
	}
}