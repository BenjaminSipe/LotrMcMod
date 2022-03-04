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

public class LOTREntityTauredainChieftain extends LOTREntityTauredainWarrior implements LOTRUnitTradeable {
   public LOTREntityTauredainChieftain(World world) {
      super(world);
      this.addTargetTasks(false);
      this.npcCape = LOTRCapes.TAURETHRIM;
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(25.0D);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordTauredain));
      this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
      this.func_70062_b(1, new ItemStack(LOTRMod.bootsTauredain));
      this.func_70062_b(2, new ItemStack(LOTRMod.legsTauredain));
      this.func_70062_b(3, new ItemStack(LOTRMod.bodyTauredain));
      this.func_70062_b(4, new ItemStack(LOTRMod.helmetTauredainChieftain));
      return data;
   }

   public float getAlignmentBonus() {
      return 5.0F;
   }

   public LOTRUnitTradeEntries getUnits() {
      return LOTRUnitTradeEntries.TAUREDAIN_CHIEFTAIN;
   }

   public LOTRInvasions getWarhorn() {
      return LOTRInvasions.TAUREDAIN;
   }

   public boolean canTradeWith(EntityPlayer entityplayer) {
      return LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 200.0F && this.isFriendlyAndAligned(entityplayer);
   }

   public void onUnitTrade(EntityPlayer entityplayer) {
      LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeTauredainChieftain);
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      if (this.isFriendlyAndAligned(entityplayer)) {
         return this.canTradeWith(entityplayer) ? "tauredain/chieftain/friendly" : "tauredain/chieftain/neutral";
      } else {
         return "tauredain/warrior/hostile";
      }
   }
}
