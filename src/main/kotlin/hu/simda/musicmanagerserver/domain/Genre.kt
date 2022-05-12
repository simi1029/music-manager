package hu.simda.musicmanagerserver.domain

data class Genre(
    val mainGenre: MainGenre,
    val subGenre: SubGenre? = null
)
