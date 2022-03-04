package lotr.common.world;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.awt.Color;
import lotr.common.LOTRDimension;
import lotr.common.network.LOTRPacketBlockFX;
import lotr.common.network.LOTRPacketHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

public class LOTRWorldProviderUtumno extends LOTRWorldProvider {
   public LOTRWorldProviderUtumno() {
      this.field_76576_e = true;
   }

   public LOTRDimension getLOTRDimension() {
      return LOTRDimension.UTUMNO;
   }

   public IChunkProvider func_76555_c() {
      return new LOTRChunkProviderUtumno(this.field_76579_a, this.field_76579_a.func_72905_C());
   }

   protected void func_76556_a() {
      for(int i = 0; i <= 15; ++i) {
         float f = (float)i / 15.0F;
         this.field_76573_f[i] = (float)Math.pow((double)f, 4.0D);
      }

   }

   public float func_76563_a(long time, float f) {
      return 0.5F;
   }

   @SideOnly(Side.CLIENT)
   public double func_76565_k() {
      return 1.0D;
   }

   public float[] handleFinalFogColors(EntityLivingBase viewer, double tick, float[] rgb) {
      double y = viewer.field_70167_r + (viewer.field_70163_u - viewer.field_70167_r) * tick;
      LOTRUtumnoLevel level = LOTRUtumnoLevel.forY((int)y);
      if (level == LOTRUtumnoLevel.FIRE) {
         int min = level.getLowestCorridorFloor();
         int max = level.getHighestCorridorRoof();
         max = (int)((double)max - (double)(max - min) * 0.3D);
         double yFactor = (y - (double)min) / (double)(max - min);
         yFactor = MathHelper.func_151237_a(yFactor, 0.0D, 1.0D);
         yFactor = 1.0D - yFactor;
         Color clr = new Color(rgb[0], rgb[1], rgb[2]);
         float[] hsb = Color.RGBtoHSB(clr.getRed(), clr.getGreen(), clr.getBlue(), (float[])null);
         float br = hsb[2];
         if ((double)br > 0.0D) {
            br = (float)((double)br + (double)(1.0F - br) * yFactor);
            hsb[2] = br;
         }

         rgb = (new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]))).getRGBComponents((float[])null);
      }

      return rgb;
   }

   public boolean func_76567_e() {
      return false;
   }

   public int getRespawnDimension(EntityPlayerMP entityplayer) {
      return LOTRDimension.MIDDLE_EARTH.dimensionID;
   }

   public boolean func_76569_d() {
      return false;
   }

   public int getActualHeight() {
      return this.getHeight();
   }

   public void setSpawnPoint(int i, int j, int k) {
   }

   public static void doEvaporateFX(World world, int i, int j, int k) {
      if (!world.field_72995_K) {
         world.func_72908_a((double)((float)i + 0.5F), (double)((float)j + 0.5F), (double)((float)k + 0.5F), "random.fizz", 0.5F, 2.6F + (world.field_73012_v.nextFloat() - world.field_73012_v.nextFloat()) * 0.8F);
         LOTRPacketBlockFX pkt = new LOTRPacketBlockFX(LOTRPacketBlockFX.Type.UTUMNO_EVAPORATE, i, j, k);
         LOTRPacketHandler.networkWrapper.sendToAllAround(pkt, new TargetPoint(world.field_73011_w.field_76574_g, (double)i + 0.5D, (double)j + 0.5D, (double)k + 0.5D, 32.0D));
      }

   }

   public interface UtumnoBlock {
   }
}
