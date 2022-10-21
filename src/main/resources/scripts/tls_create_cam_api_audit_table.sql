--------------------------------------------------------
--  File created - Thursday-Oct-08-2022   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Table TLS_CAM_API_AUDIT
--------------------------------------------------------

  CREATE TABLE "C##IDATLS"."TLS_CAM_API_AUDIT" 
   ("AUDIT_ID" VARCHAR2(22 BYTE), 
	"RESOURCE_TYPE" VARCHAR2(10 BYTE), 
	"REQUEST_URI" VARCHAR2(100 BYTE), 
	"REQUEST_DATE" TIMESTAMP (6), 
	"DATA" CLOB, 
	"RESPONSE_STATUS" NUMBER(3,0), 
	"STATUS" VARCHAR2(10 BYTE)
   ) 
--------------------------------------------------------
--  Constraints for Table TLS_CAM_API_AUDIT
--------------------------------------------------------

  ALTER TABLE "C##IDATLS"."TLS_CAM_API_AUDIT" MODIFY ("AUDIT_ID" NOT NULL ENABLE);
  ALTER TABLE "C##IDATLS"."TLS_CAM_API_AUDIT" MODIFY ("RESOURCE_TYPE" NOT NULL ENABLE);
  ALTER TABLE "C##IDATLS"."TLS_CAM_API_AUDIT" MODIFY ("REQUEST_URI" NOT NULL ENABLE);
  ALTER TABLE "C##IDATLS"."TLS_CAM_API_AUDIT" MODIFY ("REQUEST_DATE" NOT NULL ENABLE);
  ALTER TABLE "C##IDATLS"."TLS_CAM_API_AUDIT" MODIFY ("RESPONSE_STATUS" NOT NULL ENABLE);
