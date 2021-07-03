package ohm.softa.a13.utils;

import java.util.function.Supplier;

/**
 * Utility class for functional extensions
 *
 * @author Peter Kurfer
 */
public abstract class FunctionalUtils {
	private FunctionalUtils() {

	}

	/**
	 * Functional try-with-resources
	 *
	 * @param resourceSupplier supplier for the AutoCloseable resource
	 * @param consumer         function that consumes the resource and returns a value
	 * @param fallbackSupplier supplier for a fallback value
	 * @param <T>              Type of the result
	 * @param <R>              Type of the AutoCloseable resource
	 * @return instance of T supplied by the consumer or fallback value if exception occurred
	 */
	public static <T, R extends AutoCloseable> T tryWithResources(
		ExceptionalResourceSupplier<R> resourceSupplier,
		ExceptionalFunction<R, T> consumer,
		Supplier<T> fallbackSupplier
	) {
		try (R resource = resourceSupplier.get()) {
			return consumer.apply(resource);
		} catch (Exception e) {
			return fallbackSupplier.get();
		}
	}

	/**
	 * Special supplier that returns an instance of AutoCloseable
	 *
	 * @param <R> actual, inherited type of AutoCloseable
	 */
	@FunctionalInterface
	public interface ExceptionalResourceSupplier<R extends AutoCloseable> {

		/**
		 * Supplier method - may throw an exception
		 *
		 * @return AutoCloseable instance
		 * @throws Exception well...don't know why ¯\_(ツ)_/¯
		 */
		R get() throws Exception;
	}

	/**
	 * Function that allows lambda to throw exceptions without handling it
	 *
	 * @param <T> type of the input
	 * @param <R> type of out output
	 */
	public interface ExceptionalFunction<T, R> {

		/**
		 * Applying some logic to a genric input value
		 *
		 * @param input value to apply
		 * @throws Exception well...don't know why ¯\_(ツ)_/¯
		 */
		R apply(T input) throws Exception;
	}
}
