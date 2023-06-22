package chifinaldo.stackoverflowkt.badges.presentation.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import chifinaldo.stackoverflowkt.R
import chifinaldo.stackoverflowkt.badges.domain.models.Badge
import chifinaldo.stackoverflowkt.databinding.BadgeListBinding

class BadgesAdapter(private val badgesList: List<Badge>) :
    RecyclerView.Adapter<BadgesAdapter.ViewHolder>() {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            BadgeListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, mListener)
    }

    fun setOnClickListener(listener: onItemClickListener) {
        mListener = listener
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val badgeList = badgesList[position]
        holder.bind(badgeList)
    }

    override fun getItemCount(): Int = badgesList.size

    class ViewHolder(
        private val itemViewBinding: BadgeListBinding,
        listener: onItemClickListener
    ) :
        RecyclerView.ViewHolder(itemViewBinding.root) {

        init {
            itemViewBinding.HomeBadgeListConstraintLayout.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }

        fun bind(badge: Badge) {
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