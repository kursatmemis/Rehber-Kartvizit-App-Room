package com.example.rehberimapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import com.example.rehberimapp.models.Person
import com.google.android.material.snackbar.Snackbar

class RegistrationActivity : AppCompatActivity() {

    private lateinit var selectGroupButton: Button
    private lateinit var savePersonButton: Button
    private lateinit var nameEditText: TextView
    private lateinit var surnameEditText: TextView
    private lateinit var phoneEditText: TextView
    private lateinit var locationEditText: TextView
    private var group: String = "undifined"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        setViews()

        selectGroupButton.setOnClickListener {
            val popupMenu = PopupMenu(this@RegistrationActivity, it)
            menuInflater.inflate(R.menu.menu_select_group, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.family -> {
                        group = "family"
                        selectGroupButton.text = getString(R.string.menu_family)
                        true
                    }

                    R.id.work -> {
                        group = "work"
                        selectGroupButton.text = getString(R.string.menu_work)
                        true
                    }

                    R.id.course -> {
                        group = "course"
                        selectGroupButton.text = getString(R.string.menu_course)
                        true
                    }

                    R.id.friends -> {
                        group = "friends"
                        selectGroupButton.text = getString(R.string.menu_friends)
                        true
                    }

                    R.id.school -> {
                        group = "school"
                        selectGroupButton.text = getString(R.string.menu_school)
                        true
                    }

                    else -> {
                        group = "undifined"
                        true
                    }
                }
            }
            popupMenu.show()
        }

        savePersonButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val phone = phoneEditText.text.toString()
            val surname: String
            val location: String
            surname = surnameEditText.text.toString()
            location = locationEditText.text.toString()

            if (group == "undifined") {
                Toast.makeText(
                    this@RegistrationActivity,
                    "Lütfen bir grup belirtiniz!",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val person = Person(null, name, surname, phone, location, group)
                val run = Runnable {
                    val result = MainActivity.db?.personDao()?.insert(person)
                    when (group) {
                        "family" -> {
                            MainActivity.family?.add(0,person)
                            MainActivity.eklenenGrup = "family"
                        }

                        "course" -> {
                            MainActivity.course?.add(0,person)
                            MainActivity.eklenenGrup = "course"
                        }

                        "work" -> {
                            MainActivity.work?.add(0,person)
                            MainActivity.eklenenGrup = "work"
                        }

                        "school" -> {
                            MainActivity.school?.add(0,person)
                            MainActivity.eklenenGrup = "school"
                        }

                        "friends" -> {
                            MainActivity.friends?.add(0,person)
                            MainActivity.eklenenGrup = "friends"
                        }

                    }
                    MainActivity.allPeople?.add(person)
                    if (result != null && result > 0) {
                        Snackbar.make(
                            savePersonButton,
                            "Kişi Başarıyla Kaydedildi!",
                            Snackbar.LENGTH_LONG
                        )
                            .setAction("Action", null).show()
                    }
                }
                Thread(run).start()
                val timer = object : CountDownTimer(3000, 1000) {
                    override fun onTick(millisUntilFinished: Long) {

                    }

                    override fun onFinish() {
                        finish()
                    }
                }
                timer.start()
            }
            }
    }

    private fun setViews() {
        selectGroupButton = findViewById(R.id.selectGroupButton)
        savePersonButton = findViewById(R.id.savePersonButton)
        nameEditText = findViewById(R.id.nameEditText)
        surnameEditText = findViewById(R.id.surnameEditText)
        phoneEditText = findViewById(R.id.phoneEditText)
        locationEditText = findViewById(R.id.locationEditText)
    }
}