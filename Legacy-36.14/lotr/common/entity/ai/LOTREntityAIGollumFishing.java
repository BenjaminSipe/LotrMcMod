package lotr.common.entity.ai;

import java.util.Random;
import lotr.common.entity.npc.LOTREntityGollum;
import lotr.common.entity.npc.LOTRSpeech;
import net.minecraft.block.material.Material;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class LOTREntityAIGollumFishing extends EntityAIBase {
   private LOTREntityGollum theGollum;
   private double moveSpeed;
   private boolean avoidsWater;
   private World theWorld;
   private double xPosition;
   private double yPosition;
   private double zPosition;
   private int moveTick;
   private int fishTick;
   private boolean finished;

   public LOTREntityAIGollumFishing(LOTREntityGollum entity, double d) {
      this.theGollum = entity;
      this.moveSpeed = d;
      this.theWorld = entity.field_70170_p;
      this.func_75248_a(3);
   }

   public boolean func_75250_a() {
      if (this.theGollum.getGollumOwner() == null) {
         return false;
      } else if (this.theGollum.isGollumSitting()) {
         return false;
      } else if (this.theGollum.prevFishTime > 0) {
         return false;
      } else if (this.theGollum.isFishing) {
         return false;
      } else if (this.theGollum.func_71124_b(0) != null) {
         return false;
      } else if (this.theGollum.func_70681_au().nextInt(60) == 0) {
         Vec3 vec3 = this.findPossibleFishingLocation();
         if (vec3 == null) {
            return false;
         } else {
            this.xPosition = vec3.field_72450_a;
            this.yPosition = vec3.field_72448_b;
            this.zPosition = vec3.field_72449_c;
            return true;
         }
      } else {
         return false;
      }
   }

   private Vec3 findPossibleFishingLocation() {
      Random random = this.theGollum.func_70681_au();

      for(int l = 0; l < 32; ++l) {
         int i = MathHelper.func_76128_c(this.theGollum.field_70165_t) - 16 + random.nextInt(33);
         int j = MathHelper.func_76128_c(this.theGollum.field_70121_D.field_72338_b) - 8 + random.nextInt(17);
         int k = MathHelper.func_76128_c(this.theGollum.field_70161_v) - 16 + random.nextInt(33);
         if (!this.theWorld.func_147439_a(i, j + 1, k).func_149721_r() && !this.theWorld.func_147439_a(i, j, k).func_149721_r() && this.theWorld.func_147439_a(i, j - 1, k).func_149688_o() == Material.field_151586_h) {
            return Vec3.func_72443_a((double)i + 0.5D, (double)j + 0.5D, (double)k + 0.5D);
         }
      }

      return null;
   }

   public boolean func_75253_b() {
      return this.theGollum.getGollumOwner() != null && !this.theGollum.isGollumSitting() && this.moveTick < 300 && !this.finished;
   }

   public void func_75249_e() {
      this.avoidsWater = this.theGollum.func_70661_as().func_75486_a();
      this.theGollum.func_70661_as().func_75491_a(false);
      this.theGollum.isFishing = true;
   }

   public void func_75251_c() {
      this.theGollum.func_70661_as().func_75499_g();
      this.theGollum.func_70661_as().func_75491_a(this.avoidsWater);
      this.moveTick = 0;
      this.fishTick = 0;
      if (this.finished) {
         this.finished = false;
         this.theGollum.prevFishTime = 3000;
      } else {
         this.theGollum.prevFishTime = 600;
      }

      this.theGollum.isFishing = false;
   }

   public void func_75246_d() {
      if (this.atFishingLocation()) {
         if (this.theGollum.func_70090_H()) {
            this.theWorld.func_72960_a(this.theGollum, (byte)15);
            if (this.theGollum.func_70681_au().nextInt(4) == 0) {
               this.theWorld.func_72956_a(this.theGollum, this.theGollum.func_145777_O(), 1.0F, 1.0F + (this.theGollum.func_70681_au().nextFloat() - this.theGollum.func_70681_au().nextFloat()) * 0.4F);
            }

            this.theGollum.func_70683_ar().func_75660_a();
            if (this.theGollum.func_70681_au().nextInt(50) == 0) {
               LOTRSpeech.sendSpeech(this.theGollum.getGollumOwner(), this.theGollum, LOTRSpeech.getRandomSpeechForPlayer(this.theGollum, "char/gollum/fishing", this.theGollum.getGollumOwner()));
            }
         }

         ++this.fishTick;
         if (this.fishTick > 100) {
            this.theGollum.func_70062_b(0, new ItemStack(Items.field_151115_aP, 4 + this.theGollum.func_70681_au().nextInt(9)));
            this.finished = true;
            LOTRSpeech.sendSpeech(this.theGollum.getGollumOwner(), this.theGollum, LOTRSpeech.getRandomSpeechForPlayer(this.theGollum, "char/gollum/catchFish", this.theGollum.getGollumOwner()));
         }
      } else {
         this.theGollum.func_70661_as().func_75492_a(this.xPosition, this.yPosition, this.zPosition, this.moveSpeed);
         ++this.moveTick;
      }

   }

   private boolean atFishingLocation() {
      if (this.theGollum.func_70092_e(this.xPosition, this.yPosition, this.zPosition) >= 4.0D) {
         return false;
      } else {
         int i = MathHelper.func_76128_c(this.theGollum.field_70165_t);
         int j = MathHelper.func_76128_c(this.theGollum.field_70121_D.field_72338_b);
         int k = MathHelper.func_76128_c(this.theGollum.field_70161_v);
         return this.theWorld.func_147439_a(i, j, k).func_149688_o() == Material.field_151586_h || this.theWorld.func_147439_a(i, j - 1, k).func_149688_o() == Material.field_151586_h;
      }
   }
}
