package com.view;

import java.io.ByteArrayInputStream;

import com.dto.NhanVienDTO;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ThongTinNhanVienScreen {

    
    private QuanLiHoSoScreen quanLiHoSoScreen;  // Thêm trường này

   // Phương thức để nhận tham chiếu từ màn hình chính
   public void setMainScreen(QuanLiHoSoScreen quanLiHoSoScreen) {
    this.quanLiHoSoScreen = quanLiHoSoScreen;
}

     @FXML
    private Text textMaNhanVien;

    @FXML
    private Text textTenNhanVien;

    @FXML
    private Text textGioiTinh;

    @FXML
    private Text textNgaySinh;

    @FXML
    private Text textQueQuan;

    @FXML
    private Text textDiaChi;

    @FXML
    private Text textSoDienThoai;

    @FXML
    private Text textLoaiNhanVien;

    @FXML
    private Text textViTri;

    @FXML
    private Text textThoiGianVaoLam;

    @FXML
    private Text textMucLuong;

    @FXML
    private Text textEmail;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField textField;

    @FXML
    private Text textQuyenTruyCap;

    @FXML
    private Text textTrangThaiHoatDong;

    @FXML
    private Circle circleTrangThai;
    
    @FXML
    private ImageView imgChanDung;

    @FXML
    private Button btnHienMatKhau;

    @FXML
    private Button btnQuayLai;


// Hiển thị thông tin nhân viên
public void hienThiThongTin(NhanVienDTO nhanVien) {
    textMaNhanVien.setText(nhanVien.getMaNhanVien());
    textTenNhanVien.setText(nhanVien.getTenNhanVien());
    textGioiTinh.setText(nhanVien.getGioiTinh());
    textNgaySinh.setText(nhanVien.getNgaySinh() != null ? nhanVien.getNgaySinh().toString() : "N/A");
    textQueQuan.setText(nhanVien.getQueQuan());
    textDiaChi.setText(nhanVien.getDiaChi());
    textSoDienThoai.setText(nhanVien.getSoDienThoai());
    textLoaiNhanVien.setText(nhanVien.getLoaiNhanVien());
    textViTri.setText(nhanVien.getViTri());
    textThoiGianVaoLam.setText(nhanVien.getThoiGianVaoLam() != null ? nhanVien.getThoiGianVaoLam().toString() : "N/A");
    textMucLuong.setText(String.valueOf(nhanVien.getMucLuong()));
    textEmail.setText(nhanVien.getEmail());
    passwordField.setText(nhanVien.getMatKhau());
    textField.setText(nhanVien.getMatKhau());
    textQuyenTruyCap.setText(nhanVien.getQuyenTruyCap());
    textTrangThaiHoatDong.setText(nhanVien.getTrangThaiHoatDong());

      // Không cho người dùng nhập liệu vào mật khẩu
      passwordField.setDisable(true);  // Không cho phép tương tác với trường mật khẩu

      // Xóa viền của PasswordField
      passwordField.setStyle("-fx-border-width: 0; -fx-background-color: transparent;");


     if ("1".equals(nhanVien.getTrangThaiHoatDong())) {
        circleTrangThai.setFill(Color.GREEN);  // Màu xanh lá nếu trạng thái là "1"
        textTrangThaiHoatDong.setText("Đang hoạt động");
    } else if ("0".equals(nhanVien.getTrangThaiHoatDong())) {
        circleTrangThai.setFill(Color.GRAY);  // Màu xám nếu trạng thái là "0"
        textTrangThaiHoatDong.setText("Không hoạt động");
    }
    
    // Cập nhật hình ảnh nhân viên nếu có
    if (nhanVien.getAnhChanDung() != null) {
        Image image = new Image(new ByteArrayInputStream(nhanVien.getAnhChanDung()));
        imgChanDung.setImage(image);
    } else {
        imgChanDung.setImage(null); // Hoặc hình ảnh mặc định
    }
}


private boolean isPasswordVisible = false;

@FXML
private void hienMatKhau() {
    if (isPasswordVisible) {
        // Nếu mật khẩu đang hiển thị, chuyển về PasswordField
        textField.setVisible(false);  // Ẩn TextField
        passwordField.setVisible(true);  // Hiển thị lại PasswordField
    } else {
        // Nếu mật khẩu đang ẩn, hiện ra
        passwordField.setVisible(false);  // Ẩn PasswordField
        textField.setText(passwordField.getText());  // Cập nhật nội dung mật khẩu cho TextField
        textField.setVisible(true);  // Hiển thị TextField
    }

    isPasswordVisible = !isPasswordVisible;  // Đảo ngược trạng thái hiển thị mật khẩu
}



@FXML
public void quayLai() {
    // Đóng cửa sổ hiện tại
    Stage currentStage = (Stage) btnQuayLai.getScene().getWindow();
    currentStage.close();  // Đóng cửa sổ thêm nhân viên
}

}

