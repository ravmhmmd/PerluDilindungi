package com.example.perludilindungi.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmark_facility_table")
data class Bookmark(
    @PrimaryKey(autoGenerate = false)
    var facilityId: Long,

    @ColumnInfo(name = "code")
    val code: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "city")
    val city: String,

    @ColumnInfo(name = "province")
    val province: String,

    @ColumnInfo(name = "address")
    val address: String,

    @ColumnInfo(name = "latitude")
    val latitude: String,

    @ColumnInfo(name = "longitude")
    val longitude: String,

    @ColumnInfo(name = "phone")
    val phone: String,

    @ColumnInfo(name = "facility_type")
    val facilityType: String,

    @ColumnInfo(name = "hospital_class")
    val hospitalClass: String,

    @ColumnInfo(name = "status")
    val status: String,

    @ColumnInfo(name = "source_data")
    val sourceData: String,
)