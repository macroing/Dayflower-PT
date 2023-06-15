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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.macroing.art4j.color.Color3D;
import org.macroing.geo4j.common.Point3D;
import org.macroing.geo4j.common.Vector3D;
import org.macroing.geo4j.quaternion.Quaternion4D;
import org.macroing.geo4j.ray.Ray3D;
import org.macroing.java.lang.Doubles;
import org.macroing.java.util.Randoms;

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
					
					if(Randoms.nextDouble() >= probability) {
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
	
	public Optional<Intersection> intersection(final Ray3D rayWS) {
		return intersection(rayWS, Math.EPSILON, Doubles.MAX_VALUE);
	}
	
	public Optional<Intersection> intersection(final Ray3D rayWS, final double tMinimum, final double tMaximum) {
		Intersection intersection = null;
		
		for(final Primitive primitive : this.primitives) {
			final Optional<Intersection> optionalIntersection = primitive.intersection(rayWS, tMinimum, tMaximum);
			
			if(optionalIntersection.isPresent() && (intersection == null || optionalIntersection.get().getTWS() < intersection.getTWS())) {
				intersection = optionalIntersection.get();
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
		scene.addPrimitive(new Primitive(Material.matte(new Color3D(0.75D, 0.25D, 0.25D)), Shape.sphere(new Point3D(1.0e5D + 1.0D, 40.8D, 81.6D), 1.0e5D)));
		scene.addPrimitive(new Primitive(Material.matte(new Color3D(0.25D, 0.25D, 0.75D)), Shape.sphere(new Point3D(-1.0e5D + 99.0D, 40.8D, 81.6D), 1.0e5D)));
		scene.addPrimitive(new Primitive(Material.matte(new Color3D(0.75D, 0.75D, 0.75D)), Shape.sphere(new Point3D(50.0D, 40.8D, 1.0e5D), 1.0e5D)));
		scene.addPrimitive(new Primitive(Material.matte(new Color3D(0.75D, 0.75D, 0.75D)), Shape.sphere(new Point3D(50.0D, 40.8D, -1.0e5D + 170.0D), 1.0e5D)));
		scene.addPrimitive(new Primitive(Material.matte(new Color3D(0.75D, 0.75D, 0.75D)), Shape.sphere(new Point3D(50.0D, 1.0e5D, 81.6D), 1.0e5D)));
		scene.addPrimitive(new Primitive(Material.matte(new Color3D(0.75D, 0.75D, 0.75D)), Shape.sphere(new Point3D(50.0D, -1.0e5D + 81.6D, 81.6D), 1.0e5D)));
		
//		scene.addPrimitive(new Primitive(Material.matte(new Color3D(0.75D, 0.25D, 0.25D)), Shape.cone(), new Transform(new Point3D(27.0D, 16.5D, 47.0D), Quaternion4D.from(Matrix44D.rotateX(270.0D)), new Vector3D(16.5D, 16.5D, 16.5D))));
//		scene.addPrimitive(new Primitive(Material.matte(new Color3D(0.75D, 0.25D, 0.25D)), Shape.cylinder(), new Transform(new Point3D(73.0D, 16.5D, 78.0D), Quaternion4D.from(Matrix44D.rotateX(270.0D)), new Vector3D(16.5D, 16.5D, 16.5D))));
//		scene.addPrimitive(new Primitive(Material.matte(new Color3D(0.75D, 0.25D, 0.25D)), Shape.disk(), new Transform(new Point3D(27.0D, 16.5D, 47.0D), Quaternion4D.from(Matrix44D.rotateX(270.0D)), new Vector3D(16.5D, 16.5D, 16.5D))));
//		scene.addPrimitive(new Primitive(Material.matte(new Color3D(0.75D, 0.25D, 0.25D)), Shape.hyperboloid(), new Transform(new Point3D(27.0D, 16.5D, 47.0D), Quaternion4D.from(Matrix44D.rotateX(90.0D)), new Vector3D(16.5D, 16.5D, 16.5D))));
//		scene.addPrimitive(new Primitive(Material.matte(new Color3D(0.75D, 0.25D, 0.25D)), Shape.paraboloid(), new Transform(new Point3D(27.0D, 16.5D, 47.0D), Quaternion4D.from(Matrix44D.rotateX(90.0D)), new Vector3D(16.5D, 16.5D, 16.5D))));
//		scene.addPrimitive(new Primitive(Material.matte(new Color3D(0.75D, 0.25D, 0.25D)), Shape.plane()));
//		scene.addPrimitive(new Primitive(Material.matte(new Color3D(0.75D, 0.25D, 0.25D)), Shape.polygon(new Point3D(-2.0D, +2.0D, 0.0D), new Point3D(0.0D, 3.0D, 0.0D), new Point3D(+2.0D, +2.0D, 0.0D), new Point3D(+2.0D, -2.0D, 0.0D), new Point3D(-2.0D, -2.0D, 0.0D)), new Transform(new Point3D(27.0D, 16.5D, 47.0D), new Quaternion4D(), new Vector3D(10.0D, 10.0D, 10.0D))));
//		scene.addPrimitive(new Primitive(Material.matte(new Color3D(0.75D, 0.75D, 0.75D)), Shape.rectangle(), new Transform(new Point3D(27.0D, 16.5D, 47.0D), new Quaternion4D(), new Vector3D(10.0D, 10.0D, 10.0D))));
//		scene.addPrimitive(new Primitive(Material.matte(new Color3D(0.75D, 0.75D, 0.75D)), Shape.sphere(new Point3D(27.0D, 16.5D, 47.0D), 16.5D)));
//		scene.addPrimitive(new Primitive(Material.matte(new Color3D(0.75D, 0.75D, 0.75D)), Shape.torus(), new Transform(new Point3D(27.0D, 16.5D, 47.0D), Quaternion4D.from(Matrix44D.rotateZ(90.0D)), new Vector3D(16.5D, 16.5D, 16.5D))));
		
//		scene.addPrimitive(new Primitive(Material.matte(Texture.checkerboard(Texture.constant(new Color3D(0.75D, 0.25D, 0.25D)), Texture.constant(new Color3D(0.25D, 0.75D, 0.25D)), 0.0D, 1.0D, 1.0D)), Shape.polygon(new Point3D(-2.0D, -2.0D, 0.0D), new Point3D(2.0D, -2.0D, 0.0D), new Point3D(2.0D, 2.0D, 0.0D), new Point3D(0.0D, 3.0D, 0.0D), new Point3D(-2.0D, 2.0D, 0.0D)), new Transform(new Point3D(27.0D, 16.5D, 47.0D), new Quaternion4D(), new Vector3D(10.0D, 10.0D, 10.0D))));
//		scene.addPrimitive(new Primitive(Material.matte(Texture.checkerboard(Texture.constant(new Color3D(0.75D, 0.25D, 0.25D)), Texture.constant(new Color3D(0.25D, 0.75D, 0.25D)), 0.0D, 1.0D, 1.0D)), Shape.polygon(new Point3D(-2.0D, 2.0D, 0.0D), new Point3D(0.0D, 3.0D, 0.0D), new Point3D(2.0D, 2.0D, 0.0D), new Point3D(2.0D, -2.0D, 0.0D), new Point3D(-2.0D, -2.0D, 0.0D)), new Transform(new Point3D(27.0D, 16.5D, 47.0D), new Quaternion4D(), new Vector3D(10.0D, 10.0D, 10.0D))));
//		scene.addPrimitive(new Primitive(Material.matte(Texture.checkerboard(Texture.constant(new Color3D(0.75D, 0.25D, 0.25D)), Texture.constant(new Color3D(0.25D, 0.75D, 0.25D)), 0.0D, 1.0D, 1.0D)), Shape.rectangle(), new Transform(new Point3D(27.0D, 16.5D, 47.0D), new Quaternion4D(), new Vector3D(10.0D, 10.0D, 10.0D))));
//		scene.addPrimitive(new Primitive(Material.matte(Texture.checkerboard(Texture.constant(new Color3D(0.75D, 0.25D, 0.25D)), Texture.constant(new Color3D(0.25D, 0.75D, 0.25D)), 0.0D, 1.0D, 1.0D)), Shape.rectangularCuboid(), new Transform(new Point3D(27.0D, 16.5D, 47.0D), new Quaternion4D(), new Vector3D(10.0D, 10.0D, 10.0D))));
//		scene.addPrimitive(new Primitive(Material.matte(Texture.checkerboard(Texture.constant(new Color3D(0.75D, 0.25D, 0.25D)), Texture.constant(new Color3D(0.25D, 0.75D, 0.25D)), 0.0D, 1.0D, 1.0D)), Shape.triangle(), new Transform(new Point3D(27.0D, 16.5D, 47.0D), new Quaternion4D(), new Vector3D(10.0D, 10.0D, 10.0D))));
		
		scene.addPrimitive(new Primitive(Material.metal(), Shape.rectangle(), new Transform(new Point3D(27.0D, 16.5D, 47.0D), new Quaternion4D(), new Vector3D(10.0D, 10.0D, 10.0D))));
//		scene.addPrimitive(new Primitive(Material.matte(Texture.image(Image.load("./images/Image-001.png").undoGammaCorrection(), 0.0D, 1.0D, 1.0D)), Shape.rectangle(), new Transform(new Point3D(27.0D, 16.5D, 47.0D), new Quaternion4D(), new Vector3D(10.0D, 10.0D, 10.0D))));
//		scene.addPrimitive(new Primitive(Material.matte(Texture.polkaDot(Texture.constant(new Color3D(0.75D, 0.25D, 0.25D)), Texture.constant(new Color3D(0.25D, 0.75D, 0.25D)), 0.0D, 10.0D, 0.25D)), Shape.rectangle(), new Transform(new Point3D(27.0D, 16.5D, 47.0D), new Quaternion4D(), new Vector3D(10.0D, 10.0D, 10.0D))));
//		scene.addPrimitive(new Primitive(Material.matte(Texture.simplexFractionalBrownianMotion()), Shape.rectangle(), new Transform(new Point3D(27.0D, 16.5D, 47.0D), new Quaternion4D(), new Vector3D(10.0D, 10.0D, 10.0D))));
		
		scene.addPrimitive(new Primitive(Material.matte(Color3D.WHITE, new Color3D(12.0D)), Shape.sphere(new Point3D(50.0D, 681.6D - 0.27D, 81.6D), 600.0D)));
		
		return scene;
	}
	
	public static Scene createSceneSmallPT(final Camera camera) {
		final
		Scene scene = new Scene(camera);
		scene.addPrimitive(new Primitive(Material.matte(new Color3D(0.75D, 0.25D, 0.25D)), Shape.sphere(new Point3D(1.0e5D + 1.0D, 40.8D, 81.6D), 1.0e5D)));
		scene.addPrimitive(new Primitive(Material.matte(new Color3D(0.25D, 0.25D, 0.75D)), Shape.sphere(new Point3D(-1.0e5D + 99.0D, 40.8D, 81.6D), 1.0e5D)));
		scene.addPrimitive(new Primitive(Material.matte(new Color3D(0.75D, 0.75D, 0.75D)), Shape.sphere(new Point3D(50.0D, 40.8D, 1.0e5D), 1.0e5D)));
		scene.addPrimitive(new Primitive(Material.matte(new Color3D(0.75D, 0.75D, 0.75D)), Shape.sphere(new Point3D(50.0D, 40.8D, -1.0e5D + 170.0D), 1.0e5D)));
		scene.addPrimitive(new Primitive(Material.matte(new Color3D(0.75D, 0.75D, 0.75D)), Shape.sphere(new Point3D(50.0D, 1.0e5D, 81.6D), 1.0e5D)));
		scene.addPrimitive(new Primitive(Material.matte(new Color3D(0.75D, 0.75D, 0.75D)), Shape.sphere(new Point3D(50.0D, -1.0e5D + 81.6D, 81.6D), 1.0e5D)));
		scene.addPrimitive(new Primitive(Material.mirror(new Color3D(0.999D, 0.999D, 0.999D)), Shape.sphere(new Point3D(27.0D, 16.5D, 47.0D), 16.5D)));
//		scene.addPrimitive(new Primitive(Material.clearCoat(new Color3D(1.0D, 0.01D, 0.01D)), Shape.sphere(new Point3D(27.0D, 16.5D, 47.0D), 16.5D)));
//		scene.addPrimitive(new Primitive(Material.disney(), Shape.sphere(new Point3D(27.0D, 16.5D, 47.0D), 16.5D)));
//		scene.addPrimitive(new Primitive(Material.glossy(), Shape.sphere(new Point3D(27.0D, 16.5D, 47.0D), 16.5D)));
//		scene.addPrimitive(new Primitive(Material.metal(), Shape.sphere(new Point3D(27.0D, 16.5D, 47.0D), 16.5D)));
//		scene.addPrimitive(new Primitive(Material.plastic(), Shape.sphere(new Point3D(27.0D, 16.5D, 47.0D), 16.5D)));
//		scene.addPrimitive(new Primitive(Material.substrate(), Shape.sphere(new Point3D(27.0D, 16.5D, 47.0D), 16.5D)));
//		scene.addPrimitive(new Primitive(Material.translucent(), Shape.sphere(new Point3D(27.0D, 16.5D, 47.0D), 16.5D)));
//		scene.addPrimitive(new Primitive(Material.uber(), Shape.sphere(new Point3D(27.0D, 16.5D, 47.0D), 16.5D)));
		scene.addPrimitive(new Primitive(Material.glass(), Shape.sphere(new Point3D(73.0D, 16.5D, 78.0D), 16.5D)));
		scene.addPrimitive(new Primitive(Material.matte(Color3D.WHITE, new Color3D(12.0D)), Shape.sphere(new Point3D(50.0D, 681.6D - 0.27D, 81.6D), 600.0D)));
		
		return scene;
	}
}