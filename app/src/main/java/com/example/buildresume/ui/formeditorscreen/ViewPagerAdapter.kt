package com.example.buildresume.ui.formeditorscreen

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle):FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 5
    }

    override fun createFragment(position: Int): Fragment {
      return  when(position){
            0->  EditProfileDetailsFragment()
            1->  EditEducationDetailsFragment()
            2->  EditSkillsDetailsFragment()
            3->  EditProjectDetailsFragment()
            4->  EditExperienceDetailsFragment()
            else->  EditProfileDetailsFragment()

        }
    }
}