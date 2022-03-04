package lotr.common.entity.npc;

import lotr.common.LOTRCapes;
import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityDunlendingBerserker extends LOTREntityDunlendingWarrior {
   public LOTREntityDunlendingBerserker(World world) {
      super(world);
      this.npcShield = null;
      this.npcCape = LOTRCapes.DUNLENDING_BERSERKER;
   }

   public EntityAIBase getDunlendingAttackAI() {
      return new LOTREntityAIAttackOnCollide(this, 1.7D, false);
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(30.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(24.0D);
      this.func_110148_a(npcAttackDamageExtra).func_111128_a(2.0D);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      int i = this.field_70146_Z.nextInt(2);
      if (i == 0) {
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.battleaxeIron));
      } else if (i == 1) {
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.battleaxeBronze));
      }

      this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
      this.func_70062_b(1, new ItemStack(LOTRMod.bootsFur));
      this.func_70062_b(2, new ItemStack(LOTRMod.legsFur));
      this.func_70062_b(3, new ItemStack(LOTRMod.bodyBone));
      this.func_70062_b(4, new ItemStack(LOTRMod.helmetDunlending));
      return data;
   }
}
