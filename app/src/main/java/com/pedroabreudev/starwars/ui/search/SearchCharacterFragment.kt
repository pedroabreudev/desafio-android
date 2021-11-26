package com.pedroabreudev.starwars.ui.search

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.pedroabreudev.starwars.R
import com.pedroabreudev.starwars.databinding.FragmentSearchCharacterBinding
import com.pedroabreudev.starwars.ui.adapter.CharacterAdapter
import com.pedroabreudev.starwars.ui.base.BaseFragment
import com.pedroabreudev.starwars.ui.state.ResourceState
import com.pedroabreudev.starwars.util.hide
import com.pedroabreudev.starwars.util.show
import com.pedroabreudev.starwars.util.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class SearchCharacterFragment :
    BaseFragment<FragmentSearchCharacterBinding, SearchCharacterViewModel>() {

    override val viewModel: SearchCharacterViewModel by viewModels()
    private val characterAdapter by lazy { CharacterAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        clickAdapter()
        collectObserver()

        val query = savedInstanceState?.getString("last_search_query") ?: ""
        searchInit(query)

    }

    private fun searchInit(query: String) = with(binding) {
        edSearchCharacter.setText(query)
        edSearchCharacter.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                updateCharacterList()
                true
            } else {
                false
            }
        }
        edSearchCharacter.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                updateCharacterList()
                true
            } else {
                false
            }
        }
    }

    private fun updateCharacterList() = with(binding) {
        edSearchCharacter.editableText.trim().let {
            if (it.isNotEmpty()) {
                searchQuery(it.toString())
            }
        }
    }

    private fun searchQuery(query: String) {
        viewModel.fetch(query)
    }

    private fun collectObserver() = lifecycleScope.launch {
        viewModel.searchCharacter.collect { result ->
            when (result) {
                is ResourceState.Success -> {
                    binding.pbSearch.hide()
                    result.data?.let {
                        characterAdapter.characters = it.results.toList()
                    }
                }
                is ResourceState.Error -> {
                    binding.pbSearch.hide()
                    result.message?.let { message ->
                        Timber.tag("SearchCharacterFragment").e("Error -> $message")
                        toast(getString(R.string.an_error_occurred))
                    }

                }
                is ResourceState.Loading -> {
                    binding.pbSearch.show()
                }
                else -> {
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(
            "last_search_query",
            binding.edSearchCharacter.editableText.trim().toString()
        )
    }

    private fun clickAdapter() {
        characterAdapter.setOnClickListener { characterModel ->
            val action = SearchCharacterFragmentDirections
                .actionSearchCharacterFragmentToDetailsCharacterFragment()
            findNavController().navigate(action)
        }
    }

    private fun setupRecyclerView() = with(binding) {
        rvSearchCharacter.apply {
            adapter = characterAdapter
            layoutManager = LinearLayoutManager(context)
        }

    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSearchCharacterBinding =
        FragmentSearchCharacterBinding.inflate(inflater, container, false)
}