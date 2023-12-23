package com.example.shoppingapp.data

data class User(
    val firstName : String, //firstName
    val lastName : String,  //lastName
    val email : String,  //email
    var imagePath : String = "",  //이미지 경로(사용자는 가입후에 프로필이미지를 변경할수있으므로 지금은 비어있음)
) {
    //Firebase를 처리할때마다 빈생성자 필요
    //빈생성자를 만들고 빈문자열을 전달
    constructor() : this("","","","")
}
