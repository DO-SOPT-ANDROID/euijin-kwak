package org.sopt.doeuijin.feature.home.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.sopt.doeuijin.databinding.ItemFriendProfileBinding
import org.sopt.doeuijin.databinding.ItemMyProfileBinding

class ProfileAdapter : RecyclerView.Adapter<ProfileViewHolder>() {

    enum class ProfileType(val viewType: Int) {
        MY_PROFILE(0),
        FRIEND_PROFILE(1),
    }

    private val friendList: ArrayList<Profile> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        return when (viewType) {
            ProfileType.MY_PROFILE.viewType -> {
                ProfileViewHolder.MyProfileViewHolder(ItemMyProfileBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            }

            else -> {
                ProfileViewHolder.FriendProfileViewHolder(ItemFriendProfileBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (friendList[position]) {
            is Profile.MyProfile -> ProfileType.MY_PROFILE.viewType
            is Profile.FriendProfile -> ProfileType.FRIEND_PROFILE.viewType
        }
    }

    override fun getItemCount(): Int = friendList.size

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        when (holder) {
            is ProfileViewHolder.MyProfileViewHolder -> {
                (friendList[position] as? Profile.MyProfile)?.let { holder.bind(it) }
            }

            is ProfileViewHolder.FriendProfileViewHolder -> {
                (friendList[position] as? Profile.FriendProfile)?.let { holder.bind(it) }
            }
        }
    }

    fun setProfileList(list: List<Profile>) {
        friendList.clear()
        friendList.addAll(list.toList())
    }
}