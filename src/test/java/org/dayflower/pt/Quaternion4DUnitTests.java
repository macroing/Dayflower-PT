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
public final class Quaternion4DUnitTests {
	public Quaternion4DUnitTests() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testAdd() {
		final Quaternion4D a = new Quaternion4D(1.0D, 2.0D, 3.0D, 4.0D);
		final Quaternion4D b = new Quaternion4D(2.0D, 3.0D, 4.0D, 5.0D);
		final Quaternion4D c = Quaternion4D.add(a, b);
		
		assertEquals(3.0D, c.x);
		assertEquals(5.0D, c.y);
		assertEquals(7.0D, c.z);
		assertEquals(9.0D, c.w);
		
		assertThrows(NullPointerException.class, () -> Quaternion4D.add(a, null));
		assertThrows(NullPointerException.class, () -> Quaternion4D.add(null, b));
	}
	
	@Test
	public void testConjugate() {
		final Quaternion4D a = new Quaternion4D(1.0D, 2.0D, 3.0D, 4.0D);
		final Quaternion4D b = Quaternion4D.conjugate(a);
		final Quaternion4D c = Quaternion4D.conjugate(b);
		
		assertEquals(-1.0D, b.x);
		assertEquals(-2.0D, b.y);
		assertEquals(-3.0D, b.z);
		assertEquals(+4.0D, b.w);
		
		assertEquals(+1.0D, c.x);
		assertEquals(+2.0D, c.y);
		assertEquals(+3.0D, c.z);
		assertEquals(+4.0D, c.w);
		
		assertThrows(NullPointerException.class, () -> Quaternion4D.conjugate(null));
	}
	
	@Test
	public void testConstructor() {
		final Quaternion4D q = new Quaternion4D();
		
		assertEquals(0.0D, q.x);
		assertEquals(0.0D, q.y);
		assertEquals(0.0D, q.z);
		assertEquals(1.0D, q.w);
	}
	
	@Test
	public void testConstructorDoubleDoubleDouble() {
		final Quaternion4D q = new Quaternion4D(2.0D, 3.0D, 4.0D);
		
		assertEquals(2.0D, q.x);
		assertEquals(3.0D, q.y);
		assertEquals(4.0D, q.z);
		assertEquals(1.0D, q.w);
	}
	
	@Test
	public void testConstructorDoubleDoubleDoubleDouble() {
		final Quaternion4D q = new Quaternion4D(1.0D, 2.0D, 3.0D, 4.0D);
		
		assertEquals(1.0D, q.x);
		assertEquals(2.0D, q.y);
		assertEquals(3.0D, q.z);
		assertEquals(4.0D, q.w);
	}
	
	@Test
	public void testDivide() {
		final Quaternion4D a = new Quaternion4D(2.0D, 4.0D, 8.0D, 16.0D);
		final Quaternion4D b = Quaternion4D.divide(a, 2.0D);
		final Quaternion4D c = Quaternion4D.divide(a, Double.NaN);
		
		assertEquals(1.0D, b.x);
		assertEquals(2.0D, b.y);
		assertEquals(4.0D, b.z);
		assertEquals(8.0D, b.w);
		
		assertEquals(Double.NaN, c.x);
		assertEquals(Double.NaN, c.y);
		assertEquals(Double.NaN, c.z);
		assertEquals(Double.NaN, c.w);
		
		assertThrows(NullPointerException.class, () -> Quaternion4D.divide(null, 2.0D));
	}
	
	@Test
	public void testDotProduct() {
		final Quaternion4D a = new Quaternion4D(+1.0D, +0.0D, +0.0D, +0.0D);
		final Quaternion4D b = new Quaternion4D(+1.0D, +0.0D, +0.0D, +0.0D);
		final Quaternion4D c = new Quaternion4D(+0.0D, -1.0D, +0.0D, +0.0D);
		final Quaternion4D d = new Quaternion4D(-1.0D, +0.0D, +0.0D, +0.0D);
		
		assertEquals(+1.0D, Quaternion4D.dotProduct(a, b));
		assertEquals(+0.0D, Quaternion4D.dotProduct(a, c));
		assertEquals(-1.0D, Quaternion4D.dotProduct(a, d));
		
		assertThrows(NullPointerException.class, () -> Quaternion4D.dotProduct(a, null));
		assertThrows(NullPointerException.class, () -> Quaternion4D.dotProduct(null, b));
	}
	
