package com.example.todo

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class TodoService (
    private var todoRepository : TodoRepository
){
    fun getTodoList(): List<Todo>{
        return todoRepository.findAllByOrderByCreatedAtDesc()
    }
}
