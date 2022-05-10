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
public final class Vector3DUnitTests {
	public Vector3DUnitTests() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testAbs() {
		assertEquals(new Vector3D(1.0D, 2.0D, 3.0D), Vector3D.abs(new Vector3D(+1.0D, +2.0D, +3.0D)));
		assertEquals(new Vector3D(1.0D, 2.0D, 3.0D), Vector3D.abs(new Vector3D(-1.0D, -2.0D, -3.0D)));
		
		assertThrows(NullPointerException.class, () -> Vector3D.abs(null));
	}
	
	@Test
	public void testAdd() {
		final Vector3D v = Vector3D.add(new Vector3D(1.0D, 2.0D, 3.0D), new Vector3D(2.0D, 3.0D, 4.0D));
		
		assertEquals(3.0D, v.x);
		assertEquals(5.0D, v.y);
		assertEquals(7.0D, v.z);
		
		assertThrows(NullPointerException.class, () -> Vector3D.add(new Vector3D(1.0D, 2.0D, 3.0D), null));
		assertThrows(NullPointerException.class, () -> Vector3D.add(null, new Vector3D(2.0D, 3.0D, 4.0D)));
	}
	
	@Test
	public void testConstructorDoubleDoubleDouble() {
		final Vector3D v = new Vector3D(1.0D, 2.0D, 3.0D);
		
		assertEquals(1.0D, v.x);
		assertEquals(2.0D, v.y);
		assertEquals(3.0D, v.z);
	}
	
	@Test
	public void testConstructorPoint3D() {
		final Vector3D v = new Vector3D(new Point3D(1.0D, 2.0D, 3.0D));
		
		assertEquals(1.0D, v.x);
		assertEquals(2.0D, v.y);
		assertEquals(3.0D, v.z);
		
		assertThrows(NullPointerException.class, () -> new Vector3D(null));
	}
	
	@Test
	public void testCosTheta() {
		assertEquals(3.0D, new Vector3D(1.0D, 2.0D, 3.0D).cosTheta());
	}
	
	@Test
	public void testCosThetaAbs() {
		assertEquals(3.0D, new Vector3D(+1.0D, +2.0D, +3.0D).cosThetaAbs());
		assertEquals(3.0D, new Vector3D(-1.0D, -2.0D, -3.0D).cosThetaAbs());
	}
	
	@Test
	public void testCosThetaQuartic() {
		assertEquals(81.0D, new Vector3D(+1.0D, +2.0D, +3.0D).cosThetaQuartic());
		assertEquals(81.0D, new Vector3D(-1.0D, -2.0D, -3.0D).cosThetaQuartic());
	}
	
	@Test
	public void testCosThetaSquared() {
		assertEquals(9.0D, new Vector3D(+1.0D, +2.0D, +3.0D).cosThetaSquared());
		assertEquals(9.0D, new Vector3D(-1.0D, -2.0D, -3.0D).cosThetaSquared());
	}
	
	@Test
	public void testCrossProduct() {
		final Vector3D a = Vector3D.crossProduct(new Vector3D(1.0D, 0.0D, 0.0D), new Vector3D(0.0D, 1.0D, 0.0D));
		final Vector3D b = Vector3D.crossProduct(new Vector3D(0.0D, 1.0D, 0.0D), new Vector3D(0.0D, 0.0D, 1.0D));
		final Vector3D c = Vector3D.crossProduct(new Vector3D(0.0D, 0.0D, 1.0D), new Vector3D(1.0D, 0.0D, 0.0D));
		
		assertEquals(new Vector3D(0.0D, 0.0D, 1.0D), a);
		assertEquals(new Vector3D(1.0D, 0.0D, 0.0D), b);
		assertEquals(new Vector3D(0.0D, 1.0D, 0.0D), c);
		
		assertThrows(NullPointerException.class, () -> Vector3D.crossProduct(new Vector3D(1.0D, 0.0D, 0.0D), null));
		assertThrows(NullPointerException.class, () -> Vector3D.crossProduct(null, new Vector3D(0.0D, 1.0D, 0.0D)));
	}
	
