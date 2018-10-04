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

/** Assumes UTF-8 encoding. JDK 7+. */
public class HeroAbilityGetter
{
	public static HeroAbilityData[] heroAbilityPool = new HeroAbilityData[Main.TOTAL_HERO_COUNT];

	public HeroAbilityGetter(String aFileName)
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
		HeroName heroName = HeroName.valueOf(aLine.split(":")[0]);
		int arrLoc = HeroStatGetter.HeroNameToLookup(heroName);
		heroAbilityPool[arrLoc] = new HeroAbilityData(heroName, aLine.split(":")[1]);
	}

	// Private vars
	private final Path fFilePath;
	private final static Charset ENCODING = StandardCharsets.UTF_8;
}