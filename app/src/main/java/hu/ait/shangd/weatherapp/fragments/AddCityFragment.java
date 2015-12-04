package hu.ait.shangd.weatherapp.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import hu.ait.shangd.weatherapp.R;

public class AddCityFragment extends DialogFragment {

    public static final String TAG = "AddCityFragment";

    private AddCityFragmentInterface addCityFragmentInterface;

    public interface AddCityFragmentInterface {
        void onFragmentPositiveClicked(String cityName);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            addCityFragmentInterface = (AddCityFragmentInterface) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OptionsFragmentInterface");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_add_city,
                null);
        final EditText etCityName = (EditText) view.findViewById(R.id.et_add_city);

        alertDialogBuilder.setView(view).setTitle("Add a city")
                .setPositiveButton("Confirm",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (etCityName.getText().toString().equals("")) {
                                    etCityName.setError("Please enter a city");
                                } else {
                                    addCityFragmentInterface.onFragmentPositiveClicked(
                                            etCityName.getText().toString()
                                    );
                                }
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

        return alertDialogBuilder.create();
    }
}
