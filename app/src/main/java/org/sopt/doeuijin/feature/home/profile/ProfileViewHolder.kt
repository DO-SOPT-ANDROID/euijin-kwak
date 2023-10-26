package org.sopt.doeuijin.feature.home.profile

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import org.sopt.doeuijin.databinding.ItemFriendProfileBinding
import org.sopt.doeuijin.databinding.ItemMyProfileBinding

sealed class ProfileViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
    class MyProfileViewHolder(binding: ItemMyProfileBinding) : ProfileViewHolder(binding) {
        fun bind(item: Profile.MyProfile) {
        }
    }

    class FriendProfileViewHolder(binding: ItemFriendProfileBinding) : ProfileViewHolder(binding) {
        fun bind(item: Profile.FriendProfile) {
        }
    }
}