	@Test
	public void testEqualsObject() {
		final Quaternion4D a = new Quaternion4D(0.0D, 1.0D, 2.0D, 3.0D);
		final Quaternion4D b = new Quaternion4D(0.0D, 1.0D, 2.0D, 3.0D);
		final Quaternion4D c = new Quaternion4D(0.0D, 1.0D, 2.0D, 4.0D);
		final Quaternion4D d = new Quaternion4D(0.0D, 1.0D, 4.0D, 3.0D);
		final Quaternion4D e = new Quaternion4D(0.0D, 4.0D, 2.0D, 3.0D);
		final Quaternion4D f = new Quaternion4D(4.0D, 1.0D, 2.0D, 3.0D);
		final Quaternion4D g = null;
		
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
		assertNotEquals(a, g);
		assertNotEquals(g, a);
	}
	
	@Test
	public void testEqualsQuaternion4D() {
		final Quaternion4D a = new Quaternion4D(0.0D, 1.0D, 2.0D, 3.0D);
		final Quaternion4D b = new Quaternion4D(0.0D, 1.0D, 2.0D, 3.0D);
		final Quaternion4D c = new Quaternion4D(0.0D, 1.0D, 2.0D, 4.0D);
		final Quaternion4D d = new Quaternion4D(0.0D, 1.0D, 4.0D, 3.0D);
		final Quaternion4D e = new Quaternion4D(0.0D, 4.0D, 2.0D, 3.0D);
		final Quaternion4D f = new Quaternion4D(4.0D, 1.0D, 2.0D, 3.0D);
		final Quaternion4D g = null;
		
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
		assertFalse(f.equals(a));
		assertFalse(a.equals(g));
	}
	
	@Test
	public void testFromOrthonormalBasis33D() {
		final Quaternion4D q = Quaternion4D.from(new OrthonormalBasis33D(new Vector3D(0.0D, 1.0D, 0.0D), new Vector3D(1.0D, 0.0D, 0.0D), new Vector3D(0.0D, 0.0D, 1.0D)));
		
		assertEquals(0.5D, q.x);
		assertEquals(0.5D, q.y);
		assertEquals(0.5D, q.z);
		assertEquals(0.5D, q.w);
		
		assertThrows(NullPointerException.class, () -> Quaternion4D.from((OrthonormalBasis33D)(null)));
	}
	
	@Test
	public void testFromVector3D() {
		final Quaternion4D q = Quaternion4D.from(new Vector3D(1.0D, 0.0D, 0.0D));
		
		assertEquals(+0.5D, q.x);
		assertEquals(-0.5D, q.y);
		assertEquals(+0.5D, q.z);
		assertEquals(+0.5D, q.w);
		
		assertThrows(NullPointerException.class, () -> Quaternion4D.from((Vector3D)(null)));
	}
	
	@Test
	public void testHashCode() {
		final Quaternion4D a = new Quaternion4D(1.0D, 2.0D, 3.0D, 4.0D);
		final Quaternion4D b = new Quaternion4D(1.0D, 2.0D, 3.0D, 4.0D);
		
		assertEquals(a.hashCode(), a.hashCode());
		assertEquals(a.hashCode(), b.hashCode());
	}
	
	@Test
	public void testLength() {
		final Quaternion4D a = new Quaternion4D(4.0D, 0.0D, 0.0D, 0.0D);
		final Quaternion4D b = new Quaternion4D(0.0D, 4.0D, 0.0D, 0.0D);
		final Quaternion4D c = new Quaternion4D(0.0D, 0.0D, 4.0D, 0.0D);
		final Quaternion4D d = new Quaternion4D(0.0D, 0.0D, 0.0D, 4.0D);
		
		assertEquals(4.0D, a.length());
		assertEquals(4.0D, b.length());
		assertEquals(4.0D, c.length());
		assertEquals(4.0D, d.length());
	}
	
	@Test
	public void testLengthSquared() {
		final Quaternion4D q = new Quaternion4D(2.0D, 4.0D, 8.0D, 16.0D);
		
		assertEquals(340.0D, q.lengthSquared());
	}
	
	@Test
	public void testMultiplyQuaternion4DDouble() {
		final Quaternion4D a = new Quaternion4D(1.0D, 2.0D, 3.0D, 4.0D);
		final Quaternion4D b = Quaternion4D.multiply(a, 2.0D);
		
		assertEquals(2.0D, b.x);
		assertEquals(4.0D, b.y);
		assertEquals(6.0D, b.z);
		assertEquals(8.0D, b.w);
		
		assertThrows(NullPointerException.class, () -> Quaternion4D.multiply(null, 2.0D));
	}
	
