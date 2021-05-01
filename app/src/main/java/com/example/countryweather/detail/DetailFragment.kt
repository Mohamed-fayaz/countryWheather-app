package com.example.countryweather.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.example.countryweather.databinding.DetailFragmentBinding

class DetailFragment : Fragment() {

    private lateinit var viewModel: DetailViewModel
    private lateinit var binding : DetailFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val application = requireNotNull(activity).application
        binding = DetailFragmentBinding.inflate(inflater)
        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

       val countryProperty = DetailFragmentArgs.fromBundle(requireArguments()).SelectedProperty
        val viewModelFactory = DetailViewModelFactory(countryProperty)
        binding.viewModel = ViewModelProviders.of(
                this, viewModelFactory).get(DetailViewModel::class.java)
        return binding.root
    }


}

