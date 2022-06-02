package com.example.myapplication.feature.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.base.LoadingDialog
import com.example.myapplication.core.exhaustive
import com.example.myapplication.databinding.FragmentNewsBinding
import com.example.myapplication.feature.news.adapter.NewsAdapter
import com.example.myapplication.feature.sharedviewmodel.NewsSharedViewModel
import com.example.myapplication.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class NewsFragment : Fragment() {

    private val newsSharedViewModel: NewsSharedViewModel by activityViewModels()
    private lateinit var binding: FragmentNewsBinding
    private lateinit var loadingDialog: LoadingDialog
    private lateinit var adapter: NewsAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentNewsBinding.inflate(inflater, container, false)
        binding.viewModel = newsSharedViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        recyclerView = binding.recycler
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingDialog = LoadingDialog()
        loadingDialog.buildDialog(requireContext())
        initRecyclerview()
        newsSharedViewModel.viewState.observe(viewLifecycleOwner) {
            it.isLoading.let { isLoading ->
                if (isLoading) {
                    loadingDialog.showProgress(requireContext())
                } else {
                    loadingDialog.dismissDialog()
                }
            }
            it.articleList.let { list ->
                adapter.submitList(list)
            }
        }
        newsSharedViewModel.navigationLiveData.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { navigation ->
                when (navigation) {
                    is NewsSharedViewModel.Navigation.OnItemClicked -> {
                        Timber.e("Selected item position ${navigation.position}")
                        val bundle = bundleOf(Constants.POSITION to navigation.position)
                        view.findNavController().navigate(R.id.detailsFragment, bundle)
                    }
                    is NewsSharedViewModel.Navigation.OnServerError -> {
                        Toast.makeText(requireContext(), navigation.errorMessage, Toast.LENGTH_LONG).show()
                    }
                }.exhaustive
            }
        }
    }

    private fun initRecyclerview() {
        adapter = NewsAdapter(newsSharedViewModel)
        layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }
}