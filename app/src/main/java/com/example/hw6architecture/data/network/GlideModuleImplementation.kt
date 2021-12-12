package com.example.hw6architecture.data.network

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions
import com.example.hw6architecture.R
import com.example.hw6architecture.immutable_values.Constants

@GlideModule
class GlideModuleImplementation : AppGlideModule() {

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        super.applyOptions(context, builder)

        builder.apply {
            setDefaultRequestOptions(
                RequestOptions()
                    .format(DecodeFormat.PREFER_RGB_565)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
            )
        }
    }
    companion object {

        fun fillImageViewFromURI(view: ImageView, uri: String?, size: String) {

            GlideApp.with(view)
                .load("${ Constants.IMAGE_BASE_URL }${size}/${ uri }")
                .placeholder(R.drawable.ic_image_placeholder)
                .dontAnimate()
                .dontTransform()
                //.centerInside()
                .into(view)
        }
    }
}