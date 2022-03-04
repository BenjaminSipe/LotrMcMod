package lotr.client.render;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import java.util.Random;
import lotr.client.render.tileentity.LOTRRenderBeacon;
import lotr.client.render.tileentity.LOTRRenderChest;
import lotr.client.render.tileentity.LOTRRenderCommandTable;
import lotr.client.render.tileentity.LOTRRenderGuldurilGlow;
import lotr.client.render.tileentity.LOTRRenderTrollTotem;
import lotr.client.render.tileentity.LOTRRenderUnsmeltery;
import lotr.client.render.tileentity.LOTRTileEntityMobSpawnerRenderer;
import lotr.common.LOTRMod;
import lotr.common.block.LOTRBlockBeacon;
import lotr.common.block.LOTRBlockBirdCage;
import lotr.common.block.LOTRBlockClover;
import lotr.common.block.LOTRBlockCoralReef;
import lotr.common.block.LOTRBlockFlower;
import lotr.common.block.LOTRBlockFlowerPot;
import lotr.common.block.LOTRBlockGateDwarvenIthildin;
import lotr.common.block.LOTRBlockGrass;
import lotr.common.block.LOTRBlockRhunFireJar;
import lotr.common.block.LOTRBlockTallGrass;
import lotr.common.block.LOTRBlockTreasurePile;
import lotr.common.tileentity.LOTRTileEntityBeacon;
import lotr.common.tileentity.LOTRTileEntityChest;
import lotr.common.tileentity.LOTRTileEntityCommandTable;
import lotr.common.tileentity.LOTRTileEntityGulduril;
import lotr.common.tileentity.LOTRTileEntityMobSpawner;
import lotr.common.tileentity.LOTRTileEntityTrollTotem;
import lotr.common.tileentity.LOTRTileEntityUnsmeltery;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCauldron;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;

public class LOTRRenderBlocks implements ISimpleBlockRenderingHandler {
   private static Random blockRand = new Random();
   private boolean renderInvIn3D;

   public LOTRRenderBlocks(boolean flag) {
      this.renderInvIn3D = flag;
   }

   public boolean renderWorldBlock(IBlockAccess world, int i, int j, int k, Block block, int id, RenderBlocks renderblocks) {
      boolean fancyGraphics = Minecraft.func_71410_x().field_71474_y.field_74347_j;
      if (id == LOTRMod.proxy.getBeaconRenderID()) {
         this.renderBeacon(world, i, j, k, renderblocks);
         return true;
      } else if (id == LOTRMod.proxy.getBarrelRenderID()) {
         this.renderBarrel(world, i, j, k, block, renderblocks);
         return true;
      } else if (id == LOTRMod.proxy.getOrcBombRenderID()) {
         this.renderOrcBomb(world, i, j, k, block, renderblocks);
         return true;
      } else if (id == LOTRMod.proxy.getDoubleTorchRenderID()) {
         this.renderDoubleTorch(world, i, j, k, block, renderblocks);
         return true;
      } else if (id == LOTRMod.proxy.getMobSpawnerRenderID()) {
         return renderblocks.func_147784_q(block, i, j, k);
      } else if (id == LOTRMod.proxy.getPlateRenderID()) {
         this.renderPlate(world, i, j, k, block, renderblocks);
         return true;
      } else if (id == LOTRMod.proxy.getStalactiteRenderID()) {
         this.renderStalactite(world, i, j, k, block, renderblocks);
         return true;
      } else if (id == LOTRMod.proxy.getFlowerPotRenderID()) {
         this.renderFlowerPot(world, i, j, k, block, renderblocks);
         return true;
      } else if (id == LOTRMod.proxy.getCloverRenderID()) {
         renderClover(world, i, j, k, block, renderblocks, world.func_72805_g(i, j, k) == 1 ? 4 : 3, true);
         return true;
      } else if (id == LOTRMod.proxy.getEntJarRenderID()) {
         this.renderEntJar(world, i, j, k, block, renderblocks);
         return true;
      } else if (id == LOTRMod.proxy.getFenceRenderID()) {
         return renderblocks.func_147735_a((BlockFence)block, i, j, k);
      } else if (id == LOTRMod.proxy.getGrassRenderID()) {
         renderGrass(world, i, j, k, block, renderblocks, true);
         return true;
      } else if (id == LOTRMod.proxy.getFallenLeavesRenderID()) {
         if (fancyGraphics) {
            this.renderFallenLeaves(world, i, j, k, block, renderblocks, new int[]{6, 10}, new int[]{2, 6}, new int[]{2, 6}, 0.7F);
            return true;
         } else {
            return renderblocks.func_147784_q(block, i, j, k);
         }
      } else if (id == LOTRMod.proxy.getCommandTableRenderID()) {
         this.renderCommandTable(world, i, j, k, block, renderblocks);
         return true;
      } else if (id == LOTRMod.proxy.getButterflyJarRenderID()) {
         this.renderButterflyJar(world, i, j, k, block, renderblocks);
         return true;
      } else if (id == LOTRMod.proxy.getUnsmelteryRenderID()) {
         return true;
      } else if (id == LOTRMod.proxy.getChestRenderID()) {
         return true;
      } else if (id == LOTRMod.proxy.getReedsRenderID()) {
         this.renderReeds(world, i, j, k, block, renderblocks);
         return true;
      } else if (id == LOTRMod.proxy.getWasteRenderID()) {
         this.renderBlockRandomRotated(world, i, j, k, block, renderblocks, true);
         return true;
      } else if (id == LOTRMod.proxy.getBeamRenderID()) {
         this.renderBeam(world, i, j, k, block, renderblocks);
         return true;
      } else if (id == LOTRMod.proxy.getVCauldronRenderID()) {
         this.renderVanillaCauldron(world, i, j, k, block, renderblocks);
         return true;
      } else if (id == LOTRMod.proxy.getGrapevineRenderID()) {
         this.renderGrapevine(world, i, j, k, block, renderblocks);
         return true;
      } else if (id == LOTRMod.proxy.getThatchFloorRenderID()) {
         if (fancyGraphics) {
            this.renderFallenLeaves(world, i, j, k, block, renderblocks, new int[]{10, 16}, new int[]{6, 12}, new int[]{1, 1}, 1.0F);
            return true;
         } else {
            return renderblocks.func_147784_q(block, i, j, k);
         }
      } else if (id == LOTRMod.proxy.getTreasureRenderID()) {
         this.renderBlockRandomRotated(world, i, j, k, block, renderblocks, false);
         return true;
      } else if (id == LOTRMod.proxy.getFlowerRenderID()) {
         this.renderFlowerBlock(world, i, j, k, block, renderblocks);
         return true;
      } else if (id == LOTRMod.proxy.getDoublePlantRenderID()) {
         this.renderDoublePlant(world, i, j, k, (BlockDoublePlant)block, renderblocks);
         return true;
      } else if (id == LOTRMod.proxy.getBirdCageRenderID()) {
         this.renderBirdCage(world, i, j, k, block, renderblocks);
         return true;
      } else if (id == LOTRMod.proxy.getRhunFireJarRenderID()) {
         this.renderRhunFireJar(world, i, j, k, block, renderblocks);
         return true;
      } else if (id == LOTRMod.proxy.getCoralRenderID()) {
         this.renderCoral(world, i, j, k, block, renderblocks);
         return true;
      } else if (id == LOTRMod.proxy.getDoorRenderID()) {
         return this.renderDoor(world, i, j, k, block, renderblocks);
      } else if (id == LOTRMod.proxy.getRopeRenderID()) {
         this.renderRope(world, i, j, k, block, renderblocks);
         return true;
      } else if (id == LOTRMod.proxy.getOrcChainRenderID()) {
         IIcon icon = renderblocks.func_147758_b(block.func_149673_e(world, i, j, k, 0));
         renderblocks.func_147757_a(icon);
         boolean flag = renderblocks.func_147746_l(block, i, j, k);
         renderblocks.func_147771_a();
         return flag;
      } else if (id == LOTRMod.proxy.getGuldurilRenderID()) {
         return renderblocks.func_147784_q(block, i, j, k);
      } else if (id == LOTRMod.proxy.getOrcPlatingRenderID()) {
         this.renderBlockRandomRotated(world, i, j, k, block, renderblocks, true);
         return true;
      } else if (id == LOTRMod.proxy.getTrapdoorRenderID()) {
         this.renderTrapdoor(world, i, j, k, block, renderblocks);
         return true;
      } else {
         return false;
      }
   }

