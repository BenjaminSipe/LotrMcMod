package lotr.common.entity.projectile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lotr.common.LOTRMod;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemFishFood.FishType;
import net.minecraft.util.MathHelper;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.WeightedRandom.Item;
import net.minecraftforge.common.FishingHooks.FishableCategory;

public class LOTRFishing {
   private static List fish = new ArrayList();
   private static List junk = new ArrayList();
   private static List treasure = new ArrayList();

   public static LOTRFishing.FishResult getFishResult(Random rand, float chance, int luck, int speed, boolean allowJunkTreasure) {
      float junkChance = 0.1F - (float)luck * 0.025F - (float)speed * 0.01F;
      float treasureChance = 0.2F + (float)luck * 0.01F - (float)speed * 0.01F;
      junkChance = MathHelper.func_76131_a(junkChance, 0.0F, 1.0F);
      treasureChance = MathHelper.func_76131_a(treasureChance, 0.0F, 1.0F);
      ItemStack result;
      if (allowJunkTreasure) {
         if (chance < junkChance) {
            result = ((LOTRFishing.FishingItem)WeightedRandom.func_76271_a(rand, junk)).getRandomResult(rand);
            return new LOTRFishing.FishResult(FishableCategory.JUNK, result);
         }

         chance -= junkChance;
         if (chance < treasureChance) {
            result = ((LOTRFishing.FishingItem)WeightedRandom.func_76271_a(rand, treasure)).getRandomResult(rand);
            return new LOTRFishing.FishResult(FishableCategory.TREASURE, result);
         }
      }

      result = ((LOTRFishing.FishingItem)WeightedRandom.func_76271_a(rand, fish)).getRandomResult(rand);
      return new LOTRFishing.FishResult(FishableCategory.FISH, result);
   }

