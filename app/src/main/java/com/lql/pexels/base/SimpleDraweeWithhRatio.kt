package com.lql.pexels.base

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.facebook.drawee.generic.GenericDraweeHierarchy
import com.facebook.drawee.view.SimpleDraweeView

class SimpleDraweeWithRatio : SimpleDraweeView {
    constructor(context: Context, hierarchy: GenericDraweeHierarchy) : super(context, hierarchy) {}

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {}

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //16:9 ratio
        val widthPixels = View.MeasureSpec.getSize(widthMeasureSpec)

        val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)

        val heightPixels = Math.round(widthPixels * 0.562).toInt()
        val newHeightSpec = View.MeasureSpec.makeMeasureSpec(heightPixels, heightMode)

        super.onMeasure(widthMeasureSpec, newHeightSpec)
    }
}
