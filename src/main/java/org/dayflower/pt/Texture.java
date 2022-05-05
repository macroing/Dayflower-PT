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
import java.util.function.Function;

public interface Texture {
	Color3D compute(final Intersection intersection);
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add unit tests!
	static Texture blend() {
		return blend(Color3D.BLACK, Color3D.WHITE);
	}
	
//	TODO: Add unit tests!
	static Texture blend(final Color3D colorA, final Color3D colorB) {
		return blend(colorA, colorB, 0.5D);
	}
	
//	TODO: Add unit tests!
	static Texture blend(final Color3D colorA, final Color3D colorB, final double t) {
		return blend(colorA, colorB, t, t, t);
	}
	
//	TODO: Add unit tests!
	static Texture blend(final Color3D colorA, final Color3D colorB, final double tR, final double tG, final double tB) {
		return blend(constant(colorA), constant(colorB), tR, tG, tB);
	}
	
//	TODO: Add unit tests!
	static Texture blend(final Texture textureA, final Texture textureB) {
		return blend(textureA, textureB, 0.5D);
	}
	
//	TODO: Add unit tests!
	static Texture blend(final Texture textureA, final Texture textureB, final double t) {
		return blend(textureA, textureB, t, t, t);
	}
	
//	TODO: Add unit tests!
	static Texture blend(final Texture textureA, final Texture textureB, final double tR, final double tG, final double tB) {
		Objects.requireNonNull(textureA, "textureA == null");
		Objects.requireNonNull(textureB, "textureB == null");
		
		return intersection -> Color3D.blend(textureA.compute(intersection), textureB.compute(intersection), tR, tG, tB);
	}
	
//	TODO: Add unit tests!
	static Texture bullseye() {
		return bullseye(new Color3D(0.5D));
	}
	
//	TODO: Add unit tests!
	static Texture bullseye(final Color3D colorA) {
		return bullseye(colorA, Color3D.WHITE);
	}
	
//	TODO: Add unit tests!
	static Texture bullseye(final Color3D colorA, final Color3D colorB) {
		return bullseye(colorA, colorB, new Point3D(0.0D, 10.0D, 0.0D));
	}
	
//	TODO: Add unit tests!
	static Texture bullseye(final Color3D colorA, final Color3D colorB, final Point3D origin) {
		return bullseye(colorA, colorB, origin, 1.0D);
	}
	
//	TODO: Add unit tests!
	static Texture bullseye(final Color3D colorA, final Color3D colorB, final Point3D origin, final double scale) {
		return bullseye(constant(colorA), constant(colorB), origin, scale);
	}
	
//	TODO: Add unit tests!
	static Texture bullseye(final Texture textureA, final Texture textureB) {
		return bullseye(textureA, textureB, new Point3D(0.0D, 10.0D, 0.0D));
	}
	
//	TODO: Add unit tests!
	static Texture bullseye(final Texture textureA, final Texture textureB, final Point3D origin) {
		return bullseye(textureA, textureB, origin, 1.0D);
	}
	
//	TODO: Add unit tests!
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
	
//	TODO: Add unit tests!
	static Texture checkerboard() {
		return checkerboard(new Color3D(0.5D));
	}
	
//	TODO: Add unit tests!
	static Texture checkerboard(final Color3D colorA) {
		return checkerboard(colorA, Color3D.WHITE);
	}
	
//	TODO: Add unit tests!
	static Texture checkerboard(final Color3D colorA, final Color3D colorB) {
		return checkerboard(colorA, colorB, 0.0D);
	}
	
//	TODO: Add unit tests!
	static Texture checkerboard(final Color3D colorA, final Color3D colorB, final double angleDegrees) {
		return checkerboard(colorA, colorB, angleDegrees, 1.0D);
	}
	
//	TODO: Add unit tests!
	static Texture checkerboard(final Color3D colorA, final Color3D colorB, final double angleDegrees, final double scale) {
		return checkerboard(colorA, colorB, angleDegrees, scale, scale);
	}
	
//	TODO: Add unit tests!
	static Texture checkerboard(final Color3D colorA, final Color3D colorB, final double angleDegrees, final double scaleU, final double scaleV) {
		return checkerboard(constant(colorA), constant(colorB), angleDegrees, scaleU, scaleV);
	}
	
//	TODO: Add unit tests!
	static Texture checkerboard(final Texture textureA, final Texture textureB) {
		return checkerboard(textureA, textureB, 0.0D);
	}
	
//	TODO: Add unit tests!
	static Texture checkerboard(final Texture textureA, final Texture textureB, final double angleDegrees) {
		return checkerboard(textureA, textureB, angleDegrees, 1.0D);
	}
	
//	TODO: Add unit tests!
	static Texture checkerboard(final Texture textureA, final Texture textureB, final double angleDegrees, final double scale) {
		return checkerboard(textureA, textureB, angleDegrees, scale, scale);
	}
	
//	TODO: Add unit tests!
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
	
//	TODO: Add unit tests!
	static Texture constant(final Color3D color) {
		Objects.requireNonNull(color, "color == null");
		
		return intersection -> color;
	}
	
//	TODO: Add unit tests!
	static Texture constant(final double component) {
		return constant(new Color3D(component));
	}
	
//	TODO: Add unit tests!
	static Texture dotProduct() {
		return intersection -> {
			final Vector3D d = intersection.getRayWS().getDirection();
			final Vector3D n = intersection.getSurfaceNormalWSCorrectlyOriented();
			
			final double nDotD = Vector3D.dotProductAbs(n, d);
			
			return new Color3D(nDotD);
		};
	}
	
//	TODO: Add unit tests!
	static Texture function(final Function<Intersection, Color3D> function) {
		Objects.requireNonNull(function, "function == null");
		
		return intersection -> {
			return Objects.requireNonNull(function.apply(intersection));
		};
	}
	
//	TODO: Add unit tests!
	static Texture image(final Image image) {
		return image(image, 0.0D);
	}
	
//	TODO: Add unit tests!
	static Texture image(final Image image, final double angleDegrees) {
		return image(image, angleDegrees, 1.0D);
	}
	
//	TODO: Add unit tests!
	static Texture image(final Image image, final double angleDegrees, final double scale) {
		return image(image, angleDegrees, scale, scale);
	}
	
//	TODO: Add unit tests!
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
	
//	TODO: Add unit tests!
	static Texture marble() {
		return marble(new Color3D(0.8D));
	}
	
//	TODO: Add unit tests!
	static Texture marble(final Color3D colorA) {
		return marble(colorA, new Color3D(0.4D, 0.2D, 0.1D));
	}
	
//	TODO: Add unit tests!
	static Texture marble(final Color3D colorA, final Color3D colorB) {
		return marble(colorA, colorB, new Color3D(0.06D, 0.04D, 0.02D));
	}
	
//	TODO: Add unit tests!
	static Texture marble(final Color3D colorA, final Color3D colorB, final Color3D colorC) {
		return marble(colorA, colorB, colorC, 5.0D);
	}
	
//	TODO: Add unit tests!
	static Texture marble(final Color3D colorA, final Color3D colorB, final Color3D colorC, final double scale) {
		return marble(colorA, colorB, colorC, scale, 0.15D);
	}
	
//	TODO: Add unit tests!
	static Texture marble(final Color3D colorA, final Color3D colorB, final Color3D colorC, final double scale, final double stripes) {
		return marble(colorA, colorB, colorC, scale, stripes, 8);
	}
	
//	TODO: Add unit tests!
	static Texture marble(final Color3D colorA, final Color3D colorB, final Color3D colorC, final double scale, final double stripes, final int octaves) {
		Objects.requireNonNull(colorA, "colorA == null");
		Objects.requireNonNull(colorB, "colorB == null");
		Objects.requireNonNull(colorC, "colorC == null");
		
		final double frequency = Math.PI * stripes;
		
		return intersection -> {
			final Point3D p = intersection.getSurfaceIntersectionPointWS();
			
			final double x = p.x * frequency;
			final double y = p.y * frequency;
			final double z = p.z * frequency;
			final double r = PerlinNoise.turbulenceXYZ(x, y, z, octaves) * scale;
			final double s = 2.0D * Math.abs(Math.sin(x + r));
			final double t = s < 1.0D ? s : s - 1.0D;
			
			final Color3D a = s < 1.0D ? colorC : colorB;
			final Color3D b = s < 1.0D ? colorB : colorA;
			
			return Color3D.blend(a, b, t);
		};
	}
	
//	TODO: Add unit tests!
	static Texture polkaDot() {
		return polkaDot(new Color3D(0.5D));
	}
	
//	TODO: Add unit tests!
	static Texture polkaDot(final Color3D colorA) {
		return polkaDot(colorA, Color3D.WHITE);
	}
	
//	TODO: Add unit tests!
	static Texture polkaDot(final Color3D colorA, final Color3D colorB) {
		return polkaDot(colorA, colorB, 0.0D);
	}
	
//	TODO: Add unit tests!
	static Texture polkaDot(final Color3D colorA, final Color3D colorB, final double angleDegrees) {
		return polkaDot(colorA, colorB, angleDegrees, 10.0D);
	}
	
//	TODO: Add unit tests!
	static Texture polkaDot(final Color3D colorA, final Color3D colorB, final double angleDegrees, final double cellResolution) {
		return polkaDot(colorA, colorB, angleDegrees, cellResolution, 0.25D);
	}
	
//	TODO: Add unit tests!
	static Texture polkaDot(final Color3D colorA, final Color3D colorB, final double angleDegrees, final double cellResolution, final double polkaDotRadius) {
		return polkaDot(constant(colorA), constant(colorB), angleDegrees, cellResolution, polkaDotRadius);
	}
	
//	TODO: Add unit tests!
	static Texture polkaDot(final Texture textureA, final Texture textureB) {
		return polkaDot(textureA, textureB, 0.0D);
	}
	
//	TODO: Add unit tests!
	static Texture polkaDot(final Texture textureA, final Texture textureB, final double angleDegrees) {
		return polkaDot(textureA, textureB, angleDegrees, 10.0D);
	}
	
//	TODO: Add unit tests!
	static Texture polkaDot(final Texture textureA, final Texture textureB, final double angleDegrees, final double cellResolution) {
		return polkaDot(textureA, textureB, angleDegrees, cellResolution, 0.25D);
	}
	
//	TODO: Add unit tests!
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
	
//	TODO: Add unit tests!
	static Texture simplexFractionalBrownianMotion() {
		return simplexFractionalBrownianMotion(new Color3D(0.75D, 0.50D, 0.75D));
	}
	
//	TODO: Add unit tests!
	static Texture simplexFractionalBrownianMotion(final Color3D color) {
		return simplexFractionalBrownianMotion(color, 5.0D);
	}
	
//	TODO: Add unit tests!
	static Texture simplexFractionalBrownianMotion(final Color3D color, final double frequency) {
		return simplexFractionalBrownianMotion(color, frequency, 0.5D);
	}
	
//	TODO: Add unit tests!
	static Texture simplexFractionalBrownianMotion(final Color3D color, final double frequency, final double gain) {
		return simplexFractionalBrownianMotion(color, frequency, gain, 16);
	}
	
//	TODO: Add unit tests!
	static Texture simplexFractionalBrownianMotion(final Color3D color, final double frequency, final double gain, final int octaves) {
		Objects.requireNonNull(color, "color == null");
		
		return intersection -> {
			final Point3D p = intersection.getSurfaceIntersectionPointWS();
			
			final double noise = SimplexNoise.fractionalBrownianMotionXYZ(p.x, p.y, p.z, frequency, gain, 0.0D, 1.0D, octaves);
			
			return Color3D.multiply(color, noise);
		};
	}
	
//	TODO: Add unit tests!
	static Texture surfaceIntersectionPoint() {
		return intersection -> {
			final Point3D p = intersection.getSurfaceIntersectionPointOS();
			
			final double r = Math.saturate(p.x);
			final double g = Math.saturate(p.y);
			final double b = Math.saturate(p.z);
			
			return new Color3D(r, g, b);
		};
	}
	
//	TODO: Add unit tests!
	static Texture surfaceNormal() {
		return intersection -> {
			final Vector3D n = intersection.getSurfaceNormalWSCorrectlyOriented();
			
			final double r = (n.x + 1.0D) / 2.0D;
			final double g = (n.y + 1.0D) / 2.0D;
			final double b = (n.z + 1.0D) / 2.0D;
			
			return new Color3D(r, g, b);
		};
	}
	
//	TODO: Add unit tests!
	static Texture textureCoordinates() {
		return intersection -> {
			final Point2D p = intersection.getTextureCoordinates();
			
			final double r = p.x;
			final double g = p.y;
			final double b = 0.0D;
			
			return new Color3D(r, g, b);
		};
	}
}