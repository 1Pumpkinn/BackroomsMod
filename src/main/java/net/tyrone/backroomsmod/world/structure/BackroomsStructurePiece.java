package net.tyrone.backroomsmod.world.structure;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.TemplateStructurePiece;

public class BackroomsStructurePiece extends TemplateStructurePiece {
    public static final StructurePieceType TYPE = BackroomsStructurePiece::new;

    private final ResourceLocation templateLocation;
    private final Rotation rotation;

    public BackroomsStructurePiece(BlockPos pos, ResourceLocation templateLocation, RandomSource random) {
        super(TYPE, 0, StructureTemplateManager.createAndLoadTemplate(templateLocation), pos, getPlaceSettings(random));
        this.templateLocation = templateLocation;
        this.rotation = Rotation.getRandom(random);
    }

    public BackroomsStructurePiece(StructurePieceSerializationContext context, CompoundTag nbt) {
        super(TYPE, nbt, context.structureTemplateManager(), (resourceLocation) -> getPlaceSettings(context.random()));
        this.templateLocation = new ResourceLocation(nbt.getString("Template"));
        this.rotation = Rotation.valueOf(nbt.getString("Rotation"));
    }

    private static StructurePlaceSettings getPlaceSettings(RandomSource random) {
        return new StructurePlaceSettings()
                .setRotation(Rotation.getRandom(random))
                .setMirror(net.minecraft.world.level.block.Mirror.NONE)
                .setIgnoreEntities(false)
                .addProcessor(new BackroomsStructureProcessor());
    }

    @Override
    protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag nbt) {
        super.addAdditionalSaveData(context, nbt);
        nbt.putString("Template", this.templateLocation.toString());
        nbt.putString("Rotation", this.rotation.name());
    }

    @Override
    protected void handleDataMarker(String key, BlockPos pos, WorldGenLevel level, RandomSource random, BoundingBox box) {
        switch (key) {
            case "backrooms_spawner":
                // Handle spawner placement
                break;
            case "backrooms_loot":
                // Handle loot placement
                break;
            case "backrooms_connection":
                // Handle connection points for jigsaw
                break;
        }
    }

    @Override
    public void postProcess(WorldGenLevel level, StructureManager structureManager, ChunkGenerator generator, RandomSource random, BoundingBox box, ChunkPos chunkPos, BlockPos pos) {
        // Apply random variations
        if (random.nextFloat() < 0.3f) {
            // 30% chance for moldy variations
            applyMoldyVariations(level, random, box);
        }

        if (random.nextFloat() < 0.1f) {
            // 10% chance for broken lights
            applyBrokenLights(level, random, box);
        }

        super.postProcess(level, structureManager, generator, random, box, chunkPos, pos);
    }

    private void applyMoldyVariations(WorldGenLevel level, RandomSource random, BoundingBox box) {
        // Replace some wall blocks with moldy versions
        // Implementation would scan the bounding box and replace blocks
    }

    private void applyBrokenLights(WorldGenLevel level, RandomSource random, BoundingBox box) {
        // Remove some light blocks to create darker areas
        // Implementation would scan for light blocks and randomly remove them
    }
}