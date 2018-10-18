package com.apap.tugas1.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonIgnore;
/*import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;*/


@Entity
@Table(name="Provinsi")
public class ProvinsiModel implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull
	@Size (max = 10)
	@Column (name= "id", nullable = false)
	private Integer id_provinsi;
	
	@NotNull
	@Size (max = 255)
	@Column (name= "nama", nullable = false)
	private String nama_provinsi;
	
	@NotNull
	@Column (name= "presentase_tunjangan", nullable = false)
	private Double presentase_tunjangan;

	public Integer getId_provinsi() {
		return id_provinsi;
	}

	public void setId_provinsi(Integer id_provinsi) {
		this.id_provinsi = id_provinsi;
	}

	public String getNama_provinsi() {
		return nama_provinsi;
	}

	public void setNama_provinsi(String nama_provinsi) {
		this.nama_provinsi = nama_provinsi;
	}

	public Double getPresentase_tunjangan() {
		return presentase_tunjangan;
	}

	public void setPresentase_tunjangan(Double presentase_tunjangan) {
		this.presentase_tunjangan = presentase_tunjangan;
	}
	
	
}






