package com.llk.admin.util;

public class Constants {
	public static final String DATE_FORMAT="MM/dd/yyyy";
	public static final String API_VERSION = "/api/v1";
	public static final String URL_ROLE = "/roles";
	public static final String URL_RESPONSABILITY = "/responsability";
	public static final String URL_ROLE_RESPONSABILITY_MAPPING = "/roleresponsabilitymapping";
	public static final String URL_USER_ROLE_MAPPING="/userroles";
	public static final String URL_THERAPIST_LEVEL="/therapistlevel";
	public static final String URL_USER="/user";
	public static final String URL_USERS="/users";
	public static final String URL_THERAPIST="/therapist";
	public static final String URL_CLIENT="/client";
	public static final String ERROR_FAILURE="Failure";
	public static final String ERROR_MESSAGE="Unable to perform action.Please try agian.";
	public static final String PROFILE_DEV="dev";
	
	public static final String URL_ROLE_RESPONSABILITY_EXISTING="/roleResExist/{roleId}";
	public static final String URL_USER_ROLE_EXISTING="/roleToUserExist/{partyId}";
	
	public static final String SQL_ROLES_RES="select distinct m.ID, r.ROLE_ID, r.ROLE_NAME, m.RES_ID , rs.RES_NAME from ROLES r, RESPONSABILITIES rs, ROLES_RES_MAPPING m where r.ROLE_ID=m.ROLE_ID and rs.RES_ID=m.RES_ID";
	public static final String SQL_EXISTING_RES=" select distinct r.RES_NAME, m.role_id, r.res_id from ROLES_RES_MAPPING m, RESPONSABILITIES r where r.res_id=m.res_id and m.role_id";
	
	
	public static final String URL_UPDATE_THERAPIST="/updateTherapist";
	public static final String URL_UPDATE_CLIENT="/updateClient";
	
	public static final String URL_USER_ROLE="/roleToUser";
	public static final String SQL_SEARCH_USER="select distinct p.party_id, p.first_name,p.last_name, p.EMAIL\r\n" + 
			" from ROLES r, PARTY p,PARTY_ROLES_MAPING m \r\n" + 
			" where p.party_id=m.party_id and r.role_id=m.role_id";
	
	public static final String SQL_ROLES_Mapping=" select distinct m.id,m.role_id, r.ROLE_NAME, p.PARTY_ID\r\n" + 
			" from ROLES r, PARTY_ROLES_MAPING m, PARTY p\r\n" + 
			" where r.ROLE_ID=m.ROLE_ID and p.PARTY_ID=m.PARTY_ID";
	
	
	public static final String SQL_THERAPISTS_ALL = "select p.party_id,t.therapist_id,p.first_name,p.last_name,t.status from PARTY p,THERAPIST t where p.party_id=t.party_id And t.status='INACTIVE' ";
	public static final String SQL_UPDATE_THERAPIST = "UPDATE THERAPIST SET STATUS='ACTIVE' WHERE THERAPIST_ID=:THERAPIST_ID";
	
	public static final String SQL_CLIENTS_ALL = "select p.party_id,c.client_id,p.first_name,p.last_name,c.status from PARTY p,CLIENT c where p.party_id=c.party_id And c.status='INACTIVE' ";
	public static final String SQL_UPDATE_CLIENT = "UPDATE CLIENT SET STATUS='ACTIVE' WHERE CLIENT_ID=:CLIENT_ID";

}
