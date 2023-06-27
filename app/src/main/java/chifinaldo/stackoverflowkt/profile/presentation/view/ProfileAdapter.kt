package chifinaldo.stackoverflowkt.profile.presentation.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import chifinaldo.stackoverflowkt.R
import chifinaldo.stackoverflowkt.databinding.BadgeListBinding
import chifinaldo.stackoverflowkt.profile.domain.models.UserBadgeInfo

class ProfileAdapter(
    private val usersList: List<UserBadgeInfo>
) : RecyclerView.Adapter<ProfileAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            BadgeListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val badgeList = usersList[position]
        holder.bind(badgeList)
    }

    override fun getItemCount(): Int = usersList.size

    class ViewHolder(
        private val itemViewBinding: BadgeListBinding
    ) :
        RecyclerView.ViewHolder(itemViewBinding.root) {

        fun bind(badge: UserBadgeInfo) {
            itemViewBinding.apply {
                BadgeNameTextView.text = badge.name
                when (badge.rank) {
                    GOLD -> {
                        BadgeImageView.background = ContextCompat.getDrawable(
                            itemViewBinding.root.context,
                            R.drawable.badge_point_gold
                        )
                    }

                    SILVER -> BadgeImageView.background = ContextCompat.getDrawable(
                        itemViewBinding.root.context,
                        R.drawable.badge_point_silver
                    )

                    BRONZE -> BadgeImageView.background = ContextCompat.getDrawable(
                        itemViewBinding.root.context,
                        R.drawable.badge_point_bronze
                    )
                }
            }
        }
    }

    companion object BadgeRank {
        const val GOLD = "gold"
        const val SILVER = "silver"
        const val BRONZE = "bronze"
    }
}