package lotr.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Iterator;
import java.util.List;
import lotr.common.LOTRCreativeTabs;
import lotr.common.dispenser.LOTRDispenseSpawnEgg;
import lotr.common.entity.LOTREntities;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class LOTRItemSpawnEgg extends Item {
   public LOTRItemSpawnEgg() {
      this.func_77627_a(true);
      this.func_77637_a(LOTRCreativeTabs.tabSpawn);
      BlockDispenser.field_149943_a.func_82595_a(this, new LOTRDispenseSpawnEgg());
   }

   public String func_77653_i(ItemStack itemstack) {
      String itemName = ("" + StatCollector.func_74838_a(this.func_77658_a() + ".name")).trim();
      String entityName = LOTREntities.getStringFromID(itemstack.func_77960_j());
      if (entityName != null) {
         itemName = itemName + " " + StatCollector.func_74838_a("entity." + entityName + ".name");
      }

      return itemName;
   }

   @SideOnly(Side.CLIENT)
   public void func_77624_a(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag) {
      String entityName = LOTREntities.getStringFromID(itemstack.func_77960_j());
      if (entityName != null) {
         list.add(entityName);
      }

   }

   @SideOnly(Side.CLIENT)
   public int func_82790_a(ItemStack itemstack, int i) {
      LOTREntities.SpawnEggInfo info = (LOTREntities.SpawnEggInfo)LOTREntities.spawnEggs.get(itemstack.func_77960_j());
      return info != null ? (i == 0 ? info.primaryColor : info.secondaryColor) : 16777215;
   }

   public boolean func_77648_a(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l, float f, float f1, float f2) {
      if (world.field_72995_K) {
         return true;
      } else {
         Block block = world.func_147439_a(i, j, k);
         i += Facing.field_71586_b[l];
         j += Facing.field_71587_c[l];
         k += Facing.field_71585_d[l];
         double d = 0.0D;
         if (l == 1 && block != null && block.func_149645_b() == 11) {
            d = 0.5D;
         }

         Entity entity = spawnCreature(world, itemstack.func_77960_j(), (double)i + 0.5D, (double)j + d, (double)k + 0.5D);
         if (entity != null) {
            if (entity instanceof EntityLiving && itemstack.func_82837_s()) {
               ((EntityLiving)entity).func_94058_c(itemstack.func_82833_r());
            }

            if (entity instanceof LOTREntityNPC) {
               ((LOTREntityNPC)entity).setPersistentAndTraderShouldRespawn();
               ((LOTREntityNPC)entity).onArtificalSpawn();
            }

            if (!entityplayer.field_71075_bZ.field_75098_d) {
               --itemstack.field_77994_a;
            }
         }

         return true;
      }
   }

   public static Entity spawnCreature(World world, int id, double d, double d1, double d2) {
      if (!LOTREntities.spawnEggs.containsKey(id)) {
         return null;
      } else {
         String entityName = LOTREntities.getStringFromID(id);
         Entity entity = EntityList.func_75620_a(entityName, world);
         if (entity instanceof EntityLiving) {
            EntityLiving entityliving = (EntityLiving)entity;
            entityliving.func_70012_b(d, d1, d2, MathHelper.func_76142_g(world.field_73012_v.nextFloat() * 360.0F), 0.0F);
            entityliving.field_70759_as = entityliving.field_70177_z;
            entityliving.field_70761_aq = entityliving.field_70177_z;
            entityliving.func_110161_a((IEntityLivingData)null);
            world.func_72838_d(entityliving);
            entityliving.func_70642_aH();
         }

         return entity;
      }
   }

   @SideOnly(Side.CLIENT)
   public boolean func_77623_v() {
      return true;
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_77618_c(int i, int j) {
      return Items.field_151063_bx.func_77618_c(i, j);
   }

   @SideOnly(Side.CLIENT)
   public void func_94581_a(IIconRegister iconregister) {
   }

   @SideOnly(Side.CLIENT)
   public void func_150895_a(Item item, CreativeTabs tab, List list) {
      Iterator it = LOTREntities.spawnEggs.values().iterator();

      while(it.hasNext()) {
         LOTREntities.SpawnEggInfo info = (LOTREntities.SpawnEggInfo)it.next();
         list.add(new ItemStack(item, 1, info.spawnedID));
      }

   }
}
