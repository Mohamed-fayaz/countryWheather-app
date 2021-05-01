package com.example.countryweather

import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.countryweather.country.CountryProperty
import com.example.countryweather.listdata.CountryRvAdapter
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYouListener


//
@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let { val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()

        GlideToVectorYou
                .init()
                .with(imgView.context)
                .withListener(object : GlideToVectorYouListener{
                    override fun onLoadFailed() {
                        Toast.makeText(imgView.context, "Loading failed", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResourceReady() {

                    }
                }).setPlaceHolder(R.drawable.loading_animation,R.drawable.ic_broken_image)
                .load(imgUri, imgView)
    }
}

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<CountryProperty>?) {
    val adapter = recyclerView.adapter as CountryRvAdapter
    Log.i("data in adapter",data.toString())
    adapter.submitList(data)
}

//@BindingAdapter("marsApiStatus")
//fun bindStatus(statusImageView: ImageView, status: MarsApiStatus?) {
//    when (status) {
//        MarsApiStatus.LOADING -> {
//            statusImageView.visibility = View.VISIBLE
//            statusImageView.setImageResource(R.drawable.loading_animation)
//        }
//        MarsApiStatus.ERROR -> {
//            statusImageView.visibility = View.VISIBLE
//            statusImageView.setImageResource(R.drawable.ic_connection_error)
//        }
//        MarsApiStatus.DONE -> {
//            statusImageView.visibility = View.GONE
//        }
//    }
//}