package lotr.common.item;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import lotr.common.LOTRLog;
import lotr.common.entity.npc.OrcEntity;
import lotr.common.inv.OpenPouchContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.item.Items;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class LOTRItemProperties {
   private static final List preparedItemProperties = new ArrayList();
   private static final List preparedGlobalProperties = new ArrayList();
   private static final LOTRItemProperties.PreparedGlobalProperty SNEAKING = LOTRItemProperties.PreparedGlobalProperty.prepare(new ResourceLocation("lotr", "sneaking"), (itemstack, world, entity) -> {
      return entity != null && entity.func_225608_bj_() ? 1.0F : 0.0F;
   });
   public static final float ELVEN_GLOW_RANGE = 24.0F;
   private static final LOTRItemProperties.PreparedGlobalProperty ELVEN_GLOW = LOTRItemProperties.PreparedGlobalProperty.prepare(new ResourceLocation("lotr", "elven_glow"), (itemstack, world, entity) -> {
      return world != null && entity != null && !world.func_225316_b(OrcEntity.class, entity.func_174813_aQ().func_186662_g(24.0D), EntityPredicates.field_212545_b).isEmpty() ? 1.0F : 0.0F;
   });
   private static final LOTRItemProperties.PreparedItemProperty SMOKING_PIPE_COLOR = LOTRItemProperties.PreparedItemProperty.prepare(SmokingPipeItem.class, new ResourceLocation("lotr", "pipe_color"), (itemstack, world, entity) -> {
      return itemstack.func_77973_b() instanceof SmokingPipeItem ? (float)SmokingPipeItem.getSmokeColor(itemstack).func_196059_a() : 0.0F;
   });
   private static final LOTRItemProperties.PreparedItemProperty SMOKING_PIPE_MAGIC = LOTRItemProperties.PreparedItemProperty.prepare(SmokingPipeItem.class, new ResourceLocation("lotr", "pipe_is_magic"), (itemstack, world, entity) -> {
      return itemstack.func_77973_b() instanceof SmokingPipeItem && SmokingPipeItem.isMagicSmoke(itemstack) ? 1.0F : 0.0F;
   });
   private static final LOTRItemProperties.PreparedItemProperty POUCH_OPEN = LOTRItemProperties.PreparedItemProperty.prepare(PouchItem.class, new ResourceLocation("lotr", "pouch_open"), (itemstack, world, entity) -> {
      if (entity instanceof PlayerEntity) {
         PlayerEntity player = (PlayerEntity)entity;
         Container container = player.field_71070_bA;
         if (container instanceof OpenPouchContainer && ((OpenPouchContainer)container).isOpenPouch(itemstack)) {
            return 1.0F;
         }
      }

      return 0.0F;
   });
   private static final LOTRItemProperties.PreparedItemProperty REF_SHIELD_BLOCKING;
   private static final LOTRItemProperties.PreparedItemProperty REF_SPEAR_PULLING;

   public static void registerProperties() {
      Iterator var0 = ForgeRegistries.ITEMS.getValues().iterator();

      while(var0.hasNext()) {
         Item item = (Item)var0.next();
         preparedItemProperties.stream().filter((propx) -> {
            return propx.shouldApplyTo(item);
         }).forEach((propx) -> {
            ItemModelsProperties.func_239418_a_(item, propx.key, propx.getter);
         });
      }

      preparedItemProperties.clear();

      try {
         Method m_regGlobal = ObfuscationReflectionHelper.findMethod(ItemModelsProperties.class, "func_239420_a_", new Class[]{ResourceLocation.class, IItemPropertyGetter.class});
         Iterator var5 = preparedGlobalProperties.iterator();

         while(var5.hasNext()) {
            LOTRItemProperties.PreparedProperty prop = (LOTRItemProperties.PreparedProperty)var5.next();
            m_regGlobal.invoke((Object)null, prop.key, prop.getter);
         }
      } catch (Exception var3) {
         LOTRLog.error("Error registering global item properties!");
         var3.printStackTrace();
      }

      preparedGlobalProperties.clear();
   }

   static {
      REF_SHIELD_BLOCKING = LOTRItemProperties.PreparedItemProperty.copyFromVanillaItem(LOTRShieldItem.class, Items.field_185159_cQ, new ResourceLocation("blocking"));
      REF_SPEAR_PULLING = LOTRItemProperties.PreparedItemProperty.copyFromVanillaItem(SpearItem.class, Items.field_151031_f, new ResourceLocation("pulling"));
   }

   private static final class PreparedGlobalProperty extends LOTRItemProperties.PreparedProperty {
      private PreparedGlobalProperty(ResourceLocation key, IItemPropertyGetter getter) {
         super(key, getter);
      }

      public static LOTRItemProperties.PreparedGlobalProperty prepare(ResourceLocation key, IItemPropertyGetter getter) {
         LOTRItemProperties.PreparedGlobalProperty prop = new LOTRItemProperties.PreparedGlobalProperty(key, getter);
         LOTRItemProperties.preparedGlobalProperties.add(prop);
         return prop;
      }
   }

   private static final class PreparedItemProperty extends LOTRItemProperties.PreparedProperty {
      public final Predicate isApplicable;

      private PreparedItemProperty(Predicate isApplicable, ResourceLocation key, IItemPropertyGetter getter) {
         super(key, getter);
         this.isApplicable = isApplicable;
      }

      public boolean shouldApplyTo(Item item) {
         return this.isApplicable.test(item);
      }

      public static LOTRItemProperties.PreparedItemProperty prepare(Predicate isApplicable, ResourceLocation key, IItemPropertyGetter getter) {
         LOTRItemProperties.PreparedItemProperty prop = new LOTRItemProperties.PreparedItemProperty(isApplicable, key, getter);
         LOTRItemProperties.preparedItemProperties.add(prop);
         return prop;
      }

      public static LOTRItemProperties.PreparedItemProperty prepare(Class baseItemClass, ResourceLocation key, IItemPropertyGetter getter) {
         return prepare((item) -> {
            return baseItemClass.isAssignableFrom(item.getClass());
         }, key, getter);
      }

      public static LOTRItemProperties.PreparedItemProperty copyFromVanillaItem(Class baseItemClass, Item vanillaItem, ResourceLocation propertyKey) {
         Map itemPropertyMap = (Map)ObfuscationReflectionHelper.getPrivateValue(ItemModelsProperties.class, (Object)null, "field_239415_f_");
         ResourceLocation vanillaItemName = vanillaItem.getRegistryName();
         Item matchingVanillaItemInMap = (Item)itemPropertyMap.keySet().stream().filter((item) -> {
            return item.getRegistryName().equals(vanillaItemName);
         }).findFirst().orElseThrow(() -> {
            return new IllegalStateException("Could not find any item matching " + vanillaItemName + " in the item model properties key set - this is a big problem!");
         });
         IItemPropertyGetter copiedGetter = (IItemPropertyGetter)((Map)itemPropertyMap.get(matchingVanillaItemInMap)).get(propertyKey);
         if (copiedGetter == null) {
            throw new IllegalArgumentException("Could not find item model property " + propertyKey + " to copy from vanilla item " + vanillaItemName);
         } else {
            return prepare(baseItemClass, propertyKey, copiedGetter);
         }
      }
   }

   private abstract static class PreparedProperty {
      public final ResourceLocation key;
      public final IItemPropertyGetter getter;

      protected PreparedProperty(ResourceLocation key, IItemPropertyGetter getter) {
         this.key = key;
         this.getter = getter;
      }
   }
}
