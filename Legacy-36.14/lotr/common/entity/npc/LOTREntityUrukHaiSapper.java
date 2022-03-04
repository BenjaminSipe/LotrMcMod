package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntityUtils;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.entity.ai.LOTREntityAIOrcPlaceBomb;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityUrukHaiSapper extends LOTREntityUrukHai {
   public LOTREntityUrukHaiSapper(World world) {
      super(world);
      LOTREntityUtils.removeAITask(this, EntityAIAvoidEntity.class);
   }

   public EntityAIBase createOrcAttackAI() {
      this.field_70714_bg.func_75776_a(4, new LOTREntityAIOrcPlaceBomb(this, 1.5D));
      return new LOTREntityAIAttackOnCollide(this, 2.0D, false);
   }

   public boolean isOrcBombardier() {
      return true;
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      this.npcItemsInv.setBombingItem(new ItemStack(LOTRMod.orcTorchItem));
      this.npcItemsInv.setBomb(new ItemStack(LOTRMod.orcBomb, 1, 0));
      this.func_70062_b(4, new ItemStack(LOTRMod.helmetUruk));
      return data;
   }
}
