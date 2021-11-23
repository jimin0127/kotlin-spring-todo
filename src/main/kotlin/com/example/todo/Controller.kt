package com.example.todo

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*

@RestController
class TodoController(private val todoService: TodoService) {
    @GetMapping("/todo")
    fun getTodoList(
            @RequestParam("done") done: Boolean,
            pageable: Pageable
    ): Page<TodoDTO> {
        val todo: Page<Todo> = todoService.getTodoList(done, pageable)

        return todo.map {
            TodoDTO(
                it.id,
                it.todoContent,
                it.status,
                it.createdAt
            )
        }
    }

    @PostMapping("/todo")
    fun createTodo(@RequestBody todo: CreateTodoDTO) {
        todoService.createTodo(todo)
    }

    @PutMapping("/todo/{id}")
    fun updateToDone(@PathVariable id: Long) {
        todoService.updateToDone(id)
    }

    @DeleteMapping("/todo/{id}")
    fun deleteTodo(@PathVariable id: Long): Boolean {
        val deleted: Boolean = todoService.deleteTodo(id)

        return deleted
    }
}
