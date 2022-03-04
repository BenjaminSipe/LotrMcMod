package lotr.common.entity.npc;

import net.minecraft.world.World;

public class LOTREntityUtumnoObsidianWarg extends LOTREntityUtumnoWarg {
   public LOTREntityUtumnoObsidianWarg(World world) {
      super(world);
   }

   public void func_70088_a() {
      super.func_70088_a();
      this.setWargType(LOTREntityWarg.WargType.OBSIDIAN);
   }
}
