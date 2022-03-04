package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.quest.LOTRMiniQuest;
import lotr.common.quest.LOTRMiniQuestFactory;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityRohanShieldmaiden extends LOTREntityRohirrimWarrior {
   public LOTREntityRohanShieldmaiden(World world) {
      super(world);
      this.spawnRidingHorse = false;
      this.questInfo.setOfferChance(4000);
      this.questInfo.setMinAlignment(150.0F);
   }

   public void setupNPCGender() {
      this.familyInfo.setMale(false);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      if (this.field_70146_Z.nextBoolean()) {
         this.func_70062_b(4, new ItemStack(LOTRMod.helmetRohan));
      } else {
         this.func_70062_b(4, (ItemStack)null);
      }

      return data;
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      if (this.isFriendlyAndAligned(entityplayer)) {
         return this.hiredNPCInfo.getHiringPlayer() == entityplayer ? "rohan/warrior/hired" : "rohan/shieldmaiden/friendly";
      } else {
         return "rohan/warrior/hostile";
      }
   }

   public LOTRMiniQuest createMiniQuest() {
      return LOTRMiniQuestFactory.ROHAN_SHIELDMAIDEN.createQuest(this);
   }
}
