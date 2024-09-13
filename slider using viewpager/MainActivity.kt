package com.example.webview

import android.content.Context
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.webkit.WebView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.example.webview.databinding.ActivityMainBinding
import com.example.webview.databinding.DummyBinding
import com.example.webview.databinding.PodcastPlaylistSliderItemLayoutBinding
import com.example.webview.databinding.PodcastPlaylistSliderLayoutBinding
import java.lang.ref.WeakReference
import kotlin.math.abs

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setDataAdapter()
    }

    private fun setDataAdapter() {
        binding?.podcastLandingRv?.adapter = PodcastLandingDataAdapter(
                this
            )
        }

}

class PodcastLandingDataAdapter(
    private val context: Context
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {

            0 -> {
                val binding = PodcastPlaylistSliderLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                PodcastPlaylistSliderViewHolder(binding)
            }

            else -> {
                val binding = DummyBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                DummyViewHolder(binding)
            }
        }
    }

    class DummyViewHolder(binding: DummyBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    class PodcastPlaylistSliderViewHolder(private val binding: PodcastPlaylistSliderLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            setUpViewPager()
        }

        private fun setUpViewPager() {
            binding.playlistSlider.apply {
                clipChildren = false  // No clipping the left and right items
                clipToPadding = false  // Show the viewpager in full width without clipping the padding
                offscreenPageLimit = 3  // Render the left and right items
                (getChildAt(0) as RecyclerView).overScrollMode =
                    RecyclerView.OVER_SCROLL_NEVER // Remove the scroll effect

                val compositePageTransformer = CompositePageTransformer()
                compositePageTransformer.addTransformer(MarginPageTransformer((15 * Resources.getSystem().displayMetrics.density).toInt()))
                compositePageTransformer.addTransformer { page, position ->
                    val r = 1 - abs(position)
                    page.scaleY = (0.80f + r * 0.20f)
                }
                setPageTransformer(compositePageTransformer)

                adapter =
                    PodcastPlaylistSliderAdapter()
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return 10
    }

}

class PodcastPlaylistSliderAdapter(
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = PodcastPlaylistSliderItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PodcastPlaylistSliderViewHolder(binding)
    }

    class PodcastPlaylistSliderViewHolder(private val binding: PodcastPlaylistSliderItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    }

    override fun getItemCount(): Int {
        return 5
    }

}