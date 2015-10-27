package net.bafeimao.umbrella.web.repository;

import net.bafeimao.umbrella.support.repository.hibernate.HibernateRepositoryImpl;
import net.bafeimao.umbrella.web.domain.User;

import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl extends HibernateRepositoryImpl<User, Long> implements UserRepository {

	@Override
	public boolean checkExistenceByEmail(String email) {
		return count("where email=?", email) != 0;
	}

	@Override
	public boolean checkExistenceByName(String name) {
		return count("where name=?", name) != 0;
	}

}
