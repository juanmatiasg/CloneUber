package com.example.cloneuber.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.cloneuber.R
import com.example.cloneuber.databinding.FragmentLogoutDialogBinding

class LogoutDialogFragment : DialogFragment() {

    private var _binding:FragmentLogoutDialogBinding? = null
    private val binding get() =  _binding!!

    var listener: LogoutDialogListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = FragmentLogoutDialogBinding.inflate(inflater,container,false)
        // Configura el fondo redondeado para el diálogo
        dialog?.window?.setBackgroundDrawableResource(R.drawable.dialog_rounded)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonOK.setOnClickListener {
            listener?.onLogoutConfirmed()
            dismiss()
        }
        binding.buttonCancel.setOnClickListener {
            dismiss()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    interface LogoutDialogListener{
        fun onLogoutConfirmed()
    }

}