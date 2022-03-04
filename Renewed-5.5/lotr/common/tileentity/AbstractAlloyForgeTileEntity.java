package lotr.common.tileentity;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import lotr.common.init.LOTRTags;
import lotr.common.inv.SlotAndCount;
import lotr.common.recipe.AbstractAlloyForgeRecipe;
import lotr.common.util.LOTRUtil;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IRecipeHelperPopulator;
import net.minecraft.inventory.IRecipeHolder;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import org.apache.commons.lang3.ArrayUtils;

public abstract class AbstractAlloyForgeTileEntity extends LockableTileEntity implements ISidedInventory, IRecipeHolder, IRecipeHelperPopulator, ITickableTileEntity {
   public static final int RECIPE_FUNCTIONALITY_VERSION_FOR_JEI = 3;
   private int[] SLOTS_INGREDIENT = new int[]{0, 1, 2, 3};
   private int[] SLOTS_ALLOY = new int[]{4, 5, 6, 7};
   private int[] SLOTS_OUTPUT = new int[]{8, 9, 10, 11};
   private int SLOT_FUEL = 12;
   protected NonNullList inventory;
   private int burnTime;
   private int burnTimeTotal;
   private int cookTime;
   private int cookTimeTotal;
   protected final IIntArray forgeData;
   private final Map usedRecipes;
   protected final IRecipeType[] recipeTypes;
   protected final IRecipeType[] alloyRecipeTypes;
   private LazyOptional[] handlers;

   protected AbstractAlloyForgeTileEntity(TileEntityType type, IRecipeType[] recipes, IRecipeType[] alloyRecipes) {
      super(type);
      this.inventory = NonNullList.func_191197_a(this.SLOTS_INGREDIENT.length + this.SLOTS_ALLOY.length + this.SLOTS_OUTPUT.length + 1, ItemStack.field_190927_a);
      this.forgeData = new IIntArray() {
         public int func_221476_a(int index) {
            switch(index) {
            case 0:
               return AbstractAlloyForgeTileEntity.this.burnTime;
            case 1:
               return AbstractAlloyForgeTileEntity.this.burnTimeTotal;
            case 2:
               return AbstractAlloyForgeTileEntity.this.cookTime;
            case 3:
               return AbstractAlloyForgeTileEntity.this.cookTimeTotal;
            default:
               return 0;
            }
         }

         public void func_221477_a(int index, int value) {
            switch(index) {
            case 0:
               AbstractAlloyForgeTileEntity.this.burnTime = value;
               break;
            case 1:
               AbstractAlloyForgeTileEntity.this.burnTimeTotal = value;
               break;
            case 2:
               AbstractAlloyForgeTileEntity.this.cookTime = value;
               break;
            case 3:
               AbstractAlloyForgeTileEntity.this.cookTimeTotal = value;
            }

         }

         public int func_221478_a() {
            return 4;
         }
      };
      this.usedRecipes = Maps.newHashMap();
      this.handlers = SidedInvWrapper.create(this, new Direction[]{Direction.UP, Direction.DOWN, Direction.NORTH});
      this.recipeTypes = recipes;
      this.alloyRecipeTypes = alloyRecipes;
   }

   private boolean isBurning() {
      return this.burnTime > 0;
   }

   public void func_230337_a_(BlockState state, CompoundNBT nbt) {
      super.func_230337_a_(state, nbt);
      this.inventory = NonNullList.func_191197_a(this.func_70302_i_(), ItemStack.field_190927_a);
      ItemStackHelper.func_191283_b(nbt, this.inventory);
      this.burnTime = nbt.func_74762_e("BurnTime");
      this.cookTime = nbt.func_74762_e("CookTime");
      this.cookTimeTotal = nbt.func_74762_e("CookTimeTotal");
      this.burnTimeTotal = this.getBurnTime((ItemStack)this.inventory.get(this.SLOT_FUEL));
      int rSize = nbt.func_74765_d("RecipesUsedSize");

      for(int ri = 0; ri < rSize; ++ri) {
         ResourceLocation res = new ResourceLocation(nbt.func_74779_i("RecipeLocation" + ri));
         int count = nbt.func_74762_e("RecipeAmount" + ri);
         this.usedRecipes.put(res, count);
      }

   }

