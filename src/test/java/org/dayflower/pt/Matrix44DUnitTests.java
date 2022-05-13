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
public final class Matrix44DUnitTests {
	public Matrix44DUnitTests() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testConstructor() {
		final Matrix44D m = new Matrix44D();
		
		assertEquals(1.0D, m.element11);
		assertEquals(0.0D, m.element12);
		assertEquals(0.0D, m.element13);
		assertEquals(0.0D, m.element14);
		assertEquals(0.0D, m.element21);
		assertEquals(1.0D, m.element22);
		assertEquals(0.0D, m.element23);
		assertEquals(0.0D, m.element24);
		assertEquals(0.0D, m.element31);
		assertEquals(0.0D, m.element32);
		assertEquals(1.0D, m.element33);
		assertEquals(0.0D, m.element34);
		assertEquals(0.0D, m.element41);
		assertEquals(0.0D, m.element42);
		assertEquals(0.0D, m.element43);
		assertEquals(1.0D, m.element44);
	}
	
	@Test
	public void testConstructorDoubleDoubleDoubleDoubleDoubleDoubleDoubleDoubleDoubleDoubleDoubleDoubleDoubleDoubleDoubleDouble() {
		final Matrix44D m = new Matrix44D(1.0D, 2.0D, 3.0D, 4.0D, 5.0D, 6.0D, 7.0D, 8.0D, 9.0D, 10.0D, 11.0D, 12.0D, 13.0D, 14.0D, 15.0D, 16.0D);
		
		assertEquals( 1.0D, m.element11);
		assertEquals( 2.0D, m.element12);
		assertEquals( 3.0D, m.element13);
		assertEquals( 4.0D, m.element14);
		assertEquals( 5.0D, m.element21);
		assertEquals( 6.0D, m.element22);
		assertEquals( 7.0D, m.element23);
		assertEquals( 8.0D, m.element24);
		assertEquals( 9.0D, m.element31);
		assertEquals(10.0D, m.element32);
		assertEquals(11.0D, m.element33);
		assertEquals(12.0D, m.element34);
		assertEquals(13.0D, m.element41);
		assertEquals(14.0D, m.element42);
		assertEquals(15.0D, m.element43);
		assertEquals(16.0D, m.element44);
	}
	
	@Test
	public void testDeterminant() {
		final Matrix44D m = Matrix44D.identity();
		
		assertEquals(1.0D, m.determinant());
	}
	
