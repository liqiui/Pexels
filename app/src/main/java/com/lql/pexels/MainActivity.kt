package com.lql.pexels

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentTransaction
import com.lql.pexels.view.ImageListFragment
import com.lql.pexels.viewModel.SearchViewModel
import java.util.*
import kotlin.concurrent.schedule

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
    private lateinit var searchResultFragment: ImageListFragment
    private lateinit var searchResultViewModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.run {
            title = null
            setDisplayShowHomeEnabled(false)
            setDisplayShowTitleEnabled(false)
            setDisplayShowCustomEnabled(false)
            setDisplayUseLogoEnabled(false)
        }

        searchResultViewModel = SearchViewModel(1)
        searchResultFragment = ImageListFragment.newInstance(searchResultViewModel)
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_content, searchResultFragment)
            .setTransition(FragmentTransaction.TRANSIT_NONE)
            .commit()
    }

    private var searchView: SearchView? = null
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        searchView = menu?.findItem(R.id.action_search)?.let {
            it.actionView as? SearchView
        }?.apply {
            (getSystemService(Context.SEARCH_SERVICE) as? SearchManager)?.also {
                setSearchableInfo(it.getSearchableInfo(componentName))
            }
            maxWidth = Int.MAX_VALUE

            setIconifiedByDefault(false)
            isIconified = false
            isSubmitButtonEnabled = false
            setOnQueryTextListener(this@MainActivity)
            requestFocus()
            layoutParams = ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT)
        }
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return query?.let {
            if (it.length < 2) { return@let false }
            searchResultViewModel.query = it
            searchView?.clearFocus()
            true
        } ?: false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }
}
