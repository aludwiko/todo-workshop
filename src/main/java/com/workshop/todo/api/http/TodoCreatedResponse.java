package com.workshop.todo.api.http;

public class TodoCreatedResponse {
  private String todoId;

  public TodoCreatedResponse() {
  }

  public TodoCreatedResponse(final String todoId) {
    this.todoId = todoId;
  }

  public String getTodoId() {
    return todoId;
  }
}