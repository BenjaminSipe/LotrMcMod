package lotr.common.entity.npc;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.UUID;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAIRangedAttack;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTREntityWoodElfScout extends LOTREntityWoodElf {
   private static final UUID scoutArmorSpeedBoost_id = UUID.fromString("cf0ceb91-0f13-4788-be0e-a6c67a830308");
   public static final AttributeModifier scoutArmorSpeedBoost;

   public LOTREntityWoodElfScout(World world) {
      super(world);
      this.field_70714_bg.func_75776_a(2, this.rangedAttackAI);
   }

   protected EntityAIBase createElfRangedAttackAI() {
      return new LOTREntityAIRangedAttack(this, 1.25D, 25, 35, 24.0F);
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(24.0D);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      this.npcItemsInv.setIdleItem(this.npcItemsInv.getRangedWeapon());
      this.func_70062_b(1, new ItemStack(LOTRMod.bootsWoodElvenScout));
      this.func_70062_b(2, new ItemStack(LOTRMod.legsWoodElvenScout));
      this.func_70062_b(3, new ItemStack(LOTRMod.bodyWoodElvenScout));
      if (this.field_70146_Z.nextInt(10) != 0) {
         this.func_70062_b(4, new ItemStack(LOTRMod.helmetWoodElvenScout));
      }

      return data;
   }

   public void func_70636_d() {
      super.func_70636_d();
      if (!this.field_70170_p.field_72995_K && this.func_70089_S() && this.field_70154_o == null) {
         ItemStack currentItem = this.func_71124_b(0);
         if (currentItem != null && currentItem.func_77973_b() instanceof ItemBow) {
            EntityLivingBase lastAttacker = this.func_70643_av();
            if (lastAttacker != null && this.func_70068_e(lastAttacker) < 16.0D && this.field_70146_Z.nextInt(20) == 0) {
               for(int l = 0; l < 32; ++l) {
                  int i = MathHelper.func_76128_c(this.field_70165_t) - this.field_70146_Z.nextInt(16) + this.field_70146_Z.nextInt(16);
                  int j = MathHelper.func_76128_c(this.field_70163_u) - this.field_70146_Z.nextInt(3) + this.field_70146_Z.nextInt(3);
                  int k = MathHelper.func_76128_c(this.field_70161_v) - this.field_70146_Z.nextInt(16) + this.field_70146_Z.nextInt(16);
                  if (this.func_70011_f((double)i, (double)j, (double)k) > 6.0D && this.field_70170_p.func_147439_a(i, j - 1, k).func_149721_r() && !this.field_70170_p.func_147439_a(i, j, k).func_149721_r() && !this.field_70170_p.func_147439_a(i, j + 1, k).func_149721_r()) {
                     double d = (double)i + 0.5D;
                     double d1 = (double)j;
                     double d2 = (double)k + 0.5D;
                     AxisAlignedBB aabb = this.field_70121_D.func_72329_c().func_72317_d(d - this.field_70165_t, d1 - this.field_70163_u, d2 - this.field_70161_v);
                     if (this.field_70170_p.func_72855_b(aabb) && this.field_70170_p.func_72945_a(this, aabb).isEmpty() && !this.field_70170_p.func_72953_d(aabb)) {
                        this.doTeleportEffects();
                        this.func_70107_b(d, d1, d2);
                        break;
                     }
                  }
               }
            }
         }
      }

   }

   private void doTeleportEffects() {
      this.field_70170_p.func_72956_a(this, "lotr:elf.woodElf_teleport", this.func_70599_aP(), 0.5F + this.field_70146_Z.nextFloat());
      this.field_70170_p.func_72960_a(this, (byte)15);
   }

   @SideOnly(Side.CLIENT)
   public void func_70103_a(byte b) {
      if (b == 15) {
         for(int i = 0; i < 16; ++i) {
            double d = this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5D) * (double)this.field_70130_N;
            double d1 = this.field_70163_u + this.field_70146_Z.nextDouble() * (double)this.field_70131_O;
            double d2 = this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5D) * (double)this.field_70130_N;
            double d3 = -0.05D + (double)(this.field_70146_Z.nextFloat() * 0.1F);
            double d4 = -0.05D + (double)(this.field_70146_Z.nextFloat() * 0.1F);
            double d5 = -0.05D + (double)(this.field_70146_Z.nextFloat() * 0.1F);
            LOTRMod.proxy.spawnParticle("leafGreen_" + (20 + this.field_70146_Z.nextInt(30)), d, d1, d2, d3, d4, d5);
         }
      } else {
         super.func_70103_a(b);
      }

   }

   public float getAlignmentBonus() {
      return 2.0F;
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      if (this.isFriendlyAndAligned(entityplayer)) {
         if (this.hiredNPCInfo.getHiringPlayer() == entityplayer) {
            return "woodElf/elf/hired";
         } else {
            return LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= getWoodlandTrustLevel() ? "woodElf/warrior/friendly" : "woodElf/elf/neutral";
         }
      } else {
         return "woodElf/warrior/hostile";
      }
   }

   static {
      scoutArmorSpeedBoost = (new AttributeModifier(scoutArmorSpeedBoost_id, "WE Scout armor speed boost", 0.3D, 2)).func_111168_a(false);
   }
}
