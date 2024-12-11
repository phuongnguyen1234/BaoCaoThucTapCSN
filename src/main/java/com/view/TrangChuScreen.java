package com.view;

import java.io.IOException;

import com.control.DangNhapController;
import com.control.TaoVaChinhSuaBangLuongController;
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
    private Button btnDonHang;

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
    // Tải nội dung FXML và lấy controller của nó
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/bangLuongScreen.fxml"));
        Node content = loader.load();
        
        // Lấy controller từ FXML đã tải
        BangLuongScreen screenController = loader.getController();
        
        // Gọi phương thức hienThiDanhSachBangLuong trong controller
        TaoVaChinhSuaBangLuongController controller = new TaoVaChinhSuaBangLuongController();
        screenController.hienThiDanhSachBangLuong(controller.layDanhSachBangLuongThangNay());

        // Đặt nội dung vào phần center của BorderPane
        mainBorderPane.setCenter(content);

    } catch (Exception e) {
        e.printStackTrace();
        System.err.println("Không thể tải file FXML: /fxml/bangLuongScreen.fxml");
    }
}


    @FXML
    private void phanTichHoatDong() {
        loadCenterContent("/fxml/dashboard.fxml");

        //xu li logic load phan tich hoat dong
    }

    @FXML
    private void dangXuat(){
        try {
            // Tải giao diện đăng nhập
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dangNhapScreen.fxml"));
            Parent loginRoot = loader.load();

            // Lấy Stage hiện tại và thay đổi Scene
            Stage currentStage = (Stage) btnDangXuat.getScene().getWindow();
            currentStage.setScene(new Scene(loginRoot));
            currentStage.setTitle("Quản lí cà phê ABC");

            //xu li logic dang xuat

        } catch (IOException e) {
            e.printStackTrace();
        }
    } 

    /**
     * Tải nội dung FXML vào khu vực trung tâm của BorderPane
     * @param fxmlFile tên file FXML cần load
     */
    private void loadCenterContent(String fxmlFile) {
        try {
            // Tải FXML mới
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Node content = loader.load();

            // Đặt nội dung vào phần center của BorderPane
            mainBorderPane.setCenter(content);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Không thể tải file FXML: " + fxmlFile);
        }
    }

    public void thietLapQuyenTruyCap(NhanVien nhanVien) {
        if (nhanVien.getQuyenTruyCap().equalsIgnoreCase("User")) {
            btnNhanVien.setDisable(true);
            btnThucDon.setDisable(true);
            if (nhanVien.getQuyenTruyCap().equalsIgnoreCase("User")) {
                // Disable buttons for user role
                btnNhanVien.setDisable(true);
                btnBangLuong.setDisable(true);
                btnPhanTichHoatDong.setDisable(true);
            }
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
