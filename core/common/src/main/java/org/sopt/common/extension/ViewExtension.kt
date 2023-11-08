package org.sopt.common.extension

import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import coil.ImageLoader
import coil.imageLoader
import coil.load
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation

class ItemDiffCallback<T : Any>(
    val onItemsTheSame: (T, T) -> Boolean,
    val onContentsTheSame: (T, T) -> Boolean,
) : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(
        oldItem: T,
        newItem: T,
    ): Boolean = onItemsTheSame(oldItem, newItem)

    override fun areContentsTheSame(
        oldItem: T,
        newItem: T,
    ): Boolean = onContentsTheSame(oldItem, newItem)
}

fun ImageView.loadCircularImage(
    data: Any?,
    imageLoader: ImageLoader = context.imageLoader,
    builder: ImageRequest.Builder.() -> Unit = {},
) {
    this.load(data, imageLoader) {
        transformations(CircleCropTransformation())
        builder()
    }
}
