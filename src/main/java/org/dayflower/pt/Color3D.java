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

public final class Color3D {
	public static final Color3D BLACK = new Color3D(0.0D, 0.0D, 0.0D);
	public static final Color3D WHITE = new Color3D(1.0D, 1.0D, 1.0D);
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public final double b;
	public final double g;
	public final double r;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Color3D() {
		this(0.0D, 0.0D, 0.0D);
	}
	
	public Color3D(final double c) {
		this(c, c, c);
	}
	
	public Color3D(final double r, final double g, final double b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean isBlack() {
		return Math.isZero(this.r) && Math.isZero(this.g) && Math.isZero(this.b);  
	}
	
	public double average() {
		return (this.r + this.g + this.b) / 3.0D; 
	}
	
	public double max() {
		return Math.max(this.r, this.g, this.b);
	}
	
	public int toARGB() {
		final int a = 255;
		final int r = Math.toInt(Math.pow(Math.saturate(this.r), 1.0D / 2.2D) * 255.0D + 0.5D);
		final int g = Math.toInt(Math.pow(Math.saturate(this.g), 1.0D / 2.2D) * 255.0D + 0.5D);
		final int b = Math.toInt(Math.pow(Math.saturate(this.b), 1.0D / 2.2D) * 255.0D + 0.5D);
		
		return ((a & 0xFF) << 24) | ((r & 0xFF) << 16) | ((g & 0xFF) << 8) | ((b & 0xFF));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static Color3D add(final Color3D cLHS, final Color3D cRHS) {
		return new Color3D(cLHS.r + cRHS.r, cLHS.g + cRHS.g, cLHS.b + cRHS.b);
	}
	
	public static Color3D blend(final Color3D cLHS, final Color3D cRHS, final double tR, final double tG, final double tB) {
		return new Color3D(Math.lerp(cLHS.r, cRHS.r, tR), Math.lerp(cLHS.g, cRHS.g, tG), Math.lerp(cLHS.b, cRHS.b, tB));
	}
	
	public static Color3D divide(final Color3D c, final double s) {
		return new Color3D(c.r / s, c.g / s, c.b / s);
	}
	
	public static Color3D multiply(final Color3D cLHS, final Color3D cRHS) {
		return new Color3D(cLHS.r * cRHS.r, cLHS.g * cRHS.g, cLHS.b * cRHS.b);
	}
	
	public static Color3D multiply(final Color3D c, final double s) {
		return new Color3D(c.r * s, c.g * s, c.b * s);
	}
	
	public static Color3D saturate(final Color3D c) {
		return saturate(c, 0.0D, 1.0D);
	}
	
	public static Color3D saturate(final Color3D c, final double intervalA, final double intervalB) {
		return new Color3D(Math.saturate(c.r, intervalA, intervalB), Math.saturate(c.g, intervalA, intervalB), Math.saturate(c.b, intervalA, intervalB));
	}
	
	public static Color3D subtract(final Color3D cLHS, final Color3D cRHS) {
		return new Color3D(cLHS.r - cRHS.r, cLHS.g - cRHS.g, cLHS.b - cRHS.b);
	}
}