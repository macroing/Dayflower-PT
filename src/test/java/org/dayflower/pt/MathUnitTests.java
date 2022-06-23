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

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

@SuppressWarnings("static-method")
public final class MathUnitTests {
	public MathUnitTests() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testAbsDouble() {
		assertEquals(1.0D, Math.abs(-1.0D));
		assertEquals(1.0D, Math.abs(+1.0D));
	}
	
	@Test
	public void testAbsInt() {
		assertEquals(1, Math.abs(-1));
		assertEquals(1, Math.abs(+1));
	}
	
	@Test
	public void testAcos() {
		assertEquals(java.lang.Math.acos(0.5D), Math.acos(0.5D));
	}
	
	@Test
	public void testAsin() {
		assertEquals(java.lang.Math.asin(0.5D), Math.asin(0.5D));
	}
	
	@Test
	public void testAtan() {
		assertEquals(java.lang.Math.atan(0.5D), Math.atan(0.5D));
	}
	
	@Test
	public void testAtan2() {
		assertEquals(java.lang.Math.atan2(0.5D, 0.5D), Math.atan2(0.5D, 0.5D));
	}
	
	@Test
	public void testCeil() {
		assertEquals(1.0D, Math.ceil(0.5D));
	}
	
	@Test
	public void testConstants() {
		assertEquals(1.0e-4D, Math.EPSILON);
		assertEquals(+Double.MAX_VALUE, Math.MAX_VALUE);
		assertEquals(-Double.MAX_VALUE, Math.MIN_VALUE);
		assertEquals(java.lang.Math.nextDown(1.0D), Math.NEXT_DOWN_1_1);
		assertEquals(java.lang.Math.nextDown(java.lang.Math.nextDown(1.0D)), Math.NEXT_DOWN_1_2);
		assertEquals(java.lang.Math.nextDown(java.lang.Math.nextDown(java.lang.Math.nextDown(1.0D))), Math.NEXT_DOWN_1_3);
		assertEquals(java.lang.Math.nextUp(1.0D), Math.NEXT_UP_1_1);
		assertEquals(java.lang.Math.nextUp(java.lang.Math.nextUp(1.0D)), Math.NEXT_UP_1_2);
		assertEquals(java.lang.Math.nextUp(java.lang.Math.nextUp(java.lang.Math.nextUp(1.0D))), Math.NEXT_UP_1_3);
		assertEquals(Double.NaN, Math.NaN);
		assertEquals(java.lang.Math.PI, Math.PI);
		assertEquals(Double.POSITIVE_INFINITY, Math.POSITIVE_INFINITY);
	}
	
	@Test
	public void testCos() {
		assertEquals(java.lang.Math.cos(0.5D), Math.cos(0.5D));
	}
	
	@Test
	public void testEquals() {
		assertTrue(Math.equals(10.0D, 10.0D));
		
		assertFalse(Math.equals(10.0D, 20.0D));
	}
	
	@Test
	public void testFloor() {
		assertEquals(0.0D, Math.floor(0.5D));
	}
	
	@Test
	public void testFractionalPartDouble() {
		assertEquals(0.5D, Math.fractionalPart(-1.5D));
		assertEquals(0.8D, Math.fractionalPart(-1.2D));
		assertEquals(0.5D, Math.fractionalPart(+1.5D));
		assertEquals(0.8D, Math.fractionalPart(+1.8D));
	}
	
	@Test
	public void testFractionalPartDoubleBoolean() {
		assertEquals(0.5D, Math.fractionalPart(-1.5D, false));
		assertEquals(0.5D, Math.fractionalPart(-1.5D, true));
		assertEquals(0.8D, Math.fractionalPart(-1.2D, false));
		assertEquals(0.8D, Math.fractionalPart(-1.8D, true));
		assertEquals(0.5D, Math.fractionalPart(+1.5D, false));
		assertEquals(0.5D, Math.fractionalPart(+1.5D, true));
		assertEquals(0.8D, Math.fractionalPart(+1.8D, false));
	}
	
	@Test
	public void testGetOrAdd() {
		assertEquals(1.0D, Math.getOrAdd(+1.0D, 0.0D, 2.0D));
		assertEquals(1.0D, Math.getOrAdd(-1.0D, 0.0D, 2.0D));
	}
	
	@Test
	public void testIsInfinite() {
		assertTrue(Math.isInfinite(Double.NEGATIVE_INFINITY));
		assertTrue(Math.isInfinite(Double.POSITIVE_INFINITY));
		
		assertFalse(Math.isInfinite(10.0D));
		assertFalse(Math.isInfinite(Double.NaN));
	}
	
