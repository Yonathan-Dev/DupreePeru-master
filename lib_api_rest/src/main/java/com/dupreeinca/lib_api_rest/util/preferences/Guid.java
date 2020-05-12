package com.dupreeinca.lib_api_rest.util.preferences;

import java.util.UUID;

/**
 * Created by steveparrish on 3/30/16.
 */
public class Guid {
    private UUID uuid;
    private Guid() {
        uuid = UUID.randomUUID();
    }
    public static Guid NewGuid() {
        return new Guid();
    }
    public String ToString() {
        return uuid.toString();
    }
}
