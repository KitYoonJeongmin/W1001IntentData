package kr.ac.kumoh.s20200759.w1001intentdata

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import kr.ac.kumoh.s20200759.w1001intentdata.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(),OnClickListener {
    private lateinit var binding: ActivityMainBinding
    //동반객체(like static)
    companion object{
        const val keyName = "image"
    }
    private lateinit var launcher:ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGundam.setOnClickListener(this)
        binding.btnZaku.setOnClickListener(this)

        //launcher 뒤에 시험에 나올 수도
        launcher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()){
            //콜백함수 처리
            if(it.resultCode != RESULT_OK){ // == 보다 != 지향
                return@registerForActivityResult //어디까지 리턴하는지 알려줌
            }
            val result = it.data?.getIntExtra(ImageActivity.resultName,ImageActivity.NONE)
            val str = when(result){
                ImageActivity.LIKE -> "좋아요"
                ImageActivity.DISLIKE -> "싫어요"
                else -> ""
            }
            val image = it.data?.getStringExtra(ImageActivity.imageName)
            when(image){
                "gundam" -> binding.btnGundam.text = "건담 ($str)"
                "zaku" -> binding.btnZaku.text = "자쿠 ($str)"
            }
        }
    }

    override fun onClick(p0: View?) {
        val intent = Intent(this, ImageActivity::class.java)
        val Value = when(p0?.id){
            binding.btnGundam.id -> "gundam"
            binding.btnZaku.id -> "zaku"
            else -> return
        }
        intent.putExtra(keyName, Value)
        //startActivity(intent)
        launcher.launch(intent)
    }
}