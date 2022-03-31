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

import java.util.concurrent.ThreadLocalRandom;

public final class Math {
	public static final double EPSILON = 1.0e-4D;
	public static final double MAX_VALUE = Double.MAX_VALUE;
	public static final double NaN = Double.NaN;
	public static final double POSITIVE_INFINITY = Double.POSITIVE_INFINITY;
	public static final double PI = java.lang.Math.PI;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private Math() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static boolean equal(final double a, final double b) {
		return Double.compare(a, b) == 0;
	}
	
	public static boolean isInfinite(final double value) {
		return Double.isInfinite(value);
	}
	
	public static boolean isNaN(final double value) {
		return Double.isNaN(value);
	}
	
	public static boolean isZero(final double value) {
		return Double.compare(value, +0.0D) == 0 || Double.compare(value, -0.0D) == 0;
	}
	
	public static double abs(final double value) {
		return java.lang.Math.abs(value);
	}
	
	public static double acos(final double a) {
		return java.lang.Math.acos(a);
	}
	
	public static double atan(final double a) {
		return java.lang.Math.atan(a);
	}
	
	public static double atan2(final double y, final double x) {
		return java.lang.Math.atan2(y, x);
	}
	
	public static double ceil(final double value) {
		return java.lang.Math.ceil(value);
	}
	
	public static double cos(final double a) {
		return java.lang.Math.cos(a);
	}
	
	public static double floor(final double value) {
		return java.lang.Math.floor(value);
	}
	
	public static double fractionalPart(final double value) {
		return fractionalPart(value, false);
	}
	
	public static double fractionalPart(final double value, final boolean isUsingCeilOnNegativeValue) {
		return value < 0.0D && isUsingCeilOnNegativeValue ? ceil(value) - value : value - floor(value);
	}
	
	public static double getOrAdd(final double value, final double valueThreshold, final double valueAdd) {
		return value >= valueThreshold ? value : value + valueAdd;
	}
	
	public static double lerp(final double a, final double b, final double t) {
		return (1.0D - t) * a + t * b;
	}
	
	public static double log(final double a) {
		return java.lang.Math.log(a);
	}
	
	public static double max(final double a, final double b) {
		return java.lang.Math.max(a, b);
	}
	
	public static double max(final double a, final double b, final double c) {
		return max(max(a, b), c);
	}
	
	public static double min(final double a, final double b) {
		return java.lang.Math.min(a, b);
	}
	
	public static double pow(final double base, final double exponent) {
		return java.lang.Math.pow(base, exponent);
	}
	
	public static double pow2(final double base) {
		return base * base;
	}
	
	public static double pow5(final double base) {
		return base * base * base * base * base;
	}
	
	public static double random() {
		return ThreadLocalRandom.current().nextDouble();
	}
	
	public static double remainder(final double x, final double y) {
		return x - toInt(x / y) * y;
	}
	
	public static double saturate(final double value) {
		return saturate(value, 0.0D, 1.0D);
	}
	
	public static double saturate(final double value, final double valueIntervalA, final double valueIntervalB) {
		final double valueMaximum = max(valueIntervalA, valueIntervalB);
		final double valueMinimum = min(valueIntervalA, valueIntervalB);
		
		return value < valueMinimum ? valueMinimum : value > valueMaximum ? valueMaximum : value;
	}
	
	public static double sin(final double a) {
		return java.lang.Math.sin(a);
	}
	
	public static double sqrt(final double value) {
		return java.lang.Math.sqrt(value);
	}
	
	public static double tan(final double a) {
		return java.lang.Math.tan(a);
	}
	
	public static double toDegrees(final double angleRadians) {
		return java.lang.Math.toDegrees(angleRadians);
	}
	
	public static double toRadians(final double angleDegrees) {
		return java.lang.Math.toRadians(angleDegrees);
	}
	
	public static double[] solveQuadraticSystem(final double a, final double b, final double c) {
		final double[] result = new double[] {NaN, NaN};
		
		final double discriminantSquared = b * b - 4.0D * a * c;
		
		if(isZero(discriminantSquared)) {
			final double q = -0.5D * b / a;
			
			final double result0 = q;
			final double result1 = q;
			
			result[0] = result0;
			result[1] = result1;
		} else if(discriminantSquared > 0.0D) {
			final double discriminant = sqrt(discriminantSquared);
			
			final double q = -0.5D * (b > 0.0D ? b + discriminant : b - discriminant);
			
			final double result0 = q / a;
			final double result1 = c / q;
			
			result[0] = min(result0, result1);
			result[1] = max(result0, result1);
		}
		
		return result;
	}
	
	public static int max(final int a, final int b) {
		return java.lang.Math.max(a, b);
	}
	
	public static int min(final int a, final int b) {
		return java.lang.Math.min(a, b);
	}
	
	public static int toInt(final double value) {
		return (int)(value);
	}
}