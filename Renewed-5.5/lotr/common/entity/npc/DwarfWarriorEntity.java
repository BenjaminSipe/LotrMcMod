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
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.IItemProvider;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class DwarfWarriorEntity extends DwarfEntity {
   private static final SpawnEquipmentTable WEAPONS;

   public DwarfWarriorEntity(EntityType type, World w) {
      super(type, w);
   }

   protected NPCGenderProvider getGenderProvider() {
      return NPCGenderProvider.MALE;
   }

   public static MutableAttribute regAttrs() {
      return DwarfEntity.regAttrs().func_233815_a_((Attribute)LOTRAttributes.NPC_RANGED_INACCURACY.get(), 0.75D);
   }

   public ILivingEntityData func_213386_a(IServerWorld sw, DifficultyInstance diff, SpawnReason reason, ILivingEntityData spawnData, CompoundNBT dataTag) {
      spawnData = super.func_213386_a(sw, diff, reason, spawnData, dataTag);
      this.npcItemsInv.setMeleeWeapon(WEAPONS.getRandomItem(this.field_70146_Z));
      this.npcItemsInv.setIdleItemsFromMeleeWeapons();
      this.func_184201_a(EquipmentSlotType.FEET, new ItemStack((IItemProvider)LOTRItems.DWARVEN_BOOTS.get()));
      this.func_184201_a(EquipmentSlotType.LEGS, new ItemStack((IItemProvider)LOTRItems.DWARVEN_LEGGINGS.get()));
      this.func_184201_a(EquipmentSlotType.CHEST, new ItemStack((IItemProvider)LOTRItems.DWARVEN_CHESTPLATE.get()));
      if (this.field_70146_Z.nextInt(10) != 0) {
         this.func_184201_a(EquipmentSlotType.HEAD, new ItemStack((IItemProvider)LOTRItems.DWARVEN_HELMET.get()));
      }

      this.func_184201_a(EquipmentSlotType.OFFHAND, new ItemStack(Items.field_185159_cQ));
      return spawnData;
   }

   static {
      WEAPONS = SpawnEquipmentTable.of(LOTRItems.DWARVEN_SWORD, LOTRItems.DWARVEN_SWORD, LOTRItems.DWARVEN_SWORD, LOTRItems.DWARVEN_SWORD, LOTRItems.DWARVEN_SWORD, LOTRItems.DWARVEN_SWORD, LOTRItems.DWARVEN_SWORD, LOTRItems.DWARVEN_SPEAR);
   }
}
