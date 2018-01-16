package com.hectorlopezfernandez.blog.user;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
interface AuthorityRepository extends MongoRepository<Authority, String> {

	Authority findByAuthority(String authority);

}
