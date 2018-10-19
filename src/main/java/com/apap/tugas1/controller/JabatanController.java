package com.apap.tugas1.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.apap.tugas1.model.*;
import com.apap.tugas1.repository.JabatanDb;
import com.apap.tugas1.service.*;


@Controller
public class JabatanController {
	@Autowired
	private JabatanService jabatanService;
	
	@RequestMapping(value = "/jabatan/add", method = RequestMethod.GET)
	private String add (Model model) {
		model.addAttribute("jabatan",new JabatanModel());
		model.addAttribute("title", "Tambah Jabatan");
		return "add-jabatan";
	}
	
	@RequestMapping(value = "/jabatan/add", method = RequestMethod.POST)
	private String addJabatan (@ModelAttribute JabatanModel jabatan) {
		jabatanService.addJabatan(jabatan);
		return "add";
	}
	
	@RequestMapping(value = "/jabatan/view", method = RequestMethod.GET)
	private String viewJabatan (@RequestParam(value="id_jabatan") long id_jabatan, Model model) {
		JabatanModel jabatan = jabatanService.getJabatanDetailById(id_jabatan).get();
		model.addAttribute("jabatan", jabatan);
		for(int i = 0; i < jabatan.getPegawaiList().size(); i++) {
			System.out.println(jabatan.getPegawaiList().get(i).getNama_pegawai());
		}
		
		//model.addAttribute("jmlPegawai", jmlPegawai);
		model.addAttribute("title", "Informasi jabatan");
		return "view-jabatan";
	}
	
	@RequestMapping(value = "/jabatan/update", method = RequestMethod.GET)
	private String updateJabatan(@RequestParam(value = "id_jabatan") long id, Model model) {
		JabatanModel jabatan = jabatanService.getJabatanDetailById(id).get();
		model.addAttribute("jabatan",jabatan);
		model.addAttribute("title", "Update Jabatan");
		return "update-jabatan";
	}
	
	@RequestMapping(value = "/jabatan/update", method = RequestMethod.POST)
	private String updateJabatanSubmit(@ModelAttribute JabatanModel jabatan, Model model) {
		JabatanModel archieve = jabatanService.getJabatanDetailById(jabatan.getId_jabatan()).get();
		archieve.setNama(jabatan.getNama());
		archieve.setDeskripsi_jabatan((jabatan.getDeskripsi_jabatan()));
		archieve.setGaji_pokok((jabatan.getGaji_pokok()));
		jabatanService.updateJabatan(archieve);
		model.addAttribute("title", "Jabatan berhasil diubah");
		return "update";
	}
	
	@RequestMapping (value = "/jabatan/delete", method = RequestMethod.POST)
	private String deleteJabatan(@RequestParam(value="id_jabatan") long id_jabatan, Model model) {
		jabatanService.deleteJabatan(id_jabatan);
		return "delete";
	}
	
	@RequestMapping(value = "/jabatan/view-all", method = RequestMethod.GET)
	private String viewAll (Model model) {
		List<JabatanModel> jabatan = jabatanService.findAllJabatan();
		
		model.addAttribute("listJabatan", jabatan);
		model.addAttribute("title", "Informasi Dealer");
		return "view-all-jabatan";
	}
}
