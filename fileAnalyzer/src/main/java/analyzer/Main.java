package analyzer;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    private static final String FILEPATH = "src/main/resources/";

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter first file to analyze: ");
        String file1 = FILEPATH + scanner.nextLine();
        System.out.println("Enter second file to analyze: ");
        String file2 = FILEPATH + scanner.nextLine();

        try {
            FileAnalyzer analyzer = new FileAnalyzer(file1);

            System.out.println("Total lines: " + analyzer.countLines());
            System.out.println("Total words: " + analyzer.countWords());
            System.out.println("Non-empty lines: " + analyzer.countNonEmptyLines());
            System.out.println("Unique words: " + analyzer.countUniqueWords());
            System.out.println("Occurrences of computer: " + analyzer.countSpecificWord("computer"));
            System.out.println("Lines with advancements: " + analyzer.findLinesWithSpecificWord("advancements"));
            System.out.println("Average line length: " + analyzer.calculateAverageLineLength());
            System.out.println("Average word length: " + analyzer.calculateAverageWordLength());
            System.out.println("Shortest line: " + analyzer.findShortestLine());
            System.out.println("Longest line: " + analyzer.findLongestLine());
            System.out.println("Top 5 longest words: " + analyzer.findTopLongestWords(5));
            System.out.println("First word with more than 5 characters: " + analyzer.getFirstWordWithNChars(5).orElse("None"));
            System.out.println("Common words between files: " + analyzer.findCommonWords(file2));
            System.out.println("Word frequency: " + analyzer.findWordFrequency());
            System.out.println("Lines containing numbers: " + analyzer.findLinesContainingNumbers());
            System.out.println("Lines matching regex pattern '([A-Z])\\w+': " + analyzer.findLinesMatchingPattern("([A-Z])\\w+"));
            System.out.println("Palindromic words: " + analyzer.findPalindromicWords());

        } catch (IOException e){
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}