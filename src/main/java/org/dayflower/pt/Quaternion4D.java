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

import org.macroing.java.lang.Strings;

public final class Quaternion4D {
	public final double w;
	public final double x;
	public final double y;
	public final double z;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Quaternion4D() {
		this(0.0D, 0.0D, 0.0D);
	}
	
	public Quaternion4D(final double x, final double y, final double z) {
		this(x, y, z, 1.0D);
	}
	
	public Quaternion4D(final double x, final double y, final double z, final double w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public String toString() {
		return String.format("new Quaternion4D(%s, %s, %s, %s)", Strings.toNonScientificNotationJava(this.x), Strings.toNonScientificNotationJava(this.y), Strings.toNonScientificNotationJava(this.z), Strings.toNonScientificNotationJava(this.w));
	}
	
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof Quaternion4D)) {
			return false;
		} else {
			return equals(Quaternion4D.class.cast(object));
		}
	}
	
	public boolean equals(final Quaternion4D q) {
		if(q == this) {
			return true;
		} else if(q == null) {
			return false;
		} else if(!Math.equals(this.w, q.w)) {
			return false;
		} else if(!Math.equals(this.x, q.x)) {
			return false;
		} else if(!Math.equals(this.y, q.y)) {
			return false;
		} else if(!Math.equals(this.z, q.z)) {
			return false;
		} else {
			return true;
		}
	}
	
	public double length() {
		return Math.sqrt(lengthSquared());
	}
	
	public double lengthSquared() {
		return this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(Double.valueOf(this.w), Double.valueOf(this.x), Double.valueOf(this.y), Double.valueOf(this.z));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static Quaternion4D add(final Quaternion4D qLHS, final Quaternion4D qRHS) {
		return new Quaternion4D(qLHS.x + qRHS.x, qLHS.y + qRHS.y, qLHS.z + qRHS.z, qLHS.w + qRHS.w);
	}
	
	public static Quaternion4D conjugate(final Quaternion4D q) {
		return new Quaternion4D(-q.x, -q.y, -q.z, +q.w);
	}
	
	public static Quaternion4D divide(final Quaternion4D qLHS, final double sRHS) {
		return new Quaternion4D(qLHS.x / sRHS, qLHS.y / sRHS, qLHS.z / sRHS, qLHS.w / sRHS);
	}
	
//	TODO: Add unit tests!
	public static Quaternion4D from(final Matrix44D m) {
		if(m.element11 + m.element22 + m.element33 > 0.0D) {
			final double scalar = 0.5D / Math.sqrt(m.element11 + m.element22 + m.element33 + 1.0D);
			
			return normalize(new Quaternion4D((m.element23 - m.element32) * scalar, (m.element31 - m.element13) * scalar, (m.element12 - m.element21) * scalar, 0.25D / scalar));
		} else if(m.element11 > m.element22 && m.element11 > m.element33) {
			final double scalar = 2.0D * Math.sqrt(1.0D + m.element11 - m.element22 - m.element23);
			
			return normalize(new Quaternion4D(0.25D * scalar, (m.element21 + m.element12) / scalar, (m.element31 + m.element13) / scalar, (m.element23 - m.element32) / scalar));
		} else if(m.element22 > m.element33) {
			final double scalar = 2.0D * Math.sqrt(1.0D + m.element22 - m.element11 - m.element33);
			
			return normalize(new Quaternion4D((m.element21 + m.element12) / scalar, 0.25D * scalar, (m.element32 + m.element23) / scalar, (m.element31 - m.element13) / scalar));
		} else {
			final double scalar = 2.0F * Math.sqrt(1.0D + m.element33 - m.element11 - m.element22);
			
			return normalize(new Quaternion4D((m.element31 + m.element13) / scalar, (m.element23 + m.element32) / scalar, 0.25D * scalar, (m.element12 - m.element21) / scalar));
		}
	}
	
	public static Quaternion4D from(final OrthonormalBasis33D o) {
		return from(Matrix44D.rotate(o));
	}
	
	public static Quaternion4D from(final Vector3D direction) {
		return from(new OrthonormalBasis33D(direction));
	}
	
	public static Quaternion4D multiply(final Quaternion4D qLHS, final Quaternion4D qRHS) {
		return new Quaternion4D(qLHS.x * qRHS.w + qLHS.w * qRHS.x + qLHS.y * qRHS.z - qLHS.z * qRHS.y, qLHS.y * qRHS.w + qLHS.w * qRHS.y + qLHS.z * qRHS.x - qLHS.x * qRHS.z, qLHS.z * qRHS.w + qLHS.w * qRHS.z + qLHS.x * qRHS.y - qLHS.y * qRHS.x, qLHS.w * qRHS.w - qLHS.x * qRHS.x - qLHS.y * qRHS.y - qLHS.z * qRHS.z);
	}
	
	public static Quaternion4D multiply(final Quaternion4D qLHS, final Vector3D vRHS) {
		return new Quaternion4D(+qLHS.w * vRHS.x + qLHS.y * vRHS.z - qLHS.z * vRHS.y, +qLHS.w * vRHS.y + qLHS.z * vRHS.x - qLHS.x * vRHS.z, +qLHS.w * vRHS.z + qLHS.x * vRHS.y - qLHS.y * vRHS.x, -qLHS.x * vRHS.x - qLHS.y * vRHS.y - qLHS.z * vRHS.z);
	}
	
	public static Quaternion4D multiply(final Quaternion4D qLHS, final double sRHS) {
		return new Quaternion4D(qLHS.x * sRHS, qLHS.y * sRHS, qLHS.z * sRHS, qLHS.w * sRHS);
	}
	
	public static Quaternion4D negate(final Quaternion4D q) {
		return new Quaternion4D(-q.x, -q.y, -q.z, -q.w);
	}
	
	public static Quaternion4D normalize(final Quaternion4D q) {
		final double length = q.length();
		
		final boolean isLengthGTEThreshold = length >= Math.NEXT_DOWN_1_3;
		final boolean isLengthLTEThreshold = length <= Math.NEXT_UP_1_1;
		
		if(isLengthGTEThreshold && isLengthLTEThreshold) {
			return q;
		}
		
		return divide(q, length);
	}
	
	public static Quaternion4D subtract(final Quaternion4D qLHS, final Quaternion4D qRHS) {
		return new Quaternion4D(qLHS.x - qRHS.x, qLHS.y - qRHS.y, qLHS.z - qRHS.z, qLHS.w - qRHS.w);
	}
	
	public static double dotProduct(final Quaternion4D qLHS, final Quaternion4D qRHS) {
		return qLHS.x * qRHS.x + qLHS.y * qRHS.y + qLHS.z * qRHS.z + qLHS.w * qRHS.w;
	}
}