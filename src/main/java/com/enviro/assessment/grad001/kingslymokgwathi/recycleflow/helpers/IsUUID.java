package com.enviro.assessment.grad001.kingslymokgwathi.recycleflow.helpers;

import java.util.UUID;

public class IsUUID {
    public static boolean isUUID(String uuid) {
        try {
            UUID.fromString(uuid);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
