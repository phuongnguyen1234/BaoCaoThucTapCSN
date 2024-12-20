package com.view;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.model.CaPhe;
import com.model.DanhMuc;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

public class ThemVaoThucDonScreen {
    @FXML
    private TextField tenCaPheTextField, donGiaTextField;

    @FXML
    private ImageView anhMinhHoaImageView;

    @FXML
    private ComboBox<DanhMuc> danhMucCombobox;

    private CaPhe caPhe;

    private ThucDonScreen thucDonScreen;

    public void setThucDonScreen(ThucDonScreen thucDonScreen) {
        this.thucDonScreen = thucDonScreen;
    }

    @FXML
    public void initialize() {
        try{
                // Lấy danh sách danh mục từ controller hoặc từ một dịch vụ khác
            List<DanhMuc> danhMucList = new ArrayList<>();
            danhMucList.add(0, new DanhMuc("CaPheThuong", "Cà phê thường"));
            danhMucList.add(1, new DanhMuc("CaPheDacBiet", "Cà phê đặc biệt"));
            danhMucList.add(2, new DanhMuc("CaPheLanh", "Cà phê pha lạnh"));
            danhMucList.add(3, new DanhMuc("CaPheTheoMua", "Cà phê theo mùa"));

            // Chuyển danh sách thành ObservableList và set vào ComboBox
            ObservableList<DanhMuc> observableDanhMucList = FXCollections.observableArrayList(danhMucList);

            // Set ObservableList vào ComboBox
            danhMucCombobox.setItems(observableDanhMucList);

            // Cập nhật cách hiển thị tên danh mục trong ComboBox
            danhMucCombobox.setCellFactory(param -> new ListCell<DanhMuc>() {
                @Override
                protected void updateItem(DanhMuc item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? "" : item.getTenDanhMuc());  // Hiển thị TenDanhMuc thay vì đối tượng
                }
            });
            
            danhMucCombobox.setButtonCell(new ListCell<DanhMuc>() {
                @Override
                protected void updateItem(DanhMuc item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? "" : item.getTenDanhMuc());  // Cập nhật khi hiển thị trên button
                }
            });
            

            // Chọn mặc định nếu cần (tùy chọn)
            if (!danhMucList.isEmpty()) {
                danhMucCombobox.getSelectionModel().selectFirst(); // Chọn mục đầu tiên nếu danh sách không rỗng
            }

            // Khi người dùng chọn danh mục, bạn có thể lấy mã danh mục
            danhMucCombobox.setOnAction(event -> {
                DanhMuc selectedDanhMuc = danhMucCombobox.getSelectionModel().getSelectedItem();
                if (selectedDanhMuc != null) {
                    String maDanhMuc = selectedDanhMuc.getMaDanhMuc();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void kichNutThemVaoThucDon() {
        if (caPhe == null) {
            caPhe = new CaPhe(); // Tạo đối tượng mới nếu chưa khởi tạo
        }

        int donGia = 0;

        // Kiểm tra và gán giá trị từ giao diện
        if (tenCaPheTextField.getText().isEmpty()) {
            showErrorAlert("Tên cà phê không được để trống!");
            return;
        }

        try {
            donGia = Integer.parseInt(donGiaTextField.getText());
            if (donGia <= 0) {
                showErrorAlert("Đơn giá phải lớn hơn 0!");
                return;
            }
        } catch (NumberFormatException e) {
            showErrorAlert("Đơn giá phải là một số nguyên hợp lệ!");
            return;
        }

        if (danhMucCombobox.getValue() == null) {
            showErrorAlert("Vui lòng chọn danh mục!");
            return;
        }

            // Xử lý ảnh
        if (anhMinhHoaImageView.getImage() != null) {
            try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
                ImageIO.write(SwingFXUtils.fromFXImage(anhMinhHoaImageView.getImage(), null), "png", byteArrayOutputStream);
                caPhe.setAnhMinhHoa(byteArrayOutputStream.toByteArray());
            } catch (IOException e) {
                showErrorAlert("Lỗi khi xử lý ảnh minh họa!");
                return;
            }
        } else {
            caPhe.setAnhMinhHoa(null);
        }

        caPhe.setTenCaPhe(tenCaPheTextField.getText());
        caPhe.setDonGia(donGia);
        caPhe.setMaDanhMuc(danhMucCombobox.getValue().getMaDanhMuc());

        // Thêm cà phê vào thực đơn
        thucDonScreen.getCapNhatThucDonController().themCaPhe(caPhe);

        // Cập nhật giao diện
        thucDonScreen.hienThiThucDon(thucDonScreen.getTaoDonController().layThucDon("all"));


        // Thông báo thành công
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thành công");
        alert.setHeaderText(null);
        alert.setContentText("Thêm cà phê vào thực đơn thành công!");
        alert.showAndWait();

        // Ẩn cửa sổ sau khi cập nhật
        tenCaPheTextField.getScene().getWindow().hide();
    }


    @FXML
    public void kichNutQuayLai(){
        tenCaPheTextField.getScene().getWindow().hide();
    }


    @FXML
    private void chonHinhAnh() {
        // Tạo đối tượng FileChooser
        FileChooser fileChooser = new FileChooser();

        // Cấu hình bộ lọc chỉ cho phép chọn các file ảnh
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.bmp", "*.gif")
        );

        // Hiển thị hộp thoại chọn file
        File selectedFile = fileChooser.showOpenDialog(tenCaPheTextField.getScene().getWindow());

        if (selectedFile != null) {
            try {
                // Đọc file ảnh đã chọn và chuyển thành đối tượng Image
                Image selectedImage = new Image(selectedFile.toURI().toString());

                // Hiển thị ảnh trong ImageView
                anhMinhHoaImageView.setImage(selectedImage);
            } catch (Exception e) {
                e.printStackTrace();
                // Thông báo lỗi nếu không thể tải ảnh
                System.err.println("Không thể tải ảnh: " + e.getMessage());
            }
        }
    }

    // Phương thức hiển thị thông báo lỗi
    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Lỗi");
        alert.setHeaderText("Thông báo lỗi");
        alert.setContentText(message);
        alert.showAndWait();
    }

}
