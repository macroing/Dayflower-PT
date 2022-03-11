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

import java.util.Objects;
import java.util.Optional;

public abstract class Material {
	protected Material() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public abstract Optional<Result> compute(final Intersection intersection);
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static Material checkerboard(final Material materialA, final Material materialB, final double angleDegrees, final double scaleU, final double scaleV) {
		Objects.requireNonNull(materialA, "materialA == null");
		Objects.requireNonNull(materialB, "materialB == null");
		
		return new CheckerboardMaterial(materialA, materialB, angleDegrees, scaleU, scaleV);
	}
	
	public static Material glass() {
		return new GlassMaterial();
	}
	
	public static Material matte(final Texture textureKD) {
		return matte(textureKD, Texture.constant(Color3D.BLACK));
	}
	
	public static Material matte(final Texture textureKD, final Texture textureEmission) {
		Objects.requireNonNull(textureKD, "textureKD == null");
		Objects.requireNonNull(textureEmission, "textureEmission == null");
		
		return new MatteMaterial(textureKD, textureEmission);
	}
	
	public static Material metal() {
		return metal(Texture.constant(Color3D.AU_K), Texture.constant(Color3D.AU_ETA), Texture.constant(new Color3D(0.01D)), Texture.constant(new Color3D(0.01D)), true, Texture.constant(Color3D.BLACK));
	}
	
	public static Material metal(final Texture textureK, final Texture textureEta, final Texture textureRoughnessU, final Texture textureRoughnessV, final boolean isRemappingRoughness, final Texture textureEmission) {
		Objects.requireNonNull(textureK, "textureK == null");
		Objects.requireNonNull(textureEta, "textureEta == null");
		Objects.requireNonNull(textureRoughnessU, "textureRoughnessU == null");
		Objects.requireNonNull(textureRoughnessV, "textureRoughnessV == null");
		Objects.requireNonNull(textureEmission, "textureEmission == null");
		
		return new MetalMaterial(textureK, textureEta, textureRoughnessU, textureRoughnessV, isRemappingRoughness, textureEmission);
	}
	
	public static Material mirror(final Texture textureKR) {
		return mirror(textureKR, Texture.constant(Color3D.BLACK));
	}
	
	public static Material mirror(final Texture textureKR, final Texture textureEmission) {
		Objects.requireNonNull(textureKR, "textureKR == null");
		Objects.requireNonNull(textureEmission, "textureEmission == null");
		
		return new MirrorMaterial(textureKR, textureEmission);
	}
	
	public static Material phong(final Texture textureKR) {
		return phong(textureKR, Texture.constant(Color3D.BLACK));
	}
	
	public static Material phong(final Texture textureKR, final Texture textureEmission) {
		Objects.requireNonNull(textureKR, "textureKR == null");
		Objects.requireNonNull(textureEmission, "textureEmission == null");
		
		return new PhongMaterial(textureKR, textureEmission);
	}
	
	public static Material plastic() {
		return plastic(Texture.constant(new Color3D(0.05D, 0.05D, 1.0D)), Texture.constant(new Color3D(0.25D)), Texture.constant(new Color3D(0.1D)), true, Texture.constant(Color3D.BLACK));
	}
	
	public static Material plastic(final Texture textureKD, final Texture textureKS, final Texture textureRoughness, final boolean isRemappingRoughness, final Texture textureEmission) {
		Objects.requireNonNull(textureKD, "textureKD == null");
		Objects.requireNonNull(textureKS, "textureKS == null");
		Objects.requireNonNull(textureRoughness, "textureRoughness == null");
		Objects.requireNonNull(textureEmission, "textureEmission == null");
		
		return new PlasticMaterial(textureKD, textureKS, textureRoughness, isRemappingRoughness, textureEmission);
	}
	
	public static Material substrate() {
		return substrate(Texture.constant(new Color3D(1.0D, 0.2D, 0.2D)), Texture.constant(new Color3D(0.5D)), Texture.constant(new Color3D(0.1D)), Texture.constant(new Color3D(0.1D)), true, Texture.constant(Color3D.BLACK));
	}
	
