package com.example.devicemanagement.Controller;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.devicemanagement.DBHelper.DBChiTietSD;
import com.example.devicemanagement.DBHelper.DBLoaiThietBi;
import com.example.devicemanagement.DBHelper.DBNhanVien;
import com.example.devicemanagement.DBHelper.DBPhongHoc;
import com.example.devicemanagement.DBHelper.DBThietBi;
import com.example.devicemanagement.Entity.NhanVien;
import com.example.devicemanagement.R;

import java.util.ArrayList;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class LoginActivity extends AppCompatActivity {
    private DBNhanVien dbNhanVien;
    private DBLoaiThietBi dbLoaiThietBi;
    private DBThietBi dbThietBi;
    private DBPhongHoc dbPhongHoc;
    private DBChiTietSD dbChiTietSD;

    EditText txtUser, txtPass;
    Button btnLogin;
    TextView tvForgotPass;

    ArrayList<NhanVien> DSNV = new ArrayList<>();
    NhanVien nhanVienDangNhap = new NhanVien();

    final String mail = "thuhango0204@gmail.com";
    final String password = "tvchhnsxhegolohs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        getDataBase();
        setControl();
        setEvent();
    }

    private void setEvent(){
        dbNhanVien = new DBNhanVien(this);
//        dbNhanVien.themNhanVien(new NhanVien("NV01", "ngothuha", "4n/0+VVr3Mj+uVm31GQvyw==", "1", "123"));

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                String tenDangNhap = txtUser.getText().toString().trim();
                String matKhau = txtPass.getText().toString().trim();
                if(tenDangNhap.equals("")){
                    Toast.makeText(LoginActivity.this, "Tên đăng nhập không được để trống!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(matKhau.equals("")){
                    Toast.makeText(LoginActivity.this, "Mật khẩu không được để trống!", Toast.LENGTH_SHORT).show();
                    return;
                }
                nhanVienDangNhap = dbNhanVien.xetDangNhap(tenDangNhap, matKhau);
                if(nhanVienDangNhap != null){
                    Toast.makeText(LoginActivity.this, "ĐĂNG NHẬP THÀNH CÔNG!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(LoginActivity.this, "XEM LẠI TÊN ĐĂNG NHẬP VÀ MẬT KHẨU", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String messagetosend = "12345";
                Properties pros = new Properties();
                pros.put("mail.smtp.auth", "true");
                pros.put("mail.smtp.starttls.enable", "true");
                pros.put("mail.smtp.host", "smtp.gmail.com");
                pros.put("mail.smtp.port", "587");
                Session session = Session.getInstance(pros, new javax.mail.Authenticator(){
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(mail, password);
                    }
                });
                try {
                    Message message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(mail));
                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("thuhango0204@gmail.com"));
                    message.setSubject("123");
                    message.setText("12345");
                    Transport.send(message);
                    Toast.makeText(LoginActivity.this, "Gửi mật khẩu rồi!", Toast.LENGTH_SHORT).show();
                }
                catch (MessagingException e){

                }
            }
        });
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    private void setControl() {
        txtUser = findViewById(R.id.txtUser);
        txtPass = findViewById(R.id.txtPass);
        btnLogin = findViewById(R.id.btnLogin);
        tvForgotPass = findViewById(R.id.tvForgotPass);
    }

    private void getDataBase() {
        dbNhanVien = new DBNhanVien(this);
        dbNhanVien.getReadableDatabase();
        dbLoaiThietBi = new DBLoaiThietBi(this);
        dbLoaiThietBi.getReadableDatabase();
        dbThietBi = new DBThietBi(this);
        dbThietBi.getReadableDatabase();
        dbPhongHoc = new DBPhongHoc(this);
        dbPhongHoc.getReadableDatabase();
        dbChiTietSD = new DBChiTietSD(this);
        dbChiTietSD.getReadableDatabase();
    }
}