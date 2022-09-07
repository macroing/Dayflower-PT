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

import org.macroing.java.lang.Doubles;

@SuppressWarnings("static-method")
public final class Point3DUnitTests {
	public Point3DUnitTests() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testAddPoint3DVector3D() {
		final Point3D p = Point3D.add(new Point3D(1.0D, 2.0D, 3.0D), new Vector3D(1.0D, 2.0D, 3.0D));
		
		assertEquals(2.0D, p.x);
		assertEquals(4.0D, p.y);
		assertEquals(6.0D, p.z);
		
		assertThrows(NullPointerException.class, () -> Point3D.add(new Point3D(1.0D, 2.0D, 3.0D), null));
		assertThrows(NullPointerException.class, () -> Point3D.add(null, new Vector3D(1.0D, 2.0D, 3.0D)));
	}
	
	@Test
	public void testAddPoint3DVector3DDouble() {
		final Point3D p = Point3D.add(new Point3D(1.0D, 2.0D, 3.0D), new Vector3D(1.0D, 2.0D, 3.0D), 2.0D);
		
		assertEquals(3.0D, p.x);
		assertEquals(6.0D, p.y);
		assertEquals(9.0D, p.z);
		
		assertThrows(NullPointerException.class, () -> Point3D.add(new Point3D(1.0D, 2.0D, 3.0D), null, 2.0D));
		assertThrows(NullPointerException.class, () -> Point3D.add(null, new Vector3D(1.0D, 2.0D, 3.0D), 2.0D));
	}
	
	@Test
	public void testConstants() {
		assertEquals(+Double.MAX_VALUE, Point3D.MAX.x);
		assertEquals(+Double.MAX_VALUE, Point3D.MAX.y);
		assertEquals(+Double.MAX_VALUE, Point3D.MAX.z);
		
		assertEquals(-Double.MAX_VALUE, Point3D.MIN.x);
		assertEquals(-Double.MAX_VALUE, Point3D.MIN.y);
		assertEquals(-Double.MAX_VALUE, Point3D.MIN.z);
	}
	
	@Test
	public void testConstructor() {
		final Point3D p = new Point3D();
		
		assertEquals(0.0D, p.x);
		assertEquals(0.0D, p.y);
		assertEquals(0.0D, p.z);
	}
	
	@Test
	public void testConstructorDoubleDoubleDouble() {
		final Point3D p = new Point3D(1.0D, 2.0D, 3.0D);
		
		assertEquals(1.0D, p.x);
		assertEquals(2.0D, p.y);
		assertEquals(3.0D, p.z);
	}
	
	@Test
	public void testCoplanar() {
		assertTrue(Point3D.coplanar(new Point3D(1.0D, 2.0D, 3.0D), new Point3D(4.0D, 5.0D, 6.0D), new Point3D(7.0D, 8.0D, 9.0D)));
		assertTrue(Point3D.coplanar(new Point3D(0.0D, 0.0D, 0.0D), new Point3D(1.0D, 0.0D, 0.0D), new Point3D(0.0D, 1.0D, 0.0D), new Point3D(2.0D, 2.0D, 0.0D)));
		assertTrue(Point3D.coplanar(new Point3D(0.0D, 0.0D, 0.0D), new Point3D(1.0D, 0.0D, 0.0D), new Point3D(0.0D, 0.0D, 1.0D), new Point3D(2.0D, 0.0D, 2.0D)));
		assertTrue(Point3D.coplanar(new Point3D(0.0D, 0.0D, 0.0D), new Point3D(0.0D, 1.0D, 0.0D), new Point3D(0.0D, 0.0D, 1.0D), new Point3D(0.0D, 2.0D, 2.0D)));
		
		assertFalse(Point3D.coplanar(new Point3D(0.0D, 0.0D, 0.0D), new Point3D(1.0D, 0.0D, 0.0D), new Point3D(0.0D, 1.0D, 0.0D), new Point3D(2.0D, 2.0D, 2.0D)));
		assertFalse(Point3D.coplanar(new Point3D(0.0D, 0.0D, 0.0D), new Point3D(1.0D, 0.0D, 0.0D), new Point3D(0.0D, 0.0D, 1.0D), new Point3D(2.0D, 2.0D, 2.0D)));
		assertFalse(Point3D.coplanar(new Point3D(0.0D, 0.0D, 0.0D), new Point3D(0.0D, 1.0D, 0.0D), new Point3D(0.0D, 0.0D, 1.0D), new Point3D(2.0D, 2.0D, 2.0D)));
		
		assertThrows(NullPointerException.class, () -> Point3D.coplanar((Point3D[])(null)));
		assertThrows(NullPointerException.class, () -> Point3D.coplanar(new Point3D(), null, new Point3D()));
		
		assertThrows(IllegalArgumentException.class, () -> Point3D.coplanar(new Point3D(), new Point3D()));
	}
	
