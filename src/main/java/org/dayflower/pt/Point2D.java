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

public final class Point2D {
	public final double x;
	public final double y;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Point2D() {
		this(0.0D, 0.0D);
	}
	
	public Point2D(final double x, final double y) {
		this.x = x;
		this.y = y;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public String toString() {
		return String.format("new Point2D(%s, %s)", Utilities.toNonScientificNotationJava(this.x), Utilities.toNonScientificNotationJava(this.y));
	}
	
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof Point2D)) {
			return false;
		} else {
			return equals(Point2D.class.cast(object));
		}
	}
	
	public boolean equals(final Point2D p) {
		if(p == this) {
			return true;
		} else if(p == null) {
			return false;
		} else if(!Math.equals(this.x, p.x)) {
			return false;
		} else if(!Math.equals(this.y, p.y)) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean isZero() {
		return Math.isZero(this.x) && Math.isZero(this.y); 
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(Double.valueOf(this.x), Double.valueOf(this.y));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static Point2D project(final Point3D a, final Point3D b, final Vector3D u, final Vector3D v) {
		final Vector3D directionAB = Vector3D.direction(a, b);
		
		final double x = Vector3D.dotProduct(directionAB, u);
		final double y = Vector3D.dotProduct(directionAB, v);
		
		return new Point2D(x, y);
	}
	
	public static Point2D rotate(final Point2D p, final double angle, final boolean isRadians) {
		final double angleRadians = isRadians ? angle : Math.toRadians(angle);
		final double angleRadiansCos = Math.cos(angleRadians);
		final double angleRadiansSin = Math.sin(angleRadians);
		
		final double x = p.x * angleRadiansCos - p.y * angleRadiansSin;
		final double y = p.y * angleRadiansCos + p.x * angleRadiansSin;
		
		/*
		 * To rotate around a different point than origin (0.0, 0.0):
		 * 
		 * x = (p.x - c.x) * angleRadiansCos - (p.y - c.y) * angleRadiansSin + c.x;
		 * y = (p.y - c.y) * angleRadiansCos + (p.x - c.x) * angleRadiansSin + c.y;
		 */
		
		return new Point2D(x, y);
	}
	
//	TODO: Add unit tests!
	public static Point2D sampleDiskUniformDistributionByConcentricMapping() {
		return sampleDiskUniformDistributionByConcentricMapping(sampleRandom());
	}
	
//	TODO: Add unit tests!
	public static Point2D sampleDiskUniformDistributionByConcentricMapping(final Point2D p) {
		return sampleDiskUniformDistributionByConcentricMapping(p, 1.0D);
	}
	
//	TODO: Add unit tests!
	public static Point2D sampleDiskUniformDistributionByConcentricMapping(final Point2D p, final double radius) {
		if(p.isZero()) {
			return p;
		}
		
		final double a = p.x * 2.0D - 1.0D;
		final double b = p.y * 2.0D - 1.0D;
		
		if(a * a > b * b) {
			final double phi = Math.PI / 4.0D * (b / a);
			final double r = radius * a;
			
			return new Point2D(r * Math.cos(phi), r * Math.sin(phi));
		}
		
		final double phi = Math.PI / 2.0D - Math.PI / 4.0D * (a / b);
		final double r = radius * b;
		
		return new Point2D(r * Math.cos(phi), r * Math.sin(phi));
	}
	
	public static Point2D sampleExactInverseTentFilter() {
		return sampleExactInverseTentFilter(sampleRandom());
	}
	
	public static Point2D sampleExactInverseTentFilter(final Point2D p) {
		final double a = p.x * 2.0D;
		final double b = p.y * 2.0D;
		
		final double x = a < 1.0D ? Math.sqrt(a) - 1.0D : 1.0D - Math.sqrt(2.0D - a);
		final double y = b < 1.0D ? Math.sqrt(b) - 1.0D : 1.0D - Math.sqrt(2.0D - b);
		
		return new Point2D(x, y);
	}
	
	public static Point2D sampleRandom() {
		return new Point2D(Math.random(), Math.random());
	}
}