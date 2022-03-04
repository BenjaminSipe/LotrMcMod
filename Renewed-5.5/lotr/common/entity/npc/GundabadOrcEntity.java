package lotr.common.entity.npc;

import lotr.common.entity.npc.ai.goal.NPCMeleeAttackGoal;
import lotr.common.init.LOTRItems;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class GundabadOrcEntity extends OrcEntity {
   private static final SpawnEquipmentTable WEAPONS;
   private static final SpawnEquipmentTable HELMETS;
   private static final SpawnEquipmentTable CHESTPLATES;
   private static final SpawnEquipmentTable LEGGINGS;
   private static final SpawnEquipmentTable BOOTS;

   public GundabadOrcEntity(EntityType type, World w) {
      super(type, w);
   }

   protected Goal createOrcAttackGoal() {
      return new NPCMeleeAttackGoal(this, 1.4D);
   }

   public ILivingEntityData func_213386_a(IServerWorld sw, DifficultyInstance diff, SpawnReason reason, ILivingEntityData spawnData, CompoundNBT dataTag) {
      spawnData = super.func_213386_a(sw, diff, reason, spawnData, dataTag);
      this.npcItemsInv.setMeleeWeapon(WEAPONS.getRandomItem(this.field_70146_Z));
      this.npcItemsInv.setIdleItemsFromMeleeWeapons();
      this.func_184201_a(EquipmentSlotType.FEET, BOOTS.getRandomItem(this.field_70146_Z));
      this.func_184201_a(EquipmentSlotType.LEGS, LEGGINGS.getRandomItem(this.field_70146_Z));
      this.func_184201_a(EquipmentSlotType.CHEST, CHESTPLATES.getRandomItem(this.field_70146_Z));
      if (this.field_70146_Z.nextInt(3) != 0) {
         this.func_184201_a(EquipmentSlotType.HEAD, HELMETS.getRandomItem(this.field_70146_Z));
      }

      if (this.field_70146_Z.nextFloat() < 0.2F) {
         this.func_184201_a(EquipmentSlotType.OFFHAND, new ItemStack(Items.field_185159_cQ));
      }

      return spawnData;
   }

   protected void func_213333_a(DamageSource source, int looting, boolean playerHit) {
      super.func_213333_a(source, looting, playerHit);
      if (this.field_70146_Z.nextInt(4000) == 0) {
         this.func_199701_a_((new ItemStack(Blocks.field_150346_d)).func_200302_a(new StringTextComponent("Such Wealth")));
      }

   }

   static {
      WEAPONS = SpawnEquipmentTable.of(Items.field_151052_q, Items.field_151049_t, Items.field_151050_s, LOTRItems.STONE_DAGGER, LOTRItems.STONE_SPEAR, Items.field_151040_l, Items.field_151036_c, Items.field_151035_b, LOTRItems.IRON_DAGGER, LOTRItems.IRON_SPEAR, LOTRItems.BRONZE_SWORD, LOTRItems.BRONZE_AXE, LOTRItems.BRONZE_PICKAXE, LOTRItems.BRONZE_DAGGER, LOTRItems.BRONZE_SPEAR, LOTRItems.MORDOR_SCIMITAR, LOTRItems.MORDOR_AXE, LOTRItems.MORDOR_PICKAXE, LOTRItems.MORDOR_DAGGER, LOTRItems.MORDOR_SPEAR);
      HELMETS = SpawnEquipmentTable.of(Items.field_151024_Q, LOTRItems.BRONZE_HELMET, LOTRItems.FUR_HELMET, LOTRItems.BONE_HELMET, LOTRItems.MORDOR_HELMET);
      CHESTPLATES = SpawnEquipmentTable.of(Items.field_151027_R, LOTRItems.BRONZE_CHESTPLATE, LOTRItems.FUR_CHESTPLATE, LOTRItems.BONE_CHESTPLATE, LOTRItems.MORDOR_CHESTPLATE);
      LEGGINGS = SpawnEquipmentTable.of(Items.field_151026_S, LOTRItems.BRONZE_LEGGINGS, LOTRItems.FUR_LEGGINGS, LOTRItems.BONE_LEGGINGS, LOTRItems.MORDOR_LEGGINGS);
      BOOTS = SpawnEquipmentTable.of(Items.field_151021_T, LOTRItems.BRONZE_BOOTS, LOTRItems.FUR_BOOTS, LOTRItems.BONE_BOOTS, LOTRItems.MORDOR_BOOTS);
   }
}
