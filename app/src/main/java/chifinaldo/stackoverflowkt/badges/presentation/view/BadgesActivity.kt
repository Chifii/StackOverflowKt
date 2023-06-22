package chifinaldo.stackoverflowkt.badges.presentation.view

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import chifinaldo.stackoverflowkt.R
import chifinaldo.stackoverflowkt.badges.domain.models.BadgesList
import chifinaldo.stackoverflowkt.badges.presentation.viewmodel.BadgesViewModel
import chifinaldo.stackoverflowkt.databinding.ActivityBadgesBinding

class BadgesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBadgesBinding
    private val viewModel: BadgesViewModel by viewModels()

    private lateinit var floatingOpen: Animation
    private lateinit var floatingClose: Animation
    private lateinit var floatingForward: Animation
    private lateinit var floatingBackward: Animation

    private var order = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBadgesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel.setup()
        initObservables()
        setupAnimations()
        setupButtons()

        binding.HomeFloatingActionButton.setOnClickListener {
            viewModel.configButtons()
        }
    }

    private fun initObservables() = with(viewModel) {
        badgesList.observe(this@BadgesActivity) { setBadges(it) }
        showButtons.observe(this@BadgesActivity) { configButtons(it) }
        showErrorToast.observe(this@BadgesActivity) { showToast() }
    }

    private fun setBadges(badgesList: BadgesList) {
        val layoutManager: RecyclerView.LayoutManager =
            GridLayoutManager(this, 2, RecyclerView.VERTICAL, false)
        val homeRVAdapter = BadgesAdapter(badgesList.items)
        binding.apply {
            HomeRecyclerView.layoutManager = layoutManager
            HomeRecyclerView.adapter = homeRVAdapter
        }

        homeRVAdapter.setOnClickListener(
            object : BadgesAdapter.onItemClickListener {
                override fun onItemClick(position: Int) {
                    // For now it's not necessary
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

    private fun setupButtons() {
        binding.apply {
            RankFilterButton.setOnClickListener {
                viewModel.setSortFilter(RANK)
            }
            NameFilterButton.setOnClickListener {
                viewModel.setSortFilter(NAME)
            }
            AscFilterButton.setOnClickListener {
                viewModel.setOrderFilter(order).also { order = !order }
            }
        }
    }

    private fun showToast() {
        Toast.makeText(this, "Wow, you're going too fast. Take a rest", Toast.LENGTH_SHORT).show()
    }

    private fun setupAnimations() {
        floatingOpen = AnimationUtils.loadAnimation(this, R.anim.floating_open)
        floatingClose = AnimationUtils.loadAnimation(this, R.anim.floating_close)
        floatingForward = AnimationUtils.loadAnimation(this, R.anim.floating_forward)
        floatingBackward = AnimationUtils.loadAnimation(this, R.anim.floating_backward)
    }

    companion object {
        private const val TAG = "HomeActivity"
        private const val RANK = "rank"
        private const val NAME = "name"
    }
}