   static {
      fish.add(new LOTRFishing.FishingItem(new ItemStack(Items.field_151115_aP, 1, FishType.COD.func_150976_a()), 60));
      fish.add(new LOTRFishing.FishingItem(new ItemStack(Items.field_151115_aP, 1, FishType.SALMON.func_150976_a()), 25));
      fish.add(new LOTRFishing.FishingItem(new ItemStack(Items.field_151115_aP, 1, FishType.CLOWNFISH.func_150976_a()), 2));
      fish.add(new LOTRFishing.FishingItem(new ItemStack(Items.field_151115_aP, 1, FishType.PUFFERFISH.func_150976_a()), 13));
      junk.add((new LOTRFishing.FishingItem(new ItemStack(Items.field_151112_aM), 5)).setMaxDurability(0.1F));
      junk.add((new LOTRFishing.FishingItem(new ItemStack(Items.field_151041_m), 2)).setMaxDurability(0.5F));
      junk.add((new LOTRFishing.FishingItem(new ItemStack(Items.field_151053_p), 2)).setMaxDurability(0.5F));
      junk.add((new LOTRFishing.FishingItem(new ItemStack(Items.field_151039_o), 2)).setMaxDurability(0.5F));
      junk.add((new LOTRFishing.FishingItem(new ItemStack(Items.field_151038_n), 2)).setMaxDurability(0.5F));
      junk.add((new LOTRFishing.FishingItem(new ItemStack(Items.field_151017_I), 2)).setMaxDurability(0.5F));
      junk.add(new LOTRFishing.FishingItem(new ItemStack(LOTRMod.leatherHat), 10));
      junk.add((new LOTRFishing.FishingItem(new ItemStack(Items.field_151024_Q), 5)).setMaxDurability(0.5F));
      junk.add((new LOTRFishing.FishingItem(new ItemStack(Items.field_151021_T), 5)).setMaxDurability(0.5F));
      junk.add((new LOTRFishing.FishingItem(new ItemStack(LOTRMod.helmetBone), 2)).setMaxDurability(0.5F));
      junk.add((new LOTRFishing.FishingItem(new ItemStack(LOTRMod.bootsBone), 2)).setMaxDurability(0.5F));
      junk.add(new LOTRFishing.FishingItem(new ItemStack(Items.field_151144_bL, 1, 0), 5));
      junk.add(new LOTRFishing.FishingItem(new ItemStack(Items.field_151103_aS), 20));
      junk.add(new LOTRFishing.FishingItem(new ItemStack(LOTRMod.orcBone), 10));
      junk.add(new LOTRFishing.FishingItem(new ItemStack(LOTRMod.elfBone), 2));
      junk.add(new LOTRFishing.FishingItem(new ItemStack(LOTRMod.dwarfBone), 2));
      junk.add(new LOTRFishing.FishingItem(new ItemStack(LOTRMod.hobbitBone), 1));
      junk.add(new LOTRFishing.FishingItem(new ItemStack(LOTRMod.rottenLog, 1, 0), 10));
      junk.add(new LOTRFishing.FishingItem(new ItemStack(Items.field_151116_aA), 10));
      junk.add(new LOTRFishing.FishingItem(new ItemStack(Items.field_151007_F), 10));
      junk.add(new LOTRFishing.FishingItem(new ItemStack(Items.field_151054_z), 10));
      junk.add(new LOTRFishing.FishingItem(new ItemStack(LOTRMod.mug), 10));
      junk.add(new LOTRFishing.FishingItem(new ItemStack(Items.field_151122_aG), 5));
      junk.add(new LOTRFishing.FishingItem(new ItemStack(Items.field_151055_y), 10));
      junk.add(new LOTRFishing.FishingItem(new ItemStack(Items.field_151008_G), 10));
      junk.add(new LOTRFishing.FishingItem(new ItemStack(Items.field_151100_aR, 1, 0), 5));
      junk.add(new LOTRFishing.FishingItem(new ItemStack(Items.field_151078_bh), 5));
      junk.add(new LOTRFishing.FishingItem(new ItemStack(LOTRMod.saltedFlesh), 5));
      junk.add(new LOTRFishing.FishingItem(new ItemStack(LOTRMod.maggotyBread), 5));
      junk.add(new LOTRFishing.FishingItem(new ItemStack(LOTRMod.manFlesh), 5));
      junk.add(new LOTRFishing.FishingItem(new ItemStack(Blocks.field_150392_bi), 15));
      treasure.add(new LOTRFishing.FishingItem(new ItemStack(LOTRMod.pearl), 200));
      treasure.add((new LOTRFishing.FishingItem(new ItemStack(Items.field_151031_f), 20)).setMaxDurability(0.75F));
      treasure.add((new LOTRFishing.FishingItem(new ItemStack(Items.field_151112_aM), 20)).setMaxDurability(0.75F));
      treasure.add((new LOTRFishing.FishingItem(new ItemStack(LOTRMod.daggerIron), 20)).setMaxDurability(0.75F));
      treasure.add((new LOTRFishing.FishingItem(new ItemStack(LOTRMod.daggerBronze), 20)).setMaxDurability(0.75F));
      treasure.add(new LOTRFishing.FishingItem(new ItemStack(LOTRMod.silverCoin, 1, 0), 100));
      treasure.add(new LOTRFishing.FishingItem(new ItemStack(LOTRMod.silverCoin, 1, 1), 10));
      treasure.add(new LOTRFishing.FishingItem(new ItemStack(LOTRMod.silverCoin, 1, 2), 1));
      treasure.add(new LOTRFishing.FishingItem(new ItemStack(LOTRMod.pouch, 1, 0), 20));
      treasure.add(new LOTRFishing.FishingItem(new ItemStack(LOTRMod.pouch, 1, 1), 10));
      treasure.add(new LOTRFishing.FishingItem(new ItemStack(LOTRMod.pouch, 1, 2), 5));
      treasure.add(new LOTRFishing.FishingItem(new ItemStack(Items.field_151042_j), 20));
      treasure.add(new LOTRFishing.FishingItem(new ItemStack(LOTRMod.ironNugget), 10));
      treasure.add(new LOTRFishing.FishingItem(new ItemStack(LOTRMod.bronze), 10));
      treasure.add(new LOTRFishing.FishingItem(new ItemStack(LOTRMod.copper), 10));
      treasure.add(new LOTRFishing.FishingItem(new ItemStack(LOTRMod.tin), 10));
      treasure.add(new LOTRFishing.FishingItem(new ItemStack(Items.field_151074_bl), 50));
      treasure.add(new LOTRFishing.FishingItem(new ItemStack(Items.field_151043_k), 5));
      treasure.add(new LOTRFishing.FishingItem(new ItemStack(LOTRMod.silverNugget), 50));
      treasure.add(new LOTRFishing.FishingItem(new ItemStack(LOTRMod.silver), 5));
      treasure.add(new LOTRFishing.FishingItem(new ItemStack(LOTRMod.mithrilNugget), 5));
      treasure.add(new LOTRFishing.FishingItem(new ItemStack(LOTRMod.silverRing), 10));
      treasure.add(new LOTRFishing.FishingItem(new ItemStack(LOTRMod.goldRing), 5));
      treasure.add(new LOTRFishing.FishingItem(new ItemStack(LOTRMod.mithrilRing), 1));
   }

   public static class FishResult {
      public final FishableCategory category;
      public final ItemStack fishedItem;

      public FishResult(FishableCategory c, ItemStack item) {
         this.category = c;
         this.fishedItem = item;
      }
   }

   private static class FishingItem extends Item {
      private final ItemStack theItem;
      private float maxDurability;

      private FishingItem(ItemStack item, int weight) {
         super(weight);
         this.maxDurability = 0.0F;
         this.theItem = item;
      }

      public LOTRFishing.FishingItem setMaxDurability(float f) {
         this.maxDurability = f;
         return this;
      }

      public ItemStack getRandomResult(Random rand) {
         ItemStack result = this.theItem.func_77946_l();
         if (this.maxDurability > 0.0F) {
            float damageF = 1.0F - rand.nextFloat() * this.maxDurability;
            int damage = (int)(damageF * (float)result.func_77958_k());
            damage = Math.min(damage, result.func_77958_k());
            damage = Math.max(damage, 1);
            result.func_77964_b(damage);
         }

         return result;
      }

      // $FF: synthetic method
      FishingItem(ItemStack x0, int x1, Object x2) {
         this(x0, x1);
      }
   }
}
