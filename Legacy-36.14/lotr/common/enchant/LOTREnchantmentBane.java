package lotr.common.enchant;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import lotr.common.LOTRConfig;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.world.LOTRWorldProviderUtumno;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class LOTREnchantmentBane extends LOTREnchantmentDamage {
   private List entityClasses;
   private EnumCreatureAttribute entityAttribute;
   public final float baneDamage;
   public boolean isAchievable;

   private LOTREnchantmentBane(String s, float boost) {
      super(s, 0.0F);
      this.isAchievable = true;
      this.baneDamage = boost;
      this.setValueModifier((10.0F + this.baneDamage) / 10.0F);
      this.setPersistsReforge();
      this.setBypassAnvilLimit();
   }

   public LOTREnchantmentBane(String s, float boost, Class... classes) {
      this(s, boost);
      this.entityClasses = Arrays.asList(classes);
   }

   public LOTREnchantmentBane(String s, float boost, EnumCreatureAttribute attr) {
      this(s, boost);
      this.entityAttribute = attr;
   }

   public LOTREnchantmentBane setUnachievable() {
      this.isAchievable = false;
      return this;
   }

   private boolean isEntityType(EntityLivingBase entity) {
      if (this.entityClasses != null) {
         Iterator var2 = this.entityClasses.iterator();

         while(var2.hasNext()) {
            Class cls = (Class)var2.next();
            if (cls.isAssignableFrom(entity.getClass())) {
               return true;
            }
         }
      } else if (this.entityAttribute != null) {
         return entity.func_70668_bt() == this.entityAttribute;
      }

      return false;
   }

   public boolean doesEntityKillCountTowardsBane(EntityLivingBase entity, World world) {
      if (!LOTRConfig.hiredUnitKillsCountForBane && entity instanceof LOTREntityNPC && ((LOTREntityNPC)entity).hiredNPCInfo.isActive) {
         return false;
      } else {
         return world.field_73011_w instanceof LOTRWorldProviderUtumno ? false : this.isEntityType(entity);
      }
   }

   public float getBaseDamageBoost() {
      return 0.0F;
   }

   public float getEntitySpecificDamage(EntityLivingBase entity) {
      return this.isEntityType(entity) ? this.baneDamage : 0.0F;
   }

   public int getRandomKillsRequired(Random random) {
      return MathHelper.func_76136_a(random, 100, 250);
   }

   public String getDescription(ItemStack itemstack) {
      return StatCollector.func_74837_a("lotr.enchant." + this.enchantName + ".desc", new Object[]{this.formatAdditive(this.baneDamage)});
   }

   public boolean isBeneficial() {
      return true;
   }
}
