package lotr.common.entity.ai;

import lotr.common.entity.LOTREntities;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.world.World;

public class LOTREntityAINPCMate extends EntityAIBase {
   private LOTREntityNPC theNPC;
   private World theWorld;
   private LOTREntityNPC theSpouse;
   private int spawnBabyDelay = 0;
   private double moveSpeed;

   public LOTREntityAINPCMate(LOTREntityNPC npc, double d) {
      this.theNPC = npc;
      this.theWorld = npc.field_70170_p;
      this.moveSpeed = d;
      this.func_75248_a(3);
   }

   public boolean func_75250_a() {
      if (this.theNPC.getClass() == this.theNPC.familyInfo.marriageEntityClass && this.theNPC.familyInfo.spouseUniqueID != null && this.theNPC.familyInfo.children < this.theNPC.familyInfo.maxChildren && this.theNPC.familyInfo.getAge() == 0) {
         this.theSpouse = this.theNPC.familyInfo.getSpouse();
         return this.theSpouse != null && (double)this.theNPC.func_70032_d(this.theSpouse) < 16.0D && this.theSpouse.familyInfo.children < this.theSpouse.familyInfo.maxChildren && this.theSpouse.familyInfo.getAge() == 0;
      } else {
         return false;
      }
   }

   public boolean func_75253_b() {
      return this.theSpouse.func_70089_S() && this.spawnBabyDelay < 60 && this.theNPC.familyInfo.getAge() == 0 && this.theSpouse.familyInfo.getAge() == 0;
   }

   public void func_75251_c() {
      this.theSpouse = null;
      this.spawnBabyDelay = 0;
   }

   public void func_75246_d() {
      this.theNPC.func_70671_ap().func_75651_a(this.theSpouse, 10.0F, (float)this.theNPC.func_70646_bf());
      this.theNPC.func_70661_as().func_75497_a(this.theSpouse, this.moveSpeed);
      ++this.spawnBabyDelay;
      if (this.spawnBabyDelay % 20 == 0) {
         this.theNPC.spawnHearts();
      }

      if (this.spawnBabyDelay >= 60 && this.theNPC.func_70068_e(this.theSpouse) < 9.0D) {
         this.spawnBaby();
      }

   }

   private void spawnBaby() {
      LOTREntityNPC baby = (LOTREntityNPC)EntityList.func_75620_a(LOTREntities.getStringFromClass(this.theNPC.familyInfo.marriageEntityClass), this.theWorld);
      LOTREntityNPC maleParent = this.theNPC.familyInfo.isMale() ? this.theNPC : this.theSpouse;
      LOTREntityNPC femaleParent = this.theNPC.familyInfo.isMale() ? this.theSpouse : this.theNPC;
      baby.familyInfo.setChild();
      baby.familyInfo.setMale(baby.func_70681_au().nextBoolean());
      baby.familyInfo.maleParentID = maleParent.func_110124_au();
      baby.familyInfo.femaleParentID = femaleParent.func_110124_au();
      baby.createNPCChildName(maleParent, femaleParent);
      baby.func_110161_a((IEntityLivingData)null);
      baby.func_70012_b(this.theNPC.field_70165_t, this.theNPC.field_70163_u, this.theNPC.field_70161_v, 0.0F, 0.0F);
      baby.isNPCPersistent = true;
      this.theWorld.func_72838_d(baby);
      this.theNPC.familyInfo.setMaxBreedingDelay();
      this.theSpouse.familyInfo.setMaxBreedingDelay();
      ++this.theNPC.familyInfo.children;
      ++this.theSpouse.familyInfo.children;
      this.theNPC.spawnHearts();
      this.theSpouse.spawnHearts();
   }
}
