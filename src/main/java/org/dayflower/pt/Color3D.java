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

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Objects;

public final class Color3D {
	public static final Color3D AU_ETA = new Color3D(0.1428431083584272D, 0.3741312033192202D, 1.4392239236981954D);
	public static final Color3D AU_K = new Color3D(3.975360769687202D, 2.380584839029059D, 1.5995662411380493D);
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
	
	public Color3D(final int r, final int g, final int b) {
		this.r = Math.saturate(r) / 255.0D;
		this.g = Math.saturate(g) / 255.0D;
		this.b = Math.saturate(b) / 255.0D;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public String toString() {
		return String.format("new Color3D(%s, %s, %s)", Strings.toNonScientificNotationJava(this.r), Strings.toNonScientificNotationJava(this.g), Strings.toNonScientificNotationJava(this.b));
	}
	
	public boolean equals(final Color3D c) {
		if(c == this) {
			return true;
		} else if(c == null) {
			return false;
		} else if(!Math.equal(this.b, c.b)) {
			return false;
		} else if(!Math.equal(this.g, c.g)) {
			return false;
		} else if(!Math.equal(this.r, c.r)) {
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
	
	public static Color3D saturate(final Color3D c) {
		return saturate(c, 0.0D, 1.0D);
	}
	
	public static Color3D saturate(final Color3D c, final double intervalA, final double intervalB) {
		return new Color3D(Math.saturate(c.r, intervalA, intervalB), Math.saturate(c.g, intervalA, intervalB), Math.saturate(c.b, intervalA, intervalB));
	}
	
	public static Color3D subtract(final Color3D cLHS, final Color3D cRHS) {
		return new Color3D(cLHS.r - cRHS.r, cLHS.g - cRHS.g, cLHS.b - cRHS.b);
	}
	
	public static Color3D[] toArray(final BufferedImage bufferedImage) {
		final BufferedImage compatibleBufferedImage = doGetCompatibleBufferedImage(bufferedImage);
		
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
	
	private static BufferedImage doGetCompatibleBufferedImage(final BufferedImage bufferedImage) {
		return doGetCompatibleBufferedImage(bufferedImage, BufferedImage.TYPE_INT_ARGB);
	}
	
	private static BufferedImage doGetCompatibleBufferedImage(final BufferedImage bufferedImage, final int type) {
		if(bufferedImage.getType() == type) {
			return bufferedImage;
		}
		
		final BufferedImage compatibleBufferedImage = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), type);
		
		final
		Graphics2D graphics2D = compatibleBufferedImage.createGraphics();
		graphics2D.drawImage(bufferedImage, 0, 0, null);
		
		return compatibleBufferedImage;
	}
}