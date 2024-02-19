package com.restpdf.javaclases.mainclases;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/hello-world")
public class HelloApplication extends Application {

    @Override
    public Set<Class<?>> getClasses(){
        HashSet hs = new HashSet<Class<?>>();
        hs.add(HelloResource.class);
        return hs;
    }
}