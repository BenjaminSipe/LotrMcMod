package lotr.common.item;

import java.util.List;
import lotr.common.LOTRCreativeTabs;
import lotr.common.entity.LOTREntityNPCRespawner;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class LOTRItemNPCRespawner extends Item {
   public LOTRItemNPCRespawner() {
      this.func_77625_d(1);
      this.func_77637_a(LOTRCreativeTabs.tabMisc);
   }

   public boolean func_77648_a(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float f, float f1, float f2) {
      if (entityplayer.field_71075_bZ.field_75098_d) {
         if (!world.field_72995_K) {
            i += Facing.field_71586_b[side];
            j += Facing.field_71587_c[side];
            k += Facing.field_71585_d[side];
            this.placeSpawnerAt(world, i, j, k);
         }

         return true;
      } else {
         return false;
      }
   }

   public ItemStack func_77659_a(ItemStack itemstack, World world, EntityPlayer entityplayer) {
      if (entityplayer.field_71075_bZ.field_75098_d && !world.field_72995_K) {
         Vec3 eyePos = Vec3.func_72443_a(entityplayer.field_70165_t, entityplayer.field_70163_u + (double)entityplayer.func_70047_e(), entityplayer.field_70161_v);
         Vec3 look = entityplayer.func_70676_i(1.0F);
         double range = ((EntityPlayerMP)entityplayer).field_71134_c.getBlockReachDistance();
         double d = eyePos.field_72450_a + look.field_72450_a * range;
         double d1 = eyePos.field_72448_b + look.field_72448_b * range;
         double d2 = eyePos.field_72449_c + look.field_72449_c * range;
         int i = MathHelper.func_76128_c(d);
         int j = MathHelper.func_76128_c(d1);
         int k = MathHelper.func_76128_c(d2);
         this.placeSpawnerAt(world, i, j, k);
      }

      return itemstack;
   }

   private boolean placeSpawnerAt(World world, int i, int j, int k) {
      LOTREntityNPCRespawner spawner = new LOTREntityNPCRespawner(world);
      double f = 0.1D;
      double f1 = 1.0D - f;
      List entities = world.func_72872_a(LOTREntityNPCRespawner.class, AxisAlignedBB.func_72330_a((double)i + f, (double)j + f, (double)k + f, (double)i + f1, (double)j + f1, (double)k + f1));
      if (entities.isEmpty()) {
         spawner.func_70012_b((double)i + 0.5D, (double)j, (double)k + 0.5D, 0.0F, 0.0F);
         double c = 0.01D;
         if (world.func_72945_a(spawner, spawner.field_70121_D.func_72331_e(c, c, c)).isEmpty()) {
            world.func_72838_d(spawner);
            return true;
         }
      }

      return false;
   }
}
