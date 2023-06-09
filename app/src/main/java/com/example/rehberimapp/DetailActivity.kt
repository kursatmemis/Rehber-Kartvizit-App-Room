package com.example.rehberimapp

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.rehberimapp.models.Person
import com.example.rehberimapp.ui.course.CourseFragment
import com.example.rehberimapp.ui.family.FamilyFragment
import com.example.rehberimapp.ui.friends.FriendsFragment
import com.example.rehberimapp.ui.home.HomeFragment
import com.example.rehberimapp.ui.school.SchoolFragment
import com.example.rehberimapp.ui.work.WorkFragment

class DetailActivity : AppCompatActivity() {

    private lateinit var fullNameTextView: TextView
    private lateinit var phoneNumberTextView: TextView
    private lateinit var addressTextView: TextView
    private lateinit var groupTextView: TextView
    private lateinit var deletePersonButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        setViews()
        val position = intent.getIntExtra("position", -1)
        val group = intent.getStringExtra("group")
        var person: Person? = null
        when (group) {
            "course" -> {
                person = MainActivity.course?.get(position)
            }

            "school" -> {
                person = MainActivity.school?.get(position)
            }

            "work" -> {
                person = MainActivity.work?.get(position)
            }

            "family" -> {
                person = MainActivity.family?.get(position)
            }

            "friends" -> {
                person = MainActivity.friends?.get(position)
            }

            "all" -> {
                person = MainActivity.allPeople?.get(position)
            }
        }
        fullNameTextView.text = "${person?.name} ${person?.surname}"
        phoneNumberTextView.text = person?.phone
        addressTextView.text = person?.address
        groupTextView.text = person?.group

        deletePersonButton.setOnClickListener {
            val builder = AlertDialog.Builder(this@DetailActivity)
            builder.setTitle("Emin misiniz?")
            builder.setMessage("Kaydı silmek istediğinize emin misiniz?")
            builder.setPositiveButton("Sil", DialogInterface.OnClickListener { dialog, which ->
                val run = Runnable {
                    MainActivity.db?.personDao()?.deletePerson(
                        person!!.name,
                        person.surname,
                        person.phone,
                        person.group,
                        person.address
                    )

                }
                Thread(run).start()
                updateAdapter(person?.group, person!!)
                Toast.makeText(
                    this@DetailActivity,
                    "Kayıt silindi. Bir önceki sayfaya yönlendiriliyorsunuz...",
                    Toast.LENGTH_LONG
                ).show()
                val timer = object : CountDownTimer(3000, 1000) {
                    override fun onTick(millisUntilFinished: Long) {

                    }

                    override fun onFinish() {
                        finish()
                    }
                }
                timer.start()
            })
            builder.setNegativeButton("Vazgeç", DialogInterface.OnClickListener { dialog, which ->
                Toast.makeText(
                    this@DetailActivity,
                    "Silme işleminden vazgeçildi.",
                    Toast.LENGTH_LONG
                ).show()
            })
            builder.show()

        }
    }

    private fun updateAdapter(group: String?, person: Person) {

        MainActivity.allPeople?.remove(person)
        HomeFragment.personAdapter.notifyDataSetChanged()
        when (group) {
            "course" -> {
                MainActivity.course?.remove(person)
                CourseFragment.personAdapter?.notifyDataSetChanged()
            }

            "school" -> {
                MainActivity.school?.remove(person)
                SchoolFragment.personAdapter?.notifyDataSetChanged()
            }

            "work" -> {
                MainActivity.work?.remove(person)
                WorkFragment.personAdapter?.notifyDataSetChanged()
            }

            "family" -> {
                MainActivity.family?.remove(person)
                FamilyFragment.personAdapter?.notifyDataSetChanged()
            }

            "friends" -> {
                MainActivity.friends?.remove(person)
                FriendsFragment.personAdapter?.notifyDataSetChanged()
            }
        }
    }

    private fun setViews() {
        fullNameTextView = findViewById(R.id.fullNameTextView)
        phoneNumberTextView = findViewById(R.id.phoneNumberTextView)
        addressTextView = findViewById(R.id.addressTextView)
        groupTextView = findViewById(R.id.groupTextView)
        deletePersonButton = findViewById(R.id.deletePersonButton)
    }
}