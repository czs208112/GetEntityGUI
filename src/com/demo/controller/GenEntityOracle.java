package com.demo.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Map;

/**
 * 该类原本已损坏，copy自GenEntityMysql
 * 
 * @author zhangs
 *
 */
public class GenEntityOracle {

	public String FUNC_LOCATION;

	public String TABLE_NAME;
	public String CLASS_NAME;

	public String DB_CONN_URL;
	public String DB_CONN_NAME;
	public String DB_CONN_PASSWORD;
	public String DB_CONN_DRIVER;

	// -------------------
	public String DIR_CONTROLLER;
	public String DIR_DOMAIN;
	public String DIR_REPOSITORY;
	public String DIR_SERVICE;
	public String DIR_SERVICE_IMPL;

	/**************************************************************************************************/

	private String authorName = "zhangsheng";// 作者姓名

	private String primaryKey = "";
	private String[] colnames; // 列名数组
	private String[] colTypes; // 列名类型数组
	private int[] colSizes; // 列名大小数组
	private boolean f_util = false; // 是否导入包java.util.*
	private boolean f_sql = false; // 是否导入包java.sql.*

	public GenEntityOracle(Map<String, String> params) throws Exception {
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

		action();
	}

	public void action() throws Exception {
		// 创建连接
		Connection con;
		// 查要生成实体类的�?
		String sql = "select * from " + this.TABLE_NAME;
		Statement pStemt = null;
		Class.forName(this.DB_CONN_DRIVER);
		con = DriverManager.getConnection(this.DB_CONN_URL, this.DB_CONN_NAME, this.DB_CONN_PASSWORD);
		pStemt = con.prepareStatement(sql);
		ResultSet rs = pStemt.executeQuery(sql);
		ResultSetMetaData rsmd = rs.getMetaData();
		DatabaseMetaData dbMeta = con.getMetaData();
		ResultSet pkRSet = dbMeta.getPrimaryKeys(null, null, this.TABLE_NAME);
		while (pkRSet.next()) {
			primaryKey = pkRSet.getObject(4).toString();
		}
		int size = rsmd.getColumnCount(); // 统计�?
		colnames = new String[size];
		colTypes = new String[size];
		colSizes = new int[size];
		for (int i = 0; i < size; i++) {
			colnames[i] = rsmd.getColumnName(i + 1).toLowerCase();
			colTypes[i] = rsmd.getColumnTypeName(i + 1);

			if (colTypes[i].equalsIgnoreCase("datetime")) {
				f_util = true;
			}
			if (colTypes[i].equalsIgnoreCase("image") || colTypes[i].equalsIgnoreCase("text")) {
				f_sql = true;
			}
			colSizes[i] = rsmd.getColumnDisplaySize(i + 1);
		}

		String content = parse(colnames, colTypes, colSizes);

		File directory = new File("");
		// System.out.println("绝对路径�?+directory.getAbsolutePath());
		// System.out.println("相对路径�?+directory.getCanonicalPath());
		// String path = this.getClass().getResource("").getPath();
		// System.out.println(path);
		// System.out.println("src/?/" +
		// path.substring(path.lastIndexOf("/com/", path.length())));
		// String outputPath = directory.getAbsolutePath() + "\\src\\" +
		// this.DIR_DOMAIN.replace(".", "\\") + "\\" +
		// this.className + ".java";
		String outputPath = this.DIR_DOMAIN.replace(".", "\\") + "\\" + this.CLASS_NAME + ".java";
		System.out.println("文件已生成至" + outputPath);
		System.out.println("请刷新项目后，拷贝文件，并手动导入部分所需包");
		// String outputPath = packageOutPath + "/" + className +
		// ".java";
		FileWriter fw = new FileWriter(outputPath);
		PrintWriter pw = new PrintWriter(fw);
		pw.println(content);
		pw.flush();
		pw.close();
		// try {
		// con.close();
		// } catch (SQLException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}

	/**
	 * 功能：生成实体类主体代码
	 * 
	 * @param colnames
	 * @param colTypes
	 * @param colSizes
	 * @return
	 */
	private String parse(String[] colnames, String[] colTypes, int[] colSizes) {
		StringBuffer sb = new StringBuffer();
		// sb.append("package " + this.packageOutPath + ";\r\n");
		// sb.append("\r\n");
		// 判断是否导入工具�?
		if (f_util) {
			sb.append("import java.util.Date;\r\n");
		}
		if (f_sql) {
			sb.append("import java.sql.*;\r\n");
		}
		sb.append("import java.io.Serializable;\r\n");
		sb.append("import javax.persistence.Column;\r\n");
		sb.append("import javax.persistence.Entity;\r\n");
		sb.append("import javax.persistence.Id;\r\n");
		sb.append("import javax.persistence.Table;\r\n\r\n");

		// 注释部分
		sb.append("/**\r\n");
		sb.append(" * " + this.CLASS_NAME + " 实体类\r\n");
		sb.append(" * @author " + this.authorName + "\r\n");
		sb.append(" */\r\n");
		// 实体部分
		sb.append("\r\n@Entity\r\n");
		sb.append("@Table(name = \"" + this.TABLE_NAME + "\")\r\n");
		sb.append("public class " + this.CLASS_NAME + " implements Serializable {\r\n");
		sb.append("\r\n");
		sb.append("\tprivate static final long serialVersionUID = 1L;\r\n");
		sb.append("\r\n");
		processAllAttrs(sb);// 属性
		sb.append("\r\n");
		processAllMethod(sb);// get set方法
		sb.append("}\r\n");

		// System.out.println(sb.toString());
		return sb.toString();
	}

	/**
	 * 功能：生成所有属性
	 *
	 * @param sb
	 */
	private void processAllAttrs(StringBuffer sb) {
		for (int i = 0; i < colnames.length; i++) {
			if (primaryKey.toLowerCase().trim().equals(colnames[i])) {
				sb.append("\t@Id\r\n");
			}
			sb.append("\t@Column(name = \"" + colnames[i].toUpperCase() + "\")\r\n");
			sb.append("\tprivate " + sqlType2JavaType(colTypes[i]) + " " + colnames[i] + ";\r\n");
		}

	}

	/**
	 * 功能：生成所有方法
	 * 
	 * @param sb
	 */
	private void processAllMethod(StringBuffer sb) {
		for (int i = 0; i < colnames.length; i++) {
			sb.append("\tpublic void set" + initcap(colnames[i]) + "(" + sqlType2JavaType(colTypes[i]) + " "
					+ colnames[i] + "){\r\n");
			sb.append("\t\tthis." + colnames[i] + "=" + colnames[i] + ";\r\n");
			sb.append("\t}\r\n");
			sb.append("\tpublic " + sqlType2JavaType(colTypes[i]) + " get" + initcap(colnames[i]) + "(){\r\n");
			sb.append("\t\treturn " + colnames[i] + ";\r\n");
			sb.append("\t}\r\n");
		}

	}

	/**
	 * 功能：将输入字符串的首字母改成大写
	 * 
	 * @param str
	 * @return
	 */
	private String initcap(String str) {

		char[] ch = str.toCharArray();
		if (ch[0] >= 'a' && ch[0] <= 'z') {
			ch[0] = (char) (ch[0] - 32);
		}

		return new String(ch);
	}

	/**
	 * 功能：获得列的数据类�?
	 *
	 * @param sqlType
	 * @return
	 */
	private String sqlType2JavaType(String sqlType) {

		if (sqlType.equalsIgnoreCase("binary_double")) {
			return "double";
		} else if (sqlType.equalsIgnoreCase("binary_float")) {
			return "float";
		} else if (sqlType.equalsIgnoreCase("blob")) {
			return "byte[]";
		} else if (sqlType.equalsIgnoreCase("blob")) {
			return "byte[]";
		} else if (sqlType.equalsIgnoreCase("char") || sqlType.equalsIgnoreCase("nvarchar2")
				|| sqlType.equalsIgnoreCase("varchar2")) {
			return "String";
		} else if (sqlType.equalsIgnoreCase("date") || sqlType.equalsIgnoreCase("timestamp")
				|| sqlType.equalsIgnoreCase("timestamp with local time zone")
				|| sqlType.equalsIgnoreCase("timestamp with time zone")) {
			return "Date";
		} else if (sqlType.equalsIgnoreCase("number")) {
			return "Long";
		}

		return "String";
	}

}