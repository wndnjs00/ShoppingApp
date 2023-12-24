package com.example.shoppingapp.fragments.loginRegister

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.kelineyt.viewmodel.RegisterViewModel
import com.example.shoppingapp.R
import com.example.shoppingapp.data.User
import com.example.shoppingapp.databinding.FragmentRegisterBinding
import com.example.shoppingapp.util.RegisterValidation
import com.example.shoppingapp.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private val TAG = "RegisterFragment"
@AndroidEntryPoint
class RegisterFragment : Fragment(){

    private lateinit var binding : FragmentRegisterBinding
    private val viewModel by viewModels<RegisterViewModel>()
//뷰모델

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 바인딩 초기화
        binding = FragmentRegisterBinding.inflate(inflater)
        return binding.root //바인딩의 루트레이아웃 반환
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //tvDoYouHaveAccount클릭시
        binding.tvDoYouHaveAccount.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        //회원가입 버튼함수 호출
        binding.apply {
            buttonRegisterRegister.setOnClickListener {
                //클릭리스너에 등록된 세트를 등록
                val user = User(    //사용자가 동일하게
                    edFirstNameRegister.text.toString().trim(), //FirstName전달(trim()사용해서 공백제거)
                    edLastNameRegister.text.toString().trim(),  //LastName전달
                    edEmailRegister.text.toString().trim()      //Email전달
                )
                val password = edPasswordRegister.text.toString()   //password전달(비밀번호에는 빈공간있을수있게해서 trim()쓰지 않음)
                viewModel.createAccountWithEmailAndPassword(user,password)  //Firebase Authentication에서 제공하는 기능 중 하나로, 사용자가 제공한 이메일과 비밀번호로 계정을 생성
            }
        }

        //비동기 작업의 상태에 따라 UI를 업데이트하는 데 사용
        //ex)로딩 중일 때는 로딩 스피너를 표시하고, 성공 시에는 데이터를 UI에 표시하고, 에러 시에는 사용자에게 오류 메시지를 표시하는 등의 작업을 수행
        //해당 프레그먼트가 onStart상태일 때 coroutine(코루틴)시작 , onStop일때 coroutine(코루틴)취소 (이를 통해 메모리 누수 및 불필요한 리소스 사용을 방지)
        lifecycleScope.launchWhenStarted {
            viewModel.register.collect{
                when(it){
                    is Resource.Loading -> {    //비동기 작업이 진행 중일 때 실행(로딩 중에 수행해야 할 작업)
                        //버튼 애니메이션 실행
                        binding.buttonRegisterRegister.startAnimation()
                    }
                    is Resource.Success -> {    //비동기 작업이 성공적으로 완료되었을 때 실행(성공 시에 수행)
                        Log.d("test",it.data.toString()) //"test"라는 태그를 가진 로그에, 이전에 나온 값의 data를 문자열로 변환하여 출력
                        binding.buttonRegisterRegister.revertAnimation()    //오류가 발생하면 중지
                    }
                    is Resource.Error -> {      //비동기 작업이 에러 상태일 때 실행(에러 처리에 관련된 작업)
                        Log.e(TAG, it.message.toString())
                        binding.buttonRegisterRegister.revertAnimation()
                    }
                    else -> Unit
                }
            }
        }

        //유효성검사흐름 수집
        lifecycleScope.launchWhenStarted {
            viewModel.validation.collect{validation ->
                if(validation.email is RegisterValidation.Failed){
                    withContext(Dispatchers.Main){
                        binding.edEmailRegister.apply{
                            requestFocus()
                            error = validation.email.message
                        }
                    }
                }

                if (validation.password is RegisterValidation.Failed){
                    withContext(Dispatchers.Main){
                        binding.edPasswordRegister.apply {
                            requestFocus()
                            error = validation.password.message
                        }
                    }
                }
            }
        }

    }
}