	@Test
	public void testIsNaN() {
		assertTrue(Math.isNaN(Double.NaN));
		
		assertFalse(Math.isNaN(10.0D));
		assertFalse(Math.isNaN(Double.NEGATIVE_INFINITY));
		assertFalse(Math.isNaN(Double.POSITIVE_INFINITY));
	}
	
	@Test
	public void testIsZero() {
		assertTrue(Math.isZero(-0.0D));
		assertTrue(Math.isZero(+0.0D));
		
		assertFalse(Math.isZero(-1.0D));
		assertFalse(Math.isZero(+1.0D));
		assertFalse(Math.isZero(Double.NaN));
		assertFalse(Math.isZero(Double.NEGATIVE_INFINITY));
		assertFalse(Math.isZero(Double.POSITIVE_INFINITY));
	}
	
	@Test
	public void testLerp() {
		assertEquals(0.0D, Math.lerp(0.0D, 1.0D, 0.0D));
		assertEquals(0.5D, Math.lerp(0.0D, 1.0D, 0.5D));
		assertEquals(1.0D, Math.lerp(0.0D, 1.0D, 1.0D));
	}
	
	@Test
	public void testLog() {
		assertEquals(java.lang.Math.log(0.5D), Math.log(0.5D));
	}
	
	@Test
	public void testMaxDoubleDouble() {
		assertEquals(2.0D, Math.max(1.0D, 2.0D));
		assertEquals(2.0D, Math.max(2.0D, 1.0D));
	}
	
	@Test
	public void testMaxDoubleDoubleDouble() {
		assertEquals(3.0D, Math.max(1.0D, 2.0D, 3.0D));
		assertEquals(3.0D, Math.max(2.0D, 3.0D, 1.0D));
		assertEquals(3.0D, Math.max(3.0D, 1.0D, 2.0D));
	}
	
	@Test
	public void testMaxIntInt() {
		assertEquals(2, Math.max(1, 2));
		assertEquals(2, Math.max(2, 1));
	}
	
	@Test
	public void testMinDoubleDouble() {
		assertEquals(1.0D, Math.min(1.0D, 2.0D));
		assertEquals(1.0D, Math.min(2.0D, 1.0D));
	}
	
	@Test
	public void testMinDoubleDoubleDouble() {
		assertEquals(1.0D, Math.min(1.0D, 2.0D, 3.0D));
		assertEquals(1.0D, Math.min(2.0D, 3.0D, 1.0D));
		assertEquals(1.0D, Math.min(3.0D, 1.0D, 2.0D));
	}
	
	@Test
	public void testMinIntInt() {
		assertEquals(1, Math.min(1, 2));
		assertEquals(1, Math.min(2, 1));
	}
	
	@Test
	public void testNextDown() {
		assertEquals(java.lang.Math.nextDown(2.0D), Math.nextDown(2.0D));
	}
	
	@Test
	public void testNextUp() {
		assertEquals(java.lang.Math.nextUp(2.0D), Math.nextUp(2.0D));
	}
	
	@Test
	public void testNormalize() {
		assertEquals(-1.0D, Math.normalize(  0.0D, 100.0D, 200.0D));
		assertEquals(+0.0D, Math.normalize(100.0D, 100.0D, 200.0D));
		assertEquals(+0.5D, Math.normalize(150.0D, 100.0D, 200.0D));
		assertEquals(+1.0D, Math.normalize(200.0D, 100.0D, 200.0D));
		assertEquals(+2.0D, Math.normalize(300.0D, 100.0D, 200.0D));
	}
	
	@Test
	public void testPositiveModuloDoubleDouble() {
		assertEquals(-0.0D, Math.positiveModulo(-2.0D, -1.0D));
		assertEquals(-2.0D, Math.positiveModulo(-2.0D, -3.0D));
		
		assertEquals(+0.0D, Math.positiveModulo(-2.0D, +1.0D));
		
		assertEquals(+0.0D, Math.positiveModulo(+2.0D, -1.0D));
		
		assertEquals(+0.0D, Math.positiveModulo(+2.0D, +1.0D));
		assertEquals(+2.0D, Math.positiveModulo(+2.0D, +3.0D));
	}
	
