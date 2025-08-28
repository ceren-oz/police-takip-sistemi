package com.example.customer_management.service;

import com.example.customer_management.domain.*;
import com.example.customer_management.mapper.PoliceDTO;
import com.example.customer_management.mapper.PoliceMapper;
import com.example.customer_management.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;

@Service
@Transactional
public class PoliceService {

    private final PoliceRepository policeRepository;
    private final MusteriRepository musteriRepository;
    private final AracBilgileriRepository aracRepository;
    private final EkHizmetRepository ekHizmetRepository;
    private final TeminatRepository teminatRepository;
    private final PoliceMapper policeMapper;
    private final IndirimService indirimService;

    @Autowired
    public PoliceService(PoliceRepository policeRepository,
                         MusteriRepository musteriRepository,
                         AracBilgileriRepository aracRepository,
                         EkHizmetRepository ekHizmetRepository,
                         TeminatRepository teminatRepository,
                         PoliceMapper policeMapper,
                         IndirimService indirimService) {
        this.policeRepository = policeRepository;
        this.musteriRepository = musteriRepository;
        this.aracRepository = aracRepository;
        this.ekHizmetRepository = ekHizmetRepository;
        this.teminatRepository = teminatRepository;
        this.policeMapper = policeMapper;
        this.indirimService = indirimService;
    }
    public PoliceDTO createPolice(PoliceDTO dto) {
        Police police = new Police();

        int currentYear = LocalDate.now().getYear();

        // 1️⃣ Musteri ve Arac
        Musteri musteri = musteriRepository.findById(dto.getMusteriId())
                .orElseThrow(() -> new RuntimeException("Müşteri bulunamadı"));
        police.setMusteri(musteri);

        AracBilgileri arac = aracRepository.findById(dto.getAracId())
                .orElseThrow(() -> new RuntimeException("Araç bulunamadı"));
        police.setArac(arac);

        int aracYasi = currentYear - arac.getModelYili();
        int surucuYasi = currentYear - musteri.getDogumTarihi().getYear();

        // 2️⃣ Teminatlar
        if (dto.getTeminatlarIds() != null) {
            var teminatSet = new HashSet<Teminat>();
            teminatRepository.findAllById(dto.getTeminatlarIds()).forEach(teminatSet::add);
            police.setTeminatlar(teminatSet);
        }

        // 3️⃣ Ek Hizmetler
        BigDecimal hizmetToplam = BigDecimal.ZERO;
        if (dto.getEkHizmetlerIds() != null) {
            var ekHizmetSet = new HashSet<EkHizmet>();

            // Fetch from repository
            ekHizmetRepository.findAllById(dto.getEkHizmetlerIds()).forEach(e -> {
                // No need to set bedel manually, it's already stored in the entity
                if (e.getHizmetBedeli() != null) {
                    hizmetToplam.add(e.getHizmetBedeli());
                }
                ekHizmetSet.add(e);
            });

            police.setEkHizmetler(ekHizmetSet);
        }

        // 4️⃣ Temel Prim
        BigDecimal temelPrim = arac.getAracDegeri().multiply(BigDecimal.valueOf(0.001));

        // 5️⃣ Katsayılar (BigDecimal ile)
        BigDecimal Karac = BigDecimal.valueOf(
                aracYasi <= 3 ? 1.0 :
                        aracYasi <= 10 ? 1.2 : 1.5
        );

        BigDecimal Ksurucu = BigDecimal.valueOf(
                surucuYasi < 25 ? 1.4 :
                        surucuYasi <= 29 ? 1.2 :
                                surucuYasi <= 50 ? 1.0 :
                                        surucuYasi <= 65 ? 1.2 : 1.4
        );

        BigDecimal Kkullanim = switch (arac.getKullanimSekli()) {
            case BIREYSEL -> BigDecimal.valueOf(1.0);
            case TICARI -> BigDecimal.valueOf(1.3);
            case TAKSI -> BigDecimal.valueOf(1.6);
        };

        BigDecimal Khasar = BigDecimal.valueOf(
                arac.getHasarSayisi() == 0 ? 1.0 :
                        arac.getHasarSayisi() <= 2 ? 1.3 : 1.6
        );

        // 6️⃣ Risk Skoru ve Brüt Prim
        BigDecimal riskSkoru = Karac.multiply(Ksurucu).multiply(Kkullanim).multiply(Khasar);
        BigDecimal brutPrim = temelPrim.multiply(riskSkoru);

        // 7️⃣ İndirim
        BigDecimal indirimOrani = indirimService.getIndirimOrani();
        BigDecimal toplamTutar = brutPrim.add(hizmetToplam);
        BigDecimal indirimTutar = toplamTutar.multiply(indirimOrani);

        // 8️⃣ Muafiyet
        BigDecimal Kmuafiyet = switch (dto.getMuafiyetOrani().intValue()) {
            case 0 -> BigDecimal.valueOf(1.0);
            case 5 -> BigDecimal.valueOf(0.97);
            case 10 -> BigDecimal.valueOf(0.94);
            default -> BigDecimal.valueOf(1.0);
        };

        // 9️⃣ Net Prim
        BigDecimal netPrim = brutPrim.add(hizmetToplam)
                .multiply(Kmuafiyet)
                .subtract(indirimTutar);

        police.setPrim(netPrim);
        police.setRiskSkoru(riskSkoru);
        police.setMuafiyetOrani(dto.getMuafiyetOrani());

        // 10️⃣ Tarihler
        police.setTeklifTarihi(dto.getTeklifTarihi());
        police.setBaslangicTarihi(dto.getBaslangicTarihi());
        police.setBitisTarihi(dto.getBitisTarihi());


        Police saved = policeRepository.save(police);
        return policeMapper.toDto(saved);
    }
    public PoliceDTO getPoliceById(Long id) {
        Police police = policeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Poliçe bulunamadı"));
        return policeMapper.toDto(police);
    }


