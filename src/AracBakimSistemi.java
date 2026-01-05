import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.time.LocalDate;

public class AracBakimSistemi extends JFrame {

    // Arayüz elemanları
    private JTextField txtPlaka;
    private JComboBox<String> cmbIslem;
    private JTextField txtUcret;
    private JTextField txtArama;
    private JTable tblBakimListesi;
    private DefaultTableModel tabloModeli;
    private TableRowSorter<DefaultTableModel> siralayici;
    private JLabel lblToplamTutar;
    private double genelToplam = 0.0;

    private final String DOSYA_ADI = "bakim_verileri_v3.txt";

    public AracBakimSistemi() {
        setTitle("Oto Servis Yönetim Sistemi v3.0");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(null);

        // --- 1. ÜST PANEL (Veri Girişi) ---
        JPanel pnlUst = new JPanel(new BorderLayout());

        JPanel pnlGiris = new JPanel(new GridLayout(3, 2, 5, 5));
        pnlGiris.setBorder(BorderFactory.createTitledBorder("Yeni Servis Kaydı"));
        pnlGiris.setBackground(new Color(236, 240, 241));

        pnlGiris.add(new JLabel("Araç Plaka:"));
        txtPlaka = new JTextField("07 ANT 001");
        pnlGiris.add(txtPlaka);

        pnlGiris.add(new JLabel("Yapılan İşlem:"));
        String[] islemler = {"Periyodik Bakım", "Yağ Değişimi", "Lastik Değişimi", "Fren Balatası", "Motor Arızası", "Muayene", "Diğer"};
        cmbIslem = new JComboBox<>(islemler);
        pnlGiris.add(cmbIslem);

        pnlGiris.add(new JLabel("İşlem Ücreti (TL):"));
        txtUcret = new JTextField();
        pnlGiris.add(txtUcret);

        pnlUst.add(pnlGiris, BorderLayout.CENTER);
        add(pnlUst, BorderLayout.NORTH);

        // --- 2. ORTA PANEL (Tablo ve Arama) ---
        JPanel pnlOrta = new JPanel(new BorderLayout(5, 5));
        pnlOrta.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        // Butonlar
        JPanel pnlButonlar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnEkle = new JButton("Kaydet");
        JButton btnSil = new JButton("Seçili Kaydı Sil");

        btnEkle.setBackground(new Color(39, 174, 96));
        btnEkle.setForeground(Color.WHITE);
        btnSil.setBackground(new Color(192, 57, 43));
        btnSil.setForeground(Color.WHITE);

        pnlButonlar.add(btnEkle);
        pnlButonlar.add(btnSil);

        // Arama Kutusu
        JPanel pnlArama = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlArama.add(new JLabel("Plaka Ara:"));
        txtArama = new JTextField(15);
        pnlArama.add(txtArama);

        JPanel pnlAraclar = new JPanel(new BorderLayout());
        pnlAraclar.add(pnlButonlar, BorderLayout.WEST);
        pnlAraclar.add(pnlArama, BorderLayout.EAST);
        pnlOrta.add(pnlAraclar, BorderLayout.NORTH);

        // Tablo Ayarları
        String[] sutunlar = {"Tarih", "Plaka", "İşlem Türü", "Tutar (TL)"};
        tabloModeli = new DefaultTableModel(sutunlar, 0);
        tblBakimListesi = new JTable(tabloModeli);

        siralayici = new TableRowSorter<>(tabloModeli);
        tblBakimListesi.setRowSorter(siralayici);

        pnlOrta.add(new JScrollPane(tblBakimListesi), BorderLayout.CENTER);
        add(pnlOrta, BorderLayout.CENTER);

        // --- 3. ALT PANEL ---
        JPanel pnlAlt = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        lblToplamTutar = new JLabel("Toplam Maliyet: 0.0 TL");
        lblToplamTutar.setFont(new Font("Arial", Font.BOLD, 16));
        pnlAlt.add(lblToplamTutar);
        add(pnlAlt, BorderLayout.SOUTH);

        // Verileri Yükle
        dosyadanYukle();

        // --- BUTON İŞLEVLERİ (Eski Sürüm Uyumlu) ---

        // EKLE BUTONU
        btnEkle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                kayitEkle();
            }
        });

        // SİL BUTONU
        btnSil.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                kayitSil();
            }
        });

        // ARAMA FONKSİYONU
        txtArama.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String arananMetin = txtArama.getText();
                if (arananMetin.trim().length() == 0) {
                    siralayici.setRowFilter(null);
                } else {
                    siralayici.setRowFilter(RowFilter.regexFilter("(?i)" + arananMetin, 1));
                }
            }
        });
    }

    private void kayitEkle() {
        String plaka = txtPlaka.getText().toUpperCase();
        String islem = (String) cmbIslem.getSelectedItem();
        String ucretStr = txtUcret.getText();
        String tarih = LocalDate.now().toString();

        if (plaka.isEmpty() || ucretStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Lütfen tüm alanları doldurunuz.");
            return;
        }

        try {
            double ucret = Double.parseDouble(ucretStr);
            Object[] satir = {tarih, plaka, islem, ucret};
            tabloModeli.addRow(satir);

            genelToplam += ucret;
            guncelleToplam();
            dosyayaKaydet();

            txtUcret.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ücret sayı olmalıdır!", "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void kayitSil() {
        int secilenRow = tblBakimListesi.getSelectedRow();
        if (secilenRow != -1) {
            int modelRow = tblBakimListesi.convertRowIndexToModel(secilenRow);

            double silinenTutar = Double.parseDouble(tabloModeli.getValueAt(modelRow, 3).toString());
            genelToplam -= silinenTutar;
            tabloModeli.removeRow(modelRow);

            guncelleToplam();
            dosyayaKaydet();
        } else {
            JOptionPane.showMessageDialog(this, "Silinecek satırı seçiniz.");
        }
    }

    private void guncelleToplam() {
        lblToplamTutar.setText("Listelenen Toplam Maliyet: " + String.format("%.2f", genelToplam) + " TL");
    }

    private void dosyayaKaydet() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(DOSYA_ADI));
            for (int i = 0; i < tabloModeli.getRowCount(); i++) {
                String satir = tabloModeli.getValueAt(i, 0) + "," +
                        tabloModeli.getValueAt(i, 1) + "," +
                        tabloModeli.getValueAt(i, 2) + "," +
                        tabloModeli.getValueAt(i, 3);
                writer.write(satir);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void dosyadanYukle() {
        File dosya = new File(DOSYA_ADI);
        if (!dosya.exists()) return;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(dosya));
            String satir;
            while ((satir = reader.readLine()) != null) {
                String[] veriler = satir.split(",");
                if (veriler.length == 4) {
                    double tutar = Double.parseDouble(veriler[3]);
                    tabloModeli.addRow(new Object[]{veriler[0], veriler[1], veriler[2], tutar});
                    genelToplam += tutar;
                }
            }
            reader.close();
            guncelleToplam();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {}

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AracBakimSistemi().setVisible(true);
            }
        });
    }
}