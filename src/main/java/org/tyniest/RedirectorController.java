package org.tyniest;

import java.net.URI;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.reactive.server.jaxrs.ResponseBuilderImpl;

import io.smallrye.mutiny.Uni;

@Path("/g")
public class RedirectorController {

    @Inject
    Store store;

    @GET
    @Path("/{key}")
    public Uni<Response> getKey(@PathParam("key") final String key) {
        return store.get(key)
                .map(k -> new ResponseBuilderImpl().status(301).location(URI.create(k)).build());
    }
}