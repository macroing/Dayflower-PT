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

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.imageio.ImageIO;

public final class Image {
	private final Color3D[] colors;
	private final int resolutionX;
	private final int resolutionY;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Image(final BufferedImage bufferedImage) {
		this.resolutionX = bufferedImage.getWidth();
		this.resolutionY = bufferedImage.getHeight();
		this.colors = Color3D.toArray(bufferedImage);
	}
	
	public Image(final int resolutionX, final int resolutionY) {
		this.resolutionX = resolutionX;
		this.resolutionY = resolutionY;
		this.colors = IntStream.range(0, resolutionX * resolutionY).mapToObj(index -> Color3D.BLACK).toArray(Color3D[]::new);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Color3D getColor(final double x, final double y) {
		return getColor(x, y, false);
	}
	
	public Color3D getColor(final double x, final double y, final boolean isWrappingAround) {
		final int minimumX = Math.toInt(Math.floor(x));
		final int maximumX = Math.toInt(Math.ceil(x));
		
		final int minimumY = Math.toInt(Math.floor(y));
		final int maximumY = Math.toInt(Math.ceil(y));
		
		if(minimumX == maximumX && minimumY == maximumY) {
			return getColor(minimumX, minimumY, isWrappingAround);
		}
		
		final Color3D color00 = getColor(minimumX, minimumY, isWrappingAround);
		final Color3D color01 = getColor(maximumX, minimumY, isWrappingAround);
		final Color3D color10 = getColor(minimumX, maximumY, isWrappingAround);
		final Color3D color11 = getColor(maximumX, maximumY, isWrappingAround);
		
		final double xFactor = x - minimumX;
		final double yFactor = y - minimumY;
		
		final Color3D color = Color3D.blend(Color3D.blend(color00, color01, xFactor), Color3D.blend(color10, color11, xFactor), yFactor);
		
		return color;
	}
	
	public Color3D getColor(final int index) {
		return getColor(index, false);
	}
	
	public Color3D getColor(final int index, final boolean isWrappingAround) {
		final int resolution = this.resolutionX * this.resolutionY;
		
		final int transformedIndex = isWrappingAround ? Math.positiveModulo(index, resolution) : index;
		
		if(transformedIndex >= 0 && transformedIndex < resolution) {
			return this.colors[transformedIndex];
		}
		
		return Color3D.BLACK;
	}
	
	public Color3D getColor(final int x, final int y) {
		return getColor(x, y, false);
	}
	
	public Color3D getColor(final int x, final int y, final boolean isWrappingAround) {
		final int resolutionX = this.resolutionX;
		final int resolutionY = this.resolutionY;
		
		final int transformedX = isWrappingAround ? Math.positiveModulo(x, resolutionX) : x;
		final int transformedY = isWrappingAround ? Math.positiveModulo(y, resolutionY) : y;
		
		if(transformedX >= 0 && transformedX < resolutionX && transformedY >= 0 && transformedY < resolutionY) {
			return this.colors[transformedY * resolutionX + transformedX];
		}
		
		return Color3D.BLACK;
	}
	
	public Image save(final File file) {
		return save(file, "png");
	}
	
	public Image save(final File file, final String formatName) {
		Objects.requireNonNull(file, "file == null");
		Objects.requireNonNull(formatName, "formatName == null");
		
		try {
			doCreateParentFile(file);
			
			ImageIO.write(doToBufferedImage(doIsJPEG(formatName)), formatName, file);
			
			return this;
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	public Image save(final String pathname) {
		return save(pathname, "png");
	}
	
	public Image save(final String pathname, final String formatName) {
		return save(new File(pathname), formatName);
	}
	
	public Image setColor(final Color3D color, final int index) {
		Objects.requireNonNull(color, "color == null");
		
		if(index >= 0 && index < this.colors.length) {
			this.colors[index] = color;
		}
		
		return this;
	}
	
	public int getResolution() {
		return this.colors.length;
	}
	
	public int getResolutionX() {
		return this.resolutionX;
	}
	
	public int getResolutionY() {
		return this.resolutionY;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static Image load(final File file) {
		try {
			return new Image(ImageIO.read(Objects.requireNonNull(file, "file == null")));
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	public static Image load(final String pathname) {
		return load(new File(pathname));
	}
	
	public static Image load(final URL uRL) {
		try {
			return new Image(ImageIO.read(Objects.requireNonNull(uRL, "uRL == null")));
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private BufferedImage doToBufferedImage(final boolean isRGB) {
		final BufferedImage bufferedImage = new BufferedImage(this.resolutionX, this.resolutionY, isRGB ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB);
		
		final int[] dataSource = doToData();
		final int[] dataTarget = DataBufferInt.class.cast(bufferedImage.getRaster().getDataBuffer()).getData();
		
		System.arraycopy(dataSource, 0, dataTarget, 0, dataSource.length);
		
		return bufferedImage;
	}
	
	private int[] doToData() {
		return Stream.of(this.colors).mapToInt(color -> color.toARGB()).toArray();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static boolean doIsJPEG(final String formatName) {
		return formatName.matches("^\\.?[Jj][Pp][Ee]?[Gg]$");
	}
	
	private static void doCreateParentFile(final File file) {
		final File parentFile = file.getParentFile();
		
		if(parentFile != null && !parentFile.isDirectory()) {
			parentFile.mkdirs();
		}
	}
}