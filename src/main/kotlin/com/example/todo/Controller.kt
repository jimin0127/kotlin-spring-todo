package com.example.todo

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class TodoController(private val todoService: TodoService) {
    @GetMapping("/todo")
    fun getTodoList(
            @RequestParam("done") done: Boolean,
            pageable: Pageable
    ): Page<Todo> {
        return todoService.getTodoList(done, pageable)
    }
}
