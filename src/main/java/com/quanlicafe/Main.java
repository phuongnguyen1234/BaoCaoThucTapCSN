package com.quanlicafe;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            // Tải file FXML
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/dangNhapScreen.fxml"));

            // Thiết lập tiêu đề, giao diện và biểu tượng
            primaryStage.setTitle("Quản lí cà phê ABC");
            primaryStage.setScene(new Scene(root, 800, 600));
            
            // Đặt icon, đảm bảo file nằm trong resources/icons
            primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/icons/coffee-cup.png")));
            primaryStage.show();
            primaryStage.setResizable(false);
        } catch (Exception e) {
            // Xử lý lỗi nếu không tìm thấy resource hoặc gặp lỗi khi tải
            e.printStackTrace();
            System.err.println("Không thể tải giao diện. Vui lòng kiểm tra lại file FXML và đường dẫn.");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
