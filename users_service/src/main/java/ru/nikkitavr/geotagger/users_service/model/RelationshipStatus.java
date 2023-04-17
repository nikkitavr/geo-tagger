package ru.nikkitavr.geotagger.users_service.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public enum RelationshipStatus {
    INVITED, FRIEND, DECLINED;

    @Override
    public String toString() {
        return name().toLowerCase();
    }

}
