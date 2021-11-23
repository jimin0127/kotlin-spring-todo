package com.example.todo

import java.time.ZonedDateTime

data class TodoDTO (
    val id: Long,
    val todoContent: String,
    val status: TodoStatus,
    val createdAt: ZonedDateTime
)
