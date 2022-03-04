package lotr.client.gui;

import lotr.common.entity.npc.LOTREntityNPC;

public abstract class LOTRGuiNPCInteract extends LOTRGuiScreenBase {
   protected LOTREntityNPC theEntity;

   public LOTRGuiNPCInteract(LOTREntityNPC entity) {
      this.theEntity = entity;
   }

   public void func_73863_a(int i, int j, float f) {
      this.func_146276_q_();
      String s = this.theEntity.func_70005_c_();
      this.field_146289_q.func_78276_b(s, (this.field_146294_l - this.field_146289_q.func_78256_a(s)) / 2, this.field_146295_m / 5 * 3 - 20, 16777215);
      super.func_73863_a(i, j, f);
   }

   public void func_73876_c() {
      super.func_73876_c();
      if (this.theEntity == null || !this.theEntity.func_70089_S() || this.theEntity.func_70068_e(this.field_146297_k.field_71439_g) > 100.0D) {
         this.field_146297_k.field_71439_g.func_71053_j();
      }

   }
}
