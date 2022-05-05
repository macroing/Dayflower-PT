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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.image.BufferedImage;

import org.junit.jupiter.api.Test;

@SuppressWarnings("static-method")
public final class Color3DUnitTests {
	public Color3DUnitTests() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testAdd() {
		final Color3D a = new Color3D(0.0D, 0.5D, 1.0D);
		final Color3D b = new Color3D(1.0D, 0.5D, 0.0D);
		final Color3D c = Color3D.add(a, b);
		
		assertEquals(1.0D, c.r);
		assertEquals(1.0D, c.g);
		assertEquals(1.0D, c.b);
		
		assertThrows(NullPointerException.class, () -> Color3D.add(a, null));
		assertThrows(NullPointerException.class, () -> Color3D.add(null, b));
	}
	
	@Test
	public void testAverage() {
		assertEquals(0.0D, new Color3D(0.0D, 0.0D, 0.0D).average());
		assertEquals(0.5D, new Color3D(0.0D, 0.5D, 1.0D).average());
		assertEquals(1.0D, new Color3D(1.0D, 1.0D, 1.0D).average());
	}
	
	@Test
	public void testBlendColor3DColor3D() {
		final Color3D a = new Color3D(0.0D, 0.0D, 0.0D);
		final Color3D b = new Color3D(1.0D, 1.0D, 1.0D);
		final Color3D c = Color3D.blend(a, b);
		
		assertEquals(0.50D, c.r);
		assertEquals(0.50D, c.g);
		assertEquals(0.50D, c.b);
		
		assertThrows(NullPointerException.class, () -> Color3D.blend(a, null));
		assertThrows(NullPointerException.class, () -> Color3D.blend(null, b));
	}
	
	@Test
	public void testBlendColor3DColor3DDouble() {
		final Color3D a = new Color3D(0.0D, 0.0D, 0.0D);
		final Color3D b = new Color3D(1.0D, 1.0D, 1.0D);
		final Color3D c = Color3D.blend(a, b, 0.50D);
		
		assertEquals(0.50D, c.r);
		assertEquals(0.50D, c.g);
		assertEquals(0.50D, c.b);
		
		assertThrows(NullPointerException.class, () -> Color3D.blend(a, null, 0.5D));
		assertThrows(NullPointerException.class, () -> Color3D.blend(null, b, 0.5D));
	}
	
	@Test
	public void testBlendColor3DColor3DDoubleDoubleDouble() {
		final Color3D a = new Color3D(0.0D, 0.0D, 0.0D);
		final Color3D b = new Color3D(1.0D, 1.0D, 1.0D);
		final Color3D c = Color3D.blend(a, b, 0.50D, 0.50D, 0.50D);
		final Color3D d = Color3D.blend(a, b, 0.25D, 0.50D, 0.75D);
		
		assertEquals(0.50D, c.r);
		assertEquals(0.50D, c.g);
		assertEquals(0.50D, c.b);
		
		assertEquals(0.25D, d.r);
		assertEquals(0.50D, d.g);
		assertEquals(0.75D, d.b);
		
		assertThrows(NullPointerException.class, () -> Color3D.blend(a, null, 0.5D, 0.5D, 0.5D));
		assertThrows(NullPointerException.class, () -> Color3D.blend(null, b, 0.5D, 0.5D, 0.5D));
	}
	
	@Test
	public void testConstants() {
		assertEquals(0.1428431083584272D, Color3D.AU_ETA.r);
		assertEquals(0.3741312033192202D, Color3D.AU_ETA.g);
		assertEquals(1.4392239236981954D, Color3D.AU_ETA.b);
		
		assertEquals(3.9753607696872020D, Color3D.AU_K.r);
		assertEquals(2.3805848390290590D, Color3D.AU_K.g);
		assertEquals(1.5995662411380493D, Color3D.AU_K.b);
		
		assertEquals(0.0D, Color3D.BLACK.r);
		assertEquals(0.0D, Color3D.BLACK.g);
		assertEquals(0.0D, Color3D.BLACK.b);
		
		assertEquals(0.0D, Color3D.GREEN.r);
		assertEquals(1.0D, Color3D.GREEN.g);
		assertEquals(0.0D, Color3D.GREEN.b);
		
		assertEquals(1.0D, Color3D.WHITE.r);
		assertEquals(1.0D, Color3D.WHITE.g);
		assertEquals(1.0D, Color3D.WHITE.b);
	}
	
