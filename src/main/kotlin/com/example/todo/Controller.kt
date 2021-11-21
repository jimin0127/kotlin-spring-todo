package com.example.todo

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HomeController {
    @GetMapping("/")
    fun home(): ResponseEntity<Unit> = ResponseEntity.ok().build()
}

@RestController
class TodoController(private val todoService: TodoService){
    @GetMapping("/todo")
    fun getTodoList(): ResponseEntity<Any>{
        var todos = todoService.getTodoList()

        return ResponseEntity.ok().body(todos)
    }
}