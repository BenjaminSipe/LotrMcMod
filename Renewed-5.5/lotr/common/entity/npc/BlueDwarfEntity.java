package lotr.common.entity.npc;

import lotr.common.entity.npc.data.NPCFoodPool;
import lotr.common.entity.npc.data.NPCFoodPools;
import lotr.common.init.LOTRItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class BlueDwarfEntity extends DwarfEntity {
   private static final SpawnEquipmentTable WEAPONS;

   public BlueDwarfEntity(EntityType type, World w) {
      super(type, w);
   }

   protected NPCFoodPool getEatPool() {
      return NPCFoodPools.BLUE_DWARF;
   }

   public ILivingEntityData func_213386_a(IServerWorld sw, DifficultyInstance diff, SpawnReason reason, ILivingEntityData spawnData, CompoundNBT dataTag) {
      spawnData = super.func_213386_a(sw, diff, reason, spawnData, dataTag);
      this.npcItemsInv.setMeleeWeapon(WEAPONS.getRandomItem(this.field_70146_Z));
      this.npcItemsInv.clearIdleItem();
      return spawnData;
   }

   static {
      WEAPONS = SpawnEquipmentTable.of(Items.field_151036_c, Items.field_151035_b, LOTRItems.IRON_DAGGER, LOTRItems.BLUE_DWARVEN_AXE, LOTRItems.BLUE_DWARVEN_PICKAXE, LOTRItems.BLUE_DWARVEN_DAGGER);
   }
}
