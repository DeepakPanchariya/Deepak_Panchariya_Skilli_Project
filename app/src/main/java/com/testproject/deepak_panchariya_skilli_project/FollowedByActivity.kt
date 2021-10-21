package com.testproject.deepak_panchariya_skilli_project

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso
import java.io.ByteArrayOutputStream
import java.util.*

class FollowedByActivity : AppCompatActivity() {
    lateinit var userImage:ImageView
    lateinit var titleText:TextView
    lateinit var publishAtText:TextView
    lateinit var commentText:TextView
    lateinit var clickImage: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_followed_by)
        intiValues()

        clickImage.setOnClickListener {
           shareImage()
        }
    }

    private fun intiValues() {
        clickImage=findViewById(R.id.clickPhoto)
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
    private fun shareImage() {

        val bMap = (userImage.drawable as BitmapDrawable).bitmap
        val uriImage=getImageUri(this,bMap)
       // val imageAddress=intent.getStringExtra("picture")
        val share = Intent(Intent.ACTION_SEND)
      //  val imageUri = Uri.parse(imageAddress.toString())
        share.type = "image/jpeg"

        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "IMG_" + Calendar.getInstance().time)
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        share.putExtra(Intent.EXTRA_STREAM, uriImage)
        startActivity(Intent.createChooser(share, "Share Image"))
    }
    fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        val currentDate=Date(System.currentTimeMillis())
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(
            inContext.contentResolver,
            inImage,
            "IMG_$currentDate",
            null
        )
        return Uri.parse(path)
    }
}