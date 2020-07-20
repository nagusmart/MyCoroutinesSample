package com.example.mycoroutinessample.ui.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mycoroutinessample.R
import com.example.mycoroutinessample.data.model.User
import com.example.mycoroutinessample.databinding.ActivityMainBinding
import com.example.mycoroutinessample.ui.base.ViewModelFactory
import com.example.mycoroutinessample.ui.main.adapter.UserListAdapter
import com.example.mycoroutinessample.ui.main.viewmodel.MainViewModel
import com.example.mycoroutinessample.util.Status
import com.example.recyclerviewex.ApiClient
import com.example.recyclerviewex.ApiService
import com.google.gson.JsonObject

class MainActivity : AppCompatActivity() {

    lateinit var mainBinding: ActivityMainBinding

    var userList = ArrayList<User>()


    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: UserListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        mainBinding.mainActivity = this

        setupViewModel()

        setupUI()

        setUpObserver()

    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiClient.apiClient().create(ApiService::class.java))
        ).get(MainViewModel::class.java)
    }

    private fun setupUI() {
        mainBinding.recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        mainBinding.recyclerView.setItemAnimator(DefaultItemAnimator())

        adapter = UserListAdapter(userList, object : UserListAdapter.ClicListener {

            override fun update(position: Int) {

                openUpdateMessageDialog(userList.get(position), position)
                Toast.makeText(this@MainActivity, userList.get(position).title, Toast.LENGTH_SHORT)
                    .show()
            }

            override fun delete(position: Int) {

                deleteMesssageApi(userList.get(position), position)

            }
        })

        //now adding the adapter to recyclerview
        mainBinding.recyclerView.adapter = adapter
    }

    fun addUser() {
        openAddMessageDialog()
    }

    private fun openAddMessageDialog() {
        val addMessageDialog = AddMessageDialogFrag()
        addMessageDialog.setListener(object : AddMessageDialogFrag.ItemClickListener {
            override fun addMessage(name: String, title: String, message: String) {
                addMessageApi(name, title, message)
            }
        })
        addMessageDialog.show(supportFragmentManager, "Add Message Dialog")

    }

    private fun openUpdateMessageDialog(user: User, position: Int) {
        val updateMessageDialogFrag = UpdateMessageDialogFrag.newInstance(user)
        updateMessageDialogFrag.setListener(object : UpdateMessageDialogFrag.ItemClickListener {
            override fun updateMessage(id: String, name: String, title: String, message: String) {
                updateMessageApi(id, name, title, message, position)
            }
        })
        updateMessageDialogFrag.show(supportFragmentManager, "Update Message Dialog")

    }

    private fun setUpObserver() {

        viewModel.getUsers().observe(this, Observer {

            it?.let { resource ->
                when (resource.status) {

                    Status.SUCCESS -> {
                        showProgress(false)
                        resource.data?.let { users -> retrieveList(users) }
                    }
                    Status.ERROR -> {
                        showProgress(false)
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        showProgress(true)
                    }
                }

            }
        })
    }

    private fun retrieveList(users: List<User>) {
        if (users.size > 0) {
            userList.addAll(users)
            adapter.notifyDataSetChanged()
        }
    }

    private fun addMessageApi(name: String, title: String, message: String) {

        var map = HashMap<String, String>()
        map.put("name", name)
        map.put("title", title)
        map.put("message", message)

        viewModel.createUser(map).observe(this, Observer {

            it?.let { resource ->
                when (resource.status) {

                    Status.SUCCESS -> {
                        showProgress(false)
                        resource.data?.let { user -> addNewUser(user) }
                    }
                    Status.ERROR -> {
                        showProgress(false)
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        showProgress(true)
                    }
                }

            }
        })
    }

    private fun addNewUser(user: User) {
        userList.add(user)
        adapter.notifyDataSetChanged()
    }

    private fun updateMessageApi(
        id: String,
        name: String,
        title: String,
        message: String,
        position: Int
    ) {

        var map = HashMap<String, String>()
        map.put("id", id)
        map.put("name", name)
        map.put("title", title)
        map.put("message", message)

        viewModel.updateUser(map).observe(this, Observer {

            it?.let { resource ->
                when (resource.status) {

                    Status.SUCCESS -> {
                        showProgress(false)
                        resource.data?.let { user -> updateUser(user, position) }
                    }
                    Status.ERROR -> {
                        showProgress(false)
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        showProgress(true)
                    }
                }

            }
        })

    }

    private fun updateUser(user: User, position: Int) {
        userList.set(position, user)
        adapter.notifyItemChanged(position)
    }

    private fun deleteMesssageApi(user: User, position: Int) {

        var map = HashMap<String, String>()
        map.put("id", user.id)

        viewModel.deleteUser(map).observe(this, Observer {

            it?.let { resource ->
                when (resource.status) {

                    Status.SUCCESS -> {
                        showProgress(false)
                        deleteUser(position)
                    }
                    Status.ERROR -> {
                        showProgress(false)
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        showProgress(true)
                    }
                }

            }
        })
    }

    private fun deleteUser(position: Int) {
        userList.removeAt(position)
        adapter.notifyItemRemoved(position)
    }

    private fun showProgress(status: Boolean) {
        if (status) {
            mainBinding.showProgress.visibility = View.VISIBLE
        } else {
            mainBinding.showProgress.visibility = View.GONE
        }
    }
}
