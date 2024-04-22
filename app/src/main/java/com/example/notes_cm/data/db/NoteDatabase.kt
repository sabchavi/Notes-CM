package com.example.notes_cm.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.notes_cm.data.dao.NoteDao
import com.example.notes_cm.data.entities.Note

// Definição da migração de versão 2 para 3
val MIGRATION_2_3: Migration = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Aqui você adiciona a nova coluna 'date'
        database.execSQL("ALTER TABLE notes ADD COLUMN date TEXT NOT NULL DEFAULT ''")
    }
}

@Database(entities = [Note::class], version = 3, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao

    companion object {
        @Volatile
        private var INSTANCE: NoteDatabase? = null

        fun getDatabase(context: Context): NoteDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    "note_database"
                )
                    .addMigrations(MIGRATION_2_3) // Adiciona a migração aqui
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}