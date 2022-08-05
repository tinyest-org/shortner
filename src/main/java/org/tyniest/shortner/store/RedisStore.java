package org.tyniest.shortner.store;

import javax.enterprise.context.ApplicationScoped;

import io.quarkus.arc.properties.IfBuildProperty;
import io.quarkus.redis.datasource.ReactiveRedisDataSource;
import io.quarkus.redis.datasource.keys.ReactiveKeyCommands;
import io.quarkus.redis.datasource.string.ReactiveStringCommands;
import io.smallrye.mutiny.Uni;

@IfBuildProperty(name = "shortner.store", stringValue = "redis") // bean will only exist if redis is set
@ApplicationScoped
public class RedisStore implements Store {
    
    private final ReactiveKeyCommands<String> keyCommands;
    private final ReactiveStringCommands<String, String> valueCommands;

    public RedisStore(final ReactiveRedisDataSource reactive) {
        this.valueCommands = reactive.string(String.class);
        this.keyCommands = reactive.key();
    }

    @Override
    public Uni<String> get(String key) {
        return this.valueCommands.get(key);
    }

    @Override
    public Uni<Void> set(String key, String value, long expiration) {
        if (expiration == 0l) {
            return this.valueCommands.set(key, value);
        }
        return this.valueCommands.setex(key, expiration, value);
    }

    @Override
    public Uni<Void> remove(String key) {
        return this.keyCommands.del(key).replaceWithVoid();
    }
}
