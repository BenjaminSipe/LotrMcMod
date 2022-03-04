package lotr.common.entity.npc;

import lotr.common.entity.npc.ai.goal.NPCMeleeAttackGoal;
import lotr.common.entity.npc.data.NPCGenderProvider;
import lotr.common.init.LOTRItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class DaleSoldierEntity extends DaleManEntity {
   private static final SpawnEquipmentTable WEAPONS;

   public DaleSoldierEntity(EntityType type, World w) {
      super(type, w);
   }

   protected NPCGenderProvider getGenderProvider() {
      return NPCGenderProvider.MALE;
   }

   protected ITextComponent formatNPCName(ITextComponent npcName, ITextComponent typeName) {
      return this.formatGenericNPCName(npcName, typeName);
   }

   protected Goal createAttackGoal() {
      return new NPCMeleeAttackGoal(this, 1.5D);
   }

   protected void addNPCTargetingAI() {
      this.addAggressiveTargetingGoals();
   }

   public ILivingEntityData func_213386_a(IServerWorld sw, DifficultyInstance diff, SpawnReason reason, ILivingEntityData spawnData, CompoundNBT dataTag) {
      spawnData = super.func_213386_a(sw, diff, reason, spawnData, dataTag);
      this.npcItemsInv.setMeleeWeapon(WEAPONS.getRandomItem(this.field_70146_Z));
      this.npcItemsInv.setIdleItemsFromMeleeWeapons();
      this.func_184201_a(EquipmentSlotType.FEET, new ItemStack((IItemProvider)LOTRItems.DALE_BOOTS.get()));
      this.func_184201_a(EquipmentSlotType.LEGS, new ItemStack((IItemProvider)LOTRItems.DALE_LEGGINGS.get()));
      this.func_184201_a(EquipmentSlotType.CHEST, new ItemStack((IItemProvider)LOTRItems.DALE_CHESTPLATE.get()));
      if (this.field_70146_Z.nextInt(10) != 0) {
         this.func_184201_a(EquipmentSlotType.HEAD, new ItemStack((IItemProvider)LOTRItems.DALE_HELMET.get()));
      }

      this.func_184201_a(EquipmentSlotType.OFFHAND, new ItemStack((IItemProvider)LOTRItems.DALE_SHIELD.get()));
      return spawnData;
   }

   static {
      WEAPONS = SpawnEquipmentTable.of(LOTRItems.DALE_SWORD, LOTRItems.DALE_SWORD, LOTRItems.DALE_SWORD, LOTRItems.DALE_SWORD, LOTRItems.DALE_SWORD, LOTRItems.DALE_SPEAR);
   }
}
