package com.example.hw6architecture.movielist

import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView

class MoviesListRecyclerDecorator(val itemOffset: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, itemPosition: Int, parent: RecyclerView) {
        super.getItemOffsets(outRect, itemPosition, parent)

        if (itemPosition > 1) {
            outRect.top = itemOffset
        }

        outRect.right = itemOffset
        outRect.left = itemOffset
    }
}