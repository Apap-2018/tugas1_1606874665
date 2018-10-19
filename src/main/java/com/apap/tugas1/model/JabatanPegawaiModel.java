package com.apap.tugas1.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="Jabatan_pegawai")
public class JabatanPegawaiModel implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull
	@Size (max = 20)
	@Column (name= "id", nullable = false)
	private long id_jabatan_pegawai;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_pegawai", referencedColumnName = "id", nullable = false)
	@JsonIgnore
	private PegawaiModel pegawai;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_jabatan", referencedColumnName = "id", nullable = false)
	@JsonIgnore
	private JabatanModel jabatan;

	public long getId_jabatan_pegawai() {
		return id_jabatan_pegawai;
	}

	public void setId_jabatan_pegawai(long id_jabatan_pegawai) {
		this.id_jabatan_pegawai = id_jabatan_pegawai;
	}

	public PegawaiModel getPegawai() {
		return pegawai;
	}

	public void setPegawai(PegawaiModel pegawai) {
		this.pegawai = pegawai;
	}

	public JabatanModel getJabatan() {
		return jabatan;
	}

	public void setJabatan(JabatanModel jabatan) {
		this.jabatan = jabatan;
	}
	
}






