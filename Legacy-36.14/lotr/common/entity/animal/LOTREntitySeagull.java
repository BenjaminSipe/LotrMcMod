package lotr.common.entity.animal;

import java.util.List;
import net.minecraft.block.material.Material;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntitySeagull extends LOTREntityBird {
   public static float SEAGULL_SCALE = 1.4F;

   public LOTREntitySeagull(World world) {
      super(world);
      this.func_70105_a(this.field_70130_N * SEAGULL_SCALE, this.field_70131_O * SEAGULL_SCALE);
   }

   public String getBirdTextureDir() {
      return "seagull";
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      this.setBirdType(LOTREntityBird.BirdType.COMMON);
      return data;
   }

   protected boolean canStealItems() {
      return true;
   }

   protected boolean isStealable(ItemStack itemstack) {
      Item item = itemstack.func_77973_b();
      return item != Items.field_151115_aP && item != Items.field_151101_aQ ? super.isStealable(itemstack) : true;
   }

   protected boolean canBirdSpawnHere() {
      if (LOTRAmbientSpawnChecks.canSpawn(this, 8, 4, 40, 4, Material.field_151584_j, Material.field_151595_p)) {
         double range = 16.0D;
         List nearbyGulls = this.field_70170_p.func_72872_a(LOTREntitySeagull.class, this.field_70121_D.func_72314_b(range, range, range));
         return nearbyGulls.size() < 2;
      } else {
         return false;
      }
   }

   protected String func_70639_aQ() {
      return "lotr:bird.seagull.say";
   }

   protected String func_70621_aR() {
      return "lotr:bird.seagull.hurt";
   }

   protected String func_70673_aS() {
      return "lotr:bird.seagull.hurt";
   }
}
