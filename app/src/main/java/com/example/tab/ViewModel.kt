package com.example.tab

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
//import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
//import javax.inject.Inject


class PostViewModel(
    private val repository: PostRepository // 이 부분은 실제로 주입받는 의존성에 맞게 수정해야 합니다.
) : ViewModel() {
    val num = MutableStateFlow(0)

    private val _postList = MutableStateFlow<List<Post>>(emptyList())
    val postList: StateFlow<List<Post>> = _postList

    private val _post = MutableStateFlow<Post?>(null)
    val post: StateFlow<Post?> = _post

    private val _postState = MutableStateFlow<Resource<Post>>(Resource.loading(null))
    val postState: StateFlow<Resource<Post>> = _postState.asStateFlow()


    private val _postListState = MutableStateFlow<Resource<List<Post>>>(Resource.loading(null))
    val postListState: StateFlow<Resource<List<Post>>> = _postListState.asStateFlow()


    fun add() {
        num.value = num.value + 1
    }

    fun setZero() {
        num.value = 0
    }

    suspend fun getPost() {
        viewModelScope.launch {
            _postState.value = Resource.loading(null)
            try {
                val response = repository.getPost()
                if (response.isSuccessful && response.body() != null) {
                    val postList = response.body()
                    _postList.value = postList!!
                    _postListState.value = Resource.success(postList)
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Unknown error"
                    Log.e("API Error", "에러 응답: $errorBody")
                    _postListState.value = Resource.error(response.errorBody().toString(), null)
                }
            } catch (e: Exception) {
                Log.e("API Exception", "요청 중 예외 발생: ${e.message}")
                _postListState.value = Resource.error(e.message ?: "An error occurred", null)
            }
        }
    }

    suspend fun getOnePost(idx: Int) {
        viewModelScope.launch {
            _postState.value = Resource.loading(null)
            try {
                val response = repository.getOnePost(idx)
                if (response.isSuccessful && response.body() != null) {
                    val post = response.body()
                    _post.value = post!!
                    _postState.value = Resource.success(post)
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Unknown error"
                    Log.e("API Error", "에러 응답: $errorBody")
                    _postState.value = Resource.error(response.errorBody().toString(), null)
                }
            } catch (e: Exception) {
                Log.e("API Exception", "요청 중 예외 발생: ${e.message}")
                _postState.value = Resource.error(e.message ?: "An error occurred", null)
            }
        }
    }

}