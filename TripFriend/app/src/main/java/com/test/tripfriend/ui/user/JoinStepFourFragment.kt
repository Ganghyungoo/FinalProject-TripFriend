package com.test.tripfriend.ui.user

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.test.tripfriend.R
import com.test.tripfriend.databinding.FragmentJoinStepFourBinding

class JoinStepFourFragment : Fragment() {

    lateinit var fragmentJoinStepFourBinding: FragmentJoinStepFourBinding
    lateinit var loginMainActivity: LoginMainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentJoinStepFourBinding = FragmentJoinStepFourBinding.inflate(inflater)
        loginMainActivity = activity as LoginMainActivity

        fragmentJoinStepFourBinding.run {
            materialToolbarJoinStepFour.run {
                setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material)
                setNavigationIconTint(Color.BLACK)
                setNavigationOnClickListener {
                    loginMainActivity.removeFragment(LoginMainActivity.JOIN_STEP_FOUR_FRAGMENT)
                }
            }

            progressBarJoinStepFour.run {
                setOnStateItemClickListener { stateProgressBar, stateItem, stateNumber, isCurrentState ->
                    when(stateNumber){
                        1-> {
                            loginMainActivity.removeFragment(LoginMainActivity.JOIN_STEP_FOUR_FRAGMENT)
                            loginMainActivity.removeFragment(LoginMainActivity.JOIN_STEP_THREE_FRAGMENT)
                            loginMainActivity.removeFragment(LoginMainActivity.JOIN_STEP_TWO_FRAGMENT)
                        }
                        2-> {
                            loginMainActivity.removeFragment(LoginMainActivity.JOIN_STEP_FOUR_FRAGMENT)
                            loginMainActivity.removeFragment(LoginMainActivity.JOIN_STEP_THREE_FRAGMENT)
                        }
                        3-> { loginMainActivity.removeFragment(LoginMainActivity.JOIN_STEP_FOUR_FRAGMENT) }
                    }
                }
            }

            buttonJoinStepFourNext.setOnClickListener {
                if(textInputEditTextJoinStepFourPhone.text.toString() == "" || //입력 안했을 때
                    textInputEditTextJoinStepFourPhone.text.toString().length != 11 || //번호 개수가 안맞을 때
                    textInputEditTextJoinStepFourPhone.text.toString().startsWith("010") == false|| // 010으로 시작 안할 때
                    textInputEditTextJoinStepFourPhone.text.toString().contains('-')) //-를 포함했을 때
                {
                    val builder= MaterialAlertDialogBuilder(loginMainActivity,R.style.DialogTheme).apply {
                        setTitle("전화번호 입력 오류")
                        setMessage("전화번호를 확인해주세요.")
                        setNegativeButton("확인", null)
                    }
                    builder.show()
                }
                else{
                        loginMainActivity.userPhoneNumber = textInputEditTextJoinStepFourPhone.text.toString()
                        Log.d("aaaa","===============================================")
                        Log.d("aaaa","이메일 = ${loginMainActivity.userEmail}")
                        Log.d("aaaa","비밀번호 = ${loginMainActivity.userPw}")
                        Log.d("aaaa","인증방식 = ${loginMainActivity.userAuth}")
                        Log.d("aaaa","이름 = ${loginMainActivity.userName}")
                        Log.d("aaaa","닉네임 = ${loginMainActivity.userNickname}")
                        Log.d("aaaa","휴대폰 번호 = ${loginMainActivity.userPhoneNumber}")
                        Log.d("aaaa","MBTI = ${loginMainActivity.userMBTI}")
                        loginMainActivity.replaceFragment(LoginMainActivity.JOIN_STEP_FIVE_FRAGMENT, true, true, null)


                }

            }
        }

        return fragmentJoinStepFourBinding.root
    }
}