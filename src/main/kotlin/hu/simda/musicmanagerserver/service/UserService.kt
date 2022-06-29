package hu.simda.musicmanagerserver.service

import hu.simda.musicmanagerserver.dao.UserRepository
import hu.simda.musicmanagerserver.domain.User
import hu.simda.musicmanagerserver.service.exceptions.UserNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService(
    @Autowired private val userRepository: UserRepository
) {

    fun getUser(username: String): User {
        val user = userRepository.findByUsername(username)
        return if (user.isPresent) user.get() else throw UserNotFoundException("User is not found with given username!")
    }

    fun createUser(user: User): User =
        userRepository.insert(User(name = user.name, username = user.username, password = user.password))

    fun updateUser(user: User): User = userRepository.save(user)

    fun deleteUser(id: String): User {
        TODO ("needs to be implemented")
    }
}