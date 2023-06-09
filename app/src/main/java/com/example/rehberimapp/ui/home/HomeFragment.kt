package com.example.rehberimapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.rehberimapp.DetailActivity
import com.example.rehberimapp.MainActivity
import com.example.rehberimapp.adapters.PersonAdapter
import com.example.rehberimapp.databinding.FragmentHomeBinding
import com.example.rehberimapp.models.Person


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    companion object {
        lateinit var personAdapter: PersonAdapter
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        personAdapter = PersonAdapter(
            requireContext().applicationContext,
            MainActivity.allPeople as List<Person>
        )
        _binding!!.personListViewHomeFragment.adapter = personAdapter
        personAdapter.notifyDataSetChanged()

        _binding!!.searchPersonEditText.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(cs: CharSequence, arg1: Int, arg2: Int, arg3: Int) {
                val textlength: Int = cs.length
                val tempArrayList: ArrayList<Person> = ArrayList<Person>()
                for (c in MainActivity.allPeople!!) {
                    if (textlength <= c.name.length) {
                        if (c.name.toLowerCase().contains(cs.toString().toLowerCase())) {
                            tempArrayList.add(c)
                        }
                    }
                }
                personAdapter = PersonAdapter(requireContext().applicationContext, tempArrayList)
                _binding!!.personListViewHomeFragment.adapter = personAdapter
            }

            override fun beforeTextChanged(
                arg0: CharSequence, arg1: Int, arg2: Int,
                arg3: Int
            ) {
            }

            override fun afterTextChanged(arg0: Editable) {}
        })

        _binding!!.personListViewHomeFragment.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(requireContext().applicationContext, DetailActivity::class.java)
            intent.putExtra("position", position)
            intent.putExtra("group", "all")
            startActivity(intent)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}