package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
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

public class LOTREntityEasterlingWarlord extends LOTREntityEasterlingGoldWarrior implements LOTRUnitTradeable {
   public LOTREntityEasterlingWarlord(World world) {
      super(world);
      this.addTargetTasks(false);
   }

   protected EntityAIBase createEasterlingAttackAI() {
      return new LOTREntityAIAttackOnCollide(this, 1.6D, true);
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(25.0D);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.battleaxeRhun));
      this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
      this.func_70062_b(1, new ItemStack(LOTRMod.bootsRhunGold));
      this.func_70062_b(2, new ItemStack(LOTRMod.legsRhunGold));
      this.func_70062_b(3, new ItemStack(LOTRMod.bodyRhunGold));
      this.func_70062_b(4, new ItemStack(LOTRMod.helmetRhunWarlord));
      return data;
   }

   public float getAlignmentBonus() {
      return 5.0F;
   }

   public LOTRUnitTradeEntries getUnits() {
      return LOTRUnitTradeEntries.EASTERLING_WARLORD;
   }

   public LOTRInvasions getWarhorn() {
      return LOTRInvasions.RHUN;
   }

   public boolean canTradeWith(EntityPlayer entityplayer) {
      return LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 150.0F && this.isFriendlyAndAligned(entityplayer);
   }

   public void onUnitTrade(EntityPlayer entityplayer) {
      LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeRhunCaptain);
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      if (this.isFriendlyAndAligned(entityplayer)) {
         return this.canTradeWith(entityplayer) ? "rhun/warlord/friendly" : "rhun/warlord/neutral";
      } else {
         return "rhun/warrior/hostile";
      }
   }
}
