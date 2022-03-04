package lotr.common.entity.npc;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class LOTREntityUtumnoTroll extends LOTREntityTroll {
   public LOTREntityUtumnoTroll(World world) {
      super(world);
   }

   public float getTrollScale() {
      return 1.5F;
   }

   public EntityAIBase getTrollAttackAI() {
      return new LOTREntityAIAttackOnCollide(this, 2.0D, false);
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(32.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(60.0D);
      this.func_110148_a(npcAttackDamage).func_111128_a(7.0D);
   }

   public LOTRFaction getFaction() {
      return LOTRFaction.UTUMNO;
   }

   protected boolean hasTrollName() {
      return false;
   }

   protected boolean canTrollBeTickled(EntityPlayer entityplayer) {
      return false;
   }

   public void onTrollDeathBySun() {
      this.field_70170_p.func_72956_a(this, "lotr:troll.transform", this.func_70599_aP(), this.func_70647_i());
      this.field_70170_p.func_72960_a(this, (byte)15);
      this.func_70106_y();
   }

   @SideOnly(Side.CLIENT)
   public void func_70103_a(byte b) {
      if (b == 15) {
         super.func_70103_a(b);

         for(int l = 0; l < 64; ++l) {
            LOTRMod.proxy.spawnParticle("largeStone", this.field_70165_t + this.field_70146_Z.nextGaussian() * (double)this.field_70130_N * 0.5D, this.field_70163_u + this.field_70146_Z.nextDouble() * (double)this.field_70131_O, this.field_70161_v + this.field_70146_Z.nextGaussian() * (double)this.field_70130_N * 0.5D, 0.0D, 0.0D, 0.0D);
         }
      } else {
         super.func_70103_a(b);
      }

   }

   protected LOTRAchievement getKillAchievement() {
      return LOTRAchievement.killUtumnoTroll;
   }

   protected int func_70693_a(EntityPlayer entityplayer) {
      return 5 + this.field_70146_Z.nextInt(6);
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      return null;
   }
}
