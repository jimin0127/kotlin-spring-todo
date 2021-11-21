package com.example.todo

import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class TodoService (
    private var todoRepository : TodoRepository
){
    fun todoList(entity: Todo){
        todoRepository.findAll()
    }
}