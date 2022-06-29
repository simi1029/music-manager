package hu.simda.musicmanagerserver.service

import hu.simda.musicmanagerserver.dao.ArtistRepository
import hu.simda.musicmanagerserver.domain.Artist
import hu.simda.musicmanagerserver.domain.Genre
import hu.simda.musicmanagerserver.domain.MainGenre
import hu.simda.musicmanagerserver.service.exceptions.ArtistNotFoundException
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import org.bson.types.ObjectId
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.boot.test.context.SpringBootTest
import java.util.Optional

private const val ARTIST_ID = "626c002832268521b7fef07e"
private const val ARTIST_ID_2 = "626c002832268521b7fef07f"
private const val ARTIST_ID_3 = "626c002832268521b7fef07g"
private val ARTIST_OBJECT_ID = ObjectId(ARTIST_ID)
private const val ARTIST_ID_NOT_IN_DB = "123456789012345678901234"
private val ARTIST_OBJECT_ID_NOT_IN_DB = ObjectId(ARTIST_ID_NOT_IN_DB)
private const val ARTIST_NAME = "Test Artist"
private const val ARTIST_NAME_2 = "Test Artist 2"
private const val ARTIST_NAME_3 = "Test Artist 3"
private const val ARTIST_NAME_NOT_IN_DB = "Test Artist not in DB"
private const val COUNTRY = "Test country"
private val POP_GENRE = Genre(MainGenre.POP)
private const val POP_GENRE_TEXT = "POP"
private val METAL_GENRE = Genre(MainGenre.METAL)
private val ROCK_GENRE = Genre(MainGenre.ROCK)

@SpringBootTest
internal class ArtistServiceTest {

    private val artistRepository: ArtistRepository = mockk(relaxed = true)
    private val artistService = ArtistService(artistRepository)

    @Test
    fun `getAllArtists should return a list of artists`() {
        //given
        val expectedArtists = listOf(
            Artist(ARTIST_ID, ARTIST_NAME, COUNTRY, POP_GENRE),
            Artist(ARTIST_ID_2, ARTIST_NAME_2, COUNTRY, METAL_GENRE),
            Artist(ARTIST_ID_3, ARTIST_NAME_3, COUNTRY, ROCK_GENRE)
        )
        every { artistRepository.getAllArtists() } returns expectedArtists
        //when
        val actualArtists = artistService.getAllArtists()
        //then
        verify(exactly = 1) { artistRepository.getAllArtists() }
        assertEquals(expectedArtists, actualArtists)
    }

    @Test
    fun `getArtistByID should return artist when ID is DB`() {
        //given
        val expectedArtist = Artist(
            ARTIST_ID,
            ARTIST_NAME,
            COUNTRY,
            POP_GENRE
        )
        every { artistRepository.findById(ARTIST_OBJECT_ID) } returns Optional.of(expectedArtist)

        //when
        val actualArtist = artistService.getArtistByID(ARTIST_ID)

        //then
        verify(exactly = 1) { artistRepository.findById(ARTIST_OBJECT_ID) }
        assertEquals(expectedArtist, actualArtist)
    }

    @Test
    fun `getArtistByID should throw exception when ID is not found`() {
        //given
        every { artistRepository.findById(ARTIST_OBJECT_ID_NOT_IN_DB) } returns Optional.ofNullable(null)
        //when
        assertThrows<ArtistNotFoundException> {
            artistService.getArtistByID(ARTIST_ID_NOT_IN_DB)
        }
        //then
        verify(exactly = 1) { artistRepository.findById(ARTIST_OBJECT_ID_NOT_IN_DB) }
    }

