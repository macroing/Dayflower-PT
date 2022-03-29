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

public final class Transform {
	private Matrix44D objectToWorld;
	private Matrix44D worldToObject;
	private Point3D position;
	private Quaternion4D rotation;
	private Vector3D scale;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Transform() {
		this(new Point3D());
	}
	
	public Transform(final Point3D position) {
		this(position, new Quaternion4D());
	}
	
	public Transform(final Point3D position, final Quaternion4D rotation) {
		this(position, rotation, new Vector3D(1.0D, 1.0D, 1.0D));
	}
	
	public Transform(final Point3D position, final Quaternion4D rotation, final Vector3D scale) {
		this.position = Objects.requireNonNull(position, "position == null");
		this.rotation = Objects.requireNonNull(rotation, "rotation == null");
		this.scale = Objects.requireNonNull(scale, "scale == null");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Matrix44D getObjectToWorld() {
		doAttemptToComputeObjectToWorld();
		
		return this.objectToWorld;
	}
	
	public Matrix44D getWorldToObject() {
		doAttemptToComputeWorldToObject();
		
		return this.worldToObject;
	}
	
	public Point3D getPosition() {
		return this.position;
	}
	
	public Quaternion4D getRotation() {
		return this.rotation;
	}
	
	public Vector3D getScale() {
		return this.scale;
	}
	
	public void setPosition(final Point3D position) {
		this.position = Objects.requireNonNull(position, "position == null");
		this.objectToWorld = null;
		this.worldToObject = null;
	}
	
	public void setRotation(final Quaternion4D rotation) {
		this.rotation = Objects.requireNonNull(rotation, "rotation == null");
		this.objectToWorld = null;
		this.worldToObject = null;
	}
	
	public void setScale(final Vector3D scale) {
		this.scale = Objects.requireNonNull(scale, "scale == null");
		this.objectToWorld = null;
		this.worldToObject = null;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private void doAttemptToComputeObjectToWorld() {
		if(this.objectToWorld == null) {
			this.objectToWorld = Matrix44D.multiply(Matrix44D.multiply(Matrix44D.translate(this.position), Matrix44D.rotate(this.rotation)), Matrix44D.scale(this.scale));
		}
	}
	
	private void doAttemptToComputeWorldToObject() {
		doAttemptToComputeObjectToWorld();
		
		if(this.objectToWorld != null && this.worldToObject == null) {
			this.worldToObject = Matrix44D.inverse(this.objectToWorld);
		}
	}
}