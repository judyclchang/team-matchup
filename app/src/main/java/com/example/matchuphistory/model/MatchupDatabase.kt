package com.example.matchuphistory.model


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.matchuphistory.util.DATABASE_NAME

@Database(entities = [Matchup::class], version = 1)
@TypeConverters(Converters::class)
abstract class MatchupDatabase : RoomDatabase() {

    abstract fun matchupsDao(): MatchupsDao

    companion object {

        @Volatile
        private var INSTANCE: MatchupDatabase? = null

        fun getInstance(context: Context): MatchupDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        }

        private fun buildDatabase(context: Context): MatchupDatabase {
            return Room.databaseBuilder(context.applicationContext, MatchupDatabase::class.java, DATABASE_NAME)
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            val request = OneTimeWorkRequestBuilder<InitDatabaseWorker>().build()
                            WorkManager.getInstance(context).enqueue(request)
                        }
                    })
                    .build()
        }
    }
}