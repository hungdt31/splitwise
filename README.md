# Splitwise

Ứng dụng chia sẻ chi tiêu nhóm — ghi nhận chi phí chung, quyết toán công nợ và giảm thiểu số lần chuyển tiền giữa các thành viên.

## Tổng quan app

App phục vụ **nhóm người** (bạn bè, phòng trọ, du lịch, v.v.) muốn theo dõi chi tiêu chung một cách minh bạch, tránh nhầm lẫn “ai đã trả”, “ai còn nợ” và giúp **thanh toán gọn** nhất có thể.

## Mục tiêu

- **Ghi lại chi tiêu chung** — mỗi khoản chi, ai trả, chia cho ai và theo tỷ lệ nào (nếu có).
- **Tự động tính toán ai nợ ai** — cân đối công nợ net giữa các thành viên trong nhóm, không cần tự cộng trừ tay.
- **Tối ưu số giao dịch cần thanh toán** — gom nợ thành ít lần chuyển khoản nhất để kết sổ nhóm nhanh và rõ ràng.

## Cấu trúc dự án

| Thư mục | Mô tả |
|--------|--------|
| `server/` | Backend **Spring Boot** (REST API, PostgreSQL, bảo mật Spring Security). |
| `mobile/` | Ứng dụng **React Native (Expo)** — giao diện người dùng. |

## Chạy nhanh (tham khảo)

**Server** — trong `server/`:

```bash
./gradlew bootRun
```

**Mobile** — trong `mobile/`:

```bash
npm install
npm start
```

Cấu hình database và cổng server xem `server/src/main/resources/application.properties`.

---

*Dự án đang trong quá trình phát triển.*