	public static Material substrate(final Texture textureKD, final Texture textureKS, final Texture textureRoughnessU, final Texture textureRoughnessV, final boolean isRemappingRoughness, final Texture textureEmission) {
		Objects.requireNonNull(textureKD, "textureKD == null");
		Objects.requireNonNull(textureKS, "textureKS == null");
		Objects.requireNonNull(textureRoughnessU, "textureRoughnessU == null");
		Objects.requireNonNull(textureRoughnessV, "textureRoughnessV == null");
		Objects.requireNonNull(textureEmission, "textureEmission == null");
		
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
			this.angleRadians = Math.toRadians(this.angleDegrees);
			this.angleRadiansCos = Math.cos(this.angleRadians);
			this.angleRadiansSin = Math.sin(this.angleRadians);
			this.scaleU = scaleU;
			this.scaleV = scaleV;
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		@Override
		public Optional<Result> compute(final Intersection intersection) {
			final double u = intersection.getTextureCoordinates().u;
			final double v = intersection.getTextureCoordinates().v;
			
			final boolean isU = Math.fractionalPart((u * this.angleRadiansCos - v * this.angleRadiansSin) * this.scaleU) > 0.5D;
			final boolean isV = Math.fractionalPart((v * this.angleRadiansCos + u * this.angleRadiansSin) * this.scaleV) > 0.5D;
			final boolean isMaterialA = isU ^ isV;
			
			return isMaterialA ? this.materialA.compute(intersection) : this.materialB.compute(intersection);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final class Fresnel {
		private Fresnel() {
			
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public static Color3D evaluateConductor(final double cosThetaI, final Color3D etaI, final Color3D etaT, final Color3D k) {
			final double saturateCosThetaI = Math.saturate(cosThetaI, -1.0D, 1.0D);
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
			
			final double aSquaredPlusBSquaredR = Math.sqrt(t0SquaredR + etaSquaredR * etaKSquaredR * 4.0D);
			final double aSquaredPlusBSquaredG = Math.sqrt(t0SquaredG + etaSquaredG * etaKSquaredG * 4.0D);
			final double aSquaredPlusBSquaredB = Math.sqrt(t0SquaredB + etaSquaredB * etaKSquaredB * 4.0D);
			
			final double t1R = aSquaredPlusBSquaredR + cosThetaISquared;
			final double t1G = aSquaredPlusBSquaredG + cosThetaISquared;
			final double t1B = aSquaredPlusBSquaredB + cosThetaISquared;
			
			final double t2R = Math.sqrt((aSquaredPlusBSquaredR + t0R) * 0.5D) * saturateCosThetaIMultipliedBy2;
			final double t2G = Math.sqrt((aSquaredPlusBSquaredG + t0G) * 0.5D) * saturateCosThetaIMultipliedBy2;
			final double t2B = Math.sqrt((aSquaredPlusBSquaredB + t0B) * 0.5D) * saturateCosThetaIMultipliedBy2;
			
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
			return Color3D.add(r0, Color3D.multiply(Color3D.subtract(Color3D.WHITE, r0), Math.pow5(1.0D - cosTheta)));
		}
		
		public static double evaluateDielectric(final double cosThetaI, final double etaI, final double etaT) {
			final double saturateCosThetaI = Math.saturate(cosThetaI, -1.0D, 1.0D);
			
			final boolean isEntering = saturateCosThetaI > 0.0D;
			
			final double currentCosThetaI = isEntering ? saturateCosThetaI : Math.abs(saturateCosThetaI);
			final double currentEtaI = isEntering ? etaI : etaT;
			final double currentEtaT = isEntering ? etaT : etaI;
			
			final double currentSinThetaI = Math.sqrt(Math.max(0.0D, 1.0D - currentCosThetaI * currentCosThetaI));
			final double currentSinThetaT = currentEtaI / currentEtaT * currentSinThetaI;
			
			if(currentSinThetaT >= 1.0D) {
				return 1.0D;
			}
			
			final double currentCosThetaT = Math.sqrt(Math.max(0.0D, 1.0D - currentSinThetaT * currentSinThetaT));
			
			final double reflectancePara = ((currentEtaT * currentCosThetaI) - (currentEtaI * currentCosThetaT)) / ((currentEtaT * currentCosThetaI) + (currentEtaI * currentCosThetaT));
			final double reflectancePerp = ((currentEtaI * currentCosThetaI) - (currentEtaT * currentCosThetaT)) / ((currentEtaI * currentCosThetaI) + (currentEtaT * currentCosThetaT));
			final double reflectance = (reflectancePara * reflectancePara + reflectancePerp * reflectancePerp) / 2.0D;
			
			return reflectance;
		}
		
		public static double evaluateDielectricSchlick(final double cosTheta, final double r0) {
			return r0 + (1.0D - r0) * Math.pow5(Math.saturate(1.0D - cosTheta));
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final class GlassMaterial extends Material {
		public GlassMaterial() {
			
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		@Override
		public Optional<Result> compute(final Intersection intersection) {
			final Vector3D direction = intersection.getRay().getDirection();
			
			final Vector3D surfaceNormal = intersection.getSurfaceNormal();
			final Vector3D surfaceNormalCorrectlyOriented = Vector3D.orientNormal(direction, surfaceNormal);
			
			final boolean isEntering = Vector3D.dotProduct(surfaceNormal, surfaceNormalCorrectlyOriented) > 0.0D;
			
			final double etaA = 1.0D;
			final double etaB = 1.5D;
			final double etaI = isEntering ? etaA : etaB;
			final double etaT = isEntering ? etaB : etaA;
			final double eta = etaI / etaT;
			
			final Point3D reflectionOrigin = intersection.getSurfaceIntersectionPoint();
			
			final Vector3D reflectionDirection = Vector3D.reflection(direction, surfaceNormal, true);
			
			final Ray3D reflectionRay = new Ray3D(reflectionOrigin, reflectionDirection);
			
			final Optional<Vector3D> optionalTransmissionDirection = Vector3D.refraction(direction, surfaceNormalCorrectlyOriented, eta);
			
			if(optionalTransmissionDirection.isPresent()) {
				final Point3D transmissionOrigin = intersection.getSurfaceIntersectionPoint();
				
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
				
				final boolean isChoosingSpecularReflection = Math.random() < probabilityRussianRoulette;
				
				if(isChoosingSpecularReflection) {
					return Optional.of(new Result(Color3D.BLACK, new Color3D(probabilityRussianRouletteReflection), reflectionRay));
				}
				
				return Optional.of(new Result(Color3D.BLACK, new Color3D(probabilityRussianRouletteTransmission), transmissionRay));
			}
			
			return Optional.of(new Result(Color3D.BLACK, Color3D.WHITE, reflectionRay));
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
			final Vector3D w = Vector3D.orientNormal(intersection.getRay().getDirection(), intersection.getSurfaceNormal());
			final Vector3D u = Vector3D.normalize(Vector3D.crossProduct(Math.abs(w.x) > 0.1D ? Vector3D.y() : Vector3D.x(), w));
			final Vector3D v = Vector3D.normalize(Vector3D.crossProduct(w, u));
			
			final Color3D colorEmission = this.textureEmission.compute(intersection);
			final Color3D colorKD = this.textureKD.compute(intersection);
			
			return Optional.of(new Result(colorEmission, colorKD, new Ray3D(intersection.getSurfaceIntersectionPoint(), Vector3D.directionNormalized(u, v, w, s))));
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
			
			final double roughnessU = this.isRemappingRoughness ? MicrofacetDistribution.convertRoughnessToAlpha(this.textureRoughnessU.compute(intersection).average()) : this.textureRoughnessU.compute(intersection).average();
			final double roughnessV = this.isRemappingRoughness ? MicrofacetDistribution.convertRoughnessToAlpha(this.textureRoughnessV.compute(intersection).average()) : this.textureRoughnessV.compute(intersection).average();
			
			final MicrofacetDistribution microfacetDistribution = new TrowbridgeReitzMicrofacetDistribution(true, false, roughnessU, roughnessV);
			
			final OrthonormalBasis33D orthonormalBasis = intersection.getOrthonormalBasis();
			
			final Vector3D nWS = intersection.getSurfaceNormalCorrectlyOriented();
			final Vector3D oWS = Vector3D.negate(intersection.getRay().getDirection());
			final Vector3D oLS = Vector3D.transformReverseNormalize(oWS, orthonormalBasis);
			
			if(Math.isZero(oLS.z)) {
				return Optional.empty();
			}
			
			final Vector3D hLS = microfacetDistribution.sampleH(oLS, new Point2D(Math.min(Math.random(), 0.99999994D), Math.random()));
			
			final double oDotH = Vector3D.dotProduct(oLS, hLS);
			
			if(oDotH < 0.0D) {
				return Optional.empty();
			}
			
			final Vector3D iLS = Vector3D.reflection(oLS, hLS);
			final Vector3D iWS = Vector3D.transformNormalize(iLS, orthonormalBasis);
			
			if(!Vector3D.sameHemisphereZ(oLS, iLS)) {
				return Optional.empty();
			}
			
			final double cosThetaAbsO = oLS.cosThetaAbs();
			final double cosThetaAbsI = iLS.cosThetaAbs();
			
			if(Math.isZero(cosThetaAbsO) || Math.isZero(cosThetaAbsI)) {
				return Optional.empty();
			}
			
			final double probabilityDensityFunctionValue = microfacetDistribution.computePDF(oLS, hLS) / (4.0D * oDotH);
			
			if(Math.isZero(probabilityDensityFunctionValue)) {
				return Optional.empty();
			}
			
			final double iDotHAbs = Vector3D.dotProductAbs(iLS, Vector3D.faceForward(hLS, Vector3D.z()));
			final double iDotNAbs = Vector3D.dotProductAbs(iWS, nWS);
			
			final Color3D colorFresnel = Fresnel.evaluateConductor(iDotHAbs, Color3D.WHITE, colorEta, colorK);
			
			final double a = microfacetDistribution.computeDifferentialArea(hLS);
			final double b = microfacetDistribution.computeShadowingAndMasking(oLS, iLS);
			final double c = 4.0D * cosThetaAbsI * cosThetaAbsO;
			
			final double resultR = colorFresnel.r * a * b / c;
			final double resultG = colorFresnel.g * a * b / c;
			final double resultB = colorFresnel.b * a * b / c;
			
			if(Math.isZero(resultR) && Math.isZero(resultG) && Math.isZero(resultB)) {
				return Optional.empty();
			}
			
			final double reflectanceR = resultR * iDotNAbs / probabilityDensityFunctionValue;
			final double reflectanceG = resultG * iDotNAbs / probabilityDensityFunctionValue;
			final double reflectanceB = resultB * iDotNAbs / probabilityDensityFunctionValue;
			
			final Color3D emission = this.textureEmission.compute(intersection);
			final Color3D reflectance = new Color3D(reflectanceR, reflectanceG, reflectanceB);
			
			final Ray3D ray = new Ray3D(intersection.getSurfaceIntersectionPoint(), iWS);
			
			return Optional.of(new Result(emission, reflectance, ray));
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
			final double x = Math.log(Math.max(roughness, 1.0e-3D));
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
			
			return Optional.of(new Result(colorEmission, colorKR, new Ray3D(intersection.getSurfaceIntersectionPoint(), Vector3D.reflection(intersection.getRay().getDirection(), intersection.getSurfaceNormal(), true))));
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
			final Vector3D w = Vector3D.normalize(Vector3D.reflection(intersection.getRay().getDirection(), intersection.getSurfaceNormal(), true));
			final Vector3D v = Vector3D.orthogonal(w);
			final Vector3D u = Vector3D.normalize(Vector3D.crossProduct(v, w));
			
			final Color3D colorEmission = this.textureEmission.compute(intersection);
			final Color3D colorKR = this.textureKR.compute(intersection);
			
			return Optional.of(new Result(colorEmission, colorKR, new Ray3D(intersection.getSurfaceIntersectionPoint(), Vector3D.directionNormalized(u, v, w, s))));
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
			final Color3D colorKD = Color3D.saturate(this.textureKD.compute(intersection), 0.0D, Math.MAX_VALUE);
			final Color3D colorKS = Color3D.saturate(this.textureKS.compute(intersection), 0.0D, Math.MAX_VALUE);
			
			final boolean hasColorKD = !colorKD.isBlack();
			final boolean hasColorKS = !colorKS.isBlack();
			
			if(hasColorKD || hasColorKS) {
				final OrthonormalBasis33D orthonormalBasis = intersection.getOrthonormalBasis();
				
				final Vector3D nWS = intersection.getSurfaceNormalCorrectlyOriented();
				final Vector3D oWS = Vector3D.negate(intersection.getRay().getDirection());
				final Vector3D oLS = Vector3D.transformReverseNormalize(oWS, orthonormalBasis);
				
				if(Math.isZero(oLS.z)) {
					return Optional.empty();
				}
				
				final double sampleU = Math.random();
				final double sampleV = Math.random();
				
				final int matches = (hasColorKD ? 1 : 0) + (hasColorKS ? 1 : 0);
				final int match = Math.min(Math.toInt(Math.floor(sampleU * matches)), matches - 1);
				
				final boolean isSelectingLambertianBRDF = hasColorKD && match == 0;
				final boolean isSelectingTorranceSparrowBRDF = hasColorKS && (hasColorKD ? match == 1 : match == 0);
				
				final double u = Math.min(sampleU * matches - match, 0.99999994D);
				final double v = sampleV;
				
				final Point2D p = new Point2D(u, v);
				
				final double roughness = this.isRemappingRoughness ? MicrofacetDistribution.convertRoughnessToAlpha(this.textureRoughness.compute(intersection).average()) : this.textureRoughness.compute(intersection).average();
				
				final MicrofacetDistribution microfacetDistribution = new TrowbridgeReitzMicrofacetDistribution(true, false, roughness, roughness);
				
				if(isSelectingLambertianBRDF) {
					final Vector3D iLS = Vector3D.faceForwardZ(oLS, Vector3D.sampleHemisphereCosineDistribution(p));
					final Vector3D iWS = Vector3D.transformNormalize(iLS, orthonormalBasis);
					
					Color3D result = Color3D.divide(colorKD, Math.PI);
					
					double probabilityDensityFunctionValue = Vector3D.sameHemisphereZ(oLS, iLS) ? iLS.cosThetaAbs() / Math.PI : 0.0D;
					
					final Ray3D ray = new Ray3D(intersection.getSurfaceIntersectionPoint(), iWS);
					
					if(hasColorKS) {
						if(Vector3D.sameHemisphereZ(oLS, iLS)) {
							final double cosThetaAbsO = oLS.cosThetaAbs();
							final double cosThetaAbsI = iLS.cosThetaAbs();
							
							final Vector3D hLS = Vector3D.add(oLS, iLS);
							
							if(!Math.isZero(cosThetaAbsO) && !Math.isZero(cosThetaAbsI) && !hLS.isZero()) {
								final Vector3D hLSNormalized = Vector3D.normalize(hLS);
								
								probabilityDensityFunctionValue += microfacetDistribution.computePDF(oLS, hLSNormalized) / (4.0D * Vector3D.dotProduct(oLS, hLSNormalized));
								
								final double iDotHAbs = Vector3D.dotProductAbs(iLS, Vector3D.faceForward(hLS, Vector3D.z()));
								
								final double fresnel = Fresnel.evaluateDielectric(iDotHAbs, 1.5D, 1.0D);
								
								final double a = microfacetDistribution.computeDifferentialArea(hLS);
								final double b = microfacetDistribution.computeShadowingAndMasking(oLS, iLS);
								final double c = 4.0D * cosThetaAbsI * cosThetaAbsO;
								
								result = Color3D.add(result, new Color3D(fresnel * a * b / c));
							}
						}
						
						probabilityDensityFunctionValue /= 2.0D;
					}
					
					final double iDotNAbs = Vector3D.dotProductAbs(iWS, nWS);
					
					final double reflectanceR = result.r * iDotNAbs / probabilityDensityFunctionValue;
					final double reflectanceG = result.g * iDotNAbs / probabilityDensityFunctionValue;
					final double reflectanceB = result.b * iDotNAbs / probabilityDensityFunctionValue;
					
					final Color3D emission = this.textureEmission.compute(intersection);
					final Color3D reflectance = new Color3D(reflectanceR, reflectanceG, reflectanceB);
					
					return Optional.of(new Result(emission, reflectance, ray));
				}
				
				if(isSelectingTorranceSparrowBRDF) {
					final Vector3D hLS = microfacetDistribution.sampleH(oLS, p);
					
					final double oDotH = Vector3D.dotProduct(oLS, hLS);
					
					if(oDotH < 0.0D) {
						return Optional.empty();
					}
					
					final Vector3D iLS = Vector3D.reflection(oLS, hLS);
					final Vector3D iWS = Vector3D.transformNormalize(iLS, orthonormalBasis);
					
					if(!Vector3D.sameHemisphereZ(oLS, iLS)) {
						return Optional.empty();
					}
					
					final double cosThetaAbsO = oLS.cosThetaAbs();
					final double cosThetaAbsI = iLS.cosThetaAbs();
					
					if(Math.isZero(cosThetaAbsO) || Math.isZero(cosThetaAbsI)) {
						return Optional.empty();
					}
					
					double probabilityDensityFunctionValue = microfacetDistribution.computePDF(oLS, hLS) / (4.0D * oDotH);
					
					if(Math.isZero(probabilityDensityFunctionValue)) {
						return Optional.empty();
					}
					
					final double iDotHAbs = Vector3D.dotProductAbs(iLS, Vector3D.faceForward(hLS, Vector3D.z()));
					
					final double fresnel = Fresnel.evaluateDielectric(iDotHAbs, 1.5D, 1.0D);
					
					final double a = microfacetDistribution.computeDifferentialArea(hLS);
					final double b = microfacetDistribution.computeShadowingAndMasking(oLS, iLS);
					final double c = 4.0D * cosThetaAbsI * cosThetaAbsO;
					
					Color3D result = new Color3D(fresnel * a * b / c);
					
					if(result.isBlack()) {
						return Optional.empty();
					}
					
					final Ray3D ray = new Ray3D(intersection.getSurfaceIntersectionPoint(), iWS);
					
					if(hasColorKD) {
						probabilityDensityFunctionValue += Vector3D.sameHemisphereZ(oLS, iLS) ? iLS.cosThetaAbs() / Math.PI : 0.0D;
						probabilityDensityFunctionValue /= 2.0D;
						
						result = Color3D.add(result, Color3D.divide(colorKD, Math.PI));
					}
					
					final double iDotNAbs = Vector3D.dotProductAbs(iWS, nWS);
					
					final double reflectanceR = result.r * iDotNAbs / probabilityDensityFunctionValue;
					final double reflectanceG = result.g * iDotNAbs / probabilityDensityFunctionValue;
					final double reflectanceB = result.b * iDotNAbs / probabilityDensityFunctionValue;
					
					final Color3D emission = this.textureEmission.compute(intersection);
					final Color3D reflectance = new Color3D(reflectanceR, reflectanceG, reflectanceB);
					
					return Optional.of(new Result(emission, reflectance, ray));
				}
			}
			
			return Optional.empty();
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
			final Color3D colorKD = Color3D.saturate(this.textureKD.compute(intersection), 0.0D, Math.MAX_VALUE);
			final Color3D colorKS = Color3D.saturate(this.textureKS.compute(intersection), 0.0D, Math.MAX_VALUE);
			
			if(!colorKD.isBlack() && !colorKS.isBlack()) {
				final double roughnessU = this.isRemappingRoughness ? MicrofacetDistribution.convertRoughnessToAlpha(this.textureRoughnessU.compute(intersection).average()) : this.textureRoughnessU.compute(intersection).average();
				final double roughnessV = this.isRemappingRoughness ? MicrofacetDistribution.convertRoughnessToAlpha(this.textureRoughnessV.compute(intersection).average()) : this.textureRoughnessV.compute(intersection).average();
				
				final MicrofacetDistribution microfacetDistribution = new TrowbridgeReitzMicrofacetDistribution(true, false, roughnessU, roughnessV);
				
				final OrthonormalBasis33D orthonormalBasis = intersection.getOrthonormalBasis();
				
				final Vector3D nWS = intersection.getSurfaceNormalCorrectlyOriented();
				final Vector3D oWS = Vector3D.negate(intersection.getRay().getDirection());
				final Vector3D oLS = Vector3D.transformReverseNormalize(oWS, orthonormalBasis);
				
				if(Math.isZero(oLS.z)) {
					return Optional.empty();
				}
				
				final double t = Math.min(Math.random(), 0.99999994D);
				final double u = t < 0.5D ? Math.min(2.0D * t, 0.99999994D) : Math.min(2.0D * (t - 0.5D), 0.99999994D);
				final double v = Math.random();
				
				final Point2D p = new Point2D(u, v);
				
				final Vector3D i = t < 0.5D ? Vector3D.sampleHemisphereCosineDistribution(p) : Vector3D.reflection(oLS, microfacetDistribution.sampleH(oLS, p));
				final Vector3D iLS = t < 0.5D && oLS.z < 0.0D ? Vector3D.negate(i) : i;
				final Vector3D iWS = Vector3D.transformNormalize(iLS, orthonormalBasis);
				
				if(!Vector3D.sameHemisphereZ(oLS, iLS)) {
					return Optional.empty();
				}
				
				final Vector3D hLS = Vector3D.add(oLS, iLS);
				
				if(hLS.isZero()) {
					return Optional.empty();
				}
				
				final Vector3D hLSNormalized = Vector3D.normalize(hLS);
				
				final double iDotH = Vector3D.dotProduct(iLS, hLSNormalized);
				final double iDotHAbs = Math.abs(iDotH);
				final double iDotNAbs = Vector3D.dotProductAbs(iWS, nWS);
				final double oDotH = Vector3D.dotProduct(oLS, hLSNormalized);
				
				final double cosThetaAbsI = iLS.cosThetaAbs();
				final double cosThetaAbsO = oLS.cosThetaAbs();
				
				final double probabilityDensityFunctionValue = 0.5D * (cosThetaAbsI / Math.PI + microfacetDistribution.computePDF(oLS, hLSNormalized) / (4.0D * oDotH));
				
				if(Math.isZero(probabilityDensityFunctionValue)) {
					return Optional.empty();
				}
				
				final double a = 28.0D / (23.0D * Math.PI);
				final double b = 1.0D - Math.pow5(1.0D - 0.5D * cosThetaAbsI);
				final double c = 1.0D - Math.pow5(1.0D - 0.5D * cosThetaAbsO);
				final double d = microfacetDistribution.computeDifferentialArea(hLSNormalized);
				final double e = 4.0D * iDotHAbs * Math.max(cosThetaAbsI, cosThetaAbsO);
				final double f = d / e;
				
				final Color3D colorFresnel = Fresnel.evaluateDielectricSchlick(iDotH, colorKS);
				
				final double resultR = colorKD.r * a * (1.0D - colorKS.r) * b * c + colorFresnel.r * f;
				final double resultG = colorKD.g * a * (1.0D - colorKS.g) * b * c + colorFresnel.g * f;
				final double resultB = colorKD.b * a * (1.0D - colorKS.b) * b * c + colorFresnel.b * f;
				
				if(Math.isZero(resultR) && Math.isZero(resultG) && Math.isZero(resultB)) {
					return Optional.empty();
				}
				
				final double reflectanceR = resultR * iDotNAbs / probabilityDensityFunctionValue;
				final double reflectanceG = resultG * iDotNAbs / probabilityDensityFunctionValue;
				final double reflectanceB = resultB * iDotNAbs / probabilityDensityFunctionValue;
				
				final Color3D emission = this.textureEmission.compute(intersection);
				final Color3D reflectance = new Color3D(reflectanceR, reflectanceG, reflectanceB);
				
				final Ray3D ray = new Ray3D(intersection.getSurfaceIntersectionPoint(), iWS);
				
				return Optional.of(new Result(emission, reflectance, ray));
			}
			
			return Optional.empty();
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final class TrowbridgeReitzMicrofacetDistribution extends MicrofacetDistribution {
		private final double alphaX;
		private final double alphaY;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public TrowbridgeReitzMicrofacetDistribution(final boolean isSamplingVisibleArea, final boolean isSeparableModel, final double alphaX, final double alphaY) {
			super(isSamplingVisibleArea, isSeparableModel);
			
			this.alphaX = Math.max(alphaX, 0.001D);
			this.alphaY = Math.max(alphaY, 0.001D);
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		@Override
		public Vector3D sampleH(final Vector3D o, final Point2D p) {
			if(isSamplingVisibleArea()) {
				return o.z >= 0.0D ? doSample(o, p) : Vector3D.negate(doSample(Vector3D.negate(o), p));
			} else if(Math.equal(this.alphaX, this.alphaY)) {
				final double phi = p.v * 2.0D * Math.PI;
				final double cosTheta = 1.0D / Math.sqrt(1.0D + (this.alphaX * this.alphaX * p.u / (1.0D - p.u)));
				final double sinTheta = Math.sqrt(Math.max(0.0D, 1.0D - cosTheta * cosTheta));
				
				return Vector3D.orientNormalSameHemisphereZ(o, Vector3D.directionSpherical(sinTheta, cosTheta, phi));
			} else {
				final double phi = Math.atan(this.alphaY / this.alphaX * Math.tan(2.0D * Math.PI * p.v + 0.5D * Math.PI)) + (p.v > 0.5D ? Math.PI : 0.0D);
				final double cosTheta = 1.0D / Math.sqrt(1.0D + ((1.0D / (Math.pow2(Math.cos(phi)) / (this.alphaX * this.alphaX) + Math.pow2(Math.sin(phi)) / (this.alphaY * this.alphaY))) * p.u / (1.0D - p.u)));
				final double sinTheta = Math.sqrt(Math.max(0.0D, 1.0D - cosTheta * cosTheta));
				
				return Vector3D.orientNormalSameHemisphereZ(o, Vector3D.directionSpherical(sinTheta, cosTheta, phi));
			}
		}
		
		@Override
		public double computeDifferentialArea(final Vector3D h) {
			final double tanThetaSquared = h.tanThetaSquared();
			
			if(Math.isInfinite(tanThetaSquared)) {
				return 0.0D;
			}
			
			return 1.0D / (Math.PI * this.alphaX * this.alphaY * h.cosThetaQuartic() * Math.pow2(1.0D + (h.cosPhiSquared() / (this.alphaX * this.alphaX) + h.sinPhiSquared() / (this.alphaY * this.alphaY)) * tanThetaSquared));
		}
		
		@Override
		public double computeLambda(final Vector3D o) {
			final double tanThetaAbs = o.tanThetaAbs();
			
			if(Math.isInfinite(tanThetaAbs)) {
				return 0.0D;
			}
			
			return (-1.0D + Math.sqrt(1.0D + Math.pow2(Math.sqrt(o.cosPhiSquared() * this.alphaX * this.alphaX + o.sinPhiSquared() * this.alphaY * this.alphaY) * tanThetaAbs))) / 2.0D;
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		private Vector3D doSample(final Vector3D i, final Point2D p) {
			final Vector3D iStretched = Vector3D.normalize(new Vector3D(i.x * this.alphaX, i.y * this.alphaY, i.z));
			
			final double cosPhi = iStretched.cosPhi();
			final double cosTheta = iStretched.cosTheta();
			final double sinPhi = iStretched.sinPhi();
			
			if(cosTheta > 0.9999D) {
				final double r = Math.sqrt(p.u / (1.0D - p.u));
				final double phi = 2.0D * Math.PI * p.v;
				
				final double slopeX = r * Math.cos(phi);
				final double slopeY = r * Math.sin(phi);
				
				return Vector3D.normalize(new Vector3D(-((cosPhi * slopeX - sinPhi * slopeY) * this.alphaX), -((sinPhi * slopeX + cosPhi * slopeY) * this.alphaY), 1.0D));
			}
			
			final double a = Math.sqrt(Math.max(0.0D, 1.0D - cosTheta * cosTheta)) / cosTheta;
			final double b = 2.0D * p.u / (2.0D / (1.0D + Math.sqrt(1.0D + a * a))) - 1.0D;
			final double c = Math.min(1.0D / (b * b - 1.0D), 1.0e10D);
			final double d = Math.sqrt(Math.max(a * a * c * c - (b * b - a * a) * c, 0.0D));
			final double e = a * c + d;
			final double f = p.v > 0.5D ? 2.0D * (p.v - 0.5D) : 2.0D * (0.5D - p.v);
			
			final double slopeX = b < 0.0D || e > 1.0D / a ? a * c - d : e;
			final double slopeY = (p.v > 0.5D ? 1.0D : -1.0D) * (f * (f * (f * 0.27385D - 0.73369D) + 0.46341D)) / (f * (f * (f * 0.093073D + 0.309420D) - 1.0D) + 0.597999D) * Math.sqrt(1.0D + this.alphaX * this.alphaX);
			
			return Vector3D.normalize(new Vector3D(-((cosPhi * slopeX - sinPhi * slopeY) * this.alphaX), -((sinPhi * slopeX + cosPhi * slopeY) * this.alphaY), 1.0D));
		}
	}
}