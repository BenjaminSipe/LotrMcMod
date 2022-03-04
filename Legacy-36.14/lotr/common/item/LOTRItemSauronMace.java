package lotr.common.item;

import java.util.List;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.fac.LOTRFaction;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketWeaponFX;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTRItemSauronMace extends LOTRItemHammer implements LOTRStoryItem {
   public LOTRItemSauronMace() {
      super(LOTRMaterial.MORDOR);
      this.func_77656_e(1500);
      this.func_77637_a(LOTRCreativeTabs.tabStory);
      this.lotrWeaponDamage = 8.0F;
   }

   public ItemStack func_77654_b(ItemStack itemstack, World world, EntityPlayer entityplayer) {
      itemstack.func_77972_a(2, entityplayer);
      return useSauronMace(itemstack, world, entityplayer);
   }

   public static ItemStack useSauronMace(ItemStack itemstack, World world, EntityLivingBase user) {
      user.func_71038_i();
      world.func_72956_a(user, "lotr:item.maceSauron", 2.0F, (field_77697_d.nextFloat() - field_77697_d.nextFloat()) * 0.2F + 1.0F);
      if (!world.field_72995_K) {
         List entities = world.func_72872_a(EntityLivingBase.class, user.field_70121_D.func_72314_b(12.0D, 8.0D, 12.0D));
         if (!entities.isEmpty()) {
            for(int i = 0; i < entities.size(); ++i) {
               EntityLivingBase entity = (EntityLivingBase)entities.get(i);
               if (entity != user) {
                  if (entity instanceof EntityLiving) {
                     EntityLiving entityliving = (EntityLiving)entity;
                     if (LOTRFaction.MORDOR.isGoodRelation(LOTRMod.getNPCFaction(entityliving))) {
                        continue;
                     }
                  }

                  if (entity instanceof EntityPlayer) {
                     if (user instanceof EntityPlayer) {
                        if (!MinecraftServer.func_71276_C().func_71219_W() || LOTRLevelData.getData((EntityPlayer)entity).getAlignment(LOTRFaction.MORDOR) > 0.0F) {
                           continue;
                        }
                     } else if (user instanceof EntityLiving && ((EntityLiving)user).func_70638_az() != entity && LOTRLevelData.getData((EntityPlayer)entity).getAlignment(LOTRFaction.MORDOR) > 0.0F) {
                        continue;
                     }
                  }

                  float strength = 6.0F - user.func_70032_d(entity) * 0.75F;
                  if (strength < 1.0F) {
                     strength = 1.0F;
                  }

                  if (user instanceof EntityPlayer) {
                     entity.func_70097_a(DamageSource.func_76365_a((EntityPlayer)user), 6.0F * strength);
                  } else {
                     entity.func_70097_a(DamageSource.func_76358_a(user), 6.0F * strength);
                  }

                  float knockback = strength;
                  if (strength > 4.0F) {
                     knockback = 4.0F;
                  }

                  entity.func_70024_g((double)(-MathHelper.func_76126_a(user.field_70177_z * 3.1415927F / 180.0F) * 0.7F * knockback), 0.2D + 0.12D * (double)knockback, (double)(MathHelper.func_76134_b(user.field_70177_z * 3.1415927F / 180.0F) * 0.7F * knockback));
               }
            }
         }

         LOTRPacketWeaponFX packet = new LOTRPacketWeaponFX(LOTRPacketWeaponFX.Type.MACE_SAURON, user);
         LOTRPacketHandler.networkWrapper.sendToAllAround(packet, LOTRPacketHandler.nearEntity(user, 64.0D));
      }

      return itemstack;
   }

   public int func_77626_a(ItemStack itemstack) {
      return 40;
   }

   public EnumAction func_77661_b(ItemStack itemstack) {
      return EnumAction.bow;
   }

   public ItemStack func_77659_a(ItemStack itemstack, World world, EntityPlayer entityplayer) {
      entityplayer.func_71008_a(itemstack, this.func_77626_a(itemstack));
      return itemstack;
   }
}
