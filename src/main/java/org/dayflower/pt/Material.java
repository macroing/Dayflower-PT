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
import org.macroing.geo4j.common.AngleD;
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
	
	public static Material bullseye() {
		return bullseye(matte(new Color3D(0.5D)));
	}
	
	public static Material bullseye(final Material materialA) {
		return bullseye(materialA, matte(Color3D.WHITE));
	}
	
	public static Material bullseye(final Material materialA, final Material materialB) {
		return bullseye(materialA, materialB, new Point3D(0.0D, 10.0D, 0.0D));
	}
	
	public static Material bullseye(final Material materialA, final Material materialB, final Point3D origin) {
		return bullseye(materialA, materialB, origin, 1.0D);
	}
	
	public static Material bullseye(final Material materialA, final Material materialB, final Point3D origin, final double scale) {
		return new BullseyeMaterial(materialA, materialB, origin, scale);
	}
	
	public static Material checkerboard() {
		return checkerboard(matte(new Color3D(0.5D)));
	}
	
	public static Material checkerboard(final Material materialA) {
		return checkerboard(materialA, matte(Color3D.WHITE));
	}
	
	public static Material checkerboard(final Material materialA, final Material materialB) {
		return checkerboard(materialA, materialB, 0.0D);
	}
	
	public static Material checkerboard(final Material materialA, final Material materialB, final double angleDegrees) {
		return checkerboard(materialA, materialB, angleDegrees, 1.0D);
	}
	
	public static Material checkerboard(final Material materialA, final Material materialB, final double angleDegrees, final double scale) {
		return checkerboard(materialA, materialB, angleDegrees, scale, scale);
	}
	
	public static Material checkerboard(final Material materialA, final Material materialB, final double angleDegrees, final double scaleU, final double scaleV) {
		return new CheckerboardMaterial(materialA, materialB, angleDegrees, scaleU, scaleV);
	}
	
	public static Material disney() {
		return disney(Color3D.GRAY);
	}
	
	public static Material disney(final Color3D colorColor) {
		return disney(colorColor, Color3D.BLACK);
	}
	
	public static Material disney(final Color3D colorColor, final Color3D colorEmission) {
		return disney(colorColor, colorEmission, Color3D.BLACK);
	}
	
	public static Material disney(final Color3D colorColor, final Color3D colorEmission, final Color3D colorScatterDistance) {
		return disney(colorColor, colorEmission, colorScatterDistance, 0.0D);
	}
	
	public static Material disney(final Color3D colorColor, final Color3D colorEmission, final Color3D colorScatterDistance, final double doubleAnisotropic) {
		return disney(colorColor, colorEmission, colorScatterDistance, doubleAnisotropic, 0.0D);
	}
	
	public static Material disney(final Color3D colorColor, final Color3D colorEmission, final Color3D colorScatterDistance, final double doubleAnisotropic, final double doubleClearCoat) {
		return disney(colorColor, colorEmission, colorScatterDistance, doubleAnisotropic, doubleClearCoat, 1.0D);
	}
	
	public static Material disney(final Color3D colorColor, final Color3D colorEmission, final Color3D colorScatterDistance, final double doubleAnisotropic, final double doubleClearCoat, final double doubleClearCoatGloss) {
		return disney(colorColor, colorEmission, colorScatterDistance, doubleAnisotropic, doubleClearCoat, doubleClearCoatGloss, 1.0D);
	}
	
	public static Material disney(final Color3D colorColor, final Color3D colorEmission, final Color3D colorScatterDistance, final double doubleAnisotropic, final double doubleClearCoat, final double doubleClearCoatGloss, final double doubleDiffuseTransmission) {
		return disney(colorColor, colorEmission, colorScatterDistance, doubleAnisotropic, doubleClearCoat, doubleClearCoatGloss, doubleDiffuseTransmission, 1.5D);
	}
	
	public static Material disney(final Color3D colorColor, final Color3D colorEmission, final Color3D colorScatterDistance, final double doubleAnisotropic, final double doubleClearCoat, final double doubleClearCoatGloss, final double doubleDiffuseTransmission, final double doubleEta) {
		return disney(colorColor, colorEmission, colorScatterDistance, doubleAnisotropic, doubleClearCoat, doubleClearCoatGloss, doubleDiffuseTransmission, doubleEta, 0.0D);
	}
	
	public static Material disney(final Color3D colorColor, final Color3D colorEmission, final Color3D colorScatterDistance, final double doubleAnisotropic, final double doubleClearCoat, final double doubleClearCoatGloss, final double doubleDiffuseTransmission, final double doubleEta, final double doubleFlatness) {
		return disney(colorColor, colorEmission, colorScatterDistance, doubleAnisotropic, doubleClearCoat, doubleClearCoatGloss, doubleDiffuseTransmission, doubleEta, doubleFlatness, 0.0D);
	}
	
	public static Material disney(final Color3D colorColor, final Color3D colorEmission, final Color3D colorScatterDistance, final double doubleAnisotropic, final double doubleClearCoat, final double doubleClearCoatGloss, final double doubleDiffuseTransmission, final double doubleEta, final double doubleFlatness, final double doubleMetallic) {
		return disney(colorColor, colorEmission, colorScatterDistance, doubleAnisotropic, doubleClearCoat, doubleClearCoatGloss, doubleDiffuseTransmission, doubleEta, doubleFlatness, doubleMetallic, 0.5D);
	}
	
	public static Material disney(final Color3D colorColor, final Color3D colorEmission, final Color3D colorScatterDistance, final double doubleAnisotropic, final double doubleClearCoat, final double doubleClearCoatGloss, final double doubleDiffuseTransmission, final double doubleEta, final double doubleFlatness, final double doubleMetallic, final double doubleRoughness) {
		return disney(colorColor, colorEmission, colorScatterDistance, doubleAnisotropic, doubleClearCoat, doubleClearCoatGloss, doubleDiffuseTransmission, doubleEta, doubleFlatness, doubleMetallic, doubleRoughness, 0.0D);
	}
	
	public static Material disney(final Color3D colorColor, final Color3D colorEmission, final Color3D colorScatterDistance, final double doubleAnisotropic, final double doubleClearCoat, final double doubleClearCoatGloss, final double doubleDiffuseTransmission, final double doubleEta, final double doubleFlatness, final double doubleMetallic, final double doubleRoughness, final double doubleSheen) {
		return disney(colorColor, colorEmission, colorScatterDistance, doubleAnisotropic, doubleClearCoat, doubleClearCoatGloss, doubleDiffuseTransmission, doubleEta, doubleFlatness, doubleMetallic, doubleRoughness, doubleSheen, 0.5D);
	}
	
	public static Material disney(final Color3D colorColor, final Color3D colorEmission, final Color3D colorScatterDistance, final double doubleAnisotropic, final double doubleClearCoat, final double doubleClearCoatGloss, final double doubleDiffuseTransmission, final double doubleEta, final double doubleFlatness, final double doubleMetallic, final double doubleRoughness, final double doubleSheen, final double doubleSheenTint) {
		return disney(colorColor, colorEmission, colorScatterDistance, doubleAnisotropic, doubleClearCoat, doubleClearCoatGloss, doubleDiffuseTransmission, doubleEta, doubleFlatness, doubleMetallic, doubleRoughness, doubleSheen, doubleSheenTint, 0.0D);
	}
	
	public static Material disney(final Color3D colorColor, final Color3D colorEmission, final Color3D colorScatterDistance, final double doubleAnisotropic, final double doubleClearCoat, final double doubleClearCoatGloss, final double doubleDiffuseTransmission, final double doubleEta, final double doubleFlatness, final double doubleMetallic, final double doubleRoughness, final double doubleSheen, final double doubleSheenTint, final double doubleSpecularTint) {
		return disney(colorColor, colorEmission, colorScatterDistance, doubleAnisotropic, doubleClearCoat, doubleClearCoatGloss, doubleDiffuseTransmission, doubleEta, doubleFlatness, doubleMetallic, doubleRoughness, doubleSheen, doubleSheenTint, doubleSpecularTint, 0.0D);
	}
	
	public static Material disney(final Color3D colorColor, final Color3D colorEmission, final Color3D colorScatterDistance, final double doubleAnisotropic, final double doubleClearCoat, final double doubleClearCoatGloss, final double doubleDiffuseTransmission, final double doubleEta, final double doubleFlatness, final double doubleMetallic, final double doubleRoughness, final double doubleSheen, final double doubleSheenTint, final double doubleSpecularTint, final double doubleSpecularTransmission) {
		return disney(colorColor, colorEmission, colorScatterDistance, doubleAnisotropic, doubleClearCoat, doubleClearCoatGloss, doubleDiffuseTransmission, doubleEta, doubleFlatness, doubleMetallic, doubleRoughness, doubleSheen, doubleSheenTint, doubleSpecularTint, doubleSpecularTransmission, false);
	}
	
	public static Material disney(final Color3D colorColor, final Color3D colorEmission, final Color3D colorScatterDistance, final double doubleAnisotropic, final double doubleClearCoat, final double doubleClearCoatGloss, final double doubleDiffuseTransmission, final double doubleEta, final double doubleFlatness, final double doubleMetallic, final double doubleRoughness, final double doubleSheen, final double doubleSheenTint, final double doubleSpecularTint, final double doubleSpecularTransmission, final boolean isThin) {
		return disney(Texture.constant(colorColor), Texture.constant(colorEmission), Texture.constant(colorScatterDistance), Texture.constant(doubleAnisotropic), Texture.constant(doubleClearCoat), Texture.constant(doubleClearCoatGloss), Texture.constant(doubleDiffuseTransmission), Texture.constant(doubleEta), Texture.constant(doubleFlatness), Texture.constant(doubleMetallic), Texture.constant(doubleRoughness), Texture.constant(doubleSheen), Texture.constant(doubleSheenTint), Texture.constant(doubleSpecularTint), Texture.constant(doubleSpecularTransmission), isThin);
	}
	
	public static Material disney(final Texture textureColor) {
		return disney(textureColor, Texture.constant(Color3D.BLACK));
	}
	
	public static Material disney(final Texture textureColor, final Texture textureEmission) {
		return disney(textureColor, textureEmission, Texture.constant(Color3D.BLACK));
	}
	
	public static Material disney(final Texture textureColor, final Texture textureEmission, final Texture textureScatterDistance) {
		return disney(textureColor, textureEmission, textureScatterDistance, Texture.constant(Color3D.BLACK));
	}
	
	public static Material disney(final Texture textureColor, final Texture textureEmission, final Texture textureScatterDistance, final Texture textureAnisotropic) {
		return disney(textureColor, textureEmission, textureScatterDistance, textureAnisotropic, Texture.constant(Color3D.BLACK));
	}
	
	public static Material disney(final Texture textureColor, final Texture textureEmission, final Texture textureScatterDistance, final Texture textureAnisotropic, final Texture textureClearCoat) {
		return disney(textureColor, textureEmission, textureScatterDistance, textureAnisotropic, textureClearCoat, Texture.constant(Color3D.WHITE));
	}
	
	public static Material disney(final Texture textureColor, final Texture textureEmission, final Texture textureScatterDistance, final Texture textureAnisotropic, final Texture textureClearCoat, final Texture textureClearCoatGloss) {
		return disney(textureColor, textureEmission, textureScatterDistance, textureAnisotropic, textureClearCoat, textureClearCoatGloss, Texture.constant(Color3D.WHITE));
	}
	
	public static Material disney(final Texture textureColor, final Texture textureEmission, final Texture textureScatterDistance, final Texture textureAnisotropic, final Texture textureClearCoat, final Texture textureClearCoatGloss, final Texture textureDiffuseTransmission) {
		return disney(textureColor, textureEmission, textureScatterDistance, textureAnisotropic, textureClearCoat, textureClearCoatGloss, textureDiffuseTransmission, Texture.constant(1.5D));
	}
	
	public static Material disney(final Texture textureColor, final Texture textureEmission, final Texture textureScatterDistance, final Texture textureAnisotropic, final Texture textureClearCoat, final Texture textureClearCoatGloss, final Texture textureDiffuseTransmission, final Texture textureEta) {
		return disney(textureColor, textureEmission, textureScatterDistance, textureAnisotropic, textureClearCoat, textureClearCoatGloss, textureDiffuseTransmission, textureEta, Texture.constant(Color3D.BLACK));
	}
	
	public static Material disney(final Texture textureColor, final Texture textureEmission, final Texture textureScatterDistance, final Texture textureAnisotropic, final Texture textureClearCoat, final Texture textureClearCoatGloss, final Texture textureDiffuseTransmission, final Texture textureEta, final Texture textureFlatness) {
		return disney(textureColor, textureEmission, textureScatterDistance, textureAnisotropic, textureClearCoat, textureClearCoatGloss, textureDiffuseTransmission, textureEta, textureFlatness, Texture.constant(Color3D.BLACK));
	}
	
	public static Material disney(final Texture textureColor, final Texture textureEmission, final Texture textureScatterDistance, final Texture textureAnisotropic, final Texture textureClearCoat, final Texture textureClearCoatGloss, final Texture textureDiffuseTransmission, final Texture textureEta, final Texture textureFlatness, final Texture textureMetallic) {
		return disney(textureColor, textureEmission, textureScatterDistance, textureAnisotropic, textureClearCoat, textureClearCoatGloss, textureDiffuseTransmission, textureEta, textureFlatness, textureMetallic, Texture.constant(Color3D.GRAY));
	}
	
	public static Material disney(final Texture textureColor, final Texture textureEmission, final Texture textureScatterDistance, final Texture textureAnisotropic, final Texture textureClearCoat, final Texture textureClearCoatGloss, final Texture textureDiffuseTransmission, final Texture textureEta, final Texture textureFlatness, final Texture textureMetallic, final Texture textureRoughness) {
		return disney(textureColor, textureEmission, textureScatterDistance, textureAnisotropic, textureClearCoat, textureClearCoatGloss, textureDiffuseTransmission, textureEta, textureFlatness, textureMetallic, textureRoughness, Texture.constant(Color3D.BLACK));
	}
	
	public static Material disney(final Texture textureColor, final Texture textureEmission, final Texture textureScatterDistance, final Texture textureAnisotropic, final Texture textureClearCoat, final Texture textureClearCoatGloss, final Texture textureDiffuseTransmission, final Texture textureEta, final Texture textureFlatness, final Texture textureMetallic, final Texture textureRoughness, final Texture textureSheen) {
		return disney(textureColor, textureEmission, textureScatterDistance, textureAnisotropic, textureClearCoat, textureClearCoatGloss, textureDiffuseTransmission, textureEta, textureFlatness, textureMetallic, textureRoughness, textureSheen, Texture.constant(Color3D.GRAY));
	}
	
	public static Material disney(final Texture textureColor, final Texture textureEmission, final Texture textureScatterDistance, final Texture textureAnisotropic, final Texture textureClearCoat, final Texture textureClearCoatGloss, final Texture textureDiffuseTransmission, final Texture textureEta, final Texture textureFlatness, final Texture textureMetallic, final Texture textureRoughness, final Texture textureSheen, final Texture textureSheenTint) {
		return disney(textureColor, textureEmission, textureScatterDistance, textureAnisotropic, textureClearCoat, textureClearCoatGloss, textureDiffuseTransmission, textureEta, textureFlatness, textureMetallic, textureRoughness, textureSheen, textureSheenTint, Texture.constant(Color3D.BLACK));
	}
	
	public static Material disney(final Texture textureColor, final Texture textureEmission, final Texture textureScatterDistance, final Texture textureAnisotropic, final Texture textureClearCoat, final Texture textureClearCoatGloss, final Texture textureDiffuseTransmission, final Texture textureEta, final Texture textureFlatness, final Texture textureMetallic, final Texture textureRoughness, final Texture textureSheen, final Texture textureSheenTint, final Texture textureSpecularTint) {
		return disney(textureColor, textureEmission, textureScatterDistance, textureAnisotropic, textureClearCoat, textureClearCoatGloss, textureDiffuseTransmission, textureEta, textureFlatness, textureMetallic, textureRoughness, textureSheen, textureSheenTint, textureSpecularTint, Texture.constant(Color3D.BLACK));
	}
	
	public static Material disney(final Texture textureColor, final Texture textureEmission, final Texture textureScatterDistance, final Texture textureAnisotropic, final Texture textureClearCoat, final Texture textureClearCoatGloss, final Texture textureDiffuseTransmission, final Texture textureEta, final Texture textureFlatness, final Texture textureMetallic, final Texture textureRoughness, final Texture textureSheen, final Texture textureSheenTint, final Texture textureSpecularTint, final Texture textureSpecularTransmission) {
		return disney(textureColor, textureEmission, textureScatterDistance, textureAnisotropic, textureClearCoat, textureClearCoatGloss, textureDiffuseTransmission, textureEta, textureFlatness, textureMetallic, textureRoughness, textureSheen, textureSheenTint, textureSpecularTint, textureSpecularTransmission, false);
	}
	
	public static Material disney(final Texture textureColor, final Texture textureEmission, final Texture textureScatterDistance, final Texture textureAnisotropic, final Texture textureClearCoat, final Texture textureClearCoatGloss, final Texture textureDiffuseTransmission, final Texture textureEta, final Texture textureFlatness, final Texture textureMetallic, final Texture textureRoughness, final Texture textureSheen, final Texture textureSheenTint, final Texture textureSpecularTint, final Texture textureSpecularTransmission, final boolean isThin) {
		return new DisneyMaterial(textureColor, textureEmission, textureScatterDistance, textureAnisotropic, textureClearCoat, textureClearCoatGloss, textureDiffuseTransmission, textureEta, textureFlatness, textureMetallic, textureRoughness, textureSheen, textureSheenTint, textureSpecularTint, textureSpecularTransmission, isThin);
	}
	
	public static Material glass() {
		return glass(Color3D.WHITE);
	}
	
	public static Material glass(final Color3D colorK) {
		return glass(colorK, colorK);
	}
	
	public static Material glass(final Color3D colorKR, final Color3D colorKT) {
		return glass(colorKR, colorKT, Color3D.BLACK);
	}
	
	public static Material glass(final Color3D colorKR, final Color3D colorKT, final Color3D colorEmission) {
		return glass(colorKR, colorKT, colorEmission, 1.5D);
	}
	
	public static Material glass(final Color3D colorKR, final Color3D colorKT, final Color3D colorEmission, final double doubleEta) {
		return glass(colorKR, colorKT, colorEmission, doubleEta, 0.0D);
	}
	
	public static Material glass(final Color3D colorKR, final Color3D colorKT, final Color3D colorEmission, final double doubleEta, final double doubleRoughness) {
		return glass(colorKR, colorKT, colorEmission, doubleEta, doubleRoughness, doubleRoughness);
	}
	
	public static Material glass(final Color3D colorKR, final Color3D colorKT, final Color3D colorEmission, final double doubleEta, final double doubleRoughnessU, final double doubleRoughnessV) {
		return glass(colorKR, colorKT, colorEmission, doubleEta, doubleRoughnessU, doubleRoughnessV, true);
	}
	
	public static Material glass(final Color3D colorKR, final Color3D colorKT, final Color3D colorEmission, final double doubleEta, final double doubleRoughnessU, final double doubleRoughnessV, final boolean isRemappingRoughness) {
		return glass(Texture.constant(colorKR), Texture.constant(colorKT), Texture.constant(colorEmission), Texture.constant(doubleEta), Texture.constant(doubleRoughnessU), Texture.constant(doubleRoughnessV), isRemappingRoughness);
	}
	
	public static Material glass(final Texture textureK) {
		return glass(textureK, textureK);
	}
	
	public static Material glass(final Texture textureKR, final Texture textureKT) {
		return glass(textureKR, textureKT, Texture.constant(Color3D.BLACK));
	}
	
	public static Material glass(final Texture textureKR, final Texture textureKT, final Texture textureEmission) {
		return glass(textureKR, textureKT, textureEmission, Texture.constant(1.5D));
	}
	
	public static Material glass(final Texture textureKR, final Texture textureKT, final Texture textureEmission, final Texture textureEta) {
		return glass(textureKR, textureKT, textureEmission, textureEta, Texture.constant(0.0D));
	}
	
	public static Material glass(final Texture textureKR, final Texture textureKT, final Texture textureEmission, final Texture textureEta, final Texture textureRoughness) {
		return glass(textureKR, textureKT, textureEmission, textureEta, textureRoughness, textureRoughness);
	}
	
	public static Material glass(final Texture textureKR, final Texture textureKT, final Texture textureEmission, final Texture textureEta, final Texture textureRoughnessU, final Texture textureRoughnessV) {
		return glass(textureKR, textureKT, textureEmission, textureEta, textureRoughnessU, textureRoughnessV, true);
	}
	
	public static Material glass(final Texture textureKR, final Texture textureKT, final Texture textureEmission, final Texture textureEta, final Texture textureRoughnessU, final Texture textureRoughnessV, final boolean isRemappingRoughness) {
		return new GlassMaterial(textureKR, textureKT, textureEmission, textureEta, textureRoughnessU, textureRoughnessV, isRemappingRoughness);
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
		return matte(colorKD, colorEmission, 0.0D);
	}
	
	public static Material matte(final Color3D colorKD, final Color3D colorEmission, final double doubleAngle) {
		return matte(Texture.constant(colorKD), Texture.constant(colorEmission), Texture.constant(doubleAngle));
	}
	
	public static Material matte(final Texture textureKD) {
		return matte(textureKD, Texture.constant(Color3D.BLACK));
	}
	
	public static Material matte(final Texture textureKD, final Texture textureEmission) {
		return new MatteMaterial(textureKD, textureEmission, Texture.constant(0.0D));
	}
	
	public static Material matte(final Texture textureKD, final Texture textureEmission, final Texture textureAngle) {
		return new MatteMaterial(textureKD, textureEmission, textureAngle);
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
		public BXDFType getBXDFType() {
			return BXDFType.GLOSSY_REFLECTION;
		}
		
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
			final double f = Fresnel.evaluateDielectricSchlick(Vector3D.dotProduct(o, hNormalized), 1.0D);
			
			final Color3D r = this.r;
			
			return Color3D.divide(Color3D.multiply(Color3D.multiply(r, d), f), 4.0D * cosThetaAbsI * cosThetaAbsO);
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
			
			return Optional.of(new BXDFResult(getBXDFType(), result, i, pDF));
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
			this.bXDFs = Arrays.asList(bXDF);
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
			
			final BXDF[] bXDFs = new BXDF[8];
			
			final int matches = doComputeMatches(BXDFType.ALL, bXDFs);
			
			if(matches == 0) {
				return Optional.empty();
			}
			
			final int match = Ints.min((int)(Doubles.floor(sampleU * matches)), matches - 1);
			
			final BXDF bXDF = bXDFs[match];
			
			final double u = Doubles.min(sampleU * matches - match, 0.99999994D);
			final double v = sampleV;
			
			final Point2D p = new Point2D(u, v);
			
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
			
			if(matches > 1 && !bXDFResult.getBXDFType().isSpecular()) {
				final double iDotN = Vector3D.dotProduct(iWS, nWS);
				final double oDotN = Vector3D.dotProduct(oWS, nWS);
				
				final boolean isReflecting = iDotN * oDotN > 0.0D;
				
				result = Color3D.BLACK;
				
				for(int i = 0; i < matches; i++) {
					final BXDF currentBXDF = bXDFs[i];
					
					if(currentBXDF != bXDF) {
						probabilityDensityFunctionValue += currentBXDF.evaluatePDF(oLS, iLS);
					}
					
					if(isReflecting && bXDF.getBXDFType().hasReflection() || !isReflecting && bXDF.getBXDFType().hasTransmission()) {
						result = Color3D.add(result, currentBXDF.evaluateDF(oLS, iLS));
					}
				}
				
				probabilityDensityFunctionValue /= matches;
			}
			
			final Color3D reflectance = Color3D.divide(Color3D.multiply(result, Vector3D.dotProductAbs(iWS, nWS)), probabilityDensityFunctionValue);
			
			return Optional.of(new Result(emission, reflectance, ray));
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		private int doComputeMatches(final BXDFType bXDFType, final BXDF[] matchingBXDFs) {
			int matches = 0;
			
			for(final BXDF bXDF : this.bXDFs) {
				if(bXDF.getBXDFType().matches(bXDFType)) {
					matchingBXDFs[matches] = bXDF;
					
					matches++;
				}
			}
			
			return matches;
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static interface BXDF {
		BXDFType getBXDFType();
		
		Color3D evaluateDF(final Vector3D o, final Vector3D i);
		
		Optional<BXDFResult> sampleDF(final Vector3D o, final Point2D p);
		
		double evaluatePDF(final Vector3D o, final Vector3D i);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final class BXDFResult {
		private final BXDFType bXDFType;
		private final Color3D result;
		private final Vector3D i;
		private final double pDF;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public BXDFResult(final BXDFType bXDFType, final Color3D result, final Vector3D i, final double pDF) {
			this.bXDFType = Objects.requireNonNull(bXDFType, "bXDFType == null");
			this.result = Objects.requireNonNull(result, "result == null");
			this.i = Objects.requireNonNull(i, "i == null");
			this.pDF = pDF;
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public BXDFType getBXDFType() {
			return this.bXDFType;
		}
		
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
	
	private static final class BXDFType {
		public static final BXDFType ALL = new BXDFType(true, true, true, true, true);
//		public static final BXDFType ALL_EXCEPT_SPECULAR = new BXDFType(true, true, true, true, false);
		public static final BXDFType DIFFUSE_REFLECTION = doCreateReflection(true, false, false);
//		public static final BXDFType DIFFUSE_REFLECTION_AND_TRANSMISSION = doCreateReflectionAndTransmission(true, false, false);
		public static final BXDFType DIFFUSE_TRANSMISSION = doCreateTransmission(true, false, false);
		public static final BXDFType GLOSSY_REFLECTION = doCreateReflection(false, true, false);
//		public static final BXDFType GLOSSY_REFLECTION_AND_TRANSMISSION = doCreateReflectionAndTransmission(false, true, false);
		public static final BXDFType GLOSSY_TRANSMISSION = doCreateTransmission(false, true, false);
		public static final BXDFType SPECULAR_REFLECTION = doCreateReflection(false, false, true);
		public static final BXDFType SPECULAR_REFLECTION_AND_TRANSMISSION = doCreateReflectionAndTransmission(false, false, true);
		public static final BXDFType SPECULAR_TRANSMISSION = doCreateTransmission(false, false, true);
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		private static final int BIT_FLAG_HAS_REFLECTION = 1 << 0;
		private static final int BIT_FLAG_HAS_TRANSMISSION = 1 << 1;
		private static final int BIT_FLAG_IS_DIFFUSE = 1 << 2;
		private static final int BIT_FLAG_IS_GLOSSY = 1 << 3;
		private static final int BIT_FLAG_IS_SPECULAR = 1 << 4;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		private final int bitFlags;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		private BXDFType(final boolean hasReflection, final boolean hasTransmission, final boolean isDiffuse, final boolean isGlossy, final boolean isSpecular) {
			this.bitFlags = doCreateBitFlags(hasReflection, hasTransmission, isDiffuse, isGlossy, isSpecular);
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		@Override
		public String toString() {
			if(isDiffuse() && hasReflection() && hasTransmission()) {
				return "BXDFType.DIFFUSE_REFLECTION_AND_TRANSMISSION";
			} else if(isDiffuse() && hasReflection()) {
				return "BXDFType.DIFFUSE_REFLECTION";
			} else if(isDiffuse() && hasTransmission()) {
				return "BXDFType.DIFFUSE_TRANSMISSION";
			} else if(isGlossy() && hasReflection() && hasTransmission()) {
				return "BXDFType.GLOSSY_REFLECTION_AND_TRANSMISSION";
			} else if(isGlossy() && hasReflection()) {
				return "BXDFType.GLOSSY_REFLECTION";
			} else if(isGlossy() && hasTransmission()) {
				return "BXDFType.GLOSSY_TRANSMISSION";
			} else if(isSpecular() && hasReflection() && hasTransmission()) {
				return "BXDFType.SPECULAR_REFLECTION_AND_TRANSMISSION";
			} else if(isSpecular() && hasReflection()) {
				return "BXDFType.SPECULAR_REFLECTION";
			} else if(isSpecular() && hasTransmission()) {
				return "BXDFType.SPECULAR_TRANSMISSION";
			} else {
				return "";
			}
		}
		
		@Override
		public boolean equals(final Object object) {
			if(object == this) {
				return true;
			} else if(!(object instanceof BXDFType)) {
				return false;
			} else if(this.bitFlags != BXDFType.class.cast(object).bitFlags) {
				return false;
			} else {
				return true;
			}
		}
		
		public boolean hasReflection() {
			return (this.bitFlags & BIT_FLAG_HAS_REFLECTION) == BIT_FLAG_HAS_REFLECTION;
		}
		
		public boolean hasTransmission() {
			return (this.bitFlags & BIT_FLAG_HAS_TRANSMISSION) == BIT_FLAG_HAS_TRANSMISSION;
		}
		
		public boolean isDiffuse() {
			return (this.bitFlags & BIT_FLAG_IS_DIFFUSE) == BIT_FLAG_IS_DIFFUSE;
		}
		
		public boolean isGlossy() {
			return (this.bitFlags & BIT_FLAG_IS_GLOSSY) == BIT_FLAG_IS_GLOSSY;
		}
		
		public boolean isSpecular() {
			return (this.bitFlags & BIT_FLAG_IS_SPECULAR) == BIT_FLAG_IS_SPECULAR;
		}
		
		public boolean matches(final BXDFType bXDFType) {
			return (this.bitFlags & bXDFType.bitFlags) == this.bitFlags;
		}
		
		@Override
		public int hashCode() {
			return Objects.hash(Integer.valueOf(this.bitFlags));
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		private static BXDFType doCreateReflection(final boolean isDiffuse, final boolean isGlossy, final boolean isSpecular) {
			return new BXDFType(true, false, isDiffuse, isGlossy, isSpecular);
		}
		
		private static BXDFType doCreateReflectionAndTransmission(final boolean isDiffuse, final boolean isGlossy, final boolean isSpecular) {
			return new BXDFType(true, true, isDiffuse, isGlossy, isSpecular);
		}
		
		private static BXDFType doCreateTransmission(final boolean isDiffuse, final boolean isGlossy, final boolean isSpecular) {
			return new BXDFType(false, true, isDiffuse, isGlossy, isSpecular);
		}
		
		private static int doCreateBitFlags(final boolean hasReflection, final boolean hasTransmission, final boolean isDiffuse, final boolean isGlossy, final boolean isSpecular) {
			int bitFlags = 0;
			
			if(hasReflection) {
				bitFlags |= BIT_FLAG_HAS_REFLECTION;
			}
			
			if(hasTransmission) {
				bitFlags |= BIT_FLAG_HAS_TRANSMISSION;
			}
			
			if(isDiffuse) {
				bitFlags |= BIT_FLAG_IS_DIFFUSE;
			}
			
			if(isGlossy) {
				bitFlags |= BIT_FLAG_IS_GLOSSY;
			}
			
			if(isSpecular) {
				bitFlags |= BIT_FLAG_IS_SPECULAR;
			}
			
			return bitFlags;
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
	
	private static final class ConstantFresnel extends Fresnel {
		private final Color3D light;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public ConstantFresnel() {
			this(Color3D.WHITE);
		}
		
		public ConstantFresnel(final Color3D light) {
			this.light = Objects.requireNonNull(light, "light == null");
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		@Override
		public Color3D evaluate(final double cosThetaI) {
			return this.light;
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
	
	private static final class DisneyClearCoatBRDF implements BXDF {
		private final double gloss;
		private final double weight;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public DisneyClearCoatBRDF(final double gloss, final double weight) {
			this.gloss = gloss;
			this.weight = weight;
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		@Override
		public BXDFType getBXDFType() {
			return BXDFType.GLOSSY_REFLECTION;
		}
		
		@Override
		public Color3D evaluateDF(final Vector3D o, final Vector3D i) {
			final Vector3D h = Vector3D.add(i, o);
			
			if(h.isZero()) {
				return Color3D.BLACK;
			}
			
			final Vector3D hNormalized = Vector3D.normalize(h);
			
			final double d = doGTR1(hNormalized.cosThetaAbs(), this.gloss);
			final double f = Doubles.lerp(0.04D, 1.0D, Doubles.pow5(Doubles.saturate(1.0D - Vector3D.dotProduct(o, hNormalized))));
			final double g = doSmithGGGX(o.cosThetaAbs(), 0.25D) * doSmithGGGX(i.cosThetaAbs(), 0.25D);
			
			return new Color3D(this.weight * g * f * d / 4.0D);
		}
		
		@Override
		public Optional<BXDFResult> sampleDF(final Vector3D o, final Point2D p) {
			if(Doubles.isZero(o.z)) {
				return Optional.empty();
			}
			
			final double glossSquared = this.gloss * this.gloss;
			
			final double cosTheta = Doubles.sqrt(Doubles.max(0.0D, (1.0D - Doubles.pow(glossSquared, 1.0D - p.x)) / (1.0D - glossSquared)));
			final double sinTheta = Doubles.sqrt(Doubles.max(0.0D, 1.0D - cosTheta * cosTheta));
			final double phi = 2.0D * Doubles.PI * p.y;
			
			final Vector3D hSample = Vector3D.directionSpherical(sinTheta, cosTheta, phi);
			final Vector3D h = Vector3D.sameHemisphereZ(o, hSample) ? hSample : Vector3D.negate(hSample);
			final Vector3D i = Vector3D.reflection(o, h);
			
			if(!Vector3D.sameHemisphereZ(o, i)) {
				return Optional.empty();
			}
			
			final Color3D result = evaluateDF(o, i);
			
			final double pDF = evaluatePDF(o, i);
			
			return Optional.of(new BXDFResult(getBXDFType(), result, i, pDF));
		}
		
		@Override
		public double evaluatePDF(final Vector3D o, final Vector3D i) {
			if(!Vector3D.sameHemisphereZ(o, i)) {
				return 0.0D;
			}
			
			final Vector3D h = Vector3D.add(i, o);
			
			if(h.isZero()) {
				return 0.0D;
			}
			
			final Vector3D hNormalized = Vector3D.normalize(h);
			
			final double cosThetaAbsH = hNormalized.cosThetaAbs();
			
			return doGTR1(cosThetaAbsH, this.gloss) * cosThetaAbsH / (4.0D * Vector3D.dotProduct(o, hNormalized));
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		private static double doGTR1(final double cosTheta, final double alpha) {
			final double a = alpha * alpha;
			final double b = (a - 1.0D) / (Doubles.PI * Doubles.log(a) * (1.0D + (a - 1.0D) * cosTheta * cosTheta));
			
			return b;
		}
		
		private static double doSmithGGGX(final double cosTheta, final double alpha) {
			final double a = alpha * alpha;
			final double b = cosTheta * cosTheta;
			final double c = 1.0D / (cosTheta + Doubles.sqrt(a + b - a * b));
			
			return c;
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final class DisneyDiffuseBRDF implements BXDF {
		private final Color3D reflectance;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public DisneyDiffuseBRDF(final Color3D reflectance) {
			this.reflectance = Objects.requireNonNull(reflectance, "reflectance == null");
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		@Override
		public BXDFType getBXDFType() {
			return BXDFType.DIFFUSE_REFLECTION;
		}
		
		@Override
		public Color3D evaluateDF(final Vector3D o, final Vector3D i) {
			final double fresnelOA = Fresnel.evaluateDielectricSchlickWeight(o.cosThetaAbs());
			final double fresnelIA = Fresnel.evaluateDielectricSchlickWeight(i.cosThetaAbs());
			
			final double fresnelOB = 1.0D - fresnelOA / 2.0D;
			final double fresnelIB = 1.0D - fresnelIA / 2.0D;
			
			final double r = this.reflectance.r * Doubles.PI_RECIPROCAL * fresnelOB * fresnelIB;
			final double g = this.reflectance.g * Doubles.PI_RECIPROCAL * fresnelOB * fresnelIB;
			final double b = this.reflectance.b * Doubles.PI_RECIPROCAL * fresnelOB * fresnelIB;
			
			return new Color3D(r, g, b);
		}
		
		@Override
		public Optional<BXDFResult> sampleDF(final Vector3D o, final Point2D p) {
			final Vector3D iSample = Vector3D.sampleHemisphereCosineDistribution(p);
			final Vector3D i = o.z < 0.0D ? Vector3D.negateZ(iSample) : iSample;
			
			final Color3D result = evaluateDF(o, i);
			
			final double pDF = evaluatePDF(o, i);
			
			return Optional.of(new BXDFResult(getBXDFType(), result, i, pDF));
		}
		
		@Override
		public double evaluatePDF(final Vector3D o, final Vector3D i) {
			return Vector3D.sameHemisphereZ(o, i) ? i.cosThetaAbs() * Doubles.PI_RECIPROCAL : 0.0D;
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final class DisneyFakeSSBRDF implements BXDF {
		private final Color3D reflectance;
		private final double roughness;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public DisneyFakeSSBRDF(final Color3D reflectance, final double roughness) {
			this.reflectance = Objects.requireNonNull(reflectance, "reflectance == null");
			this.roughness = roughness;
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		@Override
		public BXDFType getBXDFType() {
			return BXDFType.DIFFUSE_REFLECTION;
		}
		
		@Override
		public Color3D evaluateDF(final Vector3D o, final Vector3D i) {
			final Vector3D h = Vector3D.add(i, o);
			
			if(h.isZero()) {
				return Color3D.BLACK;
			}
			
			final Vector3D hNormalized = Vector3D.normalize(h);
			
			final double cosThetaD = Vector3D.dotProduct(i, hNormalized);
			final double cosThetaAbsO = o.cosThetaAbs();
			final double cosThetaAbsI = i.cosThetaAbs();
			
			final double fresnelO = Fresnel.evaluateDielectricSchlickWeight(cosThetaAbsO);
			final double fresnelI = Fresnel.evaluateDielectricSchlickWeight(cosThetaAbsI);
			
			final double fresnelSS90 = cosThetaD * cosThetaD * this.roughness;
			final double fresnelSS = Doubles.lerp(1.0D, fresnelSS90, fresnelO) * Doubles.lerp(1.0D, fresnelSS90, fresnelI);
			
			final double scaleSS = 1.25D * (fresnelSS * (1.0D / (cosThetaAbsO + cosThetaAbsI) - 0.5D) + 0.5D);
			
			final double r = this.reflectance.r * Doubles.PI_RECIPROCAL * scaleSS;
			final double g = this.reflectance.g * Doubles.PI_RECIPROCAL * scaleSS;
			final double b = this.reflectance.b * Doubles.PI_RECIPROCAL * scaleSS;
			
			return new Color3D(r, g, b);
		}
		
		@Override
		public Optional<BXDFResult> sampleDF(final Vector3D o, final Point2D p) {
			final Vector3D iSample = Vector3D.sampleHemisphereCosineDistribution(p);
			final Vector3D i = o.z < 0.0D ? Vector3D.negateZ(iSample) : iSample;
			
			final Color3D result = evaluateDF(o, i);
			
			final double pDF = evaluatePDF(o, i);
			
			return Optional.of(new BXDFResult(getBXDFType(), result, i, pDF));
		}
		
		@Override
		public double evaluatePDF(final Vector3D o, final Vector3D i) {
			return Vector3D.sameHemisphereZ(o, i) ? i.cosThetaAbs() * Doubles.PI_RECIPROCAL : 0.0D;
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final class DisneyFresnel extends Fresnel {
		private final Color3D r0;
		private final double eta;
		private final double metallic;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public DisneyFresnel(final Color3D r0, final double eta, final double metallic) {
			this.r0 = Objects.requireNonNull(r0, "r0 == null");
			this.eta = eta;
			this.metallic = metallic;
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		@Override
		public Color3D evaluate(final double cosThetaI) {
			final Color3D a = new Color3D(Fresnel.evaluateDielectric(cosThetaI, 1.0D, this.eta));
			final Color3D b = Color3D.blend(this.r0, Color3D.WHITE, Fresnel.evaluateDielectricSchlickWeight(cosThetaI));
			final Color3D c = Color3D.blend(a, b, this.metallic);
			
			return c;
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final class DisneyMaterial extends Material {
		private final Texture textureAnisotropic;
		private final Texture textureClearCoat;
		private final Texture textureClearCoatGloss;
		private final Texture textureColor;
		private final Texture textureDiffuseTransmission;
		private final Texture textureEmission;
		private final Texture textureEta;
		private final Texture textureFlatness;
		private final Texture textureMetallic;
		private final Texture textureRoughness;
		private final Texture textureScatterDistance;
		private final Texture textureSheen;
		private final Texture textureSheenTint;
		private final Texture textureSpecularTint;
		private final Texture textureSpecularTransmission;
		private final boolean isThin;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public DisneyMaterial(final Texture textureColor, final Texture textureEmission, final Texture textureScatterDistance, final Texture textureAnisotropic, final Texture textureClearCoat, final Texture textureClearCoatGloss, final Texture textureDiffuseTransmission, final Texture textureEta, final Texture textureFlatness, final Texture textureMetallic, final Texture textureRoughness, final Texture textureSheen, final Texture textureSheenTint, final Texture textureSpecularTint, final Texture textureSpecularTransmission, final boolean isThin) {
			this.textureColor = Objects.requireNonNull(textureColor, "textureColor == null");
			this.textureEmission = Objects.requireNonNull(textureEmission, "textureEmission == null");
			this.textureScatterDistance = Objects.requireNonNull(textureScatterDistance, "textureScatterDistance == null");
			this.textureAnisotropic = Objects.requireNonNull(textureAnisotropic, "textureAnisotropic == null");
			this.textureClearCoat = Objects.requireNonNull(textureClearCoat, "textureClearCoat == null");
			this.textureClearCoatGloss = Objects.requireNonNull(textureClearCoatGloss, "textureClearCoatGloss == null");
			this.textureDiffuseTransmission = Objects.requireNonNull(textureDiffuseTransmission, "textureDiffuseTransmission == null");
			this.textureEta = Objects.requireNonNull(textureEta, "textureEta == null");
			this.textureFlatness = Objects.requireNonNull(textureFlatness, "textureFlatness == null");
			this.textureMetallic = Objects.requireNonNull(textureMetallic, "textureMetallic == null");
			this.textureRoughness = Objects.requireNonNull(textureRoughness, "textureRoughness == null");
			this.textureSheen = Objects.requireNonNull(textureSheen, "textureSheen == null");
			this.textureSheenTint = Objects.requireNonNull(textureSheenTint, "textureSheenTint == null");
			this.textureSpecularTint = Objects.requireNonNull(textureSpecularTint, "textureSpecularTint == null");
			this.textureSpecularTransmission = Objects.requireNonNull(textureSpecularTransmission, "textureSpecularTransmission == null");
			this.isThin = isThin;
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		@Override
		public Optional<Result> compute(final Intersection intersection) {
			final List<BXDF> bXDFs = new ArrayList<>();
			
			final double doubleAnisotropic = this.textureAnisotropic.compute(intersection).average();
			final double doubleClearCoat = this.textureClearCoat.compute(intersection).average();
			final double doubleDiffuseTransmission = this.textureDiffuseTransmission.compute(intersection).average() / 2.0D;
			final double doubleEta = this.textureEta.compute(intersection).average();
			final double doubleMetallic = this.textureMetallic.compute(intersection).average();
			final double doubleRoughness = this.textureRoughness.compute(intersection).average();
			final double doubleSheen = this.textureSheen.compute(intersection).average();
			final double doubleSpecularTint = this.textureSpecularTint.compute(intersection).average();
			final double doubleSpecularTransmission = this.textureSpecularTransmission.compute(intersection).average();
			
			final Color3D colorColor = Color3D.saturate(this.textureColor.compute(intersection), 0.0D, Doubles.MAX_VALUE);
			final Color3D colorTint = Color3D.normalizeRelativeLuminance(colorColor);
			final Color3D colorSheen = doubleSheen > 0.0D ? Color3D.blend(Color3D.WHITE, colorTint, this.textureSheenTint.compute(intersection).average()) : Color3D.BLACK;
			
			final double diffuseWeight = (1.0D - doubleMetallic) * (1.0D - doubleSpecularTransmission);
			
			if(diffuseWeight > 0.0D) {
				if(this.isThin) {
					final double floatFlatness = this.textureFlatness.compute(intersection).average();
					
					final Color3D colorReflectanceScale0 = Color3D.multiply(colorColor, diffuseWeight * (1.0D - floatFlatness) * (1.0D - doubleDiffuseTransmission));
					final Color3D colorReflectanceScale1 = Color3D.multiply(colorColor, diffuseWeight * (0.0D + floatFlatness) * (1.0D - doubleDiffuseTransmission));
					
					bXDFs.add(new DisneyDiffuseBRDF(colorReflectanceScale0));
					bXDFs.add(new DisneyFakeSSBRDF(colorReflectanceScale1, doubleRoughness));
				} else {
					final Color3D colorScatterDistance = this.textureScatterDistance.compute(intersection);
					
					if(colorScatterDistance.isBlack()) {
						bXDFs.add(new DisneyDiffuseBRDF(Color3D.multiply(colorColor, diffuseWeight)));
					} else {
						bXDFs.add(new SpecularBTDF(Color3D.WHITE, 1.0D, doubleEta));
					}
				}
				
				bXDFs.add(new DisneyRetroBRDF(Color3D.multiply(colorColor, diffuseWeight), doubleRoughness));
				
				if(doubleSheen > 0.0D) {
					bXDFs.add(new DisneySheenBRDF(Color3D.multiply(colorSheen, diffuseWeight * doubleSheen)));
				}
			}
			
			final double aspect = Doubles.sqrt(1.0D - doubleAnisotropic * 0.9D);
			
			final double alphaX = Doubles.max(0.001D, doubleRoughness * doubleRoughness / aspect);
			final double alphaY = Doubles.max(0.001D, doubleRoughness * doubleRoughness * aspect);
			
			final MicrofacetDistribution microfacetDistribution = new TrowbridgeReitzMicrofacetDistribution(true, true, alphaX, alphaY);
			
			final double floatR0 = ((doubleEta - 1.0D) * (doubleEta - 1.0D)) / ((doubleEta + 1.0D) * (doubleEta + 1.0D));
			
			final Color3D colorSpecularR0 = Color3D.blend(Color3D.multiply(Color3D.blend(Color3D.WHITE, colorTint, doubleSpecularTint), floatR0), colorColor, doubleMetallic);
			
			final Fresnel fresnel = new DisneyFresnel(colorSpecularR0, doubleEta, doubleMetallic);
			
			bXDFs.add(new TorranceSparrowBRDF(Color3D.WHITE, fresnel, microfacetDistribution));
			
			if(doubleClearCoat > 0.0D) {
				bXDFs.add(new DisneyClearCoatBRDF(Doubles.lerp(0.1D, 0.001D, this.textureClearCoatGloss.compute(intersection).average()), doubleClearCoat));
			}
			
			if(doubleSpecularTransmission > 0.0D) {
				final Color3D transmittanceScale = Color3D.multiply(Color3D.sqrt(colorColor), doubleSpecularTransmission);
				
				if(this.isThin) {
					final double floatRoughnessScaled = (0.65D * doubleEta - 0.35D) * doubleRoughness;
					
					final double alphaXScaled = Doubles.max(0.001D, floatRoughnessScaled * floatRoughnessScaled / aspect);
					final double alphaYScaled = Doubles.max(0.001D, floatRoughnessScaled * floatRoughnessScaled * aspect);
					
					final MicrofacetDistribution microfacetDistributionScaled = new TrowbridgeReitzMicrofacetDistribution(true, false, alphaXScaled, alphaYScaled);
					
					bXDFs.add(new TorranceSparrowBTDF(transmittanceScale, microfacetDistributionScaled, 1.0D, doubleEta));
				} else {
					bXDFs.add(new TorranceSparrowBTDF(transmittanceScale, microfacetDistribution, 1.0D, doubleEta));
				}
			}
			
			if(this.isThin) {
				bXDFs.add(new LambertianBTDF(Color3D.multiply(colorColor, doubleDiffuseTransmission)));
			}
			
			final BSDF bSDF = new BSDF(bXDFs);
			
			return bSDF.compute(intersection, this.textureEmission.compute(intersection));
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final class DisneyRetroBRDF implements BXDF {
		private final Color3D reflectance;
		private final double roughness;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public DisneyRetroBRDF(final Color3D reflectance, final double roughness) {
			this.reflectance = Objects.requireNonNull(reflectance, "reflectance == null");
			this.roughness = roughness;
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		@Override
		public BXDFType getBXDFType() {
			return BXDFType.DIFFUSE_REFLECTION;
		}
		
		@Override
		public Color3D evaluateDF(final Vector3D o, final Vector3D i) {
			final Vector3D h = Vector3D.add(i, o);
			
			if(h.isZero()) {
				return Color3D.BLACK;
			}
			
			final Vector3D hNormalized = Vector3D.normalize(h);
			
			final double cosThetaD = Vector3D.dotProduct(i, hNormalized);
			
			final double fresnelO = Fresnel.evaluateDielectricSchlickWeight(o.cosThetaAbs());
			final double fresnelI = Fresnel.evaluateDielectricSchlickWeight(i.cosThetaAbs());
			
			final double fresnelA = 2.0D * this.roughness * cosThetaD * cosThetaD;
			final double fresnelB = fresnelO + fresnelI + fresnelO * fresnelI * (fresnelA - 1.0D);
			
			final double r = this.reflectance.r * Doubles.PI_RECIPROCAL * fresnelA * fresnelB;
			final double g = this.reflectance.g * Doubles.PI_RECIPROCAL * fresnelA * fresnelB;
			final double b = this.reflectance.b * Doubles.PI_RECIPROCAL * fresnelA * fresnelB;
			
			return new Color3D(r, g, b);
		}
		
		@Override
		public Optional<BXDFResult> sampleDF(final Vector3D o, final Point2D p) {
			final Vector3D iSample = Vector3D.sampleHemisphereCosineDistribution(p);
			final Vector3D i = o.z < 0.0D ? Vector3D.negateZ(iSample) : iSample;
			
			final Color3D result = evaluateDF(o, i);
			
			final double pDF = evaluatePDF(o, i);
			
			return Optional.of(new BXDFResult(getBXDFType(), result, i, pDF));
		}
		
		@Override
		public double evaluatePDF(final Vector3D o, final Vector3D i) {
			return Vector3D.sameHemisphereZ(o, i) ? i.cosThetaAbs() * Doubles.PI_RECIPROCAL : 0.0D;
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final class DisneySheenBRDF implements BXDF {
		private final Color3D reflectance;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public DisneySheenBRDF(final Color3D reflectance) {
			this.reflectance = Objects.requireNonNull(reflectance, "reflectance == null");
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		@Override
		public BXDFType getBXDFType() {
			return BXDFType.DIFFUSE_REFLECTION;
		}
		
		@Override
		public Color3D evaluateDF(final Vector3D o, final Vector3D i) {
			final Vector3D h = Vector3D.add(i, o);
			
			if(h.isZero()) {
				return Color3D.BLACK;
			}
			
			final Vector3D hNormalized = Vector3D.normalize(h);
			
			final double cosThetaD = Vector3D.dotProduct(i, hNormalized);
			
			final double fresnel = Fresnel.evaluateDielectricSchlickWeight(cosThetaD);
			
			final double r = this.reflectance.r * fresnel;
			final double g = this.reflectance.g * fresnel;
			final double b = this.reflectance.b * fresnel;
			
			return new Color3D(r, g, b);
		}
		
		@Override
		public Optional<BXDFResult> sampleDF(final Vector3D o, final Point2D p) {
			final Vector3D iSample = Vector3D.sampleHemisphereCosineDistribution(p);
			final Vector3D i = o.z < 0.0D ? Vector3D.negateZ(iSample) : iSample;
			
			final Color3D result = evaluateDF(o, i);
			
			final double pDF = evaluatePDF(o, i);
			
			return Optional.of(new BXDFResult(getBXDFType(), result, i, pDF));
		}
		
		@Override
		public double evaluatePDF(final Vector3D o, final Vector3D i) {
			return Vector3D.sameHemisphereZ(o, i) ? i.cosThetaAbs() * Doubles.PI_RECIPROCAL : 0.0D;
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
			return r0 + (1.0D - r0) * evaluateDielectricSchlickWeight(cosTheta);
		}
		
		public static double evaluateDielectricSchlickWeight(final double cosTheta) {
			return Doubles.pow5(Doubles.saturate(1.0D - cosTheta));
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@SuppressWarnings("unused")
	private static final class FresnelSpecularBXDF implements BXDF {
		private final Color3D reflectance;
		private final Color3D transmittance;
		private final double etaA;
		private final double etaB;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public FresnelSpecularBXDF(final Color3D reflectance, final Color3D transmittance, final double etaA, final double etaB) {
			this.reflectance = Objects.requireNonNull(reflectance, "reflectance == null");
			this.transmittance = Objects.requireNonNull(transmittance, "transmittance == null");
			this.etaA = etaA;
			this.etaB = etaB;
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		@Override
		public BXDFType getBXDFType() {
			return BXDFType.SPECULAR_REFLECTION_AND_TRANSMISSION;
		}
		
		@Override
		public Color3D evaluateDF(final Vector3D o, final Vector3D i) {
			return Color3D.BLACK;
		}
		
		@Override
		public Optional<BXDFResult> sampleDF(final Vector3D o, final Point2D p) {
			final double reflectance = Fresnel.evaluateDielectric(o.cosTheta(), this.etaA, this.etaB);
			
			if(p.x < reflectance) {
				final BXDFType bXDFType = BXDFType.SPECULAR_REFLECTION;
				
				final Vector3D i = new Vector3D(-o.x, -o.y, o.z);
				
				final Color3D result = Color3D.divide(Color3D.multiply(this.reflectance, reflectance), i.cosThetaAbs());
				
				final double pDF = reflectance;
				
				return Optional.of(new BXDFResult(bXDFType, result, i, pDF));
			}
			
			final boolean isEntering = o.cosTheta() > 0.0D;
			
			final double etaI = isEntering ? this.etaA : this.etaB;
			final double etaT = isEntering ? this.etaB : this.etaA;
			
			final Optional<Vector3D> optionalI = Vector3D.refraction(o, Vector3D.orientNormal(o, Vector3D.z()), etaI / etaT);
			
			if(optionalI.isPresent()) {
				final BXDFType bXDFType = BXDFType.SPECULAR_TRANSMISSION;
				
				final Vector3D i = optionalI.get();
				
				final Color3D result = Color3D.divide(Color3D.multiply(Color3D.multiply(this.transmittance, 1.0D - reflectance), (etaI * etaI) / (etaT * etaT)), i.cosThetaAbs());
				
				final double pDF = 1.0D - reflectance;
				
				return Optional.of(new BXDFResult(bXDFType, result, i, pDF));
			}
			
			return Optional.empty();
		}
		
		@Override
		public double evaluatePDF(final Vector3D o, final Vector3D i) {
			return 0.0D;
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@SuppressWarnings("unused")
	private static final class GlassMaterial extends Material {
		private final Texture textureEmission;
		private final Texture textureEta;
		private final Texture textureKR;
		private final Texture textureKT;
		private final Texture textureRoughnessU;
		private final Texture textureRoughnessV;
		private final boolean isRemappingRoughness;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public GlassMaterial(final Texture textureKR, final Texture textureKT, final Texture textureEmission, final Texture textureEta, final Texture textureRoughnessU, final Texture textureRoughnessV, final boolean isRemappingRoughness) {
			this.textureKR = Objects.requireNonNull(textureKR, "textureKR == null");
			this.textureKT = Objects.requireNonNull(textureKT, "textureKT == null");
			this.textureEmission = Objects.requireNonNull(textureEmission, "textureEmission == null");
			this.textureEta = Objects.requireNonNull(textureEta, "textureEta == null");
			this.textureRoughnessU = Objects.requireNonNull(textureRoughnessU, "textureRoughnessU == null");
			this.textureRoughnessV = Objects.requireNonNull(textureRoughnessV, "textureRoughnessV == null");
			this.isRemappingRoughness = isRemappingRoughness;
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
			
			/*
			final Color3D colorKR = Color3D.saturate(this.textureKR.compute(intersection), 0.0D, Doubles.MAX_VALUE);
			final Color3D colorKT = Color3D.saturate(this.textureKT.compute(intersection), 0.0D, Doubles.MAX_VALUE);
			
			final double eta = this.textureEta.compute(intersection).average();
			final double roughnessU = this.textureRoughnessU.compute(intersection).average();
			final double roughnessV = this.textureRoughnessV.compute(intersection).average();
			
			if(colorKR.isBlack() && colorKT.isBlack()) {
				return Optional.empty();
			}
			
			final boolean isAllowingMultipleLobes = true;
			final boolean isSpecular = Doubles.isZero(roughnessU) && Doubles.isZero(roughnessV);
			
			if(isSpecular && isAllowingMultipleLobes) {
				return new BSDF(new FresnelSpecularBXDF(colorKR, colorKT, 1.0D, eta)).compute(intersection, this.textureEmission.compute(intersection));
			}
			
			if(isSpecular) {
				final List<BXDF> bXDFs = new ArrayList<>();
				
				if(!colorKR.isBlack()) {
					final Fresnel fresnel = new DielectricFresnel(1.0D, eta);
					
					bXDFs.add(new SpecularBRDF(colorKR, fresnel));
				}
				
				if(!colorKT.isBlack()) {
					bXDFs.add(new SpecularBTDF(colorKT, 1.0D, eta));
				}
				
				return new BSDF(bXDFs).compute(intersection, this.textureEmission.compute(intersection));
			}
			
			final List<BXDF> bXDFs = new ArrayList<>();
			
			final double roughnessURemapped = this.isRemappingRoughness ? MicrofacetDistribution.convertRoughnessToAlpha(roughnessU) : roughnessU;
			final double roughnessVRemapped = this.isRemappingRoughness ? MicrofacetDistribution.convertRoughnessToAlpha(roughnessV) : roughnessV;
			
			final MicrofacetDistribution microfacetDistribution = new TrowbridgeReitzMicrofacetDistribution(true, false, roughnessURemapped, roughnessVRemapped);
			
			if(!colorKR.isBlack()) {
				final Fresnel fresnel = new DielectricFresnel(1.0D, eta);
				
				bXDFs.add(new TorranceSparrowBRDF(colorKR, fresnel, microfacetDistribution));
			}
			
			if(!colorKT.isBlack()) {
				bXDFs.add(new TorranceSparrowBTDF(colorKT, microfacetDistribution, 1.0D, eta));
			}
			
			return new BSDF(bXDFs).compute(intersection, this.textureEmission.compute(intersection));
			*/
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
		public BXDFType getBXDFType() {
			return BXDFType.DIFFUSE_REFLECTION;
		}
		
		@Override
		public Color3D evaluateDF(final Vector3D o, final Vector3D i) {
			return Color3D.divide(this.r, Doubles.PI);
		}
		
		@Override
		public Optional<BXDFResult> sampleDF(final Vector3D o, final Point2D p) {
			final Vector3D i = Vector3D.sampleHemisphereCosineDistribution(p);
			final Vector3D iCorrectlyOriented = o.z < 0.0D ? Vector3D.negateZ(i) : i;
			
			final Color3D result = evaluateDF(o, iCorrectlyOriented);
			
			final double pDF = evaluatePDF(o, iCorrectlyOriented);
			
			return Optional.of(new BXDFResult(getBXDFType(), result, iCorrectlyOriented, pDF));
		}
		
		@Override
		public double evaluatePDF(final Vector3D o, final Vector3D i) {
			return Vector3D.sameHemisphereZ(o, i) ? i.cosThetaAbs() / Doubles.PI : 0.0D;
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final class LambertianBTDF implements BXDF {
		private final Color3D t;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public LambertianBTDF(final Color3D t) {
			this.t = Objects.requireNonNull(t, "t == null");
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		@Override
		public BXDFType getBXDFType() {
			return BXDFType.DIFFUSE_TRANSMISSION;
		}
		
		@Override
		public Color3D evaluateDF(final Vector3D o, final Vector3D i) {
			return Color3D.divide(this.t, Doubles.PI);
		}
		
		@Override
		public Optional<BXDFResult> sampleDF(final Vector3D o, final Point2D p) {
			final Vector3D i = Vector3D.sampleHemisphereCosineDistribution(p);
			final Vector3D iCorrectlyOriented = o.z > 0.0D ? Vector3D.negateZ(i) : i;
			
			final Color3D result = evaluateDF(o, iCorrectlyOriented);
			
			final double pDF = evaluatePDF(o, iCorrectlyOriented);
			
			return Optional.of(new BXDFResult(getBXDFType(), result, iCorrectlyOriented, pDF));
		}
		
		@Override
		public double evaluatePDF(final Vector3D o, final Vector3D i) {
			return Vector3D.sameHemisphereZ(o, i) ? 0.0D : i.cosThetaAbs() / Doubles.PI;
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final class MatteMaterial extends Material {
		private final Texture textureAngle;
		private final Texture textureEmission;
		private final Texture textureKD;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public MatteMaterial(final Texture textureKD, final Texture textureEmission, final Texture textureAngle) {
			this.textureKD = Objects.requireNonNull(textureKD, "textureKD == null");
			this.textureEmission = Objects.requireNonNull(textureEmission, "textureEmission == null");
			this.textureAngle = Objects.requireNonNull(textureAngle, "textureAngle == null");
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		@Override
		public Optional<Result> compute(final Intersection intersection) {
//			final Vector3D s = Vector3D.sampleHemisphereCosineDistribution();
//			final Vector3D w = Vector3D.orientNormalNegated(intersection.getRayWS().getDirection(), intersection.getSurfaceNormalWS());
//			final Vector3D u = Vector3D.normalize(Vector3D.crossProduct(Doubles.abs(w.x) > 0.1D ? Vector3D.y() : Vector3D.x(), w));
//			final Vector3D v = Vector3D.normalize(Vector3D.crossProduct(w, u));
			
//			final Color3D colorEmission = this.textureEmission.compute(intersection);
//			final Color3D colorKD = this.textureKD.compute(intersection);
			
//			return Optional.of(new Result(colorEmission, colorKD, new Ray3D(intersection.getSurfaceIntersectionPointWS(), Vector3D.directionNormalized(u, v, w, s))));
			
			final Color3D colorKD = Color3D.saturate(this.textureKD.compute(intersection), 0.0D, Doubles.MAX_VALUE);
			
			final double doubleAngle = this.textureAngle.compute(intersection).average();
			
			final AngleD angle = AngleD.degrees(Doubles.saturate(doubleAngle, 0.0D, 90.0D));
			
			if(colorKD.isBlack()) {
				return Optional.empty();
			}
			
			final BXDF bXDF = Doubles.isZero(angle.getDegrees()) ? new LambertianBRDF(colorKD) : new OrenNayarBRDF(angle, colorKD);
			
			final BSDF bSDF = new BSDF(bXDF);
			
			return bSDF.compute(intersection, this.textureEmission.compute(intersection));
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
//			final Color3D colorEmission = this.textureEmission.compute(intersection);
//			final Color3D colorKR = this.textureKR.compute(intersection);
			
//			return Optional.of(new Result(colorEmission, colorKR, new Ray3D(intersection.getSurfaceIntersectionPointWS(), Vector3D.reflection(intersection.getRayWS().getDirection(), intersection.getSurfaceNormalWS(), true))));
			
			final Color3D colorKR = Color3D.saturate(this.textureKR.compute(intersection), 0.0D, Doubles.MAX_VALUE);
			
			if(!colorKR.isBlack()) {
				final BSDF bSDF = new BSDF(new SpecularBRDF(colorKR, new ConstantFresnel()));
				
				return bSDF.compute(intersection, this.textureEmission.compute(intersection));
			}
			
			return Optional.empty();
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final class OrenNayarBRDF implements BXDF {
		private final Color3D r;
		private final double a;
		private final double b;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public OrenNayarBRDF(final AngleD angle, final Color3D r) {
			Objects.requireNonNull(angle, "angle == null");
			
			this.r = Objects.requireNonNull(r, "r == null");
			this.a = 1.0D - ((angle.getRadians() * angle.getRadians()) / (2.0D * ((angle.getRadians() * angle.getRadians()) + 0.33D)));
			this.b = 0.45D * (angle.getRadians() * angle.getRadians()) / ((angle.getRadians() * angle.getRadians()) + 0.09D);
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		@Override
		public BXDFType getBXDFType() {
			return BXDFType.DIFFUSE_REFLECTION;
		}
		
		@Override
		public Color3D evaluateDF(final Vector3D o, final Vector3D i) {
			final double cosThetaAbsI = i.cosThetaAbs();
			final double cosThetaAbsO = o.cosThetaAbs();
			
			final double sinThetaI = i.sinTheta();
			final double sinThetaO = o.sinTheta();
			
			final double maxCos = sinThetaI > 1.0e-4D && sinThetaO > 1.0e-4D ? Doubles.max(0.0D, i.cosPhi() * o.cosPhi() + i.sinPhi() * o.sinPhi()) : 0.0D;
			
			final double sinA = cosThetaAbsI > cosThetaAbsO ? sinThetaO : sinThetaI;
			final double tanB = cosThetaAbsI > cosThetaAbsO ? sinThetaI / cosThetaAbsI : sinThetaO / cosThetaAbsO;
			
			final double a = this.a;
			final double b = this.b;
			final double c = (a + b * maxCos * sinA * tanB);
			
			return Color3D.multiply(Color3D.multiply(this.r, Doubles.PI_RECIPROCAL), c);
		}
		
		@Override
		public Optional<BXDFResult> sampleDF(final Vector3D o, final Point2D p) {
			final Vector3D i = Vector3D.sampleHemisphereCosineDistribution(p);
			final Vector3D iCorrectlyOriented = o.z < 0.0D ? Vector3D.negateZ(i) : i;
			
			final Color3D result = evaluateDF(o, iCorrectlyOriented);
			
			final double pDF = evaluatePDF(o, iCorrectlyOriented);
			
			return Optional.of(new BXDFResult(getBXDFType(), result, iCorrectlyOriented, pDF));
		}
		
		@Override
		public double evaluatePDF(final Vector3D o, final Vector3D i) {
			return Vector3D.sameHemisphereZ(o, i) ? i.cosThetaAbs() / Doubles.PI : 0.0D;
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
		public BXDFType getBXDFType() {
			return BXDFType.SPECULAR_REFLECTION;
		}
		
		@Override
		public Color3D evaluateDF(final Vector3D o, final Vector3D i) {
			return Color3D.BLACK;
		}
		
		@Override
		public Optional<BXDFResult> sampleDF(final Vector3D o, final Point2D p) {
			final Vector3D i = new Vector3D(-o.x, -o.y, o.z);
			
			final Color3D result = Color3D.divide(Color3D.multiply(this.fresnel.evaluate(i.cosTheta()), this.r), i.cosThetaAbs());
			
			final float pDF = 1.0F;
			
			return Optional.of(new BXDFResult(getBXDFType(), result, i, pDF));
		}
		
		@Override
		public double evaluatePDF(final Vector3D o, final Vector3D i) {
			return 0.0D;
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
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
		public BXDFType getBXDFType() {
			return BXDFType.SPECULAR_TRANSMISSION;
		}
		
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
			
			return Optional.of(new BXDFResult(getBXDFType(), result, i, pDF));
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
		public BXDFType getBXDFType() {
			return BXDFType.GLOSSY_REFLECTION;
		}
		
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
			
			return Optional.of(new BXDFResult(getBXDFType(), result, i, pDF));
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
		public BXDFType getBXDFType() {
			return BXDFType.GLOSSY_TRANSMISSION;
		}
		
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
			
			return Optional.of(new BXDFResult(getBXDFType(), result, i, pDF));
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