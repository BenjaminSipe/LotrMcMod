package lotr.common.recipe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class MultiTableType implements FactionBasedRecipeType {
   public final ResourceLocation recipeTypeName;
   private final List tableTypes;
   private static final Random rand = new Random();
   private FactionTableType randTableType;
   private long lastRandomTime;

   public MultiTableType(ResourceLocation name, List types) {
      this.recipeTypeName = name;
      this.tableTypes = new ArrayList(types);
      Iterator var3 = types.iterator();

      while(var3.hasNext()) {
         FactionTableType facType = (FactionTableType)var3.next();
         facType.registerMultiTableType(this);
      }

   }

   public String toString() {
      return this.recipeTypeName.toString();
   }

   public boolean includesFactionType(FactionTableType type) {
      return this.tableTypes.contains(type);
   }

   public ItemStack getFactionTableIcon() {
      if (this.randTableType == null || System.currentTimeMillis() - this.lastRandomTime > 1000L) {
         this.randTableType = (FactionTableType)this.tableTypes.get(rand.nextInt(this.tableTypes.size()));
         this.lastRandomTime = System.currentTimeMillis();
      }

      return this.randTableType.getFactionTableIcon();
   }
}