	@Test
	public void testEqualsMatrix44D() {
		final Matrix44D a = new Matrix44D( 0.0D,  1.0D,  2.0D,  3.0D,  4.0D,  5.0D,  6.0D,  7.0D,  8.0D,  9.0D, 10.0D, 11.0D, 12.0D, 13.0D, 14.0D, 15.0D);
		final Matrix44D b = new Matrix44D( 0.0D,  1.0D,  2.0D,  3.0D,  4.0D,  5.0D,  6.0D,  7.0D,  8.0D,  9.0D, 10.0D, 11.0D, 12.0D, 13.0D, 14.0D, 15.0D);
		final Matrix44D c = new Matrix44D( 0.0D,  1.0D,  2.0D,  3.0D,  4.0D,  5.0D,  6.0D,  7.0D,  8.0D,  9.0D, 10.0D, 11.0D, 12.0D, 13.0D, 14.0D, 16.0D);
		final Matrix44D d = new Matrix44D( 0.0D,  1.0D,  2.0D,  3.0D,  4.0D,  5.0D,  6.0D,  7.0D,  8.0D,  9.0D, 10.0D, 11.0D, 12.0D, 13.0D, 16.0D, 15.0D);
		final Matrix44D e = new Matrix44D( 0.0D,  1.0D,  2.0D,  3.0D,  4.0D,  5.0D,  6.0D,  7.0D,  8.0D,  9.0D, 10.0D, 11.0D, 12.0D, 16.0D, 14.0D, 15.0D);
		final Matrix44D f = new Matrix44D( 0.0D,  1.0D,  2.0D,  3.0D,  4.0D,  5.0D,  6.0D,  7.0D,  8.0D,  9.0D, 10.0D, 11.0D, 16.0D, 13.0D, 14.0D, 15.0D);
		final Matrix44D g = new Matrix44D( 0.0D,  1.0D,  2.0D,  3.0D,  4.0D,  5.0D,  6.0D,  7.0D,  8.0D,  9.0D, 10.0D, 16.0D, 12.0D, 13.0D, 14.0D, 15.0D);
		final Matrix44D h = new Matrix44D( 0.0D,  1.0D,  2.0D,  3.0D,  4.0D,  5.0D,  6.0D,  7.0D,  8.0D,  9.0D, 16.0D, 11.0D, 12.0D, 13.0D, 14.0D, 15.0D);
		final Matrix44D i = new Matrix44D( 0.0D,  1.0D,  2.0D,  3.0D,  4.0D,  5.0D,  6.0D,  7.0D,  8.0D, 16.0D, 10.0D, 11.0D, 12.0D, 13.0D, 14.0D, 15.0D);
		final Matrix44D j = new Matrix44D( 0.0D,  1.0D,  2.0D,  3.0D,  4.0D,  5.0D,  6.0D,  7.0D, 16.0D,  9.0D, 10.0D, 11.0D, 12.0D, 13.0D, 14.0D, 15.0D);
		final Matrix44D k = new Matrix44D( 0.0D,  1.0D,  2.0D,  3.0D,  4.0D,  5.0D,  6.0D, 16.0D,  8.0D,  9.0D, 10.0D, 11.0D, 12.0D, 13.0D, 14.0D, 15.0D);
		final Matrix44D l = new Matrix44D( 0.0D,  1.0D,  2.0D,  3.0D,  4.0D,  5.0D, 16.0D,  7.0D,  8.0D,  9.0D, 10.0D, 11.0D, 12.0D, 13.0D, 14.0D, 15.0D);
		final Matrix44D m = new Matrix44D( 0.0D,  1.0D,  2.0D,  3.0D,  4.0D, 16.0D,  6.0D,  7.0D,  8.0D,  9.0D, 10.0D, 11.0D, 12.0D, 13.0D, 14.0D, 15.0D);
		final Matrix44D n = new Matrix44D( 0.0D,  1.0D,  2.0D,  3.0D, 16.0D,  5.0D,  6.0D,  7.0D,  8.0D,  9.0D, 10.0D, 11.0D, 12.0D, 13.0D, 14.0D, 15.0D);
		final Matrix44D o = new Matrix44D( 0.0D,  1.0D,  2.0D, 16.0D,  4.0D,  5.0D,  6.0D,  7.0D,  8.0D,  9.0D, 10.0D, 11.0D, 12.0D, 13.0D, 14.0D, 15.0D);
		final Matrix44D p = new Matrix44D( 0.0D,  1.0D, 16.0D,  3.0D,  4.0D,  5.0D,  6.0D,  7.0D,  8.0D,  9.0D, 10.0D, 11.0D, 12.0D, 13.0D, 14.0D, 15.0D);
		final Matrix44D q = new Matrix44D( 0.0D, 16.0D,  2.0D,  3.0D,  4.0D,  5.0D,  6.0D,  7.0D,  8.0D,  9.0D, 10.0D, 11.0D, 12.0D, 13.0D, 14.0D, 15.0D);
		final Matrix44D r = new Matrix44D(16.0D,  1.0D,  2.0D,  3.0D,  4.0D,  5.0D,  6.0D,  7.0D,  8.0D,  9.0D, 10.0D, 11.0D, 12.0D, 13.0D, 14.0D, 15.0D);
		final Matrix44D s = null;
		
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
		assertFalse(g.equals(a));
		assertFalse(a.equals(h));
		assertFalse(h.equals(a));
		assertFalse(a.equals(i));
		assertFalse(i.equals(a));
		assertFalse(a.equals(j));
		assertFalse(j.equals(a));
		assertFalse(a.equals(k));
		assertFalse(k.equals(a));
		assertFalse(a.equals(l));
		assertFalse(l.equals(a));
		assertFalse(a.equals(m));
		assertFalse(m.equals(a));
		assertFalse(a.equals(n));
		assertFalse(n.equals(a));
		assertFalse(a.equals(o));
		assertFalse(o.equals(a));
		assertFalse(a.equals(p));
		assertFalse(p.equals(a));
		assertFalse(a.equals(q));
		assertFalse(q.equals(a));
		assertFalse(a.equals(r));
		assertFalse(r.equals(a));
		assertFalse(a.equals(s));
	}
	
