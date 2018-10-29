package com.apap.tugas1.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.PegawaiModel;

public interface PegawaiDb extends JpaRepository<PegawaiModel, Long> {

	Optional <PegawaiModel> findByNip (String nip);

	List<PegawaiModel> findByInstansi(InstansiModel instansi);	
	
}
