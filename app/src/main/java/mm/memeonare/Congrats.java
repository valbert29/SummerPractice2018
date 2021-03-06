package mm.memeonare;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;

public class Congrats extends DialogFragment {
    MyListener callback;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
        adb.setTitle("CONGRATS!!!").setMessage("YOU'RE OFFICIALLY MEMEONARE NOW")
                .setPositiveButton("GO TO MENU", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        callback.cancel();
                    }
                });

        return adb.create();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MyListener) {
            callback = (MyListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement MyListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }
}


