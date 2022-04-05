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

public interface Texture {
	Color3D compute(final Intersection intersection);
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	static Texture blend(final Texture textureA, final Texture textureB, final double tR, final double tG, final double tB) {
		Objects.requireNonNull(textureA, "textureA == null");
		Objects.requireNonNull(textureB, "textureB == null");
		
		return intersection -> Color3D.blend(textureA.compute(intersection), textureB.compute(intersection), tR, tG, tB);
	}
	
	static Texture bullseye(final Texture textureA, final Texture textureB, final Point3D origin, final double scale) {
		Objects.requireNonNull(textureA, "textureA == null");
		Objects.requireNonNull(textureB, "textureB == null");
		Objects.requireNonNull(origin, "origin == null");
		
		return intersection -> {
			final Vector3D direction = Vector3D.direction(origin, intersection.getSurfaceIntersectionPointOS());
			
			final boolean isTextureA = (direction.length() * scale) % 1.0D > 0.5D;
			
			return isTextureA ? textureA.compute(intersection) : textureB.compute(intersection);
		};
	}
	
	static Texture checkerboard(final Texture textureA, final Texture textureB, final double angleDegrees, final double scaleU, final double scaleV) {
		Objects.requireNonNull(textureA, "textureA == null");
		Objects.requireNonNull(textureB, "textureB == null");
		
		final double angleRadians = Math.toRadians(angleDegrees);
		final double angleRadiansCos = Math.cos(angleRadians);
		final double angleRadiansSin = Math.sin(angleRadians);
		
		return intersection -> {
			final double u = intersection.getTextureCoordinates().x;
			final double v = intersection.getTextureCoordinates().y;
			
			final boolean isU = Math.fractionalPart((u * angleRadiansCos - v * angleRadiansSin) * scaleU) > 0.5D;
			final boolean isV = Math.fractionalPart((v * angleRadiansCos + u * angleRadiansSin) * scaleV) > 0.5D;
			final boolean isTextureA = isU ^ isV;
			
			return isTextureA ? textureA.compute(intersection) : textureB.compute(intersection);
		};
	}
	
	static Texture constant(final Color3D color) {
		Objects.requireNonNull(color, "color == null");
		
		return intersection -> color;
	}
	
	static Texture image(final Image image, final double angleDegrees, final double scaleU, final double scaleV) {
		Objects.requireNonNull(image, "image == null");
		
		final double angleRadians = Math.toRadians(angleDegrees);
		final double angleRadiansCos = Math.cos(angleRadians);
		final double angleRadiansSin = Math.sin(angleRadians);
		
		return intersection -> {
			final int resolutionX = image.getResolutionX();
			final int resolutionY = image.getResolutionY();
			
			final double aU = intersection.getTextureCoordinates().x;
			final double aV = intersection.getTextureCoordinates().y;
			
			final double bU = aU * angleRadiansCos - aV * angleRadiansSin;
			final double bV = aV * angleRadiansCos + aU * angleRadiansSin;
			
			final double cU = bU * scaleU;
			final double cV = bV * scaleV;
			
			final double dU = Math.positiveModulo(cU * resolutionX - 0.5D, resolutionX);
			final double dV = Math.positiveModulo(cV * resolutionY - 0.5D, resolutionY);
			
			return image.getColor(dU, dV, true);
		};
	}
	
	static Texture polkaDot(final Texture textureA, final Texture textureB, final double angleDegrees, final double cellResolution, final double polkaDotRadius) {
		Objects.requireNonNull(textureA, "textureA == null");
		Objects.requireNonNull(textureB, "textureB == null");
		
		final double angleRadians = Math.toRadians(angleDegrees);
		final double angleRadiansCos = Math.cos(angleRadians);
		final double angleRadiansSin = Math.sin(angleRadians);
		
		final double polkaDotRadiusSquared = polkaDotRadius * polkaDotRadius;
		
		return intersection -> {
			final double aU = intersection.getTextureCoordinates().x;
			final double aV = intersection.getTextureCoordinates().y;
			
			final double bU = Math.fractionalPart((aU * angleRadiansCos - aV * angleRadiansSin) * cellResolution) - 0.5D;
			final double bV = Math.fractionalPart((aV * angleRadiansCos + aU * angleRadiansSin) * cellResolution) - 0.5D;
			
			final double distanceSquared = bU * bU + bV * bV;
			
			final boolean isTextureA = distanceSquared < polkaDotRadiusSquared;
			
			return isTextureA ? textureA.compute(intersection) : textureB.compute(intersection);
		};
	}
}