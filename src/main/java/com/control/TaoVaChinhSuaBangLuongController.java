package com.control;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import com.dto.BangLuongDTO;
import com.model.BangLuong;
import com.quanlicafe.DBConnection;

public class TaoVaChinhSuaBangLuongController {
    private Connection connection;

    public TaoVaChinhSuaBangLuongController(){
        try {
            this.connection = DBConnection.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<BangLuongDTO> layDanhSachBangLuongThangNay() {
        List<BangLuongDTO> danhSachBangLuong = new ArrayList<>();
        String sql = "SELECT MaBangLuong, bl.MaNhanVien, nv.TenNhanVien, nv.LoaiNhanVien, nv.ViTri, nv.MucLuong, Thang, SoNgayCong, SoNgayNghiCoCong, SoNgayNghiKhongCong, SoGioLamThem, " +
                    "SoLuongDonDaTao, ThuongDoanhThu, LuongThucNhan, GhiChu, DuocPhepChinhSua " +
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
                    bangLuong.setDuocPhepChinhSua(rs.getString("DuocPhepChinhSua"));

                    // Thêm đối tượng BangLuongDTO vào danh sách
                    danhSachBangLuong.add(bangLuong);
                }
                System.out.println("Load thanh cong");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Xử lý ngoại lệ nếu có lỗi trong khi truy vấn cơ sở dữ liệu
        }

        return danhSachBangLuong;
    }

