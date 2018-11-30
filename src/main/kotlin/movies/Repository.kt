package movies

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import org.springframework.transaction.annotation.Transactional
import javax.persistence.TypedQuery



//2
@Repository
interface Repository : CrudRepository<MovieEntity,Long>, RepositoryCustom {

    fun findAllByGenre(genre: String): Iterable<MovieEntity>

    fun findAllByName(name: String): Iterable<MovieEntity>

    fun findByName(name: String): MovieEntity

}

@Transactional
interface RepositoryCustom {

    fun createMovie(name: String, genre: String): Long

}

@Repository
@Transactional
class RepositoryImpl : RepositoryCustom {

    @Autowired
    private lateinit var em: EntityManager

    override fun createMovie(name: String, genre: String): Long {
        val entity = MovieEntity(name, genre)
        em.persist(entity)
        return entity.id!!
    }

}