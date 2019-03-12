package org.microboard.whiteboard.repositories.task;

import org.microboard.whiteboard.model.feedback.Feedback;
import org.springframework.data.repository.CrudRepository;

public interface FeedbackRepository extends CrudRepository<Feedback, Long> {

}
