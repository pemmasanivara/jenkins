package com.llk.admin.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.llk.admin.model.Party;

public interface PartyRepository extends JpaRepository<Party, Long>{


	Party findByPartyId(Integer partyId);

	Party findByFirstName(String firstName);
	
	Party findByLastName(String lastName);

	//Party findByRoleName(String roleName);

	Party findByEmail(String email);


}
 