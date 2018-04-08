package com.workshop.todo.infractructure;

import com.workshop.todo.domain.Todo;
import com.workshop.todo.domain.TodoRepository;
import io.vavr.control.Try;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class InMemoryTodoRepository implements TodoRepository {

  final HashMap<UUID, Todo> store = new HashMap<>();

  @Override
  public Try<UUID> save(Todo todo) {
    return Try.of(() -> {
      if (!store.containsKey(todo.getId())) {
        throw new RuntimeException();
      }

      store.put(todo.getId(), todo);

      return todo.getId();
    });
  }

  @Override
  public List<Todo> getTodos() {
    return new ArrayList<>(store.values());
  }

  @Override
  public Optional<Todo> get(UUID id) {
    return Optional.of(store.get(id));
  }
}
