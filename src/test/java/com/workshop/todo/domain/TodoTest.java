package com.workshop.todo.domain;

import static org.junit.Assert.*;

import java.util.Optional;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class TodoTest {

  @Test
  public void complete() throws Exception {
    Todo foo = new Todo(UUID.randomUUID(), "foo", Optional.empty());
    Todo completedFoo = foo.complete();

    Assertions.assertThat(foo.getId()).isEqualTo(completedFoo.getId());
    Assertions.assertThat(completedFoo.isCompleted()).isTrue();
  }
}