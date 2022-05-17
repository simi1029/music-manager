package hu.simda.musicmanagerserver.dao

import hu.simda.musicmanagerserver.domain.Album
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.Aggregation
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.Optional

interface AlbumRepository : MongoRepository<Album, ObjectId> {

    @Aggregation(pipeline = [LOOKUP_ARTIST, UNWIND_ARTIST, LOOKUP_SONGS])
    fun getAllAlbums(): List<Album>

    @Aggregation(
        pipeline = [
            "{'\$match': { '_id': ?0 }}",
            LOOKUP_ARTIST,
            UNWIND_ARTIST,
            LOOKUP_SONGS
        ]
    )
    fun getAlbum(id: ObjectId): Album

    @Aggregation(
        pipeline = [
            "{'\$match': { 'title': ?0 }}",
            LOOKUP_ARTIST,
            UNWIND_ARTIST,
            LOOKUP_SONGS
        ]
    )
    fun getAlbums(title: String): List<Album>

    @Aggregation(
        pipeline = [
            "{'\$match': { 'title': ?1, 'releaseDate': ?2 }}",
            LOOKUP_ARTIST,
            UNWIND_ARTIST,
            "{'\$match': { 'artist._id': ?0 }}",
            LOOKUP_SONGS
        ]
    )
    fun getAlbum(artistID: ObjectId, title: String, releaseDate: Int): Optional<Album>

    companion object {
        private const val LOOKUP_ARTIST =
            "{'\$lookup': { from: 'artists', localField: 'artists._id', foreignField: 'artist', as: 'artist' }}"
        private const val UNWIND_ARTIST = "{'\$unwind': { path: '\$artist' }}"
        private const val LOOKUP_SONGS_ARTIST =
            "{'\$lookup': { from: 'artists', localField: 'artists._id', foreignField: 'artist', as: 'artist' }}"
        private const val UNWIND_SONG_ARTIST = "{'\$unwind': { path: '\$artist' }}"

        private const val LOOKUP_SONGS =
            "{'\$lookup': { from: 'songs', localField: 'songs._id', foreignField: 'songs', as: 'songs', pipeline: [$LOOKUP_SONGS_ARTIST, $UNWIND_SONG_ARTIST] }}"
    }
}