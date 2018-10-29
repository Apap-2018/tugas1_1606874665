package com.apap.tugas1.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.repository.PegawaiDb;
import com.apap.tugas1.service.PegawaiService;

@Service
@Transactional
public class PegawaiServiceImp implements PegawaiService {
	@Autowired
	private PegawaiDb pegawaiDb;

	@Override
	public Optional<PegawaiModel> getPegawaiDetailByNip(String nip) {
		return pegawaiDb.findByNip(nip);
	}

	@Override
	public String generateNip(PegawaiModel pegawai) {
		DateFormat df = new SimpleDateFormat("ddMMYY");
		Date tglLahir = pegawai.getTanggal_lahir();
		String formatted = df.format(tglLahir);
		System.out.println("tgl  lahir: " + formatted);

		Long kodeInstansi = pegawai.getInstansi().getId_instansi();
		System.out.println("kode instansi: " + kodeInstansi);

		int idBlkg = 0;
		for (PegawaiModel peg : findAllPegawai()) {
			if (peg.getTanggal_lahir().equals(pegawai.getTanggal_lahir())
					&& peg.getTahun_masuk().equals(pegawai.getTahun_masuk())) {
				idBlkg += 1;
			}
		}

		idBlkg += 1;

		String kodeMasuk = "";
		if (idBlkg < 10) {
			kodeMasuk = "0" + idBlkg;
		} else {
			kodeMasuk = "" + idBlkg;
		}
		System.out.println("kode masuk: " + kodeMasuk);

		return kodeInstansi + formatted + pegawai.getTahun_masuk() + kodeMasuk;
	}

	@Override
	public List<PegawaiModel> findAllPegawai() {
		return pegawaiDb.findAll();
	}

	@Override
	public PegawaiModel addPegawai(PegawaiModel pegawai) {
		return pegawaiDb.save(pegawai);
	}

	
}