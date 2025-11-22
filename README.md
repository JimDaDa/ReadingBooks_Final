### Đồ án Cuối kỳ môn Phát triển Ứng dụng Di động: Ứng dụng Đọc sách Online STORIA

Đây là dự án xây dựng ứng dụng đọc sách trên nền tảng Android (ngôn ngữ Java), hướng tới việc cải thiện trải nghiệm đọc sách cho người dùng, giúp họ tiếp cận sách mới dễ dàng, tiện lợi hơn so với sách giấy hoặc các nền tảng web truyền thống chưa được tối ưu.

#### 1. Các Chức năng và Nội dung Chính của Ứng dụng

**A. Phân hệ Người dùng (Reader):**
* **Quản lý Tài khoản & Bảo mật (UC05, UC06):**
    * Các chức năng cơ bản: Đăng ký, Đăng nhập, Đăng xuất, Quên mật khẩu.
    * **Cá nhân hóa:** Cho phép cập nhật thông tin chi tiết (tên hiển thị, số điện thoại, hình đại diện) để tối ưu hóa hồ sơ người dùng.
    * **Quyền riêng tư:** Cung cấp chức năng xóa tài khoản vĩnh viễn khỏi hệ thống nếu người dùng không còn nhu cầu sử dụng.
* **Trang chủ & Điều hướng Thông minh:**
    * Tìm kiếm truyện theo từ khóa.
    * **Sắp xếp & Phân loại:** Hệ thống tự động hiển thị danh sách truyện theo các tiêu chí: *Truyện mới đăng, Truyện có nhiều lượt xem nhất, và Truyện có lượt đánh giá cao* để người dùng dễ dàng tiếp cận nội dung hot.
* **Tương tác & Trải nghiệm Đọc (UC11, UC21):**
    * **Đọc & Đánh dấu trang (Bookmark):** Hỗ trợ đọc file PDF mượt mà, cho phép đánh dấu vị trí trang đang đọc để dễ dàng quay lại sau này.
    * **Thư viện cá nhân:** Lưu/Bỏ lưu truyện yêu thích.
    * **Chế độ Offline:** Tính năng tải truyện về máy giúp người dùng đọc sách mọi lúc mọi nơi mà không cần kết nối Internet.
    * **Phản hồi:** Đánh giá chất lượng truyện và Báo cáo các truyện có nội dung vi phạm.

**B. Phân hệ Tác giả/Quản lý Truyện:**
* Viết và biên tập thông tin truyện.
* Thêm nội dung truyện dưới dạng upload file PDF.
* Quản lý trạng thái hiển thị: Đăng truyện (Publish) công khai hoặc Hủy đăng (Unpublish) để chỉnh sửa.
* Theo dõi danh sách truyện đã đăng tải.

**C. Phân hệ Quản trị viên (Admin):**
* Thống kê hệ thống và giám sát nội dung.
* Xử lý vi phạm: Xem xét các truyện bị báo cáo nhiều lượt và thực hiện xóa khỏi cơ sở dữ liệu để đảm bảo môi trường đọc lành mạnh.

**D. Thiết kế Hệ thống:**
* Xây dựng bộ tài liệu phân tích thiết kế chi tiết bao gồm: Sơ đồ Use Case tổng quát, Đặc tả Use Case, Sơ đồ Hoạt động (Activity Diagram) và Sơ đồ Tuần tự (Sequence Diagram) cho từng luồng chức năng.

#### 2. Công nghệ và Thư viện Triển khai

Ứng dụng được xây dựng dựa trên các công nghệ hiện đại để đảm bảo hiệu suất và trải nghiệm mượt mà:

**A. Cơ sở Dữ liệu & Backend (Nền tảng Firebase):**
Hệ thống sử dụng Firebase làm backend tập trung để đồng bộ hóa dữ liệu và tăng cường bảo mật:
* **Firebase Authentication:** Quản lý vòng đời xác thực người dùng (Đăng ký/Đăng nhập) an toàn.
* **Realtime Database:** Lưu trữ và đồng bộ dữ liệu văn bản (thông tin truyện, comment, đánh giá) theo thời gian thực.
* **Firebase Storage:** Lưu trữ các file phương tiện dung lượng lớn như ảnh bìa và file nội dung PDF của truyện.

**B. Các Thư viện Hỗ trợ Giao diện & Tiện ích:**
* **PDFViewer:** Thành phần cốt lõi giúp hiển thị nội dung sách dưới dạng PDF trực quan, hỗ trợ các thao tác vuốt, chạm và zoom mượt mà.
* **Glide:** Tối ưu hóa việc tải và hiển thị hình ảnh (ảnh bìa truyện, avatar) giúp ứng dụng chạy nhẹ nhàng hơn.
* **Circle ImageView:** Bo tròn ảnh đại diện tự động, tăng tính thẩm mỹ cho giao diện profile.
* **AnimatedBottomBar:** Thanh điều hướng phía dưới với hiệu ứng chuyển động, tạo cảm giác hiện đại khi chuyển tab.
* **Spinkit:** Bộ công cụ tạo hiệu ứng loading (thanh tiến trình) đẹp mắt trong khi chờ tải dữ liệu.
