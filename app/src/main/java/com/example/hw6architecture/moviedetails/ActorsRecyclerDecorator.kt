package com.example.hw6architecture.moviedetails

import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView

class ActorsRecyclerDecorator(val itemOffset: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, itemPosition: Int, parent: RecyclerView) {
        super.getItemOffsets(outRect, itemPosition, parent)

        if (itemPosition != 0) {

            outRect.left = itemOffset
        }
        outRect.bottom = itemOffset
    }
}