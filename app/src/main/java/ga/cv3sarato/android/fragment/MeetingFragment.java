package ga.cv3sarato.android.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.chenenyu.router.Router;
import com.chenenyu.router.annotation.Route;
import ga.cv3sarato.android.R;
import ga.cv3sarato.android.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

@Route(value = "gtedx://meeting")
public class MeetingFragment extends BaseFragment {

    @BindView(R.id.enter_btn)
    Button enterBtn;
    Unbinder unbinder;
    @BindView(R.id.meeting_input)
    EditText meetingInput;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_metting, null);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick(R.id.enter_btn)
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.enter_btn:
//                Router.build("gtedx://jitsi").with("code", meetingInput.getText().toString()).go(this);
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.chat_fragment, (JitsiFragment) Router.build("gtedx://jitsi_fragment").with("code", meetingInput.getText().toString()).getFragment(getActivity()), null)
                        .addToBackStack("JITSI")
                        .commit();
                break;
            default:
                break;
        }
    }
}
