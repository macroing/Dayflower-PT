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

import java.lang.reflect.Field;//TODO: Add unit tests!

import org.macroing.art4j.color.Color3D;
import org.macroing.art4j.color.Color4D;
import org.macroing.art4j.image.Image;
import org.macroing.art4j.pixel.Color4DPixelOperator;

public final class PathTracer {
	private static final int RESOLUTION_X = 1024;
	private static final int RESOLUTION_Y = 768;
	private static final int SAMPLE_RESOLUTION_X = 2;
	private static final int SAMPLE_RESOLUTION_Y = 2;
	private static final int SAMPLES = 10;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final Image image;
	private final Scene scene;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private PathTracer() {
		this.image = new Image(RESOLUTION_X, RESOLUTION_Y, Color4D.WHITE);
		this.scene = Scene.createSceneSmallPT(new Camera(RESOLUTION_X, RESOLUTION_Y));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add unit tests!
	public void render() {
		final long currentTimeMillisA = System.currentTimeMillis();
		
		for(int pixelY = 0; pixelY < RESOLUTION_Y; pixelY++) {
			for(int pixelX = 0, pixelIndex = (RESOLUTION_Y - pixelY - 1) * RESOLUTION_X; pixelX < RESOLUTION_X; pixelX++, pixelIndex++) {
				Color3D totalRadiance = Color3D.BLACK;
				
				for(int sampleY = 0; sampleY < SAMPLE_RESOLUTION_Y; sampleY++) {
					for(int sampleX = 0; sampleX < SAMPLE_RESOLUTION_X; sampleX++) {
						Color3D radiance = Color3D.BLACK;
						
						for(int sample = 0; sample < SAMPLES; sample++) {
							radiance = Color3D.add(radiance, Color3D.divide(this.scene.radiance(this.scene.getCamera().generatePrimaryRay(pixelX, pixelY, sampleX, sampleY)), SAMPLES));
						}
						
						totalRadiance = Color3D.add(totalRadiance, Color3D.divide(Color3D.saturate(radiance), SAMPLE_RESOLUTION_X * SAMPLE_RESOLUTION_Y));
					}
				}
				
				this.image.setColor3D(totalRadiance, pixelIndex);
			}
		}
		
		final long currentTimeMillisB = System.currentTimeMillis();
		final long currentTimeMillisC =  currentTimeMillisB -  currentTimeMillisA;
		
		this.image.fillD(Color4DPixelOperator.redoGammaCorrection());
		this.image.save(String.format("./PT-%s.png", Long.toString(System.currentTimeMillis())));
		
		System.out.println("Rendering completed in " + currentTimeMillisC + " milliseconds.");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static void main(final String[] args) {
		final
		PathTracer pathTracer = new PathTracer();
		pathTracer.render();
	}
}