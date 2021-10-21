package com.testproject.deepak_panchariya_skilli_project

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.testproject.deepak_panchariya_skilli_project.Adaptor.RecyclerAdaptor
import com.testproject.deepak_panchariya_skilli_project.DataClass.DiscriptionList
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    lateinit var adaptercalling:RecyclerAdaptor
    private val CAMERA_REQUEST = 1888
    val imageCaptureCode = 2005
    lateinit var clickImage: FloatingActionButton
    lateinit var recyclerView: RecyclerView
    var imageArrayList:ArrayList<DiscriptionList> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initValues()

        clickImage.setOnClickListener {
            dispatchTakePictureIntent()
        }

    }

    private fun initValues() {
        clickImage=findViewById(R.id.clickPhoto)
        recyclerView=findViewById(R.id.recyclerView)
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = llm
        retrofitgamedata()


    }
    private fun dispatchTakePictureIntent() {
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(arrayOf(Manifest.permission.CAMERA), CAMERA_REQUEST)
        } else {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, imageCaptureCode)

        }
    }
    private fun retrofitgamedata(){
        imageArrayList.clear()

        val jsonvalues= JsonObject()
        jsonvalues.addProperty("none", "none")
        Log.e("Sending",jsonvalues.toString())
        RetrofitClient.service.userGetData(jsonvalues).enqueue(
            object : Callback<JsonArray> {

                override fun onResponse(call: Call<JsonArray>, response: Response<JsonArray>) {
                    if (response.isSuccessful) {
                        Log.e("Data",response.body().toString())
                        val responseData=response.body()!!
                        var i=0

                        while(i<responseData.size()){
                            val jsonObject:JsonObject=responseData.get(i) as JsonObject

                        val comment = jsonObject.get("comment")?.asString.toString()
                        val picture = jsonObject.get("picture")?.asString.toString()
                        val id = jsonObject.get("_id")?.asString.toString()
                        val title = jsonObject.get("title")?.asString.toString()
                        val publishedAt = jsonObject.get("publishedAt")?.asString.toString()




                        imageArrayList.add(
                            DiscriptionList(
                                comment, picture, id, publishedAt, title
                            )
                        )

                       ++i

                        }
                        Log.e("values",imageArrayList.toString())


//                        val comment = response.body()?.get("comment")?.asString.toString()
//                        val picture = response.body()?.get("picture")?.asString.toString()
//                        val id = response.body()?.get("_id")?.asString.toString()
//                        val title = response.body()?.get("title")?.asString.toString()
//                        val publishedAt = response.body()?.get("publishedAt")?.asString.toString()
//
//
//
//
//                        imageArrayList.add(
//                            DiscriptionList(
//                                comment, picture, id, publishedAt, title
//                            )
//                        )
//
//
//
                    adaptercalling = RecyclerAdaptor(
                        applicationContext,
                        imageArrayList
                    )
                    recyclerView.setHasFixedSize(true)
                    adaptercalling.notifyDataSetChanged()
                    recyclerView.adapter = adaptercalling


                } else {

                            Toast.makeText(
                                applicationContext,
                                "No Record Found!",
                                Toast.LENGTH_LONG
                            ).show()


                        }


                    }

                override fun onFailure(call: Call<JsonArray>, t: Throwable) {
                    Toast.makeText(
                        applicationContext,
                        "No connection with host"+t.message,
                        Toast.LENGTH_LONG
                    ).show()
                }

            })



    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == imageCaptureCode && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            val imagePath=getImageUri(this,imageBitmap)
            imageArrayList.add(0,
                DiscriptionList("User image added from camera",imagePath.toString(),"124578",Date(System.currentTimeMillis()).toString(),"Camera Added")
            )
            adaptercalling.notifyDataSetChanged()
//            val adaptercalling = RecyclerAdaptor(
//                applicationContext,
//                imageArrayList
//            )
//            recyclerView.setHasFixedSize(true)
//            adaptercalling.notifyDataSetChanged()
//            recyclerView.adapter = adaptercalling



        }
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