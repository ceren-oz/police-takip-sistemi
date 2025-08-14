package com.example.customer_management.repository;

import com.example.customer_management.domain.MusteriTelefon;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
//JpaRepository<MusteriTelefon, Long> :
//MusteriTelefon entity’si üzerinde CRUD işlemleri yapmamızı sağlar.
public interface MusteriTelefonRepository extends JpaRepository<MusteriTelefon,Long>{
    List<MusteriTelefon> findByMusteri_Id(String musteriId);
    //findByMusteriId ile bir müşteriye ait tüm telefonları listeleyebilirsin.
//Spring Data JPA’nın metod adı ile sorgu oluşturma özelliği.
}


//NOT !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
//JpaRepository zaten hazır CRUD metodları sunar:
//
//findById(id) → tek bir kaydı ID ile getirir.
//
//save(entity) → kaydı ekler veya günceller.
//
//deleteById(id) → ID’ye göre siler.
//
//Yani sen findById yazmasan bile JPA zaten sağlar.
//
//Bu metodları “kim yazdı” sorusu: Spring Data JPA ekibi yazdı, biz sadece interfaceimizi extends JpaRepository ile bağladık.
