package com.amar.peoplelistapp.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.amar.peoplelistapp.R
import com.amar.peoplelistapp.listeners.OnLikeListener
import com.amar.peoplelistapp.model.PersonData
import com.bumptech.glide.Glide

class PersonRecyclerViewAdapter(private var context: Context, private var dataSet: List<PersonData>, private var listener: OnLikeListener) : RecyclerView.Adapter<PersonRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.person_list_item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val person = dataSet.get(position)

        viewHolder.nameTV.text = person.firstName
        viewHolder.descTV.text = person.topic
        viewHolder.nativeTV.text = person.natives?.get(0)?.uppercase() ?: ""
        viewHolder.learnTV.text = person.learns?.get(0)?.uppercase() ?: ""

        Glide.with(viewHolder.image).load(person.pictureUrl).into(viewHolder.image);

        viewHolder.newTV.visibility = if (person.referenceCnt == 1) View.VISIBLE else View.GONE

        viewHolder.likedImage.setImageDrawable(ContextCompat.getDrawable(context, if (person.isLiked) R.drawable.ic_liked else R.drawable.ic_unliked))

        viewHolder.likedImage.setOnClickListener {
            person.isLiked = !person.isLiked

            viewHolder.likedImage.setImageDrawable(ContextCompat.getDrawable(context, if (person.isLiked) R.drawable.ic_liked else R.drawable.ic_unliked))

            listener.onLiked(person)
        }
    }

    override fun getItemCount() = dataSet.size

    fun setItems(list: List<PersonData>) {
        dataSet = list
        notifyItemInserted(dataSet.size - 1)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTV: TextView
        val descTV: TextView
        val newTV: TextView
        val nativeTV: TextView
        val learnTV: TextView
        val image: AppCompatImageView
        val likedImage: AppCompatImageView

        init {
            nameTV = view.findViewById(R.id.nameTV)
            descTV = view.findViewById(R.id.descTV)
            newTV = view.findViewById(R.id.newTV)
            nativeTV = view.findViewById(R.id.nativeTV)
            learnTV = view.findViewById(R.id.learnTV)
            image = view.findViewById(R.id.image)
            likedImage = view.findViewById(R.id.likedImage)
        }
    }

}