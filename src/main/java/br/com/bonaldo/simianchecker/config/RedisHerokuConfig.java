package br.com.bonaldo.simianchecker.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import java.net.URI;
import java.net.URISyntaxException;

@Profile("heroku")
@Configuration
public class RedisHerokuConfig {

    @Value("${spring.redis.url:}")
    private String url;

    @Bean
    JedisConnectionFactory jedisConnectionFactory() throws URISyntaxException {

        URI uri = new URI(url);

        final RedisStandaloneConfiguration redisStandaloneConfiguration
                = new RedisStandaloneConfiguration(uri.getHost(), uri.getPort());
        redisStandaloneConfiguration.setPassword(RedisPassword.of(uri.getAuthority().split(":")[1]));

        return new JedisConnectionFactory(redisStandaloneConfiguration);
    }
}