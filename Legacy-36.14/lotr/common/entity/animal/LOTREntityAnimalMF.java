package lotr.common.entity.animal;

import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntities;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public abstract class LOTREntityAnimalMF extends EntityAnimal {
   public LOTREntityAnimalMF(World world) {
      super(world);
   }

   public abstract Class getAnimalMFBaseClass();

   public abstract boolean isMale();

   public boolean func_70878_b(EntityAnimal mate) {
      LOTREntityAnimalMF mfMate = (LOTREntityAnimalMF)mate;
      if (mate == this) {
         return false;
      } else if (this.getAnimalMFBaseClass().equals(mfMate.getAnimalMFBaseClass()) && this.func_70880_s() && mate.func_70880_s()) {
         boolean thisMale = this.isMale();
         boolean otherMale = mfMate.isMale();
         return thisMale != otherMale;
      } else {
         return false;
      }
   }

   public ItemStack getPickedResult(MovingObjectPosition target) {
      return new ItemStack(LOTRMod.spawnEgg, 1, LOTREntities.getEntityID(this));
   }
}
