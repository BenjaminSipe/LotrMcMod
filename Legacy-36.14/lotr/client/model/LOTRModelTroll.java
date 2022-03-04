package lotr.client.model;

import lotr.common.entity.item.LOTREntityStoneTroll;
import lotr.common.entity.npc.LOTREntityMountainTroll;
import lotr.common.entity.npc.LOTREntitySnowTroll;
import lotr.common.entity.npc.LOTREntityTroll;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

public class LOTRModelTroll extends ModelBase {
   public ModelRenderer head;
   public ModelRenderer headHurt;
   public ModelRenderer body;
   public ModelRenderer rightArm;
   public ModelRenderer leftArm;
   public ModelRenderer rightLeg;
   public ModelRenderer leftLeg;
   public ModelRenderer woodenClub;
   public ModelRenderer woodenClubSpikes;
   public ModelRenderer warhammer;
   public ModelRenderer battleaxe;
   private boolean isOutiftModel;

   public LOTRModelTroll() {
      this(0.0F);
   }

   public LOTRModelTroll(float f) {
      this.isOutiftModel = false;
      this.field_78090_t = 128;
      this.field_78089_u = 128;
      this.head = new ModelRenderer(this, 0, 0);
      this.head.func_78790_a(-6.0F, -6.0F, -12.0F, 12, 12, 12, f);
      this.head.func_78793_a(0.0F, -27.0F, -6.0F);
      this.head.func_78784_a(40, 0).func_78790_a(6.0F, -2.0F, -8.0F, 1, 4, 3, f);
      this.head.field_78809_i = true;
      this.head.func_78784_a(40, 0).func_78790_a(-7.0F, -2.0F, -8.0F, 1, 4, 3, f);
      this.head.field_78809_i = false;
      this.head.func_78784_a(0, 0).func_78790_a(-1.0F, -1.0F, -14.0F, 2, 3, 2, f);
      this.headHurt = new ModelRenderer(this, 48, 44);
      this.headHurt.func_78790_a(-6.0F, -6.0F, -12.0F, 12, 12, 12, f);
      this.headHurt.func_78793_a(0.0F, -27.0F, -6.0F);
      this.headHurt.func_78784_a(40, 0).func_78790_a(6.0F, -2.0F, -8.0F, 1, 4, 3, f);
      this.headHurt.field_78809_i = true;
      this.headHurt.func_78784_a(40, 0).func_78790_a(-7.0F, -2.0F, -8.0F, 1, 4, 3, f);
      this.headHurt.field_78809_i = false;
      this.headHurt.func_78784_a(0, 0).func_78790_a(-1.0F, -1.0F, -14.0F, 2, 3, 2, f);
      this.body = new ModelRenderer(this, 48, 0);
      this.body.func_78790_a(-12.0F, -28.0F, -8.0F, 24, 28, 16, f);
      this.body.func_78793_a(0.0F, 0.0F, 0.0F);
      this.rightArm = new ModelRenderer(this, 0, 24);
      this.rightArm.field_78809_i = true;
      this.rightArm.func_78790_a(-12.0F, -3.0F, -6.0F, 12, 12, 12, f);
      this.rightArm.func_78793_a(-12.0F, -23.0F, 0.0F);
      this.rightArm.func_78784_a(0, 48).func_78790_a(-11.0F, 9.0F, -5.0F, 10, 20, 10, f);
      this.leftArm = new ModelRenderer(this, 0, 24);
      this.leftArm.func_78790_a(0.0F, -3.0F, -6.0F, 12, 12, 12, f);
      this.leftArm.func_78793_a(12.0F, -23.0F, 0.0F);
      this.leftArm.func_78784_a(0, 48).func_78790_a(1.0F, 9.0F, -5.0F, 10, 20, 10, f);
      this.rightLeg = new ModelRenderer(this, 0, 78);
      this.rightLeg.field_78809_i = true;
      this.rightLeg.func_78790_a(-6.0F, 0.0F, -6.0F, 11, 12, 12, f);
      this.rightLeg.func_78793_a(-6.0F, 0.0F, 0.0F);
      this.rightLeg.func_78784_a(0, 102).func_78789_a(-5.5F, 12.0F, -5.0F, 10, 12, 10);
      this.leftLeg = new ModelRenderer(this, 0, 78);
      this.leftLeg.func_78790_a(-5.0F, 0.0F, -6.0F, 11, 12, 12, f);
      this.leftLeg.func_78793_a(6.0F, 0.0F, 0.0F);
      this.leftLeg.func_78784_a(0, 102).func_78789_a(-4.5F, 12.0F, -5.0F, 10, 12, 10);
      this.woodenClub = new ModelRenderer(this, 0, 0);
      this.woodenClub.func_78790_a(-9.0F, 5.0F, 21.0F, 6, 24, 6, f);
      this.woodenClub.func_78793_a(-12.0F, -23.0F, 0.0F);
      this.woodenClubSpikes = new ModelRenderer(this, 24, 0);
      this.woodenClubSpikes.func_78790_a(-12.0F, 25.0F, 23.5F, 12, 1, 1, f);
      this.woodenClubSpikes.func_78790_a(-12.0F, 20.0F, 23.5F, 12, 1, 1, f);
      this.woodenClubSpikes.func_78790_a(-12.0F, 15.0F, 23.5F, 12, 1, 1, f);
      this.woodenClubSpikes.func_78784_a(24, 2);
      this.woodenClubSpikes.func_78790_a(-6.5F, 25.0F, 18.0F, 1, 1, 12, f);
      this.woodenClubSpikes.func_78790_a(-6.5F, 20.0F, 18.0F, 1, 1, 12, f);
      this.woodenClubSpikes.func_78790_a(-6.5F, 15.0F, 18.0F, 1, 1, 12, f);
      this.woodenClubSpikes.func_78793_a(-12.0F, -23.0F, 0.0F);
      this.warhammer = new ModelRenderer(this, 52, 29);
      this.warhammer.func_78793_a(-12.0F, -23.0F, 0.0F);
      this.warhammer.func_78790_a(-7.5F, 5.0F, 22.5F, 3, 20, 3, f);
      this.warhammer.func_78784_a(0, 32).func_78790_a(-12.0F, 25.0F, 14.0F, 12, 12, 20, f);
      this.battleaxe = new ModelRenderer(this, 64, 0);
      this.battleaxe.func_78793_a(-12.0F, -23.0F, 0.0F);
      this.battleaxe.func_78790_a(-7.0F, -40.0F, 22.5F, 2, 80, 2, f);
      this.battleaxe.func_78784_a(72, 0);
      this.battleaxe.func_78790_a(-6.0F, 20.0F, 24.0F, 0, 24, 16, f);
   }

