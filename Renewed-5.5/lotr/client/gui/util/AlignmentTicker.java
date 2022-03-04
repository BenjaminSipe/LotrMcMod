package lotr.client.gui.util;

import lotr.common.LOTRLog;
import lotr.common.data.LOTRLevelData;
import lotr.common.fac.Faction;
import lotr.common.fac.FactionSettingsManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;

public class AlignmentTicker {
   private final ResourceLocation factionName;
   private float prevAlign;
   private float currentAlign;
   private boolean firstRender = false;
   private int moveTick = 0;
   private int prevMoveTick = 0;
   private static final int MOVE_TIME = 20;
   private int flashTick;
   private static final int FLASH_TIME = 30;
   private int displayNumericalTick;
   private static final int DISPLAY_NUMERICAL_TIME = 200;

   public AlignmentTicker(ResourceLocation facName) {
      this.factionName = facName;
   }

   private Faction resolveFactionReference() {
      Faction faction = FactionSettingsManager.clientInstance().getCurrentLoadedFactions().getFactionByName(this.factionName);
      if (faction == null) {
         LOTRLog.warn("Alignment ticker couldn't resolve reference to faction %s. Potential world leak?", this.factionName);
      }

      return faction;
   }

   public void update(PlayerEntity player, boolean forceInstant) {
      Faction faction = this.resolveFactionReference();
      float playerCurrentAlign = faction != null ? LOTRLevelData.clientInstance().getData(player).getAlignmentData().getAlignment(faction) : 0.0F;
      if (forceInstant) {
         this.prevAlign = this.currentAlign = playerCurrentAlign;
         this.prevMoveTick = this.moveTick = 0;
         this.flashTick = 0;
         this.displayNumericalTick = 0;
      } else {
         if (this.currentAlign != playerCurrentAlign) {
            this.prevAlign = this.currentAlign;
            this.currentAlign = playerCurrentAlign;
            this.prevMoveTick = this.moveTick = 20;
            this.flashTick = 30;
            this.displayNumericalTick = 200;
         }

         this.prevMoveTick = this.moveTick;
         if (this.moveTick > 0) {
            --this.moveTick;
            if (this.moveTick <= 0) {
               this.prevAlign = this.currentAlign;
            }
         }

         if (this.flashTick > 0) {
            --this.flashTick;
         }

         if (this.displayNumericalTick > 0) {
            --this.displayNumericalTick;
         }
      }

   }

   public float getInterpolatedAlignment(float f) {
      if (this.moveTick == 0) {
         return this.prevAlign;
      } else {
         float tickF = (float)this.prevMoveTick + (float)(this.moveTick - this.prevMoveTick) * f;
         tickF /= 20.0F;
         tickF = 1.0F - tickF;
         float align = this.prevAlign + (this.currentAlign - this.prevAlign) * tickF;
         return align;
      }
   }

   public int getFlashTick() {
      return this.flashTick;
   }

   public int getDisplayNumericalTick() {
      return this.displayNumericalTick;
   }
}
