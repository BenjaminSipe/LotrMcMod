package lotr.common.datagen;

import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.google.common.collect.UnmodifiableIterator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;
import lotr.common.init.LOTREntities;
import lotr.common.init.LOTRItems;
import lotr.common.item.VesselDrinkItem;
import lotr.common.loot.functions.SetNPCDrinkPotency;
import lotr.common.loot.functions.SetPouchColorFromEntityFaction;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootParameterSet;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTableManager;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.loot.TableLootEntry;
import net.minecraft.loot.ValidationTracker;
import net.minecraft.loot.LootTable.Builder;
import net.minecraft.loot.conditions.KilledByPlayer;
import net.minecraft.loot.conditions.RandomChance;
import net.minecraft.loot.conditions.RandomChanceWithLooting;
import net.minecraft.loot.functions.LootingEnchantBonus;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NPCLootTableGenerator implements IDataProvider {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
   private final DataGenerator dataGenerator;
   private final Map lootTables = new HashMap();
   private final LootParameterSet parameterSet;

   public NPCLootTableGenerator(DataGenerator dataGenerator) {
      this.parameterSet = LootParameterSets.field_216263_d;
      this.dataGenerator = dataGenerator;
   }

   private void constructLootTables() {
      ResourceLocation orcBones = this.makeCommonPart("orc_bones", this.tableWithSinglePoolOfItemWithCount((IItemProvider)LOTRItems.ORC_BONE.get(), 1.0F));
      ResourceLocation elfBones = this.makeCommonPart("elf_bones", this.tableWithSinglePoolOfItemWithCount((IItemProvider)LOTRItems.ELF_BONE.get(), 1.0F));
      ResourceLocation manBones = this.makeCommonPart("man_bones", this.tableWithSinglePoolOfItemWithCount(Items.field_151103_aS, 1.0F));
      ResourceLocation dwarfBones = this.makeCommonPart("dwarf_bones", this.tableWithSinglePoolOfItemWithCount((IItemProvider)LOTRItems.DWARF_BONE.get(), 1.0F));
      ResourceLocation hobbitBones = this.makeCommonPart("hobbit_bones", this.tableWithSinglePoolOfItemWithCount((IItemProvider)LOTRItems.HOBBIT_BONE.get(), 1.0F));
      ResourceLocation wargBones = this.makeCommonPart("warg_bones", LootTable.func_216119_b().func_216040_a(this.poolWithItemEntryWithCount((IItemProvider)LOTRItems.WARG_BONE.get(), 1.0F, 3.0F)));
      ResourceLocation wargFurs = this.makeCommonPart("warg_furs", LootTable.func_216119_b().func_216040_a(this.poolWithItemEntryWithCount((IItemProvider)LOTRItems.FUR.get(), 1.0F, 3.0F)));
      ResourceLocation arrows = this.makeCommonPart("arrows", this.tableWithSinglePoolOfItemWithCount(Items.field_151032_g, 2.0F));
      ResourceLocation elfLembas = this.makeCommonPart("elf_lembas", LootTable.func_216119_b().func_216040_a(this.poolWithItemEntry((IItemProvider)LOTRItems.LEMBAS.get())));
      ResourceLocation dwarfRareDrops = this.makeCommonPart("dwarf_rare_drops", LootTable.func_216119_b().func_216040_a(this.pool().func_216045_a(this.itemLootEntry(Items.field_151042_j)).func_216045_a(this.itemLootEntry((IItemProvider)LOTRItems.DWARVEN_STEEL_INGOT.get())).func_216045_a(this.itemLootEntryWithCountAndLootingBonus(Items.field_151074_bl, 1.0F, 3.0F, 0.0F, 1.0F)).func_216045_a(this.itemLootEntryWithCountAndLootingBonus((IItemProvider)LOTRItems.SILVER_NUGGET.get(), 1.0F, 3.0F, 0.0F, 1.0F))));
      ResourceLocation pouch = this.makeCommonPart("pouch", LootTable.func_216119_b().func_216040_a(this.pool().func_216045_a(this.itemLootEntry((IItemProvider)LOTRItems.SMALL_POUCH.get()).func_216086_a(6)).func_216045_a(this.itemLootEntry((IItemProvider)LOTRItems.MEDIUM_POUCH.get()).func_216086_a(3)).func_216045_a(this.itemLootEntry((IItemProvider)LOTRItems.LARGE_POUCH.get()).func_216086_a(1)).func_212841_b_(SetPouchColorFromEntityFaction.setPouchColorFromEntityFactionBuilder(0.5F)).func_212840_b_(KilledByPlayer.func_215994_b()).func_212840_b_(RandomChance.func_216004_a(0.016666F))));
      ResourceLocation orcBase = this.makeCommonPart("orc", LootTable.func_216119_b().func_216040_a(this.poolWithSingleEntryOfOtherTable(orcBones)).func_216040_a(this.poolWithSingleEntryOfOtherTable(pouch)).func_216040_a(this.poolWithItemEntryWithCount(Items.field_151078_bh, 2.0F)).func_216040_a(this.poolWithItemEntryWithCount((IItemProvider)LOTRItems.MAGGOTY_BREAD.get(), 1.0F, 2.0F).func_212840_b_(RandomChance.func_216004_a(0.1F))).func_216040_a(this.poolWithItemEntryWithDrinkPotency((IItemProvider)LOTRItems.ORC_DRAUGHT.get()).func_212840_b_(KilledByPlayer.func_215994_b()).func_212840_b_(RandomChanceWithLooting.func_216003_a(0.025F, 0.008F))));
      ResourceLocation orcWithOrcSteel = this.makeCommonPart("orc_with_orc_steel", LootTable.func_216119_b().func_216040_a(this.poolWithSingleEntryOfOtherTable(orcBase)).func_216040_a(this.poolWithItemEntryWithCount((IItemProvider)LOTRItems.ORC_STEEL_INGOT.get(), 1.0F, 2.0F).func_212840_b_(KilledByPlayer.func_215994_b()).func_212840_b_(RandomChanceWithLooting.func_216003_a(0.025F, 0.008F))));
      ResourceLocation orcWithUrukSteel = this.makeCommonPart("orc_with_uruk_steel", LootTable.func_216119_b().func_216040_a(this.poolWithSingleEntryOfOtherTable(orcBase)).func_216040_a(this.poolWithItemEntryWithCount((IItemProvider)LOTRItems.URUK_STEEL_INGOT.get(), 1.0F, 2.0F).func_212840_b_(KilledByPlayer.func_215994_b()).func_212840_b_(RandomChanceWithLooting.func_216003_a(0.025F, 0.008F))));
      ResourceLocation elfBase = this.makeCommonPart("elf", LootTable.func_216119_b().func_216040_a(this.poolWithSingleEntryOfOtherTable(elfBones)).func_216040_a(this.poolWithSingleEntryOfOtherTable(pouch)).func_216040_a(this.poolWithSingleEntryOfOtherTable(arrows)));
      ResourceLocation elfWithMiruvor = this.makeCommonPart("elf_with_miruvor", LootTable.func_216119_b().func_216040_a(this.poolWithSingleEntryOfOtherTable(elfBase)).func_216040_a(this.poolWithItemEntryWithDrinkPotency((IItemProvider)LOTRItems.MIRUVOR.get()).func_212840_b_(KilledByPlayer.func_215994_b()).func_212840_b_(RandomChanceWithLooting.func_216003_a(0.05F, 0.016F))));
      ResourceLocation manBase = this.makeCommonPart("man", LootTable.func_216119_b().func_216040_a(this.poolWithSingleEntryOfOtherTable(manBones)).func_216040_a(this.poolWithSingleEntryOfOtherTable(pouch)));
      ResourceLocation dwarfBase = this.makeCommonPart("dwarf", LootTable.func_216119_b().func_216040_a(this.poolWithSingleEntryOfOtherTable(dwarfBones)).func_216040_a(this.poolWithSingleEntryOfOtherTable(pouch)).func_216040_a(this.poolWithSingleEntryOfOtherTable(dwarfRareDrops).func_212840_b_(KilledByPlayer.func_215994_b()).func_212840_b_(RandomChanceWithLooting.func_216003_a(0.05F, 0.016F))).func_216040_a(this.poolWithItemEntry((IItemProvider)LOTRItems.BOOK_OF_TRUE_SILVER.get()).func_212840_b_(KilledByPlayer.func_215994_b()).func_212840_b_(RandomChanceWithLooting.func_216003_a(0.025F, 0.005F))));
      ResourceLocation hobbitBase = this.makeCommonPart("hobbit", LootTable.func_216119_b().func_216040_a(this.poolWithSingleEntryOfOtherTable(hobbitBones)).func_216040_a(this.poolWithSingleEntryOfOtherTable(pouch)));
      ResourceLocation wargBase = this.makeCommonPart("warg", LootTable.func_216119_b().func_216040_a(this.poolWithSingleEntryOfOtherTable(wargBones)).func_216040_a(this.poolWithSingleEntryOfOtherTable(wargFurs)));
      ResourceLocation hobbit = this.makeFactionBase("hobbit", LootTable.func_216119_b().func_216040_a(this.poolWithSingleEntryOfOtherTable(hobbitBase)));
      ResourceLocation breeMan = this.makeFactionBase("bree_man", LootTable.func_216119_b().func_216040_a(this.poolWithSingleEntryOfOtherTable(manBase)));
      ResourceLocation breeHobbit = this.makeFactionBase("bree_hobbit", LootTable.func_216119_b().func_216040_a(this.poolWithSingleEntryOfOtherTable(hobbitBase)));
      ResourceLocation blueMountains = this.makeFactionBase("blue_mountains", LootTable.func_216119_b().func_216040_a(this.poolWithSingleEntryOfOtherTable(dwarfBase)));
      ResourceLocation lindon = this.makeFactionBase("lindon", LootTable.func_216119_b().func_216040_a(this.poolWithSingleEntryOfOtherTable(elfWithMiruvor)));
      ResourceLocation rivendell = this.makeFactionBase("rivendell", LootTable.func_216119_b().func_216040_a(this.poolWithSingleEntryOfOtherTable(elfWithMiruvor)));
      ResourceLocation gundabad = this.makeFactionBase("gundabad", LootTable.func_216119_b().func_216040_a(this.poolWithSingleEntryOfOtherTable(orcWithOrcSteel)));
      ResourceLocation woodElf = this.makeFactionBase("wood_elf", LootTable.func_216119_b().func_216040_a(this.poolWithSingleEntryOfOtherTable(elfBase)));
      ResourceLocation dale = this.makeFactionBase("dale", LootTable.func_216119_b().func_216040_a(this.poolWithSingleEntryOfOtherTable(manBase)));
      ResourceLocation durinsFolk = this.makeFactionBase("durins_folk", LootTable.func_216119_b().func_216040_a(this.poolWithSingleEntryOfOtherTable(dwarfBase)));
      ResourceLocation galadhrim = this.makeFactionBase("galadhrim", LootTable.func_216119_b().func_216040_a(this.poolWithSingleEntryOfOtherTable(elfWithMiruvor)).func_216040_a(this.poolWithSingleEntryOfOtherTable(elfLembas).func_212840_b_(KilledByPlayer.func_215994_b()).func_212840_b_(RandomChanceWithLooting.func_216003_a(0.025F, 0.008F))));
      ResourceLocation dunlending = this.makeFactionBase("dunlending", LootTable.func_216119_b().func_216040_a(this.poolWithSingleEntryOfOtherTable(manBase)));
      ResourceLocation uruk = this.makeFactionBase("uruk", LootTable.func_216119_b().func_216040_a(this.poolWithSingleEntryOfOtherTable(orcWithUrukSteel)));
      ResourceLocation isengardSnaga = this.makeFactionBase("isengard_snaga", LootTable.func_216119_b().func_216040_a(this.poolWithSingleEntryOfOtherTable(orcWithOrcSteel)));
      ResourceLocation rohan = this.makeFactionBase("rohan", LootTable.func_216119_b().func_216040_a(this.poolWithSingleEntryOfOtherTable(manBase)));
      ResourceLocation gondor = this.makeFactionBase("gondor", LootTable.func_216119_b().func_216040_a(this.poolWithSingleEntryOfOtherTable(manBase)));
      ResourceLocation mordor = this.makeFactionBase("mordor", LootTable.func_216119_b().func_216040_a(this.poolWithSingleEntryOfOtherTable(orcWithOrcSteel)));
      ResourceLocation harnedhrim = this.makeFactionBase("harnedhrim", LootTable.func_216119_b().func_216040_a(this.poolWithSingleEntryOfOtherTable(manBase)));
      ResourceLocation coastSouthron = this.makeFactionBase("coast_southron", LootTable.func_216119_b().func_216040_a(this.poolWithSingleEntryOfOtherTable(manBase)));
      ResourceLocation umbar = this.makeFactionBase("umbar", LootTable.func_216119_b().func_216040_a(this.poolWithSingleEntryOfOtherTable(manBase)));
      this.makeEntityTableFromPools(LOTREntities.HOBBIT, hobbit);
      this.makeEntityTableFromPools(LOTREntities.MORDOR_ORC, mordor);
      this.makeEntityTableFromPools(LOTREntities.GONDOR_MAN, gondor);
      this.makeEntityTableFromPools(LOTREntities.GALADHRIM_ELF, galadhrim);
      this.makeEntityTableFromPools(LOTREntities.GONDOR_SOLDIER, gondor);
      this.makeEntityTableFromPools(LOTREntities.DWARF, durinsFolk);
      this.makeEntityTableFromPools(LOTREntities.DWARF_WARRIOR, durinsFolk);
      this.makeEntityTableFromPools(LOTREntities.GALADHRIM_WARRIOR, galadhrim);
      this.makeEntityTableFromPools(LOTREntities.URUK, uruk);
      this.makeEntityTableFromPools(LOTREntities.ROHAN_MAN, rohan);
      this.makeEntityTableFromPools(LOTREntities.ROHIRRIM_WARRIOR, rohan);
      this.makeEntityTableFromPools(LOTREntities.GUNDABAD_ORC, gundabad);
      this.makeEntityTableFromPools(LOTREntities.DALE_MAN, dale);
      this.makeEntityTableFromPools(LOTREntities.DALE_SOLDIER, dale);
      this.makeEntityTableFromPools(LOTREntities.DUNLENDING, dunlending);
      this.makeEntityTableFromPools(LOTREntities.DUNLENDING_WARRIOR, dunlending);
      this.makeEntityTableFromPools(LOTREntities.LINDON_ELF, lindon);
      this.makeEntityTableFromPools(LOTREntities.LINDON_WARRIOR, lindon);
      this.makeEntityTableFromPools(LOTREntities.RIVENDELL_ELF, rivendell);
      this.makeEntityTableFromPools(LOTREntities.RIVENDELL_WARRIOR, rivendell);
      this.makeEntityTableFromPools(LOTREntities.COAST_SOUTHRON, coastSouthron);
      this.makeEntityTableFromPools(LOTREntities.COAST_SOUTHRON_WARRIOR, coastSouthron);
      this.makeEntityTableFromPools(LOTREntities.HARNEDHRIM, harnedhrim);
      this.makeEntityTableFromPools(LOTREntities.HARNENNOR_WARRIOR, harnedhrim);
      this.makeEntityTableFromPools(LOTREntities.BLUE_MOUNTAINS_DWARF, blueMountains);
      this.makeEntityTableFromPools(LOTREntities.BLUE_MOUNTAINS_WARRIOR, blueMountains);
      this.makeEntityTableFromPools(LOTREntities.UMBAR_MAN, umbar);
      this.makeEntityTableFromPools(LOTREntities.UMBAR_SOLDIER, umbar);
      this.makeEntityTableFromPools(LOTREntities.GONDOR_ARCHER, gondor, arrows);
      this.makeEntityTableFromPools(LOTREntities.ROHIRRIM_BOWMAN, rohan, arrows);
      this.makeEntityTableFromPools(LOTREntities.DALE_BOWMAN, dale, arrows);
      this.makeEntityTableFromPools(LOTREntities.DUNLENDING_BOWMAN, dunlending, arrows);
      this.makeEntityTableFromPools(LOTREntities.COAST_SOUTHRON_ARCHER, coastSouthron, arrows);
      this.makeEntityTableFromPools(LOTREntities.HARNENNOR_ARCHER, harnedhrim, arrows);
      this.makeEntityTableFromPools(LOTREntities.UMBAR_ARCHER, umbar, arrows);
      this.makeEntityTableFromPools(LOTREntities.MORDOR_ORC_ARCHER, mordor, arrows);
      this.makeEntityTableFromPools(LOTREntities.GUNDABAD_ORC_ARCHER, gundabad, arrows);
      this.makeEntityTableFromPools(LOTREntities.URUK_ARCHER, uruk, arrows);
      this.makeEntityTableFromPools(LOTREntities.DWARF_ARCHER, durinsFolk, arrows);
      this.makeEntityTableFromPools(LOTREntities.BLUE_MOUNTAINS_ARCHER, blueMountains, arrows);
      this.makeEntityTableFromPools(LOTREntities.WOOD_ELF, woodElf);
      this.makeEntityTableFromPools(LOTREntities.WOOD_ELF_WARRIOR, woodElf);
      this.makeEntityTableFromPools(LOTREntities.BREE_MAN, breeMan);
      this.makeEntityTableFromPools(LOTREntities.BREE_HOBBIT, breeHobbit);
      this.makeEntityTableFromPools(LOTREntities.BREE_GUARD, breeMan);
      this.makeEntityTableFromPools(LOTREntities.GUNDABAD_WARG, wargBase);
      this.makeEntityTableFromPools(LOTREntities.ISENGARD_WARG, wargBase);
      this.makeEntityTableFromPools(LOTREntities.MORDOR_WARG, wargBase);
      this.makeEntityTableFromPools(LOTREntities.ISENGARD_SNAGA, isengardSnaga);
      this.makeEntityTableFromPools(LOTREntities.ISENGARD_SNAGA_ARCHER, isengardSnaga, arrows);
   }

   private void makeEntityTable(Supplier entityType, Builder builder) {
      this.lootTables.put(new ResourceLocation("lotr", "entities/" + ((EntityType)entityType.get()).getRegistryName().func_110623_a()), builder);
   }

   private void makeEntityTableFromPools(Supplier entityType, ResourceLocation... basePools) {
      Builder tableBuilder = LootTable.func_216119_b();
      Stream.of(basePools).forEach((pool) -> {
         tableBuilder.func_216040_a(this.poolWithSingleEntryOfOtherTable(pool));
      });
      this.makeEntityTable(entityType, tableBuilder);
   }

   private ResourceLocation makeCommonPart(String name, Builder builder) {
      ResourceLocation path = new ResourceLocation("lotr", "entities/common/parts/" + name);
      this.lootTables.put(path, builder);
      return path;
   }

   private ResourceLocation makeFactionBase(String name, Builder builder) {
      ResourceLocation path = new ResourceLocation("lotr", "entities/common/faction_bases/" + name);
      this.lootTables.put(path, builder);
      return path;
   }

   private Builder tableWithSinglePoolOfItemWithCount(IItemProvider item, float maxCount) {
      return LootTable.func_216119_b().func_216040_a(this.poolWithItemEntryWithCount(item, maxCount));
   }

   private net.minecraft.loot.LootPool.Builder pool() {
      return this.pool(1);
   }

   private net.minecraft.loot.LootPool.Builder pool(int rolls) {
      return LootPool.func_216096_a().func_216046_a(ConstantRange.func_215835_a(rolls));
   }

   private net.minecraft.loot.LootPool.Builder poolWithItemEntryWithCount(IItemProvider item, float maxCount) {
      return this.poolWithItemEntryWithCount(item, 0.0F, maxCount);
   }

   private net.minecraft.loot.LootPool.Builder poolWithItemEntryWithCount(IItemProvider item, float minCount, float maxCount) {
      return this.poolWithItemEntry(item, (itemLootEntry) -> {
         itemLootEntry.func_212841_b_(SetCount.func_215932_a(RandomValueRange.func_215837_a(minCount, maxCount))).func_212841_b_(LootingEnchantBonus.func_215915_a(RandomValueRange.func_215837_a(0.0F, 1.0F)));
      });
   }

   private net.minecraft.loot.LootPool.Builder poolWithItemEntryWithDrinkPotency(IItemProvider item) {
      Item asItem = item.func_199767_j();
      if (asItem instanceof VesselDrinkItem && ((VesselDrinkItem)asItem).hasPotencies) {
         return this.poolWithItemEntry(item, (itemLootEntry) -> {
            itemLootEntry.func_212841_b_(SetNPCDrinkPotency.setNPCDrinkPotencyBuilder());
         });
      } else {
         throw new IllegalArgumentException(asItem.getRegistryName() + " is not a drink item with potencies");
      }
   }

   private net.minecraft.loot.LootPool.Builder poolWithItemEntry(IItemProvider item) {
      return this.poolWithItemEntry(item, (itemLootEntry) -> {
      });
   }

   private net.minecraft.loot.LootPool.Builder poolWithItemEntry(IItemProvider item, Consumer lootFunctionAdder) {
      net.minecraft.loot.StandaloneLootEntry.Builder itemLootEntry = this.itemLootEntry(item);
      lootFunctionAdder.accept(itemLootEntry);
      return this.pool().func_216045_a(itemLootEntry);
   }

   private net.minecraft.loot.LootPool.Builder poolWithSingleEntryOfOtherTable(ResourceLocation otherTable) {
      return this.pool().func_216045_a(TableLootEntry.func_216171_a(otherTable));
   }

   private net.minecraft.loot.StandaloneLootEntry.Builder itemLootEntry(IItemProvider item) {
      return ItemLootEntry.func_216168_a(item);
   }

   private net.minecraft.loot.StandaloneLootEntry.Builder itemLootEntryWithCount(IItemProvider item, float minCount, float maxCount) {
      return this.itemLootEntry(item).func_212841_b_(SetCount.func_215932_a(RandomValueRange.func_215837_a(minCount, maxCount)));
   }

   private net.minecraft.loot.StandaloneLootEntry.Builder itemLootEntryWithCountAndLootingBonus(IItemProvider item, float minCount, float maxCount) {
      return this.itemLootEntryWithCountAndLootingBonus(item, minCount, maxCount, 0.0F, 1.0F);
   }

   private net.minecraft.loot.StandaloneLootEntry.Builder itemLootEntryWithCountAndLootingBonus(IItemProvider item, float minCount, float maxCount, float minBonus, float maxBonus) {
      return this.itemLootEntryWithCount(item, minCount, maxCount).func_212841_b_(LootingEnchantBonus.func_215915_a(RandomValueRange.func_215837_a(minBonus, maxBonus)));
   }

   public void func_200398_a(DirectoryCache cache) {
      this.constructLootTables();
      Path rootPath = this.dataGenerator.func_200391_b();
      Map tables = Maps.newHashMap();
      this.lootTables.forEach((name, tableBuilder) -> {
         if (tables.put(name, tableBuilder.func_216039_a(this.parameterSet).func_216038_b()) != null) {
            throw new IllegalStateException("Duplicate loot table " + name);
         }
      });
      LootParameterSet var10002 = LootParameterSets.field_216266_g;
      Function var10003 = (name) -> {
         return null;
      };
      tables.getClass();
      ValidationTracker validationTracker = new ValidationTracker(var10002, var10003, tables::get);
      this.validate(tables, validationTracker);
      Multimap problems = validationTracker.func_227527_a_();
      if (!problems.isEmpty()) {
         problems.forEach((tableName, problem) -> {
            LOGGER.warn("Found validation problem in " + tableName + ": " + problem);
         });
         throw new IllegalStateException("Failed to validate loot tables, see logs");
      } else {
         tables.forEach((name, lootTable) -> {
            Path path = getPath(rootPath, name);

            try {
               IDataProvider.func_218426_a(GSON, cache, LootTableManager.func_215301_a(lootTable), path);
            } catch (IOException var6) {
               LOGGER.error("Couldn't generate loot table {}", path, var6);
            }

         });
      }
   }

   private void validate(Map map, ValidationTracker validationTracker) {
      UnmodifiableIterator var3 = Sets.intersection(LootTables.func_215796_a(), map.keySet()).iterator();

      while(var3.hasNext()) {
         ResourceLocation name = (ResourceLocation)var3.next();
         validationTracker.func_227530_a_("Shouldn't be overwriting built-in table: " + name);
      }

      map.keySet().stream().filter((namex) -> {
         return !namex.func_110624_b().equals("lotr");
      }).forEach((namex) -> {
         validationTracker.func_227530_a_("Table " + namex + " is not in the mod's own namespace");
      });
      map.forEach((namex, lootTable) -> {
         LootTableManager.func_227508_a_(validationTracker, namex, lootTable);
      });
   }

   private static Path getPath(Path path, ResourceLocation name) {
      return path.resolve("data/" + name.func_110624_b() + "/loot_tables/" + name.func_110623_a() + ".json");
   }

   public String func_200397_b() {
      return "LOTRNPCLootTables";
   }
}
