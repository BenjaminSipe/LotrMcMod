package lotr.common.recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import lotr.common.LOTRLog;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;

public class FactionTableType implements FactionBasedRecipeType {
   public final ResourceLocation recipeTypeName;
   private final Supplier blockIconSupplier;
   private final List associatedMultiTableTypes = new ArrayList();
   /** @deprecated */
   @Deprecated
   public final String recipeID;

   public FactionTableType(ResourceLocation name, Supplier blockSup) {
      this.recipeTypeName = name;
      this.recipeID = this.recipeTypeName.toString();
      this.blockIconSupplier = blockSup;
   }

   public String toString() {
      return this.recipeTypeName.toString();
   }

   public ItemStack getFactionTableIcon() {
      return new ItemStack((IItemProvider)this.blockIconSupplier.get());
   }

   /** @deprecated */
   @Deprecated
   public ItemStack getIcon() {
      return this.getFactionTableIcon();
   }

   protected void registerMultiTableType(MultiTableType t) {
      if (!t.includesFactionType(this)) {
         throw new IllegalArgumentException("Invalid - multi table type " + t.toString() + " does not include faction table " + this.toString() + "!");
      } else {
         if (!this.associatedMultiTableTypes.contains(t)) {
            this.associatedMultiTableTypes.add(t);
         } else {
            LOTRLog.warn("Faction table type %s already includes multi table type %s", this.toString(), t.toString());
         }

      }
   }

   public List getMultiTableTypes() {
      return new ArrayList(this.associatedMultiTableTypes);
   }
}
