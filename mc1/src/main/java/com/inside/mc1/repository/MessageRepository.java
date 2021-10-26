package com.inside.mc1.repository;

import com.inside.mc1.entity.MessageEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring JPA repository class for CRUD operations
 * over MessageEntity class on the DB level.
 */
@Repository
public interface MessageRepository extends CrudRepository<MessageEntity, Integer> {
}
