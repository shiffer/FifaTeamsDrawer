package com.infi.teamdrawer.utils;

import android.text.TextUtils;

import org.json.JSONArray;

import java.lang.ref.Reference;
import java.util.Collection;
import java.util.Map;

@SuppressWarnings("unused")
public final class JavaUtils {

	private static final String TAG = "JavaUtils";

	public static final int UNSET_INT_VALUE = -1;
	public static final long UNSET_LONG_VALUE = -1L;
	public static final float UNSET_FLOAT_VALUE = -1f;
	public static final double UNSET_DOUBLE_VALUE = -1d;

	public static final String LINE_SEPARATOR = System.getProperty("line.separator");

	private JavaUtils() {}

	/**
	 * Compares two {@code int} values numerically.
	 * The value returned is identical to what would be returned by:
	 * <pre>
	 *    Integer.valueOf(x).compareTo(Integer.valueOf(y))
	 * </pre>
	 *
	 * @param  x the first {@code int} to compare
	 * @param  y the second {@code int} to compare
	 * @return the value {@code 0} if {@code x == y};
	 *         a value less than {@code 0} if {@code x < y}; and
	 *         a value greater than {@code 0} if {@code x > y}
	 * @since 1.7
	 */
	public static int compare(int x, int y) {
		return (x < y) ? -1 : ((x == y) ? 0 : 1);
	}

	/**
	 * Compares two {@code long} values numerically.
	 * The value returned is identical to what would be returned by:
	 * <pre>
	 *    Long.valueOf(x).compareTo(Long.valueOf(y))
	 * </pre>
	 *
	 * @param  x the first {@code long} to compare
	 * @param  y the second {@code long} to compare
	 * @return the value {@code 0} if {@code x == y};
	 *         a value less than {@code 0} if {@code x < y}; and
	 *         a value greater than {@code 0} if {@code x > y}
	 * @since 1.7
	 */
	public static long compare(long x, long y) {
		return (x < y) ? -1 : ((x == y) ? 0 : 1);
	}

	/**
	 * Null-safe equivalent of {@code a.equals(b)}.
	 */
	public static boolean equals(Object a, Object b) {
		return (a == null) ? (b == null) : a.equals(b);
	}

	/**
	 * Equivalent of {@link TextUtils#equals(CharSequence, CharSequence)}.
	 * Will return true if both CharSequences are empty or null
	 *
	 */
	public static boolean equalsIfEmptyOrNul(CharSequence a, CharSequence b) {
		return isEmpty(a) ? isEmpty(b) : a.equals(b);
	}

	/**
	 * Null-safe equivalent of {@link String#equalsIgnoreCase(String) equalsIgnoreCase}.
	 */
	public static boolean equalsIgnoreCase(String a, String b) {
		return (a == null) ? (b == null) : a.equalsIgnoreCase(b);
	}

	/**
	 * Checking that string is not null, not empty, and not white space only using standard Java classes.
	 *
	 * @param string
	 *            a String to be checked for not null, not empty, and not white space only.
	 * @return {@code true} if provided string is not null, is not empty, and has at least one character that is not considered white space.
	 */
	public static boolean isNotNullNotEmptyNotWhiteSpaceOnly(final String string) {
		return string != null && !string.isEmpty() && !string.trim().isEmpty() && !string.equalsIgnoreCase("null");
	}

	/**
	 * Checking if that string is null, empty or white space only using standard Java classes.
	 *
	 * @param string a String to be checked for if null, empty or white space only.
	 * @return {@code true} if provided string is null, empty or white space only, false otherwise.
	 */
	public static boolean isNullEmptyOrWhiteSpaceOnly(final String string) {
		return !isNotNullNotEmptyNotWhiteSpaceOnly(string);
	}

	/**
	 * Checking that the collection is not null, not empty
	 *
	 * @param collection
	 *            a Collection to be checked for not null, not empty
	 * @return {@code true} if provided collection is not null, is not empty.
	 */
	public static <T> boolean isNotNullNotEmpty(final Collection<T> collection) {
		return collection != null && !collection.isEmpty();
	}

	/**
	 * Checking that the collection is not null, not empty
	 *
	 * @param collection
	 *            a Collection to be checked for not null, not empty
	 * @return {@code true} if provided collection is not null, is not empty.
	 */
	public static <T> boolean isNotNullNotDeepEmpty(final Collection<T> collection) {
		if(collection != null && !collection.isEmpty()){
			for (T t : collection) {
				if(t != null){
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Checking that the array is not null, not empty
	 *
	 * @param array an array to be checked for not null, not empty
	 * @return {@code true} if provided array is not null, is not empty.
	 */
	public static <T> boolean isNotNullNotEmpty(final T[] array) {
		return array != null && array.length > 0;
	}

	/**
	 * Checking that the int array is not null, not empty
	 *
	 * @param array an int array to be checked for not null, not empty
	 * @return {@code true} if provided array is not null, is not empty.
	 */
	public static boolean isNotNullNotEmpty(final int[] array) {
		return array != null && array.length > 0;
	}

	/**
	 * Checking that the long array is not null, not empty
	 *
	 * @param array a long array to be checked for not null, not empty
	 * @return {@code true} if provided array is not null, is not empty.
	 */
	public static boolean isNotNullNotEmpty(final long[] array) {
		return array != null && array.length > 0;
	}

	public static boolean isNotNullNotEmpty(final byte[] array) {
		return array != null && array.length > 0;
	}

	/**
	 * Checking that the float array is not null, not empty
	 *
	 * @param array a float array to be checked for not null, not empty
	 * @return {@code true} if provided array is not null, is not empty.
	 */
	public static boolean isNotNullNotEmpty(final float[] array) {
		return array != null && array.length > 0;
	}

	/**
	 * Checking that the double array is not null, not empty
	 *
	 * @param array a double array to be checked for not null, not empty
	 * @return {@code true} if provided array is not null, is not empty.
	 */
	public static boolean isNotNullNotEmpty(final double[] array) {
		return array != null && array.length > 0;
	}

	/**
	 * Checking that the boolean array is not null, not empty
	 *
	 * @param array an boolean array to be checked for not null, not empty
	 * @return {@code true} if provided array is not null, is not empty.
	 */
	public static boolean isNotNullNotEmpty(final boolean[] array) {
		return array != null && array.length > 0;
	}

	/**
	 * Checking that the JSONArray is not null, not empty
	 *
	 * @param jsonArray a JSONArray to be checked for not null, not empty
	 * @return {@code true} if provided jsonArray is not null, is not empty.
	 */
	public static boolean isNotNullNotEmpty(final JSONArray jsonArray) {
		return jsonArray != null && jsonArray.length() > 0;
	}

	/**
	 * Checking that the map is not null, not empty
	 *
	 * @param map a map to be checked for not null, not empty
	 * @return {@code true} if provided map is not null, is not empty.
	 */
	public static <K, V> boolean isNotNullNotEmpty(final Map<K, V> map) {
		return map != null && !map.isEmpty();
	}

	public static boolean isEmpty(CharSequence cs) {
		return cs == null || cs.length() == 0;
	}

	public static boolean isNotEmpty(CharSequence cs) {
		return !isEmpty(cs);
	}

	public static <T> T getWeakRef(Reference<T> weakReference) {
		T object = null;
		if(weakReference != null){
			object = weakReference.get();
		}

		return object;
	}

}
