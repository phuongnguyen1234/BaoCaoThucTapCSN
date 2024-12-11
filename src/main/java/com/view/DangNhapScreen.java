package com.view;

import com.control.DangNhapController;
import com.model.LuuEmail;
import com.model.NhanVien;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.io.IOException;

public class DangNhapScreen {

    @FXML
    private TextField emailTextField;

    @FXML
    private PasswordField matKhauTextField;

    private DangNhapController dangNhapController;

    @FXML
    public void initialize() {
        try {
            // Khởi tạo DangNhapController với kết nối database
            dangNhapController = new DangNhapController();
        } catch (Exception e) {
            e.printStackTrace();
            showError("Lỗi kết nối đến cơ sở dữ liệu!");
        }
    }

    @FXML
    private void kichNutDangNhap(ActionEvent event) {
        String email = emailTextField.getText().trim();
        String matKhau = matKhauTextField.getText().trim();

        // Kiểm tra thông tin đầu vào
        if (email.isEmpty() || matKhau.isEmpty()) {
            showError("Vui lòng nhập đầy đủ email và mật khẩu!");
            return;
        }

        try {
            NhanVien nhanVien = dangNhapController.dangNhap(email, matKhau);

            // Lưu email người dùng vào session
            LuuEmail.setEmail(email);

            // Chuyển sang màn hình chính
            if ("Admin".equalsIgnoreCase(nhanVien.getQuyenTruyCap())) {
                System.out.println("Admin đăng nhập thành công!");
                chuyenManHinh("/fxml/trangChuScreen.fxml", "Trang chủ (Admin)");
            } else if ("User".equalsIgnoreCase(nhanVien.getQuyenTruyCap())) {
                System.out.println("User đăng nhập thành công!");
                chuyenManHinh("/fxml/trangChuScreen.fxml", "Trang chủ (User)");
            }
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    private void chuyenManHinh(String fxmlFile, String tieuDe) throws IOException {
        Stage currentStage = (Stage) emailTextField.getScene().getWindow(); // Lấy Stage hiện tại
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = loader.load();

        currentStage.setScene(new Scene(root));
        currentStage.setTitle(tieuDe); // Đặt tiêu đề cho màn hình mới
        currentStage.show();
    }

    private void showError(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setContentText(message);
        alert.show();
    }
}
