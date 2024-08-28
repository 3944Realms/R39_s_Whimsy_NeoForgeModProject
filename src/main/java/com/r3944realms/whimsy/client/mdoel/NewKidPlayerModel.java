package com.r3944realms.whimsy.client.mdoel;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class NewKidPlayerModel<T extends LivingEntity> extends PlayerModel<T> {
    private final List<ModelPart> parts;
    public final ModelPart leftSleeve;
    public final ModelPart rightSleeve;
    public final ModelPart leftPants;
    public final ModelPart rightPants;
    public final ModelPart jacket;
    private final ModelPart cloak;
    private final ModelPart ear;
    private final boolean slim;

    public NewKidPlayerModel(ModelPart root, boolean slim) {
        super(root, slim);
        this.slim = slim;
        this.ear = root.getChild("ear");
        this.cloak = root.getChild("cloak");
        this.leftSleeve = root.getChild("left_sleeve");
        this.rightSleeve = root.getChild("right_sleeve");
        this.leftPants = root.getChild("left_pants");
        this.rightPants = root.getChild("right_pants");
        this.jacket = root.getChild("jacket");
        this.parts = root.getAllParts().filter(p -> !p.isEmpty()).collect(ImmutableList.toImmutableList());
    }

    public static MeshDefinition createMesh(CubeDeformation pCubeDeformation, boolean pSlim) {
        MeshDefinition meshdefinition = HumanoidModel.createMesh(pCubeDeformation, 0.0F);
        PartDefinition partdefinition = meshdefinition.getRoot();
        partdefinition.addOrReplaceChild("ear", CubeListBuilder.create().texOffs(24, 0).addBox(-1.5F, -3.0F, -0.5F, 3.0F, 3.0F, 0.5F, pCubeDeformation), PartPose.ZERO);
        partdefinition.addOrReplaceChild(
                "cloak",
                CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, 0.0F, -0.5F, 5.0F, 8.0F, 0.5F, pCubeDeformation, 0.5F, 0.25F),
                PartPose.offset(0.0F, 0.0F, 0.0F)
        );
        float f = 0.25F;
        if (pSlim) {
            partdefinition.addOrReplaceChild(
                    "left_arm",
                    CubeListBuilder.create().texOffs(32, 48).addBox(-0.5F, -1.0F, -1.0F, 1.5F, 6.0F, 2.0F, pCubeDeformation),
                    PartPose.offset(2.5F, 1.25F, 0.0F)
            );
            partdefinition.addOrReplaceChild(
                    "right_arm",
                    CubeListBuilder.create().texOffs(40, 16).addBox(-1.0F, -1.0F, -1.0F, 1.5F, 6.0F, 2.0F, pCubeDeformation),
                    PartPose.offset(-2.5F, 1.25F, 0.0F)
            );
            partdefinition.addOrReplaceChild(
                    "left_sleeve",
                    CubeListBuilder.create().texOffs(48, 48).addBox(-0.5F, -1.0F, -1.0F, 1.5F, 6.0F, 2.0F, pCubeDeformation.extend(0.125F)),
                    PartPose.offset(2.5F, 1.25F, 0.0F)
            );
            partdefinition.addOrReplaceChild(
                    "right_sleeve",
                    CubeListBuilder.create().texOffs(40, 32).addBox(-1.0F, -1.0F, -1.0F, 1.5F, 6.0F, 2.0F, pCubeDeformation.extend(0.125F)),
                    PartPose.offset(-2.5F, 1.25F, 0.0F)
            );
        } else {
            partdefinition.addOrReplaceChild(
                    "left_arm",
                    CubeListBuilder.create().texOffs(32, 48).addBox(-0.5F, -1.0F, -1.0F, 2.0F, 6.0F, 2.0F, pCubeDeformation),
                    PartPose.offset(2.5F, 1.0F, 0.0F)
            );
            partdefinition.addOrReplaceChild(
                    "left_sleeve",
                    CubeListBuilder.create().texOffs(48, 48).addBox(-0.5F, -1.0F, -1.0F, 2.0F, 6.0F, 2.0F, pCubeDeformation.extend(0.125F)),
                    PartPose.offset(2.5F, 1.0F, 0.0F)
            );
            partdefinition.addOrReplaceChild(
                    "right_sleeve",
                    CubeListBuilder.create().texOffs(40, 32).addBox(-1.5F, -1.0F, -1.0F, 2.0F, 6.0F, 2.0F, pCubeDeformation.extend(0.125F)),
                    PartPose.offset(-2.5F, 1.0F, 0.0F)
            );
        }

        partdefinition.addOrReplaceChild(
                "left_leg", CubeListBuilder.create().texOffs(16, 48).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, pCubeDeformation), PartPose.offset(0.95F, 6.0F, 0.0F)
        );
        partdefinition.addOrReplaceChild(
                "left_pants",
                CubeListBuilder.create().texOffs(0, 48).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, pCubeDeformation.extend(0.125F)),
                PartPose.offset(0.95F, 6.0F, 0.0F)
        );
        partdefinition.addOrReplaceChild(
                "right_pants",
                CubeListBuilder.create().texOffs(0, 32).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, pCubeDeformation.extend(0.125F)),
                PartPose.offset(-0.95F, 6.0F, 0.0F)
        );
        partdefinition.addOrReplaceChild(
                "jacket", CubeListBuilder.create().texOffs(16, 32).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 6.0F, 2.0F, pCubeDeformation.extend(0.125F)), PartPose.ZERO
        );
        return meshdefinition;
    }

    @Override
    public void renderEars(@NotNull PoseStack poseStack, @NotNull VertexConsumer buffer, int packedLight, int packedOverlay) {
        this.ear.copyFrom(this.head);
        this.ear.render(poseStack, buffer, packedLight, packedOverlay);
    }

    @Override
    public void renderCloak(@NotNull PoseStack poseStack, @NotNull VertexConsumer buffer, int packedLight, int packedOverlay) {
        this.cloak.render(poseStack, buffer, packedLight, packedOverlay);
    }

    @Override
    protected @NotNull Iterable<ModelPart> bodyParts() {
        return Iterables.concat(super.bodyParts(), ImmutableList.of(this.leftPants, this.rightPants, this.leftSleeve, this.rightSleeve, this.jacket));
    }

    @Override
    public void setupAnim(@NotNull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        this.leftPants.copyFrom(this.leftLeg);
        this.rightPants.copyFrom(this.rightLeg);
        this.leftSleeve.copyFrom(this.leftArm);
        this.rightSleeve.copyFrom(this.rightArm);
        this.jacket.copyFrom(this.body);
        this.cloak.copyFrom(this.body);
    }
}
