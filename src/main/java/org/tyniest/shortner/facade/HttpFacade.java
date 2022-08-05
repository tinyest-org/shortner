package org.tyniest.shortner.facade;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.tyniest.shortner.store.Store;
import org.tyniest.shortner.utils.UuidHelper;

import io.smallrye.mutiny.Uni;

@Path("/p")
public class HttpFacade {
    
    private final Store store;

    public HttpFacade(final Store store) {
        this.store = store;
    }

    @POST
    public Uni<String> createKey(@QueryParam("url") final String url) {
        final var key = UuidHelper.getCompactUUID4();
        return store.set(key, url).replaceWith(key);
    }
}
