package org.sopt.doeuijin.feature.home.profile

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import org.sopt.doeuijin.databinding.ItemFriendProfileBinding
import org.sopt.doeuijin.databinding.ItemMyProfileBinding

sealed class ProfileViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
    class MyProfileViewHolder(private val binding: ItemMyProfileBinding) : ProfileViewHolder(binding) {
        fun bind(item: Profile.MyProfile) {
            binding.run {
                tvName.text = item.name
                tvDescription.text = item.description
            }
        }
    }

    class FriendProfileViewHolder(private val binding: ItemFriendProfileBinding) : ProfileViewHolder(binding) {
        fun bind(item: Profile.FriendProfile) {
        }
    }
}