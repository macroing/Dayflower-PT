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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

@SuppressWarnings("static-method")
public final class OrthonormalBasis33DUnitTests {
	public OrthonormalBasis33DUnitTests() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testConstructor() {
		final OrthonormalBasis33D o = new OrthonormalBasis33D();
		
		assertEquals(new Vector3D(1.0D, 0.0D, 0.0D), o.u);
		assertEquals(new Vector3D(0.0D, 1.0D, 0.0D), o.v);
		assertEquals(new Vector3D(0.0D, 0.0D, 1.0D), o.w);
	}
	
	@Test
	public void testConstructorVector3D() {
		final OrthonormalBasis33D o = new OrthonormalBasis33D(new Vector3D(0.0D, 0.0D, 1.0D));
		
		assertEquals(new Vector3D(+1.0D, 0.0D, -0.0D), o.u);
		assertEquals(new Vector3D(-0.0D, 1.0D, +0.0D), o.v);
		assertEquals(new Vector3D(+0.0D, 0.0D, +1.0D), o.w);
		
		assertThrows(NullPointerException.class, () -> new OrthonormalBasis33D(null));
	}
	
	@Test
	public void testConstructorVector3DVector3D() {
		final OrthonormalBasis33D o = new OrthonormalBasis33D(new Vector3D(0.0D, 0.0D, 1.0D), new Vector3D(0.0D, 1.0D, 0.0D));
		
		assertEquals(new Vector3D(1.0D, 0.0D, 0.0D), o.u);
		assertEquals(new Vector3D(0.0D, 1.0D, 0.0D), o.v);
		assertEquals(new Vector3D(0.0D, 0.0D, 1.0D), o.w);
		
		assertThrows(NullPointerException.class, () -> new OrthonormalBasis33D(new Vector3D(0.0D, 0.0D, 1.0D), null));
		assertThrows(NullPointerException.class, () -> new OrthonormalBasis33D(null, new Vector3D(0.0D, 1.0D, 0.0D)));
	}
	
	@Test
	public void testConstructorVector3DVector3DVector3D() {
		final OrthonormalBasis33D o = new OrthonormalBasis33D(new Vector3D(0.0D, 0.0D, 1.0D), new Vector3D(0.0D, 1.0D, 0.0D), new Vector3D(1.0D, 0.0D, 0.0D));
		
		assertEquals(new Vector3D(1.0D, 0.0D, 0.0D), o.u);
		assertEquals(new Vector3D(0.0D, 1.0D, 0.0D), o.v);
		assertEquals(new Vector3D(0.0D, 0.0D, 1.0D), o.w);
		
		assertThrows(NullPointerException.class, () -> new OrthonormalBasis33D(new Vector3D(0.0D, 0.0D, 1.0D), new Vector3D(0.0D, 1.0D, 0.0D), null));
		assertThrows(NullPointerException.class, () -> new OrthonormalBasis33D(new Vector3D(0.0D, 0.0D, 1.0D), null, new Vector3D(1.0D, 0.0D, 0.0D)));
		assertThrows(NullPointerException.class, () -> new OrthonormalBasis33D(null, new Vector3D(0.0D, 1.0D, 0.0D), new Vector3D(1.0D, 0.0D, 0.0D)));
	}
	
	@Test
	public void testEqualsObject() {
		final OrthonormalBasis33D a = new OrthonormalBasis33D(new Vector3D(0.0D, 0.0D, 1.0D), new Vector3D(0.0D, 1.0D, 0.0D), new Vector3D(1.0D, 0.0D, 0.0D));
		final OrthonormalBasis33D b = new OrthonormalBasis33D(new Vector3D(0.0D, 0.0D, 1.0D), new Vector3D(0.0D, 1.0D, 0.0D), new Vector3D(1.0D, 0.0D, 0.0D));
		final OrthonormalBasis33D c = new OrthonormalBasis33D(new Vector3D(0.0D, 0.0D, 1.0D), new Vector3D(0.0D, 1.0D, 0.0D), new Vector3D(2.0D, 0.0D, 0.0D));
		final OrthonormalBasis33D d = new OrthonormalBasis33D(new Vector3D(0.0D, 0.0D, 1.0D), new Vector3D(0.0D, 2.0D, 0.0D), new Vector3D(1.0D, 0.0D, 0.0D));
		final OrthonormalBasis33D e = new OrthonormalBasis33D(new Vector3D(0.0D, 0.0D, 2.0D), new Vector3D(0.0D, 1.0D, 0.0D), new Vector3D(1.0D, 0.0D, 0.0D));
		final OrthonormalBasis33D f = null;
		
		assertEquals(a, a);
		assertEquals(a, b);
		assertEquals(b, a);
		
		assertNotEquals(a, c);
		assertNotEquals(c, a);
		assertNotEquals(a, d);
		assertNotEquals(d, a);
		assertNotEquals(a, e);
		assertNotEquals(e, a);
		assertNotEquals(a, f);
		assertNotEquals(f, a);
	}
	
