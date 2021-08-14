package com.architectcoders.arquitectomarvel.ui.main

import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.architectcoders.arquitectomarvel.R
import com.architectcoders.arquitectomarvel.databinding.DialogFragmentPasswordBinding
import com.architectcoders.arquitectomarvel.ui.common.EXTRA_PASSWORD_STATE_DIALOG_FRAGMENT
import com.architectcoders.arquitectomarvel.ui.common.putEnum
import com.architectcoders.arquitectomarvel.ui.main.MainViewModel.PasswordState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PasswordDialogFragment : DialogFragment() {

    private var _binding: DialogFragmentPasswordBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel: MainViewModel by activityViewModels()

    companion object {
        fun getInstance(
            passwordState: PasswordState,
        ): PasswordDialogFragment =
            PasswordDialogFragment().apply {
                arguments = Bundle().apply {
                    putEnum(EXTRA_PASSWORD_STATE_DIALOG_FRAGMENT, passwordState)
                }
            }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogFragmentPasswordBinding.inflate(layoutInflater)
        return when {
            arguments?.getString(EXTRA_PASSWORD_STATE_DIALOG_FRAGMENT)
                ?.equals(PasswordState.SAVE_PASSWORD.name) == true -> savePassword()

            arguments?.getString(EXTRA_PASSWORD_STATE_DIALOG_FRAGMENT)
                ?.equals(PasswordState.REQUEST_PASSWORD.name) == true -> requestPassword()

            arguments?.getString(EXTRA_PASSWORD_STATE_DIALOG_FRAGMENT)
                ?.equals(PasswordState.REQUEST_HINT.name) == true -> requestHint()

            else -> Dialog(requireContext())
        }
    }

    override fun onStart() {
        super.onStart()
        restartPasswordState()
    }

    private fun restartPasswordState() {
        mainViewModel.updatePasswordState(PasswordState.INITIAL_STATE)
    }

    private fun savePassword(): Dialog {
        return createAlertDialog(R.string.save, ::checkPasswordConditions)
    }

    private fun createAlertDialog(
        positiveMessageId: Int,
        positiveConditionListener: () -> Unit,
        negativeMessageId: Int = -1,
        negativeConditionListener: (() -> Unit)? = null,
        neutralMessageId: Int = -1,
        neutralConditionListener: (() -> Unit)? = null
    ): Dialog {
        val alertDialog = with(AlertDialog.Builder(requireContext())) {
            setView(binding.root)
            setPositiveButton(positiveMessageId, null)
            if (negativeMessageId != -1) {
                setNegativeButton(negativeMessageId, null)
            }
            if (neutralMessageId != -1) {
                setNeutralButton(neutralMessageId, null)
            }
            show()
        }
        val buttonPositive = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
        buttonPositive.setOnClickListener {
            positiveConditionListener()
        }
        if (negativeConditionListener != null) {
            val buttonNegative = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)
            buttonNegative.setOnClickListener {
                negativeConditionListener()
                dialog?.cancel()
            }
        }
        if (neutralConditionListener != null) {
            val buttonNeutral = alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL)
            buttonNeutral.setOnClickListener {
                neutralConditionListener()
                dialog?.cancel()
            }
        }
        return alertDialog
    }

    private fun checkPasswordConditions() {
        when {
            binding.password.text.isEmpty() -> {
                showErrorOnEditText(
                    binding.password,
                    R.string.empty_password
                )
            }

            binding.repeatPassword.text.isEmpty() -> {
                showErrorOnEditText(
                    binding.repeatPassword,
                    R.string.empty_password
                )
            }

            binding.recoveryHint.text.isEmpty() -> {
                showErrorOnEditText(
                    binding.recoveryHint,
                    R.string.empty_hint
                )
            }

            binding.password.text.toString() == binding.repeatPassword.text.toString() -> {
                mainViewModel.saveCredentials(
                    binding.password.text.toString().toInt(),
                    binding.recoveryHint.text.toString()
                )
                mainViewModel.updatePasswordState(PasswordState.SUCCESSFUL)
                dialog?.cancel()
                restartPasswordState()
            }
            else -> {
                showErrorOnEditText(
                    binding.repeatPassword,
                    R.string.different_passwords
                )
            }
        }
    }

    private fun requestPassword(): Dialog {
        binding.passwordDialogTitle.text =
            getString(R.string.password).replaceFirstChar { it.uppercase() }
        binding.passwordDialogMessage.isVisible = false
        binding.repeatPassword.isVisible = false
        binding.recoveryHintLabel.isVisible = false
        binding.recoveryHint.isVisible = false
        return createAlertDialog(
            R.string.ok,
            ::verifyPassword,
            R.string.forgot_password,
            ::callListenerToRequestHint
        )
    }

    private fun verifyPassword() =
        mainViewModel.checkIfPasswordIsCorrect(
            binding.password.text.toString().let { if (it.isBlank()) 0 else it.toInt() },
            ::isPasswordCorrect
        )

    private fun isPasswordCorrect(isCorrect: Boolean) {
        if (isCorrect) {
            mainViewModel.updatePasswordState(PasswordState.SUCCESSFUL)
            dialog?.cancel()
            restartPasswordState()
        } else {
            binding.password.requestFocus()
            binding.password.error = getString(R.string.incorrect_password)
        }
    }

    private fun callListenerToRequestHint() {
        mainViewModel.updatePasswordState(PasswordState.REQUEST_HINT)
    }

    private fun requestHint(): Dialog {
        binding.passwordDialogTitle.text = getString(R.string.type_hint_title)
        binding.passwordDialogMessage.text = getString(R.string.type_hint_message)
        binding.password.isVisible = false
        binding.repeatPassword.isVisible = false
        binding.recoveryHintLabel.isVisible = false
        return createAlertDialog(
            R.string.ok,
            ::verifyHint,
            R.string.use_password,
            ::callListenerToRequestPassword,
            R.string.reset_password,
            ::callListenerToResetData
        )
    }

    private fun verifyHint() =
        mainViewModel.checkIfHintIsCorrect(binding.recoveryHint.text.toString(), ::isHintCorrect)

    private fun isHintCorrect(isCorrect: Boolean) {
        if (isCorrect) {
            mainViewModel.updatePasswordState(PasswordState.SUCCESSFUL)
            dialog?.cancel()
            restartPasswordState()
        } else {
            binding.recoveryHint.requestFocus()
            binding.recoveryHint.error = getString(R.string.incorrect_hint)
        }
    }

    private fun callListenerToRequestPassword() {
        mainViewModel.updatePasswordState(PasswordState.REQUEST_PASSWORD)
    }

    private fun callListenerToResetData() {
        mainViewModel.updatePasswordState(PasswordState.SAVE_PASSWORD)
        mainViewModel.deleteCredentials()
        mainViewModel.resetLocalFavoriteCharacters()
    }

    private fun showErrorOnEditText(editText: EditText, messageId: Int) {
        editText.requestFocus()
        editText.error = getString(messageId)
    }
}
