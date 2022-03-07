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

public interface Material {
	Optional<Result> compute(final Intersection intersection);
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	static Material checkerboard(final Material materialA, final Material materialB, final double angleDegrees, final double scaleU, final double scaleV) {
		Objects.requireNonNull(materialA, "materialA == null");
		Objects.requireNonNull(materialB, "materialB == null");
		
		final double angleRadians = Math.toRadians(angleDegrees);
		final double angleRadiansCos = Math.cos(angleRadians);
		final double angleRadiansSin = Math.sin(angleRadians);
		
		return intersection -> {
			final double u = intersection.getTextureCoordinates().u;
			final double v = intersection.getTextureCoordinates().v;
			
			final boolean isU = Math.fractionalPart((u * angleRadiansCos - v * angleRadiansSin) * scaleU) > 0.5D;
			final boolean isV = Math.fractionalPart((v * angleRadiansCos + u * angleRadiansSin) * scaleV) > 0.5D;
			final boolean isMaterialA = isU ^ isV;
			
			return isMaterialA ? materialA.compute(intersection) : materialB.compute(intersection);
		};
	}
	
	static Material glass() {
		return intersection -> {
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
				
				final double reflectance = Schlick.fresnelDielectric(cosThetaICorrectlyOriented, r0);
				final double transmittance = 1.0D - reflectance;
				
				final double probabilityRussianRoulette = 0.25D + 0.5D * reflectance;
				final double probabilityRussianRouletteReflection = reflectance / probabilityRussianRoulette;
				final double probabilityRussianRouletteTransmission = transmittance / (1.0D - probabilityRussianRoulette);
				
				final boolean isChoosingSpecularReflection = Math.random() < probabilityRussianRoulette;
				
				if(isChoosingSpecularReflection) {
					return Optional.of(new Result(new Color3D(), new Color3D(probabilityRussianRouletteReflection), reflectionRay));
				}
				
				return Optional.of(new Result(new Color3D(), new Color3D(probabilityRussianRouletteTransmission), transmissionRay));
			}
			
			return Optional.of(new Result(new Color3D(), new Color3D(1.0D), reflectionRay));
		};
	}
	
	static Material matte(final Texture reflectance) {
		return matte(reflectance, Texture.constant(new Color3D()));
	}
	
	static Material matte(final Texture reflectance, final Texture emission) {
		Objects.requireNonNull(reflectance, "reflectance == null");
		Objects.requireNonNull(emission, "emission == null");
		
		return intersection -> {
			final Vector3D s = Vector3D.sampleHemisphereCosineDistribution();
			final Vector3D w = Vector3D.orientNormal(intersection.getRay().getDirection(), intersection.getSurfaceNormal());
			final Vector3D u = Vector3D.normalize(Vector3D.crossProduct(Math.abs(w.x) > 0.1D ? Vector3D.y() : Vector3D.x(), w));
			final Vector3D v = Vector3D.normalize(Vector3D.crossProduct(w, u));
			
//			result * abs(dot(i, n)) / pdf
//			(reflectance / PI) * abs(dot(i, n)) / (abs(i.z) / PI)
//			final Color3D c = Color3D.divide(Color3D.multiply(Color3D.divide(reflectance.compute(intersection), Math.PI), Math.abs(Vector3D.dotProduct(s, new Vector3D(0.0D, 0.0D, 1.0D)))), Math.abs(s.z) / Math.PI);
			
			return Optional.of(new Result(emission.compute(intersection), reflectance.compute(intersection), new Ray3D(intersection.getSurfaceIntersectionPoint(), Vector3D.directionNormalized(u, v, w, s))));
		};
	}
	
	static Material metal(final Texture reflectance) {
		Objects.requireNonNull(reflectance, "reflectance == null");
		
		return intersection -> {
			final Vector3D s = Vector3D.sampleHemispherePowerCosineDistribution();
			final Vector3D w = Vector3D.normalize(Vector3D.reflection(intersection.getRay().getDirection(), intersection.getSurfaceNormal(), true));
			final Vector3D v = Vector3D.orthogonal(w);
			final Vector3D u = Vector3D.normalize(Vector3D.crossProduct(v, w));
			
			return Optional.of(new Result(new Color3D(), reflectance.compute(intersection), new Ray3D(intersection.getSurfaceIntersectionPoint(), Vector3D.directionNormalized(u, v, w, s))));
		};
	}
	
	static Material mirror(final Texture reflectance) {
		Objects.requireNonNull(reflectance, "reflectance == null");
		
		return intersection -> Optional.of(new Result(new Color3D(), reflectance.compute(intersection), new Ray3D(intersection.getSurfaceIntersectionPoint(), Vector3D.reflection(intersection.getRay().getDirection(), intersection.getSurfaceNormal(), true))));
	}
	
	static Material substrate(final Texture textureKD, final Texture textureKS, final Texture textureEmission, final Texture textureRoughnessU, final Texture textureRoughnessV, final boolean isRemappingRoughness) {
		Objects.requireNonNull(textureKD, "textureKD == null");
		Objects.requireNonNull(textureKS, "textureKS == null");
		Objects.requireNonNull(textureEmission, "textureEmission == null");
		Objects.requireNonNull(textureRoughnessU, "textureRoughnessU == null");
		Objects.requireNonNull(textureRoughnessV, "textureRoughnessV == null");
		
		return intersection -> {
			final Color3D colorKD = Color3D.saturate(textureKD.compute(intersection), 0.0D, Math.MAX_VALUE);
			final Color3D colorKS = Color3D.saturate(textureKS.compute(intersection), 0.0D, Math.MAX_VALUE);
			
			if(!colorKD.isBlack() && !colorKS.isBlack()) {
				final double roughnessU = Math.max(isRemappingRoughness ? MicrofacetDistribution.convertRoughnessToAlpha(textureRoughnessU.compute(intersection).average()) : textureRoughnessU.compute(intersection).average(), 0.001D);
				final double roughnessV = Math.max(isRemappingRoughness ? MicrofacetDistribution.convertRoughnessToAlpha(textureRoughnessV.compute(intersection).average()) : textureRoughnessV.compute(intersection).average(), 0.001D);
				
				final boolean isSamplingVisibleArea = true;
				
				final Vector3D oW = intersection.getSurfaceNormal();
				final Vector3D oV = Vector3D.orthogonal(oW);
				final Vector3D oU = Vector3D.normalize(Vector3D.crossProduct(oV, oW));
				
				final Vector3D oWS = Vector3D.negate(intersection.getRay().getDirection());
				final Vector3D oLS = Vector3D.transformReverseNormalize(oWS, oU, oV, oW);
				
				final double u = Math.min(Math.random(), 0.99999994D);
				final double v = Math.random();
				
				if(u < 0.5D) {
					final Vector3D iSample = Vector3D.sampleHemisphereCosineDistribution(Math.min(2.0D * u, 0.99999994D), v);
					final Vector3D iLS = oLS.z < 0.0D ? Vector3D.negate(iSample) : iSample;
					final Vector3D iWS = Vector3D.transformNormalize(iLS, oU, oV, oW);
					
					final Color3D result = BXDF.evaluateDistributionFunctionFresnelBlendBRDF(oLS, iLS, colorKD, colorKS, roughnessU, roughnessV);
					
					final double probabilityDensityFunctionValue = BXDF.evaluatePDFFresnelBlendBRDF(oLS, iLS, isSamplingVisibleArea, roughnessU, roughnessV);
					
					final Color3D reflectance = Color3D.divide(Color3D.multiply(result, Math.abs(Vector3D.dotProduct(iLS, Vector3D.z()))), probabilityDensityFunctionValue);
					
					return Optional.of(new Result(Color3D.BLACK, reflectance, new Ray3D(intersection.getSurfaceIntersectionPoint(), iWS)));
				}
				
				final Vector3D nLS = MicrofacetDistribution.sampleNormalTrowbridgeReitz(oLS, new Point2D(Math.min(2.0D * (u - 0.5D), 0.99999994D), v), isSamplingVisibleArea, roughnessU, roughnessV);
				final Vector3D iLS = Vector3D.reflection(oLS, nLS);
				final Vector3D iWS = Vector3D.transformNormalize(iLS, oU, oV, oW);
				
				if(!Vector3D.sameHemisphereZ(oLS, iLS)) {
					return Optional.empty();
				}
				
				final Color3D result = BXDF.evaluateDistributionFunctionFresnelBlendBRDF(oLS, iLS, colorKD, colorKS, roughnessU, roughnessV);
				
				final double probabilityDensityFunctionValue = BXDF.evaluatePDFFresnelBlendBRDF(oLS, iLS, isSamplingVisibleArea, roughnessU, roughnessV);
				
				final Color3D reflectance = Color3D.divide(Color3D.multiply(result, Math.abs(Vector3D.dotProduct(iLS, Vector3D.z()))), probabilityDensityFunctionValue);
				
				return Optional.of(new Result(Color3D.BLACK, reflectance, new Ray3D(intersection.getSurfaceIntersectionPoint(), iWS)));
			}
			
			return Optional.empty();
		};
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static final class BXDF {
		private BXDF() {
			
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public static Color3D evaluateDistributionFunctionFresnelBlendBRDF(final Vector3D outgoing, final Vector3D incoming, final Color3D colorKD, final Color3D colorKS, final double alphaX, final double alphaY) {
			Objects.requireNonNull(outgoing, "outgoing == null");
			Objects.requireNonNull(incoming, "incoming == null");
			
			final Vector3D n = Vector3D.add(outgoing, incoming);
			
			if(Math.isZero(n.x) && Math.isZero(n.y) && Math.isZero(n.z)) {
				return Color3D.BLACK;
			}
			
			final Vector3D nNormalized = Vector3D.normalize(n);
			
			final double a = 28.0D / (23.0D * Math.PI);
			final double b = 1.0D - Math.pow(1.0D - 0.5D * incoming.cosThetaAbs(), 5.0D);
			final double c = 1.0D - Math.pow(1.0D - 0.5D * outgoing.cosThetaAbs(), 5.0D);
			final double d = MicrofacetDistribution.computeDifferentialAreaTrowbridgeReitz(nNormalized, alphaX, alphaY);
			final double e = 4.0D * Math.abs(Vector3D.dotProduct(incoming, nNormalized)) * Math.max(incoming.cosThetaAbs(), outgoing.cosThetaAbs());
			final double f = d / e;
			
			final Color3D fresnel = Schlick.fresnelDielectric(Vector3D.dotProduct(incoming, nNormalized), colorKS);
			final Color3D colorDiffuse = Color3D.multiply(Color3D.multiply(Color3D.multiply(Color3D.multiply(colorKD, a), Color3D.subtract(Color3D.WHITE, colorKS)), b), c);
			final Color3D colorSpecular = Color3D.multiply(fresnel, f);
			
			return Color3D.add(colorDiffuse, colorSpecular);
		}
		
		public static double evaluatePDFFresnelBlendBRDF(final Vector3D outgoing, final Vector3D incoming, final boolean isSamplingVisibleArea, final double alphaX, final double alphaY) {
			Objects.requireNonNull(outgoing, "outgoing == null");
			Objects.requireNonNull(incoming, "incoming == null");
			
			if(!Vector3D.sameHemisphereZ(outgoing, incoming)) {
				return 0.0D;
			}
			
			final Vector3D n = Vector3D.normalize(Vector3D.add(outgoing, incoming));
			
			return 0.5D * (incoming.cosThetaAbs() / Math.PI + MicrofacetDistribution.computePDFTrowbridgeReitz(outgoing, n, isSamplingVisibleArea, alphaX, alphaY) / (4.0D * Vector3D.dotProduct(outgoing, n)));
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static final class MicrofacetDistribution {
		private MicrofacetDistribution() {
			
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public static Vector3D sampleNormalTrowbridgeReitz(final Vector3D o, final Point2D s, final boolean isSamplingVisibleArea, final double x, final double y) {
			final double u = s.u;
			final double v = s.v;
			
			if(isSamplingVisibleArea && o.z >= 0.0D) {
				return doSample(o, x, y, u, v);
			} else if(isSamplingVisibleArea) {
				return Vector3D.negate(doSample(Vector3D.negate(o), x, y, u, v));
			} else if(Math.equal(x, y)) {
				final double phi = v * 2.0D * Math.PI;
				final double cosTheta = 1.0D / Math.sqrt(1.0D + (x * x * u / (1.0D - u)));
				final double sinTheta = Math.sqrt(Math.max(0.0D, 1.0D - cosTheta * cosTheta));
				
				return Vector3D.orientNormalSameHemisphereZ(o, Vector3D.directionSpherical(sinTheta, cosTheta, phi));
			} else {
				final double phi = Math.atan(y / x * Math.tan(2.0D * Math.PI * v + 0.5D * Math.PI)) + (v > 0.5D ? Math.PI : 0.0D);
				final double cosTheta = 1.0D / Math.sqrt(1.0D + ((1.0D / (Math.pow2(Math.cos(phi)) / (x * x) + Math.pow2(Math.sin(phi)) / (y * y))) * u / (1.0D - u)));
				final double sinTheta = Math.sqrt(Math.max(0.0D, 1.0D - cosTheta * cosTheta));
				
				return Vector3D.orientNormalSameHemisphereZ(o, Vector3D.directionSpherical(sinTheta, cosTheta, phi));
			}
		}
		
		public static double computeDifferentialAreaTrowbridgeReitz(final Vector3D n, final double x, final double y) {
			final double tanThetaSquared = n.tanThetaSquared();
			
			if(Math.isInfinite(tanThetaSquared)) {
				return 0.0D;
			}
			
			return 1.0D / (Math.PI * x * y * n.cosThetaQuartic() * Math.pow2(1.0D + (n.cosPhiSquared() / (x * x) + n.sinPhiSquared() / (y * y)) * tanThetaSquared));
		}
		
		public static double computeLambdaTrowbridgeReitz(final Vector3D o, final double x, final double y) {
			final double tanThetaAbs = o.tanThetaAbs();
			
			if(Math.isInfinite(tanThetaAbs)) {
				return 0.0D;
			}
			
			return (-1.0D + Math.sqrt(1.0D + Math.pow2(Math.sqrt(o.cosPhiSquared() * x * x + o.sinPhiSquared() * y * y) * tanThetaAbs))) / 2.0D;
		}
		
		public static double computePDFTrowbridgeReitz(final Vector3D o, final Vector3D n, final boolean isSamplingVisibleArea, final double x, final double y) {
			return isSamplingVisibleArea ? computeDifferentialAreaTrowbridgeReitz(n, x, y) * computeShadowingAndMaskingTrowbridgeReitz(o, x, y) * Math.abs(Vector3D.dotProduct(o, n)) / o.cosThetaAbs() : computeDifferentialAreaTrowbridgeReitz(n, x, y) * n.cosThetaAbs();
		}
		
		public static double computeShadowingAndMaskingTrowbridgeReitz(final Vector3D o, final double x, final double y) {
			return 1.0D / (1.0D + computeLambdaTrowbridgeReitz(o, x, y));
		}
		
		public static double computeShadowingAndMaskingTrowbridgeReitz(final Vector3D o, final Vector3D i, final boolean isSeparableModel, final double x, final double y) {
			return isSeparableModel ? computeShadowingAndMaskingTrowbridgeReitz(o, x, y) * computeShadowingAndMaskingTrowbridgeReitz(i, x, y) : 1.0D / (1.0D + computeLambdaTrowbridgeReitz(o, x, y) + computeLambdaTrowbridgeReitz(i, x, y));
		}
		
		public static double convertRoughnessToAlpha(final double roughness) {
			final double x = Math.log(Math.max(roughness, 1.0e-3D));
			final double y = 1.62142D + 0.819955D * x + 0.1734D * x * x + 0.0171201D * x * x * x + 0.000640711D * x * x * x * x;
			
			return y;
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		private static Vector3D doSample(final Vector3D i, final double x, final double y, final double u, final double v) {
			final Vector3D iStretched = Vector3D.normalize(new Vector3D(i.x * x, i.y * y, i.z));
			
			final double[] slope = doComputeSlope(iStretched.cosTheta(), u, v);
			
			return Vector3D.normalize(new Vector3D(-((iStretched.cosPhi() * slope[0] - iStretched.sinPhi() * slope[1]) * x), -((iStretched.sinPhi() * slope[0] + iStretched.cosPhi() * slope[1]) * y), 1.0D));
		}
		
		private static double[] doComputeSlope(final double cosTheta, final double u, final double v) {
			if(cosTheta > 0.9999D) {
				final double r = Math.sqrt(u / (1.0D - u));
				final double phi = 2.0D * Math.PI * v;
				
				final double x = r * Math.cos(phi);
				final double y = r * Math.sin(phi);
				
				return new double[] {x, y};
			}
			
			final double a = Math.sqrt(Math.max(0.0D, 1.0D - cosTheta * cosTheta)) / cosTheta;
			final double b = 2.0D * u / (2.0D / (1.0D + Math.sqrt(1.0D + a * a))) - 1.0D;
			final double c = Math.min(1.0D / (b * b - 1.0D), 1.0e10D);
			final double d = Math.sqrt(Math.max(a * a * c * c - (b * b - a * a) * c, 0.0D));
			final double e = a * c + d;
			final double f = v > 0.5D ? 2.0D * (v - 0.5D) : 2.0D * (0.5D - v);
			
			final double x = b < 0.0D || e > 1.0D / a ? a * c - d : e;
			final double y = (v > 0.5D ? 1.0D : -1.0D) * (f * (f * (f * 0.27385D - 0.73369D) + 0.46341D)) / (f * (f * (f * 0.093073D + 0.309420D) - 1.0D) + 0.597999D) * Math.sqrt(1.0D + x * x);
			
			return new double[] {x, y};
		}
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
	
	public final class Schlick {
		private Schlick() {
			
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public static Color3D fresnelDielectric(final double cosTheta, final Color3D r0) {
			return Color3D.add(r0, Color3D.multiply(Color3D.subtract(Color3D.WHITE, r0), Math.pow(1.0D - cosTheta, 5.0D)));
		}
		
		public static double fresnelDielectric(final double cosTheta, final double r0) {
			return r0 + (1.0D - r0) * fresnelWeight(cosTheta);
		}
		
		public static double fresnelWeight(final double cosTheta) {
			return Math.pow5(Math.saturate(1.0D - cosTheta));
		}
	}
}