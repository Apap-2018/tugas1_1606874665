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
		model.addAttribute("title", "Informasi jabatan");
		return "view-jabatan";
	}
	
	@RequestMapping(value = "/jabatan/update/{id}", method = RequestMethod.GET)
	private String updateJabatan(@RequestParam(value = "id") long id, Model model) {
		JabatanModel jabatan = jabatanService.getJabatanDetailById(id).get();
		model.addAttribute("jabatan",jabatan);
		model.addAttribute("title", "Update Jabatan");
		return "update-jabatan";
	}
	
	/**@RequestMapping(value = "/jabatan/update/{id}", method = RequestMethod.POST)
	private String updateJabatanSubmit(@PathVariable (value = "id") long id, @ModelAttribute Optional<JabatanModel> jabatan) {
		if(jabatan.isPresent()) {
			jabatanService.updateJabatan(id, dealer);
			return "update";
		}
		return "error";
	}*/
	
}
