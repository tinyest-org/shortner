package org.tyniest;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import io.smallrye.mutiny.Uni;

@Path("/p")
public class HttpFacade {
    
    @Inject
    Store store;

    @POST
    public Uni<String> createKey(@QueryParam("url") final String url) {
        final var key = UuidHelper.getCompactUUID4();
        return store.set(key, url).replaceWith(key);
    }
}
