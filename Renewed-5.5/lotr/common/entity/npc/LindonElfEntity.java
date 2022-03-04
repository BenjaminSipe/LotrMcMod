package lotr.common.entity.npc;

import lotr.common.entity.npc.data.name.NPCNameGenerator;
import lotr.common.entity.npc.data.name.NPCNameGenerators;
import lotr.common.init.LOTRItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.IItemProvider;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class LindonElfEntity extends ElfEntity {
   public LindonElfEntity(EntityType type, World w) {
      super(type, w);
   }

   protected NPCNameGenerator getNameGenerator() {
      return NPCNameGenerators.SINDARIN;
   }

   public ILivingEntityData func_213386_a(IServerWorld sw, DifficultyInstance diff, SpawnReason reason, ILivingEntityData spawnData, CompoundNBT dataTag) {
      spawnData = super.func_213386_a(sw, diff, reason, spawnData, dataTag);
      this.npcItemsInv.setMeleeWeapon(new ItemStack((IItemProvider)LOTRItems.LINDON_DAGGER.get()));
      this.npcItemsInv.setRangedWeapon(new ItemStack(Items.field_151031_f));
      this.npcItemsInv.clearIdleItem();
      return spawnData;
   }
}
