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
				final Vector3D oLS = Vector3D.normalize(new Vector3D(Vector3D.dotProduct(oWS, oU), Vector3D.dotProduct(oWS, oV), Vector3D.dotProduct(oWS, oW)));
				
				final double u = Math.min(Math.random(), 0.99999994D);
				final double v = Math.random();
				
				if(u < 0.5D) {
					final Vector3D iSample = Vector3D.sampleHemisphereCosineDistribution(Math.min(2.0D * u, 0.99999994D), v);
					final Vector3D iLS = oLS.z < 0.0D ? Vector3D.negate(iSample) : iSample;
					final Vector3D iWS = new Vector3D(iLS.x * oU.x + iLS.y * oV.x + iLS.z * oW.x, iLS.x * oU.y + iLS.y * oV.y + iLS.z * oW.y, iLS.x * oU.z + iLS.y * oV.z + iLS.z * oW.z);
					
					final Color3D result = BXDF.evaluateDistributionFunctionFresnelBlendBRDF(oLS, iLS, colorKD, colorKS, roughnessU, roughnessV);
					
					final double probabilityDensityFunctionValue = BXDF.evaluatePDFFresnelBlendBRDF(oLS, iLS, isSamplingVisibleArea, roughnessU, roughnessV);
					
					final Color3D reflectance = Color3D.divide(Color3D.multiply(result, Math.abs(Vector3D.dotProduct(iLS, Vector3D.z()))), probabilityDensityFunctionValue);
					
					return Optional.of(new Result(Color3D.BLACK, reflectance, new Ray3D(intersection.getSurfaceIntersectionPoint(), iWS)));
				}
				
				final Vector3D nLS = MicrofacetDistribution.sampleNormalTrowbridgeReitz(oLS, new Point2D(Math.min(2.0D * (u - 0.5D), 0.99999994D), v), isSamplingVisibleArea, roughnessU, roughnessV);
				final Vector3D iLS = Vector3D.reflection(oLS, nLS);
				final Vector3D iWS = new Vector3D(iLS.x * oU.x + iLS.y * oV.x + iLS.z * oW.x, iLS.x * oU.y + iLS.y * oV.y + iLS.z * oW.y, iLS.x * oU.z + iLS.y * oV.z + iLS.z * oW.z);
				
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
		
		public static Vector3D sampleNormalTrowbridgeReitz(final Vector3D outgoing, final Point2D sample, final boolean isSamplingVisibleArea, final double alphaX, final double alphaY) {
			final double u = sample.u;
			final double v = sample.v;
			
			if(isSamplingVisibleArea) {
				return outgoing.z >= 0.0D ? doSample(outgoing, alphaX, alphaY, u, v) : Vector3D.negate(doSample(Vector3D.negate(outgoing), alphaX, alphaY, u, v));
			} else if(Math.equal(alphaX, alphaY)) {
				final double phi = v * 2.0D * Math.PI;
				final double tanThetaSquared = alphaX * alphaX * u / (1.0D - u);
				final double cosTheta = 1.0D / Math.sqrt(1.0D + tanThetaSquared);
				final double sinTheta = Math.sqrt(Math.max(0.0D, 1.0D - cosTheta * cosTheta));
				
				final Vector3D normal = Vector3D.directionSpherical(sinTheta, cosTheta, phi);
				final Vector3D normalCorrectlyOriented = Vector3D.sameHemisphereZ(outgoing, normal) ? normal : Vector3D.negate(normal);
				
				return normalCorrectlyOriented;
			} else {
				final double phi = Math.atan(alphaY / alphaX * Math.tan(2.0D * Math.PI * v + 0.5D * Math.PI)) + (v > 0.5D ? Math.PI : 0.0D);
				final double cosPhi = Math.cos(phi);
				final double sinPhi = Math.sin(phi);
				final double alphaXSquared = alphaX * alphaX;
				final double alphaYSquared = alphaY * alphaY;
				final double alphaSquared = 1.0D / (cosPhi * cosPhi / alphaXSquared + sinPhi * sinPhi / alphaYSquared);
				final double tanThetaSquared = alphaSquared * u / (1.0D - u);
				final double cosTheta = 1.0D / Math.sqrt(1.0D + tanThetaSquared);
				final double sinTheta = Math.sqrt(Math.max(0.0D, 1.0D - cosTheta * cosTheta));
				
				final Vector3D normal = Vector3D.directionSpherical(sinTheta, cosTheta, phi);
				final Vector3D normalCorrectlyOriented = Vector3D.sameHemisphereZ(outgoing, normal) ? normal : Vector3D.negate(normal);
				
				return normalCorrectlyOriented;
			}
		}
		
		public static double computeDifferentialAreaTrowbridgeReitz(final Vector3D normal, final double alphaX, final double alphaY) {
			final double tanThetaSquaredNormal = normal.tanThetaSquared();
			
			if(Math.isInfinite(tanThetaSquaredNormal)) {
				return 0.0D;
			}
			
			final double alphaXSquared = alphaX * alphaX;
			final double alphaYSquared = alphaY * alphaY;
			
			final double cosPhiSquaredNormal = normal.cosPhiSquared();
			final double sinPhiSquaredNormal = normal.sinPhiSquared();
			
			final double cosThetaQuarticNormal = normal.cosThetaQuartic();
			
			final double exponent = (cosPhiSquaredNormal / alphaXSquared + sinPhiSquaredNormal / alphaYSquared) * tanThetaSquaredNormal;
			
			final double differentialArea = 1.0D / (Math.PI * alphaX * alphaY * cosThetaQuarticNormal * (1.0D + exponent) * (1.0D + exponent));
			
			return differentialArea;
		}
		
		public static double computeLambdaTrowbridgeReitz(final Vector3D outgoing, final double alphaX, final double alphaY) {
			final double tanThetaAbsOutgoing = outgoing.tanThetaAbs();
			
			if(Math.isInfinite(tanThetaAbsOutgoing)) {
				return 0.0D;
			}
			
			final double cosPhiSquaredOutgoing = outgoing.cosPhiSquared();
			final double sinPhiSquaredOutgoing = outgoing.sinPhiSquared();
			
			final double alpha = Math.sqrt(cosPhiSquaredOutgoing * alphaX * alphaX + sinPhiSquaredOutgoing * alphaY * alphaY);
			final double alphaTanThetaAbsOutgoingSquared = (alpha * tanThetaAbsOutgoing) * (alpha * tanThetaAbsOutgoing);
			
			final double lambda = (-1.0D + Math.sqrt(1.0D + alphaTanThetaAbsOutgoingSquared)) / 2.0D;
			
			return lambda;
		}
		
		public static double computePDFTrowbridgeReitz(final Vector3D outgoing, final Vector3D normal, final boolean isSamplingVisibleArea, final double alphaX, final double alphaY) {
			return isSamplingVisibleArea ? computeDifferentialAreaTrowbridgeReitz(normal, alphaX, alphaY) * computeShadowingAndMaskingTrowbridgeReitz(outgoing, alphaX, alphaY) * Math.abs(Vector3D.dotProduct(outgoing, normal)) / outgoing.cosThetaAbs() : computeDifferentialAreaTrowbridgeReitz(normal, alphaX, alphaY) * normal.cosThetaAbs();
		}
		
		public static double computeShadowingAndMaskingTrowbridgeReitz(final Vector3D outgoing, final double alphaX, final double alphaY) {
			return 1.0D / (1.0D + computeLambdaTrowbridgeReitz(outgoing, alphaX, alphaY));
		}
		
		public static double computeShadowingAndMaskingTrowbridgeReitz(final Vector3D outgoing, final Vector3D incoming, final boolean isSeparableModel, final double alphaX, final double alphaY) {
			return isSeparableModel ? computeShadowingAndMaskingTrowbridgeReitz(outgoing, alphaX, alphaY) * computeShadowingAndMaskingTrowbridgeReitz(incoming, alphaX, alphaY) : 1.0D / (1.0D + computeLambdaTrowbridgeReitz(outgoing, alphaX, alphaY) + computeLambdaTrowbridgeReitz(incoming, alphaX, alphaY));
		}
		
		public static double convertRoughnessToAlpha(final double roughness) {
			final double x = Math.max(roughness, 1.0e-3D);
			final double y = Math.log(x);
			final double z = 1.62142D + 0.819955D * y + 0.1734D * y * y + 0.0171201D * y * y * y + 0.000640711D * y * y * y * y;
			
			return z;
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		private static Vector3D doSample(final Vector3D incoming, final double alphaX, final double alphaY, final double u, final double v) {
			final Vector3D incomingStretched = Vector3D.normalize(new Vector3D(incoming.x * alphaX, incoming.y * alphaY, incoming.z));
			
			final double[] slope = doComputeSlope(incomingStretched.cosTheta(), u, v);
			
			final double x = -((incomingStretched.cosPhi() * slope[0] - incomingStretched.sinPhi() * slope[1]) * alphaX);
			final double y = -((incomingStretched.sinPhi() * slope[0] + incomingStretched.cosPhi() * slope[1]) * alphaY);
			final double z = 1.0D;
			
			return Vector3D.normalize(new Vector3D(x, y, z));
		}
		
		private static double[] doComputeSlope(final double cosThetaIncoming, final double u, final double v) {
			if(cosThetaIncoming > 0.9999D) {
				final double r = Math.sqrt(u / (1.0D - u));
				final double phi = 2.0D * Math.PI * v;
				
				final double cosPhi = Math.cos(phi);
				final double sinPhi = Math.sin(phi);
				
				final double x = r * cosPhi;
				final double y = r * sinPhi;
				
				return new double[] {x, y};
			}
			
			final double sinThetaIncoming = Math.sqrt(Math.max(0.0D, 1.0D - cosThetaIncoming * cosThetaIncoming));
			final double tanThetaIncoming = sinThetaIncoming / cosThetaIncoming;
			
			final double a = 2.0D / (1.0D + Math.sqrt(1.0D + tanThetaIncoming * tanThetaIncoming));
			final double b = 2.0D * u / a - 1.0D;
			final double c = Math.min(1.0D / (b * b - 1.0D), 1.0e10D);
			final double d = tanThetaIncoming;
			final double e = Math.sqrt(Math.max(d * d * c * c - (b * b - d * d) * c, 0.0D));
			final double f = d * c - e;
			final double g = d * c + e;
			final double h = v > 0.5D ? 1.0D : -1.0D;
			final double i = v > 0.5D ? 2.0D * (v - 0.5D) : 2.0D * (0.5D - v);
			final double j = (i * (i * (i * 0.27385D - 0.73369D) + 0.46341D)) / (i * (i * (i * 0.093073D + 0.309420D) - 1.0D) + 0.597999D);
			
			final double x = b < 0.0D || g > 1.0D / tanThetaIncoming ? f : g;
			final double y = h * j * Math.sqrt(1.0D + x * x);
			
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
			final double m = Math.saturate(1.0D - cosTheta);
			
			return (m * m) * (m * m) * m;
		}
	}
}