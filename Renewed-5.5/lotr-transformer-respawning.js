
/*
 * Coremod transformer to support the mod's custom respawning mechanics.
 * For the interested reader, these are the functions of this transformer:
 *
 * - Modifying respawn mechanics so that players who die in the Middle-earth dimension without a bed respawn there instead of in the Overworld.
 * - Modifying the respawn position in the Middle-earth dimension depending on distance from bed / world spawnpoint.
 *
 */

function initializeCoreMod() {

	Opcodes = Java.type("org.objectweb.asm.Opcodes");
	InsnList = Java.type("org.objectweb.asm.tree.InsnList");
	MethodNode = Java.type("org.objectweb.asm.tree.MethodNode");
	FieldNode = Java.type("org.objectweb.asm.tree.FieldNode");
	AbstractInsnNode = Java.type("org.objectweb.asm.tree.AbstractInsnNode");
	InsnNode = Java.type("org.objectweb.asm.tree.InsnNode");
	VarInsnNode = Java.type("org.objectweb.asm.tree.VarInsnNode");
	FieldInsnNode = Java.type("org.objectweb.asm.tree.FieldInsnNode");
	MethodInsnNode = Java.type("org.objectweb.asm.tree.MethodInsnNode");
	
	ASMAPI = Java.type("net.minecraftforge.coremod.api.ASMAPI");
	
	// Opcodes
	ALOAD = Opcodes.ALOAD;
	ASTORE = Opcodes.ASTORE;
	INVOKESTATIC = Opcodes.INVOKESTATIC;
	
	return {
		"PlayerList#func_232644_a_#getCheckedBedRespawnPosition": {
			"target": {
				"type": "METHOD",
				"class": "net.minecraft.server.management.PlayerList",
				"methodName": "func_232644_a_",
				"methodDesc": "(Lnet/minecraft/entity/player/ServerPlayerEntity;Z)Lnet/minecraft/entity/player/ServerPlayerEntity;"
			},
			"transformer": function(methodNode) {
				var instructions = methodNode.instructions;
	
				var found_getBedRespawnPos = ASMAPI.findFirstMethodCall(methodNode, ASMAPI.MethodType.VIRTUAL, "net/minecraft/entity/player/ServerPlayerEntity", "func_241140_K_", "()Lnet/minecraft/util/math/BlockPos;");
				// node after must be ASTORE 3, assigning the bed pos from the above method call
				var node_storeBedRespawnPos = found_getBedRespawnPos.getNext();
				if (!(node_storeBedRespawnPos.getOpcode() === ASTORE && node_storeBedRespawnPos.var === 3)) {
					throw new Error("Transformer broken - expected ASTORE 3 one node after, but was " + node_storeBedRespawnPos);
				}
				
				// redirect the bedRespawnPos through a checking method
				var newIns = new InsnList();
				newIns.add(new VarInsnNode(ALOAD, 1)); // player
				newIns.add(new MethodInsnNode(INVOKESTATIC, "lotr/common/coremod/InjectMethods$Respawning", "getCheckedBedRespawnPosition", "(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/player/ServerPlayerEntity;)Lnet/minecraft/util/math/BlockPos;", false));
			
				instructions.insert(found_getBedRespawnPos, newIns);
				
				return methodNode;
			}
		},
		"PlayerList#func_232644_a_#getDefaultRespawnWorld": {
			"target": {
				"type": "METHOD",
				"class": "net.minecraft.server.management.PlayerList",
				"methodName": "func_232644_a_",
				"methodDesc": "(Lnet/minecraft/entity/player/ServerPlayerEntity;Z)Lnet/minecraft/entity/player/ServerPlayerEntity;"
			},
			"transformer": function(methodNode) {
				var instructions = methodNode.instructions;

				var found_getDefaultRespawnWorld = ASMAPI.findFirstMethodCall(methodNode, ASMAPI.MethodType.VIRTUAL, "net/minecraft/server/MinecraftServer", "func_241755_D_", "()Lnet/minecraft/world/server/ServerWorld;");
				
				var newIns = new InsnList();
				newIns.add(new VarInsnNode(ALOAD, 1)); // player
				newIns.add(new MethodInsnNode(INVOKESTATIC, "lotr/common/coremod/InjectMethods$Respawning", "getDefaultRespawnWorld", "(Lnet/minecraft/world/server/ServerWorld;Lnet/minecraft/entity/player/ServerPlayerEntity;)Lnet/minecraft/world/server/ServerWorld;", false));
			
				instructions.insert(found_getDefaultRespawnWorld, newIns);
				
				return methodNode;
			}
		},
		"PlayerList#func_232644_a_#relocatePlayerIfNeeded": {
			"target": {
				"type": "METHOD",
				"class": "net.minecraft.server.management.PlayerList",
				"methodName": "func_232644_a_",
				"methodDesc": "(Lnet/minecraft/entity/player/ServerPlayerEntity;Z)Lnet/minecraft/entity/player/ServerPlayerEntity;"
			},
			"transformer": function(methodNode) {
				var instructions = methodNode.instructions;

				var found_beginWhileLoop = ASMAPI.findFirstMethodCall(methodNode, ASMAPI.MethodType.VIRTUAL, "net/minecraft/world/server/ServerWorld", "hasNoCollisions", "(Lnet/minecraft/entity/Entity;)Z");
				if (found_beginWhileLoop === null) {
					// then try the srg name
					found_beginWhileLoop = ASMAPI.findFirstMethodCall(methodNode, ASMAPI.MethodType.VIRTUAL, "net/minecraft/world/server/ServerWorld", "func_226669_j_", "(Lnet/minecraft/entity/Entity;)Z");
				}
				// we don't need to change this node, it's just a useful anchor point at just after the right position in the method to insert the new instructions.
				
				// sanity check - node 2 steps before should be this
				var node2BeforeFound = found_beginWhileLoop.getPrevious().getPrevious();
				if (!(node2BeforeFound.getOpcode() === ALOAD && node2BeforeFound.var === 8)) {
					throw new Error("Transformer broken - expected ALOAD 8 two nodes before, but was " + node2BeforeFound);
				}
				
				var newIns = new InsnList();
				newIns.add(new VarInsnNode(ALOAD, 7)); // optional
				newIns.add(new VarInsnNode(ALOAD, 10)); // newPlayer
				newIns.add(new VarInsnNode(ALOAD, 1)); // deadPlayer
				newIns.add(new MethodInsnNode(INVOKESTATIC, "lotr/common/coremod/InjectMethods$Respawning", "relocatePlayerIfNeeded", "(Ljava/util/Optional;Lnet/minecraft/entity/player/ServerPlayerEntity;Lnet/minecraft/entity/player/ServerPlayerEntity;)V", false));
			
				instructions.insert(node2BeforeFound, newIns);
				
				return methodNode;
			}
		}
	};
}
