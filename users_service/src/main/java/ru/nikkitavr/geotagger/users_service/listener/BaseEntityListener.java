package ru.nikkitavr.geotagger.users_service.listener;


import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.springframework.stereotype.Component;
import ru.nikkitavr.geotagger.users_service.model.BaseEntity;

import java.time.LocalDateTime;

@Component
public class BaseEntityListener {

    @PrePersist
    public void onCreate(BaseEntity baseEntity) {
        baseEntity.setCreatedDate(LocalDateTime.now());
        baseEntity.setUpdatedDate(LocalDateTime.now());
        // baseEntity.setCreatedUser(currentAuthentication().userDetails);
    }

    @PreUpdate
    public void onUpdate(BaseEntity baseEntity) {
        baseEntity.setUpdatedDate(LocalDateTime.now());
    }
}