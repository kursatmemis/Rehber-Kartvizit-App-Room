package com.example.rehberimapp.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.rehberimapp.models.Person

@Dao
interface PersonDao {

    @Insert
    fun insert(person: Person): Long

    @Query("select * from person order by personId desc limit 10")
    fun getLast10Persons(): MutableList<Person>?

    @Query("SELECT * FROM Person WHERE `group` = 'family'")
    fun getFamily(): MutableList<Person>

    @Query("SELECT * FROM Person WHERE `group` = 'course'")
    fun getCourse(): MutableList<Person>

    @Query("SELECT * FROM Person WHERE `group` = 'friends'")
    fun getFriends(): MutableList<Person>

    @Query("SELECT * FROM Person WHERE `group` = 'school'")
    fun getSchool(): MutableList<Person>

    @Query("SELECT * FROM Person WHERE `group` = 'work'")
    fun getWork(): MutableList<Person>

    @Query("DELETE FROM person")
    fun deleteAll()

    @Query("DELETE FROM Person WHERE name = :personName AND surname = :personSurname AND phone = :phone AND `group` = :group AND address = :address")
    fun deletePerson(
        personName: String,
        personSurname: String,
        phone: String,
        group: String,
        address: String
    )

    @Query("select * from person")
    fun getAll(): List<Person>
}