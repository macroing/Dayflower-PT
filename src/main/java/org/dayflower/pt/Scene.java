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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class Scene {
	private final Camera camera;
	private final List<Primitive> primitives;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Scene(final Camera camera) {
		this.camera = Objects.requireNonNull(camera, "camera == null");
		this.primitives = new ArrayList<>();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Camera getCamera() {
		return this.camera;
	}
	
	public Color3D radiance(final Ray3D ray) {
		return radiance(ray, 0);
	}
	
	public Color3D radiance(final Ray3D ray, final int depth) {
		final Optional<Intersection> optionalIntersection = intersection(ray);
		
		if(optionalIntersection.isPresent()) {
			final Optional<Material.Result> optionalResult = optionalIntersection.get().getPrimitive().getMaterial().compute(optionalIntersection.get());
			
			if(optionalResult.isPresent()) {
				final Material.Result result = optionalResult.get();
				
				if(depth >= 20) {
					return result.getEmission();
				}
				
				if(depth >= 5) {
					final double probability = result.getReflectance().max();
					
					if(Math.random() >= probability) {
						return result.getEmission();
					}
					
					return Color3D.add(result.getEmission(), Color3D.multiply(Color3D.divide(result.getReflectance(), probability), radiance(result.getRay(), depth + 1)));
				}
				
				return Color3D.add(result.getEmission(), Color3D.multiply(result.getReflectance(), radiance(result.getRay(), depth + 1)));
			}
		}
		
		return Color3D.BLACK;
	}
	
	public List<Primitive> getPrimitives() {
		return new ArrayList<>(this.primitives);
	}
	
	public Optional<Intersection> intersection(final Ray3D ray) {
		return intersection(ray, Math.EPSILON, Math.MAX_VALUE);
	}
	
	public Optional<Intersection> intersection(final Ray3D ray, final double tMinimum, final double tMaximum) {
		Intersection intersection = null;
		
		for(final Primitive primitive : this.primitives) {
			final Shape shape = primitive.getShape();
			
			final double t = shape.intersection(ray, tMinimum, tMaximum);
			
			if(!Math.isNaN(t) && (intersection == null || t < intersection.getT())) {
				intersection = new Intersection(primitive, ray, t);
			}
		}
		
		return Optional.ofNullable(intersection);
	}
	
	public void addPrimitive(final Primitive primitive) {
		this.primitives.add(Objects.requireNonNull(primitive, "primitive == null"));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static Scene createScene(final Camera camera) {
		final
		Scene scene = new Scene(camera);
//		scene.addPrimitive(new Primitive(Material.checkerboard(Material.substrate(), Material.metal(), 0.0D, 1.0D, 1.0D), Shape.sphere(new Point3D( 1.0e5D + 1.0D, 40.8D, 81.6D), 1.0e5D)));
		scene.addPrimitive(new Primitive(Material.matte(Texture.constant(new Color3D(0.750D, 0.250D, 0.250D))), Shape.sphere(new Point3D( 1.0e5D + 1.0D, 40.8D, 81.6D), 1.0e5D)));
		scene.addPrimitive(new Primitive(Material.matte(Texture.constant(new Color3D(0.250D, 0.250D, 0.750D))), Shape.sphere(new Point3D(-1.0e5D + 99.0D, 40.8D, 81.6D), 1.0e5D)));
		scene.addPrimitive(new Primitive(Material.matte(Texture.constant(new Color3D(0.750D, 0.750D, 0.750D))), Shape.sphere(new Point3D(50.0D, 40.8D, 1.0e5D), 1.0e5D)));
		scene.addPrimitive(new Primitive(Material.matte(Texture.constant(new Color3D(0.750D, 0.750D, 0.750D))), Shape.sphere(new Point3D(50.0D, 40.8D, -1.0e5D + 170.0D), 1.0e5D)));
		scene.addPrimitive(new Primitive(Material.matte(Texture.constant(new Color3D(0.750D, 0.750D, 0.750D))), Shape.sphere(new Point3D(50.0D, 1.0e5D, 81.6D), 1.0e5D)));
		scene.addPrimitive(new Primitive(Material.matte(Texture.constant(new Color3D(0.750D, 0.750D, 0.750D))), Shape.sphere(new Point3D(50.0D, -1.0e5D + 81.6D, 81.6D), 1.0e5D)));
//		scene.addPrimitive(new Primitive(Material.mirror(Texture.constant(new Color3D(0.999D, 0.999D, 0.999D))), Shape.sphere(new Point3D(27.0D, 16.5D, 47.0D), 16.5D)));
//		scene.addPrimitive(new Primitive(Material.substrate(), Shape.sphere(new Point3D(27.0D, 16.5D, 47.0D), 16.5D)));
//		scene.addPrimitive(new Primitive(Material.metal(), Shape.sphere(new Point3D(27.0D, 16.5D, 47.0D), 16.5D)));
		scene.addPrimitive(new Primitive(Material.plastic(), Shape.sphere(new Point3D(27.0D, 16.5D, 47.0D), 16.5D)));
		scene.addPrimitive(new Primitive(Material.glass(), Shape.sphere(new Point3D(73.0D, 16.5D, 78.0D), 16.5D)));
		scene.addPrimitive(new Primitive(Material.matte(Texture.constant(new Color3D(0.000D, 0.000D, 0.000D)), Texture.constant(new Color3D(12.0D))), Shape.sphere(new Point3D(50.0D, 681.6D - 0.27D, 81.6D), 600.0D)));
		
		return scene;
	}
}