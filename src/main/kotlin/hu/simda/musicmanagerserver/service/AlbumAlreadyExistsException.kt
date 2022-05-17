package hu.simda.musicmanagerserver.service

import java.lang.RuntimeException

class AlbumAlreadyExistsException(message: String) : RuntimeException(message) {

}
