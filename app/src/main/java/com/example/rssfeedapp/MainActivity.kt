package com.example.rssfeedapp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.rssfeedapp.data.RSSFeedDatabaseBuilder
import com.example.rssfeedapp.data.RSSFeedRepository
import com.example.rssfeedapp.databinding.ActivityMainBinding
import com.example.rssfeedapp.model.Image
import com.example.rssfeedapp.model.RSS
import com.example.rssfeedapp.model.RSSFeed
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding
    private val rssFeedDao = RSSFeedDatabaseBuilder.getInstance().rssFeedDao()
    private val rssFeedRepository = RSSFeedRepository(rssFeedDao)
    private lateinit var editText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        editText = findViewById(R.id.addLinkTextInputEditText)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(
                    R.id.fragmentContainer,
                    RSSFeedsFragment.create(),
                    RSSFeedsFragment.TAG
                )
                .commit()
        }
    }

    fun requestCall(view: View) {
        val api = RSSFeedApi.create()
        val call = api.getRSS(editText.text.toString())

        call.enqueue(object : Callback<RSS> {
            override fun onResponse(call: Call<RSS>, response: Response<RSS>) {
                response.body()?.channel?.let { addRSSFeed(it.title, it.description, it.image) }
            }

            override fun onFailure(call: Call<RSS>, t: Throwable) {
                Log.e("TAG", "Failure" + t.message)
            }
        })
    }

    fun addRSSFeed(title: String, description: String, image: Image) {
        val rssFeed =
            RSSFeed(id = 0, title = title, description = description, imageURL = image.url)
        rssFeedRepository.insertFeed(rssFeed)
    }
}