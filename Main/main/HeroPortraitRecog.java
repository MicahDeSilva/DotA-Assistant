package main;

import java.awt.AWTException;
import java.awt.Robot;
import java.util.Arrays;

public class HeroPortraitRecog
{
	public static int iteration;

	public static enum HeroName
	{
		Anti_Mage, Axe, Bane, Bloodseeker, Crystal_Maiden, Drow_Ranger, Earthshaker, Juggernaut, Mirana, Shadow_Fiend, Morphling, Phantom_Lancer, Puck, Pudge, Razor, Sand_King, Storm_Spirit, Sven, Tiny, Vengeful_Spirit, Windranger, Zeus, Kunkka, Lina, Lich, Lion, Shadow_Shaman, Slardar, Tidehunter, Witch_Doctor, Riki, Enigma, Tinker, Sniper, Necrophos, Warlock, Beastmaster, Queen_of_Pain, Venomancer, Faceless_Void, Wraith_King, Death_Prophet, Phantom_Assassin, Pugna, Templar_Assassin, Viper, Luna, Dragon_Knight, Dazzle, Clockwerk, Leshrac, Natures_Prophet, Lifestealer, Dark_Seer, Clinkz, Omniknight, Enchantress, Huskar, Night_Stalker, Broodmother, Bounty_Hunter, Weaver, Jakiro, Batrider, Chen, Spectre, Doom, Ancient_Apparition, Ursa, Spirit_Breaker, Gyrocopter, Alchemist, Invoker, Silencer, Outworld_Devourer, Lycan, Brewmaster, Shadow_Demon, Lone_Druid, Chaos_Knight, Meepo, Treant_Protector, Ogre_Magi, Undying, Rubick, Disruptor, Nyx_Assassin, Naga_Siren, Keeper_of_the_Light, Io, Visage, Slark, Medusa, Troll_Warlord, Centaur_Warrunner, Magnus, Timbersaw, Bristleback, Tusk, Skywrath_Mage, Abaddon, Elder_Titan, Legion_Commander, Ember_Spirit, Earth_Spirit, Terrorblade, Phoenix, Oracle, Techies, Winter_Wyvern, Arc_Warden, Underlord, Monkey_King;
	}

