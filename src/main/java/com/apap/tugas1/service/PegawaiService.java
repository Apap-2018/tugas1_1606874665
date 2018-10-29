package com.apap.tugas1.service;

import java.util.List;
import java.util.Optional;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;

public interface PegawaiService {
	Optional<PegawaiModel> getPegawaiDetailByNip(String nip);
	String generateNip(PegawaiModel pegawai);
	List<PegawaiModel> findAllPegawai();
	PegawaiModel addPegawai(PegawaiModel pegawai);
	Optional<PegawaiModel> getPegawaiDetailById(long id);
	List<PegawaiModel> getPegawaiByInstansiAndJabatan(InstansiModel instansi, JabatanModel jabatan);
	List<PegawaiModel> getPegawaiByInstansi(InstansiModel instansi);
	
}

