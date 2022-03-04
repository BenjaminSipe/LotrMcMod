package lotr.common.entity.item;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class LOTREntityArrowPoisoned extends EntityArrow implements IEntityAdditionalSpawnData {
   public LOTREntityArrowPoisoned(World world) {
      super(world);
   }

   public LOTREntityArrowPoisoned(World world, double d, double d1, double d2) {
      super(world, d, d1, d2);
   }

   public LOTREntityArrowPoisoned(World world, EntityLivingBase shooter, EntityLivingBase target, float charge, float inaccuracy) {
      super(world, shooter, target, charge, inaccuracy);
   }

   public LOTREntityArrowPoisoned(World world, EntityLivingBase shooter, float charge) {
      super(world, shooter, charge);
   }

   public void writeSpawnData(ByteBuf data) {
      data.writeDouble(this.field_70159_w);
      data.writeDouble(this.field_70181_x);
      data.writeDouble(this.field_70179_y);
      data.writeInt(this.field_70250_c == null ? -1 : this.field_70250_c.func_145782_y());
   }

   public void readSpawnData(ByteBuf data) {
      this.field_70159_w = data.readDouble();
      this.field_70181_x = data.readDouble();
      this.field_70179_y = data.readDouble();
      int id = data.readInt();
      if (id >= 0) {
         Entity entity = this.field_70170_p.func_73045_a(id);
         if (entity != null) {
            this.field_70250_c = entity;
         }
      }

   }

   public void func_70100_b_(EntityPlayer entityplayer) {
      NBTTagCompound nbt = new NBTTagCompound();
      this.func_70014_b(nbt);
      boolean isInGround = nbt.func_74771_c("inGround") == 1;
      if (!this.field_70170_p.field_72995_K && isInGround && this.field_70249_b <= 0) {
         boolean pickup = this.field_70251_a == 1 || this.field_70251_a == 2 && entityplayer.field_71075_bZ.field_75098_d;
         if (this.field_70251_a == 1 && !entityplayer.field_71071_by.func_70441_a(new ItemStack(LOTRMod.arrowPoisoned, 1))) {
            pickup = false;
         }

         if (pickup) {
            this.func_85030_a("random.pop", 0.2F, ((this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.7F + 1.0F) * 2.0F);
            entityplayer.func_71001_a(this, 1);
            this.func_70106_y();
         }
      }

   }
}
