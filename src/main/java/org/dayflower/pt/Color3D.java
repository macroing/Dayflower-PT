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

import java.awt.image.BufferedImage;
import java.lang.reflect.Field;//TODO: Add unit tests!
import java.util.Objects;

public final class Color3D {
	public static final Color3D AU_ETA = new Color3D(0.1428431083584272D, 0.3741312033192202D, 1.4392239236981954D);
	public static final Color3D AU_K = new Color3D(3.975360769687202D, 2.380584839029059D, 1.5995662411380493D);
	public static final Color3D BLACK = new Color3D(0.0D, 0.0D, 0.0D);
	public static final Color3D GREEN = new Color3D(0.0D, 1.0D, 0.0D);
	public static final Color3D WHITE = new Color3D(1.0D, 1.0D, 1.0D);
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final double SRGB_BREAK_POINT;
	private static final double SRGB_GAMMA;
	private static final double SRGB_SEGMENT_OFFSET;
	private static final double SRGB_SLOPE;
	private static final double SRGB_SLOPE_MATCH;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public final double b;
	public final double g;
	public final double r;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	static {
		SRGB_BREAK_POINT = 0.00304D;
		SRGB_GAMMA = 2.4D;
		SRGB_SLOPE = 1.0D / (SRGB_GAMMA / Math.pow(SRGB_BREAK_POINT, 1.0D / SRGB_GAMMA - 1.0D) - SRGB_GAMMA * SRGB_BREAK_POINT + SRGB_BREAK_POINT);
		SRGB_SLOPE_MATCH = SRGB_GAMMA * SRGB_SLOPE / Math.pow(SRGB_BREAK_POINT, 1.0D / SRGB_GAMMA - 1.0D);
		SRGB_SEGMENT_OFFSET = SRGB_SLOPE_MATCH * Math.pow(SRGB_BREAK_POINT, 1.0D / SRGB_GAMMA) - SRGB_SLOPE * SRGB_BREAK_POINT;
	}
	
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
	
	public Color3D(final int r, final int g, final int b) {
		this.r = Math.saturate(r) / 255.0D;
		this.g = Math.saturate(g) / 255.0D;
		this.b = Math.saturate(b) / 255.0D;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public String toString() {
		return String.format("new Color3D(%s, %s, %s)", Utilities.toNonScientificNotationJava(this.r), Utilities.toNonScientificNotationJava(this.g), Utilities.toNonScientificNotationJava(this.b));
	}
	
	public boolean equals(final Color3D c) {
		if(c == this) {
			return true;
		} else if(c == null) {
			return false;
		} else if(!Math.equals(this.b, c.b)) {
			return false;
		} else if(!Math.equals(this.g, c.g)) {
			return false;
		} else if(!Math.equals(this.r, c.r)) {
			return false;
		} else {
			return true;
		}
	}
	
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof Color3D)) {
			return false;
		} else {
			return equals(Color3D.class.cast(object));
		}
	}
	
	public boolean isBlack() {
		return Math.isZero(this.r) && Math.isZero(this.g) && Math.isZero(this.b);  
	}
	
	public double average() {
		return (this.r + this.g + this.b) / 3.0D; 
	}
	
	public double max() {
		return Math.max(this.r, this.g, this.b);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(Double.valueOf(this.b), Double.valueOf(this.g), Double.valueOf(this.r));
	}
	
//	TODO: Add unit tests!
	public int toARGB() {
		final int a = 255;
		final int r = Math.saturate(Math.toInt(this.r * 255.0D + 0.5D));
		final int g = Math.saturate(Math.toInt(this.g * 255.0D + 0.5D));
		final int b = Math.saturate(Math.toInt(this.b * 255.0D + 0.5D));
		
		return ((a & 0xFF) << 24) | ((r & 0xFF) << 16) | ((g & 0xFF) << 8) | ((b & 0xFF));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static Color3D add(final Color3D cLHS, final Color3D cRHS) {
		return new Color3D(cLHS.r + cRHS.r, cLHS.g + cRHS.g, cLHS.b + cRHS.b);
	}
	
	public static Color3D blend(final Color3D cLHS, final Color3D cRHS) {
		return blend(cLHS, cRHS, 0.5D);
	}
	
	public static Color3D blend(final Color3D cLHS, final Color3D cRHS, final double t) {
		return blend(cLHS, cRHS, t, t, t);
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
	
//	TODO: Add unit tests!
	public static Color3D redoGammaCorrection(final Color3D c) {
		return new Color3D(doRedoGammaCorrection(c.r), doRedoGammaCorrection(c.g), doRedoGammaCorrection(c.b));
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
	
//	TODO: Add unit tests!
	public static Color3D undoGammaCorrection(final Color3D c) {
		return new Color3D(doUndoGammaCorrection(c.r), doUndoGammaCorrection(c.g), doUndoGammaCorrection(c.b));
	}
	
//	TODO: Add unit tests!
	public static Color3D[] toArray(final BufferedImage bufferedImage) {
		final BufferedImage compatibleBufferedImage = Utilities.getCompatibleBufferedImage(bufferedImage);
		
		final int resolutionX = compatibleBufferedImage.getWidth();
		final int resolutionY = compatibleBufferedImage.getHeight();
		
		final Color3D[] array = new Color3D[resolutionX * resolutionY];
		
		for(int index = 0; index < array.length; index++) {
			final int x = index % resolutionX;
			final int y = index / resolutionX;
			
			final int colorRGB = compatibleBufferedImage.getRGB(x, y);
			
			final int r = (colorRGB >> 16) & 0xFF;
			final int g = (colorRGB >>  8) & 0xFF;
			final int b = (colorRGB >>  0) & 0xFF;
			
			array[index] = new Color3D(r, g, b);
		}
		
		return array;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static double doRedoGammaCorrection(final double value) {
		return value <= SRGB_BREAK_POINT ? value * SRGB_SLOPE : SRGB_SLOPE_MATCH * Math.pow(value, 1.0D / SRGB_GAMMA) - SRGB_SEGMENT_OFFSET;
	}
	
	private static double doUndoGammaCorrection(final double value) {
		return value <= SRGB_BREAK_POINT * SRGB_SLOPE ? value / SRGB_SLOPE : Math.pow((value + SRGB_SEGMENT_OFFSET) / SRGB_SLOPE_MATCH, SRGB_GAMMA);
	}
}