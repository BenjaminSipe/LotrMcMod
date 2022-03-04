package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.fac.LOTRFaction;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityMordorWarg extends LOTREntityWarg {
   public LOTREntityMordorWarg(World world) {
      super(world);
   }

   protected void func_70088_a() {
      super.func_70088_a();
      this.setWargType(LOTREntityWarg.WargType.BLACK);
   }

   public LOTREntityNPC createWargRider() {
      if (this.field_70146_Z.nextBoolean()) {
         this.setWargArmor(new ItemStack(LOTRMod.wargArmorMordor));
      }

      return (LOTREntityNPC)(this.field_70170_p.field_73012_v.nextBoolean() ? new LOTREntityMordorOrcArcher(this.field_70170_p) : new LOTREntityMordorOrc(this.field_70170_p));
   }

   public LOTRFaction getFaction() {
      return LOTRFaction.MORDOR;
   }

   public float getAlignmentBonus() {
      return 2.0F;
   }
}
