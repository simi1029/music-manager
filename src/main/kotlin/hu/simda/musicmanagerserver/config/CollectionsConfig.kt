package hu.simda.musicmanagerserver.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.DependsOn
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.index.Index
import org.springframework.data.mongodb.core.indexOps
import javax.annotation.PostConstruct

@Configuration
@DependsOn("mongoTemplate")
class CollectionsConfig {

    @Autowired
    private lateinit var mongoTemplate: MongoTemplate

    @PostConstruct
    fun initIndexes() {
        mongoTemplate.indexOps("albums").ensureIndex(
                Index()
                    .named("album_artist_title_releasedate_idx")
                    .on("artist", DESC)
                    .on("title", DESC)
                    .on("releaseDate", DESC)
                    .unique()
        )
        mongoTemplate.indexOps("songs").ensureIndex(
            Index()
                .named("song_artist_title_idx")
                .on("artist", DESC)
                .on("title", DESC)
                .unique()
        )
    }

    companion object {
        private val DESC = Sort.Direction.DESC
    }
}