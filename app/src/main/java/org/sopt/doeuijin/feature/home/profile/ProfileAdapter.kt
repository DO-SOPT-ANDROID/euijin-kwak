package org.sopt.doeuijin.feature.home.profile

import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import org.sopt.common.extension.ItemDiffCallback
import org.sopt.doeuijin.databinding.ItemFriendProfileBinding
import org.sopt.doeuijin.databinding.ItemLandscapeProfileBinding
import org.sopt.doeuijin.databinding.ItemMyProfileBinding

class ProfileAdapter(
    private val orientation: Int,
    private val onRemoveItem: ((Profile.FriendProfile) -> Unit)? = null,
) : ListAdapter<Profile, ProfileViewHolder>(
    ItemDiffCallback<Profile>(
        onItemsTheSame = { old, new -> old == new },
        onContentsTheSame = { old, new -> old.name == new.name },
    ),
) {

    enum class ProfileType(val viewType: Int) {
        MY_PROFILE(0),
        FRIEND_PROFILE(1),
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        return when (viewType) {
            ProfileType.MY_PROFILE.viewType -> {
                if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                    ProfileViewHolder.MyProfileViewHolder(ItemMyProfileBinding.inflate(LayoutInflater.from(parent.context), parent, false))
                } else {
                    ProfileViewHolder.LandscapeProfileViewHolder(ItemLandscapeProfileBinding.inflate(LayoutInflater.from(parent.context), parent, false))
                }
            }

            else -> {
                if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                    ProfileViewHolder.FriendProfileViewHolder(
                        binding = ItemFriendProfileBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                        itemLongClicked = {
                            onRemoveItem?.invoke(it)
                        },
                    )
                } else {
                    ProfileViewHolder.LandscapeProfileViewHolder(ItemLandscapeProfileBinding.inflate(LayoutInflater.from(parent.context), parent, false))
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (currentList[position]) {
            is Profile.MyProfile -> ProfileType.MY_PROFILE.viewType
            is Profile.FriendProfile -> ProfileType.FRIEND_PROFILE.viewType
        }
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            (holder as? ProfileViewHolder.LandscapeProfileViewHolder)?.bind(currentList[position])
            return
        }
        when (holder) {
            is ProfileViewHolder.MyProfileViewHolder -> {
                (currentList[position] as? Profile.MyProfile)?.let { holder.bind(it) }
            }

            is ProfileViewHolder.FriendProfileViewHolder -> {
                (currentList[position] as? Profile.FriendProfile)?.let { holder.bind(it) }
            }

            else -> Unit
        }
    }
}