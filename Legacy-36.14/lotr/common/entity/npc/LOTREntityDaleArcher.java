package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAIRangedAttack;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityDaleArcher extends LOTREntityDaleSoldier {
   public LOTREntityDaleArcher(World world) {
      super(world);
      this.spawnRidingHorse = false;
   }

   protected EntityAIBase createDaleAttackAI() {
      return new LOTREntityAIRangedAttack(this, 1.3D, 30, 50, 16.0F);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      this.npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.daleBow));
      this.npcItemsInv.setIdleItem(this.npcItemsInv.getRangedWeapon());
      return data;
   }

   protected void onAttackModeChange(LOTREntityNPC.AttackMode mode, boolean mounted) {
      if (mode == LOTREntityNPC.AttackMode.IDLE) {
         this.func_70062_b(0, this.npcItemsInv.getIdleItem());
      } else {
         this.func_70062_b(0, this.npcItemsInv.getRangedWeapon());
      }

   }

   protected void func_70628_a(boolean flag, int i) {
      super.func_70628_a(flag, i);
      this.dropNPCArrows(i);
   }
}
