package org.sopt.doeuijin.feature.home.profile

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import org.sopt.common.extension.loadCircularImage
import org.sopt.doeuijin.R
import org.sopt.doeuijin.databinding.ItemFriendProfileBinding
import org.sopt.doeuijin.databinding.ItemLandscapeProfileBinding
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

    class FriendProfileViewHolder(
        private val binding: ItemFriendProfileBinding,
        private val itemLongClicked: (Profile.FriendProfile) -> Unit,
    ) : ProfileViewHolder(binding) {
        private var item: Profile.FriendProfile? = null

        init {
            binding.root.setOnLongClickListener {
                itemLongClicked(item ?: return@setOnLongClickListener false)
                true
            }
        }

        fun bind(item: Profile.FriendProfile) {
            this.item = item
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

    class LandscapeProfileViewHolder(private val binding: ItemLandscapeProfileBinding) : ProfileViewHolder(binding) {
        fun bind(item: Profile) {
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