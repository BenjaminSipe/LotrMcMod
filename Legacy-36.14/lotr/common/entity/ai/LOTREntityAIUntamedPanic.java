package lotr.common.entity.ai;

import lotr.common.entity.npc.LOTREntityNPCRideable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;

public class LOTREntityAIUntamedPanic extends EntityAIBase {
   private LOTREntityNPCRideable theMount;
   private double speed;
   private double targetX;
   private double targetY;
   private double targetZ;

   public LOTREntityAIUntamedPanic(LOTREntityNPCRideable mount, double d) {
      this.theMount = mount;
      this.speed = d;
      this.func_75248_a(1);
   }

   public boolean func_75250_a() {
      if (!this.theMount.isNPCTamed() && this.theMount.field_70153_n instanceof EntityPlayer) {
         Vec3 vec3 = RandomPositionGenerator.func_75463_a(this.theMount, 5, 4);
         if (vec3 == null) {
            return false;
         } else {
            this.targetX = vec3.field_72450_a;
            this.targetY = vec3.field_72448_b;
            this.targetZ = vec3.field_72449_c;
            return true;
         }
      } else {
         return false;
      }
   }

   public void func_75249_e() {
      this.theMount.func_70661_as().func_75492_a(this.targetX, this.targetY, this.targetZ, this.speed);
   }

   public boolean func_75253_b() {
      return !this.theMount.func_70661_as().func_75500_f() && this.theMount.field_70153_n instanceof EntityPlayer && !this.theMount.isNPCTamed();
   }

   public void func_75246_d() {
      if (this.theMount.func_70681_au().nextInt(50) == 0) {
         if (this.theMount.field_70153_n instanceof EntityPlayer) {
            int i = this.theMount.getNPCTemper();
            int j = this.theMount.getMaxNPCTemper();
            if (j > 0 && this.theMount.func_70681_au().nextInt(j) < i) {
               this.theMount.tameNPC((EntityPlayer)this.theMount.field_70153_n);
               this.theMount.spawnHearts();
               return;
            }

            this.theMount.increaseNPCTemper(5);
         }

         this.theMount.field_70153_n.func_70078_a((Entity)null);
         this.theMount.field_70153_n = null;
         this.theMount.angerNPC();
         this.theMount.spawnSmokes();
      }

   }
}
