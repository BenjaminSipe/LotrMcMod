package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRCapes;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.world.spawning.LOTRInvasions;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityBlackrootCaptain extends LOTREntityBlackrootArcher implements LOTRUnitTradeable {
   public LOTREntityBlackrootCaptain(World world) {
      super(world);
      this.addTargetTasks(false);
      this.npcCape = LOTRCapes.BLACKROOT;
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(25.0D);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      this.npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.blackrootBow));
      this.npcItemsInv.setIdleItem(this.npcItemsInv.getRangedWeapon());
      this.func_70062_b(1, new ItemStack(LOTRMod.bootsBlackroot));
      this.func_70062_b(2, new ItemStack(LOTRMod.legsBlackroot));
      this.func_70062_b(3, new ItemStack(LOTRMod.bodyBlackroot));
      this.func_70062_b(4, (ItemStack)null);
      return data;
   }

   public float getAlignmentBonus() {
      return 5.0F;
   }

   public LOTRUnitTradeEntries getUnits() {
      return LOTRUnitTradeEntries.BLACKROOT_CAPTAIN;
   }

   public LOTRInvasions getWarhorn() {
      return LOTRInvasions.GONDOR_BLACKROOT;
   }

   public boolean canTradeWith(EntityPlayer entityplayer) {
      return LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 150.0F && this.isFriendlyAndAligned(entityplayer);
   }

   public void onUnitTrade(EntityPlayer entityplayer) {
      LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeBlackrootCaptain);
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      if (this.isFriendlyAndAligned(entityplayer)) {
         return this.canTradeWith(entityplayer) ? "gondor/blackrootCaptain/friendly" : "gondor/blackrootCaptain/neutral";
      } else {
         return "gondor/soldier/hostile";
      }
   }
}
