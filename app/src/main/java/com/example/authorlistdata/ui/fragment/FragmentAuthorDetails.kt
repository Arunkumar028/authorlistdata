package com.example.authorlistdata.ui.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.authorlistdata.databinding.FragmentAuthorDetailsBinding
import com.example.authorlistdata.ui.activity.MainActivity


class FragmentAuthorDetails : Fragment() {

    private lateinit var binding: FragmentAuthorDetailsBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (requireActivity() as MainActivity).supportActionBar!!.hide()
        binding = FragmentAuthorDetailsBinding.inflate(inflater, container, false)

        var content =(arguments?.getString("content").toString())
        var photo_url =(arguments?.getString("photo_url").toString())
        var favoriteicon =(arguments?.getString("fav_icon"))

        binding.authorContent.text=content

        Glide.with(binding.authorProfilePicture.context)
            .load("https://message-list.appspot.com${photo_url}")
            .transform(CenterInside(), RoundedCorners(100))
            .into(binding.authorProfilePicture)

        if (favoriteicon?.equals("1") == true){
            binding.authorProfilePicture.visibility=View.VISIBLE
            binding.toolbar.favoriteSymbol.setBackgroundResource(com.example.authorlistdata.R.drawable.heart)
        }else{
            binding.toolbar.favoriteSymbol.visibility=View.GONE
        }

        binding.toolbar.backArrow.setOnClickListener {
            findNavController().navigate(com.example.authorlistdata.R.id.action_author_list)
        }
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onDestroyView() {
        super.onDestroyView()
      //  binding=null
    }



}