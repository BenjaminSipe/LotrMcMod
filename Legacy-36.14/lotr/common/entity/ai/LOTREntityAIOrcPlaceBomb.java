package lotr.common.entity.ai;

import lotr.common.block.LOTRBlockOrcBomb;
import lotr.common.entity.item.LOTREntityOrcBomb;
import lotr.common.entity.npc.LOTREntityOrc;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.world.World;

public class LOTREntityAIOrcPlaceBomb extends EntityAIBase {
   private World worldObj;
   private LOTREntityOrc attacker;
   private EntityLivingBase entityTarget;
   private double moveSpeed;
   private PathEntity entityPathEntity;
   private int rePathDelay;

   public LOTREntityAIOrcPlaceBomb(LOTREntityOrc entity, double speed) {
      this.attacker = entity;
      this.worldObj = entity.field_70170_p;
      this.moveSpeed = speed;
      this.func_75248_a(3);
   }

   public boolean func_75250_a() {
      EntityLivingBase entity = this.attacker.func_70638_az();
      if (entity == null) {
         return false;
      } else if (this.attacker.npcItemsInv.getBomb() == null) {
         return false;
      } else {
         this.entityTarget = entity;
         this.entityPathEntity = this.attacker.func_70661_as().func_75494_a(this.entityTarget);
         return this.entityPathEntity != null;
      }
   }

   public boolean func_75253_b() {
      if (this.attacker.npcItemsInv.getBomb() == null) {
         return false;
      } else {
         EntityLivingBase entity = this.attacker.func_70638_az();
         return entity == null ? false : (!this.entityTarget.func_70089_S() ? false : !this.attacker.func_70661_as().func_75500_f());
      }
   }

   public void func_75249_e() {
      this.attacker.func_70661_as().func_75484_a(this.entityPathEntity, this.moveSpeed);
      this.rePathDelay = 0;
   }

   public void func_75251_c() {
      this.entityTarget = null;
      this.attacker.func_70661_as().func_75499_g();
   }

   public void func_75246_d() {
      this.attacker.func_70671_ap().func_75651_a(this.entityTarget, 30.0F, 30.0F);
      if (this.attacker.func_70635_at().func_75522_a(this.entityTarget) && --this.rePathDelay <= 0) {
         this.rePathDelay = 4 + this.attacker.func_70681_au().nextInt(7);
         this.attacker.func_70661_as().func_75497_a(this.entityTarget, this.moveSpeed);
      }

      if ((double)this.attacker.func_70032_d(this.entityTarget) < 3.0D) {
         LOTREntityOrcBomb bomb = new LOTREntityOrcBomb(this.worldObj, this.attacker.field_70165_t, this.attacker.field_70163_u + 1.0D, this.attacker.field_70161_v, this.attacker);
         int meta = 0;
         ItemStack bombItem = this.attacker.npcItemsInv.getBomb();
         if (bombItem != null && Block.func_149634_a(bombItem.func_77973_b()) instanceof LOTRBlockOrcBomb) {
            meta = bombItem.func_77960_j();
         }

         bomb.setBombStrengthLevel(meta);
         bomb.setFuseFromHiredUnit();
         bomb.droppedByHiredUnit = this.attacker.hiredNPCInfo.isActive;
         bomb.droppedTargetingPlayer = this.entityTarget instanceof EntityPlayer;
         this.worldObj.func_72838_d(bomb);
         this.worldObj.func_72956_a(this.attacker, "game.tnt.primed", 1.0F, 1.0F);
         this.worldObj.func_72956_a(this.attacker, "lotr:orc.fire", 1.0F, (this.worldObj.field_73012_v.nextFloat() - this.worldObj.field_73012_v.nextFloat()) * 0.2F + 1.0F);
         this.attacker.npcItemsInv.setBomb((ItemStack)null);
         int bombSlot = 5;
         if (this.attacker.hiredReplacedInv.hasReplacedEquipment(bombSlot)) {
            this.attacker.hiredReplacedInv.onEquipmentChanged(bombSlot, (ItemStack)null);
         }

         this.attacker.refreshCurrentAttackMode();
      }

   }
}
