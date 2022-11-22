package com.example.buildresume.ui.homescreen

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.buildresume.R
import com.example.buildresume.UtilClass.showLog
import com.example.buildresume.data.DataModel
import com.example.buildresume.data.Resume
import com.example.buildresume.data.WelcomeCard
import com.example.buildresume.databinding.HomescreenWelcomeViewBinding
import com.example.buildresume.databinding.SingleResumeViewBinding

class HomeScreenRecyclerAdapter(private val listener: IResumeAdapter) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val resumeList by lazy { ArrayList<DataModel>() }
    private var selectedResumePosition = -1

    inner class WelcomeViewHolder(
        private val binding: HomescreenWelcomeViewBinding,
        val context: Context
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.textViewUserNameSingleView.text = "Tony stark"
        }
    }

    inner class ResumeViewHolder(
        private val binding: SingleResumeViewBinding,
        val context: Context
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(currentResume: Resume, iResumeAdapter: IResumeAdapter) {
            binding.apply {
                if (currentResume.userName.isNotEmpty()) {
                    textViewResumeUserNameHomeScreen.text = currentResume.userName
                }
                singleResumeViewConstraint.setBackgroundResource(R.drawable.single_view_shape)
                imageViewDeleteResume.visibility = View.GONE
            }
            itemView.setOnLongClickListener {
                if (selectedResumePosition == -1) {
                    selectedResumePosition = adapterPosition
                    iResumeAdapter.isItemSelected()
                    binding.apply {
                        singleResumeViewConstraint.setBackgroundResource(R.drawable.delete_resume_shape)
                        textViewResumeUserNameHomeScreen.visibility = View.INVISIBLE
                        imageVIewResumeDocumentView.visibility = View.INVISIBLE
                        imageViewDeleteResume.visibility = View.VISIBLE
                        singleResumeViewConstraint.setOnClickListener {
                            singleResumeViewConstraint.setBackgroundResource(R.drawable.single_view_shape)
                            textViewResumeUserNameHomeScreen.visibility = View.VISIBLE
                            imageVIewResumeDocumentView.visibility = View.VISIBLE
                            imageViewDeleteResume.visibility = View.GONE
                            iResumeAdapter.onClickDeleteResume(currentResume)
                            selectedResumePosition = -1
                        }
                    }
                } else {
                    Toast.makeText(
                        context.applicationContext,
                        context.getString(R.string.not_possible),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                return@setOnLongClickListener true
            }
            binding.imageVIewResumeDocumentView.visibility = View.VISIBLE
            binding.imageViewDeleteResume.visibility = View.GONE
            binding.textViewResumeUserNameHomeScreen.visibility = View.VISIBLE
            itemView.setOnClickListener {
                iResumeAdapter.onClickOpenResume(currentResume)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        showLog("viewType", "$viewType")
        when (viewType) {
            R.layout.homescreen_welcome_view -> {
                val welcomeViewBinding = HomescreenWelcomeViewBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return WelcomeViewHolder(welcomeViewBinding, parent.context)
            }
            R.layout.single_resume_view -> {
                val recyclerViewBinding = SingleResumeViewBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return ResumeViewHolder(recyclerViewBinding, parent.context)
            }
            else -> {
                throw IllegalArgumentException("Invalid ViewType Provider")
            }
        }
    }

    override fun getItemCount(): Int = resumeList.size

    fun deselectItem() {
        notifyItemChanged(selectedResumePosition)
        selectedResumePosition = -1
    }

    fun updateList(newList: List<Resume>) {
        resumeList.clear()
        resumeList.addAll(newList)
        notifyDataSetChanged()
    }

    interface IResumeAdapter {
        fun onClickOpenResume(resume: Resume)
        fun onClickDeleteResume(resume: Resume)
        fun isItemSelected()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is WelcomeViewHolder -> {
                val viewHolder = holder as WelcomeViewHolder
                viewHolder.bind()
            }
            is ResumeViewHolder -> {
                val currentResume = resumeList[position] as Resume
                val viewHolder = holder as ResumeViewHolder
                viewHolder.bind(currentResume, iResumeAdapter = listener)
            }

        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (resumeList[position]) {
            is WelcomeCard -> R.layout.homescreen_welcome_view
            else -> R.layout.single_resume_view
        }
    }


}