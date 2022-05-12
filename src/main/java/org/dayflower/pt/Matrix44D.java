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

public final class Matrix44D {
	public final double element11;
	public final double element12;
	public final double element13;
	public final double element14;
	public final double element21;
	public final double element22;
	public final double element23;
	public final double element24;
	public final double element31;
	public final double element32;
	public final double element33;
	public final double element34;
	public final double element41;
	public final double element42;
	public final double element43;
	public final double element44;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Matrix44D() {
		this(1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D);
	}
	
	public Matrix44D(final double element11, final double element12, final double element13, final double element14, final double element21, final double element22, final double element23, final double element24, final double element31, final double element32, final double element33, final double element34, final double element41, final double element42, final double element43, final double element44) {
		this.element11 = element11;
		this.element12 = element12;
		this.element13 = element13;
		this.element14 = element14;
		this.element21 = element21;
		this.element22 = element22;
		this.element23 = element23;
		this.element24 = element24;
		this.element31 = element31;
		this.element32 = element32;
		this.element33 = element33;
		this.element34 = element34;
		this.element41 = element41;
		this.element42 = element42;
		this.element43 = element43;
		this.element44 = element44;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public String toString() {
		final String row1 = String.format("%s, %s, %s, %s", Utilities.toNonScientificNotationJava(this.element11), Utilities.toNonScientificNotationJava(this.element12), Utilities.toNonScientificNotationJava(this.element13), Utilities.toNonScientificNotationJava(this.element14));
		final String row2 = String.format("%s, %s, %s, %s", Utilities.toNonScientificNotationJava(this.element21), Utilities.toNonScientificNotationJava(this.element22), Utilities.toNonScientificNotationJava(this.element23), Utilities.toNonScientificNotationJava(this.element24));
		final String row3 = String.format("%s, %s, %s, %s", Utilities.toNonScientificNotationJava(this.element31), Utilities.toNonScientificNotationJava(this.element32), Utilities.toNonScientificNotationJava(this.element33), Utilities.toNonScientificNotationJava(this.element34));
		final String row4 = String.format("%s, %s, %s, %s", Utilities.toNonScientificNotationJava(this.element41), Utilities.toNonScientificNotationJava(this.element42), Utilities.toNonScientificNotationJava(this.element43), Utilities.toNonScientificNotationJava(this.element44));
		
		return String.format("new Matrix44D(%s, %s, %s, %s)", row1, row2, row3, row4);
	}
	
	public boolean equals(final Matrix44D m) {
		if(m == this) {
			return true;
		} else if(m == null) {
			return false;
		} else if(!Math.equals(this.element11, m.element11)) {
			return false;
		} else if(!Math.equals(this.element12, m.element12)) {
			return false;
		} else if(!Math.equals(this.element13, m.element13)) {
			return false;
		} else if(!Math.equals(this.element14, m.element14)) {
			return false;
		} else if(!Math.equals(this.element21, m.element21)) {
			return false;
		} else if(!Math.equals(this.element22, m.element22)) {
			return false;
		} else if(!Math.equals(this.element23, m.element23)) {
			return false;
		} else if(!Math.equals(this.element24, m.element24)) {
			return false;
		} else if(!Math.equals(this.element31, m.element31)) {
			return false;
		} else if(!Math.equals(this.element32, m.element32)) {
			return false;
		} else if(!Math.equals(this.element33, m.element33)) {
			return false;
		} else if(!Math.equals(this.element34, m.element34)) {
			return false;
		} else if(!Math.equals(this.element41, m.element41)) {
			return false;
		} else if(!Math.equals(this.element42, m.element42)) {
			return false;
		} else if(!Math.equals(this.element43, m.element43)) {
			return false;
		} else if(!Math.equals(this.element44, m.element44)) {
			return false;
		} else {
			return true;
		}
	}
	
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof Matrix44D)) {
			return false;
		} else {
			return equals(Matrix44D.class.cast(object));
		}
	}
	
	public boolean isInvertible() {
		return Math.abs(determinant()) >= 1.0e-12D;
	}
	
	public double determinant() {
		final double a = this.element11 * this.element22 - this.element12 * this.element21;
		final double b = this.element11 * this.element23 - this.element13 * this.element21;
		final double c = this.element11 * this.element24 - this.element14 * this.element21;
		final double d = this.element12 * this.element23 - this.element13 * this.element22;
		final double e = this.element12 * this.element24 - this.element14 * this.element22;
		final double f = this.element13 * this.element24 - this.element14 * this.element23;
		final double g = this.element31 * this.element42 - this.element32 * this.element41;
		final double h = this.element31 * this.element43 - this.element33 * this.element41;
		final double i = this.element31 * this.element44 - this.element34 * this.element41;
		final double j = this.element32 * this.element43 - this.element33 * this.element42;
		final double k = this.element32 * this.element44 - this.element34 * this.element42;
		final double l = this.element33 * this.element44 - this.element34 * this.element43;
		
		return a * l - b * k + c * j + d * i - e * h + f * g;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(new Object[] {
			Double.valueOf(this.element11), Double.valueOf(this.element12), Double.valueOf(this.element13), Double.valueOf(this.element14),
			Double.valueOf(this.element21), Double.valueOf(this.element22), Double.valueOf(this.element23), Double.valueOf(this.element24),
			Double.valueOf(this.element31), Double.valueOf(this.element32), Double.valueOf(this.element33), Double.valueOf(this.element34),
			Double.valueOf(this.element41), Double.valueOf(this.element42), Double.valueOf(this.element43), Double.valueOf(this.element44)
		});
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static Matrix44D identity() {
		return new Matrix44D();
	}
	
	public static Matrix44D inverse(final Matrix44D m) {
		final double a = m.element11 * m.element22 - m.element12 * m.element21;
		final double b = m.element11 * m.element23 - m.element13 * m.element21;
		final double c = m.element11 * m.element24 - m.element14 * m.element21;
		final double d = m.element12 * m.element23 - m.element13 * m.element22;
		final double e = m.element12 * m.element24 - m.element14 * m.element22;
		final double f = m.element13 * m.element24 - m.element14 * m.element23;
		final double g = m.element31 * m.element42 - m.element32 * m.element41;
		final double h = m.element31 * m.element43 - m.element33 * m.element41;
		final double i = m.element31 * m.element44 - m.element34 * m.element41;
		final double j = m.element32 * m.element43 - m.element33 * m.element42;
		final double k = m.element32 * m.element44 - m.element34 * m.element42;
		final double l = m.element33 * m.element44 - m.element34 * m.element43;
		
		final double determinant = a * l - b * k + c * j + d * i - e * h + f * g;
		final double determinantReciprocal = 1.0D / determinant;
		
		if(Math.abs(determinant) < 1.0e-12D) {
			throw new IllegalArgumentException("The Matrix44D 'm' cannot be inverted!");
		}
		
		final double element11 = (+m.element22 * l - m.element23 * k + m.element24 * j) * determinantReciprocal;
		final double element12 = (-m.element12 * l + m.element13 * k - m.element14 * j) * determinantReciprocal;
		final double element13 = (+m.element42 * f - m.element43 * e + m.element44 * d) * determinantReciprocal;
		final double element14 = (-m.element32 * f + m.element33 * e - m.element34 * d) * determinantReciprocal;
		final double element21 = (-m.element21 * l + m.element23 * i - m.element24 * h) * determinantReciprocal;
		final double element22 = (+m.element11 * l - m.element13 * i + m.element14 * h) * determinantReciprocal;
		final double element23 = (-m.element41 * f + m.element43 * c - m.element44 * b) * determinantReciprocal;
		final double element24 = (+m.element31 * f - m.element33 * c + m.element34 * b) * determinantReciprocal;
		final double element31 = (+m.element21 * k - m.element22 * i + m.element24 * g) * determinantReciprocal;
		final double element32 = (-m.element11 * k + m.element12 * i - m.element14 * g) * determinantReciprocal;
		final double element33 = (+m.element41 * e - m.element42 * c + m.element44 * a) * determinantReciprocal;
		final double element34 = (-m.element31 * e + m.element32 * c - m.element34 * a) * determinantReciprocal;
		final double element41 = (-m.element21 * j + m.element22 * h - m.element23 * g) * determinantReciprocal;
		final double element42 = (+m.element11 * j - m.element12 * h + m.element13 * g) * determinantReciprocal;
		final double element43 = (-m.element41 * d + m.element42 * b - m.element43 * a) * determinantReciprocal;
		final double element44 = (+m.element31 * d - m.element32 * b + m.element33 * a) * determinantReciprocal;
		
		return new Matrix44D(element11, element12, element13, element14, element21, element22, element23, element24, element31, element32, element33, element34, element41, element42, element43, element44);
	}
	
	public static Matrix44D multiply(final Matrix44D mLHS, final Matrix44D mRHS) {
		final double element11 = mLHS.element11 * mRHS.element11 + mLHS.element12 * mRHS.element21 + mLHS.element13 * mRHS.element31 + mLHS.element14 * mRHS.element41;
		final double element12 = mLHS.element11 * mRHS.element12 + mLHS.element12 * mRHS.element22 + mLHS.element13 * mRHS.element32 + mLHS.element14 * mRHS.element42;
		final double element13 = mLHS.element11 * mRHS.element13 + mLHS.element12 * mRHS.element23 + mLHS.element13 * mRHS.element33 + mLHS.element14 * mRHS.element43;
		final double element14 = mLHS.element11 * mRHS.element14 + mLHS.element12 * mRHS.element24 + mLHS.element13 * mRHS.element34 + mLHS.element14 * mRHS.element44;
		final double element21 = mLHS.element21 * mRHS.element11 + mLHS.element22 * mRHS.element21 + mLHS.element23 * mRHS.element31 + mLHS.element24 * mRHS.element41;
		final double element22 = mLHS.element21 * mRHS.element12 + mLHS.element22 * mRHS.element22 + mLHS.element23 * mRHS.element32 + mLHS.element24 * mRHS.element42;
		final double element23 = mLHS.element21 * mRHS.element13 + mLHS.element22 * mRHS.element23 + mLHS.element23 * mRHS.element33 + mLHS.element24 * mRHS.element43;
		final double element24 = mLHS.element21 * mRHS.element14 + mLHS.element22 * mRHS.element24 + mLHS.element23 * mRHS.element34 + mLHS.element24 * mRHS.element44;
		final double element31 = mLHS.element31 * mRHS.element11 + mLHS.element32 * mRHS.element21 + mLHS.element33 * mRHS.element31 + mLHS.element34 * mRHS.element41;
		final double element32 = mLHS.element31 * mRHS.element12 + mLHS.element32 * mRHS.element22 + mLHS.element33 * mRHS.element32 + mLHS.element34 * mRHS.element42;
		final double element33 = mLHS.element31 * mRHS.element13 + mLHS.element32 * mRHS.element23 + mLHS.element33 * mRHS.element33 + mLHS.element34 * mRHS.element43;
		final double element34 = mLHS.element31 * mRHS.element14 + mLHS.element32 * mRHS.element24 + mLHS.element33 * mRHS.element34 + mLHS.element34 * mRHS.element44;
		final double element41 = mLHS.element41 * mRHS.element11 + mLHS.element42 * mRHS.element21 + mLHS.element43 * mRHS.element31 + mLHS.element44 * mRHS.element41;
		final double element42 = mLHS.element41 * mRHS.element12 + mLHS.element42 * mRHS.element22 + mLHS.element43 * mRHS.element32 + mLHS.element44 * mRHS.element42;
		final double element43 = mLHS.element41 * mRHS.element13 + mLHS.element42 * mRHS.element23 + mLHS.element43 * mRHS.element33 + mLHS.element44 * mRHS.element43;
		final double element44 = mLHS.element41 * mRHS.element14 + mLHS.element42 * mRHS.element24 + mLHS.element43 * mRHS.element34 + mLHS.element44 * mRHS.element44;
		
		return new Matrix44D(element11, element12, element13, element14, element21, element22, element23, element24, element31, element32, element33, element34, element41, element42, element43, element44);
	}
	
	public static Matrix44D rotate(final OrthonormalBasis33D o) {
		return rotate(o.w, o.v, o.u);
	}
	
//	TODO: Add unit tests!
	public static Matrix44D rotate(final Quaternion4D q) {
		final double uX = 1.0D - 2.0D * (q.y * q.y + q.z * q.z);
		final double uY = 0.0D + 2.0D * (q.x * q.y - q.w * q.z);
		final double uZ = 0.0D + 2.0D * (q.x * q.z + q.w * q.y);
		final double vX = 0.0D + 2.0D * (q.x * q.y + q.w * q.z);
		final double vY = 1.0D - 2.0D * (q.x * q.x + q.z * q.z);
		final double vZ = 0.0D + 2.0D * (q.y * q.z - q.w * q.x);
		final double wX = 0.0D + 2.0D * (q.x * q.z - q.w * q.y);
		final double wY = 0.0D + 2.0D * (q.y * q.z + q.w * q.x);
		final double wZ = 1.0D - 2.0D * (q.x * q.x + q.y * q.y);
		
		final Vector3D u = new Vector3D(uX, uY, uZ);
		final Vector3D v = new Vector3D(vX, vY, vZ);
		final Vector3D w = new Vector3D(wX, wY, wZ);
		
		return rotate(w, v, u);
	}
	
	public static Matrix44D rotate(final Vector3D w, final Vector3D v) {
		final Vector3D wNormalized = Vector3D.normalize(w);
		final Vector3D uNormalized = Vector3D.normalize(Vector3D.crossProduct(Vector3D.normalize(v), wNormalized));
		final Vector3D vNormalized = Vector3D.crossProduct(wNormalized, uNormalized);
		
		return rotate(wNormalized, vNormalized, uNormalized);
	}
	
	public static Matrix44D rotate(final Vector3D w, final Vector3D v, final Vector3D u) {
		final double element11 = u.x;
		final double element12 = v.x;
		final double element13 = w.x;
		final double element14 = 0.0D;
		final double element21 = u.y;
		final double element22 = v.y;
		final double element23 = w.y;
		final double element24 = 0.0D;
		final double element31 = u.z;
		final double element32 = v.z;
		final double element33 = w.z;
		final double element34 = 0.0D;
		final double element41 = 0.0D;
		final double element42 = 0.0D;
		final double element43 = 0.0D;
		final double element44 = 1.0D;
		
		return new Matrix44D(element11, element12, element13, element14, element21, element22, element23, element24, element31, element32, element33, element34, element41, element42, element43, element44);
	}
	
	public static Matrix44D rotateX(final double angle) {
		return rotateX(angle, false);
	}
	
	public static Matrix44D rotateX(final double angle, final boolean isRadians) {
		final double angleRadians = isRadians ? angle : Math.toRadians(angle);
		
		final double cos = Math.cos(angleRadians);
		final double sin = Math.sin(angleRadians);
		
		return new Matrix44D(1.0D, 0.0D, 0.0D, 0.0D, 0.0D, cos, -sin, 0.0D, 0.0D, sin, cos, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D);
	}
	
	public static Matrix44D rotateY(final double angle) {
		return rotateY(angle, false);
	}
	
	public static Matrix44D rotateY(final double angle, final boolean isRadians) {
		final double angleRadians = isRadians ? angle : Math.toRadians(angle);
		
		final double cos = Math.cos(angleRadians);
		final double sin = Math.sin(angleRadians);
		
		return new Matrix44D(cos, 0.0D, sin, 0.0D, 0.0D, 1.0D, 0.0D, 0.0D, -sin, 0.0D, cos, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D);
	}
	
	public static Matrix44D rotateZ(final double angle) {
		return rotateZ(angle, false);
	}
	
	public static Matrix44D rotateZ(final double angle, final boolean isRadians) {
		final double angleRadians = isRadians ? angle : Math.toRadians(angle);
		
		final double cos = Math.cos(angleRadians);
		final double sin = Math.sin(angleRadians);
		
		return new Matrix44D(cos, -sin, 0.0D, 0.0D, sin, cos, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D);
	}
	
	public static Matrix44D scale(final Vector3D v) {
		return scale(v.x, v.y, v.z);
	}
	
	public static Matrix44D scale(final double scalar) {
		return scale(scalar, scalar, scalar);
	}
	
	public static Matrix44D scale(final double x, final double y, final double z) {
		return new Matrix44D(x, 0.0D, 0.0D, 0.0D, 0.0D, y, 0.0D, 0.0D, 0.0D, 0.0D, z, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D);
	}
	
	public static Matrix44D translate(final Point3D p) {
		return translate(p.x, p.y, p.z);
	}
	
	public static Matrix44D translate(final double x, final double y, final double z) {
		return new Matrix44D(1.0D, 0.0D, 0.0D, x, 0.0D, 1.0D, 0.0D, y, 0.0D, 0.0D, 1.0D, z, 0.0D, 0.0D, 0.0D, 1.0D);
	}
	
	public static Matrix44D transpose(final Matrix44D m) {
		final double element11 = m.element11;
		final double element12 = m.element21;
		final double element13 = m.element31;
		final double element14 = m.element41;
		final double element21 = m.element12;
		final double element22 = m.element22;
		final double element23 = m.element32;
		final double element24 = m.element42;
		final double element31 = m.element13;
		final double element32 = m.element23;
		final double element33 = m.element33;
		final double element34 = m.element43;
		final double element41 = m.element14;
		final double element42 = m.element24;
		final double element43 = m.element34;
		final double element44 = m.element44;
		
		return new Matrix44D(element11, element12, element13, element14, element21, element22, element23, element24, element31, element32, element33, element34, element41, element42, element43, element44);
	}
}