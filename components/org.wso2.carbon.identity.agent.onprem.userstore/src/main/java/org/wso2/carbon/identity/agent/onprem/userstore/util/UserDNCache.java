package org.wso2.carbon.identity.agent.onprem.userstore.util;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.agent.onprem.userstore.constant.CommonConstants;

import java.util.concurrent.TimeUnit;

/**
 * This is used to cache users' LDAP DNs.
 */
public class UserDNCache {
    public boolean isEnabled = true;
    private static Cache<String, Object> cache;
    private static UserDNCache userDNCache = new UserDNCache();
    private static Log log = LogFactory.getLog(UserDNCache.class);

    private UserDNCache() {
            cache = CacheBuilder.newBuilder()
                    .maximumSize(CommonConstants.MAX_USER_DN_CACHE_SIZE)
                    .expireAfterAccess(CommonConstants.MAX_USER_DN_CACHE_TIMEOUT, TimeUnit.MINUTES).build();
    }

    /**
     * Gets a new instance of UserCache.
     *
     * @return A new instance of UserCache.
     */
    public static UserDNCache getInstance() {
        return userDNCache;
    }

    /**
     * Getting existing cache if the cache available, else returns a newly created cache.
     * This logic handles by javax.cache implementation
     */
    private Cache<String, Object> getUserDNCache() {
        return cache;
    }

    /**
     * Avoiding NullPointerException when the cache is null.
     *
     * @return boolean whether given cache is null
     */
    private boolean isCacheNull(Cache<String, Object> cache) {
        if (cache == null) {
            if (log.isDebugEnabled()) {
                StackTraceElement[] elemets = Thread.currentThread()
                        .getStackTrace();
                StringBuilder traceString = new StringBuilder("");
                for (int i = 1; i < elemets.length; ++i) {
                    traceString.append(elemets[i]
                            + System.getProperty("line.separator"));
                }
                log.debug("UserDN_CACHE doesn't exist in CacheManager:\n"
                        + traceString);
            }
            return true;
        }
        return false;
    }

    /**
     * Adds an entry to the cache. DN of the given user is cached.
     *
     * @param userName     username of the user whose DN is cached
     */
    public void addToCache(String userName,
                           Object userDN) {

        // Element already in the cache. Remove it first
        clearCacheEntry(userName);

        Cache<String, Object> cache = this.getUserDNCache();
        // Check for null
        if (isCacheNull(cache)) {
            return;
        }
        cache.put(userName, userDN);
    }

    /**
     * Get the DN of the given user if available.
     *
     * @param userName   Username of the user.
     */
    public Object get(String userName) {

        Cache<String, Object> cache = this.getUserDNCache();
        // check for null
        if (isCacheNull(cache)) {
            return null;
        }
        return cache.getIfPresent(userName);
    }

    /**
     * Clears a given cache entry.
     *
     * @param username   User name to remove the cache key.
     */
    public void clearCacheEntry(String username) {
        Cache<String, Object> cache = this.getUserDNCache();
        // check for null
        if (isCacheNull(cache)) {
            return;
        }
        cache.invalidate(username);
    }

    /**
     * Disable cache completely. Can not enable the cache again.
     */
    public static void disableCache() {
        cache = null;
    }
}
