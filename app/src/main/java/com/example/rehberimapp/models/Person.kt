package com.example.rehberimapp.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("person")
data class Person(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("personId") val personId: Int?,
    @ColumnInfo("name") val name: String,
    @ColumnInfo("surname") val surname: String,
    @ColumnInfo("phone") val phone: String,
    @ColumnInfo("address") val address: String,
    @ColumnInfo("group") val group: String
) {
    override fun equals(other: Any?): Boolean {
        return this.name == (other as Person).name
    }
}
