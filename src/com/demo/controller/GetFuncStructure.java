package com.demo.controller;

import java.io.File;
import java.util.Map;

public class GetFuncStructure {

	public String FUNC_LOCATION;
	public String TABLE_NAME;
	public String CLASS_NAME;

	public String DB_CONN_URL;
	public String DB_CONN_NAME;
	public String DB_CONN_PASSWORD;
	public String DB_CONN_DRIVER;

	public String DIR_CONTROLLER;
	public String DIR_DOMAIN;
	public String DIR_REPOSITORY;
	public String DIR_SERVICE;
	public String DIR_SERVICE_IMPL;

	public GetFuncStructure(Map<String, String> params) {
		FUNC_LOCATION = params.get("func_location");
		TABLE_NAME = params.get("table_name");
		CLASS_NAME = params.get("class_name");
		DB_CONN_URL = params.get("db_conn_url");
		DB_CONN_NAME = params.get("db_conn_name");
		DB_CONN_PASSWORD = params.get("db_conn_password");
		DB_CONN_DRIVER = params.get("db_conn_driver");

		DIR_CONTROLLER = FUNC_LOCATION + "/controller";
		DIR_DOMAIN = FUNC_LOCATION + "/domain";
		DIR_REPOSITORY = FUNC_LOCATION + "/repository";
		DIR_SERVICE = FUNC_LOCATION + "/service";
		DIR_SERVICE_IMPL = FUNC_LOCATION + "/service/impl";

	}

	public boolean getDirectories() {
		File controller = new File(this.DIR_CONTROLLER);
		File domain = new File(this.DIR_DOMAIN);
		File repository = new File(this.DIR_REPOSITORY);
		File service = new File(this.DIR_SERVICE);
		File impl = new File(this.DIR_SERVICE_IMPL);

		boolean suc = false;
		if (!controller.mkdir() || !domain.mkdir() || !repository.mkdir() || !service.mkdir() || !impl.mkdir()) {
			System.out.println("目录结构创建失败");
		} else {
			suc = true;
			System.out.println("已的创建目录结构");
		}
		return suc;
	}

}
