package lotr.common.entity.ai;

import lotr.common.entity.npc.LOTREntityGollum;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTREntityAIGollumFollowOwner extends EntityAIBase {
   private LOTREntityGollum theGollum;
   private EntityPlayer theOwner;
   private double moveSpeed;
   private PathNavigate theGollumPathfinder;
   private int followTick;
   private float maxDist;
   private float minDist;
   private boolean avoidsWater;
   private World theWorld;

   public LOTREntityAIGollumFollowOwner(LOTREntityGollum entity, double d, float f, float f1) {
      this.theGollum = entity;
      this.moveSpeed = d;
      this.theGollumPathfinder = entity.func_70661_as();
      this.minDist = f;
      this.maxDist = f1;
      this.theWorld = entity.field_70170_p;
      this.func_75248_a(3);
   }

   public boolean func_75250_a() {
      EntityPlayer entityplayer = this.theGollum.getGollumOwner();
      if (entityplayer == null) {
         return false;
      } else if (this.theGollum.isGollumSitting()) {
         return false;
      } else if (this.theGollum.func_70068_e(entityplayer) < (double)(this.minDist * this.minDist)) {
         return false;
      } else {
         this.theOwner = entityplayer;
         return true;
      }
   }

   public boolean func_75253_b() {
      return this.theGollum.getGollumOwner() != null && !this.theGollumPathfinder.func_75500_f() && this.theGollum.func_70068_e(this.theOwner) > (double)(this.maxDist * this.maxDist) && !this.theGollum.isGollumSitting();
   }

   public void func_75249_e() {
      this.followTick = 0;
      this.avoidsWater = this.theGollum.func_70661_as().func_75486_a();
      this.theGollum.func_70661_as().func_75491_a(false);
   }

   public void func_75251_c() {
      this.theOwner = null;
      this.theGollumPathfinder.func_75499_g();
      this.theGollum.func_70661_as().func_75491_a(this.avoidsWater);
   }

   public void func_75246_d() {
      this.theGollum.func_70671_ap().func_75651_a(this.theOwner, 10.0F, (float)this.theGollum.func_70646_bf());
      if (!this.theGollum.isGollumSitting() && --this.followTick <= 0) {
         this.followTick = 10;
         if (!this.theGollumPathfinder.func_75497_a(this.theOwner, this.moveSpeed) && this.theGollum.func_70068_e(this.theOwner) >= 256.0D) {
            int i = MathHelper.func_76128_c(this.theOwner.field_70165_t);
            int j = MathHelper.func_76128_c(this.theOwner.field_70121_D.field_72338_b);
            int k = MathHelper.func_76128_c(this.theOwner.field_70161_v);
            float f = this.theGollum.field_70130_N / 2.0F;
            float f1 = this.theGollum.field_70131_O;
            AxisAlignedBB theGollumBoundingBox = AxisAlignedBB.func_72330_a(this.theOwner.field_70165_t - (double)f, this.theOwner.field_70163_u - (double)this.theGollum.field_70129_M + (double)this.theGollum.field_70139_V, this.theOwner.field_70161_v - (double)f, this.theOwner.field_70165_t + (double)f, this.theOwner.field_70163_u - (double)this.theGollum.field_70129_M + (double)this.theGollum.field_70139_V + (double)f1, this.theOwner.field_70161_v + (double)f);
            if (this.theWorld.func_147461_a(theGollumBoundingBox).isEmpty() && this.theWorld.func_147439_a(i, j - 1, k).isSideSolid(this.theWorld, i, j - 1, k, ForgeDirection.UP)) {
               this.theGollum.func_70012_b(this.theOwner.field_70165_t, this.theOwner.field_70121_D.field_72338_b, this.theOwner.field_70161_v, this.theGollum.field_70177_z, this.theGollum.field_70125_A);
               this.theGollum.field_70143_R = 0.0F;
               this.theGollum.func_70661_as().func_75499_g();
            }
         }
      }

   }
}
