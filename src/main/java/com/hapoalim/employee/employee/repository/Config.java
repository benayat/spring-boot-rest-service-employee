package com.hapoalim.employee.employee.repository;

// import java.net.InetAddress;
import java.net.URI;
// import java.net.UnknownHostException;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
// import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
// import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
// import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
public class Config {
    @Autowired
    private Environment env;

    @Bean
    public RestHighLevelClient client() {
        final String stringUrl = env.getProperty("ELASTICSEARCH_URL");
        System.out.println(stringUrl);
        final URI uri = URI.create(stringUrl);

        String host = uri.getHost();
        ClientConfiguration clientConfiguration = ClientConfiguration.builder().connectedTo(host + ":9200").build();

        return RestClients.create(clientConfiguration).rest();
    }

    // @Bean
    // public ElasticsearchOperations elasticsearchTemplate() {
    // return new ElasticsearchRestTemplate(client());
    // }
}