package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.fac.LOTRFaction;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityUrukWarg extends LOTREntityWarg {
   public LOTREntityUrukWarg(World world) {
      super(world);
   }

   public LOTREntityNPC createWargRider() {
      if (this.field_70146_Z.nextBoolean()) {
         this.setWargArmor(new ItemStack(LOTRMod.wargArmorUruk));
      }

      return (LOTREntityNPC)(this.field_70170_p.field_73012_v.nextBoolean() ? new LOTREntityIsengardSnagaArcher(this.field_70170_p) : new LOTREntityIsengardSnaga(this.field_70170_p));
   }

   public LOTRFaction getFaction() {
      return LOTRFaction.ISENGARD;
   }

   public float getAlignmentBonus() {
      return 2.0F;
   }
}
