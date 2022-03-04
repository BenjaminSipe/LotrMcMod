package lotr.client.render.entity;

import lotr.client.model.LOTRModelTroll;
import lotr.common.entity.npc.LOTREntityMountainTrollChieftain;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRRenderMountainTrollChieftain extends LOTRRenderMountainTroll {
   private static ResourceLocation armorTexture = new ResourceLocation("lotr:mob/troll/mountainTrollChieftain_armor.png");
   private LOTRModelTroll helmetModel = new LOTRModelTroll(1.5F, 2);
   private LOTRModelTroll chestplateModel = new LOTRModelTroll(1.5F, 3);

   public void func_76986_a(Entity entity, double d, double d1, double d2, float f, float f1) {
      super.func_76986_a(entity, d, d1, d2, f, f1);
      LOTREntityMountainTrollChieftain troll = (LOTREntityMountainTrollChieftain)entity;
      if (troll.field_70175_ag) {
         BossStatus.func_82824_a(troll, false);
      }

   }

   protected int func_77032_a(EntityLivingBase entity, int pass, float f) {
      LOTREntityMountainTrollChieftain troll = (LOTREntityMountainTrollChieftain)entity;
      this.func_110776_a(armorTexture);
      if (pass == 2 && troll.getTrollArmorLevel() >= 2) {
         this.helmetModel.field_78095_p = this.field_77045_g.field_78095_p;
         this.func_77042_a(this.helmetModel);
         return 1;
      } else if (pass == 3 && troll.getTrollArmorLevel() >= 1) {
         this.chestplateModel.field_78095_p = this.field_77045_g.field_78095_p;
         this.func_77042_a(this.chestplateModel);
         return 1;
      } else {
         return super.func_77032_a(entity, pass, f);
      }
   }

   protected void func_77041_b(EntityLivingBase entity, float f) {
      super.func_77041_b(entity, f);
      GL11.glTranslatef(0.0F, -((LOTREntityMountainTrollChieftain)entity).getSpawningOffset(f), 0.0F);
   }
}