	@Test
	public void testDirectionPoint3DPoint3D() {
		final Vector3D v = Vector3D.direction(new Point3D(10.0D, 20.0D, 30.0D), new Point3D(20.0D, 40.0D, 60.0D));
		
		assertEquals(10.0D, v.x);
		assertEquals(20.0D, v.y);
		assertEquals(30.0D, v.z);
		
		assertThrows(NullPointerException.class, () -> Vector3D.direction(new Point3D(), null));
		assertThrows(NullPointerException.class, () -> Vector3D.direction(null, new Point3D()));
	}
	
	@Test
	public void testDirectionVector3DVector3DVector3D() {
		final Vector3D v = Vector3D.direction(new Vector3D(1.0D, 0.0D, 0.0D), new Vector3D(0.0D, 1.0D, 0.0D), new Vector3D(0.0D, 0.0D, 1.0D));
		
		assertEquals(1.0D, v.x);
		assertEquals(1.0D, v.y);
		assertEquals(1.0D, v.z);
		
		assertThrows(NullPointerException.class, () -> Vector3D.direction(new Vector3D(1.0D, 0.0D, 0.0D), new Vector3D(0.0D, 1.0D, 0.0D), null));
		assertThrows(NullPointerException.class, () -> Vector3D.direction(new Vector3D(1.0D, 0.0D, 0.0D), null, new Vector3D(0.0D, 0.0D, 1.0D)));
		assertThrows(NullPointerException.class, () -> Vector3D.direction(null, new Vector3D(0.0D, 1.0D, 0.0D), new Vector3D(0.0D, 0.0D, 1.0D)));
	}
	
	@Test
	public void testDirectionVector3DVector3DVector3DVector3D() {
		final Vector3D v = Vector3D.direction(new Vector3D(1.0D, 0.0D, 0.0D), new Vector3D(0.0D, 1.0D, 0.0D), new Vector3D(0.0D, 0.0D, 1.0D), new Vector3D(2.0D, 2.0D, 2.0D));
		
		assertEquals(2.0D, v.x);
		assertEquals(2.0D, v.y);
		assertEquals(2.0D, v.z);
		
		assertThrows(NullPointerException.class, () -> Vector3D.direction(new Vector3D(1.0D, 0.0D, 0.0D), new Vector3D(0.0D, 1.0D, 0.0D), new Vector3D(0.0D, 0.0D, 1.0D), null));
		assertThrows(NullPointerException.class, () -> Vector3D.direction(new Vector3D(1.0D, 0.0D, 0.0D), new Vector3D(0.0D, 1.0D, 0.0D), null, new Vector3D(2.0D, 2.0D, 2.0D)));
		assertThrows(NullPointerException.class, () -> Vector3D.direction(new Vector3D(1.0D, 0.0D, 0.0D), null, new Vector3D(0.0D, 0.0D, 1.0D), new Vector3D(2.0D, 2.0D, 2.0D)));
		assertThrows(NullPointerException.class, () -> Vector3D.direction(null, new Vector3D(0.0D, 1.0D, 0.0D), new Vector3D(0.0D, 0.0D, 1.0D), new Vector3D(2.0D, 2.0D, 2.0D)));
	}
	
