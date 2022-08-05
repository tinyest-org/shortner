package org.tyniest;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

import io.quarkus.arc.DefaultBean;
import io.smallrye.mutiny.Uni;

@DefaultBean
@ApplicationScoped
public class InMemory implements Store {

    private final Map<String, String> data;

    public InMemory() {
        this.data = new HashMap<>();
    }

    @Override
    public Uni<String> get(String key) {
        return Uni.createFrom().item(data.get(key));
    }

    @Override
    public Uni<Void> set(String key, String value) {
        return Uni.createFrom().item(data.put(key, value)).replaceWithVoid();
    }
}
