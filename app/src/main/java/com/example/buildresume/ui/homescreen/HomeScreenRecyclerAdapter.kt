package com.example.buildresume.ui.homescreen

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.buildresume.R
import com.example.buildresume.data.Resume
import com.example.buildresume.databinding.SingleResumeViewBinding

class HomeScreenRecyclerAdapter (private val listener: IResumeAdapter) :
    RecyclerView.Adapter<HomeScreenRecyclerAdapter.ResumeViewHolder>() {

    private val resumeList by lazy { ArrayList<Resume>() }

    private var selectedResumePosition = -1

    inner class ResumeViewHolder(private val binding: SingleResumeViewBinding, val context: Context) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(currentResume: Resume, iResumeAdapter: IResumeAdapter) {
            binding.apply {
                if(currentResume.userName.isNotEmpty()){
                    textViewResumeUserNameHomeScreen.text = currentResume.userName
                }
                singleResumeViewConstraint.setBackgroundResource(R.drawable.single_view_shape)
                imageViewDeleteResume.visibility = View.GONE
            }
            itemView.setOnLongClickListener {
                if (selectedResumePosition == -1) {
                    selectedResumePosition = adapterPosition
                    Log.d("position","$adapterPosition")
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
                }else{
                    Toast.makeText(context.applicationContext, context.getString(R.string.not_possible), Toast.LENGTH_SHORT).show()
                }
                return@setOnLongClickListener true
            }
            binding.imageViewDeleteResume.visibility = View.GONE
            binding.textViewResumeUserNameHomeScreen.visibility = View.VISIBLE
            itemView.setOnClickListener {
                iResumeAdapter.onClickOpenResume(currentResume)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResumeViewHolder {
        val binding = SingleResumeViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ResumeViewHolder(binding, parent.context)
    }

    override fun onBindViewHolder(holder: ResumeViewHolder, position: Int) {
        val currentResume = resumeList[position]
        holder.bind(currentResume, iResumeAdapter = listener)
    }

    override fun getItemCount(): Int {
        return resumeList.size
    }

    fun deselectItem(){
        notifyItemChanged(selectedResumePosition)
        selectedResumePosition = -1
    }

    fun updateList(newList: List<Resume>) {
        resumeList.clear()
        resumeList.addAll(newList)
        //notifyItemRangeChanged(0,newList.size+1) // crash -> invalid item position.
        notifyDataSetChanged()
    }

    interface IResumeAdapter {
        fun onClickOpenResume(resume: Resume)
        fun onClickDeleteResume(resume: Resume)
        fun isItemSelected()
    }

}