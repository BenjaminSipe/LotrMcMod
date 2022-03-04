package lotr.common.init;

import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.types.Type;
import java.util.function.Supplier;
import lotr.common.LOTRLog;
import lotr.common.tileentity.AlloyForgeTileEntity;
import lotr.common.tileentity.CustomWaypointMarkerTileEntity;
import lotr.common.tileentity.DwarvenForgeTileEntity;
import lotr.common.tileentity.ElvenForgeTileEntity;
import lotr.common.tileentity.GondorBeaconTileEntity;
import lotr.common.tileentity.HobbitOvenTileEntity;
import lotr.common.tileentity.KegTileEntity;
import lotr.common.tileentity.OrcForgeTileEntity;
import lotr.common.tileentity.PalantirTileEntity;
import lotr.common.tileentity.PlateTileEntity;
import lotr.common.tileentity.VesselDrinkTileEntity;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntityType.Builder;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.datafix.DataFixesManager;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class LOTRTileEntities {
   public static final DeferredRegister TILE_ENTITIES;
   public static final RegistryObject ALLOY_FORGE;
   public static final RegistryObject DWARVEN_FORGE;
   public static final RegistryObject ELVEN_FORGE;
   public static final RegistryObject ORC_FORGE;
   public static final RegistryObject HOBBIT_OVEN;
   public static final RegistryObject PLATE;
   public static final RegistryObject KEG;
   public static final RegistryObject CUSTOM_WAYPOINT_MARKER;
   public static final RegistryObject GONDOR_BEACON;
   public static final RegistryObject VESSEL_DRINK;
   public static final RegistryObject PALANTIR;

   public static void register() {
      IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
      TILE_ENTITIES.register(bus);
   }

   private static RegistryObject register(String key, Supplier builderSup) {
      Type datafixType = null;

      try {
         datafixType = DataFixesManager.func_210901_a().getSchema(DataFixUtils.makeKey(SharedConstants.func_215069_a().getWorldVersion())).getChoiceType(TypeReferences.field_211294_j, key);
      } catch (IllegalArgumentException var4) {
         LOTRLog.error("No data fixer registered for block entity {}", key);
         if (SharedConstants.field_206244_b) {
            throw var4;
         }
      }

      return TILE_ENTITIES.register(key, () -> {
         return ((Builder)builderSup.get()).func_206865_a(datafixType);
      });
   }

   static {
      TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, "lotr");
      ALLOY_FORGE = register("alloy_forge", () -> {
         return Builder.func_223042_a(AlloyForgeTileEntity::new, new Block[]{(Block)LOTRBlocks.ALLOY_FORGE.get()});
      });
      DWARVEN_FORGE = register("dwarven_forge", () -> {
         return Builder.func_223042_a(DwarvenForgeTileEntity::new, new Block[]{(Block)LOTRBlocks.DWARVEN_FORGE.get()});
      });
      ELVEN_FORGE = register("elven_forge", () -> {
         return Builder.func_223042_a(ElvenForgeTileEntity::new, new Block[]{(Block)LOTRBlocks.ELVEN_FORGE.get()});
      });
      ORC_FORGE = register("orc_forge", () -> {
         return Builder.func_223042_a(OrcForgeTileEntity::new, new Block[]{(Block)LOTRBlocks.ORC_FORGE.get()});
      });
      HOBBIT_OVEN = register("hobbit_oven", () -> {
         return Builder.func_223042_a(HobbitOvenTileEntity::new, new Block[]{(Block)LOTRBlocks.HOBBIT_OVEN.get()});
      });
      PLATE = register("plate", () -> {
         return Builder.func_223042_a(PlateTileEntity::new, new Block[]{(Block)LOTRBlocks.FINE_PLATE.get(), (Block)LOTRBlocks.STONEWARE_PLATE.get(), (Block)LOTRBlocks.WOODEN_PLATE.get()});
      });
      KEG = register("keg", () -> {
         return Builder.func_223042_a(KegTileEntity::new, new Block[]{(Block)LOTRBlocks.KEG.get()});
      });
      CUSTOM_WAYPOINT_MARKER = register("custom_waypoint_marker", () -> {
         return Builder.func_223042_a(CustomWaypointMarkerTileEntity::new, new Block[]{(Block)LOTRBlocks.CUSTOM_WAYPOINT_MARKER.get()});
      });
      GONDOR_BEACON = register("gondor_beacon", () -> {
         return Builder.func_223042_a(GondorBeaconTileEntity::new, new Block[]{(Block)LOTRBlocks.GONDOR_BEACON.get()});
      });
      VESSEL_DRINK = register("vessel_drink", () -> {
         return Builder.func_223042_a(VesselDrinkTileEntity::new, new Block[]{(Block)LOTRBlocks.WOODEN_MUG.get(), (Block)LOTRBlocks.CERAMIC_MUG.get(), (Block)LOTRBlocks.GOLDEN_GOBLET.get(), (Block)LOTRBlocks.SILVER_GOBLET.get(), (Block)LOTRBlocks.COPPER_GOBLET.get(), (Block)LOTRBlocks.WOODEN_CUP.get(), (Block)LOTRBlocks.ALE_HORN.get(), (Block)LOTRBlocks.GOLDEN_ALE_HORN.get()});
      });
      PALANTIR = register("palantir", () -> {
         return Builder.func_223042_a(PalantirTileEntity::new, new Block[]{(Block)LOTRBlocks.PALANTIR.get()});
      });
   }
}
