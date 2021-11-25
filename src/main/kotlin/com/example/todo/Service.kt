package com.example.todo

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import javax.transaction.Transactional

interface TodoService {
    fun getTodoList(done: Boolean, pageable: Pageable): Page<Todo>
    fun createTodo(todo: CreateTodoDTO)
    fun updateToDone(id: Long)
    fun deleteTodo(id: Long)
}

@Service
@Transactional
class SimpleTodoService (
    private val todoRepository : TodoRepository
) : TodoService {
    override fun getTodoList(
            done: Boolean,
            pageable: Pageable
    ): Page<Todo> {
        val todoStatus: TodoStatus = if (done) TodoStatus.DONE else TodoStatus.NOT_DONE
        return todoRepository.findAllByStatusOrderByCreatedAtDesc(pageable, todoStatus)
    }

    override fun createTodo(todo: CreateTodoDTO) {
        todoRepository.save(todo.toEntity())
    }

    override fun updateToDone(id: Long) {
        val todo: Todo = todoRepository.findById(id).orElseThrow()
        todo.setDone()
    }

    override fun deleteTodo(id: Long) {
        todoRepository.deleteById(id)
    }
}
