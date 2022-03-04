package lotr.common.entity.npc;

import lotr.common.entity.npc.ai.goal.NPCMeleeAttackGoal;
import lotr.common.entity.npc.data.NPCGenderProvider;
import lotr.common.entity.npc.util.LeatherDyeUtil;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class BreeGuardEntity extends BreeManEntity {
   private static final SpawnEquipmentTable WEAPONS;
   private static final int BOOTS_COLOR = 3354152;
   private static final int[] SUIT_COLORS;

   public BreeGuardEntity(EntityType type, World w) {
      super(type, w);
   }

   protected NPCGenderProvider getGenderProvider() {
      return NPCGenderProvider.MALE;
   }

   protected Goal createAttackGoal() {
      return new NPCMeleeAttackGoal(this, 1.45D);
   }

   protected void addNPCTargetingAI() {
      this.addAggressiveTargetingGoals();
   }

   public ILivingEntityData func_213386_a(IServerWorld sw, DifficultyInstance diff, SpawnReason reason, ILivingEntityData spawnData, CompoundNBT dataTag) {
      spawnData = super.func_213386_a(sw, diff, reason, spawnData, dataTag);
      this.npcItemsInv.setMeleeWeapon(WEAPONS.getRandomItem(this.field_70146_Z));
      this.npcItemsInv.setIdleItemsFromMeleeWeapons();
      if (this.field_70146_Z.nextInt(3) == 0) {
         this.func_184201_a(EquipmentSlotType.FEET, new ItemStack(Items.field_151029_X));
      } else {
         this.func_184201_a(EquipmentSlotType.FEET, LeatherDyeUtil.dyeLeather(Items.field_151021_T, 3354152));
      }

      if (this.field_70146_Z.nextInt(3) == 0) {
         this.func_184201_a(EquipmentSlotType.LEGS, new ItemStack(Items.field_151022_W));
      } else {
         this.func_184201_a(EquipmentSlotType.LEGS, LeatherDyeUtil.dyeLeather(Items.field_151026_S, SUIT_COLORS, this.field_70146_Z));
      }

      if (this.field_70146_Z.nextInt(3) == 0) {
         this.func_184201_a(EquipmentSlotType.CHEST, new ItemStack(Items.field_151023_V));
      } else {
         this.func_184201_a(EquipmentSlotType.CHEST, LeatherDyeUtil.dyeLeather(Items.field_151027_R, SUIT_COLORS, this.field_70146_Z));
      }

      this.func_184201_a(EquipmentSlotType.HEAD, new ItemStack(Items.field_151028_Y));
      this.func_184201_a(EquipmentSlotType.OFFHAND, new ItemStack(Items.field_185159_cQ));
      return spawnData;
   }

   static {
      WEAPONS = SpawnEquipmentTable.of(Items.field_151040_l, Items.field_151040_l, Items.field_151040_l);
      SUIT_COLORS = new int[]{11373426, 7823440, 5983041, 9535090};
   }
}
