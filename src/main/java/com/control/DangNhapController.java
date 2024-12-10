package com.control;

import com.model.NhanVien;
import com.quanlicafe.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DangNhapController {
    private Connection connection;

    public DangNhapController() throws Exception {
        // Lấy kết nối từ DBConnection
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
                    capNhatTrangThaiHoatDong(email);

                    // Tạo đối tượng NhanVien
                    NhanVien nhanVien = new NhanVien();
                    nhanVien.setEmail(email);
                    nhanVien.setQuyenTruyCap(resultSet.getString("QuyenTruyCap"));
                    nhanVien.setTrangThaiHoatDong("1");

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

    private void capNhatTrangThaiHoatDong(String email) throws SQLException {
        String updateSql = "UPDATE NHANVIEN SET TrangThaiHoatDong = '1' WHERE Email = ?";
        try (PreparedStatement updateStatement = connection.prepareStatement(updateSql)) {
            updateStatement.setString(1, email);
            updateStatement.executeUpdate();
        }
    }


     // Phương thức đăng xuất
    public static void dangXuat(String email) {
        try (Connection conn = DBConnection.getConnection()) {
            // Cập nhật trạng thái hoạt động khi người dùng đăng xuất
            String sql = "UPDATE NHANVIEN SET TrangThaiHoatDong = '0' WHERE Email = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, email);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
