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

public final class OrthonormalBasis33D {
	public final Vector3D u;
	public final Vector3D v;
	public final Vector3D w;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
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
	
	public static OrthonormalBasis33D transform(final Matrix44D mLHS, final OrthonormalBasis33D oRHS) {
		final Vector3D u = Vector3D.normalize(Vector3D.transform(mLHS, oRHS.u));
		final Vector3D v = Vector3D.normalize(Vector3D.transform(mLHS, oRHS.v));
		final Vector3D w = Vector3D.normalize(Vector3D.transform(mLHS, oRHS.w));
		
		return new OrthonormalBasis33D(w, v, u);
	}
	
	public static OrthonormalBasis33D transformTranspose(final Matrix44D mLHS, final OrthonormalBasis33D oRHS) {
		final Vector3D u = Vector3D.normalize(Vector3D.transformTranspose(mLHS, oRHS.u));
		final Vector3D v = Vector3D.normalize(Vector3D.transformTranspose(mLHS, oRHS.v));
		final Vector3D w = Vector3D.normalize(Vector3D.transformTranspose(mLHS, oRHS.w));
		
		return new OrthonormalBasis33D(w, v, u);
	}
}