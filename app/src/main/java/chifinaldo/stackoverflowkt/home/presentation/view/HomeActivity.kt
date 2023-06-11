package chifinaldo.stackoverflowkt.home.presentation.view

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import chifinaldo.stackoverflowkt.R
import chifinaldo.stackoverflowkt.databinding.ActivityHomeBinding
import chifinaldo.stackoverflowkt.home.domain.models.BadgesList
import chifinaldo.stackoverflowkt.home.presentation.viewmodel.HomeViewModel

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val viewModel: HomeViewModel by viewModels()

    private lateinit var floatingOpen: Animation
    private lateinit var floatingClose: Animation
    private lateinit var floatingForward: Animation
    private lateinit var floatingBackward: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel.setup()
        initObservables()
        setupAnimations()

        binding.HomeFloatingActionButton.setOnClickListener {
            viewModel.configButtons()
        }
    }

    private fun initObservables() = with(viewModel) {
        badgesList.observe(this@HomeActivity) { setBadges(it) }
        showButtons.observe(this@HomeActivity) { configButtons(it) }
    }

    private fun setBadges(badgesList: BadgesList) {
        val layoutManager: RecyclerView.LayoutManager =
            GridLayoutManager(this, 2, RecyclerView.VERTICAL, false)
        val homeRVAdapter = HomeAdapter(badgesList.items)
        binding.apply {
            HomeRecyclerView.layoutManager = layoutManager
            HomeRecyclerView.adapter = homeRVAdapter
        }

        homeRVAdapter.setOnClickListener(
            object : HomeAdapter.onItemClickListener {
                override fun onItemClick(position: Int) {

                }
            }
        )
    }

    private fun configButtons(showButtons: Boolean) {
        if (showButtons) {
            binding.apply {
                HomeFloatingActionButton.startAnimation(floatingForward)

                AscFilterButton.visibility = View.VISIBLE
                AscFilterButton.startAnimation(floatingOpen)

                NameFilterButton.visibility = View.VISIBLE
                NameFilterButton.startAnimation(floatingOpen)

                RankFilterButton.visibility = View.VISIBLE
                RankFilterButton.startAnimation(floatingOpen)

            }
        } else {
            binding.apply {
                HomeFloatingActionButton.startAnimation(floatingBackward)

                RankFilterButton.visibility = View.GONE
                RankFilterButton.startAnimation(floatingClose)

                NameFilterButton.visibility = View.GONE
                NameFilterButton.startAnimation(floatingClose)

                AscFilterButton.visibility = View.GONE
                AscFilterButton.startAnimation(floatingClose)
            }
        }
    }

    private fun setupAnimations() {
        floatingOpen = AnimationUtils.loadAnimation(this, R.anim.floating_open)
        floatingClose = AnimationUtils.loadAnimation(this, R.anim.floating_close)
        floatingForward = AnimationUtils.loadAnimation(this, R.anim.floating_forward)
        floatingBackward = AnimationUtils.loadAnimation(this, R.anim.floating_backward)
    }

}