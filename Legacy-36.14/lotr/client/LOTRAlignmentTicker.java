package lotr.client;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import lotr.common.LOTRDimension;
import lotr.common.LOTRLevelData;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.player.EntityPlayer;

public class LOTRAlignmentTicker {
   private static Map allFactionTickers = new HashMap();
   private final LOTRFaction theFac;
   private float oldAlign;
   private float newAlign;
   private int moveTick = 0;
   private int prevMoveTick = 0;
   private static final int moveTime = 20;
   public int flashTick;
   private static final int flashTime = 30;
   public int numericalTick;
   private static final int numericalTime = 200;

   public static LOTRAlignmentTicker forFaction(LOTRFaction fac) {
      LOTRAlignmentTicker ticker = (LOTRAlignmentTicker)allFactionTickers.get(fac);
      if (ticker == null) {
         ticker = new LOTRAlignmentTicker(fac);
         allFactionTickers.put(fac, ticker);
      }

      return ticker;
   }

   public static void updateAll(EntityPlayer entityplayer, boolean forceInstant) {
      LOTRDimension[] var2 = LOTRDimension.values();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         LOTRDimension dim = var2[var4];
         Iterator var6 = dim.factionList.iterator();

         while(var6.hasNext()) {
            LOTRFaction fac = (LOTRFaction)var6.next();
            forFaction(fac).update(entityplayer, forceInstant);
         }
      }

   }

   public LOTRAlignmentTicker(LOTRFaction f) {
      this.theFac = f;
   }

   public void update(EntityPlayer entityplayer, boolean forceInstant) {
      float curAlign = LOTRLevelData.getData(entityplayer).getAlignment(this.theFac);
      if (forceInstant) {
         this.oldAlign = this.newAlign = curAlign;
         this.prevMoveTick = this.moveTick = 0;
         this.flashTick = 0;
         this.numericalTick = 0;
      } else {
         if (this.newAlign != curAlign) {
            this.oldAlign = this.newAlign;
            this.newAlign = curAlign;
            this.prevMoveTick = this.moveTick = 20;
            this.flashTick = 30;
            this.numericalTick = 200;
         }

         this.prevMoveTick = this.moveTick;
         if (this.moveTick > 0) {
            --this.moveTick;
            if (this.moveTick <= 0) {
               this.oldAlign = this.newAlign;
            }
         }

         if (this.flashTick > 0) {
            --this.flashTick;
         }

         if (this.numericalTick > 0) {
            --this.numericalTick;
         }
      }

   }

   public float getInterpolatedAlignment(float f) {
      if (this.moveTick == 0) {
         return this.oldAlign;
      } else {
         float tickF = (float)this.prevMoveTick + (float)(this.moveTick - this.prevMoveTick) * f;
         tickF /= 20.0F;
         tickF = 1.0F - tickF;
         float align = this.oldAlign + (this.newAlign - this.oldAlign) * tickF;
         return align;
      }
   }
}
