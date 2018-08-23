package com.silvalazaro.ws;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * Representa o servidor WebService
 *
 * @author Lazaro Silva
 */
@ApplicationPath("ws")
public class WS extends Application {

    private Set<Object> singletons = new HashSet<Object>();

    public WS() {
        singletons.add(new ExcecaoMapper());
        singletons.add(new WSChamado());
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
}
