package org.tyniest.shortner.store;

import io.smallrye.mutiny.Uni;

public interface Store<T> {
    Uni<T> get(String key);
    /**
     * 
     * @param key
     * @param value
     * @param expiration
     * @param reverse
     * @return previous value if existed or null
     */
    Uni<T> set(String key, T value, long expiration, boolean reverse);
    Uni<Void> remove(String key);
}
