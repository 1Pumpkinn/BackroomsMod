package net.tyrone.backroomsmod.world.structure;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;

import java.util.Optional;

public class BackroomsStructure extends Structure {
    public static final Codec<BackroomsStructure> CODEC = RecordCodecBuilder.<BackroomsStructure>mapCodec(instance ->
            instance.group(
                    settingsCodec(instance)
            ).apply(instance, BackroomsStructure::new)).codec();

    public static final StructureType<BackroomsStructure> TYPE = () -> CODEC;

    public BackroomsStructure(StructureSettings settings) {
        super(settings);
    }

    @Override
    protected Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        ChunkPos chunkPos = context.chunkPos();
        BlockPos blockPos = new BlockPos(chunkPos.getMinBlockX(), 0, chunkPos.getMinBlockZ());

        return Optional.of(new GenerationStub(blockPos, (structurePiecesBuilder) -> {
            generatePieces(structurePiecesBuilder, context);
        }));
    }

    private void generatePieces(StructurePiecesBuilder builder, GenerationContext context) {
        ChunkPos chunkPos = context.chunkPos();
        WorldgenRandom random = context.random();

        BlockPos centerPos = new BlockPos(
                chunkPos.getMinBlockX() + 8,
                64, // Fixed Y level for consistent generation
                chunkPos.getMinBlockZ() + 8
        );

        // Generate the main backrooms structure using jigsaw
        BackroomsStructurePiece piece = new BackroomsStructurePiece(
                centerPos,
                new ResourceLocation(BackroomsMod.MODID, "backrooms/hall_start"),
                random
        );

        builder.addPiece(piece);

        // Generate additional connected pieces
        generateConnectedPieces(builder, centerPos, random, 3); // 3 levels of recursion
    }

    private void generateConnectedPieces(StructurePiecesBuilder builder, BlockPos center, WorldgenRandom random, int depth) {
        if (depth <= 0) return;

        // Generate pieces in the four cardinal directions
        BlockPos[] directions = {
                center.offset(16, 0, 0),   // East
                center.offset(-16, 0, 0),  // West
                center.offset(0, 0, 16),   // South
                center.offset(0, 0, -16)   // North
        };

        for (BlockPos pos : directions) {
            if (random.nextFloat() < 0.7f) { // 70% chance to generate
                ResourceLocation template = getRandomTemplate(random);
                BackroomsStructurePiece piece = new BackroomsStructurePiece(pos, template, random);
                builder.addPiece(piece);

                // Recursively generate more pieces
                generateConnectedPieces(builder, pos, random, depth - 1);
            }
        }
    }

    private ResourceLocation getRandomTemplate(WorldgenRandom random) {
        String[] templates = {
                "backrooms/hall_straight",
                "backrooms/hall_corner",
                "backrooms/hall_t_junction",
                "backrooms/hall_cross",
                "backrooms/room_small",
                "backrooms/room_large",
                "backrooms/room_pillar"
        };

        return new ResourceLocation(BackroomsMod.MODID, templates[random.nextInt(templates.length)]);
    }

    @Override
    public StructureType<?> type() {
        return TYPE;
    }

    @Override
    public GenerationStep.Decoration step() {
        return GenerationStep.Decoration.SURFACE_STRUCTURES;
    }
}