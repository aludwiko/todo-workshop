package com.workshop.todo.infractructure;

import static java.util.Optional.empty;
import static org.junit.Assert.*;

import com.workshop.todo.domain.Todo;
import com.workshop.todo.domain.TodoRepository;
import java.util.List;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class InMemoryTodoRepositoryTest {

  private TodoRepository repository = new InMemoryTodoRepository();

  @Test
  public void shouldSave() throws Exception {
    final UUID id = UUID.randomUUID();
    final Todo todo = new Todo(id, "desc", empty());

    repository.save(todo);

    final Todo todoFromRepo = repository.get(id).orElseThrow(Exception::new);
    assertEquals(todo, todoFromRepo);
  }

  @Test
  public void shouldReturnAllTodos() {
    Todo foo = new Todo(UUID.randomUUID(), "foo", empty());
    Todo bar = new Todo(UUID.randomUUID(), "bar", empty());

    repository.save(foo);
    repository.save(bar);

    List<Todo> todos = repository.getTodos();

    Assertions.assertThat(todos).containsOnly(foo, bar);
  }
}
