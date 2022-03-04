package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.LOTRShields;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityBlackrootSoldier extends LOTREntityGondorSoldier {
   public LOTREntityBlackrootSoldier(World world) {
      super(world);
      this.spawnRidingHorse = this.field_70146_Z.nextInt(10) == 0;
      this.npcShield = LOTRShields.ALIGNMENT_BLACKROOT_VALE;
   }

   public EntityAIBase createGondorAttackAI() {
      return new LOTREntityAIAttackOnCollide(this, 1.45D, false);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordGondor));
      this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
      this.func_70062_b(1, new ItemStack(LOTRMod.bootsBlackroot));
      this.func_70062_b(2, new ItemStack(LOTRMod.legsBlackroot));
      this.func_70062_b(3, new ItemStack(LOTRMod.bodyBlackroot));
      if (this.field_70146_Z.nextInt(10) != 0) {
         this.func_70062_b(4, new ItemStack(LOTRMod.helmetBlackroot));
      } else {
         this.func_70062_b(4, (ItemStack)null);
      }

      return data;
   }

   protected void onAttackModeChange(LOTREntityNPC.AttackMode mode, boolean mounted) {
      if (mode == LOTREntityNPC.AttackMode.IDLE) {
         this.func_70062_b(0, this.npcItemsInv.getIdleItem());
      } else {
         this.func_70062_b(0, this.npcItemsInv.getMeleeWeapon());
      }

   }
}
