package com.example.perludilindungi.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BookmarkDatabaseDao {

    @Insert
    suspend fun insertBookmark(bookmark: Bookmark)

    @Query("SELECT * from bookmark_facility_table")
    fun getAllBookmarks(): LiveData<List<Bookmark>>

    @Query("SELECT EXISTS (SELECT * from bookmark_facility_table WHERE facilityId = :id)")
    suspend fun isFacilityBookmarked(id: Long): Boolean

    @Query("DELETE FROM bookmark_facility_table WHERE facilityId = :id")
    suspend fun deleteBookmarkById(id: Long)
}