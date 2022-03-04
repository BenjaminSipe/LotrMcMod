package lotr.common.entity.projectile;

import lotr.common.entity.npc.LOTREntityMirkwoodSpider;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class LOTREntityMysteryWeb extends EntityThrowable {
   public LOTREntityMysteryWeb(World world) {
      super(world);
   }

   public LOTREntityMysteryWeb(World world, EntityLivingBase entityliving) {
      super(world, entityliving);
   }

   public LOTREntityMysteryWeb(World world, double d, double d1, double d2) {
      super(world, d, d1, d2);
   }

   protected void func_70184_a(MovingObjectPosition m) {
      if (this.func_85052_h() == null || m.field_72308_g != this.func_85052_h()) {
         if (!this.field_70170_p.field_72995_K) {
            boolean spawnedSpider = false;
            if (this.field_70146_Z.nextInt(4) == 0) {
               LOTREntityMirkwoodSpider spider = new LOTREntityMirkwoodSpider(this.field_70170_p);
               spider.setSpiderScale(0);
               spider.liftSpawnRestrictions = true;

               for(int i = -2; i <= -2 && !spawnedSpider; ++i) {
                  for(int j = 0; j <= 3 && !spawnedSpider; ++j) {
                     for(int k = -2; k <= -2 && !spawnedSpider; ++k) {
                        spider.func_70012_b(this.field_70165_t + (double)i / 2.0D, this.field_70163_u + (double)j / 3.0D, this.field_70161_v + (double)k / 2.0D, this.field_70146_Z.nextFloat() * 360.0F, 0.0F);
                        if (spider.func_70601_bi()) {
                           spider.liftSpawnRestrictions = false;
                           spider.func_110161_a((IEntityLivingData)null);
                           this.field_70170_p.func_72838_d(spider);
                           if (this.func_85052_h() != null) {
                              spider.func_70624_b(this.func_85052_h());
                           }

                           spawnedSpider = true;
                        }
                     }
                  }
               }
            }

            if (!spawnedSpider) {
               IInventory tempInventory = new InventoryBasic("mysteryWeb", true, 1);
               LOTRChestContents.fillInventory(tempInventory, this.field_70146_Z, LOTRChestContents.MIRKWOOD_LOOT, 1);
               ItemStack item = tempInventory.func_70301_a(0);
               if (this.field_70146_Z.nextInt(500) == 0) {
                  item = new ItemStack(Items.field_151127_ba, 64);
               }

               if (item != null) {
                  EntityItem entityitem = new EntityItem(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, item);
                  entityitem.field_145804_b = 10;
                  this.field_70170_p.func_72838_d(entityitem);
               }
            }

            this.func_85030_a("random.pop", 0.2F, ((this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.7F + 1.0F) * 2.0F);
            this.func_70106_y();
         }

      }
   }

   protected float func_70182_d() {
      return 0.5F;
   }

   protected float func_70185_h() {
      return 0.01F;
   }
}
