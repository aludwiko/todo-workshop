package com.workshop.todo.api.http;

import com.workshop.todo.application.TodoCompletion;
import com.workshop.todo.application.TodoCompletionStatus;
import com.workshop.todo.domain.Todo;
import com.workshop.todo.domain.TodoRepository;
import io.vavr.control.Try;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/todo")
public class TodosController {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private TodoCompletion todoCompletion;

  private TodoRepository todoRepository;

  @Autowired
  public TodosController(final TodoRepository todoRepository, final TodoCompletion todoCompletion) {
    this.todoRepository = todoRepository;
    this.todoCompletion = todoCompletion;
  }

  @RequestMapping(method = RequestMethod.GET, produces = "application/json")
  public ResponseEntity<Iterable<Todo>> todosList() {
    return new ResponseEntity<>(todoRepository.getTodos(), HttpStatus.OK);
  }

  @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
  public ResponseEntity createTodo(@RequestBody CreateTodoRequest createTodoRequest) {
    Todo newTodo = new Todo(
        UUID.randomUUID(), createTodoRequest.getDescription(), createTodoRequest.getDeadline().map(date -> LocalDateTime
        .ofInstant(date.toInstant(), ZoneId.systemDefault())));
    Try<UUID> tryUuid = todoRepository.save(newTodo);

    if (tryUuid.isSuccess()) {
      return new ResponseEntity(new TodoCreatedResponse(tryUuid.get().toString()));
    }

    return new ResponseEntity(HttpStatus.NOT_FOUND);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
  public ResponseEntity todo(@PathVariable("id") String id) {
    Optional<Todo> optionalTodo = todoRepository.get(UUID.fromString(id));

    return optionalTodo.<ResponseEntity>map(todo -> new ResponseEntity<>(todo, HttpStatus.OK))
        .orElseGet(() -> new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND));

  }

  @RequestMapping(value = "/{id}", method = RequestMethod.PATCH, consumes = "application/json")
  public ResponseEntity<HttpStatus> patchTodo(@PathVariable("id") String id, @RequestBody PatchTodoRequest patchTodoRequest) {
    UUID uuid = UUID.fromString(id);
    TodoCompletionStatus status = TodoCompletionStatus.NOT_UPDATED;

    if (patchTodoRequest.isCompleted()) {
      status  = this.todoCompletion.markCompleted(uuid);
    }

    return new ResponseEntity<HttpStatus>( status != TodoCompletionStatus.NOT_EXIST ? HttpStatus.OK : HttpStatus.NOT_FOUND);
  }
}