	@Test
	public void testEqualsObject() {
		final Matrix44D a = new Matrix44D( 0.0D,  1.0D,  2.0D,  3.0D,  4.0D,  5.0D,  6.0D,  7.0D,  8.0D,  9.0D, 10.0D, 11.0D, 12.0D, 13.0D, 14.0D, 15.0D);
		final Matrix44D b = new Matrix44D( 0.0D,  1.0D,  2.0D,  3.0D,  4.0D,  5.0D,  6.0D,  7.0D,  8.0D,  9.0D, 10.0D, 11.0D, 12.0D, 13.0D, 14.0D, 15.0D);
		final Matrix44D c = new Matrix44D( 0.0D,  1.0D,  2.0D,  3.0D,  4.0D,  5.0D,  6.0D,  7.0D,  8.0D,  9.0D, 10.0D, 11.0D, 12.0D, 13.0D, 14.0D, 16.0D);
		final Matrix44D d = new Matrix44D( 0.0D,  1.0D,  2.0D,  3.0D,  4.0D,  5.0D,  6.0D,  7.0D,  8.0D,  9.0D, 10.0D, 11.0D, 12.0D, 13.0D, 16.0D, 15.0D);
		final Matrix44D e = new Matrix44D( 0.0D,  1.0D,  2.0D,  3.0D,  4.0D,  5.0D,  6.0D,  7.0D,  8.0D,  9.0D, 10.0D, 11.0D, 12.0D, 16.0D, 14.0D, 15.0D);
		final Matrix44D f = new Matrix44D( 0.0D,  1.0D,  2.0D,  3.0D,  4.0D,  5.0D,  6.0D,  7.0D,  8.0D,  9.0D, 10.0D, 11.0D, 16.0D, 13.0D, 14.0D, 15.0D);
		final Matrix44D g = new Matrix44D( 0.0D,  1.0D,  2.0D,  3.0D,  4.0D,  5.0D,  6.0D,  7.0D,  8.0D,  9.0D, 10.0D, 16.0D, 12.0D, 13.0D, 14.0D, 15.0D);
		final Matrix44D h = new Matrix44D( 0.0D,  1.0D,  2.0D,  3.0D,  4.0D,  5.0D,  6.0D,  7.0D,  8.0D,  9.0D, 16.0D, 11.0D, 12.0D, 13.0D, 14.0D, 15.0D);
		final Matrix44D i = new Matrix44D( 0.0D,  1.0D,  2.0D,  3.0D,  4.0D,  5.0D,  6.0D,  7.0D,  8.0D, 16.0D, 10.0D, 11.0D, 12.0D, 13.0D, 14.0D, 15.0D);
		final Matrix44D j = new Matrix44D( 0.0D,  1.0D,  2.0D,  3.0D,  4.0D,  5.0D,  6.0D,  7.0D, 16.0D,  9.0D, 10.0D, 11.0D, 12.0D, 13.0D, 14.0D, 15.0D);
		final Matrix44D k = new Matrix44D( 0.0D,  1.0D,  2.0D,  3.0D,  4.0D,  5.0D,  6.0D, 16.0D,  8.0D,  9.0D, 10.0D, 11.0D, 12.0D, 13.0D, 14.0D, 15.0D);
		final Matrix44D l = new Matrix44D( 0.0D,  1.0D,  2.0D,  3.0D,  4.0D,  5.0D, 16.0D,  7.0D,  8.0D,  9.0D, 10.0D, 11.0D, 12.0D, 13.0D, 14.0D, 15.0D);
		final Matrix44D m = new Matrix44D( 0.0D,  1.0D,  2.0D,  3.0D,  4.0D, 16.0D,  6.0D,  7.0D,  8.0D,  9.0D, 10.0D, 11.0D, 12.0D, 13.0D, 14.0D, 15.0D);
		final Matrix44D n = new Matrix44D( 0.0D,  1.0D,  2.0D,  3.0D, 16.0D,  5.0D,  6.0D,  7.0D,  8.0D,  9.0D, 10.0D, 11.0D, 12.0D, 13.0D, 14.0D, 15.0D);
		final Matrix44D o = new Matrix44D( 0.0D,  1.0D,  2.0D, 16.0D,  4.0D,  5.0D,  6.0D,  7.0D,  8.0D,  9.0D, 10.0D, 11.0D, 12.0D, 13.0D, 14.0D, 15.0D);
		final Matrix44D p = new Matrix44D( 0.0D,  1.0D, 16.0D,  3.0D,  4.0D,  5.0D,  6.0D,  7.0D,  8.0D,  9.0D, 10.0D, 11.0D, 12.0D, 13.0D, 14.0D, 15.0D);
		final Matrix44D q = new Matrix44D( 0.0D, 16.0D,  2.0D,  3.0D,  4.0D,  5.0D,  6.0D,  7.0D,  8.0D,  9.0D, 10.0D, 11.0D, 12.0D, 13.0D, 14.0D, 15.0D);
		final Matrix44D r = new Matrix44D(16.0D,  1.0D,  2.0D,  3.0D,  4.0D,  5.0D,  6.0D,  7.0D,  8.0D,  9.0D, 10.0D, 11.0D, 12.0D, 13.0D, 14.0D, 15.0D);
		final Matrix44D s = null;
		
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
		assertNotEquals(a, h);
		assertNotEquals(h, a);
		assertNotEquals(a, i);
		assertNotEquals(i, a);
		assertNotEquals(a, j);
		assertNotEquals(j, a);
		assertNotEquals(a, k);
		assertNotEquals(k, a);
		assertNotEquals(a, l);
		assertNotEquals(l, a);
		assertNotEquals(a, m);
		assertNotEquals(m, a);
		assertNotEquals(a, n);
		assertNotEquals(n, a);
		assertNotEquals(a, o);
		assertNotEquals(o, a);
		assertNotEquals(a, p);
		assertNotEquals(p, a);
		assertNotEquals(a, q);
		assertNotEquals(q, a);
		assertNotEquals(a, r);
		assertNotEquals(r, a);
		assertNotEquals(a, s);
		assertNotEquals(s, a);
	}
	
