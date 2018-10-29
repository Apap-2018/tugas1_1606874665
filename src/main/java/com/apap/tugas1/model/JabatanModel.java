package com.apap.tugas1.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="Jabatan")
public class JabatanModel implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull
	@Column (name= "id", nullable = false)
	private long id_jabatan;
	
	@NotNull
	@Size (max = 255)
	@Column (name= "nama", nullable = false)
	private String nama;
	
	@NotNull
	@Size (max = 255)
	@Column (name= "deskripsi", nullable = false)
	private String deskripsi_jabatan;
	
	@NotNull
	@Column (name= "gaji_pokok", nullable = false)
	private Double gaji_pokok;

	@ManyToMany(fetch = FetchType.LAZY,
			cascade = {
					CascadeType.PERSIST,
					CascadeType.MERGE
			},
			mappedBy = "jabatanList")
		private List<PegawaiModel> pegawaiList;

	public long getId_jabatan() {
		return id_jabatan;
	}


	public void setId_jabatan(long id_jabatan) {
		this.id_jabatan = id_jabatan;
	}

	public String getNama() {
		return nama;
	}


	public void setNama(String nama) {
		this.nama = nama;
	}


	public String getDeskripsi_jabatan() {
		return deskripsi_jabatan;
	}


	public void setDeskripsi_jabatan(String deskripsi_jabatan) {
		this.deskripsi_jabatan = deskripsi_jabatan;
	}


	public Double getGaji_pokok() {
		return gaji_pokok;
	}


	public void setGaji_pokok(Double gaji_pokok) {
		this.gaji_pokok = gaji_pokok;
	}


	public List<PegawaiModel> getPegawaiList() {
		return pegawaiList;
	}

	public void setPegawaiList(List<PegawaiModel> pegawaiList) {
		this.pegawaiList = pegawaiList;
	}

}






