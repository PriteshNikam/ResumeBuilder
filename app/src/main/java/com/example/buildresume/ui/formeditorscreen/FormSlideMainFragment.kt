package com.example.buildresume.ui.formeditorscreen

import android.content.ActivityNotFoundException
import android.content.Intent
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
            if (binding.tabLayoutFormSlide.selectedTabPosition == PROFILE_INDEX) {
                remove()
                requireActivity().onBackPressed()
            } else {
                binding.tabLayoutFormSlide.getTabAt(PROFILE_INDEX)?.select()
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
        binding = FragmentFormSlideMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
                    PROFILE_INDEX -> tab.text = getString(R.string.profile)
                    EDUCATION_INDEX -> tab.text = getString(R.string.education)
                    SKILLS_INDEX -> tab.text = getString(R.string.skills)
                    PROJECT_INDEX -> tab.text = getString(R.string.project)
                    EXPERIENCE_INDEX -> tab.text = getString(R.string.experience)
                }
            }.attach()
        }
    }

    private fun generatePDF() {
        binding.topAppBarFormSlide.setOnMenuItemClickListener { menuItem ->
            if (resumeViewModel.resume.isFormFilled()) {
                when (menuItem.itemId) {
                    R.id.download -> {
                        resumeViewModel.generatePdf(requireContext()) // library function
                        openPdf()
                        showToast(requireContext(), R.string.generate_pdf)
                        true
                    }
                    R.id.share -> {
                        resumeViewModel.generatePdf(requireContext())
                        sharePdf()
                        showToast(requireContext(), R.string.toast_share_pdf)
                        true
                    }
                    else -> false
                }
            } else {
                showToast(requireContext(), R.string.fill_all_details)
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
                requireContext().applicationContext.packageName.toString() + PROVIDER, file
            )
            target.setDataAndType(fileURI, getString(R.string.application_pdf))
            target.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            val intent = Intent.createChooser(target, getString(R.string.select_app))
            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                showToast(requireContext(), R.string.install_wps_app)
            }
        }
    }

    private fun sharePdf() {
        resumeViewModel.resume.run {
            val pdfName = "${userName}_${resumeTime}.pdf"
            val file = File(requireContext().getExternalFilesDir("/"), pdfName)
            val target = Intent(Intent.ACTION_SEND)
            val fileURI = FileProvider.getUriForFile(
                requireContext(),
                requireContext().applicationContext.packageName.toString() + PROVIDER, file
            )
            target.setDataAndType(fileURI, getString(R.string.application_pdf))
            target.putExtra(Intent.EXTRA_STREAM, fileURI)
            target.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            val intent = Intent.createChooser(target, getString(R.string.select_app_share))
            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                showToast(requireContext(), R.string.install_wps_app)
            }
        }
    }

    companion object {
        const val PROFILE_INDEX = 0
        const val EDUCATION_INDEX = 1
        const val SKILLS_INDEX = 2
        const val PROJECT_INDEX = 3
        const val EXPERIENCE_INDEX = 4
        const val PROVIDER = ".provider"
    }
}