	@Test
	public void testDotProduct() {
		assertEquals(-1.0D, Vector3D.dotProduct(Vector3D.x(-1.0D), Vector3D.x(+1.0D)));
		assertEquals(-1.0D, Vector3D.dotProduct(Vector3D.x(+1.0D), Vector3D.x(-1.0D)));
		assertEquals(+1.0D, Vector3D.dotProduct(Vector3D.x(-1.0D), Vector3D.x(-1.0D)));
		assertEquals(+1.0D, Vector3D.dotProduct(Vector3D.x(+1.0D), Vector3D.x(+1.0D)));
		assertEquals(+0.0D, Vector3D.dotProduct(Vector3D.x(+0.0D), Vector3D.x(+1.0D)));
		assertEquals(+0.0D, Vector3D.dotProduct(Vector3D.x(+0.0D), Vector3D.x(-1.0D)));
		assertEquals(+0.0D, Vector3D.dotProduct(Vector3D.x(+1.0D), Vector3D.x(+0.0D)));
		assertEquals(+0.0D, Vector3D.dotProduct(Vector3D.x(-1.0D), Vector3D.x(+0.0D)));
		
		assertEquals(-1.0D, Vector3D.dotProduct(Vector3D.y(-1.0D), Vector3D.y(+1.0D)));
		assertEquals(-1.0D, Vector3D.dotProduct(Vector3D.y(+1.0D), Vector3D.y(-1.0D)));
		assertEquals(+1.0D, Vector3D.dotProduct(Vector3D.y(-1.0D), Vector3D.y(-1.0D)));
		assertEquals(+1.0D, Vector3D.dotProduct(Vector3D.y(+1.0D), Vector3D.y(+1.0D)));
		assertEquals(+0.0D, Vector3D.dotProduct(Vector3D.y(+0.0D), Vector3D.y(+1.0D)));
		assertEquals(+0.0D, Vector3D.dotProduct(Vector3D.y(+0.0D), Vector3D.y(-1.0D)));
		assertEquals(+0.0D, Vector3D.dotProduct(Vector3D.y(+1.0D), Vector3D.y(+0.0D)));
		assertEquals(+0.0D, Vector3D.dotProduct(Vector3D.y(-1.0D), Vector3D.y(+0.0D)));
		
		assertEquals(-1.0D, Vector3D.dotProduct(Vector3D.z(-1.0D), Vector3D.z(+1.0D)));
		assertEquals(-1.0D, Vector3D.dotProduct(Vector3D.z(+1.0D), Vector3D.z(-1.0D)));
		assertEquals(+1.0D, Vector3D.dotProduct(Vector3D.z(-1.0D), Vector3D.z(-1.0D)));
		assertEquals(+1.0D, Vector3D.dotProduct(Vector3D.z(+1.0D), Vector3D.z(+1.0D)));
		assertEquals(+0.0D, Vector3D.dotProduct(Vector3D.z(+0.0D), Vector3D.z(+1.0D)));
		assertEquals(+0.0D, Vector3D.dotProduct(Vector3D.z(+0.0D), Vector3D.z(-1.0D)));
		assertEquals(+0.0D, Vector3D.dotProduct(Vector3D.z(+1.0D), Vector3D.z(+0.0D)));
		assertEquals(+0.0D, Vector3D.dotProduct(Vector3D.z(-1.0D), Vector3D.z(+0.0D)));
		
		assertThrows(NullPointerException.class, () -> Vector3D.dotProduct(Vector3D.x(1.0D), null));
		assertThrows(NullPointerException.class, () -> Vector3D.dotProduct(null, Vector3D.x(1.0D)));
	}
	
