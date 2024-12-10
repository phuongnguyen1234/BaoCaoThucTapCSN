package com.control;

import com.model.NhanVien;
import com.quanlicafe.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DangNhapController {
    private Connection connection;

    public DangNhapController(Connection connection) {
        this.connection = connection;
    }

    public NhanVien dangNhap(String email, String matKhau) throws Exception {
        String sql = "SELECT * FROM NHANVIEN WHERE Email = ? AND MatKhau = ?";
        // Tạo đối tượng NhanVien
        NhanVien nhanVien = new NhanVien();

        try (Connection conn = DBConnection.getConnection(); // Lấy kết nối từ DBConnection
             PreparedStatement preparedStatement = conn.prepareStatement(sql);){

        
        preparedStatement.setString(1, email);
        preparedStatement.setString(2, matKhau);

        ResultSet resultSet = preparedStatement.executeQuery();
        System.out.println("truy vấn thành công");
        System.out.println(resultSet);
        if (resultSet.next()) {
            String trangThai = resultSet.getString("TrangThaiHoatDong" );
            if (trangThai.equals("1")) {
                throw new Exception("Tài khoản đã được đăng nhập ở nơi khác!");
            } else {

            // Cập nhật trạng thái hoạt động
            String updateSql = "UPDATE NHANVIEN SET TrangThaiHoatDong = '1' WHERE Email = ?";
            PreparedStatement updateStatement = connection.prepareStatement(updateSql);
            updateStatement.setString(1, email);
            updateStatement.executeUpdate();

            nhanVien.setQuyenTruyCap(resultSet.getString("QuyenTruyCap"));
            nhanVien.setTrangThaiHoatDong("1");
            System.out.println("Đăng nhập thành công!");
         
        } } else {
            throw new Exception("Email hoặc mật khẩu không đúng!");
        }} catch (Exception e){
            e.printStackTrace();
        }
    return nhanVien;
}
}