	@Test
	public void testHashCode() {
		final Matrix44D a = new Matrix44D(1.0D, 2.0D, 3.0D, 4.0D, 5.0D, 6.0D, 7.0D, 8.0D, 9.0D, 10.0D, 11.0D, 12.0D, 13.0D, 14.0D, 15.0D, 16.0D);
		final Matrix44D b = new Matrix44D(1.0D, 2.0D, 3.0D, 4.0D, 5.0D, 6.0D, 7.0D, 8.0D, 9.0D, 10.0D, 11.0D, 12.0D, 13.0D, 14.0D, 15.0D, 16.0D);
		
		assertEquals(a.hashCode(), a.hashCode());
		assertEquals(a.hashCode(), b.hashCode());
	}
	
	@Test
	public void testIdentity() {
		final Matrix44D m = Matrix44D.identity();
		
		assertEquals(1.0D, m.element11);
		assertEquals(0.0D, m.element12);
		assertEquals(0.0D, m.element13);
		assertEquals(0.0D, m.element14);
		assertEquals(0.0D, m.element21);
		assertEquals(1.0D, m.element22);
		assertEquals(0.0D, m.element23);
		assertEquals(0.0D, m.element24);
		assertEquals(0.0D, m.element31);
		assertEquals(0.0D, m.element32);
		assertEquals(1.0D, m.element33);
		assertEquals(0.0D, m.element34);
		assertEquals(0.0D, m.element41);
		assertEquals(0.0D, m.element42);
		assertEquals(0.0D, m.element43);
		assertEquals(1.0D, m.element44);
	}
	
	@Test
	public void testInverse() {
		final Matrix44D a = new Matrix44D(2.0D, 0.0D, 0.0D, 0.0D, 0.0D, 2.0D, 0.0D, 0.0D, 0.0D, 0.0D, 2.0D, 0.0D, 0.0D, 0.0D, 0.0D, 2.0D);
		final Matrix44D b = Matrix44D.inverse(a);
		final Matrix44D c = Matrix44D.inverse(b);
		final Matrix44D d = Matrix44D.multiply(a, b);
		final Matrix44D e = Matrix44D.identity();
		
		assertEquals(a, c);
		assertEquals(d, e);
		
		assertThrows(IllegalArgumentException.class, () -> Matrix44D.inverse(new Matrix44D(1.0D, 2.0D, 3.0D, 4.0D, 5.0D, 6.0D, 7.0D, 8.0D, 9.0D, 10.0D, 11.0D, 12.0D, 13.0D, 14.0D, 15.0D, 16.0D)));
		assertThrows(NullPointerException.class, () -> Matrix44D.inverse(null));
	}
	
	@Test
	public void testIsInvertible() {
		final Matrix44D a = new Matrix44D(1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 0.0D, 0.0D,  0.0D,  1.0D,  0.0D,  0.0D,  0.0D,  0.0D,  1.0D);
		final Matrix44D b = new Matrix44D(1.0D, 2.0D, 3.0D, 4.0D, 5.0D, 6.0D, 7.0D, 8.0D, 9.0D, 10.0D, 11.0D, 12.0D, 13.0D, 14.0D, 15.0D, 16.0D);
		
		assertTrue(a.isInvertible());
		
		assertFalse(b.isInvertible());
	}
	
	@Test
	public void testMultiply() {
		final Matrix44D a = new Matrix44D(1.0D, 2.0D, 3.0D, 4.0D, 5.0D, 6.0D, 7.0D, 8.0D, 9.0D, 10.0D, 11.0D, 12.0D, 13.0D, 14.0D, 15.0D, 16.0D);
		final Matrix44D b = Matrix44D.identity();
		final Matrix44D c = Matrix44D.multiply(a, b);
		
		assertEquals(a, c);
		
		assertThrows(NullPointerException.class, () -> Matrix44D.multiply(a, null));
		assertThrows(NullPointerException.class, () -> Matrix44D.multiply(null, b));
	}
	
	@Test
	public void testRotateOrthonormalBasis33D() {
		final Matrix44D m = Matrix44D.rotate(new OrthonormalBasis33D(Vector3D.z(), Vector3D.y(), Vector3D.x()));
		
		assertEquals(1.0D, m.element11);
		assertEquals(0.0D, m.element12);
		assertEquals(0.0D, m.element13);
		assertEquals(0.0D, m.element14);
		assertEquals(0.0D, m.element21);
		assertEquals(1.0D, m.element22);
		assertEquals(0.0D, m.element23);
		assertEquals(0.0D, m.element24);
		assertEquals(0.0D, m.element31);
		assertEquals(0.0D, m.element32);
		assertEquals(1.0D, m.element33);
		assertEquals(0.0D, m.element34);
		assertEquals(0.0D, m.element41);
		assertEquals(0.0D, m.element42);
		assertEquals(0.0D, m.element43);
		assertEquals(1.0D, m.element44);
		
		assertThrows(NullPointerException.class, () -> Matrix44D.rotate((OrthonormalBasis33D)(null)));
	}
	
