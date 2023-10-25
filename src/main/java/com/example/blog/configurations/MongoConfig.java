package com.example.blog.configurations;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
        import org.springframework.context.annotation.Configuration;
        import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Collection;
import java.util.Collections;

//@Configuration
//public class MongoConfig {
//    @Bean
//    public MongoTemplate mongoTemplate() {
//        // Configure your MongoDB connection here
//        return new MongoTemplate(yourMongoDbFactory);
//    }
//}


//@Configuration
//@EnableMongoRepositories(basePackages = "com.examples.blog.repositories")
//public class MongoConfig extends AbstractMongoClientConfiguration {
//
//        //private final List<Converter<?, ?>> converters = new ArrayList<Converter<?, ?>>();
//
//        @Override
//        protected String getDatabaseName() {
//                return "test";
//        }
//
//        @Override
//        public MongoClient mongoClient() {
//                final ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017/test");
//                final MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
//                        .applyConnectionString(connectionString)
//                        .build();
//                return MongoClients.create(mongoClientSettings);
//        }
//
//        @Override
//        public Collection<String> getMappingBasePackages() {
//                return Collections.singleton("org.spring.mongo.demo");
//        }
//}