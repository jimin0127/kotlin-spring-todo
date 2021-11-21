package com.example.todo

import java.time.ZonedDateTime
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import javax.persistence.Enumerated
import javax.persistence.EnumType
import javax.persistence.Column


@Entity
@Table(name="todo")
class Todo(
    @Id
    var id: Long,
    todoContent: String

){
    @Column(nullable = false)
    val todoContent: String = todoContent

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var status: TodoStatus = TodoStatus.NOT_DONE

    @Column(nullable = false)
    var createdAt: ZonedDateTime = ZonedDateTime.now()
}

enum class TodoStatus {
    NOT_DONE,
    DONE
}
