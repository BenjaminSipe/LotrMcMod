package lotr.common.entity.npc.ai.goal;

import lotr.common.entity.npc.NPCEntity;
import lotr.common.entity.npc.data.NPCFoodPool;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;

public class NPCEatGoal extends NPCConsumeGoal {
   private final NPCFoodPool foodPool;

   public NPCEatGoal(NPCEntity entity, NPCFoodPool foods, int chance) {
      super(entity, chance);
      this.foodPool = foods;
   }

   protected ItemStack createConsumable() {
      return this.foodPool.getRandomFood(this.rand);
   }

   protected void updateConsumeTick(int tick) {
      if (tick % 4 == 0) {
         ItemStack itemstack = this.getHeldConsumingItem();
         this.addItemParticles(itemstack, 5);
         this.playEatSound(itemstack);
      }

   }

   protected void consume() {
      ItemStack itemstack = this.getHeldConsumingItem();
      this.addItemParticles(itemstack, 16);
      this.playEatSound(itemstack);
      Item item = itemstack.func_77973_b();
      if (item.func_219971_r()) {
         Food food = item.func_219967_s();
         this.theEntity.func_70691_i((float)food.func_221466_a());
      }

   }

   private void playEatSound(ItemStack itemstack) {
      this.theEntity.func_184185_a(this.theEntity.func_213353_d(itemstack), 0.5F + 0.5F * (float)this.rand.nextInt(2), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
   }

   private void addItemParticles(ItemStack itemstack, int count) {
      for(int i = 0; i < count; ++i) {
         Vector3d motion = new Vector3d(((double)this.rand.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D);
         motion = motion.func_178789_a((float)Math.toRadians((double)(-this.theEntity.field_70125_A)));
         motion = motion.func_178785_b((float)Math.toRadians((double)(-this.theEntity.field_70177_z)));
         Vector3d pos = new Vector3d(((double)this.rand.nextFloat() - 0.5D) * 0.3D, (double)(-this.rand.nextFloat()) * 0.6D - 0.3D, 0.6D);
         pos = pos.func_178789_a((float)Math.toRadians((double)(-this.theEntity.field_70125_A)));
         pos = pos.func_178785_b((float)Math.toRadians((double)(-this.theEntity.field_70177_z)));
         pos = pos.func_72441_c(this.theEntity.func_226277_ct_(), this.theEntity.func_226280_cw_(), this.theEntity.func_226281_cx_());
         ((ServerWorld)this.theEntity.field_70170_p).func_195598_a(new ItemParticleData(ParticleTypes.field_197591_B, itemstack), pos.field_72450_a, pos.field_72448_b, pos.field_72449_c, 1, motion.field_72450_a, motion.field_72448_b + 0.05D, motion.field_72449_c, 0.0D);
      }

   }
}
