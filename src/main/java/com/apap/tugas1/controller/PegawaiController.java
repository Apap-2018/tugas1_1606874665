package com.apap.tugas1.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.hibernate.validator.constraints.pl.NIP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.apap.tugas1.model.*;
import com.apap.tugas1.repository.JabatanDb;
import com.apap.tugas1.service.*;

import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PegawaiController {
	@Autowired
	private PegawaiService pegawaiService;
	@Autowired
	private JabatanService jabatanService;
	@Autowired
	private InstansiService instansiService;
	@Autowired
	private ProvinsiService provinsiService;
	
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
		List<PegawaiModel> allPegawai = instansi.getPegawaiInstansi();
		
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
	
	@RequestMapping(value = "/pegawai/add")
	private String addPegawai (Model model) {
		PegawaiModel pegawai = new PegawaiModel();
		pegawai.setJabatanList(new ArrayList());
		pegawai.getJabatanList().add(new JabatanModel());
		List<JabatanModel> listJabatan = jabatanService.findAllJabatan();
		List<ProvinsiModel> listProvinsi = provinsiService.findAllProvinsi();
		List<InstansiModel> listInstansi = instansiService.findAllInstansi();
		
		System.out.println("jab list add pegawai: " +pegawai.getJabatanList().size());
		for(int i=0; i<pegawai.getJabatanList().size();i++) {
			System.out.println("jab list:" +pegawai.getJabatanList().get(i).getNama());
		}
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("listProvinsi", listProvinsi);
		model.addAttribute("listInstansi", listInstansi);
		model.addAttribute("title", "Tambah Pegawai");
		return "add-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/tambah/instansi",method = RequestMethod.GET)
	private @ResponseBody List<InstansiModel> cekInstansi(@RequestParam(value="provinsiId") long provinsiId) {
		System.out.println(provinsiId + "AAAAAAAAAAAAAAA");
		ProvinsiModel provinsi = provinsiService.getProvinsiDetailById(provinsiId).get();
		return provinsi.getInstansiList();
	}
	
	@RequestMapping(value = "/pegawai/add", method = RequestMethod.POST, params= {"addJabatan"})
	private String addJabatan (@ModelAttribute PegawaiModel pegawai,Model model, BindingResult bindingResult) {
		if (pegawai.getJabatanList() == null) {
			pegawai.setJabatanList(new ArrayList());
		}
		pegawai.getJabatanList().add(new JabatanModel());
		List<JabatanModel> listJabatan = jabatanService.findAllJabatan();
		List<ProvinsiModel> listProvinsi = provinsiService.findAllProvinsi();
		List<InstansiModel> listInstansi = instansiService.findAllInstansi();
		
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("listProvinsi", listProvinsi);
		model.addAttribute("listInstansi", listInstansi);
		model.addAttribute("title", "Tambah Pegawai");
		return "add-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/add", method = RequestMethod.POST, params= {"submit"})
	private String addPegawaiSubmit (@ModelAttribute PegawaiModel pegawai,Model model) {
		
		System.out.println(pegawai.getNama_pegawai());
		System.out.println("nip: "+ pegawaiService.generateNip(pegawai));
		
		
		String nipPegawai = pegawaiService.generateNip(pegawai);
		
		pegawai.setNip(nipPegawai);
		pegawai.setId(pegawaiService.findAllPegawai().size());
		pegawaiService.addPegawai(pegawai);
		
		
		System.out.println("FIX NAMA: " + pegawai.getNama_pegawai());
		System.out.println("instansi->"+ pegawai.getInstansi().getNama_instansi());
		System.out.println("nip: "+ pegawaiService.generateNip(pegawai));
		
		model.addAttribute("pegawai", pegawai);
		
		return "add-pegawai-success";
		
	}
	
	 @RequestMapping(value = "/pegawai/cari",method = RequestMethod.GET)
	private  String filter(@RequestParam(value = "idProvinsi", required=false) Optional<String> idProvinsi,
			@RequestParam(value="idInstansi",  required=false) Optional<String> id_instansi,
			@RequestParam(value="idJabatan", required=false) Optional<String> id_jabatan,
			Model model) {
		List<JabatanModel> allJabatan = jabatanService.findAllJabatan();
		List<InstansiModel> allInstansi = instansiService.findAllInstansi();
		List<ProvinsiModel> allProvinsi = provinsiService.findAllProvinsi();
		model.addAttribute("allInstansi",allInstansi);
		model.addAttribute("allProvinsi",allProvinsi);
		model.addAttribute("allJabatan",allJabatan);
		model.addAttribute("title", "Cari Pegawai");
		
		List<PegawaiModel> allPegawai = pegawaiService.findAllPegawai();
		List<PegawaiModel> result = new ArrayList<>();
		if (idProvinsi.isPresent()) {
			System.out.println("is it even enter this");
			if (id_instansi.isPresent() && id_jabatan.isPresent()) {
				// ALL instansi, jabatan, provinsi
				//pke instansi per provinsi aja
				System.out.println("masuk id instansi and jabatan");
				List<PegawaiModel> temp = new ArrayList<>();
				Long idInstansi = Long.parseLong(id_instansi.get());
				InstansiModel instansi = instansiService.getInstansiDetailById(idInstansi).get();
				System.out.println(instansi.getNama_instansi());
				Long idJabatan = Long.parseLong(id_jabatan.get());
				JabatanModel jabatan = jabatanService.getJabatanDetailById(idJabatan).get();
				System.out.println(jabatan.getNama());
				temp = pegawaiService.getPegawaiByInstansi(instansi);
				System.out.println(temp.size());
				for (PegawaiModel peg : temp) {
					for (JabatanModel jab : peg.getJabatanList()) {
						if (jab.equals(jabatan)) {
							result.add(peg);
						}
					}
				}
				System.out.println(result.size());
			}
			else if (!(id_instansi.isPresent()) && id_jabatan.isPresent()) {
				//provinsi
				//jabatan
				//provinsi & jabatan
				List<PegawaiModel> temp = new ArrayList<>();
				Long idProv = Long.parseLong(idProvinsi.get());
				ProvinsiModel prov = provinsiService.getProvinsiDetailById(idProv).get();
				for (PegawaiModel peg : allPegawai) {
					if (peg.getInstansi().getProvinsi().equals(prov)) {
						temp.add(peg);
					}
				}
				Long idJabatan = Long.parseLong(id_jabatan.get());
				JabatanModel jabatan = jabatanService.getJabatanDetailById(idJabatan).get();
				for (PegawaiModel peg : temp) {
					for (JabatanModel jab : peg.getJabatanList()) {
						if (jab.equals(jabatan)) {
							result.add(peg);
						}
					}
				}
			}
			else if(id_instansi.isPresent() && !(id_jabatan.isPresent())) { 
				//provinsi dan instansi
				System.out.println("provinsi dan instansi");
				Long idInstansi = Long.parseLong(id_instansi.get());
				InstansiModel instansi = instansiService.getInstansiDetailById(idInstansi).get();
				result = pegawaiService.getPegawaiByInstansi(instansi);
				
			}
			else if(!(id_instansi.isPresent()) && !(id_jabatan.isPresent())) {
				//just provinsi
				Long idProv = Long.parseLong(idProvinsi.get());
				ProvinsiModel prov = provinsiService.getProvinsiDetailById(idProv).get();
				for (PegawaiModel peg : allPegawai) {
					if(peg.getInstansi().getProvinsi().equals(prov)) {
						result.add(peg);
					}
				}
			}
		}
		else {
			//jabatan
			//instansi
			//jabatan dan instansi-worked
			if (id_jabatan.isPresent() && id_instansi.isPresent()) {
				List<PegawaiModel> temp = new ArrayList<>();
				Long idInstansi = Long.parseLong(id_instansi.get());
				InstansiModel instansi = instansiService.getInstansiDetailById(idInstansi).get();
				Long idJabatan = Long.parseLong(id_jabatan.get());
				JabatanModel jabatan = jabatanService.getJabatanDetailById(idJabatan).get();
				temp = pegawaiService.getPegawaiByInstansi(instansi);
				for (PegawaiModel peg : temp) {
					for (JabatanModel jab : peg.getJabatanList()) {
						if (jab.equals(jabatan)) {
							result.add(peg);
						}
					}
				}
			}
			
			//jabatan doang
			else if(id_jabatan.isPresent() && !(id_instansi.isPresent())) {
				Long idJabatan = Long.parseLong(id_jabatan.get());
				JabatanModel jabatan = jabatanService.getJabatanDetailById(idJabatan).get();
				for (PegawaiModel peg : allPegawai) {
					for (JabatanModel jab : peg.getJabatanList()) {
						if (jab.equals(jabatan)) {
							result.add(peg);
						}
					}
				}
			}
			//instansi doang
			else if(!(id_jabatan.isPresent()) && id_instansi.isPresent()) {
				Long idInstansi = Long.parseLong(id_instansi.get());
				InstansiModel instansi = instansiService.getInstansiDetailById(idInstansi).get();
				result = pegawaiService.getPegawaiByInstansi(instansi);
			}
			else if(!(id_jabatan.isPresent()) && !(id_instansi.isPresent())) {
				result = null;
			}
		}
		model.addAttribute("allData",result);
		return "cari-pegawai";
	}
	 
	
	@RequestMapping(value = "/pegawai/update", method = RequestMethod.GET)
	private String updatePegawai(@RequestParam String nip, Model model) {
		System.out.println("update pegawai");
		PegawaiModel pegawai = pegawaiService.getPegawaiDetailByNip(nip).get();
		
		List<JabatanModel> listJabatan = jabatanService.findAllJabatan();
		List<ProvinsiModel> listProvinsi = provinsiService.findAllProvinsi();
		List<InstansiModel> listInstansi = instansiService.findAllInstansi();
		
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("listProvinsi", listProvinsi);
		model.addAttribute("listInstansi", listInstansi);
		model.addAttribute("title", "Update Pegawai");
		
		return "update-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/update", method = RequestMethod.POST, params={"submit"})
	private String updatePegawaiSubmit(@ModelAttribute PegawaiModel pegawai, Model model) {	
		System.out.println("update");
		System.out.println("tes->"+pegawai.getId());
		PegawaiModel pegawaiUpdated = pegawaiService.getPegawaiDetailById(pegawai.getId()).get();
		
		pegawaiUpdated.setNama_pegawai(pegawai.getNama_pegawai());
		pegawaiUpdated.setTempat_lahir(pegawai.getTempat_lahir());
		pegawaiUpdated.setTanggal_lahir(pegawai.getTanggal_lahir());
		pegawaiUpdated.setTahun_masuk(pegawai.getTahun_masuk());
		pegawaiUpdated.setInstansi(pegawai.getInstansi());
		pegawaiUpdated.setJabatanList(pegawai.getJabatanList());
		
		String nipPegawai=pegawaiService.generateNip(pegawaiUpdated);
		pegawaiUpdated.setNip(nipPegawai);
		
		pegawaiService.addPegawai(pegawaiUpdated);
		
		return "update-pegawai-success";
	}
	
	@RequestMapping(value="/pegawai/update",method = RequestMethod.POST, params= {"addJabatan"})
	private String addRowUpdate (@ModelAttribute PegawaiModel pegawai, Model model, BindingResult bindingResult) {
		if (pegawai.getJabatanList() == null) {
			pegawai.setJabatanList(new ArrayList());
		}
		System.out.println(pegawai.getJabatanList().size());
		pegawai.getJabatanList().add(new JabatanModel());
		
		List<JabatanModel> jab = jabatanService.findAllJabatan();
		List<ProvinsiModel> prov = provinsiService.findAllProvinsi();
		model.addAttribute("listProvinsi", prov);
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("jabatanList",jab);
		model.addAttribute("title", "Ubah Data Pegawai");
		return "update-pegawai";
	}
}
