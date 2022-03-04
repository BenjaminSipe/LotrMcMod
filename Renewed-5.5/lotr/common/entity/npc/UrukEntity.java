package lotr.common.entity.npc;

import lotr.common.entity.npc.ai.goal.NPCMeleeAttackGoal;
import lotr.common.init.LOTRItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.AttributeModifierMap.MutableAttribute;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.IItemProvider;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class UrukEntity extends OrcEntity {
   private static final SpawnEquipmentTable WEAPONS;

   public UrukEntity(EntityType type, World w) {
      super(type, w);
      this.isOrcWeakInSun = false;
   }

   public static MutableAttribute regAttrs() {
      return OrcEntity.regAttrs().func_233815_a_(Attributes.field_233818_a_, 26.0D).func_233815_a_(Attributes.field_233821_d_, 0.22D);
   }

   protected Goal createOrcAttackGoal() {
      return new NPCMeleeAttackGoal(this, 1.4D);
   }

   public ILivingEntityData func_213386_a(IServerWorld sw, DifficultyInstance diff, SpawnReason reason, ILivingEntityData spawnData, CompoundNBT dataTag) {
      spawnData = super.func_213386_a(sw, diff, reason, spawnData, dataTag);
      this.npcItemsInv.setMeleeWeapon(WEAPONS.getRandomItem(this.field_70146_Z));
      this.npcItemsInv.setIdleItemsFromMeleeWeapons();
      this.func_184201_a(EquipmentSlotType.FEET, new ItemStack((IItemProvider)LOTRItems.URUK_BOOTS.get()));
      this.func_184201_a(EquipmentSlotType.LEGS, new ItemStack((IItemProvider)LOTRItems.URUK_LEGGINGS.get()));
      this.func_184201_a(EquipmentSlotType.CHEST, new ItemStack((IItemProvider)LOTRItems.URUK_CHESTPLATE.get()));
      if (this.field_70146_Z.nextInt(10) != 0) {
         this.func_184201_a(EquipmentSlotType.HEAD, new ItemStack((IItemProvider)LOTRItems.URUK_HELMET.get()));
      }

      this.func_184201_a(EquipmentSlotType.OFFHAND, new ItemStack(Items.field_185159_cQ));
      return spawnData;
   }

   protected float func_70647_i() {
      return super.func_70647_i() * 0.75F;
   }

   static {
      WEAPONS = SpawnEquipmentTable.of(LOTRItems.URUK_CLEAVER, LOTRItems.URUK_CLEAVER, LOTRItems.URUK_CLEAVER, LOTRItems.URUK_CLEAVER, LOTRItems.URUK_CLEAVER, LOTRItems.URUK_DAGGER, LOTRItems.URUK_DAGGER, LOTRItems.URUK_CLEAVER, LOTRItems.URUK_SPEAR);
   }
}
