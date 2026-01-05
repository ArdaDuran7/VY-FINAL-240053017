# Öğrenci ve Ders Bilgileri
**Adı Soyadı:** Arda Duran  
**Öğrenci No:** 240053017  
**Bölüm:** Bilgisayar Programcılığı (2. Sınıf)  
**Ders:** BPR 215 – Veri Yapıları (Final Projesi)  
**Okul:** Alanya Üniversitesi

---

# 🚗 Araç Bakım ve Masraf Takip Sistemi

Bu proje, araç sahiplerinin bakım geçmişlerini, parça değişimlerini ve masraflarını güvenli bir şekilde takip etmeleri için geliştirilmiş **Java Swing** tabanlı bir masaüstü uygulamasıdır. Manuel defter tutma veya karmaşık Excel tabloları yerine; nesne yönelimli ve dinamik veri yapılarına dayalı pratik bir çözüm sunar.

## 🎯 Temel Özellikler

* **Kayıt Yönetimi:** Bakım verilerini ekleme, silme ve listeleme.
* **Kalıcı Depolama (File Persistence):** Veriler program kapansa bile yerel bir `.txt` dosyasında (CSV formatında) güvenle saklanır.
* **Canlı Filtreleme:** Arama kutusuna yazılan kelimeye göre listeyi anlık olarak süzme (Regex & TableRowSorter).
* **Otomatik Tarih:** İşlem yapıldığı anda sistem tarihini otomatik kaydetme.
* **Maliyet Hesabı:** Filtrelenen veya listelenen kayıtların toplam tutarını hesaplama.

## 🛠️ Kullanılan Teknolojiler

* **Dil:** Java
* **Arayüz (GUI):** Java Swing (Nimbus Teması)
* **Veri Yapıları:** ArrayList, Vector (Dinamik Bellek Yönetimi)
* **Dosya Sistemi:** Java I/O (BufferedReader/Writer)