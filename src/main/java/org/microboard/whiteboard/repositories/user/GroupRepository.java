package org.microboard.whiteboard.repositories.user;

import org.microboard.whiteboard.model.user.Group;
import org.springframework.data.repository.CrudRepository;

public interface GroupRepository extends CrudRepository<Group, Long> {

}
