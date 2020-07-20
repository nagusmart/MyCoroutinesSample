package com.example.mycoroutinessample.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mycoroutinessample.R
import com.example.mycoroutinessample.data.model.User

class UserListAdapter(val userList: ArrayList<User>, val mListener: ClicListener) :
    RecyclerView.Adapter<UserListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_layout, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(userList.get(position))
    }

    interface ClicListener {
        fun update(position: Int)
        fun delete(position: Int)

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {


        fun bindItems(user: User) {

            val name = itemView.findViewById<TextView>(R.id.name)
            val title = itemView.findViewById<TextView>(R.id.title)
            val message = itemView.findViewById<TextView>(R.id.message)

            val update = itemView.findViewById<ImageView>(R.id.update)
            val delete = itemView.findViewById<ImageView>(R.id.delete)

            name.text = user.name
            title.text = user.title
            message.text = user.message

            update.setOnClickListener(this)
            delete.setOnClickListener(this)
        }

        override fun onClick(view: View?) {

            if (view?.id == R.id.update)
                mListener.update(adapterPosition)
            if (view?.id == R.id.delete)
                mListener.delete(adapterPosition)

        }

    }
}