	@Test
	public void testEqualsOrthonormalBasis33D() {
		final OrthonormalBasis33D a = new OrthonormalBasis33D(new Vector3D(0.0D, 0.0D, 1.0D), new Vector3D(0.0D, 1.0D, 0.0D), new Vector3D(1.0D, 0.0D, 0.0D));
		final OrthonormalBasis33D b = new OrthonormalBasis33D(new Vector3D(0.0D, 0.0D, 1.0D), new Vector3D(0.0D, 1.0D, 0.0D), new Vector3D(1.0D, 0.0D, 0.0D));
		final OrthonormalBasis33D c = new OrthonormalBasis33D(new Vector3D(0.0D, 0.0D, 1.0D), new Vector3D(0.0D, 1.0D, 0.0D), new Vector3D(2.0D, 0.0D, 0.0D));
		final OrthonormalBasis33D d = new OrthonormalBasis33D(new Vector3D(0.0D, 0.0D, 1.0D), new Vector3D(0.0D, 2.0D, 0.0D), new Vector3D(1.0D, 0.0D, 0.0D));
		final OrthonormalBasis33D e = new OrthonormalBasis33D(new Vector3D(0.0D, 0.0D, 2.0D), new Vector3D(0.0D, 1.0D, 0.0D), new Vector3D(1.0D, 0.0D, 0.0D));
		final OrthonormalBasis33D f = null;
		
		assertTrue(a.equals(a));
		assertTrue(a.equals(b));
		assertTrue(b.equals(a));
		
		assertFalse(a.equals(c));
		assertFalse(c.equals(a));
		assertFalse(a.equals(d));
		assertFalse(d.equals(a));
		assertFalse(a.equals(e));
		assertFalse(e.equals(a));
		assertFalse(a.equals(f));
	}
	
	@Test
	public void testFromMatrix44D() {
		final OrthonormalBasis33D o = OrthonormalBasis33D.from(new Matrix44D());
		
		assertEquals(new Vector3D(1.0D, 0.0D, 0.0D), o.u);
		assertEquals(new Vector3D(0.0D, 1.0D, 0.0D), o.v);
		assertEquals(new Vector3D(0.0D, 0.0D, 1.0D), o.w);
		
		assertThrows(NullPointerException.class, () -> OrthonormalBasis33D.from((Matrix44D)(null)));
	}
	
	@Test
	public void testFromQuaternion4D() {
		final OrthonormalBasis33D o = OrthonormalBasis33D.from(new Quaternion4D(0.5D, 0.5D, 0.5D, 0.5D));
		
		assertEquals(new Vector3D(0.0D, 0.0D, 1.0D), o.u);
		assertEquals(new Vector3D(1.0D, 0.0D, 0.0D), o.v);
		assertEquals(new Vector3D(0.0D, 1.0D, 0.0D), o.w);
		
		assertThrows(NullPointerException.class, () -> OrthonormalBasis33D.from((Quaternion4D)(null)));
	}
	
	@Test
	public void testHashCode() {
		final OrthonormalBasis33D a = new OrthonormalBasis33D(new Vector3D(0.0D, 0.0D, 1.0D), new Vector3D(0.0D, 1.0D, 0.0D), new Vector3D(1.0D, 0.0D, 0.0D));
		final OrthonormalBasis33D b = new OrthonormalBasis33D(new Vector3D(0.0D, 0.0D, 1.0D), new Vector3D(0.0D, 1.0D, 0.0D), new Vector3D(1.0D, 0.0D, 0.0D));
		
		assertEquals(a.hashCode(), b.hashCode());
	}
	
	@Test
	public void testToString() {
		final OrthonormalBasis33D o = new OrthonormalBasis33D(new Vector3D(0.0D, 0.0D, 1.0D), new Vector3D(0.0D, 1.0D, 0.0D), new Vector3D(1.0D, 0.0D, 0.0D));
		
		assertEquals("new OrthonormalBasis33D(new Vector3D(0.0D, 0.0D, 1.0D), new Vector3D(0.0D, 1.0D, 0.0D), new Vector3D(1.0D, 0.0D, 0.0D))", o.toString());
	}
	
