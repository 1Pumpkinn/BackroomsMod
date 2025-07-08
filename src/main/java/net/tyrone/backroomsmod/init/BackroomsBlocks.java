package net.tyrone.backroomsmod.init;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.tyrone.backroomsmod.BackroomsMod;

public class BackroomsBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, net.tyrone.backroomsmod.BackroomsMod.MODID);

    // Backrooms blocks
    public static final RegistryObject<Block> BACKROOMS_WALL = BLOCKS.register("backrooms_wall",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_YELLOW)
                    .strength(2.0F, 6.0F)
                    .sound(SoundType.STONE)
                    .requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> BACKROOMS_FLOOR = BLOCKS.register("backrooms_floor",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_YELLOW)
                    .strength(2.0F, 6.0F)
                    .sound(SoundType.STONE)
                    .requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> BACKROOMS_CEILING = BLOCKS.register("backrooms_ceiling",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_YELLOW)
                    .strength(2.0F, 6.0F)
                    .sound(SoundType.STONE)
                    .requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> MOLDY_WALL = BLOCKS.register("moldy_wall",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_GREEN)
                    .strength(1.5F, 4.0F)
                    .sound(SoundType.STONE)
                    .requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> BUZZING_LIGHT = BLOCKS.register("buzzing_light",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_LIGHT_GRAY)
                    .strength(1.0F, 2.0F)
                    .sound(SoundType.GLASS)
                    .lightLevel(state -> 15)
                    .requiresCorrectToolForDrops()));
}