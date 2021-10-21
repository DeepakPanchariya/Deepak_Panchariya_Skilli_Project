package com.testproject.deepak_panchariya_skilli_project.Adaptor

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.testproject.deepak_panchariya_skilli_project.DataClass.DiscriptionList
import com.testproject.deepak_panchariya_skilli_project.FollowedByActivity
import com.testproject.deepak_panchariya_skilli_project.R


class RecyclerAdaptor(private var context: Context,
                      private var exampleList: List<DiscriptionList>

): RecyclerView.Adapter<RecyclerAdaptor.MyViewHolder>() {






    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var userTitle:TextView = itemView.findViewById(R.id.userTitle)
        var userImage:ImageView = itemView.findViewById(R.id.userImageImgView)
        var fullWindow:LinearLayout = itemView.findViewById(R.id.fullWindow)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.single_recycler_item,
                parent,
                false
            )
        )
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.setIsRecyclable(false)
        val currentItem=exampleList[position]
      //  holder.shimmer.start(holder.Gamename)
       holder.userTitle.text=currentItem.title
        Picasso.get()
            .load(currentItem.picture)
            .placeholder(R.drawable.loading)
            .error(R.drawable.no_image_found)
            .into(holder.userImage)
      //  holder.userImage.setImageURI(Uri.parse(currentItem.picture))
        holder.fullWindow.setOnClickListener {
            val intent= Intent(context, FollowedByActivity::class.java)
            intent.putExtra("comment",currentItem.comment)
            intent.putExtra("picture",currentItem.picture)
            intent.putExtra("publishAt",currentItem.publishAt)
            intent.putExtra("title",currentItem.title)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }







    }

    override fun getItemCount(): Int {
        return exampleList.size
    }






}

