package net.tyrone.backroomsmod.init;

import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.tyrone.backroomsmod.BackroomsMod;
import net.tyrone.backroomsmod.world.structure.BackroomsStructure;

public class BackroomsStructures {
    public static final DeferredRegister<StructureType<?>> STRUCTURE_TYPES =
            DeferredRegister.create(ForgeRegistries.STRUCTURE_TYPES, BackroomsMod.MODID);

    public static final DeferredRegister<Structure> STRUCTURES =
            DeferredRegister.create(ForgeRegistries.STRUCTURES, BackroomsMod.MODID);

    public static final DeferredRegister<StructurePieceType> STRUCTURE_PIECES =
            DeferredRegister.create(ForgeRegistries.STRUCTURE_PIECE_TYPES, BackroomsMod.MODID);

    public static final RegistryObject<StructureType<BackroomsStructure>> BACKROOMS_STRUCTURE_TYPE =
            STRUCTURE_TYPES.register("backrooms_structure", () -> BackroomsStructure.TYPE);

    public static final RegistryObject<StructurePieceType> BACKROOMS_PIECE =
            STRUCTURE_PIECES.register("backrooms_piece", () -> BackroomsStructurePiece.TYPE);
}