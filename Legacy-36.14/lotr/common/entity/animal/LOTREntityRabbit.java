package lotr.common.entity.animal;

import java.util.List;
import java.util.UUID;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntities;
import lotr.common.entity.LOTRRandomSkinEntity;
import lotr.common.entity.ai.LOTREntityAIAvoidWithChance;
import lotr.common.entity.ai.LOTREntityAIFlee;
import lotr.common.entity.ai.LOTREntityAIRabbitEatCrops;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.entity.npc.LOTRFarmhand;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class LOTREntityRabbit extends EntityCreature implements LOTRAmbientCreature, LOTRRandomSkinEntity {
   private static final String fleeSound = "lotr:rabbit.flee";

   public LOTREntityRabbit(World world) {
      super(world);
      this.func_70105_a(0.5F, 0.5F);
      this.func_70661_as().func_75491_a(true);
      this.field_70714_bg.func_75776_a(0, new EntityAISwimming(this));
      this.field_70714_bg.func_75776_a(1, new LOTREntityAIFlee(this, 2.0D));
      this.field_70714_bg.func_75776_a(2, new LOTREntityAIAvoidWithChance(this, EntityPlayer.class, 4.0F, 1.3D, 1.5D, 0.05F, "lotr:rabbit.flee"));
      this.field_70714_bg.func_75776_a(2, new LOTREntityAIAvoidWithChance(this, LOTREntityNPC.class, 4.0F, 1.3D, 1.5D, 0.05F, "lotr:rabbit.flee"));
      this.field_70714_bg.func_75776_a(3, new LOTREntityAIRabbitEatCrops(this, 1.2D));
      this.field_70714_bg.func_75776_a(4, new EntityAIWander(this, 1.0D));
      this.field_70714_bg.func_75776_a(5, new EntityAIWatchClosest(this, EntityLivingBase.class, 8.0F, 0.05F));
      this.field_70714_bg.func_75776_a(6, new EntityAILookIdle(this));
   }

   public void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_75682_a(17, (byte)0);
   }

   public boolean isRabbitEating() {
      return this.field_70180_af.func_75683_a(17) == 1;
   }

   public void setRabbitEating(boolean flag) {
      this.field_70180_af.func_75692_b(17, Byte.valueOf((byte)(flag ? 1 : 0)));
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(4.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.25D);
   }

   public void setUniqueID(UUID uuid) {
      this.field_96093_i = uuid;
   }

   protected boolean func_70650_aV() {
      return true;
   }

   public boolean func_70097_a(DamageSource damagesource, float f) {
      boolean flag = super.func_70097_a(damagesource, f);
      if (flag && !this.field_70170_p.field_72995_K && damagesource.func_76346_g() instanceof EntityPlayer && this.isRabbitEating()) {
         EntityPlayer entityplayer = (EntityPlayer)damagesource.func_76346_g();
         LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.attackRabbit);
      }

      return flag;
   }

   public void func_70628_a(boolean flag, int i) {
      int meat = this.field_70146_Z.nextInt(3) + this.field_70146_Z.nextInt(1 + i);

      for(int l = 0; l < meat; ++l) {
         if (this.func_70027_ad()) {
            this.func_145779_a(LOTRMod.rabbitCooked, 1);
         } else {
            this.func_145779_a(LOTRMod.rabbitRaw, 1);
         }
      }

   }

   protected boolean func_70692_ba() {
      return true;
   }

   public boolean func_70601_bi() {
      if (super.func_70601_bi()) {
         boolean flag = LOTRAmbientSpawnChecks.canSpawn(this, 8, 4, 32, 4, Material.field_151585_k, Material.field_151582_l);
         if (flag) {
            int i = MathHelper.func_76128_c(this.field_70165_t);
            int j = MathHelper.func_76128_c(this.field_70163_u);
            int k = MathHelper.func_76128_c(this.field_70161_v);
            return !this.anyFarmhandsNearby(i, j, k);
         }
      }

      return false;
   }

   public boolean anyFarmhandsNearby(int i, int j, int k) {
      int range = 16;
      List farmhands = this.field_70170_p.func_72872_a(LOTRFarmhand.class, AxisAlignedBB.func_72330_a((double)i, (double)j, (double)k, (double)(i + 1), (double)(j + 1), (double)(k + 1)).func_72314_b((double)range, (double)range, (double)range));
      return !farmhands.isEmpty();
   }

   public float func_70783_a(int i, int j, int k) {
      Block block = this.field_70170_p.func_147439_a(i, j - 1, k);
      return block == Blocks.field_150349_c ? 10.0F : this.field_70170_p.func_72801_o(i, j, k) - 0.5F;
   }

   protected int func_70693_a(EntityPlayer entityplayer) {
      return 1 + this.field_70146_Z.nextInt(2);
   }

   protected String func_70621_aR() {
      return "lotr:rabbit.hurt";
   }

   protected String func_70673_aS() {
      return "lotr:rabbit.death";
   }

   public int func_70627_aG() {
      return 200;
   }

   public ItemStack getPickedResult(MovingObjectPosition target) {
      return new ItemStack(LOTRMod.spawnEgg, 1, LOTREntities.getEntityID(this));
   }
}
