package main;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import main.HeroPortraitRecog.HeroName;

public class ItemDataGetter
{
	public enum ItemName
	{
		Abyssal_Blade, Arcane_Ring, Armlet_of_Mordiggian, Assault_Cuirass, Skull_Basher, Belt_of_Strength, Battle_Fury, Black_King_Bar, Blade_Mail,
		Blades_of_Attack, Blade_of_Alacrity, Blink_Dagger, Bloodstone, Boots_of_Speed, Band_of_Elvenskin, Bottle, Rune_Double_Damage, Rune_Haste,
		Rune_Illusion, Rune_Invisibility, Rune_Regeneration, Rune_Bounty, Rune_Arcane, Bracer, Iron_Branch, Broadsword, Buckler, Butterfly, Chainmail,
		Circlet, Clarity, Faerie_Fire, Skybreaker, Infused_Raindrop, Banana, Jade_Bow, Spirit_Helix, Tome_of_Knowledge, Wind_Lace, Blight_Stone,
		Dragon_Lance, Aether_Lens, Claymore, Cloak, Crimson_Guard, Animal_Courier, Flying_Courier, Euls_Scepter_of_Divinity,
		Euls_Scepter_of_Divinity_level_2, Dagon, Dagon_level_2, Dagon_level_3, Dagon_level_4, Dagon_level_5, Demon_Edge, Desolator, Diffusal_Blade,
		Diffusal_Blade_level_2, Dust_of_Appearance, Eaglesong, Energy_Booster, Ethereal_Blade, Healing_Salve, Force_Staff, Force_Staff_Recipe,
		Gauntlets_of_Strength, Gem_of_True_Sight, Ghost_Scepter, Aether_Staff, Gloves_of_Haste, Daedalus, Hand_of_Midas, Headdress,
		Heart_of_Tarrasque, Helm_of_Iron_Will, Helm_of_the_Dominator, Hood_of_Defiance, Hyperstone, Shadow_Blade, Silver_Edge, Glimmer_Cape, Red_Mist,
		Octarine_Core, Iron_Talon, Enchanted_Mango, Lotus_Orb, Nightfall_Striders, Guardian_Greaves, Observer_and_Sentry_Wards, Solar_Crest,
		Rune_Breaker, Bounty_Pact, Natures_Mend, Blinders, Javelin, Crystalys, Morbid_Mask, Linkens_Sphere, Maelstrom, Magic_Stick, Magic_Wand,
		Manta_Style, Mantle_of_Intelligence, Mask_of_Madness, Mekansm, Mithril_Hammer, Mjollnir, Monkey_King_Bar, Mystic_Staff, Necronomicon,
		Necronomicon_level_2, Necronomicon_level_3, Null_Talisman, Eye_of_Omen, Hurricane_Pike, Whip, Oblivion_Staff, Ogre_Club, Orchid_Malevolence,
		Bloodthorn, Perseverance, Phase_Boots, Pipe_of_Insight, Platemail, Point_Booster, Poor_Mans_Shield, Power_Treads, PT_Strength, PT_Agility,
		PT_Intelligence, Quarterstaff, Echo_Sabre, Quelling_Blade, Radiance, Divine_Rapier, Reaver, Refresher_Orb, Aegis_of_the_Immortal, Cheese,
		Sacred_Relic, Ring_of_Basilius, Ring_of_Health, Ring_of_Protection, Ring_of_Regen, Robe_of_the_Magi, Rod_of_Atos, Sange, Heavens_Halberd,
		Sange_and_Yasha, Satanic, Scythe_of_Vyse, Shivas_Guard, Eye_of_Skadi, Slippers_of_Agility, Sages_Mask, Soul_Booster, Soul_Ring,
		Staff_of_Wizardry, Stout_Shield, Venomous_Spike_Temp_Name, Moon_Shard, Talisman_of_Evasion, Tango, Tango_Shared, Town_Portal_Scroll,
		Tranquil_Boots, Tranquil_Boots_Recipe, Tranquil_Boots_Level_2, Tranquil_Boots_Level_2_Recipe, Boots_of_Travel, Boots_of_Travel_Recipe,
		Boots_of_Travel_2, Ultimate_Orb, Aghanims_Scepter, Urn_of_Shadows, Urn_of_Shadows_Recipe, Vanguard, Vitality_Booster, Vladmirs_Offering,
		Void_Stone, Observer_Ward, Sentry_Ward, Wraith_Band, Yasha, Arcane_Boots, Arcane_Boots_2, Orb_of_Venom, Drum_of_Endurance,
		Medallion_of_Courage, Smoke_of_Deceit, Veil_of_Discord, Ring_of_Aquila, Shadow_Amulet, Euls_Scepter_Recipe, Silver_Edge_Recipe,
		Maelstrom_Recipe, Shivas_Guard_Recipe, Diffusal_Blade_Recipe, Drum_of_Endurance_Recipe, Orchid_Malevolence_Recipe, Bloodstone_Recipe,
		Manta_Style_Recipe, Hurricane_Pike_Recipe, Refresher_Orb_Recipe, Linkens_Sphere_Recipe, Daedalus_Recipe, Hand_of_Midas_Recipe, Mekansm_Recipe,
		Assault_Cuirass_Recipe, Pipe_of_Insight_Recipe, Heart_of_Tarrasque_Recipe, Bloodthorn_Recipe, Guardian_Greaves_Recipe, Mjollnir_Recipe,
		Abyssal_Blade_Recipe, Black_King_Bar_Recipe, Dagon_Recipe, Soul_Ring_Recipe, Yasha_Recipe, Crimson_Guard_Recipe, Necronomicon_Recipe,
		Sange_Recipe, Skull_Basher_Recipe, Aether_Lens_Recipe, Veil_of_Discord_Recipe, Buckler_Recipe, Crystalys_Recipe, Mask_of_Madness_Recipe,
		Headdress_Recipe, Wraith_Band_Recipe, Helm_of_the_Dominator_Recipe, Armlet_of_Mordiggian_Recipe, Null_Talisman_Recipe, Iron_Talon_Recipe,
		Radiance_Recipe, Empty
	}

	public static ItemData[] itemDataPool = new ItemData[ItemName.values().length];
	public static Map<ItemName, Integer> map = new HashMap<ItemName, Integer>();

	public int iteration = 0;

	public ItemDataGetter(String aFileName)
	{
		fFilePath = Paths.get(aFileName);
	}

	public final void processLineByLine() throws IOException
	{
		try (Scanner scanner = new Scanner(fFilePath, ENCODING.name()))
		{
			while (scanner.hasNextLine())
			{
				processLine(scanner.nextLine());
			}
		}
	}

	protected void processLine(String aLine)
	{
		ItemName itemName = ItemName.valueOf(aLine.split(":")[0]);
		map.put(itemName, iteration);
		itemDataPool[iteration] = new ItemData(itemName, aLine.split(":")[1]);
		iteration++;
	}

	public static int ItemNameToLookup(ItemName itemName)
	{
		return map.get(itemName);
	}

	// Private vars
	private final Path fFilePath;
	private final static Charset ENCODING = StandardCharsets.UTF_8;
}
