package ohm.softa.a13.utils;

import ohm.softa.a13.model.Tweet;

import java.util.regex.Pattern;
import java.util.stream.Stream;

public class SplitUtils {

	private static final Pattern ALPHA_NUMERICAL = Pattern.compile("[a-z0-9@]+");

	/**
	 * Pattern to split for words
	 */
	private static final Pattern WORD_SPLIT = Pattern.compile("\\W");


	private SplitUtils() {

	}

	public static Stream<String> splitTweetText(final Stream<Tweet> tweetStream) {
		return tweetStream
			.map(Tweet::getText)
			.flatMap(WORD_SPLIT::splitAsStream)
			.map(String::toLowerCase)
			.filter(word -> ALPHA_NUMERICAL.matcher(word).matches());
	}
}