	@Test
	public void testPositiveModuloIntInt() {
		assertEquals(-0, Math.positiveModulo(-2, -1));
		assertEquals(-2, Math.positiveModulo(-2, -3));
		
		assertEquals(+0, Math.positiveModulo(-2, +1));
		
		assertEquals(+0, Math.positiveModulo(+2, -1));
		
		assertEquals(+0, Math.positiveModulo(+2, +1));
		assertEquals(+2, Math.positiveModulo(+2, +3));
	}
	
	@Test
	public void testPow() {
		assertEquals(java.lang.Math.pow(2.0D, 2.0D), Math.pow(2.0D, 2.0D));
	}
	
	@Test
	public void testPow2() {
		assertEquals(4.0D, Math.pow2(2.0D));
	}
	
	@Test
	public void testPow5() {
		assertEquals(32.0D, Math.pow5(2.0D));
	}
	
	@Test
	public void testRandom() {
		final double value = Math.random();
		
		assertTrue(value >= 0.0D && value < 1.0D);
	}
	
	@Test
	public void testSaturateDouble() {
		assertEquals(0.0D, Math.saturate(0.0D));
		assertEquals(1.0D, Math.saturate(1.0D));
		
		assertEquals(0.0D, Math.saturate(-1.0D));
		
		assertEquals(1.0D, Math.saturate(2.0D));
	}
	
	@Test
	public void testSaturateDoubleDoubleDouble() {
		assertEquals(0.0D, Math.saturate(0.0D, 0.0D, 1.0D));
		assertEquals(0.0D, Math.saturate(0.0D, 1.0D, 0.0D));
		assertEquals(1.0D, Math.saturate(1.0D, 0.0D, 1.0D));
		assertEquals(1.0D, Math.saturate(1.0D, 1.0D, 0.0D));
		
		assertEquals(0.0D, Math.saturate(-1.0D, 0.0D, 1.0D));
		assertEquals(0.0D, Math.saturate(-1.0D, 1.0D, 0.0D));
		
		assertEquals(1.0D, Math.saturate(2.0D, 0.0D, 1.0D));
		assertEquals(1.0D, Math.saturate(2.0D, 1.0D, 0.0D));
	}
	
	@Test
	public void testSaturateInt() {
		assertEquals(  0, Math.saturate(  0));
		assertEquals(255, Math.saturate(255));
		
		assertEquals(0, Math.saturate(-1));
		
		assertEquals(255, Math.saturate(256));
	}
	
	@Test
	public void testSaturateIntIntInt() {
		assertEquals(  0, Math.saturate(  0,   0, 255));
		assertEquals(  0, Math.saturate(  0, 255,   0));
		assertEquals(255, Math.saturate(255,   0, 255));
		assertEquals(255, Math.saturate(255, 255,   0));
		
		assertEquals(0, Math.saturate(-1,   0, 255));
		assertEquals(0, Math.saturate(-1, 255,   0));
		
		assertEquals(255, Math.saturate(256,   0, 255));
		assertEquals(255, Math.saturate(256, 255,   0));
	}
	
	@Test
	public void testSin() {
		assertEquals(java.lang.Math.sin(0.5D), Math.sin(0.5D));
	}
	
	@Test
	public void testSolveQuadraticSystem() {
		assertArrayEquals(new double[] {-2.0D, -0.3333333333333333D}, Math.solveQuadraticSystem(3.0D, +7.0D, 2.0D));
		assertArrayEquals(new double[] {+0.3333333333333333D, +2.0D}, Math.solveQuadraticSystem(3.0D, -7.0D, 2.0D));
		assertArrayEquals(new double[] {-1.0D, -1.0D}, Math.solveQuadraticSystem(1.0D, 2.0D, 1.0D));
		assertArrayEquals(new double[] {Double.NaN, Double.NaN}, Math.solveQuadraticSystem(0.25D, 0.0D, 1.0D));
	}
	
	@Test
	public void testSqrt() {
		assertEquals(java.lang.Math.sqrt(4.0D), Math.sqrt(4.0D));
	}
	
	@Test
	public void testTan() {
		assertEquals(java.lang.Math.tan(0.5D), Math.tan(0.5D));
	}
	
	@Test
	public void testToDegrees() {
		assertEquals(java.lang.Math.toDegrees(0.5D), Math.toDegrees(0.5D));
	}
	
	@Test
	public void testToInt() {
		assertEquals(+1, Math.toInt(+1.5D));
		assertEquals(-1, Math.toInt(-1.5D));
	}
	
	@Test
	public void testToRadians() {
		assertEquals(java.lang.Math.toRadians(0.5D), Math.toRadians(0.5D));
	}
}