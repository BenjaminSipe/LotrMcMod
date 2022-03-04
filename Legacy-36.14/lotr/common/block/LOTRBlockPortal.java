package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import lotr.common.LOTRConfig;
import lotr.common.LOTRDimension;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.fac.LOTRFaction;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;

public abstract class LOTRBlockPortal extends BlockContainer {
   private LOTRFaction[] portalFactions;
   private Class teleporterClass;

   public LOTRBlockPortal(LOTRFaction[] factions, Class c) {
      super(Material.field_151567_E);
      this.portalFactions = factions;
      this.teleporterClass = c;
   }

   public void func_149719_a(IBlockAccess world, int i, int j, int k) {
      float f = 0.0625F;
      this.func_149676_a(0.0F, 0.0F, 0.0F, 1.0F, f, 1.0F);
   }

   @SideOnly(Side.CLIENT)
   public boolean func_149646_a(IBlockAccess world, int i, int j, int k, int side) {
      return side != 0 ? false : super.func_149646_a(world, i, j, k, side);
   }

   public void func_149743_a(World world, int i, int j, int k, AxisAlignedBB aabb, List list, Entity entity) {
   }

   public abstract void setPlayerInPortal(EntityPlayer var1);

   public void func_149670_a(World world, int i, int j, int k, Entity entity) {
      if (LOTRConfig.enablePortals) {
         if (this.canUsePortal(entity)) {
            if (entity.field_70154_o == null && entity.field_70153_n == null) {
               if (entity instanceof EntityPlayer) {
                  this.setPlayerInPortal((EntityPlayer)entity);
               } else {
                  this.transferEntity(entity, world);
               }
            }
         } else if (!world.field_72995_K) {
            entity.func_70015_d(4);
            entity.func_70097_a(DamageSource.field_76372_a, 2.0F);
            world.func_72956_a(entity, "random.fizz", 0.5F, 1.5F + (world.field_73012_v.nextFloat() - world.field_73012_v.nextFloat()) * 0.5F);
         }

      }
   }

   private boolean canUsePortal(Entity entity) {
      int var4;
      if (entity instanceof EntityPlayer) {
         EntityPlayer player = (EntityPlayer)entity;
         if (player.field_71075_bZ.field_75098_d) {
            return true;
         }

         LOTRFaction[] var3 = this.portalFactions;
         var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            LOTRFaction faction = var3[var5];
            if (LOTRLevelData.getData(player).getAlignment(faction) >= 1.0F) {
               return true;
            }
         }
      } else {
         LOTRFaction[] var7 = this.portalFactions;
         int var8 = var7.length;

         for(var4 = 0; var4 < var8; ++var4) {
            LOTRFaction faction = var7[var4];
            if (!LOTRMod.getNPCFaction(entity).isBadRelation(faction) && entity.field_70154_o == null && entity.field_70153_n == null && entity.field_71088_bW == 0) {
               return true;
            }
         }
      }

      return false;
   }

   public Teleporter getPortalTeleporter(WorldServer world) {
      Iterator var2 = world.customTeleporters.iterator();

      Object obj;
      do {
         if (!var2.hasNext()) {
            Teleporter teleporter = null;

            try {
               teleporter = (Teleporter)this.teleporterClass.getConstructor(WorldServer.class).newInstance(world);
            } catch (Exception var4) {
               var4.printStackTrace();
            }

            world.customTeleporters.add(teleporter);
            return teleporter;
         }

         obj = var2.next();
      } while(!this.teleporterClass.isInstance(obj));

      return (Teleporter)obj;
   }

   private void transferEntity(Entity entity, World world) {
      if (!world.field_72995_K) {
         int dimension = 0;
         if (entity.field_71093_bK == 0) {
            dimension = LOTRDimension.MIDDLE_EARTH.dimensionID;
         } else if (entity.field_71093_bK == LOTRDimension.MIDDLE_EARTH.dimensionID) {
            dimension = 0;
         }

         LOTRMod.transferEntityToDimension(entity, dimension, this.getPortalTeleporter(DimensionManager.getWorld(dimension)));
      }

   }

   public boolean func_149662_c() {
      return false;
   }

   public boolean func_149686_d() {
      return false;
   }

   public int func_149745_a(Random par1Random) {
      return 0;
   }

   public int func_149645_b() {
      return -1;
   }

   @SideOnly(Side.CLIENT)
   public void func_149734_b(World world, int i, int j, int k, Random random) {
      if (random.nextInt(100) == 0) {
         world.func_72980_b((double)i + 0.5D, (double)j + 0.5D, (double)k + 0.5D, "portal.portal", 0.5F, random.nextFloat() * 0.4F + 0.8F, false);
      }

   }

   public void func_149726_b(World world, int i, int j, int k) {
      if (world.field_73011_w.field_76574_g != 0 && world.field_73011_w.field_76574_g != LOTRDimension.MIDDLE_EARTH.dimensionID) {
         world.func_147468_f(i, j, k);
      }

   }

   public abstract boolean isValidPortalLocation(World var1, int var2, int var3, int var4, boolean var5);

   @SideOnly(Side.CLIENT)
   public Item func_149694_d(World world, int i, int j, int k) {
      return Item.func_150899_d(0);
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      return Blocks.field_150427_aO.func_149691_a(i, j);
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
   }
}
