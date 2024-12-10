package com.view;

import com.control.DangNhapController;
import com.model.NhanVien;
import com.quanlicafe.DBConnection;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.sql.Connection;
import java.sql.DriverManager;


public class DangNhapScreen {
    @FXML
    private TextField emailTextField;
    @FXML
    private PasswordField matKhauTextField;

    private DangNhapController dangNhapController;

    public DangNhapScreen() {
    try {
        Connection connection = DBConnection.getConnection();
        dangNhapController = new DangNhapController(connection);
    } catch (Exception e) {
        e.printStackTrace();
    }
}

    @FXML
    private void kichNutDangNhap() {
        String email = emailTextField.getText();
        String matKhau = matKhauTextField.getText();

        try {
            NhanVien nhanVien = dangNhapController.dangNhap(email, matKhau);

            // Chuyển sang màn hình chính
            if (nhanVien.getQuyenTruyCap().equalsIgnoreCase("Admin")) {
                System.out.println("Admin đăng nhập thành công!");
                // Tải màn hình Admin
            } else if (nhanVien.getQuyenTruyCap().equalsIgnoreCase("User")) {
                System.out.println("User đăng nhập thành công!");
                // Tải màn hình User
            }
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    
}
