package com.example.customer_management.domain;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;

@MappedSuperclass //Bu annotation, bu sınıfın bir veritabanı tablosu olmayacağını ama
// alanlarının (field) kendisinden miras alan entity’lerin tablolarına eklenmesini sağlar.
public abstract class BaseEntity {

    @Column(name = "olusturulma_tarihi", updatable = false)
    //Bu alan bir kere (insert sırasında) setlenir, güncellenemez. Böylece ilk oluşturulma tarihi hep korunur.
    private LocalDateTime olusturulmaTarihi;
    //Tarih + saat bilgisini saklar.

    @Column(name = "guncelleme_tarihi")
    //Bu alan her güncellemede yenilenir.
    private LocalDateTime guncellemeTarihi;

    @PrePersist//Entity ilk kez veritabanına kaydedilmeden hemen önce bu metod otomatik çalışır.
    protected void onCreate() {
        this.olusturulmaTarihi = LocalDateTime.now();
        this.guncellemeTarihi = LocalDateTime.now();
        //İlk kayıt yapılırken hem olustur hem guncelleme aynı zamana setlenir.
    }

    @PreUpdate//Entity üzerinde bir değişiklik yapıldığında (update), kaydedilmeden hemen önce çalışır.
    protected void onUpdate() {
        this.guncellemeTarihi = LocalDateTime.now();
        //Sadece guncellemeTarihi alanını günceller.
    }


    //Setter yazmadık çünkü tarih alanlarını elle değiştirmeye gerek yok, otomatik yönetiliyor.

    public LocalDateTime getOlusturulmaTarihi() {
        return olusturulmaTarihi;
    }

    public LocalDateTime getGuncellemeTarihi() {
        return guncellemeTarihi;
    }
}
