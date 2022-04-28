package hu.simda.musicmanagerserver.dao

import com.expediagroup.graphql.generator.scalars.ID
import hu.simda.musicmanagerserver.domain.Album
import hu.simda.musicmanagerserver.domain.Artist
import hu.simda.musicmanagerserver.domain.Song

class AlbumRepository {
    object Artists {
        val PORCUPINE_TREE = Artist(ID("1"), "Porcupine Tree", "UK")
        val DREAM_THEATER = Artist(ID("2"), "Dream Theater", "USA")
    }

    private val albums: MutableList<Album> = mutableListOf(
        Album(
            ID("1"), Artists.PORCUPINE_TREE, "Fear of a Blank Planet", 2007,
            listOf(
                Song(ID("1"), Artists.PORCUPINE_TREE, "Fear of a Blank Planet", 448, null, null),
                Song(ID("2"), Artists.PORCUPINE_TREE, "My Ashes", 307, null, null),
                Song(ID("3"), Artists.PORCUPINE_TREE, "Anesthetize", 1062, null, null),
                Song(ID("4"), Artists.PORCUPINE_TREE, "Sentimental", 326, null, null),
                Song(ID("5"), Artists.PORCUPINE_TREE, "Way Out of Here", 457, null, null),
                Song(ID("6"), Artists.PORCUPINE_TREE, "Sleep Together", 448, null, null)
            ), null, null, null
        ),
        Album(
            ID("2"), Artists.DREAM_THEATER, "Fear of a Blank Planet", 2007,
            listOf(
                Song(ID("1"), Artists.DREAM_THEATER, "In the Presence of Enemies, Part I", 540, null, null),
                Song(ID("2"), Artists.DREAM_THEATER, "Forsaken", 336, null, null),
                Song(ID("3"), Artists.DREAM_THEATER, "Constant Motion", 415, null, null),
                Song(ID("4"), Artists.DREAM_THEATER, "The Dark Eternal Night", 533, null, null),
                Song(ID("5"), Artists.DREAM_THEATER, "Repentance", 643, null, null),
                Song(ID("6"), Artists.DREAM_THEATER, "Prophets of War", 360, null, null),
                Song(ID("7"), Artists.DREAM_THEATER, "The Ministry of Lost Souls", 897, null, null),
                Song(ID("8"), Artists.DREAM_THEATER, "In the Presence of Enemies, Part II", 998, null, null)
            ), null, null, null
        )
    )

    fun getAllAlbums(): MutableList<Album> {
        return albums
    }

    fun saveAlbum(album: Album) {
        albums.add(album)
    }
}