	@Test
	public void testRotateVector3DVector3D() {
		final Matrix44D m = Matrix44D.rotate(new Vector3D(0.0D, 0.0D, 1.0D), new Vector3D(0.0D, 1.0D, 0.0D));
		
		assertEquals(1.0D, m.element11);
		assertEquals(0.0D, m.element12);
		assertEquals(0.0D, m.element13);
		assertEquals(0.0D, m.element14);
		assertEquals(0.0D, m.element21);
		assertEquals(1.0D, m.element22);
		assertEquals(0.0D, m.element23);
		assertEquals(0.0D, m.element24);
		assertEquals(0.0D, m.element31);
		assertEquals(0.0D, m.element32);
		assertEquals(1.0D, m.element33);
		assertEquals(0.0D, m.element34);
		assertEquals(0.0D, m.element41);
		assertEquals(0.0D, m.element42);
		assertEquals(0.0D, m.element43);
		assertEquals(1.0D, m.element44);
		
		assertThrows(NullPointerException.class, () -> Matrix44D.rotate(new Vector3D(0.0D, 0.0D, 1.0D), null));
		assertThrows(NullPointerException.class, () -> Matrix44D.rotate((Vector3D)(null), new Vector3D(0.0D, 1.0D, 0.0D)));
	}
	
	@Test
	public void testRotateVector3DVector3DVector3D() {
		final Matrix44D m = Matrix44D.rotate(new Vector3D(0.0D, 0.0D, 1.0D), new Vector3D(0.0D, 1.0D, 0.0D), new Vector3D(1.0D, 0.0D, 0.0D));
		
		assertEquals(1.0D, m.element11);
		assertEquals(0.0D, m.element12);
		assertEquals(0.0D, m.element13);
		assertEquals(0.0D, m.element14);
		assertEquals(0.0D, m.element21);
		assertEquals(1.0D, m.element22);
		assertEquals(0.0D, m.element23);
		assertEquals(0.0D, m.element24);
		assertEquals(0.0D, m.element31);
		assertEquals(0.0D, m.element32);
		assertEquals(1.0D, m.element33);
		assertEquals(0.0D, m.element34);
		assertEquals(0.0D, m.element41);
		assertEquals(0.0D, m.element42);
		assertEquals(0.0D, m.element43);
		assertEquals(1.0D, m.element44);
		
		assertThrows(NullPointerException.class, () -> Matrix44D.rotate(new Vector3D(0.0D, 0.0D, 1.0D), new Vector3D(0.0D, 1.0D, 0.0D), null));
		assertThrows(NullPointerException.class, () -> Matrix44D.rotate(new Vector3D(0.0D, 0.0D, 1.0D), null, new Vector3D(1.0D, 0.0D, 0.0D)));
		assertThrows(NullPointerException.class, () -> Matrix44D.rotate(null, new Vector3D(0.0D, 1.0D, 0.0D), new Vector3D(1.0D, 0.0D, 0.0D)));
	}
	
	@Test
	public void testRotateXDouble() {
		final double angleDegrees = 90.0D;
		final double angleRadians = Math.toRadians(angleDegrees);
		final double angleRadiansCos = Math.cos(angleRadians);
		final double angleRadiansSin = Math.sin(angleRadians);
		
		final Matrix44D m = Matrix44D.rotateX(angleDegrees);
		
		assertEquals(1.0D,             m.element11);
		assertEquals(0.0D,             m.element12);
		assertEquals(0.0D,             m.element13);
		assertEquals(0.0D,             m.element14);
		assertEquals(0.0D,             m.element21);
		assertEquals(+angleRadiansCos, m.element22);
		assertEquals(-angleRadiansSin, m.element23);
		assertEquals(0.0D,             m.element24);
		assertEquals(0.0D,             m.element31);
		assertEquals(+angleRadiansSin, m.element32);
		assertEquals(+angleRadiansCos, m.element33);
		assertEquals(0.0D,             m.element34);
		assertEquals(0.0D,             m.element41);
		assertEquals(0.0D,             m.element42);
		assertEquals(0.0D,             m.element43);
		assertEquals(1.0D,             m.element44);
	}
	
