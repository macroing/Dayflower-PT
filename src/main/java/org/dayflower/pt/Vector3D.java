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

import java.util.Optional;

public final class Vector3D {
	public final double x;
	public final double y;
	public final double z;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Vector3D(final double x, final double y, final double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public double length() {
		return Math.sqrt(lengthSquared());
	}
	
	public double lengthSquared() {
		return this.x * this.x + this.y * this.y + this.z * this.z;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static double dotProduct(final Vector3D vLHS, final Vector3D vRHS) {
		return vLHS.x * vRHS.x + vLHS.y * vRHS.y + vLHS.z * vRHS.z;
	}
	
	public static Optional<Vector3D> refraction(final Vector3D direction, final Vector3D normal, final double eta) {
		final double cosThetaI = dotProduct(direction, normal);
		final double sinThetaISquared = 1.0D - cosThetaI * cosThetaI;
		final double sinThetaTSquared = 1.0D - eta * eta * sinThetaISquared;
		final double cosThetaT = Math.sqrt(sinThetaTSquared);
		
		if(sinThetaTSquared < 0.0D) {
			return Optional.empty();
		}
		
		return Optional.of(subtract(multiply(direction, eta), multiply(normal, eta * cosThetaI + cosThetaT)));
	}
	
	public static Vector3D abs(final Vector3D v) {
		return new Vector3D(Math.abs(v.x), Math.abs(v.y), Math.abs(v.z));
	}
	
	public static Vector3D add(final Vector3D vLHS, final Vector3D vRHS) {
		return new Vector3D(vLHS.x + vRHS.x, vLHS.y + vRHS.y, vLHS.z + vRHS.z);
	}
	
	public static Vector3D crossProduct(final Vector3D vLHS, final Vector3D vRHS) {
		return new Vector3D(vLHS.y * vRHS.z - vLHS.z * vRHS.y, vLHS.z * vRHS.x - vLHS.x * vRHS.z, vLHS.x * vRHS.y - vLHS.y * vRHS.x);
	}
	
	public static Vector3D direction(final Point3D eye, final Point3D lookAt) {
		return new Vector3D(lookAt.x - eye.x, lookAt.y - eye.y, lookAt.z - eye.z);
	}
	
	public static Vector3D direction(final Vector3D u, final Vector3D v, final Vector3D w) {
		return new Vector3D(u.x + v.x + w.x, u.y + v.y + w.y, u.z + v.z + w.z);
	}
	
	public static Vector3D direction(final Vector3D u, final Vector3D v, final Vector3D w, final Vector3D s) {
		return new Vector3D(u.x * s.x + v.x * s.y + w.x * s.z, u.y * s.x + v.y * s.y + w.y * s.z, u.z * s.x + v.z * s.y + w.z * s.z);
	}
	
	public static Vector3D directionNormalized(final Point3D eye, final Point3D lookAt) {
		return normalize(direction(eye, lookAt));
	}
	
	public static Vector3D directionNormalized(final Vector3D u, final Vector3D v, final Vector3D w) {
		return normalize(direction(u, v, w));
	}
	
	public static Vector3D directionNormalized(final Vector3D u, final Vector3D v, final Vector3D w, final Vector3D s) {
		return normalize(direction(u, v, w, s));
	}
	
	public static Vector3D directionSpherical(final double sinTheta, final double cosTheta, final double phi) {
		return new Vector3D(sinTheta * Math.cos(phi), sinTheta * Math.sin(phi), cosTheta);
	}
	
	public static Vector3D directionSphericalNormalized(final double sinTheta, final double cosTheta, final double phi) {
		return normalize(directionSpherical(sinTheta, cosTheta, phi));
	}
	
	public static Vector3D divide(final Vector3D v, final double s) {
		return new Vector3D(v.x / s, v.y / s, v.z / s);
	}
	
	public static Vector3D multiply(final Vector3D v, final double s) {
		return new Vector3D(v.x * s, v.y * s, v.z * s);
	}
	
	public static Vector3D negate(final Vector3D v) {
		return new Vector3D(-v.x, -v.y, -v.z);
	}
	
	public static Vector3D normalize(final Vector3D v) {
		return divide(v, v.length());
	}
	
	public static Vector3D orientNormal(final Vector3D direction, final Vector3D normal) {
		return dotProduct(direction, normal) < 0.0D ? normal : negate(normal);
	}
	
	public static Vector3D orthogonal(final Vector3D v) {
		final Vector3D v0 = normalize(v);
		final Vector3D v1 = abs(v0);
		
		if(v1.x < v1.y && v1.x < v1.z) {
			return normalize(new Vector3D(+0.0D, +v0.y, -v0.y));
		} else if(v1.y < v1.z) {
			return normalize(new Vector3D(+v0.z, +0.0D, -v0.x));
		} else {
			return normalize(new Vector3D(+v0.y, -v0.x, +0.0D));
		}
	}
	
	public static Vector3D reflection(final Vector3D direction, final Vector3D normal) {
		return subtract(direction, multiply(normal, dotProduct(direction, normal) * 2.0D));
	}
	
	public static Vector3D sampleHemisphereCosineDistribution() {
		return sampleHemisphereCosineDistribution(Math.random(), Math.random());
	}
	
	public static Vector3D sampleHemisphereCosineDistribution(final double u, final double v) {
		return directionSpherical(Math.sqrt(v), Math.sqrt(1.0D - v), 2.0D * Math.PI * u);
	}
	
	public static Vector3D sampleHemispherePowerCosineDistribution() {
		return sampleHemispherePowerCosineDistribution(Math.random(), Math.random());
	}
	
	public static Vector3D sampleHemispherePowerCosineDistribution(final double u, final double v) {
		return sampleHemispherePowerCosineDistribution(u, v, 20.0D);
	}
	
	public static Vector3D sampleHemispherePowerCosineDistribution(final double u, final double v, final double exponent) {
		final double cosTheta = Math.pow(1.0D - v, 1.0D / (exponent + 1.0D));
		final double sinTheta = Math.sqrt(Math.max(0.0D, 1.0D - cosTheta * cosTheta));
		final double phi = 2.0D * Math.PI * u;
		
		return directionSpherical(sinTheta, cosTheta, phi);
	}
	
	public static Vector3D subtract(final Vector3D vLHS, final Vector3D vRHS) {
		return new Vector3D(vLHS.x - vRHS.x, vLHS.y - vRHS.y, vLHS.z - vRHS.z);
	}
	
	public static Vector3D x() {
		return new Vector3D(1.0D, 0.0D, 0.0D);
	}
	
	public static Vector3D y() {
		return new Vector3D(0.0D, 1.0D, 0.0D);
	}
}