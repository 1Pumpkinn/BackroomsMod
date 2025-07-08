package net.tyrone.backroomsmod.world.structure;

import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.tyrone.backroomsmod.BackroomsMod;
import net.tyrone.backroomsmod.init.BackroomsTemplatepools;

import java.util.List;

public class BackroomsJigsawGenerator {
    private static final int MAX_DEPTH = 7;
    private static final int MAX_PIECES = 128;

    public static void generateJigsawStructure(
            StructurePiecesBuilder builder,
            BlockPos startPos,
            Rotation rotation,
            int maxDepth,
            RandomSource random,
            Registry<StructureTemplatePool> templatePoolRegistry,
            StructureTemplateManager templateManager) {

        // Get the starting template pool
        ResourceLocation startPoolLocation = new ResourceLocation(BackroomsMod.MODID, "backrooms/hall_start");
        Holder<StructureTemplatePool> startPool = templatePoolRegistry.getHolderOrThrow(
                BackroomsTemplatepools.BACKROOMS_HALL_START);

        // Use JigsawPlacement for proper jigsaw generation
        JigsawPlacement.addPieces(
                builder,
                startPool,
                startPos,
                maxDepth,
                rotation,
                templateManager,
                random,
                MAX_PIECES
        );
    }

    public static void generateBackroomsLayout(
            StructurePiecesBuilder builder,
            BlockPos centerPos,
            RandomSource random,
            Registry<StructureTemplatePool> templatePools,
            StructureTemplateManager templateManager) {

        List<BackroomsRoom> rooms = generateRoomLayout(centerPos, random);

        for (BackroomsRoom room : rooms) {
            generateRoom(builder, room, random, templatePools, templateManager);
        }

        // Connect rooms with hallways
        connectRooms(builder, rooms, random, templatePools, templateManager);
    }

    private static List<BackroomsRoom> generateRoomLayout(BlockPos center, RandomSource random) {
        List<BackroomsRoom> rooms = Lists.newArrayList();

        // Generate a grid-like layout with some randomness
        int gridSize = 5 + random.nextInt(3); // 5-7 rooms per side
        int spacing = 24; // Distance between room centers

        for (int x = -gridSize/2; x <= gridSize/2; x++) {
            for (int z = -gridSize/2; z <= gridSize/2; z++) {
                // Skip some rooms for variety
                if (random.nextFloat() < 0.3f) continue;

                BlockPos roomPos = center.offset(x * spacing, 0, z * spacing);
                BackroomsRoom.RoomType type = BackroomsRoom.RoomType.values()[random.nextInt(BackroomsRoom.RoomType.values().length)];

                rooms.add(new BackroomsRoom(roomPos, type, Rotation.getRandom(random)));
            }
        }

        return rooms;
    }

    private static void generateRoom(
            StructurePiecesBuilder builder,
            BackroomsRoom room,
            RandomSource random,
            Registry<StructureTemplatePool> templatePools,
            StructureTemplateManager templateManager) {

        ResourceLocation templateLocation = room.getTemplateLocation();

        BackroomsStructurePiece piece = new BackroomsStructurePiece(
                room.getPos(),
                templateLocation,
                random
        );

        builder.addPiece(piece);
    }

    private static void connectRooms(
            StructurePiecesBuilder builder,
            List<BackroomsRoom> rooms,
            RandomSource random,
            Registry<StructureTemplatePool> templatePools,
            StructureTemplateManager templateManager) {

        for (int i = 0; i < rooms.size(); i++) {
            BackroomsRoom room1 = rooms.get(i);

            // Connect to nearby rooms
            for (int j = i + 1; j < rooms.size(); j++) {
                BackroomsRoom room2 = rooms.get(j);

                if (room1.getPos().distSqr(room2.getPos()) < 32 * 32) { // Within 32 blocks
                    if (random.nextFloat() < 0.4f) { // 40% chance to connect
                        generateHallway(builder, room1.getPos(), room2.getPos(), random, templatePools, templateManager);
                    }
                }
            }
        }
    }

    private static void generateHallway(
            StructurePiecesBuilder builder,
            BlockPos start,
            BlockPos end,
            RandomSource random,
            Registry<StructureTemplatePool> templatePools,
            StructureTemplateManager templateManager) {

        // Simple L-shaped hallway
        BlockPos corner = new BlockPos(start.getX(), start.getY(), end.getZ());

        // Generate straight hallway from start to corner
        generateHallwaySegment(builder, start, corner, random, templateManager);

        // Generate straight hallway from corner to end
        generateHallwaySegment(builder, corner, end, random, templateManager);
    }

    private static void generateHallwaySegment(
            StructurePiecesBuilder builder,
            BlockPos start,
            BlockPos end,
            RandomSource random,
            StructureTemplateManager templateManager) {

        int distance = Math.max(Math.abs(end.getX() - start.getX()), Math.abs(end.getZ() - start.getZ()));
        int segments = distance / 8; // 8 block segments

        for (int i = 0; i < segments; i++) {
            BlockPos segmentPos = start.offset(
                    (end.getX() - start.getX()) * i / segments,
                    0,
                    (end.getZ() - start.getZ()) * i / segments
            );

            BackroomsStructurePiece piece = new BackroomsStructurePiece(
                    segmentPos,
                    new ResourceLocation(BackroomsMod.MODID, "backrooms/hall_straight"),
                    random
            );

            builder.addPiece(piece);
        }
    }

    private static class BackroomsRoom {
        private final BlockPos pos;
        private final RoomType type;
        private final Rotation rotation;

        public BackroomsRoom(BlockPos pos, RoomType type, Rotation rotation) {
            this.pos = pos;
            this.type = type;
            this.rotation = rotation;
        }

        public BlockPos getPos() { return pos; }
        public RoomType getType() { return type; }
        public Rotation getRotation() { return rotation; }

        public ResourceLocation getTemplateLocation() {
            return new ResourceLocation(BackroomsMod.MODID, "backrooms/" + type.getTemplateName());
        }

        public enum RoomType {
            SMALL("room_small"),
            LARGE("room_large"),
            PILLAR("room_pillar"),
            CORNER("room_corner"),
            OFFICE("room_office");

            private final String templateName;

            RoomType(String templateName) {
                this.templateName = templateName;
            }

            public String getTemplateName() {
                return templateName;
            }
        }
    }
}