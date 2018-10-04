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
public class HeroStatGetter
{
	public static HeroData[] bigHeroDataPool = new HeroData[Main.TOTAL_HERO_COUNT];
	public static Map<HeroName, Integer> map = new HashMap<HeroName, Integer>();

	public int iteration = 0;

	public HeroStatGetter(String aFileName)
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
		map.put(heroName, iteration);
		bigHeroDataPool[iteration] = new HeroData(heroName, aLine.split(":")[1]);
		iteration ++;
	}

	public static int HeroNameToLookup(HeroName heroName)
	{
		return map.get(heroName);
	}

	// Private vars
	private final Path fFilePath;
	private final static Charset ENCODING = StandardCharsets.UTF_8;
}