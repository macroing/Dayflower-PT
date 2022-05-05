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
public final class Vector2DUnitTests {
	public Vector2DUnitTests() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testConstructor() {
		final Vector2D v = new Vector2D();
		
		assertEquals(0.0D, v.x);
		assertEquals(0.0D, v.y);
	}
	
	@Test
	public void testConstructorDoubleDouble() {
		final Vector2D v = new Vector2D(1.0D, 2.0D);
		
		assertEquals(1.0D, v.x);
		assertEquals(2.0D, v.y);
	}
	
	@Test
	public void testCrossProduct() {
		final Vector2D a = new Vector2D(1.0D, 2.0D);
		final Vector2D b = new Vector2D(2.0D, 4.0D);
		
		assertEquals(0.0D, Vector2D.crossProduct(a, b));
		
		assertThrows(NullPointerException.class, () -> Vector2D.crossProduct(a, null));
		assertThrows(NullPointerException.class, () -> Vector2D.crossProduct(null, b));
	}
	
	@Test
	public void testDirection() {
		final Vector2D v = Vector2D.direction(new Point2D(1.0D, 2.0D), new Point2D(2.0D, 3.0D));
		
		assertEquals(1.0D, v.x);
		assertEquals(1.0D, v.y);
		
		assertThrows(NullPointerException.class, () -> Vector2D.direction(new Point2D(1.0D, 2.0D), null));
		assertThrows(NullPointerException.class, () -> Vector2D.direction(null, new Point2D(2.0D, 3.0D)));
	}
	
	@Test
	public void testDirectionXY() {
		final Vector2D v = Vector2D.directionXY(new Point3D(1.0D, 2.0D, 3.0D));
		
		assertEquals(1.0D, v.x);
		assertEquals(2.0D, v.y);
		
		assertThrows(NullPointerException.class, () -> Vector2D.directionXY(null));
	}
	
	@Test
	public void testDirectionYZ() {
		final Vector2D v = Vector2D.directionYZ(new Point3D(1.0D, 2.0D, 3.0D));
		
		assertEquals(2.0D, v.x);
		assertEquals(3.0D, v.y);
		
		assertThrows(NullPointerException.class, () -> Vector2D.directionYZ(null));
	}
	
	@Test
	public void testDirectionZX() {
		final Vector2D v = Vector2D.directionZX(new Point3D(1.0D, 2.0D, 3.0D));
		
		assertEquals(3.0D, v.x);
		assertEquals(1.0D, v.y);
		
		assertThrows(NullPointerException.class, () -> Vector2D.directionZX(null));
	}
	
	@Test
	public void testEqualsObject() {
		final Vector2D a = new Vector2D(1.0D, 2.0D);
		final Vector2D b = new Vector2D(1.0D, 2.0D);
		final Vector2D c = new Vector2D(1.0D, 3.0D);
		final Vector2D d = new Vector2D(3.0D, 2.0D);
		final Vector2D e = null;
		
		assertEquals(a, a);
		assertEquals(a, b);
		assertEquals(b, a);
		
		assertNotEquals(a, c);
		assertNotEquals(c, a);
		assertNotEquals(a, d);
		assertNotEquals(d, a);
		assertNotEquals(a, e);
		assertNotEquals(e, a);
	}
	
	@Test
	public void testEqualsVector2D() {
		final Vector2D a = new Vector2D(1.0D, 2.0D);
		final Vector2D b = new Vector2D(1.0D, 2.0D);
		final Vector2D c = new Vector2D(1.0D, 3.0D);
		final Vector2D d = new Vector2D(3.0D, 2.0D);
		final Vector2D e = null;
		
		assertTrue(a.equals(a));
		assertTrue(a.equals(b));
		assertTrue(b.equals(a));
		
		assertFalse(a.equals(c));
		assertFalse(c.equals(a));
		assertFalse(a.equals(d));
		assertFalse(d.equals(a));
		assertFalse(a.equals(e));
	}
	
	@Test
	public void testHashCode() {
		final Vector2D a = new Vector2D(1.0D, 2.0D);
		final Vector2D b = new Vector2D(1.0D, 2.0D);
		
		assertEquals(a.hashCode(), b.hashCode());
	}
	
	@Test
	public void testLength() {
		final Vector2D a = new Vector2D(0.0D, 4.0D);
		final Vector2D b = new Vector2D(4.0D, 0.0D);
		
		assertEquals(4.0D, a.length());
		assertEquals(4.0D, b.length());
	}
	
	@Test
	public void testLengthSquared() {
		final Vector2D v = new Vector2D(2.0D, 4.0D);
		
		assertEquals(20.0D, v.lengthSquared());
	}
	
	@Test
	public void testSubtract() {
		final Vector2D a = new Vector2D(4.0D, 3.0D);
		final Vector2D b = new Vector2D(2.0D, 1.0D);
		final Vector2D c = Vector2D.subtract(a, b);
		
		assertEquals(2.0D, c.x);
		assertEquals(2.0D, c.y);
		
		assertThrows(NullPointerException.class, () -> Vector2D.subtract(a, null));
		assertThrows(NullPointerException.class, () -> Vector2D.subtract(null, b));
	}
	
	@Test
	public void testToString() {
		final Vector2D v = new Vector2D(1.0D, 2.0D);
		
		assertEquals("new Vector2D(1.0D, 2.0D)", v.toString());
	}
}