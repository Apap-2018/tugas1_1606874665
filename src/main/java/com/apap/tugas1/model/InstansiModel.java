package com.apap.tugas1.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


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
	@JoinColumn(name = "id_provinsi", referencedColumnName = "id", nullable = false)
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	@JsonIgnore
	private ProvinsiModel provinsi;
	
	
	
	@OneToMany(mappedBy = "instansi", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore   
	private List<PegawaiModel> pegawaiInstansi;


	public List<PegawaiModel> getPegawaiInstansi() {
		return pegawaiInstansi;
	}


	public void setPegawaiInstansi(List<PegawaiModel> pegawaiInstansi) {
		this.pegawaiInstansi = pegawaiInstansi;
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






