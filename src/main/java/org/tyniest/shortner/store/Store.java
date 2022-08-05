package org.tyniest.shortner.store;

import io.smallrye.mutiny.Uni;

public interface Store {
    Uni<String> get(String key);
    Uni<Void> set(String key, String value, long expiration);
    Uni<Void> remove(String key);
}
