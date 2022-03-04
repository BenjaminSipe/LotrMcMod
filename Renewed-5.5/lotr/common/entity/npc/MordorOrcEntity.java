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
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.IItemProvider;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class MordorOrcEntity extends OrcEntity {
   private static final SpawnEquipmentTable WEAPONS;

   public MordorOrcEntity(EntityType type, World w) {
      super(type, w);
   }

   public static MutableAttribute regAttrs() {
      return OrcEntity.regAttrs().func_233815_a_(Attributes.field_233818_a_, 20.0D);
   }

   protected Goal createOrcAttackGoal() {
      return new NPCMeleeAttackGoal(this, 1.4D);
   }

   public ILivingEntityData func_213386_a(IServerWorld sw, DifficultyInstance diff, SpawnReason reason, ILivingEntityData spawnData, CompoundNBT dataTag) {
      spawnData = super.func_213386_a(sw, diff, reason, spawnData, dataTag);
      this.npcItemsInv.setMeleeWeapon(WEAPONS.getRandomItem(this.field_70146_Z));
      this.npcItemsInv.setIdleItemsFromMeleeWeapons();
      this.func_184201_a(EquipmentSlotType.FEET, new ItemStack((IItemProvider)LOTRItems.MORDOR_BOOTS.get()));
      this.func_184201_a(EquipmentSlotType.LEGS, new ItemStack((IItemProvider)LOTRItems.MORDOR_LEGGINGS.get()));
      this.func_184201_a(EquipmentSlotType.CHEST, new ItemStack((IItemProvider)LOTRItems.MORDOR_CHESTPLATE.get()));
      if (this.field_70146_Z.nextInt(5) != 0) {
         this.func_184201_a(EquipmentSlotType.HEAD, new ItemStack((IItemProvider)LOTRItems.MORDOR_HELMET.get()));
      }

      if (this.field_70146_Z.nextFloat() < 0.5F) {
         this.func_184201_a(EquipmentSlotType.OFFHAND, new ItemStack((IItemProvider)LOTRItems.MORDOR_SHIELD.get()));
      }

      return spawnData;
   }

   static {
      WEAPONS = SpawnEquipmentTable.of(LOTRItems.MORDOR_SCIMITAR, LOTRItems.MORDOR_DAGGER, LOTRItems.MORDOR_DAGGER, LOTRItems.MORDOR_SCIMITAR, LOTRItems.MORDOR_SCIMITAR, LOTRItems.MORDOR_PICKAXE, LOTRItems.MORDOR_AXE, LOTRItems.MORDOR_SCIMITAR);
   }
}
