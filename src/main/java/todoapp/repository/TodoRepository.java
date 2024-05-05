package todoapp.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import todoapp.domain.Todo;

import java.util.List;
import java.util.Optional;

@Repository
public interface TodoRepository extends PagingAndSortingRepository<Todo,
        Long> {
    Optional<Todo> findByCompleted(String completed);

    Todo save(Todo todo);

    void deleteById(long id);

    List<Todo> findById(long id);

    List<Todo> findAll();
}