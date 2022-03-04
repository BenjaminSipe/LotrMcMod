package lotr.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import lotr.common.LOTRCreativeTabs;
import lotr.common.entity.item.LOTREntityBossTrophy;
import net.minecraft.block.Block;
import net.minecraft.block.Block.SoundType;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRItemBossTrophy extends Item {
   @SideOnly(Side.CLIENT)
   private IIcon[] trophyIcons;

   public LOTRItemBossTrophy() {
      this.func_77637_a(LOTRCreativeTabs.tabDeco);
      this.func_77625_d(1);
      this.func_77656_e(0);
      this.func_77627_a(true);
   }

   public static LOTRItemBossTrophy.TrophyType getTrophyType(ItemStack itemstack) {
      return itemstack.func_77973_b() instanceof LOTRItemBossTrophy ? getTrophyType(itemstack.func_77960_j()) : null;
   }

   public static LOTRItemBossTrophy.TrophyType getTrophyType(int i) {
      return LOTRItemBossTrophy.TrophyType.forID(i);
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_77617_a(int i) {
      if (i >= this.trophyIcons.length) {
         i = 0;
      }

      return this.trophyIcons[i];
   }

   @SideOnly(Side.CLIENT)
   public void func_94581_a(IIconRegister iconregister) {
      this.trophyIcons = new IIcon[LOTRItemBossTrophy.TrophyType.trophyTypes.size()];

      for(int i = 0; i < this.trophyIcons.length; ++i) {
         this.trophyIcons[i] = iconregister.func_94245_a(this.func_111208_A() + "_" + ((LOTRItemBossTrophy.TrophyType)LOTRItemBossTrophy.TrophyType.trophyTypes.get(i)).trophyName);
      }

   }

   public String func_77667_c(ItemStack itemstack) {
      return super.func_77658_a() + "." + getTrophyType(itemstack).trophyName;
   }

   public boolean func_77648_a(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float f, float f1, float f2) {
      LOTRItemBossTrophy.TrophyType trophyType = getTrophyType(itemstack);
      SoundType blockSound = Blocks.field_150348_b.field_149762_H;
      if (world.func_147439_a(i, j, k).isReplaceable(world, i, j, k)) {
         side = 1;
      } else if (side == 1) {
         ++j;
      }

      if (side == 0) {
         return false;
      } else {
         if (side == 1) {
            if (!entityplayer.func_82247_a(i, j, k, side, itemstack)) {
               return false;
            }

            Block block = world.func_147439_a(i, j - 1, k);
            world.func_72805_g(i, j - 1, k);
            if (block.isSideSolid(world, i, j - 1, k, ForgeDirection.UP) && !world.field_72995_K) {
               LOTREntityBossTrophy trophy = new LOTREntityBossTrophy(world);
               trophy.func_70012_b((double)((float)i + 0.5F), (double)j, (double)((float)k + 0.5F), 180.0F - entityplayer.field_70177_z % 360.0F, 0.0F);
               trophy.setTrophyHanging(false);
               if (world.func_72855_b(trophy.field_70121_D) && world.func_72945_a(trophy, trophy.field_70121_D).size() == 0 && !world.func_72953_d(trophy.field_70121_D)) {
                  trophy.setTrophyType(trophyType);
                  world.func_72838_d(trophy);
                  world.func_72956_a(trophy, blockSound.func_150496_b(), (blockSound.func_150497_c() + 1.0F) / 2.0F, blockSound.func_150494_d() * 0.8F);
                  --itemstack.field_77994_a;
                  return true;
               }

               trophy.func_70106_y();
               return false;
            }
         } else {
            if (!entityplayer.func_82247_a(i, j, k, side, itemstack)) {
               return false;
            }

            if (!world.field_72995_K) {
               int direction = Direction.field_71579_d[side];
               LOTREntityBossTrophy trophy = new LOTREntityBossTrophy(world);
               trophy.func_70012_b((double)((float)(i + Direction.field_71583_a[direction]) + 0.5F), (double)j, (double)((float)(k + Direction.field_71581_b[direction]) + 0.5F), (float)direction * 90.0F, 0.0F);
               trophy.setTrophyHanging(true);
               trophy.setTrophyFacing(direction);
               if (world.func_72855_b(trophy.field_70121_D) && !world.func_72953_d(trophy.field_70121_D) && trophy.hangingOnValidSurface()) {
                  trophy.setTrophyType(trophyType);
                  world.func_72838_d(trophy);
                  world.func_72956_a(trophy, blockSound.func_150496_b(), (blockSound.func_150497_c() + 1.0F) / 2.0F, blockSound.func_150494_d() * 0.8F);
                  --itemstack.field_77994_a;
                  return true;
               }

               trophy.func_70106_y();
               return false;
            }
         }

         return false;
      }
   }

   @SideOnly(Side.CLIENT)
   public void func_150895_a(Item item, CreativeTabs tab, List list) {
      Iterator var4 = LOTRItemBossTrophy.TrophyType.trophyTypes.iterator();

      while(var4.hasNext()) {
         LOTRItemBossTrophy.TrophyType type = (LOTRItemBossTrophy.TrophyType)var4.next();
         list.add(new ItemStack(item, 1, type.trophyID));
      }

   }

   public static enum TrophyType {
      MOUNTAIN_TROLL_CHIEFTAIN(0, "mtc"),
      MALLORN_ENT(1, "mallornEnt");

      public static List trophyTypes = new ArrayList();
      private static Map trophyForID = new HashMap();
      public final int trophyID;
      public final String trophyName;

      private TrophyType(int i, String s) {
         this.trophyID = i;
         this.trophyName = s;
      }

      public static LOTRItemBossTrophy.TrophyType forID(int ID) {
         return (LOTRItemBossTrophy.TrophyType)trophyForID.get(ID);
      }

      static {
         LOTRItemBossTrophy.TrophyType[] var0 = values();
         int var1 = var0.length;

         for(int var2 = 0; var2 < var1; ++var2) {
            LOTRItemBossTrophy.TrophyType t = var0[var2];
            trophyTypes.add(t);
            trophyForID.put(t.trophyID, t);
         }

      }
   }
}
