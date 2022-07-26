package com.example.authorlistdata.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.authorlistdata.R
import com.example.authorlistdata.databinding.FragmentAuthorBinding
import com.example.authorlistdata.ui.activity.MainActivity
import com.example.authorlistdata.ui.adapter.MessageAdapter
import com.example.authorlistdata.utils.Status
import com.example.authorlistdata.viewmodel.MainViewModel
import com.example.mvvmretrofitcoroutines.data.Authorentity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentAuthor : Fragment(),
    MessageAdapter.Favoriteseleted,
    MessageAdapter.Deletedseleted,SwipeRefreshLayout.OnRefreshListener
{

    private lateinit var binding: FragmentAuthorBinding
    // onDestroyView.
    private val _binding get() = binding!!
    private lateinit var messageAdapter: MessageAdapter
    val mainviewmodel: MainViewModel by viewModels()
    private var filter = ArrayList<Authorentity>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {
        binding = FragmentAuthorBinding.inflate(inflater, container, false)

        SetUpUi()
        SetUpObserver()
        val view= _binding.root
        return view
    }

    private fun SetUpObserver() {
        mainviewmodel.getMessage().observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.recyclerView.visibility = View.VISIBLE
                    renderList(it.data as ArrayList)
                    filter = it.data as ArrayList<Authorentity>
                    binding.progressDialog.visibility = View.GONE
                }
                Status.ERROR -> {
                    Toast.makeText(activity, it.toString(), Toast.LENGTH_SHORT).show()
                }
                Status.LOADING -> {
                    binding.progressDialog.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                }
            }
        }
    }
    private fun renderList(message: MutableList<Authorentity>) {
        messageAdapter.addData(message)
        messageAdapter.notifyDataSetChanged()
    }

    private fun SetUpUi() {
        messageAdapter = MessageAdapter(ArrayList(), this, this)
        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = messageAdapter
        }
        binding.headerViews.searchView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                filtername(s.toString().lowercase())
            }

        })

        binding.mSwipeRefreshLayout.setOnRefreshListener {
            SetUpObserver()
            binding.mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    private fun filtername(str: String) {
        val filterlist = ArrayList<Authorentity>()
        for (name in filter) {
            if (name.name?.lowercase()?.contains(str) == true) {
                filterlist.add(name)
            }
            messageAdapter.setFilter(filterlist)
            messageAdapter.notifyDataSetChanged()
        }
        if (!str.isEmpty()) {
            binding.headerViews.relativelayout.visibility = View.VISIBLE
            binding.headerViews.searchCountTextview.text =
                filterlist.size.toString() + " Result"
        } else {
            binding.headerViews.relativelayout.visibility = View.GONE
        }
    }

    override fun Favseleted(favItem: Authorentity, position: Int, str: String) {
        if (str.equals("update")) {
            var name = favItem.name
            var updated = favItem.updated
            var url = favItem.photoUrl
            var id = favItem.id
            var content = favItem.content
            var favoriteicon = "0"
            if (favItem.favoriteicon.equals("0")) {
                favoriteicon = "1"
            } else {
                favoriteicon = "0"
            }
            var updatedata = Authorentity(id, name, updated, url, content, favoriteicon.toString())
            mainviewmodel.updatedata(updatedata)
            messageAdapter.updatedata(position,updatedata)

        } else {
            val bundle = Bundle()
            bundle.putString("content", favItem.content)
            bundle.putString("photo_url", favItem.photoUrl)
            bundle.putString("fav_icon", favItem.favoriteicon)
            findNavController().navigate(R.id.action_author_details,bundle)
        }
    }

    override fun Deletedseleted(favItem: Authorentity, position: Int) {
        mainviewmodel.deletedata(favItem)
        messageAdapter.delete(favItem)
    }

    override fun onResume() {
        super.onResume()
         (requireActivity() as MainActivity).supportActionBar!!.show()
    }

    override fun onRefresh() {
        Toast.makeText(activity, "Refresh", Toast.LENGTH_SHORT).show()
        Handler().postDelayed(Runnable {
            binding.mSwipeRefreshLayout.setRefreshing(false)
                                       }, 2000)
    }

    override fun onDestroyView() {
        super.onDestroyView()
      //  messageAdapter=null
            //  binding=null

    }

}