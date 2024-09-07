package analyzer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class FileAnalyzer {

    private List<String> lines;

    /**
     * Constructor to initialize the FileAnalyzer object
     *
     * @param filePath The path to the file to be analyzed
     * @throws IOException If an error occurs while reading the file
     */
    public FileAnalyzer(String filePath) throws IOException {
        try {
            lines = Files.readAllLines(Paths.get(filePath));
        } catch (IOException e) {
            System.out.println("Failed to read file: " + filePath);
            e.printStackTrace();
        }
    }

    /**
     * Count the number of lines in a file
     *
     * @return The number of lines in the file
     */
    public long countLines(){
        return lines.size();
    }

    /**
     * Count the total number of words in a file
     *
     * @return The total number of words in the file
     */
    public long countWords(){
        return lines.stream()
                .map(line -> line.split("\\W+").length)
                .reduce(0,Integer::sum);
    }

    /**
     * Count the number of non-empty lines in a file
     *
     * @return The number of non-empty lines in the file
     */
    public long countNonEmptyLines(){
        return lines.stream()
                .filter(line -> !line.trim().isEmpty())
                .count();
    }

    /**
     * Count the number of unique words in a file
     *
     * @return The number of unique words in the file
     */
    public long countUniqueWords(){
        return lines.stream()
                .flatMap(line -> Arrays.stream(line.split("\\W+")))
                .map(String::toLowerCase)
                .distinct()
                .count();
    }

    /**
     * Count the occurrences of a specific word in a file
     *
     * @param word The word to be counted
     * @return The number of occurrences of the word in the file
     */
    public long countSpecificWord(String word){
        String lowerCaseWord = word.toLowerCase();
        return lines.stream()
                .flatMap(line -> Arrays.stream(line.split("\\W+")))
                .filter(w -> w.equalsIgnoreCase(lowerCaseWord))
                .count();
    }

    /**
     * Find lines that contain a specific word
     *
     * @param word
     * @return A list of lines that contain the word
     */
    public List<String> findLinesWithSpecificWord(String word){
        String lowerCaseWord = word.toLowerCase();
        return lines.stream()
                .filter(line -> Arrays.stream(line.split("\\W+")).anyMatch(w -> w.equalsIgnoreCase(lowerCaseWord)))
                .collect(Collectors.toList());
    }

    /**
     * Calculate the average line length in a file
     *
     * @return The average line length in the file
     */
    public double calculateAverageLineLength(){
        return lines.stream()
                .mapToInt(String::length)
                .average()
                .orElse(0.0);
    }

    /**
     * Calculate the average word length in a file
     *
     * @return The average word length in the file
     */
    public double calculateAverageWordLength() {
        return lines.stream()
                .flatMap(line -> Arrays.stream(line.split("\\W+")))
                .mapToInt(String::length)
                .average()
                .orElse(0.0);
    }

    /**
     * Find the shortest line in a file
     *
     * @return The shortest line in the file, or an empty string if the file is empty or all lines are empty
     */
    public String findShortestLine(){
        return lines.stream()
                .filter(line -> !line.trim().isEmpty())
                .min(Comparator.comparingInt(String::length))
                .orElse("");
    }

    /**
     * Find the longest line in a file
     *
     * @return The longest line in the file, or an empty string if the file is empty or all lines are empty
     */
    public String findLongestLine(){
        return lines.stream()
                .filter(line -> !line.trim().isEmpty())
                .max(Comparator.comparingInt(String::length))
                .orElse("");
    }

    /**
     * Find the top n longest words in a file
     *
     * @param n
     * @return A list of the top n longest words
     */
    public List<String> findTopLongestWords(int n){
        return lines.stream()
                .flatMap(line -> Arrays.stream(line.split("\\W+")))
                .distinct()
                .sorted(Comparator.comparingInt(String::length).reversed())
                .limit(n)
                .collect(Collectors.toList());
    }

    /**
     * Find the first word with more than n characters
     *
     * @param n The minimum number of characters in the word
     * @return An Optional containing the first word with more than n characters, or an empty Optional if no such word is found
     */
    public Optional<String> getFirstWordWithNChars(int n){
        return lines.stream()
                .flatMap(line -> Arrays.stream(line.split("\\W+")))
                .filter(word -> word.length() > n)
                .findFirst();
    }

    /**
     * Find common words between the current file and another file
     *
     * @param file2 The path to the second file
     * @return A list of common words between the two files
     * @throws IOException If there is an error reading the second file
     */
    public Set<String> findCommonWords(String file2) throws IOException{
        List<String> otherLines = Files.readAllLines(Paths.get(file2));
        Set<String> wordsInOtherFile = otherLines.stream()
                .flatMap(line -> Arrays.stream(line.split("\\W+")))
                .map(String::toLowerCase)
                .collect(Collectors.toSet());

        return lines.stream()
                .flatMap(line -> Arrays.stream(line.split("\\W+")))
                .map(String::toLowerCase)
                .filter(wordsInOtherFile::contains)
                .collect(Collectors.toSet());

    }

    /**
     * Find the frequency of each word in a file
     *
     * @return A map where the keys are the words and the values are their frequencies
     */
    public Map<String, Long> findWordFrequency(){
        return lines.stream()
                .flatMap(line -> Arrays.stream(line.split("\\W+")))
                .map(String::toLowerCase)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    /**
     * Find the lines that contain numbers in a file
     *
     * @return A list of lines that contain numbers in the file
     */
    public List<String> findLinesContainingNumbers(){
        return lines.stream()
                .filter(line -> line.matches(".*\\d+.*"))
                .collect(Collectors.toList());
    }

    /**
     * Find lines that match a regex pattern
     *
     * @param pattern
     * @return A list of lines that match the regex patter
     */
    public List<String> findLinesMatchingPattern(String pattern){
        Pattern regex = Pattern.compile(pattern);
        return lines.stream()
                .filter(line -> regex.matcher(line).find())
                .collect(Collectors.toList());
    }

    /**
     * Find palindromic words in a file
     *
     * @return A set of unique palindromic words found in the line
     */
    public Set<String> findPalindromicWords(){
        return lines.stream()
                .flatMap(line -> Arrays.stream(line.split("\\W+")))
                .filter(word -> !word.isEmpty())
                .filter(word -> new StringBuilder(word).reverse().toString().equalsIgnoreCase(word))
                .collect(Collectors.toSet());
    }
}
