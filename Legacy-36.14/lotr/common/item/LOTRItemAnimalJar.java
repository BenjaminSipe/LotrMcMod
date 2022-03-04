package lotr.common.item;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import lotr.common.block.LOTRBlockAnimalJar;
import lotr.common.entity.LOTREntities;
import lotr.common.entity.animal.LOTREntityButterfly;
import lotr.common.tileentity.LOTRTileEntityAnimalJar;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class LOTRItemAnimalJar extends LOTRItemBlockMetadata {
   public LOTRItemAnimalJar(Block block) {
      super(block);
      this.func_77625_d(1);
   }

   public static NBTTagCompound getEntityData(ItemStack itemstack) {
      if (itemstack.func_77942_o()) {
         NBTTagCompound nbt;
         if (itemstack.func_77978_p().func_74764_b("LOTRButterfly")) {
            nbt = itemstack.func_77978_p().func_74775_l("LOTRButterfly");
            if (!nbt.func_82582_d()) {
               nbt.func_74778_a("id", LOTREntities.getStringFromClass(LOTREntityButterfly.class));
               setEntityData(itemstack, (NBTTagCompound)nbt.func_74737_b());
            }

            itemstack.func_77978_p().func_82580_o("LOTRButterfly");
         }

         if (itemstack.func_77978_p().func_74764_b("JarEntity")) {
            nbt = itemstack.func_77978_p().func_74775_l("JarEntity");
            if (!nbt.func_82582_d()) {
               return nbt;
            }
         }
      }

      return null;
   }

   public static void setEntityData(ItemStack itemstack, NBTTagCompound nbt) {
      if (itemstack.func_77978_p() == null) {
         itemstack.func_77982_d(new NBTTagCompound());
      }

      if (nbt == null) {
         itemstack.func_77978_p().func_82580_o("JarEntity");
      } else {
         itemstack.func_77978_p().func_74782_a("JarEntity", nbt);
      }

   }

   public static Entity getItemJarEntity(ItemStack itemstack, World world) {
      NBTTagCompound nbt = getEntityData(itemstack);
      if (nbt != null) {
         Entity jarEntity = EntityList.func_75615_a(nbt, world);
         return jarEntity;
      } else {
         return null;
      }
   }

   public boolean placeBlockAt(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float f, float f1, float f2, int metadata) {
      if (super.placeBlockAt(itemstack, entityplayer, world, i, j, k, side, f, f1, f2, metadata)) {
         TileEntity tileentity = world.func_147438_o(i, j, k);
         if (tileentity instanceof LOTRTileEntityAnimalJar) {
            LOTRTileEntityAnimalJar jar = (LOTRTileEntityAnimalJar)tileentity;
            jar.setEntityData(getEntityData(itemstack));
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean func_111207_a(ItemStack itemstack, EntityPlayer entityplayer, EntityLivingBase entity) {
      itemstack = entityplayer.func_71045_bC();
      World world = entityplayer.field_70170_p;
      LOTRBlockAnimalJar jarBlock = (LOTRBlockAnimalJar)this.field_150939_a;
      if (jarBlock.canCapture(entity) && getEntityData(itemstack) == null) {
         if (!world.field_72995_K) {
            NBTTagCompound nbt = new NBTTagCompound();
            if (entity.func_70039_c(nbt)) {
               setEntityData(itemstack, nbt);
               entity.func_85030_a("random.pop", 0.5F, 0.5F + world.field_73012_v.nextFloat() * 0.5F);
               entity.func_70106_y();
               if (entity instanceof LOTREntityButterfly) {
                  LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.catchButterfly);
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public ItemStack func_77659_a(ItemStack itemstack, World world, EntityPlayer entityplayer) {
      if (!world.field_72995_K) {
         Entity jarEntity = getItemJarEntity(itemstack, world);
         if (jarEntity != null) {
            double x = entityplayer.field_70165_t;
            double y = entityplayer.field_70121_D.field_72338_b + (double)entityplayer.func_70047_e();
            double z = entityplayer.field_70161_v;
            Vec3 look = entityplayer.func_70040_Z();
            float length = 2.0F;
            x += look.field_72450_a * (double)length;
            y += look.field_72448_b * (double)length;
            z += look.field_72449_c * (double)length;
            jarEntity.func_70012_b(x, y, z, world.field_73012_v.nextFloat(), 0.0F);
            world.func_72838_d(jarEntity);
            jarEntity.func_85030_a("random.pop", 0.5F, 0.5F + world.field_73012_v.nextFloat() * 0.5F);
            setEntityData(itemstack, (NBTTagCompound)null);
         }
      }

      return itemstack;
   }
}
