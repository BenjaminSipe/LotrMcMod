package lotr.common.entity.npc;

import lotr.common.init.LOTRItems;
import lotr.common.init.LOTRMaterial;
import lotr.common.item.ManFleshItem;
import lotr.common.util.GameRuleUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.AttributeModifierMap.MutableAttribute;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TieredItem;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IItemProvider;
import net.minecraft.world.World;

public abstract class AbstractMannishEntity extends NPCEntity {
   public AbstractMannishEntity(EntityType type, World w) {
      super(type, w);
      this.spawnRequiresSurfaceBlock = true;
   }

   public static MutableAttribute regAttrs() {
      return NPCEntity.registerBaseNPCAttributes().func_233815_a_(Attributes.field_233818_a_, 20.0D).func_233815_a_(Attributes.field_233821_d_, 0.2D);
   }

   public void func_70645_a(DamageSource damagesource) {
      super.func_70645_a(damagesource);
      if (!this.field_70170_p.field_72995_K && GameRuleUtil.canDropLoot(this.field_70170_p) && this.field_70146_Z.nextInt(5) == 0) {
         Entity killer = damagesource.func_76346_g();
         if (killer instanceof LivingEntity) {
            LivingEntity livingKiller = (LivingEntity)killer;
            if (ManFleshItem.isManFleshAligned(livingKiller)) {
               ItemStack itemstack = livingKiller.func_184614_ca();
               if (!itemstack.func_190926_b()) {
                  Item item = itemstack.func_77973_b();
                  IItemTier material = null;
                  if (item instanceof TieredItem) {
                     material = ((TieredItem)item).func_200891_e();
                  }

                  if (material != null) {
                     boolean canHarvest = (Boolean)LOTRMaterial.ifLOTRToolMaterial(material).map(LOTRMaterial.AsTool::canHarvestManFlesh).orElse(false);
                     if (canHarvest) {
                        ItemStack flesh = new ItemStack((IItemProvider)LOTRItems.MAN_FLESH.get(), 1 + this.field_70146_Z.nextInt(2));
                        this.func_70099_a(flesh, 0.0F);
                     }
                  }
               }
            }
         }
      }

   }
}
