package lotr.common.entity.npc;

import lotr.common.entity.npc.ai.AttackGoalsHolder;
import lotr.common.entity.npc.ai.StandardAttackModeUpdaters;
import lotr.common.entity.npc.ai.goal.NPCRangedAttackGoal;
import lotr.common.init.LOTRItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class DunlendingBowmanEntity extends DunlendingWarriorEntity {
   private static final SpawnEquipmentTable DAGGERS;

   public DunlendingBowmanEntity(EntityType type, World w) {
      super(type, w);
      this.getNPCCombatUpdater().setAttackModeUpdater(StandardAttackModeUpdaters.meleeRangedSwitching());
   }

   protected void initialiseAttackGoals(AttackGoalsHolder holder) {
      super.initialiseAttackGoals(holder);
      holder.setRangedAttackGoal(new NPCRangedAttackGoal(this, 1.4D, 20, 16.0F));
   }

   public ILivingEntityData func_213386_a(IServerWorld sw, DifficultyInstance diff, SpawnReason reason, ILivingEntityData spawnData, CompoundNBT dataTag) {
      spawnData = super.func_213386_a(sw, diff, reason, spawnData, dataTag);
      this.npcItemsInv.setRangedWeapon(new ItemStack(Items.field_151031_f));
      this.npcItemsInv.setMeleeWeapon(DAGGERS.getRandomItem(this.field_70146_Z));
      this.npcItemsInv.setIdleItemsFromRangedWeapons();
      this.func_184201_a(EquipmentSlotType.OFFHAND, ItemStack.field_190927_a);
      return spawnData;
   }

   static {
      DAGGERS = SpawnEquipmentTable.of(LOTRItems.IRON_DAGGER, LOTRItems.BRONZE_DAGGER);
   }
}
