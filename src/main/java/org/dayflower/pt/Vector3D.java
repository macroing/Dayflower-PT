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
import java.util.Optional;

public final class Vector3D {
	public final double x;
	public final double y;
	public final double z;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Vector3D(final Point3D p) {
		this(p.x, p.y, p.z);
	}
	
	public Vector3D(final double x, final double y, final double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public String toString() {
		return String.format("new Vector3D(%s, %s, %s)", Utilities.toNonScientificNotationJava(this.x), Utilities.toNonScientificNotationJava(this.y), Utilities.toNonScientificNotationJava(this.z));
	}
	
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof Vector3D)) {
			return false;
		} else {
			return equals(Vector3D.class.cast(object));
		}
	}
	
	public boolean equals(final Vector3D v) {
		if(v == this) {
			return true;
		} else if(v == null) {
			return false;
		} else if(!Math.equals(this.x, v.x)) {
			return false;
		} else if(!Math.equals(this.y, v.y)) {
			return false;
		} else if(!Math.equals(this.z, v.z)) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean isZero() {
		return Math.isZero(this.x) && Math.isZero(this.y) && Math.isZero(this.z);  
	}
	
	public double cosPhi() {
		final double sinTheta = sinTheta();
		
		if(Math.isZero(sinTheta)) {
			return 1.0D;
		}
		
		return Math.saturate(this.x / sinTheta, -1.0D, 1.0D);
	}
	
	public double cosPhiSquared() {
		return cosPhi() * cosPhi();
	}
	
	public double cosTheta() {
		return this.z;
	}
	
	public double cosThetaAbs() {
		return Math.abs(cosTheta());
	}
	
	public double cosThetaQuartic() {
		return cosThetaSquared() * cosThetaSquared();
	}
	
	public double cosThetaSquared() {
		return cosTheta() * cosTheta();
	}
	
	public double length() {
		return Math.sqrt(lengthSquared());
	}
	
	public double lengthSquared() {
		return this.x * this.x + this.y * this.y + this.z * this.z;
	}
	
	public double sinPhi() {
		final double sinTheta = sinTheta();
		
		if(Math.isZero(sinTheta)) {
			return 0.0D;
		}
		
		return Math.saturate(this.y / sinTheta, -1.0D, 1.0D);
	}
	
	public double sinPhiSquared() {
		return sinPhi() * sinPhi();
	}
	
	public double sinTheta() {
		return Math.sqrt(sinThetaSquared());
	}
	
	public double sinThetaSquared() {
		return Math.max(0.0D, 1.0D - cosThetaSquared());
	}
	
	public double tanTheta() {
		return sinTheta() / cosTheta();
	}
	
	public double tanThetaAbs() {
		return Math.abs(tanTheta());
	}
	
	public double tanThetaSquared() {
		return sinThetaSquared() / cosThetaSquared();
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(Double.valueOf(this.x), Double.valueOf(this.y), Double.valueOf(this.z));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
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
	
	public static Vector3D faceForwardLHS(final Vector3D vLHS, final Vector3D vRHS) {
		return dotProduct(vLHS, vRHS) < 0.0D ? negate(vLHS) : vLHS;
	}
	
	public static Vector3D faceForwardLHSNegated(final Vector3D vLHS, final Vector3D vRHS) {
		return dotProduct(vLHS, vRHS) > 0.0D ? negate(vLHS) : vLHS;
	}
	
	public static Vector3D faceForwardRHSZ(final Vector3D vLHS, final Vector3D vRHS) {
		return vLHS.z < 0.0D ? negateZ(vRHS) : vRHS;
	}
	
	public static Vector3D hadamardProduct(final Vector3D vLHS, final Vector3D vRHS) {
		return new Vector3D(vLHS.x * vRHS.x, vLHS.y * vRHS.y, vLHS.z * vRHS.z);
	}
	
	public static Vector3D multiply(final Vector3D v, final double s) {
		return new Vector3D(v.x * s, v.y * s, v.z * s);
	}
	
	public static Vector3D negate(final Vector3D v) {
		return new Vector3D(-v.x, -v.y, -v.z);
	}
	
	public static Vector3D negateZ(final Vector3D v) {
		return new Vector3D(v.x, v.y, -v.z);
	}
	
	public static Vector3D normal(final Point3D a, final Point3D b, final Point3D c) {
		return crossProduct(directionNormalized(a, b), directionNormalized(a, c));
	}
	
	public static Vector3D normalNormalized(final Point3D a, final Point3D b, final Point3D c) {
		return normalize(normal(a, b, c));
	}
	
	public static Vector3D normalize(final Vector3D v) {
		return divide(v, v.length());
	}
	
	public static Vector3D orientNormal(final Vector3D direction, final Vector3D normal) {
		return dotProduct(direction, normal) < 0.0D ? normal : negate(normal);
	}
	
	public static Vector3D orientNormalSameHemisphereZ(final Vector3D direction, final Vector3D normal) {
		return sameHemisphereZ(direction, normal) ? normal : negate(normal);
	}
	
	public static Vector3D orthogonal(final Vector3D v) {
		final Vector3D v0 = normalize(v);
		final Vector3D v1 = abs(v0);
		
		if(v1.x > v1.y && v1.x > v1.z) {
			return normalize(new Vector3D(+0.0D, +v0.z, -v0.y));
		} else if(v1.y > v1.z) {
			return normalize(new Vector3D(+v0.z, +0.0D, -v0.x));
		} else {
			return normalize(new Vector3D(+v0.y, -v0.x, +0.0D));
		}
	}
	
	public static Vector3D reciprocal(final Vector3D v) {
		return new Vector3D(1.0D / v.x, 1.0D / v.y, 1.0D / v.z);
	}
	
	public static Vector3D reflection(final Vector3D direction, final Vector3D normal) {
		return reflection(direction, normal, false);
	}
	
	public static Vector3D reflection(final Vector3D direction, final Vector3D normal, final boolean isFacingSurface) {
		return isFacingSurface ? subtract(direction, multiply(normal, dotProduct(direction, normal) * 2.0D)) : subtract(multiply(normal, dotProduct(direction, normal) * 2.0D), direction);
	}
	
	public static Vector3D sampleHemisphereCosineDistribution() {
		return sampleHemisphereCosineDistribution(Point2D.sampleRandom());
	}
	
	public static Vector3D sampleHemisphereCosineDistribution(final Point2D p) {
//		This version is similar to the one in SmallPT:
//		return directionSpherical(Math.sqrt(p.y), Math.sqrt(1.0D - p.y), 2.0D * Math.PI * p.x);
		
		final Point2D q = Point2D.sampleDiskUniformDistributionByConcentricMapping(p);
		
		return new Vector3D(q.x, q.y, Math.sqrt(Math.max(0.0D, 1.0D - q.x * q.x - q.y * q.y)));
	}
	
	public static Vector3D sampleHemispherePowerCosineDistribution() {
		return sampleHemispherePowerCosineDistribution(Point2D.sampleRandom());
	}
	
	public static Vector3D sampleHemispherePowerCosineDistribution(final Point2D p) {
		return sampleHemispherePowerCosineDistribution(p, 20.0D);
	}
	
	public static Vector3D sampleHemispherePowerCosineDistribution(final Point2D p, final double exponent) {
		final double cosTheta = Math.pow(1.0D - p.y, 1.0D / (exponent + 1.0D));
		final double sinTheta = Math.sqrt(Math.max(0.0D, 1.0D - cosTheta * cosTheta));
		final double phi = 2.0D * Math.PI * p.x;
		
		return directionSpherical(sinTheta, cosTheta, phi);
	}
	
	public static Vector3D subtract(final Vector3D vLHS, final Vector3D vRHS) {
		return new Vector3D(vLHS.x - vRHS.x, vLHS.y - vRHS.y, vLHS.z - vRHS.z);
	}
	
	public static Vector3D transform(final Matrix44D mLHS, final Vector3D vRHS) {
		return new Vector3D(mLHS.element11 * vRHS.x + mLHS.element12 * vRHS.y + mLHS.element13 * vRHS.z, mLHS.element21 * vRHS.x + mLHS.element22 * vRHS.y + mLHS.element23 * vRHS.z, mLHS.element31 * vRHS.x + mLHS.element32 * vRHS.y + mLHS.element33 * vRHS.z);
	}
	
	public static Vector3D transform(final Vector3D v, final OrthonormalBasis33D o) {
		return new Vector3D(v.x * o.u.x + v.y * o.v.x + v.z * o.w.x, v.x * o.u.y + v.y * o.v.y + v.z * o.w.y, v.x * o.u.z + v.y * o.v.z + v.z * o.w.z);
	}
	
	public static Vector3D transformNormalize(final Vector3D v, final OrthonormalBasis33D o) {
		return normalize(transform(v, o));
	}
	
	public static Vector3D transformReverse(final Vector3D v, final OrthonormalBasis33D o) {
		return new Vector3D(dotProduct(v, o.u), dotProduct(v, o.v), dotProduct(v, o.w));
	}
	
	public static Vector3D transformReverseNormalize(final Vector3D v, final OrthonormalBasis33D o) {
		return normalize(transformReverse(v, o));
	}
	
	public static Vector3D transformTranspose(final Matrix44D mLHS, final Vector3D vRHS) {
		return new Vector3D(mLHS.element11 * vRHS.x + mLHS.element21 * vRHS.y + mLHS.element31 * vRHS.z, mLHS.element12 * vRHS.x + mLHS.element22 * vRHS.y + mLHS.element32 * vRHS.z, mLHS.element13 * vRHS.x + mLHS.element23 * vRHS.y + mLHS.element33 * vRHS.z);
	}
	
	public static Vector3D x() {
		return x(1.0D);
	}
	
	public static Vector3D x(final double x) {
		return new Vector3D(x, 0.0D, 0.0D);
	}
	
	public static Vector3D y() {
		return y(1.0D);
	}
	
	public static Vector3D y(final double y) {
		return new Vector3D(0.0D, y, 0.0D);
	}
	
	public static Vector3D z() {
		return z(1.0D);
	}
	
	public static Vector3D z(final double z) {
		return new Vector3D(0.0D, 0.0D, z);
	}
	
	public static boolean sameHemisphereZ(final Vector3D vLHS, final Vector3D vRHS) {
		return vLHS.z * vRHS.z > 0.0D;
	}
	
	public static double dotProduct(final Vector3D vLHS, final Vector3D vRHS) {
		return vLHS.x * vRHS.x + vLHS.y * vRHS.y + vLHS.z * vRHS.z;
	}
	
	public static double dotProductAbs(final Vector3D vLHS, final Vector3D vRHS) {
		return Math.abs(dotProduct(vLHS, vRHS));
	}
	
	public static double tripleProduct(final Vector3D vLHSDP, final Vector3D vLHSCP, final Vector3D vRHSCP) {
		return dotProduct(vLHSDP, crossProduct(vLHSCP, vRHSCP));
	}
}