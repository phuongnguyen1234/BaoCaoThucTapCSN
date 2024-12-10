package com.view;

import java.io.IOException;

import com.control.DangNhapController;
import com.model.LuuEmail;
import com.model.NhanVien;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class TrangChuScreen {
    @FXML
    private BorderPane mainBorderPane;

    @FXML
    private Button btnDangXuat;

    @FXML
    private Button btnThucDon;

    @FXML
    private Button btnNhanVien;

    @FXML
    private Button btnBangLuong;

    @FXML
    private Button btnPhanTichHoatDong;

    @FXML
    private void thucDon() {
        loadCenterContent("/fxml/thucDonScreen.fxml");

        //xu li logic load thuc don
    }

    @FXML
    private void donHang() {
        loadCenterContent("/fxml/donHangScreen.fxml");

        //xu li logic load don hang
    }

    @FXML
    private void nhanVien() {
        loadCenterContent("/fxml/quanLiHoSoScreen.fxml");

        //xu li logic load nhan vien
    }

    @FXML
    private void bangLuong() {
        loadCenterContent("/fxml/bangLuongScreen.fxml");

        //xu li logic load bang luong
    }

    @FXML
    private void phanTichHoatDong() {
        loadCenterContent("/fxml/dashboard.fxml");

        //xu li logic load phan tich hoat dong
    }

    // Method to load center content based on action
    private void loadCenterContent(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();
            mainBorderPane.setCenter(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method for setting user access rights (disable buttons based on role)
    @FXML
    public void thietLapQuyenTruyCap(NhanVien nhanVien) {
        if (nhanVien.getQuyenTruyCap().equalsIgnoreCase("User")) {
            // Disable buttons for user role
            btnNhanVien.setDisable(true);
            btnBangLuong.setDisable(true);
            btnPhanTichHoatDong.setDisable(true);
        }
    }

    @FXML
    private void dangXuat(ActionEvent event) {
        // Lấy email từ UserSession và đăng xuất
        String email = LuuEmail.getEmail(); 
        DangNhapController.dangXuat(email);

        // Chuyển về màn hình đăng nhập
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dangNhapScreen.fxml"));
            Parent root = loader.load();
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));
            currentStage.setTitle("Đăng nhập");
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    }

