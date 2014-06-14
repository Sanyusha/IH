package android.ih.news.api;

import java.io.IOException;
import java.util.List;

import android.util.JsonReader;
import android.util.JsonToken;

/**
 * A utility class for JSON stuff.
 * 
 * @author peter
 *
 */
public class JSONUtil {

	/**
	 * Sets the list of expectedItems with the contents of reader.
	 * @param reader the JSON reader
	 * @param expectedItems the list to be filled
	 * @param clazz the actual class of an item in the list
	 */
	public static <T extends JSONParsableObject, E extends T> void readObjectArray(JsonReader reader, List<T> expectedItems, Class<E> clazz) 
			throws IOException, InstantiationException, IllegalAccessException {
		
		reader.beginArray();
		while (reader.hasNext()) {
			E newInstance = clazz.newInstance();
			newInstance.parse(reader);
			expectedItems.add(newInstance);
		}
		reader.endArray();
	}

	public static int safeIntRead(JsonReader reader) throws IOException {
		int ret = 0;
		if (reader.peek() != JsonToken.NULL) {
			ret = reader.nextInt();
		} else {
			reader.nextNull();
		}
		return ret;
	}
	
	public static String safeStringRead(JsonReader reader, boolean setToEmptyIfNull) throws IOException {
		String ret = setToEmptyIfNull ? "" : null;
		if (reader.peek() != JsonToken.NULL) {
			ret = reader.nextString();
		} else {
			reader.nextNull();
		}
		return ret;
	}
	
	/**
	 * A generic interface for objects that can be parsed using a JSON reader.
	 * 
	 * @author peter
	 *
	 */
	public interface JSONParsableObject {

		public void parse(JsonReader reader) throws IOException, InstantiationException, IllegalAccessException;
	}
}
