package com.workshop.todo.domain;

import io.vavr.control.Try;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TodoRepository {

  Try<UUID> save(Todo todo);
  List<Todo> getTodos();
  Optional<Todo> get(UUID id);
}
