package com.control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import com.dto.BangLuongDTO;
import com.model.BangLuong;
import com.quanlicafe.DBConnection;

public class TaoVaChinhSuaBangLuongController {
    private Connection connection;

    public TaoVaChinhSuaBangLuongController() throws Exception {
        this.connection = DBConnection.getConnection();
    }

    public List<BangLuongDTO> layDanhSachBangLuongThangNay() {
        List<BangLuongDTO> danhSachBangLuong = new ArrayList<>();
        String sql = "SELECT MaBangLuong, bl.MaNhanVien, nv.TenNhanVien, nv.LoaiNhanVien, nv.ViTri, nv.MucLuong, Thang, SoNgayCong, SoNgayNghiCoCong, SoNgayNghiKhongCong, SoGioLamThem, " +
                    "SoLuongDonDaTao, ThuongDoanhThu, LuongThucNhan, GhiChu " +
                    "FROM BANGLUONG bl INNER JOIN NHANVIEN nv ON nv.MaNhanVien = bl.MaNhanVien " +
                    "WHERE DATE_FORMAT(Thang, '%Y-%m') = ?";

        // Lấy tháng hiện tại (năm-tháng dạng yyyy-MM)
        String thangHienTai = YearMonth.now().toString();

        try (PreparedStatement ppstm = connection.prepareStatement(sql)) {
            // Gán tham số cho câu truy vấn
            ppstm.setString(1, thangHienTai);

            BangLuong bl = new BangLuong();

            try (ResultSet rs = ppstm.executeQuery()) {
                // Xử lý kết quả trả về từ ResultSet
                while (rs.next()) {
                    BangLuongDTO bangLuong = new BangLuongDTO();
                    bangLuong.setMaBangLuong(rs.getString("MaBangLuong"));
                    bangLuong.setMaNhanVien(rs.getString("MaNhanVien"));
                    bangLuong.setTenNhanVien(rs.getString("TenNhanVien"));
                    bangLuong.setLoaiNhanVien(rs.getString("LoaiNhanVien"));
                    bangLuong.setViTri(rs.getString("ViTri"));
                    bangLuong.setMucLuong(rs.getInt("MucLuong"));
                    bangLuong.setThang(bl.dateSQLToYearMonth(rs.getDate("Thang").toString()));
                    bangLuong.setSoNgayCong(rs.getInt("SoNgayCong"));
                    bangLuong.setSoNgayNghiCoCong(rs.getInt("SoNgayNghiCoCong"));
                    bangLuong.setSoNgayNghiKhongCong(rs.getInt("SoNgayNghiKhongCong"));
                    bangLuong.setSoGioLamThem(rs.getInt("SoGioLamThem"));
                    bangLuong.setSoLuongDonDaTao(rs.getInt("SoLuongDonDaTao"));
                    bangLuong.setThuongDoanhThu(rs.getInt("ThuongDoanhThu"));
                    bangLuong.setLuongThucNhan(rs.getInt("LuongThucNhan"));
                    bangLuong.setGhiChu(rs.getString("GhiChu"));

                    // Thêm đối tượng BangLuongDTO vào danh sách
                    danhSachBangLuong.add(bangLuong);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Xử lý ngoại lệ nếu có lỗi trong khi truy vấn cơ sở dữ liệu
        }

        return danhSachBangLuong;
    }


    public void sua(BangLuongDTO bangLuongUpdate) {
        //tinh lai luong thuc nhan
        int updatedLuong = bangLuongUpdate.getSoNgayCong() * bangLuongUpdate.getMucLuong() + bangLuongUpdate.getThuongDoanhThu();
        bangLuongUpdate.setLuongThucNhan(updatedLuong);

        String sql = "UPDATE BANGLUONG SET SoNgayCong =?, SoNgayNghiCoCong =?, SoNgayNghiKhongCong =?, SoGioLamThem =?, SoLuongDonDaTao =?, ThuongDoanhThu =?, LuongThucNhan =?, GhiChu =? WHERE MaBangLuong =?";
    
        try (PreparedStatement ppstm = connection.prepareStatement(sql)) {
            // Gán giá trị cho các tham số
            ppstm.setInt(1, bangLuongUpdate.getSoNgayCong());
            ppstm.setInt(2, bangLuongUpdate.getSoNgayNghiCoCong());
            ppstm.setInt(3, bangLuongUpdate.getSoNgayNghiKhongCong());
            ppstm.setInt(4, bangLuongUpdate.getSoGioLamThem());
            ppstm.setInt(5, bangLuongUpdate.getSoLuongDonDaTao());
            ppstm.setInt(6, bangLuongUpdate.getThuongDoanhThu());
            ppstm.setInt(7, bangLuongUpdate.getLuongThucNhan());
            ppstm.setString(8, bangLuongUpdate.getGhiChu());
            ppstm.setString(9, bangLuongUpdate.getMaBangLuong());
    
            // Sử dụng executeUpdate() để thực thi lệnh UPDATE
            int rowsAffected = ppstm.executeUpdate();
    
            // Kiểm tra kết quả
            if (rowsAffected > 0) {
                System.out.println("Sửa thành công!");
            } else {
                System.out.println("Sửa thất bại! Không tìm thấy bảng lương có mã: " + bangLuongUpdate.getMaBangLuong());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    public boolean kiemTraBangLuongThangNayDaTonTai(){
        //lay thang truoc
        YearMonth thangTruoc = YearMonth.now().minusMonths(1);
        String thangTruocString = thangTruoc.toString();

        String sql = "SELECT * FROM BANGLUONG WHERE DATE_FORMAT(Thang, '%Y-%m') = ?";
        try (PreparedStatement ppstm = connection.prepareStatement(sql)) {
            ppstm.setString(1, thangTruocString);

            try (ResultSet rs = ppstm.executeQuery()) {
                return rs.next(); // Khi trả về true, tức là đã tồn tại bảng lương trong tháng trước
            } catch (Exception e){
                e.printStackTrace(); // Xử lý ngoại lệ nếu có l��i trong khi truy vấn cơ sở dữ liệu
            }
        } catch (Exception e){
            e.printStackTrace(); // Xử lý ngoại lệ nếu có l��i trong khi truy vấn cơ sở dữ liệu
        }
        return false;
    }
}
