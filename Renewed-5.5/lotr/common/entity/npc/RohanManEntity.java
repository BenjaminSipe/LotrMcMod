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
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class RohanManEntity extends ManEntity {
   private static final SpawnEquipmentTable WEAPONS;

   public RohanManEntity(EntityType type, World w) {
      super(type, w);
   }

   protected NPCNameGenerator getNameGenerator() {
      return NPCNameGenerators.ROHAN;
   }

   protected ITextComponent formatNPCName(ITextComponent npcName, ITextComponent typeName) {
      return new TranslationTextComponent("entityname.lotr.rohan_man", new Object[]{npcName, typeName});
   }

   protected Goal createAttackGoal() {
      return new NPCMeleeAttackGoal(this, 1.4D);
   }

   protected void addNPCTargetingAI() {
      this.addNonAggressiveTargetingGoals();
   }

   protected NPCFoodPool getEatPool() {
      return NPCFoodPools.ROHAN;
   }

   protected NPCFoodPool getDrinkPool() {
      return NPCFoodPools.ROHAN_DRINK;
   }

   public ILivingEntityData func_213386_a(IServerWorld sw, DifficultyInstance diff, SpawnReason reason, ILivingEntityData spawnData, CompoundNBT dataTag) {
      spawnData = super.func_213386_a(sw, diff, reason, spawnData, dataTag);
      this.npcItemsInv.setMeleeWeapon(WEAPONS.getRandomItem(this.field_70146_Z));
      this.npcItemsInv.clearIdleItem();
      return spawnData;
   }

   static {
      WEAPONS = SpawnEquipmentTable.of(LOTRItems.ROHAN_DAGGER, LOTRItems.IRON_DAGGER, LOTRItems.BRONZE_DAGGER, Items.field_151036_c, LOTRItems.BRONZE_AXE, Items.field_151049_t);
   }
}
