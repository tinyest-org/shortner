package org.tyniest.shortner.facade;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.tyniest.shortner.service.ShortnerService;

import io.smallrye.mutiny.Uni;

/**
 * This class is a only an http facade to add data
 */
@Path("/_")
public class HttpFacade {

    private final ShortnerService service;

    public HttpFacade(final ShortnerService service) {
        this.service = service;
    }

    @GET
    public String pong() {
        return "PONG";
    }

    /**
     * Returns null if URL is invalid
     * @param url
     * @return
     */
    @POST
    public Uni<String> createKey(@QueryParam("url") final String url) {
        return service.createKey(url).onFailure().recoverWithNull();
    }

    @DELETE
    public Uni<Void> deleteUrl(@QueryParam("url") final String url) {
        return service.deleteUrl(url);
    }
}
