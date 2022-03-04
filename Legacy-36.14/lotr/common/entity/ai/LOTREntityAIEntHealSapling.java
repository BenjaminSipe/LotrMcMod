package lotr.common.entity.ai;

import java.util.Iterator;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityEnt;
import lotr.common.tileentity.LOTRTileEntityCorruptMallorn;
import net.minecraft.block.Block;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class LOTREntityAIEntHealSapling extends EntityAIBase {
   private LOTREntityEnt theEnt;
   private World theWorld;
   private double moveSpeed;
   private double xPos;
   private double yPos;
   private double zPos;
   private int healingTick;
   private static int HEAL_TIME = 160;
   private int pathingTick;
   private int rePathDelay;

   public LOTREntityAIEntHealSapling(LOTREntityEnt ent, double d) {
      this.theEnt = ent;
      this.moveSpeed = d;
      this.theWorld = ent.field_70170_p;
      this.func_75248_a(3);
   }

   public boolean func_75250_a() {
      if (this.theEnt.canHealSapling) {
         Vec3 vec3 = this.findSapling();
         if (vec3 != null) {
            this.xPos = vec3.field_72450_a;
            this.yPos = vec3.field_72448_b;
            this.zPos = vec3.field_72449_c;
            return true;
         }
      }

      return false;
   }

   public boolean func_75253_b() {
      if (!this.theEnt.canHealSapling) {
         return false;
      } else if (this.pathingTick < 300 && this.healingTick < HEAL_TIME) {
         Block block = this.theWorld.func_147439_a(MathHelper.func_76128_c(this.xPos), MathHelper.func_76128_c(this.yPos), MathHelper.func_76128_c(this.zPos));
         return block == LOTRMod.corruptMallorn;
      } else {
         return false;
      }
   }

   public void func_75251_c() {
      this.pathingTick = 0;
      this.healingTick = 0;
      this.rePathDelay = 0;
      this.theEnt.setHealingSapling(false);
   }

   public void func_75246_d() {
      if (this.theEnt.func_70092_e(this.xPos, this.yPos, this.zPos) > 9.0D) {
         this.theEnt.setHealingSapling(false);
         --this.rePathDelay;
         if (this.rePathDelay <= 0) {
            this.rePathDelay = 10;
            this.theEnt.func_70661_as().func_75492_a(this.xPos, this.yPos, this.zPos, this.moveSpeed);
         }

         ++this.pathingTick;
      } else {
         this.theEnt.func_70661_as().func_75499_g();
         this.theEnt.func_70671_ap().func_75650_a(this.xPos, this.yPos + 0.5D, this.zPos, 10.0F, (float)this.theEnt.func_70646_bf());
         this.theEnt.setHealingSapling(true);
         this.theEnt.saplingHealTarget = new ChunkCoordinates(MathHelper.func_76128_c(this.xPos), MathHelper.func_76128_c(this.yPos), MathHelper.func_76128_c(this.zPos));
         ++this.healingTick;
         if (this.healingTick >= HEAL_TIME) {
            this.theWorld.func_147465_d(MathHelper.func_76128_c(this.xPos), MathHelper.func_76128_c(this.yPos), MathHelper.func_76128_c(this.zPos), LOTRMod.sapling, 1, 3);
            this.theEnt.setHealingSapling(false);
         }
      }

   }

   private Vec3 findSapling() {
      double leastDistSq = 576.0D;
      LOTRTileEntityCorruptMallorn mallorn = null;
      Iterator var4 = this.theWorld.field_147482_g.iterator();

      while(var4.hasNext()) {
         Object obj = var4.next();
         if (obj instanceof LOTRTileEntityCorruptMallorn) {
            LOTRTileEntityCorruptMallorn te = (LOTRTileEntityCorruptMallorn)obj;
            double distSq = this.theEnt.func_70092_e((double)te.field_145851_c + 0.5D, (double)te.field_145848_d, (double)te.field_145849_e + 0.5D);
            if (distSq < leastDistSq) {
               mallorn = te;
               leastDistSq = distSq;
            }
         }
      }

      if (mallorn != null) {
         return Vec3.func_72443_a((double)mallorn.field_145851_c + 0.5D, (double)mallorn.field_145848_d, (double)mallorn.field_145849_e + 0.5D);
      } else {
         return null;
      }
   }
}
