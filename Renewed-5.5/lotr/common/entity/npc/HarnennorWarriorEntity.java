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
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class HarnennorWarriorEntity extends HarnedhrimEntity {
   private static final SpawnEquipmentTable WEAPONS;
   private static final int[] TURBAN_COLORS;

   public HarnennorWarriorEntity(EntityType type, World w) {
      super(type, w);
   }

   protected NPCGenderProvider getGenderProvider() {
      return NPCGenderProvider.MALE;
   }

   protected ITextComponent formatNPCName(ITextComponent npcName, ITextComponent typeName) {
      return this.formatGenericNPCName(npcName, typeName);
   }

   public static MutableAttribute regAttrs() {
      return GondorManEntity.regAttrs().func_233815_a_((Attribute)LOTRAttributes.NPC_RANGED_INACCURACY.get(), 0.75D);
   }

   public ILivingEntityData func_213386_a(IServerWorld sw, DifficultyInstance diff, SpawnReason reason, ILivingEntityData spawnData, CompoundNBT dataTag) {
      spawnData = super.func_213386_a(sw, diff, reason, spawnData, dataTag);
      this.npcItemsInv.setMeleeWeapon(WEAPONS.getRandomItem(this.field_70146_Z));
      this.npcItemsInv.setIdleItemsFromMeleeWeapons();
      this.func_184201_a(EquipmentSlotType.FEET, new ItemStack((IItemProvider)LOTRItems.HARNENNOR_BOOTS.get()));
      this.func_184201_a(EquipmentSlotType.LEGS, new ItemStack((IItemProvider)LOTRItems.HARNENNOR_LEGGINGS.get()));
      this.func_184201_a(EquipmentSlotType.CHEST, new ItemStack((IItemProvider)LOTRItems.HARNENNOR_CHESTPLATE.get()));
      if (this.field_70146_Z.nextInt(10) != 0) {
         this.func_184201_a(EquipmentSlotType.HEAD, new ItemStack((IItemProvider)LOTRItems.HARNENNOR_HELMET.get()));
      }

      this.func_184201_a(EquipmentSlotType.OFFHAND, new ItemStack((IItemProvider)LOTRItems.HARNENNOR_SHIELD.get()));
      return spawnData;
   }

   static {
      WEAPONS = SpawnEquipmentTable.of(LOTRItems.HARAD_SWORD, LOTRItems.HARAD_SWORD, LOTRItems.HARAD_SWORD, LOTRItems.HARAD_DAGGER, LOTRItems.HARAD_DAGGER, LOTRItems.HARAD_SWORD, LOTRItems.HARAD_SPEAR);
      TURBAN_COLORS = new int[]{1643539, 6309443, 7014914, 7809314, 5978155};
   }
}
