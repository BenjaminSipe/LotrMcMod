package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityHorse;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityDolAmrothSoldier extends LOTREntityGondorLevyman {
   private static ItemStack[] manAtArmsWeapons;

   public LOTREntityDolAmrothSoldier(World world) {
      super(world);
      this.spawnRidingHorse = this.field_70146_Z.nextInt(6) == 0;
   }

   public LOTRNPCMount createMountToRide() {
      LOTREntityHorse horse = (LOTREntityHorse)super.createMountToRide();
      horse.setMountArmor(new ItemStack(LOTRMod.horseArmorDolAmroth));
      return horse;
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      int i = this.field_70146_Z.nextInt(manAtArmsWeapons.length);
      this.npcItemsInv.setMeleeWeapon(manAtArmsWeapons[i].func_77946_l());
      this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
      this.func_70062_b(1, new ItemStack(Items.field_151021_T));
      this.func_70062_b(2, new ItemStack(LOTRMod.legsDolAmrothGambeson));
      this.func_70062_b(3, new ItemStack(LOTRMod.bodyDolAmrothGambeson));
      if (this.field_70146_Z.nextInt(3) == 0) {
         this.func_70062_b(4, (ItemStack)null);
      } else {
         i = this.field_70146_Z.nextInt(3);
         if (i == 0) {
            this.func_70062_b(4, new ItemStack(LOTRMod.helmetDolAmroth));
         } else if (i == 1) {
            this.func_70062_b(4, new ItemStack(Items.field_151028_Y));
         } else if (i == 2) {
            this.func_70062_b(4, new ItemStack(Items.field_151024_Q));
         }
      }

      return data;
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      if (this.isFriendlyAndAligned(entityplayer)) {
         return this.hiredNPCInfo.getHiringPlayer() == entityplayer ? "gondor/swanKnight/hired" : "gondor/swanKnight/friendly";
      } else {
         return "gondor/swanKnight/hostile";
      }
   }

   static {
      manAtArmsWeapons = new ItemStack[]{new ItemStack(LOTRMod.swordDolAmroth), new ItemStack(LOTRMod.swordDolAmroth), new ItemStack(LOTRMod.swordGondor), new ItemStack(Items.field_151040_l)};
   }
}
