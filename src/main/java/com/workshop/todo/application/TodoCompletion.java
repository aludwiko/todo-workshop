package com.workshop.todo.application;

import com.workshop.todo.domain.Todo;
import com.workshop.todo.domain.TodoRepository;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TodoCompletion {

  private TodoRepository todoRepository;

  @Autowired
  public TodoCompletion(final TodoRepository todoRepository) {
    this.todoRepository = todoRepository;
  }

  public TodoCompletionStatus markCompleted(UUID id) {
    return todoRepository.get(id)
        .map(todo -> {
          todo.complete();
          return TodoCompletionStatus.UPDATED;
        }).orElse(TodoCompletionStatus.NOT_EXIST);
  }
}
