package com.rogomi.atlasfx.common

import android.R
import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProgressBarHandler {

    private var mProgressBar: ProgressBar
    private var mContext: Context
    private var progressDialog: ProgressDialog? = null

    constructor(context: Context) {
        mContext = context
        progressDialog = ProgressDialog(context)

        val layout = (context as Activity).findViewById<View>(android.R.id.content).rootView as ViewGroup

        mProgressBar = ProgressBar(context, null, R.attr.progressBarStyle)
        mProgressBar.isIndeterminate = true
        val params = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT
        )

        val rl = RelativeLayout(context)

        rl.gravity = Gravity.CENTER
        rl.addView(mProgressBar)

        layout.addView(rl, params)

        hide()
    }



    fun hide() {
        mProgressBar.visibility = View.INVISIBLE
    }

    fun showProgressDialog() {
        Log.d("progreess", "home ishowing: " + progressDialog!!.isShowing)
        try {
            if (!progressDialog!!.isShowing) {
                progressDialog!!.setMessage("Loading...")
                progressDialog!!.setCancelable(false)
                progressDialog!!.show()
            }
        }catch(e: Exception){
            Log.d("error", e.localizedMessage)
        }

    }


    fun showProgressDialog(message: String) {
        if (!progressDialog!!.isShowing) {
            progressDialog!!.setMessage(message)
            progressDialog!!.setCancelable(false)
            progressDialog!!.show()
        }

    }


    fun hideProgressDialog() {
        try {
            if (progressDialog != null) {
                progressDialog!!.dismiss()
            }
        } catch (e: Exception) {
            Log.d("ProgressBar", "E: " + e.localizedMessage)
        }

    }


}