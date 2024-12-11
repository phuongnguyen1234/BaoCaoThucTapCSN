package com.view;

import java.util.List;

import com.dto.BangLuongDTO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class BangLuongScreen {
    @FXML
    private TableView<BangLuongDTO> tableViewBangLuong;

    @FXML
    private TableColumn<BangLuongDTO, String> colMaBangLuong;

    @FXML
    private TableColumn<BangLuongDTO, String> colTenNhanVien;

    @FXML
    private TableColumn<BangLuongDTO, String> colThang;

    @FXML
    private TableColumn<BangLuongDTO, Integer> colNgayCong;

    @FXML
    private TableColumn<BangLuongDTO, Integer> colNghiCoCong;

    @FXML
    private TableColumn<BangLuongDTO, Integer> colNghiKhongCong;

    @FXML
    private TableColumn<BangLuongDTO, Integer> colThuongDoanhThu;

    @FXML
    private TableColumn<BangLuongDTO, Integer> colLuongThucNhan;

    @FXML
    private TableColumn<BangLuongDTO, String> colGhiChu;

    @FXML
    private TableColumn<BangLuongDTO, Void> colHanhDong; // Đảm bảo kiểu dữ liệu là Void vì đây là cột hành động.

    private final ObservableList<BangLuongDTO> bangLuongList = FXCollections.observableArrayList();

    public void initialize() {
        // Link columns with data from getters
        colMaBangLuong.setCellValueFactory(new PropertyValueFactory<>("maBangLuong"));
        colTenNhanVien.setCellValueFactory(new PropertyValueFactory<>("tenNhanVien"));
        colThang.setCellValueFactory(new PropertyValueFactory<>("thang"));
        colNgayCong.setCellValueFactory(new PropertyValueFactory<>("soNgayCong"));
        colNghiCoCong.setCellValueFactory(new PropertyValueFactory<>("soNgayNghiCoCong"));
        colNghiKhongCong.setCellValueFactory(new PropertyValueFactory<>("soNgayNghiKhongCong"));
        colThuongDoanhThu.setCellValueFactory(new PropertyValueFactory<>("thuongDoanhThu"));
        colLuongThucNhan.setCellValueFactory(new PropertyValueFactory<>("luongThucNhan"));
        colGhiChu.setCellValueFactory(new PropertyValueFactory<>("ghiChu"));

        // Cấu hình cột hành động với nút "Sửa"
        colHanhDong.setCellFactory(col -> new TableCell<>() {
            private final Button suaButton = new Button("Sửa");

            {
                // Tạo ImageView và thêm ảnh
                ImageView editIcon = new ImageView(getClass().getResource("/icons/write.png").toExternalForm());
                editIcon.setFitWidth(30); // Chiều rộng ảnh
                editIcon.setFitHeight(30); // Chiều cao ảnh
                suaButton.setGraphic(editIcon);

                suaButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
    
                // Gắn class CSS vào nút
                suaButton.getStyleClass().add("button-edit");
    
                // Gắn sự kiện onAction cho nút "Sửa"
                suaButton.setOnAction(event -> sua(getTableRow().getItem()));
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setGraphic(null);
                } else {
                    setGraphic(suaButton);
                }
            }
        });

        // Bind data to TableView
        tableViewBangLuong.setItems(bangLuongList);
    }


    private void sua(BangLuongDTO bangLuong) {
        if (bangLuong == null) {
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/chiTietBangLuongForm.fxml"));
            Parent root = loader.load();
    
            ChinhSuaBangLuongScreen controller = loader.getController();
            controller.setBangLuong(bangLuong);
            controller.setBangLuongScreen(this); // Truyền tham chiếu đến BangLuongScreen
    
            // Tạo và hiển thị dialog
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL); // Modal dialog
            stage.setTitle("Sửa Bảng Lương");
            stage.setScene(new Scene(root, 500, 690));
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    public void hienThiDanhSachBangLuong(List<BangLuongDTO> danhSachBangLuong){
        bangLuongList.clear();
        bangLuongList.addAll(danhSachBangLuong);
    }
}
