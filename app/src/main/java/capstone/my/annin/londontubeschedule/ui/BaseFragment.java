package capstone.my.annin.londontubeschedule.ui;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment implements ShowSnackbar
{
    public void showSnackbar()
    {
    }

    @Override
    public void onAttach(@NonNull Context context)
    {
        super.onAttach(context);
        try
        {
            ((MainActivity) getActivity()).updateShowSnackbar(this);
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

}
