package com.apap.tugas1.service;

import java.util.List;
import java.util.Optional;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.repository.JabatanDb;

public interface JabatanService {
	Optional<JabatanModel> getJabatanDetailById(long id);
	void addJabatan(JabatanModel jabatan);
	List<JabatanModel> findAllJabatan();
}

