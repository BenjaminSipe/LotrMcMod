package lotr.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Iterator;
import java.util.List;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRLevelData;
import lotr.common.world.structure.LOTRStructures;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class LOTRItemStructureSpawner extends Item {
   @SideOnly(Side.CLIENT)
   private IIcon iconBase;
   @SideOnly(Side.CLIENT)
   private IIcon iconOverlay;
   @SideOnly(Side.CLIENT)
   private IIcon iconVillageBase;
   @SideOnly(Side.CLIENT)
   private IIcon iconVillageOverlay;
   public static int lastStructureSpawnTick = 0;

   public LOTRItemStructureSpawner() {
      this.func_77627_a(true);
      this.func_77637_a(LOTRCreativeTabs.tabSpawn);
   }

   public String func_77653_i(ItemStack itemstack) {
      String s = ("" + StatCollector.func_74838_a(this.func_77658_a() + ".name")).trim();
      String structureName = LOTRStructures.getNameFromID(itemstack.func_77960_j());
      if (structureName != null) {
         s = s + " " + StatCollector.func_74838_a("lotr.structure." + structureName + ".name");
      }

      return s;
   }

   @SideOnly(Side.CLIENT)
   public void func_94581_a(IIconRegister iconregister) {
      this.iconBase = iconregister.func_94245_a(this.func_111208_A() + "_base");
      this.iconOverlay = iconregister.func_94245_a(this.func_111208_A() + "_overlay");
      this.iconVillageBase = iconregister.func_94245_a(this.func_111208_A() + "_village_base");
      this.iconVillageOverlay = iconregister.func_94245_a(this.func_111208_A() + "_village_overlay");
   }

   @SideOnly(Side.CLIENT)
   public boolean func_77623_v() {
      return true;
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_77618_c(int i, int pass) {
      LOTRStructures.StructureColorInfo info = (LOTRStructures.StructureColorInfo)LOTRStructures.structureItemSpawners.get(i);
      if (info != null) {
         if (info.isVillage) {
            return pass == 0 ? this.iconVillageBase : this.iconVillageOverlay;
         } else {
            return pass == 0 ? this.iconBase : this.iconOverlay;
         }
      } else {
         return this.iconBase;
      }
   }

   @SideOnly(Side.CLIENT)
   public int func_82790_a(ItemStack itemstack, int pass) {
      LOTRStructures.StructureColorInfo info = (LOTRStructures.StructureColorInfo)LOTRStructures.structureItemSpawners.get(itemstack.func_77960_j());
      if (info != null) {
         return pass == 0 ? info.colorBackground : info.colorForeground;
      } else {
         return 16777215;
      }
   }

   public boolean func_77648_a(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float f, float f1, float f2) {
      if (world.field_72995_K) {
         return true;
      } else if (LOTRLevelData.structuresBanned()) {
         entityplayer.func_145747_a(new ChatComponentTranslation("chat.lotr.spawnStructure.disabled", new Object[0]));
         return false;
      } else if (LOTRLevelData.isPlayerBannedForStructures(entityplayer)) {
         entityplayer.func_145747_a(new ChatComponentTranslation("chat.lotr.spawnStructure.banned", new Object[0]));
         return false;
      } else if (lastStructureSpawnTick > 0) {
         entityplayer.func_145747_a(new ChatComponentTranslation("chat.lotr.spawnStructure.wait", new Object[]{(double)lastStructureSpawnTick / 20.0D}));
         return false;
      } else {
         i += Facing.field_71586_b[side];
         j += Facing.field_71587_c[side];
         k += Facing.field_71585_d[side];
         if (this.spawnStructure(entityplayer, world, itemstack.func_77960_j(), i, j, k) && !entityplayer.field_71075_bZ.field_75098_d) {
            --itemstack.field_77994_a;
         }

         return true;
      }
   }

   private boolean spawnStructure(EntityPlayer entityplayer, World world, int id, int i, int j, int k) {
      if (!LOTRStructures.structureItemSpawners.containsKey(id)) {
         return false;
      } else {
         LOTRStructures.IStructureProvider strProvider = LOTRStructures.getStructureForID(id);
         if (strProvider != null) {
            boolean generated = strProvider.generateStructure(world, entityplayer, i, j, k);
            if (generated) {
               lastStructureSpawnTick = 20;
               world.func_72956_a(entityplayer, "lotr:item.structureSpawner", 1.0F, 1.0F);
            }

            return generated;
         } else {
            return false;
         }
      }
   }

   @SideOnly(Side.CLIENT)
   public void func_150895_a(Item item, CreativeTabs tab, List list) {
      Iterator var4 = LOTRStructures.structureItemSpawners.values().iterator();

      while(var4.hasNext()) {
         LOTRStructures.StructureColorInfo info = (LOTRStructures.StructureColorInfo)var4.next();
         if (!info.isHidden) {
            list.add(new ItemStack(item, 1, info.spawnedID));
         }
      }

   }
}
