package com.example.ebooks.data.repo

import com.example.ebooks.ResultState
import com.example.ebooks.data.reponse.BookCategoryModels
import com.example.ebooks.data.reponse.BookModels
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class Repo @Inject constructor(private val firebaseDatabase: FirebaseDatabase) {

    suspend fun getAllBooks() : Flow<ResultState<List<BookModels>>> = callbackFlow{

        trySend(ResultState.Loading)

        val valueEvent = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var items: List<BookModels> = emptyList()

                items = snapshot.children.mapNotNull { value ->
                    value.getValue(BookModels::class.java)!!
                }
                trySend(ResultState.Success(items))
                println("Data Retrived : $items")
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(ResultState.Error(error.message))
                println("Error : ${error.message}")
            }

        }

        firebaseDatabase.reference.child("Books").addValueEventListener(valueEvent)

        awaitClose {
            firebaseDatabase.reference.child("Books").removeEventListener(valueEvent)
            close()
        }
    }

    suspend fun getAllCategory() : Flow<ResultState<List<BookCategoryModels>>> = callbackFlow {
        trySend(ResultState.Loading)

        val valueEvent = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var items: List<BookCategoryModels> = emptyList()

                items = snapshot.children.map { value ->
                    value.getValue<BookCategoryModels>()!!
                }
                trySend(ResultState.Success(items))
                println("Data Retrived : $items")
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(ResultState.Error(error.message))
                println("Error : ${error.message}")
            }
        }
        firebaseDatabase.reference.child("BookCategory").addValueEventListener(valueEvent)
        awaitClose {
            firebaseDatabase.reference.child("BookCategory").removeEventListener(valueEvent)
            close()
        }

    }

    suspend fun  getBooksByCategory(Category : String): Flow<ResultState<List<BookModels>>> = callbackFlow {
        trySend(ResultState.Loading)

        val valueEvent = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var items: List<BookModels> = emptyList()

                items = snapshot.children.filter {
                    it.getValue<BookModels>()!!.category == Category
                }.map {
                    it.getValue<BookModels>()!!
                }
                trySend(ResultState.Success(items))
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(ResultState.Error(error.message))

            }

        }


        firebaseDatabase.reference.child("Books").addValueEventListener(valueEvent)

        awaitClose {
            firebaseDatabase.reference.child("Books").removeEventListener(valueEvent)
            close()
        }


    }

}