package lotr.common.entity.npc;

import lotr.common.entity.npc.ai.goal.NPCMeleeAttackGoal;
import lotr.common.entity.npc.ai.goal.NPCRangedAttackGoal;
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

public class GaladhrimWarriorEntity extends GaladhrimElfEntity {
   private static final SpawnEquipmentTable WEAPONS;
   private boolean isDefendingTree;

   public GaladhrimWarriorEntity(EntityType type, World w) {
      super(type, w);
   }

   public static MutableAttribute regAttrs() {
      return GaladhrimElfEntity.regAttrs().func_233815_a_(Attributes.field_233819_b_, 24.0D);
   }

   protected Goal createElfRangedAttackGoal() {
      return new NPCRangedAttackGoal(this, 1.25D, 20, 24.0F);
   }

   protected Goal createElfMeleeAttackGoal() {
      return new NPCMeleeAttackGoal(this, 1.4D);
   }

   public ILivingEntityData func_213386_a(IServerWorld sw, DifficultyInstance diff, SpawnReason reason, ILivingEntityData spawnData, CompoundNBT dataTag) {
      spawnData = super.func_213386_a(sw, diff, reason, spawnData, dataTag);
      this.npcItemsInv.setMeleeWeapon(WEAPONS.getRandomItem(this.field_70146_Z));
      this.npcItemsInv.setRangedWeapon(new ItemStack(Items.field_151031_f));
      this.npcItemsInv.setIdleItemsFromMeleeWeapons();
      this.func_184201_a(EquipmentSlotType.FEET, new ItemStack((IItemProvider)LOTRItems.GALADHRIM_BOOTS.get()));
      this.func_184201_a(EquipmentSlotType.LEGS, new ItemStack((IItemProvider)LOTRItems.GALADHRIM_LEGGINGS.get()));
      this.func_184201_a(EquipmentSlotType.CHEST, new ItemStack((IItemProvider)LOTRItems.GALADHRIM_CHESTPLATE.get()));
      if (this.field_70146_Z.nextInt(10) != 0) {
         this.func_184201_a(EquipmentSlotType.HEAD, new ItemStack((IItemProvider)LOTRItems.GALADHRIM_HELMET.get()));
      }

      this.func_184201_a(EquipmentSlotType.OFFHAND, new ItemStack(Items.field_185159_cQ));
      return spawnData;
   }

   public void setDefendingTree() {
      this.isDefendingTree = true;
   }

   public void func_213281_b(CompoundNBT nbt) {
      super.func_213281_b(nbt);
      nbt.func_74757_a("DefendingTree", this.isDefendingTree);
   }

   public void func_70037_a(CompoundNBT nbt) {
      super.func_70037_a(nbt);
      this.isDefendingTree = nbt.func_74767_n("DefendingTree");
   }

   static {
      WEAPONS = SpawnEquipmentTable.of(LOTRItems.GALADHRIM_SWORD, LOTRItems.GALADHRIM_SWORD, LOTRItems.GALADHRIM_SWORD, LOTRItems.GALADHRIM_SWORD, LOTRItems.GALADHRIM_SWORD, LOTRItems.GALADHRIM_SWORD, LOTRItems.GALADHRIM_SPEAR);
   }
}
