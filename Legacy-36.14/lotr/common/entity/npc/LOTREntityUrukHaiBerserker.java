package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityUrukHaiBerserker extends LOTREntityUrukHai {
   public static float BERSERKER_SCALE = 1.15F;

   public LOTREntityUrukHaiBerserker(World world) {
      super(world);
      this.func_70105_a(this.npcWidth * BERSERKER_SCALE, this.npcHeight * BERSERKER_SCALE);
   }

   public EntityAIBase createOrcAttackAI() {
      return new LOTREntityAIAttackOnCollide(this, 1.6D, false);
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(30.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(24.0D);
      this.func_110148_a(npcAttackDamageExtra).func_111128_a(2.0D);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.scimitarUrukBerserker));
      this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
      this.func_70062_b(4, new ItemStack(LOTRMod.helmetUrukBerserker));
      this.func_70062_b(3, new ItemStack(LOTRMod.bodyFur));
      this.func_70062_b(2, new ItemStack(LOTRMod.legsFur));
      this.func_70062_b(1, new ItemStack(LOTRMod.bootsFur));
      return data;
   }

   protected float func_70647_i() {
      return super.func_70647_i() * 0.8F;
   }
}