	@Test
	public void testDistance() {
		final Point3D a = new Point3D(1.0D, 2.0D, 3.0D);
		final Point3D b = new Point3D(1.0D, 6.0D, 3.0D);
		
		assertEquals(4.0D, Point3D.distance(a, b));
		
		assertThrows(NullPointerException.class, () -> Point3D.distance(a, null));
		assertThrows(NullPointerException.class, () -> Point3D.distance(null, b));
	}
	
	@Test
	public void testDistanceSquared() {
		final Point3D a = new Point3D(1.0D, 2.0D, 3.0D);
		final Point3D b = new Point3D(2.0D, 4.0D, 6.0D);
		
		assertEquals(14.0D, Point3D.distanceSquared(a, b));
		
		assertThrows(NullPointerException.class, () -> Point3D.distanceSquared(a, null));
		assertThrows(NullPointerException.class, () -> Point3D.distanceSquared(null, b));
	}
	
	@Test
	public void testEqualsObject() {
		final Point3D a = new Point3D(1.0D, 2.0D, 3.0D);
		final Point3D b = new Point3D(1.0D, 2.0D, 3.0D);
		final Point3D c = new Point3D(1.0D, 2.0D, 4.0D);
		final Point3D d = new Point3D(1.0D, 4.0D, 3.0D);
		final Point3D e = new Point3D(4.0D, 2.0D, 3.0D);
		final Point3D f = null;
		
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
	public void testEqualsPoint3D() {
		final Point3D a = new Point3D(1.0D, 2.0D, 3.0D);
		final Point3D b = new Point3D(1.0D, 2.0D, 3.0D);
		final Point3D c = new Point3D(1.0D, 2.0D, 4.0D);
		final Point3D d = new Point3D(1.0D, 4.0D, 3.0D);
		final Point3D e = new Point3D(4.0D, 2.0D, 3.0D);
		final Point3D f = null;
		
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
		final Point3D a = new Point3D(1.0D, 2.0D, 3.0D);
		final Point3D b = new Point3D(1.0D, 2.0D, 3.0D);
		
		assertEquals(a.hashCode(), b.hashCode());
	}
	
	@Test
	public void testLerp() {
		final Point3D a = Point3D.lerp(new Point3D(10.0D, 10.0D, 10.0D), new Point3D(20.0D, 20.0D, 20.0D), 0.0D);
		final Point3D b = Point3D.lerp(new Point3D(10.0D, 10.0D, 10.0D), new Point3D(20.0D, 20.0D, 20.0D), 0.5D);
		final Point3D c = Point3D.lerp(new Point3D(10.0D, 10.0D, 10.0D), new Point3D(20.0D, 20.0D, 20.0D), 1.0D);
		
		assertEquals(new Point3D(10.0D, 10.0D, 10.0D), a);
		assertEquals(new Point3D(15.0D, 15.0D, 15.0D), b);
		assertEquals(new Point3D(20.0D, 20.0D, 20.0D), c);
		
		assertThrows(NullPointerException.class, () -> Point3D.lerp(new Point3D(10.0D, 10.0D, 10.0D), null, 0.5D));
		assertThrows(NullPointerException.class, () -> Point3D.lerp(null, new Point3D(20.0D, 20.0D, 20.0D), 0.5D));
	}
	
	@Test
	public void testMax() {
		final Point3D p = Point3D.max();
		
		assertEquals(+Double.MAX_VALUE, p.x);
		assertEquals(+Double.MAX_VALUE, p.y);
		assertEquals(+Double.MAX_VALUE, p.z);
	}
	
	@Test
	public void testMaxPoint3DPoint3D() {
		final Point3D p = Point3D.max(new Point3D(1.0D, 4.0D, 5.0D), new Point3D(2.0D, 3.0D, 6.0D));
		
		assertEquals(2.0D, p.x);
		assertEquals(4.0D, p.y);
		assertEquals(6.0D, p.z);
		
		assertThrows(NullPointerException.class, () -> Point3D.max(new Point3D(1.0D, 4.0D, 5.0D), null));
		assertThrows(NullPointerException.class, () -> Point3D.max(null, new Point3D(2.0D, 3.0D, 6.0D)));
	}
	
	@Test
	public void testMidpoint() {
		final Point3D p = Point3D.midpoint(new Point3D(10.0D, 20.0D, 30.0D), new Point3D(20.0D, 30.0D, 40.0D));
		
		assertEquals(15.0D, p.x);
		assertEquals(25.0D, p.y);
		assertEquals(35.0D, p.z);
		
		assertThrows(NullPointerException.class, () -> Point3D.midpoint(new Point3D(10.0D, 20.0D, 30.0D), null));
		assertThrows(NullPointerException.class, () -> Point3D.midpoint(null, new Point3D(20.0D, 30.0D, 40.0D)));
	}
	
	@Test
	public void testMin() {
		final Point3D p = Point3D.min();
		
		assertEquals(-Double.MAX_VALUE, p.x);
		assertEquals(-Double.MAX_VALUE, p.y);
		assertEquals(-Double.MAX_VALUE, p.z);
	}
	
	@Test
	public void testMinPoint3DPoint3D() {
		final Point3D p = Point3D.min(new Point3D(1.0D, 4.0D, 5.0D), new Point3D(2.0D, 3.0D, 6.0D));
		
		assertEquals(1.0D, p.x);
		assertEquals(3.0D, p.y);
		assertEquals(5.0D, p.z);
		
		assertThrows(NullPointerException.class, () -> Point3D.min(new Point3D(1.0D, 4.0D, 5.0D), null));
		assertThrows(NullPointerException.class, () -> Point3D.min(null, new Point3D(2.0D, 3.0D, 6.0D)));
	}
	
	@Test
	public void testSphericalPhi() {
		final Point3D x = new Point3D(1.0D, 0.0D, 0.0D);
		final Point3D y = new Point3D(0.0D, 1.0D, 0.0D);
		final Point3D z = new Point3D(0.0D, 0.0D, 1.0D);
		
		assertEquals(0.0D,              x.sphericalPhi());
		assertEquals(Doubles.PI / 2.0D, y.sphericalPhi());
		assertEquals(0.0D,              z.sphericalPhi());
	}
	
	@Test
	public void testToString() {
		final Point3D p = new Point3D(1.0D, 2.0D, 3.0D);
		
		assertEquals("new Point3D(1.0D, 2.0D, 3.0D)", p.toString());
	}
	
	@Test
	public void testTransform() {
		final Point3D a = new Point3D(1.0D, 2.0D, 3.0D);
		final Point3D b = Point3D.transform(Matrix44D.scale(1.0D, 2.0D, 3.0D), a);
		final Point3D c = Point3D.transform(Matrix44D.translate(1.0D, 2.0D, 3.0D), a);
		
		assertEquals(1.0D, b.x);
		assertEquals(4.0D, b.y);
		assertEquals(9.0D, b.z);
		
		assertEquals(2.0D, c.x);
		assertEquals(4.0D, c.y);
		assertEquals(6.0D, c.z);
		
		assertThrows(NullPointerException.class, () -> Point3D.transform(Matrix44D.translate(1.0D, 2.0D, 3.0D), null));
		assertThrows(NullPointerException.class, () -> Point3D.transform(null, a));
	}
	
	@Test
	public void testTransformAndDivide() {
		final Point3D a = new Point3D(1.0D, 2.0D, 3.0D);
		final Point3D b = Point3D.transformAndDivide(Matrix44D.scale(1.0D, 2.0D, 3.0D), a);
		final Point3D c = Point3D.transformAndDivide(Matrix44D.translate(1.0D, 2.0D, 3.0D), a);
		final Point3D d = Point3D.transformAndDivide(new Matrix44D(1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 2.0D), a);
		final Point3D e = Point3D.transformAndDivide(new Matrix44D(1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D), a);
		
		assertEquals(1.0D, b.x);
		assertEquals(4.0D, b.y);
		assertEquals(9.0D, b.z);
		
		assertEquals(2.0D, c.x);
		assertEquals(4.0D, c.y);
		assertEquals(6.0D, c.z);
		
		assertEquals(0.5D, d.x);
		assertEquals(1.0D, d.y);
		assertEquals(1.5D, d.z);
		
		assertEquals(1.0D, e.x);
		assertEquals(2.0D, e.y);
		assertEquals(3.0D, e.z);
		
		assertThrows(NullPointerException.class, () -> Point3D.transformAndDivide(Matrix44D.translate(1.0D, 2.0D, 3.0D), null));
		assertThrows(NullPointerException.class, () -> Point3D.transformAndDivide(null, a));
	}
}