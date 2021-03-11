package com.llk.user.dao;

import org.springframework.data.repository.CrudRepository;

import com.llk.user.model.Party;

public interface UserRepository extends CrudRepository<Party, Long>,UserRepositoryCustom {
	Party findByEmail(String email);
}
