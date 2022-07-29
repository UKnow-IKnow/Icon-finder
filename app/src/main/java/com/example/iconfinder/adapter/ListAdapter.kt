package com.example.iconfinder.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.iconfinder.BuildConfig
import com.example.iconfinder.R
import com.example.iconfinder.api.askForPermission
import com.example.iconfinder.api.downloadImage
import com.example.iconfinder.api.isPermissionGranted
import com.example.iconfinder.model.Icon
import com.example.iconfinder.utils.BASE_URL
import com.example.iconfinder.utils.CLIENT_ID
import com.example.iconfinder.utils.CLIENT_SECRET
import kotlinx.android.synthetic.main.item.view.*

class ListAdapter(var list: List<Icon>) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        )

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    fun submitList(list: List<Icon>) {
        this.list = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(position: Int) {
            val item = list[position]

            with(itemView) {
                if (item.rasterSizes.size > 6)
                    image.load(item.rasterSizes[6].formats[0].previewUrl) {
                        crossfade(true)
                        placeholder(R.drawable.ic_loading)
                    }
                else
                    image.load(item.rasterSizes[0].formats[0].previewUrl) {
                        crossfade(true)
                        placeholder(R.drawable.ic_loading)
                    }

                if (item.isPremium) {
                    image_paid.visibility = View.VISIBLE
                    download_btn.visibility = View.GONE
                    price.visibility = View.VISIBLE

                    if (item.prices.isNotEmpty())
                        price.text = "${item.prices[0].currency} ${item.prices[0].price}"

                } else {
                    image_paid.visibility = View.INVISIBLE
                    download_btn.visibility = View.VISIBLE
                    price.visibility = View.INVISIBLE

                    setButtonClick(item)
                }
            }
        }

        private fun View.setButtonClick(item: Icon) {
            download_btn.setOnClickListener {
                if (!isPermissionGranted(context)) {
                    askForPermission(context as Activity)
                } else {
                    val filePath = item.rasterSizes[0].formats[0].downloadUrl
                    val downloadUrl = getDownloadUrl("$BASE_URL$filePath")
                    downloadImage(context, downloadUrl)
                }
            }
        }

        private fun getDownloadUrl(baseUrl: String) =
            "$baseUrl?$CLIENT_ID=${BuildConfig.CLIENT_ID}&$CLIENT_SECRET=${BuildConfig.CLIENT_SECRET}"
    }
}