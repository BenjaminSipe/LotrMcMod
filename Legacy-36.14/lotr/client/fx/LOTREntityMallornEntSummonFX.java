package lotr.client.fx;

import net.minecraft.block.Block;
import net.minecraft.client.particle.EntityDiggingFX;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class LOTREntityMallornEntSummonFX extends EntityDiggingFX {
   private Entity summoner;
   private Entity summoned;
   private int summonedID;
   private float arcParam;

   public LOTREntityMallornEntSummonFX(World world, double d, double d1, double d2, double d3, double d4, double d5, int summonerID, int summonedID, float t, Block block, int meta, int color) {
      super(world, d, d1, d2, d3, d4, d5, block, meta);
      this.field_70159_w = d3;
      this.field_70181_x = d4;
      this.field_70179_y = d5;
      this.summoner = this.field_70170_p.func_73045_a(summonerID);
      this.summoned = this.field_70170_p.func_73045_a(summonedID);
      this.arcParam = t;
      this.field_70547_e = (int)(40.0F * this.arcParam);
      this.field_70552_h *= (float)(color >> 16 & 255) / 255.0F;
      this.field_70553_i *= (float)(color >> 8 & 255) / 255.0F;
      this.field_70551_j *= (float)(color & 255) / 255.0F;
      this.field_70544_f *= 2.0F;
      this.field_70545_g = 0.0F;
      this.field_70155_l = 10.0D;
      this.field_70145_X = true;
      this.updateArcPos();
   }

   public void func_70071_h_() {
      super.func_70071_h_();
      this.updateArcPos();
   }

   private void updateArcPos() {
      if (this.summoner != null && this.summoned != null && this.summoner.func_70089_S() && this.summoned.func_70089_S()) {
         double[] posA = new double[]{this.summoner.field_70165_t, this.summoner.field_70121_D.field_72338_b + (double)this.summoner.field_70131_O * 0.7D, this.summoner.field_70161_v};
         double[] posC = new double[]{this.summoned.field_70165_t, this.summoned.field_70121_D.field_72338_b + (double)this.summoned.field_70131_O * 0.7D, this.summoned.field_70161_v};
         double[] posB = new double[]{(posA[0] + posC[0]) / 2.0D, (posA[1] + posC[1]) / 2.0D + 20.0D, (posA[2] + posC[2]) / 2.0D};
         double[] ab = this.lerp(posA, posB, this.arcParam);
         double[] bc = this.lerp(posB, posC, this.arcParam);
         double[] abbc = this.lerp(ab, bc, this.arcParam);
         this.field_70165_t = abbc[0];
         this.field_70163_u = abbc[1];
         this.field_70161_v = abbc[2];
      } else {
         this.func_70106_y();
      }
   }

   private double[] lerp(double[] a, double[] b, float t) {
      double[] ab = new double[a.length];

      for(int i = 0; i < ab.length; ++i) {
         ab[i] = a[i] + (b[i] - a[i]) * (double)t;
      }

      return ab;
   }
}
