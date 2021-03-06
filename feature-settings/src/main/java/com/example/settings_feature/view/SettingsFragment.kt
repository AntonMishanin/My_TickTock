package com.example.settings_feature.view

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.RecyclerView
import com.example.base.UiState
import com.example.settings_feature.R
import com.example.settings_feature.navigator.SettingsNavigator
import com.example.settings_feature.view_model.SettingsViewModel
import com.example.utils.visible
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: SettingsViewModel by viewModels { viewModelFactory }

    private lateinit var adapter: SettingsAdapter

    private var recyclerViewContent: RecyclerView? = null
    private var progressBar: ProgressBar? = null
    private var failView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val navigator = requireActivity() as SettingsNavigator

        viewModel.onViewCreated(navigator)
        initView()
        observeViewModel()
    }

    private fun initView() {
        adapter =
            SettingsAdapter(viewModel::onItemClick)
        recyclerViewContent = view?.findViewById(R.id.recyclerView_settings_content)
        recyclerViewContent?.adapter = adapter

        progressBar = view?.findViewById(R.id.progress_bar)

        failView = view?.findViewById(R.id.layout_fail)

        val backButton = requireView().findViewById<ImageButton>(R.id.button_back)
        backButton.setOnClickListener { viewModel.onClickBack() }
    }

    private fun observeViewModel() {
        viewModel.content.observe(viewLifecycleOwner, adapter::setContent)
        viewModel.state.observe(viewLifecycleOwner, ::changeState)
    }

    private fun changeState(state: UiState) {
        recyclerViewContent?.visible(state == UiState.CONTENT)
        progressBar?.visible(state == UiState.PROGRESS)
        failView?.visible(state == UiState.FAIL)
    }
}