   public CompoundNBT func_189515_b(CompoundNBT nbt) {
      super.func_189515_b(nbt);
      nbt.func_74768_a("BurnTime", this.burnTime);
      nbt.func_74768_a("CookTime", this.cookTime);
      nbt.func_74768_a("CookTimeTotal", this.cookTimeTotal);
      ItemStackHelper.func_191282_a(nbt, this.inventory);
      nbt.func_74777_a("RecipesUsedSize", (short)this.usedRecipes.size());
      int ri = 0;

      for(Iterator var3 = this.usedRecipes.entrySet().iterator(); var3.hasNext(); ++ri) {
         Entry entry = (Entry)var3.next();
         nbt.func_74778_a("RecipeLocation" + ri, ((ResourceLocation)entry.getKey()).toString());
         nbt.func_74768_a("RecipeAmount" + ri, (Integer)entry.getValue());
      }

      return nbt;
   }

   public void func_73660_a() {
      boolean wasBurning = this.isBurning();
      boolean needUpdate = false;
      if (this.isBurning()) {
         --this.burnTime;
      }

      if (!this.field_145850_b.field_72995_K) {
         ItemStack fuelItem = (ItemStack)this.inventory.get(this.SLOT_FUEL);
         boolean anyIngredients = false;
         int[] var5 = this.SLOTS_INGREDIENT;
         int var6 = var5.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            int i = var5[var7];
            if (!((ItemStack)this.inventory.get(i)).func_190926_b()) {
               anyIngredients = true;
               break;
            }
         }

         if (!this.isBurning() && (fuelItem.func_190926_b() || !anyIngredients)) {
            if (!this.isBurning() && this.cookTime > 0) {
               this.cookTime = MathHelper.func_76125_a(this.cookTime - 2, 0, this.cookTimeTotal);
            }
         } else {
            if (!this.isBurning() && this.canDoSmelting()) {
               this.burnTime = this.getBurnTime(fuelItem);
               this.burnTimeTotal = this.burnTime;
               if (this.isBurning()) {
                  needUpdate = true;
                  if (fuelItem.hasContainerItem()) {
                     this.inventory.set(this.SLOT_FUEL, fuelItem.getContainerItem());
                  } else if (!fuelItem.func_190926_b()) {
                     Item item = fuelItem.func_77973_b();
                     fuelItem.func_190918_g(1);
                     if (fuelItem.func_190926_b()) {
                        this.inventory.set(this.SLOT_FUEL, fuelItem.getContainerItem());
                     }
                  }
               }
            }

            if (this.isBurning() && this.canDoSmelting()) {
               ++this.cookTime;
               if (this.cookTime == this.cookTimeTotal) {
                  this.cookTime = 0;
                  this.cookTimeTotal = this.getCookTime();
                  this.doSmelt();
                  needUpdate = true;
               }
            } else {
               this.cookTime = 0;
            }
         }

         if (wasBurning != this.isBurning()) {
            needUpdate = true;
            this.field_145850_b.func_180501_a(this.field_174879_c, (BlockState)this.field_145850_b.func_180495_p(this.field_174879_c).func_206870_a(AbstractFurnaceBlock.field_220091_b, this.isBurning()), 3);
         }
      }

