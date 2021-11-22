package com.example.todo

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class HomeController {
    @GetMapping("/")
    fun home(): ResponseEntity<Unit> = ResponseEntity.ok().build()
}

@RestController
class TodoController(private val todoService: TodoService){
    @GetMapping("/todo")
    fun getTodoList(
            @RequestParam("done", required = false) done: Boolean? = null,
            pageable: Pageable
    ): ResponseEntity<Any>{
        val todos: Page<Todo> = todoService.getTodoList(done, pageable)

        return ResponseEntity.ok().body(todos)
    }
}
