package uned.dlr.pfc.controller;

import uned.dlr.pfc.model.WhatToShareEnum;

public class CompartirData{
	
	public CompartirData() {
		super();
	}
	public CompartirData(String nombre, String whatToShare) {
		super();
		this.nombre = nombre;
		this.whatToShare = whatToShare;
	}
	String nombre;
	String whatToShare;
	public String getWhatToShare() {
		return whatToShare;
	}
	public void setWhatToShare(String whatToShare) {
		this.whatToShare = whatToShare;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public WhatToShareEnum toEnumWhatToShare(){
		if(whatToShare==null)  return WhatToShareEnum.both;
		if(whatToShare.equalsIgnoreCase("test")) return WhatToShareEnum.test;
		else if(whatToShare.equalsIgnoreCase("code")) return WhatToShareEnum.code;
		else return WhatToShareEnum.both;
	}
	
}
