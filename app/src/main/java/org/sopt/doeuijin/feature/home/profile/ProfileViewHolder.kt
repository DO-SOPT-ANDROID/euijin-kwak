package org.sopt.doeuijin.feature.home.profile

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import org.sopt.common.extension.loadCircularImage
import org.sopt.doeuijin.R
import org.sopt.doeuijin.databinding.ItemFriendProfileBinding
import org.sopt.doeuijin.databinding.ItemMyProfileBinding

sealed class ProfileViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
    class MyProfileViewHolder(private val binding: ItemMyProfileBinding) : ProfileViewHolder(binding) {
        fun bind(item: Profile.MyProfile) {
            binding.run {
                tvName.text = item.name
                tvDescription.text = item.description
                if (item.image.isNotEmpty()) {
                    ivProfile.loadCircularImage(item.image)
                }
            }
        }
    }

    class FriendProfileViewHolder(private val binding: ItemFriendProfileBinding) : ProfileViewHolder(binding) {
        fun bind(item: Profile.FriendProfile) {
            binding.run {
                tvName.text = item.name
                tvDescription.text = item.description
                if (item.image.isEmpty()) {
                    ivProfile.loadCircularImage(R.drawable.logo_sopt_dark)
                } else {
                    ivProfile.loadCircularImage(item.image)
                }
            }
        }
    }
}