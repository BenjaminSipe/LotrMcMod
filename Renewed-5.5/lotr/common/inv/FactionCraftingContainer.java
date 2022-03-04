package lotr.common.inv;

import java.util.Iterator;
import java.util.Optional;
import lotr.common.fac.Faction;
import lotr.common.fac.FactionSettingsManager;
import lotr.common.init.LOTRContainers;
import lotr.common.recipe.FactionTableType;
import lotr.common.recipe.LOTRRecipes;
import lotr.common.recipe.MultiTableType;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.SSetSlotPacket;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

public class FactionCraftingContainer extends Container {
   private final FactionCraftingInventory craftMatrix = new FactionCraftingInventory(this, 3, 3);
   private final CraftResultInventory craftResult = new CraftResultInventory();
   private final PlayerEntity craftingPlayer;
   private IWorldPosCallable worldPos;
   private final FactionCraftingContainer.FactionCraftingContainerInitData initData;
   private boolean standardCraftingTableRecipes;

   public FactionCraftingContainer(int windowID, PlayerInventory inv, PacketBuffer extraData) {
      super((ContainerType)LOTRContainers.FACTION_CRAFTING.get(), windowID);
      this.worldPos = IWorldPosCallable.field_221489_a;
      this.craftingPlayer = inv.field_70458_d;
      if (extraData == null) {
         throw new IllegalArgumentException("FactionCraftingContainer REQUIRES extra packet buffer data to initialise!");
      } else {
         this.initData = FactionCraftingContainer.FactionCraftingContainerInitData.read(extraData, this.craftingPlayer.field_70170_p);
         this.func_75146_a(new FactionCraftingResultSlot(inv.field_70458_d, this, this.craftMatrix, this.craftResult, 0, 124, 35));

         int x;
         int x;
         for(x = 0; x < 3; ++x) {
            for(x = 0; x < 3; ++x) {
               this.func_75146_a(new Slot(this.craftMatrix, x + x * 3, 30 + x * 18, 17 + x * 18));
            }
         }

         for(x = 0; x < 3; ++x) {
            for(x = 0; x < 9; ++x) {
               this.func_75146_a(new Slot(inv, x + x * 9 + 9, 8 + x * 18, 84 + x * 18));
            }
         }

         for(x = 0; x < 9; ++x) {
            this.func_75146_a(new Slot(inv, x, 8 + x * 18, 142));
         }

      }
   }

   public Block getCraftingBlock() {
      return this.initData.tableBlock;
   }

   protected FactionTableType getRecipeType() {
      return this.initData.factionRecipeType;
   }

   protected Faction getFaction() {
      return this.initData.faction;
   }

   public FactionCraftingContainer setWorldPosCallable(IWorldPosCallable pos) {
      this.worldPos = pos;
      return this;
   }

   public boolean isStandardCraftingActive() {
      return this.standardCraftingTableRecipes;
   }

   public void setStandardCraftingActive(boolean flag) {
      boolean prev = this.standardCraftingTableRecipes;
      this.standardCraftingTableRecipes = flag;
      if (this.standardCraftingTableRecipes != prev) {
         this.func_75130_a(this.craftMatrix);
      }

   }

   public void func_75130_a(IInventory inv) {
      this.worldPos.func_221486_a((world, pos) -> {
         this.tryMatchRecipe(this.field_75152_c, world, this.craftingPlayer, this.craftMatrix, this.craftResult);
      });
   }

   protected void tryMatchRecipe(int windowID, World world, PlayerEntity player, CraftingInventory inv, CraftResultInventory resultInv) {
      if (!world.field_72995_K) {
         ServerPlayerEntity serverPlayer = (ServerPlayerEntity)player;
         Optional optRecipe = this.findMatchingRecipeOfAppropriateType(world, serverPlayer, inv);
         ItemStack result = ItemStack.field_190927_a;
         if (optRecipe.isPresent()) {
            ICraftingRecipe recipe = (ICraftingRecipe)optRecipe.get();
            if (resultInv.func_201561_a(world, serverPlayer, recipe)) {
               result = recipe.func_77572_b(inv);
            }
         }

         resultInv.func_70299_a(0, result);
         serverPlayer.field_71135_a.func_147359_a(new SSetSlotPacket(windowID, 0, result));
      }

   }

   public Optional findMatchingRecipeOfAppropriateType(World world, PlayerEntity player, CraftingInventory inv) {
      return this.standardCraftingTableRecipes ? this.findMatchingRecipeOfType(world, player, inv, IRecipeType.field_222149_a) : this.findMatchingFactionOrMulti(world, player, inv);
   }

