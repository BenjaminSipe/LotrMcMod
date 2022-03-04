package lotr.common.entity.npc;

import lotr.common.LOTRLevelData;
import lotr.common.LOTRPlayerData;
import lotr.common.entity.LOTREntities;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.fac.LOTRFaction;
import lotr.common.item.LOTRItemCoin;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class LOTRUnitTradeEntry {
   public Class entityClass;
   public Class mountClass;
   private Item mountArmor;
   private float mountArmorChance;
   private String name;
   private int initialCost;
   public float alignmentRequired;
   private LOTRUnitTradeEntry.PledgeType pledgeType;
   public LOTRHiredNPCInfo.Task task;
   private String extraInfo;

   public LOTRUnitTradeEntry(Class c, int cost, float alignment) {
      this.pledgeType = LOTRUnitTradeEntry.PledgeType.NONE;
      this.task = LOTRHiredNPCInfo.Task.WARRIOR;
      this.extraInfo = null;
      this.entityClass = c;
      this.initialCost = cost;
      this.alignmentRequired = alignment;
      if (LOTRBannerBearer.class.isAssignableFrom(this.entityClass)) {
         this.setExtraInfo("Banner");
      }

   }

   public LOTRUnitTradeEntry(Class c, Class c1, String s, int cost, float alignment) {
      this(c, cost, alignment);
      this.mountClass = c1;
      this.name = s;
   }

   public LOTRUnitTradeEntry setTask(LOTRHiredNPCInfo.Task t) {
      this.task = t;
      return this;
   }

   public LOTRUnitTradeEntry setMountArmor(Item item) {
      return this.setMountArmor(item, 1.0F);
   }

   public LOTRUnitTradeEntry setMountArmor(Item item, float chance) {
      this.mountArmor = item;
      this.mountArmorChance = chance;
      return this;
   }

   public LOTRUnitTradeEntry setPledgeExclusive() {
      return this.setPledgeType(LOTRUnitTradeEntry.PledgeType.FACTION);
   }

   public LOTRUnitTradeEntry setPledgeType(LOTRUnitTradeEntry.PledgeType t) {
      this.pledgeType = t;
      return this;
   }

   public LOTRUnitTradeEntry.PledgeType getPledgeType() {
      return this.pledgeType;
   }

   public LOTRUnitTradeEntry setExtraInfo(String s) {
      this.extraInfo = s;
      return this;
   }

   public boolean hasExtraInfo() {
      return this.extraInfo != null;
   }

   public String getFormattedExtraInfo() {
      return StatCollector.func_74838_a("lotr.unitinfo." + this.extraInfo);
   }

   public int getCost(EntityPlayer entityplayer, LOTRHireableBase trader) {
      float cost = (float)this.initialCost;
      float maxDiscount = 0.5F;
      float notPledgedExpense = 2.0F;
      LOTRFaction fac = trader.getFaction();
      LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
      float alignment = pd.getAlignment(fac);
      boolean pledged = pd.isPledgedTo(fac);
      float alignSurplus = Math.max(alignment - this.alignmentRequired, 0.0F);
      float f;
      if (pledged) {
         f = alignSurplus / 1500.0F;
         f = MathHelper.func_76131_a(f, 0.0F, 1.0F);
         f *= 0.5F;
         cost *= 1.0F - f;
      } else {
         cost *= 2.0F;
         f = alignSurplus / 2000.0F;
         f = MathHelper.func_76131_a(f, 0.0F, 1.0F);
         f *= 0.5F;
         cost *= 1.0F - f;
      }

      int costI = Math.round(cost);
      costI = Math.max(costI, 1);
      return costI;
   }

   public boolean hasRequiredCostAndAlignment(EntityPlayer entityplayer, LOTRHireableBase trader) {
      int coins = LOTRItemCoin.getInventoryValue(entityplayer, false);
      if (coins < this.getCost(entityplayer, trader)) {
         return false;
      } else {
         LOTRFaction fac = trader.getFaction();
         if (!this.pledgeType.canAcceptPlayer(entityplayer, fac)) {
            return false;
         } else {
            float alignment = LOTRLevelData.getData(entityplayer).getAlignment(fac);
            return alignment >= this.alignmentRequired;
         }
      }
   }

   public String getUnitTradeName() {
      if (this.mountClass == null) {
         String entityName = LOTREntities.getStringFromClass(this.entityClass);
         return StatCollector.func_74838_a("entity." + entityName + ".name");
      } else {
         return StatCollector.func_74838_a("lotr.unit." + this.name);
      }
   }

   public void hireUnit(EntityPlayer entityplayer, LOTRHireableBase trader, String squadron) {
      if (this.hasRequiredCostAndAlignment(entityplayer, trader)) {
         trader.onUnitTrade(entityplayer);
         int cost = this.getCost(entityplayer, trader);
         LOTRItemCoin.takeCoins(cost, entityplayer);
         ((LOTREntityNPC)trader).playTradeSound();
         World world = entityplayer.field_70170_p;
         LOTREntityNPC hiredNPC = this.getOrCreateHiredNPC(world);
         if (hiredNPC != null) {
            EntityLiving mount = null;
            if (this.mountClass != null) {
               mount = this.createHiredMount(world);
            }

            boolean unitExists = world.field_72996_f.contains(hiredNPC);
            hiredNPC.hiredNPCInfo.hireUnit(entityplayer, !unitExists, trader.getFaction(), this, squadron, mount);
            if (!unitExists) {
               world.func_72838_d(hiredNPC);
               if (mount != null) {
                  world.func_72838_d(mount);
               }
            }
         }
      }

   }

   public LOTREntityNPC getOrCreateHiredNPC(World world) {
      LOTREntityNPC entity = (LOTREntityNPC)EntityList.func_75620_a(LOTREntities.getStringFromClass(this.entityClass), world);
      entity.initCreatureForHire((IEntityLivingData)null);
      entity.refreshCurrentAttackMode();
      return entity;
   }

   public EntityLiving createHiredMount(World world) {
      if (this.mountClass == null) {
         return null;
      } else {
         EntityLiving entity = (EntityLiving)EntityList.func_75620_a(LOTREntities.getStringFromClass(this.mountClass), world);
         if (entity instanceof LOTREntityNPC) {
            ((LOTREntityNPC)entity).initCreatureForHire((IEntityLivingData)null);
            ((LOTREntityNPC)entity).refreshCurrentAttackMode();
         } else {
            entity.func_110161_a((IEntityLivingData)null);
         }

         if (this.mountArmor != null && world.field_73012_v.nextFloat() < this.mountArmorChance) {
            if (entity instanceof LOTREntityHorse) {
               ((LOTREntityHorse)entity).setMountArmor(new ItemStack(this.mountArmor));
            } else if (entity instanceof LOTREntityWarg) {
               ((LOTREntityWarg)entity).setWargArmor(new ItemStack(this.mountArmor));
            }
         }

         return entity;
      }
   }

   public static enum PledgeType {
      NONE(0),
      FACTION(1),
      ANY_ELF(2),
      ANY_DWARF(3);

      public final int typeID;

      private PledgeType(int i) {
         this.typeID = i;
      }

      public boolean canAcceptPlayer(EntityPlayer entityplayer, LOTRFaction fac) {
         LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
         LOTRFaction pledged = pd.getPledgeFaction();
         if (this == NONE) {
            return true;
         } else if (this == FACTION) {
            return pd.isPledgedTo(fac);
         } else if (this == ANY_ELF) {
            return pledged != null && pledged.isOfType(LOTRFaction.FactionType.TYPE_ELF) && !pledged.isOfType(LOTRFaction.FactionType.TYPE_MAN);
         } else if (this != ANY_DWARF) {
            return false;
         } else {
            return pledged != null && pledged.isOfType(LOTRFaction.FactionType.TYPE_DWARF);
         }
      }

      public String getCommandReqText(LOTRFaction fac) {
         return this == NONE ? null : StatCollector.func_74837_a("lotr.hiredNPC.commandReq.pledge." + this.name(), new Object[]{fac.factionName()});
      }

      public static LOTRUnitTradeEntry.PledgeType forID(int i) {
         LOTRUnitTradeEntry.PledgeType[] var1 = values();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            LOTRUnitTradeEntry.PledgeType t = var1[var3];
            if (t.typeID == i) {
               return t;
            }
         }

         return NONE;
      }
   }
}
