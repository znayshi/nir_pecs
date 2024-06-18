package com.cmi.presentation.config

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.cmi.presentation.R
import com.cmi.presentation.config.edit.CategorySelectOptions
import com.cmi.presentation.databinding.FragmentConfigurationOptionBinding
import com.cmi.presentation.ktx.setSafeOnClickListener
import com.cmi.presentation.ktx.setUpNavigation

enum class FLOW {
    CATEGORY, PICTOGRAM
}

class ConfigurationOptionFragment : Fragment() {

    private var _binding: FragmentConfigurationOptionBinding? = null
    private val binding get() = _binding!!

    private val args: ConfigurationOptionFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConfigurationOptionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() = with(binding) {
        lyToolbar.txtTitle.text = getString(R.string.text_toolbar_name_format,
            getString(R.string.text_settings),
            getString(if (args.flow == FLOW.PICTOGRAM) R.string.text_pictogram else R.string.text_category))
        lyToolbar.toolbar.setUpNavigation {
            findNavController().popBackStack()
        }

        setUpNames()

        cardViewSelect.setSafeOnClickListener { navigateToSelectScreen() }
        cardViewAdd.setSafeOnClickListener { navigateToAddScreen() }
        cardViewEdit.setSafeOnClickListener { navigateToEditScreen() }
        cardViewDelete.setSafeOnClickListener { navigateToDeleteScreen() }
    }

    private fun setUpNames() = with(binding) {
        val nameResId = if (args.flow == FLOW.PICTOGRAM) R.string.text_pictogram else R.string.text_category
        val name = getString(nameResId)
        txtSelect.text = getString(R.string.text_select_format, name)
        txtAdd.text = getString(R.string.text_add_format, name)
        txtEdit.text = getString(R.string.text_edit_format, name)
        txtDelete.text = getString(R.string.text_delete_format, name)
    }

    private fun navigateToSelectScreen() {
        val directions = when (args.flow) {
            FLOW.PICTOGRAM -> ConfigurationOptionFragmentDirections.actionConfigurationOptionFragmentToSelectCategoryFragment(
                CategorySelectOptions.SELECT_PICTOGRAM_FOR_PECS
            )
            FLOW.CATEGORY -> ConfigurationOptionFragmentDirections.actionConfigurationOptionFragmentToSelectCategoryForPecsFragment()
        }
        findNavController().navigate(directions)
    }

    private fun navigateToAddScreen() {
        val directions = when (args.flow) {
            FLOW.PICTOGRAM -> ConfigurationOptionFragmentDirections.actionConfigurationOptionFragmentToAddPictogramFragment()
            FLOW.CATEGORY -> ConfigurationOptionFragmentDirections.actionConfigurationOptionFragmentToAddCategoryFragment()
        }
        findNavController().navigate(directions)
    }

    private fun navigateToEditScreen() {
        val directions = when (args.flow) {
            FLOW.PICTOGRAM -> ConfigurationOptionFragmentDirections.actionConfigurationOptionFragmentToSelectCategoryFragment(
                CategorySelectOptions.EDIT_PICTOGRAM
            )
            FLOW.CATEGORY -> ConfigurationOptionFragmentDirections.actionConfigurationOptionFragmentToSelectCategoryFragment(
                CategorySelectOptions.EDIT_CATEGORY
            )
        }
        findNavController().navigate(directions)
    }

    private fun navigateToDeleteScreen() {
        val directions = when (args.flow) {
            FLOW.PICTOGRAM -> ConfigurationOptionFragmentDirections.actionConfigurationOptionFragmentToSelectCategoryFragment(
                CategorySelectOptions.DELETE_PICTOGRAM
            )
            FLOW.CATEGORY -> ConfigurationOptionFragmentDirections.actionConfigurationOptionFragmentToDeleteCategoryFragment()
        }
        findNavController().navigate(directions)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
