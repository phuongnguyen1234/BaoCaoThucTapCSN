package com.view;

import com.control.TaoVaChinhSuaBangLuongController;
import com.dto.BangLuongDTO;

import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

public class ChinhSuaBangLuongScreen {
    @FXML
    private Text maBangLuongText;

    @FXML
    private Text tenNhanVienText;

    @FXML
    private Text loaiNhanVienText;

    @FXML
    private Text viTriText;

    @FXML
    private Text thangText;

    @FXML
    private Spinner<Integer> ngayCongSpinner;

    @FXML
    private Spinner<Integer> nghiCoCongSpinner;

    @FXML
    private Spinner<Integer> nghiKhongCongSpinner;

    @FXML
    private Spinner<Integer> gioLamThemSpinner;

    @FXML
    private Text soDonDaTaoText;

    @FXML
    private Text thuongDoanhThuText;

    @FXML
    private Text luongThucNhanText;

    @FXML
    private TextArea ghiChuTextArea;

    private BangLuongDTO bangLuong;

    public void initialize() {
    // Thiết lập giá trị tối thiểu, tối đa và bước nhảy
    SpinnerValueFactory<Integer> valueFactoryNgayCong = 
        new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 40, 0, 1);
    
    SpinnerValueFactory<Integer> valueFactoryNghiCoCong = 
        new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 40, 0, 1);
    
    SpinnerValueFactory<Integer> valueFactoryNghiKhongCong =
        new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 40, 0, 1);
    
    SpinnerValueFactory<Integer> valueFactoryGioLamThem = 
        new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0, 1);

    // Gán giá trị ban đầu (giá trị mặc định)
    ngayCongSpinner.setValueFactory(valueFactoryNgayCong);
    nghiCoCongSpinner.setValueFactory(valueFactoryNghiCoCong);
    nghiKhongCongSpinner.setValueFactory(valueFactoryNghiKhongCong);
    gioLamThemSpinner.setValueFactory(valueFactoryGioLamThem);
}

    public void setBangLuong(BangLuongDTO bangLuong) {
        this.bangLuong = bangLuong;
        loadBangLuongData(); // Load dữ liệu lên form
    }

    private void loadBangLuongData() {
        if (bangLuong != null) {
            maBangLuongText.setText("Mã bảng lương: " + bangLuong.getMaBangLuong());
            tenNhanVienText.setText("Tên nhân viên: " + bangLuong.getTenNhanVien());
            loaiNhanVienText.setText("Loại nhân viên: " + bangLuong.getLoaiNhanVien());
            viTriText.setText("Vị trí: " + bangLuong.getViTri());
            ngayCongSpinner.getValueFactory().setValue(bangLuong.getSoNgayCong());
            nghiCoCongSpinner.getValueFactory().setValue(bangLuong.getSoNgayNghiCoCong());
            nghiKhongCongSpinner.getValueFactory().setValue(bangLuong.getSoNgayNghiKhongCong());
            gioLamThemSpinner.getValueFactory().setValue(bangLuong.getSoGioLamThem());
            thangText.setText(bangLuong.getThang().toString());
            soDonDaTaoText.setText("Số đơn đã tạo: " + String.valueOf(bangLuong.getThuongDoanhThu()));
            thuongDoanhThuText.setText("Thưởng doanh thu: " + String.valueOf(bangLuong.getThuongDoanhThu()));
            luongThucNhanText.setText("Lương thực nhận: " + String.valueOf(bangLuong.getLuongThucNhan()));
            ghiChuTextArea.setText(bangLuong.getGhiChu());
        }
    }

    @FXML
    private void kichNutCapNhat() {
        if (bangLuong != null) {
            bangLuong.setSoNgayCong(ngayCongSpinner.getValue());
            bangLuong.setSoNgayNghiCoCong(nghiCoCongSpinner.getValue());
            bangLuong.setSoNgayNghiKhongCong(nghiKhongCongSpinner.getValue());
            bangLuong.setSoGioLamThem(gioLamThemSpinner.getValue());
            bangLuong.setGhiChu(ghiChuTextArea.getText());
        }
        try {
            TaoVaChinhSuaBangLuongController controller = new TaoVaChinhSuaBangLuongController();
            controller.sua(bangLuong);

            // Làm mới danh sách ở BangLuongScreen
            if (bangLuongScreen != null) {
                bangLuongScreen.hienThiDanhSachBangLuong(controller.layDanhSachBangLuongThangNay());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Đóng cửa sổ hiện tại
        maBangLuongText.getScene().getWindow().hide();
    }


    @FXML
    private void kichNutQuayLai(){
        maBangLuongText.getScene().getWindow().hide();
    }

    private BangLuongScreen bangLuongScreen;

    public void setBangLuongScreen(BangLuongScreen bangLuongScreen) {
        this.bangLuongScreen = bangLuongScreen;
    }

}