	@Test
	public void testRotateXDoubleBoolean() {
		final double angleDegrees = 90.0D;
		final double angleRadians = Math.toRadians(angleDegrees);
		final double angleRadiansCos = Math.cos(angleRadians);
		final double angleRadiansSin = Math.sin(angleRadians);
		
		final Matrix44D a = Matrix44D.rotateX(angleDegrees, false);
		final Matrix44D b = Matrix44D.rotateX(angleRadians, true);
		
		assertEquals(1.0D,             a.element11);
		assertEquals(0.0D,             a.element12);
		assertEquals(0.0D,             a.element13);
		assertEquals(0.0D,             a.element14);
		assertEquals(0.0D,             a.element21);
		assertEquals(+angleRadiansCos, a.element22);
		assertEquals(-angleRadiansSin, a.element23);
		assertEquals(0.0D,             a.element24);
		assertEquals(0.0D,             a.element31);
		assertEquals(+angleRadiansSin, a.element32);
		assertEquals(+angleRadiansCos, a.element33);
		assertEquals(0.0D,             a.element34);
		assertEquals(0.0D,             a.element41);
		assertEquals(0.0D,             a.element42);
		assertEquals(0.0D,             a.element43);
		assertEquals(1.0D,             a.element44);
		
		assertEquals(1.0D,             b.element11);
		assertEquals(0.0D,             b.element12);
		assertEquals(0.0D,             b.element13);
		assertEquals(0.0D,             b.element14);
		assertEquals(0.0D,             b.element21);
		assertEquals(+angleRadiansCos, b.element22);
		assertEquals(-angleRadiansSin, b.element23);
		assertEquals(0.0D,             b.element24);
		assertEquals(0.0D,             b.element31);
		assertEquals(+angleRadiansSin, b.element32);
		assertEquals(+angleRadiansCos, b.element33);
		assertEquals(0.0D,             b.element34);
		assertEquals(0.0D,             b.element41);
		assertEquals(0.0D,             b.element42);
		assertEquals(0.0D,             b.element43);
		assertEquals(1.0D,             b.element44);
	}
	
	@Test
	public void testRotateYDouble() {
		final double angleDegrees = 90.0D;
		final double angleRadians = Math.toRadians(angleDegrees);
		final double angleRadiansCos = Math.cos(angleRadians);
		final double angleRadiansSin = Math.sin(angleRadians);
		
		final Matrix44D m = Matrix44D.rotateY(angleDegrees);
		
		assertEquals(+angleRadiansCos, m.element11);
		assertEquals(0.0D,             m.element12);
		assertEquals(+angleRadiansSin, m.element13);
		assertEquals(0.0D,             m.element14);
		assertEquals(0.0D,             m.element21);
		assertEquals(1.0D,             m.element22);
		assertEquals(0.0D,             m.element23);
		assertEquals(0.0D,             m.element24);
		assertEquals(-angleRadiansSin, m.element31);
		assertEquals(0.0D,             m.element32);
		assertEquals(+angleRadiansCos, m.element33);
		assertEquals(0.0D,             m.element34);
		assertEquals(0.0D,             m.element41);
		assertEquals(0.0D,             m.element42);
		assertEquals(0.0D,             m.element43);
		assertEquals(1.0D,             m.element44);
	}
	
	@Test
	public void testRotateYDoubleBoolean() {
		final double angleDegrees = 90.0D;
		final double angleRadians = Math.toRadians(angleDegrees);
		final double angleRadiansCos = Math.cos(angleRadians);
		final double angleRadiansSin = Math.sin(angleRadians);
		
		final Matrix44D a = Matrix44D.rotateY(angleDegrees, false);
		final Matrix44D b = Matrix44D.rotateY(angleRadians, true);
		
		assertEquals(+angleRadiansCos, a.element11);
		assertEquals(0.0D,             a.element12);
		assertEquals(+angleRadiansSin, a.element13);
		assertEquals(0.0D,             a.element14);
		assertEquals(0.0D,             a.element21);
		assertEquals(1.0D,             a.element22);
		assertEquals(0.0D,             a.element23);
		assertEquals(0.0D,             a.element24);
		assertEquals(-angleRadiansSin, a.element31);
		assertEquals(0.0D,             a.element32);
		assertEquals(+angleRadiansCos, a.element33);
		assertEquals(0.0D,             a.element34);
		assertEquals(0.0D,             a.element41);
		assertEquals(0.0D,             a.element42);
		assertEquals(0.0D,             a.element43);
		assertEquals(1.0D,             a.element44);
		
		assertEquals(+angleRadiansCos, b.element11);
		assertEquals(0.0D,             b.element12);
		assertEquals(+angleRadiansSin, b.element13);
		assertEquals(0.0D,             b.element14);
		assertEquals(0.0D,             b.element21);
		assertEquals(1.0D,             b.element22);
		assertEquals(0.0D,             b.element23);
		assertEquals(0.0D,             b.element24);
		assertEquals(-angleRadiansSin, b.element31);
		assertEquals(0.0D,             b.element32);
		assertEquals(+angleRadiansCos, b.element33);
		assertEquals(0.0D,             b.element34);
		assertEquals(0.0D,             b.element41);
		assertEquals(0.0D,             b.element42);
		assertEquals(0.0D,             b.element43);
		assertEquals(1.0D,             b.element44);
	}
	
