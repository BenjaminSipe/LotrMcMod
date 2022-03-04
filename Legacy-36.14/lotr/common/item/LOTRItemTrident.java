package lotr.common.item;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.entity.projectile.LOTRFishing;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import net.minecraftforge.common.FishingHooks.FishableCategory;

public class LOTRItemTrident extends LOTRItemPolearm {
   public LOTRItemTrident(LOTRMaterial material) {
      this(material.toToolMaterial());
   }

   public LOTRItemTrident(ToolMaterial material) {
      super(material);
   }

   public EnumAction func_77661_b(ItemStack itemstack) {
      return EnumAction.bow;
   }

   public int func_77626_a(ItemStack itemstack) {
      return 72000;
   }

   public ItemStack func_77659_a(ItemStack itemstack, World world, EntityPlayer entityplayer) {
      entityplayer.func_71008_a(itemstack, this.func_77626_a(itemstack));
      return itemstack;
   }

   public void func_77615_a(ItemStack itemstack, World world, EntityPlayer entityplayer, int useTick) {
      int usageTime = this.func_77626_a(itemstack) - useTick;
      if (usageTime > 5) {
         entityplayer.func_71038_i();
         MovingObjectPosition m = this.func_77621_a(world, entityplayer, true);
         if (m != null && m.field_72313_a == MovingObjectType.BLOCK) {
            int i = m.field_72311_b;
            int j = m.field_72312_c;
            int k = m.field_72309_d;
            if (this.canFishAt(world, i, j, k)) {
               double d;
               for(int l = 0; l < 20; ++l) {
                  double d = (double)((float)i + world.field_73012_v.nextFloat());
                  double d1 = (double)((float)j + world.field_73012_v.nextFloat());
                  d = (double)((float)k + world.field_73012_v.nextFloat());
                  String s = world.field_73012_v.nextBoolean() ? "bubble" : "splash";
                  world.func_72869_a(s, d, d1, d, 0.0D, (double)(world.field_73012_v.nextFloat() * 0.2F), 0.0D);
               }

               if (!world.field_72995_K) {
                  entityplayer.func_71020_j(0.06F);
                  if (world.field_73012_v.nextInt(3) == 0) {
                     world.func_72908_a((double)i + 0.5D, (double)j + 0.5D, (double)k + 0.5D, "random.splash", 0.5F, 1.0F + (world.field_73012_v.nextFloat() - world.field_73012_v.nextFloat()) * 0.4F);
                     itemstack.func_77972_a(1, entityplayer);
                     if (world.field_73012_v.nextInt(3) == 0) {
                        float chance = world.field_73012_v.nextFloat();
                        int luck = EnchantmentHelper.func_151386_g(entityplayer);
                        int speed = EnchantmentHelper.func_151387_h(entityplayer);
                        LOTRFishing.FishResult result = LOTRFishing.getFishResult(world.field_73012_v, chance, luck, speed, false);
                        EntityItem fish = new EntityItem(world, (double)i + 0.5D, (double)j + 0.5D, (double)k + 0.5D, result.fishedItem);
                        d = entityplayer.field_70165_t - fish.field_70165_t;
                        double d1 = entityplayer.field_70163_u - fish.field_70163_u;
                        double d2 = entityplayer.field_70161_v - fish.field_70161_v;
                        double dist = (double)MathHelper.func_76133_a(d * d + d1 * d1 + d2 * d2);
                        double motion = 0.1D;
                        fish.field_70159_w = d * motion;
                        fish.field_70181_x = d1 * motion + (double)MathHelper.func_76133_a(dist) * 0.08D;
                        fish.field_70179_y = d2 * motion;
                        world.func_72838_d(fish);
                        entityplayer.func_71064_a(result.category.stat, 1);
                        world.func_72838_d(new EntityXPOrb(world, fish.field_70165_t, fish.field_70163_u, fish.field_70161_v, world.field_73012_v.nextInt(3) + 1));
                        if (result.category == FishableCategory.FISH && this == LOTRMod.dunlendingTrident) {
                           LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useDunlendingTrident);
                        }
                     }
                  }
               }
            }
         }

      }
   }

   private boolean canFishAt(World world, int i, int j, int k) {
      double d = (double)i + 0.5D;
      double d1 = (double)j + 0.5D;
      double d2 = (double)k + 0.5D;
      double d3 = 0.125D;
      AxisAlignedBB boundingBox = AxisAlignedBB.func_72330_a(d - d3, d1 - d3, d2 - d3, d + d3, d1 + d3, d2 + d3);
      byte range = 5;

      for(int l = 0; l < range; ++l) {
         double d5 = boundingBox.field_72338_b + (boundingBox.field_72337_e - boundingBox.field_72338_b) * (double)(l + 0) / (double)range - d3 + d3;
         double d6 = boundingBox.field_72338_b + (boundingBox.field_72337_e - boundingBox.field_72338_b) * (double)(l + 1) / (double)range - d3 + d3;
         AxisAlignedBB aabb = AxisAlignedBB.func_72330_a(boundingBox.field_72340_a, d5, boundingBox.field_72339_c, boundingBox.field_72336_d, d6, boundingBox.field_72334_f);
         if (world.func_72830_b(aabb, Material.field_151586_h)) {
            return true;
         }
      }

      return false;
   }
}
