package lotr.common.entity.npc;

import lotr.common.entity.npc.data.NPCGenderProvider;
import lotr.common.init.LOTRAttributes;
import lotr.common.init.LOTRItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifierMap.MutableAttribute;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.IItemProvider;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class CoastSouthronWarriorEntity extends CoastSouthronEntity {
   private static final SpawnEquipmentTable IRON_WEAPONS;
   private static final SpawnEquipmentTable BRONZE_WEAPONS;
   private static final int[] TURBAN_COLORS;

   public CoastSouthronWarriorEntity(EntityType type, World w) {
      super(type, w);
   }

   protected NPCGenderProvider getGenderProvider() {
      return NPCGenderProvider.MALE;
   }

   public static MutableAttribute regAttrs() {
      return CoastSouthronEntity.regAttrs().func_233815_a_((Attribute)LOTRAttributes.NPC_RANGED_INACCURACY.get(), 0.75D);
   }

   protected void addNPCTargetingAI() {
      this.addAggressiveTargetingGoals();
   }

   public ILivingEntityData func_213386_a(IServerWorld sw, DifficultyInstance diff, SpawnReason reason, ILivingEntityData spawnData, CompoundNBT dataTag) {
      spawnData = super.func_213386_a(sw, diff, reason, spawnData, dataTag);
      if (this.field_70146_Z.nextInt(3) == 0) {
         this.npcItemsInv.setMeleeWeapon(BRONZE_WEAPONS.getRandomItem(this.field_70146_Z));
      } else {
         this.npcItemsInv.setMeleeWeapon(IRON_WEAPONS.getRandomItem(this.field_70146_Z));
      }

      this.npcItemsInv.setIdleItemsFromMeleeWeapons();
      this.func_184201_a(EquipmentSlotType.FEET, new ItemStack((IItemProvider)LOTRItems.HARAD_BOOTS.get()));
      this.func_184201_a(EquipmentSlotType.LEGS, new ItemStack((IItemProvider)LOTRItems.HARAD_LEGGINGS.get()));
      this.func_184201_a(EquipmentSlotType.CHEST, new ItemStack((IItemProvider)LOTRItems.HARAD_CHESTPLATE.get()));
      if (this.field_70146_Z.nextInt(10) != 0) {
         this.func_184201_a(EquipmentSlotType.HEAD, new ItemStack((IItemProvider)LOTRItems.HARAD_HELMET.get()));
      }

      this.func_184201_a(EquipmentSlotType.OFFHAND, new ItemStack((IItemProvider)LOTRItems.COAST_SOUTHRON_SHIELD.get()));
      return spawnData;
   }

   static {
      IRON_WEAPONS = SpawnEquipmentTable.of(LOTRItems.UMBAR_SCIMITAR, LOTRItems.UMBAR_SCIMITAR, LOTRItems.UMBAR_SCIMITAR, LOTRItems.UMBAR_DAGGER, LOTRItems.UMBAR_DAGGER, LOTRItems.UMBAR_SCIMITAR, LOTRItems.UMBAR_SCIMITAR, LOTRItems.UMBAR_SCIMITAR, LOTRItems.UMBAR_SCIMITAR, LOTRItems.UMBAR_SPEAR);
      BRONZE_WEAPONS = SpawnEquipmentTable.of(LOTRItems.HARAD_SWORD, LOTRItems.HARAD_SWORD, LOTRItems.HARAD_SWORD, LOTRItems.HARAD_DAGGER, LOTRItems.HARAD_DAGGER, LOTRItems.HARAD_SWORD, LOTRItems.HARAD_SPEAR);
      TURBAN_COLORS = new int[]{1643539, 6309443, 7014914, 7809314, 5978155};
   }
}
