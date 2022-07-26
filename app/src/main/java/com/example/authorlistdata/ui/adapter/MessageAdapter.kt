package com.example.authorlistdata.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.authorlistdata.R
import com.example.authorlistdata.databinding.AdapterMovieBinding
import com.example.mvvmretrofitcoroutines.data.Authorentity


class MessageAdapter(
    private var list: ArrayList<Authorentity>,
    private var favoriteseleted: Favoriteseleted,
    private var deletedseleted: Deletedseleted

) : RecyclerView.Adapter<MessageAdapter.ViewHolder>() {


    inner class ViewHolder(var binding: AdapterMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: Authorentity) {

            binding.authorName.text = user.name
            binding.authorYear.text = user.updated

            binding.favoriteIcon.setOnClickListener {
                favoriteseleted.Favseleted(user, position, "update")
                if (user.favoriteicon.equals("0")) {
                    binding.favoriteIcon.setBackgroundResource(R.drawable.favorite_icon);
                    notifyDataSetChanged()
                }
                else {
                    binding.favoriteIcon.setBackgroundResource(R.drawable.heart);
                    notifyDataSetChanged()
                }
            }

            if (user.favoriteicon.equals("0")) {
                binding.favoriteIcon.setBackgroundResource(R.drawable.favorite_icon);
            } else {
                binding.favoriteIcon.setBackgroundResource(R.drawable.heart);
            }
            binding.deleteIcon.setOnClickListener {
                deletedseleted.Deletedseleted(user, position)
            }
            Glide.with(binding.authorProfile.context)
                .load("https://message-list.appspot.com${user.photoUrl}")
                .transform(CenterInside(), RoundedCorners(100))
                .into(binding.authorProfile)

            binding.constraint.setOnClickListener{
                favoriteseleted.Favseleted(user, position, "next")
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            AdapterMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount():
            Int = list.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun addData(message: MutableList<Authorentity>) {
        this.list = message as ArrayList<Authorentity>
        notifyDataSetChanged()

    }

    fun delete(deleteItem: Authorentity) {
        list.remove(deleteItem)
        notifyDataSetChanged()
    }

    fun setFilter(filterlist: java.util.ArrayList<Authorentity>) {
        this.list = filterlist
    }

    fun updatedata(position: Int, favItem: Authorentity) {
        list.set(position,favItem)
        notifyDataSetChanged()

    }

    interface Favoriteseleted {
        fun Favseleted(favItem: Authorentity, position: Int, s: String)
    }

    interface Deletedseleted {
        fun Deletedseleted(favItem: Authorentity, position: Int)
    }
}
