package lotr.common.data;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import lotr.common.LOTRLog;
import lotr.common.fac.FactionPointer;
import lotr.common.util.TriConsumer;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.ResourceLocationException;
import org.apache.commons.lang3.tuple.Pair;

public class DataUtil {
   public static Object getIfNBTContains(Object currentValue, CompoundNBT nbt, String key, BiFunction nbtGetter) {
      return nbt.func_74764_b(key) ? nbtGetter.apply(nbt, key) : currentValue;
   }

   public static UUID getUniqueIdBackCompat(CompoundNBT nbt, String key) {
      try {
         return nbt.func_186857_a(key);
      } catch (NullPointerException | IllegalArgumentException var3) {
         if (hasOldUniqueId(nbt, key)) {
            return new UUID(nbt.func_74763_f(key + "Most"), nbt.func_74763_f(key + "Least"));
         } else {
            throw var3;
         }
      }
   }

   private static boolean hasOldUniqueId(CompoundNBT nbt, String key) {
      return nbt.func_150297_b(key + "Most", 4) && nbt.func_150297_b(key + "Least", 4);
   }

   public static boolean hasUniqueIdBackCompat(CompoundNBT nbt, String key) {
      return nbt.func_186855_b(key) || hasOldUniqueId(nbt, key);
   }

   public static ListNBT saveCollectionAsCompoundListNBT(Collection collection, BiConsumer elementToNbt) {
      ListNBT tagList = new ListNBT();
      Iterator var3 = collection.iterator();

      while(var3.hasNext()) {
         Object t = var3.next();
         CompoundNBT nbt = new CompoundNBT();
         elementToNbt.accept(nbt, t);
         tagList.add(nbt);
      }

      return tagList;
   }

   public static ListNBT saveCollectionAsPrimitiveListNBT(Collection collection, Function elementToNbt) {
      ListNBT tagList = new ListNBT();
      Iterator var3 = collection.iterator();

      while(var3.hasNext()) {
         Object t = var3.next();
         tagList.add(elementToNbt.apply(t));
      }

      return tagList;
   }

   public static void loadCollectionFromCompoundListNBT(Collection collection, ListNBT tagList, Function nbtToElement) {
      collection.clear();

      for(int i = 0; i < tagList.size(); ++i) {
         CompoundNBT nbt = tagList.func_150305_b(i);
         Object element = nbtToElement.apply(nbt);
         if (element != null) {
            collection.add(element);
         }
      }

   }

   public static void loadCollectionFromPrimitiveListNBT(Collection collection, ListNBT tagList, BiFunction listNbtToPrimitive, Function primitiveToElement) {
      collection.clear();

      for(int i = 0; i < tagList.size(); ++i) {
         Object value = listNbtToPrimitive.apply(tagList, i);
         Object element = primitiveToElement.apply(value);
         if (element != null) {
            collection.add(element);
         }
      }

   }

   public static ListNBT saveMapAsListNBT(Map map, TriConsumer entryToNbt) {
      ListNBT tagList = new ListNBT();
      Iterator var3 = map.entrySet().iterator();

      while(var3.hasNext()) {
         Entry e = (Entry)var3.next();
         Object key = e.getKey();
         Object value = e.getValue();
         CompoundNBT nbt = new CompoundNBT();
         entryToNbt.accept(nbt, key, value);
         tagList.add(nbt);
      }

      return tagList;
   }

   public static void loadMapFromListNBT(Map map, ListNBT tagList, Function nbtToEntry) {
      map.clear();

      for(int i = 0; i < tagList.size(); ++i) {
         CompoundNBT nbt = tagList.func_150305_b(i);
         Pair entry = (Pair)nbtToEntry.apply(nbt);
         if (entry != null) {
            map.put(entry.getKey(), entry.getValue());
         }
      }

   }

   public static void writeOptionalToNBT(CompoundNBT nbt, String key, Optional opt, TriConsumer nbtPutter) {
      opt.ifPresent((val) -> {
         nbtPutter.accept(nbt, key, val);
      });
   }