	@Test
	public void testRotateZDouble() {
		final double angleDegrees = 90.0D;
		final double angleRadians = Math.toRadians(angleDegrees);
		final double angleRadiansCos = Math.cos(angleRadians);
		final double angleRadiansSin = Math.sin(angleRadians);
		
		final Matrix44D m = Matrix44D.rotateZ(angleDegrees);
		
		assertEquals(+angleRadiansCos, m.element11);
		assertEquals(-angleRadiansSin, m.element12);
		assertEquals(0.0D,             m.element13);
		assertEquals(0.0D,             m.element14);
		assertEquals(+angleRadiansSin, m.element21);
		assertEquals(+angleRadiansCos, m.element22);
		assertEquals(0.0D,             m.element23);
		assertEquals(0.0D,             m.element24);
		assertEquals(0.0D,             m.element31);
		assertEquals(0.0D,             m.element32);
		assertEquals(1.0D,             m.element33);
		assertEquals(0.0D,             m.element34);
		assertEquals(0.0D,             m.element41);
		assertEquals(0.0D,             m.element42);
		assertEquals(0.0D,             m.element43);
		assertEquals(1.0D,             m.element44);
	}
	
	@Test
	public void testRotateZDoubleBoolean() {
		final double angleDegrees = 90.0D;
		final double angleRadians = Math.toRadians(angleDegrees);
		final double angleRadiansCos = Math.cos(angleRadians);
		final double angleRadiansSin = Math.sin(angleRadians);
		
		final Matrix44D a = Matrix44D.rotateZ(angleDegrees, false);
		final Matrix44D b = Matrix44D.rotateZ(angleRadians, true);
		
		assertEquals(+angleRadiansCos, a.element11);
		assertEquals(-angleRadiansSin, a.element12);
		assertEquals(0.0D,             a.element13);
		assertEquals(0.0D,             a.element14);
		assertEquals(+angleRadiansSin, a.element21);
		assertEquals(+angleRadiansCos, a.element22);
		assertEquals(0.0D,             a.element23);
		assertEquals(0.0D,             a.element24);
		assertEquals(0.0D,             a.element31);
		assertEquals(0.0D,             a.element32);
		assertEquals(1.0D,             a.element33);
		assertEquals(0.0D,             a.element34);
		assertEquals(0.0D,             a.element41);
		assertEquals(0.0D,             a.element42);
		assertEquals(0.0D,             a.element43);
		assertEquals(1.0D,             a.element44);
		
		assertEquals(+angleRadiansCos, b.element11);
		assertEquals(-angleRadiansSin, b.element12);
		assertEquals(0.0D,             b.element13);
		assertEquals(0.0D,             b.element14);
		assertEquals(+angleRadiansSin, b.element21);
		assertEquals(+angleRadiansCos, b.element22);
		assertEquals(0.0D,             b.element23);
		assertEquals(0.0D,             b.element24);
		assertEquals(0.0D,             b.element31);
		assertEquals(0.0D,             b.element32);
		assertEquals(1.0D,             b.element33);
		assertEquals(0.0D,             b.element34);
		assertEquals(0.0D,             b.element41);
		assertEquals(0.0D,             b.element42);
		assertEquals(0.0D,             b.element43);
		assertEquals(1.0D,             b.element44);
	}
	
	@Test
	public void testScaleDouble() {
		final Matrix44D m = Matrix44D.scale(2.0D);
		
		assertEquals(2.0D, m.element11);
		assertEquals(0.0D, m.element12);
		assertEquals(0.0D, m.element13);
		assertEquals(0.0D, m.element14);
		assertEquals(0.0D, m.element21);
		assertEquals(2.0D, m.element22);
		assertEquals(0.0D, m.element23);
		assertEquals(0.0D, m.element24);
		assertEquals(0.0D, m.element31);
		assertEquals(0.0D, m.element32);
		assertEquals(2.0D, m.element33);
		assertEquals(0.0D, m.element34);
		assertEquals(0.0D, m.element41);
		assertEquals(0.0D, m.element42);
		assertEquals(0.0D, m.element43);
		assertEquals(1.0D, m.element44);
	}
	
	@Test
	public void testScaleDoubleDoubleDouble() {
		final Matrix44D m = Matrix44D.scale(2.0D, 3.0D, 4.0D);
		
		assertEquals(2.0D, m.element11);
		assertEquals(0.0D, m.element12);
		assertEquals(0.0D, m.element13);
		assertEquals(0.0D, m.element14);
		assertEquals(0.0D, m.element21);
		assertEquals(3.0D, m.element22);
		assertEquals(0.0D, m.element23);
		assertEquals(0.0D, m.element24);
		assertEquals(0.0D, m.element31);
		assertEquals(0.0D, m.element32);
		assertEquals(4.0D, m.element33);
		assertEquals(0.0D, m.element34);
		assertEquals(0.0D, m.element41);
		assertEquals(0.0D, m.element42);
		assertEquals(0.0D, m.element43);
		assertEquals(1.0D, m.element44);
	}
	
