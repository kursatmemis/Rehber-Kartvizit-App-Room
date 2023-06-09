package com.example.rehberimapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.rehberimapp.configs.AppDataBase
import com.example.rehberimapp.databinding.ActivityMainBinding
import com.example.rehberimapp.models.Person
import com.example.rehberimapp.ui.course.CourseFragment
import com.example.rehberimapp.ui.family.FamilyFragment
import com.example.rehberimapp.ui.friends.FriendsFragment
import com.example.rehberimapp.ui.home.HomeFragment
import com.example.rehberimapp.ui.school.SchoolFragment
import com.example.rehberimapp.ui.work.WorkFragment
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    companion object {
        var db: AppDataBase? = null
        var allPeople: MutableList<Person>? = null
        var family: MutableList<Person>? = null
        var course: MutableList<Person>? = null
        var friends: MutableList<Person>? = null
        var school: MutableList<Person>? = null
        var work: MutableList<Person>? = null
        var eklenenGrup: String? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = AppDataBase.accessDataBase(this)
        getGroups()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        binding.appBarMain.addPersonFab.setOnClickListener { view ->
            val intent = Intent(this@MainActivity, RegistrationActivity::class.java)
            startActivity(intent)
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_family,
                R.id.nav_school,
                R.id.nav_friends,
                R.id.nav_work,
                R.id.nav_course
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        if (allPeople?.size == 0) {
            add10DefaultPeople()
            val job = GlobalScope.launch(Dispatchers.IO) {
                allPeople = db?.personDao()?.getLast10Persons()
            }
            runBlocking {
                job.join() // `join()` işlevi kullanılarak `launch` işlemi tamamlanana kadar beklenir.
            }
            allPeople = allPeople?.reversed()?.toMutableList()
            HomeFragment.personAdapter.clear()
            HomeFragment.personAdapter.addAll(allPeople as List<Person>)
            HomeFragment.personAdapter.notifyDataSetChanged()

        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun add10DefaultPeople() {
        val person1 = Person(null, "Ayşe", "Kaya", "532-987-6543", "Atatürk Bulv. No:12", "family")
        val person2 = Person(null, "Mehmet", "Öztürk", "533-246-1357", "İnönü Cad. No:3", "course")
        val person3 = Person(null, "Fatma", "Demir", "536-879-0246", "Cumhuriyet Sk. No:8", "friends")
        val person4 = Person(null, "Ahmet", "Avcı", "544-680-3192", "Fatih Mah. Sokak:2", "school")
        val person5 = Person(null, "Zeynep", "Şahin", "537-501-8273", "Bahçe Sok. No:6", "work")
        val person6 = Person(null, "Mustafa", "Kılıç", "531-912-7384", "Gazi Cad. No:9", "family")
        val person7 = Person(null, "Elif", "Arslan", "538-425-6790", "Ada Sk. No:4", "course")
        val person8 = Person(null, "Hasan", "Çelik", "546-837-2019", "Şehitler Mah. No:1", "friends")
        val person9 = Person(null, "Sema", "Yıldız", "530-694-1082", "Barış Cd. No:7", "school")
        val person10 = Person(null, "Ali", "Yılmaz", "555-123-4567", "Şehitler Cd. No:5", "work")
        family?.add(person1)
        family?.add(person6)
        course?.add(person2)
        course?.add(person7)
        friends?.add(person3)
        friends?.add(person8)
        school?.add(person4)
        school?.add(person9)
        work?.add(person5)
        work?.add(person10)
        val job = GlobalScope.launch(Dispatchers.IO) {
            db?.personDao()?.insert(person1)
            db?.personDao()?.insert(person2)
            db?.personDao()?.insert(person3)
            db?.personDao()?.insert(person4)
            db?.personDao()?.insert(person5)
            db?.personDao()?.insert(person6)
            db?.personDao()?.insert(person7)
            db?.personDao()?.insert(person8)
            db?.personDao()?.insert(person9)
            db?.personDao()?.insert(person10)
        }
        runBlocking {
            job.join() // `join()` işlevi kullanılarak `launch` işlemi tamamlanana kadar beklenir.
        }

    }

    private fun getGroups() {
        val run = Runnable {
            allPeople = db?.personDao()?.getLast10Persons()
            family = db?.personDao()?.getFamily()
            course = db?.personDao()?.getCourse()
            friends = db?.personDao()?.getFriends()
            school = db?.personDao()?.getSchool()
            work = db?.personDao()?.getWork()
        }
        Thread(run).start()
    }

    override fun onResume() {
        super.onResume()
        val job = GlobalScope.launch {
            allPeople = db?.personDao()?.getLast10Persons()
        }
        runBlocking {
            job.join() // `join()` işlevi kullanılarak `launch` işlemi tamamlanana kadar beklenir.
        }
        HomeFragment.personAdapter.clear()
        HomeFragment.personAdapter.addAll(allPeople as List<Person>)
        updateAdapter(eklenenGrup)
    }

    private fun updateAdapter(group: String?) {
        when (group) {
            "school" -> {
                SchoolFragment.personAdapter?.notifyDataSetChanged()
            }

            "course" -> {
                CourseFragment.personAdapter?.notifyDataSetChanged()
            }

            "friends" -> {
                FriendsFragment.personAdapter?.notifyDataSetChanged()
            }

            "family" -> {
                FamilyFragment.personAdapter?.notifyDataSetChanged()
            }

            "work" -> {
                WorkFragment.personAdapter?.notifyDataSetChanged()
            }
        }
    }
}