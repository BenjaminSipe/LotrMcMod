package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.world.spawning.LOTRInvasions;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityAngmarOrcMercenaryCaptain extends LOTREntityAngmarOrc implements LOTRUnitTradeable {
   public LOTREntityAngmarOrcMercenaryCaptain(World world) {
      super(world);
      this.func_70105_a(0.6F, 1.8F);
      this.addTargetTasks(false);
      this.isWeakOrc = false;
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(25.0D);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.orcSkullStaff));
      this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
      this.func_70062_b(1, new ItemStack(LOTRMod.bootsAngmar));
      this.func_70062_b(2, new ItemStack(LOTRMod.legsAngmar));
      this.func_70062_b(3, new ItemStack(LOTRMod.bodyAngmar));
      this.func_70062_b(4, (ItemStack)null);
      return data;
   }

   public float getAlignmentBonus() {
      return 5.0F;
   }

   public LOTRUnitTradeEntries getUnits() {
      return LOTRUnitTradeEntries.ANGMAR_ORC_MERCENARY_CAPTAIN;
   }

   public LOTRInvasions getWarhorn() {
      return LOTRInvasions.ANGMAR;
   }

   public boolean canTradeWith(EntityPlayer entityplayer) {
      return LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 150.0F && this.isFriendlyAndAligned(entityplayer);
   }

   public void onUnitTrade(EntityPlayer entityplayer) {
      LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeAngmarCaptain);
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      if (this.isFriendlyAndAligned(entityplayer)) {
         return this.canTradeWith(entityplayer) ? "angmar/chieftain/friendly" : "angmar/chieftain/neutral";
      } else {
         return "angmar/orc/hostile";
      }
   }
}