	@Test
	public void testDotProductAbs() {
		assertEquals(+1.0D, Vector3D.dotProductAbs(Vector3D.x(-1.0D), Vector3D.x(+1.0D)));
		assertEquals(+1.0D, Vector3D.dotProductAbs(Vector3D.x(+1.0D), Vector3D.x(-1.0D)));
		assertEquals(+1.0D, Vector3D.dotProductAbs(Vector3D.x(-1.0D), Vector3D.x(-1.0D)));
		assertEquals(+1.0D, Vector3D.dotProductAbs(Vector3D.x(+1.0D), Vector3D.x(+1.0D)));
		assertEquals(+0.0D, Vector3D.dotProductAbs(Vector3D.x(+0.0D), Vector3D.x(+1.0D)));
		assertEquals(+0.0D, Vector3D.dotProductAbs(Vector3D.x(+0.0D), Vector3D.x(-1.0D)));
		assertEquals(+0.0D, Vector3D.dotProductAbs(Vector3D.x(+1.0D), Vector3D.x(+0.0D)));
		assertEquals(+0.0D, Vector3D.dotProductAbs(Vector3D.x(-1.0D), Vector3D.x(+0.0D)));
		
		assertEquals(+1.0D, Vector3D.dotProductAbs(Vector3D.y(-1.0D), Vector3D.y(+1.0D)));
		assertEquals(+1.0D, Vector3D.dotProductAbs(Vector3D.y(+1.0D), Vector3D.y(-1.0D)));
		assertEquals(+1.0D, Vector3D.dotProductAbs(Vector3D.y(-1.0D), Vector3D.y(-1.0D)));
		assertEquals(+1.0D, Vector3D.dotProductAbs(Vector3D.y(+1.0D), Vector3D.y(+1.0D)));
		assertEquals(+0.0D, Vector3D.dotProductAbs(Vector3D.y(+0.0D), Vector3D.y(+1.0D)));
		assertEquals(+0.0D, Vector3D.dotProductAbs(Vector3D.y(+0.0D), Vector3D.y(-1.0D)));
		assertEquals(+0.0D, Vector3D.dotProductAbs(Vector3D.y(+1.0D), Vector3D.y(+0.0D)));
		assertEquals(+0.0D, Vector3D.dotProductAbs(Vector3D.y(-1.0D), Vector3D.y(+0.0D)));
		
		assertEquals(+1.0D, Vector3D.dotProductAbs(Vector3D.z(-1.0D), Vector3D.z(+1.0D)));
		assertEquals(+1.0D, Vector3D.dotProductAbs(Vector3D.z(+1.0D), Vector3D.z(-1.0D)));
		assertEquals(+1.0D, Vector3D.dotProductAbs(Vector3D.z(-1.0D), Vector3D.z(-1.0D)));
		assertEquals(+1.0D, Vector3D.dotProductAbs(Vector3D.z(+1.0D), Vector3D.z(+1.0D)));
		assertEquals(+0.0D, Vector3D.dotProductAbs(Vector3D.z(+0.0D), Vector3D.z(+1.0D)));
		assertEquals(+0.0D, Vector3D.dotProductAbs(Vector3D.z(+0.0D), Vector3D.z(-1.0D)));
		assertEquals(+0.0D, Vector3D.dotProductAbs(Vector3D.z(+1.0D), Vector3D.z(+0.0D)));
		assertEquals(+0.0D, Vector3D.dotProductAbs(Vector3D.z(-1.0D), Vector3D.z(+0.0D)));
		
		assertThrows(NullPointerException.class, () -> Vector3D.dotProductAbs(Vector3D.x(1.0D), null));
		assertThrows(NullPointerException.class, () -> Vector3D.dotProductAbs(null, Vector3D.x(1.0D)));
	}
	
