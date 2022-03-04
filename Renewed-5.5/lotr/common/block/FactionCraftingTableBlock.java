package lotr.common.block;

import io.netty.buffer.Unpooled;
import java.util.Optional;
import java.util.Random;
import java.util.function.Supplier;
import lotr.common.LOTRLog;
import lotr.common.data.AlignmentDataModule;
import lotr.common.data.LOTRLevelData;
import lotr.common.fac.AlignmentLevels;
import lotr.common.fac.AlignmentPredicates;
import lotr.common.fac.Faction;
import lotr.common.fac.FactionPointer;
import lotr.common.fac.FactionPointers;
import lotr.common.init.LOTRBlocks;
import lotr.common.init.LOTRContainers;
import lotr.common.inv.FactionCraftingContainer;
import lotr.common.recipe.FactionTableType;
import lotr.common.recipe.LOTRRecipes;
import lotr.common.stat.LOTRStats;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

public abstract class FactionCraftingTableBlock extends Block {
   private final FactionPointer tableFac;
   private final FactionTableType factionRecipeType;
   private final String tableScreenName;

   public FactionCraftingTableBlock(Supplier block, FactionPointer tableFac, FactionTableType factionRecipeType, String tableScreenName) {
      this(Properties.func_200950_a((AbstractBlock)block.get()), tableFac, factionRecipeType, tableScreenName);
   }

   public FactionCraftingTableBlock(Properties props, FactionPointer tableFac, FactionTableType factionRecipeType, String tableScreenName) {
      super(props);
      this.tableFac = tableFac;
      this.factionRecipeType = factionRecipeType;
      this.tableScreenName = tableScreenName;
   }

   public ActionResultType func_225533_a_(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult trace) {
      if (this.hasRequiredAligment(player)) {
         if (world.field_72995_K) {
            return ActionResultType.SUCCESS;
         } else {
            ContainerType containerType = (ContainerType)LOTRContainers.FACTION_CRAFTING.get();
            ITextComponent containerTitle = new TranslationTextComponent(String.format("container.%s.%s", "lotr", this.tableScreenName));
            FactionCraftingContainer.FactionCraftingContainerInitData initData = new FactionCraftingContainer.FactionCraftingContainerInitData(this, this.factionRecipeType, (Faction)this.tableFac.resolveFaction((IWorldReader)world).orElseThrow(() -> {
               return new IllegalStateException("Faction crafting table couldn't resolve faction " + this.tableFac.getName() + " when sending container to client");
            }));
            PacketBuffer initBuf = new PacketBuffer(Unpooled.buffer());
            initData.write(initBuf);
            NetworkHooks.openGui((ServerPlayerEntity)player, new SimpleNamedContainerProvider((i, inv, p) -> {
               return ((FactionCraftingContainer)containerType.create(i, inv, initBuf)).setWorldPosCallable(IWorldPosCallable.func_221488_a(world, pos));
            }, containerTitle), initData::write);
            player.func_195066_a(LOTRStats.INTERACT_FACTION_CRAFTING_TABLE);
            return ActionResultType.SUCCESS;
         }
      } else {
         if (!world.field_72995_K) {
            ServerWorld sWorld = (ServerWorld)world;

            for(int l = 0; l < 8; ++l) {
               double x = (double)((float)pos.func_177958_n() + world.field_73012_v.nextFloat());
               double y = (double)(pos.func_177956_o() + 1);
               double z = (double)((float)pos.func_177952_p() + world.field_73012_v.nextFloat());
               sWorld.func_195598_a(ParticleTypes.field_197601_L, x, y, z, 1, 0.0D, 0.0D, 0.0D, 0.0D);
            }

            AlignmentLevels.notifyAlignmentNotHighEnough((ServerPlayerEntity)player, 1.0F, this.tableFac);
         }

         return ActionResultType.SUCCESS;
      }
   }

   private boolean hasRequiredAligment(PlayerEntity player) {
      AlignmentDataModule alignData = LOTRLevelData.getSidedData(player).getAlignmentData();
      Optional optFaction = this.tableFac.resolveFaction((IWorldReader)player.field_70170_p);
      return (Boolean)optFaction.map((faction) -> {
         return alignData.hasAlignment(faction, AlignmentPredicates.greaterThanOrEqual(1.0F));
      }).orElseGet(() -> {
         LOTRLog.warn("Player %s tried to use faction table %s, but the associated faction %s does not exist in the current datapacks! Allowing the use.", player.func_200200_C_().getString(), this.getRegistryName(), this.tableFac.getName());
         return true;
      });
   }

   public static class Bree extends FactionCraftingTableBlock {
      public Bree(Supplier block) {
         super(block, FactionPointers.BREE, LOTRRecipes.BREE_CRAFTING, "bree_crafting");
      }
   }

   public static class Dunlending extends FactionCraftingTableBlock {
      public Dunlending(Supplier block) {
         super(block, FactionPointers.DUNLAND, LOTRRecipes.DUNLENDING_CRAFTING, "dunlending_crafting");
      }
   }

   public static class Lossoth extends FactionCraftingTableBlock {
      public Lossoth(Supplier block) {
         super(block, FactionPointers.LOSSOTH, LOTRRecipes.LOSSOTH_CRAFTING, "lossoth_crafting");
      }
   }

