package com.example.cuisineapp.ui.fragments.home

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class FilterChipDecor: RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)


        val position = parent.getChildAdapterPosition(view)
        val totalItem = state.itemCount

        if (position == 0) {
            outRect.left = 16
        }
        if(position == totalItem-1) {
            outRect.right = 16
        }
    }
}