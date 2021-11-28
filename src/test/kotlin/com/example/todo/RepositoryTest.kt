package com.example.todo

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.Pageable
import javax.persistence.EntityManager
import javax.transaction.Transactional


@SpringBootTest
@Transactional
class RepositoryTest (
    @Autowired
    private val repository: TodoRepository,
    @Autowired
    private val entityManager: EntityManager,
){
    @Test
    fun `완료한 TODO를 최근 등록일 순서로 가져온다`() {
        val todo1 = Todo("test1")
        val todo2 = Todo("test2")
        todo1.setDone()
        todo2.setDone()
        entityManager.persist(todo1)
        entityManager.persist(todo2)

        val pageable = Pageable.ofSize(5)

        val todos = repository.findAllByStatusOrderByCreatedAtDesc(pageable, TodoStatus.DONE)

        // 조회된 리스트의 status가 모두 DONE 인지 확인합니다.
        todos.content.map { assertThat(it.status).isEqualTo(TodoStatus.DONE) }

        // 등록일을 기준으로 정렬되었는지 확인합니다.
        assertThat(todos.content[0].id).isEqualTo(2L)
        assertThat(todos.content[1].id).isEqualTo(1L)
    }

    @Test
    fun `완료되지 않은 TODO를 최근 등록일 순서로 가져온다`() {
        val pageable = Pageable.ofSize(5)

        val todo1 = Todo("test1")
        val todo2 = Todo("test2")
        val todo3 = Todo("test3")
        entityManager.persist(todo1)
        entityManager.persist(todo2)
        entityManager.persist(todo3)

        val todos = repository.findAllByStatusOrderByCreatedAtDesc(pageable, TodoStatus.NOT_DONE)

        // 조회된 리스트의 status가 모두 NOT_DONE 인지 확인합니다.
        todos.content.map { assertThat(it.status).isEqualTo(TodoStatus.NOT_DONE) }

        // 등록일을 기준으로 정렬되었는지 확인합니다.
        assertThat(todos.content[0].id).isEqualTo(3L)
        assertThat(todos.content[1].id).isEqualTo(2L)
        assertThat(todos.content[2].id).isEqualTo(1L)
    }

    @Test
    fun `TODO를 저장한다`() {
        repository.save(Todo("test1"))
        repository.save(Todo("test2"))

        val entityTodo1 = entityManager.find(Todo::class.java, 1L)
        val entityTodo2 = entityManager.find(Todo::class.java, 2L)

        assertThat(entityTodo1.todoContent).isEqualTo("test1")
        assertThat(entityTodo2.todoContent).isEqualTo("test2")
    }

    @Test
    fun `id로 TODO를 가져온다`() {
        val todo1 = Todo("test1")
        entityManager.persist(todo1)

        val todo = repository.findById(1).orElseThrow()

        assertThat(todo1).isEqualTo(todo)
    }

    @Test
    fun `id로 TODO를 삭제한다`() {
        val todo1 = Todo("test1")
        val todo2 = Todo("test2")

        entityManager.persist(todo1)
        entityManager.persist(todo2)

        repository.deleteById(1)

        val entityTodo1 = entityManager.find(Todo::class.java, 1L)
        val entityTodo2 = entityManager.find(Todo::class.java, 2L)

        assertThat(entityTodo1).isEqualTo(null)
        assertThat(entityTodo2).isEqualTo(todo2)
    }
}
