package hu.simda.musicmanagerserver.service.exceptions

import java.lang.RuntimeException

class AlbumAlreadyExistsException(message: String) : RuntimeException(message) {

}
