package net.tyrone.backroomsmod.init;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.tyrone.backroomsmod.BackroomsMod;

public class BackroomsItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, net.tyrone.backroomsmod.BackroomsMod.MODID);

    // Block items
    public static final RegistryObject<Item> BACKROOMS_WALL = ITEMS.register("backrooms_wall",
            () -> new BlockItem(BackroomsBlocks.BACKROOMS_WALL.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));

    public static final RegistryObject<Item> BACKROOMS_FLOOR = ITEMS.register("backrooms_floor",
            () -> new BlockItem(BackroomsBlocks.BACKROOMS_FLOOR.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));

    public static final RegistryObject<Item> BACKROOMS_CEILING = ITEMS.register("backrooms_ceiling",
            () -> new BlockItem(BackroomsBlocks.BACKROOMS_CEILING.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));

    public static final RegistryObject<Item> MOLDY_WALL = ITEMS.register("moldy_wall",
            () -> new BlockItem(BackroomsBlocks.MOLDY_WALL.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));

    public static final RegistryObject<Item> BUZZING_LIGHT = ITEMS.register("buzzing_light",
            () -> new BlockItem(BackroomsBlocks.BUZZING_LIGHT.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
}