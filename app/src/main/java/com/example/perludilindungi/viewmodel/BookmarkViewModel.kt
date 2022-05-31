package com.example.perludilindungi.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.perludilindungi.database.Bookmark
import com.example.perludilindungi.database.BookmarkDatabaseDao
import androidx.lifecycle.viewModelScope
import com.example.perludilindungi.data.Faskes
import com.example.perludilindungi.data.ResultsFaskes
import com.example.perludilindungi.detail.FacilityDetailFragmentArgs
import com.example.perludilindungi.list.FacilityListFragment
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class BookmarkViewModel(
    private val database: BookmarkDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private var id: Long = 0

    private lateinit var data: Bookmark

    fun setFacilityId(facilityId: Long) {
        id = facilityId
    }

    private fun addNewBookmark(bookmark: Bookmark) {
        val newItem = getNewBookmarkEntry(
            bookmark.facilityId,
            bookmark.code,
            bookmark.name,
            bookmark.city,
            bookmark.province,
            bookmark.address,
            bookmark.latitude,
            bookmark.longitude,
            bookmark.phone,
            bookmark.facilityType,
            bookmark.hospitalClass,
            bookmark.status,
            bookmark.sourceData
        )
        insertItem(newItem)
    }

    private fun insertItem(item: Bookmark) {
        viewModelScope.launch {
            database.insertBookmark(item)
        }
    }

    private fun getNewBookmarkEntry(
        facilityId: Long,
        code: String,
        name: String,
        city: String,
        province: String,
        address: String,
        latitude: String,
        longitude: String,
        phone: String,
        facilityType: String,
        hospitalClass: String,
        status: String,
        sourceData: String
    ): Bookmark {
        return Bookmark(
            facilityId,
            code,
            name,
            city,
            province,
            address,
            latitude,
            longitude,
            phone,
            facilityType,
            hospitalClass,
            status,
            sourceData
        )
    }

    val currentBookmarkData: LiveData<List<Bookmark>>
        get() = database.getAllBookmarks()

    private fun deleteBookmarkById() {
        viewModelScope.launch {
            database.deleteBookmarkById(id)
        }
    }

    private val _currentBookmarkButton = MutableLiveData<String>()
    val currentBookmarkButton: LiveData<String>
        get() = _currentBookmarkButton

    fun getBookmarkStatus() {
        viewModelScope.launch {
            if (getBookmarkStatusFromDatabase()) {
                _currentBookmarkButton.value = "- Unbookmark"
            } else {
                _currentBookmarkButton.value = "+ Bookmark"
            }
        }
    }

    fun getBookmarkDataFromArgs(args: FacilityDetailFragmentArgs): Bookmark {
        data = Bookmark(
            args.id,
            args.code,
            args.name,
            args.city,
            args.province,
            args.address,
            args.latitude,
            args.longitude,
            args.phone,
            args.facilityType,
            args.hospitalClass,
            args.status,
            args.source
        )
        return data
    }

    private suspend fun getBookmarkStatusFromDatabase(): Boolean{
        return database.isFacilityBookmarked(id)
    }

    fun handleOnClickBookmarkButton(args: FacilityDetailFragmentArgs) {
        if (_currentBookmarkButton.value == "- Unbookmark") {
            deleteBookmarkById()
            getBookmarkStatus()
        } else {
            addNewBookmark(getBookmarkDataFromArgs(args))
            getBookmarkStatus()
        }
    }
}