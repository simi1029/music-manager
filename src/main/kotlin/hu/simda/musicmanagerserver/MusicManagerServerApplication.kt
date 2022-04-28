package hu.simda.musicmanagerserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MusicManagerServerApplication

fun main(args: Array<String>) {
	runApplication<MusicManagerServerApplication>(*args)
}
