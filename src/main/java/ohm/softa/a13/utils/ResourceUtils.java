package ohm.softa.a13.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

import static java.util.stream.Collectors.toSet;
import static ohm.softa.a13.utils.FunctionalUtils.tryWithResources;

/**
 * Util class to load resources
 *
 * @author Peter Kurfer
 */
public abstract class ResourceUtils {

	private static final Class<?> THIS_CLASS = ResourceUtils.class;
	private static final String STOP_WORDS_FILE_PATH = "/stopwords.txt";

	private ResourceUtils() {

	}

	/**
	 * Load stop words from the app resources
	 *
	 * @return List of stop words - will be empty if resource file is not found or an error occurred
	 */
	public static Set<String> loadStopWords() {
		final Set<String> stopWords = new HashSet<>();
		try (final var scanner = new Scanner(THIS_CLASS.getResourceAsStream(STOP_WORDS_FILE_PATH))) {
			while (scanner.hasNext()) {
				stopWords.add(scanner.next());
			}
		} catch (Exception e) {
			/* ignored */
		}
		return stopWords;
	}

	/**
	 * Load stop words from the app resources
	 *
	 * @return List of stop words - will be empty if resource file is not found or an error occurred
	 */
	public static Set<String> loadStopWordsFunctional() {
		return tryWithResources(
			() -> new BufferedReader(new InputStreamReader(THIS_CLASS.getResourceAsStream(STOP_WORDS_FILE_PATH))),
			br -> br.lines().collect(toSet()),
			HashSet::new
		);
	}
}
