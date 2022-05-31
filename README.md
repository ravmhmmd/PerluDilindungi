# Tugas Besar PBD 1 - IF3210-2022-Android-67

## Kelompok 67

- 13519190 - Gregorius Dimas Baskara
- 13519201 - Muhammad Rayhan Ravianda
- 13519203 - Ramadhana Bhanuharya Wishnumurti

## Deskripsi Aplikasi

Aplikasi *Perlu Dilindungi* adalah aplikasi yang terinspirasi dari aplikasi *Peduli Lindungi* yang diterbitkan oleh Kementerian Kesehatan Republik Indonesia dalam menghadapi COVID-19. Aplikasi memiliki beberapa fitur, yaitu menampilkan berita COVID-19, menampilkan daftar fasilitas kesehatan untuk vaksinasi, menampilkan detail informasi fasilitas kesehatan, menampilkan daftar bookmark faskes, dan melakukan "Check-In". Aplikasi dibuat menggunakan android studio dengan bahasa pemrograman Kotlin.

## Cara Kerja Aplikasi

#### Menampilkan Berita COVID-19

1. Ketuk pada tab `News` pada navigation bar di bawah.
2. Dalam beberapa saat aplikasi akan menampilkan daftar berita terkini COVID-19.
3. Kartu berita dapat diketuk dan pengguna akan diarahkan pada halaman detail berita yang memuat halaman web berita tersebut pada aplikasi.

#### Menampilkan Daftar Fasilitas Kesehatan untuk Vaksinasi

1. Ketuk pada tab `Lokasi Vaksin` pada navigation bar di bawah.
2. Pilih Kota dan Provinsi pengguna
3. Ketuk tombol `Search`
4. Aplikasi akan menampilkan daftar 5 fasilitas kesehatan untuk vaksinasi yang terurut berdasarkan jarak dari pengguna

#### Menampilkan Detail Informasi Fasilitas Kesehatan

1. Pada saat pengguna berada pada tab `Lokasi Vaksin` dan aplikasi sudah menunjukkan daftar fasilitas kesehatan, maupun saat pengguna berada pada tab `Bookmark`, pengguna dapat mengetuk pada salah kartu fasilitas dan akan diarahkan pada halaman detail fasilitas
2. Pengguna dapat melihat detail fasilitas dan mengetuk `Google Maps` untuk diarahkan pada peta yang menunjukkan lokasi fasilitas
3. Pengguna dapat mengetuk `+ Bookmark` untuk menambahkan fasilitas ke dalam daftar bookmark dan `- Unbookmark` untuk menghapus dari daftar

#### Menampilkan Daftar Bookmark Faskes

1. Ketuk pada tab `Bookmark` pada navigation bar di bawah.
2. Pengguna akan disajikan daftar fasilitas yang sudah ia bookmark

#### Melakukan "Check-In"
1. Ketuk pada `Floating Action Button` yang ber-icon QR Code, pengguna akan diarahkan ke halaman checkin
2. Aplikasi akan memeriksa suhu pengguna
3. Ketuk `Scan` dan aplikasi akan meminta izin untuk mengaktifkan kamera
4. Scan Barcode yang tersedia
5. Aplikasi akan mengarahkan kembali ke halaman checkin dan memberikan feedback apakah checkin berhasil atau tidak

## Library dan Justifikasi

1. Room untuk penyimpanan daftar faskes yang sudah dibookmark pada database SQLite, karena termasuk ke dalam batasan aplikasi pada spek.
2. Androidx Navigation untuk navigasi karena mudah untuk digunakan dan dapat dibaca dengan mudah melalui Navigation Graph.
3. SafeArgs untuk penyampaian argumen pada action navigasi karena disertai dengan fitur security.
4. Glide untuk memudahkan memasukkan gambar pada ImageView dari URL.
5. lifecycle-viewmodel dan lifecycle-livedata untuk dapat menerapkan pemisahan yang baik antara bagian UI dan data, serta menerapkan observer pattern pada pemanggilan dari API maupun database.
6. Retrofit untuk pemanggilan API menggunakan REST karena developer friendly, mudah dibaca dan digunakan, serta memiliki komunitas pengguna yang besar
7. Android Material untuk membantu membuat UI yang menarik dengan lebih mudah.
8. Zxing untuk membantu pengaksesan foto barcode
9. android.apps.maps untuk pengaksesan google maps

## Screenshot Aplikasi
![Laman News](./screenshot/news.jpg?raw=true)
![Laman News Detail](./screenshot/news-detail.jpg?raw=true)
![Laman Faskes](./screenshot/faskes.jpg?raw=true)
![Laman Detail Faskes](./screenshot/faskes-detail.jpg?raw=true)
![Laman Lokasi Faskes di GMaps](./screenshot/faskes-detail-gmaps.jpg?raw=true)
![Laman Bookmark](./screenshot/bookmark.jpg?raw=true)
![Laman CheckIn Berhasil](./screenshot/checkin-complete.jpg?raw=true)
![Laman CheckIn Gagal](./screenshot/checkin-failed.jpg?raw=true)


## Pembagian Tugas

| Item                                | NIM      |
| ----------------------------------- | -------- |
| Setup Navigation                    | 13519190 |
| News Detail                         | 13519190 |
| Facility Detail                     | 13519190 |
| Bookmark List                       | 13519190 |
| Bookmark + Unbookmark Mechanism     | 13519190 |
| Setup Room + DAO                    | 13519190 |
| Revamp All UI (layout)              | 13519190 |
| Setup Scan Barcode                  | 13519201 |
| Setup Check-in Retrofit (for API)   | 13519201 |
| Implement Check-in feature          | 13519201 |
| Implement Recycle List for News     | 13519203 |
| Implement Recycle List for Search   | 13519203 |
| Implement Responsive View Search    | 13519203 |
| Implement Search Mechanism          | 13519203 |
| Setup News Retrofit (for API)       | 13519203 |
| Setup Search Retrofit (for API)     | 13519203 |
