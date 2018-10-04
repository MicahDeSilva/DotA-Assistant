package main;

import main.HeroPortraitRecog.HeroName;

public class HeroData
{
	public static enum Attribute
	{
		STRENGTH, AGILITY, INTELLIGENCE
	}

	public final HeroName heroName;
	public final float STRENGTH, STRENGTH_GAIN, AGILITY, AGILITY_GAIN, INTELLIGENCE, INTELLIGENCE_GAIN, MOVE_SPEED, ARMOR_START, ATK_DAMAGE_MIN, ATK_DAMAGE_MAX, ATK_RANGE, BASE_ATK_TIME, DAY_VISION, NIGHT_VISION, TURN_RATE, BASE_HP_REGEN,
			BASE_ATK_DAMAGE, BASE_ARMOR, MAG_RESIST;
	public final Attribute primaryAttribute;

	public HeroData(HeroName heroName, String stats)
	{
		this.heroName = heroName;
		String[] statSplit = stats.split(",");

		primaryAttribute = Attribute.valueOf(statSplit[0].toUpperCase());

		STRENGTH = Float.parseFloat(statSplit[1]);
		STRENGTH_GAIN = Float.parseFloat(statSplit[2]);
		AGILITY = Float.parseFloat(statSplit[3]);
		AGILITY_GAIN = Float.parseFloat(statSplit[4]);
		INTELLIGENCE = Float.parseFloat(statSplit[5]);
		INTELLIGENCE_GAIN = Float.parseFloat(statSplit[6]);

		MOVE_SPEED = Float.parseFloat(statSplit[10]);
		ARMOR_START = Float.parseFloat(statSplit[11]);
		ATK_DAMAGE_MIN = Float.parseFloat(statSplit[12]);
		ATK_DAMAGE_MAX = Float.parseFloat(statSplit[13]);
		ATK_RANGE = Float.parseFloat(statSplit[14]);
		BASE_ATK_TIME = Float.parseFloat(statSplit[15]);

		DAY_VISION = Float.parseFloat(statSplit[18]);
		NIGHT_VISION = Float.parseFloat(statSplit[19]);
		TURN_RATE = Float.parseFloat(statSplit[20]);
		BASE_HP_REGEN = Float.parseFloat(statSplit[22]);

		BASE_ARMOR = ARMOR_START - (AGILITY / 7);

		if (heroName == HeroName.Meepo)
			MAG_RESIST = 0.35f;
		else if (heroName == HeroName.Visage)
			MAG_RESIST = 0.1f;
		else
			MAG_RESIST = 0.25f;

		switch (primaryAttribute)
		{
			case STRENGTH:
				BASE_ATK_DAMAGE = ((ATK_DAMAGE_MIN + ATK_DAMAGE_MAX) / 2f) - STRENGTH;
				break;
			case AGILITY:
				BASE_ATK_DAMAGE = ((ATK_DAMAGE_MIN + ATK_DAMAGE_MAX) / 2f) - AGILITY;
				break;
			default:
				BASE_ATK_DAMAGE = ((ATK_DAMAGE_MIN + ATK_DAMAGE_MAX) / 2f) - INTELLIGENCE;
				break;
		}

	}

	public byte[] getAbilityLevels(int heroLevel)
	{
		byte[] abilityLevels = new byte[4];

		for (int i = 0; i < 4; i++)
		{
			abilityLevels[i] = 0;
		}

		byte[] abilityData = HeroAbilityGetter.heroAbilityPool[HeroStatGetter.HeroNameToLookup(heroName)].abilityBuild;

		for (int i = 0; i < Math.min(heroLevel, 18); i++)
		{
			if (abilityData[i] != 0)
				abilityLevels[abilityData[i] - 1]++;
		}

		return abilityLevels;
	}
}
