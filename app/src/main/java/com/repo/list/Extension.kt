package com.repo.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide


fun Context.showToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}

fun ImageView.loadImageFromUrl(url: String) {
    Glide.with(this.context).load(url).into(this)
}

fun View.visible(visible: Boolean) {
    this.visibility = if (visible) {
        VISIBLE
    } else {
        INVISIBLE
    }
}

fun Context.inflater(layoutId: Int, parent: ViewGroup, attachToRoot: Boolean = false): View =
    LayoutInflater.from(this).inflate(layoutId, parent, attachToRoot)
