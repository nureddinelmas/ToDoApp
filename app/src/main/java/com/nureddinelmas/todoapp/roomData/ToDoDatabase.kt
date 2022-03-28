package com.nureddinelmas.todoapp.roomData

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nureddinelmas.todoapp.model.ToDo
import com.nureddinelmas.todoapp.utils.Converter


@Database(entities = [ToDo::class], version = 3, exportSchema = false)
@TypeConverters(Converter::class)
abstract class ToDoDatabase : RoomDatabase(){

    abstract fun toDoDao(): ToDoDao

    companion object {

        @Volatile
        private var INSTANCE: ToDoDatabase? = null

        fun getDatabase(context: Context): ToDoDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }

            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ToDoDatabase::class.java,
                    "to_do_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }

    }

    fun destroyInstance() {
        INSTANCE = null
    }

}