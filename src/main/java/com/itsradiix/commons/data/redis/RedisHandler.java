package com.itsradiix.commons.data.redis;

import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;

import java.util.List;
import java.util.UUID;

public class RedisHandler {
	public static final RedisAccess redisAccess = RedisAccess.INSTANCE;
	public static final RedissonClient redissonClient = redisAccess.getRedissonClient();

	/* public static void loadPlayersIntoRedisFromDB(){
		List<Account> allAccounts = Account.getAccountDAO().find().asList();
		for (Account account : allAccounts){
			addPlayerToRedis(account.getNameManager().getName(), account.getUniqueID());
		}
	} */

	public static UUID getPlayerInRedis(String name){
		RMap<String, String> map = getPlayersMap();
		String uuid = map.get(name);
		if (uuid == null){
			return null;
		} else {
			return UUID.fromString(uuid);
		}
	}

	public static void removeOnlinePlayerFromRedis(String name){
		getPlayersMap().remove(name);
	}

	public static void addPlayerToRedis(String name, UUID uuid){
		getPlayersMap().put(name, uuid.toString());
	}

	public static RMap<String, String> getPlayersMap() {
		return redissonClient.getMap("playersMap");
	}
}

