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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.awt.image.BufferedImage;

import org.junit.jupiter.api.Test;

@SuppressWarnings("static-method")
public final class UtilitiesUnitTests {
	public UtilitiesUnitTests() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testGetCompatibleBufferedImageBufferedImage() {
		final BufferedImage bufferedImageA = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
		final BufferedImage bufferedImageB = Utilities.getCompatibleBufferedImage(bufferedImageA);
		
		assertNotNull(bufferedImageB);
		
		assertSame(bufferedImageA, bufferedImageB);
	}
	
	@Test
	public void testGetCompatibleBufferedImageBufferedImageInt() {
		final BufferedImage bufferedImageA = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
		final BufferedImage bufferedImageB = Utilities.getCompatibleBufferedImage(bufferedImageA, BufferedImage.TYPE_INT_RGB);
		final BufferedImage bufferedImageC = Utilities.getCompatibleBufferedImage(bufferedImageA, BufferedImage.TYPE_INT_BGR);
		
		assertNotNull(bufferedImageB);
		assertNotNull(bufferedImageC);
		
		assertSame(bufferedImageA, bufferedImageB);
		
		assertEquals(100, bufferedImageC.getWidth());
		assertEquals(100, bufferedImageC.getHeight());
		assertEquals(BufferedImage.TYPE_INT_BGR, bufferedImageC.getType());
		
		assertThrows(NullPointerException.class, () -> Utilities.getCompatibleBufferedImage(null, BufferedImage.TYPE_INT_RGB));
		assertThrows(IllegalArgumentException.class, () -> Utilities.getCompatibleBufferedImage(bufferedImageA, -1));
	}
	
	@Test
	public void testRequireNonNullArray() {
		assertArrayEquals(new String[] {"A", "B", "C"}, Utilities.requireNonNullArray(new String[] {"A",  "B", "C"}, "array"));
		
		assertThrows(NullPointerException.class, () -> Utilities.requireNonNullArray(new String[0], null));
		assertThrows(NullPointerException.class, () -> Utilities.requireNonNullArray(null, "array"));
		assertThrows(NullPointerException.class, () -> Utilities.requireNonNullArray(new String[] {"A", null, "C"}, "array"));
	}
	
	@Test
	public void testRequireRange() {
		assertEquals(10, Utilities.requireRange(10, 10, 20, "value"));
		assertEquals(10, Utilities.requireRange(10, 20, 10, "value"));
		assertEquals(20, Utilities.requireRange(20, 10, 20, "value"));
		assertEquals(20, Utilities.requireRange(20, 20, 10, "value"));
		
		assertThrows(IllegalArgumentException.class, () -> Utilities.requireRange(10, 20, 30, "value"));
		assertThrows(IllegalArgumentException.class, () -> Utilities.requireRange(10, 30, 20, "value"));
		assertThrows(IllegalArgumentException.class, () -> Utilities.requireRange(40, 20, 30, "value"));
		assertThrows(IllegalArgumentException.class, () -> Utilities.requireRange(40, 30, 20, "value"));
		
		assertThrows(NullPointerException.class, () -> Utilities.requireRange(10, 10, 20, null));
	}
	
	@Test
	public void testToNonScientificNotationJava() {
		assertEquals("Double.NaN", Utilities.toNonScientificNotationJava(Double.NaN));
		assertEquals("Double.NEGATIVE_INFINITY", Utilities.toNonScientificNotationJava(Double.NEGATIVE_INFINITY));
		assertEquals("Double.POSITIVE_INFINITY", Utilities.toNonScientificNotationJava(Double.POSITIVE_INFINITY));
		assertEquals("-1.0D", Utilities.toNonScientificNotationJava(-1.0D));
		assertEquals("1.0D", Utilities.toNonScientificNotationJava(1.0D));
	}
}