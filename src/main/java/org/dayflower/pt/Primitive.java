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

public final class Primitive {
	private final Material material;
	private final Shape3D shape;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Primitive(final Material material, final Shape3D shape) {
		this.material = Objects.requireNonNull(material, "material == null");
		this.shape = Objects.requireNonNull(shape, "shape == null");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Material getMaterial() {
		return this.material;
	}
	
	public Shape3D getShape() {
		return this.shape;
	}
}