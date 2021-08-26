package com.brianmartone.comicdisplay.ui.main

import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.brianmartone.comicdisplay.R
import com.brianmartone.service.marvel.network.dto.MarvelImageVariant
import com.squareup.picasso.Picasso

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.comicDisplayData.observe(this) { displayData ->
            this.view?.findViewById<ProgressBar>(R.id.progressBar)?.visibility = GONE
            this.view?.findViewById<TextView>(R.id.comicLabel)?.let {
                it.visibility = VISIBLE
                it.text = displayData.title
            }
            displayData.coverImageUrl?.let { Picasso.get().also { pic -> pic.isLoggingEnabled = true }.load(it.toExternalForm()).into(this.view?.findViewById(R.id.comicCoverImage)) }
        }
        viewModel.getComic(79809, MarvelImageVariant.FULL)
    }
}
