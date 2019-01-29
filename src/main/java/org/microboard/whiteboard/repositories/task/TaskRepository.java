package org.microboard.whiteboard.repositories.task;

import org.microboard.whiteboard.model.task.Task;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<Task, Long> {

}
