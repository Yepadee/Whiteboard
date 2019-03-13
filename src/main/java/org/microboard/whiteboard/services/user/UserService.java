package org.microboard.whiteboard.services.user;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.microboard.whiteboard.model.user.User;
import org.springframework.stereotype.Service;

@Service
public class UserService extends BaseUserService<User> {
	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public void detachUser(User u) {
		entityManager.detach(u);
	}

	@Transactional
	public void persistUser(User u) {
		entityManager.persist(u);
	}

	@Transactional
	public void changePerms(Long userId, String perms) {
		entityManager.createNamedQuery("User.changePerms").setParameter(1, perms).setParameter(2, userId)
				.executeUpdate();
	}
}
