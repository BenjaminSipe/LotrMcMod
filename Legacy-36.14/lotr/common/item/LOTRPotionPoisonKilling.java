package lotr.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRDamage;
import lotr.common.LOTRMod;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class LOTRPotionPoisonKilling extends Potion {
   public LOTRPotionPoisonKilling() {
      super(30, true, Potion.field_76436_u.func_76401_j());
      this.func_76390_b("potion.lotr.drinkPoison");
      this.func_76404_a(Potion.field_76436_u.func_76388_g());
      this.func_76399_b(0, 0);
   }

   public void func_76394_a(EntityLivingBase entity, int level) {
      entity.func_70097_a(LOTRDamage.poisonDrink, 1.0F);
   }

   public boolean func_76397_a(int tick, int level) {
      int freq = 5 >> level;
      return freq > 0 ? tick % freq == 0 : true;
   }

   @SideOnly(Side.CLIENT)
   public boolean func_76400_d() {
      return false;
   }

   @SideOnly(Side.CLIENT)
   public void renderInventoryEffect(int x, int y, PotionEffect effect, Minecraft mc) {
      LOTRMod.proxy.renderCustomPotionEffect(x, y, effect, mc);
   }
}
