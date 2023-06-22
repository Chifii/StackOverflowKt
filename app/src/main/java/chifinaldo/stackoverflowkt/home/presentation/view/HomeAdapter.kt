package chifinaldo.stackoverflowkt.home.presentation.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import chifinaldo.stackoverflowkt.databinding.HomeUserListBinding
import chifinaldo.stackoverflowkt.home.domain.models.User
import com.bumptech.glide.Glide

class HomeAdapter(
    private val usersList: List<User>
) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            HomeUserListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val userList = usersList[position]
        holder.bind(userList)
    }

    fun setOnClickListener(listener: onItemClickListener) {
        mListener = listener
    }

    override fun getItemCount(): Int = usersList.size

    class ViewHolder(
        private val itemViewBinding: HomeUserListBinding,
        listener: onItemClickListener
    ) : RecyclerView.ViewHolder(itemViewBinding.root) {

        init {
            itemViewBinding.homeUserListConstraintLayout.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }

        fun bind(user: User) {
            itemViewBinding.apply {
                Glide.with(itemView)
                    .load(user.profileImage)
                    .into(profilePicture)

                usersName.text = user.displayName
                bronzeCount.text = user.badgeType.bronze.toString()
                silverCount.text = user.badgeType.silver.toString()
                goldCount.text = user.badgeType.gold.toString()
                reputationCount.text = user.reputation.toString()
            }
        }

    }
}