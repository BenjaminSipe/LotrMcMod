package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRCapes;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.world.spawning.LOTRInvasions;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityNearHaradrimWarlord extends LOTREntityNearHaradrimWarrior implements LOTRUnitTradeable {
   public LOTREntityNearHaradrimWarlord(World world) {
      super(world);
      this.addTargetTasks(false);
      this.npcCape = LOTRCapes.NEAR_HARAD;
   }

   public EntityAIBase createHaradrimAttackAI() {
      return new LOTREntityAIAttackOnCollide(this, 1.6D, false);
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(25.0D);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.poleaxeNearHarad));
      this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
      this.func_70062_b(4, new ItemStack(LOTRMod.helmetNearHaradWarlord));
      return data;
   }

   public float getAlignmentBonus() {
      return 5.0F;
   }

   public LOTRUnitTradeEntries getUnits() {
      return LOTRUnitTradeEntries.NEAR_HARADRIM_WARLORD;
   }

   public LOTRInvasions getWarhorn() {
      return LOTRInvasions.NEAR_HARAD_COAST;
   }

   public boolean canTradeWith(EntityPlayer entityplayer) {
      return LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 150.0F && this.isFriendlyAndAligned(entityplayer);
   }

   public void onUnitTrade(EntityPlayer entityplayer) {
      LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeNearHaradWarlord);
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      if (this.isFriendlyAndAligned(entityplayer)) {
         return this.canTradeWith(entityplayer) ? "nearHarad/coast/warlord/friendly" : "nearHarad/coast/warlord/neutral";
      } else {
         return "nearHarad/coast/warrior/hostile";
      }
   }
}
