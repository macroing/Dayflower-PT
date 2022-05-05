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

public final class Camera {
	private final Point3D eye;
	private final Vector3D u;
	private final Vector3D v;
	private final Vector3D w;
	private final double resolutionX;
	private final double resolutionY;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add unit tests!
	public Camera() {
		this(1024.0D, 768.0D);
	}
	
//	TODO: Add unit tests!
	public Camera(final double resolutionX, final double resolutionY) {
		this(resolutionX, resolutionY, 2.0D * Math.tan(Math.toRadians(28.799323D) * 0.5D));
	}
	
//	TODO: Add unit tests!
	public Camera(final double resolutionX, final double resolutionY, final double fieldOfViewX) {
		this.eye = new Point3D(50.0D, 50.0D, 295.6D);
		this.w = Vector3D.normalize(new Vector3D(0.0D, -0.042612D, -1.0D));
		this.u = new Vector3D(fieldOfViewX * resolutionX / resolutionY, 0.0D, 0.0D);
		this.v = Vector3D.multiply(Vector3D.normalize(Vector3D.crossProduct(this.u, this.w)), fieldOfViewX);
		this.resolutionX = resolutionX;
		this.resolutionY = resolutionY;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add unit tests!
	public Ray3D generatePrimaryRay(final double pixelX, final double pixelY, final double sampleU, final double sampleV) {
		final Point2D sample = doSample(pixelX, pixelY, sampleU, sampleV);
		
		final Vector3D u = Vector3D.multiply(this.u, sample.x);
		final Vector3D v = Vector3D.multiply(this.v, sample.y);
		final Vector3D w = this.w;
		
		final Vector3D direction = Vector3D.direction(u, v, w);
		
		final Point3D origin = Point3D.add(this.eye, Vector3D.multiply(direction, 140.0D));
		
		final Vector3D directionNormalized = Vector3D.normalize(direction);
		
		return new Ray3D(origin, directionNormalized);
	}
	
//	TODO: Add unit tests!
	public Vector3D getU() {
		return this.u;
	}
	
//	TODO: Add unit tests!
	public Vector3D getV() {
		return this.v;
	}
	
//	TODO: Add unit tests!
	public Vector3D getW() {
		return this.w;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private Point2D doSample(final double pixelX, final double pixelY, final double sampleU, final double sampleV) {
//		Generate a Point2D instance whose x and y fields contains doubles in the range [-1.0D, +1.0D]:
		final Point2D sample = Point2D.sampleExactInverseTentFilter();
		
//		The variables sampleU and sampleV are in the range [0.0D, 1.0D]:
		final double sampleU1 = (sampleU + 0.5D + sample.x) / 2.0D;
		final double sampleV1 = (sampleV + 0.5D + sample.y) / 2.0D;
		
//		The variables sampleU1 and sampleV1 are in the range [-0.25D, +1.25D]:
		final double sampleU2 = (sampleU1 + pixelX) / this.resolutionX - 0.5D;
		final double sampleV2 = (sampleV1 + pixelY) / this.resolutionY - 0.5D;
		
		return new Point2D(sampleU2, sampleV2);
	}
}