package com.testproject.deepak_panchariya_skilli_project

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.testproject.deepak_panchariya_skilli_project.Adaptor.RecyclerAdaptor
import com.testproject.deepak_panchariya_skilli_project.DataClass.DiscriptionList
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    var imageArrayList:ArrayList<DiscriptionList> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initValues()

    }

    private fun initValues() {
        recyclerView=findViewById(R.id.recyclerView)
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = llm
        retrofitgamedata()


    }
    private fun retrofitgamedata(){
        imageArrayList.clear()

        val jsonvalues= JsonObject()

        jsonvalues.addProperty("env_type", "none")
       // jsonvalues.addProperty("unique_token", session.getSession_userid())

        //retrofit client calling
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
                    val adaptercalling = RecyclerAdaptor(
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
}