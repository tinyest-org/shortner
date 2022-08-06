package org.tyniest.shortner.store;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

import io.quarkus.arc.DefaultBean;
import io.smallrye.mutiny.Uni;

@DefaultBean
@ApplicationScoped
public class InMemory implements Store<String> {

    private final Map<String, String> data;

    public InMemory() {
        this.data = new HashMap<>();
    }

    @Override
    public Uni<String> get(String key) {
        return Uni.createFrom().item(data.get(key));
    }

    /**
     * Does not handle expiration
     */
    @Override
    public Uni<String> set(String key, String value, final long expiration, final boolean reverse) {
        // 0 -> key never expire
        return Uni.createFrom().item(data.put(key, value));
    }

    @Override
    public Uni<Void> remove(String key) {
        return Uni.createFrom().item(data.remove(key)).replaceWithVoid();
    }
}
