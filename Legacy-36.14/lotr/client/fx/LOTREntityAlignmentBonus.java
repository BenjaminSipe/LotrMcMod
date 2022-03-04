package lotr.client.fx;

import java.util.Iterator;
import lotr.common.fac.LOTRAlignmentBonusMap;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class LOTREntityAlignmentBonus extends Entity {
   public int particleAge;
   public int particleMaxAge;
   public String name;
   public LOTRFaction mainFaction;
   public float prevMainAlignment;
   public LOTRAlignmentBonusMap factionBonusMap;
   public boolean isKill;
   public boolean isHiredKill;
   public float conquestBonus;

   public LOTREntityAlignmentBonus(World world, double d, double d1, double d2, String s, LOTRFaction f, float pre, LOTRAlignmentBonusMap fMap, boolean kill, boolean hiredKill, float conqBonus) {
      super(world);
      this.func_70105_a(0.5F, 0.5F);
      this.field_70129_M = this.field_70131_O / 2.0F;
      this.func_70107_b(d, d1, d2);
      this.particleAge = 0;
      this.name = s;
      this.mainFaction = f;
      this.prevMainAlignment = pre;
      this.factionBonusMap = fMap;
      this.isKill = kill;
      this.isHiredKill = hiredKill;
      this.conquestBonus = conqBonus;
      this.calcMaxAge();
   }

   private void calcMaxAge() {
      float highestBonus = 0.0F;
      Iterator var2 = this.factionBonusMap.getChangedFactions().iterator();

      while(var2.hasNext()) {
         LOTRFaction fac = (LOTRFaction)var2.next();
         float bonus = Math.abs((Float)this.factionBonusMap.get(fac));
         if (bonus > highestBonus) {
            highestBonus = bonus;
         }
      }

      float conq = Math.abs(this.conquestBonus);
      if (conq > highestBonus) {
         highestBonus = conq;
      }

      this.particleMaxAge = 80;
      int extra = (int)(Math.min(1.0F, highestBonus / 50.0F) * 220.0F);
      this.particleMaxAge += extra;
   }

   protected void func_70088_a() {
   }

   public void func_70014_b(NBTTagCompound nbt) {
   }

   public void func_70037_a(NBTTagCompound nbt) {
   }

   public void func_70071_h_() {
      super.func_70071_h_();
      ++this.particleAge;
      if (this.particleAge >= this.particleMaxAge) {
         this.func_70106_y();
      }

   }

   protected boolean func_70041_e_() {
      return false;
   }

   public boolean func_85032_ar() {
      return true;
   }

   public boolean func_70104_M() {
      return false;
   }
}
