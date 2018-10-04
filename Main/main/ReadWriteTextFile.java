package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ReadWriteTextFile
{

	List<String> lines;

	final static String FILE_NAME = "Resources/ItemColors.txt";
	final static String OUTPUT_FILE_NAME = "Resources/Test.txt";
	final static Charset ENCODING = StandardCharsets.UTF_8;

	// For smaller files

	/**
	 * Note: the javadoc of Files.readAllLines says it's intended for small files. But its implementation uses buffering, so it's likely good even for fairly large files.
	 */
	List<String> readSmallTextFile(String aFileName) throws IOException
	{
		Path path = Paths.get(aFileName);
		return Files.readAllLines(path, ENCODING);
	}

	void writeSmallTextFile(List<String> aLines, String aFileName) throws IOException
	{
		Path path = Paths.get(aFileName);
		Files.write(path, aLines, ENCODING);
	}
}