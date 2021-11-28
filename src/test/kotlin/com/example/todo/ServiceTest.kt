package com.example.todo

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable


@SpringBootTest(classes = [SimpleTodoService::class])
class ServiceTest {

    @Autowired
    private lateinit var service: TodoService
    @MockBean
    private lateinit var todoRepository: TodoRepository

    @Test
    fun `완료된 TODO를 조회한다`() {
        val pageable = Pageable.ofSize(5)
        val todoList = listOf(Todo("test1"), Todo("test2"), Todo("test3"), Todo("test4"), Todo("test5"))
        todoList.map { it.setDone() }
        val result = PageImpl(todoList, pageable, 5)

        Mockito.`when`(todoRepository.findAllByStatusOrderByCreatedAtDesc(pageable, status = TodoStatus.DONE))
            .thenReturn(result)

        val todos = service.getTodoList(done = true, pageable)

        todos.map {
            assertThat(it.status).isEqualTo(TodoStatus.DONE)
        }
    }

    @Test
    fun `완료되지 않은 TODO를 조회한다`() {
        val pageable = Pageable.ofSize(5)
        val todoList = listOf(Todo("test1"), Todo("test2"), Todo("test3"))
        val result = PageImpl(todoList, pageable, 3)

        Mockito.`when`(todoRepository.findAllByStatusOrderByCreatedAtDesc(pageable, status = TodoStatus.NOT_DONE))
            .thenReturn(result)

        val todos = service.getTodoList(done = false, pageable)
        todos.map {
            assertThat(it.status).isEqualTo(TodoStatus.NOT_DONE)
        }
    }

    @Test
    fun `TODO를 생성한다`() {
        val createTodoDTO = CreateTodoDTO("test1")

        Mockito.`when`(
            todoRepository.save(argThat<Todo> { true })
        ).thenReturn(createTodoDTO.toEntity())

        assertThat(service.createTodo(createTodoDTO)).isEqualTo(Unit)
    }


    @Test
    fun `완료되지 않은 TODO를 완료된 상태로 변경한다`() {
        val todo = Todo("test1")

        service.updateToDone(1L)

        verify(todo).setDone()
    }

    @Test
    fun `id로 TODO를 삭제한다`() {
        service.deleteTodo(1)

        verify(todoRepository).deleteById(1L)
    }
}
