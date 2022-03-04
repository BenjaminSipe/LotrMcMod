package lotr.common.entity.npc;

import lotr.common.entity.npc.ai.StandardAttackModeUpdaters;
import lotr.common.entity.npc.ai.goal.NPCMeleeAttackGoal;
import lotr.common.entity.npc.data.NPCGenderProvider;
import lotr.common.init.LOTRAttributes;
import lotr.common.init.LOTRItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifierMap.MutableAttribute;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class RohirrimWarriorEntity extends RohanManEntity {
   private static final SpawnEquipmentTable WEAPONS;

   public RohirrimWarriorEntity(EntityType type, World w) {
      super(type, w);
      this.getNPCCombatUpdater().setAttackModeUpdater(StandardAttackModeUpdaters.mountableMeleeOnly());
   }

   protected NPCGenderProvider getGenderProvider() {
      return NPCGenderProvider.MALE;
   }

   protected ITextComponent formatNPCName(ITextComponent npcName, ITextComponent typeName) {
      return this.formatGenericNPCName(npcName, typeName);
   }

   public static MutableAttribute regAttrs() {
      return RohanManEntity.regAttrs().func_233815_a_((Attribute)LOTRAttributes.NPC_RANGED_INACCURACY.get(), 0.75D).func_233815_a_((Attribute)LOTRAttributes.NPC_MOUNT_ATTACK_SPEED.get(), 2.0D);
   }

   protected Goal createAttackGoal() {
      return new NPCMeleeAttackGoal(this, 1.45D);
   }

   protected void addNPCTargetingAI() {
      this.addAggressiveTargetingGoals();
   }

   public ILivingEntityData func_213386_a(IServerWorld sw, DifficultyInstance diff, SpawnReason reason, ILivingEntityData spawnData, CompoundNBT dataTag) {
      spawnData = super.func_213386_a(sw, diff, reason, spawnData, dataTag);
      this.npcItemsInv.setMeleeWeapon(WEAPONS.getRandomItem(this.field_70146_Z));
      this.npcItemsInv.setIdleItemsFromMeleeWeapons();
      this.func_184201_a(EquipmentSlotType.FEET, new ItemStack((IItemProvider)LOTRItems.ROHAN_BOOTS.get()));
      this.func_184201_a(EquipmentSlotType.LEGS, new ItemStack((IItemProvider)LOTRItems.ROHAN_LEGGINGS.get()));
      this.func_184201_a(EquipmentSlotType.CHEST, new ItemStack((IItemProvider)LOTRItems.ROHAN_CHESTPLATE.get()));
      this.func_184201_a(EquipmentSlotType.HEAD, new ItemStack((IItemProvider)LOTRItems.ROHAN_HELMET.get()));
      this.func_184201_a(EquipmentSlotType.OFFHAND, new ItemStack((IItemProvider)LOTRItems.ROHAN_SHIELD.get()));
      return spawnData;
   }

   static {
      WEAPONS = SpawnEquipmentTable.of(LOTRItems.ROHAN_SWORD, LOTRItems.ROHAN_SWORD, LOTRItems.ROHAN_SWORD);
   }
}
