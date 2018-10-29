package com.apap.tugas1.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.repository.InstansiDb;
import com.apap.tugas1.repository.JabatanDb;
import com.apap.tugas1.repository.ProvinsiDb;

@Service
@Transactional 
public class ProvinsiServiceImp implements ProvinsiService{
	@Autowired
	private ProvinsiDb provinsiDb;

	@Override
	public List<ProvinsiModel> findAllProvinsi() {
		return provinsiDb.findAll();
	}

	@Override
	public Optional<ProvinsiModel> getProvinsiDetailById(long idProvinsi) {
		// TODO Auto-generated method stub
		return provinsiDb.findById(idProvinsi);
	}


	
}