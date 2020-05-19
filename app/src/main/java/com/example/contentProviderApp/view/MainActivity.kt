package com.example.contentProviderApp.view

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider


import com.example.contentProviderApp.R
import com.example.contentProviderApp.contentProvider.AppContentProvider
import com.example.contentProviderApp.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        val cursorData =
            contentResolver.query(AppContentProvider.CONTENT_URI, null, null, null, null)
        if (cursorData == null) {
            Toast.makeText(this, "null", Toast.LENGTH_SHORT).show()
        } else {
            if (cursorData.moveToFirst()) {
                Toast.makeText(this, "Data already exist", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(
                    this,
                    "No data found in database. Calling api for fetching data",
                    Toast.LENGTH_LONG
                ).show()
                callApi()
                progressBar.visibility = View.VISIBLE
            }
            cursorData.close()

        }
    }

    @SuppressLint("CheckResult")
    private fun callApi() {
        viewModel.getDetails("5d565297300000680030a986")
        viewModel.responseData.observe(this, Observer { data ->
            if (data != null && data.isNotEmpty()) {
                data.forEach {
                    val values = ContentValues()
                    values.put("id", it.id)
                    values.put("name", it.name)
                    values.put("username", it.username)
                    values.put("email", it.email)
                    values.put("profileImage", it.profileImage)
                    if (it.address != null) {
                        values.put("street", it.address!!.street)
                        values.put("suite", it.address!!.suite)
                        values.put("zipCode", it.address!!.zipcode)
                        values.put("city", it.address!!.city)
                        values.put("lat", it.address!!.geo.lat)
                        values.put("lng", it.address!!.geo.lng)
                    }
                    values.put("phone", it.phone)
                    values.put("website", it.website)
                    if (it.company != null) {
                        values.put("companyName", it.company!!.name)
                        values.put("catchPhrase", it.company!!.catchPhrase)
                        values.put("bs", it.company!!.bs)
                    }
                    contentResolver.insert(AppContentProvider.CONTENT_URI, values)

                }
                progressBar.visibility = View.GONE
                Toast.makeText(
                    this,
                    "Data inserted to db.Check 2nd app for data",
                    Toast.LENGTH_SHORT
                ).show()

            }
        })

        viewModel.error.observe(this, Observer {
            if (it != null) {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })
    }
}
