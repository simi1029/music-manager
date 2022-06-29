package hu.simda.musicmanagerserver.controller

import com.expediagroup.graphql.server.operations.Mutation
import hu.simda.musicmanagerserver.domain.User
import hu.simda.musicmanagerserver.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller

@Controller
class UserMutation(@Autowired private val userService: UserService) : Mutation {

    fun createUser(user: User) = userService.createUser(user)

    fun deleteUser(username: String) = userService.deleteUser(username)
}