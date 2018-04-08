package com.workshop.todo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class Todo {
  private UUID id;
  private String description;
  @JsonFormat(pattern = "yyy-mm-dd")
  private Optional<LocalDateTime> deadline;
  private boolean completed = false;

  public Todo(final UUID id, final String description, final Optional<LocalDateTime> deadline) {
    this.id = Objects.requireNonNull(id, "id");
    this.description = Objects.requireNonNull(description);
    this.deadline = Objects.requireNonNull(deadline);
  }

  public UUID getId() {
    return id;
  }

  public String getDescription() {
    return description;
  }

  public Optional<LocalDateTime> getDeadline() {
    return deadline;
  }

  public boolean isCompleted() {
    return completed;
  }

  public Todo complete() {
    Todo newTodo = new Todo(id, description, deadline);
    newTodo.completed = true;

    return newTodo;
  }
}
