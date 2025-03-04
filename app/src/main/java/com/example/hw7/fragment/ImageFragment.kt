package com.example.hw7.fragment

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide.init
import com.example.hw7.view.viewModels.ImageViewModel
import com.example.hw7.R
import com.example.hw7.databinding.FragmentImageBinding
import com.example.hw7.model.core.Either
import com.example.hw7.model.models.ImageResponse
import com.example.hw7.view.adapters.ImageAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ImageFragment : Fragment() {

    private lateinit var binding: FragmentImageBinding
    private val viewModel: ImageViewModel by viewModels()
    private lateinit var adapter: ImageAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentImageBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        updateUi()
        loadData()
    }

    private fun init() {
        adapter = ImageAdapter()
        binding.recyclerView.adapter = adapter
    }

    private fun updateUi() {
        viewModel.images.observe(viewLifecycleOwner) { response ->
            val images = response.hits?.filterNotNull() ?: emptyList()
            adapter.submitList(images)
            binding.errorTextView.visibility = if (images.isEmpty()) View.VISIBLE else View.GONE
        }

        viewModel.event.observe(viewLifecycleOwner) { event ->
            when (event) {
                is ImageViewModel.Event.ShowError -> {
                    binding.errorTextView.text = event.image
                    binding.errorTextView.visibility = View.VISIBLE
                }
            }
        }

        viewModel.weather.observe(viewLifecycleOwner, Observer { weather ->
            val temp = weather.current?.tempC
            binding.tvWeather.text = "$temp°C"
            binding.tvWeather.visibility = View.VISIBLE
        })
    }

    private fun loadData() {
        val apiKey = "49085045-188b342da441f8a6e1476a6e9"
        val query = "beach"
        viewModel.getImages(apiKey, query)

        val weatherApiKey = "4019aa9d8ddd44c9a47150314250702"
        val city = "Bishkek"
        viewModel.getWeather(weatherApiKey, city)
    }
}