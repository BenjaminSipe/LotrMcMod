package lotr.common.entity.npc;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.client.LOTRTickHandlerClient;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.entity.ai.LOTREntityAIFollowHiringPlayer;
import lotr.common.entity.ai.LOTREntityAIHiredRemainStill;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.culling.Frustrum;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public abstract class LOTREntityHuornBase extends LOTREntityTree {
   public boolean ignoringFrustumForRender = false;

   public LOTREntityHuornBase(World world) {
      super(world);
      this.func_70105_a(1.5F, 4.0F);
      this.func_70661_as().func_75491_a(true);
      this.field_70714_bg.func_75776_a(0, new EntityAISwimming(this));
      this.field_70714_bg.func_75776_a(1, new LOTREntityAIHiredRemainStill(this));
      this.field_70714_bg.func_75776_a(2, new LOTREntityAIAttackOnCollide(this, 1.5D, false));
      this.field_70714_bg.func_75776_a(3, new LOTREntityAIFollowHiringPlayer(this));
   }

   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_75682_a(17, (byte)0);
   }

   public boolean isHuornActive() {
      return this.field_70180_af.func_75683_a(17) == 1;
   }

   public void setHuornActive(boolean flag) {
      this.field_70180_af.func_75692_b(17, Byte.valueOf((byte)(flag ? 1 : 0)));
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(60.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(24.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.3D);
      this.func_110148_a(npcAttackDamage).func_111128_a(4.0D);
   }

   @SideOnly(Side.CLIENT)
   public boolean func_145770_h(double d, double d1, double d2) {
      EntityLivingBase viewer = Minecraft.func_71410_x().field_71451_h;
      float f = LOTRTickHandlerClient.renderTick;
      double viewX = viewer.field_70142_S + (viewer.field_70165_t - viewer.field_70142_S) * (double)f;
      double viewY = viewer.field_70137_T + (viewer.field_70163_u - viewer.field_70137_T) * (double)f;
      double viewZ = viewer.field_70136_U + (viewer.field_70161_v - viewer.field_70136_U) * (double)f;
      Frustrum camera = new Frustrum();
      camera.func_78547_a(viewX, viewY, viewZ);
      AxisAlignedBB expandedBB = this.field_70121_D.func_72314_b(2.0D, 3.0D, 2.0D);
      if (camera.func_78546_a(expandedBB)) {
         this.ignoringFrustumForRender = true;
         this.field_70158_ak = true;
      }

      return super.func_145770_h(d, d1, d2);
   }

   public void func_70636_d() {
      super.func_70636_d();
      if (!this.field_70170_p.field_72995_K) {
         boolean active = !this.func_70661_as().func_75500_f() || this.func_70638_az() != null && this.func_70638_az().func_70089_S();
         this.setHuornActive(active);
      }

   }

   protected int func_70682_h(int i) {
      return i;
   }

   public void func_70108_f(Entity entity) {
      if (this.isHuornActive()) {
         super.func_70108_f(entity);
      } else {
         double x = this.field_70159_w;
         double y = this.field_70181_x;
         double z = this.field_70179_y;
         super.func_70108_f(entity);
         this.field_70159_w = x;
         this.field_70181_x = y;
         this.field_70179_y = z;
      }

   }

   public void func_82167_n(Entity entity) {
      if (this.isHuornActive()) {
         super.func_82167_n(entity);
      } else {
         double x = this.field_70159_w;
         double y = this.field_70181_x;
         double z = this.field_70179_y;
         super.func_82167_n(entity);
         this.field_70159_w = x;
         this.field_70181_x = y;
         this.field_70179_y = z;
      }

   }

   public boolean func_70097_a(DamageSource damagesource, float f) {
      boolean flag = super.func_70097_a(damagesource, f);
      if (flag && !this.field_70170_p.field_72995_K && !this.isHuornActive()) {
         this.setHuornActive(true);
      }

      return flag;
   }

   protected String func_70621_aR() {
      return Blocks.field_150364_r.field_149762_H.func_150495_a();
   }

   protected String func_70673_aS() {
      return Blocks.field_150364_r.field_149762_H.func_150495_a();
   }

   protected float func_70647_i() {
      return super.func_70647_i() * 0.75F;
   }
}
