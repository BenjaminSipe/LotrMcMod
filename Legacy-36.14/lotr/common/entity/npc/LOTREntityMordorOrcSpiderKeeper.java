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

public class LOTREntityMordorOrcSpiderKeeper extends LOTREntityMordorOrc implements LOTRUnitTradeable {
   public LOTREntityMordorOrcSpiderKeeper(World world) {
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
      this.func_70062_b(1, new ItemStack(LOTRMod.bootsOrc));
      this.func_70062_b(2, new ItemStack(LOTRMod.legsOrc));
      this.func_70062_b(3, new ItemStack(LOTRMod.bodyOrc));
      this.func_70062_b(4, (ItemStack)null);
      if (!this.field_70170_p.field_72995_K) {
         LOTREntityMordorSpider spider = new LOTREntityMordorSpider(this.field_70170_p);
         spider.func_70012_b(this.field_70165_t, this.field_70163_u, this.field_70161_v, this.field_70177_z, 0.0F);
         spider.setSpiderScale(3);
         if (this.field_70170_p.func_147461_a(spider.field_70121_D).isEmpty() || this.liftSpawnRestrictions) {
            spider.func_110161_a((IEntityLivingData)null);
            this.field_70170_p.func_72838_d(spider);
            this.func_70078_a(spider);
         }
      }

      return data;
   }

   public float getAlignmentBonus() {
      return 5.0F;
   }

   public LOTRUnitTradeEntries getUnits() {
      return LOTRUnitTradeEntries.MORDOR_ORC_SPIDER_KEEPER;
   }

   public LOTRInvasions getWarhorn() {
      return LOTRInvasions.MORDOR_NAN_UNGOL;
   }

   public boolean canTradeWith(EntityPlayer entityplayer) {
      return LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 250.0F && this.isFriendlyAndAligned(entityplayer);
   }

   public void onUnitTrade(EntityPlayer entityplayer) {
      LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeOrcSpiderKeeper);
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      if (this.isFriendlyAndAligned(entityplayer)) {
         return this.canTradeWith(entityplayer) ? "mordor/chieftain/friendly" : "mordor/chieftain/neutral";
      } else {
         return "mordor/orc/hostile";
      }
   }
}
