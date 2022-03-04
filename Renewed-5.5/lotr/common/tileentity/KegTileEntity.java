package lotr.common.tileentity;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import lotr.common.LOTRLog;
import lotr.common.block.KegBlock;
import lotr.common.init.LOTRBlocks;
import lotr.common.init.LOTRTileEntities;
import lotr.common.inv.KegContainer;
import lotr.common.inv.SlotAndCount;
import lotr.common.item.VesselDrinkItem;
import lotr.common.recipe.DrinkBrewingRecipe;
import lotr.common.recipe.LOTRRecipes;
import lotr.common.util.LOTRUtil;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IRecipeHelperPopulator;
import net.minecraft.inventory.IRecipeHolder;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import org.apache.commons.lang3.ArrayUtils;

public class KegTileEntity extends LockableTileEntity implements ISidedInventory, IRecipeHolder, IRecipeHelperPopulator, ITickableTileEntity {
   public static final int DEFAULT_BREWING_TIME = 12000;
   public static final int FULL_KEG_AMOUNT = 16;
   private static final int[] SLOTS_INGREDIENT = new int[]{0, 1, 2, 3, 4, 5};
   private static final int[] SLOTS_WATER = new int[]{6, 7, 8};
   private static final int SLOT_BREW = 9;
   private NonNullList inventory;
   private KegTileEntity.KegMode kegMode;
   private int brewTime;
   private int brewTimeTotal;
   private final IIntArray kegData;
   private ResourceLocation currentBrewingRecipe;
   private final Map usedRecipes;
   private List usingPlayers;
   private int numPlayersOpened;
   private LazyOptional[] handlers;

   public KegTileEntity() {
      super((TileEntityType)LOTRTileEntities.KEG.get());
      this.inventory = NonNullList.func_191197_a(SLOTS_INGREDIENT.length + SLOTS_WATER.length + 1, ItemStack.field_190927_a);
      this.kegMode = KegTileEntity.KegMode.EMPTY;
      this.kegData = new IIntArray() {
         public int func_221476_a(int index) {
            switch(index) {
            case 0:
               return KegTileEntity.this.kegMode.id;
            case 1:
               return KegTileEntity.this.brewTime;
            case 2:
               return KegTileEntity.this.brewTimeTotal;
            default:
               return 0;
            }
         }

         public void func_221477_a(int index, int value) {
            switch(index) {
            case 0:
               KegTileEntity.this.kegMode = KegTileEntity.KegMode.forId(value);
               break;
            case 1:
               KegTileEntity.this.brewTime = value;
               break;
            case 2:
               KegTileEntity.this.brewTimeTotal = value;
            }

         }

         public int func_221478_a() {
            return 3;
         }
      };
      this.usedRecipes = Maps.newHashMap();
      this.usingPlayers = new ArrayList();
      this.handlers = SidedInvWrapper.create(this, new Direction[]{Direction.UP, Direction.DOWN, Direction.NORTH});
   }

   protected Container func_213906_a(int id, PlayerInventory player) {
      return new KegContainer(id, player, this, this.kegData);
   }

   protected ITextComponent func_213907_g() {
      return new TranslationTextComponent("container.lotr.keg");
   }

   public void func_230337_a_(BlockState state, CompoundNBT nbt) {
      super.func_230337_a_(state, nbt);
      CompoundNBT droppableNbt = nbt.func_74775_l("KegDroppableData");
      this.inventory = NonNullList.func_191197_a(this.func_70302_i_(), ItemStack.field_190927_a);
      ItemStackHelper.func_191283_b(droppableNbt, this.inventory);
      this.kegMode = KegTileEntity.KegMode.forId(droppableNbt.func_74771_c("KegMode"));
      this.brewTime = droppableNbt.func_74762_e("BrewingTime");
      this.brewTimeTotal = droppableNbt.func_74762_e("BrewingTimeTotal");
      if (droppableNbt.func_74764_b("CurrentRecipe")) {
         this.currentBrewingRecipe = new ResourceLocation(droppableNbt.func_74779_i("CurrentRecipe"));
      }

      if (droppableNbt.func_74764_b("RecipesUsed")) {
         ListNBT recipesList = droppableNbt.func_150295_c("RecipesUsed", 10);

         for(int ri = 0; ri < recipesList.size(); ++ri) {
            CompoundNBT recipeTag = recipesList.func_150305_b(ri);
            ResourceLocation res = new ResourceLocation(recipeTag.func_74779_i("Id"));
            int count = recipeTag.func_74762_e("Count");
            this.usedRecipes.put(res, count);
         }
      }

   }

