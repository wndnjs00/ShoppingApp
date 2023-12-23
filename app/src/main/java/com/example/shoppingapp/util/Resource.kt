package com.example.shoppingapp.util

sealed class Resource<T>(           //모든 데이터유형 수신할수있는 일반클래스
    val data : T? = null,           //일반 데이터
    val message : String? = null,   //오류 메세지
) {

    class Success<T>(data : T) : Resource<T>(data)  // 작업이 성공했을때
    class Error<T>(message: String) : Resource<T>(message = message)    //에러발생 시
    class Loading<T>: Resource<T>()   //로딩상태
    class Unspecified<T> : Resource<T>()
}