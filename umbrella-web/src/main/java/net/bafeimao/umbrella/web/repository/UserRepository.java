package net.bafeimao.umbrella.web.repository;

import net.bafeimao.umbrella.support.repository.GenericRepository;
import net.bafeimao.umbrella.web.domain.User;

public interface UserRepository extends GenericRepository<User, Long> {
 
	boolean checkExistenceByName(String name);

	boolean checkExistenceByEmail(String email);
}
