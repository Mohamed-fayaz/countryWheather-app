package com.example.countryweather.listdata

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.LifecycleOwner

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.countryweather.country.CountryProperty
import com.example.countryweather.database.CountryDatabase


import com.example.countryweather.databinding.ListFragmentBinding
import org.w3c.dom.Text
import java.util.*
import kotlin.collections.ArrayList


class ListFragment : Fragment() {
    lateinit var viewModel: ListViewModel
    lateinit var list: List<CountryProperty>
    var list2: MutableList<CountryProperty>? = null
    lateinit var adapter : CountryRvAdapter

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val binding = ListFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val application = requireNotNull(this.activity).application
        val dataSource = CountryDatabase.getInstance(application).countryDao
        val viewModelFactory = ListViewModelFactory(dataSource)
        viewModel = ViewModelProviders.of(
                this, viewModelFactory).get(ListViewModel::class.java)


        binding.viewModel = viewModel
        val countryRv = binding.photosGrid
        countryRv.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)


        adapter = CountryRvAdapter(CountryRvAdapter.OnClickListener{
            viewModel.displayPropertyDetails(it)
        })

binding.photosGrid.adapter = adapter

        viewModel.navigateToSelectedProperty.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                this.findNavController().navigate(ListFragmentDirections.actionShowDetail(it))
                viewModel.displayPropertyDetailsComplete()
            }
        })

        viewModel.properties.observe(viewLifecycleOwner, {
            list = it
            adapter.submitList(it)
        })



binding.editSearch.addTextChangedListener(object : TextWatcher {
    override fun afterTextChanged(s: Editable?) {
        s?.let { viewModel.filter(it) }?.let { adapter.updatelist(it) }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        var text =  binding.editSearch.text
        Log.i("text changed","$text")
        Toast.makeText(context,"$text",Toast.LENGTH_SHORT)

    }
})


        return binding.root
    }



}