    public PoliceDTO updatePolice(Long policeId, PoliceDTO dto) {
        // 1️⃣ Fetch existing police
        Police police = policeRepository.findById(policeId)
                .orElseThrow(() -> new RuntimeException("Poliçe bulunamadı"));

        int currentYear = LocalDate.now().getYear();

        // 2️⃣ Update Musteri
        if (dto.getMusteriId() != null) {
            Musteri musteri = musteriRepository.findById(dto.getMusteriId())
                    .orElseThrow(() -> new RuntimeException("Müşteri bulunamadı"));
            police.setMusteri(musteri);
        }

        // 3️⃣ Update Arac
        if (dto.getAracId() != null) {
            AracBilgileri arac = aracRepository.findById(dto.getAracId())
                    .orElseThrow(() -> new RuntimeException("Araç bulunamadı"));
            police.setArac(arac);
        }

        AracBilgileri arac = police.getArac();
        Musteri musteri = police.getMusteri();
        int aracYasi = currentYear - arac.getModelYili();
        int surucuYasi = currentYear - musteri.getDogumTarihi().getYear();

        // 4️⃣ Update Teminatlar
        if (dto.getTeminatlarIds() != null) {
            var teminatSet = new HashSet<Teminat>();
            teminatRepository.findAllById(dto.getTeminatlarIds()).forEach(teminatSet::add);
            police.setTeminatlar(teminatSet);
        }

        // 5️⃣ Update EkHizmetler
        BigDecimal hizmetToplam = BigDecimal.ZERO;
        if (dto.getEkHizmetlerIds() != null) {
            var ekHizmetSet = new HashSet<EkHizmet>();
            ekHizmetRepository.findAllById(dto.getEkHizmetlerIds()).forEach(e -> {
                ekHizmetSet.add(e);
                if (e.getHizmetBedeli() != null) {
                    hizmetToplam.add(e.getHizmetBedeli());
                }
            });
            police.setEkHizmetler(ekHizmetSet);
        }

        // 6️⃣ Prim ve Risk Hesaplama
        BigDecimal temelPrim = arac.getAracDegeri().multiply(BigDecimal.valueOf(0.001));

        BigDecimal Karac = BigDecimal.valueOf(aracYasi <= 3 ? 1.0 :
                aracYasi <= 10 ? 1.2 : 1.5);

        BigDecimal Ksurucu = BigDecimal.valueOf(surucuYasi < 25 ? 1.4 :
                surucuYasi <= 29 ? 1.2 :
                        surucuYasi <= 50 ? 1.0 :
                                surucuYasi <= 65 ? 1.2 : 1.4);

        BigDecimal Kkullanim = switch (arac.getKullanimSekli()) {
            case BIREYSEL -> BigDecimal.valueOf(1.0);
            case TICARI -> BigDecimal.valueOf(1.3);
            case TAKSI -> BigDecimal.valueOf(1.6);
        };

        BigDecimal Khasar = BigDecimal.valueOf(
                arac.getHasarSayisi() == 0 ? 1.0 :
                        arac.getHasarSayisi() <= 2 ? 1.3 : 1.6
        );

        BigDecimal riskSkoru = Karac.multiply(Ksurucu).multiply(Kkullanim).multiply(Khasar);
        BigDecimal brutPrim = temelPrim.multiply(riskSkoru);

        BigDecimal indirimOrani = indirimService.getIndirimOrani();
        BigDecimal toplamTutar = brutPrim.add(hizmetToplam);
        BigDecimal indirimTutar = toplamTutar.multiply(indirimOrani);

        BigDecimal Kmuafiyet = switch (dto.getMuafiyetOrani().intValue()) {
            case 0 -> BigDecimal.valueOf(1.0);
            case 5 -> BigDecimal.valueOf(0.97);
            case 10 -> BigDecimal.valueOf(0.94);
            default -> BigDecimal.valueOf(1.0);
        };

        BigDecimal netPrim = brutPrim.add(hizmetToplam)
                .multiply(Kmuafiyet)
                .subtract(indirimTutar);

        // 7️⃣ Set calculated values
        police.setPrim(netPrim);
        police.setRiskSkoru(riskSkoru);
        police.setMuafiyetOrani(dto.getMuafiyetOrani());

        // 8️⃣ Update dates
        if (dto.getTeklifTarihi() != null) police.setTeklifTarihi(dto.getTeklifTarihi());
        if (dto.getBaslangicTarihi() != null) police.setBaslangicTarihi(dto.getBaslangicTarihi());
        if (dto.getBitisTarihi() != null) police.setBitisTarihi(dto.getBitisTarihi());

        Police saved = policeRepository.save(police);
        return policeMapper.toDto(saved);
    }

    public void deletePolice(Long id) {
        Police police = policeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Poliçe bulunamadı"));
        policeRepository.delete(police);
    }

}
