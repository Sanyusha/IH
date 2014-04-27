package com.example.piemenu;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class datePickerFragment extends DialogFragment {
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	return new AlertDialog.Builder(getActivity())
	.setView(new PieMenu(getActivity()))
	.setTitle("Bla")
	.setPositiveButton(android.R.string.ok, null)
	.create();
	}
}
