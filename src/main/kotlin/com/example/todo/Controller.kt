package com.example.todo

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
class HomeController {
    @GetMapping("/")
    fun home(): ResponseEntity<Unit> = ResponseEntity.ok().build()
}

@RestController
class TodoController(private val todoService: TodoService){
    @GetMapping("/todo")
    fun getTodoList(@RequestParam(required = false) done: Boolean? = null): ResponseEntity<Any>{
        val todos = todoService.getTodoList(done)

        return ResponseEntity.ok().body(todos)
    }
}
