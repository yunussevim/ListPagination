package com.scorp.studycase.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.scorp.studycase.databinding.ActivityMainBinding
import com.scorp.studycase.ui.adapter.PaginationAdapter
import com.scorp.studycase.util.PaginationScrollListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainActivityViewModel by viewModels()

    @Inject
    lateinit var rvAdapter: PaginationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()
        initObservers()
        initListeners()
    }

    private fun initRecyclerView() {
        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        binding.rvUsers.adapter = rvAdapter
    }

    private fun initObservers() {
        viewModel.people.observe(this) {
            if (!it.isNullOrEmpty()) {
                rvAdapter.setListData(ArrayList(it))
                binding.btnRefresh.visibility = View.VISIBLE
                binding.btnNoData.visibility = View.GONE
            } else {
                rvAdapter.setListData(ArrayList())
                binding.btnRefresh.visibility = View.GONE
                binding.btnNoData.visibility = View.VISIBLE
            }
        }
        viewModel.isLoading.observe(this) {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }
        viewModel.errorState.observe(this) {
            Toast.makeText(this, getString(it), Toast.LENGTH_SHORT).show()
        }
    }

    private fun initListeners() {
        binding.rvUsers.addOnScrollListener(object : PaginationScrollListener() {
            override fun loadMoreItems() {
                viewModel.fetchData()
            }

            override fun refreshPage() {
                viewModel.refreshPage()
            }
        })

        binding.btnNoData.setOnClickListener { viewModel.fetchData() }
        binding.btnRefresh.setOnClickListener { viewModel.refreshPage() }
    }
}