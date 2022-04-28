package hu.simda.musicmanagerserver.domain

import com.expediagroup.graphql.generator.scalars.ID

data class Album(
    val id: ID,
    val artist: Artist,
    val title: String,
    val releaseDate: Int,
    val songs: List<Song>,
    val albumCover: String?,
    val rank: Rank?,
    val rating: Int?
)
