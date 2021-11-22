package com.example.todo

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class TodoService (
    private var todoRepository : TodoRepository
){
    fun getTodoList(done: Boolean?): List<Todo>{
        if(done==null)
            return todoRepository.findAllByOrderByCreatedAtDesc()

        val todoStatus: TodoStatus = if (done) TodoStatus.DONE else TodoStatus.NOT_DONE
        return todoRepository.findAllByStatusOrderByCreatedAtDesc(todoStatus)
    }
}
