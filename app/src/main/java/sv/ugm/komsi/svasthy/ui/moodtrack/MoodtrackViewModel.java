package sv.ugm.komsi.svasthy.ui.moodtrack;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MoodtrackViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MoodtrackViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is moodtrack fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}