package com.example.devicemanagement.Controller;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.devicemanagement.DBHelper.DBChiTietSD;
import com.example.devicemanagement.DBHelper.DBLoaiThietBi;
import com.example.devicemanagement.DBHelper.DBNhanVien;
import com.example.devicemanagement.DBHelper.DBPhongHoc;
import com.example.devicemanagement.DBHelper.DBThietBi;
import com.example.devicemanagement.Entity.NhanVien;
import com.example.devicemanagement.R;

import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class LoginActivity extends AppCompatActivity {
    private DBNhanVien dbNhanVien;

    EditText txtUser, txtPass;
    Button btnLogin;
    TextView tvForgotPass, tvTieuDeQL;

    ArrayList<NhanVien> DSNV = new ArrayList<>();
    NhanVien nhanVienDangNhap = new NhanVien();

    final String mail = "thuhango0204@gmail.com";
    final String password = "tvchhnsxhegolohs";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setControl();
        startAnimation();
        setEvent();
    }

    private void startAnimation() {
        Animation animation = new AnimationUtils().loadAnimation(this, R.anim.anim);
        tvTieuDeQL.setAnimation(animation);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setEvent() {
        dbNhanVien = new DBNhanVien(this);
//        dbNhanVien.themNhanVien(new NhanVien("NV02", "Thu Hà", "02042000", "thuhango0204@gmail.com", "123"));
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                String tenDangNhap = txtUser.getText().toString().trim();
                String matKhau = txtPass.getText().toString().trim();
                if (tenDangNhap.equals("")) {
                    Toast.makeText(LoginActivity.this, "Tên đăng nhập không được để trống!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (matKhau.equals("")) {
                    Toast.makeText(LoginActivity.this, "Mật khẩu không được để trống!", Toast.LENGTH_SHORT).show();
                    return;
                }
                nhanVienDangNhap = dbNhanVien.xetDangNhap(tenDangNhap, matKhau);
                if (nhanVienDangNhap != null) {
                    Toast.makeText(LoginActivity.this, "ĐĂNG NHẬP THÀNH CÔNG!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                    //sử dụng bundle gửi dữ liệu của object nhân viên
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("thong_tin_nv", nhanVienDangNhap);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "XEM LẠI TÊN ĐĂNG NHẬP VÀ MẬT KHẨU", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvForgotPass.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                String tenDangNhap = txtUser.getText().toString().trim();
                if (tenDangNhap.equals("")) {
                    Toast.makeText(LoginActivity.this, "Bạn cần nhập tên đăng nhập!", Toast.LENGTH_SHORT).show();
                    return;
                }
                NhanVien nhanVien = dbNhanVien.xetGuiMail(tenDangNhap);

                if (nhanVien != null) {
                    String matKhauMoi = taoMatKhau();
                    dbNhanVien.suaMatKhau(nhanVien.getTenDangNhap(), matKhauMoi);
                    tvForgotPass.setEnabled(false);
                    Toast.makeText(LoginActivity.this, "VUI LÒNG ĐỢI, HỆ THỐNG ĐANG XỬ LÝ", Toast.LENGTH_SHORT).show();
                    guiMail(nhanVien.getMail(), matKhauMoi);
                } else
                    Toast.makeText(LoginActivity.this, "TÊN ĐĂNG NHẬP KHÔNG TỒN TẠI!", Toast.LENGTH_SHORT).show();
            }
        });
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public String taoMatKhau() {
        Random generator = new Random();
        int value = generator.nextInt((999999 - 100000) + 1) + 100000;
        return value + "";
    }

    private void guiMail(String mailToSend, String matKhauMoi) {
        Properties pros = new Properties();
        pros.put("mail.smtp.auth", "true");
        pros.put("mail.smtp.starttls.enable", "true");
        pros.put("mail.smtp.host", "smtp.gmail.com");
        pros.put("mail.smtp.port", "587");
        Session session = Session.getInstance(pros, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mail, password);
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailToSend));
            message.setSubject("APP QUẢN LÝ THIẾT BỊ TRƯỜNG HỌC - LẤY LẠI MẬT KHẨU");
            message.setText("Mật khẩu mới của bạn là: " + matKhauMoi);
            Transport.send(message);
            Toast.makeText(LoginActivity.this, "Mật khẩu mới đã được gửi vào mail!", Toast.LENGTH_SHORT).show();

        } catch (MessagingException e) {
            Log.e("Lỗi", e.getMessage());
        }
        tvForgotPass.setEnabled(true);
    }

    private void setControl() {
        txtUser = findViewById(R.id.txtUser);
        txtPass = findViewById(R.id.txtPass);
        btnLogin = findViewById(R.id.btnLogin);
        tvForgotPass = findViewById(R.id.tvForgotPass);
        tvTieuDeQL = findViewById(R.id.tvTieuDeQL);
    }
}