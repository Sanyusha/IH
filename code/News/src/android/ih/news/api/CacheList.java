package android.ih.news.api;

import java.util.ArrayList;
import java.util.List;

/**
 * A utility for a simple cache list - the items are ordered sequentially, no gaps.
 *  
 * @author peter
 */
public class CacheList {

	/**
	 * Limits to the fetch, so we use the cache as much as we can and limit network calls.
	 */
	public static class FetchLimit {
	
		private int start;
		private int limit;
		
		public FetchLimit(int start, int limit) {
			this.start = start;
			this.limit = limit;
		}
		public int getStart() {
			return start;
		}
		public void setStart(int start) {
			this.start = start;
		}
		public int getLimit() {
			return limit;
		}
		public void setLimit(int limit) {
			this.limit = limit;
		}
	}
	
	/**
	 * Calculates the need for the network fetch 
	 * @param forceUpdate whether to ignore the cache and forcefully update
	 * @param existingCache the existing cache list
	 * @param startIndex the requested starting point
	 * @param count the requested number of items
	 * @return a proper starting point and limit, according to the parameters
	 */
	public static FetchLimit getActualLimit(Boolean forceUpdate, List<?> existingCache, int startIndex, int count) {
		// ignore cache
		if (forceUpdate || existingCache == null) {
			existingCache = new ArrayList<Object>();
		}
		
		int endIndex = startIndex + count - 1;
		startIndex = existingCache.size(); // start fetching immediately after the end of cache
		count = Math.max(0, endIndex - existingCache.size() + 1); // fetch the exact number
		
		return new FetchLimit(startIndex, count);
	}

	/**
	 * Returns the part of the cache that is requested, might have less items then requested and even empty, depending on the actual cache.
	 * @param existingCache the cache
	 * @param startIndex requested starting index
	 * @param count the number of requested items
	 * @return the part of the cache that is requested, might have less items then requested and even empty, depending on the actual cache
	 */
	public static <T> List<T> getItemsFromCacheList(List<T> existingCache, int startIndex, int count) {
		return existingCache.subList(Math.max(0, Math.min(startIndex, existingCache.size() - 1)), 
				Math.min(startIndex + count, existingCache.size()));
	}

	/**
	 * Updates and reruns the cache
	 * @param existingCache the existing cache
	 * @param forceUpdate whether to update it
	 * @param newItems the new items to add to the cache (items are added to the end of the list)
	 * @return the modified cache, with the new items
	 */
	public static <T> List<T> fillCacheList(List<T> existingCache, Boolean forceUpdate, List<T> newItems) {
		// cache needs to be set
		if (forceUpdate || existingCache == null) {
			existingCache = new ArrayList<T>();
		}
		
		// the way we work with cache is to add the items to the end (forceUpdate deletes existing cache as well)
		existingCache.addAll(newItems);
		
		return existingCache;
	}
}
