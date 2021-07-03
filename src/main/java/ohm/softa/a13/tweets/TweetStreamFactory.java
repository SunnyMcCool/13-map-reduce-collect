package ohm.softa.a13.tweets;

import ohm.softa.a13.tweets.generators.OnlineTweetStreamGenerator;
import ohm.softa.a13.tweets.generators.OfflineTweetStreamGenerator;
import ohm.softa.a13.tweets.generators.TweetStreamGenerator;
import org.apache.commons.lang3.NotImplementedException;

import java.io.IOException;
import java.util.Properties;

/**
 * Factory singleton to create tweet streams
 * @author Peter Kurfer
 */
public final class TweetStreamFactory {

    private static final TweetStreamFactory instance = new TweetStreamFactory();

    private final boolean isTwitter4jConfigured;
    private final TweetStreamGenerator onlineTweetStreamGenerator;
	private final TweetStreamGenerator offlineTweetStreamGenerator;


	private TweetStreamFactory() {
        boolean configured = false;
        onlineTweetStreamGenerator = new OnlineTweetStreamGenerator();
		offlineTweetStreamGenerator = new OfflineTweetStreamGenerator();
        try {
            Properties twitter4jProps = new Properties();

            /* load properties to determine if Twitter4j is configured */
            twitter4jProps.load(TweetStreamFactory.class.getResourceAsStream("/twitter4j.properties"));

            /* filter properties for value '<dummy>' as in the initial state */
            configured = twitter4jProps.stringPropertyNames()
                    .stream()
                    .map(twitter4jProps::getProperty)
                    .noneMatch(value -> value.equals("<dummy>"));

        } catch (IOException | NullPointerException e) {
            configured = false;
        } finally {
            isTwitter4jConfigured = configured;
        }
    }

    /**
     * Singleton accessor
     * @return singleton instance
     */
    public static TweetStreamFactory getInstance() {
        return instance;
    }

    /**
     * Determine if Twitter4j is configured correctly
     */
    public final boolean isOnlineAvailable() {
        return isTwitter4jConfigured;
    }

    /**
     * Get a new stream generator
     * if Twitter4j is not configured the offline source is used
     * @param tweetSource indicator which source of Tweets to use
     */
    public final TweetStreamGenerator getStreamGenerator(TweetSource tweetSource) {
        if (tweetSource == TweetSource.ONLINE && isTwitter4jConfigured) {
            return onlineTweetStreamGenerator;
        }
        /* TODO return offline source generator */
        throw new NotImplementedException("TweetStreamFactory.getTweetsStream() is not implemented yet");
    }

    /**
     * Get a new stream generator from offline source
     */
    public final TweetStreamGenerator getStreamGenerator() {
        return getStreamGenerator(TweetSource.OFFLINE);
    }
}