   public void renderInventoryBlock(Block block, int meta, int id, RenderBlocks renderblocks) {
      if (id == LOTRMod.proxy.getBeaconRenderID()) {
         ((LOTRRenderBeacon)TileEntityRendererDispatcher.field_147556_a.func_147546_a(LOTRTileEntityBeacon.class)).renderInvBeacon();
      }

      if (id == LOTRMod.proxy.getBarrelRenderID()) {
         this.renderInvBarrel(block, renderblocks);
      }

      if (id == LOTRMod.proxy.getOrcBombRenderID()) {
         this.renderInvOrcBomb(block, meta, renderblocks);
      }

      if (id == LOTRMod.proxy.getMobSpawnerRenderID()) {
         renderStandardInvBlock(renderblocks, block, 0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
         ((LOTRTileEntityMobSpawnerRenderer)TileEntityRendererDispatcher.field_147556_a.func_147546_a(LOTRTileEntityMobSpawner.class)).renderInvMobSpawner(meta);
      }

      if (id == LOTRMod.proxy.getStalactiteRenderID()) {
         this.renderInvStalactite(block, meta, renderblocks);
      }

      if (id == LOTRMod.proxy.getCloverRenderID()) {
         renderInvClover(block, renderblocks, meta == 1 ? 4 : 3);
      }

      if (id == LOTRMod.proxy.getEntJarRenderID()) {
         this.renderInvEntJar(block, renderblocks);
      }

      if (id == LOTRMod.proxy.getTrollTotemRenderID()) {
         ((LOTRRenderTrollTotem)TileEntityRendererDispatcher.field_147556_a.func_147546_a(LOTRTileEntityTrollTotem.class)).renderInvTrollTotem(meta);
      }

      if (id == LOTRMod.proxy.getFenceRenderID()) {
         this.renderInvFence(block, meta, renderblocks);
      }

      if (id == LOTRMod.proxy.getCommandTableRenderID()) {
         this.renderInvCommandTable(block, renderblocks);
         ((LOTRRenderCommandTable)TileEntityRendererDispatcher.field_147556_a.func_147546_a(LOTRTileEntityCommandTable.class)).renderInvTable();
      }

      if (id == LOTRMod.proxy.getButterflyJarRenderID()) {
         this.renderInvButterflyJar(block, renderblocks);
      }

      if (id == LOTRMod.proxy.getUnsmelteryRenderID()) {
         ((LOTRRenderUnsmeltery)TileEntityRendererDispatcher.field_147556_a.func_147546_a(LOTRTileEntityUnsmeltery.class)).renderInvUnsmeltery();
      }

      if (id == LOTRMod.proxy.getChestRenderID()) {
         ((LOTRRenderChest)TileEntityRendererDispatcher.field_147556_a.func_147546_a(LOTRTileEntityChest.class)).renderInvChest(block, meta);
      }

      if (id == LOTRMod.proxy.getWasteRenderID()) {
         renderStandardInvBlock(renderblocks, block, meta);
      }

      if (id == LOTRMod.proxy.getBeamRenderID()) {
         renderStandardInvBlock(renderblocks, block, meta);
      }

      if (id == LOTRMod.proxy.getTreasureRenderID()) {
         LOTRBlockTreasurePile.setTreasureBlockBounds(block, meta);
         renderblocks.func_147775_a(block);
         renderblocks.field_147847_n = true;
         renderStandardInvBlock(renderblocks, block, meta);
         renderblocks.func_147762_c();
      }

      if (id == LOTRMod.proxy.getBirdCageRenderID()) {
         this.renderInvBirdCage(block, renderblocks, meta);
      }

      if (id == LOTRMod.proxy.getRhunFireJarRenderID()) {
         this.renderInvRhunFireJar(block, renderblocks, meta);
      }

      if (id == LOTRMod.proxy.getCoralRenderID()) {
         renderStandardInvBlock(renderblocks, block, meta);
      }

      if (id == LOTRMod.proxy.getGuldurilRenderID()) {
         renderStandardInvBlock(renderblocks, block, 0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D, meta);
         ((LOTRRenderGuldurilGlow)TileEntityRendererDispatcher.field_147556_a.func_147546_a(LOTRTileEntityGulduril.class)).renderInvGlow();
      }

      if (id == LOTRMod.proxy.getOrcPlatingRenderID()) {
         renderStandardInvBlock(renderblocks, block, meta);
      }

      if (id == LOTRMod.proxy.getTrapdoorRenderID()) {
         renderStandardInvBlock(renderblocks, block, meta);
      }

   }

   public boolean shouldRender3DInInventory(int modelID) {
      return this.renderInvIn3D;
   }

   public int getRenderId() {
      return 0;
   }

   private void renderBeacon(IBlockAccess world, int i, int j, int k, RenderBlocks renderblocks) {
      if (LOTRBlockBeacon.isFullyLit(world, i, j, k)) {
         renderblocks.func_147801_a(Blocks.field_150480_ab, i, j, k);
      }

   }

   private void renderBarrel(IBlockAccess world, int i, int j, int k, Block block, RenderBlocks renderblocks) {
      int ao = getAO();
      setAO(0);
      renderblocks.field_147837_f = true;
      renderblocks.func_147782_a(0.15625D, 0.0625D, 0.15625D, 0.84375D, 0.75D, 0.84375D);
      renderblocks.func_147784_q(block, i, j, k);
      float[] var8 = new float[]{0.25F, 0.5F};
      int var9 = var8.length;

      int var10;
      float f;
      for(var10 = 0; var10 < var9; ++var10) {
         f = var8[var10];
         renderblocks.func_147782_a(0.125D, (double)f, 0.125D, 0.875D, (double)(f + 0.0625F), 0.875D);
         renderblocks.func_147784_q(block, i, j, k);
      }

      var8 = new float[]{0.0F, 0.6875F};
      var9 = var8.length;

      for(var10 = 0; var10 < var9; ++var10) {
         f = var8[var10];
         renderblocks.func_147782_a(0.125D, (double)f, 0.125D, 0.25D, (double)(f + 0.125F), 0.875D);
         renderblocks.func_147784_q(block, i, j, k);
         renderblocks.func_147782_a(0.75D, (double)f, 0.125D, 0.875D, (double)(f + 0.125F), 0.875D);
         renderblocks.func_147784_q(block, i, j, k);
         renderblocks.func_147782_a(0.25D, (double)f, 0.125D, 0.75D, (double)(f + 0.125F), 0.25D);
         renderblocks.func_147784_q(block, i, j, k);
         renderblocks.func_147782_a(0.25D, (double)f, 0.75D, 0.75D, (double)(f + 0.125F), 0.875D);
         renderblocks.func_147784_q(block, i, j, k);
      }

      renderblocks.func_147757_a(block.func_149733_h(-1));
      int meta = world.func_72805_g(i, j, k);
      if (meta == 2) {
         renderblocks.func_147782_a(0.4375D, 0.25D, 0.0D, 0.5625D, 0.375D, 0.125D);
         renderblocks.func_147784_q(block, i, j, k);
         renderblocks.func_147782_a(0.46875D, 0.125D, 0.0D, 0.53125D, 0.25D, 0.0625D);
         renderblocks.func_147784_q(block, i, j, k);
      } else if (meta == 3) {
         renderblocks.func_147782_a(0.4375D, 0.25D, 0.875D, 0.5625D, 0.375D, 1.0D);
         renderblocks.func_147784_q(block, i, j, k);
         renderblocks.func_147782_a(0.46875D, 0.125D, 0.9375D, 0.53125D, 0.25D, 1.0D);
         renderblocks.func_147784_q(block, i, j, k);
      } else if (meta == 4) {
         renderblocks.func_147782_a(0.0D, 0.25D, 0.4375D, 0.125D, 0.375D, 0.5625D);
         renderblocks.func_147784_q(block, i, j, k);
         renderblocks.func_147782_a(0.0D, 0.125D, 0.46875D, 0.0625D, 0.25D, 0.53125D);
         renderblocks.func_147784_q(block, i, j, k);
      } else if (meta == 5) {
         renderblocks.func_147782_a(0.875D, 0.25D, 0.4375D, 1.0D, 0.375D, 0.5625D);
         renderblocks.func_147784_q(block, i, j, k);
         renderblocks.func_147782_a(0.9375D, 0.125D, 0.46875D, 1.0D, 0.25D, 0.53125D);
         renderblocks.func_147784_q(block, i, j, k);
      }

      renderblocks.func_147771_a();
      renderblocks.field_147837_f = false;
      setAO(ao);
   }

   private void renderInvBarrel(Block block, RenderBlocks renderblocks) {
      renderblocks.field_147837_f = true;
      renderStandardInvBlock(renderblocks, block, 0.15625D, 0.0625D, 0.15625D, 0.84375D, 0.75D, 0.84375D);
      float[] var3 = new float[]{0.25F, 0.5F};
      int var4 = var3.length;

      int var5;
      float f;
      for(var5 = 0; var5 < var4; ++var5) {
         f = var3[var5];
         renderStandardInvBlock(renderblocks, block, 0.125D, (double)f, 0.125D, 0.875D, (double)(f + 0.0625F), 0.875D);
      }

      var3 = new float[]{0.0F, 0.6875F};
      var4 = var3.length;

      for(var5 = 0; var5 < var4; ++var5) {
         f = var3[var5];
         renderStandardInvBlock(renderblocks, block, 0.125D, (double)f, 0.125D, 0.25D, (double)(f + 0.125F), 0.875D);
         renderStandardInvBlock(renderblocks, block, 0.75D, (double)f, 0.125D, 0.875D, (double)(f + 0.125F), 0.875D);
         renderStandardInvBlock(renderblocks, block, 0.25D, (double)f, 0.125D, 0.75D, (double)(f + 0.125F), 0.25D);
         renderStandardInvBlock(renderblocks, block, 0.25D, (double)f, 0.75D, 0.75D, (double)(f + 0.125F), 0.875D);
      }

      renderblocks.func_147757_a(block.func_149733_h(-1));
      renderStandardInvBlock(renderblocks, block, 0.875D, 0.25D, 0.4375D, 1.0D, 0.375D, 0.5625D);
      renderStandardInvBlock(renderblocks, block, 0.9375D, 0.125D, 0.46875D, 1.0D, 0.25D, 0.53125D);
      renderblocks.func_147771_a();
      renderblocks.field_147837_f = false;
   }

   private void renderOrcBomb(IBlockAccess world, int i, int j, int k, Block block, RenderBlocks renderblocks) {
      int ao = getAO();
      setAO(0);
      renderblocks.field_147837_f = true;
      renderblocks.func_147782_a(0.125D, 0.1875D, 0.125D, 0.875D, 0.9375D, 0.875D);
      renderblocks.func_147784_q(block, i, j, k);
      renderblocks.func_147782_a(0.375D, 0.9375D, 0.375D, 0.625D, 1.0D, 0.625D);
      renderblocks.func_147784_q(block, i, j, k);
      renderblocks.func_147782_a(0.25D, 0.9375D, 0.375D, 0.3125D, 1.0D, 0.4375D);
      renderblocks.func_147784_q(block, i, j, k);
      renderblocks.func_147782_a(0.25D, 0.9375D, 0.5625D, 0.3125D, 1.0D, 0.625D);
      renderblocks.func_147784_q(block, i, j, k);
      renderblocks.func_147782_a(0.6875D, 0.9375D, 0.375D, 0.75D, 1.0D, 0.4375D);
      renderblocks.func_147784_q(block, i, j, k);
      renderblocks.func_147782_a(0.6875D, 0.9375D, 0.5625D, 0.75D, 1.0D, 0.625D);
      renderblocks.func_147784_q(block, i, j, k);
      renderblocks.func_147782_a(0.375D, 0.9375D, 0.25D, 0.4375D, 1.0D, 0.3125D);
      renderblocks.func_147784_q(block, i, j, k);
      renderblocks.func_147782_a(0.5625D, 0.9375D, 0.25D, 0.625D, 1.0D, 0.3125D);
      renderblocks.func_147784_q(block, i, j, k);
      renderblocks.func_147782_a(0.375D, 0.9375D, 0.6875D, 0.4375D, 1.0D, 0.75D);
      renderblocks.func_147784_q(block, i, j, k);
      renderblocks.func_147782_a(0.5625D, 0.9375D, 0.6875D, 0.625D, 1.0D, 0.75D);
      renderblocks.func_147784_q(block, i, j, k);
      renderblocks.func_147782_a(0.125D, 0.0D, 0.25D, 0.1875D, 0.1875D, 0.75D);
      renderblocks.func_147784_q(block, i, j, k);
      renderblocks.func_147782_a(0.8125D, 0.0D, 0.25D, 0.875D, 0.1875D, 0.75D);
      renderblocks.func_147784_q(block, i, j, k);
      renderblocks.func_147782_a(0.25D, 0.0D, 0.125D, 0.75D, 0.1875D, 0.1875D);
      renderblocks.func_147784_q(block, i, j, k);
      renderblocks.func_147782_a(0.25D, 0.0D, 0.8125D, 0.75D, 0.1875D, 0.875D);
      renderblocks.func_147784_q(block, i, j, k);
      renderblocks.func_147757_a(block.func_149691_a(-1, world.func_72805_g(i, j, k)));
      renderblocks.func_147782_a(0.0D, 0.625D, 0.3125D, 0.0625D, 0.6875D, 0.6875D);
      renderblocks.func_147784_q(block, i, j, k);
      renderblocks.func_147782_a(0.0625D, 0.625D, 0.3125D, 0.125D, 0.6875D, 0.375D);
      renderblocks.func_147784_q(block, i, j, k);
      renderblocks.func_147782_a(0.0625D, 0.625D, 0.625D, 0.125D, 0.6875D, 0.6875D);
      renderblocks.func_147784_q(block, i, j, k);
      renderblocks.func_147782_a(0.9375D, 0.625D, 0.3125D, 1.0D, 0.6875D, 0.6875D);
      renderblocks.func_147784_q(block, i, j, k);
      renderblocks.func_147782_a(0.875D, 0.625D, 0.3125D, 0.9375D, 0.6875D, 0.375D);
      renderblocks.func_147784_q(block, i, j, k);
      renderblocks.func_147782_a(0.875D, 0.625D, 0.625D, 0.9375D, 0.6875D, 0.6875D);
      renderblocks.func_147784_q(block, i, j, k);
      renderblocks.func_147782_a(0.3125D, 0.625D, 0.0D, 0.6875D, 0.6875D, 0.0625D);
      renderblocks.func_147784_q(block, i, j, k);
      renderblocks.func_147782_a(0.3125D, 0.625D, 0.0625D, 0.375D, 0.6875D, 0.125D);
      renderblocks.func_147784_q(block, i, j, k);
      renderblocks.func_147782_a(0.625D, 0.625D, 0.0625D, 0.6875D, 0.6875D, 0.125D);
      renderblocks.func_147784_q(block, i, j, k);
      renderblocks.func_147782_a(0.3125D, 0.625D, 0.9375D, 0.6875D, 0.6875D, 1.0D);
      renderblocks.func_147784_q(block, i, j, k);
      renderblocks.func_147782_a(0.3125D, 0.625D, 0.875D, 0.375D, 0.6875D, 0.9375D);
      renderblocks.func_147784_q(block, i, j, k);
      renderblocks.func_147782_a(0.625D, 0.625D, 0.875D, 0.6875D, 0.6875D, 0.9375D);
      renderblocks.func_147784_q(block, i, j, k);
      renderblocks.func_147771_a();
      renderblocks.field_147837_f = false;
      setAO(ao);
   }

   private void renderInvOrcBomb(Block block, int meta, RenderBlocks renderblocks) {
      renderblocks.field_147837_f = true;
      renderStandardInvBlock(renderblocks, block, 0.125D, 0.1875D, 0.125D, 0.875D, 0.9375D, 0.875D, meta);
      renderStandardInvBlock(renderblocks, block, 0.375D, 0.9375D, 0.375D, 0.625D, 1.0D, 0.625D, meta);
      renderStandardInvBlock(renderblocks, block, 0.25D, 0.9375D, 0.375D, 0.3125D, 1.0D, 0.4375D, meta);
      renderStandardInvBlock(renderblocks, block, 0.25D, 0.9375D, 0.5625D, 0.3125D, 1.0D, 0.625D, meta);
      renderStandardInvBlock(renderblocks, block, 0.6875D, 0.9375D, 0.375D, 0.75D, 1.0D, 0.4375D, meta);
      renderStandardInvBlock(renderblocks, block, 0.6875D, 0.9375D, 0.5625D, 0.75D, 1.0D, 0.625D, meta);
      renderStandardInvBlock(renderblocks, block, 0.375D, 0.9375D, 0.25D, 0.4375D, 1.0D, 0.3125D, meta);
      renderStandardInvBlock(renderblocks, block, 0.5625D, 0.9375D, 0.25D, 0.625D, 1.0D, 0.3125D, meta);
      renderStandardInvBlock(renderblocks, block, 0.375D, 0.9375D, 0.6875D, 0.4375D, 1.0D, 0.75D, meta);
      renderStandardInvBlock(renderblocks, block, 0.5625D, 0.9375D, 0.6875D, 0.625D, 1.0D, 0.75D, meta);
      renderStandardInvBlock(renderblocks, block, 0.125D, 0.0D, 0.25D, 0.1875D, 0.1875D, 0.75D, meta);
      renderStandardInvBlock(renderblocks, block, 0.8125D, 0.0D, 0.25D, 0.875D, 0.1875D, 0.75D, meta);
      renderStandardInvBlock(renderblocks, block, 0.25D, 0.0D, 0.125D, 0.75D, 0.1875D, 0.1875D, meta);
      renderStandardInvBlock(renderblocks, block, 0.25D, 0.0D, 0.8125D, 0.75D, 0.1875D, 0.875D, meta);
      renderblocks.func_147757_a(block.func_149691_a(-1, meta));
      renderStandardInvBlock(renderblocks, block, 0.0D, 0.625D, 0.3125D, 0.0625D, 0.6875D, 0.6875D, meta);
      renderStandardInvBlock(renderblocks, block, 0.0625D, 0.625D, 0.3125D, 0.125D, 0.6875D, 0.375D, meta);
      renderStandardInvBlock(renderblocks, block, 0.0625D, 0.625D, 0.625D, 0.125D, 0.6875D, 0.6875D, meta);
      renderStandardInvBlock(renderblocks, block, 0.9375D, 0.625D, 0.3125D, 1.0D, 0.6875D, 0.6875D, meta);
      renderStandardInvBlock(renderblocks, block, 0.875D, 0.625D, 0.3125D, 0.9375D, 0.6875D, 0.375D, meta);
      renderStandardInvBlock(renderblocks, block, 0.875D, 0.625D, 0.625D, 0.9375D, 0.6875D, 0.6875D, meta);
      renderStandardInvBlock(renderblocks, block, 0.3125D, 0.625D, 0.0D, 0.6875D, 0.6875D, 0.0625D, meta);
      renderStandardInvBlock(renderblocks, block, 0.3125D, 0.625D, 0.0625D, 0.375D, 0.6875D, 0.125D, meta);
      renderStandardInvBlock(renderblocks, block, 0.625D, 0.625D, 0.0625D, 0.6875D, 0.6875D, 0.125D, meta);
      renderStandardInvBlock(renderblocks, block, 0.3125D, 0.625D, 0.9375D, 0.6875D, 0.6875D, 1.0D, meta);
      renderStandardInvBlock(renderblocks, block, 0.3125D, 0.625D, 0.875D, 0.375D, 0.6875D, 0.9375D, meta);
      renderStandardInvBlock(renderblocks, block, 0.625D, 0.625D, 0.875D, 0.6875D, 0.6875D, 0.9375D, meta);
      renderblocks.func_147771_a();
      renderblocks.field_147837_f = false;
   }

   private void renderDoubleTorch(IBlockAccess world, int i, int j, int k, Block block, RenderBlocks renderblocks) {
      block.func_149719_a(world, i, j, k);
      renderblocks.func_147775_a(block);
      renderblocks.func_147784_q(block, i, j, k);
   }

   private void renderPlate(IBlockAccess world, int i, int j, int k, Block block, RenderBlocks renderblocks) {
      int ao = getAO();
      setAO(0);
      renderblocks.field_147837_f = true;
      renderblocks.func_147782_a(0.1875D, 0.0D, 0.1875D, 0.8125D, 0.0625D, 0.8125D);
      renderblocks.func_147784_q(block, i, j, k);
      renderblocks.func_147782_a(0.125D, 0.0625D, 0.125D, 0.875D, 0.125D, 0.875D);
      renderblocks.func_147784_q(block, i, j, k);
      renderblocks.field_147837_f = false;
      setAO(ao);
   }

   public static void renderEntityPlate(IBlockAccess world, int i, int j, int k, Block block, RenderBlocks renderblocks) {
      renderblocks.field_147837_f = true;
      renderStandardInvBlock(renderblocks, block, 0.1875D, 0.0D, 0.1875D, 0.8125D, 0.0625D, 0.8125D);
      renderStandardInvBlock(renderblocks, block, 0.125D, 0.0625D, 0.125D, 0.875D, 0.125D, 0.875D);
      renderblocks.field_147837_f = false;
   }

   private void renderStalactite(IBlockAccess world, int i, int j, int k, Block block, RenderBlocks renderblocks) {
      int ao = getAO();
      setAO(0);
      renderblocks.field_147837_f = true;
      int metadata = world.func_72805_g(i, j, k);

      for(int l = 0; l < 16; ++l) {
         if (metadata == 0) {
            renderblocks.func_147782_a((double)(0.5F - (float)l / 16.0F * 0.25F), (double)((float)l / 16.0F), (double)(0.5F - (float)l / 16.0F * 0.25F), (double)(0.5F + (float)l / 16.0F * 0.25F), (double)((float)(l + 1) / 16.0F), (double)(0.5F + (float)l / 16.0F * 0.25F));
            renderblocks.func_147784_q(block, i, j, k);
         } else if (metadata == 1) {
            renderblocks.func_147782_a((double)(0.25F + (float)l / 16.0F * 0.25F), (double)((float)l / 16.0F), (double)(0.25F + (float)l / 16.0F * 0.25F), (double)(0.75F - (float)l / 16.0F * 0.25F), (double)((float)(l + 1) / 16.0F), (double)(0.75F - (float)l / 16.0F * 0.25F));
            renderblocks.func_147784_q(block, i, j, k);
         }
      }

      renderblocks.field_147837_f = false;
      setAO(ao);
   }

   private void renderInvStalactite(Block block, int metadata, RenderBlocks renderblocks) {
      renderblocks.field_147837_f = true;

      for(int l = 0; l < 16; ++l) {
         if (metadata == 0) {
            renderStandardInvBlock(renderblocks, block, (double)(0.5F - (float)l / 16.0F * 0.25F), (double)((float)l / 16.0F), (double)(0.5F - (float)l / 16.0F * 0.25F), (double)(0.5F + (float)l / 16.0F * 0.25F), (double)((float)(l + 1) / 16.0F), (double)(0.5F + (float)l / 16.0F * 0.25F));
         } else if (metadata == 1) {
            renderStandardInvBlock(renderblocks, block, (double)(0.25F + (float)l / 16.0F * 0.25F), (double)((float)l / 16.0F), (double)(0.25F + (float)l / 16.0F * 0.25F), (double)(0.75F - (float)l / 16.0F * 0.25F), (double)((float)(l + 1) / 16.0F), (double)(0.75F - (float)l / 16.0F * 0.25F));
         }
      }

      renderblocks.field_147837_f = false;
   }

   private void renderFlowerPot(IBlockAccess world, int i, int j, int k, Block block, RenderBlocks renderblocks) {
      renderblocks.func_147784_q(block, i, j, k);
      Tessellator tessellator = Tessellator.field_78398_a;
      tessellator.func_78380_c(block.func_149677_c(world, i, j, k));
      float f = 1.0F;
      int l = block.func_149720_d(world, i, j, k);
      IIcon icon = renderblocks.func_147777_a(block, 0);
      float f1 = (float)(l >> 16 & 255) / 255.0F;
      float f2 = (float)(l >> 8 & 255) / 255.0F;
      float f3 = (float)(l & 255) / 255.0F;
      float f4;
      if (EntityRenderer.field_78517_a) {
         f4 = (f1 * 30.0F + f2 * 59.0F + f3 * 11.0F) / 100.0F;
         float f5 = (f1 * 30.0F + f2 * 70.0F) / 100.0F;
         float f6 = (f1 * 30.0F + f3 * 70.0F) / 100.0F;
         f1 = f4;
         f2 = f5;
         f3 = f6;
      }

      tessellator.func_78386_a(f * f1, f * f2, f * f3);
      f4 = 0.1865F;
      renderblocks.func_147764_f(block, (double)((float)i - 0.5F + f4), (double)j, (double)k, icon);
      renderblocks.func_147798_e(block, (double)((float)i + 0.5F - f4), (double)j, (double)k, icon);
      renderblocks.func_147734_d(block, (double)i, (double)j, (double)((float)k - 0.5F + f4), icon);
      renderblocks.func_147761_c(block, (double)i, (double)j, (double)((float)k + 0.5F - f4), icon);
      renderblocks.func_147806_b(block, (double)i, (double)((float)j - 0.5F + f4 + 0.1875F), (double)k, renderblocks.func_147745_b(Blocks.field_150346_d));
      ItemStack plant = LOTRBlockFlowerPot.getPlant(world, i, j, k);
      if (plant != null) {
         Block plantBlock = Block.func_149634_a(plant.func_77973_b());
         int plantMeta = plant.func_77960_j();
         int plantColor = plantBlock.func_149741_i(plantMeta);
         if (plantColor != 16777215) {
            float plantR = (float)(plantColor >> 16 & 255) / 255.0F;
            float plantG = (float)(plantColor >> 8 & 255) / 255.0F;
            float plantB = (float)(plantColor & 255) / 255.0F;
            tessellator.func_78386_a(plantR, plantG, plantB);
         }

         tessellator.func_78372_c(0.0F, 0.25F, 0.0F);
         if (plantBlock == LOTRMod.shireHeather) {
            renderblocks.func_147765_a(plantBlock.func_149691_a(2, plantMeta), (double)i, (double)j, (double)k, 0.6F);
         } else if (plantBlock == LOTRMod.clover) {
            renderClover(world, i, j, k, plantBlock, renderblocks, plantMeta == 1 ? 4 : 3, false);
         } else if (plantBlock instanceof LOTRBlockGrass) {
            renderGrass(world, i, j, k, plantBlock, renderblocks, false);
         } else if (plantBlock instanceof LOTRBlockFlower) {
            renderblocks.func_147765_a(plantBlock.func_149691_a(2, plantMeta), (double)i, (double)j, (double)k, 0.75F);
         } else if (plantBlock.func_149645_b() == 1) {
            renderblocks.func_147765_a(plantBlock.func_149691_a(2, plantMeta), (double)i, (double)j, (double)k, 0.75F);
         } else {
            renderblocks.func_147805_b(plantBlock, i, j, k);
         }

         tessellator.func_78372_c(0.0F, -0.25F, 0.0F);
      }

   }

   private static void renderClover(IBlockAccess world, int i, int j, int k, Block block, RenderBlocks renderblocks, int petalCount, boolean randomTranslation) {
      double scale = 0.5D;
      Tessellator tessellator = Tessellator.field_78398_a;
      tessellator.func_78380_c(block.func_149677_c(world, i, j, k));
      int l = block.func_149720_d(world, i, j, k);
      float f = 1.0F;
      float f1 = (float)(l >> 16 & 255) / 255.0F;
      float f2 = (float)(l >> 8 & 255) / 255.0F;
      float f3 = (float)(l & 255) / 255.0F;
      if (EntityRenderer.field_78517_a) {
         float f4 = (f1 * 30.0F + f2 * 59.0F + f3 * 11.0F) / 100.0F;
         float f5 = (f1 * 30.0F + f2 * 70.0F) / 100.0F;
         float f6 = (f1 * 30.0F + f3 * 70.0F) / 100.0F;
         f1 = f4;
         f2 = f5;
         f3 = f6;
      }

      tessellator.func_78386_a(f * f1, f * f2, f * f3);
      renderblocks.func_147757_a(LOTRBlockClover.stemIcon);
      double posX = (double)i;
      double posY = (double)j;
      double posZ = (double)k;
      if (randomTranslation) {
         long seed = (long)(i * 3129871) ^ (long)k * 116129781L ^ (long)j;
         seed = seed * seed * 42317861L + seed * 11L;
         posX += ((double)((float)(seed >> 16 & 15L) / 15.0F) - 0.5D) * 0.5D;
         posZ += ((double)((float)(seed >> 24 & 15L) / 15.0F) - 0.5D) * 0.5D;
      }

      renderblocks.func_147765_a(block.func_149691_a(2, 0), posX, posY, posZ, (float)scale);
      renderblocks.func_147771_a();

      for(int petal = 0; petal < petalCount; ++petal) {
         float rotation = (float)petal / (float)petalCount * 3.1415927F * 2.0F;
         IIcon icon = LOTRBlockClover.petalIcon;
         double d = (double)icon.func_94209_e();
         double d1 = (double)icon.func_94206_g();
         double d2 = (double)icon.func_94212_f();
         double d3 = (double)icon.func_94210_h();
         double d4 = posX + 0.5D;
         double d5 = posY + 0.5D * scale + (double)petal * 0.0025D;
         double d6 = posZ + 0.5D;
         Vec3[] vecs = new Vec3[]{Vec3.func_72443_a(0.5D * scale, 0.0D, -0.5D * scale), Vec3.func_72443_a(-0.5D * scale, 0.0D, -0.5D * scale), Vec3.func_72443_a(-0.5D * scale, 0.0D, 0.5D * scale), Vec3.func_72443_a(0.5D * scale, 0.0D, 0.5D * scale)};

         for(int l1 = 0; l1 < vecs.length; ++l1) {
            vecs[l1].func_72442_b(rotation);
            vecs[l1].field_72450_a += d4;
            vecs[l1].field_72448_b += d5;
            vecs[l1].field_72449_c += d6;
         }

         tessellator.func_78374_a(vecs[0].field_72450_a, vecs[0].field_72448_b, vecs[0].field_72449_c, d, d1);
         tessellator.func_78374_a(vecs[1].field_72450_a, vecs[1].field_72448_b, vecs[1].field_72449_c, d, d3);
         tessellator.func_78374_a(vecs[2].field_72450_a, vecs[2].field_72448_b, vecs[2].field_72449_c, d2, d3);
         tessellator.func_78374_a(vecs[3].field_72450_a, vecs[3].field_72448_b, vecs[3].field_72449_c, d2, d1);
         tessellator.func_78374_a(vecs[3].field_72450_a, vecs[3].field_72448_b, vecs[3].field_72449_c, d2, d1);
         tessellator.func_78374_a(vecs[2].field_72450_a, vecs[2].field_72448_b, vecs[2].field_72449_c, d2, d3);
         tessellator.func_78374_a(vecs[1].field_72450_a, vecs[1].field_72448_b, vecs[1].field_72449_c, d, d3);
         tessellator.func_78374_a(vecs[0].field_72450_a, vecs[0].field_72448_b, vecs[0].field_72449_c, d, d1);
      }

   }

   private static void renderInvClover(Block block, RenderBlocks renderblocks, int petalCount) {
      GL11.glDisable(2896);
      double scale = 1.0D;
      Tessellator tessellator = Tessellator.field_78398_a;
      int l = block.func_149741_i(0);
      float f = 1.0F;
      float f1 = (float)(l >> 16 & 255) / 255.0F;
      float f2 = (float)(l >> 8 & 255) / 255.0F;
      float f3 = (float)(l & 255) / 255.0F;
      float rotation;
      if (EntityRenderer.field_78517_a) {
         float f4 = (f1 * 30.0F + f2 * 59.0F + f3 * 11.0F) / 100.0F;
         rotation = (f1 * 30.0F + f2 * 70.0F) / 100.0F;
         float f6 = (f1 * 30.0F + f3 * 70.0F) / 100.0F;
         f1 = f4;
         f2 = rotation;
         f3 = f6;
      }

      tessellator.func_78386_a(f * f1, f * f2, f * f3);
      renderblocks.func_147757_a(LOTRBlockClover.stemIcon);
      tessellator.func_78382_b();
      renderblocks.func_147765_a(block.func_149691_a(2, 0), -scale * 0.5D, -scale * 0.5D, -scale * 0.5D, (float)scale);
      tessellator.func_78381_a();
      renderblocks.func_147771_a();

      for(int petal = 0; petal < petalCount; ++petal) {
         rotation = (float)petal / (float)petalCount * 3.1415927F * 2.0F;
         IIcon icon = LOTRBlockClover.petalIcon;
         double d = (double)icon.func_94209_e();
         double d1 = (double)icon.func_94206_g();
         double d2 = (double)icon.func_94212_f();
         double d3 = (double)icon.func_94210_h();
         double d4 = 0.0D;
         double d5 = (double)petal * 0.0025D;
         double d6 = 0.0D;
         Vec3[] vecs = new Vec3[]{Vec3.func_72443_a(0.5D * scale, 0.0D, -0.5D * scale), Vec3.func_72443_a(-0.5D * scale, 0.0D, -0.5D * scale), Vec3.func_72443_a(-0.5D * scale, 0.0D, 0.5D * scale), Vec3.func_72443_a(0.5D * scale, 0.0D, 0.5D * scale)};

         for(int l1 = 0; l1 < vecs.length; ++l1) {
            vecs[l1].func_72442_b(rotation);
            vecs[l1].field_72450_a += d4;
            vecs[l1].field_72448_b += d5;
            vecs[l1].field_72449_c += d6;
         }

         tessellator.func_78382_b();
         tessellator.func_78374_a(vecs[0].field_72450_a, vecs[0].field_72448_b, vecs[0].field_72449_c, d, d1);
         tessellator.func_78374_a(vecs[1].field_72450_a, vecs[1].field_72448_b, vecs[1].field_72449_c, d, d3);
         tessellator.func_78374_a(vecs[2].field_72450_a, vecs[2].field_72448_b, vecs[2].field_72449_c, d2, d3);
         tessellator.func_78374_a(vecs[3].field_72450_a, vecs[3].field_72448_b, vecs[3].field_72449_c, d2, d1);
         tessellator.func_78374_a(vecs[3].field_72450_a, vecs[3].field_72448_b, vecs[3].field_72449_c, d2, d1);
         tessellator.func_78374_a(vecs[2].field_72450_a, vecs[2].field_72448_b, vecs[2].field_72449_c, d2, d3);
         tessellator.func_78374_a(vecs[1].field_72450_a, vecs[1].field_72448_b, vecs[1].field_72449_c, d, d3);
         tessellator.func_78374_a(vecs[0].field_72450_a, vecs[0].field_72448_b, vecs[0].field_72449_c, d, d1);
         tessellator.func_78381_a();
      }

      GL11.glEnable(2896);
   }

   public static void renderDwarvenDoorGlow(LOTRBlockGateDwarvenIthildin block, RenderBlocks renderblocks, int i, int j, int k) {
      Tessellator tessellator = Tessellator.field_78398_a;
      block.func_149719_a(renderblocks.field_147845_a, i, j, k);
      renderblocks.func_147775_a(block);
      double d = 0.01D;
      IIcon icon = block.getGlowIcon(renderblocks.field_147845_a, i, j, k, 0);
      if (icon != null) {
         tessellator.func_78382_b();
         renderblocks.func_147768_a(block, 0.0D, -d, 0.0D, icon);
         tessellator.func_78381_a();
         renderblocks.field_147842_e = false;
      }

      icon = block.getGlowIcon(renderblocks.field_147845_a, i, j, k, 1);
      if (icon != null) {
         tessellator.func_78382_b();
         renderblocks.func_147806_b(block, 0.0D, d, 0.0D, icon);
         tessellator.func_78381_a();
         renderblocks.field_147842_e = false;
      }

      icon = block.getGlowIcon(renderblocks.field_147845_a, i, j, k, 2);
      if (icon != null) {
         tessellator.func_78382_b();
         renderblocks.func_147761_c(block, 0.0D, 0.0D, -d, icon);
         tessellator.func_78381_a();
         renderblocks.field_147842_e = false;
      }

      icon = block.getGlowIcon(renderblocks.field_147845_a, i, j, k, 3);
      if (icon != null) {
         tessellator.func_78382_b();
         renderblocks.func_147734_d(block, 0.0D, 0.0D, d, icon);
         tessellator.func_78381_a();
         renderblocks.field_147842_e = false;
      }

      icon = block.getGlowIcon(renderblocks.field_147845_a, i, j, k, 4);
      if (icon != null) {
         tessellator.func_78382_b();
         renderblocks.func_147798_e(block, -d, 0.0D, 0.0D, icon);
         tessellator.func_78381_a();
         renderblocks.field_147842_e = false;
      }

      icon = block.getGlowIcon(renderblocks.field_147845_a, i, j, k, 5);
      if (icon != null) {
         tessellator.func_78382_b();
         renderblocks.func_147764_f(block, d, 0.0D, 0.0D, icon);
         tessellator.func_78381_a();
         renderblocks.field_147842_e = false;
      }

   }

   private void renderEntJar(IBlockAccess world, int i, int j, int k, Block block, RenderBlocks renderblocks) {
      int ao = getAO();
      setAO(0);
      renderblocks.field_147837_f = true;
      renderblocks.func_147782_a(0.25D, 0.0D, 0.25D, 0.75D, 0.0625D, 0.75D);
      renderblocks.func_147784_q(block, i, j, k);
      renderblocks.func_147782_a(0.25D, 0.0625D, 0.25D, 0.75D, 0.875D, 0.3125D);
      renderblocks.func_147784_q(block, i, j, k);
      renderblocks.func_147782_a(0.25D, 0.0625D, 0.6875D, 0.75D, 0.875D, 0.75D);
      renderblocks.func_147784_q(block, i, j, k);
      renderblocks.func_147782_a(0.25D, 0.0625D, 0.3125D, 0.31255000829696655D, 0.875D, 0.6875D);
      renderblocks.func_147784_q(block, i, j, k);
      renderblocks.func_147782_a(0.6875D, 0.0625D, 0.3125D, 0.75D, 0.875D, 0.6875D);
      renderblocks.func_147784_q(block, i, j, k);
      renderblocks.field_147837_f = false;
      setAO(ao);
   }

   private void renderInvEntJar(Block block, RenderBlocks renderblocks) {
      renderblocks.field_147837_f = true;
      renderStandardInvBlock(renderblocks, block, 0.25D, 0.0D, 0.25D, 0.75D, 0.0625D, 0.75D);
      renderStandardInvBlock(renderblocks, block, 0.25D, 0.0625D, 0.25D, 0.75D, 0.875D, 0.3125D);
      renderStandardInvBlock(renderblocks, block, 0.25D, 0.0625D, 0.6875D, 0.75D, 0.875D, 0.75D);
      renderStandardInvBlock(renderblocks, block, 0.25D, 0.0625D, 0.3125D, 0.31255000829696655D, 0.875D, 0.6875D);
      renderStandardInvBlock(renderblocks, block, 0.6875D, 0.0625D, 0.3125D, 0.75D, 0.875D, 0.6875D);
      renderblocks.field_147837_f = false;
   }

   private void renderInvFence(Block block, int meta, RenderBlocks renderblocks) {
      for(int k = 0; k < 4; ++k) {
         float f = 0.125F;
         float f1 = 0.0625F;
         if (k == 0) {
            renderblocks.func_147782_a((double)(0.5F - f), 0.0D, 0.0D, (double)(0.5F + f), 1.0D, (double)(f * 2.0F));
         }

         if (k == 1) {
            renderblocks.func_147782_a((double)(0.5F - f), 0.0D, (double)(1.0F - f * 2.0F), (double)(0.5F + f), 1.0D, 1.0D);
         }

         if (k == 2) {
            renderblocks.func_147782_a((double)(0.5F - f1), (double)(1.0F - f1 * 3.0F), (double)(-f1 * 2.0F), (double)(0.5F + f1), (double)(1.0F - f1), (double)(1.0F + f1 * 2.0F));
         }

         if (k == 3) {
            renderblocks.func_147782_a((double)(0.5F - f1), (double)(0.5F - f1 * 3.0F), (double)(-f1 * 2.0F), (double)(0.5F + f1), (double)(0.5F - f1), (double)(1.0F + f1 * 2.0F));
         }

         GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
         Tessellator tessellator = Tessellator.field_78398_a;
         tessellator.func_78382_b();
         tessellator.func_78375_b(0.0F, -1.0F, 0.0F);
         renderblocks.func_147768_a(block, 0.0D, 0.0D, 0.0D, renderblocks.func_147787_a(block, 0, meta));
         tessellator.func_78381_a();
         tessellator.func_78382_b();
         tessellator.func_78375_b(0.0F, 1.0F, 0.0F);
         renderblocks.func_147806_b(block, 0.0D, 0.0D, 0.0D, renderblocks.func_147787_a(block, 1, meta));
         tessellator.func_78381_a();
         tessellator.func_78382_b();
         tessellator.func_78375_b(0.0F, 0.0F, -1.0F);
         renderblocks.func_147761_c(block, 0.0D, 0.0D, 0.0D, renderblocks.func_147787_a(block, 2, meta));
         tessellator.func_78381_a();
         tessellator.func_78382_b();
         tessellator.func_78375_b(0.0F, 0.0F, 1.0F);
         renderblocks.func_147734_d(block, 0.0D, 0.0D, 0.0D, renderblocks.func_147787_a(block, 3, meta));
         tessellator.func_78381_a();
         tessellator.func_78382_b();
         tessellator.func_78375_b(-1.0F, 0.0F, 0.0F);
         renderblocks.func_147798_e(block, 0.0D, 0.0D, 0.0D, renderblocks.func_147787_a(block, 4, meta));
         tessellator.func_78381_a();
         tessellator.func_78382_b();
         tessellator.func_78375_b(1.0F, 0.0F, 0.0F);
         renderblocks.func_147764_f(block, 0.0D, 0.0D, 0.0D, renderblocks.func_147787_a(block, 5, meta));
         tessellator.func_78381_a();
         GL11.glTranslatef(0.5F, 0.5F, 0.5F);
      }

      renderblocks.func_147782_a(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
   }

   private static void renderGrass(IBlockAccess world, int i, int j, int k, Block block, RenderBlocks renderblocks, boolean randomTranslation) {
      Tessellator tessellator = Tessellator.field_78398_a;
      tessellator.func_78380_c(block.func_149677_c(world, i, j, k));
      int meta = world.func_72805_g(i, j, k);
      int l = block.func_149720_d(world, i, j, k);
      float f = 1.0F;
      float f1 = (float)(l >> 16 & 255) / 255.0F;
      float f2 = (float)(l >> 8 & 255) / 255.0F;
      float f3 = (float)(l & 255) / 255.0F;
      if (EntityRenderer.field_78517_a) {
         float f4 = (f1 * 30.0F + f2 * 59.0F + f3 * 11.0F) / 100.0F;
         float f5 = (f1 * 30.0F + f2 * 70.0F) / 100.0F;
         float f6 = (f1 * 30.0F + f3 * 70.0F) / 100.0F;
         f1 = f4;
         f2 = f5;
         f3 = f6;
      }

      tessellator.func_78386_a(f * f1, f * f2, f * f3);
      double posX = (double)i;
      double posY = (double)j;
      double posZ = (double)k;
      if (randomTranslation) {
         long seed = (long)(i * 3129871) ^ (long)k * 116129781L ^ (long)j;
         seed = seed * seed * 42317861L + seed * 11L;
         posX += ((double)((float)(seed >> 16 & 15L) / 15.0F) - 0.5D) * 0.5D;
         posY += ((double)((float)(seed >> 20 & 15L) / 15.0F) - 1.0D) * 0.2D;
         posZ += ((double)((float)(seed >> 24 & 15L) / 15.0F) - 0.5D) * 0.5D;
      }

      renderblocks.func_147765_a(block.func_149691_a(2, meta), posX, posY, posZ, 1.0F);
      renderblocks.func_147771_a();
      if (block == LOTRMod.tallGrass && meta >= 0 && meta < LOTRBlockTallGrass.grassOverlay.length && LOTRBlockTallGrass.grassOverlay[meta]) {
         tessellator.func_78386_a(1.0F, 1.0F, 1.0F);
         renderblocks.func_147765_a(block.func_149691_a(-1, meta), posX, posY, posZ, 1.0F);
         renderblocks.func_147771_a();
      }

   }

   private void renderFallenLeaves(IBlockAccess world, int i, int j, int k, Block block, RenderBlocks renderblocks, int[] minMaxLeaves, int[] minMaxXSize, int[] minMaxZSize, float shade) {
      Tessellator tessellator = Tessellator.field_78398_a;
      tessellator.func_78380_c(block.func_149677_c(world, i, j, k));
      world.func_72805_g(i, j, k);
      int color = block.func_149720_d(world, i, j, k);
      float r = (float)(color >> 16 & 255) / 255.0F;
      float g = (float)(color >> 8 & 255) / 255.0F;
      float b = (float)(color & 255) / 255.0F;
      r *= shade;
      g *= shade;
      b *= shade;
      if (EntityRenderer.field_78517_a) {
         r = (r * 30.0F + g * 59.0F + b * 11.0F) / 100.0F;
         g = (r * 30.0F + g * 70.0F) / 100.0F;
         b = (r * 30.0F + b * 70.0F) / 100.0F;
      }

      tessellator.func_78386_a(r, g, b);
      long seed = (long)i * 237690503L ^ (long)k * 2689286L ^ (long)j;
      seed = seed * seed * 1732965593L + seed * 673L;
      blockRand.setSeed(seed);
      IIcon icon = block.func_149673_e(world, i, j, k, 0);
      int leaves = MathHelper.func_76136_a(blockRand, minMaxLeaves[0], minMaxLeaves[1]);

      for(int l = 0; l < leaves; ++l) {
         double posX = (double)((float)i + blockRand.nextFloat());
         double posZ = (double)((float)k + blockRand.nextFloat());
         double posY = (double)((float)j + 0.01F + (float)l / (float)leaves * 0.1F);
         float rotation = blockRand.nextFloat() * 3.1415927F * 2.0F;
         int xSize = MathHelper.func_76136_a(blockRand, minMaxXSize[0], minMaxXSize[1]);
         int zSize = MathHelper.func_76136_a(blockRand, minMaxZSize[0], minMaxZSize[1]);
         double xSizeD = (double)xSize / 16.0D;
         double zSizeD = (double)zSize / 16.0D;
         int intMinU = MathHelper.func_76136_a(blockRand, 0, 16 - xSize);
         int intMinV = MathHelper.func_76136_a(blockRand, 0, 16 - zSize);
         double minU = (double)icon.func_94214_a((double)intMinU);
         double minV = (double)icon.func_94207_b((double)intMinV);
         double maxU = (double)icon.func_94214_a((double)(intMinU + xSize));
         double maxV = (double)icon.func_94207_b((double)(intMinV + zSize));
         double x2 = xSizeD / 2.0D;
         double z2 = zSizeD / 2.0D;
         Vec3[] vecs = new Vec3[]{Vec3.func_72443_a(-x2, 0.0D, -z2), Vec3.func_72443_a(-x2, 0.0D, z2), Vec3.func_72443_a(x2, 0.0D, z2), Vec3.func_72443_a(x2, 0.0D, -z2)};

         for(int v = 0; v < vecs.length; ++v) {
            Vec3 vec = vecs[v];
            vec.func_72442_b(rotation);
            vec.field_72450_a += posX;
            vec.field_72448_b += posY;
            vec.field_72449_c += posZ;
         }

         tessellator.func_78374_a(vecs[0].field_72450_a, vecs[0].field_72448_b, vecs[0].field_72449_c, minU, minV);
         tessellator.func_78374_a(vecs[1].field_72450_a, vecs[1].field_72448_b, vecs[1].field_72449_c, minU, maxV);
         tessellator.func_78374_a(vecs[2].field_72450_a, vecs[2].field_72448_b, vecs[2].field_72449_c, maxU, maxV);
         tessellator.func_78374_a(vecs[3].field_72450_a, vecs[3].field_72448_b, vecs[3].field_72449_c, maxU, minV);
      }

   }

   private void renderCommandTable(IBlockAccess world, int i, int j, int k, Block block, RenderBlocks renderblocks) {
      int ao = getAO();
      setAO(0);
      renderblocks.field_147837_f = true;
      renderblocks.func_147775_a(block);
      renderblocks.func_147784_q(block, i, j, k);
      renderblocks.func_147757_a(Blocks.field_150344_f.func_149691_a(0, 0));
      renderblocks.func_147782_a(-0.5D, 1.0D, -0.5D, 0.5D, 1.0625D, 0.5D);
      renderblocks.func_147784_q(block, i, j, k);
      renderblocks.func_147782_a(-0.5D, 1.0D, 0.5D, 0.5D, 1.0625D, 1.5D);
      renderblocks.func_147784_q(block, i, j, k);
      renderblocks.func_147782_a(0.5D, 1.0D, -0.5D, 1.5D, 1.0625D, 0.5D);
      renderblocks.func_147784_q(block, i, j, k);
      renderblocks.func_147782_a(0.5D, 1.0D, 0.5D, 1.5D, 1.0625D, 1.5D);
      renderblocks.func_147784_q(block, i, j, k);
      renderblocks.func_147771_a();
      renderblocks.field_147837_f = false;
      setAO(ao);
   }

   private void renderInvCommandTable(Block block, RenderBlocks renderblocks) {
      renderblocks.field_147837_f = true;
      renderStandardInvBlock(renderblocks, block, 0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
      renderblocks.func_147757_a(Blocks.field_150344_f.func_149691_a(0, 0));
      renderStandardInvBlock(renderblocks, block, -0.5D, 1.0D, -0.5D, 1.5D, 1.0625D, 1.5D);
      renderblocks.func_147771_a();
      renderblocks.field_147837_f = false;
   }

   private void renderButterflyJar(IBlockAccess world, int i, int j, int k, Block block, RenderBlocks renderblocks) {
      int ao = getAO();
      setAO(0);
      renderblocks.field_147837_f = true;
      renderblocks.func_147782_a(0.1875D, 0.0D, 0.1875D, 0.8125D, 0.5625D, 0.8125D);
      renderblocks.func_147784_q(block, i, j, k);
      renderblocks.func_147782_a(0.1875D, 0.0D, 0.1875D, 0.8125D, 0.0625D, 0.8125D);
      renderblocks.func_147784_q(block, i, j, k);
      renderblocks.func_147782_a(0.25D, 0.5625D, 0.25D, 0.75D, 0.6875D, 0.75D);
      renderblocks.func_147784_q(block, i, j, k);
      renderblocks.func_147757_a(block.func_149691_a(-1, 0));
      renderblocks.func_147782_a(0.1875D, 0.6875D, 0.1875D, 0.8125D, 0.75D, 0.8125D);
      renderblocks.func_147784_q(block, i, j, k);
      renderblocks.func_147771_a();
      renderblocks.field_147837_f = false;
      setAO(ao);
   }

   private void renderInvButterflyJar(Block block, RenderBlocks renderblocks) {
      renderblocks.field_147837_f = true;
      renderStandardInvBlock(renderblocks, block, 0.1875D, 0.0D, 0.1875D, 0.8125D, 0.5625D, 0.8125D);
      renderStandardInvBlock(renderblocks, block, 0.1875D, 0.0D, 0.1875D, 0.8125D, 0.0625D, 0.8125D);
      renderStandardInvBlock(renderblocks, block, 0.25D, 0.5625D, 0.25D, 0.75D, 0.6875D, 0.75D);
      renderblocks.func_147757_a(block.func_149691_a(-1, 0));
      renderStandardInvBlock(renderblocks, block, 0.1875D, 0.6875D, 0.1875D, 0.8125D, 0.75D, 0.8125D);
      renderblocks.func_147771_a();
      renderblocks.field_147837_f = false;
   }

   private void renderReeds(IBlockAccess world, int i, int j, int k, Block block, RenderBlocks renderblocks) {
      block.func_149719_a(world, i, j, k);
      renderblocks.func_147775_a(block);
      Tessellator tessellator = Tessellator.field_78398_a;
      tessellator.func_78380_c(block.func_149677_c(world, i, j, k));
      int c = block.func_149720_d(world, i, j, k);
      float r = (float)(c >> 16 & 255) / 255.0F;
      float g = (float)(c >> 8 & 255) / 255.0F;
      float b = (float)(c & 255) / 255.0F;
      if (EntityRenderer.field_78517_a) {
         float r1 = (r * 30.0F + g * 59.0F + b * 11.0F) / 100.0F;
         float g1 = (r * 30.0F + g * 70.0F) / 100.0F;
         float b1 = (r * 30.0F + b * 70.0F) / 100.0F;
         r = r1;
         g = g1;
         b = b1;
      }

      tessellator.func_78386_a(r, g, b);
      double d = (double)i;
      double d1 = (double)j;
      double d2 = (double)k;
      IIcon iicon;
      if (world.func_147439_a(i, j - 1, k) == block) {
         iicon = renderblocks.func_147793_a(block, world, i, j, k, 0);
         renderblocks.func_147765_a(iicon, d, d1, d2, 1.0F);
      } else {
         iicon = renderblocks.func_147793_a(block, world, i, j, k, 0);
         renderblocks.func_147765_a(iicon, d, d1, d2, 1.0F);

         for(int j1 = j - 1; j1 > 0; --j1) {
            --d1;
            tessellator.func_78380_c(block.func_149677_c(world, i, j1, k));
            boolean lower = world.func_147439_a(i, j1 - 1, k).func_149662_c();
            if (lower) {
               iicon = renderblocks.func_147793_a(block, world, i, j, k, -2);
               renderblocks.func_147765_a(iicon, d, d1, d2, 1.0F);
               break;
            }

            iicon = renderblocks.func_147793_a(block, world, i, j, k, -1);
            renderblocks.func_147765_a(iicon, d, d1, d2, 1.0F);
         }
      }

   }

   private void renderBlockRandomRotated(IBlockAccess world, int i, int j, int k, Block block, RenderBlocks renderblocks, boolean rotateSides) {
      int[] sides = new int[6];

      for(int l = 0; l < sides.length; ++l) {
         int hash = i * 234890405 ^ k * 37383934 ^ j;
         hash += l * 285502;
         blockRand.setSeed((long)hash);
         blockRand.setSeed(blockRand.nextLong());
         sides[l] = blockRand.nextInt(4);
      }

      if (rotateSides) {
         renderblocks.field_147875_q = sides[0];
         renderblocks.field_147873_r = sides[1];
         renderblocks.field_147871_s = sides[2];
         renderblocks.field_147869_t = sides[3];
      }

      renderblocks.field_147867_u = sides[4];
      renderblocks.field_147865_v = sides[5];
      renderblocks.func_147784_q(block, i, j, k);
      if (rotateSides) {
         renderblocks.field_147875_q = 0;
         renderblocks.field_147873_r = 0;
         renderblocks.field_147871_s = 0;
         renderblocks.field_147869_t = 0;
      }

      renderblocks.field_147867_u = 0;
      renderblocks.field_147865_v = 0;
   }

   private void renderBeam(IBlockAccess world, int i, int j, int k, Block block, RenderBlocks renderblocks) {
      int meta = world.func_72805_g(i, j, k);
      int dir = meta & 12;
      if (dir == 0) {
         renderblocks.field_147875_q = 3;
         renderblocks.field_147869_t = 3;
      } else if (dir == 4) {
         renderblocks.field_147875_q = 1;
         renderblocks.field_147873_r = 2;
         renderblocks.field_147867_u = 2;
         renderblocks.field_147865_v = 1;
      } else if (dir == 8) {
         renderblocks.field_147871_s = 1;
         renderblocks.field_147869_t = 2;
      }

      renderblocks.func_147784_q(block, i, j, k);
      renderblocks.field_147871_s = 0;
      renderblocks.field_147875_q = 0;
      renderblocks.field_147873_r = 0;
      renderblocks.field_147869_t = 0;
      renderblocks.field_147867_u = 0;
      renderblocks.field_147865_v = 0;
   }

   private void renderVanillaCauldron(IBlockAccess world, int i, int j, int k, Block block, RenderBlocks renderblocks) {
      renderblocks.func_147784_q(block, i, j, k);
      Tessellator tessellator = Tessellator.field_78398_a;
      tessellator.func_78380_c(block.func_149677_c(world, i, j, k));
      int color = block.func_149720_d(world, i, j, k);
      float r = (float)(color >> 16 & 255) / 255.0F;
      float g = (float)(color >> 8 & 255) / 255.0F;
      float b = (float)(color & 255) / 255.0F;
      float w;
      if (EntityRenderer.field_78517_a) {
         float r1 = (r * 30.0F + g * 59.0F + b * 11.0F) / 100.0F;
         w = (r * 30.0F + g * 70.0F) / 100.0F;
         float b1 = (r * 30.0F + b * 70.0F) / 100.0F;
         r = r1;
         g = w;
         b = b1;
      }

      tessellator.func_78386_a(r, g, b);
      IIcon iconSide = block.func_149733_h(2);
      w = 0.125F;
      renderblocks.func_147764_f(block, (double)((float)i - 1.0F + w), (double)j, (double)k, iconSide);
      renderblocks.func_147798_e(block, (double)((float)i + 1.0F - w), (double)j, (double)k, iconSide);
      renderblocks.func_147734_d(block, (double)i, (double)j, (double)((float)k - 1.0F + w), iconSide);
      renderblocks.func_147761_c(block, (double)i, (double)j, (double)((float)k + 1.0F - w), iconSide);
      IIcon iconInner = BlockCauldron.func_150026_e("inner");
      renderblocks.func_147806_b(block, (double)i, (double)((float)j - 1.0F + 0.25F), (double)k, iconInner);
      renderblocks.func_147768_a(block, (double)i, (double)((float)j + 1.0F - 0.75F), (double)k, iconInner);
      int meta = world.func_72805_g(i, j, k);
      if (meta > 0) {
         color = Blocks.field_150355_j.func_149720_d(world, i, j, k);
         r = (float)(color >> 16 & 255) / 255.0F;
         g = (float)(color >> 8 & 255) / 255.0F;
         b = (float)(color & 255) / 255.0F;
         tessellator.func_78386_a(r, g, b);
         IIcon iconWater = BlockLiquid.func_149803_e("water_still");
         renderblocks.func_147806_b(block, (double)i, (double)((float)j - 1.0F + BlockCauldron.func_150025_c(meta)), (double)k, iconWater);
      }

   }

   private void renderGrapevine(IBlockAccess world, int i, int j, int k, Block block, RenderBlocks renderblocks) {
      block.func_149683_g();
      renderblocks.func_147775_a(block);
      renderblocks.func_147784_q(block, i, j, k);
      int meta = world.func_72805_g(i, j, k);
      renderblocks.func_147757_a(block.func_149691_a(-1, meta));
      block.func_149719_a(world, i, j, k);
      renderblocks.func_147775_a(block);
      double d = 0.001D;
      renderblocks.field_147855_j += d;
      renderblocks.field_147857_k -= d;
      renderblocks.func_147784_q(block, i, j, k);
      renderblocks.func_147771_a();
   }

   private void renderFlowerBlock(IBlockAccess world, int i, int j, int k, Block block, RenderBlocks renderblocks) {
      Tessellator tessellator = Tessellator.field_78398_a;
      tessellator.func_78380_c(block.func_149677_c(world, i, j, k));
      int color = block.func_149720_d(world, i, j, k);
      float r = (float)(color >> 16 & 255) / 255.0F;
      float g = (float)(color >> 8 & 255) / 255.0F;
      float b = (float)(color & 255) / 255.0F;
      if (EntityRenderer.field_78517_a) {
         float f3 = (r * 30.0F + g * 59.0F + b * 11.0F) / 100.0F;
         float f4 = (r * 30.0F + g * 70.0F) / 100.0F;
         float f5 = (r * 30.0F + b * 70.0F) / 100.0F;
         r = f3;
         g = f4;
         b = f5;
      }

      tessellator.func_78386_a(r, g, b);
      double d = (double)i;
      double d1 = (double)j;
      double d2 = (double)k;
      long seed = (long)(i * 3129871) ^ (long)k * 116129781L ^ (long)j;
      seed = seed * seed * 42317861L + seed * 11L;
      d += ((double)((float)(seed >> 16 & 15L) / 15.0F) - 0.5D) * 0.3D;
      d2 += ((double)((float)(seed >> 24 & 15L) / 15.0F) - 0.5D) * 0.3D;
      IIcon iicon = renderblocks.func_147787_a(block, 0, world.func_72805_g(i, j, k));
      renderblocks.func_147765_a(iicon, d, d1, d2, 1.0F);
   }

   private void renderDoublePlant(IBlockAccess world, int i, int j, int k, BlockDoublePlant block, RenderBlocks renderblocks) {
      Tessellator tessellator = Tessellator.field_78398_a;
      tessellator.func_78380_c(block.func_149677_c(world, i, j, k));
      int color = block.func_149720_d(world, i, j, k);
      float r = (float)(color >> 16 & 255) / 255.0F;
      float g = (float)(color >> 8 & 255) / 255.0F;
      float b = (float)(color & 255) / 255.0F;
      if (EntityRenderer.field_78517_a) {
         float f3 = (r * 30.0F + g * 59.0F + b * 11.0F) / 100.0F;
         float f4 = (r * 30.0F + g * 70.0F) / 100.0F;
         float f5 = (r * 30.0F + b * 70.0F) / 100.0F;
         r = f3;
         g = f4;
         b = f5;
      }

      tessellator.func_78386_a(r, g, b);
      double d = (double)i;
      double d1 = (double)j;
      double d2 = (double)k;
      long seed = (long)(i * 3129871) ^ (long)k * 116129781L;
      seed = seed * seed * 42317861L + seed * 11L;
      d += ((double)((float)(seed >> 16 & 15L) / 15.0F) - 0.5D) * 0.3D;
      d2 += ((double)((float)(seed >> 24 & 15L) / 15.0F) - 0.5D) * 0.3D;
      int meta = world.func_72805_g(i, j, k);
      boolean isTop = BlockDoublePlant.func_149887_c(meta);
      int plantType;
      if (isTop) {
         if (world.func_147439_a(i, j - 1, k) != block) {
            return;
         }

         plantType = BlockDoublePlant.func_149890_d(world.func_72805_g(i, j - 1, k));
      } else {
         plantType = BlockDoublePlant.func_149890_d(meta);
      }

      IIcon icon = block.func_149888_a(isTop, plantType);
      renderblocks.func_147765_a(icon, d, d1, d2, 1.0F);
   }

   private void renderBirdCage(IBlockAccess world, int i, int j, int k, Block block, RenderBlocks renderblocks) {
      int ao = getAO();
      setAO(0);
      renderblocks.field_147837_f = true;
      int meta = world.func_72805_g(i, j, k);
      float d = 0.001F;
      if (!LOTRBlockBirdCage.isSameBirdCage(world, i, j, k, i - 1, j, k)) {
         renderblocks.func_147782_a(0.0D, 0.0625D, 0.0D, (double)(0.0F + d), 1.0D, 1.0D);
         renderblocks.func_147784_q(block, i, j, k);
      }

      if (!LOTRBlockBirdCage.isSameBirdCage(world, i, j, k, i + 1, j, k)) {
         renderblocks.func_147782_a((double)(1.0F - d), 0.0625D, 0.0D, 1.0D, 1.0D, 1.0D);
         renderblocks.func_147784_q(block, i, j, k);
      }

      if (!LOTRBlockBirdCage.isSameBirdCage(world, i, j, k, i, j, k - 1)) {
         renderblocks.func_147782_a(0.0D, 0.0625D, 0.0D, 1.0D, 1.0D, (double)(0.0F + d));
         renderblocks.func_147784_q(block, i, j, k);
      }

      if (!LOTRBlockBirdCage.isSameBirdCage(world, i, j, k, i, j, k + 1)) {
         renderblocks.func_147782_a(0.0D, 0.0625D, (double)(1.0F - d), 1.0D, 1.0D, 1.0D);
         renderblocks.func_147784_q(block, i, j, k);
      }

      renderblocks.func_147782_a(0.0D, (double)(1.0F - d), 0.0D, 1.0D, 1.0D, 1.0D);
      renderblocks.func_147784_q(block, i, j, k);
      renderblocks.func_147757_a(block.func_149691_a(-1, meta));
      renderblocks.func_147782_a(0.0D, 0.0D, 0.0D, 1.0D, 0.0625D, 1.0D);
      renderblocks.func_147784_q(block, i, j, k);
      Block above = world.func_147439_a(i, j + 1, k);
      above.func_149719_a(world, i, j + 1, k);
      if (!above.func_149688_o().func_76220_a() || above.func_149665_z() > 0.0D || !above.func_149688_o().func_76218_k() && above.func_149645_b() == 0) {
         renderblocks.func_147782_a(0.375D, 1.0D, 0.375D, 0.625D, 1.0625D, 0.625D);
         renderblocks.func_147784_q(block, i, j, k);
         renderblocks.func_147782_a(0.46875D, 1.0625D, 0.46875D, 0.53125D, 1.1875D, 0.53125D);
         renderblocks.func_147784_q(block, i, j, k);
      }

      renderblocks.func_147771_a();
      renderblocks.field_147837_f = false;
      setAO(ao);
   }

   private void renderInvBirdCage(Block block, RenderBlocks renderblocks, int meta) {
      renderblocks.field_147837_f = true;
      float d = 0.001F;
      renderStandardInvBlock(renderblocks, block, 0.0D, 0.0625D, 0.0D, (double)(0.0F + d), 1.0D, 1.0D, meta);
      renderStandardInvBlock(renderblocks, block, (double)(1.0F - d), 0.0625D, 0.0D, 1.0D, 1.0D, 1.0D, meta);
      renderStandardInvBlock(renderblocks, block, 0.0D, 0.0625D, 0.0D, 1.0D, 1.0D, (double)(0.0F + d), meta);
      renderStandardInvBlock(renderblocks, block, 0.0D, 0.0625D, (double)(1.0F - d), 1.0D, 1.0D, 1.0D, meta);
      renderStandardInvBlock(renderblocks, block, 0.0D, (double)(1.0F - d), 0.0D, 1.0D, 1.0D, 1.0D, meta);
      renderblocks.func_147757_a(block.func_149691_a(-1, meta));
      renderStandardInvBlock(renderblocks, block, 0.0D, 0.0D, 0.0D, 1.0D, 0.0625D, 1.0D, meta);
      renderStandardInvBlock(renderblocks, block, 0.375D, 1.0D, 0.375D, 0.625D, 1.0625D, 0.625D, meta);
      renderStandardInvBlock(renderblocks, block, 0.46875D, 1.0625D, 0.46875D, 0.53125D, 1.1875D, 0.53125D, meta);
      renderblocks.func_147771_a();
      renderblocks.field_147837_f = false;
   }

   private void renderRhunFireJar(IBlockAccess world, int i, int j, int k, Block block, RenderBlocks renderblocks) {
      int ao = getAO();
      setAO(0);
      renderblocks.field_147837_f = true;
      LOTRBlockRhunFireJar.renderingStage = 1;
      renderblocks.func_147782_a(0.125D, 0.0D, 0.125D, 0.875D, 0.5D, 0.875D);
      renderblocks.func_147784_q(block, i, j, k);
      LOTRBlockRhunFireJar.renderingStage = 2;
      renderblocks.func_147782_a(0.3125D, 0.5D, 0.3125D, 0.6875D, 0.6875D, 0.6875D);
      renderblocks.func_147784_q(block, i, j, k);
      LOTRBlockRhunFireJar.renderingStage = 3;
      renderblocks.func_147782_a(0.25D, 0.6875D, 0.25D, 0.75D, 0.8125D, 0.75D);
      renderblocks.func_147784_q(block, i, j, k);
      LOTRBlockRhunFireJar.renderingStage = 4;
      renderblocks.func_147782_a(0.3125D, 0.8125D, 0.3125D, 0.6875D, 0.875D, 0.6875D);
      renderblocks.func_147784_q(block, i, j, k);
      LOTRBlockRhunFireJar.renderingStage = 5;
      renderblocks.func_147782_a(0.375D, 0.875D, 0.5D, 0.625D, 1.0D, 0.5D);
      renderblocks.func_147784_q(block, i, j, k);
      renderblocks.func_147782_a(0.5D, 0.875D, 0.375D, 0.5D, 1.0D, 0.625D);
      renderblocks.func_147784_q(block, i, j, k);
      LOTRBlockRhunFireJar.renderingStage = 6;
      renderblocks.func_147782_a(0.0D, 0.0D, 0.5D, 1.0D, 1.0D, 0.5D);
      renderblocks.func_147784_q(block, i, j, k);
      renderblocks.func_147782_a(0.5D, 0.0D, 0.0D, 0.5D, 1.0D, 1.0D);
      renderblocks.func_147784_q(block, i, j, k);
      LOTRBlockRhunFireJar.renderingStage = 0;
      renderblocks.field_147837_f = false;
      setAO(ao);
   }

   private void renderInvRhunFireJar(Block block, RenderBlocks renderblocks, int meta) {
      renderblocks.field_147837_f = true;
      LOTRBlockRhunFireJar.renderingStage = 1;
      renderStandardInvBlock(renderblocks, block, 0.125D, 0.0D, 0.125D, 0.875D, 0.5D, 0.875D);
      LOTRBlockRhunFireJar.renderingStage = 2;
      renderStandardInvBlock(renderblocks, block, 0.3125D, 0.5D, 0.3125D, 0.6875D, 0.6875D, 0.6875D);
      LOTRBlockRhunFireJar.renderingStage = 3;
      renderStandardInvBlock(renderblocks, block, 0.25D, 0.6875D, 0.25D, 0.75D, 0.8125D, 0.75D);
      LOTRBlockRhunFireJar.renderingStage = 4;
      renderStandardInvBlock(renderblocks, block, 0.3125D, 0.8125D, 0.3125D, 0.6875D, 0.875D, 0.6875D);
      LOTRBlockRhunFireJar.renderingStage = 5;
      renderStandardInvBlock(renderblocks, block, 0.375D, 0.875D, 0.5D, 0.625D, 1.0D, 0.5D);
      renderStandardInvBlock(renderblocks, block, 0.5D, 0.875D, 0.375D, 0.5D, 1.0D, 0.625D);
      LOTRBlockRhunFireJar.renderingStage = 6;
      renderStandardInvBlock(renderblocks, block, 0.0D, 0.0D, 0.5D, 1.0D, 1.0D, 0.5D);
      renderStandardInvBlock(renderblocks, block, 0.5D, 0.0D, 0.0D, 0.5D, 1.0D, 1.0D);
      LOTRBlockRhunFireJar.renderingStage = 0;
      renderblocks.field_147837_f = false;
   }

   private void renderCoral(IBlockAccess world, int i, int j, int k, Block block, RenderBlocks renderblocks) {
      renderblocks.func_147784_q(block, i, j, k);
      Tessellator tessellator = Tessellator.field_78398_a;
      tessellator.func_78380_c(block.func_149677_c(world, i, j + 1, k));
      tessellator.func_78386_a(1.0F, 1.0F, 1.0F);
      IIcon icon = ((LOTRBlockCoralReef)block).getRandomPlantIcon(i, j, k);
      renderblocks.func_147765_a(icon, (double)i, (double)(j + 1), (double)k, 1.0F);
   }

   private boolean renderDoor(IBlockAccess world, int i, int j, int k, Block block, RenderBlocks renderblocks) {
      Tessellator tessellator = Tessellator.field_78398_a;
      int meta = world.func_72805_g(i, j, k);
      boolean topDoor = (meta & 8) != 0;
      if (topDoor) {
         if (world.func_147439_a(i, j - 1, k) != block) {
            return false;
         }
      } else if (world.func_147439_a(i, j + 1, k) != block) {
         return false;
      }

      boolean flag = false;
      float f = 0.5F;
      float f1 = 1.0F;
      float f2 = 0.8F;
      float f3 = 0.6F;
      int light = block.func_149677_c(world, i, j, k);
      if (!topDoor || world.func_147439_a(i, j - 1, k) != block) {
         tessellator.func_78380_c(renderblocks.field_147855_j > 0.0D ? light : block.func_149677_c(world, i, j - 1, k));
         tessellator.func_78386_a(f, f, f);
         renderblocks.func_147768_a(block, (double)i, (double)j, (double)k, renderblocks.func_147793_a(block, world, i, j, k, 0));
         flag = true;
      }

      if (topDoor || world.func_147439_a(i, j + 1, k) != block) {
         tessellator.func_78380_c(renderblocks.field_147857_k < 1.0D ? light : block.func_149677_c(world, i, j + 1, k));
         tessellator.func_78386_a(f1, f1, f1);
         renderblocks.func_147806_b(block, (double)i, (double)j, (double)k, renderblocks.func_147793_a(block, world, i, j, k, 1));
         flag = true;
      }

      tessellator.func_78380_c(renderblocks.field_147851_l > 0.0D ? light : block.func_149677_c(world, i, j, k - 1));
      tessellator.func_78386_a(f2, f2, f2);
      IIcon iicon = renderblocks.func_147793_a(block, world, i, j, k, 2);
      renderblocks.func_147761_c(block, (double)i, (double)j, (double)k, iicon);
      flag = true;
      renderblocks.field_147842_e = false;
      tessellator.func_78380_c(renderblocks.field_147853_m < 1.0D ? light : block.func_149677_c(world, i, j, k + 1));
      tessellator.func_78386_a(f2, f2, f2);
      iicon = renderblocks.func_147793_a(block, world, i, j, k, 3);
      renderblocks.func_147734_d(block, (double)i, (double)j, (double)k, iicon);
      flag = true;
      renderblocks.field_147842_e = false;
      tessellator.func_78380_c(renderblocks.field_147859_h > 0.0D ? light : block.func_149677_c(world, i - 1, j, k));
      tessellator.func_78386_a(f3, f3, f3);
      iicon = renderblocks.func_147793_a(block, world, i, j, k, 4);
      renderblocks.func_147798_e(block, (double)i, (double)j, (double)k, iicon);
      flag = true;
      renderblocks.field_147842_e = false;
      tessellator.func_78380_c(renderblocks.field_147861_i < 1.0D ? light : block.func_149677_c(world, i + 1, j, k));
      tessellator.func_78386_a(f3, f3, f3);
      iicon = renderblocks.func_147793_a(block, world, i, j, k, 5);
      renderblocks.func_147764_f(block, (double)i, (double)j, (double)k, iicon);
      flag = true;
      renderblocks.field_147842_e = false;
      return flag;
   }

   private void renderRope(IBlockAccess world, int i, int j, int k, Block block, RenderBlocks renderblocks) {
      double ropeWidth = 0.125D;
      double ropeMinX = 0.5D - ropeWidth / 2.0D;
      double ropeMaxX = 1.0D - ropeMinX;
      double ropeOffset = 0.0625D;
      int meta = world.func_72805_g(i, j, k);
      boolean top = world.func_147439_a(i, j + 1, k) != block || world.func_72805_g(i, j + 1, k) != meta;
      double knotHeight = 0.25D;
      double knotBottom = 1.0D - knotHeight;
      double knotWidth = 0.25D;
      double knotMinX = 0.5D - knotWidth / 2.0D;
      double knotMaxX = 1.0D - knotMinX;
      double ropeTop = top ? 1.0D - knotHeight : 1.0D;
      if (meta == 5) {
         renderblocks.func_147782_a(0.0D, 0.0D, ropeMinX, ropeOffset, ropeTop, ropeMaxX);
         renderblocks.func_147784_q(block, i, j, k);
         if (top) {
            renderblocks.func_147753_b(true);
            renderblocks.func_147782_a(0.0D, knotBottom, knotMinX, knotWidth, 1.0D, knotMaxX);
            renderblocks.func_147784_q(block, i, j, k);
            renderblocks.func_147753_b(false);
         }
      }

      if (meta == 4) {
         renderblocks.func_147782_a(1.0D - ropeOffset, 0.0D, ropeMinX, 1.0D, ropeTop, ropeMaxX);
         renderblocks.func_147784_q(block, i, j, k);
         if (top) {
            renderblocks.func_147753_b(true);
            renderblocks.func_147782_a(1.0D - knotWidth, knotBottom, knotMinX, 1.0D, 1.0D, knotMaxX);
            renderblocks.func_147784_q(block, i, j, k);
            renderblocks.func_147753_b(false);
         }
      }

      if (meta == 3) {
         renderblocks.func_147782_a(ropeMinX, 0.0D, 0.0D, ropeMaxX, ropeTop, ropeOffset);
         renderblocks.func_147784_q(block, i, j, k);
         if (top) {
            renderblocks.func_147753_b(true);
            renderblocks.func_147782_a(knotMinX, knotBottom, 0.0D, knotMaxX, 1.0D, knotWidth);
            renderblocks.func_147784_q(block, i, j, k);
            renderblocks.func_147753_b(false);
         }
      }

      if (meta == 2) {
         renderblocks.func_147782_a(ropeMinX, 0.0D, 1.0D - ropeOffset, ropeMaxX, ropeTop, 1.0D);
         renderblocks.func_147784_q(block, i, j, k);
         if (top) {
            renderblocks.func_147753_b(true);
            renderblocks.func_147782_a(knotMinX, knotBottom, 1.0D - knotWidth, knotMaxX, 1.0D, 1.0D);
            renderblocks.func_147784_q(block, i, j, k);
            renderblocks.func_147753_b(false);
         }
      }

   }

   private void renderTrapdoor(IBlockAccess world, int i, int j, int k, Block block, RenderBlocks renderblocks) {
      int meta = world.func_72805_g(i, j, k);
      if (!BlockTrapDoor.func_150118_d(meta)) {
         int dir = meta & 3;
         if (dir == 0) {
            renderblocks.field_147867_u = 3;
            renderblocks.field_147865_v = 3;
         } else if (dir == 1) {
            renderblocks.field_147867_u = 0;
            renderblocks.field_147865_v = 0;
         } else if (dir == 2) {
            renderblocks.field_147867_u = 1;
            renderblocks.field_147865_v = 2;
         } else if (dir == 3) {
            renderblocks.field_147867_u = 2;
            renderblocks.field_147865_v = 1;
         }
      }

      renderblocks.func_147784_q(block, i, j, k);
      renderblocks.field_147867_u = renderblocks.field_147865_v = 0;
   }

   private static void renderStandardInvBlock(RenderBlocks renderblocks, Block block, int meta) {
      block.func_149683_g();
      renderblocks.func_147775_a(block);
      double d = renderblocks.field_147859_h;
      double d1 = renderblocks.field_147855_j;
      double d2 = renderblocks.field_147851_l;
      double d3 = renderblocks.field_147861_i;
      double d4 = renderblocks.field_147857_k;
      double d5 = renderblocks.field_147853_m;
      renderStandardInvBlock(renderblocks, block, d, d1, d2, d3, d4, d5, meta);
   }

   public static void renderStandardInvBlock(RenderBlocks renderblocks, Block block, double d, double d1, double d2, double d3, double d4, double d5) {
      renderStandardInvBlock(renderblocks, block, d, d1, d2, d3, d4, d5, 0);
   }

   public static void renderStandardInvBlock(RenderBlocks renderblocks, Block block, double d, double d1, double d2, double d3, double d4, double d5, int metadata) {
      Tessellator tessellator = Tessellator.field_78398_a;
      renderblocks.func_147782_a(d, d1, d2, d3, d4, d5);
      GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
      tessellator.func_78382_b();
      tessellator.func_78375_b(0.0F, -1.0F, 0.0F);
      renderblocks.func_147768_a(block, 0.0D, 0.0D, 0.0D, renderblocks.func_147787_a(block, 0, metadata));
      tessellator.func_78381_a();
      tessellator.func_78382_b();
      tessellator.func_78375_b(0.0F, 1.0F, 0.0F);
      renderblocks.func_147806_b(block, 0.0D, 0.0D, 0.0D, renderblocks.func_147787_a(block, 1, metadata));
      tessellator.func_78381_a();
      tessellator.func_78382_b();
      tessellator.func_78375_b(0.0F, 0.0F, -1.0F);
      renderblocks.func_147761_c(block, 0.0D, 0.0D, 0.0D, renderblocks.func_147787_a(block, 2, metadata));
      tessellator.func_78381_a();
      tessellator.func_78382_b();
      tessellator.func_78375_b(0.0F, 0.0F, 1.0F);
      renderblocks.func_147734_d(block, 0.0D, 0.0D, 0.0D, renderblocks.func_147787_a(block, 3, metadata));
      tessellator.func_78381_a();
      tessellator.func_78382_b();
      tessellator.func_78375_b(-1.0F, 0.0F, 0.0F);
      renderblocks.func_147798_e(block, 0.0D, 0.0D, 0.0D, renderblocks.func_147787_a(block, 4, metadata));
      tessellator.func_78381_a();
      tessellator.func_78382_b();
      tessellator.func_78375_b(1.0F, 0.0F, 0.0F);
      renderblocks.func_147764_f(block, 0.0D, 0.0D, 0.0D, renderblocks.func_147787_a(block, 5, metadata));
      tessellator.func_78381_a();
      GL11.glTranslatef(0.5F, 0.5F, 0.5F);
   }

   private static int getAO() {
      return Minecraft.func_71410_x().field_71474_y.field_74348_k;
   }

   private static void setAO(int i) {
      Minecraft.func_71410_x().field_71474_y.field_74348_k = i;
   }
}