	@Test
	public void testMultiplyQuaternion4DQuaternion4D() {
		final Quaternion4D a = new Quaternion4D(1.0D, 2.0D, 3.0D, 4.0D);
		final Quaternion4D b = new Quaternion4D(1.0D, 2.0D, 3.0D, 4.0D);
		final Quaternion4D c = Quaternion4D.multiply(a, b);
		
		assertEquals( 8.0D, c.x);
		assertEquals(16.0D, c.y);
		assertEquals(24.0D, c.z);
		assertEquals( 2.0D, c.w);
		
		assertThrows(NullPointerException.class, () -> Quaternion4D.multiply(a, (Quaternion4D)(null)));
		assertThrows(NullPointerException.class, () -> Quaternion4D.multiply(null, b));
	}
	
	@Test
	public void testMultiplyQuaternion4DVector3D() {
		final Quaternion4D a = new Quaternion4D(1.0D, 2.0D, 3.0D, 4.0D);
		final Quaternion4D b = Quaternion4D.multiply(a, new Vector3D(1.0D, 2.0D, 3.0D));
		
		assertEquals(+ 4.0D, b.x);
		assertEquals(+ 8.0D, b.y);
		assertEquals(+12.0D, b.z);
		assertEquals(-14.0D, b.w);
		
		assertThrows(NullPointerException.class, () -> Quaternion4D.multiply(a, (Vector3D)(null)));
		assertThrows(NullPointerException.class, () -> Quaternion4D.multiply(null, new Vector3D(1.0D, 2.0D, 3.0D)));
	}
	
	@Test
	public void testNegate() {
		final Quaternion4D a = new Quaternion4D(1.0D, 2.0D, 3.0D, 4.0D);
		final Quaternion4D b = Quaternion4D.negate(a);
		final Quaternion4D c = Quaternion4D.negate(b);
		
		assertEquals(-1.0D, b.x);
		assertEquals(-2.0D, b.y);
		assertEquals(-3.0D, b.z);
		assertEquals(-4.0D, b.w);
		
		assertEquals(+1.0D, c.x);
		assertEquals(+2.0D, c.y);
		assertEquals(+3.0D, c.z);
		assertEquals(+4.0D, c.w);
		
		assertThrows(NullPointerException.class, () -> Quaternion4D.negate(null));
	}
	
	@Test
	public void testNormalize() {
		final Quaternion4D a = new Quaternion4D(+1.0D, +0.0D, +0.0D, +0.0D);
		final Quaternion4D b = Quaternion4D.normalize(a);
		final Quaternion4D c = new Quaternion4D(+0.0D, +1.0D, +0.0D, +0.0D);
		final Quaternion4D d = Quaternion4D.normalize(c);
		final Quaternion4D e = new Quaternion4D(+0.0D, +0.0D, +1.0D, +0.0D);
		final Quaternion4D f = Quaternion4D.normalize(e);
		final Quaternion4D g = new Quaternion4D(+0.0D, +0.0D, +0.0D, +1.0D);
		final Quaternion4D h = Quaternion4D.normalize(g);
		final Quaternion4D i = new Quaternion4D(+1.0D, +1.0D, +1.0D, +1.0D);
		final Quaternion4D j = Quaternion4D.normalize(i);
		final Quaternion4D k = Quaternion4D.normalize(j);
		final Quaternion4D l = new Quaternion4D(+0.4D, +0.4D, +0.4D, +0.4D);
		final Quaternion4D m = Quaternion4D.normalize(l);
		final Quaternion4D n = Quaternion4D.normalize(m);
		
		assertEquals(a, b);
		assertEquals(c, d);
		assertEquals(e, f);
		assertEquals(g, h);
		assertEquals(j, k);
		assertEquals(m, n);
		
		assertTrue(a == b);
		assertTrue(c == d);
		assertTrue(e == f);
		assertTrue(j == k);
		assertTrue(m == n);
		
		assertThrows(NullPointerException.class, () -> Quaternion4D.normalize(null));
	}
	
	@Test
	public void testSubtract() {
		final Quaternion4D a = new Quaternion4D(3.0D, 5.0D, 7.0D, 9.0D);
		final Quaternion4D b = new Quaternion4D(2.0D, 3.0D, 4.0D, 5.0D);
		final Quaternion4D c = Quaternion4D.subtract(a, b);
		
		assertEquals(1.0D, c.x);
		assertEquals(2.0D, c.y);
		assertEquals(3.0D, c.z);
		assertEquals(4.0D, c.w);
		
		assertThrows(NullPointerException.class, () -> Quaternion4D.subtract(a, null));
		assertThrows(NullPointerException.class, () -> Quaternion4D.subtract(null, b));
	}
	
	@Test
	public void testToString() {
		final Quaternion4D q = new Quaternion4D(1.0D, 2.0D, 3.0D, 4.0D);
		
		assertEquals("new Quaternion4D(1.0D, 2.0D, 3.0D, 4.0D)", q.toString());
	}
}