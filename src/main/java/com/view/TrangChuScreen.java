package com.view;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.SQLException;

import com.control.CapNhatThucDonController;
import com.control.DangNhapController;
import com.control.DonHangController;
import com.control.PhanTichHoatDongController;
import com.control.QuanLiHoSoController;
import com.control.TaoDonGoiDoMoiController;
import com.control.TaoVaChinhSuaBangLuongController;
import com.model.NhanVien;
import com.model.SessionManager;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TrangChuScreen {
    @FXML
    private BorderPane mainBorderPane;

    @FXML
    private Button btnNhanVien, btnBangLuong, btnPhanTichHoatDong;

    @FXML
    private ImageView profileImage;

    @FXML
    private Text tenNguoiDungText;

    //khoi tao 1 controller cho cac chuc nang
    private TaoDonGoiDoMoiController taoDonController = new TaoDonGoiDoMoiController();
    private CapNhatThucDonController capNhatThucDonController = new CapNhatThucDonController();
    private TaoVaChinhSuaBangLuongController taoLuongController = new TaoVaChinhSuaBangLuongController();
    private PhanTichHoatDongController phanTichHoatDongController = new PhanTichHoatDongController();
    private DonHangController donHangController = new DonHangController();
    private QuanLiHoSoController quanLiHoSoController = new QuanLiHoSoController();

    private NhanVien nhanVien;

    @FXML
    public void initialize() {
        try {
            // Khai báo và lấy thông tin nhân viên (chỉ gọi 1 lần)
            nhanVien = SessionManager.getNhanVienByCurrentSession();
            if (nhanVien != null) {
                // Cập nhật tên người dùng
                tenNguoiDungText.setText(nhanVien.getTenNhanVien());

                // Cập nhật ảnh đại diện (nếu có)
                byte[] anhChanDung = nhanVien.getAnhChanDung();
                if (anhChanDung != null && anhChanDung.length > 0) {
                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(anhChanDung);
                    profileImage.setImage(new Image(byteArrayInputStream));
                } else {
                    profileImage.setImage(new Image("/icons/profile.png"));
                }

                // Thiết lập quyền truy cập
                if (nhanVien.getQuyenTruyCap().equalsIgnoreCase("User")) {
                    btnNhanVien.setDisable(true);
                    btnBangLuong.setDisable(true);
                    btnPhanTichHoatDong.setDisable(true);
                } else if (nhanVien.getQuyenTruyCap().equalsIgnoreCase("Admin")) {
                    btnNhanVien.setDisable(false);
                    btnBangLuong.setDisable(false);
                    btnPhanTichHoatDong.setDisable(false);
                }

                // Đảm bảo mainBorderPane đã được khởi tạo
                Platform.runLater(() -> {
                    if (mainBorderPane != null) {
                        Stage currentStage = (Stage) mainBorderPane.getScene().getWindow();
                        currentStage.setOnCloseRequest(event -> {
                            try {
                                String currentSessionId = SessionManager.getCurrentSessionId();
                                if(currentSessionId != null){
                                    NhanVien nhanVienHienTai = SessionManager.getNhanVienByCurrentSession();
                                    if(nhanVien!=null){
                                        DangNhapController.capNhatTrangThaiHoatDong(nhanVien.getEmail(), "0");
                                    SessionManager.removeSession(SessionManager.getCurrentSessionId());
                                    }
                                }
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            Platform.exit();
                        });
                    }
                });
            }
            thucDon();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi khi khởi tạo giao diện: " + e.getMessage());
        }
    }

    @FXML
    private void thucDon() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/thucDonScreen.fxml"));
            Node content = loader.load();

            // Lấy controller từ FXML đã tải
            ThucDonScreen screenController = loader.getController();
            // Truyền đối tượng TaoDonGoiDoMoiController đã khởi tạo từ trước
            screenController.setTaoDonController(taoDonController);
            screenController.setCapNhatThucDonController(capNhatThucDonController);
            
            screenController.setNhanVien(nhanVien);

            // Hiển thị danh sách đồ uống trong đơn hàng
            screenController.hienThiDanhSachCaPheTrongDon();

            // Hiển thị thực đơn
            screenController.hienThiThucDon(taoDonController.layThucDon("all"));
            screenController.datLai();

            // Đặt nội dung vào phần center của BorderPane
            mainBorderPane.setCenter(content);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Không thể tải file FXML: /fxml/thucDonScreen.fxml");
        }
    }


    @FXML
    private void donHang() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/donHangScreen.fxml"));
            Node content = loader.load();
            
            // Lấy controller từ FXML đã tải
            DonHangScreen screenController = loader.getController();

            screenController.setDonHangController(donHangController);
            screenController.setNhanVien(nhanVien);
            screenController.hienThiDanhSachDonHang(donHangController.loadDonHangFromDatabase());
            // Đặt nội dung vào phần center của BorderPane
            mainBorderPane.setCenter(content);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Không thể tải file FXML: /fxml/donHangScreen.fxml");
        }
    }

    @FXML
    private void nhanVien() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/quanLiHoSoScreen.fxml"));
            Node content = loader.load();

            QuanLiHoSoScreen screen = loader.getController();

            screen.setQuanLiHoSoController(quanLiHoSoController);
            screen.hienThiDanhSachNhanVien(quanLiHoSoController.layDanhSachNhanVien());

            mainBorderPane.setCenter(content);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Không thể tải file FXML: /fxml/quanLiHoSoScreen.fxml");
        }
    }

    @FXML
    private void bangLuong() {
        // Tải nội dung FXML và lấy controller của nó
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/bangLuongScreen.fxml"));
            Node content = loader.load();
            
            // Lấy controller từ FXML đã tải
            BangLuongScreen screenController = loader.getController();

            screenController.setTaoLuongController(taoLuongController);
            screenController.hienThiDanhSachBangLuong(taoLuongController.layDanhSachBangLuongThangNay());
            // Đặt nội dung vào phần center của BorderPane
            mainBorderPane.setCenter(content);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Không thể tải file FXML: /fxml/bangLuongScreen.fxml");
        }
    }


    @FXML
    private void phanTichHoatDong() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dashboard.fxml"));
            Node content = loader.load();
            
            // Lấy controller từ FXML đã tải
            PhanTichHoatDongScreen screenController = loader.getController();

            screenController.setPhanTichHoatDongController(phanTichHoatDongController);
            screenController.hienThiSoLieuThongKe();
            // Đặt nội dung vào phần center của BorderPane
            mainBorderPane.setCenter(content);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Không thể tải file FXML: /fxml/dashboard.fxml");
        }
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
        // Gọi phương thức logout
        SessionManager.dangXuatUserHienTai();
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
