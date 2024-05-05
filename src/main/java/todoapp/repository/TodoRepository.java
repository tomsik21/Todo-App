package todoapp.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import todoapp.domain.Todo;

import java.util.List;

@Repository
public interface TodoRepository extends PagingAndSortingRepository<Todo,
        Long>{

    Todo save(Todo todo);

    void deleteById(long id);

    List<Object> findById(long id);

    Object findAll();
}