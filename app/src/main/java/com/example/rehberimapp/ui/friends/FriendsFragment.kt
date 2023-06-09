package com.example.rehberimapp.ui.friends

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
import com.example.rehberimapp.databinding.FragmentFriendsBinding

class FriendsFragment : Fragment() {

    private var _binding: FragmentFriendsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    companion object {
        var personAdapter: PersonAdapter? = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFriendsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        personAdapter = PersonAdapter(requireContext().applicationContext, MainActivity.friends as MutableList)
        _binding!!.personListViewFriendsFragment.adapter = personAdapter
        personAdapter?.notifyDataSetChanged()

        _binding!!.personListViewFriendsFragment.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(requireContext().applicationContext, DetailActivity::class.java)
            intent.putExtra("position", position)
            intent.putExtra("group", "friends")
            startActivity(intent)
        }
        return root
    }
}