	public static HeroName[] heroRecog()
	{
		HeroName[] hero = new HeroName[10];
		try
		{
			Robot robot = new Robot();

			for (int i = 0; i < 5; i++)
			{
				hero[i] = findHeroByColour(robot.getPixelColor(885 + 63 * i, 20).getRGB());
			}

			for (int i = 0; i < 5; i++)
			{
				hero[i + 5] = findHeroByColour(robot.getPixelColor(1409 + 63 * i, 20).getRGB());
			}

			return hero;
		}
		catch (AWTException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
	}

	public static HeroName findHeroByColour(int rgb)
	{

		switch (rgb)
		{
			case (-16645116):
				return HeroName.Abaddon;
			case (-8892653):
				return HeroName.Alchemist;
			case (-8742706):
				return HeroName.Ancient_Apparition;
			case (-16316658):
				return HeroName.Anti_Mage;
			case (-8930575):
				return HeroName.Arc_Warden;
			case (-13094352):
				return HeroName.Axe;
			case (-14475743):
				return HeroName.Bane;
			case (-16579840):
				return HeroName.Batrider;
			case (-10470880):
				return HeroName.Beastmaster;
			case (-11530490):
				return HeroName.Bloodseeker;
			case (-6330042):
				return HeroName.Bounty_Hunter;
			case (-3760546):
				return HeroName.Brewmaster;
			case (-11582686):
				return HeroName.Bristleback;
			case (-13952214):
				return HeroName.Broodmother;
			case (-8633053):
				return HeroName.Centaur_Warrunner;
			case (-8381430):
				return HeroName.Chaos_Knight;
			case (-11388893):
				return HeroName.Chen;
			case (-6654664):
				return HeroName.Clinkz;
			case (-10918548):
				return HeroName.Clockwerk;
			case (-3229544):
				return HeroName.Crystal_Maiden;
			case (-15264477):
				return HeroName.Dark_Seer;
			case (-9366946):
				return HeroName.Dazzle;
			case (-12641213):
				return HeroName.Death_Prophet;
			case (-9360825):
				return HeroName.Disruptor;
			case (-15134965):
				return HeroName.Doom;
			case (-11714512):
				return HeroName.Dragon_Knight;
			case (-4793602):
				return HeroName.Drow_Ranger;
			case (-8696035):
				return HeroName.Earthshaker;
			case (-11303838):
				return HeroName.Earth_Spirit;
			case (-3298558):
				return HeroName.Elder_Titan;
			case (-8966378):
				return HeroName.Ember_Spirit;
			case (-6599627):
				return HeroName.Enchantress;
			case (-12705705):
				return HeroName.Enigma;
			case (-13288834):
				return HeroName.Faceless_Void;
			case (-12831697):
				return HeroName.Gyrocopter;
			case (-16043198):
				return HeroName.Huskar;
			case (-3429479):
				return HeroName.Invoker;
			case (-2826261):
				return HeroName.Io;
			case (-8800561):
				return HeroName.Jakiro;
			case (-6326692):
				return HeroName.Juggernaut;
			case (-6133177):
				return HeroName.Keeper_of_the_Light;
			case (-13361900):
				return HeroName.Kunkka;
			case (-15069422):
				return HeroName.Legion_Commander;
			case (-14062944):
				return HeroName.Leshrac;
			case (-12773856):
				return HeroName.Lich;
			case (-12902388):
				return HeroName.Lifestealer;
			case (-7325152):
				return HeroName.Lina;
			case (-7843215):
				return HeroName.Lion;
			case (-15005437):
				return HeroName.Lone_Druid;
			case (-2384004):
				return HeroName.Luna;
			case (-11707321):
				return HeroName.Lycan;
			case (-13812916):
				return HeroName.Magnus;
			case (-11460084):
				return HeroName.Medusa;
			case (-12032363):
				return HeroName.Meepo;
			case (-3761760):
				return HeroName.Mirana;
			case (-1140872):
				return HeroName.Monkey_King;
			case (-14373943):
				return HeroName.Morphling;
			case (-5210812):
				return HeroName.Naga_Siren;
			case (-7499921):
				return HeroName.Natures_Prophet;
			case (-15385269):
				return HeroName.Necrophos;
			case (-14078136):
				return HeroName.Night_Stalker;
			case (-7766199):
				return HeroName.Nyx_Assassin;
			case (-9410431):
				return HeroName.Ogre_Magi;
			case (-2650267):
				return HeroName.Omniknight;
			case (-13757144):
				return HeroName.Oracle;
			case (-12880542):
				return HeroName.Outworld_Devourer;
			case (-15386553):
				return HeroName.Phantom_Assassin;
			case (-6636346):
				return HeroName.Phantom_Lancer;
			case (-12509946):
				return HeroName.Phoenix;
			case (-11688459):
				return HeroName.Puck;
			case (-5465250):
				return HeroName.Pudge;
			case (-15904697):
				return HeroName.Pugna;
			case (-14276564):
				return HeroName.Queen_of_Pain;
			case (-12605470):
				return HeroName.Razor;
			case (-15790551):
				return HeroName.Riki;
			case (-16315885):
				return HeroName.Rubick;
			case (-5206190):
				return HeroName.Sand_King;
			case (-8387065):
				return HeroName.Shadow_Demon;
			case (-15987186):
				return HeroName.Shadow_Fiend;
			case (-14342879):
				return HeroName.Shadow_Shaman;
			case (-11980221):
				return HeroName.Silencer;
			case (-3370370):
				return HeroName.Skywrath_Mage;
			case (-8092793):
				return HeroName.Slardar;
			case (-6778993):
				return HeroName.Slark;
			case (-9030590):
				return HeroName.Sniper;
			case (-16644859):
				return HeroName.Spectre;
			case (-14734795):
				return HeroName.Spirit_Breaker;
			case(-14037815):
				return HeroName.Storm_Spirit;
			case (-14407639):
				return HeroName.Sven;
			case(-8362676):
				return HeroName.Techies;
			case(-13282972):
				return HeroName.Templar_Assassin;
			case(-14409165):
				return HeroName.Terrorblade;
			case (-10926276):
				return HeroName.Tidehunter;
			case(-14741218):
				return HeroName.Timbersaw;
			case(-4145953):
				return HeroName.Tinker;
			case (-11314360):
				return HeroName.Tiny;
			case(-6387594):
				return HeroName.Treant_Protector;
			case(-1090487):
				return HeroName.Troll_Warlord;
			case(-1579551):
				return HeroName.Tusk;
			case (-5595549):
				return HeroName.Underlord;
			case(-14344169):
				return HeroName.Undying;
			case(-14216688):
				return HeroName.Ursa;
			case(-13609601):
				return HeroName.Vengeful_Spirit;
			case(-14018299):
				return HeroName.Venomancer;
			case (-16580088):
				return HeroName.Viper;
			case(-16051689):
				return HeroName.Visage;
			case (-3107732):
				return HeroName.Warlock;
			case (-12572373):
				return HeroName.Weaver;
			case (-2911385):
				return HeroName.Windranger;
			case(-15849939):
				return HeroName.Winter_Wyvern;
			case (-3750952):
				return HeroName.Witch_Doctor;
			case (-12059997):
				return HeroName.Wraith_King;
			case (-9745094):
				return HeroName.Zeus;
			case(-6931669):
				return HeroName.Windranger;
			
			
			case (-13290187):
				return null;
			default:
				System.out.println("case(" + rgb + "):\n\treturn HeroName.;");
		}
		return null;
	}
}
