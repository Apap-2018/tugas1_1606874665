package com.apap.tugas1.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonIgnore;
/*import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;*/


@Entity
@Table(name="Instansi")
public class InstansiModel implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull
	@Size (max = 20)
	@Column (name= "id", nullable = false)
	private long id_instansi;
	
	@NotNull
	@Size (max = 255)
	@Column (name= "nama", nullable = false)
	private String nama_instansi;
	
	@NotNull
	@Size (max = 255)
	@Column (name= "deskripsi", nullable = false)
	private String deskripsi_instansi;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Size (max = 20)
	@NotNull
	@JoinColumn(name="id_provinsi", referencedColumnName = "id", nullable = false)
	@JsonIgnore
	private ProvinsiModel provinsi;
	
	@OneToMany(mappedBy = "instansi", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<PegawaiModel> pegawai;

	

	public List<PegawaiModel> getPegawai() {
		return pegawai;
	}


	public void setPegawai(List<PegawaiModel> pegawai) {
		this.pegawai = pegawai;
	}


	public long getId_instansi() {
		return id_instansi;
	}


	public void setId_instansi(long id_instansi) {
		this.id_instansi = id_instansi;
	}


	public String getNama_instansi() {
		return nama_instansi;
	}


	public void setNama_instansi(String nama_instansi) {
		this.nama_instansi = nama_instansi;
	}


	public String getDeskripsi_instansi() {
		return deskripsi_instansi;
	}


	public void setDeskripsi_instansi(String deskripsi_instansi) {
		this.deskripsi_instansi = deskripsi_instansi;
	}


	public ProvinsiModel getProvinsi() {
		return provinsi;
	}


	public void setProvinsi(ProvinsiModel provinsi) {
		this.provinsi = provinsi;
	}

	
}






