package lotr.common.entity.npc;

import java.util.Iterator;
import java.util.List;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.LOTRPlayerData;
import lotr.common.fac.LOTRFaction;
import lotr.common.item.LOTRItemManFlesh;
import lotr.common.item.LOTRItemThrowingAxe;
import lotr.common.item.LOTRMaterial;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public abstract class LOTREntityMan extends LOTREntityNPC {
   public LOTREntityMan(World world) {
      super(world);
   }

   public void func_70645_a(DamageSource damagesource) {
      super.func_70645_a(damagesource);
      if (!this.field_70170_p.field_72995_K && LOTRMod.canDropLoot(this.field_70170_p) && this.field_70146_Z.nextInt(5) == 0) {
         List manFleshFactions = LOTRItemManFlesh.getManFleshFactions();
         Entity damager = damagesource.func_76364_f();
         if (damager instanceof EntityLivingBase) {
            EntityLivingBase entity = (EntityLivingBase)damager;
            boolean isAligned = false;
            if (entity instanceof EntityPlayer) {
               LOTRPlayerData playerData = LOTRLevelData.getData((EntityPlayer)entity);
               Iterator var7 = manFleshFactions.iterator();

               while(var7.hasNext()) {
                  LOTRFaction f = (LOTRFaction)var7.next();
                  if (playerData.getAlignment(f) > 0.0F) {
                     isAligned = true;
                  }
               }
            } else {
               LOTRFaction npcFaction = LOTRMod.getNPCFaction(entity);
               isAligned = manFleshFactions.contains(npcFaction);
            }

            if (isAligned) {
               ItemStack itemstack = entity.func_70694_bm();
               if (itemstack != null) {
                  Item item = itemstack.func_77973_b();
                  ToolMaterial material = null;
                  if (item instanceof ItemSword) {
                     ItemSword sword = (ItemSword)item;
                     material = LOTRMaterial.getToolMaterialByName(sword.func_150932_j());
                  } else if (item instanceof ItemTool) {
                     ItemTool tool = (ItemTool)item;
                     material = tool.func_150913_i();
                  } else if (item instanceof LOTRItemThrowingAxe) {
                     LOTRItemThrowingAxe axe = (LOTRItemThrowingAxe)item;
                     material = axe.getAxeMaterial();
                  }

                  if (material != null) {
                     boolean canHarvest = false;
                     Iterator var10 = LOTRMaterial.allLOTRMaterials.iterator();

                     while(var10.hasNext()) {
                        LOTRMaterial lotrMaterial = (LOTRMaterial)var10.next();
                        if (lotrMaterial.toToolMaterial() == material && lotrMaterial.canHarvestManFlesh()) {
                           canHarvest = true;
                           break;
                        }
                     }

                     if (canHarvest) {
                        ItemStack flesh = new ItemStack(LOTRMod.manFlesh, 1 + this.field_70146_Z.nextInt(2));
                        this.func_70099_a(flesh, 0.0F);
                     }
                  }
               }
            }
         }
      }

   }
}
