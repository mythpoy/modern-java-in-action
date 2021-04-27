package challenges;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.partitioningBy;

public class Strings {

    private static final Set<Character> allVowels
            = new HashSet(Arrays.asList('a', 'e', 'i', 'o', 'u'));

    private Strings() {
        throw new AssertionError("Cannot be instantiated");
    }

    public static Map<String, Long> countDuplicateCharacters(String str) {

        if (str == null || str.isBlank()) {
            // or throw IllegalArgumentException
            return Collections.emptyMap();
        }

        return str.codePoints()
                .mapToObj(c -> String.valueOf(Character.toChars(c)))
                .collect(Collectors.groupingBy(c -> c, counting()));
    }

    public static String firstNonRepeatedCharacter(String str) {

        if (str == null || str.isBlank()) {
            // or throw IllegalArgumentException
            return String.valueOf(Character.MIN_VALUE);
        }

        Map<Integer, Long> chs = str.codePoints()
                .mapToObj(cp -> cp)
                .collect(Collectors.groupingBy(Function.identity(),
                        LinkedHashMap::new, counting()));

        int cp = chs.entrySet().stream()
                .filter(e -> e.getValue() == 1L)
                .findFirst()
                .map(Map.Entry::getKey)
                .orElse(Integer.valueOf(Character.MIN_VALUE));

        return String.valueOf(Character.toChars(cp));
    }

    public static String reverse(String str) {

        if (str == null || str.isBlank()) {
            // or throw IllegalArgumentException
            return "";
        }

        return new StringBuilder(str).reverse().toString();
    }

    public static boolean containsOnlyDigits(String str) {

        if (str == null || str.isBlank()) {
            // or throw IllegalArgumentException
            return false;
        }

        return !str.chars()
                .anyMatch(n -> !Character.isDigit(n));
    }

    public static Pair<Long, Long> countVowelsAndConsonants(String str) {
        Map<Boolean, Long> result = str.chars()
                .mapToObj(c -> (char) c)
                .filter(ch -> (ch >= 'a' && ch <= 'z'))
                .collect(partitioningBy(c -> allVowels.contains(c), counting()));

        return Pair.of(result.get(true), result.get(false));
    }

    public static long countOccurrencesOfACertainCharacter(String str, String ch) {

        if (str == null || ch == null || str.isEmpty() || ch.isEmpty()) {
            // or throw IllegalArgumentException
            return -1;
        }

        if (ch.codePointCount(0, ch.length()) > 1) {
            return -1; // there is more than 1 Unicode character in the given String
        }

        int codePoint = ch.codePointAt(0);

        return str.codePoints()
                .filter(c -> c == codePoint)
                .count();
    }

    public static String removeWhitespaces(String str) {

        if (str == null || str.isEmpty()) {
            // or throw IllegalArgumentException
            return "";
        }

        return str.replaceAll("\\s", "");
    }

    public static String joinByDelimiter(char delimiter, String... args) {

        if (args == null || args.length == 0) {
            // or throw IllegalArgumentException
            return "";
        }

        return Arrays.stream(args, 0, args.length)
                .collect(Collectors.joining(String.valueOf(delimiter)));
    }
}
