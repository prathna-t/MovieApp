package com.example.movielibrary.provider;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class LoginViewModel extends AndroidViewModel {
    private LoginRepository loginRepository;
    private LiveData<List<LoginTable>> mAllLogin;

    // get data from respository
    public LoginViewModel(Application application) {
        super(application);
        loginRepository = new LoginRepository(application);
        mAllLogin = loginRepository.getAllList();
    }
    public LiveData<List<LoginTable>> getAllList() {
        return mAllLogin;
    }

    public void signUp(LoginTable loginTable) {
        loginRepository.signUp(loginTable);
    }

    public LoginTable getUserCredentials(String email) {
        return loginRepository.getUserCredentials(email);
    }

    public void deleteAllData() {
        loginRepository.deleteAllData();
    }

}