   public LOTRModelTroll(float f, int i) {
      this(f);
      this.isOutiftModel = true;
      if (i == 0) {
         this.head.field_78806_j = true;
         this.body.field_78806_j = true;
         this.rightArm.field_78806_j = true;
         this.leftArm.field_78806_j = true;
         this.rightLeg.field_78806_j = false;
         this.leftLeg.field_78806_j = false;
      } else if (i == 1) {
         this.head.field_78806_j = false;
         this.body.field_78806_j = false;
         this.rightArm.field_78806_j = false;
         this.leftArm.field_78806_j = false;
         this.rightLeg.field_78806_j = true;
         this.leftLeg.field_78806_j = true;
      } else if (i == 2) {
         this.head.field_78806_j = true;
         this.body.field_78806_j = false;
         this.rightArm.field_78806_j = false;
         this.leftArm.field_78806_j = false;
         this.rightLeg.field_78806_j = false;
         this.leftLeg.field_78806_j = false;
      } else if (i == 3) {
         this.head.field_78806_j = false;
         this.body.field_78806_j = true;
         this.rightArm.field_78806_j = true;
         this.leftArm.field_78806_j = true;
         this.rightLeg.field_78806_j = false;
         this.leftLeg.field_78806_j = false;
      }

   }

   public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.func_78087_a(f, f1, f2, f3, f4, f5, entity);
      boolean isHurt = false;
      boolean hasTwoHeads = false;
      if (entity instanceof LOTREntityTroll) {
         LOTREntityTroll troll = (LOTREntityTroll)entity;
         isHurt = !this.isOutiftModel && troll.shouldRenderHeadHurt();
         hasTwoHeads = troll.hasTwoHeads();
      } else if (entity instanceof LOTREntityStoneTroll) {
         LOTREntityStoneTroll troll = (LOTREntityStoneTroll)entity;
         isHurt = false;
         hasTwoHeads = troll.hasTwoHeads();
      }

