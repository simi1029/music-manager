package hu.simda.musicmanagerserver.dao

import hu.simda.musicmanagerserver.domain.User
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import java.util.Optional

interface UserRepository : MongoRepository<User, ObjectId> {
    @Query(value = "{ 'username' : ?0 }")
    fun findByUsername(username: String): Optional<User>
}