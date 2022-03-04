package lotr.common.block;

import java.util.Random;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTRBlockOre extends Block {
   public LOTRBlockOre() {
      super(Material.field_151576_e);
      this.func_149647_a(LOTRCreativeTabs.tabBlock);
      this.func_149711_c(3.0F);
      this.func_149752_b(5.0F);
      this.func_149672_a(Block.field_149769_e);
   }

   public Item func_149650_a(int i, Random random, int j) {
      if (this == LOTRMod.oreNaurite) {
         return LOTRMod.nauriteGem;
      } else if (this == LOTRMod.oreQuendite) {
         return LOTRMod.quenditeCrystal;
      } else if (this == LOTRMod.oreGlowstone) {
         return Items.field_151114_aO;
      } else if (this == LOTRMod.oreGulduril) {
         return LOTRMod.guldurilCrystal;
      } else if (this == LOTRMod.oreSulfur) {
         return LOTRMod.sulfur;
      } else {
         return this == LOTRMod.oreSaltpeter ? LOTRMod.saltpeter : Item.func_150898_a(this);
      }
   }

   public int func_149745_a(Random random) {
      if (this == LOTRMod.oreNaurite) {
         return 1 + random.nextInt(2);
      } else if (this == LOTRMod.oreGlowstone) {
         return 2 + random.nextInt(4);
      } else {
         return this != LOTRMod.oreSulfur && this != LOTRMod.oreSaltpeter ? 1 : 1 + random.nextInt(2);
      }
   }

   public int func_149679_a(int i, Random random) {
      if (i > 0 && Item.func_150898_a(this) != this.func_149650_a(0, random, i)) {
         int factor = random.nextInt(i + 2) - 1;
         factor = Math.max(factor, 0);
         int drops = this.func_149745_a(random) * (factor + 1);
         if (this == LOTRMod.oreGlowstone) {
            drops = Math.min(drops, 8);
         }

         return drops;
      } else {
         return this.func_149745_a(random);
      }
   }

   public void func_149690_a(World world, int i, int j, int k, int meta, float f, int fortune) {
      super.func_149690_a(world, i, j, k, meta, f, fortune);
      if (this.func_149650_a(meta, world.field_73012_v, fortune) != Item.func_150898_a(this)) {
         int amountXp = 0;
         if (this == LOTRMod.oreNaurite) {
            amountXp = MathHelper.func_76136_a(world.field_73012_v, 0, 2);
         }

         if (this == LOTRMod.oreQuendite) {
            amountXp = MathHelper.func_76136_a(world.field_73012_v, 2, 5);
         }

         if (this == LOTRMod.oreGlowstone) {
            amountXp = MathHelper.func_76136_a(world.field_73012_v, 2, 4);
         }

         if (this == LOTRMod.oreGulduril) {
            amountXp = MathHelper.func_76136_a(world.field_73012_v, 2, 5);
         }

         if (this == LOTRMod.oreSulfur || this == LOTRMod.oreSaltpeter) {
            amountXp = MathHelper.func_76136_a(world.field_73012_v, 0, 2);
         }

         this.func_149657_c(world, i, j, k, amountXp);
      }

   }

   public void func_149636_a(World world, EntityPlayer entityplayer, int i, int j, int k, int l) {
      super.func_149636_a(world, entityplayer, i, j, k, l);
      if (!world.field_72995_K) {
         if (this == LOTRMod.oreMithril) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.mineMithril);
         }

         if (this == LOTRMod.oreQuendite) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.mineQuendite);
         }

         if (this == LOTRMod.oreGlowstone) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.mineGlowstone);
         }

         if (this == LOTRMod.oreNaurite) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.mineNaurite);
         }

         if (this == LOTRMod.oreGulduril) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.mineGulduril);
         }
      }

   }
}
