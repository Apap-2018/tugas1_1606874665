package com.apap.tugas1.controller;

import java.util.Date;
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
	@Autowired
	private InstansiService instansiService;
	
	@RequestMapping("/")
	private String home(Model model) {
		List<JabatanModel> listJabatan = jabatanService.findAllJabatan();
		List<InstansiModel> listInstansi = instansiService.findAllInstansi();
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("listInstansi", listInstansi);
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
	
	@RequestMapping (value = "/pegawai/termuda-tertua", method = RequestMethod.GET)
	private String viewTermudaTertua(@RequestParam(value="id_instansi") long id_instansi, Model model) {
		InstansiModel instansi = instansiService.getInstansiDetailById(id_instansi).get();
		List<PegawaiModel> allPegawai = instansi.getPegawai();
		
		int tua = 0;
		int muda = 100000;
		
		PegawaiModel pegawaiTertua = allPegawai.get(0);
		PegawaiModel pegawaiTermuda = allPegawai.get(0);
		PegawaiModel pegawainya;
		
		long skrg = System.currentTimeMillis();
		Date today = new Date(skrg);
		
		for(int i = 0; i < allPegawai.size(); i++) {
			pegawainya = allPegawai.get(i);
			Date birthday = pegawainya.getTanggal_lahir();
			int age = today.getYear() - birthday.getYear();
			if(age > tua) { 
				tua = age;
				pegawaiTertua = pegawainya;
			}
			if(age < muda) {
				muda = age;
				pegawaiTermuda = pegawainya;
			}
		}
		model.addAttribute("tertua", pegawaiTertua);
		model.addAttribute("termuda", pegawaiTermuda);
		model.addAttribute("title", "Informasi jabatan");
		return "view-termuda-tertua";
	}
	
	@RequestMapping(value = "/pegawai/add", method = RequestMethod.GET)
	private String add (Model model) {
		//model.addAttribute("jabatan",new JabatanModel());
		model.addAttribute("title", "Tambah Pegawai");
		return "add-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/add", method = RequestMethod.POST)
	private String addPegawai (@ModelAttribute PegawaiModel pegawai) {
		//jabatanService.addJabatan(jabatan);
		return "add";
	}
	
	@RequestMapping(value = "/pegawai/cari", method = RequestMethod.GET)
	private String cariPegawai (@ModelAttribute PegawaiModel pegawai) {
		//jabatanService.addJabatan(jabatan);
		return "cari-pegawai";
	}
}
