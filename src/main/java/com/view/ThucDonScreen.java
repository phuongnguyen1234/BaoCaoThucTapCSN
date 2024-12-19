package com.view;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.control.CapNhatThucDonController;
import com.control.TaoDonGoiDoMoiController;
import com.dto.CaPheDTO;
import com.dto.DanhMucDTO;
import com.dto.DonHangDTO;
import com.model.CaPhe;
import com.model.DanhMuc;
import com.model.NhanVien;
import com.model.SessionManager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class ThucDonScreen {
    @FXML
    private TableView<CaPheDTO> tableViewDatHang;

    @FXML
    private TableColumn<CaPheDTO, String> colTenCaPhe, colYeuCauDacBiet;

    @FXML
    private TableColumn<CaPheDTO, Integer> colSoLuong, colDonGia, colTamTinh;

    @FXML
    private TableColumn<CaPheDTO, Void> colHanhDong;

    @FXML
    private VBox vBoxThucDon;

    @FXML
    private Text tongTienText;

    @FXML
    private ComboBox<DanhMuc> danhMucCombobox;

    @FXML
    private Button btnThemVaoThucDon;

    private DonHangDTO donHang; //bien luu data de hien thi len man hinh

    private final ObservableList<CaPheDTO> danhSachCaPheTrongDon = FXCollections.observableArrayList();

    private TaoDonGoiDoMoiController taoDonController;

    private CapNhatThucDonController capNhatThucDonController;

    private NhanVien nhanVien;

    public void setTaoDonController(TaoDonGoiDoMoiController taoDonController) {
        this.taoDonController = taoDonController;
    }

    public TaoDonGoiDoMoiController getTaoDonController() {
        return taoDonController;
    }

    public void setCapNhatThucDonController(CapNhatThucDonController capNhatThucDonController){
        this.capNhatThucDonController = capNhatThucDonController;
    }

    public void setNhanVien(NhanVien nhanVien){
        this.nhanVien = nhanVien;
    }

    public void initialize() {
        // Cấu hình các cột trong TableView
        colTenCaPhe.setCellValueFactory(new PropertyValueFactory<>("tenCaPhe"));
        colYeuCauDacBiet.setCellValueFactory(new PropertyValueFactory<>("yeuCauDacBiet"));
        colSoLuong.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
        colDonGia.setCellValueFactory(new PropertyValueFactory<>("donGia"));
        colTamTinh.setCellValueFactory(new PropertyValueFactory<>("tamTinh"));
    
        colHanhDong.setCellFactory(col -> new TableCell<>() {
            private final Button suaButton = new Button("Sửa");
            private final Button xoaButton = new Button("Xóa");
    
            {
                suaButton.getStyleClass().add("btn-sua");
                suaButton.setOnAction(event -> sua(getTableRow().getItem()));
    
                xoaButton.getStyleClass().add("btn-xoa");
                xoaButton.setOnAction(event -> xoa(getTableRow().getItem()));
            }
    
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
    
                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setGraphic(null);
                } else {
                    HBox hBox = new HBox(10, suaButton, xoaButton);
                    hBox.setAlignment(Pos.CENTER);
                    setGraphic(hBox);
                }
            }
        });

        tableViewDatHang.getColumns().forEach(column -> {
            column.setReorderable(false);
        });
        
    
        // Tải danh sách danh mục từ Controller
        List<DanhMuc> danhMucList = new ArrayList<>();
        danhMucList.add(0, new DanhMuc("all", "Tất cả")); // Thêm giá trị "Tất cả" vào đầu danh sách
        danhMucList.add(1, new DanhMuc("CaPheThuong", "Cà phê thường"));
        danhMucList.add(2, new DanhMuc("CaPheDacBiet", "Cà phê đặc biệt"));
        danhMucList.add(3, new DanhMuc("CaPheLanh", "Cà phê pha lạnh"));
        danhMucList.add(4, new DanhMuc("CaPheTheoMua", "Cà phê theo mùa"));
    
        ObservableList<DanhMuc> observableDanhMucList = FXCollections.observableArrayList(danhMucList);
        danhMucCombobox.setItems(observableDanhMucList);
    
        // Cài đặt hiển thị của combobox
        danhMucCombobox.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(DanhMuc item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getTenDanhMuc());
            }
        });

        danhMucCombobox.setButtonCell(new ListCell<DanhMuc>() {
            @Override
            protected void updateItem(DanhMuc item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getTenDanhMuc());  // Cập nhật khi hiển thị trên button
            }
        });
    
        // Đặt giá trị mặc định là "Tất cả"
        danhMucCombobox.setValue(danhMucList.get(0)); 
    
        // Xử lý sự kiện khi chọn danh mục
        danhMucCombobox.setOnAction(event -> {
            DanhMuc selectedDanhMuc = danhMucCombobox.getValue();
            if (selectedDanhMuc != null) {
                if ("all".equals(selectedDanhMuc.getMaDanhMuc())) {
                    hienThiThucDon(taoDonController.layThucDon("all")); 
                } else {
                    List<DanhMucDTO> filteredList = taoDonController.layThucDon(selectedDanhMuc.getMaDanhMuc());
                    hienThiThucDon(filteredList); // Hiển thị danh sách cà phê theo danh mục được chọn
                }
            }
        });
    
        // Gắn dữ liệu TableView với danh sách cà phê trong đơn
        tableViewDatHang.setItems(danhSachCaPheTrongDon);
        //nhanVien = session.getNhanVienByCurrentSession();
        btnThemVaoThucDon.setVisible(false);
    }
    
    
    private void sua(CaPheDTO caPhe) {
        if (caPhe == null) return;
    
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/chinhSuaDonForm.fxml"));
            Parent root = loader.load();
    
            ChinhSuaDonScreen controller = loader.getController();
            controller.setCaPhe(caPhe);
            controller.setThucDonScreen(this);
            controller.setController(taoDonController);
    
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Chỉnh sửa " + caPhe.getTenCaPhe() + " trong đơn hàng");
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    private void xoa(CaPheDTO caPhe) {
        if (caPhe == null) {
            return;
        }
    
        // Tạo Alert kiểu CONFIRMATION
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận xóa");
        alert.setHeaderText("Bạn có chắc chắn muốn xóa " + caPhe.getTenCaPhe() + " ra khỏi đơn?");
        alert.setContentText("Hành động này không thể hoàn tác.");
    
        // Hiển thị và chờ người dùng phản hồi
        Optional<ButtonType> result = alert.showAndWait();
    
        // Xử lý phản hồi của người dùng
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                // Gọi hàm xóa trong TaoDonGoiDoMoiController
                taoDonController.xoa(caPhe);
    
                // Cập nhật lại giao diện TableView
                hienThiDanhSachCaPheTrongDon();
                capNhatTongTien(danhSachCaPheTrongDon);
    
                System.out.println("Món đã được xóa: " + caPhe.getTenCaPhe());
            } catch (Exception e) {
                e.printStackTrace();
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Lỗi");
                errorAlert.setHeaderText("Không thể xóa món.");
                errorAlert.setContentText("Vui lòng thử lại sau.");
                errorAlert.showAndWait();
            }
        } else {
            System.out.println("Người dùng đã hủy.");
        }
    }
    

    public void hienThiDanhSachCaPheTrongDon() {
        tableViewDatHang.setItems(taoDonController.layDanhSachCaPheTrongDon());
        tableViewDatHang.refresh();
        capNhatTongTien(danhSachCaPheTrongDon);
    }
    

    public void hienThiThucDon(List<DanhMucDTO> danhMucList) {
        vBoxThucDon.getChildren().clear(); // Xóa các phần tử cũ trong VBox

        for (DanhMucDTO danhMuc : danhMucList) {
            // Tạo Label hiển thị tên danh mục
            Label lblDanhMuc = new Label(danhMuc.getTenDanhMuc());
            lblDanhMuc.getStyleClass().add("category-label");

            // Thêm Label vào VBox
            vBoxThucDon.getChildren().add(lblDanhMuc);

            // Tạo GridPane cho danh sách cà phê
            GridPane gridPane = new GridPane();
            gridPane.setHgap(10); // Khoảng cách ngang giữa các cột
            gridPane.setVgap(10); // Khoảng cách dọc giữa các hàng
            gridPane.setPadding(new Insets(8, 8, 25, 8));

            // Thiết lập chiều cao của các hàng sao cho chúng có chiều cao bằng nhau
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setVgrow(Priority.ALWAYS); // Cung cấp không gian cho hàng để giãn đều
            gridPane.getRowConstraints().add(rowConstraints);

            // Lấy danh sách cà phê của danh mục
            List<CaPhe> danhSachCaPhe = danhMuc.getDanhSachCaPhe();

            // Thêm cà phê vào GridPane (4 sản phẩm mỗi hàng)
            int columns = 4; // Số cột cố định
            for (int i = 0; i < danhSachCaPhe.size(); i++) {
                CaPhe caPhe = danhSachCaPhe.get(i);

                // Tạo VBox cho mỗi sản phẩm cà phê
                VBox caPheBox = new VBox(6); // Khoảng cách giữa các thành phần bên trong VBox
                caPheBox.setAlignment(Pos.CENTER);
                caPheBox.setMaxSize(160, 280); // Thiết lập chiều cao và chiều rộng tối đa
                caPheBox.setPrefSize(160, 260); // Thiết lập chiều cao và chiều rộng ưa thích
                caPheBox.getStyleClass().add("white-bg");
                caPheBox.getStyleClass().add("shadow");
                caPheBox.getStyleClass().add("radius");

                // Ảnh minh họa
                ImageView anhMinhHoaImageView = new ImageView();
                anhMinhHoaImageView.setFitHeight(120); // Chiều cao ảnh nhỏ hơn
                anhMinhHoaImageView.setFitWidth(120); // Chiều rộng ảnh nhỏ hơn
                anhMinhHoaImageView.setPreserveRatio(false);
                if (caPhe.getAnhMinhHoa() != null && caPhe.getAnhMinhHoa().length > 0) {
                    InputStream inputStream = new ByteArrayInputStream(caPhe.getAnhMinhHoa());
                    anhMinhHoaImageView.setImage(new Image(inputStream));
                } else {
                    anhMinhHoaImageView.setImage(new Image(getClass().getResource("/icons/coffee.png").toExternalForm()));
                }

                // Thêm sự kiện click vào ảnh cà phê
                anhMinhHoaImageView.setCursor(Cursor.HAND);
                anhMinhHoaImageView.setOnMouseClicked(event -> {
                    System.out.println("Thêm cà phê vào đơn: " + caPhe.getTenCaPhe());
                    CaPheDTO dto = new CaPheDTO();
                    dto.setMaCaPhe(caPhe.getMaCaPhe());
                    dto.setTenCaPhe(caPhe.getTenCaPhe());
                    dto.setAnhMinhHoa(caPhe.getAnhMinhHoa());
                    dto.setDonGia(caPhe.getDonGia());
                    dto.setMaDanhMuc(caPhe.getMaDanhMuc());

                    hienThiFormThemDoUong(dto); // Gọi phương thức mở form thêm vào đơn hàng
                });

                // Tên cà phê
                Text tenCaPheText = new Text(caPhe.getTenCaPhe());
                tenCaPheText.setWrappingWidth(156); // Giới hạn chiều rộng để text không tràn
                tenCaPheText.setTextAlignment(TextAlignment.CENTER);
                tenCaPheText.setStyle("-fx-font-size: 17px; -fx-font-weight: bold; -fx-font-family: Open Sans;");

                // Tạo VBox để chứa Text và cố định chiều cao
                VBox vBoxText = new VBox();
                vBoxText.setPrefHeight(45);
                vBoxText.setMinHeight(45);
                vBoxText.setMaxHeight(45); // Cố định chiều cao cho VBox (và Text bên trong)
                vBoxText.setAlignment(Pos.CENTER);
                vBoxText.getChildren().add(tenCaPheText);

                // Đơn giá
                Text donGiaText = new Text(String.format("%d VND", caPhe.getDonGia()));
                donGiaText.setWrappingWidth(130);
                donGiaText.setTextAlignment(TextAlignment.CENTER);
                donGiaText.setStyle("-fx-font-size: 16px; -fx-font-family: Open Sans;");

                // Nút sửa (chỉ hiển thị nếu quyền truy cập là Admin)
                HBox hBoxButtons = new HBox(5);
                hBoxButtons.setPrefWidth(100);
                hBoxButtons.setPrefHeight(47);
                hBoxButtons.setAlignment(Pos.CENTER);

                if ("Admin".equals(nhanVien.getQuyenTruyCap())) {
                    btnThemVaoThucDon.setVisible(true);
                    Button btnSua = new Button();
                    btnSua.setPrefSize(40, 40); // Kích thước nhỏ hơn
                    btnSua.setStyle("-fx-background-color: #1f9eed; -fx-padding: 0;"); // Không viền, không padding
                    ImageView iconSua = new ImageView(new Image(getClass().getResource("/icons/write.png").toExternalForm()));
                    iconSua.setFitWidth(33); // Thu nhỏ biểu tượng
                    iconSua.setFitHeight(30);
                    btnSua.setGraphic(iconSua); // Gán biểu tượng vào nút
                    btnSua.setCursor(Cursor.HAND);
                    btnSua.setOnAction(event -> {
                        System.out.println("Sửa cà phê: " + caPhe.getTenCaPhe());
                        moFormSuaCaPheTrongThucDon(caPhe);
                    });

                    hBoxButtons.getChildren().add(btnSua);
                }

                // Thêm các thành phần vào VBox
                caPheBox.getChildren().addAll(anhMinhHoaImageView, vBoxText, donGiaText, hBoxButtons);

                // Thêm VBox cà phê vào GridPane
                int row = i / columns; // Hàng hiện tại
                int col = i % columns; // Cột hiện tại
                gridPane.add(caPheBox, col, row);
            }

            // Thêm GridPane vào VBox
            vBoxThucDon.getChildren().add(gridPane);
        }
    }

    
    @FXML
    private void thanhToan() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/chiTietDonHangDeThanhToan.fxml"));
            Parent root = loader.load();

            // Tải controller của form hóa đơn
            XacNhanDonHangScreen controller = loader.getController();

            //set thong tin don hang
            donHang = new DonHangDTO();
            donHang.setMaNhanVien(nhanVien.getMaNhanVien());
            donHang.setTenNhanVien(nhanVien.getTenNhanVien());
            donHang.setDanhSachCaPheTrongDon(taoDonController.layDanhSachCaPheTrongDon());
            int tongTien = 0;
            for (CaPheDTO caPheDTO : taoDonController.layDanhSachCaPheTrongDon()) {
                tongTien += caPheDTO.getTamTinh(); // Cộng giá trị tamTinh của từng món
            }
            donHang.setTongTien(tongTien);

            controller.setDonHang(donHang);
            controller.setTaoDonController(taoDonController);
            controller.setThucDonScreen(this);

            // Hiển thị dialog
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Hóa Đơn");
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText("Không thể mở form hóa đơn.");
            alert.setContentText("Vui lòng kiểm tra lại file FXML.");
            alert.showAndWait();
        }
    }

    public void hienThiFormThemDoUong(CaPheDTO caPhe) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/themVaoDonForm.fxml"));
            Parent root = loader.load();

            ThemVaoDonScreen controller = loader.getController();
            controller.setCaPhe(caPhe);
            controller.setThucDonScreen(this);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Thêm " + caPhe.getTenCaPhe() + " vào đơn");
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void moFormSuaCaPheTrongThucDon(CaPhe caPhe){
        //kiem tra xem co nhan vien thu ngan nao khac dang hoat dong
        boolean nhanVienKhacOnline = capNhatThucDonController.kiemTraNhanVienKhacDangOnline();

        if(nhanVienKhacOnline){ //neu co nhan vien online
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText("Không thể chỉnh sửa đồ uống");
            alert.setContentText("Hiện tại, vẫn đang có nhân viên thu ngân đang hoạt động. Vui lòng thử lại sau!");
            alert.showAndWait();
        }
        else{
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/chinhSuaDoUongTrongThucDonForm.fxml"));
                Parent root = loader.load();

                ChinhSuaDoUongTrongThucDonScreen controller = loader.getController();
                controller.setCaPhe(caPhe);
                controller.setThucDonScreen(this);

                // Hiển thị dialog
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Sửa cà phê " + caPhe.getTenCaPhe());
                stage.setScene(new Scene(root));
                stage.setResizable(false);
                stage.showAndWait();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void themVaoThucDon(){
        //kiem tra xem co nhan vien thu ngan nao khac dang hoat dong
        boolean nhanVienKhacOnline = capNhatThucDonController.kiemTraNhanVienKhacDangOnline();

        if(nhanVienKhacOnline){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText("Không thể chỉnh sửa đồ uống");
            alert.setContentText("Hiện tại, vẫn đang có nhân viên thu ngân đang hoạt động. Vui lòng thử lại sau!");
            alert.showAndWait();
        }
        else{
            try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/themVaoThucDonForm.fxml"));
            Parent root = loader.load();

            ThemVaoThucDonScreen controller = loader.getController();
            controller.setThucDonScreen(this);

            // Hiển thị dialog
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Thêm cà phê vào thực đơn");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.showAndWait();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void capNhatTongTien(ObservableList<CaPheDTO> danhSachCaPheTrongDon) {
        int tongTien = 0;
        // Lặp qua từng món trong danh sách và tính tổng tiền
        for (CaPheDTO caPheDTO : danhSachCaPheTrongDon) {
            tongTien += caPheDTO.getTamTinh(); // Cộng giá trị tamTinh của từng món
        }
    
        // Cập nhật tổng tiền lên giao diện
        tongTienText.setText("Tổng tiền: " + tongTien + " VND");
    }

    @FXML
    public void datLai() {
        // Xóa tất cả các món trong đơn
        taoDonController.resetDanhSachCaPheTrongDon();
            
        // Cập nhật lại TableView
        tableViewDatHang.setItems(danhSachCaPheTrongDon);
        tableViewDatHang.refresh();
    
        // Cập nhật lại tổng tiền
        capNhatTongTien(danhSachCaPheTrongDon);
    } 
}
