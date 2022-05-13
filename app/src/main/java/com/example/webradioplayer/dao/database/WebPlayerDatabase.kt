package com.example.webradioplayer.dao.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import java.util.concurrent.Executors

@Database(entities = [ListRadiostation::class], version = 1)

abstract class WebPlayerDatabase : RoomDatabase() {
    abstract fun radiostationDao(): ListRadiostationDao

    companion object {
        @Volatile
        private var INSTANCE: WebPlayerDatabase? = null

        fun getDatabase(context: Context): WebPlayerDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    WebPlayerDatabase::class.java,
                    "app_database")
                    .createFromAsset("database/bus_schedule.db")
                    .build()
                INSTANCE = instance

                instance
            }
        }
    }


    /*
    companion object {

        @Volatile
        private var INSTANCE: WebPlayerDatabase? = null

        fun getInstance(context: Context): WebPlayerDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context): WebPlayerDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                WebPlayerDatabase::class.java,
                "LocalAppDB.db"
            )
                // prepopulate the database after onCreate was called
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        // insert the data on the IO Thread
                        ioThread {
                            getInstance(context).radiostationDao().insert(PREPOPULATE_DATA)
                        }
                    }
                })
                .build()
        }

        val PREPOPULATE_DATA = ListRadiostation(
            0,
            "RPR1.Heavy Metal",
            "http://streams.rpr1.de/rpr-metal-128-mp3?usid=0-0-H-M-D-45",
            "Metall"
        )




        private val IO_EXECUTOR = Executors.newSingleThreadExecutor()

        /**
         * Utility method to run blocks on a dedicated background thread, used for io/database work.
         */
        fun ioThread(f : () -> Unit) {
            IO_EXECUTOR.execute(f)
        }


    }

*/


}