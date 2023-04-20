package ru.nikkitavr.geotagger.users_service.model;

import jakarta.persistence.*;
import lombok.Data;
import ru.nikkitavr.geotagger.users_service.listener.BaseEntityListener;

import java.time.LocalDateTime;

@EntityListeners(BaseEntityListener.class)
@MappedSuperclass
@Data
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id = 0L;

    @Column(name = "CREATED_DATE")
    protected LocalDateTime createdDate;

    @Column(name = "UPDATED_DATE")
    protected LocalDateTime updatedDate;

}