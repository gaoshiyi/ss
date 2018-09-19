/**
 * 
 */
package com.sstc.hmis.shiro;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.util.CollectionUtils;

/**
  * <p> Title: RedisCache </p>
  * <p> Description:  TODO </p>
  * <p> Company: SSTC </p> 
  * @author  Qxiaoxiang
  * @date  2017年9月11日 下午7:44:35
   */
public class RedisCache<K, V> implements Cache<K, V> {

	private RedisManager cache;

	private String keyPrefix = "session:shiro";


	public RedisCache(RedisManager cache) {
		if (cache == null) {
			throw new IllegalArgumentException("Cache argument cannot be null.");
		}
		this.cache = cache;
	}

	public RedisCache(RedisManager cache, String prefix) {
		this(cache);
		this.keyPrefix = prefix;
	}


	/**
	 * 鑾峰緱byte[]鍨嬬殑key
	 * @param key
	 * @return
	 */
	private byte[] getByteKey(K key){
		if(key instanceof String){
			String preKey = this.keyPrefix + key;
    		return preKey.getBytes();
    	}else{
    		return SerializeUtils.serialize(key);
    	}
	}
 	
	@Override
	public V get(K key) throws CacheException {
		try {
			if (key == null) {
	            return null;
	        }else{
	        	byte[] rawValue = cache.get(getByteKey(key));
	        	@SuppressWarnings("unchecked")
				V value = (V)SerializeUtils.deserialize(rawValue);
	        	return value;
	        }
		} catch (Throwable t) {
			throw new CacheException(t);
		}

	}

	@Override
	public V put(K key, V value) throws CacheException {
		 try {
			 	cache.set(getByteKey(key), SerializeUtils.serialize(value));
	            return value;
	        } catch (Throwable t) {
	            throw new CacheException(t);
	        }
	}

	@Override
	public V remove(K key) throws CacheException {
		try {
            V previous = get(key);
            cache.del(getByteKey(key));
            return previous;
        } catch (Throwable t) {
            throw new CacheException(t);
        }
	}

	@Override
	public void clear() throws CacheException {
		try {
			Set<byte[]> keys = cache.keys(this.keyPrefix + "*");
			if( keys == null ){
				return;
			}
			for (byte[] key : keys){
				cache.del(key);
			}
        } catch (Throwable t) {
            throw new CacheException(t);
        }
	}

	@Override
	public int size() {
		try {
			Set<byte[]> keys = cache.keys(this.keyPrefix + "*");
            return keys != null ? keys.size():0;
        } catch (Throwable t) {
            throw new CacheException(t);
        }
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<K> keys() {
		try {
            Set<byte[]> keys = cache.keys(this.keyPrefix + "*");
            if (CollectionUtils.isEmpty(keys)) {
            	return Collections.emptySet();
            }else{
            	Set<K> newKeys = new HashSet<K>();
            	for(byte[] key:keys){
            		newKeys.add((K)key);
            	}
            	return newKeys;
            }
        } catch (Throwable t) {
            throw new CacheException(t);
        }
	}

	@Override
	public Collection<V> values() {
		try {
            Set<byte[]> keys = cache.keys(this.keyPrefix + "*");
            if (!CollectionUtils.isEmpty(keys)) {
                List<V> values = new ArrayList<V>(keys.size());
                for (byte[] key : keys) {
                    @SuppressWarnings("unchecked")
					V value = get((K)key);
                    if (value != null) {
                        values.add(value);
                    }
                }
                return Collections.unmodifiableList(values);
            } else {
                return Collections.emptyList();
            }
        } catch (Throwable t) {
            throw new CacheException(t);
        }
	}
}
