package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.fac.LOTRFaction;
import lotr.common.quest.LOTRMiniQuest;
import lotr.common.quest.LOTRMiniQuestFactory;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTREntityWickedDwarf extends LOTREntityDwarf implements LOTRTradeable.Smith {
   private static ItemStack[] wickedWeapons;

   public static LOTRFaction[] getTradeFactions() {
      return new LOTRFaction[]{LOTRFaction.MORDOR, LOTRFaction.ANGMAR, LOTRFaction.RHUDEL};
   }

   public LOTREntityWickedDwarf(World world) {
      super(world);
      this.addTargetTasks(true);
   }

   public LOTRTradeEntries getBuyPool() {
      return LOTRTradeEntries.WICKED_DWARF_BUY;
   }

   public LOTRTradeEntries getSellPool() {
      return LOTRTradeEntries.WICKED_DWARF_SELL;
   }

   public void setupNPCGender() {
      this.familyInfo.setMale(true);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      int i = this.field_70146_Z.nextInt(wickedWeapons.length);
      this.npcItemsInv.setMeleeWeapon(wickedWeapons[i].func_77946_l());
      this.npcItemsInv.setIdleItem(new ItemStack(LOTRMod.pickaxeDwarven));
      if (this.field_70146_Z.nextInt(4) == 0) {
         this.func_70062_b(1, new ItemStack(LOTRMod.bootsDwarven));
         this.func_70062_b(2, new ItemStack(LOTRMod.legsDwarven));
         this.func_70062_b(3, new ItemStack(LOTRMod.bodyDwarven));
      } else {
         this.func_70062_b(1, (ItemStack)null);
         this.func_70062_b(2, (ItemStack)null);
         this.func_70062_b(3, (ItemStack)null);
      }

      this.func_70062_b(4, (ItemStack)null);
      return data;
   }

   public LOTRFaction getFaction() {
      return LOTRFaction.MORDOR;
   }

   protected LOTRAchievement getKillAchievement() {
      return LOTRAchievement.killWickedDwarf;
   }

   public float getAlignmentBonus() {
      return 2.0F;
   }

   public boolean canTradeWith(EntityPlayer entityplayer) {
      boolean hasSuitableAlignment = false;
      LOTRFaction[] var3 = getTradeFactions();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         LOTRFaction f = var3[var5];
         if (LOTRLevelData.getData(entityplayer).getAlignment(f) >= 100.0F) {
            hasSuitableAlignment = true;
            break;
         }
      }

      return hasSuitableAlignment && this.isFriendly(entityplayer);
   }

   public void onPlayerTrade(EntityPlayer entityplayer, LOTRTradeEntries.TradeType type, ItemStack itemstack) {
      LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeWickedDwarf);
   }

   protected boolean canDwarfSpawnHere() {
      int i = MathHelper.func_76128_c(this.field_70165_t);
      int j = MathHelper.func_76128_c(this.field_70121_D.field_72338_b);
      int k = MathHelper.func_76128_c(this.field_70161_v);
      return j > 62 && this.field_70170_p.func_147439_a(i, j - 1, k) == this.field_70170_p.func_72807_a(i, k).field_76752_A;
   }

   protected boolean canDwarfSpawnAboveGround() {
      return true;
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      if (this.isFriendlyAndAligned(entityplayer)) {
         return this.canTradeWith(entityplayer) ? "dwarf/wicked/friendly" : "dwarf/wicked/neutral";
      } else {
         return "dwarf/wicked/hostile";
      }
   }

   public LOTRMiniQuest createMiniQuest() {
      return null;
   }

   public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
      return null;
   }

   static {
      wickedWeapons = new ItemStack[]{new ItemStack(LOTRMod.swordDwarven), new ItemStack(LOTRMod.battleaxeDwarven), new ItemStack(LOTRMod.hammerDwarven)};
   }
}
