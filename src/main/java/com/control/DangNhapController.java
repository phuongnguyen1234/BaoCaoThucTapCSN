package com.control;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.model.NhanVien;
import com.quanlicafe.DBConnection;

public class DangNhapController {
    private Connection connection;

    public DangNhapController() throws Exception {
        this.connection = DBConnection.getConnection();
    }

    // Phương thức đăng nhập
    public NhanVien dangNhap(String email, String matKhau) throws Exception {
        // Kiểm tra tài khoản trong cơ sở dữ liệu
        String sql = "SELECT * FROM NHANVIEN WHERE Email = ? AND MatKhau = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, matKhau);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    boolean trangThai = resultSet.getString("TrangThaiHoatDong").equals("1");
                    if (trangThai) {
                        throw new Exception("Tài khoản đã được đăng nhập ở nơi khác!");
                    }

                    // Cập nhật trạng thái hoạt động
                    capNhatTrangThaiHoatDong(email, "1");

                    // Tạo đối tượng NhanVien
                    NhanVien nhanVien = new NhanVien();
                    nhanVien.setMaNhanVien(resultSet.getString("MaNhanVien"));
                    nhanVien.setTenNhanVien(resultSet.getString("TenNhanVien"));

                    // Lấy dữ liệu AnhChanDung từ BLOB
                    Blob blob = resultSet.getBlob("AnhChanDung");
                    if (blob != null) {
                    try (InputStream inputStream = blob.getBinaryStream()) {
                        
                        // Chuyển dữ liệu từ InputStream thành byte[]
                        byte[] anhChanDung = inputStream.readAllBytes();  // Hoặc sử dụng buffer nếu cần đọc lớn hơn
                        nhanVien.setAnhChanDung(anhChanDung);
                    } catch (IOException e) {
                        e.printStackTrace();
                        throw new Exception("Lỗi khi đọc ảnh từ cơ sở dữ liệu!");
                    }
                    }
                    nhanVien.setEmail(email);
                    nhanVien.setQuyenTruyCap(resultSet.getString("QuyenTruyCap"));

                    return nhanVien;
                } else {
                    throw new Exception("Email hoặc mật khẩu không đúng!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Lỗi kết nối cơ sở dữ liệu!");
        }
    }

    public static void capNhatTrangThaiHoatDong(String email, String trangThai) throws SQLException {
        String updateSql = "UPDATE NHANVIEN SET TrangThaiHoatDong = ? WHERE Email = ?";
        try (PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(updateSql)) {
            preparedStatement.setString(1, trangThai);
            preparedStatement.setString(2, email);
            preparedStatement.executeUpdate();
        } 
    }

}