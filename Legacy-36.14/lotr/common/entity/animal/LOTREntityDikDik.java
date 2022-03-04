package lotr.common.entity.animal;

import java.util.UUID;
import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntities;
import lotr.common.entity.LOTRRandomSkinEntity;
import lotr.common.entity.ai.LOTREntityAIAvoidWithChance;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class LOTREntityDikDik extends EntityCreature implements LOTRAmbientCreature, LOTRRandomSkinEntity {
   public LOTREntityDikDik(World world) {
      super(world);
      this.func_70105_a(0.6F, 1.0F);
      this.func_70661_as().func_75491_a(true);
      this.field_70714_bg.func_75776_a(0, new EntityAISwimming(this));
      this.field_70714_bg.func_75776_a(1, new EntityAIAvoidEntity(this, LOTREntityLionBase.class, 12.0F, 1.5D, 2.0D));
      this.field_70714_bg.func_75776_a(1, new LOTREntityAIAvoidWithChance(this, EntityPlayer.class, 12.0F, 1.5D, 2.0D, 0.1F));
      this.field_70714_bg.func_75776_a(2, new EntityAIPanic(this, 2.0D));
      this.field_70714_bg.func_75776_a(3, new EntityAIWander(this, 1.2D));
      this.field_70714_bg.func_75776_a(4, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
      this.field_70714_bg.func_75776_a(5, new EntityAILookIdle(this));
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(12.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.25D);
   }

   public void setUniqueID(UUID uuid) {
      this.field_96093_i = uuid;
   }

   public boolean func_70650_aV() {
      return true;
   }

   protected boolean func_70692_ba() {
      return true;
   }

   public boolean func_70601_bi() {
      return super.func_70601_bi() ? LOTRAmbientSpawnChecks.canSpawn(this, 8, 4, 32, 4, Material.field_151585_k, Material.field_151582_l) : false;
   }

   public float func_70783_a(int i, int j, int k) {
      Block block = this.field_70170_p.func_147439_a(i, j - 1, k);
      return block == Blocks.field_150349_c ? 10.0F : this.field_70170_p.func_72801_o(i, j, k) - 0.5F;
   }

   public int func_70627_aG() {
      return 300;
   }

   protected String func_70639_aQ() {
      return "lotr:deer.say";
   }

   protected String func_70621_aR() {
      return "lotr:deer.hurt";
   }

   protected String func_70673_aS() {
      return "lotr:deer.death";
   }

   protected float func_70647_i() {
      return super.func_70647_i() * 1.3F;
   }

   public ItemStack getPickedResult(MovingObjectPosition target) {
      return new ItemStack(LOTRMod.spawnEgg, 1, LOTREntities.getEntityID(this));
   }
}
