package com.view;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.control.CapNhatThucDonController;
import com.control.TaoDonGoiDoMoiController;
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

public class ChinhSuaDoUongTrongThucDonScreen {
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

    public void setCaPhe(CaPhe caPhe){
        this.caPhe = caPhe;
        loadChinhSuaCaPheData();
    }

    private void loadChinhSuaCaPheData() {
        if (caPhe != null) {
            // Cập nhật tên cà phê và đơn giá
            tenCaPheTextField.setText(caPhe.getTenCaPhe());
            donGiaTextField.setText(String.valueOf(caPhe.getDonGia()));
    
            // Tìm kiếm và chọn danh mục tương ứng trong ComboBox
            for (DanhMuc item : danhMucCombobox.getItems()) {
                if (item.getMaDanhMuc().equals(caPhe.getMaDanhMuc())) {
                    danhMucCombobox.setValue(item); // Chọn mục danh mục trong ComboBox
                    break;
                }
            }            
    
            // Cập nhật ảnh minh họa
            anhMinhHoaImageView.setImage(new Image(new ByteArrayInputStream(caPhe.getAnhMinhHoa())));
        }
    }
    

    @FXML
    public void kichNutCapNhat() {
        if (caPhe == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText("Không có dữ liệu để cập nhật");
            alert.setContentText("Vui lòng chọn một món cà phê để cập nhật!");
            alert.showAndWait();
            return;
        }

        try {
            // Kiểm tra và gán giá trị từ giao diện
            if (tenCaPheTextField.getText().isEmpty()) {
                throw new IllegalArgumentException("Tên cà phê không được để trống!");
            }
            caPhe.setTenCaPhe(tenCaPheTextField.getText());

            try {
                int donGia = Integer.parseInt(donGiaTextField.getText());
                if (donGia <= 0) throw new IllegalArgumentException("Đơn giá phải lớn hơn 0!");
                caPhe.setDonGia(donGia);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Đơn giá phải là một số nguyên hợp lệ!");
            }

            if (danhMucCombobox.getValue() == null) {
                throw new IllegalArgumentException("Vui lòng chọn danh mục!");
            }
            caPhe.setMaDanhMuc(danhMucCombobox.getValue().getMaDanhMuc());

            // Xử lý ảnh
            if (anhMinhHoaImageView.getImage() != null) {
                try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
                    ImageIO.write(SwingFXUtils.fromFXImage(anhMinhHoaImageView.getImage(), null), "png", byteArrayOutputStream);
                    caPhe.setAnhMinhHoa(byteArrayOutputStream.toByteArray());
                } catch (IOException e) {
                    throw new RuntimeException("Lỗi khi xử lý ảnh minh họa!");
                }
            }

            // Cập nhật cà phê
            CapNhatThucDonController controller = new CapNhatThucDonController();
            controller.suaCaPhe(caPhe);

            // Cập nhật giao diện
            if (thucDonScreen != null) {
                TaoDonGoiDoMoiController controller1 = new TaoDonGoiDoMoiController();
                thucDonScreen.hienThiThucDon(controller1.layThucDon("all"));
            }

            // Thông báo thành công
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thành công");
            alert.setHeaderText(null);
            alert.setContentText("Cập nhật cà phê thành công!");
            alert.showAndWait();

        } catch (IllegalArgumentException e) {
            // Hiển thị thông báo lỗi
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi nhập liệu");
            alert.setHeaderText("Dữ liệu không hợp lệ");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi hệ thống");
            alert.setHeaderText("Đã xảy ra lỗi");
            alert.setContentText("Vui lòng thử lại sau!");
            alert.showAndWait();
        }
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

}
