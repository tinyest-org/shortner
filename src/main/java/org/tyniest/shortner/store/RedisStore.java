package org.tyniest.shortner.store;

import javax.enterprise.context.ApplicationScoped;

import io.quarkus.arc.properties.IfBuildProperty;
import io.quarkus.redis.datasource.ReactiveRedisDataSource;
import io.quarkus.redis.datasource.keys.ReactiveKeyCommands;
import io.quarkus.redis.datasource.string.ReactiveStringCommands;
import io.smallrye.mutiny.Uni;

@IfBuildProperty(name = "shortner.store", stringValue = "redis") // bean will only exist if redis is set
@ApplicationScoped
public class RedisStore implements Store<String> {

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
    public Uni<String> set(String key, String value, long expiration, boolean reverse) {
        return reverse ? setWithReverse(key, value, expiration) : setNoReverse(key, value, expiration);
    }

    protected Uni<String> setNoReverse(String key, String value, long expiration) {
        if (expiration == 0l) {
            return this.valueCommands.set(key, value).replaceWith(() -> null);
        }
        return this.valueCommands.setex(key, expiration, value).replaceWith(() -> null);
    }

    protected Uni<String> setWithReverse(String key, String value, long expiration) {
        return this.get(value).chain(currentKey -> {
            if (currentKey == null) {
                return this.setNoReverse(key, value, expiration)
                        .chain(() -> this.setNoReverse(value, key, expiration));
            }
            return Uni.createFrom().item(currentKey);
        });
    }

    @Override
    public Uni<Void> remove(String key) {
        return this.keyCommands.del(key).replaceWithVoid();
    }
}
