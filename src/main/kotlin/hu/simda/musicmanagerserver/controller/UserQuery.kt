package hu.simda.musicmanagerserver.controller

import com.expediagroup.graphql.server.operations.Query
import hu.simda.musicmanagerserver.domain.User
import hu.simda.musicmanagerserver.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller

@Controller
class UserQuery(@Autowired private val userService: UserService) : Query {

    fun getUser(username: String): User = userService.getUser(username)
}