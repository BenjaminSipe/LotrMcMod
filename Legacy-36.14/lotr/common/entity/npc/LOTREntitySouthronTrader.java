package lotr.common.entity.npc;

import java.awt.Color;
import java.util.Random;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.item.LOTRItemHaradRobes;
import lotr.common.item.LOTRItemHaradTurban;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public abstract class LOTREntitySouthronTrader extends LOTREntityNearHaradrim implements LOTRTradeable {
   public LOTREntitySouthronTrader(World world) {
      super(world);
      this.addTargetTasks(false);
   }

   public static ItemStack createTraderTurban(Random random) {
      ItemStack turban = new ItemStack(LOTRMod.helmetHaradRobes);
      if (random.nextInt(3) == 0) {
         LOTRItemHaradTurban.setHasOrnament(turban, true);
      }

      float h = random.nextFloat() * 360.0F;
      float s = MathHelper.func_151240_a(random, 0.6F, 0.8F);
      float b = MathHelper.func_151240_a(random, 0.5F, 0.75F);
      int turbanColor = Color.HSBtoRGB(h, s, b) & 16777215;
      LOTRItemHaradRobes.setRobesColor(turban, turbanColor);
      return turban;
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      this.func_70062_b(4, createTraderTurban(this.field_70146_Z));
      return data;
   }

   public void onAttackModeChange(LOTREntityNPC.AttackMode mode, boolean mounted) {
      if (mode == LOTREntityNPC.AttackMode.IDLE) {
         this.func_70062_b(0, this.npcItemsInv.getIdleItem());
      } else {
         this.func_70062_b(0, this.npcItemsInv.getMeleeWeapon());
      }

   }

   public float getAlignmentBonus() {
      return 2.0F;
   }

   public boolean canTradeWith(EntityPlayer entityplayer) {
      return LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 0.0F && this.isFriendlyAndAligned(entityplayer);
   }

   public void onPlayerTrade(EntityPlayer entityplayer, LOTRTradeEntries.TradeType type, ItemStack itemstack) {
      LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeBazaarTrader);
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      return this.isFriendlyAndAligned(entityplayer) ? "nearHarad/coast/bazaarTrader/friendly" : "nearHarad/coast/bazaarTrader/hostile";
   }
}
