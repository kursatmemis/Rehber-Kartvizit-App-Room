package com.example.rehberimapp.ui.family

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rehberimapp.DetailActivity
import com.example.rehberimapp.MainActivity
import com.example.rehberimapp.adapters.PersonAdapter
import com.example.rehberimapp.databinding.FragmentFamilyBinding
import com.example.rehberimapp.ui.home.HomeFragment

class FamilyFragment : Fragment() {

    private var _binding: FragmentFamilyBinding? = null

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
        _binding = FragmentFamilyBinding.inflate(inflater, container, false)
        val root: View = binding.root
        personAdapter = PersonAdapter(requireContext().applicationContext, MainActivity.family as MutableList)
        _binding!!.personListViewFamilyFragment.adapter = personAdapter
        personAdapter?.notifyDataSetChanged()

        _binding!!.personListViewFamilyFragment.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(requireContext().applicationContext, DetailActivity::class.java)
            intent.putExtra("position", position)
            intent.putExtra("group", "family")
            startActivity(intent)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}