package com.example.ebooks.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ebooks.ResultState
import com.example.ebooks.data.repo.Repo
import com.example.ebooks.data.reponse.BookCategoryModels
import com.example.ebooks.data.reponse.BookModels
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(private val repo: Repo) : ViewModel() {

    val _getAllBooksState = MutableStateFlow(GetAllBooksState())
    val getAllBooksState = _getAllBooksState.asStateFlow()

    val _getAllCategoryState = MutableStateFlow(GetAllCategoryState())
    val getAllCategoryState = _getAllCategoryState.asStateFlow()

    val _getBooksByCategoryState = MutableStateFlow(GetBooksByCategoryState())
    val getBooksByCategoryState = _getBooksByCategoryState.asStateFlow()


    fun getAllBooks() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getAllBooks().collect {
                when (it) {
                    is ResultState.Success -> {
                        _getAllBooksState.value =
                            GetAllBooksState(data = it.data, isLoading = false)
                        println("Data : ${it.data}")
                    }

                    is ResultState.Error -> {
                        _getAllBooksState.value = GetAllBooksState(error = it.message)
                    }

                    is ResultState.Loading -> {
                        _getAllBooksState.value = GetAllBooksState(isLoading = true)

                    }
                }
            }
        }
    }

    fun getAllCategory() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getAllCategory().collect {
                when (it) {
                    is ResultState.Success -> {
                        _getAllCategoryState.value =
                            GetAllCategoryState(data = it.data, isLoading = false)
                    }

                    is ResultState.Error -> {
                        _getAllCategoryState.value = GetAllCategoryState(error = it.message)
                    }

                    is ResultState.Loading -> {
                        _getAllCategoryState.value = GetAllCategoryState(isLoading = true)

                    }
                }
            }

        }
    }

    fun getBooksByCategory(Category : String){
        viewModelScope.launch(Dispatchers.IO) {
            repo.getBooksByCategory(Category).collect{
                when(it){
                    is ResultState.Loading -> {
                        _getBooksByCategoryState.value = GetBooksByCategoryState(isLoading = true)
                    }
                    is ResultState.Success -> {
                        _getBooksByCategoryState.value = GetBooksByCategoryState(data = it.data, isLoading = false)
                    }
                    is ResultState.Error -> {
                        _getBooksByCategoryState.value = GetBooksByCategoryState(error = it.message)
                    }

                }


            }
        }

    }

}

data class GetAllBooksState(
    val isLoading : Boolean = false,
    val data : List<BookModels> = emptyList(),
    val error : String = ""
)

data class GetAllCategoryState(
    val isLoading : Boolean = false,
    val data : List<BookCategoryModels> = emptyList(),
    val error : String = ""
)

data class GetBooksByCategoryState(
    val isLoading : Boolean = false,
    val data : List<BookModels> = emptyList(),
    val error : String = ""
)
