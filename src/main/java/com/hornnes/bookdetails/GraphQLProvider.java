package com.hornnes.bookdetails;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URL;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

@Component
public class GraphQLProvider {

    @Autowired
    GraphQLDataFetchers graphQLDataFetchers;

    private GraphQL graphQL;

    @PostConstruct
    public void init() throws IOException {
        URL url = Resources.getResource("schema.graphqls");
        String sdl = Resources.toString(url, Charsets.UTF_8);
        GraphQLSchema graphQLSchema = buildSchema(sdl);
        this.graphQL = GraphQL.newGraphQL(graphQLSchema).build();
    }



    private GraphQLSchema buildSchema(String sdl) {
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(sdl);
        RuntimeWiring runtimeWiring = buildWiring();
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
    }

    private RuntimeWiring buildWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type(newTypeWiring("Query")
                        .dataFetcher("bookById", graphQLDataFetchers.getBookByIdDataFetcher()))
                .type(newTypeWiring("Book")
                        .dataFetcher("author", graphQLDataFetchers.getAuthorDataFetcher()))
                .build();

        // This line is new: we need to register the additional DataFetcher
                        //.dataFetcher("pageCount", graphQLDataFetchers.getPageCountDataFetcher())).build();
    }

    @Bean
    public GraphQL graphQL() {
        return graphQL;
    }

    // Om propertiesene for GraphQL og Database ikke matcher:
    // Lets assume for a second we have a mismatch and the book Map has a key totalPages instead of pageCount.
    // This would result in a null value for pageCount for every book, because the PropertyDataFetcher can’t fetch the right value.
    // In order to fix that you would have to register a new DataFetcher for Book.pageCount which looks like this:

    //    public DataFetcher getPageCountDataFetcher() {
    //        return dataFetchingEnvironment -> {
    //            Map<String,String> book = dataFetchingEnvironment.getSource();
    //            return book.get("totalPages");
    //        };
    //    }
}
