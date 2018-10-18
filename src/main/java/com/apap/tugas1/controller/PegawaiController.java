package com.apap.tugas1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.apap.tugas1.model.*;
import com.apap.tugas1.repository.JabatanDb;
import com.apap.tugas1.service.*;

@Controller
public class PegawaiController {
	@Autowired
	private PegawaiService pegawaiService;
	@Autowired
	private JabatanService jabatanService;
	
	
	@RequestMapping("/")
	private String home(Model model) {
		List<JabatanModel> listJabatan = jabatanService.findAllJabatan();
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("title", "Home");
		return "home";
	}
	
	@RequestMapping(value = "/pegawai/view", method = RequestMethod.GET)
	private String viewPegawai (String nip, Model model) {
		PegawaiModel pegawai = pegawaiService.getPegawaiDetailByNip(nip).get();
		List<JabatanPegawaiModel> listJabatanPegawai = pegawai.getListJabatanPegawai();
		
		Double gajiMax = (double) 0;
		for(int i = 0; i < listJabatanPegawai.size(); i++) {
			if(listJabatanPegawai.get(i).getJabatan().getGaji_pokok() > gajiMax) {
				gajiMax = listJabatanPegawai.get(i).getJabatan().getGaji_pokok();
			}
		}
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("gaji", gajiMax);
		model.addAttribute("title", "Info Pegawai");
		return "view-pegawai";
	}
}
