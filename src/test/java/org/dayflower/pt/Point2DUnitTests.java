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
public final class Point2DUnitTests {
	public Point2DUnitTests() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testConstructor() {
		final Point2D p = new Point2D();
		
		assertEquals(0.0D, p.x);
		assertEquals(0.0D, p.y);
	}
	
	@Test
	public void testConstructorDoubleDouble() {
		final Point2D p = new Point2D(1.0D, 2.0D);
		
		assertEquals(1.0D, p.x);
		assertEquals(2.0D, p.y);
	}
	
	@Test
	public void testEqualsObject() {
		final Point2D a = new Point2D(1.0D, 2.0D);
		final Point2D b = new Point2D(1.0D, 2.0D);
		final Point2D c = new Point2D(1.0D, 3.0D);
		final Point2D d = new Point2D(3.0D, 2.0D);
		final Point2D e = null;
		
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
	public void testEqualsPoint2D() {
		final Point2D a = new Point2D(1.0D, 2.0D);
		final Point2D b = new Point2D(1.0D, 2.0D);
		final Point2D c = new Point2D(1.0D, 3.0D);
		final Point2D d = new Point2D(3.0D, 2.0D);
		final Point2D e = null;
		
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
		final Point2D a = new Point2D(1.0D, 2.0D);
		final Point2D b = new Point2D(1.0D, 2.0D);
		
		assertEquals(a.hashCode(), b.hashCode());
	}
	
	@Test
	public void testIsZero() {
		assertTrue(new Point2D(0.0D, 0.0D).isZero());
		
		assertFalse(new Point2D(0.0D, 1.0D).isZero());
		assertFalse(new Point2D(1.0D, 0.0D).isZero());
		assertFalse(new Point2D(1.0D, 1.0D).isZero());
	}
	
	@Test
	public void testProject() {
		final Point2D a = Point2D.project(new Point3D(0.0D, 0.0D, 0.0D), new Point3D(1.0D, 0.0D, 0.0D), new Vector3D(1.0D, 0.0D, 0.0D), new Vector3D(0.0D, 1.0D, 0.0D));
		final Point2D b = Point2D.project(new Point3D(0.0D, 0.0D, 0.0D), new Point3D(0.0D, 1.0D, 0.0D), new Vector3D(1.0D, 0.0D, 0.0D), new Vector3D(0.0D, 1.0D, 0.0D));
		final Point2D c = Point2D.project(new Point3D(0.0D, 0.0D, 0.0D), new Point3D(0.0D, 0.0D, 1.0D), new Vector3D(1.0D, 0.0D, 0.0D), new Vector3D(0.0D, 1.0D, 0.0D));
		
		assertEquals(1.0D, a.x);
		assertEquals(0.0D, a.y);
		
		assertEquals(0.0D, b.x);
		assertEquals(1.0D, b.y);
		
		assertEquals(0.0D, c.x);
		assertEquals(0.0D, c.y);
		
		assertThrows(NullPointerException.class, () -> Point2D.project(new Point3D(0.0D, 0.0D, 0.0D), new Point3D(1.0D, 0.0D, 0.0D), new Vector3D(1.0D, 0.0D, 0.0D), null));
		assertThrows(NullPointerException.class, () -> Point2D.project(new Point3D(0.0D, 0.0D, 0.0D), new Point3D(1.0D, 0.0D, 0.0D), null, new Vector3D(0.0D, 1.0D, 0.0D)));
		assertThrows(NullPointerException.class, () -> Point2D.project(new Point3D(0.0D, 0.0D, 0.0D), null, new Vector3D(1.0D, 0.0D, 0.0D), new Vector3D(0.0D, 1.0D, 0.0D)));
		assertThrows(NullPointerException.class, () -> Point2D.project(null, new Point3D(1.0D, 0.0D, 0.0D), new Vector3D(1.0D, 0.0D, 0.0D), new Vector3D(0.0D, 1.0D, 0.0D)));
	}
	
	@Test
	public void testRotateCounterclockwise() {
		final Point2D a = new Point2D(1.0D, 1.0D);
		final Point2D b = Point2D.rotateCounterclockwise(a, 360.0D, false);
		final Point2D c = Point2D.rotateCounterclockwise(a, java.lang.Math.toRadians(360.0D), true);
		
		assertEquals(1.0000000000000002D, b.x);
		assertEquals(0.9999999999999998D, b.y);
		
		assertEquals(1.0000000000000002D, c.x);
		assertEquals(0.9999999999999998D, c.y);
		
		assertThrows(NullPointerException.class, () -> Point2D.rotateCounterclockwise(null, 360.0D, false));
	}
	
	@Test
	public void testSampleDiskUniformDistributionByConcentricMapping() {
		final Point2D a = Point2D.sampleDiskUniformDistributionByConcentricMapping();
		
		assertTrue(a.x >= -1.0D && a.x <= 1.0D);
		assertTrue(a.y >= -1.0D && a.y <= 1.0D);
	}
	
	@Test
	public void testSampleDiskUniformDistributionByConcentricMappingPoint2D() {
		final Point2D a = Point2D.sampleDiskUniformDistributionByConcentricMapping(new Point2D(0.0D, 0.0D));
		final Point2D b = Point2D.sampleDiskUniformDistributionByConcentricMapping(new Point2D(1.0D, 0.5D));
		final Point2D c = Point2D.sampleDiskUniformDistributionByConcentricMapping(new Point2D(0.5D, 1.0D));
		
		assertEquals(0.0D, a.x);
		assertEquals(0.0D, a.y);
		
		assertEquals(1.0D, b.x);
		assertEquals(0.0D, b.y);
		
		assertEquals(0.00000000000000006123233995736766D, c.x);
		assertEquals(1.00000000000000000000000000000000D, c.y);
		
		assertThrows(NullPointerException.class, () -> Point2D.sampleDiskUniformDistributionByConcentricMapping(null));
	}
	
	@Test
	public void testSampleDiskUniformDistributionByConcentricMappingPoint2DDouble() {
		final Point2D a = Point2D.sampleDiskUniformDistributionByConcentricMapping(new Point2D(0.0D, 0.0D), 1.0D);
		final Point2D b = Point2D.sampleDiskUniformDistributionByConcentricMapping(new Point2D(1.0D, 0.5D), 1.0D);
		final Point2D c = Point2D.sampleDiskUniformDistributionByConcentricMapping(new Point2D(0.5D, 1.0D), 1.0D);
		
		assertEquals(0.0D, a.x);
		assertEquals(0.0D, a.y);
		
		assertEquals(1.0D, b.x);
		assertEquals(0.0D, b.y);
		
		assertEquals(0.00000000000000006123233995736766D, c.x);
		assertEquals(1.00000000000000000000000000000000D, c.y);
		
		assertThrows(NullPointerException.class, () -> Point2D.sampleDiskUniformDistributionByConcentricMapping(null, 1.0D));
	}
	
	@Test
	public void testSampleExactInverseTentFilter() {
		final Point2D p = Point2D.sampleExactInverseTentFilter();
		
		assertTrue(p.x >= -1.0D && p.x <= 1.0D);
		assertTrue(p.y >= -1.0D && p.y <= 1.0D);
	}
	
	@Test
	public void testSampleExactInverseTentFilterPoint2D() {
		final Point2D a = Point2D.sampleExactInverseTentFilter(new Point2D(0.0D, 0.0D));
		final Point2D b = Point2D.sampleExactInverseTentFilter(new Point2D(0.5D, 0.5D));
		final Point2D c = Point2D.sampleExactInverseTentFilter(new Point2D(1.0D, 1.0D));
		
		assertEquals(-1.0D, a.x);
		assertEquals(-1.0D, a.y);
		
		assertEquals(0.0D, b.x);
		assertEquals(0.0D, b.y);
		
		assertEquals(1.0D, c.x);
		assertEquals(1.0D, c.y);
		
		assertThrows(NullPointerException.class, () -> Point2D.sampleExactInverseTentFilter(null));
	}
	
	@Test
	public void testSampleRandom() {
		final Point2D p = Point2D.sampleRandom();
		
		assertTrue(p.x >= 0.0D && p.x < 1.0D);
		assertTrue(p.y >= 0.0D && p.y < 1.0D);
	}
	
	@Test
	public void testToString() {
		final Point2D p = new Point2D(1.0D, 2.0D);
		
		assertEquals("new Point2D(1.0D, 2.0D)", p.toString());
	}
}