      if (hasTwoHeads) {
         GL11.glPushMatrix();
         GL11.glRotatef(-15.0F, 0.0F, 0.0F, 1.0F);
         GL11.glRotatef(10.0F, 0.0F, 1.0F, 0.0F);
         if (isHurt) {
            this.headHurt.func_78785_a(f5);
         } else {
            this.head.func_78785_a(f5);
         }

         GL11.glPopMatrix();
         GL11.glPushMatrix();
         GL11.glRotatef(15.0F, 0.0F, 0.0F, 1.0F);
         GL11.glRotatef(-10.0F, 0.0F, 1.0F, 0.0F);
         if (isHurt) {
            this.headHurt.func_78785_a(f5);
         } else {
            this.head.func_78785_a(f5);
         }

         GL11.glPopMatrix();
      } else if (isHurt) {
         this.headHurt.func_78785_a(f5);
      } else {
         this.head.func_78785_a(f5);
      }

      this.body.func_78785_a(f5);
      this.rightArm.func_78785_a(f5);
      this.leftArm.func_78785_a(f5);
      this.rightLeg.func_78785_a(f5);
      this.leftLeg.func_78785_a(f5);
   }

   public void func_78087_a(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
      this.head.field_78800_c = 0.0F;
      this.head.field_78797_d = -27.0F;
      this.head.field_78808_h = 0.0F;
      this.body.field_78800_c = 0.0F;
      this.body.field_78797_d = 0.0F;
      this.body.field_78808_h = 0.0F;
      this.rightArm.field_78800_c = -12.0F;
      this.rightArm.field_78797_d = -23.0F;
      this.rightArm.field_78808_h = 0.0F;
      this.leftArm.field_78800_c = 12.0F;
      this.leftArm.field_78797_d = -23.0F;
      this.leftArm.field_78808_h = 0.0F;
      this.head.field_78796_g = f3 / 57.295776F;
      this.head.field_78795_f = f4 / 57.295776F;
      float f6;
      if (entity instanceof LOTREntityTroll && ((LOTREntityTroll)entity).sniffTime > 0) {
         f6 = ((float)((LOTREntityTroll)entity).sniffTime - (f2 - (float)entity.field_70173_aa)) / 8.0F;
         f6 *= 6.2831855F;
         this.head.field_78796_g = MathHelper.func_76126_a(f6) * 0.5F;
      }

      this.rightArm.field_78795_f = MathHelper.func_76134_b(f * 0.6662F + 3.1415927F) * 2.0F * f1 * 0.5F;
      this.leftArm.field_78795_f = MathHelper.func_76134_b(f * 0.6662F) * 2.0F * f1 * 0.5F;
      if (entity instanceof LOTREntityTroll) {
         this.rightArm.field_78795_f = this.rightArm.field_78795_f * 0.5F - 0.31415927F;
      }

      this.rightArm.field_78808_h = 0.0F;
      this.leftArm.field_78808_h = 0.0F;
      float f6;
      float armRotationOffsetX;
      ModelRenderer var10000;
      if (this.field_78095_p > -9990.0F) {
         f6 = this.field_78095_p;
         this.body.field_78796_g = MathHelper.func_76126_a(MathHelper.func_76129_c(f6) * 3.1415927F * 2.0F) * 0.2F;
         this.rightArm.field_78798_e = MathHelper.func_76126_a(this.body.field_78796_g) * 5.0F;
         this.rightArm.field_78800_c = -MathHelper.func_76134_b(this.body.field_78796_g) * 12.0F;
         this.leftArm.field_78798_e = -MathHelper.func_76126_a(this.body.field_78796_g) * 5.0F;
         this.leftArm.field_78800_c = MathHelper.func_76134_b(this.body.field_78796_g) * 12.0F;
         var10000 = this.rightArm;
         var10000.field_78796_g += this.body.field_78796_g;
         var10000 = this.leftArm;
         var10000.field_78796_g += this.body.field_78796_g;
         var10000 = this.leftArm;
         var10000.field_78795_f += this.body.field_78796_g;
         f6 = 1.0F - this.field_78095_p;
         f6 *= f6;
         f6 *= f6;
         f6 = 1.0F - f6;
         f6 = MathHelper.func_76126_a(f6 * 3.1415927F);
         armRotationOffsetX = MathHelper.func_76126_a(this.field_78095_p * 3.1415927F) * -(this.head.field_78795_f - 0.7F) * 0.75F;
         this.rightArm.field_78795_f = (float)((double)this.rightArm.field_78795_f - ((double)f6 * 1.2D + (double)armRotationOffsetX));
         var10000 = this.rightArm;
         var10000.field_78796_g += this.body.field_78796_g * 2.0F;
         this.rightArm.field_78808_h = MathHelper.func_76126_a(this.field_78095_p * 3.1415927F) * -0.4F;
      }

      this.rightLeg.field_78795_f = MathHelper.func_76134_b(f * 0.6662F) * 1.4F * f1;
      this.leftLeg.field_78795_f = MathHelper.func_76134_b(f * 0.6662F + 3.1415927F) * 1.4F * f1;
      this.rightLeg.field_78796_g = 0.0F;
      this.leftLeg.field_78796_g = 0.0F;
      this.rightArm.field_78796_g = 0.0F;
      this.leftArm.field_78796_g = 0.0F;
      var10000 = this.rightArm;
      var10000.field_78808_h += MathHelper.func_76134_b(f2 * 0.09F) * 0.05F + 0.05F;
      var10000 = this.leftArm;
      var10000.field_78808_h -= MathHelper.func_76134_b(f2 * 0.09F) * 0.05F + 0.05F;
      var10000 = this.rightArm;
      var10000.field_78795_f += MathHelper.func_76126_a(f2 * 0.067F) * 0.05F;
      var10000 = this.leftArm;
      var10000.field_78795_f -= MathHelper.func_76126_a(f2 * 0.067F) * 0.05F;
      boolean throwing = false;
      if (entity instanceof LOTREntityMountainTroll && ((LOTREntityMountainTroll)entity).isThrowingRocks()) {
         throwing = true;
      }

      if (entity instanceof LOTREntitySnowTroll && ((LOTREntitySnowTroll)entity).isThrowingSnow()) {
         throwing = true;
      }

      if (throwing) {
         var10000 = this.rightArm;
         var10000.field_78795_f -= 0.5F;
         var10000 = this.rightArm;
         var10000.field_78808_h -= 0.4F;
         this.leftArm.field_78795_f = this.rightArm.field_78795_f;
         this.leftArm.field_78796_g = -this.rightArm.field_78796_g;
         this.leftArm.field_78808_h = -this.rightArm.field_78808_h;
      }

      if (entity instanceof EntityLivingBase) {
         f6 = MathHelper.func_76126_a(f * 0.2F) * 0.3F * f1;
         var10000 = this.head;
         var10000.field_78800_c += MathHelper.func_76126_a(f6) * 27.0F;
         var10000 = this.head;
         var10000.field_78797_d += 27.0F - MathHelper.func_76134_b(f6) * 27.0F;
         var10000 = this.head;
         var10000.field_78808_h += f6;
         var10000 = this.body;
         var10000.field_78808_h += f6;
         armRotationOffsetX = MathHelper.func_76126_a(f6) * 23.0F + MathHelper.func_76134_b(f6) * 12.0F - 12.0F;
         float armRotationOffsetY = MathHelper.func_76134_b(f6) * -23.0F + MathHelper.func_76126_a(f6) * 12.0F + 23.0F;
         var10000 = this.rightArm;
         var10000.field_78800_c += armRotationOffsetX;
         var10000 = this.rightArm;
         var10000.field_78797_d += -armRotationOffsetY;
         var10000 = this.rightArm;
         var10000.field_78808_h += f6;
         var10000 = this.leftArm;
         var10000.field_78800_c += armRotationOffsetX;
         var10000 = this.leftArm;
         var10000.field_78797_d += armRotationOffsetY;
         var10000 = this.leftArm;
         var10000.field_78808_h += f6;
      }

      this.headHurt.field_78800_c = this.head.field_78800_c;
      this.headHurt.field_78797_d = this.head.field_78797_d;
      this.headHurt.field_78798_e = this.head.field_78798_e;
      this.headHurt.field_78795_f = this.head.field_78795_f;
      this.headHurt.field_78796_g = this.head.field_78796_g;
      this.headHurt.field_78808_h = this.head.field_78808_h;
   }

   public void renderWoodenClub(float f) {
      this.woodenClub.field_78800_c = this.rightArm.field_78800_c;
      this.woodenClub.field_78797_d = this.rightArm.field_78797_d;
      this.woodenClub.field_78798_e = this.rightArm.field_78798_e;
      this.woodenClub.field_78795_f = this.rightArm.field_78795_f - 1.5707964F;
      this.woodenClub.field_78796_g = this.rightArm.field_78796_g;
      this.woodenClub.field_78808_h = this.rightArm.field_78808_h;
      this.woodenClub.func_78785_a(f);
   }

   public void renderWoodenClubWithSpikes(float f) {
      this.renderWoodenClub(f);
      this.woodenClubSpikes.field_78800_c = this.woodenClub.field_78800_c;
      this.woodenClubSpikes.field_78797_d = this.woodenClub.field_78797_d;
      this.woodenClubSpikes.field_78798_e = this.woodenClub.field_78798_e;
      this.woodenClubSpikes.field_78795_f = this.woodenClub.field_78795_f;
      this.woodenClubSpikes.field_78796_g = this.woodenClub.field_78796_g;
      this.woodenClubSpikes.field_78808_h = this.woodenClub.field_78808_h;
      this.woodenClubSpikes.func_78785_a(f);
   }

   public void renderWarhammer(float f) {
      this.warhammer.field_78800_c = this.rightArm.field_78800_c;
      this.warhammer.field_78797_d = this.rightArm.field_78797_d;
      this.warhammer.field_78798_e = this.rightArm.field_78798_e;
      this.warhammer.field_78795_f = this.rightArm.field_78795_f - 1.5707964F;
      this.warhammer.field_78796_g = this.rightArm.field_78796_g;
      this.warhammer.field_78808_h = this.rightArm.field_78808_h;
      this.warhammer.func_78785_a(f);
   }

   public void renderBattleaxe(float f) {
      this.battleaxe.field_78800_c = this.rightArm.field_78800_c;
      this.battleaxe.field_78797_d = this.rightArm.field_78797_d;
      this.battleaxe.field_78798_e = this.rightArm.field_78798_e;
      this.battleaxe.field_78795_f = this.rightArm.field_78795_f - 1.5707964F;
      this.battleaxe.field_78796_g = this.rightArm.field_78796_g;
      this.battleaxe.field_78808_h = this.rightArm.field_78808_h;
      this.battleaxe.func_78785_a(f);
   }
}
