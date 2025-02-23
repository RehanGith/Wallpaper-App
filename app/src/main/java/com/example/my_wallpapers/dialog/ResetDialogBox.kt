package com.example.my_wallpapers.dialog

import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.my_wallpapers.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

fun Fragment.setUpBottomDialog(
   onSendClick: (String) -> Unit
) {
    val dialog = BottomSheetDialog(requireContext(), R.style.DialogStyle)
    dialog.setContentView(R.layout.reset_passowrd_dialog)
    dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
    dialog.show()

    val edEmail = dialog.findViewById<EditText>(R.id.edResetPassword)
    val btnReset = dialog.findViewById<Button>(R.id.buttonSendResetPassword)
    val btnCancel = dialog.findViewById<Button>(R.id.buttonCancelResetPassword)

    if (btnCancel != null) {
        btnCancel.setOnClickListener {
            dialog.dismiss()

        }
    } else {
        Log.d("TAG", "setUpBottomDialog: btnCancel is null")
    }

    if (btnReset != null) {
        if(edEmail != null) {
            btnReset.setOnClickListener {
                onSendClick(edEmail.text.toString())
                dialog.dismiss()
            }
        } else {
            Log.d("TAG", "setUpBottomDialog: edEmail is null")
        }
    } else {
        Log.d("TAG", "setUpBottomDialog: btnReset is null")
    }

}