package lotr.common.item;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import lotr.common.LOTRLog;
import lotr.common.init.LOTREntities;
import lotr.common.init.LOTRItemGroups;
import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.IDispenseItemBehavior;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.item.Item.Properties;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class LOTRSpawnEggItem extends SpawnEggItem {
   private final Supplier entityTypeSup;
   public static final Set ALL_MOD_SPAWN_EGGS = new HashSet();
   private static final Map POST_ENTITY_REGISTRY_SPAWN_EGGS = new HashMap();

   public LOTRSpawnEggItem(LOTREntities.SpawnEggInfo egg) {
      super((EntityType)null, egg.colors.primaryColor, egg.colors.secondaryColor, (new Properties()).func_200916_a(LOTRItemGroups.SPAWNERS));
      this.entityTypeSup = egg.regType;
      ALL_MOD_SPAWN_EGGS.add(this);
   }

   public static void afterEntityRegistry() {
      ALL_MOD_SPAWN_EGGS.forEach((spawnEgg) -> {
         EntityType entityType = (EntityType)spawnEgg.entityTypeSup.get();
         POST_ENTITY_REGISTRY_SPAWN_EGGS.put(entityType, spawnEgg);
      });

      Map dispenseBehaviorRegistry;
      try {
         dispenseBehaviorRegistry = (Map)ObfuscationReflectionHelper.getPrivateValue(SpawnEggItem.class, (Object)null, "field_195987_b");
         dispenseBehaviorRegistry.putAll(POST_ENTITY_REGISTRY_SPAWN_EGGS);
      } catch (Exception var3) {
         LOTRLog.error("Exception adding mod spawn eggs to the vanilla map");
         var3.printStackTrace();
      }

      try {
         dispenseBehaviorRegistry = (Map)ObfuscationReflectionHelper.getPrivateValue(DispenserBlock.class, (Object)null, "field_149943_a");
         IDispenseItemBehavior vanillaSpawnEggDispense = (IDispenseItemBehavior)dispenseBehaviorRegistry.get(Items.field_196165_cw);
         ALL_MOD_SPAWN_EGGS.forEach((spawnEgg) -> {
            IDispenseItemBehavior var10000 = (IDispenseItemBehavior)dispenseBehaviorRegistry.put(spawnEgg, vanillaSpawnEggDispense);
         });
      } catch (Exception var2) {
         LOTRLog.error("Exception adding mod spawn eggs' dispenser behaviour");
         var2.printStackTrace();
      }

   }

   public static LOTRSpawnEggItem getModSpawnEgg(EntityType type) {
      return (LOTRSpawnEggItem)POST_ENTITY_REGISTRY_SPAWN_EGGS.get(type);
   }

   public EntityType func_208076_b(@Nullable CompoundNBT nbt) {
      EntityType superResult = super.func_208076_b(nbt);
      return superResult == null ? (EntityType)this.entityTypeSup.get() : superResult;
   }
}
