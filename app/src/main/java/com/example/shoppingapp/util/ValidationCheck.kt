package com.example.shoppingapp.util

import android.util.Patterns
import java.util.regex.Pattern

//회원가입 유효성 검사
//email.password 확인하는 함수작성

//이메일 유효성검사
fun validateEmail(email:String) : RegisterValidation{
    //이메일이 비어있는지 확인
    if (email.isEmpty())
        return RegisterValidation.Failed("이메일은 비워둘수없습니다")

    //이메일 형식인지 확인
    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        return RegisterValidation.Failed("잘못된 이메일형식입니다")

    return RegisterValidation.Success
}

//패스워드 유효성검사
fun validatePassword(password:String) : RegisterValidation{
    if (password.isEmpty())
        return RegisterValidation.Failed("비밀번호는 비워둘수없습니다")

    //비밀번호 길이가 6보다 작은지 확인
    if (password.length < 6)
        return RegisterValidation.Failed("비밀번호는 6글자 이상이여야합니다")

    return RegisterValidation.Success
}