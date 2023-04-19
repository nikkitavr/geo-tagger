package ru.nikkita.vr.geotagger.authservice.model;

import jakarta.persistence.*;
import lombok.Data;
import ru.nikkita.vr.geotagger.authservice.listener.BaseEntityListener;

import java.time.LocalDateTime;
@EntityListeners(BaseEntityListener.class)
@MappedSuperclass
@Data
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id = 0L;

    @Column(name = "created_date")
    protected LocalDateTime createdDate;

    @Column(name = "updated_date")
    protected LocalDateTime updatedDate;
}
