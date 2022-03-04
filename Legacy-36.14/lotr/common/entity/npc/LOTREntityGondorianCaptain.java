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

public class LOTREntityGondorianCaptain extends LOTREntityGondorSoldier implements LOTRUnitTradeable {
   public LOTREntityGondorianCaptain(World world) {
      super(world);
      this.addTargetTasks(false);
      this.npcCape = LOTRCapes.GONDOR;
   }

   public EntityAIBase createGondorAttackAI() {
      return new LOTREntityAIAttackOnCollide(this, 1.6D, false);
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(25.0D);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordGondor));
      this.npcItemsInv.setMeleeWeaponMounted(this.npcItemsInv.getMeleeWeapon());
      this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
      this.npcItemsInv.setIdleItemMounted(this.npcItemsInv.getMeleeWeaponMounted());
      this.func_70062_b(1, new ItemStack(LOTRMod.bootsGondor));
      this.func_70062_b(2, new ItemStack(LOTRMod.legsGondor));
      this.func_70062_b(3, new ItemStack(LOTRMod.bodyGondor));
      this.func_70062_b(4, (ItemStack)null);
      return data;
   }

   public float getAlignmentBonus() {
      return 5.0F;
   }

   public LOTRUnitTradeEntries getUnits() {
      return LOTRUnitTradeEntries.GONDORIAN_CAPTAIN;
   }

   public LOTRInvasions getWarhorn() {
      return LOTRInvasions.GONDOR;
   }

   public boolean canTradeWith(EntityPlayer entityplayer) {
      return LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 200.0F && this.isFriendlyAndAligned(entityplayer);
   }

   public void onUnitTrade(EntityPlayer entityplayer) {
      LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeGondorianCaptain);
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      if (this.isFriendlyAndAligned(entityplayer)) {
         return this.canTradeWith(entityplayer) ? "gondor/captain/friendly" : "gondor/captain/neutral";
      } else {
         return "gondor/soldier/hostile";
      }
   }
}
