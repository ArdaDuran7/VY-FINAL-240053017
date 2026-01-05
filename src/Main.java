import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Dinamik liste yapısı (ArrayList) kullanılarak işlemler hafızada tutulur.
        ArrayList<Islem> servisListesi = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        boolean devam = true;

        System.out.println("### Araç Servis Takip Sistemine Hoş Geldiniz ###");

        while (devam) {
            System.out.println("\n1. Yeni İşlem Ekle");
            System.out.println("2. Yapılan İşlemleri Listele");
            System.out.println("3. Toplam Maliyeti Hesapla");
            System.out.println("4. Çıkış");
            System.out.print("Seçiminiz: ");

            int secim = scanner.nextInt();
            scanner.nextLine(); // Buffer temizleme

            switch (secim) {
                case 1:
                    System.out.print("Yapılan işlem (Örn: Yağ Değişimi): ");
                    String islemAdi = scanner.nextLine();
                    System.out.print("Ücret (TL): ");
                    double ucret = scanner.nextDouble();

                    // Yeni nesne oluşturulup listeye ekleniyor
                    servisListesi.add(new Islem(islemAdi, ucret));
                    System.out.println("Kayıt başarıyla eklendi.");
                    break;

                case 2:
                    System.out.println("\n--- Servis Geçmişi ---");
                    if (servisListesi.isEmpty()) {
                        System.out.println("Henüz kayıt bulunmamaktadır.");
                    } else {
                        for (Islem kayit : servisListesi) {
                            System.out.println(kayit.toString());
                        }
                    }
                    break;

                case 3:
                    double toplam = 0;
                    for (Islem kayit : servisListesi) {
                        toplam += kayit.getUcret();
                    }
                    System.out.println("Toplam Servis Maliyeti: " + toplam + " TL");
                    break;

                case 4:
                    System.out.println("Sistemden çıkılıyor...");
                    devam = false;
                    break;

                default:
                    System.out.println("Geçersiz seçim, tekrar deneyiniz.");
            }
        }
    }
}