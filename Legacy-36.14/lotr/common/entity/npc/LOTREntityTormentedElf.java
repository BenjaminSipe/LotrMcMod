package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityTormentedElf extends LOTREntityElf {
   public LOTREntityTormentedElf(World world) {
      super(world);
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(24.0D);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      int i = this.field_70146_Z.nextInt(5);
      if (i != 0 && i != 1 && i != 2) {
         if (i == 3) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerUtumno));
         } else if (i == 4) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerUtumnoPoisoned));
         }
      } else {
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordUtumno));
      }

      this.npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.utumnoBow));
      this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
      return data;
   }

   public LOTRFaction getFaction() {
      return LOTRFaction.UTUMNO;
   }

   protected LOTRAchievement getKillAchievement() {
      return LOTRAchievement.killTormentedElf;
   }

   protected void func_70628_a(boolean flag, int i) {
      super.func_70628_a(flag, i);
   }

   protected void dropElfItems(boolean flag, int i) {
   }

   public boolean canElfSpawnHere() {
      return true;
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      return "utumno/elf/hostile";
   }

   public String func_70639_aQ() {
      return null;
   }

   public String getAttackSound() {
      return null;
   }
}
