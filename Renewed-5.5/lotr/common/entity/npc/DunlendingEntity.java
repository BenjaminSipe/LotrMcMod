package lotr.common.entity.npc;

import lotr.common.entity.npc.ai.goal.NPCMeleeAttackGoal;
import lotr.common.entity.npc.data.NPCFoodPool;
import lotr.common.entity.npc.data.NPCFoodPools;
import lotr.common.entity.npc.data.name.NPCNameGenerator;
import lotr.common.entity.npc.data.name.NPCNameGenerators;
import lotr.common.init.LOTRItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.IItemProvider;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class DunlendingEntity extends ManEntity {
   private static final SpawnEquipmentTable WEAPONS;

   public DunlendingEntity(EntityType type, World w) {
      super(type, w);
   }

   protected NPCNameGenerator getNameGenerator() {
      return NPCNameGenerators.DUNLENDING;
   }

   protected Goal createAttackGoal() {
      return new NPCMeleeAttackGoal(this, 1.5D);
   }

   protected void addNPCTargetingAI() {
      this.addAggressiveTargetingGoals();
   }

   protected NPCFoodPool getEatPool() {
      return NPCFoodPools.DUNLENDING;
   }

   protected NPCFoodPool getDrinkPool() {
      return NPCFoodPools.DUNLENDING_DRINK;
   }

   public ILivingEntityData func_213386_a(IServerWorld sw, DifficultyInstance diff, SpawnReason reason, ILivingEntityData spawnData, CompoundNBT dataTag) {
      spawnData = super.func_213386_a(sw, diff, reason, spawnData, dataTag);
      this.npcItemsInv.setMeleeWeapon(WEAPONS.getRandomItem(this.field_70146_Z));
      this.npcItemsInv.setIdleItemsFromMeleeWeapons();
      if (this.field_70146_Z.nextInt(4) == 0) {
         this.func_184201_a(EquipmentSlotType.HEAD, new ItemStack((IItemProvider)LOTRItems.FUR_HELMET.get()));
      }

      return spawnData;
   }

   static {
      WEAPONS = SpawnEquipmentTable.of(LOTRItems.DUNLENDING_CLUB, LOTRItems.DUNLENDING_CLUB, LOTRItems.DUNLENDING_CLUB, Items.field_151052_q, Items.field_151049_t, LOTRItems.STONE_DAGGER, LOTRItems.STONE_SPEAR, LOTRItems.BRONZE_AXE, LOTRItems.BRONZE_DAGGER, LOTRItems.BRONZE_SPEAR, Items.field_151036_c, LOTRItems.IRON_DAGGER, LOTRItems.IRON_SPEAR);
   }
}
