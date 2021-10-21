package com.testproject.deepak_panchariya_skilli_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class FollowedByActivity : AppCompatActivity() {
    lateinit var userImage:ImageView
    lateinit var titleText:TextView
    lateinit var publishAtText:TextView
    lateinit var commentText:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_followed_by)
        intiValues()
    }

    private fun intiValues() {

        userImage=findViewById(R.id.userImageImgView)
        titleText=findViewById(R.id.nameTitleTV)
        publishAtText=findViewById(R.id.publishedAtTV)
        commentText=findViewById(R.id.commentTV)
        val imageAddress=intent.getStringExtra("picture")
        Picasso.get()
            .load(imageAddress)
            .placeholder(R.drawable.loading)
            .error(R.drawable.no_image_found)
            .into(userImage)
        titleText.text=intent.getStringExtra("title")
        publishAtText.text=intent.getStringExtra("publishAt")
        commentText.text=intent.getStringExtra("comment")
    }
}