package com.example.countryweather.listdata

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.countryweather.database.CountryDatabase


import com.example.countryweather.databinding.ListFragmentBinding


class ListFragment : Fragment() {
  lateinit var viewModel : ListViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = ListFragmentBinding.inflate(inflater)
        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this
        val application = requireNotNull(this.activity).application
        val dataSource = CountryDatabase.getInstance(application).countryDao
        val viewModelFactory = ListViewModelFactory(dataSource)
      viewModel = ViewModelProviders.of(
            this, viewModelFactory).get(ListViewModel::class.java)
        // Giving the binding access to the OverviewViewModel
        binding.viewModel = viewModel
        val countryRv = binding.photosGrid
        countryRv.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)


        binding.photosGrid.adapter = CountryRvAdapter(CountryRvAdapter.OnClickListener {
            viewModel.displayPropertyDetails(it)
        })


        viewModel.navigateToSelectedProperty.observe(viewLifecycleOwner, Observer {
            if ( null != it ) {
                this.findNavController().navigate(ListFragmentDirections.actionShowDetail(it))
                viewModel.displayPropertyDetailsComplete()
            }
        })



        return binding.root
    }


}