   public static class Dale extends FactionCraftingTableBlock {
      public Dale(Supplier block) {
         super(block, FactionPointers.DALE, LOTRRecipes.DALE_CRAFTING, "dale_crafting");
      }
   }

   public static class Dorwinion extends FactionCraftingTableBlock {
      public Dorwinion(Supplier block) {
         super(block, FactionPointers.DORWINION, LOTRRecipes.DORWINION_CRAFTING, "dorwinion_crafting");
      }
   }

   public static class Angmar extends FactionCraftingTableBlock {
      public Angmar(Supplier block) {
         super(block, FactionPointers.ANGMAR, LOTRRecipes.ANGMAR_CRAFTING, "angmar_crafting");
      }
   }

   public static class DolAmroth extends FactionCraftingTableBlock {
      public DolAmroth(Supplier block) {
         super(block, FactionPointers.GONDOR, LOTRRecipes.DOL_AMROTH_CRAFTING, "dol_amroth_crafting");
      }
   }

   public static class Ranger extends FactionCraftingTableBlock {
      public Ranger(Supplier block) {
         super(block, FactionPointers.DUNEDAIN_NORTH, LOTRRecipes.RANGER_CRAFTING, "ranger_crafting");
      }
   }

   public static class BlueMountains extends FactionCraftingTableBlock {
      public BlueMountains(Supplier block) {
         super(block, FactionPointers.BLUE_MOUNTAINS, LOTRRecipes.BLUE_MOUNTAINS_CRAFTING, "blue_mountains_crafting");
      }
   }

   public static class Hobbit extends FactionCraftingTableBlock {
      public Hobbit(Supplier block) {
         super(block, FactionPointers.HOBBITS, LOTRRecipes.HOBBIT_CRAFTING, "hobbit_crafting");
      }
   }

   public static class Uruk extends FactionCraftingTableBlock {
      public Uruk(Supplier block) {
         super(block, FactionPointers.ISENGARD, LOTRRecipes.URUK_CRAFTING, "uruk_crafting");
      }
   }

   public static class Umbar extends FactionCraftingTableBlock {
      public Umbar(Supplier block) {
         super(block, FactionPointers.NEAR_HARAD, LOTRRecipes.UMBAR_CRAFTING, "umbar_crafting");
      }
   }

   public static class Harad extends FactionCraftingTableBlock {
      public Harad(Supplier block) {
         super(block, FactionPointers.NEAR_HARAD, LOTRRecipes.HARAD_CRAFTING, "harad_crafting");
      }
   }

   public static class WoodElven extends FactionCraftingTableBlock {
      public WoodElven(Supplier block) {
         super(block, FactionPointers.WOODLAND_REALM, LOTRRecipes.WOOD_ELVEN_CRAFTING, "wood_elven_crafting");
      }
   }

   public static class Galadhrim extends FactionCraftingTableBlock {
      public Galadhrim(Supplier block) {
         super(block, FactionPointers.LOTHLORIEN, LOTRRecipes.GALADHRIM_CRAFTING, "galadhrim_crafting");
      }
   }

   public static class Rivendell extends FactionCraftingTableBlock {
      public Rivendell(Supplier block) {
         super(block, FactionPointers.HIGH_ELVES, LOTRRecipes.RIVENDELL_CRAFTING, "rivendell_crafting");
      }
   }

   public static class Lindon extends FactionCraftingTableBlock {
      public Lindon(Supplier block) {
         super(block, FactionPointers.HIGH_ELVES, LOTRRecipes.LINDON_CRAFTING, "lindon_crafting");
      }
   }

   public static class Dwarven extends FactionCraftingTableBlock {
      public Dwarven(Supplier block) {
         super(block, FactionPointers.DURINS_FOLK, LOTRRecipes.DWARVEN_CRAFTING, "dwarven_crafting");
      }
   }

   public static class Rohan extends FactionCraftingTableBlock {
      public Rohan(Supplier block) {
         super(block, FactionPointers.ROHAN, LOTRRecipes.ROHAN_CRAFTING, "rohan_crafting");
      }
   }

   public static class Mordor extends FactionCraftingTableBlock {
      public Mordor(Supplier block) {
         super(Properties.func_200950_a((AbstractBlock)block.get()).func_235838_a_(LOTRBlocks.constantLight(8)), FactionPointers.MORDOR, LOTRRecipes.MORDOR_CRAFTING, "mordor_crafting");
      }

      @OnlyIn(Dist.CLIENT)
      public void func_180655_c(BlockState state, World world, BlockPos pos, Random rand) {
         for(int l = 0; l < 2; ++l) {
            double d0 = (double)((float)pos.func_177958_n() + MathHelper.func_151240_a(rand, 0.25F, 0.75F));
            double d1 = (double)pos.func_177956_o() + 1.0D;
            double d2 = (double)((float)pos.func_177952_p() + MathHelper.func_151240_a(rand, 0.25F, 0.75F));
            world.func_195594_a(ParticleTypes.field_197631_x, d0, d1, d2, 0.0D, 0.0D, 0.0D);
         }

      }
   }

   public static class Gondor extends FactionCraftingTableBlock {
      public Gondor(Supplier block) {
         super(block, FactionPointers.GONDOR, LOTRRecipes.GONDOR_CRAFTING, "gondor_crafting");
      }
   }
}
