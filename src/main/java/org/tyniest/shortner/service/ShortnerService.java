package org.tyniest.shortner.service;

import java.net.URI;

import javax.enterprise.context.ApplicationScoped;

import org.tyniest.shortner.exception.InvalidURLException;
import org.tyniest.shortner.store.Store;
import org.tyniest.shortner.utils.UuidHelper;

import io.smallrye.mutiny.Uni;

@ApplicationScoped
public class ShortnerService {
    
    private final Store<String> store;

    public ShortnerService(final Store<String> store) {
        this.store = store;
    }
    
    protected boolean validate(final String url) {
        try {
            URI.create(url);
            return true;
        } catch (NullPointerException | IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Return a shorter key for a given url
     * @param url
     * @return
     */
    public Uni<String> createKey(final String url) {
        if (!this.validate(url)) {
            return Uni.createFrom().failure(InvalidURLException::new);
        }
        final var key = UuidHelper.getCompactUUID4();
        return store.set(key, url, 0, true)
            .map(oldKey -> oldKey == null ? key : oldKey);
    }

    public Uni<Void> deleteUrl(final String url) {
        if (!this.validate(url)) {
            return Uni.createFrom().failure(InvalidURLException::new);
        }
        return store.remove(url);
    }
}