    public List<BangLuongDTO> layDanhSachBangLuongTheoThoiGian(String thang, String nam) {
        List<BangLuongDTO> danhSachBangLuong = new ArrayList<>();
        String sql = "SELECT MaBangLuong, bl.MaNhanVien, nv.TenNhanVien, nv.LoaiNhanVien, nv.ViTri, nv.MucLuong, Thang, SoNgayCong, SoNgayNghiCoCong, SoNgayNghiKhongCong, SoGioLamThem, " +
                    "SoLuongDonDaTao, ThuongDoanhThu, LuongThucNhan, GhiChu, DuocPhepChinhSua " +
                    "FROM BANGLUONG bl INNER JOIN NHANVIEN nv ON nv.MaNhanVien = bl.MaNhanVien " +
                    "WHERE DATE_FORMAT(Thang, '%Y-%m') = ?";

        // Lấy tháng hiện tại (năm-tháng dạng yyyy-MM)
        String thangHienTai = nam + "-" + thang;

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
                    bangLuong.setDuocPhepChinhSua(rs.getString("DuocPhepChinhSua"));

                    // Thêm đối tượng BangLuongDTO vào danh sách
                    danhSachBangLuong.add(bangLuong);
                }
                System.out.println("Load thanh cong");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Xử lý ngoại lệ nếu có lỗi trong khi truy vấn cơ sở dữ liệu
        }

        return danhSachBangLuong;
    }


    public void sua(BangLuongDTO bangLuongUpdate) {
        //tinh lai luong thuc nhan
        int soGioLamMotNgay;
        String loaiNhanVien = bangLuongUpdate.getLoaiNhanVien();
        if("Full-time".equals(loaiNhanVien)){
            soGioLamMotNgay = 8;
        } else soGioLamMotNgay = 5;
        int updatedLuong = bangLuongUpdate.getSoNgayCong() * bangLuongUpdate.getMucLuong() * soGioLamMotNgay + bangLuongUpdate.getThuongDoanhThu();
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
    
    public String generateMaBangLuong() {
        String query = "SELECT MAX(CAST(SUBSTRING(MaBangLuong, 3) AS UNSIGNED)) FROM BANGLUONG";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                int lastMaBangLuong = rs.getInt(1);
                // Tạo mã bảng lương mới
                int newMaBangLuong = lastMaBangLuong + 1;
                return String.format("BL%04d", newMaBangLuong); // Định dạng mã bảng lương (BL0001, BL0002, ...)
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "BL0001"; // Nếu chưa có bảng lương nào, bắt đầu từ BL0001
    }

    public int taoBangLuongThangNay() {
        int soLuongBangLuongMoi = 0; // Biến đếm số bảng lương mới được tạo
        YearMonth currentMonth = YearMonth.now();
        YearMonth lastMonth = currentMonth.minusMonths(1);

        // Tính tổng doanh thu tháng trước
        String thuongQuery = "SELECT SUM(TongTien) FROM DONHANG WHERE MONTH(ThoiGianDatHang) = ? AND YEAR(ThoiGianDatHang) = ?";
        int totalRevenue = 0;
        try (PreparedStatement ps = connection.prepareStatement(thuongQuery)) {
            ps.setInt(1, lastMonth.getMonthValue());
            ps.setInt(2, lastMonth.getYear());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                totalRevenue = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Mức doanh thu chỉ tiêu
        double targetRevenue = 50000000;

        // Tính thưởng doanh thu
        int thuongDoanhThu = 0;
        if (totalRevenue >= targetRevenue * 1.5) {
            thuongDoanhThu = 1000000;
        } else if (totalRevenue >= targetRevenue * 1.25) {
            thuongDoanhThu = 500000;
        } else if (totalRevenue >= targetRevenue * 1.1) {
            thuongDoanhThu = 200000;
        }

        // Cập nhật bảng lương tháng trước
        String updateSalaryQuery = "SELECT bl.MaNhanVien, nv.MucLuong, bl.SoNgayCong, bl.SoGioLamThem, nv.LoaiNhanVien "
                + "FROM BANGLUONG bl INNER JOIN NHANVIEN nv ON bl.MaNhanVien = nv.MaNhanVien WHERE bl.Thang = ?";
        try (PreparedStatement ps = connection.prepareStatement(updateSalaryQuery)) {
            ps.setDate(1, Date.valueOf(lastMonth.atDay(1)));
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String maNhanVien = rs.getString("MaNhanVien");
                int mucLuong = rs.getInt("MucLuong");
                int soNgayCong = rs.getInt("SoNgayCong");
                int soGioLamThem = rs.getInt("SoGioLamThem");
                String loaiNhanVien = rs.getString("LoaiNhanVien");

                int soGioLamMotNgay;
                if("Full-time".equals(loaiNhanVien)){
                    soGioLamMotNgay = 8;
                } else soGioLamMotNgay = 5;

                int soNgayLamThem = soGioLamThem / soGioLamMotNgay;
                int soGioLamThemDu = soGioLamThem % soGioLamMotNgay;
                int soTienLamThem = (int) (soGioLamThemDu * mucLuong);

                soNgayCong += soNgayLamThem;

                int luongThucNhan = mucLuong * soNgayCong * soGioLamMotNgay + thuongDoanhThu + soTienLamThem;
                String ghiChu = String.format("Làm thêm %d giờ = %d ngày công + %d giờ. %d giờ = %d VND",
                        soGioLamThem, soNgayLamThem, soGioLamThemDu, soGioLamThemDu, soTienLamThem);

                String updateLuongQuery = "UPDATE BANGLUONG SET SoNgayCong = ?, ThuongDoanhThu = ?, LuongThucNhan = ?, GhiChu = ?, DuocPhepChinhSua = ? WHERE MaNhanVien = ? AND Thang = ?";
                try (PreparedStatement updatePs = connection.prepareStatement(updateLuongQuery)) {
                    updatePs.setInt(1, soNgayCong);
                    updatePs.setInt(2, thuongDoanhThu);
                    updatePs.setInt(3, luongThucNhan);
                    updatePs.setString(4, ghiChu);
                    updatePs.setInt(5, 0); // Không được phép chỉnh sửa
                    updatePs.setString(6, maNhanVien);
                    updatePs.setDate(7, Date.valueOf(lastMonth.atDay(1)));
                    updatePs.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Tạo bảng lương mới cho tháng hiện tại
        String getEmployeesQuery = "SELECT * FROM NHANVIEN WHERE MaNhanVien != 'NV000'";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(getEmployeesQuery)) {
            while (rs.next()) {
                String maNhanVien = rs.getString("MaNhanVien");
                String loaiNhanVien = rs.getString("LoaiNhanVien");
                int mucLuong = rs.getInt("MucLuong");

                String checkSalaryQuery = "SELECT COUNT(*) FROM BANGLUONG WHERE MaNhanVien = ? AND Thang = ?";
                try (PreparedStatement checkPs = connection.prepareStatement(checkSalaryQuery)) {
                    checkPs.setString(1, maNhanVien);
                    checkPs.setDate(2, Date.valueOf(currentMonth.atDay(1)));
                    ResultSet checkRs = checkPs.executeQuery();
                    if (checkRs.next() && checkRs.getInt(1) == 0) {
                        String maBangLuong = generateMaBangLuong();
                        int soNgayCong = loaiNhanVien.equalsIgnoreCase("Full-time") ? 28 : 20;

                        int soGioLamMotNgay = loaiNhanVien.equalsIgnoreCase("Full-time") ? 8 : 5;

                        String insertSalaryQuery = "INSERT INTO BANGLUONG (MaBangLuong, MaNhanVien, Thang, SoNgayCong, SoNgayNghiCoCong, SoNgayNghiKhongCong, SoGioLamThem, SoLuongDonDaTao, ThuongDoanhThu, LuongThucNhan, GhiChu, DuocPhepChinhSua)"
                                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                        try (PreparedStatement insertPs = connection.prepareStatement(insertSalaryQuery)) {
                            insertPs.setString(1, maBangLuong);
                            insertPs.setString(2, maNhanVien);
                            insertPs.setDate(3, Date.valueOf(currentMonth.atDay(1)));
                            insertPs.setInt(4, soNgayCong);
                            insertPs.setInt(5, 0);
                            insertPs.setInt(6, 0);
                            insertPs.setInt(7, 0);
                            insertPs.setInt(8, 0);
                            insertPs.setInt(9, 0);
                            insertPs.setInt(10, mucLuong * soNgayCong * soGioLamMotNgay + thuongDoanhThu);
                            insertPs.setString(11, "");
                            insertPs.setInt(12, 1);
                            insertPs.executeUpdate();
                            soLuongBangLuongMoi++;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return soLuongBangLuongMoi;
    }

}
