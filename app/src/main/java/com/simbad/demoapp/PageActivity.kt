package com.simbad.demoapp

import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity

class PageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_page)
        initView()
    }

    private fun initView() {
        val view: WebView? = findViewById(R.id.WebView);
        val html = "<a href=\"simbad://home\">back</a>"
        view?.loadData(html, "text/html", "UTF-8");
    }
}