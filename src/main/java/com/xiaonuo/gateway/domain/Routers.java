package com.xiaonuo.gateway.domain;


import java.util.Arrays;

public class Routers {
    private String id;
    private String uri;
    private String predicates;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getPredicates() {
        return predicates;
    }

    public void setPredicates(String predicates) {
        this.predicates = predicates;
    }

    @Override
    public String toString() {
        return "Route{" +
                "id='" + id + '\'' +
                ", uri='" + uri + '\'' +
                ", predicates=" + predicates +
                '}';
    }
}
