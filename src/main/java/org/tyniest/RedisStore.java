package org.tyniest;

import javax.enterprise.context.ApplicationScoped;

import io.quarkus.redis.datasource.ReactiveRedisDataSource;
import io.quarkus.redis.datasource.string.ReactiveStringCommands;
import io.smallrye.mutiny.Uni;

@ApplicationScoped
public class RedisStore implements Store {
    
    private final ReactiveStringCommands<String, String> valueCommands;

    public RedisStore(final ReactiveRedisDataSource reactive) {
        this.valueCommands = reactive.string(String.class);
    }

    @Override
    public Uni<String> get(String key) {
        return this.valueCommands.get(key);
    }

    @Override
    public Uni<Void> set(String key, String value) {
        return this.valueCommands.set(key, value);
    }
}
