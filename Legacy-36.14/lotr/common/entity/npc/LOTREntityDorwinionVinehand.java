package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAIFarm;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

public class LOTREntityDorwinionVinehand extends LOTREntityDorwinionMan implements LOTRFarmhand {
   private Item seedsItem;

   public LOTREntityDorwinionVinehand(World world) {
      super(world);
      this.field_70714_bg.func_75776_a(3, new LOTREntityAIFarm(this, 1.0D, 1.0F));
      this.field_70715_bh.field_75782_a.clear();
      this.addTargetTasks(false);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      this.npcItemsInv.setMeleeWeapon(new ItemStack(Items.field_151019_K));
      this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
      if (this.field_70146_Z.nextBoolean()) {
         this.seedsItem = LOTRMod.seedsGrapeRed;
      } else {
         this.seedsItem = LOTRMod.seedsGrapeWhite;
      }

      return data;
   }

   public IPlantable getUnhiredSeeds() {
      return (IPlantable)this.seedsItem;
   }

   public void func_70014_b(NBTTagCompound nbt) {
      super.func_70014_b(nbt);
      if (this.seedsItem != null) {
         nbt.func_74768_a("SeedsID", Item.func_150891_b(this.seedsItem));
      }

   }

   public void func_70037_a(NBTTagCompound nbt) {
      super.func_70037_a(nbt);
      if (nbt.func_74764_b("SeedsID")) {
         Item item = Item.func_150899_d(nbt.func_74762_e("SeedsID"));
         if (item != null && item instanceof IPlantable) {
            this.seedsItem = item;
         }
      }

   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      return this.hiredNPCInfo.getHiringPlayer() == entityplayer ? "dorwinion/vinehand/hired" : super.getSpeechBank(entityplayer);
   }
}
