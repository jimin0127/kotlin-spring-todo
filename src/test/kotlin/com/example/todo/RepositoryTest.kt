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
        val todo3 = Todo("test3")
        todo1.setDone()
        todo2.setDone()
        entityManager.persist(todo1)
        entityManager.persist(todo2)
        entityManager.persist(todo3)

        val pageable = Pageable.ofSize(5)

        val todos = repository.findAllByStatusOrderByCreatedAtDesc(pageable, TodoStatus.DONE)

        assertThat(todos.content.size).isEqualTo(2)
    }

    @Test
    fun `완료되지 않은 TODO를 최근 등록일 순서로 가져온다`() {
        val pageable = Pageable.ofSize(5)

        val todo1 = Todo("test1")
        val todo2 = Todo("test2")
        val todo3 = Todo("test3")
        todo1.setDone()
        todo2.setDone()
        entityManager.persist(todo1)
        entityManager.persist(todo2)
        entityManager.persist(todo3)

        val todos = repository.findAllByStatusOrderByCreatedAtDesc(pageable, TodoStatus.NOT_DONE)

        assertThat(todos.content.size).isEqualTo(1)
    }

    @Test
    fun `TODO를 저장한다`() {
        val todo1 = Todo("test1")
        entityManager.persist(todo1)

        repository.save(Todo("test2"))

        assertThat(entityManager.contains(Todo("test2")))
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
