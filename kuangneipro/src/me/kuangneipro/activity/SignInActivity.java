package me.kuangneipro.activity;

import com.igexin.sdk.PushManager;

import me.kuangneipro.R;
import me.kuangneipro.entity.UserInfo;
import me.kuangneipro.util.LoginUtil;
import me.kuangneipro.util.LoginUtil.OnSignInLisener;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignInActivity extends Activity {
	
	private Button signinButton;
	private Button registerButton;
	private EditText editPhone;
	private EditText editPassword;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_signin);
		//��������clientid,��ע����ռ���
		PushManager.getInstance().initialize(this.getApplicationContext());
		
		signinButton = (Button)findViewById(R.id.btnSignin);
		editPhone = (EditText)findViewById(R.id.editPhone);
		editPassword = (EditText)findViewById(R.id.editPassword);
		registerButton = (Button)findViewById(R.id.btnSigninRegister);
		
		
		UserInfo userInfo = UserInfo.loadSelfUserInfo();
		if(userInfo!=null){
			editPhone.setText(userInfo.getUsername());
			editPassword.setText(userInfo.getPassword());
		}
		
		
		registerButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(SignInActivity.this, RegisterActivity.class);
		    	startActivity(intent);
			}
		});
		signinButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(TextUtils.isEmpty(editPhone.getText())|| TextUtils.isEmpty(editPassword.getText())){
					Toast.makeText(SignInActivity.this, "�������˺�����", Toast.LENGTH_SHORT).show();
				}else{
					LoginUtil.signin(editPhone.getText().toString(), editPassword.getText().toString(), new OnSignInLisener() {
						
						@Override
						public void onSignInFinish(boolean isSuccess, UserInfo userInfo) {
							if(isSuccess){
								Intent intent = new Intent(SignInActivity.this, MainActivity.class);
						    	startActivity(intent);
							}else{
								Toast.makeText(SignInActivity.this, "��¼ʧ�ܣ������ԣ�", Toast.LENGTH_SHORT).show();
							}
							
						}
					});
				}
				
				
			}
		});
	}
}
