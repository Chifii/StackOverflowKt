package chifinaldo.stackoverflowkt.profile.presentation.view

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import chifinaldo.stackoverflowkt.R
import chifinaldo.stackoverflowkt.databinding.ActivityProfileBinding
import chifinaldo.stackoverflowkt.profile.domain.models.UserBadge
import chifinaldo.stackoverflowkt.profile.domain.models.UserBadgeInfo
import chifinaldo.stackoverflowkt.profile.presentation.viewmodel.ProfileViewModel
import com.bumptech.glide.Glide

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private val viewModel: ProfileViewModel by viewModels()

    private lateinit var floatingOpen: Animation
    private lateinit var floatingClose: Animation
    private lateinit var floatingForward: Animation
    private lateinit var floatingBackward: Animation

    private lateinit var profileAdapter: ProfileAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val userId = intent.getIntExtra("userId", 0).toString()

        initObservables()
        viewModel.getUserInfo(userId)

        setupAnimations()
        setupButtons()

        binding.ProfileFloatingActionButton.setOnClickListener {
            viewModel.configButtons()
        }
    }

    private fun initObservables() = with(viewModel) {
        userBadgeInfo.observe(this@ProfileActivity) { drawProfile(it) }
        error.observe(this@ProfileActivity) { showErrorView(it) }
        showButtons.observe(this@ProfileActivity) { configButtons(it) }
        badgeInfo.observe(this@ProfileActivity) { drawBadges(it.items) }
    }

    private fun drawProfile(userBadge: UserBadge) {
        if (userBadge.items.isEmpty()) {
            showErrorView(showError = true, wasEmptyData = true)
        } else {
            drawBadges(userBadge.items)

            binding.apply {
                nameTextView.text = userBadge.items.first().name
                Glide.with(this@ProfileActivity).load(userBadge.items.first().user.profileImage)
                    .into(profileImageView)
                accountIdNumber.text = userBadge.items.first().user.accountId.toString()
                userIdNumber.text = userBadge.items.first().user.userId.toString()
            }
            showLoadingView(false)
        }
    }

    private fun drawBadges(usersList: List<UserBadgeInfo>) {
        val layoutManager: RecyclerView.LayoutManager =
            GridLayoutManager(this, 2, RecyclerView.VERTICAL, false)
        profileAdapter = ProfileAdapter(usersList)

        binding.profileBadgesRecyclerView.apply {
            setHasFixedSize(true)
            this.layoutManager = layoutManager
            adapter = profileAdapter
        }
        binding.profileBadgesRecyclerView.adapter?.notifyDataSetChanged()
    }

    private fun showLoadingView(showLoading: Boolean) {
        binding.apply {
            if (showLoading) {
                lottieAnimation.setAnimation(R.raw.loading_animation)
                lottieAnimation.playAnimation()
                animationConstraintLayout.visibility = View.VISIBLE
                tryAgainButton.visibility = View.GONE
            } else {
                animationConstraintLayout.visibility = View.GONE
                tryAgainButton.visibility = View.GONE
            }
        }
    }

    private fun showErrorView(showError: Boolean, wasEmptyData: Boolean = false) {
        binding.apply {
            if (showError) {
                if (wasEmptyData) {
                    errorTextView.visibility = View.VISIBLE
                } else {
                    errorTextView.visibility = View.INVISIBLE
                }
                lottieAnimation.setAnimation(R.raw.error_animation)
                lottieAnimation.playAnimation()
                animationConstraintLayout.visibility = View.VISIBLE
                tryAgainButton.visibility = View.VISIBLE
                tryAgainButton.setOnClickListener {
                    showLoadingView(true)
                    showErrorView(false)
                    onBackPressed()
                }
            } else {
                animationConstraintLayout.visibility = View.GONE
                tryAgainButton.visibility = View.GONE
            }
        }
    }

    private fun configButtons(showButtons: Boolean) {
        if (showButtons) {
            binding.apply {
                ProfileFloatingActionButton.startAnimation(floatingForward)

                BronzeFilterButton.visibility = View.VISIBLE
                BronzeFilterButton.startAnimation(floatingOpen)

                SilverFilterButton.visibility = View.VISIBLE
                SilverFilterButton.startAnimation(floatingOpen)

                GoldFilterButton.visibility = View.VISIBLE
                GoldFilterButton.startAnimation(floatingOpen)
            }
        } else {
            binding.apply {
                ProfileFloatingActionButton.startAnimation(floatingBackward)

                BronzeFilterButton.visibility = View.GONE
                BronzeFilterButton.startAnimation(floatingClose)

                SilverFilterButton.visibility = View.GONE
                SilverFilterButton.startAnimation(floatingClose)

                GoldFilterButton.visibility = View.GONE
                GoldFilterButton.startAnimation(floatingClose)
            }
        }
    }

    private fun setupButtons() {
        binding.apply {
            GoldFilterButton.setOnClickListener {
                viewModel.setSortFilter(GOLD)
            }
            SilverFilterButton.setOnClickListener {
                viewModel.setSortFilter(SILVER)
            }
            BronzeFilterButton.setOnClickListener {
                viewModel.setSortFilter(BRONZE)
            }
        }
    }

    private fun setupAnimations() {
        floatingOpen = AnimationUtils.loadAnimation(this, R.anim.floating_open)
        floatingClose = AnimationUtils.loadAnimation(this, R.anim.floating_close)
        floatingForward = AnimationUtils.loadAnimation(this, R.anim.floating_forward)
        floatingBackward = AnimationUtils.loadAnimation(this, R.anim.floating_backward)
    }

    companion object {
        private const val GOLD = "gold"
        private const val SILVER = "silver"
        private const val BRONZE = "bronze"
    }
}