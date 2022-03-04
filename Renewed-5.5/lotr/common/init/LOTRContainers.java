package lotr.common.init;

import lotr.common.inv.AlloyForgeContainer;
import lotr.common.inv.FactionCraftingContainer;
import lotr.common.inv.KegContainer;
import lotr.common.inv.PouchContainer;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class LOTRContainers {
   public static final DeferredRegister CONTAINERS;
   public static final RegistryObject FACTION_CRAFTING;
   public static final RegistryObject ALLOY_FORGE;
   public static final RegistryObject KEG;
   public static final RegistryObject POUCH;

   public static void register() {
      CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());
   }

   static {
      CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, "lotr");
      FACTION_CRAFTING = CONTAINERS.register("faction_crafting", () -> {
         return IForgeContainerType.create(FactionCraftingContainer::new);
      });
      ALLOY_FORGE = CONTAINERS.register("alloy_forge", () -> {
         return IForgeContainerType.create(AlloyForgeContainer::new);
      });
      KEG = CONTAINERS.register("keg", () -> {
         return IForgeContainerType.create(KegContainer::new);
      });
      POUCH = CONTAINERS.register("pouch", () -> {
         return IForgeContainerType.create(PouchContainer::new);
      });
   }
}
