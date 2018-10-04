package main;

import java.util.Arrays;

import main.HeroPortraitRecog.HeroName;

public class HeroAbilityData
{
	public final HeroName heroName;
	public byte[] abilityBuild;

	public HeroAbilityData(HeroName heroName, String abilites)
	{
		this.heroName = heroName;
		String[] abilitySplit = abilites.split(",");
		byte[] abilitySplitByte = new byte[abilitySplit.length];

		for (int i = 0; i < abilitySplit.length; i++)
		{
			abilitySplitByte[i] = (byte) Integer.parseInt(abilitySplit[i]);
		}

		abilityBuild = new byte[18];

		for (int i = 0; i < abilityBuild.length; i++)
		{
			abilityBuild[i] = 0;

			for (int j = 0; j < abilitySplitByte.length; j++)
			{
				if (abilitySplitByte[j] == i+1)
				{
					abilityBuild[i] = (byte) ((j / 4) + 1);
				}
			}
		}
	}
}