	@Test
	public void testTransform() {
		final OrthonormalBasis33D a = new OrthonormalBasis33D(new Vector3D(0.0D, 0.0D, 1.0D), new Vector3D(0.0D, 1.0D, 0.0D), new Vector3D(1.0D, 0.0D, 0.0D));
		final OrthonormalBasis33D b = OrthonormalBasis33D.transform(Matrix44D.rotateX(+180.0D), a);
		final OrthonormalBasis33D c = OrthonormalBasis33D.transform(Matrix44D.rotateX(-180.0D), b);
		final OrthonormalBasis33D d = OrthonormalBasis33D.transform(Matrix44D.rotateY(+180.0D), a);
		final OrthonormalBasis33D e = OrthonormalBasis33D.transform(Matrix44D.rotateY(-180.0D), d);
		final OrthonormalBasis33D f = OrthonormalBasis33D.transform(Matrix44D.rotateZ(+180.0D), a);
		final OrthonormalBasis33D g = OrthonormalBasis33D.transform(Matrix44D.rotateZ(-180.0D), f);
		
		assertEquals(a, c);
		assertEquals(a, e);
		assertEquals(a, g);
		
		assertEquals(new OrthonormalBasis33D(new Vector3D(0.0D, -0.00000000000000012246467991473532D, -1.0D), new Vector3D(0.0D, -1.0D, 0.00000000000000012246467991473532D), new Vector3D(1.0D, 0.0D, 0.0D)), b);
		assertEquals(new OrthonormalBasis33D(new Vector3D(0.00000000000000012246467991473532D, 0.0D, -1.0D), new Vector3D(0.0D, 1.0D, 0.0D), new Vector3D(-1.0D, 0.0D, -0.00000000000000012246467991473532D)), d);
		assertEquals(new OrthonormalBasis33D(new Vector3D(0.0D, 0.0D, 1.0D), new Vector3D(-0.00000000000000012246467991473532D, -1.0D, 0.0D), new Vector3D(-1.0D, 0.00000000000000012246467991473532D, 0.0D)), f);
		
		assertThrows(NullPointerException.class, () -> OrthonormalBasis33D.transform(Matrix44D.rotateX(+180.0D), null));
		assertThrows(NullPointerException.class, () -> OrthonormalBasis33D.transform(null, a));
	}
	
	@Test
	public void testTransformTranspose() {
		final OrthonormalBasis33D a = new OrthonormalBasis33D(new Vector3D(0.0D, 0.0D, 1.0D), new Vector3D(0.0D, 1.0D, 0.0D), new Vector3D(1.0D, 0.0D, 0.0D));
		final OrthonormalBasis33D b = OrthonormalBasis33D.transformTranspose(Matrix44D.transpose(Matrix44D.rotateX(+180.0D)), a);
		final OrthonormalBasis33D c = OrthonormalBasis33D.transformTranspose(Matrix44D.transpose(Matrix44D.rotateX(-180.0D)), b);
		final OrthonormalBasis33D d = OrthonormalBasis33D.transformTranspose(Matrix44D.transpose(Matrix44D.rotateY(+180.0D)), a);
		final OrthonormalBasis33D e = OrthonormalBasis33D.transformTranspose(Matrix44D.transpose(Matrix44D.rotateY(-180.0D)), d);
		final OrthonormalBasis33D f = OrthonormalBasis33D.transformTranspose(Matrix44D.transpose(Matrix44D.rotateZ(+180.0D)), a);
		final OrthonormalBasis33D g = OrthonormalBasis33D.transformTranspose(Matrix44D.transpose(Matrix44D.rotateZ(-180.0D)), f);
		
		assertEquals(a, c);
		assertEquals(a, e);
		assertEquals(a, g);
		
		assertEquals(new OrthonormalBasis33D(new Vector3D(0.0D, -0.00000000000000012246467991473532D, -1.0D), new Vector3D(0.0D, -1.0D, 0.00000000000000012246467991473532D), new Vector3D(1.0D, 0.0D, 0.0D)), b);
		assertEquals(new OrthonormalBasis33D(new Vector3D(0.00000000000000012246467991473532D, 0.0D, -1.0D), new Vector3D(0.0D, 1.0D, 0.0D), new Vector3D(-1.0D, 0.0D, -0.00000000000000012246467991473532D)), d);
		assertEquals(new OrthonormalBasis33D(new Vector3D(0.0D, 0.0D, 1.0D), new Vector3D(-0.00000000000000012246467991473532D, -1.0D, 0.0D), new Vector3D(-1.0D, 0.00000000000000012246467991473532D, 0.0D)), f);
		
		assertThrows(NullPointerException.class, () -> OrthonormalBasis33D.transformTranspose(Matrix44D.rotateX(+180.0D), null));
		assertThrows(NullPointerException.class, () -> OrthonormalBasis33D.transformTranspose(null, a));
	}
}