package ohm.softa.a13.tweets.generators;

import ohm.softa.a13.model.Tweet;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.stream.Stream;

import java.util.Arrays;
import java.util.stream.Stream;

public class OfflineTweetStreamGenerator implements TweetStreamGenerator{

	private static final String TRUMP_TWEETS_JSON_PATH = "/trump_tweets.json";

	private final Gson gson;

	public OfflineTweetStreamGenerator() {
		this.gson = new Gson();
	}

	@Override
	public Stream<Tweet> getTweetStream() {
		try (Reader reader = new InputStreamReader(getClass().getResourceAsStream(TRUMP_TWEETS_JSON_PATH))){
			/* create new Stream of deserialized array of Tweets */
			return Arrays.stream(gson.fromJson(reader, Tweet[].class));
		}catch (IOException e){
			return Stream.of();
		}
	}
}
