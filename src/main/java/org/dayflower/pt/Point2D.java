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

public final class Point2D {
	public final double u;
	public final double v;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Point2D() {
		this(0.0D, 0.0D);
	}
	
	public Point2D(final double u, final double v) {
		this.u = u;
		this.v = v;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean isZero() {
		return Math.isZero(this.u) && Math.isZero(this.v); 
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static Point2D sampleDiskUniformDistributionByConcentricMapping() {
		return sampleDiskUniformDistributionByConcentricMapping(sampleRandom());
	}
	
	public static Point2D sampleDiskUniformDistributionByConcentricMapping(final Point2D p) {
		return sampleDiskUniformDistributionByConcentricMapping(p, 1.0D);
	}
	
	public static Point2D sampleDiskUniformDistributionByConcentricMapping(final Point2D p, final double radius) {
		if(p.isZero()) {
			return p;
		}
		
		final double a = p.u * 2.0D - 1.0D;
		final double b = p.v * 2.0D - 1.0D;
		
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
		final double x = p.u * 2.0D;
		final double y = p.v * 2.0D;
		
		final double u = x < 1.0D ? Math.sqrt(x) - 1.0D : 1.0D - Math.sqrt(2.0D - x);
		final double v = y < 1.0D ? Math.sqrt(y) - 1.0D : 1.0D - Math.sqrt(2.0D - y);
		
		return new Point2D(u, v);
	}
	
	public static Point2D sampleRandom() {
		return new Point2D(Math.random(), Math.random());
	}
}