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
import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.imageio.ImageIO;

public final class Image {
	private final Color3D[] colors;
	private final int resolutionX;
	private final int resolutionY;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Image(final int resolutionX, final int resolutionY) {
		this.resolutionX = resolutionX;
		this.resolutionY = resolutionY;
		this.colors = IntStream.range(0, resolutionX * resolutionY).mapToObj(index -> Color3D.BLACK).toArray(Color3D[]::new);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
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