/**
 * Copyright 2022 - 2023 J&#246;rgen Lundgren
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

import org.macroing.geo4j.common.Point3D;
import org.macroing.geo4j.common.Vector3D;
import org.macroing.geo4j.matrix.Matrix44D;
import org.macroing.geo4j.ray.Ray3D;

@SuppressWarnings("static-method")
public final class BoundingVolumeUnitTests {
	public BoundingVolumeUnitTests() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testAxisAlignedBoundingBox() {
		assertThrows(NullPointerException.class, () -> BoundingVolume.axisAlignedBoundingBox(null, null));
		assertThrows(NullPointerException.class, () -> BoundingVolume.axisAlignedBoundingBox((Point3D[])(null)));
		
		assertThrows(IllegalArgumentException.class, () -> BoundingVolume.axisAlignedBoundingBox());
	}
	
	@Test
	public void testAxisAlignedBoundingBoxContains() {
		final BoundingVolume boundingVolume = BoundingVolume.axisAlignedBoundingBox(new Point3D(-5.0D, -5.0D, -5.0D), new Point3D(+5.0D, +5.0D, +5.0D));
		
		final Point3D a = new Point3D(+9.0D, +0.0D, +0.0D);
		final Point3D b = new Point3D(-9.0D, +0.0D, +0.0D);
		final Point3D c = new Point3D(+0.0D, +9.0D, +0.0D);
		final Point3D d = new Point3D(+0.0D, -9.0D, +0.0D);
		final Point3D e = new Point3D(+0.0D, +0.0D, +9.0D);
		final Point3D f = new Point3D(+0.0D, +0.0D, -9.0D);
		
		final Point3D g = new Point3D(+5.0D, +0.0D, +0.0D);
		final Point3D h = new Point3D(-5.0D, +0.0D, +0.0D);
		final Point3D i = new Point3D(+0.0D, +5.0D, +0.0D);
		final Point3D j = new Point3D(+0.0D, -5.0D, +0.0D);
		final Point3D k = new Point3D(+0.0D, +0.0D, +5.0D);
		final Point3D l = new Point3D(+0.0D, +0.0D, -5.0D);
		final Point3D m = new Point3D(+0.0D, +0.0D, +0.0D);
		
		assertFalse(boundingVolume.contains(a));
		assertFalse(boundingVolume.contains(b));
		assertFalse(boundingVolume.contains(c));
		assertFalse(boundingVolume.contains(d));
		assertFalse(boundingVolume.contains(e));
		assertFalse(boundingVolume.contains(f));
		
		assertTrue(boundingVolume.contains(g));
		assertTrue(boundingVolume.contains(h));
		assertTrue(boundingVolume.contains(i));
		assertTrue(boundingVolume.contains(j));
		assertTrue(boundingVolume.contains(k));
		assertTrue(boundingVolume.contains(l));
		assertTrue(boundingVolume.contains(m));
		
		assertThrows(NullPointerException.class, () -> boundingVolume.contains(null));
	}
	
	@Test
	public void testAxisAlignedBoundingBoxEquals() {
		final BoundingVolume a = BoundingVolume.axisAlignedBoundingBox(new Point3D(-5.0D, -5.0D, -5.0D), new Point3D(+5.0D, +5.0D, +5.0D));
		final BoundingVolume b = BoundingVolume.axisAlignedBoundingBox(new Point3D(-5.0D, -5.0D, -5.0D), new Point3D(+5.0D, +5.0D, +5.0D));
		final BoundingVolume c = BoundingVolume.axisAlignedBoundingBox(new Point3D(-5.0D, -5.0D, -5.0D), new Point3D(+6.0D, +6.0D, +6.0D));
		final BoundingVolume d = BoundingVolume.axisAlignedBoundingBox(new Point3D(-6.0D, -6.0D, -6.0D), new Point3D(+5.0D, +5.0D, +5.0D));
		final BoundingVolume e = null;
		
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
	public void testAxisAlignedBoundingBoxHashCode() {
		final BoundingVolume a = BoundingVolume.axisAlignedBoundingBox(new Point3D(-5.0D, -5.0D, -5.0D), new Point3D(+5.0D, +5.0D, +5.0D));
		final BoundingVolume b = BoundingVolume.axisAlignedBoundingBox(new Point3D(-5.0D, -5.0D, -5.0D), new Point3D(+5.0D, +5.0D, +5.0D));
		
		assertEquals(a.hashCode(), a.hashCode());
		assertEquals(a.hashCode(), b.hashCode());
	}
	
	@Test
	public void testAxisAlignedBoundingBoxIntersection() {
		final BoundingVolume boundingVolume = BoundingVolume.axisAlignedBoundingBox(new Point3D(-5.0D, -5.0D, +5.0D), new Point3D(+5.0D, +5.0D, +10.0D));
		
		final Ray3D rayA = new Ray3D(new Point3D(0.0D, 0.0D, 0.0D), Vector3D.z());
		final Ray3D rayB = new Ray3D(new Point3D(0.0D, 0.0D, 0.0D), Vector3D.x());
		
		final double tMinimumA =  0.0D;
		final double tMinimumB =  5.0D;
		final double tMinimumC = 10.0D;
		
		final double tMaximumA = Double.MAX_VALUE;
		final double tMaximumB = 5.0D;
		
		final double expectedTA =  5.0D;
		final double expectedTB = 10.0D;
		final double expectedTC = Double.NaN;
		
		assertEquals(expectedTA, boundingVolume.intersection(rayA, tMinimumA, tMaximumA));
		assertEquals(expectedTB, boundingVolume.intersection(rayA, tMinimumB, tMaximumA));
		assertEquals(expectedTC, boundingVolume.intersection(rayA, tMinimumC, tMaximumA));
		assertEquals(expectedTC, boundingVolume.intersection(rayA, tMinimumA, tMaximumB));
		
		assertEquals(expectedTC, boundingVolume.intersection(rayB, tMinimumA, tMaximumA));
		
		assertThrows(NullPointerException.class, () -> boundingVolume.intersection(null, 0.0D, Double.MAX_VALUE));
	}
	
	@Test
	public void testAxisAlignedBoundingBoxIntersects() {
		final BoundingVolume boundingVolume = BoundingVolume.axisAlignedBoundingBox(new Point3D(-5.0D, -5.0D, +5.0D), new Point3D(+5.0D, +5.0D, +10.0D));
		
		final Ray3D rayA = new Ray3D(new Point3D(0.0D, 0.0D, 0.0D), Vector3D.z());
		final Ray3D rayB = new Ray3D(new Point3D(0.0D, 0.0D, 0.0D), Vector3D.x());
		
		final double tMinimumA =  0.0D;
		final double tMinimumB =  5.0D;
		final double tMinimumC = 10.0D;
		
		final double tMaximumA = Double.MAX_VALUE;
		final double tMaximumB = 5.0D;
		
		assertTrue(boundingVolume.intersects(rayA, tMinimumA, tMaximumA));
		assertTrue(boundingVolume.intersects(rayA, tMinimumB, tMaximumA));
		
		assertFalse(boundingVolume.intersects(rayA, tMinimumC, tMaximumA));
		assertFalse(boundingVolume.intersects(rayA, tMinimumA, tMaximumB));
		assertFalse(boundingVolume.intersects(rayB, tMinimumA, tMaximumA));
		
		assertThrows(NullPointerException.class, () -> boundingVolume.intersection(null, 0.0D, Double.MAX_VALUE));
	}
	
	@Test
	public void testAxisAlignedBoundingBoxToString() {
		final BoundingVolume boundingVolume = BoundingVolume.axisAlignedBoundingBox(new Point3D(-1.0D, -1.0D, -1.0D), new Point3D(+1.0D, +1.0D, +1.0D));
		
		assertEquals("BoundingVolume.axisAlignedBoundingBox(new Point3D(1.0D, 1.0D, 1.0D), new Point3D(-1.0D, -1.0D, -1.0D))", boundingVolume.toString());
	}
	
	@Test
	public void testAxisAlignedBoundingBoxTransform() {
		final BoundingVolume a = BoundingVolume.axisAlignedBoundingBox(new Point3D(10.0D, 20.0D, 30.0D), new Point3D(20.0D, 30.0D, 40.0D));
		final BoundingVolume b = a.transform(Matrix44D.translate(+10.0D, +20.0D, +30.0D));
		final BoundingVolume c = b.transform(Matrix44D.translate(-10.0D, -20.0D, -30.0D));
		
		assertEquals(a, c);
		
		assertNotEquals(a, b);
		
		assertThrows(NullPointerException.class, () -> a.transform(null));
	}
	
	@Test
	public void testBoundingSphere() {
		assertThrows(NullPointerException.class, () -> BoundingVolume.boundingSphere(null, 1.0D));
	}
	
	@Test
	public void testBoundingSphereContains() {
		final BoundingVolume boundingVolume = BoundingVolume.boundingSphere(new Point3D(0.0D, 0.0D, 0.0D), 5.0D);
		
		final Point3D a = new Point3D(+9.0D, +0.0D, +0.0D);
		final Point3D b = new Point3D(-9.0D, +0.0D, +0.0D);
		final Point3D c = new Point3D(+0.0D, +9.0D, +0.0D);
		final Point3D d = new Point3D(+0.0D, -9.0D, +0.0D);
		final Point3D e = new Point3D(+0.0D, +0.0D, +9.0D);
		final Point3D f = new Point3D(+0.0D, +0.0D, -9.0D);
		
		final Point3D g = new Point3D(+5.0D, +0.0D, +0.0D);
		final Point3D h = new Point3D(-5.0D, +0.0D, +0.0D);
		final Point3D i = new Point3D(+0.0D, +5.0D, +0.0D);
		final Point3D j = new Point3D(+0.0D, -5.0D, +0.0D);
		final Point3D k = new Point3D(+0.0D, +0.0D, +5.0D);
		final Point3D l = new Point3D(+0.0D, +0.0D, -5.0D);
		final Point3D m = new Point3D(+0.0D, +0.0D, +0.0D);
		
		assertFalse(boundingVolume.contains(a));
		assertFalse(boundingVolume.contains(b));
		assertFalse(boundingVolume.contains(c));
		assertFalse(boundingVolume.contains(d));
		assertFalse(boundingVolume.contains(e));
		assertFalse(boundingVolume.contains(f));
		
		assertTrue(boundingVolume.contains(g));
		assertTrue(boundingVolume.contains(h));
		assertTrue(boundingVolume.contains(i));
		assertTrue(boundingVolume.contains(j));
		assertTrue(boundingVolume.contains(k));
		assertTrue(boundingVolume.contains(l));
		assertTrue(boundingVolume.contains(m));
		
		assertThrows(NullPointerException.class, () -> boundingVolume.contains(null));
	}
	
	@Test
	public void testBoundingSphereEquals() {
		final BoundingVolume a = BoundingVolume.boundingSphere(new Point3D(0.0D, 0.0D, 0.0D), 1.0D);
		final BoundingVolume b = BoundingVolume.boundingSphere(new Point3D(0.0D, 0.0D, 0.0D), 1.0D);
		final BoundingVolume c = BoundingVolume.boundingSphere(new Point3D(1.0D, 1.0D, 1.0D), 1.0D);
		final BoundingVolume d = BoundingVolume.boundingSphere(new Point3D(0.0D, 0.0D, 0.0D), 2.0D);
		final BoundingVolume e = null;
		
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
	public void testBoundingSphereHashCode() {
		final BoundingVolume a = BoundingVolume.boundingSphere(new Point3D(0.0D, 0.0D, 0.0D), 1.0D);
		final BoundingVolume b = BoundingVolume.boundingSphere(new Point3D(0.0D, 0.0D, 0.0D), 1.0D);
		
		assertEquals(a.hashCode(), a.hashCode());
		assertEquals(a.hashCode(), b.hashCode());
	}
	
	@Test
	public void testBoundingSphereIntersection() {
		final BoundingVolume boundingVolume = BoundingVolume.boundingSphere(new Point3D(0.0D, 0.0D, 10.0D), 5.0D);
		
		final Ray3D rayA = new Ray3D(new Point3D(0.0D, 0.0D, 0.0D), Vector3D.z());
		final Ray3D rayB = new Ray3D(new Point3D(0.0D, 0.0D, 0.0D), Vector3D.x());
		
		final double tMinimumA =  0.0D;
		final double tMinimumB =  5.0D;
		final double tMinimumC = 15.0D;
		
		final double tMaximumA = Double.MAX_VALUE;
		final double tMaximumB = 5.0D;
		
		final double expectedTA =  5.0D;
		final double expectedTB = 15.0D;
		final double expectedTC = Double.NaN;
		
		assertEquals(expectedTA, boundingVolume.intersection(rayA, tMinimumA, tMaximumA));
		assertEquals(expectedTB, boundingVolume.intersection(rayA, tMinimumB, tMaximumA));
		assertEquals(expectedTC, boundingVolume.intersection(rayA, tMinimumC, tMaximumA));
		assertEquals(expectedTC, boundingVolume.intersection(rayA, tMinimumA, tMaximumB));
		
		assertEquals(expectedTC, boundingVolume.intersection(rayB, tMinimumA, tMaximumA));
		
		assertThrows(NullPointerException.class, () -> boundingVolume.intersection(null, 0.0D, Double.MAX_VALUE));
	}
	
	@Test
	public void testBoundingSphereIntersects() {
		final BoundingVolume boundingVolume = BoundingVolume.boundingSphere(new Point3D(0.0D, 0.0D, 10.0D), 5.0D);
		
		final Ray3D rayA = new Ray3D(new Point3D(0.0D, 0.0D, 0.0D), Vector3D.z());
		final Ray3D rayB = new Ray3D(new Point3D(0.0D, 0.0D, 0.0D), Vector3D.x());
		
		final double tMinimumA =  0.0D;
		final double tMinimumB =  5.0D;
		final double tMinimumC = 15.0D;
		
		final double tMaximumA = Double.MAX_VALUE;
		final double tMaximumB = 5.0D;
		
		assertTrue(boundingVolume.intersects(rayA, tMinimumA, tMaximumA));
		assertTrue(boundingVolume.intersects(rayA, tMinimumB, tMaximumA));
		
		assertFalse(boundingVolume.intersects(rayA, tMinimumC, tMaximumA));
		assertFalse(boundingVolume.intersects(rayA, tMinimumA, tMaximumB));
		assertFalse(boundingVolume.intersects(rayB, tMinimumA, tMaximumA));
		
		assertThrows(NullPointerException.class, () -> boundingVolume.intersection(null, 0.0D, Double.MAX_VALUE));
	}
	
	@Test
	public void testBoundingSphereToString() {
		final BoundingVolume boundingVolume = BoundingVolume.boundingSphere(new Point3D(0.0D, 0.0D, 0.0D), 1.0D);
		
		assertEquals("BoundingVolume.boundingSphere(new Point3D(0.0D, 0.0D, 0.0D), 1.0D)", boundingVolume.toString());
	}
	
	@Test
	public void testBoundingSphereTransform() {
		final BoundingVolume a = BoundingVolume.boundingSphere(new Point3D(10.0D, 20.0D, 30.0D), 2.0D);
		final BoundingVolume b = a.transform(Matrix44D.translate(+10.0D, +20.0D, +30.0D));
		final BoundingVolume c = b.transform(Matrix44D.translate(-10.0D, -20.0D, -30.0D));
		
		assertEquals(a, c);
		
		assertNotEquals(a, b);
		
		assertThrows(NullPointerException.class, () -> a.transform(null));
	}
	
	@Test
	public void testInfiniteBoundingVolumeContains() {
		final BoundingVolume boundingVolume = BoundingVolume.infiniteBoundingVolume();
		
		final Point3D point = new Point3D(10.0D, 20.0D, 30.0D);
		
		assertTrue(boundingVolume.contains(point));
		
		assertThrows(NullPointerException.class, () -> boundingVolume.contains(null));
	}
	
	@Test
	public void testInfiniteBoundingVolumeEquals() {
		final BoundingVolume a = BoundingVolume.infiniteBoundingVolume();
		final BoundingVolume b = BoundingVolume.infiniteBoundingVolume();
		final BoundingVolume c = null;
		
		assertEquals(a, a);
		assertEquals(a, b);
		assertEquals(b, a);
		assertNotEquals(a, c);
		assertNotEquals(c, a);
	}
	
	@Test
	public void testInfiniteBoundingVolumeHashCode() {
		final BoundingVolume a = BoundingVolume.infiniteBoundingVolume();
		final BoundingVolume b = BoundingVolume.infiniteBoundingVolume();
		
		assertEquals(a.hashCode(), a.hashCode());
		assertEquals(a.hashCode(), b.hashCode());
	}
	
	@Test
	public void testInfiniteBoundingVolumeIntersection() {
		final Ray3D ray = new Ray3D(new Point3D(), Vector3D.z());
		
		final double tMinimum = 1.0D;
		final double tMaximum = Double.MAX_VALUE;
		
		final BoundingVolume boundingVolume = BoundingVolume.infiniteBoundingVolume();
		
		assertEquals(tMinimum, boundingVolume.intersection(ray, tMinimum, tMaximum));
		
		assertThrows(NullPointerException.class, () -> boundingVolume.intersection(null, tMinimum, tMaximum));
	}
	
	@Test
	public void testInfiniteBoundingVolumeIntersects() {
		final Ray3D ray = new Ray3D(new Point3D(), Vector3D.z());
		
		final double tMinimum = 1.0D;
		final double tMaximum = Double.MAX_VALUE;
		
		final BoundingVolume boundingVolume = BoundingVolume.infiniteBoundingVolume();
		
		assertTrue(boundingVolume.intersects(ray, tMinimum, tMaximum));
		
		assertThrows(NullPointerException.class, () -> boundingVolume.intersection(null, tMinimum, tMaximum));
	}
	
	@Test
	public void testInfiniteBoundingVolumeToString() {
		final BoundingVolume boundingVolume = BoundingVolume.infiniteBoundingVolume();
		
		assertEquals("BoundingVolume.infiniteBoundingVolume()", boundingVolume.toString());
	}
	
	@Test
	public void testInfiniteBoundingVolumeTransform() {
		final BoundingVolume a = BoundingVolume.infiniteBoundingVolume();
		final BoundingVolume b = a.transform(Matrix44D.translate(10.0D, 20.0D, 30.0D));
		
		assertTrue(a == b);
		
		assertThrows(NullPointerException.class, () -> a.transform(null));
	}
}