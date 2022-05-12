package hu.simda.musicmanagerserver.controller

import com.expediagroup.graphql.server.operations.Query
import hu.simda.musicmanagerserver.domain.Rank
import org.springframework.stereotype.Controller

@Controller
class RankQuery : Query {
    fun getRanks(): List<Rank> = Rank.values().toList()
}