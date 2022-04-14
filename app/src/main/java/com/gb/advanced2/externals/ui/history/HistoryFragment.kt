package com.gb.advanced2.externals.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.gb.advanced2.app.Contract
import com.gb.advanced2.app.getMainScope
import com.gb.advanced2.databinding.HistoryFragmentBinding
import com.gb.advanced2.entities.SearchHistoryRecord
import com.gb.advanced2.externals.ui.navigation.NavigatorHolder
import org.koin.android.ext.android.inject
import org.koin.core.scope.Scope

class HistoryFragment : Fragment() {
    private var _binding: HistoryFragmentBinding? = null
    private val binding: HistoryFragmentBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        navigatorHolder = scope.get()
        _binding = HistoryFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val adapter = HistoryListAdapter { handleListClick(it) }
    private val viewModel by inject<Contract.ViewModel>()

    private lateinit var navigatorHolder: NavigatorHolder
    private val scope: Scope by lazy { getMainScope() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.historyList.adapter = adapter
        binding.historyList.layoutManager = LinearLayoutManager(requireContext())

        viewModel.getHistoryScreenState().observe(viewLifecycleOwner) { renderHistoryState(it) }
    }

    private fun renderHistoryState(state: Contract.HistoryScreenState) {
        when (state) {
            is Contract.HistoryScreenState.HistoryLoaded -> adapter.submitList(state.history)
            is Contract.HistoryScreenState.Error -> {
                Toast.makeText(
                    requireContext(),
                    "Error: ${state.error}",
                    Toast.LENGTH_LONG
                ).show()
            }
            is Contract.HistoryScreenState.Loading -> {
                adapter.submitList(listOf())
                // TODO progress bar
            }
        }
    }

    private fun handleListClick(record: SearchHistoryRecord) {
        viewModel.search(record.searchQuery)
        navigatorHolder.getNavigator().goToSearch()
    }
}