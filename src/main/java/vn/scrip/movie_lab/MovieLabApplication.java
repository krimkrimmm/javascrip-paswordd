//B1:
//
//Tạo controller để trả về giao diện cho trang quên mật khẩu
//B2:
//Xử lý validation cho form quên mật khẩu (xử lý ở frontend - áp dụng Jquery validation)
//+ Email không được để trống
//+ Email không đúng định dạng
//B3:
//
//Viết API backend xử lý trường hợp quên mật khẩu -> gửi đường link kích hoạt vào trong email
//POST /api/auth/forgot-password (AuthApi)
//Request body {
//email : String
//}
//Các bước thực hiện:
//Validation email phía backend với các rule tương tự như frontend (Spring Validation)
//Kiểm tra email có tồn tại trong hệ thống không? -> Nếu không có throw Exception
//Nếu có thì tạo ra token kích hoạt (token giống với trường hợp đăng nhập nhưng có type = “FORGOT_PASSWORD”)
//Đường link kích hoạt sẽ có dạng: http://localhost:8080/dat-lai-mat-khau?token=token
//Gửi email chứa link kích hoạt đến email của người dùng
//B4:
//Ở frontend, khi người dùng click vào nút “Quên mật khẩu” thì gửi request lên server
//Nếu thành công -> Hiển thị thông báo “Kiểm tra email của bạn để lấy link đặt lại mật khẩu”
//B5:
//Khi người dùng click vào link trong email -> Mở ra trang đặt lại mật khẩu (tương tự như trang xác thực tài khoản đã làm trong buổi hôm trước)
//Trong WebController định nghĩa API: GET /dat-lai-mat-khau để trả về trang đặt lại mật khẩu
//Các bước xử lý kiểm tra với token -> trả về message tương ứng (làm tương tự như /xac-thuc-tai-khoan)
//Trường hợp success = false -> Hiển thị message tương ứng
//Trường hợp success = true -> Hiển thị form để đặt lại mật khẩu
//Form đặt lại mật khẩu gồm 2 trường input có tiêu đề: Mật khẩu mới, Xác nhận mật khẩu mới
//B6:
//Xử lý validation cho form đặt lại mật khẩu (xử lý ở frontend - áp dụng Jquery validation)
//Mật khẩu không được để trống
//Xác nhận mật khẩu không được để trống
//Xác nhận mật khẩu phải trùng với mật khẩu mới
//B7:
//Viết API backend xử lý trường hợp đặt lại mật khẩu
//POST /api/auth/reset-password (AuthApi)
//Request body {
//token : String,
//password : String,
//confirmPassword : String
//}
//Các bước thực hiện:
//Validation token phải tồn tại trong hệ thống và có type = “FORGOT_PASSWORD” (sử dụng method findByTokenAndType hôm trước đã viết)
//Validation mật khẩu không được để trống
//Validation xác nhận mật khẩu không được để trống
//Validation xác nhận mật khẩu phải trùng với mật khẩu mới
//Cập nhật mật khẩu mới cho user (mật khẩu cần được mã hóa trước khi lưu vào database)
//Set confirmedAt = current time cho token -> Lưu vào trong DB
//B8:
//Ở frontend, khi người dùng click vào nút “Đặt lại mật khẩu” thì gửi request lên server
//Nếu thành công
//-> Hiển thị thông báo “Đặt lại mật khẩu thành công”
//-> Chuyển hướng người dùng về trang đăng nhập
//Nếu thất bại -> Hiển thị thông báo lỗi tương ứng

package vn.scrip.movie_lab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MovieLabApplication {
	public static void main(String[] args) {
		SpringApplication.run(MovieLabApplication.class, args);
	}
}