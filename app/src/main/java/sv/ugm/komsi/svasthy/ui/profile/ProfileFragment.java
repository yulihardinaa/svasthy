package sv.ugm.komsi.svasthy.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import sv.ugm.komsi.svasthy.R;
import sv.ugm.komsi.svasthy.ui.moodtrack.MoodtrackViewModel;

public class ProfileFragment extends Fragment {

    private MoodtrackViewModel moodtrackViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        moodtrackViewModel =
                ViewModelProviders.of(this).get(MoodtrackViewModel.class);
        View root = inflater.inflate(R.layout.fragment_moodtrack, container, false);
        final TextView textView = root.findViewById(R.id.text_moodtrack);
        moodtrackViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
