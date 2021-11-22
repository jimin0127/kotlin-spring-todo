package com.example.todo

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import javax.transaction.Transactional

interface TodoService {
    fun getTodoList(done: Boolean?, pageable: Pageable): Page<Todo>
}

@Service
@Transactional
class SimpleTodoService (
    private var todoRepository : TodoRepository
) : TodoService{
    override fun getTodoList(
            done: Boolean?,
            pageable: Pageable
    ): Page<Todo> {
        if(done==null)
            return todoRepository.findAllByOrderByCreatedAtDesc(pageable)

        val todoStatus: TodoStatus = if (done) TodoStatus.DONE else TodoStatus.NOT_DONE
        return todoRepository.findAllByStatusOrderByCreatedAtDesc(pageable, todoStatus)
    }
}
