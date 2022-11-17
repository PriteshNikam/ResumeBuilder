package com.example.buildresume.ui.formeditorscreen

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.buildresume.R
import com.example.buildresume.UtilClass.showToast
import com.example.buildresume.databinding.FragmentFormSlideMainBinding
import com.example.buildresume.viewmodel.ResumeViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.io.File

class FormSlideMainFragment : Fragment() {

    private lateinit var binding: FragmentFormSlideMainBinding

    private val resumeViewModel: ResumeViewModel by activityViewModels()

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (binding.tabLayoutFormSlide.selectedTabPosition == 0) {
                remove()
                requireActivity().onBackPressed()
            } else {
                binding.tabLayoutFormSlide.getTabAt(0)?.select()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFormSlideMainBinding.inflate(inflater, container, false)

        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        generatePDF()

        binding.topAppBarFormSlide.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.run {
            viewpagerFormSlide.adapter =
                ViewPagerAdapter(requireActivity().supportFragmentManager, lifecycle)
            tabLayoutFormSlide.tabMode = TabLayout.MODE_SCROLLABLE

            TabLayoutMediator(tabLayoutFormSlide, viewpagerFormSlide) { tab, position ->
                when (position) {
                    0 -> tab.text = "Profile"
                    1 -> tab.text = "Education"
                    2 -> tab.text = "Skills"
                    3 -> tab.text = "Project"
                    4 -> tab.text = "Experience"
                }
            }.attach()
        }
        return binding.root
    }

    private fun generatePDF() {
        binding.topAppBarFormSlide.setOnMenuItemClickListener { menuItem ->
            if (resumeViewModel.resume.isFormFilled()) {
                when (menuItem.itemId) {
                    R.id.download -> {
                        resumeViewModel.generatePdf(requireContext()) // library function
                        openPdf()
                        showToast(requireContext(),R.string.generate_pdf)
                        true
                    }else -> false
                }
            }else{
                showToast(requireContext(),R.string.fill_all_details)
                false
            }
        }
    }

    private fun openPdf() {
        resumeViewModel.resume.run {
            val pdfName = "${userName}_${resumeTime}.pdf"
            val file = File(requireContext().getExternalFilesDir("/"), pdfName)
            val target = Intent(Intent.ACTION_VIEW)
            val fileURI = FileProvider.getUriForFile(
                requireContext(),
                requireContext().applicationContext.packageName.toString() + ".provider", file
            )
            target.setDataAndType(fileURI, "application/pdf")
            target.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            val intent = Intent.createChooser(target, getString(R.string.select_app))
            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                showToast(requireContext(), R.string.install_wps_app)
            }
        }
    }

}