    @Test
    fun `getArtistByName should return artist when name is in DB`() {
        //given
        val expectedArtist = Artist(
            ARTIST_ID,
            ARTIST_NAME_NOT_IN_DB,
            COUNTRY,
            POP_GENRE
        )
        every { artistRepository.getArtist(ARTIST_NAME_NOT_IN_DB) } returns Optional.of(expectedArtist)
        //when
        val actualArtist = artistService.getArtistByName(ARTIST_NAME_NOT_IN_DB)
        //then
        verify(exactly = 1) { artistRepository.getArtist(ARTIST_NAME_NOT_IN_DB) }
        assertEquals(expectedArtist, actualArtist)
    }

    @Test
    fun `getArtistByName should throw exception when name is not found`() {
        //given
        every { artistRepository.getArtist(ARTIST_NAME_NOT_IN_DB) } returns Optional.ofNullable(null)
        //when
        assertThrows<ArtistNotFoundException> {
            artistService.getArtistByName(ARTIST_NAME_NOT_IN_DB)
        }
        //then
        verify(exactly = 1) { artistRepository.getArtist(ARTIST_NAME_NOT_IN_DB) }
    }

    @Test
    fun `getArtistByGenre should return a list of artists`() {
        //given
        val expectedArtists = listOf(
            Artist(ARTIST_ID, ARTIST_NAME, COUNTRY, POP_GENRE),
            Artist(ARTIST_ID_2, ARTIST_NAME_2, COUNTRY, POP_GENRE)
        )
        every { artistRepository.getArtistsByGenre(POP_GENRE_TEXT) } returns expectedArtists
        //when
        val actualArtists = artistService.getArtistsByGenre(POP_GENRE)
        //then
        verify(exactly = 1) { artistRepository.getArtistsByGenre(POP_GENRE_TEXT) }
        assertEquals(expectedArtists, actualArtists)
    }

    @Test
    fun `getArtistByCountry should return a list of artists`() {
        //given
        val expectedArtists = listOf(
            Artist(ARTIST_ID, ARTIST_NAME, COUNTRY, POP_GENRE),
            Artist(ARTIST_ID_2, ARTIST_NAME_2, COUNTRY, POP_GENRE)
        )
        every { artistRepository.getArtistsByCountry(COUNTRY) } returns expectedArtists
        //when
        val actualArtists = artistService.getArtistsByCountry(COUNTRY)
        //then
        verify(exactly = 1) { artistRepository.getArtistsByCountry(COUNTRY) }
        assertEquals(expectedArtists, actualArtists)
    }

    @Test
    fun `createNewArtist should insert new artist to DB and return the inserted artist with new ID generated`() {
        //given
        val inputArtist = Artist("", ARTIST_NAME, COUNTRY, POP_GENRE)
        val expectedArtist = Artist(ARTIST_ID, ARTIST_NAME, COUNTRY, POP_GENRE)
        every { artistRepository.insert(any<Artist>()) } returns expectedArtist
        //when
        val actualArtist = artistService.createNewArtist(inputArtist)
        //then
        verify(exactly = 1) { artistRepository.insert(any<Artist>()) }
        assertEquals(expectedArtist, actualArtist)
    }

    @Test
    fun `updateArtist should update artist`() {
        //given
        val expectedArtist = Artist(ARTIST_ID, ARTIST_NAME, COUNTRY, POP_GENRE)
        every { artistRepository.save(expectedArtist) } returns expectedArtist
        //when
        val actualArtist = artistService.updateArtist(expectedArtist)
        //then
        verify(exactly = 1) { artistRepository.save(expectedArtist) }
        assertEquals(expectedArtist, actualArtist)
    }

    @Test
    fun `deleteArtist should delete artist and return 'Success' message`() {
        //given
        val expectedArtist = Artist(ARTIST_ID, ARTIST_NAME, COUNTRY, POP_GENRE)
        every { artistRepository.findById(ARTIST_OBJECT_ID) } returns Optional.of(expectedArtist)
        justRun { artistRepository.delete(expectedArtist) }
        //when
        val actualMessage = artistService.deleteArtist(ARTIST_ID)
        //then
        verify(exactly = 1) { artistRepository.delete(expectedArtist) }
        assertEquals("Success", actualMessage)
    }
}