	@Test
	public void testEqualsObject() {
		final Vector3D a = new Vector3D(1.0D, 2.0D, 3.0D);
		final Vector3D b = new Vector3D(1.0D, 2.0D, 3.0D);
		final Vector3D c = new Vector3D(1.0D, 2.0D, 4.0D);
		final Vector3D d = new Vector3D(1.0D, 4.0D, 3.0D);
		final Vector3D e = new Vector3D(4.0D, 2.0D, 3.0D);
		final Vector3D f = null;
		
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
	public void testEqualsVector3D() {
		final Vector3D a = new Vector3D(1.0D, 2.0D, 3.0D);
		final Vector3D b = new Vector3D(1.0D, 2.0D, 3.0D);
		final Vector3D c = new Vector3D(1.0D, 2.0D, 4.0D);
		final Vector3D d = new Vector3D(1.0D, 4.0D, 3.0D);
		final Vector3D e = new Vector3D(4.0D, 2.0D, 3.0D);
		final Vector3D f = null;
		
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
	public void testHashCode() {
		final Vector3D a = new Vector3D(1.0D, 2.0D, 3.0D);
		final Vector3D b = new Vector3D(1.0D, 2.0D, 3.0D);
		
		assertEquals(a.hashCode(), b.hashCode());
	}
	
	@Test
	public void testIsZero() {
		assertTrue(new Vector3D(0.0D, 0.0D, 0.0D).isZero());
		
		assertFalse(new Vector3D(0.0D, 0.0D, 1.0D).isZero());
		assertFalse(new Vector3D(0.0D, 1.0D, 0.0D).isZero());
		assertFalse(new Vector3D(1.0D, 0.0D, 0.0D).isZero());
		assertFalse(new Vector3D(1.0D, 1.0D, 1.0D).isZero());
	}
	
	@Test
	public void testLength() {
		assertEquals(4.0D, new Vector3D(0.0D, 0.0D, 4.0D).length());
		assertEquals(4.0D, new Vector3D(0.0D, 4.0D, 0.0D).length());
		assertEquals(4.0D, new Vector3D(4.0D, 0.0D, 0.0D).length());
	}
	
	@Test
	public void testLengthSquared() {
		assertEquals(14.0D, new Vector3D(1.0D, 2.0D, 3.0D).lengthSquared());
	}
	
	@Test
	public void testNegate() {
		final Vector3D a = Vector3D.negate(new Vector3D(+1.0D, +1.0D, +1.0D));
		final Vector3D b = Vector3D.negate(new Vector3D(-1.0D, -1.0D, -1.0D));
		
		assertEquals(-1.0D, a.x);
		assertEquals(-1.0D, a.y);
		assertEquals(-1.0D, a.z);
		
		assertEquals(+1.0D, b.x);
		assertEquals(+1.0D, b.y);
		assertEquals(+1.0D, b.z);
		
		assertThrows(NullPointerException.class, () -> Vector3D.negate(null));
	}
	
	@Test
	public void testNegateZ() {
		final Vector3D a = Vector3D.negateZ(new Vector3D(+1.0D, +1.0D, +1.0D));
		final Vector3D b = Vector3D.negateZ(new Vector3D(-1.0D, -1.0D, -1.0D));
		
		assertEquals(+1.0D, a.x);
		assertEquals(+1.0D, a.y);
		assertEquals(-1.0D, a.z);
		
		assertEquals(-1.0D, b.x);
		assertEquals(-1.0D, b.y);
		assertEquals(+1.0D, b.z);
		
		assertThrows(NullPointerException.class, () -> Vector3D.negateZ(null));
	}
	
	@Test
	public void testSameHemisphereZ() {
		assertTrue(Vector3D.sameHemisphereZ(new Vector3D(0.0D, 0.0D, +1.0D), new Vector3D(0.0D, 0.0D, +1.0D)));
		assertTrue(Vector3D.sameHemisphereZ(new Vector3D(0.0D, 0.0D, -1.0D), new Vector3D(0.0D, 0.0D, -1.0D)));
		
		assertFalse(Vector3D.sameHemisphereZ(new Vector3D(0.0D, 0.0D, +1.0D), new Vector3D(0.0D, 0.0D, -1.0D)));
		assertFalse(Vector3D.sameHemisphereZ(new Vector3D(0.0D, 0.0D, -1.0D), new Vector3D(0.0D, 0.0D, +1.0D)));
		
		assertThrows(NullPointerException.class, () -> Vector3D.sameHemisphereZ(new Vector3D(0.0D, 0.0D, 0.0D), null));
		assertThrows(NullPointerException.class, () -> Vector3D.sameHemisphereZ(null, new Vector3D(0.0D, 0.0D, 0.0D)));
	}
	
	@Test
	public void testToString() {
		final Vector3D v = new Vector3D(1.0D, 2.0D, 3.0D);
		
		assertEquals("new Vector3D(1.0D, 2.0D, 3.0D)", v.toString());
	}
	
	@Test
	public void testX() {
		final Vector3D v = Vector3D.x();
		
		assertEquals(1.0D, v.x);
		assertEquals(0.0D, v.y);
		assertEquals(0.0D, v.z);
	}
	
	@Test
	public void testXDouble() {
		final Vector3D v = Vector3D.x(2.0D);
		
		assertEquals(2.0D, v.x);
		assertEquals(0.0D, v.y);
		assertEquals(0.0D, v.z);
	}
	
	@Test
	public void testY() {
		final Vector3D v = Vector3D.y();
		
		assertEquals(0.0D, v.x);
		assertEquals(1.0D, v.y);
		assertEquals(0.0D, v.z);
	}
	
	@Test
	public void testYDouble() {
		final Vector3D v = Vector3D.y(2.0D);
		
		assertEquals(0.0D, v.x);
		assertEquals(2.0D, v.y);
		assertEquals(0.0D, v.z);
	}
	
	@Test
	public void testZ() {
		final Vector3D v = Vector3D.z();
		
		assertEquals(0.0D, v.x);
		assertEquals(0.0D, v.y);
		assertEquals(1.0D, v.z);
	}
	
	@Test
	public void testZDouble() {
		final Vector3D v = Vector3D.z(2.0D);
		
		assertEquals(0.0D, v.x);
		assertEquals(0.0D, v.y);
		assertEquals(2.0D, v.z);
	}
}