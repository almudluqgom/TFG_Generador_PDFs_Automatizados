package com.restpdf.javaclases.mainclases;

import jakarta.ws.rs.*;

@Path("/hello-world")
public class HelloResource {
    @GET
    //@Path("hellothere")
    @Produces("text/plain")
    public String hello() {
        return "Obi Wan Kenobi";
    }
}