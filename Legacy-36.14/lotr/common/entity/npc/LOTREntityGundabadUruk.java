package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import lotr.common.LOTRShields;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityGundabadUruk extends LOTREntityGundabadOrc {
   private static ItemStack[] urukWeapons;

   public LOTREntityGundabadUruk(World world) {
      super(world);
      this.func_70105_a(0.6F, 1.8F);
      this.isWeakOrc = false;
      this.npcShield = LOTRShields.ALIGNMENT_GUNDABAD;
   }

   public EntityAIBase createOrcAttackAI() {
      return new LOTREntityAIAttackOnCollide(this, 1.5D, false);
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(25.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(24.0D);
      this.func_110148_a(npcRangedAccuracy).func_111128_a(0.75D);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      int i = this.field_70146_Z.nextInt(urukWeapons.length);
      this.npcItemsInv.setMeleeWeapon(urukWeapons[i].func_77946_l());
      if (this.field_70146_Z.nextInt(6) == 0) {
         this.npcItemsInv.setSpearBackup(this.npcItemsInv.getMeleeWeapon());
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearGundabadUruk));
      }

      this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
      this.func_70062_b(1, new ItemStack(LOTRMod.bootsGundabadUruk));
      this.func_70062_b(2, new ItemStack(LOTRMod.legsGundabadUruk));
      this.func_70062_b(3, new ItemStack(LOTRMod.bodyGundabadUruk));
      this.func_70062_b(4, new ItemStack(LOTRMod.helmetGundabadUruk));
      return data;
   }

   public float getAlignmentBonus() {
      return 2.0F;
   }

   protected LOTRAchievement getKillAchievement() {
      return LOTRAchievement.killGundabadUruk;
   }

   protected float func_70647_i() {
      return super.func_70647_i() * 0.75F;
   }

   static {
      urukWeapons = new ItemStack[]{new ItemStack(LOTRMod.swordGundabadUruk), new ItemStack(LOTRMod.battleaxeGundabadUruk), new ItemStack(LOTRMod.hammerGundabadUruk), new ItemStack(LOTRMod.daggerGundabadUruk), new ItemStack(LOTRMod.daggerGundabadUrukPoisoned), new ItemStack(LOTRMod.pikeGundabadUruk)};
   }
}