   public CompoundNBT func_189515_b(CompoundNBT nbt) {
      super.func_189515_b(nbt);
      CompoundNBT droppableNbt = new CompoundNBT();
      ItemStackHelper.func_191282_a(droppableNbt, this.inventory);
      droppableNbt.func_74774_a("KegMode", (byte)this.kegMode.id);
      droppableNbt.func_74768_a("BrewingTime", this.brewTime);
      droppableNbt.func_74768_a("BrewingTimeTotal", this.brewTimeTotal);
      if (this.currentBrewingRecipe != null) {
         droppableNbt.func_74778_a("CurrentRecipe", this.currentBrewingRecipe.toString());
      }

      ListNBT recipesList = new ListNBT();
      Iterator var4 = this.usedRecipes.entrySet().iterator();

      while(var4.hasNext()) {
         Entry entry = (Entry)var4.next();
         CompoundNBT recipeTag = new CompoundNBT();
         recipeTag.func_74778_a("Id", ((ResourceLocation)entry.getKey()).toString());
         recipeTag.func_74768_a("Count", (Integer)entry.getValue());
         recipesList.add(recipeTag);
      }

      droppableNbt.func_218657_a("RecipesUsed", recipesList);
      nbt.func_218657_a("KegDroppableData", droppableNbt);
      return nbt;
   }

   public void func_73660_a() {
      boolean needUpdate = false;
      if (!this.field_145850_b.field_72995_K) {
         if (this.kegMode == KegTileEntity.KegMode.BREWING) {
            ItemStack brewingItem = this.func_70301_a(9);
            if (!brewingItem.func_190926_b()) {
               ++this.brewTime;
               if (this.brewTime >= this.brewTimeTotal) {
                  this.brewTime = 0;
                  VesselDrinkItem.Potency potency = VesselDrinkItem.getPotency(brewingItem);
                  if (potency.isMax()) {
                     this.kegMode = KegTileEntity.KegMode.FULL;
                     this.brewTime = 0;
                     this.brewTimeTotal = 0;
                     needUpdate = true;
                  } else {
                     VesselDrinkItem.setPotency(brewingItem, potency.getNext());
                     this.setRecipeUsed_id(this.currentBrewingRecipe);
                     needUpdate = true;
                  }
               }
            } else {
               this.kegMode = KegTileEntity.KegMode.EMPTY;
               this.brewTime = 0;
               this.brewTimeTotal = 0;
               this.currentBrewingRecipe = null;
               needUpdate = true;
            }
         } else {
            this.brewTime = 0;
         }

         if (this.kegMode == KegTileEntity.KegMode.FULL) {
            if (!this.usingPlayers.isEmpty() && this.currentBrewingRecipe != null) {
               PlayerEntity firstUser = (PlayerEntity)this.usingPlayers.get(0);
               this.completeCurrentRecipe(firstUser);
            }

            if (this.func_70301_a(9).func_190926_b()) {
               this.kegMode = KegTileEntity.KegMode.EMPTY;
               needUpdate = true;
            }
         }
      }

      if (needUpdate) {
         this.func_70296_d();
      }

   }

