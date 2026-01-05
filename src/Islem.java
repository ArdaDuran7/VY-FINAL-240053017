public class Islem {
    private String aciklama;
    private double ucret;

    public Islem(String aciklama, double ucret) {
        this.aciklama = aciklama;
        this.ucret = ucret;
    }

    // Getter metotları veriye erişim sağlar (Encapsulation)
    public String getAciklama() {
        return aciklama;
    }

    public double getUcret() {
        return ucret;
    }

    @Override
    public String toString() {
        return "İşlem: " + aciklama + " | Tutar: " + ucret + " TL";
    }
}