
# Tokonyadia

Proyek ini adalah aplikasi e-commerce yang dikembangkan sebagai tugas di tempat pelatihan. Aplikasi ini memungkinkan pengguna untuk melakukan pembelian produk dengan fitur autentikasi pengguna, manajemen token, dan lainnya.

## Fitur Utama

- **Autentikasi Pengguna**: Pengguna dapat mendaftar dan masuk ke akun mereka.
- **Manajemen Produk**: Pengguna dapat melihat, menambahkan, dan mengedit produk.
- **Pembelian dan Transaksi**: Pengguna dapat melakukan pembelian dan melacak status pesanan.
- **Pengelolaan Token**: Implementasi JWT untuk keamanan.
- **Saldo Pengguna**: Setiap pengguna memiliki saldo yang dapat digunakan untuk berbelanja di website Tokonyadia.
- **Keranjang Belanja (Cart)**: Fitur yang memungkinkan pengguna menyimpan produk yang ingin dibeli di kemudian hari.
- **Kategori**: Memungkinkan pengguna untuk memilah produk berdasarkan kategori tertentu.
- **Produk**: Menyediakan informasi detail mengenai produk yang tersedia di toko.
- **Pesanan (Order)**: Pengguna dapat mengelola dan melacak pesanan yang telah dilakukan.
- **Pembayaran**: Menggunakan Midtrans dan saldo pengguna untuk memproses transaksi.
- **Ukuran Produk**: Fitur untuk mengambil dan memperbarui ukuran pada produk tertentu.
- **Alamat Pengiriman (User Shipping)**: Memungkinkan pengguna untuk mengelola alamat pengiriman mereka.

## Teknologi yang Digunakan

- **Java 17**: Bahasa pemrograman utama yang digunakan untuk mengembangkan aplikasi.
- **Spring Boot**: Kerangka kerja untuk membangun aplikasi Java dengan cepat dan mudah.
- **PostgreSQL**: Sistem manajemen basis data yang digunakan untuk menyimpan data aplikasi.
- **Hibernate**: Framework untuk mempermudah interaksi dengan database.
- **Lombok**: Mengurangi boilerplate code dengan anotasi yang menyediakan fungsi otomatis.
- **Mockito**: Framework untuk melakukan testing dan mocking objek dalam pengujian.
- **Ngrok**: Alat yang digunakan untuk membuat tunnel ke localhost, memungkinkan akses aplikasi dari internet.

## Link Ngrok

Silakan klik [di sini](https://2aa6-182-253-247-224.ngrok-free.app) untuk mengakses aplikasi melalui Ngrok.

## ERD (Entity Relationship Diagram)

![ERD](/static/erd.jpg)

## Instalasi

Untuk menjalankan proyek ini, ikuti langkah-langkah berikut:

1. **Clone repositori**:
   ```bash
   git clone https://github.com/username/tokonyadia.git
   cd(tokonyadia)
   ```

2. **Setup PostgreSQL**:
    - Buat database baru dengan nama `tokonyadia`.
    - Jalankan skrip SQL yang diperlukan untuk membuat tabel.

3. **Setup Redis**:
    - Pastikan Redis sudah terinstall dan berjalan di lokal.
    - Anda dapat mengunduh Redis dari [situs resmi Redis](https://redis.io/download) dan mengikuti instruksi pemasangan.
    - Untuk memulai Redis, jalankan perintah berikut:
      ```bash
      redis-server
      ```

4. **Setup Midtrans**:
    - Daftar dan buat akun di [Midtrans](https://midtrans.com).
    - Buat proyek baru di dashboard Midtrans untuk mendapatkan `Client Key` dan `Server Key`.
    - Konfigurasikan `Client Key` dan `Server Key` di file konfigurasi aplikasi (biasanya di `application.properties` atau `application.yml`).
      ```properties
      midtrans.server.key=YOUR_SECRET_KEY
      ```

5. **Jalankan Aplikasi**:
    - Setelah semua pengaturan selesai, jalankan aplikasi menggunakan perintah:
      ```bash
      ./mvnw spring-boot:run
      ```