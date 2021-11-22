package com.example.todo

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository


interface TodoRepository : JpaRepository<Todo, Long>{
    fun findAllByOrderByCreatedAtDesc(pageable: Pageable): Page<Todo>
    fun findAllByStatusOrderByCreatedAtDesc(pageable: Pageable, status: TodoStatus): Page<Todo>
}
