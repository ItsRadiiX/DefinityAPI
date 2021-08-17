package com.itsradiix.commons.data.redis;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;

public class RedisAccess {
	public static RedisAccess INSTANCE;
	private final RedissonClient redissonClient;

	public RedisAccess(RedisCredentials credentials){
		INSTANCE = this;
		this.redissonClient = initRedisson(credentials);
	}

	public static void init(String ip, int port, String password){
		new RedisAccess(new RedisCredentials(ip, password, port));
	}

	public static void init(String ip, int port, String password, String clientName){
		new RedisAccess(new RedisCredentials(ip, password, port, clientName));
	}

	public static void close(){
		RedisAccess.INSTANCE.getRedissonClient().shutdown();
	}

	public RedissonClient initRedisson(RedisCredentials credentials){
		final Config config = new Config();

		config.setCodec(new JsonJacksonCodec());
		config.setThreads(2);
		config.setNettyThreads(2);
		config.useSingleServer().setAddress(credentials.toRedisURL())
				.setDatabase(3)
				.setClientName(credentials.getClientName());

		return Redisson.create(config);
	}

	public RedissonClient getRedissonClient() {
		return redissonClient;
	}
}
