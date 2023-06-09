package com.example.rehberimapp.ui.school

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rehberimapp.DetailActivity
import com.example.rehberimapp.MainActivity
import com.example.rehberimapp.adapters.PersonAdapter
import com.example.rehberimapp.databinding.FragmentSchoolBinding

class SchoolFragment : Fragment() {

    private var _binding: FragmentSchoolBinding? = null

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
        _binding = FragmentSchoolBinding.inflate(inflater, container, false)
        val root: View = binding.root
        personAdapter = PersonAdapter(requireContext().applicationContext, MainActivity.school as MutableList)
        _binding!!.personListViewSchoolFragment.adapter = personAdapter
        personAdapter?.notifyDataSetChanged()

        _binding!!.personListViewSchoolFragment.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(requireContext().applicationContext, DetailActivity::class.java)
            intent.putExtra("position", position)
            intent.putExtra("group", "school")
            startActivity(intent)
        }
        return root
    }

}