	@Test
	public void testConstructor() {
		final Color3D c = new Color3D();
		
		assertEquals(0.0D, c.r);
		assertEquals(0.0D, c.g);
		assertEquals(0.0D, c.b);
	}
	
	@Test
	public void testConstructorDouble() {
		final Color3D c = new Color3D(1.0D);
		
		assertEquals(1.0D, c.r);
		assertEquals(1.0D, c.g);
		assertEquals(1.0D, c.b);
	}
	
	@Test
	public void testConstructorDoubleDoubleDouble() {
		final Color3D c = new Color3D(0.0D, 0.5D, 1.0D);
		
		assertEquals(0.0D, c.r);
		assertEquals(0.5D, c.g);
		assertEquals(1.0D, c.b);
	}
	
	@Test
	public void testConstructorIntIntInt() {
		final Color3D c = new Color3D(255, 255, 255);
		
		assertEquals(1.0D, c.r);
		assertEquals(1.0D, c.g);
		assertEquals(1.0D, c.b);
	}
	
	@Test
	public void testDivideColor3DDouble() {
		final Color3D a = new Color3D(2.0D, 4.0D, 8.0D);
		final Color3D b = Color3D.divide(a, 2.0D);
		
		assertEquals(1.0D, b.r);
		assertEquals(2.0D, b.g);
		assertEquals(4.0D, b.b);
		
		assertThrows(NullPointerException.class, () -> Color3D.divide(null, 2.0D));
	}
	
