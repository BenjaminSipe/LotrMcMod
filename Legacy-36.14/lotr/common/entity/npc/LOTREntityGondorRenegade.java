package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.fac.LOTRFaction;
import lotr.common.quest.LOTRMiniQuest;
import lotr.common.quest.LOTRMiniQuestFactory;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityGondorRenegade extends LOTREntityGondorSoldier {
   private static ItemStack[] weaponsUmbar;

   public LOTREntityGondorRenegade(World world) {
      super(world);
      this.npcShield = null;
      this.spawnRidingHorse = false;
      this.questInfo.setOfferChance(4000);
      this.questInfo.setMinAlignment(50.0F);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      int i = this.field_70146_Z.nextInt(weaponsUmbar.length);
      this.npcItemsInv.setMeleeWeapon(weaponsUmbar[i].func_77946_l());
      this.npcItemsInv.setMeleeWeaponMounted(this.npcItemsInv.getMeleeWeapon());
      if (this.field_70146_Z.nextInt(5) == 0) {
         this.npcItemsInv.setSpearBackup(this.npcItemsInv.getMeleeWeapon());
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearNearHarad));
      }

      this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
      this.npcItemsInv.setIdleItemMounted(this.npcItemsInv.getMeleeWeaponMounted());
      if (this.field_70146_Z.nextInt(5) == 0) {
         this.func_70062_b(1, new ItemStack(LOTRMod.bootsPelargir));
         this.func_70062_b(2, new ItemStack(LOTRMod.legsPelargir));
         this.func_70062_b(3, new ItemStack(LOTRMod.bodyPelargir));
         this.func_70062_b(4, (ItemStack)null);
      } else {
         this.func_70062_b(1, new ItemStack(LOTRMod.bootsGondor));
         this.func_70062_b(2, new ItemStack(LOTRMod.legsGondor));
         this.func_70062_b(3, new ItemStack(LOTRMod.bodyGondor));
         this.func_70062_b(4, (ItemStack)null);
      }

      return data;
   }

   public LOTRFaction getFaction() {
      return LOTRFaction.NEAR_HARAD;
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      if (this.isFriendlyAndAligned(entityplayer)) {
         return this.hiredNPCInfo.getHiringPlayer() == entityplayer ? "nearHarad/renegade/hired" : "nearHarad/renegade/friendly";
      } else {
         return "nearHarad/renegade/hostile";
      }
   }

   public LOTRMiniQuest createMiniQuest() {
      return LOTRMiniQuestFactory.GONDOR_RENEGADE.createQuest(this);
   }

   static {
      weaponsUmbar = new ItemStack[]{new ItemStack(LOTRMod.scimitarNearHarad), new ItemStack(LOTRMod.scimitarNearHarad), new ItemStack(LOTRMod.scimitarNearHarad), new ItemStack(LOTRMod.poleaxeNearHarad), new ItemStack(LOTRMod.poleaxeNearHarad), new ItemStack(LOTRMod.maceNearHarad), new ItemStack(LOTRMod.pikeNearHarad)};
   }
}
