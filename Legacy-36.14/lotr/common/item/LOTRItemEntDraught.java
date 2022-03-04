package lotr.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.List;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRLevelData;
import lotr.common.block.LOTRBlockSaplingBase;
import lotr.common.entity.npc.LOTREntityHuorn;
import lotr.common.entity.npc.LOTREntityTree;
import lotr.common.entity.npc.LOTRHiredNPCInfo;
import lotr.common.fac.LOTRAlignmentValues;
import lotr.common.fac.LOTRFaction;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class LOTRItemEntDraught extends Item {
   @SideOnly(Side.CLIENT)
   private IIcon[] draughtIcons;
   public static LOTRItemEntDraught.DraughtInfo[] draughtTypes;

   public LOTRItemEntDraught() {
      this.func_77627_a(true);
      this.func_77656_e(0);
      this.func_77625_d(1);
      this.func_77637_a(LOTRCreativeTabs.tabFood);
   }

   private LOTRItemEntDraught.DraughtInfo getDraughtInfo(ItemStack itemstack) {
      int i = itemstack.func_77960_j();
      if (i >= draughtTypes.length) {
         i = 0;
      }

      return draughtTypes[i];
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_77617_a(int i) {
      if (i >= this.draughtIcons.length) {
         i = 0;
      }

      return this.draughtIcons[i];
   }

   public String func_77667_c(ItemStack itemstack) {
      return super.func_77658_a() + "." + itemstack.func_77960_j();
   }

   @SideOnly(Side.CLIENT)
   public void func_94581_a(IIconRegister iconregister) {
      this.draughtIcons = new IIcon[draughtTypes.length];

      for(int i = 0; i < draughtTypes.length; ++i) {
         this.draughtIcons[i] = iconregister.func_94245_a(this.func_111208_A() + "_" + draughtTypes[i].name);
      }

   }

   @SideOnly(Side.CLIENT)
   public void func_150895_a(Item item, CreativeTabs tab, List list) {
      for(int i = 0; i < draughtTypes.length; ++i) {
         list.add(new ItemStack(item, 1, i));
      }

   }

   @SideOnly(Side.CLIENT)
   public void func_77624_a(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag) {
      LOTRItemMug.addPotionEffectsToTooltip(itemstack, entityplayer, list, flag, this.getDraughtInfo(itemstack).effects);
   }

   public int func_77626_a(ItemStack itemstack) {
      return 32;
   }

   public EnumAction func_77661_b(ItemStack itemstack) {
      return EnumAction.drink;
   }

   public ItemStack func_77659_a(ItemStack itemstack, World world, EntityPlayer entityplayer) {
      if (this.canPlayerDrink(entityplayer, itemstack)) {
         entityplayer.func_71008_a(itemstack, this.func_77626_a(itemstack));
      }

      return itemstack;
   }

   public boolean canPlayerDrink(EntityPlayer entityplayer, ItemStack itemstack) {
      return !this.getDraughtInfo(itemstack).effects.isEmpty() || entityplayer.func_71043_e(true);
   }

   public ItemStack func_77654_b(ItemStack itemstack, World world, EntityPlayer entityplayer) {
      if (LOTRLevelData.getData(entityplayer).getAlignment(LOTRFaction.FANGORN) < 0.0F) {
         if (!world.field_72995_K) {
            entityplayer.func_70690_d(new PotionEffect(Potion.field_76436_u.field_76415_H, 100));
         }
      } else {
         if (entityplayer.func_71043_e(false)) {
            entityplayer.func_71024_bL().func_75122_a(this.getDraughtInfo(itemstack).heal, this.getDraughtInfo(itemstack).saturation);
         }

         if (!world.field_72995_K) {
            List effects = this.getDraughtInfo(itemstack).effects;

            for(int i = 0; i < effects.size(); ++i) {
               PotionEffect effect = (PotionEffect)effects.get(i);
               entityplayer.func_70690_d(new PotionEffect(effect.func_76456_a(), effect.func_76459_b()));
            }
         }

         if (!world.field_72995_K && entityplayer.func_71045_bC() == itemstack) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.drinkEntDraught);
         }
      }

      return !entityplayer.field_71075_bZ.field_75098_d ? new ItemStack(Items.field_151054_z) : itemstack;
   }

   public boolean func_77648_a(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float f, float f1, float f2) {
      if (this.getDraughtInfo(itemstack).name.equals("gold")) {
         if (LOTRLevelData.getData(entityplayer).getAlignment(LOTRFaction.FANGORN) < 500.0F) {
            if (!world.field_72995_K) {
               LOTRAlignmentValues.notifyAlignmentNotHighEnough(entityplayer, 500.0F, LOTRFaction.FANGORN);
            }

            return false;
         }

         Block block = world.func_147439_a(i, j, k);
         int meta = world.func_72805_g(i, j, k);
         if (block instanceof BlockSapling || block instanceof LOTRBlockSaplingBase) {
            meta &= 7;

            for(int huornType = 0; huornType < LOTREntityTree.TYPES.length; ++huornType) {
               if (block == LOTREntityTree.SAPLING_BLOCKS[huornType] && meta == LOTREntityTree.SAPLING_META[huornType]) {
                  LOTREntityHuorn huorn = new LOTREntityHuorn(world);
                  huorn.setTreeType(huornType);
                  huorn.isNPCPersistent = true;
                  huorn.liftSpawnRestrictions = true;
                  huorn.func_70012_b((double)i + 0.5D, (double)j, (double)k + 0.5D, 0.0F, 0.0F);
                  if (huorn.func_70601_bi()) {
                     if (!world.field_72995_K) {
                        world.func_72838_d(huorn);
                        huorn.initCreatureForHire((IEntityLivingData)null);
                        huorn.hiredNPCInfo.isActive = true;
                        huorn.hiredNPCInfo.alignmentRequiredToCommand = 500.0F;
                        huorn.hiredNPCInfo.setHiringPlayer(entityplayer);
                        huorn.hiredNPCInfo.setTask(LOTRHiredNPCInfo.Task.WARRIOR);
                        LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.summonHuorn);
                     }

                     for(int l = 0; l < 24; ++l) {
                        double d = (double)i + 0.5D - world.field_73012_v.nextDouble() * 2.0D + world.field_73012_v.nextDouble() * 2.0D;
                        double d1 = (double)j + world.field_73012_v.nextDouble() * 4.0D;
                        double d2 = (double)k + 0.5D - world.field_73012_v.nextDouble() * 2.0D + world.field_73012_v.nextDouble() * 2.0D;
                        world.func_72869_a("happyVillager", d, d1, d2, 0.0D, 0.0D, 0.0D);
                     }

                     if (!entityplayer.field_71075_bZ.field_75098_d) {
                        entityplayer.field_71071_by.func_70299_a(entityplayer.field_71071_by.field_70461_c, new ItemStack(Items.field_151054_z));
                     }

                     return true;
                  }
               }
            }
         }
      }

      return false;
   }

   static {
      draughtTypes = new LOTRItemEntDraught.DraughtInfo[]{(new LOTRItemEntDraught.DraughtInfo("green", 0, 0.0F)).addEffect(Potion.field_76424_c.field_76415_H, 120).addEffect(Potion.field_76422_e.field_76415_H, 120).addEffect(Potion.field_76420_g.field_76415_H, 120), new LOTRItemEntDraught.DraughtInfo("brown", 20, 3.0F), new LOTRItemEntDraught.DraughtInfo("gold", 0, 0.0F), (new LOTRItemEntDraught.DraughtInfo("yellow", 0, 0.0F)).addEffect(Potion.field_76428_l.field_76415_H, 60), (new LOTRItemEntDraught.DraughtInfo("red", 0, 0.0F)).addEffect(Potion.field_76426_n.field_76415_H, 180), (new LOTRItemEntDraught.DraughtInfo("silver", 0, 0.0F)).addEffect(Potion.field_76439_r.field_76415_H, 180), (new LOTRItemEntDraught.DraughtInfo("blue", 0, 0.0F)).addEffect(Potion.field_76427_o.field_76415_H, 150)};
   }

   public static class DraughtInfo {
      public String name;
      public int heal;
      public float saturation;
      public List effects = new ArrayList();

      public DraughtInfo(String s, int i, float f) {
         this.name = s;
         this.heal = i;
         this.saturation = f;
      }

      public LOTRItemEntDraught.DraughtInfo addEffect(int i, int j) {
         this.effects.add(new PotionEffect(i, j * 20));
         return this;
      }
   }
}
