package lotr.common.entity.npc;

import lotr.common.entity.npc.ai.goal.HobbitSmokeGoal;
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
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class BreeManEntity extends ManEntity {
   private static final SpawnEquipmentTable WEAPONS;
   public static final String CARROT_EATER_NAME = "Peter Jackson";

   public BreeManEntity(EntityType type, World w) {
      super(type, w);
   }

   protected NPCNameGenerator getNameGenerator() {
      return NPCNameGenerators.BREE;
   }

   protected Goal createAttackGoal() {
      return new NPCMeleeAttackGoal(this, 1.3D);
   }

   protected void addNPCTargetingAI() {
      this.addNonAggressiveTargetingGoals();
   }

   protected NPCFoodPool getEatPool() {
      return NPCFoodPools.BREE;
   }

   protected NPCFoodPool getDrinkPool() {
      return NPCFoodPools.BREE_DRINK;
   }

   protected void addConsumingGoals(int prio) {
      super.addConsumingGoals(prio);
      this.field_70714_bg.func_75776_a(prio, new HobbitSmokeGoal(this, 12000));
   }

   public ILivingEntityData func_213386_a(IServerWorld sw, DifficultyInstance diff, SpawnReason reason, ILivingEntityData spawnData, CompoundNBT dataTag) {
      spawnData = super.func_213386_a(sw, diff, reason, spawnData, dataTag);
      this.npcItemsInv.setMeleeWeapon(WEAPONS.getRandomItem(this.field_70146_Z));
      this.npcItemsInv.clearIdleItem();
      if (this.personalInfo.isMale() && this.field_70146_Z.nextInt(2000) == 0) {
         this.personalInfo.setName("Peter Jackson");
         this.npcItemsInv.setIdleItem(new ItemStack(Items.field_151172_bF));
      }

      return spawnData;
   }

   static {
      WEAPONS = SpawnEquipmentTable.of(LOTRItems.IRON_DAGGER, LOTRItems.BRONZE_DAGGER, Items.field_151036_c, LOTRItems.BRONZE_AXE, Items.field_151049_t);
   }
}
