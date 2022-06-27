package org.j4el.com.repository;

import org.j4el.com.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    boolean existsTaskByTitle(String title);

}
