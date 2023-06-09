package com.example.rehberimapp.ui.course

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rehberimapp.DetailActivity
import com.example.rehberimapp.MainActivity
import com.example.rehberimapp.R
import com.example.rehberimapp.adapters.PersonAdapter
import com.example.rehberimapp.databinding.FragmentCourseBinding
import com.example.rehberimapp.databinding.FragmentFamilyBinding

class CourseFragment : Fragment() {

    private var _binding: FragmentCourseBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    companion object{
        var personAdapter: PersonAdapter? = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCourseBinding.inflate(inflater, container, false)
        val root: View = binding.root
        personAdapter = PersonAdapter(requireContext().applicationContext, MainActivity.course as MutableList)
        personAdapter?.notifyDataSetChanged()
        _binding!!.personListViewCourseFragment.adapter = personAdapter

        _binding!!.personListViewCourseFragment.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(requireContext().applicationContext, DetailActivity::class.java)
            intent.putExtra("position", position)
            intent.putExtra("group", "course")
            startActivity(intent)
        }
        return root
    }
}