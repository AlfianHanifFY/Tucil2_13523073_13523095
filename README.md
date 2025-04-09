<h1 align="center">Tugas Kecil 2 IF2211 Strategi Algoritma
Kompresi Gambar dengan Metode Quadtree
</h1>

<p align="center">
  <img src="doc/river.gif" alt="Demo" style="max-width: 300px; max-height: 400px;" />
</p>

## About

### Kompresi Gambar dengan Metode Quadtree

Salah satu metode kompresi gambar yang dapat digunakan adalah Quadtree. Metode ini membagi gambar menjadi bagian-bagian kecil berdasarkan keseragaman warna. Jika suatu bagian tidak seragam, maka akan dibagi lagi menjadi empat bagian. Setiap bagian menyimpan informasi posisi, ukuran, dan rata-rata warna. Dengan cara ini, gambar dapat dikompresi secara efisien tanpa menghilangkan detail penting.

## Program Structure

```bash
.
├── README.md
├── Tucil2.jar
├── bin
├── doc
│   └── river.gif
├── manifest.txt
├── src
│   ├── external_lib                    # Tempat menyimpan library external
│   │   └── howTo.txt
│   ├── lib
│   │   ├── Compressor.java
│   │   ├── IO.java
│   │   ├── Image.java
│   │   ├── Pixel.java
│   │   └── Quadtree.java
│   └── main.java
└── test
```

## Additional Structure Description

- algoritma Divide and Conquer untuk penyelesaian kompresi terdapat pada `/src/lib/Compressor.java` pada method `compress()`
- file testing dapat disimpan pada `/test`
- library external dapat disimpan pada `/src/external_lib`

## Pre-Requisite

Untuk menjalankan program ini, pastikan sudah menginstal:

- Java Development Kit (JDK) – versi 8 atau lebih baru
- Library eksternal untuk GIF: (Opsional jika ingin compile ulang )
  - download code pada pranala berikut [animated-gif-lib-for-java](https://github.com/rtyley/animated-gif-lib-for-java)
  - simpan code pada `src/external_lib `

## How To Run

### Clone Repositori

clone repositori ini dengan command

```sh
git clone https://github.com/AlfianHanifFY/Tucil2_13523073_13523095.git
```

ubah directory

```sh
cd Tucil2_13523073_13523095
```

### Using executable .jar (Recommended)

Jika ingin langsung menjalankan program tanpa mengompilasi ulang:

```sh
java -jar Tucil2_13523073_13523095.jar
```

### Re-compile

- pastikan untuk mengunduh dan menginstal library berikut:
  <br>
  [animated-gif-lib-for-java](https://github.com/rtyley/animated-gif-lib-for-java)
  <br>
  Setelah diunduh, simpan folder hasil ekstraksi ke dalam direktori `src/external_lib/` sehingga struktur folder menjadi seperti ini:

  ```bash
  ├── src
  │   ├── Main.java
  │   ├── external_lib
  │   │   ├── animated-gif-lib-for-java-master
  │   │   │   ├── pom.xml
  │   │   │   ├── src
  │   │   │   │   ├── main
  │   │   │   │   │   └── java
  │   │   │   │   │       └── com/madgag/gif/fmsware/*.java
  ```

- Compile Source Code

  ```bash
  javac -d bin -cp src $(find src -path "_test*" -prune -o -name "*.java" -print)
  ```

- Run Program

  ```bash
  java -cp bin main
  ```

## Kontributor

|   NIM    |             Nama             |
| :------: | :--------------------------: |
| 13523073 | Alfian Hanif Fitria Yustanto |
| 13523095 |         Rafif Faras          |
