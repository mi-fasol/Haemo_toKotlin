package com.example.haemo.viewModel

//import dagger.hilt.android.lifecycle.HiltViewModel
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.haemo.model.LoginModel
import com.example.haemo.model.PostModel
import com.example.haemo.network.Resource
import com.example.haemo.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

//import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    private val _postModelList = MutableStateFlow<List<PostModel>>(emptyList())
    val postModelList: StateFlow<List<PostModel>> = _postModelList

    private val _postModel = MutableStateFlow<PostModel?>(null)
    val postModel: StateFlow<PostModel?> = _postModel

    private val _loginState = MutableStateFlow<Resource<LoginModel>>(Resource.loading(null))
    val loginState: StateFlow<Resource<LoginModel>> = _loginState.asStateFlow()

    suspend fun login(loginModel: LoginModel) {
        viewModelScope.launch {
            _loginState.value = Resource.loading(null)
            try {
                val response = repository.tryLogin(loginModel)
                if (response.isSuccessful && response.body() != null) {
                    val isSuccess = response.body()

                } else {
                    val errorBody = response.errorBody()?.string() ?: "Unknown error"
                    Log.e("API Error", "로그인 에러 응답: $errorBody")
                    _loginState.value = Resource.error(response.errorBody().toString(), null)
                }
            } catch (e: Exception) {
                Log.e("API Exception", "로그인 요청 중 예외 발생: ${e.message}")
                _loginState.value = Resource.error(e.message ?: "An error occurred", null)
            }
        }
    }
}