      if (needUpdate) {
         this.func_70296_d();
      }

   }

   protected boolean canDoSmelting() {
      for(int i = 0; i < this.SLOTS_INGREDIENT.length; ++i) {
         int slotI = this.SLOTS_INGREDIENT[i];
         int slotA = this.SLOTS_ALLOY[i];
         int slotO = this.SLOTS_OUTPUT[i];
         if (this.canSmelt(slotI, slotA, slotO)) {
            return true;
         }
      }

      return false;
   }

   protected boolean canSmelt(int slotIngredient, int slotAlloy, int slotOutput) {
      ItemStack output = this.getSmeltingResult((ItemStack)this.inventory.get(slotIngredient), (ItemStack)this.inventory.get(slotAlloy));
      if (output.func_190926_b()) {
         return false;
      } else {
         ItemStack existingOutput = (ItemStack)this.inventory.get(slotOutput);
         if (existingOutput.func_190926_b()) {
            return true;
         } else if (!existingOutput.func_77969_a(output)) {
            return false;
         } else if (existingOutput.func_190916_E() + output.func_190916_E() <= this.func_70297_j_() && existingOutput.func_190916_E() + output.func_190916_E() <= existingOutput.func_77976_d()) {
            return true;
         } else {
            return existingOutput.func_190916_E() + output.func_190916_E() <= output.func_77976_d();
         }
      }
   }

   public final ItemStack getSmeltingResult(ItemStack ingredientStack, ItemStack alloyStack) {
      if (ingredientStack.func_190926_b()) {
         return ItemStack.field_190927_a;
      } else {
         Inventory furnaceTestInv;
         IRecipeType[] var4;
         int var5;
         int var6;
         IRecipeType recipeType;
         IRecipe furnaceRecipe;
         ItemStack output;
         if (!alloyStack.func_190926_b()) {
            furnaceTestInv = new Inventory(2);
            furnaceTestInv.func_70299_a(0, ingredientStack);
            furnaceTestInv.func_70299_a(1, alloyStack);
            var4 = this.alloyRecipeTypes;
            var5 = var4.length;

            for(var6 = 0; var6 < var5; ++var6) {
               recipeType = var4[var6];
               furnaceRecipe = (IRecipe)this.field_145850_b.func_199532_z().func_215371_a(recipeType, furnaceTestInv, this.field_145850_b).orElse((Object)null);
               if (furnaceRecipe != null) {
                  output = furnaceRecipe.func_77571_b();
                  return output;
               }
            }
         } else {
            furnaceTestInv = new Inventory(1);
            furnaceTestInv.func_70299_a(0, ingredientStack);
            var4 = this.recipeTypes;
            var5 = var4.length;

            for(var6 = 0; var6 < var5; ++var6) {
               recipeType = var4[var6];
               furnaceRecipe = (IRecipe)this.field_145850_b.func_199532_z().func_215371_a(recipeType, furnaceTestInv, this.field_145850_b).orElse((Object)null);
               if (furnaceRecipe != null) {
                  output = furnaceRecipe.func_77571_b();
                  if (recipeType != IRecipeType.field_222150_b || this.isDefaultFurnaceRecipeAcceptable(ingredientStack, output)) {
                     return output;
                  }
               }
            }
         }

         return ItemStack.field_190927_a;
      }
   }

   protected boolean isDefaultFurnaceRecipeAcceptable(ItemStack ingredientStack, ItemStack resultStack) {
      Item ingredient = ingredientStack.func_77973_b();
      if (ingredient instanceof BlockItem) {
         Block block = ((BlockItem)ingredient).func_179223_d();
         Material material = block.func_176223_P().func_185904_a();
         if (material == Material.field_151576_e || material == Material.field_151595_p || material == Material.field_151571_B) {
            return true;
         }
      }

      if (ingredient.func_206844_a(LOTRTags.Items.ALLOY_FORGE_EXTRA_SMELTABLES)) {
         return true;
      } else {
         return false;
      }
   }

   protected void doSmelt() {
      for(int i = 0; i < this.SLOTS_INGREDIENT.length; ++i) {
         int slotI = this.SLOTS_INGREDIENT[i];
         int slotA = this.SLOTS_ALLOY[i];
         int slotO = this.SLOTS_OUTPUT[i];
         this.smeltItemInSlot(slotI, slotA, slotO);
      }

   }

   private void smeltItemInSlot(int slotIngredient, int slotAlloy, int slotOutput) {
      IRecipe foundRecipe = null;
      boolean isAlloyRecipe = false;
      ItemStack ingredientStack = (ItemStack)this.inventory.get(slotIngredient);
      ItemStack alloyStack = (ItemStack)this.inventory.get(slotAlloy);
      Inventory alloyTestInv;
      IRecipeType[] var9;
      int var10;
      int var11;
      IRecipeType recipeType;
      if (!alloyStack.func_190926_b()) {
         alloyTestInv = new Inventory(2);
         alloyTestInv.func_70299_a(0, ingredientStack);
         alloyTestInv.func_70299_a(1, alloyStack);
         var9 = this.alloyRecipeTypes;
         var10 = var9.length;

         for(var11 = 0; var11 < var10; ++var11) {
            recipeType = var9[var11];
            foundRecipe = (IRecipe)this.field_145850_b.func_199532_z().func_215371_a(recipeType, alloyTestInv, this.field_145850_b).orElse((Object)null);
            if (foundRecipe != null) {
               isAlloyRecipe = true;
               break;
            }
         }
      } else {
         alloyTestInv = new Inventory(1);
         alloyTestInv.func_70299_a(0, ingredientStack);
         var9 = this.recipeTypes;
         var10 = var9.length;

         for(var11 = 0; var11 < var10; ++var11) {
            recipeType = var9[var11];
            foundRecipe = (IRecipe)this.field_145850_b.func_199532_z().func_215371_a(recipeType, alloyTestInv, this.field_145850_b).orElse((Object)null);
            if (foundRecipe != null) {
               break;
            }
         }
      }

      if (foundRecipe != null && this.canSmelt(slotIngredient, slotAlloy, slotOutput)) {
         ItemStack output = foundRecipe.func_77571_b();
         ItemStack existingOutput = (ItemStack)this.inventory.get(slotOutput);
         if (existingOutput.func_190926_b()) {
            this.inventory.set(slotOutput, output.func_77946_l());
         } else if (existingOutput.func_77973_b() == output.func_77973_b()) {
            existingOutput.func_190917_f(output.func_190916_E());
         }

         if (!this.field_145850_b.field_72995_K) {
            this.func_193056_a(foundRecipe);
         }

         if (ingredientStack.func_77973_b() == Blocks.field_196577_ad.func_199767_j() && !((ItemStack)this.inventory.get(this.SLOT_FUEL)).func_190926_b() && ((ItemStack)this.inventory.get(this.SLOT_FUEL)).func_77973_b() == Items.field_151133_ar) {
            this.inventory.set(this.SLOT_FUEL, new ItemStack(Items.field_151131_as));
         }

         ingredientStack.func_190918_g(1);
         if (isAlloyRecipe) {
            alloyStack.func_190918_g(1);
         }
      }

   }

   protected int getBurnTime(ItemStack itemstack) {
      if (itemstack.func_190926_b()) {
         return 0;
      } else {
         Item item = itemstack.func_77973_b();
         return ForgeHooks.getBurnTime(itemstack);
      }
   }

   protected int getCookTime() {
      return 400;
   }

   public int[] func_180463_a(Direction side) {
      if (side == Direction.DOWN) {
         return LOTRUtil.combineArrays(this.SLOTS_OUTPUT, new int[]{this.SLOT_FUEL});
      } else {
         return side == Direction.UP ? SlotAndCount.sortSlotsByCount(this, this.SLOTS_INGREDIENT) : new int[]{this.SLOT_FUEL};
      }
   }

   public boolean func_180462_a(int index, ItemStack stack, Direction direction) {
      return this.func_94041_b(index, stack);
   }

   public boolean func_180461_b(int index, ItemStack stack, Direction direction) {
      if (direction == Direction.DOWN && index == this.SLOT_FUEL) {
         Item item = stack.func_77973_b();
         if (item != Items.field_151131_as && item != Items.field_151133_ar) {
            return false;
         }
      }

      return true;
   }

   public boolean func_94041_b(int index, ItemStack stack) {
      if (ArrayUtils.contains(this.SLOTS_INGREDIENT, index)) {
         return true;
      } else if (index != this.SLOT_FUEL) {
         return false;
      } else {
         ItemStack currentFuel = (ItemStack)this.inventory.get(1);
         return AbstractFurnaceTileEntity.func_213991_b(stack) || stack.func_77973_b() == Items.field_151133_ar && currentFuel.func_77973_b() != Items.field_151133_ar;
      }
   }

   public int func_70302_i_() {
      return this.inventory.size();
   }

   public boolean func_191420_l() {
      Iterator var1 = this.inventory.iterator();

      ItemStack itemstack;
      do {
         if (!var1.hasNext()) {
            return true;
         }

         itemstack = (ItemStack)var1.next();
      } while(itemstack.func_190926_b());

      return false;
   }

   public ItemStack func_70301_a(int index) {
      return (ItemStack)this.inventory.get(index);
   }

   public ItemStack func_70298_a(int index, int count) {
      return ItemStackHelper.func_188382_a(this.inventory, index, count);
   }

   public ItemStack func_70304_b(int index) {
      return ItemStackHelper.func_188383_a(this.inventory, index);
   }

   public void func_70299_a(int index, ItemStack stack) {
      ItemStack itemstack = (ItemStack)this.inventory.get(index);
      boolean sameItem = !stack.func_190926_b() && stack.func_77969_a(itemstack) && ItemStack.func_77970_a(stack, itemstack);
      this.inventory.set(index, stack);
      if (stack.func_190916_E() > this.func_70297_j_()) {
         stack.func_190920_e(this.func_70297_j_());
      }

      if (ArrayUtils.contains(this.SLOTS_INGREDIENT, index) && !sameItem) {
         this.cookTimeTotal = this.getCookTime();
         this.func_70296_d();
      }

   }

   public boolean func_70300_a(PlayerEntity player) {
      if (this.field_145850_b.func_175625_s(this.field_174879_c) != this) {
         return false;
      } else {
         return player.func_70092_e((double)this.field_174879_c.func_177958_n() + 0.5D, (double)this.field_174879_c.func_177956_o() + 0.5D, (double)this.field_174879_c.func_177952_p() + 0.5D) <= 64.0D;
      }
   }

   public void func_174888_l() {
      this.inventory.clear();
   }

   public void func_193056_a(IRecipe recipe) {
      if (recipe != null) {
         this.usedRecipes.compute(recipe.func_199560_c(), (res, i) -> {
            return 1 + (i == null ? 0 : i);
         });
      }

   }

   public IRecipe func_193055_i() {
      return null;
   }

   public void func_201560_d(PlayerEntity player) {
   }

   public void onResultTaken(PlayerEntity player) {
      List recipes = Lists.newArrayList();
      Iterator var3 = this.usedRecipes.entrySet().iterator();

      while(var3.hasNext()) {
         Entry entry = (Entry)var3.next();
         player.field_70170_p.func_199532_z().func_215367_a((ResourceLocation)entry.getKey()).ifPresent((recipe) -> {
            recipes.add(recipe);
            float xp = 0.0F;
            if (recipe instanceof AbstractCookingRecipe) {
               xp = ((AbstractCookingRecipe)recipe).func_222138_b();
            } else if (recipe instanceof AbstractAlloyForgeRecipe) {
               xp = ((AbstractAlloyForgeRecipe)recipe).getExperience();
            }

            LOTRUtil.spawnXPOrbs(player, (Integer)entry.getValue(), xp);
         });
      }

      player.func_195065_a(recipes);
      this.usedRecipes.clear();
   }

   public void func_194018_a(RecipeItemHelper helper) {
      Iterator var2 = this.inventory.iterator();

      while(var2.hasNext()) {
         ItemStack itemstack = (ItemStack)var2.next();
         helper.func_194112_a(itemstack);
      }

   }

   public LazyOptional getCapability(Capability capability, Direction facing) {
      if (!this.field_145846_f && facing != null && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
         if (facing == Direction.UP) {
            return this.handlers[0].cast();
         } else {
            return facing == Direction.DOWN ? this.handlers[1].cast() : this.handlers[2].cast();
         }
      } else {
         return super.getCapability(capability, facing);
      }
   }

   public void func_145843_s() {
      super.func_145843_s();

      for(int x = 0; x < this.handlers.length; ++x) {
         this.handlers[x].invalidate();
      }

   }
}