	@Test
	public void testEqualsColor3D() {
		final Color3D a = new Color3D(0.0D, 0.5D, 1.0D);
		final Color3D b = new Color3D(0.0D, 0.5D, 1.0D);
		final Color3D c = new Color3D(0.0D, 0.5D, 1.5D);
		final Color3D d = new Color3D(0.0D, 1.0D, 1.0D);
		final Color3D e = new Color3D(0.5D, 0.5D, 1.0D);
		final Color3D f = null;
		
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
	public void testEqualsObject() {
		final Color3D a = new Color3D(0.0D, 0.5D, 1.0D);
		final Color3D b = new Color3D(0.0D, 0.5D, 1.0D);
		final Color3D c = new Color3D(0.0D, 0.5D, 1.5D);
		final Color3D d = new Color3D(0.0D, 1.0D, 1.0D);
		final Color3D e = new Color3D(0.5D, 0.5D, 1.0D);
		final Color3D f = null;
		
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
	public void testHashCode() {
		final Color3D a = new Color3D(0.0D, 0.5D, 1.0D);
		final Color3D b = new Color3D(0.0D, 0.5D, 1.0D);
		
		assertEquals(a.hashCode(), b.hashCode());
	}
	
	@Test
	public void testIsBlack() {
		assertTrue(new Color3D(0.0D, 0.0D, 0.0D).isBlack());
		
		assertFalse(new Color3D(1.0D, 0.0D, 0.0D).isBlack());
		assertFalse(new Color3D(0.0D, 1.0D, 0.0D).isBlack());
		assertFalse(new Color3D(0.0D, 0.0D, 1.0D).isBlack());
	}
	
	@Test
	public void testMax() {
		assertEquals(1.0D, new Color3D(1.0D, 0.0D, 0.0D).max());
		assertEquals(1.0D, new Color3D(0.0D, 1.0D, 0.0D).max());
		assertEquals(1.0D, new Color3D(0.0D, 0.0D, 1.0D).max());
	}
	
	@Test
	public void testMultiplyColor3DColor3D() {
		final Color3D a = new Color3D(1.0D, 2.0D, 4.0D);
		final Color3D b = new Color3D(1.0D, 1.5D, 2.0D);
		final Color3D c = Color3D.multiply(a, b);
		
		assertEquals(1.0D, c.r);
		assertEquals(3.0D, c.g);
		assertEquals(8.0D, c.b);
		
		assertThrows(NullPointerException.class, () -> Color3D.multiply(a, null));
		assertThrows(NullPointerException.class, () -> Color3D.multiply(null, b));
	}
	
	@Test
	public void testMultiplyColor3DDouble() {
		final Color3D a = new Color3D(1.0D, 2.0D, 4.0D);
		final Color3D b = Color3D.multiply(a, 2.0D);
		
		assertEquals(2.0D, b.r);
		assertEquals(4.0D, b.g);
		assertEquals(8.0D, b.b);
		
		assertThrows(NullPointerException.class, () -> Color3D.multiply(null, 2.0D));
	}
	
	@Test
	public void testRedoGammaCorrection() {
		final Color3D a = new Color3D(0.0D, 0.5D, 1.0D);
		final Color3D b = Color3D.redoGammaCorrection(a);
		
		assertEquals(0.0000000000000000D, b.r);
		assertEquals(0.7353568526581377D, b.g);
		assertEquals(1.0000000000000000D, b.b);
		
		assertThrows(NullPointerException.class, () -> Color3D.redoGammaCorrection(null));
	}
	
	@Test
	public void testSaturateColor3D() {
		final Color3D a = new Color3D(+0.0D, +0.5D, +1.0D);
		final Color3D b = new Color3D(-1.0D, +0.5D, +2.0D);
		final Color3D c = Color3D.saturate(a);
		final Color3D d = Color3D.saturate(b);
		
		assertEquals(0.0D, c.r);
		assertEquals(0.5D, c.g);
		assertEquals(1.0D, c.b);
		
		assertEquals(0.0D, d.r);
		assertEquals(0.5D, d.g);
		assertEquals(1.0D, d.b);
		
		assertThrows(NullPointerException.class, () -> Color3D.saturate(null));
	}
	
	@Test
	public void testSaturateColor3DDoubleDouble() {
		final Color3D a = new Color3D(+0.0D, +0.5D, +1.0D);
		final Color3D b = new Color3D(-1.0D, +0.5D, +2.0D);
		final Color3D c = Color3D.saturate(a, 0.0D, 1.0D);
		final Color3D d = Color3D.saturate(a, 1.0D, 0.0D);
		final Color3D e = Color3D.saturate(b, 0.0D, 1.0D);
		final Color3D f = Color3D.saturate(b, 1.0D, 0.0D);
		
		assertEquals(0.0D, c.r);
		assertEquals(0.5D, c.g);
		assertEquals(1.0D, c.b);
		
		assertEquals(0.0D, d.r);
		assertEquals(0.5D, d.g);
		assertEquals(1.0D, d.b);
		
		assertEquals(0.0D, e.r);
		assertEquals(0.5D, e.g);
		assertEquals(1.0D, e.b);
		
		assertEquals(0.0D, f.r);
		assertEquals(0.5D, f.g);
		assertEquals(1.0D, f.b);
		
		assertThrows(NullPointerException.class, () -> Color3D.saturate(null, 0.0D, 1.0D));
	}
	
	@Test
	public void testSubtract() {
		final Color3D a = new Color3D(1.0D, 1.0D, 1.0D);
		final Color3D b = new Color3D(1.0D, 0.5D, 0.0D);
		final Color3D c = Color3D.subtract(a, b);
		
		assertEquals(0.0D, c.r);
		assertEquals(0.5D, c.g);
		assertEquals(1.0D, c.b);
		
		assertThrows(NullPointerException.class, () -> Color3D.subtract(a, null));
		assertThrows(NullPointerException.class, () -> Color3D.subtract(null, b));
	}
	
	@Test
	public void testToARGB() {
		final int a = new Color3D(1.0D, 0.0D, 0.0D).toARGB();
		final int b = new Color3D(2.0D, 0.0D, 0.0D).toARGB();
		final int c = new Color3D(0.0D, 1.0D, 0.0D).toARGB();
		final int d = new Color3D(0.0D, 2.0D, 0.0D).toARGB();
		final int e = new Color3D(0.0D, 0.0D, 1.0D).toARGB();
		final int f = new Color3D(0.0D, 0.0D, 2.0D).toARGB();
		
		assertEquals(a, b);
		assertEquals(c, d);
		assertEquals(e, f);
		
		assertEquals(255, (a >> 24) & 0xFF);
		assertEquals(255, (a >> 16) & 0xFF);
		assertEquals(  0, (a >>  8) & 0xFF);
		assertEquals(  0, (a >>  0) & 0xFF);
		
		assertEquals(255, (c >> 24) & 0xFF);
		assertEquals(  0, (c >> 16) & 0xFF);
		assertEquals(255, (c >>  8) & 0xFF);
		assertEquals(  0, (c >>  0) & 0xFF);
		
		assertEquals(255, (e >> 24) & 0xFF);
		assertEquals(  0, (e >> 16) & 0xFF);
		assertEquals(  0, (e >>  8) & 0xFF);
		assertEquals(255, (e >>  0) & 0xFF);
	}
	
	@Test
	public void testToArray() {
		final int r = ((255 & 0xFF) << 24) | ((255 & 0xFF) << 16) | ((  0 & 0xFF) << 8) | ((  0 & 0xFF));
		final int g = ((255 & 0xFF) << 24) | ((  0 & 0xFF) << 16) | ((255 & 0xFF) << 8) | ((  0 & 0xFF));
		final int b = ((255 & 0xFF) << 24) | ((  0 & 0xFF) << 16) | ((  0 & 0xFF) << 8) | ((255 & 0xFF));
		
		final
		BufferedImage bufferedImage = new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB);
		bufferedImage.setRGB(0, 0, r);
		bufferedImage.setRGB(1, 0, r);
		bufferedImage.setRGB(2, 0, r);
		bufferedImage.setRGB(0, 1, g);
		bufferedImage.setRGB(1, 1, g);
		bufferedImage.setRGB(2, 1, g);
		bufferedImage.setRGB(0, 2, b);
		bufferedImage.setRGB(1, 2, b);
		bufferedImage.setRGB(2, 2, b);
		
		final Color3D[] array = Color3D.toArray(bufferedImage);
		
		assertNotNull(array);
		
		assertEquals(9, array.length);
		
		assertEquals(new Color3D(1.0D, 0.0D, 0.0D), array[0]);
		assertEquals(new Color3D(1.0D, 0.0D, 0.0D), array[1]);
		assertEquals(new Color3D(1.0D, 0.0D, 0.0D), array[2]);
		assertEquals(new Color3D(0.0D, 1.0D, 0.0D), array[3]);
		assertEquals(new Color3D(0.0D, 1.0D, 0.0D), array[4]);
		assertEquals(new Color3D(0.0D, 1.0D, 0.0D), array[5]);
		assertEquals(new Color3D(0.0D, 0.0D, 1.0D), array[6]);
		assertEquals(new Color3D(0.0D, 0.0D, 1.0D), array[7]);
		assertEquals(new Color3D(0.0D, 0.0D, 1.0D), array[8]);
		
		assertThrows(NullPointerException.class, () -> Color3D.toArray(null));
	}
	
	@Test
	public void testToString() {
		final Color3D c = new Color3D(0.0D, 0.5D, 1.0D);
		
		assertEquals("new Color3D(0.0D, 0.5D, 1.0D)", c.toString());
	}
	
	@Test
	public void testUndoGammaCorrection() {
		final Color3D a = new Color3D(0.0D, 0.7353568526581377D, 1.0D);
		final Color3D b = Color3D.undoGammaCorrection(a);
		
		assertEquals(0.0D, b.r);
		assertEquals(0.5D, b.g);
		assertEquals(1.0D, b.b);
		
		assertThrows(NullPointerException.class, () -> Color3D.undoGammaCorrection(null));
	}
}