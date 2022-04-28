package hu.simda.musicmanagerserver.domain

import com.expediagroup.graphql.generator.scalars.ID

data class Artist(val id: ID, val name: String, val country: String)
