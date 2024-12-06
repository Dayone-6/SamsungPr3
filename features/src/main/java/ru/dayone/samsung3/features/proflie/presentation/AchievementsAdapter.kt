package ru.dayone.samsung3.features.proflie.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.dayone.samsung3.features.R
import ru.dayone.samsung3.features.proflie.domain.data.models.Achievement

class AchievementsAdapter(
    private val achievements: List<Achievement>
) : RecyclerView.Adapter<AchievementsAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvTitle: TextView = view.findViewById(R.id.tv_ach_name)
        private val tvDescription: TextView = view.findViewById(R.id.tv_ach_description)

        fun bind(achievement: Achievement) {
            tvTitle.text = achievement.title
            tvDescription.text = achievement.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_achievement, parent, false)
        )
    }

    override fun getItemCount(): Int = achievements.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val achievement = achievements[position]

        holder.bind(achievement)
    }
}