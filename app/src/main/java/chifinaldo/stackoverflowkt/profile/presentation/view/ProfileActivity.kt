package chifinaldo.stackoverflowkt.profile.presentation.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import chifinaldo.stackoverflowkt.R
import chifinaldo.stackoverflowkt.databinding.ActivityProfileBinding
import chifinaldo.stackoverflowkt.profile.domain.models.UserBadge
import chifinaldo.stackoverflowkt.profile.presentation.viewmodel.ProfileViewModel
import com.bumptech.glide.Glide

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val userId = intent.getIntExtra("accountId", 0).toString()

        initObservables()
        viewModel.getUserInfo(userId)
    }

    private fun initObservables() = with(viewModel) {
        userBadgeInfo.observe(this@ProfileActivity) { drawProfile(it) }
        error.observe(this@ProfileActivity) { showErrorView(it) }
    }

    private fun drawProfile(userBadge: UserBadge) {
        if (userBadge.items.isEmpty()) {
            showErrorView(showError = true, wasEmptyData = true)
        } else {
            val layoutManager: RecyclerView.LayoutManager =
                LinearLayoutManager(this, RecyclerView.VERTICAL, false)
            val homeRVAdapter = ProfileAdapter(userBadge.items)

            binding.profileBadgesRecyclerView.apply {
                setHasFixedSize(true)
                this.layoutManager = layoutManager
                adapter = homeRVAdapter
            }

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
}