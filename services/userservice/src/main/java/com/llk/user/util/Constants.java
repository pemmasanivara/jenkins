package com.llk.user.util;

public class Constants {
	private Constants() {};
	public static final String DATE_FORMAT="MM/dd/yyyy";
	public static final String API_VERSION="/api/v1";
	public static final String URL_USER="/save";
	public static final String URL_PROFILE="/profile";
	public static final String TOKEN_TYPE = "Bearer ";
	public static final String SQL_ROLES_RES="select distinct r.role_id,r.role_name,rs.res_id,rs.res_name\r\n" + 
			"from ROLES r,RESPONSABILITIES rs,ROLES_RES_MAPPING rm \r\n" + 
			"where rm.res_id=rs.res_id \r\n" + 
			"and rm.role_id=r.role_id \r\n" + 
			"and rm.role_id\r\n" + 
			"and rm.ROLE_ID IN(select distinct role_id from PARTY_ROLES_MAPING where party_id=?)\r\n" + 
			"ORDER BY r.role_id";
}
