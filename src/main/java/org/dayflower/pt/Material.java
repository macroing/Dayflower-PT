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
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.macroing.art4j.color.Color3D;
import org.macroing.geo4j.common.Point2D;
import org.macroing.geo4j.common.Point3D;
import org.macroing.geo4j.common.Vector3D;
import org.macroing.geo4j.onb.OrthonormalBasis33D;
import org.macroing.geo4j.ray.Ray3D;
import org.macroing.java.lang.Doubles;
import org.macroing.java.lang.Ints;
import org.macroing.java.util.Randoms;

public abstract class Material {
	protected Material() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public abstract Optional<Result> compute(final Intersection intersection);
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@link Material} instance with a bullseye pattern.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Material.bullseye(Material.matte(new Color3D(0.5D)));
	 * }
	 * </pre>
	 * 
	 * @return a {@code Material} instance with a bullseye pattern
	 */
	public static Material bullseye() {
		return bullseye(matte(new Color3D(0.5D)));
	}
	
	/**
	 * Returns a {@link Material} instance with a bullseye pattern.
	 * <p>
	 * If {@code materialA} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Material.bullseye(materialA, Material.matte(Color3D.WHITE));
	 * }
	 * </pre>
	 * 
	 * @param materialA a {@code Material} instance
	 * @return a {@code Material} instance with a bullseye pattern
	 * @throws NullPointerException thrown if, and only if, {@code materialA} is {@code null}
	 */
	public static Material bullseye(final Material materialA) {
		return bullseye(materialA, matte(Color3D.WHITE));
	}
	
	/**
	 * Returns a {@link Material} instance with a bullseye pattern.
	 * <p>
	 * If either {@code materialA} or {@code materialB} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Material.bullseye(materialA, materialB, new Point3D(0.0D, 10.0D, 0.0D));
	 * }
	 * </pre>
	 * 
	 * @param materialA a {@code Material} instance
	 * @param materialB a {@code Material} instance
	 * @return a {@code Material} instance with a bullseye pattern
	 * @throws NullPointerException thrown if, and only if, either {@code materialA} or {@code materialB} are {@code null}
	 */
	public static Material bullseye(final Material materialA, final Material materialB) {
		return bullseye(materialA, materialB, new Point3D(0.0D, 10.0D, 0.0D));
	}
	
	/**
	 * Returns a {@link Material} instance with a bullseye pattern.
	 * <p>
	 * If either {@code materialA}, {@code materialB} or {@code origin} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Material.bullseye(materialA, materialB, origin, 1.0D);
	 * }
	 * </pre>
	 * 
	 * @param materialA a {@code Material} instance
	 * @param materialB a {@code Material} instance
	 * @param origin a {@link Point3D} instance
	 * @return a {@code Material} instance with a bullseye pattern
	 * @throws NullPointerException thrown if, and only if, either {@code materialA}, {@code materialB} or {@code origin} are {@code null}
	 */
	public static Material bullseye(final Material materialA, final Material materialB, final Point3D origin) {
		return bullseye(materialA, materialB, origin, 1.0D);
	}
	
	/**
	 * Returns a {@link Material} instance with a bullseye pattern.
	 * <p>
	 * If either {@code materialA}, {@code materialB} or {@code origin} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param materialA a {@code Material} instance
	 * @param materialB a {@code Material} instance
	 * @param origin a {@link Point3D} instance
	 * @param scale a {@code double} value with a scale factor
	 * @return a {@code Material} instance with a bullseye pattern
	 * @throws NullPointerException thrown if, and only if, either {@code materialA}, {@code materialB} or {@code origin} are {@code null}
	 */
	public static Material bullseye(final Material materialA, final Material materialB, final Point3D origin, final double scale) {
		return new BullseyeMaterial(materialA, materialB, origin, scale);
	}
	
	/**
	 * Returns a {@link Material} instance with a checkerboard pattern.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Material.checkerboard(Material.matte(new Color3D(0.5D)));
	 * }
	 * </pre>
	 * 
	 * @return a {@code Material} instance with a checkerboard pattern
	 */
	public static Material checkerboard() {
		return checkerboard(matte(new Color3D(0.5D)));
	}
	
	/**
	 * Returns a {@link Material} instance with a checkerboard pattern.
	 * <p>
	 * If {@code materialA} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Material.checkerboard(materialA, Material.matte(Color3D.WHITE));
	 * }
	 * </pre>
	 * 
	 * @param materialA a {@code Material} instance
	 * @return a {@code Material} instance with a checkerboard pattern
	 * @throws NullPointerException thrown if, and only if, {@code materialA} is {@code null}
	 */
	public static Material checkerboard(final Material materialA) {
		return checkerboard(materialA, matte(Color3D.WHITE));
	}
	
	/**
	 * Returns a {@link Material} instance with a checkerboard pattern.
	 * <p>
	 * If either {@code materialA} or {@code materialB} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Material.checkerboard(materialA, materialB, 0.0D);
	 * }
	 * </pre>
	 * 
	 * @param materialA a {@code Material} instance
	 * @param materialB a {@code Material} instance
	 * @return a {@code Material} instance with a checkerboard pattern
	 * @throws NullPointerException thrown if, and only if, either {@code materialA} or {@code materialB} are {@code null}
	 */
	public static Material checkerboard(final Material materialA, final Material materialB) {
		return checkerboard(materialA, materialB, 0.0D);
	}
	
	/**
	 * Returns a {@link Material} instance with a checkerboard pattern.
	 * <p>
	 * If either {@code materialA} or {@code materialB} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Material.checkerboard(materialA, materialB, angleDegrees, 1.0D);
	 * }
	 * </pre>
	 * 
	 * @param materialA a {@code Material} instance
	 * @param materialB a {@code Material} instance
	 * @param angleDegrees a {@code double} value with an angle in degrees
	 * @return a {@code Material} instance with a checkerboard pattern
	 * @throws NullPointerException thrown if, and only if, either {@code materialA} or {@code materialB} are {@code null}
	 */
	public static Material checkerboard(final Material materialA, final Material materialB, final double angleDegrees) {
		return checkerboard(materialA, materialB, angleDegrees, 1.0D);
	}
	
	/**
	 * Returns a {@link Material} instance with a checkerboard pattern.
	 * <p>
	 * If either {@code materialA} or {@code materialB} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Material.checkerboard(materialA, materialB, angleDegrees, scale, scale);
	 * }
	 * </pre>
	 * 
	 * @param materialA a {@code Material} instance
	 * @param materialB a {@code Material} instance
	 * @param angleDegrees a {@code double} value with an angle in degrees
	 * @param scale a {@code double} value with a scale factor
	 * @return a {@code Material} instance with a checkerboard pattern
	 * @throws NullPointerException thrown if, and only if, either {@code materialA} or {@code materialB} are {@code null}
	 */
	public static Material checkerboard(final Material materialA, final Material materialB, final double angleDegrees, final double scale) {
		return checkerboard(materialA, materialB, angleDegrees, scale, scale);
	}
	
	/**
	 * Returns a {@link Material} instance with a checkerboard pattern.
	 * <p>
	 * If either {@code materialA} or {@code materialB} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param materialA a {@code Material} instance
	 * @param materialB a {@code Material} instance
	 * @param angleDegrees a {@code double} value with an angle in degrees
	 * @param scaleU a {@code double} value with a scale factor along the U-direction
	 * @param scaleV a {@code double} value with a scale factor along the V-direction
	 * @return a {@code Material} instance with a checkerboard pattern
	 * @throws NullPointerException thrown if, and only if, either {@code materialA} or {@code materialB} are {@code null}
	 */
	public static Material checkerboard(final Material materialA, final Material materialB, final double angleDegrees, final double scaleU, final double scaleV) {
		return new CheckerboardMaterial(materialA, materialB, angleDegrees, scaleU, scaleV);
	}
	
	/**
	 * Returns a {@link Material} instance with glass.
	 * 
	 * @return a {@code Material} instance with glass
	 */
	public static Material glass() {
		return new GlassMaterial();
	}
	
	public static Material glossy() {
		return glossy(Color3D.GRAY);
	}
	
	public static Material glossy(final Color3D colorKR) {
		return glossy(colorKR, new Color3D(0.1D));
	}
	
	public static Material glossy(final Color3D colorKR, final Color3D colorRoughness) {
		return glossy(colorKR, colorRoughness, Color3D.BLACK);
	}
	
	public static Material glossy(final Color3D colorKR, final Color3D colorRoughness, final Color3D colorEmission) {
		return new GlossyMaterial(Texture.constant(colorKR), Texture.constant(colorRoughness), Texture.constant(colorEmission));
	}
	
	public static Material glossy(final Texture textureKR) {
		return glossy(textureKR, Texture.constant(new Color3D(0.1D)));
	}
	
	public static Material glossy(final Texture textureKR, final Texture textureRoughness) {
		return glossy(textureKR, textureRoughness, Texture.constant(Color3D.BLACK));
	}
	
	public static Material glossy(final Texture textureKR, final Texture textureRoughness, final Texture textureEmission) {
		return new GlossyMaterial(textureKR, textureRoughness, textureEmission);
	}
	
	public static Material matte() {
		return matte(new Color3D(0.5D));
	}
	
	public static Material matte(final Color3D colorKD) {
		return matte(colorKD, Color3D.BLACK);
	}
	
	public static Material matte(final Color3D colorKD, final Color3D colorEmission) {
		return matte(Texture.constant(colorKD), Texture.constant(colorEmission));
	}
	
	public static Material matte(final Texture textureKD) {
		return matte(textureKD, Texture.constant(Color3D.BLACK));
	}
	
	public static Material matte(final Texture textureKD, final Texture textureEmission) {
		return new MatteMaterial(textureKD, textureEmission);
	}
	
	public static Material metal() {
		return metal(Color3D.AU_K, Color3D.AU_ETA);
	}
	
	public static Material metal(final Color3D colorK, final Color3D colorEta) {
		return metal(colorK, colorEta, 0.01D);
	}
	
	public static Material metal(final Color3D colorK, final Color3D colorEta, final double doubleRoughness) {
		return metal(colorK, colorEta, doubleRoughness, doubleRoughness);
	}
	
	public static Material metal(final Color3D colorK, final Color3D colorEta, final double doubleRoughnessU, final double doubleRoughnessV) {
		return metal(colorK, colorEta, doubleRoughnessU, doubleRoughnessV, true);
	}
	
	public static Material metal(final Color3D colorK, final Color3D colorEta, final double doubleRoughnessU, final double doubleRoughnessV, final boolean isRemappingRoughness) {
		return metal(colorK, colorEta, doubleRoughnessU, doubleRoughnessV, isRemappingRoughness, Color3D.BLACK);
	}
	
	public static Material metal(final Color3D colorK, final Color3D colorEta, final double doubleRoughnessU, final double doubleRoughnessV, final boolean isRemappingRoughness, final Color3D colorEmission) {
		return metal(Texture.constant(colorK), Texture.constant(colorEta), Texture.constant(doubleRoughnessU), Texture.constant(doubleRoughnessV), isRemappingRoughness, Texture.constant(colorEmission));
	}
	
	public static Material metal(final Texture textureK, final Texture textureEta, final Texture textureRoughnessU, final Texture textureRoughnessV, final boolean isRemappingRoughness, final Texture textureEmission) {
		return new MetalMaterial(textureK, textureEta, textureRoughnessU, textureRoughnessV, isRemappingRoughness, textureEmission);
	}
	
	public static Material mirror(final Color3D colorKR) {
		return mirror(colorKR, Color3D.BLACK);
	}
	
	public static Material mirror(final Color3D colorKR, final Color3D colorEmission) {
		return mirror(Texture.constant(colorKR), Texture.constant(colorEmission));
	}
	
	public static Material mirror(final Texture textureKR) {
		return mirror(textureKR, Texture.constant(Color3D.BLACK));
	}
	
	public static Material mirror(final Texture textureKR, final Texture textureEmission) {
		return new MirrorMaterial(textureKR, textureEmission);
	}
	
	public static Material phong() {
		return phong(new Color3D(0.5D));
	}
	
	public static Material phong(final Color3D colorKR) {
		return phong(colorKR, Color3D.BLACK);
	}
	
	public static Material phong(final Color3D colorKR, final Color3D colorEmission) {
		return new PhongMaterial(Texture.constant(colorKR), Texture.constant(colorEmission));
	}
	
	public static Material phong(final Texture textureKR) {
		return phong(textureKR, Texture.constant(Color3D.BLACK));
	}
	
	public static Material phong(final Texture textureKR, final Texture textureEmission) {
		return new PhongMaterial(textureKR, textureEmission);
	}
	
	public static Material plastic() {
		return plastic(new Color3D(0.2D, 0.2D, 0.5D));
	}
	
	public static Material plastic(final Color3D colorKD) {
		return plastic(colorKD, new Color3D(0.5D));
	}
	
	public static Material plastic(final Color3D colorKD, final Color3D colorKS) {
		return plastic(colorKD, colorKS, 0.025D);
	}
	
	public static Material plastic(final Color3D colorKD, final Color3D colorKS, final double doubleRoughness) {
		return plastic(colorKD, colorKS, doubleRoughness, true);
	}
	
	public static Material plastic(final Color3D colorKD, final Color3D colorKS, final double doubleRoughness, final boolean isRemappingRoughness) {
		return plastic(colorKD, colorKS, doubleRoughness, isRemappingRoughness, Color3D.BLACK);
	}
	
	public static Material plastic(final Color3D colorKD, final Color3D colorKS, final double doubleRoughness, final boolean isRemappingRoughness, final Color3D colorEmission) {
		return plastic(Texture.constant(colorKD), Texture.constant(colorKS), Texture.constant(doubleRoughness), isRemappingRoughness, Texture.constant(colorEmission));
	}
	
	public static Material plastic(final Texture textureKD) {
		return plastic(textureKD, Texture.constant(new Color3D(0.5D)));
	}
	
	public static Material plastic(final Texture textureKD, final Texture textureKS) {
		return plastic(textureKD, textureKS, Texture.constant(new Color3D(0.025D)));
	}
	
	public static Material plastic(final Texture textureKD, final Texture textureKS, final Texture textureRoughness) {
		return plastic(textureKD, textureKS, textureRoughness, true);
	}
	
	public static Material plastic(final Texture textureKD, final Texture textureKS, final Texture textureRoughness, final boolean isRemappingRoughness) {
		return plastic(textureKD, textureKS, textureRoughness, isRemappingRoughness, Texture.constant(Color3D.BLACK));
	}
	
	public static Material plastic(final Texture textureKD, final Texture textureKS, final Texture textureRoughness, final boolean isRemappingRoughness, final Texture textureEmission) {
		return new PlasticMaterial(textureKD, textureKS, textureRoughness, isRemappingRoughness, textureEmission);
	}
	
	public static Material substrate() {
		return substrate(new Color3D(1.0D, 0.2D, 0.2D));
	}
	
	public static Material substrate(final Color3D colorKD) {
		return substrate(colorKD, new Color3D(0.5D));
	}
	
	public static Material substrate(final Color3D colorKD, final Color3D colorKS) {
		return substrate(colorKD, colorKS, 0.1D);
	}
	
	public static Material substrate(final Color3D colorKD, final Color3D colorKS, final double doubleRoughness) {
		return substrate(colorKD, colorKS, doubleRoughness, doubleRoughness);
	}
	
	public static Material substrate(final Color3D colorKD, final Color3D colorKS, final double doubleRoughnessU, final double doubleRoughnessV) {
		return substrate(colorKD, colorKS, doubleRoughnessU, doubleRoughnessV, true);
	}
	
	public static Material substrate(final Color3D colorKD, final Color3D colorKS, final double doubleRoughnessU, final double doubleRoughnessV, final boolean isRemappingRoughness) {
		return substrate(colorKD, colorKS, doubleRoughnessU, doubleRoughnessV, isRemappingRoughness, Color3D.BLACK);
	}
	
	public static Material substrate(final Color3D colorKD, final Color3D colorKS, final double doubleRoughnessU, final double doubleRoughnessV, final boolean isRemappingRoughness, final Color3D colorEmission) {
		return substrate(Texture.constant(colorKD), Texture.constant(colorKS), Texture.constant(doubleRoughnessU), Texture.constant(doubleRoughnessV), isRemappingRoughness, Texture.constant(colorEmission));
	}
	
	public static Material substrate(final Texture textureKD) {
		return substrate(textureKD, Texture.constant(new Color3D(0.5D)));
	}
	
	public static Material substrate(final Texture textureKD, final Texture textureKS) {
		return substrate(textureKD, textureKS, Texture.constant(new Color3D(0.1D)));
	}
	
	public static Material substrate(final Texture textureKD, final Texture textureKS, final Texture textureRoughness) {
		return substrate(textureKD, textureKS, textureRoughness, textureRoughness);
	}
	
	public static Material substrate(final Texture textureKD, final Texture textureKS, final Texture textureRoughnessU, final Texture textureRoughnessV) {
		return substrate(textureKD, textureKS, textureRoughnessU, textureRoughnessV, true);
	}
	
	public static Material substrate(final Texture textureKD, final Texture textureKS, final Texture textureRoughnessU, final Texture textureRoughnessV, final boolean isRemappingRoughness) {
		return substrate(textureKD, textureKS, textureRoughnessU, textureRoughnessV, isRemappingRoughness, Texture.constant(Color3D.BLACK));
	}
	
	public static Material substrate(final Texture textureKD, final Texture textureKS, final Texture textureRoughnessU, final Texture textureRoughnessV, final boolean isRemappingRoughness, final Texture textureEmission) {
		return new SubstrateMaterial(textureKD, textureKS, textureRoughnessU, textureRoughnessV, isRemappingRoughness, textureEmission);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static final class Result {
		private final Color3D emission;
		private final Color3D reflectance;
		private final Ray3D ray;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public Result(final Color3D emission, final Color3D reflectance, final Ray3D ray) {
			this.emission = Objects.requireNonNull(emission, "emission == null");
			this.reflectance = Objects.requireNonNull(reflectance, "reflectance == null");
			this.ray = Objects.requireNonNull(ray, "ray == null");
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public Color3D getEmission() {
			return this.emission;
		}
		
		public Color3D getReflectance() {
			return this.reflectance;
		}
		
		public Ray3D getRay() {
			return this.ray;
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final class AshikhminShirleyBRDF implements BXDF {
		private final Color3D r;
		private final double exponent;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public AshikhminShirleyBRDF(final Color3D r, final double roughness) {
			this.r = Objects.requireNonNull(r, "r == null");
			this.exponent = 1.0F / (roughness * roughness);
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		@Override
		public Color3D evaluateDF(final Vector3D o, final Vector3D i) {
			final double cosThetaAbsO = o.cosThetaAbs();
			final double cosThetaAbsI = i.cosThetaAbs();
			
			if(Doubles.isZero(cosThetaAbsO) || Doubles.isZero(cosThetaAbsI)) {
				return Color3D.BLACK;
			}
			
			final Vector3D h = Vector3D.add(o, i);
			
			if(h.isZero()) {
				return Color3D.BLACK;
			}
			
			final Vector3D hNormalized = Vector3D.normalize(h);
			
			final double d = (this.exponent + 1.0D) * Doubles.pow(Doubles.abs(hNormalized.cosTheta()), this.exponent) * Doubles.PI_MULTIPLIED_BY_2_RECIPROCAL;
			final double f = Schlick.fresnelDielectric(Vector3D.dotProduct(o, hNormalized), 1.0D);
			
			final Color3D r = this.r;
			
			return Color3D.divide(Color3D.multiply(Color3D.multiply(r, d), f), 4.0F * cosThetaAbsI * cosThetaAbsO);
		}
		
		@Override
		public Optional<BXDFResult> sampleDF(final Vector3D o, final Point2D p) {
			if(Doubles.isZero(o.z)) {
				return Optional.empty();
			}
			
			final Vector3D hSample = Vector3D.sampleHemispherePowerCosineDistribution(p, this.exponent);
			final Vector3D h = Vector3D.dotProduct(Vector3D.z(), o) < 0.0D ? Vector3D.negate(hSample) : hSample;
			
			final double oDotH = Vector3D.dotProduct(o, h);
			
			if(oDotH < 0.0D) {
				return Optional.empty();
			}
			
			final Vector3D i = Vector3D.reflection(o, h);
			
			if(!Vector3D.sameHemisphereZ(o, i)) {
				return Optional.empty();
			}
			
			final Color3D result = evaluateDF(o, i);
			
			final double pDF = evaluatePDF(o, i);
			
			return Optional.of(new BXDFResult(result, i, pDF));
		}
		
		@Override
		public double evaluatePDF(final Vector3D o, final Vector3D i) {
			if(!Vector3D.sameHemisphereZ(o, i)) {
				return 0.0D;
			}
			
			final Vector3D h = Vector3D.normalize(Vector3D.add(o, i));
			
			return (this.exponent + 1.0D) * Doubles.pow(Doubles.abs(h.cosTheta()), this.exponent) / (Doubles.PI * 8.0D * Doubles.abs(Vector3D.dotProduct(o, h)));
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final class BSDF {
		private final List<BXDF> bXDFs;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public BSDF(final BXDF bXDF) {
			this(Arrays.asList(bXDF));
		}
		
		public BSDF(final List<BXDF> bXDFs) {
			this.bXDFs = new ArrayList<>(bXDFs);
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public Optional<Result> compute(final Intersection intersection, final Color3D emission) {
			final OrthonormalBasis33D orthonormalBasis = intersection.getOrthonormalBasisWS();
			
			final Vector3D nWS = intersection.getSurfaceNormalWSCorrectlyOriented();
			final Vector3D oWS = Vector3D.negate(intersection.getRayWS().getDirection());
			final Vector3D oLS = orthonormalBasis.transformReverseNormalize(oWS);
			
			if(Doubles.isZero(oLS.z)) {
				return Optional.empty();
			}
			
			final double sampleU = Randoms.nextDouble();
			final double sampleV = Randoms.nextDouble();
			
			final int matches = this.bXDFs.size();
			final int match = Ints.min((int)(Doubles.floor(sampleU * matches)), matches - 1);
			
			if(matches == 0) {
				return Optional.empty();
			}
			
			final double u = Doubles.min(sampleU * matches - match, 0.99999994D);
			final double v = sampleV;
			
			final Point2D p = new Point2D(u, v);
			
			final BXDF bXDF = this.bXDFs.get(match);
			
			final Optional<BXDFResult> optionalBXDFResult = bXDF.sampleDF(oLS, p);
			
			if(optionalBXDFResult.isEmpty()) {
				return Optional.empty();
			}
			
			final BXDFResult bXDFResult = optionalBXDFResult.get();
			
			final Vector3D iLS = bXDFResult.getI();
			final Vector3D iWS = orthonormalBasis.transformNormalize(iLS);
			
			Color3D result = bXDFResult.getResult();
			
			if(result.isBlack()) {
				return Optional.empty();
			}
			
			double probabilityDensityFunctionValue = bXDFResult.getPDF();
			
			if(Doubles.isZero(probabilityDensityFunctionValue)) {
				return Optional.empty();
			}
			
			final Ray3D ray = new Ray3D(intersection.getSurfaceIntersectionPointWS(), iWS);
			
			if(matches > 1) {
				for(final BXDF currentBXDF : this.bXDFs) {
					if(currentBXDF != bXDF) {
						probabilityDensityFunctionValue += currentBXDF.evaluatePDF(oLS, iLS);
						
						result = Color3D.add(result, currentBXDF.evaluateDF(oLS, iLS));
					}
				}
				
				probabilityDensityFunctionValue /= matches;
			}
			
			final Color3D reflectance = Color3D.divide(Color3D.multiply(result, Vector3D.dotProductAbs(iWS, nWS)), probabilityDensityFunctionValue);
			
			return Optional.of(new Result(emission, reflectance, ray));
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static interface BXDF {
		Color3D evaluateDF(final Vector3D o, final Vector3D i);
		
		Optional<BXDFResult> sampleDF(final Vector3D o, final Point2D p);
		
		double evaluatePDF(final Vector3D o, final Vector3D i);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final class BXDFResult {
		private final Color3D result;
		private final Vector3D i;
		private final double pDF;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public BXDFResult(final Color3D result, final Vector3D i, final double pDF) {
			this.result = Objects.requireNonNull(result, "result == null");
			this.i = Objects.requireNonNull(i, "i == null");
			this.pDF = pDF;
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public Color3D getResult() {
			return this.result;
		}
		
		public Vector3D getI() {
			return this.i;
		}
		
		public double getPDF() {
			return this.pDF;
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final class BullseyeMaterial extends Material {
		private final Material materialA;
		private final Material materialB;
		private final Point3D origin;
		private final double scale;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public BullseyeMaterial(final Material materialA, final Material materialB, final Point3D origin, final double scale) {
			this.materialA = Objects.requireNonNull(materialA, "materialA == null");
			this.materialB = Objects.requireNonNull(materialB, "materialB == null");
			this.origin = Objects.requireNonNull(origin, "origin == null");
			this.scale = scale;
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		@Override
		public Optional<Result> compute(final Intersection intersection) {
			final Vector3D direction = Vector3D.direction(this.origin, intersection.getSurfaceIntersectionPointOS());
			
			final boolean isTextureA = (direction.length() * this.scale) % 1.0D > 0.5D;
			
			return isTextureA ? this.materialA.compute(intersection) : this.materialB.compute(intersection);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final class CheckerboardMaterial extends Material {
		private final Material materialA;
		private final Material materialB;
		private final double angleDegrees;
		private final double angleRadians;
		private final double angleRadiansCos;
		private final double angleRadiansSin;
		private final double scaleU;
		private final double scaleV;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public CheckerboardMaterial(final Material materialA, final Material materialB, final double angleDegrees, final double scaleU, final double scaleV) {
			this.materialA = Objects.requireNonNull(materialA, "materialA == null");
			this.materialB = Objects.requireNonNull(materialB, "materialB == null");
			this.angleDegrees = angleDegrees;
			this.angleRadians = Doubles.toRadians(this.angleDegrees);
			this.angleRadiansCos = Doubles.cos(this.angleRadians);
			this.angleRadiansSin = Doubles.sin(this.angleRadians);
			this.scaleU = scaleU;
			this.scaleV = scaleV;
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		@Override
		public Optional<Result> compute(final Intersection intersection) {
			final double u = intersection.getTextureCoordinates().x;
			final double v = intersection.getTextureCoordinates().y;
			
			final boolean isU = Doubles.fractionalPart((u * this.angleRadiansCos - v * this.angleRadiansSin) * this.scaleU) > 0.5D;
			final boolean isV = Doubles.fractionalPart((v * this.angleRadiansCos + u * this.angleRadiansSin) * this.scaleV) > 0.5D;
			final boolean isMaterialA = isU ^ isV;
			
			return isMaterialA ? this.materialA.compute(intersection) : this.materialB.compute(intersection);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final class ConductorFresnel extends Fresnel {
		private final Color3D etaI;
		private final Color3D etaT;
		private final Color3D k;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public ConductorFresnel(final Color3D etaI, final Color3D etaT, final Color3D k) {
			this.etaI = Objects.requireNonNull(etaI, "etaI == null");
			this.etaT = Objects.requireNonNull(etaT, "etaT == null");
			this.k = Objects.requireNonNull(k, "k == null");
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		@Override
		public Color3D evaluate(final double cosThetaI) {
			return evaluateConductor(Doubles.abs(cosThetaI), this.etaI, this.etaT, this.k);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final class DielectricFresnel extends Fresnel {
		private final double etaI;
		private final double etaT;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public DielectricFresnel(final double etaI, final double etaT) {
			this.etaI = etaI;
			this.etaT = etaT;
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		@Override
		public Color3D evaluate(final double cosThetaI) {
			return new Color3D(evaluateDielectric(cosThetaI, this.etaI, this.etaT));
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static abstract class Fresnel {
		protected Fresnel() {
			
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public abstract Color3D evaluate(final double cosThetaI);
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public static Color3D evaluateConductor(final double cosThetaI, final Color3D etaI, final Color3D etaT, final Color3D k) {
			final double saturateCosThetaI = Doubles.saturate(cosThetaI, -1.0D, 1.0D);
			final double saturateCosThetaIMultipliedBy2 = saturateCosThetaI * 2.0D;
			
			final double etaR = etaT.r / etaI.r;
			final double etaG = etaT.g / etaI.g;
			final double etaB = etaT.b / etaI.b;
			
			final double etaKR = k.r / etaI.r;
			final double etaKG = k.g / etaI.g;
			final double etaKB = k.b / etaI.b;
			
			final double cosThetaISquared = saturateCosThetaI * saturateCosThetaI;
			final double sinThetaISquared = 1.0D - cosThetaISquared;
			final double sinThetaISquaredSquared = sinThetaISquared * sinThetaISquared;
			
			final double etaSquaredR = etaR * etaR;
			final double etaSquaredG = etaG * etaG;
			final double etaSquaredB = etaB * etaB;
			
			final double etaKSquaredR = etaKR * etaKR;
			final double etaKSquaredG = etaKG * etaKG;
			final double etaKSquaredB = etaKB * etaKB;
			
			final double t0R = etaSquaredR - etaKSquaredR - sinThetaISquared;
			final double t0G = etaSquaredG - etaKSquaredG - sinThetaISquared;
			final double t0B = etaSquaredB - etaKSquaredB - sinThetaISquared;
			
			final double t0SquaredR = t0R * t0R;
			final double t0SquaredG = t0G * t0G;
			final double t0SquaredB = t0B * t0B;
			
			final double aSquaredPlusBSquaredR = Doubles.sqrt(t0SquaredR + etaSquaredR * etaKSquaredR * 4.0D);
			final double aSquaredPlusBSquaredG = Doubles.sqrt(t0SquaredG + etaSquaredG * etaKSquaredG * 4.0D);
			final double aSquaredPlusBSquaredB = Doubles.sqrt(t0SquaredB + etaSquaredB * etaKSquaredB * 4.0D);
			
			final double t1R = aSquaredPlusBSquaredR + cosThetaISquared;
			final double t1G = aSquaredPlusBSquaredG + cosThetaISquared;
			final double t1B = aSquaredPlusBSquaredB + cosThetaISquared;
			
			final double t2R = Doubles.sqrt((aSquaredPlusBSquaredR + t0R) * 0.5D) * saturateCosThetaIMultipliedBy2;
			final double t2G = Doubles.sqrt((aSquaredPlusBSquaredG + t0G) * 0.5D) * saturateCosThetaIMultipliedBy2;
			final double t2B = Doubles.sqrt((aSquaredPlusBSquaredB + t0B) * 0.5D) * saturateCosThetaIMultipliedBy2;
			
			final double t3R = aSquaredPlusBSquaredR * cosThetaISquared + sinThetaISquaredSquared;
			final double t3G = aSquaredPlusBSquaredG * cosThetaISquared + sinThetaISquaredSquared;
			final double t3B = aSquaredPlusBSquaredB * cosThetaISquared + sinThetaISquaredSquared;
			
			final double t4R = t2R * sinThetaISquared;
			final double t4G = t2G * sinThetaISquared;
			final double t4B = t2B * sinThetaISquared;
			
			final double reflectanceSR = (t1R - t2R) / (t1R + t2R);
			final double reflectanceSG = (t1G - t2G) / (t1G + t2G);
			final double reflectanceSB = (t1B - t2B) / (t1B + t2B);
			
			final double reflectancePR = reflectanceSR * (t3R - t4R) / (t3R + t4R);
			final double reflectancePG = reflectanceSG * (t3G - t4G) / (t3G + t4G);
			final double reflectancePB = reflectanceSB * (t3B - t4B) / (t3B + t4B);
			
			final double reflectanceR = (reflectancePR + reflectanceSR) * 0.5D;
			final double reflectanceG = (reflectancePG + reflectanceSG) * 0.5D;
			final double reflectanceB = (reflectancePB + reflectanceSB) * 0.5D;
			
			return new Color3D(reflectanceR, reflectanceG, reflectanceB);
		}
		
		public static Color3D evaluateDielectricSchlick(final double cosTheta, final Color3D r0) {
			return Color3D.add(r0, Color3D.multiply(Color3D.subtract(Color3D.WHITE, r0), Doubles.pow5(1.0D - cosTheta)));
		}
		
		public static double evaluateDielectric(final double cosThetaI, final double etaI, final double etaT) {
			final double saturateCosThetaI = Doubles.saturate(cosThetaI, -1.0D, 1.0D);
			
			final boolean isEntering = saturateCosThetaI > 0.0D;
			
			final double currentCosThetaI = isEntering ? saturateCosThetaI : Doubles.abs(saturateCosThetaI);
			final double currentEtaI = isEntering ? etaI : etaT;
			final double currentEtaT = isEntering ? etaT : etaI;
			
			final double currentSinThetaI = Doubles.sqrt(Doubles.max(0.0D, 1.0D - currentCosThetaI * currentCosThetaI));
			final double currentSinThetaT = currentEtaI / currentEtaT * currentSinThetaI;
			
			if(currentSinThetaT >= 1.0D) {
				return 1.0D;
			}
			
			final double currentCosThetaT = Doubles.sqrt(Doubles.max(0.0D, 1.0D - currentSinThetaT * currentSinThetaT));
			
			final double reflectancePara = ((currentEtaT * currentCosThetaI) - (currentEtaI * currentCosThetaT)) / ((currentEtaT * currentCosThetaI) + (currentEtaI * currentCosThetaT));
			final double reflectancePerp = ((currentEtaI * currentCosThetaI) - (currentEtaT * currentCosThetaT)) / ((currentEtaI * currentCosThetaI) + (currentEtaT * currentCosThetaT));
			final double reflectance = (reflectancePara * reflectancePara + reflectancePerp * reflectancePerp) / 2.0D;
			
			return reflectance;
		}
		
		public static double evaluateDielectricSchlick(final double cosTheta, final double r0) {
			return r0 + (1.0D - r0) * Doubles.pow5(Doubles.saturate(1.0D - cosTheta));
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final class GlassMaterial extends Material {
		public GlassMaterial() {
			
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		@Override
		public Optional<Result> compute(final Intersection intersection) {
			final Vector3D direction = intersection.getRayWS().getDirection();
			
			final Vector3D surfaceNormal = intersection.getSurfaceNormalWS();
			final Vector3D surfaceNormalCorrectlyOriented = Vector3D.orientNormalNegated(direction, surfaceNormal);
			
			final boolean isEntering = Vector3D.dotProduct(surfaceNormal, surfaceNormalCorrectlyOriented) > 0.0D;
			
			final double etaA = 1.0D;
			final double etaB = 1.5D;
			final double etaI = isEntering ? etaA : etaB;
			final double etaT = isEntering ? etaB : etaA;
			final double eta = etaI / etaT;
			
			final Point3D reflectionOrigin = intersection.getSurfaceIntersectionPointWS();
			
			final Vector3D reflectionDirection = Vector3D.reflection(direction, surfaceNormal, true);
			
			final Ray3D reflectionRay = new Ray3D(reflectionOrigin, reflectionDirection);
			
			final Optional<Vector3D> optionalTransmissionDirection = Vector3D.refraction(direction, surfaceNormalCorrectlyOriented, eta);
			
			if(optionalTransmissionDirection.isPresent()) {
				final Point3D transmissionOrigin = intersection.getSurfaceIntersectionPointWS();
				
				final Vector3D transmissionDirection = optionalTransmissionDirection.get();
				
				final Ray3D transmissionRay = new Ray3D(transmissionOrigin, transmissionDirection);
				
				final double cosThetaI = Vector3D.dotProduct(direction, surfaceNormalCorrectlyOriented);
				final double cosThetaICorrectlyOriented = isEntering ? -cosThetaI : Vector3D.dotProduct(transmissionDirection, surfaceNormal);
				
				final double r0 = (etaB - etaA) * (etaB - etaA) / ((etaB + etaA) * (etaB + etaA));
				
				final double reflectance = Fresnel.evaluateDielectricSchlick(cosThetaICorrectlyOriented, r0);
				final double transmittance = 1.0D - reflectance;
				
				final double probabilityRussianRoulette = 0.25D + 0.5D * reflectance;
				final double probabilityRussianRouletteReflection = reflectance / probabilityRussianRoulette;
				final double probabilityRussianRouletteTransmission = transmittance / (1.0D - probabilityRussianRoulette);
				
				final boolean isChoosingSpecularReflection = Randoms.nextDouble() < probabilityRussianRoulette;
				
				if(isChoosingSpecularReflection) {
					return Optional.of(new Result(Color3D.BLACK, new Color3D(probabilityRussianRouletteReflection), reflectionRay));
				}
				
				return Optional.of(new Result(Color3D.BLACK, new Color3D(probabilityRussianRouletteTransmission), transmissionRay));
			}
			
			return Optional.of(new Result(Color3D.BLACK, Color3D.WHITE, reflectionRay));
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final class GlossyMaterial extends Material {
		private final Texture textureEmission;
		private final Texture textureKR;
		private final Texture textureRoughness;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public GlossyMaterial(final Texture textureKR, final Texture textureRoughness, final Texture textureEmission) {
			this.textureKR = Objects.requireNonNull(textureKR, "textureKR == null");
			this.textureRoughness = Objects.requireNonNull(textureRoughness, "textureRoughness == null");
			this.textureEmission = Objects.requireNonNull(textureEmission, "textureEmission == null");
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		@Override
		public Optional<Result> compute(final Intersection intersection) {
			final Color3D colorKR = this.textureKR.compute(intersection);
			
			final double roughness = this.textureRoughness.compute(intersection).average();
			
			final BSDF bSDF = new BSDF(new AshikhminShirleyBRDF(colorKR, roughness));
			
			return bSDF.compute(intersection, this.textureEmission.compute(intersection));
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final class LambertianBRDF implements BXDF {
		private final Color3D r;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public LambertianBRDF(final Color3D r) {
			this.r = Objects.requireNonNull(r, "r == null");
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		@Override
		public Color3D evaluateDF(final Vector3D o, final Vector3D i) {
			return Color3D.divide(this.r, Doubles.PI);
		}
		
		@Override
		public Optional<BXDFResult> sampleDF(final Vector3D o, final Point2D p) {
			final Vector3D i = Vector3D.sampleHemisphereCosineDistribution(p);
			final Vector3D iCorrectlyOriented = o.z < 0.0D ? Vector3D.negateZ(i) : i;
			
			final Color3D result = evaluateDF(o, i);
			
			final double pDF = evaluatePDF(o, i);
			
			return Optional.of(new BXDFResult(result, iCorrectlyOriented, pDF));
		}
		
		@Override
		public double evaluatePDF(final Vector3D o, final Vector3D i) {
			return Vector3D.sameHemisphereZ(o, i) ? i.cosThetaAbs() / Doubles.PI : 0.0D;
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final class MatteMaterial extends Material {
		private final Texture textureEmission;
		private final Texture textureKD;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public MatteMaterial(final Texture textureKD, final Texture textureEmission) {
			this.textureKD = Objects.requireNonNull(textureKD, "textureKD == null");
			this.textureEmission = Objects.requireNonNull(textureEmission, "textureEmission == null");
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		@Override
		public Optional<Result> compute(final Intersection intersection) {
			final Vector3D s = Vector3D.sampleHemisphereCosineDistribution();
			final Vector3D w = Vector3D.orientNormalNegated(intersection.getRayWS().getDirection(), intersection.getSurfaceNormalWS());
			final Vector3D u = Vector3D.normalize(Vector3D.crossProduct(Doubles.abs(w.x) > 0.1D ? Vector3D.y() : Vector3D.x(), w));
			final Vector3D v = Vector3D.normalize(Vector3D.crossProduct(w, u));
			
			final Color3D colorEmission = this.textureEmission.compute(intersection);
			final Color3D colorKD = this.textureKD.compute(intersection);
			
			return Optional.of(new Result(colorEmission, colorKD, new Ray3D(intersection.getSurfaceIntersectionPointWS(), Vector3D.directionNormalized(u, v, w, s))));
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final class MetalMaterial extends Material {
		private final Texture textureEmission;
		private final Texture textureEta;
		private final Texture textureK;
		private final Texture textureRoughnessU;
		private final Texture textureRoughnessV;
		private final boolean isRemappingRoughness;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public MetalMaterial(final Texture textureK, final Texture textureEta, final Texture textureRoughnessU, final Texture textureRoughnessV, final boolean isRemappingRoughness, final Texture textureEmission) {
			this.textureK = Objects.requireNonNull(textureK, "textureK == null");
			this.textureEta = Objects.requireNonNull(textureEta, "textureEta == null");
			this.textureRoughnessU = Objects.requireNonNull(textureRoughnessU, "textureRoughnessU == null");
			this.textureRoughnessV = Objects.requireNonNull(textureRoughnessV, "textureRoughnessV == null");
			this.isRemappingRoughness = isRemappingRoughness;
			this.textureEmission = Objects.requireNonNull(textureEmission, "textureEmission == null");
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		@Override
		public Optional<Result> compute(final Intersection intersection) {
			final Color3D colorEta = this.textureEta.compute(intersection);
			final Color3D colorK = this.textureK.compute(intersection);
			
			final double roughnessUComputed = this.textureRoughnessU.compute(intersection).average();
			final double roughnessVComputed = this.textureRoughnessV.compute(intersection).average();
			
			final double roughnessU = this.isRemappingRoughness ? MicrofacetDistribution.convertRoughnessToAlpha(roughnessUComputed) : roughnessUComputed;
			final double roughnessV = this.isRemappingRoughness ? MicrofacetDistribution.convertRoughnessToAlpha(roughnessVComputed) : roughnessVComputed;
			
			final Fresnel fresnel = new ConductorFresnel(Color3D.WHITE, colorEta, colorK);
			
			final MicrofacetDistribution microfacetDistribution = new TrowbridgeReitzMicrofacetDistribution(true, false, roughnessU, roughnessV);
			
			final BSDF bSDF = new BSDF(new TorranceSparrowBRDF(Color3D.WHITE, fresnel, microfacetDistribution));
			
			return bSDF.compute(intersection, this.textureEmission.compute(intersection));
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static abstract class MicrofacetDistribution {
		private final boolean isSamplingVisibleArea;
		private final boolean isSeparableModel;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		protected MicrofacetDistribution(final boolean isSamplingVisibleArea, final boolean isSeparableModel) {
			this.isSamplingVisibleArea = isSamplingVisibleArea;
			this.isSeparableModel = isSeparableModel;
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public abstract Vector3D sampleH(final Vector3D o, final Point2D p);
		
		public final boolean isSamplingVisibleArea() {
			return this.isSamplingVisibleArea;
		}
		
		public final boolean isSeparableModel() {
			return this.isSeparableModel;
		}
		
		public abstract double computeDifferentialArea(final Vector3D h);
		
		public abstract double computeLambda(final Vector3D o);
		
		public final double computePDF(final Vector3D o, final Vector3D h) {
			return isSamplingVisibleArea() ? computeDifferentialArea(h) * computeShadowingAndMasking(o) * Vector3D.dotProductAbs(o, h) / o.cosThetaAbs() : computeDifferentialArea(h) * h.cosThetaAbs();
		}
		
		public final double computeShadowingAndMasking(final Vector3D o) {
			return 1.0D / (1.0D + computeLambda(o));
		}
		
		public final double computeShadowingAndMasking(final Vector3D o, final Vector3D i) {
			return isSeparableModel() ? computeShadowingAndMasking(o) * computeShadowingAndMasking(i) : 1.0D / (1.0D + computeLambda(o) + computeLambda(i));
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public static double convertRoughnessToAlpha(final double roughness) {
			final double x = Doubles.log(Doubles.max(roughness, 1.0e-3D));
			final double y = 1.62142D + 0.819955D * x + 0.1734D * x * x + 0.0171201D * x * x * x + 0.000640711D * x * x * x * x;
			
			return y;
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final class MirrorMaterial extends Material {
		private final Texture textureEmission;
		private final Texture textureKR;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public MirrorMaterial(final Texture textureKR, final Texture textureEmission) {
			this.textureKR = Objects.requireNonNull(textureKR, "textureKR == null");
			this.textureEmission = Objects.requireNonNull(textureEmission, "textureEmission == null");
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		@Override
		public Optional<Result> compute(final Intersection intersection) {
			final Color3D colorEmission = this.textureEmission.compute(intersection);
			final Color3D colorKR = this.textureKR.compute(intersection);
			
			return Optional.of(new Result(colorEmission, colorKR, new Ray3D(intersection.getSurfaceIntersectionPointWS(), Vector3D.reflection(intersection.getRayWS().getDirection(), intersection.getSurfaceNormalWS(), true))));
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final class PhongMaterial extends Material {
		private final Texture textureEmission;
		private final Texture textureKR;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public PhongMaterial(final Texture textureKR, final Texture textureEmission) {
			this.textureKR = Objects.requireNonNull(textureKR, "textureKR == null");
			this.textureEmission = Objects.requireNonNull(textureEmission, "textureEmission == null");
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		@Override
		public Optional<Result> compute(final Intersection intersection) {
			final Vector3D s = Vector3D.sampleHemispherePowerCosineDistribution();
			final Vector3D w = Vector3D.normalize(Vector3D.reflection(intersection.getRayWS().getDirection(), intersection.getSurfaceNormalWS(), true));
			final Vector3D u = Vector3D.orthogonal(w);
			final Vector3D v = Vector3D.normalize(Vector3D.crossProduct(w, u));
			
			final Color3D colorEmission = this.textureEmission.compute(intersection);
			final Color3D colorKR = this.textureKR.compute(intersection);
			
			return Optional.of(new Result(colorEmission, colorKR, new Ray3D(intersection.getSurfaceIntersectionPointWS(), Vector3D.directionNormalized(u, v, w, s))));
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final class PlasticMaterial extends Material {
		private final Texture textureEmission;
		private final Texture textureKD;
		private final Texture textureKS;
		private final Texture textureRoughness;
		private final boolean isRemappingRoughness;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public PlasticMaterial(final Texture textureKD, final Texture textureKS, final Texture textureRoughness, final boolean isRemappingRoughness, final Texture textureEmission) {
			this.textureKD = Objects.requireNonNull(textureKD, "textureKD == null");
			this.textureKS = Objects.requireNonNull(textureKS, "textureKS == null");
			this.textureRoughness = Objects.requireNonNull(textureRoughness, "textureRoughness == null");
			this.isRemappingRoughness = isRemappingRoughness;
			this.textureEmission = Objects.requireNonNull(textureEmission, "textureEmission == null");
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		@Override
		public Optional<Result> compute(final Intersection intersection) {
			final Color3D colorKD = Color3D.saturate(this.textureKD.compute(intersection), 0.0D, Doubles.MAX_VALUE);
			final Color3D colorKS = Color3D.saturate(this.textureKS.compute(intersection), 0.0D, Doubles.MAX_VALUE);
			
			final boolean hasColorKD = !colorKD.isBlack();
			final boolean hasColorKS = !colorKS.isBlack();
			
			if(hasColorKD || hasColorKS) {
				final List<BXDF> bXDFs = new ArrayList<>();
				
				if(hasColorKD) {
					bXDFs.add(new LambertianBRDF(colorKD));
				}
				
				if(hasColorKS) {
					final double roughnessComputed = this.textureRoughness.compute(intersection).average();
					final double roughness = this.isRemappingRoughness ? MicrofacetDistribution.convertRoughnessToAlpha(roughnessComputed) : roughnessComputed;
					
					bXDFs.add(new TorranceSparrowBRDF(colorKS, new DielectricFresnel(1.5D, 1.0D), new TrowbridgeReitzMicrofacetDistribution(true, false, roughness, roughness)));
				}
				
				final BSDF bSDF = new BSDF(bXDFs);
				
				return bSDF.compute(intersection, this.textureEmission.compute(intersection));
			}
			
			return Optional.empty();
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final class Schlick {
		private Schlick() {
			
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public static double fresnelDielectric(final double cosTheta, final double r0) {
			return r0 + (1.0D - r0) * fresnelWeight(cosTheta);
		}
		
		public static double fresnelWeight(final double cosTheta) {
			return Doubles.pow5(Doubles.saturate(1.0D - cosTheta));
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@SuppressWarnings("unused")
	private static final class SpecularBRDF implements BXDF {
		private final Color3D r;
		private final Fresnel fresnel;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public SpecularBRDF(final Color3D r, final Fresnel fresnel) {
			this.r = Objects.requireNonNull(r, "r == null");
			this.fresnel = Objects.requireNonNull(fresnel, "fresnel == null");
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		@Override
		public Color3D evaluateDF(final Vector3D o, final Vector3D i) {
			return Color3D.BLACK;
		}
		
		@Override
		public Optional<BXDFResult> sampleDF(final Vector3D o, final Point2D p) {
			final Vector3D i = new Vector3D(-o.x, -o.y, o.z);
			
			final Color3D result = Color3D.divide(Color3D.multiply(this.fresnel.evaluate(i.cosTheta()), this.r), i.cosThetaAbs());
			
			final float pDF = 1.0F;
			
			return Optional.of(new BXDFResult(result, i, pDF));
		}
		
		@Override
		public double evaluatePDF(final Vector3D o, final Vector3D i) {
			return 0.0D;
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@SuppressWarnings("unused")
	private static final class SpecularBTDF implements BXDF {
		private final Color3D t;
		private final Fresnel fresnel;
		private final double etaA;
		private final double etaB;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public SpecularBTDF(final Color3D t, final double etaA, final double etaB) {
			this.t = Objects.requireNonNull(t, "t == null");
			this.fresnel = new DielectricFresnel(etaA, etaB);
			this.etaA = etaA;
			this.etaB = etaB;
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		@Override
		public Color3D evaluateDF(final Vector3D o, final Vector3D i) {
			return Color3D.BLACK;
		}
		
		@Override
		public Optional<BXDFResult> sampleDF(final Vector3D o, final Point2D p) {
			final boolean isEntering = o.cosTheta() > 0.0D;
			
			final double etaI = isEntering ? this.etaA : this.etaB;
			final double etaT = isEntering ? this.etaB : this.etaA;
			
			final Optional<Vector3D> optionalI = Vector3D.refraction(o, Vector3D.orientNormal(o, Vector3D.z()), etaI / etaT);
			
			if(optionalI.isEmpty()) {
				return Optional.empty();
			}
			
			final Vector3D i = optionalI.get();
			
			final Color3D a = Color3D.WHITE;
			final Color3D b = this.fresnel.evaluate(i.cosTheta());
			final Color3D c = Color3D.subtract(a, b);
			final Color3D d = Color3D.multiply(this.t, c);
			final Color3D e = Color3D.multiply(d, (etaI * etaI) / (etaT * etaT));
			
			final Color3D result = Color3D.divide(e, i.cosThetaAbs());
			
			final double pDF = 1.0D;
			
			return Optional.of(new BXDFResult(result, i, pDF));
		}
		
		@Override
		public double evaluatePDF(final Vector3D o, final Vector3D i) {
			return 0.0D;
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final class SubstrateMaterial extends Material {
		private final Texture textureEmission;
		private final Texture textureKD;
		private final Texture textureKS;
		private final Texture textureRoughnessU;
		private final Texture textureRoughnessV;
		private final boolean isRemappingRoughness;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public SubstrateMaterial(final Texture textureKD, final Texture textureKS, final Texture textureRoughnessU, final Texture textureRoughnessV, final boolean isRemappingRoughness, final Texture textureEmission) {
			this.textureKD = Objects.requireNonNull(textureKD, "textureKD == null");
			this.textureKS = Objects.requireNonNull(textureKS, "textureKS == null");
			this.textureRoughnessU = Objects.requireNonNull(textureRoughnessU, "textureRoughnessU == null");
			this.textureRoughnessV = Objects.requireNonNull(textureRoughnessV, "textureRoughnessV == null");
			this.isRemappingRoughness = isRemappingRoughness;
			this.textureEmission = Objects.requireNonNull(textureEmission, "textureEmission == null");
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		@Override
		public Optional<Result> compute(final Intersection intersection) {
			final Color3D colorKD = Color3D.saturate(this.textureKD.compute(intersection), 0.0D, Doubles.MAX_VALUE);
			final Color3D colorKS = Color3D.saturate(this.textureKS.compute(intersection), 0.0D, Doubles.MAX_VALUE);
			
			if(!colorKD.isBlack() && !colorKS.isBlack()) {
				final double roughnessU = this.isRemappingRoughness ? MicrofacetDistribution.convertRoughnessToAlpha(this.textureRoughnessU.compute(intersection).average()) : this.textureRoughnessU.compute(intersection).average();
				final double roughnessV = this.isRemappingRoughness ? MicrofacetDistribution.convertRoughnessToAlpha(this.textureRoughnessV.compute(intersection).average()) : this.textureRoughnessV.compute(intersection).average();
				
				final MicrofacetDistribution microfacetDistribution = new TrowbridgeReitzMicrofacetDistribution(true, false, roughnessU, roughnessV);
				
				final OrthonormalBasis33D orthonormalBasis = intersection.getOrthonormalBasisWS();
				
				final Vector3D nWS = intersection.getSurfaceNormalWSCorrectlyOriented();
				final Vector3D oWS = Vector3D.negate(intersection.getRayWS().getDirection());
				final Vector3D oLS = orthonormalBasis.transformReverseNormalize(oWS);
				
				if(Doubles.isZero(oLS.z)) {
					return Optional.empty();
				}
				
				final double t = Doubles.min(Randoms.nextDouble(), 0.99999994D);
				final double u = t < 0.5D ? Doubles.min(2.0D * t, 0.99999994D) : Doubles.min(2.0D * (t - 0.5D), 0.99999994D);
				final double v = Randoms.nextDouble();
				
				final Point2D p = new Point2D(u, v);
				
				final Vector3D i = t < 0.5D ? Vector3D.sampleHemisphereCosineDistribution(p) : Vector3D.reflection(oLS, microfacetDistribution.sampleH(oLS, p));
				final Vector3D iLS = t < 0.5D && oLS.z < 0.0D ? Vector3D.negate(i) : i;
				final Vector3D iWS = orthonormalBasis.transformNormalize(iLS);
				
				if(!Vector3D.sameHemisphereZ(oLS, iLS)) {
					return Optional.empty();
				}
				
				final Vector3D hLS = Vector3D.add(oLS, iLS);
				
				if(hLS.isZero()) {
					return Optional.empty();
				}
				
				final Vector3D hLSNormalized = Vector3D.normalize(hLS);
				
				final double iDotH = Vector3D.dotProduct(iLS, hLSNormalized);
				final double iDotHAbs = Doubles.abs(iDotH);
				final double iDotNAbs = Vector3D.dotProductAbs(iWS, nWS);
				final double oDotH = Vector3D.dotProduct(oLS, hLSNormalized);
				
				final double cosThetaAbsI = iLS.cosThetaAbs();
				final double cosThetaAbsO = oLS.cosThetaAbs();
				
				final double probabilityDensityFunctionValue = 0.5D * (cosThetaAbsI / Doubles.PI + microfacetDistribution.computePDF(oLS, hLSNormalized) / (4.0D * oDotH));
				
				if(Doubles.isZero(probabilityDensityFunctionValue)) {
					return Optional.empty();
				}
				
				final double a = 28.0D / (23.0D * Doubles.PI);
				final double b = 1.0D - Doubles.pow5(1.0D - 0.5D * cosThetaAbsI);
				final double c = 1.0D - Doubles.pow5(1.0D - 0.5D * cosThetaAbsO);
				final double d = microfacetDistribution.computeDifferentialArea(hLSNormalized);
				final double e = 4.0D * iDotHAbs * Doubles.max(cosThetaAbsI, cosThetaAbsO);
				final double f = d / e;
				
				final Color3D colorFresnel = Fresnel.evaluateDielectricSchlick(iDotH, colorKS);
				
				final double resultR = colorKD.r * a * (1.0D - colorKS.r) * b * c + colorFresnel.r * f;
				final double resultG = colorKD.g * a * (1.0D - colorKS.g) * b * c + colorFresnel.g * f;
				final double resultB = colorKD.b * a * (1.0D - colorKS.b) * b * c + colorFresnel.b * f;
				
				if(Doubles.isZero(resultR) && Doubles.isZero(resultG) && Doubles.isZero(resultB)) {
					return Optional.empty();
				}
				
				final double reflectanceR = resultR * iDotNAbs / probabilityDensityFunctionValue;
				final double reflectanceG = resultG * iDotNAbs / probabilityDensityFunctionValue;
				final double reflectanceB = resultB * iDotNAbs / probabilityDensityFunctionValue;
				
				final Color3D emission = this.textureEmission.compute(intersection);
				final Color3D reflectance = new Color3D(reflectanceR, reflectanceG, reflectanceB);
				
				final Ray3D ray = new Ray3D(intersection.getSurfaceIntersectionPointWS(), iWS);
				
				return Optional.of(new Result(emission, reflectance, ray));
			}
			
			return Optional.empty();
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final class TorranceSparrowBRDF implements BXDF {
		private final Color3D r;
		private final Fresnel fresnel;
		private final MicrofacetDistribution microfacetDistribution;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public TorranceSparrowBRDF(final Color3D r, final Fresnel fresnel, final MicrofacetDistribution microfacetDistribution) {
			this.r = Objects.requireNonNull(r, "r == null");
			this.fresnel = Objects.requireNonNull(fresnel, "fresnel == null");
			this.microfacetDistribution = Objects.requireNonNull(microfacetDistribution, "microfacetDistribution == null");
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		@Override
		public Color3D evaluateDF(final Vector3D o, final Vector3D i) {
			final double cosThetaAbsO = o.cosThetaAbs();
			final double cosThetaAbsI = i.cosThetaAbs();
			
			if(Doubles.isZero(cosThetaAbsO) || Doubles.isZero(cosThetaAbsI)) {
				return Color3D.BLACK;
			}
			
			final Vector3D h = Vector3D.add(o, i);
			
			if(h.isZero()) {
				return Color3D.BLACK;
			}
			
			final Vector3D hNormalized = Vector3D.normalize(h);
			final Vector3D hNormalizedCorrectlyOriented = Vector3D.orientNormal(Vector3D.z(), hNormalized);
			
			final double cosThetaI = Vector3D.dotProduct(i, hNormalizedCorrectlyOriented);
			
			final double d = this.microfacetDistribution.computeDifferentialArea(hNormalized);
			final double g = this.microfacetDistribution.computeShadowingAndMasking(o, i);
			
			final Color3D r = this.r;
			final Color3D f = this.fresnel.evaluate(cosThetaI);
			
			return Color3D.divide(Color3D.multiply(Color3D.multiply(Color3D.multiply(r, d), g), f), 4.0F * cosThetaAbsI * cosThetaAbsO);
		}
		
		@Override
		public Optional<BXDFResult> sampleDF(final Vector3D o, final Point2D p) {
			if(Doubles.isZero(o.z)) {
				return Optional.empty();
			}
			
			final Vector3D h = this.microfacetDistribution.sampleH(o, p);
			
			final double oDotH = Vector3D.dotProduct(o, h);
			
			if(oDotH < 0.0D) {
				return Optional.empty();
			}
			
			final Vector3D i = Vector3D.reflection(o, h);
			
			if(!Vector3D.sameHemisphereZ(o, i)) {
				return Optional.empty();
			}
			
			final Color3D result = evaluateDF(o, i);
			
			final double pDF = evaluatePDF(o, i);
			
			return Optional.of(new BXDFResult(result, i, pDF));
		}
		
		@Override
		public double evaluatePDF(final Vector3D o, final Vector3D i) {
			if(!Vector3D.sameHemisphereZ(o, i)) {
				return 0.0D;
			}
			
			final Vector3D h = Vector3D.normalize(Vector3D.add(o, i));
			
			final double oDotH = Vector3D.dotProduct(o, h);
			
			return this.microfacetDistribution.computePDF(o, h) / (4.0D * oDotH);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@SuppressWarnings("unused")
	private static final class TorranceSparrowBTDF implements BXDF {
		private final Color3D t;
		private final Fresnel fresnel;
		private final MicrofacetDistribution microfacetDistribution;
		private final double etaA;
		private final double etaB;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public TorranceSparrowBTDF(final Color3D t, final MicrofacetDistribution microfacetDistribution, final double etaA, final double etaB) {
			this.t = Objects.requireNonNull(t, "t == null");
			this.fresnel = new DielectricFresnel(etaA, etaB);
			this.microfacetDistribution = Objects.requireNonNull(microfacetDistribution, "microfacetDistribution == null");
			this.etaA = etaA;
			this.etaB = etaB;
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		@Override
		public Color3D evaluateDF(final Vector3D o, final Vector3D i) {
			if(Vector3D.sameHemisphereZ(o, i)) {
				return Color3D.BLACK;
			}
			
			final double cosThetaO = o.cosTheta();
			final double cosThetaI = i.cosTheta();
			
			if(Doubles.isZero(cosThetaO) || Doubles.isZero(cosThetaI)) {
				return Color3D.BLACK;
			}
			
			final double eta = cosThetaO > 0.0D ? this.etaB / this.etaA : this.etaA / this.etaB;
			
			final Vector3D hNormalized = Vector3D.normalize(Vector3D.add(Vector3D.multiply(i, eta), o));
			final Vector3D hNormalizedCorrectlyOriented = hNormalized.z < 0.0F ? Vector3D.negate(hNormalized) : hNormalized;
			
			final double oDotH = Vector3D.dotProduct(o, hNormalizedCorrectlyOriented);
			final double iDotH = Vector3D.dotProduct(i, hNormalizedCorrectlyOriented);
			
			if(oDotH * iDotH > 0.0D) {
				return Color3D.BLACK;
			}
			
			final Color3D f = this.fresnel.evaluate(oDotH);
			final Color3D t = this.t;
			
			final double a = oDotH + eta * iDotH;
			final double b = 1.0D / eta;
			
			final double d = this.microfacetDistribution.computeDifferentialArea(hNormalizedCorrectlyOriented);
			final double g = this.microfacetDistribution.computeShadowingAndMasking(o, i);
			
			return Color3D.multiply(Color3D.multiply(Color3D.subtract(Color3D.WHITE, f), t), Doubles.abs(d * g * eta * eta * Doubles.abs(iDotH) * Doubles.abs(oDotH) * b * b / (cosThetaI * cosThetaO * a * a)));
		}
		
		@Override
		public Optional<BXDFResult> sampleDF(final Vector3D o, final Point2D p) {
			if(Doubles.isZero(o.z)) {
				return Optional.empty();
			}
			
			final Vector3D h = this.microfacetDistribution.sampleH(o, p);
			
			final double oDotH = Vector3D.dotProduct(o, h);
			
			if(oDotH < 0.0D) {
				return Optional.empty();
			}
			
			final double eta = o.cosTheta() > 0.0D ? this.etaA / this.etaB : this.etaB / this.etaA;
			
			final Optional<Vector3D> optionalI = Vector3D.refraction(o, h, eta);
			
			if(optionalI.isEmpty()) {
				return Optional.empty();
			}
			
			final Vector3D i = optionalI.get();
			
			final Color3D result = evaluateDF(o, i);
			
			final double pDF = evaluatePDF(o, i);
			
			return Optional.of(new BXDFResult(result, i, pDF));
		}
		
		@Override
		public double evaluatePDF(final Vector3D o, final Vector3D i) {
			if(Vector3D.sameHemisphereZ(o, i)) {
				return 0.0D;
			}
			
			final double eta = o.cosTheta() > 0.0D ? this.etaB / this.etaA : this.etaA / this.etaB;
			
			final Vector3D h = Vector3D.normalize(Vector3D.add(Vector3D.multiply(i, eta), o));
			
			final double oDotH = Vector3D.dotProduct(o, h);
			final double iDotH = Vector3D.dotProduct(i, h);
			
			if(oDotH * iDotH > 0.0D) {
				return 0.0D;
			}
			
			final double a = oDotH + eta * iDotH;
			final double b = Doubles.abs((eta * eta * iDotH) / (a * a));
			
			return this.microfacetDistribution.computePDF(o, h) * b;
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final class TrowbridgeReitzMicrofacetDistribution extends MicrofacetDistribution {
		private final double alphaX;
		private final double alphaY;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public TrowbridgeReitzMicrofacetDistribution(final boolean isSamplingVisibleArea, final boolean isSeparableModel, final double alphaX, final double alphaY) {
			super(isSamplingVisibleArea, isSeparableModel);
			
			this.alphaX = Doubles.max(alphaX, 0.001D);
			this.alphaY = Doubles.max(alphaY, 0.001D);
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		@Override
		public Vector3D sampleH(final Vector3D o, final Point2D p) {
			if(isSamplingVisibleArea()) {
				return o.z >= 0.0D ? doSample(o, p) : Vector3D.negate(doSample(Vector3D.negate(o), p));
			} else if(Doubles.equals(this.alphaX, this.alphaY)) {
				final double phi = p.y * 2.0D * Doubles.PI;
				final double cosTheta = 1.0D / Doubles.sqrt(1.0D + (this.alphaX * this.alphaX * p.x / (1.0D - p.x)));
				final double sinTheta = Doubles.sqrt(Doubles.max(0.0D, 1.0D - cosTheta * cosTheta));
				
				return Vector3D.orientNormalSameHemisphereZ(o, Vector3D.directionSpherical(sinTheta, cosTheta, phi));
			} else {
				final double phi = Doubles.atan(this.alphaY / this.alphaX * Doubles.tan(2.0D * Doubles.PI * p.y + 0.5D * Doubles.PI)) + (p.y > 0.5D ? Doubles.PI : 0.0D);
				final double cosTheta = 1.0D / Doubles.sqrt(1.0D + ((1.0D / (Doubles.pow2(Doubles.cos(phi)) / (this.alphaX * this.alphaX) + Doubles.pow2(Doubles.sin(phi)) / (this.alphaY * this.alphaY))) * p.x / (1.0D - p.x)));
				final double sinTheta = Doubles.sqrt(Doubles.max(0.0D, 1.0D - cosTheta * cosTheta));
				
				return Vector3D.orientNormalSameHemisphereZ(o, Vector3D.directionSpherical(sinTheta, cosTheta, phi));
			}
		}
		
		@Override
		public double computeDifferentialArea(final Vector3D h) {
			final double tanThetaSquared = h.tanThetaSquared();
			
			if(Doubles.isInfinite(tanThetaSquared)) {
				return 0.0D;
			}
			
			return 1.0D / (Doubles.PI * this.alphaX * this.alphaY * h.cosThetaQuartic() * Doubles.pow2(1.0D + (h.cosPhiSquared() / (this.alphaX * this.alphaX) + h.sinPhiSquared() / (this.alphaY * this.alphaY)) * tanThetaSquared));
		}
		
		@Override
		public double computeLambda(final Vector3D o) {
			final double tanThetaAbs = o.tanThetaAbs();
			
			if(Doubles.isInfinite(tanThetaAbs)) {
				return 0.0D;
			}
			
			return (-1.0D + Doubles.sqrt(1.0D + Doubles.pow2(Doubles.sqrt(o.cosPhiSquared() * this.alphaX * this.alphaX + o.sinPhiSquared() * this.alphaY * this.alphaY) * tanThetaAbs))) / 2.0D;
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		private Vector3D doSample(final Vector3D i, final Point2D p) {
			final Vector3D iStretched = Vector3D.normalize(new Vector3D(i.x * this.alphaX, i.y * this.alphaY, i.z));
			
			final double cosPhi = iStretched.cosPhi();
			final double cosTheta = iStretched.cosTheta();
			final double sinPhi = iStretched.sinPhi();
			
			if(cosTheta > 0.9999D) {
				final double r = Doubles.sqrt(p.x / (1.0D - p.x));
				final double phi = 2.0D * Doubles.PI * p.y;
				
				final double slopeX = r * Doubles.cos(phi);
				final double slopeY = r * Doubles.sin(phi);
				
				return Vector3D.normalize(new Vector3D(-((cosPhi * slopeX - sinPhi * slopeY) * this.alphaX), -((sinPhi * slopeX + cosPhi * slopeY) * this.alphaY), 1.0D));
			}
			
			final double a = Doubles.sqrt(Doubles.max(0.0D, 1.0D - cosTheta * cosTheta)) / cosTheta;
			final double b = 2.0D * p.x / (2.0D / (1.0D + Doubles.sqrt(1.0D + a * a))) - 1.0D;
			final double c = Doubles.min(1.0D / (b * b - 1.0D), 1.0e10D);
			final double d = Doubles.sqrt(Doubles.max(a * a * c * c - (b * b - a * a) * c, 0.0D));
			final double e = a * c + d;
			final double f = p.y > 0.5D ? 2.0D * (p.y - 0.5D) : 2.0D * (0.5D - p.y);
			
			final double slopeX = b < 0.0D || e > 1.0D / a ? a * c - d : e;
			final double slopeY = (p.y > 0.5D ? 1.0D : -1.0D) * (f * (f * (f * 0.27385D - 0.73369D) + 0.46341D)) / (f * (f * (f * 0.093073D + 0.309420D) - 1.0D) + 0.597999D) * Doubles.sqrt(1.0D + this.alphaX * this.alphaX);
			
			return Vector3D.normalize(new Vector3D(-((cosPhi * slopeX - sinPhi * slopeY) * this.alphaX), -((sinPhi * slopeX + cosPhi * slopeY) * this.alphaY), 1.0D));
		}
	}
}