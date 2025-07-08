package net.tyrone.backroomsmod.world.structure;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import javax.annotation.Nullable;

public class BackroomsStructureProcessor extends StructureProcessor {
    public static final Codec<BackroomsStructureProcessor> CODEC = Codec.unit(BackroomsStructureProcessor::new);

    @Nullable
    @Override
    public StructureTemplate.StructureBlockInfo processBlock(LevelReader level, BlockPos jigsawPos, BlockPos relativePos, StructureTemplate.StructureBlockInfo blockInfo, StructureTemplate.StructureBlockInfo relativeBlockInfo, StructurePlaceSettings settings) {
        RandomSource random = settings.getRandom(relativePos);

        // Process yellow wool as backrooms walls
        if (blockInfo.state().getBlock() == Blocks.YELLOW_WOOL) {
            if (random.nextFloat() < 0.1f) {
                // 10% chance for moldy wall
                return new StructureTemplate.StructureBlockInfo(
                        blockInfo.pos(),
                        BackroomsBlocks.MOLDY_WALL.get().defaultBlockState(),
                        blockInfo.nbt()
                );
            } else {
                return new StructureTemplate.StructureBlockInfo(
                        blockInfo.pos(),
                        BackroomsBlocks.BACKROOMS_WALL.get().defaultBlockState(),
                        blockInfo.nbt()
                );
            }
        }

        // Process yellow concrete as backrooms floor
        if (blockInfo.state().getBlock() == Blocks.YELLOW_CONCRETE) {
            return new StructureTemplate.StructureBlockInfo(
                    blockInfo.pos(),
                    BackroomsBlocks.BACKROOMS_FLOOR.get().defaultBlockState(),
                    blockInfo.nbt()
            );
        }

        // Process light gray concrete as backrooms ceiling
        if (blockInfo.state().getBlock() == Blocks.LIGHT_GRAY_CONCRETE) {
            return new StructureTemplate.StructureBlockInfo(
                    blockInfo.pos(),
                    BackroomsBlocks.BACKROOMS_CEILING.get().defaultBlockState(),
                    blockInfo.nbt()
            );
        }

        // Process glowstone as buzzing lights
        if (blockInfo.state().getBlock() == Blocks.GLOWSTONE) {
            if (random.nextFloat() < 0.05f) {
                // 5% chance for broken light (air)
                return new StructureTemplate.StructureBlockInfo(
                        blockInfo.pos(),
                        Blocks.AIR.defaultBlockState(),
                        blockInfo.nbt()
                );
            } else {
                return new StructureTemplate.StructureBlockInfo(
                        blockInfo.pos(),
                        BackroomsBlocks.BUZZING_LIGHT.get().defaultBlockState(),
                        blockInfo.nbt()
                );
            }
        }

        // Process structure voids as connection points
        if (blockInfo.state().getBlock() == Blocks.STRUCTURE_VOID) {
            // Mark as jigsaw connection point
            return blockInfo;
        }

        return blockInfo;
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return BackroomsProcessorTypes.BACKROOMS_PROCESSOR.get();
    }
}