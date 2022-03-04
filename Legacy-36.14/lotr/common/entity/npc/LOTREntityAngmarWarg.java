package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.fac.LOTRFaction;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityAngmarWarg extends LOTREntityWarg {
   public LOTREntityAngmarWarg(World world) {
      super(world);
   }

   public LOTREntityNPC createWargRider() {
      if (this.field_70146_Z.nextBoolean()) {
         this.setWargArmor(new ItemStack(LOTRMod.wargArmorAngmar));
      }

      return (LOTREntityNPC)(this.field_70170_p.field_73012_v.nextBoolean() ? new LOTREntityAngmarOrcArcher(this.field_70170_p) : new LOTREntityAngmarOrc(this.field_70170_p));
   }

   public LOTRFaction getFaction() {
      return LOTRFaction.ANGMAR;
   }

   public float getAlignmentBonus() {
      return 2.0F;
   }
}
