package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRCapes;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.world.spawning.LOTRInvasions;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityDolAmrothCaptain extends LOTREntitySwanKnight implements LOTRUnitTradeable {
   public LOTREntityDolAmrothCaptain(World world) {
      super(world);
      this.addTargetTasks(false);
      this.npcCape = LOTRCapes.GONDOR;
   }

   public EntityAIBase createGondorAttackAI() {
      return new LOTREntityAIAttackOnCollide(this, 1.6D, false);
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordDolAmroth));
      this.npcItemsInv.setMeleeWeaponMounted(this.npcItemsInv.getMeleeWeapon());
      this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
      this.npcItemsInv.setIdleItemMounted(this.npcItemsInv.getMeleeWeaponMounted());
      this.func_70062_b(1, new ItemStack(LOTRMod.bootsDolAmroth));
      this.func_70062_b(2, new ItemStack(LOTRMod.legsDolAmroth));
      this.func_70062_b(3, new ItemStack(LOTRMod.bodyDolAmroth));
      this.func_70062_b(4, (ItemStack)null);
      return data;
   }

   public float getAlignmentBonus() {
      return 5.0F;
   }

   public LOTRUnitTradeEntries getUnits() {
      return LOTRUnitTradeEntries.DOL_AMROTH_CAPTAIN;
   }

   public LOTRInvasions getWarhorn() {
      return LOTRInvasions.GONDOR_DOL_AMROTH;
   }

   public boolean canTradeWith(EntityPlayer entityplayer) {
      return LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 200.0F && this.isFriendlyAndAligned(entityplayer);
   }

   public void onUnitTrade(EntityPlayer entityplayer) {
      LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeDolAmrothCaptain);
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      if (this.isFriendlyAndAligned(entityplayer)) {
         return this.canTradeWith(entityplayer) ? "gondor/dolAmrothCaptain/friendly" : "gondor/dolAmrothCaptain/neutral";
      } else {
         return "gondor/swanKnight/hostile";
      }
   }
}