   public static Optional readOptionalFromNBT(CompoundNBT nbt, String key, BiFunction nbtGetter) {
      return nbt.func_74764_b(key) ? Optional.ofNullable(nbtGetter.apply(nbt, key)) : Optional.empty();
   }

   public static void putResourceLocation(CompoundNBT nbt, String key, ResourceLocation value) {
      nbt.func_74778_a(key, value.toString());
   }

   public static ResourceLocation getResourceLocation(CompoundNBT nbt, String key) {
      String resString = nbt.func_74779_i(key);

      try {
         return new ResourceLocation(resString);
      } catch (ResourceLocationException var4) {
         LOTRLog.error("Invalid resourcelocation string '%s' for NBT key '%s'", resString, key);
         var4.printStackTrace();
         return null;
      }
   }

   public static void writeOptionalFactionPointerToNBT(CompoundNBT nbt, String key, Optional pointer) {
      Optional optName = pointer.map(FactionPointer::getName);
      writeOptionalToNBT(nbt, key, optName, DataUtil::putResourceLocation);
   }

   public static Optional readOptionalFactionPointerFromNBT(CompoundNBT nbt, String key) {
      Optional optName = readOptionalFromNBT(nbt, key, DataUtil::getResourceLocation);
      return optName.map(FactionPointer::of);
   }

   public static void writeCollectionToBuffer(PacketBuffer buf, Collection collection, Consumer elementToBuffer) {
      buf.func_150787_b(collection.size());
      collection.forEach(elementToBuffer);
   }

   public static void fillCollectionFromBuffer(PacketBuffer buf, Collection collection, Supplier bufferToElement) {
      collection.clear();
      int collectionSize = buf.func_150792_a();

      for(int i = 0; i < collectionSize; ++i) {
         Object element = bufferToElement.get();
         if (element != null) {
            collection.add(element);
         }
      }

   }

   public static Collection readNewCollectionFromBuffer(PacketBuffer buf, Supplier collectionSupplier, Supplier bufferToElement) {
      Collection collection = (Collection)collectionSupplier.get();
      fillCollectionFromBuffer(buf, collection, bufferToElement);
      return collection;
   }

   public static void writeMapToBuffer(PacketBuffer buf, Map map, BiConsumer entryToBuffer) {
      buf.func_150787_b(map.size());
      map.forEach(entryToBuffer);
   }

   public static void fillMapFromBuffer(PacketBuffer buf, Map map, Supplier bufferToEntry) {
      map.clear();
      int mapSize = buf.func_150792_a();

      for(int i = 0; i < mapSize; ++i) {
         Pair entry = (Pair)bufferToEntry.get();
         if (entry != null) {
            map.put(entry.getKey(), entry.getValue());
         }
      }

   }

   public static Map readNewMapFromBuffer(PacketBuffer buf, Supplier mapSupplier, Supplier bufferToEntry) {
      Map map = (Map)mapSupplier.get();
      fillMapFromBuffer(buf, map, bufferToEntry);
      return map;
   }

   public static void writeNullableToBuffer(PacketBuffer buf, Object object, Runnable elementToBuffer) {
      boolean hasObject = object != null;
      buf.writeBoolean(hasObject);
      if (hasObject) {
         elementToBuffer.run();
      }

   }

   public static void writeNullableToBuffer(PacketBuffer buf, Object object, BiFunction elementToBuffer) {
      writeNullableToBuffer(buf, object, () -> {
         PacketBuffer var10000 = (PacketBuffer)elementToBuffer.apply(buf, object);
      });
   }

   public static void writeNullableToBuffer(PacketBuffer buf, Object object, BiConsumer elementToBuffer) {
      writeNullableToBuffer(buf, object, () -> {
         elementToBuffer.accept(object, buf);
      });
   }

   public static Object readNullableFromBuffer(PacketBuffer buf, Supplier bufferToObject) {
      boolean hasObject = buf.readBoolean();
      return hasObject ? bufferToObject.get() : null;
   }

   public static Object readNullableFromBuffer(PacketBuffer buf, Function bufferToObject) {
      return readNullableFromBuffer(buf, () -> {
         return bufferToObject.apply(buf);
      });
   }
}
