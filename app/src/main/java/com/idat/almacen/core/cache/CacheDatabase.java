package com.idat.almacen.core.cache;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.idat.almacen.core.app.Almacen;
import com.idat.almacen.core.cache.models.UserCache;
import com.idat.almacen.core.cache.repositories.UserCacheDao;

@Database(entities = {UserCache.class}, version = 4)
public abstract class CacheDatabase extends RoomDatabase {
    private static final String DB_NAME = "CacheDB";

    private static CacheDatabase instance;

    public static CacheDatabase getInstance() {
        if (instance==null) {
            instance = Room.databaseBuilder(
                    Almacen.getContext(),
                    CacheDatabase.class,
                    DB_NAME
            ).fallbackToDestructiveMigration().build();
        }
        return instance;
    }

    // Repositories
    public abstract UserCacheDao authCacheRepository();
}
