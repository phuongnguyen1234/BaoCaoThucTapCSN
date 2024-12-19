package com.view;

import java.io.ByteArrayInputStream;

import com.control.TaoDonGoiDoMoiController;
import com.dto.CaPheDTO;

import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class ChinhSuaDonScreen {
    @FXML
    private Text maCaPheText, tenCaPheText, donGiaText;

    @FXML
    private ImageView anhMinhHoaImageView;

    @FXML
    private Spinner<Integer> soLuongSpinner;

    @FXML
    private TextArea yeuCauDacBietTextArea;

    private CaPheDTO caPhe;

    private TaoDonGoiDoMoiController controller;
    private ThucDonScreen thucDonScreen;

    public void initialize() {
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1, 1);
        soLuongSpinner.setValueFactory(valueFactory);
    }

    public void setCaPhe(CaPheDTO caPhe) {
        this.caPhe = caPhe;
        loadChinhSuaDonData();
    }

    private void loadChinhSuaDonData() {
        if (caPhe != null) {
            maCaPheText.setText("Mã cà phê: " + caPhe.getMaCaPhe());
            tenCaPheText.setText("Tên cà phê: " + caPhe.getTenCaPhe());
            anhMinhHoaImageView.setImage(new Image(new ByteArrayInputStream(caPhe.getAnhMinhHoa())));
            donGiaText.setText("Đơn giá: " + caPhe.getDonGia());
            soLuongSpinner.getValueFactory().setValue(caPhe.getSoLuong());
            yeuCauDacBietTextArea.setText(caPhe.getYeuCauDacBiet());
        }
    }

    @FXML
    private void kichNutCapNhat() {
        if (caPhe != null) {
            int soLuong = soLuongSpinner.getValue();
            String yeuCau = yeuCauDacBietTextArea.getText();
            int tamTinh = soLuong*caPhe.getDonGia();

            caPhe.setSoLuong(soLuong);
            caPhe.setYeuCauDacBiet(yeuCau);
            caPhe.setTamTinh(tamTinh);

            thucDonScreen.getTaoDonController().sua(caPhe);
            thucDonScreen.hienThiDanhSachCaPheTrongDon();
            thucDonScreen.capNhatTongTien(thucDonScreen.getTaoDonController().layDanhSachCaPheTrongDon());

            tenCaPheText.getScene().getWindow().hide();
        }
    }

    @FXML
    private void kichNutQuayLai() {
        maCaPheText.getScene().getWindow().hide();
    }

    public void setThucDonScreen(ThucDonScreen thucDonScreen) {
        this.thucDonScreen = thucDonScreen;
    }

    public void setController(TaoDonGoiDoMoiController controller) {
        this.controller = controller;
    }
}

