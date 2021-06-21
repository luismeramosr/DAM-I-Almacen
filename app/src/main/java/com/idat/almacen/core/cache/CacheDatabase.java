package com.idat.almacen.core.cache;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.idat.almacen.core.cache.models.AuthCache;
import com.idat.almacen.core.cache.repositories.AuthCacheDao;

@Database(entities = {AuthCache.class}, version = 1)
public abstract class CacheDatabase extends RoomDatabase {
    private static final String DB_NAME = "CacheDB";

    private static CacheDatabase instance;

    public static CacheDatabase getInstance(final Context context) {
        if (instance==null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    CacheDatabase.class,
                    DB_NAME
            ).fallbackToDestructiveMigration().build();
        }
        return instance;
    }

    // Repositories
    public abstract AuthCacheDao authCacheRepository();
}
