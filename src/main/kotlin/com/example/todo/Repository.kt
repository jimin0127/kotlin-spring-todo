package com.example.todo

import org.springframework.data.jpa.repository.JpaRepository

interface TodoRepository : JpaRepository<Todo, Long>{
    fun findAllByOrderByCreatedAtDesc(): List<Todo>
    fun findAllByStatusOrderByCreatedAtDesc(status: TodoStatus): List<Todo>
}
