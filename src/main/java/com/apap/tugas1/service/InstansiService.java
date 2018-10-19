package com.apap.tugas1.service;

import java.util.List;
import java.util.Optional;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;

public interface InstansiService {

	List<InstansiModel> findAllInstansi();

	Optional<InstansiModel> getInstansiDetailById(long id_instansi);
	
	
}
