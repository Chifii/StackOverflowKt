package chifinaldo.stackoverflowkt.home.presentation.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import chifinaldo.stackoverflowkt.R
import chifinaldo.stackoverflowkt.databinding.ActivityHomeBinding
import chifinaldo.stackoverflowkt.home.domain.models.UserList
import chifinaldo.stackoverflowkt.home.presentation.viewmodel.HomeViewModel
import chifinaldo.stackoverflowkt.profile.presentation.view.ProfileActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initObservables()

        binding.profileSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return query?.let {
                    if (it.isEmpty() || it.length < 3) {
                        Toast.makeText(
                            this@HomeActivity,
                            R.string.error_bad_query,
                            Toast.LENGTH_SHORT
                        ).show()
                        return false
                    } else {
                        showLoadingView(true)
                        viewModel.searchUser(query)
                        hideKeyboard()
                        return true
                    }
                } ?: false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

        binding.profileSearchView.onFocusChangeListener =
            View.OnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) {
                    hideKeyboard()
                }
            }
    }

    private fun initObservables() = with(viewModel) {
        usersList.observe(this@HomeActivity) { drawUsers(it) }
        error.observe(this@HomeActivity) { showErrorView(it) }
    }

    private fun drawUsers(usersList: UserList) {
        if (usersList.items.isEmpty()) {
            showErrorView(true)
            Toast.makeText(this, R.string.error_no_user_found, Toast.LENGTH_SHORT).show()
        } else {
            val layoutManager: RecyclerView.LayoutManager =
                LinearLayoutManager(this, RecyclerView.VERTICAL, false)
            val homeRVAdapter = HomeAdapter(usersList.items)

            binding.apply {
                usersProfileRecyclerView.layoutManager = layoutManager
                usersProfileRecyclerView.adapter = homeRVAdapter

                emptySerchTextView.visibility = View.GONE
            }

            showLoadingView(false)

            homeRVAdapter.setOnClickListener(object : HomeAdapter.onItemClickListener {
                override fun onItemClick(position: Int) {
                    val intent = Intent(this@HomeActivity, ProfileActivity::class.java)
                    intent.putExtra("userId", usersList.items[position].userId)
                    startActivity(intent)
                }
            })
        }
    }

    private fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.profileSearchView.windowToken, 0)
    }

    private fun showLoadingView(showLoading: Boolean) {
        binding.apply {
            if (showLoading) {
                lottieAnimation.setAnimation(R.raw.loading_animation)
                lottieAnimation.playAnimation()
                animationConstraintLayout.visibility = View.VISIBLE
                tryAgainButton.visibility = View.GONE
                searchingTextView.visibility = View.VISIBLE
            } else {
                animationConstraintLayout.visibility = View.GONE
                tryAgainButton.visibility = View.GONE
            }
        }
    }

    private fun showErrorView(showError: Boolean) {
        binding.apply {
            if (showError) {
                searchingTextView.visibility = View.INVISIBLE
                lottieAnimation.setAnimation(R.raw.error_animation)
                lottieAnimation.playAnimation()
                animationConstraintLayout.visibility = View.VISIBLE
                tryAgainButton.visibility = View.VISIBLE
                tryAgainButton.setOnClickListener {
                    showLoadingView(true)
                    showErrorView(false)
                }
            } else {
                animationConstraintLayout.visibility = View.GONE
                tryAgainButton.visibility = View.GONE
            }
        }
    }

}