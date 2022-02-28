package com.amar.peoplelistapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.amar.peoplelistapp.databinding.ActivityPeopleBinding
import com.amar.peoplelistapp.listeners.OnLikeListener
import com.amar.peoplelistapp.model.PersonData
import com.amar.peoplelistapp.ui.adapters.PersonRecyclerViewAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class PeopleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPeopleBinding
    val viewModel: PeopleViewModel by viewModels()

    lateinit var adapter: PersonRecyclerViewAdapter
    lateinit var listener: OnLikeListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPeopleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listener = object : OnLikeListener {
            override fun onLiked(person: PersonData) {
                viewModel.setLiked(person.id, person.isLiked)
            }
        }

        adapter = PersonRecyclerViewAdapter(applicationContext, listOf(), listener)
        binding.recyclerView.adapter = adapter

        viewModel.getPeopleListData()

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    viewModel.loadMore()
                }
            }
        })

        lifecycleScope.launchWhenCreated {
            viewModel.peopleList.collect {
                adapter.setItems(it)
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.showProgress.collect {showProgress ->
                binding.progressBar.visibility = if (showProgress) View.VISIBLE else View.GONE
            }
        }

    }
}