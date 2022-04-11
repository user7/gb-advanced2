package com.gb.advanced2.externals.ui.description

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.gb.advanced2.app.Contract
import com.gb.advanced2.databinding.DescriptionFragmentBinding
import com.gb.advanced2.entities.Article
import org.koin.android.ext.android.inject

class DescriptionFragment : Fragment() {
    private var _binding: DescriptionFragmentBinding? = null
    private val binding: DescriptionFragmentBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DescriptionFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val adapter = DescriptionListAdapter()
    private val viewModel by inject<Contract.ViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.descriptionList.adapter = adapter
        binding.descriptionList.layoutManager = LinearLayoutManager(requireContext())
        viewModel.getDescriptionScreenState().observe(viewLifecycleOwner) { renderState(it) }
    }

    private fun renderState(article: Article?) {
        binding.term.text = article?.term ?: ""
        adapter.submitList(article?.meanings)
    }
}