   public void handleBrewButtonPress(ServerPlayerEntity player) {
      ItemStack brewingItem = this.func_70301_a(9);
      if (this.kegMode == KegTileEntity.KegMode.EMPTY && !brewingItem.func_190926_b()) {
         this.kegMode = KegTileEntity.KegMode.BREWING;
         int[] inputSlots = LOTRUtil.combineArrays(SLOTS_INGREDIENT, SLOTS_WATER);
         int[] var4 = inputSlots;
         int var5 = inputSlots.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            int slot = var4[var6];
            ItemStack inSlot = this.func_70301_a(slot);
            if (!inSlot.func_190926_b()) {
               ItemStack suitableContainer = ItemStack.field_190927_a;
               if (inSlot.hasContainerItem()) {
                  suitableContainer = inSlot.getContainerItem();
                  if (suitableContainer.func_77984_f() && suitableContainer.func_77952_i() > suitableContainer.func_77958_k()) {
                     suitableContainer = ItemStack.field_190927_a;
                  }
               }

               inSlot.func_190918_g(1);
               if (inSlot.func_190926_b()) {
                  this.func_70299_a(slot, suitableContainer);
               }
            }
         }

         this.resendKegInventoryToPlayers();
      } else if (this.kegMode == KegTileEntity.KegMode.BREWING && !brewingItem.func_190926_b()) {
         VesselDrinkItem.Potency potency = VesselDrinkItem.getPotency(brewingItem);
         if (!potency.isMin()) {
            VesselDrinkItem.setPotency(brewingItem, potency.getPrev());
            this.kegMode = KegTileEntity.KegMode.FULL;
            this.brewTime = 0;
            this.brewTimeTotal = 0;
            this.completeCurrentRecipe(player);
         }
      }

   }

   private void resendKegInventoryToPlayers() {
      Iterator var1 = this.usingPlayers.iterator();

      while(var1.hasNext()) {
         ServerPlayerEntity user = (ServerPlayerEntity)var1.next();
         user.field_71070_bA.func_75142_b();
      }

   }

   private void completeCurrentRecipe(PlayerEntity player) {
      List recipes = Lists.newArrayList();
      Iterator var3 = this.usedRecipes.entrySet().iterator();

      while(var3.hasNext()) {
         Entry entry = (Entry)var3.next();
         player.field_70170_p.func_199532_z().func_215367_a((ResourceLocation)entry.getKey()).ifPresent((recipe) -> {
            recipes.add(recipe);
            float xp = 0.0F;
            if (recipe instanceof DrinkBrewingRecipe) {
               xp = ((DrinkBrewingRecipe)recipe).getExperience();
            }

            LOTRUtil.spawnXPOrbs(player, (Integer)entry.getValue(), xp);
         });
      }

      player.func_195065_a(recipes);
      this.usedRecipes.clear();
      this.currentBrewingRecipe = null;
      this.brewTimeTotal = 0;
   }

   private void addUsingPlayer(ServerPlayerEntity player) {
      if (!this.usingPlayers.contains(player)) {
         this.usingPlayers.add(player);
      }

   }

   private void removeUsingPlayer(ServerPlayerEntity player) {
      this.usingPlayers.remove(player);
   }

   public void func_174889_b(PlayerEntity player) {
      if (player instanceof ServerPlayerEntity) {
         this.addUsingPlayer((ServerPlayerEntity)player);
      }

      if (!player.func_175149_v()) {
         if (this.numPlayersOpened < 0) {
            this.numPlayersOpened = 0;
         }

         ++this.numPlayersOpened;
         BlockState state = this.func_195044_w();
         boolean open = (Boolean)state.func_177229_b(KegBlock.OPEN);
         if (!open) {
            this.playOpenOrCloseSound(state, SoundEvents.field_219602_O);
            this.setOpenProperty(state, true);
         }

         this.scheduleTick();
      }

   }

   private void scheduleTick() {
      this.field_145850_b.func_205220_G_().func_205360_a(this.func_174877_v(), this.func_195044_w().func_177230_c(), 5);
   }

   public void kegTick() {
      int i = this.field_174879_c.func_177958_n();
      int j = this.field_174879_c.func_177956_o();
      int k = this.field_174879_c.func_177952_p();
      this.numPlayersOpened = LOTRUtil.calculatePlayersUsingSingleContainer(this.field_145850_b, i, j, k, KegContainer.class, (kegContainer) -> {
         return kegContainer.theKeg == this;
      });
      if (this.numPlayersOpened > 0) {
         this.scheduleTick();
      } else {
         BlockState state = this.func_195044_w();
         if (state.func_177230_c() != LOTRBlocks.KEG.get()) {
            LOTRLog.warn("Keg tileentity ticking at (%s) expected keg block but found %s - removing", this.field_174879_c, state.func_177230_c().getRegistryName());
            this.func_145843_s();
            return;
         }

         boolean open = (Boolean)state.func_177229_b(KegBlock.OPEN);
         if (open) {
            this.playOpenOrCloseSound(state, SoundEvents.field_219601_N);
            this.setOpenProperty(state, false);
         }
      }

   }

   public void func_174886_c(PlayerEntity player) {
      if (player instanceof ServerPlayerEntity) {
         this.removeUsingPlayer((ServerPlayerEntity)player);
      }

      if (!player.func_175149_v()) {
         --this.numPlayersOpened;
      }

   }

   private void setOpenProperty(BlockState state, boolean open) {
      this.field_145850_b.func_180501_a(this.func_174877_v(), (BlockState)state.func_206870_a(KegBlock.OPEN, open), 3);
   }

   private void playOpenOrCloseSound(BlockState state, SoundEvent sound) {
      Vector3i facingOffset = ((Direction)state.func_177229_b(KegBlock.field_185512_D)).func_176730_m();
      double x = (double)this.field_174879_c.func_177958_n() + 0.5D + (double)facingOffset.func_177958_n() / 2.0D;
      double y = (double)this.field_174879_c.func_177956_o() + 0.5D + (double)facingOffset.func_177956_o() / 2.0D;
      double z = (double)this.field_174879_c.func_177952_p() + 0.5D + (double)facingOffset.func_177952_p() / 2.0D;
      this.field_145850_b.func_184148_a((PlayerEntity)null, x, y, z, sound, SoundCategory.BLOCKS, 0.5F, this.field_145850_b.field_73012_v.nextFloat() * 0.1F + 0.9F);
   }

   public int[] func_180463_a(Direction side) {
      if (side == Direction.DOWN) {
         return LOTRUtil.combineArrays(SLOTS_INGREDIENT, SLOTS_WATER);
      } else {
         return side == Direction.UP ? SlotAndCount.sortSlotsByCount(this, SLOTS_INGREDIENT) : SLOTS_WATER;
      }
   }

   public boolean func_180462_a(int index, ItemStack stack, Direction direction) {
      return this.kegMode == KegTileEntity.KegMode.EMPTY ? this.func_94041_b(index, stack) : false;
   }

   public boolean func_180461_b(int index, ItemStack stack, Direction direction) {
      if (direction == Direction.DOWN && this.isIngredientOrWaterSlot(index)) {
         return this.kegMode != KegTileEntity.KegMode.EMPTY;
      } else {
         return false;
      }
   }

   public boolean func_94041_b(int index, ItemStack stack) {
      if (ArrayUtils.contains(SLOTS_INGREDIENT, index)) {
         return true;
      } else {
         return ArrayUtils.contains(SLOTS_WATER, index) ? DrinkBrewingRecipe.isWaterSource(stack) : false;
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
      ItemStack ret = ItemStackHelper.func_188382_a(this.inventory, index, count);
      if (this.isIngredientOrWaterSlot(index)) {
         this.updateBrewingRecipe();
      }

      return ret;
   }

   public ItemStack func_70304_b(int index) {
      ItemStack ret = ItemStackHelper.func_188383_a(this.inventory, index);
      if (this.isIngredientOrWaterSlot(index)) {
         this.updateBrewingRecipe();
      }

      return ret;
   }

   public void func_70299_a(int index, ItemStack stack) {
      ItemStack itemstack = (ItemStack)this.inventory.get(index);
      this.inventory.set(index, stack);
      if (stack.func_190916_E() > this.func_70297_j_()) {
         stack.func_190920_e(this.func_70297_j_());
      }

      if (this.isIngredientOrWaterSlot(index)) {
         this.updateBrewingRecipe();
      }

   }

   private boolean isIngredientOrWaterSlot(int index) {
      return ArrayUtils.contains(SLOTS_INGREDIENT, index) || ArrayUtils.contains(SLOTS_WATER, index);
   }

   private void updateBrewingRecipe() {
      if (this.kegMode == KegTileEntity.KegMode.EMPTY) {
         IInventory proxyInv = new Inventory(9);
         int[] var2 = SLOTS_INGREDIENT;
         int var3 = var2.length;

         int var4;
         int i;
         for(var4 = 0; var4 < var3; ++var4) {
            i = var2[var4];
            proxyInv.func_70299_a(i, this.func_70301_a(i));
         }

         var2 = SLOTS_WATER;
         var3 = var2.length;

         for(var4 = 0; var4 < var3; ++var4) {
            i = var2[var4];
            proxyInv.func_70299_a(i, this.func_70301_a(i));
         }

         Optional opt = this.field_145850_b.func_199532_z().func_215371_a(LOTRRecipes.DRINK_BREWING, proxyInv, this.field_145850_b);
         if (opt.isPresent()) {
            DrinkBrewingRecipe recipe = (DrinkBrewingRecipe)opt.get();
            this.currentBrewingRecipe = recipe.func_199560_c();
            ItemStack result = recipe.func_77572_b(proxyInv);
            result.func_190920_e(16);
            VesselDrinkItem.setPotency(result, VesselDrinkItem.Potency.WEAK);
            this.func_70299_a(9, result);
            this.brewTimeTotal = recipe.getBrewTime();
         } else {
            this.currentBrewingRecipe = null;
            this.func_70299_a(9, ItemStack.field_190927_a);
            this.brewTimeTotal = 0;
         }

         this.func_70296_d();
         if (!this.field_145850_b.field_72995_K) {
            this.resendKegInventoryToPlayers();
         }
      }

   }

   private DrinkBrewingRecipe findBrewingRecipe(ResourceLocation rId) {
      Optional opt = this.field_145850_b.func_199532_z().func_215367_a(rId);
      if (opt.isPresent()) {
         IRecipe recipe = (IRecipe)opt.get();
         if (recipe instanceof DrinkBrewingRecipe) {
            return (DrinkBrewingRecipe)recipe;
         }
      }

      LOTRLog.warn("Keg attempted to find brewing recipe by known id %s, this recipe really should exist but doesn't!", rId);
      return null;
   }

   public KegTileEntity.KegMode getKegMode() {
      return this.kegMode;
   }

   public ItemStack getFinishedBrewDrink() {
      return this.kegMode == KegTileEntity.KegMode.FULL ? this.func_70301_a(9) : ItemStack.field_190927_a;
   }

   public void fillBrewedWith(ItemStack stack) {
      this.func_70299_a(9, stack);
      this.kegMode = KegTileEntity.KegMode.FULL;
      this.func_70296_d();
   }

   public void consumeServing() {
      ItemStack drink = this.getFinishedBrewDrink();
      if (!drink.func_190926_b()) {
         drink.func_190918_g(1);
         this.func_70299_a(9, drink);
         if (drink.func_190926_b()) {
            this.kegMode = KegTileEntity.KegMode.EMPTY;
            this.func_70296_d();
         }
      }

   }

   public void clearBrewedSlot() {
      this.func_70299_a(9, ItemStack.field_190927_a);
   }

   public void dropContentsExceptBrew() {
      for(int i = 0; i < this.func_70302_i_(); ++i) {
         if (i != 9) {
            InventoryHelper.func_180173_a(this.field_145850_b, (double)this.field_174879_c.func_177958_n(), (double)this.field_174879_c.func_177956_o(), (double)this.field_174879_c.func_177952_p(), this.func_70301_a(i));
         }
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
      this.updateBrewingRecipe();
   }

   public void func_193056_a(IRecipe recipe) {
      if (recipe != null) {
         this.setRecipeUsed_id(recipe.func_199560_c());
      }

   }

   private void setRecipeUsed_id(ResourceLocation rId) {
      this.usedRecipes.compute(rId, (res, i) -> {
         return 1 + (i == null ? 0 : i);
      });
   }

   public IRecipe func_193055_i() {
      return null;
   }

   public void func_201560_d(PlayerEntity player) {
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

   public static enum KegMode {
      EMPTY(0),
      BREWING(1),
      FULL(2);

      public final int id;

      private KegMode(int i) {
         this.id = i;
      }

      public static KegTileEntity.KegMode forId(int i) {
         KegTileEntity.KegMode[] var1 = values();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            KegTileEntity.KegMode b = var1[var3];
            if (b.id == i) {
               return b;
            }
         }

         return EMPTY;
      }
   }
}
