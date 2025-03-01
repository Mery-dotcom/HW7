package com.example.hw7.fragment

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        adapter = ImageAdapter(
            clickListener = { image -> },
            longClickListener = { image -> }
        )
        binding.recyclerView.adapter = adapter
    }

    private fun updateUi() {
        viewModel.images.observe(viewLifecycleOwner) { response ->
            when (response){
                is Either.Success -> {
                    val images: List<ImageResponse.Hit> = response.success?.hits?.filterNotNull() ?: emptyList()
                    adapter.submitList(images)
                    binding.errorTextView.visibility = View.GONE
                }
                is Either.Error -> {
                    binding.errorTextView.text = "Error: ${response.error}"
                    binding.errorTextView.visibility = View.VISIBLE
                }
            }
        }

        viewModel.event.observe(viewLifecycleOwner) { event ->
            when (event) {
                is ImageViewModel.Event.ShowError -> {
                    binding.errorTextView.text = event.message
                    binding.errorTextView.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun loadData() {
        val apiKey = "Key"
        val query = "query"
        viewModel.getImages(apiKey, query)
    }
}