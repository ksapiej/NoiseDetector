package com.example.noisedetector.Fragment.auth;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.example.noisedetector.Activity.MainActivity;
import com.example.noisedetector.Model.RegistrationUserBuilder;
import com.example.noisedetector.Model.User;
import com.example.noisedetector.R;
import com.example.noisedetector.Util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Krzysiek on 09.10.2018.
 */

public class AuthorizationFragment extends Fragment implements AuthorizationView{

    private static final String TAG = AuthorizationFragment.class.getSimpleName();

    @BindView(R.id.login_component)
    LinearLayout loginComponent;
    @BindView(R.id.register_component)
    LinearLayout registerComponent;

    @BindView(R.id.username_login)
    EditText usernameLogin;
    @BindView(R.id.password)
    EditText password;

    @BindView(R.id.username_register)
    EditText usernameRegister;

    @BindView(R.id.email)
    EditText email;

    @BindView(R.id.password1)
    EditText password1;

    @BindView(R.id.password2)
    EditText password2;

    @BindView(R.id.sex_radio_group)
    RadioGroup sexRadioGroup;


    @BindView(R.id.component_selection)
    RadioGroup componentSelection;



    private int shortAnimationDuration;
    private AuthorizationPresenterInterface presenter;
    private Unbinder unbinder;


    public AuthorizationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new AuthorizationPresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_authorization, container, false);

        unbinder = ButterKnife.bind(this, rootView);
        loginComponent.setVisibility(View.VISIBLE);
        registerComponent.setVisibility(View.GONE);
        shortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);

        presenter.attachView(this);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        presenter.detachView();
    }

    @OnCheckedChanged({R.id.login_radio_button, R.id.register_radio_button})
    public void selectionChanged(CompoundButton radioButton, boolean checked) {
        if (checked) {
            Log.i(TAG, radioButton.getId() + "");
            changeSelection(radioButton.getId() == R.id.login_radio_button);
        }
    }

    private void changeSelection(boolean selection) {
        if (selection) {
            animate(loginComponent, registerComponent);
        } else {
            animate(registerComponent, loginComponent);
        }
    }

    private void animate(View fadingIn, View fadingOut) {

        fadingIn.setAlpha(0f);
        fadingIn.setVisibility(View.VISIBLE);
        fadingOut.setVisibility(View.GONE);
        fadingIn.animate()
                .alpha(1f)
                .setDuration(shortAnimationDuration)
                .setListener(null);
    }

    @OnClick(R.id.login_button)
    public void login() {
        presenter.login(usernameLogin.getText().toString(), password.getText().toString());
    }


    @Override
    public void startMainActivity(){
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void onRegistrationFailure(String message) {
        ToastUtil.showToast(message);
    }

    @Override
    public void onSuccessFullRegistration() {
        ToastUtil.showToast(getString(R.string.successfully_registered));
        changeSelection(true);
        componentSelection.check(R.id.login_radio_button);
    }

    @OnClick(R.id.register_button)
    public void register() {
        User user = checkConditions();
        if(user!=null) presenter.register(user);
    }

    private User checkConditions() {
        RegistrationUserBuilder userBuilder = new RegistrationUserBuilder();

        if(usernameRegister.getText().length()==0) {
            ToastUtil.showToast(getString(R.string.enter_username));
            return null;
        }
        else if(email.getText().length()==0) {
            ToastUtil.showToast(getString(R.string.enter_email));
            return null;
        }
        else if(password1.getText().length()==0) {
            ToastUtil.showToast(getString(R.string.enter_password));
            return null;
        }
        else if(password2.getText().length()==0) {
            ToastUtil.showToast(getString(R.string.enter_passwords));
            return null;
        }
        else if(!password2.getText().toString().equals(password1.getText().toString())) {
            ToastUtil.showToast(getString(R.string.passwords_must_be_equal));
            return null;
        }


        boolean sex = sexRadioGroup.getCheckedRadioButtonId() == R.id.male;

        return userBuilder
                .setEmail(email.getText().toString())
                .setPassword(password1.getText().toString())
                .setUserName(usernameRegister.getText().toString())
                .setSex(sex)
                .build();
    }
}
