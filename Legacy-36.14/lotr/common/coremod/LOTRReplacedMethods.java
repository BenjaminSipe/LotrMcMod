package lotr.common.coremod;

import cpw.mods.fml.common.network.internal.FMLMessage;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import cpw.mods.fml.common.network.internal.FMLRuntimeCodec;
import cpw.mods.fml.common.network.internal.FMLMessage.EntitySpawnMessage;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.EntityRegistry.EntityRegistration;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import lotr.common.LOTRConfig;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.LOTRReflection;
import lotr.common.block.LOTRBlockDirtPath;
import lotr.common.block.LOTRBlockMechanisedRail;
import lotr.common.block.LOTRBlockMordorDirt;
import lotr.common.block.LOTRBlockMordorMoss;
import lotr.common.block.LOTRBlockMud;
import lotr.common.block.LOTRBlockMudGrass;
import lotr.common.block.LOTRBlockSand;
import lotr.common.block.LOTRBlockSandstone;
import lotr.common.block.LOTRBlockSlabDirt;
import lotr.common.block.LOTRBlockSlabGravel;
import lotr.common.block.LOTRBlockSlabSand;
import lotr.common.enchant.LOTREnchantmentHelper;
import lotr.common.entity.LOTRMountFunctions;
import lotr.common.entity.item.LOTREntityBanner;
import lotr.common.item.LOTRWeaponStats;
import lotr.common.util.LOTRCommonIcons;
import lotr.common.util.LOTRLog;
import lotr.common.world.spawning.LOTRSpawnerAnimals;
import net.minecraft.block.Block;
import net.minecraft.block.BlockClay;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.BlockGravel;
import net.minecraft.block.BlockMycelium;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.BlockSnowBlock;
import net.minecraft.block.BlockWall;
import net.minecraft.block.material.Material;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.command.IEntitySelector;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentDurability;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.server.S14PacketEntity;
import net.minecraft.network.play.server.S18PacketEntityTeleport;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class LOTRReplacedMethods {
   public static class BlockRendering {
      private static Random blockRand = new Random();
      private static Map naturalBlockClassTable = new HashMap();
      private static Map naturalBlockTable = new HashMap();
      private static Map cachedNaturalBlocks = new HashMap();

      private static void add(Class cls) {
         add(cls, 0, 1, 2, 3, 4, 5);
      }

      private static void add(Class cls, Integer... sides) {
         naturalBlockClassTable.put(cls, getSideFlagsFrom(sides));
      }

      private static void add(Block block, int meta) {
         add(block, -1, 0, 1, 2, 3, 4, 5);
      }

      private static void add(Block block, int meta, Integer... sides) {
         naturalBlockTable.put(new LOTRReplacedMethods.BlockRendering.BlockAndMeta(block, meta), getSideFlagsFrom(sides));
      }

      private static boolean[] getSideFlagsFrom(Integer... sides) {
         List sidesAsList = Arrays.asList(sides);
         boolean[] sideFlags = new boolean[6];

         for(int side = 0; side < sideFlags.length; ++side) {
            if (sidesAsList.contains(side)) {
               sideFlags[side] = true;
            }
         }

         return sideFlags;
      }

      private static void setupNaturalBlockTable() {
         add(BlockGrass.class, 1, 0);
         add(Blocks.field_150346_d, 0);
         add(Blocks.field_150346_d, 1);
         add(Blocks.field_150346_d, 2, 1, 0);
         add(LOTRBlockSlabDirt.class);
         add(LOTRBlockMudGrass.class, 1, 0);
         add(LOTRBlockMud.class);
         add(BlockSand.class);
         add(LOTRBlockSand.class);
         add(LOTRBlockSlabSand.class);
         add(Blocks.field_150322_A, 0, 1, 0);
         add(Blocks.field_150333_U, 1, 1, 0);
         add(Blocks.field_150334_T, 1, 1, 0);
         add(Blocks.field_150372_bz, 1, 0);
         add(LOTRMod.wallStoneV, 4, 1, 0);
         add(LOTRBlockSandstone.class, 1, 0);
         add(LOTRMod.slabSingle7, 5, 1, 0);
         add(LOTRMod.slabDouble7, 5, 1, 0);
         add(LOTRMod.stairsRedSandstone, 1, 0);
         add(LOTRMod.wallStoneV, 5, 1, 0);
         add(LOTRMod.slabSingle10, 6, 1, 0);
         add(LOTRMod.slabDouble10, 6, 1, 0);
         add(LOTRMod.stairsWhiteSandstone, 1, 0);
         add(LOTRMod.wall3, 14, 1, 0);
         add(LOTRMod.rock, 0, 1, 0);
         add(BlockGravel.class);
         add(LOTRBlockSlabGravel.class);
         add(BlockClay.class);
         add(BlockSnow.class);
         add(BlockSnowBlock.class);
         add(BlockMycelium.class, 1, 0);
         add(LOTRBlockMordorDirt.class);
         add(LOTRBlockDirtPath.class);
         add(LOTRBlockMordorMoss.class);
      }

      public static boolean renderStandardBlock(RenderBlocks renderblocks, Block block, int i, int j, int k) {
         if (naturalBlockClassTable.isEmpty()) {
            setupNaturalBlockTable();
         }

         if (!LOTRConfig.naturalBlocks) {
            return renderblocks.func_147784_q(block, i, j, k);
         } else {
            int meta = renderblocks.field_147845_a.func_72805_g(i, j, k);
            LOTRReplacedMethods.BlockRendering.BlockAndMeta bam = new LOTRReplacedMethods.BlockRendering.BlockAndMeta(block, meta);
            boolean[] sideFlags = null;
            if (cachedNaturalBlocks.containsKey(bam)) {
               sideFlags = (boolean[])cachedNaturalBlocks.get(bam);
            } else if (naturalBlockTable.containsKey(bam)) {
               sideFlags = (boolean[])naturalBlockTable.get(bam);
               cachedNaturalBlocks.put(bam, sideFlags);
            } else {
               Iterator var8 = naturalBlockClassTable.keySet().iterator();

               while(var8.hasNext()) {
                  Class cls = (Class)var8.next();
                  if (cls.isAssignableFrom(block.getClass())) {
                     sideFlags = (boolean[])naturalBlockClassTable.get(cls);
                     cachedNaturalBlocks.put(bam, sideFlags);
                  }
               }
            }

            if (sideFlags != null) {
               int[] randomSides = new int[6];

               for(int l = 0; l < randomSides.length; ++l) {
                  int hash = i * 234890405 ^ k * 37383934 ^ j;
                  hash += l * 285502;
                  blockRand.setSeed((long)hash);
                  blockRand.setSeed(blockRand.nextLong());
                  randomSides[l] = blockRand.nextInt(4);
               }

               if (sideFlags[0]) {
                  renderblocks.field_147865_v = randomSides[0];
               }

               if (sideFlags[1]) {
                  renderblocks.field_147867_u = randomSides[1];
               }

               if (sideFlags[2]) {
                  renderblocks.field_147869_t = randomSides[2];
               }

               if (sideFlags[3]) {
                  renderblocks.field_147871_s = randomSides[3];
               }

               if (sideFlags[4]) {
                  renderblocks.field_147873_r = randomSides[4];
               }

               if (sideFlags[5]) {
                  renderblocks.field_147875_q = randomSides[5];
               }
            }

            boolean flag = renderblocks.func_147784_q(block, i, j, k);
            if (sideFlags != null) {
               renderblocks.field_147875_q = 0;
               renderblocks.field_147873_r = 0;
               renderblocks.field_147871_s = 0;
               renderblocks.field_147869_t = 0;
               renderblocks.field_147867_u = 0;
               renderblocks.field_147865_v = 0;
            }

            return flag;
         }
      }

      private static class BlockAndMeta {
         public final Block block;
         public final int meta;

         public BlockAndMeta(Block b, int m) {
            this.block = b;
            this.meta = m;
         }

         public boolean equals(Object other) {
            if (!(other instanceof LOTRReplacedMethods.BlockRendering.BlockAndMeta)) {
               return false;
            } else {
               LOTRReplacedMethods.BlockRendering.BlockAndMeta otherBM = (LOTRReplacedMethods.BlockRendering.BlockAndMeta)other;
               return otherBM.block == this.block && otherBM.meta == this.meta;
            }
         }

         public int hashCode() {
            return (this.block.func_149739_a() + "_" + this.meta).hashCode();
         }
      }
   }

   public static class EntityPackets {
      public static Packet getMobSpawnPacket(Entity entity) {
         EntityRegistration er = EntityRegistry.instance().lookupModSpawn(entity.getClass(), false);
         if (er == null) {
            return null;
         } else if (er.usesVanillaSpawning()) {
            return null;
         } else {
            FMLMessage msg = new EntitySpawnMessage(er, entity, er.getContainer());
            ByteBuf data = Unpooled.buffer();
            data.writeByte(2);

            try {
               (new FMLRuntimeCodec()).encodeInto((ChannelHandlerContext)null, msg, data);
            } catch (Exception var5) {
               LOTRLog.logger.error("***********************************************");
               LOTRLog.logger.error("LOTR: ERROR sending mob spawn packet to client!");
               LOTRLog.logger.error("***********************************************");
            }

            return new FMLProxyPacket(data, "FML");
         }
      }
   }

   public static class NetHandlerClient {
      public static void handleEntityTeleport(NetHandlerPlayClient handler, S18PacketEntityTeleport packet) {
         World world = LOTRMod.proxy.getClientWorld();
         Entity entity = world.func_73045_a(packet.func_149451_c());
         if (entity != null) {
            entity.field_70118_ct = packet.func_149449_d();
            entity.field_70117_cu = packet.func_149448_e();
            entity.field_70116_cv = packet.func_149446_f();
            if (!LOTRMountFunctions.isPlayerControlledMount(entity)) {
               double d0 = (double)entity.field_70118_ct / 32.0D;
               double d1 = (double)entity.field_70117_cu / 32.0D + 0.015625D;
               double d2 = (double)entity.field_70116_cv / 32.0D;
               float f = (float)(packet.func_149450_g() * 360) / 256.0F;
               float f1 = (float)(packet.func_149447_h() * 360) / 256.0F;
               entity.func_70056_a(d0, d1, d2, f, f1, 3);
            }
         }

      }

      public static void handleEntityMovement(NetHandlerPlayClient handler, S14PacketEntity packet) {
         World world = LOTRMod.proxy.getClientWorld();
         Entity entity = packet.func_149065_a(world);
         if (entity != null) {
            entity.field_70118_ct += packet.func_149062_c();
            entity.field_70117_cu += packet.func_149061_d();
            entity.field_70116_cv += packet.func_149064_e();
            if (!LOTRMountFunctions.isPlayerControlledMount(entity)) {
               double d0 = (double)entity.field_70118_ct / 32.0D;
               double d1 = (double)entity.field_70117_cu / 32.0D;
               double d2 = (double)entity.field_70116_cv / 32.0D;
               float f = packet.func_149060_h() ? (float)(packet.func_149066_f() * 360) / 256.0F : entity.field_70177_z;
               float f1 = packet.func_149060_h() ? (float)(packet.func_149063_g() * 360) / 256.0F : entity.field_70125_A;
               entity.func_70056_a(d0, d1, d2, f, f1, 3);
            }
         }

      }
   }

   public static class ClientPlayer {
      public static void horseJump(EntityClientPlayerMP entityplayer) {
         int jump = (int)(entityplayer.func_110319_bJ() * 100.0F);
         Entity mount = entityplayer.field_70154_o;
         if (mount instanceof EntityHorse) {
            ((EntityHorse)mount).func_110206_u(jump);
         }

         entityplayer.field_71174_a.func_147297_a(new C0BPacketEntityAction(entityplayer, 6, jump));
      }
   }

   public static class Potions {
      public static double getStrengthModifier(Potion thisPotion, int level, AttributeModifier modifier) {
         return thisPotion.field_76415_H == Potion.field_76437_t.field_76415_H ? -0.5D * (double)(level + 1) : 0.5D * (double)(level + 1);
      }
   }

   public static class Enchants {
      public static boolean isPlayerMeleeKill(Entity entity, DamageSource source) {
         boolean flag = entity instanceof EntityPlayer && source.func_76364_f() == entity;
         return flag;
      }

      public static float getEnchantmentModifierLiving(float base, EntityLivingBase attacker, EntityLivingBase target) {
         float f = base + LOTREnchantmentHelper.calcEntitySpecificDamage(attacker.func_70694_bm(), target);
         return f;
      }

      public static float func_152377_a(float base, ItemStack itemstack, EnumCreatureAttribute creatureAttribute) {
         float f = base + LOTREnchantmentHelper.calcBaseMeleeDamageBoost(itemstack);
         return f;
      }

      public static boolean attemptDamageItem(ItemStack itemstack, int damages, Random random) {
         if (!itemstack.func_77984_f()) {
            return false;
         } else {
            if (damages > 0) {
               int unbreaking = EnchantmentHelper.func_77506_a(Enchantment.field_77347_r.field_77352_x, itemstack);
               int negated = 0;
               int l;
               if (unbreaking > 0) {
                  for(l = 0; l < damages; ++l) {
                     if (EnchantmentDurability.func_92097_a(itemstack, unbreaking, random)) {
                        ++negated;
                     }
                  }
               }

               for(l = 0; l < damages; ++l) {
                  if (LOTREnchantmentHelper.negateDamage(itemstack, random)) {
                     ++negated;
                  }
               }

               damages -= negated;
               if (damages <= 0) {
                  return false;
               }
            }

            itemstack.func_77964_b(itemstack.func_77960_j() + damages);
            return itemstack.func_77960_j() > itemstack.func_77958_k();
         }
      }

      public static int c_attemptDamageItem(int unmodified, ItemStack stack, int damages, Random random, EntityLivingBase elb) {
         int ret = unmodified;

         for(int i = 0; i < damages; ++i) {
            if (LOTREnchantmentHelper.negateDamage(stack, random)) {
               --ret;
            }
         }

         return ret;
      }

      public static boolean getSilkTouchModifier(boolean base, EntityLivingBase entity) {
         boolean flag = base;
         if (LOTREnchantmentHelper.isSilkTouch(entity.func_70694_bm())) {
            flag = true;
         }

         return flag;
      }

      public static int getKnockbackModifier(int base, EntityLivingBase attacker, EntityLivingBase target) {
         int i = base + LOTRWeaponStats.getBaseExtraKnockback(attacker.func_70694_bm());
         i += LOTREnchantmentHelper.calcExtraKnockback(attacker.func_70694_bm());
         return i;
      }

      public static int getFortuneModifier(int base, EntityLivingBase entity) {
         int i = base + LOTREnchantmentHelper.calcLootingLevel(entity.func_70694_bm());
         return i;
      }

      public static int getLootingModifier(int base, EntityLivingBase entity) {
         int i = base + LOTREnchantmentHelper.calcLootingLevel(entity.func_70694_bm());
         return i;
      }

      public static int getSpecialArmorProtection(int base, ItemStack[] armor, DamageSource source) {
         int i = base + LOTREnchantmentHelper.calcSpecialArmorSetProtection(armor, source);
         i = MathHelper.func_76125_a(i, 0, 25);
         return i;
      }

      public static int getMaxFireProtectionLevel(int base, Entity entity) {
         int i = Math.max(base, LOTREnchantmentHelper.getMaxFireProtectionLevel(entity.func_70035_c()));
         return i;
      }

      public static int getFireAspectModifier(int base, EntityLivingBase entity) {
         int i = base + LOTREnchantmentHelper.calcFireAspectForMelee(entity.func_70694_bm());
         return i;
      }

      public static int getDamageReduceAmount(ItemStack itemstack) {
         return LOTRWeaponStats.getArmorProtection(itemstack);
      }
   }

   public static class PathFinder {
      public static boolean isWoodenDoor(Block block) {
         return block instanceof BlockDoor && block.func_149688_o() == Material.field_151575_d;
      }

      public static boolean isFenceGate(Block block) {
         return block instanceof BlockFenceGate;
      }
   }

   public static class Spawner {
      public static int performSpawning_optimised(WorldServer world, boolean hostiles, boolean peacefuls, boolean rareTick) {
         return LOTRSpawnerAnimals.performSpawning(world, hostiles, peacefuls, rareTick);
      }
   }

   public static class Minecart {
      public static boolean checkForPoweredRail(EntityMinecart cart, int x, int y, int z, Block block, boolean flagIn) {
         World world = cart.field_70170_p;
         if (block instanceof LOTRBlockMechanisedRail) {
            LOTRBlockMechanisedRail mechRailBlock = (LOTRBlockMechanisedRail)block;
            int meta = world.func_72805_g(x, y, z);
            flagIn = mechRailBlock.isPowerOn(meta);
         }

         return flagIn;
      }

      public static boolean checkForDepoweredRail(EntityMinecart cart, int x, int y, int z, Block block, boolean flagIn) {
         World world = cart.field_70170_p;
         if (block instanceof LOTRBlockMechanisedRail) {
            LOTRBlockMechanisedRail mechRailBlock = (LOTRBlockMechanisedRail)block;
            int meta = world.func_72805_g(x, y, z);
            flagIn = !mechRailBlock.isPowerOn(meta);
         }

         return flagIn;
      }
   }

   public static class Lightning {
      public static void init(EntityLightningBolt bolt) {
         bolt.field_70155_l = 5.0D;
      }

      public static void doSetBlock(World world, int i, int j, int k, Block block) {
         if (block != Blocks.field_150480_ab || !LOTRConfig.disableLightningGrief) {
            world.func_147449_b(i, j, k, block);
         }
      }
   }

   public static class Food {
      public static float getExhaustionFactor() {
         return LOTRConfig.changedHunger ? 0.3F : 1.0F;
      }
   }

   public static class Player {
      public static boolean canEat(EntityPlayer entityplayer, boolean forced) {
         if (entityplayer.field_71075_bZ.field_75102_a) {
            return false;
         } else if (forced) {
            return true;
         } else if (entityplayer.func_71024_bL().func_75121_c()) {
            return true;
         } else {
            boolean feastMode = LOTRConfig.canAlwaysEat;
            if (entityplayer.field_70170_p.field_72995_K) {
               feastMode = LOTRLevelData.clientside_thisServer_feastMode;
            }

            return feastMode && entityplayer.field_70154_o == null;
         }
      }
   }

   public static class Anvil {
      public static AxisAlignedBB getCollisionBoundingBoxFromPool(Block block, World world, int i, int j, int k) {
         block.func_149719_a(world, i, j, k);
         return AxisAlignedBB.func_72330_a((double)i + block.func_149704_x(), (double)j + block.func_149665_z(), (double)k + block.func_149706_B(), (double)i + block.func_149753_y(), (double)j + block.func_149669_A(), (double)k + block.func_149693_C());
      }
   }

   public static class Cauldron {
      public static int getRenderType() {
         return LOTRMod.proxy == null ? 24 : LOTRMod.proxy.getVCauldronRenderID();
      }
   }

   public static class Piston {
      public static boolean canPushBlock(Block block, World world, int i, int j, int k, boolean flag) {
         AxisAlignedBB bannerSearchBox = AxisAlignedBB.func_72330_a((double)i, (double)j, (double)k, (double)(i + 1), (double)(j + 4), (double)(k + 1));
         List banners = world.func_82733_a(LOTREntityBanner.class, bannerSearchBox, new IEntitySelector() {
            public boolean func_82704_a(Entity entity) {
               LOTREntityBanner banner = (LOTREntityBanner)entity;
               return !banner.field_70128_L && banner.isProtectingTerritory();
            }
         });
         return !banners.isEmpty() ? false : LOTRReflection.canPistonPushBlock(block, world, i, j, k, flag);
      }
   }

   public static class Wall {
      public static boolean canConnectWallTo(IBlockAccess world, int i, int j, int k) {
         return LOTRReplacedMethods.Fence.canConnectFenceTo(world, i, j, k);
      }
   }

   public static class Trapdoor {
      public static boolean canPlaceBlockOnSide(World world, int i, int j, int k, int side) {
         return true;
      }

      public static boolean isValidSupportBlock(Block block) {
         return true;
      }

      public static int getRenderType(Block block) {
         return LOTRMod.proxy != null ? LOTRMod.proxy.getTrapdoorRenderID() : 0;
      }
   }

   public static class Pumpkin {
      public static int alterIconSideParam(Block block, int side, int meta) {
         if (block == Blocks.field_150423_aK) {
            if (meta == 2 && side == 2) {
               side = 3;
            } else if (meta == 3 && side == 5) {
               side = 4;
            } else if (meta == 0 && side == 3) {
               side = 2;
            } else if (meta == 1 && side == 4) {
               side = 5;
            }
         }

         return side;
      }
   }

   public static class Fence {
      public static boolean canConnectFenceTo(IBlockAccess world, int i, int j, int k) {
         Block block = world.func_147439_a(i, j, k);
         if (!(block instanceof BlockFence) && !(block instanceof BlockFenceGate) && !(block instanceof BlockWall)) {
            return block.func_149688_o().func_76218_k() && block.func_149686_d() && block.func_149688_o() != Material.field_151572_C;
         } else {
            return true;
         }
      }

      public static boolean canPlacePressurePlate(Block block) {
         return block instanceof BlockFence;
      }
   }

   public static class Fire {
      public static boolean isFireSpreadDisabled() {
         return LOTRConfig.disableFireSpread;
      }
   }

   public static class StaticLiquid {
      public static void updateTick_optimised(Block thisBlock, World world, int i, int j, int k, Random random) {
         if (thisBlock.func_149688_o() == Material.field_151587_i) {
            int tries = random.nextInt(3);
            int i1 = 0;

            while(true) {
               if (i1 >= tries) {
                  if (tries == 0) {
                     i1 = i;
                     int k1 = k;

                     for(int l = 0; l < 3; ++l) {
                        i = i1 + random.nextInt(3) - 1;
                        k = k1 + random.nextInt(3) - 1;
                        if (world.func_72899_e(i, j, k) && world.func_147437_c(i, j + 1, k) && isFlammable(world, i, j, k)) {
                           world.func_147449_b(i, j + 1, k, Blocks.field_150480_ab);
                        }
                     }
                  }
                  break;
               }

               i += random.nextInt(3) - 1;
               k += random.nextInt(3) - 1;
               ++j;
               if (!world.func_72899_e(i, j, k)) {
                  return;
               }

               Block block = world.func_147439_a(i, j, k);
               if (block.func_149688_o() == Material.field_151579_a) {
                  if (isFlammable(world, i - 1, j, k) || isFlammable(world, i + 1, j, k) || isFlammable(world, i, j, k - 1) || isFlammable(world, i, j, k + 1) || isFlammable(world, i, j - 1, k) || isFlammable(world, i, j + 1, k)) {
                     world.func_147449_b(i, j, k, Blocks.field_150480_ab);
                     return;
                  }
               } else if (block.func_149688_o().func_76230_c()) {
                  return;
               }

               ++i1;
            }
         }

      }

      private static boolean isFlammable(World world, int i, int j, int k) {
         return world.func_72899_e(i, j, k) && world.func_147439_a(i, j, k).func_149688_o().func_76217_h();
      }
   }

   public static class Dirt {
      public static String nameIndex1 = "coarse";

      public static int damageDropped(int i) {
         return i == 1 ? 1 : 0;
      }

      public static ItemStack createStackedBlock(Block thisBlock, int i) {
         Item item = Item.func_150898_a(thisBlock);
         return new ItemStack(item, 1, i);
      }

      public static void getSubBlocks(Block thisBlock, Item item, CreativeTabs tab, List list) {
         list.add(new ItemStack(thisBlock, 1, 0));
         list.add(new ItemStack(thisBlock, 1, 1));
         list.add(new ItemStack(thisBlock, 1, 2));
      }

      public static int getDamageValue(World world, int i, int j, int k) {
         int meta = world.func_72805_g(i, j, k);
         return meta;
      }
   }

   public static class Grass {
      public static final int MIN_GRASS_LIGHT = 4;
      public static final int MAX_GRASS_OPACITY = 2;
      public static final int MIN_SPREAD_LIGHT = 9;

      public static void updateTick_optimised(World world, int i, int j, int k, Random random) {
         if (!world.field_72995_K) {
            int checkRange = 1;
            if (!world.func_72904_c(i - checkRange, j - checkRange, k - checkRange, i + checkRange, j + checkRange, k + checkRange)) {
               return;
            }

            if (world.func_72957_l(i, j + 1, k) < 4 && world.getBlockLightOpacity(i, j + 1, k) > 2) {
               Block block = world.func_147439_a(i, j, k);
               if (block == Blocks.field_150349_c) {
                  world.func_147449_b(i, j, k, Blocks.field_150346_d);
               }

               if (block == LOTRMod.mudGrass) {
                  world.func_147449_b(i, j, k, LOTRMod.mud);
               }
            } else if (world.func_72957_l(i, j + 1, k) >= 9) {
               for(int l = 0; l < 4; ++l) {
                  int i1 = i + random.nextInt(3) - 1;
                  int j1 = j + random.nextInt(5) - 3;
                  int k1 = k + random.nextInt(3) - 1;
                  if (world.func_72899_e(i1, j1, k1) && world.func_72904_c(i1 - checkRange, j1 - checkRange, k1 - checkRange, i1 + checkRange, j1 + checkRange, k1 + checkRange) && world.func_72957_l(i1, j1 + 1, k1) >= 4 && world.getBlockLightOpacity(i1, j1 + 1, k1) <= 2) {
                     Block block = world.func_147439_a(i1, j1, k1);
                     int meta = world.func_72805_g(i1, j1, k1);
                     if (block == Blocks.field_150346_d && meta == 0) {
                        world.func_147465_d(i1, j1, k1, Blocks.field_150349_c, 0, 3);
                     }

                     if (block == LOTRMod.mud && meta == 0) {
                        world.func_147465_d(i1, j1, k1, LOTRMod.mudGrass, 0, 3);
                     }
                  }
               }
            }
         }

      }
   }

   public static class Stone {
      public static IIcon getIconWorld(Block block, IBlockAccess world, int i, int j, int k, int side) {
         if (LOTRConfig.snowyStone && block == Blocks.field_150348_b && side != 0 && side != 1) {
            Block above = world.func_147439_a(i, j + 1, k);
            Material aboveMat = above.func_149688_o();
            if (aboveMat == Material.field_151597_y || aboveMat == Material.field_151596_z) {
               return LOTRCommonIcons.iconStoneSnow;
            }
         }

         return block.func_149691_a(side, world.func_72805_g(i, j, k));
      }

      public static IIcon getIconSideMeta(Block block, IIcon defaultIcon, int side, int meta) {
         if (LOTRConfig.snowyStone && block == Blocks.field_150348_b && meta == 1000) {
            if (side == 1) {
               return Blocks.field_150433_aE.func_149691_a(1, 0);
            }

            if (side != 0) {
               return LOTRCommonIcons.iconStoneSnow;
            }
         }

         return defaultIcon;
      }
   }
}
