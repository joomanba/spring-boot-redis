package dev.simplesolution.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@EnableRedisRepositories(basePackages = "dev.simplesolution.repository")
public class RedisConfig {

	/**
	 * Redis Cluster 구성 설정
	 */
	@Autowired
	private RedisClusterConfigurationProperties clusterProperties;

	/**
	 * JedisPool관련 설정
	 * @return
	 */
	@Bean
	public JedisPoolConfig jedisPoolConfig() {
		return new JedisPoolConfig();
	}

	/**
	 * Redis Cluster 구성 설정
	 */
	@Bean
	public JedisConnectionFactory jedisConnectionFactory() {
		return new JedisConnectionFactory(new RedisClusterConfiguration(clusterProperties.getNodes()),jedisPoolConfig());
	}

//	@Bean
//	public JedisConnectionFactory jedisConnectionFactory(JedisPoolConfig jedisPoolConfig) {
//		return  new JedisConnectionFactory(new RedisClusterConfiguration(clusterProperties.getNodes()),jedisPoolConfig);
//	}

	/**
	 * RedisTemplate관련 설정
	 *
	 * -Thread-safety Bean
	 * @return
	 */
	@Bean(name="redisTemplate")
	public RedisTemplate redisTemplate() {

		RedisTemplate redisTemplate = new RedisTemplate<>();

		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new StringRedisSerializer());
		redisTemplate.setConnectionFactory(jedisConnectionFactory());

		return redisTemplate;

	}


	//	@Bean
//	public RedisTemplate<String, Object> redisTemplate() {
//		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//		redisTemplate.setConnectionFactory(jedisConnectionFactory());
//		return redisTemplate;
//	}

	
//	@Bean
//	public RedisStandaloneConfiguration redisStandaloneConfiguration() {
//		RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
//		config.setHostName("localhost");
//		config.setPort(6379);
////		config.setPort(7000);
//		return config;
//	}
	
//	@Bean
//	public JedisConnectionFactory jedisConnectionFactory() {
//		return  new JedisConnectionFactory(redisStandaloneConfiguration());
//	}
//
//	@Bean
//	public RedisTemplate<String, Object> redisTemplate() {
//		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//		redisTemplate.setConnectionFactory(jedisConnectionFactory());
//		return redisTemplate;
//	}
}