	@Test
	public void testScaleVector3D() {
		final Matrix44D m = Matrix44D.scale(new Vector3D(2.0D, 3.0D, 4.0D));
		
		assertEquals(2.0D, m.element11);
		assertEquals(0.0D, m.element12);
		assertEquals(0.0D, m.element13);
		assertEquals(0.0D, m.element14);
		assertEquals(0.0D, m.element21);
		assertEquals(3.0D, m.element22);
		assertEquals(0.0D, m.element23);
		assertEquals(0.0D, m.element24);
		assertEquals(0.0D, m.element31);
		assertEquals(0.0D, m.element32);
		assertEquals(4.0D, m.element33);
		assertEquals(0.0D, m.element34);
		assertEquals(0.0D, m.element41);
		assertEquals(0.0D, m.element42);
		assertEquals(0.0D, m.element43);
		assertEquals(1.0D, m.element44);
		
		assertThrows(NullPointerException.class, () -> Matrix44D.scale(null));
	}
	
	@Test
	public void testToString() {
		final Matrix44D m = new Matrix44D(1.0D, 2.0D, 3.0D, 4.0D, 5.0D, 6.0D, 7.0D, 8.0D, 9.0D, 10.0D, 11.0D, 12.0D, 13.0D, 14.0D, 15.0D, 16.0D);
		
		assertEquals("new Matrix44D(1.0D, 2.0D, 3.0D, 4.0D, 5.0D, 6.0D, 7.0D, 8.0D, 9.0D, 10.0D, 11.0D, 12.0D, 13.0D, 14.0D, 15.0D, 16.0D)", m.toString());
	}
	
	@Test
	public void testTranslateDoubleDoubleDouble() {
		final Matrix44D m = Matrix44D.translate(2.0D, 3.0D, 4.0D);
		
		assertEquals(1.0D, m.element11);
		assertEquals(0.0D, m.element12);
		assertEquals(0.0D, m.element13);
		assertEquals(2.0D, m.element14);
		assertEquals(0.0D, m.element21);
		assertEquals(1.0D, m.element22);
		assertEquals(0.0D, m.element23);
		assertEquals(3.0D, m.element24);
		assertEquals(0.0D, m.element31);
		assertEquals(0.0D, m.element32);
		assertEquals(1.0D, m.element33);
		assertEquals(4.0D, m.element34);
		assertEquals(0.0D, m.element41);
		assertEquals(0.0D, m.element42);
		assertEquals(0.0D, m.element43);
		assertEquals(1.0D, m.element44);
	}
	
	@Test
	public void testTranslatePoint3D() {
		final Matrix44D m = Matrix44D.translate(new Point3D(2.0D, 3.0D, 4.0D));
		
		assertEquals(1.0D, m.element11);
		assertEquals(0.0D, m.element12);
		assertEquals(0.0D, m.element13);
		assertEquals(2.0D, m.element14);
		assertEquals(0.0D, m.element21);
		assertEquals(1.0D, m.element22);
		assertEquals(0.0D, m.element23);
		assertEquals(3.0D, m.element24);
		assertEquals(0.0D, m.element31);
		assertEquals(0.0D, m.element32);
		assertEquals(1.0D, m.element33);
		assertEquals(4.0D, m.element34);
		assertEquals(0.0D, m.element41);
		assertEquals(0.0D, m.element42);
		assertEquals(0.0D, m.element43);
		assertEquals(1.0D, m.element44);
		
		assertThrows(NullPointerException.class, () -> Matrix44D.translate(null));
	}
	
	@Test
	public void testTranspose() {
		final Matrix44D a = new Matrix44D(1.0D, 2.0D, 3.0D, 4.0D, 5.0D, 6.0D, 7.0D, 8.0D, 9.0D, 10.0D, 11.0D, 12.0D, 13.0D, 14.0D, 15.0D, 16.0D);
		final Matrix44D b = Matrix44D.transpose(a);
		
		assertEquals( 1.0D, b.element11);
		assertEquals( 5.0D, b.element12);
		assertEquals( 9.0D, b.element13);
		assertEquals(13.0D, b.element14);
		assertEquals( 2.0D, b.element21);
		assertEquals( 6.0D, b.element22);
		assertEquals(10.0D, b.element23);
		assertEquals(14.0D, b.element24);
		assertEquals( 3.0D, b.element31);
		assertEquals( 7.0D, b.element32);
		assertEquals(11.0D, b.element33);
		assertEquals(15.0D, b.element34);
		assertEquals( 4.0D, b.element41);
		assertEquals( 8.0D, b.element42);
		assertEquals(12.0D, b.element43);
		assertEquals(16.0D, b.element44);
		
		assertThrows(NullPointerException.class, () -> Matrix44D.transpose(null));
	}
}