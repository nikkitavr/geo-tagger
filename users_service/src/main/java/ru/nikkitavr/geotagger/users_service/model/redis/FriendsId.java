package ru.nikkitavr.geotagger.users_service.model.redis;

import lombok.Data;

import java.util.List;

@Data
public class FriendsId {
    private List<Long> id;
}
