package hu.simda.musicmanagerserver.domain

import com.expediagroup.graphql.generator.scalars.ID

data class Song(
    val id: ID,
    val artist: Artist,
    val title: String,
    val length: Int,
    val rank: Rank?,
    val rating: Int?
)
