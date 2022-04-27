package hu.simda.musicmanagerserver

import com.coxautodev.graphql.tools.SchemaParser
import javax.servlet.annotation.WebServlet
import graphql.kickstart.servlet.GraphQLHttpServlet
import graphql.kickstart.servlet.GraphQLConfiguration

@WebServlet(urlPatterns = ["/graphql"])
class GraphQLEndpoint : GraphQLHttpServlet() {
    override fun getConfiguration(): GraphQLConfiguration {
        return GraphQLConfiguration.with(
            SchemaParser.newParser()
                .file("schema.graphqls")
                .build()
                .makeExecutableSchema()
        ).build()
    }
}