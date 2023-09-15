package com.test.tripfriend.ui.user

import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEach
import androidx.core.view.get
import com.google.android.material.chip.Chip
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.test.tripfriend.R
import com.test.tripfriend.databinding.FragmentJoinStepFiveBinding
import com.test.tripfriend.dataclassmodel.User
import com.test.tripfriend.repository.UserRepository


class JoinStepFiveFragment : Fragment() {

    lateinit var fragmentJoinStepFiveBinding: FragmentJoinStepFiveBinding
    lateinit var loginMainActivity: LoginMainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentJoinStepFiveBinding = FragmentJoinStepFiveBinding.inflate(inflater)
        loginMainActivity = activity as LoginMainActivity

        fragmentJoinStepFiveBinding.run {
            materialToolbarJoinStepFive.run{
                setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material)
                setNavigationIconTint(Color.BLACK)
                setNavigationOnClickListener {
                    loginMainActivity.removeFragment(LoginMainActivity.JOIN_STEP_FIVE_FRAGMENT)
                }
            }

            progressBarJoinStepFive.run {
                setOnStateItemClickListener { stateProgressBar, stateItem, stateNumber, isCurrentState ->
                    when(stateNumber){
                        1-> {
                            loginMainActivity.removeFragment(LoginMainActivity.JOIN_STEP_FIVE_FRAGMENT)
                            loginMainActivity.removeFragment(LoginMainActivity.JOIN_STEP_FOUR_FRAGMENT)
                            loginMainActivity.removeFragment(LoginMainActivity.JOIN_STEP_THREE_FRAGMENT)
                            loginMainActivity.removeFragment(LoginMainActivity.JOIN_STEP_TWO_FRAGMENT)
                        }
                        2-> {
                            loginMainActivity.removeFragment(LoginMainActivity.JOIN_STEP_FIVE_FRAGMENT)
                            loginMainActivity.removeFragment(LoginMainActivity.JOIN_STEP_FOUR_FRAGMENT)
                            loginMainActivity.removeFragment(LoginMainActivity.JOIN_STEP_THREE_FRAGMENT)
                        }
                        3-> {
                            loginMainActivity.removeFragment(LoginMainActivity.JOIN_STEP_FIVE_FRAGMENT)
                            loginMainActivity.removeFragment(LoginMainActivity.JOIN_STEP_FOUR_FRAGMENT)
                        }
                        4-> { loginMainActivity.removeFragment(LoginMainActivity.JOIN_STEP_FIVE_FRAGMENT) }
                    }
                }
            }

            buttonJoinStepFiveSubmit.setOnClickListener {
                val selectedChipId = chipGroupJoinStepFiveMBTIChips.checkedChipId
                if (selectedChipId != View.NO_ID) {
                    val selectedChip = fragmentJoinStepFiveBinding.root.findViewById<Chip>(selectedChipId)
                    val selectedChipText = selectedChip.text.toString()
                    loginMainActivity.userMBTI = selectedChipText
                    Log.d("aaaa","===============================================")
                    Log.d("aaaa","이메일 = ${loginMainActivity.userEmail}")
                    Log.d("aaaa","비밀번호 = ${loginMainActivity.userPw}")
                    Log.d("aaaa","인증방식 = ${loginMainActivity.userAuth}")
                    Log.d("aaaa","이름 = ${loginMainActivity.userName}")
                    Log.d("aaaa","닉네임 = ${loginMainActivity.userNickname}")
                    Log.d("aaaa","휴대폰 번호 = ${loginMainActivity.userPhoneNumber}")
                    Log.d("aaaa","MBTI = ${loginMainActivity.userMBTI}")

                    val userClass = User(
                        loginMainActivity.userAuth,
                        loginMainActivity.userEmail,
                        loginMainActivity.userPw,
                        loginMainActivity.userNickname,
                        loginMainActivity.userName,
                        loginMainActivity.userPhoneNumber,
                        loginMainActivity.userMBTI
                    )

                    //db에 저장
                    UserRepository.addUser(userClass){
                        //가입 완료 시 회원가입 단계 화면 백스택에서 전부 제거
                        loginMainActivity.removeFragment(LoginMainActivity.JOIN_STEP_FIVE_FRAGMENT)
                        loginMainActivity.removeFragment(LoginMainActivity.JOIN_STEP_FOUR_FRAGMENT)
                        loginMainActivity.removeFragment(LoginMainActivity.JOIN_STEP_THREE_FRAGMENT)
                        loginMainActivity.removeFragment(LoginMainActivity.JOIN_STEP_TWO_FRAGMENT)
                        loginMainActivity.removeFragment(LoginMainActivity.JOIN_STEP_ONE_FRAGMENT)
                        Snackbar.make(fragmentJoinStepFiveBinding.root, "회원가입이 완료되었습니다.", Snackbar.LENGTH_SHORT).show()
                    }
                } else {
                    val builder= MaterialAlertDialogBuilder(loginMainActivity,R.style.DialogTheme).apply {
                        setTitle("MBTI 선택")
                        setMessage("MBTI를 선택해주세요.")
                        setNegativeButton("확인", null)
                    }
                    builder.show()
                }




            }

            chipJoinStepFiveNon.setOnCheckedChangeListener { compoundButton, checked ->
                //모름 칩 선택되었을 때 다이얼로그 발생
                if(checked){
                    val builder= MaterialAlertDialogBuilder(loginMainActivity,R.style.DialogTheme).apply {
                        setTitle("MBTI 선택")
                        setMessage("MBTI를 선택하면 다른 사용자들에게 더 많은 정보를 제공할 수 있습니다.")
                        setNegativeButton("모름으로 선택", null)
                        setPositiveButton("MBTI 선택"){ dialogInterface: DialogInterface, i: Int ->
                            chipJoinStepFiveNon.isChecked = false
                        }
                    }
                    builder.show()
                }
            }
        }

        return fragmentJoinStepFiveBinding.root
    }
}