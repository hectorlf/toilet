package com.hectorlopezfernandez.blog.auth;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrincipalRepository extends MongoRepository<Principal, String> {

	Principal findByUsername(String username);

}
