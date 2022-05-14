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

public final class OrthonormalBasis33D {
	public final Vector3D u;
	public final Vector3D v;
	public final Vector3D w;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public OrthonormalBasis33D() {
		this.w = Vector3D.z();
		this.v = Vector3D.y();
		this.u = Vector3D.x();
	}
	
	public OrthonormalBasis33D(final Vector3D w) {
		this.w = Vector3D.normalize(w);
		this.u = Vector3D.orthogonal(w);
		this.v = Vector3D.crossProduct(this.w, this.u);
	}
	
	public OrthonormalBasis33D(final Vector3D w, final Vector3D v) {
		this.w = Vector3D.normalize(w);
		this.u = Vector3D.normalize(Vector3D.crossProduct(Vector3D.normalize(v), this.w));
		this.v = Vector3D.crossProduct(this.w, this.u);
	}
	
	public OrthonormalBasis33D(final Vector3D w, final Vector3D v, final Vector3D u) {
		this.w = Objects.requireNonNull(w, "w == null");
		this.v = Objects.requireNonNull(v, "v == null");
		this.u = Objects.requireNonNull(u, "u == null");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public String toString() {
		return String.format("new OrthonormalBasis33D(%s, %s, %s)", this.w, this.v, this.u);
	}
	
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof OrthonormalBasis33D)) {
			return false;
		} else {
			return equals(OrthonormalBasis33D.class.cast(object));
		}
	}
	
	public boolean equals(final OrthonormalBasis33D o) {
		if(o == this) {
			return true;
		} else if(o == null) {
			return false;
		} else if(!Objects.equals(this.u, o.u)) {
			return false;
		} else if(!Objects.equals(this.v, o.v)) {
			return false;
		} else if(!Objects.equals(this.w, o.w)) {
			return false;
		} else {
			return true;
		}
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.u, this.v, this.w);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static OrthonormalBasis33D from(final Matrix44D m) {
		final Vector3D u = new Vector3D(m.element11, m.element21, m.element31);
		final Vector3D v = new Vector3D(m.element12, m.element22, m.element32);
		final Vector3D w = new Vector3D(m.element13, m.element23, m.element33);
		
		return new OrthonormalBasis33D(w, v, u);
	}
	
	public static OrthonormalBasis33D from(final Quaternion4D q) {
		final Quaternion4D r = Quaternion4D.normalize(q);
		
		final Vector3D u = new Vector3D(1.0D - 2.0D * (r.y * r.y + r.z * r.z),        2.0D * (r.x * r.y - r.w * r.z),        2.0D * (r.x * r.z + r.w * r.y));
		final Vector3D v = new Vector3D(       2.0D * (r.x * r.y + r.w * r.z), 1.0D - 2.0D * (r.x * r.x + r.z * r.z),        2.0D * (r.y * r.z - r.w * r.x));
		final Vector3D w = new Vector3D(       2.0D * (r.x * r.z - r.w * r.y),        2.0D * (r.y * r.z + r.w * r.x), 1.0D - 2.0D * (r.x * r.x + r.y * r.y));
		
		return new OrthonormalBasis33D(w, v, u);
	}
	
//	TODO: Add unit tests!
	public static OrthonormalBasis33D transform(final Matrix44D mLHS, final OrthonormalBasis33D oRHS) {
		final Vector3D u = Vector3D.normalize(Vector3D.transform(mLHS, oRHS.u));
		final Vector3D v = Vector3D.normalize(Vector3D.transform(mLHS, oRHS.v));
		final Vector3D w = Vector3D.normalize(Vector3D.transform(mLHS, oRHS.w));
		
		return new OrthonormalBasis33D(w, v, u);
	}
	
//	TODO: Add unit tests!
	public static OrthonormalBasis33D transformTranspose(final Matrix44D mLHS, final OrthonormalBasis33D oRHS) {
		final Vector3D u = Vector3D.normalize(Vector3D.transformTranspose(mLHS, oRHS.u));
		final Vector3D v = Vector3D.normalize(Vector3D.transformTranspose(mLHS, oRHS.v));
		final Vector3D w = Vector3D.normalize(Vector3D.transformTranspose(mLHS, oRHS.w));
		
		return new OrthonormalBasis33D(w, v, u);
	}
}