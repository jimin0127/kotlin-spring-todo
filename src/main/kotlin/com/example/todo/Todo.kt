package com.example.todo

import java.time.ZonedDateTime
import javax.persistence.*


@Entity
class Todo(
    todoContent: String
){
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0

    @Column(nullable = false)
    var todoContent: String = todoContent

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var status: TodoStatus = TodoStatus.NOT_DONE

    @Column(nullable = false)
    var createdAt: ZonedDateTime = ZonedDateTime.now()

    fun setDone(): Unit {
        status = TodoStatus.DONE
    }
}

enum class TodoStatus {
    NOT_DONE,
    DONE
}
