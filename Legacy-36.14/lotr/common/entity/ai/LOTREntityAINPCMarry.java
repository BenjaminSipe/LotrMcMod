package lotr.common.entity.ai;

import java.util.Iterator;
import java.util.List;
import lotr.common.LOTRLevelData;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.fac.LOTRAlignmentValues;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityAINPCMarry extends EntityAIBase {
   private LOTREntityNPC theNPC;
   private World theWorld;
   private LOTREntityNPC theSpouse;
   private int marryDelay = 0;
   private double moveSpeed;

   public LOTREntityAINPCMarry(LOTREntityNPC npc, double d) {
      this.theNPC = npc;
      this.theWorld = npc.field_70170_p;
      this.moveSpeed = d;
      this.func_75248_a(3);
   }

   public boolean func_75250_a() {
      if (this.theNPC.getClass() == this.theNPC.familyInfo.marriageEntityClass && this.theNPC.familyInfo.spouseUniqueID == null && this.theNPC.familyInfo.getAge() == 0 && this.theNPC.func_71124_b(4) == null && this.theNPC.func_71124_b(0) != null) {
         List list = this.theNPC.field_70170_p.func_72872_a(this.theNPC.familyInfo.marriageEntityClass, this.theNPC.field_70121_D.func_72314_b(16.0D, 4.0D, 16.0D));
         LOTREntityNPC spouse = null;
         double distanceSq = Double.MAX_VALUE;
         Iterator iterator = list.iterator();

         while(iterator.hasNext()) {
            LOTREntityNPC candidate = (LOTREntityNPC)iterator.next();
            if (this.theNPC.familyInfo.canMarryNPC(candidate) && candidate.familyInfo.canMarryNPC(this.theNPC)) {
               double d = this.theNPC.func_70068_e(candidate);
               if (d <= distanceSq) {
                  distanceSq = d;
                  spouse = candidate;
               }
            }
         }

         if (spouse == null) {
            return false;
         } else {
            this.theSpouse = spouse;
            return true;
         }
      } else {
         return false;
      }
   }

   public boolean func_75253_b() {
      return this.theSpouse != null && this.theSpouse.func_70089_S() && this.theNPC.familyInfo.canMarryNPC(this.theSpouse) && this.theSpouse.familyInfo.canMarryNPC(this.theNPC);
   }

   public void func_75251_c() {
      this.theSpouse = null;
      this.marryDelay = 0;
   }

   public void func_75246_d() {
      this.theNPC.func_70671_ap().func_75651_a(this.theSpouse, 10.0F, (float)this.theNPC.func_70646_bf());
      this.theNPC.func_70661_as().func_75497_a(this.theSpouse, this.moveSpeed);
      ++this.marryDelay;
      if (this.marryDelay % 20 == 0) {
         this.theNPC.spawnHearts();
      }

      if (this.marryDelay >= 60 && this.theNPC.func_70068_e(this.theSpouse) < 9.0D) {
         this.marry();
      }

   }

   private void marry() {
      this.theNPC.familyInfo.spouseUniqueID = this.theSpouse.func_110124_au();
      this.theSpouse.familyInfo.spouseUniqueID = this.theNPC.func_110124_au();
      this.theNPC.func_70062_b(0, (ItemStack)null);
      this.theNPC.func_70062_b(4, new ItemStack(this.theNPC.familyInfo.marriageRing));
      this.theSpouse.func_70062_b(0, (ItemStack)null);
      this.theSpouse.func_70062_b(4, new ItemStack(this.theNPC.familyInfo.marriageRing));
      this.theNPC.changeNPCNameForMarriage(this.theSpouse);
      this.theSpouse.changeNPCNameForMarriage(this.theNPC);
      int maxChildren = this.theNPC.familyInfo.getRandomMaxChildren();
      this.theNPC.familyInfo.maxChildren = maxChildren;
      this.theSpouse.familyInfo.maxChildren = maxChildren;
      this.theNPC.familyInfo.setMaxBreedingDelay();
      this.theSpouse.familyInfo.setMaxBreedingDelay();
      this.theNPC.spawnHearts();
      this.theSpouse.spawnHearts();
      EntityPlayer ringPlayer = this.theNPC.familyInfo.getRingGivingPlayer();
      if (ringPlayer != null) {
         LOTRLevelData.getData(ringPlayer).addAlignment(ringPlayer, LOTRAlignmentValues.MARRIAGE_BONUS, this.theNPC.getFaction(), this.theNPC);
         if (this.theNPC.familyInfo.marriageAchievement != null) {
            LOTRLevelData.getData(ringPlayer).addAchievement(this.theNPC.familyInfo.marriageAchievement);
         }
      }

      EntityPlayer ringPlayerSpouse = this.theSpouse.familyInfo.getRingGivingPlayer();
      if (ringPlayerSpouse != null) {
         LOTRLevelData.getData(ringPlayerSpouse).addAlignment(ringPlayerSpouse, LOTRAlignmentValues.MARRIAGE_BONUS, this.theSpouse.getFaction(), this.theSpouse);
         if (this.theSpouse.familyInfo.marriageAchievement != null) {
            LOTRLevelData.getData(ringPlayerSpouse).addAchievement(this.theSpouse.familyInfo.marriageAchievement);
         }
      }

      this.theWorld.func_72838_d(new EntityXPOrb(this.theWorld, this.theNPC.field_70165_t, this.theNPC.field_70163_u, this.theNPC.field_70161_v, this.theNPC.func_70681_au().nextInt(8) + 2));
   }
}