   private Optional findMatchingFactionOrMulti(World world, PlayerEntity player, CraftingInventory inv) {
      FactionTableType tableType = this.getRecipeType();
      Optional recipe = this.findMatchingRecipeOfType(world, player, inv, tableType);
      if (recipe.isPresent()) {
         return recipe;
      } else {
         Iterator var6 = tableType.getMultiTableTypes().iterator();

         Optional multiRecipe;
         do {
            if (!var6.hasNext()) {
               return Optional.empty();
            }

            MultiTableType multiType = (MultiTableType)var6.next();
            multiRecipe = this.findMatchingRecipeOfType(world, player, inv, multiType);
         } while(!multiRecipe.isPresent());

         return multiRecipe;
      }
   }

   private Optional findMatchingRecipeOfType(World world, PlayerEntity player, CraftingInventory inv, IRecipeType type) {
      return world.func_199532_z().func_215371_a(type, inv, world);
   }

   public void func_75134_a(PlayerEntity player) {
      super.func_75134_a(player);
      this.worldPos.func_221486_a((world, pos) -> {
         this.func_193327_a(player, world, this.craftMatrix);
      });
   }

   public boolean func_75145_c(PlayerEntity player) {
      return func_216963_a(this.worldPos, player, this.getCraftingBlock());
   }

   public ItemStack func_82846_b(PlayerEntity player, int index) {
      ItemStack itemstack = ItemStack.field_190927_a;
      Slot slot = (Slot)this.field_75151_b.get(index);
      if (slot != null && slot.func_75216_d()) {
         ItemStack itemstack1 = slot.func_75211_c();
         itemstack = itemstack1.func_77946_l();
         if (index == 0) {
            this.worldPos.func_221486_a((world, pos) -> {
               itemstack1.func_77973_b().func_77622_d(itemstack1, world, player);
            });
            if (!this.func_75135_a(itemstack1, 10, 46, true)) {
               return ItemStack.field_190927_a;
            }

            slot.func_75220_a(itemstack1, itemstack);
         } else if (index >= 10 && index < 46) {
            if (!this.func_75135_a(itemstack1, 1, 10, false)) {
               if (index < 37) {
                  if (!this.func_75135_a(itemstack1, 37, 46, false)) {
                     return ItemStack.field_190927_a;
                  }
               } else if (!this.func_75135_a(itemstack1, 10, 37, false)) {
                  return ItemStack.field_190927_a;
               }
            }
         } else if (!this.func_75135_a(itemstack1, 10, 46, false)) {
            return ItemStack.field_190927_a;
         }

         if (itemstack1.func_190926_b()) {
            slot.func_75215_d(ItemStack.field_190927_a);
         } else {
            slot.func_75218_e();
         }

         if (itemstack1.func_190916_E() == itemstack.func_190916_E()) {
            return ItemStack.field_190927_a;
         }

         ItemStack itemstack2 = slot.func_190901_a(player, itemstack1);
         if (index == 0) {
            player.func_71019_a(itemstack2, false);
         }
      }

      return itemstack;
   }

   public boolean func_94530_a(ItemStack stack, Slot slotIn) {
      return slotIn.field_75224_c != this.craftResult && super.func_94530_a(stack, slotIn);
   }

   public static class FactionCraftingContainerInitData {
      private final Block tableBlock;
      private final FactionTableType factionRecipeType;
      private final Faction faction;

      public FactionCraftingContainerInitData(Block tableBlock, FactionTableType factionRecipeType, Faction faction) {
         this.tableBlock = tableBlock;
         this.factionRecipeType = factionRecipeType;
         this.faction = faction;
      }

      public void write(PacketBuffer buf) {
         buf.func_192572_a(this.tableBlock.getRegistryName());
         buf.func_192572_a(this.factionRecipeType.recipeTypeName);
         buf.func_192572_a(this.faction.getName());
      }

      public static FactionCraftingContainer.FactionCraftingContainerInitData read(PacketBuffer buf, World world) {
         Block tableBlock = (Block)ForgeRegistries.BLOCKS.getValue(buf.func_192575_l());
         FactionTableType factionRecipeType = (FactionTableType)LOTRRecipes.findRecipeTypeByNameOrThrow(buf.func_192575_l(), FactionTableType.class);
         Faction faction = FactionSettingsManager.sidedInstance((IWorldReader)world).getCurrentLoadedFactions().getFactionByName(buf.func_192575_l());
         return new FactionCraftingContainer.FactionCraftingContainerInitData(tableBlock, factionRecipeType, faction);
      }
   }
}
