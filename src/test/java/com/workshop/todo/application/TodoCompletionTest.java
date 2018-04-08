package com.workshop.todo.application;

import com.workshop.todo.domain.Todo;
import com.workshop.todo.domain.TodoRepository;
import com.workshop.todo.infractructure.InMemoryTodoRepository;
import java.util.Optional;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import static org.junit.Assert.*;

public class TodoCompletionTest {

  @Test
  public void markCompleted() throws Exception {
    TodoRepository todoRepository = new InMemoryTodoRepository();
    TodoCompletion todoCompletion = new com.workshop.todo.application.TodoCompletion(todoRepository);

    Todo foo = new Todo(UUID.randomUUID(), "foo", Optional.empty());
    todoRepository.save(foo);

    todoCompletion.markCompleted(foo.getId());

    Todo completedFoo = todoRepository.get(foo.getId()).orElseThrow(() -> new Exception());

    assertEquals(true, completedFoo.isCompleted());
  }

  @Test(expected = RuntimeException.class)
  public void testMarkCompletedThrowsExceptionForNotExistingTodo() throws Exception {
    TodoCompletion todoCompletion =
        new com.workshop.todo.application.TodoCompletion(new InMemoryTodoRepository());


    todoCompletion.markCompleted(UUID.randomUUID());
  }

}